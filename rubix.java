import java.io.UnsupportedEncodingException;    
import java.nio.charset.StandardCharsets;
import java.io.BufferedReader;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.util.Random;
import java.lang.*;
import java.io.Console;

//import javax.xml.bind.DatatypeConverter;
 
class GetOpt {

    private String[] theArgs = null;
    private int argCount = 0;
    private String optString = null;

    public GetOpt(String[] args, String opts) {
        theArgs = args;
        argCount = theArgs.length;
        optString = opts;
    }

    // user can toggle this to control printing of error messages
    public boolean optErr = false;

    public int processArg(String arg, int n) {
        int value;
        try {
            value = Integer.parseInt(arg);
        }
        catch (NumberFormatException e) {
            if (optErr)
                System.err.println("processArg cannot process " + arg //NOI18N
                                   + " as an integer"); //NOI18N
            return n;
        }
        return value;
    }

    public int tryArg(int k, int n) {
        int value;
        try {
            value = processArg(theArgs[k], n);
        }
        catch (ArrayIndexOutOfBoundsException e) {
            if (optErr)
                System.err.println("tryArg: no theArgs[" + k + "]"); //NOI18N
            return n;
        }
        return value;
    }

    public long processArg(String arg, long n) {
        long value;
        try {
            value = Long.parseLong(arg);
        }
        catch (NumberFormatException e) {
            if (optErr)
                System.err.println("processArg cannot process " + arg //NOI18N
                                   + " as a long"); //NOI18N
            return n;
        }
        return value;
    }

    public long tryArg(int k, long n) {
        long value;
        try {
            value = processArg(theArgs[k], n);
        }
        catch (ArrayIndexOutOfBoundsException e) {
            if (optErr)
                System.err.println("tryArg: no theArgs[" + k + "]"); //NOI18N
            return n;
        }
        return value;
    }

    public double processArg(String arg, double d) {
        double value;
        try {
            value = Double.valueOf(arg).doubleValue();
        }
        catch (NumberFormatException e) {
            if (optErr)
                System.err.println("processArg cannot process " + arg //NOI18N
                                   + " as a double"); //NOI18N
            return d;
        }
        return value;
    }

    public double tryArg(int k, double d) {
        double value;
        try {
            value = processArg(theArgs[k], d);
        }
        catch (ArrayIndexOutOfBoundsException e) {
            if (optErr)
                System.err.println("tryArg: no theArgs[" + k + "]"); //NOI18N
            return d;
        }
        return value;
    }

    public float processArg(String arg, float f) {
        float value;
        try {
            value = Float.valueOf(arg).floatValue();
        }
        catch (NumberFormatException e) {
            if (optErr)
                System.err.println("processArg cannot process " + arg //NOI18N
                                   + " as a float"); //NOI18N
            return f;
        }
        return value;
    }

    public float tryArg(int k, float f) {
        float value;
        try {
            value = processArg(theArgs[k], f);
        }
        catch (ArrayIndexOutOfBoundsException e) {
            if (optErr)
                System.err.println("tryArg: no theArgs[" + k + "]"); //NOI18N
            return f;
        }
        return value;
    }

    public boolean processArg(String arg, boolean b) {
        // `true' in any case mixture is true; anything else is false
        return Boolean.valueOf(arg).booleanValue();
    }

    public boolean tryArg(int k, boolean b) {
        boolean value;
        try {
            value = processArg(theArgs[k], b);
        }
        catch (ArrayIndexOutOfBoundsException e) {
            if (optErr)
                System.err.println("tryArg: no theArgs[" + k + "]"); //NOI18N
            return b;
        }
        return value;
    }

    public String tryArg(int k, String s) {
        String value;
        try {
            value = theArgs[k];
        }
        catch (ArrayIndexOutOfBoundsException e) {
            if (optErr)
                System.err.println("tryArg: no theArgs[" + k + "]"); //NOI18N
            return s;
        }
        return value;
    }

    private static void writeError(String msg, char ch) {
        System.err.println("GetOpt: " + msg + " -- " + ch); //NOI18N
    }

    public static final int optEOF = -1;

    private int optIndex = 0;

    public int optIndexGet() {
        return optIndex;
    }

    public void optIndexSet(int i) {
        optIndex = i;
    }

    private String optArg = null;

