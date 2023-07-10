// VNQ  - Lightweight secure message app
//
// To be use with java 11
//
//jar -cvfm vnq.jar manifest.txt Filename.class vnq$1.class vnq$vnqPrint.class rubixByteArray.class encByteArray.class other.class getMsg.class cleanInbox.class getPw.class wsetup.class wdeleteMB.class wpostMsg.class wemail.class ABCD.class setUp.class removeMB.class postMsg.class SendBase64Email\$1.class SendBase64Email.class getAbout.class vnq.class
//
import java.io.*;
import java.awt.*;
import java.lang.*;
import java.util.*;
import javax.swing.*;
import java.util.Date;
import java.awt.event.*;
import java.awt.print.*;
import java.awt.Toolkit;
import java.util.Random;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.awt.datatransfer.*;
import javax.swing.JFileChooser;
import java.text.SimpleDateFormat;
import java.security.MessageDigest;

import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Base64.Decoder;
import java.nio.charset.StandardCharsets;


class Filename {
  private String fullPath;
  private char pathSeparator, extensionSeparator;

  public Filename(String str, char sep, char ext) {
    fullPath = str;
    pathSeparator = sep;
    extensionSeparator = ext;
  }

  public String extension() {
    int dot = fullPath.lastIndexOf(extensionSeparator);
    return fullPath.substring(dot + 1);
  }

  public String filename() { // gets filename without extension
    int dot = fullPath.lastIndexOf(extensionSeparator);
    int sep = fullPath.lastIndexOf(pathSeparator);
    return fullPath.substring(sep + 1, dot);
  }

  public String path() {
    int sep = fullPath.lastIndexOf(pathSeparator);
    return fullPath.substring(0, sep);
  }
}

