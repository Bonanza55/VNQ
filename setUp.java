import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;
 
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPSClient;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTPConnectionClosedException;

class setUp {
  public setUp() { }

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

  public void SetUpAcct(String mailBox, String A, String B, String version) {

    String C = A;
    String D = B;

    String dirName  = mailBox.toLowerCase();;
    String PROPFILE = "vnq.properties";
    String server   = "host4.bakop.com";
    String user     = "bonanza50";
    int    port     = 21;

    File ib = new File("inbox");
    File ob = new File("outbox");
    ib.mkdir();
    ob.mkdir();

    ABCD fP     = new ABCD();
    String abcd = fP.getABCD(C,D);

    FTPSClient ftps = new FTPSClient();
    try {
        abcd = ash123(abcd).substring(0,15);
        ftps.connect(server, port);
        ftps.login(user, abcd);
        ftps.enterLocalPassiveMode();
        ftps.setFileType(FTP.BINARY_FILE_TYPE);
        boolean done = ftps.makeDirectory(dirName);
        if (done) {
          done = ftps.makeDirectory(dirName+"/inbox");
          done = ftps.makeDirectory(dirName+"/outbox");
        } else {
          // do nothing.
        }
        ftps.changeWorkingDirectory("Dist");
        FTPFile[] ftpFiles = ftps.listFiles();
        if (ftpFiles != null && ftpFiles.length > 0) {
          for (FTPFile file : ftpFiles) {
             if (!file.isFile()) {
                continue;
             }
             OutputStream output;
             output = new FileOutputStream(file.getName());
             ftps.retrieveFile(file.getName(),output);
             output.close();
          }
        }
        Properties prop = new Properties();
        prop.put("version",version);
        prop.setProperty("otherPassKey","DEFAULT");
        prop.setProperty("otherPassKeyLen","0");
        prop.setProperty("MailBox",dirName);
        try {
           prop.store(new FileOutputStream(PROPFILE), null);
        } catch (IOException exp) {
           exp.printStackTrace();
        }
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
