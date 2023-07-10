import java.io.*;
import java.util.*;
import java.awt.Insets;
import java.awt.Container;
import java.awt.GridBagLayout;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.awt.event.ActionEvent;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import java.security.MessageDigest;

class getPw implements ActionListener {

  JFrame appFrame;

  JLabel jlbmyPassKey;
  JTextField jtfmyPassKey;
  JButton jbnSetPw, jbnClear, jbnExit;
         
  Properties prop    = new Properties();
  InputStream input  = null;
  String myPassKey   = null;
  String myPassKeyLen= null;
  String outPropFile = null;
  String XYZ         = null;
  String xyz         = null;
  int    SEED        = 0;
  Container cPane;

  public getPw() { }

  public void getPassKey(String propFileName, String defaultPW){

    outPropFile = propFileName;
    xyz = defaultPW;
    try {
      input = new FileInputStream(propFileName);
      prop.load(input);
      myPassKey    = prop.getProperty("otherPassKey");
      myPassKeyLen = prop.getProperty("otherPassKeyLen");
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


    /*Create a frame, get its contentpane and set layout*/
    appFrame = new JFrame("vnq");

    cPane = appFrame.getContentPane();
    cPane.setLayout(new GridBagLayout());
       
    //Arrange components on contentPane and set Action Listeners to each JButton
    arrangeComponents();
       
    appFrame.setSize(350,150);
    appFrame.setResizable(false);
    appFrame.setLocationRelativeTo(null);
    appFrame.setVisible(true);
    appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

  }
     
  public void arrangeComponents(){
    jlbmyPassKey = new JLabel("Pass Key");

    jtfmyPassKey = new JTextField(50);

    jbnSetPw  = new JButton("Set");
    jbnClear = new JButton("Clear");
    jbnExit  = new JButton("Exit");

    /*add all initialized components to the container*/

    GridBagConstraints gridBagConstraintsx05 = new GridBagConstraints();
    gridBagConstraintsx05.gridx = 0;
    gridBagConstraintsx05.gridy = 2;
    gridBagConstraintsx05.insets = new Insets(5,5,5,5); 
    cPane.add(jlbmyPassKey, gridBagConstraintsx05);
        
    GridBagConstraints gridBagConstraintsx06 = new GridBagConstraints();
    gridBagConstraintsx06.gridx = 1;
    gridBagConstraintsx06.gridy = 2;
    gridBagConstraintsx06.insets = new Insets(5,5,5,5); 
    gridBagConstraintsx06.gridwidth = 3;
    gridBagConstraintsx06.fill = GridBagConstraints.BOTH;
    cPane.add(jtfmyPassKey, gridBagConstraintsx06);
        
    GridBagConstraints gridBagConstraintsx10 = new GridBagConstraints();
    gridBagConstraintsx10.gridx = 1;
    gridBagConstraintsx10.gridy = 4;
    gridBagConstraintsx10.insets = new Insets(5,5,5,5); 
    cPane.add(jbnSetPw, gridBagConstraintsx10);
        
    GridBagConstraints gridBagConstraintsx15 = new GridBagConstraints();
    gridBagConstraintsx15.gridx = 2;
    gridBagConstraintsx15.gridy = 4;
    gridBagConstraintsx15.insets = new Insets(5,5,5,5); 
    cPane.add(jbnClear, gridBagConstraintsx15);
        
    GridBagConstraints gridBagConstraintsx16 = new GridBagConstraints();
    gridBagConstraintsx16.gridx = 3;
    gridBagConstraintsx16.gridy = 4;
    gridBagConstraintsx16.insets = new Insets(5,5,5,5); 
    cPane.add(jbnExit, gridBagConstraintsx16);
       
    jbnSetPw.addActionListener(this);
    jbnClear.addActionListener(this);
    jbnExit.addActionListener(this);
    appFrame.getRootPane().setDefaultButton(jbnSetPw);
  }

  public static String ash123(String base) {
      try{
          MessageDigest digest = MessageDigest.getInstance("SHA-256");
          byte[] hash = digest.digest(base.getBytes("UTF-8"));
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

  public void actionPerformed (ActionEvent e){
       
      rubixByteArray rBA = new rubixByteArray();
      Random rnd = new Random();
      SEED = rnd.nextInt();
      XYZ = ash123(String.valueOf(SEED));
      XYZ = XYZ.substring(0,50).trim();
      if (e.getSource() == jbnSetPw){
         myPassKey = null;
         myPassKey = jtfmyPassKey.getText();
         if (myPassKey == null) {
           myPassKey = "DEFAULT";
         }
         if (myPassKey.length() < 1) {
           myPassKey = "DEFAULT";
         }
         int size = myPassKey.length();
         char [] xy = new char[xyz.length()];
         char [] XY = new char[XYZ.length()];
         char [] PK = new char[myPassKey.length()];
         xy = xyz.toCharArray();
         XY = XYZ.toCharArray();
         PK = myPassKey.toCharArray();

         // interlace XY and xy
         char[] XX = new char[XY.length + xy.length];
         int J = 0;
         int K = 0;
         for (int i = 0; i < XX.length; i++) {
           if (i % 2 == 0) {
             XX[i]   = xy[J++];
           } else {
             XX[i]   = XY[K++];
           }
         }
         // interlace passkey
         char[] BF = new char[PK.length + XY.length + xy.length];
         J = 0;
         K = 0;
         for (int i = 0; i < (PK.length*2); i++) {
           if (i % 2 == 0) {
             BF[i]   = PK[J++];
           } else {
             BF[i]   = XX[K++];
           }
         }
         J = J+K; 
         for (int i = J; i < XX.length; i++) {
           BF[i] = XX[i];
         }
         size = BF.length;
         char [] BI = new char[size+1];
         char [] BO = new char[size+1];

         for (int j = 1; j < size; j++) {
            BI[j] = BF[j-1];
         }
         // Scramble the cipherText once more.
         if (myPassKey.compareTo("DEFAULT")==0) {
           // do nothing
         } else {
           rBA.doENC(xyz,size,BI,BO);
         }
         for (int j = 1; j < size; j++) {
            BF[j-1] = BO[j];
         }
         prop.setProperty("otherPassKey",String.valueOf(BF).trim());
         prop.setProperty("otherPassKeyLen",String.valueOf(PK.length));
         try {
            prop.store(new FileOutputStream(outPropFile), null);
         } catch (IOException ex) {
              ex.printStackTrace();
         }
         appFrame.setVisible(false); //you can't see me!
         appFrame.dispose(); //Destroy the JFrame object
         
      }

      if (e.getSource() == jbnClear){
         clear();
      }

      if (e.getSource() == jbnExit){      
        appFrame.setVisible(false); //you can't see me!
        appFrame.dispose(); //Destroy the JFrame object
        return;
      }

  }

  public void clear(){
    jtfmyPassKey.setText("");
  }
}