public class vnq extends JFrame implements ActionListener
{
private static InputStream in     = null; // input file
private static OutputStream out   = null; // output file
private static final int EOF      = -1;   // end of file
private static String aAa         = "5p4VP257CUJbQX3f77mXM8kdb857rKr2SFd64f87S37hV0u7FX9NzvddBR705335Q9fFfaX7zn9bEk31yU3d5619zjaU0aq2zcSc";
private static String bBb         = "dMQmXJ7VccbRyXJKhFSrSrbuN9XvFRBdQzFXbjnkUUy3zEzqzS";
private static String PKY         = null;
private static String outBox      = "outBox/";
private static String inBox       = "inBox/";
private static String inString    = null; // in string buffer
private static int buffer         = 0;    // one character buffer
private static int N              = 0;    // number of input bits left in buffer
private static int M              = 0;    // number of output bits left in buffer
private static int i,j,k          = 0;
private static int KEY_DIGEST     = 32;
private static int encTF          = 0;
private static int EncOnly        = 0;
private static int loadFile       = 0;
private static int gpwRC          = 0;
private static int noFileSelected = 0;
private static String CWD         = null;
private static String mailBox     = null;
private static String version     = null;
private static String myPassKey   = "DEFAULT";
private static String myPassKeyLen= "0";
private static String EmailMsg    = null; // String to email;
private static String EmailSub    = null; // Default subject;
private static String PROPFILE    = "vnq.properties";
private static String VERSION     = "11";
private JTextArea ta;
private int count;
private JMenuBar menuBar;
private JMenu fileM,editM,toolsM,sendM,getM,inboxM,helpM;
private JScrollPane scpane;
private JMenuItem exitI,printI,clearI,cutI,copyI,pasteI,selectI,decI,encI,aboutI,loadI,emailI,setUpI,deleteMBI,PKYI,getI,postI,loadIBI,cleanIBI,helpSendI,helpGetI,helpEncDecI;
private String pad;
private JToolBar toolBar;

private static final SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd-HHmm");

private static String myOutFile  = null;  // input file name
private static String myInFile   = null;  // output file name

public vnq() {

    super("vnq");
    setSize(1000, 600);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Container pane = getContentPane();
    pane.setLayout(new BorderLayout());

    count = 0;
    pad = " ";
    ta = new JTextArea(); //textarea
    menuBar  = new JMenuBar();       //menubar
    fileM    = new JMenu("File");    
    editM    = new JMenu("Edit");    
    inboxM   = new JMenu("Inbox");   
    toolsM   = new JMenu("Tools");   
    helpM    = new JMenu("Help");    
    sendM    = new JMenu("Send");    
    getM     = new JMenu("Get");     
    scpane   = new JScrollPane(ta);  
    exitI    = new JMenuItem("Exit");
    printI   = new JMenuItem("Print");
    PKYI     = new JMenuItem("Change Passkey");
    setUpI   = new JMenuItem("Setup Mailbox");
    deleteMBI= new JMenuItem("Delete Mailbox");
    cutI     = new JMenuItem("Cut");
    clearI   = new JMenuItem("Clear");
    copyI    = new JMenuItem("Copy");
    pasteI   = new JMenuItem("Paste");
    selectI  = new JMenuItem("Select All"); 
    aboutI   = new JMenuItem("About vnq");      
    loadI    = new JMenuItem("Open");      
    loadIBI  = new JMenuItem("Open");      
    cleanIBI = new JMenuItem("Clean Up");      
    encI     = new JMenuItem("Encrypt");  
    decI     = new JMenuItem("Decrypt"); 
    emailI   = new JMenuItem("Email msg");   
    getI     = new JMenuItem("Get Msg");   
    postI    = new JMenuItem("Post Msg");   
    helpSendI= new JMenuItem("Send A Message");   
    helpGetI= new JMenuItem("Get A Message");   
    helpEncDecI= new JMenuItem("Encrypt A Message");   
    toolBar  = new JToolBar();

    ta.setLineWrap(true);
    ta.setWrapStyleWord(true);
    ta.setFont(new Font("monospaced",Font.PLAIN,18));

    setJMenuBar(menuBar);
    menuBar.add(fileM);
    menuBar.add(editM);
    menuBar.add(sendM);
    menuBar.add(inboxM);
    menuBar.add(toolsM);
    menuBar.add(helpM);

    fileM.add(loadI);
    fileM.add(encI);
    fileM.add(decI);
    fileM.add(printI);
    fileM.add(setUpI);
    fileM.add(deleteMBI);
    fileM.add(exitI);

    editM.add(selectI);
    editM.add(cutI);
    editM.add(copyI);
    editM.add(pasteI);
    editM.add(clearI);

    toolsM.add(PKYI);

    inboxM.add(loadIBI);
    inboxM.add(cleanIBI);

    sendM.add(postI);
    sendM.add(emailI);

    getM.add(getI);

    helpM.add(helpSendI);
    helpM.add(helpEncDecI);
    helpM.add(aboutI);

    encI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
    decI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK));
    loadI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
    emailI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.CTRL_MASK));
    printI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
    cutI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
    clearI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
    copyI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
    pasteI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
    selectI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
    PKYI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K, ActionEvent.CTRL_MASK));
    setUpI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
    deleteMBI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
    postI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
    getI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK));
    loadIBI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));
    cleanIBI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, ActionEvent.CTRL_MASK));
    helpSendI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
    helpGetI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
    helpEncDecI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));

    pane.add(scpane,BorderLayout.CENTER);
    pane.add(toolBar,BorderLayout.SOUTH);

    encI.addActionListener(this);
    decI.addActionListener(this);
    loadI.addActionListener(this);
    aboutI.addActionListener(this);
    emailI.addActionListener(this);
    printI.addActionListener(this);
    exitI.addActionListener(this);
    cutI.addActionListener(this);
    clearI.addActionListener(this);
    copyI.addActionListener(this);
    pasteI.addActionListener(this);
    selectI.addActionListener(this);
    PKYI.addActionListener(this);
    setUpI.addActionListener(this);
    deleteMBI.addActionListener(this);
    postI.addActionListener(this);
    getI.addActionListener(this);
    loadIBI.addActionListener(this);
    cleanIBI.addActionListener(this);
    helpSendI.addActionListener(this);
    helpGetI.addActionListener(this);
    helpEncDecI.addActionListener(this);

    setVisible(true);
    File ff = new File(PROPFILE);
    if(!ff.exists() && !ff.isDirectory()) { 
      ta.setText("It looks like you have not setup your mailbox?\n\nSelect <File> <Setup Mailbox>, enter a valid email address, or firstname.lastname if you dont have email, then press Make.\n\nThis will take about 10 seconds then you can start sending messages.");
    }
} 

  // Choose an input file.
  public void getInputFile(String home){

    String HOME = null;
    if (home.compareTo("DEFAULT")==0) {
      HOME = System.getenv("HOME"); // get the home directory
      if (HOME == null) {
        HOME = "/";
      }
      } else {
        HOME = home;
    }
    JFileChooser jFileChooser = new JFileChooser();
    jFileChooser.setCurrentDirectory(new File(HOME));
    String FPATH = "";
    CWD = "";
    myInFile  = null;
    myOutFile = null;
    noFileSelected = 0;
  
    int result = jFileChooser.showOpenDialog(new JFrame());
    if (result == JFileChooser.APPROVE_OPTION) {
      File selectedFile = jFileChooser.getSelectedFile();
      myInFile = selectedFile.getAbsolutePath();
      FPATH = myInFile;

      // WINDOWS
      if (selectedFile.separatorChar == '\\') {
        Filename myHomePage = new Filename(FPATH, '\\', '.');
        CWD = myHomePage.path();
      }

      // UNIX
      if (selectedFile.separatorChar == '/') {
        Filename myHomePage = new Filename(FPATH, '/', '.');
        CWD = myHomePage.path();
      }
      try {
          File inFile  = new File(myInFile); 
          in  = new BufferedInputStream(new FileInputStream(inFile));
      }   
        catch (FileNotFoundException ex) {
        System.err.println("File not found.");
      }
      loadFile = 1;
    }
 }

