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

class wemail implements ActionListener {

  JFrame appFrame;

  JLabel jlbEmailAddr, jlbSubject;
  JTextField jtfEmailAddr, jtfSubject;
  JButton jbnSend, jbnClear, jbnExit;
         
  String inFile, outFile, subject, EmailAddr, msgSub64, msgBase64;
  Container cPane;

  public wemail() {   
    inFile     = null ;
    outFile    = null ;
    subject    = null ;
    EmailAddr  = null ;
  }

  public void createGUI(String AmsgSub64, String AmsgBase64){

    msgBase64 = AmsgBase64;
    msgSub64  = AmsgSub64;

    /*Create a frame, get its contentpane and set layout*/
    appFrame = new JFrame("Wemail");

    cPane = appFrame.getContentPane();
    cPane.setLayout(new GridBagLayout());
       
    //Arrange components on contentPane and set Action Listeners to each JButton
    arrangeComponents();
       
    appFrame.setSize(500,250);
    appFrame.setResizable(false);
    appFrame.setLocationRelativeTo(null);
    appFrame.setVisible(true);
    appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

  }
     
  public void arrangeComponents(){
    jlbEmailAddr = new JLabel("Email To");
    jlbSubject   = new JLabel("Subject");

    jtfEmailAddr = new JTextField(50);
    jtfSubject   = new JTextField(50);
    jtfSubject.setText("VNQ Message");  

    jbnSend  = new JButton("Send");
    jbnClear = new JButton("Clear");
    jbnExit  = new JButton("Exit");

    /*add all initialized components to the container*/

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
        
    GridBagConstraints gridBagConstraintsx07 = new GridBagConstraints();
    gridBagConstraintsx07.gridx = 0;
    gridBagConstraintsx07.insets = new Insets(5,5,5,5); 
    gridBagConstraintsx07.gridy = 3;
    cPane.add(jlbSubject, gridBagConstraintsx07);
        
    GridBagConstraints gridBagConstraintsx08 = new GridBagConstraints();
    gridBagConstraintsx08.gridx = 1;
    gridBagConstraintsx08.gridy = 3;
    gridBagConstraintsx08.gridwidth = 3;
    gridBagConstraintsx08.insets = new Insets(5,5,5,5); 
    gridBagConstraintsx08.fill = GridBagConstraints.BOTH;
    cPane.add(jtfSubject, gridBagConstraintsx08);
        
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
         int rc = SendEmail(msgBase64);
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

  public int SendEmail(String msgBase64){
    inFile    = null;
    EmailAddr = jtfEmailAddr.getText();
    subject   = jtfSubject.getText();
   
    if (EmailAddr.length() >= 6 ) {
      SendBase64Email em64 = new SendBase64Email();
      int returnCode = em64.Send(EmailAddr,subject,msgBase64);
      return returnCode;
    } else {
      return 0;
    }
  } 

  public void clearPw(){
    jtfEmailAddr.setText("");
    jtfSubject.setText("");
  }

  public void clear(){
    jtfEmailAddr.setText("");
    jtfSubject.setText("");
  }
}

