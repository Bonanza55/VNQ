/** Encrypt or Decrypt a dynamic char array **/

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.lang.*;
 
public final class rubixByteArray {

  private static int KEY_DIGEST  = 32; // Cipher Key hash size
  private static int COUNT       = 0;  // Rubix block count
  private static byte[] myKey    = new byte[KEY_DIGEST]; // Ciphertext char array

  private static char[][] TOP = new char[3][3];  
  private static char[][] BOT = new char[3][3];  
  private static char[][] FNT = new char[3][3];  
  private static char[][] LFT = new char[3][3];  
  private static char[][] BAK = new char[3][3];  
  private static char[][] RIT = new char[3][3];  

  private static char[][] myTOP = new char[3][3];  
  private static char[][] myBOT = new char[3][3];  
  private static char[][] myFNT = new char[3][3];  
  private static char[][] myLFT = new char[3][3];  
  private static char[][] myBAK = new char[3][3];  
  private static char[][] myRIT = new char[3][3];  

  // don't instantiate
  public rubixByteArray() { 
  }

 private static void blockCipher_E(int c, char[] charsIN, char[] charsOUT) {
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

 private static void blockCipher_D(int c, char[] charsIN, char[] charsOUT) {
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


  private static void fillBytesOUT(char[] charsOUT) {

    charsOUT[1] = TOP[1][1];
    charsOUT[2] = TOP[1][2];
    charsOUT[3] = TOP[2][1];
    charsOUT[4] = TOP[2][2];

    charsOUT[5] = BOT[1][1];
    charsOUT[6] = BOT[1][2];
    charsOUT[7] = BOT[2][1];
    charsOUT[8] = BOT[2][2];

    charsOUT[9]  = FNT[1][1];
    charsOUT[10] = FNT[1][2];
    charsOUT[11] = FNT[2][1];
    charsOUT[12] = FNT[2][2];

    charsOUT[13] = LFT[1][1];
    charsOUT[14] = LFT[1][2];
    charsOUT[15] = LFT[2][1];
    charsOUT[16] = LFT[2][2];

    charsOUT[17] = BAK[1][1];
    charsOUT[18] = BAK[1][2];
    charsOUT[19] = BAK[2][1];
    charsOUT[20] = BAK[2][2];

    charsOUT[21] = RIT[1][1];
    charsOUT[22] = RIT[1][2];
    charsOUT[23] = RIT[2][1];
    charsOUT[24] = RIT[2][2];

  }

  private static void fillRubix(int u, char[] charsIN) {

    TOP[1][1] = charsIN[u+1];
    TOP[1][2] = charsIN[u+2];
    TOP[2][1] = charsIN[u+3];
    TOP[2][2] = charsIN[u+4];

    BOT[1][1] = charsIN[u+5];
    BOT[1][2] = charsIN[u+6];
    BOT[2][1] = charsIN[u+7];
    BOT[2][2] = charsIN[u+8];

    FNT[1][1] = charsIN[u+9];
    FNT[1][2] = charsIN[u+10];
    FNT[2][1] = charsIN[u+11];
    FNT[2][2] = charsIN[u+12];

    LFT[1][1] = charsIN[u+13];
    LFT[1][2] = charsIN[u+14];
    LFT[2][1] = charsIN[u+15];
    LFT[2][2] = charsIN[u+16];

    BAK[1][1] = charsIN[u+17];
    BAK[1][2] = charsIN[u+18];
    BAK[2][1] = charsIN[u+19];
    BAK[2][2] = charsIN[u+20];

    RIT[1][1] = charsIN[u+21];
    RIT[1][2] = charsIN[u+22];
    RIT[2][1] = charsIN[u+23];
    RIT[2][2] = charsIN[u+24];

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

  public static void doENC(String myCipherKeyString,int size, char[] dataBytesIn, char[] dataBytesOut) {
  ash123(myCipherKeyString);
  getBlockCount(myCipherKeyString);
  int i = 1;
  int j = 1;
  int u = 1;
  int r = 0;
  int k = 1;
  char[] charsIN = new char[size];   // Data in char array
  char[] charsOUT= new char[size];   // Data out char array
  while (u < size) {
    charsIN[u] = dataBytesIn[u];
    i++;
    u++;
    if (i == 25) {
      fillRubix(k-1,charsIN);
      blockCipher_E(COUNT,charsIN,charsOUT);   // Count number of operations.
      blockCipher_E(COUNT+1,charsIN,charsOUT); // again
      blockCipher_E(COUNT+2,charsIN,charsOUT); // again
      fillBytesOUT(charsOUT);
      for (j = 1; j < 25; j++) {
        dataBytesOut[k+(j-1)] = charsOUT[j];
      }
      i = 1;
      k = u;
    }
    if (u == size) {
      for (j = k; j < size; j++) {
        dataBytesOut[j] = charsIN[j];
      }
    }
  }
  charsOUT = null;
  charsIN  = null;
}

  public static void doDEC(String myCipherKeyString,int size, char[] dataBytesIn, char[] dataBytesOut) {
  ash123(myCipherKeyString);
  getBlockCount(myCipherKeyString);
  int i = 1;
  int j = 1;
  int u = 1;
  int r = 0;
  int k = 1;
  int l = 1;
  char[] charsIN = new char[size];   // Data in char array
  char[] charsOUT= new char[size];   // Data out char array
  while (u < size) {
    charsIN[u] = dataBytesIn[u];
    i++;
    u++;
    if (i == 25) {
      fillRubix(k-1,charsIN);
      blockCipher_D(COUNT,charsIN,charsOUT);   // Count number of operations.
      blockCipher_D(COUNT+1,charsIN,charsOUT); // again
      blockCipher_D(COUNT+2,charsIN,charsOUT); // again
      fillBytesOUT(charsOUT);
      for (j = 1; j < 25; j++) {
        dataBytesOut[k+(j-1)] = charsOUT[j];
      }
      i = 1;
      k = u;
    }
    if (u == size) {
      for (j = k; j < size; j++) {
        dataBytesOut[j] = charsIN[j];
      }
    }
  }
  charsOUT = null;
  charsIN  = null;
}
} // End rubixByteArray class