    public String optArgGet() {
        return optArg;
    }

    private int optPosition = 1;

    public int getopt() {
        optArg = null;
        if (theArgs == null || optString == null)
            return optEOF;
        if (optIndex < 0 || optIndex >= argCount)
            return optEOF;
        String thisArg = theArgs[optIndex];
        int argLength = thisArg.length();
        // handle special cases
        if (argLength <= 1 || thisArg.charAt(0) != '-') {
            // e.g., "", "a", "abc", or just "-"
            return optEOF;
        }
        else if (thisArg.equals("--")) {//NOI18N
            // end of non-option args
            optIndex++;
            return optEOF;
        }
        // get next "letter" from option argument
        char ch = thisArg.charAt(optPosition);
        // find this option in optString
        int pos = optString.indexOf(ch);
        if (pos == -1 || ch == ':') {
            if (optErr) {
                writeError("illegal option", ch); //NOI18N
            }
            ch = '?';
        }
        else { // handle colon, if present
            if (pos < optString.length() - 1 && optString.charAt(pos + 1) == ':') {
                if (optPosition != argLength - 1) {
                    // take rest of current arg as optArg
                    optArg = thisArg.substring(optPosition + 1);
                    optPosition = argLength - 1; // force advance to next arg below
                }
                else { // take next arg as optArg
                    optIndex++;
                    if (optIndex < argCount
                            && (theArgs[optIndex].charAt(0) != '-' ||
                            theArgs[optIndex].length() >= 2 &&
                            (optString.indexOf(theArgs[optIndex].charAt(1)) == -1
                            || theArgs[optIndex].charAt(1) == ':'))) {
                        optArg = theArgs[optIndex];
                    }
                    else {
                        if (optErr) {
                            writeError("option requires an argument", ch); //NOI18N
                        }
                        optArg = null;
                        ch = ':'; // Linux man page for getopt(3) says : not ?
                    }
                }
            }
        }
        // advance to next option argument,
        // which might be in thisArg or next arg
        optPosition++;
        if (optPosition >= argLength) {
            optIndex++;
            optPosition = 1;
        }
        return ch;
    }

}
public final class rubix {

  private static InputStream in   = null; // input file 
  private static OutputStream out = null; // output file
  private static final int EOF    = -1;   // end of file
  private static int buffer;              // one character buffer
  private static int N;                   // number of input bits left in buffer
  private static int M;                   // number of output bits left in buffer
  private static int gotIN  = 0;          // got an input file name
  private static int gotOUT = 0;          // got an output file name

  private static int KEY_DIGEST  = 32;  // Cipher Key hash size

  private static int keylen      = 0;             // Chiper key length 
  private static int SEED        = 0;             // Random seed 
  private static int COUNT       = 0;             // BLOCK COUNT
  private static byte[] myKey    = new byte[100]; // Ciphertext byte array
  private static byte[] myKeyS   = new byte[100]; // Ciphertext byte array Saved
  private static byte[] bytesIN  = new byte[100]; // Data in byte array
  private static byte[] bytesOUT = new byte[100]; // Data out byte array
  private static byte   byteOUT;
  private static byte   myByte;
 
  private static byte[][] TOP = new byte[3][3];  
  private static byte[][] BOT = new byte[3][3];  
  private static byte[][] FNT = new byte[3][3];  
  private static byte[][] LFT = new byte[3][3];  
  private static byte[][] BAK = new byte[3][3];  
  private static byte[][] RIT = new byte[3][3];  

  private static byte[][] myTOP = new byte[3][3];  
  private static byte[][] myBOT = new byte[3][3];  
  private static byte[][] myFNT = new byte[3][3];  
  private static byte[][] myLFT = new byte[3][3];  
  private static byte[][] myBAK = new byte[3][3];  
  private static byte[][] myRIT = new byte[3][3];  

  private static String myOutFile  = null;  // input file name
  private static String myInFile   = null;  // output file name

  private static Console cnsl      = null;  // console object for password

  // don't instantiate
  private rubix() { }