// File handling methods.
  private static void clearBuffer() {
      if (M == 0) return;
      if (M > 0) buffer <<= (8 - N);
      try { out.write(buffer); }
      catch (IOException e) { e.printStackTrace(); }
      M = 0;
      buffer = 0;
  }

  private static void fillBuffer() {
  try {
      buffer = in.read();
      N = 8;
  }
    catch (IOException e) { System.out.println("EOF"); buffer = EOF; N = -1; }
  }

  public void close() {
  try {
    in.close();
  }
  catch (IOException e) {
    e.printStackTrace();
    throw new RuntimeException("Could not close BinaryStdIn");
  }
  }

  public static boolean isEmpty() {
    return buffer == EOF;
  }

  public static char readchar() {
  if (isEmpty()) throw new RuntimeException("Reading from empty input stream");

    // special case when aligned byte
    if (N == 8) {
      int x = buffer;
      fillBuffer();
      return (char) (x & 0xff);
    }
    // combine last N bits of current buffer with first 8-N bits of new buffer
    int x = buffer;
    x <<= (8-N);
    int oldN = N;
    fillBuffer();
    if (isEmpty()) throw new RuntimeException("Reading from empty input stream");
    N = oldN;
    x |= (buffer >>> N);
    return (char) (x & 0xff);
    // the above code doesn't quite work for the last character if N = 8
    // because buffer will be -1
    }

  public static byte readByte() {
    char c = readchar();
    byte x = (byte) (c & 0xff);
    return x;
  }



public void ash123(byte[] myKey,String base) {
  try{
    MessageDigest digest = MessageDigest.getInstance("SHA-256");
    byte[] hash = digest.digest(base.getBytes("UTF8"));

    for (int i = 0; i < hash.length; i++) {
      myKey[i]   = hash[i];
    }
  } catch(Exception ex){
    throw new RuntimeException(ex);
  }
}

