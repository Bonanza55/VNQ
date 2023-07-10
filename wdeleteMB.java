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

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;

class wdeleteMB implements ActionListener {

  JFrame appFrame;

  JLabel jlbmyMailBox,jlbMsg;
  JTextField jtfmyMailBox;
  JButton jbnDelete, jbnClear, jbnExit;
         
  Container cPane;
  static String MB = null;
  static String C = null;
  static String D = null;

  public wdeleteMB() { }

  public void deleteMB(String A, String B){

   C = A;
   D = B;

   InputStream input = null;

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

    String mailB  = MB.toLowerCase();

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
    jlbMsg       = new JLabel("Enter Mail Box Name.");
    jtfmyMailBox = new JTextField(60);
    jtfmyMailBox.setText(MB);

    jbnDelete  = new JButton("Delete");
    jbnClear = new JButton("Clear");
    jbnExit  = new JButton("Exit");

    /*add all initialized components to the container*/

    GridBagConstraints gridBagConstraintsx06 = new GridBagConstraints();
    gridBagConstraintsx06.gridx = 1;
    gridBagConstraintsx06.gridy = 2;
    gridBagConstraintsx06.insets = new Insets(5,5,5,5); 
    gridBagConstraintsx06.gridwidth = 3;
    gridBagConstraintsx06.fill = GridBagConstraints.BOTH;
    cPane.add(jtfmyMailBox, gridBagConstraintsx06);
        
    GridBagConstraints gridBagConstraintsx10 = new GridBagConstraints();
    gridBagConstraintsx10.gridx = 1;
    gridBagConstraintsx10.gridy = 4;
    gridBagConstraintsx10.insets = new Insets(5,5,5,5); 
    cPane.add(jbnDelete, gridBagConstraintsx10);
        
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
       
    GridBagConstraints gridBagConstraintsx07 = new GridBagConstraints();
    gridBagConstraintsx07.gridx = 2;
    gridBagConstraintsx07.gridy = 5;
    gridBagConstraintsx07.insets = new Insets(5,5,5,5); 
    cPane.add(jlbMsg, gridBagConstraintsx07);
        
    jbnDelete.addActionListener(this);
    jbnClear.addActionListener(this);
    jbnExit.addActionListener(this);
    appFrame.getRootPane().setDefaultButton(jbnDelete);
  }

  public void actionPerformed (ActionEvent e){
       
      if (e.getSource() == jbnDelete){
         String mailBox = null;
         mailBox = jtfmyMailBox.getText();
         removeMB rMB = new removeMB();
         if (mailBox.compareTo(MB)==0) { 
           rMB.RemoveMB(C,D);
           appFrame.setVisible(false); //you can't see me!
           appFrame.dispose(); //Destroy the JFrame object
         }
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
    jtfmyMailBox.setText("");
  }
}