  private static void openFile() {
    try {
      if (gotIN == 0) {
        in = new BufferedInputStream(System.in);
      } else {
        File inFile  = new File(myInFile);
        in  = new BufferedInputStream(new FileInputStream(inFile));
      }
      if (gotOUT == 0) {
        out = new BufferedOutputStream(System.out);
      } else {
        File outFile = new File(myOutFile);
        out = new BufferedOutputStream(new FileOutputStream(outFile));
      }
    }
    catch (FileNotFoundException ex) {
      System.err.println("File not found.");
      System.exit(1);
    }
  }

 private static void blockCipher_E(int c) {
   for (int i = 1; i < c; i++) {
     Bot_Right();
     Top_Right();
     Fnt_Right();
     Rit_Right();
     Bot_Left();
     Top_Left();
     Rit_Right();
     Fnt_Right();
     
   }
 }

 private static void blockCipher_D(int c) {
   for (int i = 1; i < c; i++) {
     Fnt_Left();
     Rit_Left();
     Top_Right();
     Bot_Right();
     Rit_Left();
     Fnt_Left();
     Top_Left();
     Bot_Left();
   }
 }

 private static void Rit_Right() {

   myFNT[1][2] = FNT[1][2];
   myFNT[2][1] = FNT[2][1];

   myTOP[1][2] = TOP[1][2];
   myTOP[2][1] = TOP[2][1];

   myBOT[1][1] = BOT[1][1];
   myBOT[2][2] = BOT[2][2];

   myBAK[1][1] = BAK[1][1];
   myBAK[2][2] = BAK[2][2];

   myRIT[1][1] = RIT[1][1];
   myRIT[1][2] = RIT[1][2];
   myRIT[2][1] = RIT[2][1];
   myRIT[2][2] = RIT[2][2];

   RIT[1][1] = myRIT[1][2];
   RIT[1][2] = myRIT[2][1];
   RIT[2][1] = myRIT[2][2];
   RIT[2][2] = myRIT[1][1];

   FNT[1][2] = myTOP[1][2];
   FNT[2][1] = myTOP[2][1];

   TOP[1][2] = myBAK[2][2];
   TOP[2][1] = myBAK[1][1];

   BAK[1][1] = myBOT[1][1];
   BAK[2][2] = myBOT[2][2];

   BOT[1][1] = myFNT[2][1];
   BOT[2][2] = myFNT[1][2];
 }

 private static void Rit_Left() {

   myFNT[1][2] = FNT[1][2];
   myFNT[2][1] = FNT[2][1];

   myTOP[1][2] = TOP[1][2];
   myTOP[2][1] = TOP[2][1];

   myBOT[1][1] = BOT[1][1];
   myBOT[2][2] = BOT[2][2];

   myBAK[1][1] = BAK[1][1];
   myBAK[2][2] = BAK[2][2];

   myRIT[1][1] = RIT[1][1];
   myRIT[1][2] = RIT[1][2];
   myRIT[2][1] = RIT[2][1];
   myRIT[2][2] = RIT[2][2];

   RIT[1][1] = myRIT[2][2];
   RIT[1][2] = myRIT[1][1];
   RIT[2][1] = myRIT[1][2];
   RIT[2][2] = myRIT[2][1];

   FNT[1][2] = myBOT[2][2];
   FNT[2][1] = myBOT[1][1];

   TOP[1][2] = myFNT[1][2];
   TOP[2][1] = myFNT[2][1];

   BAK[1][1] = myTOP[2][1];
   BAK[2][2] = myTOP[1][2];

   BOT[1][1] = myBAK[1][1];
   BOT[2][2] = myBAK[2][2];
 }