public void actionPerformed(ActionEvent e) {
   JMenuItem   choice = (JMenuItem) e.getSource();
   rubixByteArray rBA = new rubixByteArray();
   encByteArray   eBA = new encByteArray();
   Properties    prop = new Properties();
   Random         rnd = new Random();
   String   tmpString = null;
   if ((choice == encI) && (encTF == 0)) { // ENCRYPT
     InputStream input = null;
     try {
       input = new FileInputStream(PROPFILE);
       prop.load(input);
       version      = prop.getProperty("version");
       myPassKey    = prop.getProperty("otherPassKey");
       myPassKeyLen = prop.getProperty("otherPassKeyLen");
       mailBox   = prop.getProperty("MailBox").toLowerCase();
     } catch (IOException ex) {
         ex.printStackTrace();
     } finally {
       if (input != null) {
         try {
           input.close();
         } catch (IOException e3) {
            e3.printStackTrace();
        }
       }
     }
   try {
       other o = new other();
       if (myPassKey.compareTo("DEFAULT")==0) {
         // do nothing
       } else {
         myPassKey = o.Other(myPassKey,Integer.parseInt(myPassKeyLen),bBb).trim();
       }
       Date date = new Date();
       int SEED = rnd.nextInt();
       Timestamp timeStamp = new Timestamp(date.getTime());
       String tempTxt = ta.getText();
       if (tempTxt.length() > 0) {
         tempTxt = "From: "+mailBox+"\n"+"Time: "+timeStamp+"\n\n"+tempTxt;
         ta.setText(tempTxt);
         byte[] plainText = ta.getText().getBytes("UTF8");
         int size = plainText.length;
         if (size > 0) {
            byte[] myKey        = new byte[100];
            byte[] myKeyE       = new byte[KEY_DIGEST+1];
            byte[] HeadBytesIn  = new byte[4];
            byte[] HeadBytesOut = new byte[8];
            for(i = 0; i <= 3; i++) {
              HeadBytesIn[i] = plainText[i];
            }
            if (myPassKey.compareTo("DEFAULT")==0) {
              ash123(myKey,bBb+Integer.toString(SEED)+bBb);
            } else {
              ash123(myKey,myPassKey+Integer.toString(SEED)+myPassKey);
            }
            for(i = 0; i <= 7; i++) {
              HeadBytesOut[i] = myKey[i];
            }
            // JAVA8 
            String encodedH = new String(Base64.getEncoder().encodeToString(HeadBytesOut));
            if (myPassKey.compareTo("DEFAULT")==0) {
              ash123(myKey,bBb+encodedH);
            } else {
              ash123(myKey,myPassKey+encodedH);
            }
            for(i = 0; i <= KEY_DIGEST; i++) {
              myKeyE[i] = myKey[i];
            }
            // JAVA8
            PKY = new String(Base64.getEncoder().encodeToString(myKeyE));
            byte[] dataBytesIn  = new byte[size+2];
            byte[] dataBytesOut = new byte[size]; // Data out byte array
            for (i = 1; i <= size; i++) {
               dataBytesIn[i] = plainText[i-1];
            }
            eBA.doENC(PKY,size,dataBytesIn,dataBytesOut);
            size = dataBytesOut.length;
            // JAVA8
            String encoded = new String(Base64.getEncoder().encodeToString(dataBytesOut));
            ta.setText(null);
            String cipherText = encodedH+encoded;
            size = cipherText.length();
            char [] BF = new char[size+1];
            char [] BI = new char[size+1];
            char [] BO = new char[size+1];
            BF = cipherText.toCharArray();
            for (int j = 1; j < size; j++) {
               BI[j] = BF[j-1];
            }
            // Scramble the cipherText once more. 
            if (myPassKey.compareTo("DEFAULT")==0) {
              rBA.doENC(bBb,size,BI,BO);
            } else {
              rBA.doENC(myPassKey,size,BI,BO);
            }
            for (int j = 1; j < size; j++) {
               BF[j-1] = BO[j];
            }

            tmpString = String.valueOf(BF);
            if (tmpString.endsWith("=")) {
              EmailMsg = tmpString.substring(0, tmpString.length() - 2); 
            } else { 
              if (tmpString.endsWith("==")) {
                EmailMsg = tmpString.substring(0, tmpString.length() - 3); 
              } else {
                EmailMsg = String.valueOf(BF);
              }
            }

            EmailSub = "vnq."+encodedH.substring(0,10)+".txt";
            ta.setText(EmailMsg);
            PKY      = null;
            plainText    = null;
            cipherText   = null;
            myKey        = null;
            myKeyE       = null;
            plainText    = null;
            dataBytesIn  = null;
            dataBytesOut = null;
            HeadBytesIn  = null;
            HeadBytesOut = null;
            encoded      = null;
            encTF = 1;
            System.gc();
          }
        }
      } catch(Exception ex){
          throw new RuntimeException(ex);
      }
    } // if

    if (choice == decI) { // DECRYPT
     InputStream input = null;
     try {
       input = new FileInputStream(PROPFILE);
       prop.load(input);
       myPassKey    = prop.getProperty("otherPassKey");
       myPassKeyLen = prop.getProperty("otherPassKeyLen");
     } catch (IOException ex) {
         ex.printStackTrace();
     } finally {
       if (input != null) {
         try {
           input.close();
         } catch (IOException e3) {
            e3.printStackTrace();
        }
       }
     }
     try {
       encTF = 0;
       other o = new other();
       String str = new String();
       str = ta.getText();
       int size = str.length();
       char[] BF2 = new char[size+1]; // Data in char array
       char[] BI2 = new char[size+1]; // Data in char array
       char[] BO2 = new char[size+1]; // Data out char array
       BF2 = str.toCharArray();
       for (int j = 1; j < size; j++) {
         BI2[j] = BF2[j-1];
       }
       // Scramble the cipherText once more. 
       if (myPassKey.compareTo("DEFAULT")==0) {
         rBA.doDEC(bBb,size,BI2,BO2);
       } else {
         myPassKey = o.Other(myPassKey,Integer.parseInt(myPassKeyLen),bBb);
         rBA.doDEC(myPassKey.trim(),size,BI2,BO2);
       }
       for (int j = 1; j < size; j++) {
         BF2[j-1] = BO2[j];
       }
       String cipherTextStr = String.valueOf(BF2);
       byte[] cipherText = cipherTextStr.getBytes("UTF-8");
       size = cipherText.length;
       if (size > 0) {
          byte[] myKey        = new byte[100];
          byte[] myKeyD       = new byte[KEY_DIGEST+1];
          byte[] HeadBytesIn  = new byte[12];
          byte[] HeadBytesOut = new byte[4];

          for(i = 0; i <= 11; i++) {
            HeadBytesIn[i] = cipherText[i];
          }
          String encodedH = new String(HeadBytesIn);
          if (myPassKey.compareTo("DEFAULT")==0) {
            ash123(myKey,bBb+encodedH);
          } else {
            ash123(myKey,myPassKey+encodedH);
          }
          for(i = 0; i <= KEY_DIGEST; i++) {
            myKeyD[i] = myKey[i];
          }
          // JAVA8
          PKY = new String(Base64.getEncoder().encodeToString(myKeyD));
          byte[] dataBytesIn  = new byte[size+12];
          for(i = 0; i < size-12; i++) {
            dataBytesIn[i] = cipherText[i+12];
          }
          String encoded = new String(dataBytesIn).trim();
          String decoded = null;
          // JAVA8
          Base64.Decoder decoder = Base64.getDecoder();
          byte[] dataBytesIn2    = decoder.decode(encoded);
          size = dataBytesIn2.length;
          byte[] dataBytesOut = new byte[size];
          eBA.doDEC(PKY,size,dataBytesIn2,dataBytesOut);
          size = dataBytesOut.length;
          byte[] displayOut = new byte[size]; 
          for (i = 0; i < size-1; i++) {
             displayOut[i] = dataBytesOut[i+1];
          }
          decoded = new String(displayOut).trim();
          ta.setText(null);
          ta.setText(decoded);
          PKY          = null;
          cipherText   = null;
          encoded      = null;
          decoded      = null;
          myKey        = null;
          myKeyD       = null;
          cipherText   = null;
          dataBytesIn  = null;
          dataBytesIn2 = null;
          dataBytesOut = null;
          HeadBytesIn  = null;
          HeadBytesOut = null;
          System.gc();
     }
     } catch(Exception ex){
        throw new RuntimeException(ex);
     }
    } // if

    if (choice == exitI) {
       File pf = new File(PROPFILE);
       if(pf.exists() && !pf.isDirectory()) { 
         InputStream input = null;
         try {
           input = new FileInputStream(PROPFILE);
           prop.load(input);
           mailBox = prop.getProperty("MailBox").toLowerCase();
         } catch (IOException ex) {
             ex.printStackTrace();
         } finally {
         if (input != null) {
            try {
              input.close();
             } catch (IOException e1) {
                e1.printStackTrace();
             }
         } // if
         } // finally
         prop.setProperty("otherPassKey","DEFAULT");
         prop.setProperty("MailBox",mailBox);
         try {
            prop.store(new FileOutputStream(PROPFILE), null);
         } catch (IOException ex) {
            ex.printStackTrace();
         }
         encTF = 0;
       }
       System.exit(0);
    }

    if (choice == loadI) {
       encTF = 0;
       loadFile = 0;
       getInputFile("DEFAULT");
       if (loadFile == 1) {
          ta.setText(null);
          fillBuffer();
          ArrayList<Byte> bIN = new ArrayList<Byte>();
          while (!vnq.isEmpty()) {
             bIN.add(vnq.readByte());
          }
          int size = bIN.size();
          byte[] fileBytesIn  = new byte[size];
          for(i = 0; i < size; i++) {
             fileBytesIn[i] = bIN.get(i).byteValue();
          }
          inString = new String(fileBytesIn);
          ta.setText(null);
          ta.setText(inString);
          EmailMsg = inString;
          fileBytesIn = null;
          bIN         = null;
          System.gc();
       }
       loadFile = 0;
    }

    if (choice == clearI) {
      encTF = 0;
      ta.setText(null);
    }

    if (choice == cutI) {
      encTF = 0;
      String myString = ta.getSelectedText();
      StringSelection stringSelection = new StringSelection(myString);
      Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
      clpbrd.setContents(stringSelection, null);
      ta.setText(null);
    }

    if (choice == copyI) {
      String myString = ta.getSelectedText();
      StringSelection stringSelection = new StringSelection(myString);
      Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
      clpbrd.setContents(stringSelection, null);
    }

    if (choice == pasteI) {
      String myString = new String();
      try {
         Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
         Transferable t = clpbrd.getContents(null);
         if (t.isDataFlavorSupported(DataFlavor.stringFlavor))
           myString = (String)t.getTransferData(DataFlavor.stringFlavor);
           ta.setText(null);
           ta.setText(myString);
      } catch (UnsupportedFlavorException | IOException ex) {
           ta.setText(null);
      }
    }

    if (choice == selectI) {
        ta.selectAll();
    }

    if (choice == loadIBI) {
       encTF = 0;
       loadFile = 0;
       getMsg gmsg = new getMsg();
       gmsg.GetMsg(aAa,bBb);
       getInputFile(inBox);
       loadMyFile();
    }

    if (choice == helpEncDecI) {
       encTF = 0;
       loadFile = 0;
       try {
          File inFile  = new File("helpEncDec.txt"); 
          in  = new BufferedInputStream(new FileInputStream(inFile));
       }   
        catch (FileNotFoundException ex) {
        System.err.println("File not found.");
       }
       loadFile = 1;
       loadMyFile();
    }

    if (choice == helpGetI) {
       encTF = 0;
       loadFile = 0;
       try {
          File inFile  = new File("helpGet.txt"); 
          in  = new BufferedInputStream(new FileInputStream(inFile));
       }   
        catch (FileNotFoundException ex) {
        System.err.println("File not found.");
       }
       loadFile = 1;
       loadMyFile();
    }

    if (choice == helpSendI) {
       encTF = 0;
       loadFile = 0;
       try {
          File inFile  = new File("helpSend.txt"); 
          in  = new BufferedInputStream(new FileInputStream(inFile));
       }   
        catch (FileNotFoundException ex) {
        System.err.println("File not found.");
       }
       loadFile = 1;
       loadMyFile();
    }

    if (choice == cleanIBI) {
        cleanInbox cib = new cleanInbox();
        cib.cleanInbox();
    }

    if (choice == aboutI) {
        getAbout gA = new getAbout();
        gA.getAbout(PROPFILE);
    }

    if (choice == PKYI) {
        getPw gpw = new getPw();
        gpw.getPassKey(PROPFILE,bBb);
    }

    if (choice == setUpI) {
        wsetup su = new wsetup();
        su.wsetupMB(aAa,bBb,VERSION);
        ta.setText(null);
    }

    if (choice == deleteMBI) {
        wdeleteMB dMB = new wdeleteMB();
        dMB.deleteMB(aAa,bBb);
        ta.setText(null);
    }

    if (choice == printI) {
       String myMsg = null;
       EmailMsg = ta.getText();
       Container cPane;
       JFrame appFrame = new JFrame("Print Message");
       cPane = appFrame.getContentPane();
       cPane.setLayout(new BorderLayout());
       appFrame.setSize(200,100);
       appFrame.setResizable(true);
       appFrame.setLocationRelativeTo(null);
       appFrame.setVisible(true);
       appFrame.addWindowListener(new WindowAdapter() {
          public void windowClosing(WindowEvent e) {
       }
       });
       JButton printButton = new JButton("Print");
       printButton.addActionListener(new vnqPrint());
       appFrame.add("Center", printButton);
       appFrame.setVisible(true);
    }

    if (choice == getI) {
       encTF = 0;
       loadFile = 0;
       getMsg gmsg = new getMsg();
       gmsg.GetMsg(aAa,bBb);
       getInputFile(inBox);
       loadMyFile();
    }

    if (choice == postI) {
      int postIt = 1;
      if (( encTF == 0 ) && (EncOnly == 1)) { 
         postIt = 0; 
      }
      if (loadFile == 0) {
        EmailMsg = null;
        EmailMsg = ta.getText();
        if (EmailMsg.length() < 1) {
          postIt = 0;;
        }
      }
      File pf = new File(PROPFILE);
      if(pf.exists() && !pf.isDirectory()) { 
        InputStream input = null;
        try {
          input = new FileInputStream(PROPFILE);
          prop.load(input);
          mailBox = prop.getProperty("MailBox").toLowerCase();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
        if (input != null) {
           try {
             input.close();
            } catch (IOException e1) {
               e1.printStackTrace();
            }
        } // if
        } // finally
      }
      if (postIt == 1) { 
        Date date = new Date();
        Timestamp timeStamp = new Timestamp(date.getTime());
        // JAVA 8
        String msgName = "vnq."+sdf.format(timeStamp)+".txt";
        String msgPath = outBox+msgName;
        try {
           PrintStream out = new PrintStream(new FileOutputStream(msgPath));
           System.setOut(out);
        } catch (FileNotFoundException ex) {
          return;
        }
        // Write the file to outbox, add who it's from and when.
        if ( encTF == 0 ) { 
          EmailMsg = "From: "+mailBox+"\n"+"Time: "+timeStamp+"\n\n"+EmailMsg;
        }
        System.out.println(EmailMsg);
        wpostMsg pmsg = new wpostMsg();
        pmsg.PostMsg(msgName,msgPath,aAa,bBb);
        EmailMsg = null;
      }
    }

    if (choice == emailI) {
      File pf = new File(PROPFILE);
      if(pf.exists() && !pf.isDirectory()) {
        InputStream input = null;
        try {
          input = new FileInputStream(PROPFILE);
          prop.load(input);
          mailBox = prop.getProperty("MailBox").toLowerCase();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
        if (input != null) {
           try {
             input.close();
            } catch (IOException e1) {
               e1.printStackTrace();
            }
        } // if
        } // finally
      }
      int mailIt = 1;
      if (( encTF == 0 ) && (EncOnly == 1)) {
         mailIt = 0;
      }
      if (loadFile == 0) {
        EmailMsg = null;
        EmailMsg = ta.getText();
        Date date = new Date();
        Timestamp timeStamp = new Timestamp(date.getTime());
        EmailMsg = "From: "+mailBox+"\n"+"Time: "+timeStamp+"\n\n"+EmailMsg;
        if (EmailMsg.length() < 1) {
          mailIt = 0;;
        }
      }
      if (mailIt == 1) {
        wemail wm = new wemail();
        wm.createGUI(EmailSub,EmailMsg);
        EmailMsg = null;
        EmailSub = null;
      }
    }
}

