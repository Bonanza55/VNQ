import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;
 
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPSClient;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTPConnectionClosedException;

class postMsg {
  public postMsg() { }

    public String ash123(String base) {
      try{
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(base.getBytes("UTF8"));
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
           String hex = Integer.toHexString(0xff & hash[i]);
           if(hex.length() == 1) hexString.append('0');
             hexString.append(hex);
        }
        return hexString.toString();
      } catch(Exception ex){
        throw new RuntimeException(ex);
      }
    }
    String C = null;
    String D = null;

  public void PostMsg(String mailBox, String uploadFile, String uploadPath, String A, String B) {

    C = A;
    D = B;

    String MB         = mailBox.toLowerCase();
    String localFile  = null;
    InputStream input = null;

    String server = "host4.bakop.com";
    String user   = "bonanza50";
    String folder = "outbox/";
    int    port   = 21;
    ABCD fP     = new ABCD();
    String abcd   = fP.getABCD(C,D);

    localFile  = uploadPath; 
    uploadFile = MB+"/"+uploadFile;

    FTPSClient ftps = new FTPSClient();
    try {
        abcd = ash123(abcd).substring(0,15);
        ftps.connect(server, port);
        ftps.login(user, abcd);
        ftps.enterLocalPassiveMode();
        ftps.setFileType(FTP.BINARY_FILE_TYPE);
        File LocalFile = new File(localFile);
        String RemoteFile = uploadFile;
        InputStream inputStream = new FileInputStream(LocalFile);
        boolean done = ftps.storeFile(RemoteFile, inputStream);
        if (done) {
          if (LocalFile.exists()) {
            LocalFile.delete();
          }
        } else {
          // do nothing
        }
        inputStream.close();
    } catch (IOException ex) {
        System.out.println("Error: " + ex.getMessage());
        ex.printStackTrace();
    } finally {
      try {
        if (ftps.isConnected()) {
          ftps.logout();
          ftps.disconnect();
        }
      } catch (IOException ex) {
            ex.printStackTrace();
      }
    }
  }
} // class