 private static void Bot_Right() {
  
  myBOT[1][1] = BOT[1][1];
  myBOT[1][2] = BOT[1][2];
  myBOT[2][1] = BOT[2][1];
  myBOT[2][2] = BOT[2][2];
  
  myFNT[2][1] = FNT[2][1];
  myFNT[2][2] = FNT[2][2];
  
  myLFT[2][1] = LFT[2][1];
  myLFT[2][2] = LFT[2][2];
  
  myBAK[2][1] = BAK[2][1];
  myBAK[2][2] = BAK[2][2];
  
  myRIT[2][1] = RIT[2][1];
  myRIT[2][2] = RIT[2][2];
  
  BOT[1][1] = myBOT[2][2];
  BOT[1][2] = myBOT[1][1];
  BOT[2][1] = myBOT[1][2];
  BOT[2][2] = myBOT[2][1];
  
  FNT[2][1] = myRIT[2][1];
  FNT[2][2] = myRIT[2][2];
  
  LFT[2][1] = myFNT[2][1];
  LFT[2][2] = myFNT[2][2];
  
  BAK[2][1] = myLFT[2][1];
  BAK[2][2] = myLFT[2][2];
  
  RIT[2][1] = myBAK[2][1];
  RIT[2][2] = myBAK[2][2];
  
}
  
private static void Bot_Left() {
  
  myBOT[1][1] = BOT[1][1];
  myBOT[1][2] = BOT[1][2];
  myBOT[2][1] = BOT[2][1];
  myBOT[2][2] = BOT[2][2];
  
  myFNT[2][1] = FNT[2][1];
  myFNT[2][2] = FNT[2][2];
  
  myLFT[2][1] = LFT[2][1];
  myLFT[2][2] = LFT[2][2];
  
  myBAK[2][1] = BAK[2][1];
  myBAK[2][2] = BAK[2][2];
  
  myRIT[2][1] = RIT[2][1];
  myRIT[2][2] = RIT[2][2];
  
  BOT[2][2] = myBOT[1][1];
  BOT[2][1] = myBOT[2][2];
  BOT[1][2] = myBOT[2][1];
  BOT[1][1] = myBOT[1][2];
  
  FNT[2][1] = myLFT[2][1];
  FNT[2][2] = myLFT[2][2];
  
  LFT[2][1] = myBAK[2][1];
  LFT[2][2] = myBAK[2][2];
  
  BAK[2][1] = myRIT[2][1];
  BAK[2][2] = myRIT[2][2];
  
  RIT[2][1] = myFNT[2][1];
  RIT[2][2] = myFNT[2][2];
  
}
  
private static void Fnt_Right() {
  
  myFNT[1][1] = FNT[1][1];
  myFNT[1][2] = FNT[1][2];
  myFNT[2][1] = FNT[2][1];
  myFNT[2][2] = FNT[2][2];
  
  myLFT[1][2] = LFT[1][2];
  myLFT[2][1] = LFT[2][1];
  
  myTOP[2][1] = TOP[2][1];
  myTOP[2][2] = TOP[2][2];
  
  myRIT[1][1] = RIT[1][1];
  myRIT[2][2] = RIT[2][2];
  
  myBOT[1][1] = BOT[1][1];
  myBOT[1][2] = BOT[1][2];
  
  FNT[1][1] = myFNT[2][2];
  FNT[1][2] = myFNT[1][1];
  FNT[2][1] = myFNT[1][2];
  FNT[2][2] = myFNT[2][1];
  
  LFT[1][2] = myBOT[1][1];
  LFT[2][1] = myBOT[1][2];
  
  TOP[2][1] = myLFT[1][2];
  TOP[2][2] = myLFT[2][1];
  
  RIT[1][1] = myTOP[2][2];
  RIT[2][2] = myTOP[2][1];
  
  BOT[1][1] = myRIT[2][2];
  BOT[1][2] = myRIT[1][1];
  
}
  
private static void Fnt_Left() {
  
  myFNT[1][1] = FNT[1][1];
  myFNT[1][2] = FNT[1][2];
  myFNT[2][1] = FNT[2][1];
  myFNT[2][2] = FNT[2][2];
  
  myLFT[1][2] = LFT[1][2];
  myLFT[2][1] = LFT[2][1];
  
  myTOP[2][1] = TOP[2][1];
  myTOP[2][2] = TOP[2][2];
  
  myRIT[1][1] = RIT[1][1];
  myRIT[2][2] = RIT[2][2];
  
  myBOT[1][1] = BOT[1][1];
  myBOT[1][2] = BOT[1][2];
  
  FNT[1][1] = myFNT[1][2];
  FNT[1][2] = myFNT[2][1];
  FNT[2][1] = myFNT[2][2];
  FNT[2][2] = myFNT[1][1];
  
  LFT[1][2] = myTOP[2][1];
  LFT[2][1] = myTOP[2][2];
  
  TOP[2][1] = myRIT[2][2];
  TOP[2][2] = myRIT[1][1];
  
  RIT[1][1] = myBOT[1][2];
  RIT[2][2] = myBOT[1][1];
  
  BOT[1][1] = myLFT[1][2];
  BOT[1][2] = myLFT[2][1];
  
}

