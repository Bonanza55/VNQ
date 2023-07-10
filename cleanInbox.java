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

import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.sql.Timestamp;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import java.text.SimpleDateFormat;

class cleanInbox implements ActionListener {

  JFrame appFrame;

  JLabel jlbCleanInboxA;
  JLabel jlbCleanInboxB;
  JButton jbnYes, jbnNo, jbnExit;
         
  InputStream input  = null;
  String myInbox     = "inbox/";
  String outPropFile = null;
  Container cPane;


  public cleanInbox() { }

  private static final SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");

  public void cleanInbox(){

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
    jlbCleanInboxA = new JLabel("Delete All Messages");
    jlbCleanInboxB = new JLabel("Older Than Today.");

    jbnYes  = new JButton("Yes");
    jbnNo = new JButton("No");
    jbnExit  = new JButton("Exit");

    /*add all initialized components to the container*/
    GridBagConstraints gridBagConstraintsx01 = new GridBagConstraints();
    gridBagConstraintsx01.gridx = 2;
    gridBagConstraintsx01.gridy = 1;
    gridBagConstraintsx01.insets = new Insets(5,5,5,5); 
    cPane.add(jlbCleanInboxA, gridBagConstraintsx01);

    GridBagConstraints gridBagConstraintsx05 = new GridBagConstraints();
    gridBagConstraintsx05.gridx = 2;
    gridBagConstraintsx05.gridy = 2;
    gridBagConstraintsx05.insets = new Insets(5,5,5,5); 
    cPane.add(jlbCleanInboxB, gridBagConstraintsx05);
        
    GridBagConstraints gridBagConstraintsx10 = new GridBagConstraints();
    gridBagConstraintsx10.gridx = 1;
    gridBagConstraintsx10.gridy = 4;
    gridBagConstraintsx10.insets = new Insets(5,5,5,5); 
    cPane.add(jbnYes, gridBagConstraintsx10);
        
    GridBagConstraints gridBagConstraintsx15 = new GridBagConstraints();
    gridBagConstraintsx15.gridx = 2;
    gridBagConstraintsx15.gridy = 4;
    gridBagConstraintsx15.insets = new Insets(5,5,5,5); 
    cPane.add(jbnNo, gridBagConstraintsx15);
        
    GridBagConstraints gridBagConstraintsx16 = new GridBagConstraints();
    gridBagConstraintsx16.gridx = 3;
    gridBagConstraintsx16.gridy = 4;
    gridBagConstraintsx16.insets = new Insets(5,5,5,5); 
    cPane.add(jbnExit, gridBagConstraintsx16);
       
    jbnYes.addActionListener(this);
    jbnNo.addActionListener(this);
    jbnExit.addActionListener(this);
    appFrame.getRootPane().setDefaultButton(jbnYes);
  }

  public void actionPerformed (ActionEvent e){
       
      if (e.getSource() == jbnYes){
        Date date = new Date();
        Timestamp timeStamp = new Timestamp(date.getTime());
        int today = Integer.parseInt(sdf.format(timeStamp));
        File folder = new File("inbox");
        File[] listOfFiles = folder.listFiles();
        int fDate = 0;
        for (int i = 0; i < listOfFiles.length; i++) {
          if (listOfFiles[i].isFile()) {
            if (listOfFiles[i].getName().substring(0,4).compareTo("vnq.")==0) {
              fDate = Integer.parseInt(listOfFiles[i].getName().substring(4,10));
              if (fDate < today) {
                File fName = new File("inbox/"+listOfFiles[i].getName());
                fName.delete();
              }
            }
          }
        }
        appFrame.setVisible(false); //you can't see me!
        appFrame.dispose(); //Destroy the JFrame object
        return;
      }

      if (e.getSource() == jbnNo){
        appFrame.setVisible(false); //you can't see me!
        appFrame.dispose(); //Destroy the JFrame object
        return;
      }

      if (e.getSource() == jbnExit){      
        appFrame.setVisible(false); //you can't see me!
        appFrame.dispose(); //Destroy the JFrame object
        return;
      }

  }
}
