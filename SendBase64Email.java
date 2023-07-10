import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import java.text.*;
import java.io.*;
import java.util.Scanner;
import java.util.Properties;

class SendBase64Email {
  public SendBase64Email() { }
  
  public int Send(String EmailAddr, String Subject, String msgBase64) {

     String subj       = Subject;
     String to         = EmailAddr;
     String msg        = msgBase64;
     String host       = null;
     String from       = null;
     String From       = null;
     String port       = null;
     String U          = null;
     String P          = null;
     String auth       = "no";
     InputStream input = null;

     /*
     EMAILauth=yes
     EMAILhost=smtp.comporium.net
     EMAILuser=wrx@comporium.net
     EMAILport=465
     EMAILpassword=p58rM73c6
     */

     Properties prop = new Properties();
     try {
       input = new FileInputStream("vnq.properties");
       prop.load(input);
       auth = "yes";
       host = "smtp.comporium.net";
       port = "465";
       U    = "wrx@comporium.net";
       P    = "p58rM73c6";
       From = prop.getProperty("MailBox");
       from = U;
       } catch (IOException ex) {
   	 ex.printStackTrace();
       } finally {
       if (input != null) {
         try {
   	   input.close();
         } catch (IOException e) {
    	    e.printStackTrace();
         }
        }
      }

      final String user     = U;
      final String password = P;

      Properties properties = System.getProperties();

      Session session = null;
      if (auth.compareTo("no")==0) { 
        session = Session.getDefaultInstance(properties);
      }

      if (auth.compareTo("yes")==0) { 
        Properties props = new Properties();
        props.put("mail.smtp.host",host);
        props.put("mail.smtp.socketFactory.port",port);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port",port); 
        session = Session.getDefaultInstance(props,
        new javax.mail.Authenticator() {
           protected PasswordAuthentication getPasswordAuthentication() {
         return new PasswordAuthentication(user,password);
            }
        });
      }

      try{
         MimeMessage message = new MimeMessage(session);
         message.setFrom(new InternetAddress("noreply@vnq.com"));
         message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
         message.setSubject(subj);
         BodyPart messageBodyPart = new MimeBodyPart();
         messageBodyPart.setText(msg);
         Multipart multipart = new MimeMultipart();
         multipart.addBodyPart(messageBodyPart);
         messageBodyPart = new MimeBodyPart();
         message.setContent(multipart );
         Transport.send(message);
         return 0;
      } catch (MessagingException mex) {
         mex.printStackTrace();
         return 1;
      }
  } // Send
  } // Class  
