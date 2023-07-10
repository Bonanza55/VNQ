class otherPw {
public otherPw() { }

public String DecOtherPwd(String myOtherPw, int PwLen, String myPKV) {

rubixByteArray rBA = new rubixByteArray();
String xyz = myPKV;

char [] BF = myOtherPw.toCharArray();
int size = BF.length;
char[] BI = new char[size+1]; // Data in char array
char[] BO = new char[size+1]; // Data out char array
char[] PK = new char[PwLen]; 

for (int j = 1; j < size; j++) {
  BI[j] = BF[j-1];
}
// De-scramble the cipherText
rBA.doDEC(xyz,size,BI,BO);
for (int j = 1; j < xyz.length(); j++) {
  BF[j-1] = BO[j];
}
int J = 0;
for (int i = 0; i < (PwLen*2); i++) {
  if (i % 2 == 0) {
    PK[J++]   = BF[i];
  }
}
return (String.valueOf(PK));

}
}