  private static void Top_Left() {

   myTOP[1][1] = TOP[1][1];
   myTOP[1][2] = TOP[1][2];
   myTOP[2][1] = TOP[2][1];
   myTOP[2][2] = TOP[2][2];

   myFNT[1][1] = FNT[1][1];
   myFNT[1][2] = FNT[1][2];

   myLFT[1][1] = LFT[1][1];
   myLFT[1][2] = LFT[1][2];

   myBAK[1][1] = BAK[1][1];
   myBAK[1][2] = BAK[1][2];

   myRIT[1][1] = RIT[1][1];
   myRIT[1][2] = RIT[1][2];

   TOP[2][2] = myTOP[1][1];
   TOP[2][1] = myTOP[2][2];
   TOP[1][2] = myTOP[2][1];
   TOP[1][1] = myTOP[1][2];

   FNT[1][1] = myLFT[1][1];
   FNT[1][2] = myLFT[1][2];

   LFT[1][1] = myBAK[1][1];
   LFT[1][2] = myBAK[1][2];

   BAK[1][1] = myRIT[1][1];
   BAK[1][2] = myRIT[1][2];

   RIT[1][1] = myFNT[1][1];
   RIT[1][2] = myFNT[1][2];

  }

  private static void Top_Right() {

   myTOP[1][1] = TOP[1][1];
   myTOP[1][2] = TOP[1][2];
   myTOP[2][1] = TOP[2][1];
   myTOP[2][2] = TOP[2][2];

   myFNT[1][1] = FNT[1][1];
   myFNT[1][2] = FNT[1][2];

   myLFT[1][1] = LFT[1][1];
   myLFT[1][2] = LFT[1][2];

   myBAK[1][1] = BAK[1][1];
   myBAK[1][2] = BAK[1][2];

   myRIT[1][1] = RIT[1][1];
   myRIT[1][2] = RIT[1][2];

   TOP[1][1] = myTOP[2][2];
   TOP[1][2] = myTOP[1][1];
   TOP[2][1] = myTOP[1][2];
   TOP[2][2] = myTOP[2][1];

   FNT[1][1] = myRIT[1][1];
   FNT[1][2] = myRIT[1][2];

   LFT[1][1] = myFNT[1][1];
   LFT[1][2] = myFNT[1][2];

   BAK[1][1] = myLFT[1][1];
   BAK[1][2] = myLFT[1][2];

   RIT[1][1] = myBAK[1][1];
   RIT[1][2] = myBAK[1][2];

  }


  private static void fillBytesOUT() {

    bytesOUT[1] = TOP[1][1];
    bytesOUT[2] = TOP[1][2];
    bytesOUT[3] = TOP[2][1];
    bytesOUT[4] = TOP[2][2];

    bytesOUT[5] = BOT[1][1];
    bytesOUT[6] = BOT[1][2];
    bytesOUT[7] = BOT[2][1];
    bytesOUT[8] = BOT[2][2];

    bytesOUT[9]  = FNT[1][1];
    bytesOUT[10] = FNT[1][2];
    bytesOUT[11] = FNT[2][1];
    bytesOUT[12] = FNT[2][2];

    bytesOUT[13] = LFT[1][1];
    bytesOUT[14] = LFT[1][2];
    bytesOUT[15] = LFT[2][1];
    bytesOUT[16] = LFT[2][2];

    bytesOUT[17] = BAK[1][1];
    bytesOUT[18] = BAK[1][2];
    bytesOUT[19] = BAK[2][1];
    bytesOUT[20] = BAK[2][2];

    bytesOUT[21] = RIT[1][1];
    bytesOUT[22] = RIT[1][2];
    bytesOUT[23] = RIT[2][1];
    bytesOUT[24] = RIT[2][2];

  }

