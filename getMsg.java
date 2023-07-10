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

class getMsg {
  public getMsg() { }
  
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

  public void GetMsg(String A, String B) {

   C = A;
   D = B;

   InputStream input = null;
   String MB   = null;

   Properties prop = new Properties();
   try {
     input = new FileInputStream("vnq.properties");
     prop.load(input);
     MB = prop.getProperty("MailBox");
     } catch (IOException ex) {
       ex.printStackTrace();
     } finally {
     if (input != null) {
       try {
         input.close();
       } catch (IOException e) {
          e.printStackTrace();
       }
      } // if
    } // finally

    String server = "host4.bakop.com";
    String user   = "bonanza50";
    String folder = "inbox/";
    int    port   = 21;
    String mailB  = MB.toLowerCase();
    ABCD fP       = new ABCD();
    String abcd   = fP.getABCD(C,D);

    FTPSClient ftps = new FTPSClient();
    try {
        abcd = ash123(abcd).substring(0,15);
        ftps.connect(server,port);
        ftps.login(user,abcd);
        ftps.enterLocalPassiveMode();
        ftps.setFileType(FTP.BINARY_FILE_TYPE);
        ftps.changeWorkingDirectory(mailB);
        FTPFile[] ftpFiles = ftps.listFiles();
        if (ftpFiles != null && ftpFiles.length > 0) {
          for (FTPFile file : ftpFiles) {
             if (!file.isFile()) {
                continue;
             }
             OutputStream output;
             output = new FileOutputStream(folder+file.getName());
             ftps.retrieveFile(file.getName(),output);
             output.close();
             ftps.deleteFile(file.getName());
          }
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
