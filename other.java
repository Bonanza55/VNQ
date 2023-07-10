class other {
public other() { }

public String Other(String A, int D, String B) {

rubixByteArray rBA = new rubixByteArray();
String xyz = B;

char [] BF = A.toCharArray();
int size = BF.length;
char[] BI = new char[size+1]; // Data in char array
char[] BO = new char[size+1]; // Data out char array
char[] C = new char[D]; 

for (int j = 1; j < size; j++) {
  BI[j] = BF[j-1];
}
// De-scramble the cipherText
rBA.doDEC(xyz,size,BI,BO);
for (int j = 1; j < xyz.length(); j++) {
  BF[j-1] = BO[j];
}
int J = 0;
for (int i = 0; i < (D*2); i++) {
  if (i % 2 == 0) {
    C[J++]   = BF[i];
  }
}
return (String.valueOf(C));

}
}