  private static void fillRubix() {

    TOP[1][1] = bytesIN[1];
    TOP[1][2] = bytesIN[2];
    TOP[2][1] = bytesIN[3];
    TOP[2][2] = bytesIN[4];

    BOT[1][1] = bytesIN[5];
    BOT[1][2] = bytesIN[6];
    BOT[2][1] = bytesIN[7];
    BOT[2][2] = bytesIN[8];

    FNT[1][1] = bytesIN[9];
    FNT[1][2] = bytesIN[10];
    FNT[2][1] = bytesIN[11];
    FNT[2][2] = bytesIN[12];

    LFT[1][1] = bytesIN[13];
    LFT[1][2] = bytesIN[14];
    LFT[2][1] = bytesIN[15];
    LFT[2][2] = bytesIN[16];

    BAK[1][1] = bytesIN[17];
    BAK[1][2] = bytesIN[18];
    BAK[2][1] = bytesIN[19];
    BAK[2][2] = bytesIN[20];

    RIT[1][1] = bytesIN[21];
    RIT[1][2] = bytesIN[22];
    RIT[2][1] = bytesIN[23];
    RIT[2][2] = bytesIN[24];

  }

  private static void getBlockCount(String base) {
  try{
    MessageDigest digest = MessageDigest.getInstance("SHA-256");
    byte[] hash = digest.digest(base.getBytes("UTF-8"));

    String a = String.valueOf(Math.abs((int)hash[0]));
    String b = String.valueOf(Math.abs((int)hash[1]));
    COUNT = Integer.valueOf(a.substring(0,1)) + Integer.valueOf(b.substring(0,1));

  } catch(Exception ex){
    throw new RuntimeException(ex);
  }
  }



  private static void ash123(String base) {
  try{
    MessageDigest digest = MessageDigest.getInstance("SHA-256");
    byte[] hash = digest.digest(base.getBytes("UTF-8"));

    //System.err.println(hash.length);
    for (int i = 0; i < hash.length; i++) {
      myKey[i]   = hash[i];
      myKeyS[i]  = hash[i];
    }
  } catch(Exception ex){
    throw new RuntimeException(ex);
  }
  }

  private static void clearBuffer() {
      if (M == 0) return;
      if (M > 0) buffer <<= (8 - N);
      try { out.write(buffer); }
      catch (IOException e) { e.printStackTrace(); }
      M = 0;
      buffer = 0;
  }

  private static void writeBit(boolean bit) {
      // add bit to buffer
      buffer <<= 1;
      if (bit) buffer |= 1;

      // if buffer is full (8 bits), write out as a single byte
      M++;
      if (M == 8) clearBuffer();
  } 

  private static void fillBuffer() {
  try { 
      buffer = in.read(); 
      N = 8; 
  }
    catch (IOException e) { System.out.println("EOF"); buffer = EOF; N = -1; }
  }

  public static void close() {
  try {
    in.close();
  }
  catch (IOException e) {
    e.printStackTrace();
    throw new RuntimeException("Could not close BinaryStdIn");
  }
  }