public void loadMyFile() {
  if (loadFile == 1) {
     ta.setText(null);
     fillBuffer();
     ArrayList<Byte> bIN = new ArrayList<Byte>();
     while (!vnq.isEmpty()) {
        bIN.add(vnq.readByte());
     }
     int size = bIN.size();
     byte[] fileBytesIn  = new byte[size];
     for(i = 0; i < size; i++) {
        fileBytesIn[i] = bIN.get(i).byteValue();
     }
     inString = new String(fileBytesIn);
     ta.setText(null);
     ta.setText(inString);
     EmailMsg = inString;
     fileBytesIn = null;
     bIN         = null;
     System.gc();
  }
  loadFile = 0;
}
class vnqPrint implements Printable, ActionListener {

    public int print(Graphics g, PageFormat pf, int page) throws PrinterException {
        if (page > 0) { /* We have only one page, and 'page' is zero-based */
            return NO_SUCH_PAGE;
        }
        Font font = new Font("Monospaced", Font.PLAIN, 10);
        g.setFont(font);
        Graphics2D g2d = (Graphics2D)g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());
        String line = null;
        int width_orig = 80;
        int width      = 80;
        int x = 0;
        int y = width;
        int X = 30;
        int Y = 50;
        double size  = 0;
        double frac  = 0;
        double size2 = 0;
        double delta = 0;

        String[] outputs = EmailMsg.split("\n");
        for(int i=0; i<outputs.length; i++) {
           size = outputs[i].length();
           if (size < width) {
             width = (int)size;
             y = width;
           }
           frac = size % width;
           size2 = (size - frac)/width;
           delta = size - (size-frac);
           for(int j=0; j < (int)size2; j++) {
             line = outputs[i].substring(x,y);
             g.drawString(line,X,Y);
             Y = Y + 15;
             x = x + width;
             y = y + width;
           }
           if (size2 > 1) {
             line = outputs[i].substring(x,(y-width)+(int)delta);
             g.drawString(line,X,Y);
           }
           x = 0;
           y = width_orig;
           width = y;
           Y = Y + 12;
        }
        return PAGE_EXISTS;
    }

    public void actionPerformed(ActionEvent e) {
      PrinterJob job = PrinterJob.getPrinterJob();
      job.setPrintable(this);
      boolean ok = job.printDialog();
      if (ok) {
        try {
          job.print();
        } catch (PrinterException ex) {
           /* The job did not successfully complete */
        }
      }
    }
}

public static void main(String[] args) {
  new vnq();
} // main
} // class
