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

import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;

class getAbout implements ActionListener {

  JFrame  appFrame;
  JLabel  jlApplication;
  JLabel  jlVerText;
  JLabel  jlMailBox;
  JButton jbnExit;

  Properties  prop    = new Properties();
  InputStream input   = null;
  String      version = null;
  String      mailBox = null;

  Container cPane;

  public getAbout() { }

  public void getAbout(String propFileName){

    try {
      input = new FileInputStream(propFileName);
      prop.load(input);
      version = prop.getProperty("version");
      mailBox = prop.getProperty("MailBox");
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
       
    appFrame.setSize(400,200);
    appFrame.setResizable(false);
    appFrame.setLocationRelativeTo(null);
    appFrame.setVisible(true);
    appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

  }
     
  public void arrangeComponents(){
    jlApplication = new JLabel("VNQ: The light weight message utility.");
    jlVerText     = new JLabel("Version: "+version);
    jlMailBox     = new JLabel("Mailbox: "+mailBox);

    jbnExit  = new JButton("Exit");

    /*add all initialized components to the container*/

    GridBagConstraints gridBagConstraintsx01 = new GridBagConstraints();
    gridBagConstraintsx01.gridx = 0;
    gridBagConstraintsx01.gridy = 0;
    gridBagConstraintsx01.insets = new Insets(5,5,5,5); 
    cPane.add(jlApplication, gridBagConstraintsx01);
        
    GridBagConstraints gridBagConstraintsx02 = new GridBagConstraints();
    gridBagConstraintsx02.gridx = 0;
    gridBagConstraintsx02.gridy = 1;
    gridBagConstraintsx02.insets = new Insets(5,5,5,5); 
    cPane.add(jlVerText, gridBagConstraintsx02);

    GridBagConstraints gridBagConstraintsx03 = new GridBagConstraints();
    gridBagConstraintsx03.gridx = 0;
    gridBagConstraintsx03.gridy = 2;
    gridBagConstraintsx03.insets = new Insets(5,5,5,5); 
    cPane.add(jlMailBox, gridBagConstraintsx03);

    GridBagConstraints gridBagConstraintsx16 = new GridBagConstraints();
    gridBagConstraintsx16.gridx = 0;
    gridBagConstraintsx16.gridy = 5;
    gridBagConstraintsx16.insets = new Insets(5,5,5,5); 
    cPane.add(jbnExit, gridBagConstraintsx16);
       
    jbnExit.addActionListener(this);
  }

  public void actionPerformed (ActionEvent e){
    if (e.getSource() == jbnExit){
      appFrame.setVisible(false); //you can't see me!
      appFrame.dispose(); //Destroy the JFrame object
      return;
    }
  }
}