  public static void flush() {
    clearBuffer();
    try { out.flush(); }
    catch (IOException e) { e.printStackTrace(); }
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

  private static void writeByte(byte x) {
    assert x >= 0 && x < 256;

    // optimized if byte-aligned
    if (M == 0) {
      try { 
        out.write(x); 
      }
      catch (IOException e) { e.printStackTrace(); }
      return;
    }

    // otherwise write one bit at a time
    for (int i = 0; i < 8; i++) {
      boolean bit = ((x >>> (8 - i - 1)) & 1) == 1;
      writeBit(bit);
    }
    }

  public static void write(byte x) {
     writeByte(x);
  }

  public static int randInt(int min, int max) {

      // Usually this can be a field rather than a method variable
      Random rand = new Random();

      // nextInt is normally exclusive of the top value,
      // so add 1 to make it inclusive
      int randomNum = rand.nextInt((max - min) + 1) + min;

      return randomNum;
  }

  // Use environmental VAR for key.
  // Windows:  set CIPHERKEY="f00Bar"
  // UNIX/MAC: export CIPHERKEY=f00Bar
    
  // MAIN LINE LOGIC
  public static void main(String[] args) {
  String cipherKeyString   = null;
  String modifierKeyString = null;
  GetOpt go = new GetOpt(args, "hedm:p:i:o:");
  go.optErr = true;
  int info  = 0;
  int ch    = -1;
  int DEC   = 0;
  int ENC   = 0;
  int gotPW = 0;
  int gotMOD = 0;

  // process options in command line arguments
  while ((ch = go.getopt()) != go.optEOF) {
    if ((char)ch == 'h') {
      System.err.println("");
      System.err.println("java -jar rubix.jar [-e|-d] -i inputFile -o outputFile [-p]<cipher key> [-m] <modifier> [-h]");
      System.err.println("cat [type] inputFile | java -jar rubix.jar [-e|-d] [-p]<cipher key> [-m] <modifier> [-h] > outputFile");
      System.err.println("");
      System.exit(0);
    } else if ((char)ch == 'd') {
        DEC = 1;
    } else if ((char)ch == 'e') {
        ENC = 1;
    } else if ((char)ch == 'p') {
        cipherKeyString = go.optArgGet();
        if (cipherKeyString.length() < 6) {
          System.err.println ("ERROR: Key must be 6 or greater characters");
          System.exit(1);
        } else {
          gotPW = 1;
        }
    } else if ((char)ch == 'i') {
        myInFile = go.optArgGet();
        if (myInFile.length() < 1) {
          System.err.println ("ERROR: Invalid input file name.");
          System.exit(1);
        } else {
          gotIN = 1;
        }
    } else if ((char)ch == 'o') {
        myOutFile = go.optArgGet();
        if (myOutFile.length() < 1) {
          System.err.println ("ERROR: Invalid output file name.");
          System.exit(1);
        } else {
          gotOUT = 1;
        }
    } else if ((char)ch == 'm') {
        modifierKeyString = go.optArgGet();
        if (modifierKeyString.length() < 6) {
          System.err.println ("ERROR: Modifier must be 6 or greater characters");
          System.exit(1);
        } else {
          gotMOD = 1;
        }
    } else
        System.exit(1);         // undefined option
  }                             // getopt() returns '?'

  if ( gotPW == 0 ) {
    try {
      cipherKeyString = System.getenv("CIPHERKEY"); // used cipherKey environmental var if set. 
      keylen = cipherKeyString.length();
      if (cipherKeyString != null) {
        if (keylen < 6) {
          System.err.println ("ERROR: CIPHERKEY must be 6 or greater characters");
          cnsl = System.console();
          if (cnsl != null) {
            char[] pwd = cnsl.readPassword("Password: ");
            cipherKeyString = new String(pwd);
          }      
        }
      }
    }
    catch (Exception e) {
      try {
        cnsl = System.console();
        if (cnsl != null) {
          char[] pwd = cnsl.readPassword("Password: ");
          cipherKeyString = new String(pwd);
        }      
        } catch(Exception ex) {
          System.err.println ("ERROR: No CIPHERKEY variable found, use -p.");
          System.exit(1);
        } 
    }
  }
  
  // Open inpuit / output files. 
  openFile();
  fillBuffer();

  ash123(cipherKeyString+cipherKeyString);
  getBlockCount(cipherKeyString); // Sets the value for COUNT

  int r = 0;
  int i = 1;
  int j = 1;

if (ENC == 1) {
  while (!rubix.isEmpty()) {
    bytesIN[i] = rubix.readByte();
    i++;
    if (rubix.isEmpty()) {
      fillRubix();
      fillBytesOUT();
      for (j = 1; j < i; j++) {
        rubix.write(bytesOUT[j]);
      }
    }
    if (i == 25) {
      fillRubix();
      blockCipher_E(COUNT);   // Count number of operations.
      blockCipher_E(COUNT+1); // Again.
      blockCipher_E(COUNT+2); // Again.
      fillBytesOUT();
      for (j = 1; j < 25; j++) {
        rubix.write(bytesOUT[j]);
      }
      i = 1;
      rubix.flush();
    }
  }
  rubix.flush();
}

if (DEC == 1) {
  while (!rubix.isEmpty()) {
    bytesIN[i] = rubix.readByte();
    i++;
    if (rubix.isEmpty()) {
      fillRubix();
      fillBytesOUT();
      for (j = 1; j < i; j++) {
        rubix.write(bytesOUT[j]);
      }
    }
    if (i == 25) {
      fillRubix();
      blockCipher_D(COUNT);   // Count number of operations.
      blockCipher_D(COUNT+1); // Again.
      blockCipher_D(COUNT+2); // Again.
      fillBytesOUT();
      for (j = 1; j < 25; j++) {
        rubix.write(bytesOUT[j]);
      }
      i = 1;
      rubix.flush();
    }
  }
  rubix.flush();
}

} // End Main
} // End rubix class


