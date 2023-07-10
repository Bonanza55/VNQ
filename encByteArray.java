/** Encrypt or Decrypt a dynamic byte array **/

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.lang.*;
 
public final class encByteArray {

  private static int KEY_DIGEST  = 32; // Cipher Key hash size
  private static int COUNT       = 0;  // Rubix block count
  private static byte[] myKey    = new byte[KEY_DIGEST]; // Ciphertext byte array

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

  // don't instantiate
  public encByteArray() { 
  }

  // XOR two char variables and return byte
  private static Byte XOR(char a,char b){
  int I = 0;
    I = a ^ b;
    return (byte)I;
  } 

 private static void blockCipher_E(int c, byte[] bytesIN, byte[] bytesOUT) {
   for (int i = 1; i < c; i++) {
       Bot_Right(); // a
       Top_Right(); // b
       Fnt_Right(); // c
       Rit_Right(); // d
       Bot_Left();  // e
       Top_Left();  // f
       Rit_Right(); // g
       Fnt_Right(); // h
   }
 }

 private static void blockCipher_D(int c, byte[] bytesIN, byte[] bytesOUT) {
   for (int i = 1; i < c; i++) {
      Fnt_Left();  // h
      Rit_Left();  // g
      Top_Right(); // f
      Bot_Right(); // e
      Rit_Left();  // d
      Fnt_Left();  // c
      Top_Left();  // b
      Bot_Left();  // a
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


  private static void fillBytesOUT(byte[] bytesOUT) {

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

  private static void fillRubix(int u, byte[] bytesIN) {

    TOP[1][1] = bytesIN[u+1];
    TOP[1][2] = bytesIN[u+2];
    TOP[2][1] = bytesIN[u+3];
    TOP[2][2] = bytesIN[u+4];

    BOT[1][1] = bytesIN[u+5];
    BOT[1][2] = bytesIN[u+6];
    BOT[2][1] = bytesIN[u+7];
    BOT[2][2] = bytesIN[u+8];

    FNT[1][1] = bytesIN[u+9];
    FNT[1][2] = bytesIN[u+10];
    FNT[2][1] = bytesIN[u+11];
    FNT[2][2] = bytesIN[u+12];

    LFT[1][1] = bytesIN[u+13];
    LFT[1][2] = bytesIN[u+14];
    LFT[2][1] = bytesIN[u+15];
    LFT[2][2] = bytesIN[u+16];

    BAK[1][1] = bytesIN[u+17];
    BAK[1][2] = bytesIN[u+18];
    BAK[2][1] = bytesIN[u+19];
    BAK[2][2] = bytesIN[u+20];

    RIT[1][1] = bytesIN[u+21];
    RIT[1][2] = bytesIN[u+22];
    RIT[2][1] = bytesIN[u+23];
    RIT[2][2] = bytesIN[u+24];

  }

  public static void getBlockCount(String base) {
  try{
    MessageDigest digest = MessageDigest.getInstance("SHA-256");
    byte[] hash = digest.digest(base.getBytes("UTF-8"));

    String a = String.valueOf(Math.abs((int)hash[0]));
    String b = String.valueOf(Math.abs((int)hash[1]));
    String c = String.valueOf(Math.abs((int)hash[2]));
    String d = String.valueOf(Math.abs((int)hash[3]));
    COUNT = Integer.valueOf(a.substring(0,1)) + Integer.valueOf(b.substring(0,1)) + Integer.valueOf(c.substring(0,1)) + Integer.valueOf(d.substring(0,1));

  } catch(Exception ex){
    throw new RuntimeException(ex);
  }
  }

  public static void ash123(String base) {
  try{
    MessageDigest digest = MessageDigest.getInstance("SHA-256");
    byte[] hash = digest.digest(base.getBytes("UTF-8"));

    for (int i = 0; i < hash.length; i++) {
      myKey[i] = hash[i];
    }
  } catch(Exception ex){
    throw new RuntimeException(ex);
  }
  }

  public static void doENC(String myCipherKeyString,int size, byte[] dataBytesIn, byte[] dataBytesOut) {
  ash123(myCipherKeyString);
  getBlockCount(myCipherKeyString);
  int i = 1;
  int j = 1;
  int u = 1;
  int r = 0;
  int k = 1;
  byte[] bytesIN = new byte[size];   // Data in byte array
  byte[] bytesOUT= new byte[size];   // Data out byte array
  while (u < size) {
    bytesIN[u] = dataBytesIn[u];
    i++;
    u++;
    if (i == 25) {
      fillRubix(k-1,bytesIN);
      blockCipher_E(COUNT,bytesIN,bytesOUT);   // Count number of operations.
      blockCipher_E(COUNT+1,bytesIN,bytesOUT); // again
      blockCipher_E(COUNT+2,bytesIN,bytesOUT); // again
      fillBytesOUT(bytesOUT);
      for (j = 1; j < 25; j++) {
        dataBytesOut[k+(j-1)] = XOR((char)bytesOUT[j],(char)myKey[r++]);
        if (r == KEY_DIGEST ) {
          r = 0;
        }
      }
      i = 1;
      k = u;
    }
    if (u == size) {
      for (j = k; j < size; j++) {
        dataBytesOut[j] = XOR((char)bytesIN[j],(char)myKey[r++]);
        if (r == KEY_DIGEST ) {
          r = 0;
        }
      }
    }
  }
  bytesOUT = null;
  bytesIN  = null;
}

  public static void doDEC(String myCipherKeyString,int size, byte[] dataBytesIn, byte[] dataBytesOut) {
  ash123(myCipherKeyString);
  getBlockCount(myCipherKeyString);
  int i = 1;
  int j = 1;
  int u = 1;
  int r = 0;
  int k = 1;
  int l = 1;
  byte[] bytesIN = new byte[size];   // Data in byte array
  byte[] bytesOUT= new byte[size];   // Data out byte array
  while (u < size) {
    bytesIN[u] = XOR((char)dataBytesIn[u],(char)myKey[r++]);
    if (r == KEY_DIGEST ) {
      r = 0;
    }
    i++;
    u++;
    if (i == 25) {
      fillRubix(k-1,bytesIN);
      blockCipher_D(COUNT,bytesIN,bytesOUT);   // Count number of operations.
      blockCipher_D(COUNT+1,bytesIN,bytesOUT); // again
      blockCipher_D(COUNT+2,bytesIN,bytesOUT); // again
      fillBytesOUT(bytesOUT);
      for (j = 1; j < 25; j++) {
        dataBytesOut[k+(j-1)] = bytesOUT[j];
      }
      i = 1;
      k = u;
    }
    if (u == size) {
      for (j = k; j < size; j++) {
        dataBytesOut[j] = bytesIN[j];
      }
    }
  }
  bytesOUT = null;
  bytesIN  = null;
}
} // End encByteArray class
