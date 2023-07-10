/** Encrypt or Decrypt a char array **/

import java.io.*;
import java.awt.*;
import java.lang.*;
import java.util.*;
import javax.swing.*;
import java.util.Date;
import java.awt.event.*;
import java.awt.Toolkit;
import java.util.Random;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.io.IOException;
import java.lang.*;
 
public final class myEncDec {

  public static String cipherKeyString = "1am2C00l";

  // don't instantiate
  public myEncDec() { }
    
  // MAIN LINE LOGIC
  public static void main(String[] args) {
  rubixByteArray rBA = new rubixByteArray();

  String data = "Encrypt/Decrypt a binary or text file with a simple GUI or easy to use powerful command line utility.  The encrypted file has no specific header information so to a file scanner it looks like a ordinrry binary file.  **** INSTALL **** 1) Save wscacmd.zip to desired folder.  2) Unzip wscacmd.zip.  3) In Windows you will need to set the HOME env.  **** GUI **** java -jar wscacmd.jar Note: Output file writes to the same directory as the input file.  The input file remains un-touched.  I recomend you name the ciphertext filename.ext.enc so you know it's an scacmd encrypted file.  For best results, always use the file chooser for the input file,";
  //System.out.println(data);
  System.out.println();

  int size = data.length();

  char[] buffer       = new char[size+1]; // Data in char array
  char[] dataBytesIn  = new char[size+1]; // Data in char array
  char[] dataBytesOut = new char[size+1]; // Data out char array

  buffer = data.toCharArray();
  for (int j = 1; j < size; j++) {
    dataBytesIn[j] = buffer[j-1];
  }

  // Encrypt Logic
  rBA.doENC(cipherKeyString,size,dataBytesIn,dataBytesOut);
  for (int j = 1; j < size; j++) {
    buffer[j-1] = dataBytesOut[j];
  }
  System.out.println();
  String str = String.valueOf(buffer);
  System.out.println(str);
  System.out.println();


  size = str.length();

  char[] buffer2       = new char[size+1]; // Data in char array
  char[] dataBytesIn2  = new char[size+1]; // Data in char array
  char[] dataBytesOut2 = new char[size+1]; // Data out char array

  buffer2 = str.toCharArray();
  for (int j = 1; j < size; j++) {
    dataBytesIn2[j] = buffer2[j-1];
  }

  // Decrypt logic.
  rBA.doDEC(cipherKeyString,size,dataBytesIn2,dataBytesOut2);
  for (int j = 1; j < size; j++) {
    buffer[j-1] = dataBytesOut2[j];
  }
  System.out.println();
  str = String.valueOf(buffer);
  System.out.println(str);
  System.out.println();

  } // End Main
} // End myEnc class

