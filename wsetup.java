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

class wsetup implements ActionListener {

  JFrame appFrame;

  JLabel jlbmyMailBox,jlbMsg;
  JTextField jtfmyMailBox;
  JButton jbnSend, jbnClear, jbnExit;
         
  InputStream input = null;
  String version    = null;
  String myPassKey  = null;
  String mailBox    = null;
  String C          = null;
  String D          = null;

  Container cPane;

  public wsetup() { }

  public void wsetupMB(String A, String B, String V){

    C = A;
    D = B;
    version = V;

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
    jlbmyMailBox = new JLabel("Mail Box");
    jlbMsg       = new JLabel("Enter New Mail Box Name.");

    jtfmyMailBox = new JTextField(60);

    jbnSend  = new JButton("Make");
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
    cPane.add(jbnSend, gridBagConstraintsx10);
        
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
        
    jbnSend.addActionListener(this);
    jbnClear.addActionListener(this);
    jbnExit.addActionListener(this);
    appFrame.getRootPane().setDefaultButton(jbnSend);
  }

  public void actionPerformed (ActionEvent e){
       
      if (e.getSource() == jbnSend){
         mailBox = null;
         mailBox = jtfmyMailBox.getText();
         setUp su = new setUp();
         su.SetUpAcct(mailBox.toLowerCase(),C,D,version);
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
    jtfmyMailBox.setText("");
  }
}
