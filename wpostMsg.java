import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

class wpostMsg implements ActionListener {

  JFrame appFrame;

  JLabel jlbEmailAddr;
  JTextField jtfEmailAddr;
  JButton jbnSend, jbnClear, jbnExit;
         
  String EmailAddr, outFileName, outFilePath;
  Container cPane;
  String C = null;
  String D = null;

  public wpostMsg() {   
    EmailAddr  = null ;
  }

  public void PostMsg(String AoutfileName,String AoutfilePath,String A, String B){

    C = A;
    D = B;

    outFileName = AoutfileName;
    outFilePath = AoutfilePath;
    appFrame = new JFrame("Post Message");
    cPane = appFrame.getContentPane();
    cPane.setLayout(new GridBagLayout());
    arrangeComponents();
    appFrame.setSize(400,200);
    appFrame.setResizable(false);
    appFrame.setLocationRelativeTo(null);
    appFrame.setVisible(true);
    appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

  }
     
  public void arrangeComponents(){
    jlbEmailAddr = new JLabel("To Mailbox");
    jtfEmailAddr = new JTextField(50);
    jbnSend  = new JButton("Post");
    jbnClear = new JButton("Clear");
    jbnExit  = new JButton("Exit");

    GridBagConstraints gridBagConstraintsx05 = new GridBagConstraints();
    gridBagConstraintsx05.gridx = 0;
    gridBagConstraintsx05.gridy = 2;
    gridBagConstraintsx05.insets = new Insets(5,5,5,5); 
    cPane.add(jlbEmailAddr, gridBagConstraintsx05);
        
    GridBagConstraints gridBagConstraintsx06 = new GridBagConstraints();
    gridBagConstraintsx06.gridx = 1;
    gridBagConstraintsx06.gridy = 2;
    gridBagConstraintsx06.insets = new Insets(5,5,5,5); 
    gridBagConstraintsx06.gridwidth = 3;
    gridBagConstraintsx06.fill = GridBagConstraints.BOTH;
    cPane.add(jtfEmailAddr, gridBagConstraintsx06);
        
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
       
    jbnSend.addActionListener(this);
    jbnClear.addActionListener(this);
    jbnExit.addActionListener(this);
    appFrame.getRootPane().setDefaultButton(jbnSend);
  }

  public void actionPerformed (ActionEvent e){
       
      if (e.getSource() == jbnSend){
         SendMsg(outFileName,C,D);
         appFrame.setVisible(false); //you can't see me!
         appFrame.dispose(); //Destroy the JFrame object
         return;
         
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

  public void SendMsg(String outFileName,String A, String B){
    EmailAddr = jtfEmailAddr.getText();
    if (EmailAddr.length() >= 5 ) {
      postMsg pmsg = new postMsg();
      pmsg.PostMsg(EmailAddr,outFileName,outFilePath,A,B);
    } else {
      // do nothing
    }
  } 

  public void clear(){
    jtfEmailAddr.setText("");
  }
}

