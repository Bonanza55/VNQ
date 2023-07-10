class ABCD {
public ABCD() { }

public String getABCD(String A, String B) {

other o = new other();

String C = A;
String D = B;

String a = C.substring(0,1);
String b = C.substring(79,80);

int c = Integer.parseInt(b+a);

C = o.Other(C,c,D).trim();

char[][] abc = new char[3][5];
char[][] tmp = new char[3][5];

tmp[0][0] = C.substring(0,1).charAt(0);
tmp[0][1] = C.substring(1,2).charAt(0); 
tmp[0][2] = C.substring(2,3).charAt(0); 
tmp[0][3] = C.substring(3,4).charAt(0); 
tmp[0][4] = C.substring(4,5).charAt(0); 
tmp[1][0] = C.substring(5,6).charAt(0); 
tmp[1][1] = C.substring(6,7).charAt(0); 
tmp[1][2] = C.substring(7,8).charAt(0); 
tmp[1][3] = C.substring(8,9).charAt(0); 
tmp[1][4] = C.substring(9,10).charAt(0); 
tmp[2][0] = C.substring(10,11).charAt(0); 
tmp[2][1] = C.substring(11,12).charAt(0); 
tmp[2][2] = C.substring(12,13).charAt(0); 
tmp[2][3] = C.substring(13,14).charAt(0); 
tmp[2][4] = C.substring(14,15).charAt(0); 

abc[0][0] = tmp[2][1];
abc[0][1] = tmp[1][4]; 
abc[0][2] = tmp[2][0];  
abc[0][3] = tmp[2][2];
abc[0][4] = tmp[0][0]; 
abc[1][0] = tmp[2][4];
abc[1][1] = tmp[0][4];
abc[1][2] = tmp[2][3]; 
abc[1][3] = tmp[0][2]; 
abc[1][4] = tmp[1][2];
abc[2][0] = tmp[0][1];
abc[2][1] = tmp[1][1];
abc[2][2] = tmp[0][3];
abc[2][3] = tmp[1][3]; 
abc[2][4] = tmp[1][0];

StringBuffer ab = new StringBuffer();
ab.append((char)abc[0][0]);
ab.append((char)abc[0][1]);
ab.append((char)abc[0][2]);
ab.append((char)abc[0][3]);
ab.append((char)abc[0][4]);
ab.append((char)abc[1][0]);
ab.append((char)abc[1][1]);
ab.append((char)abc[1][2]);
ab.append((char)abc[1][3]);
ab.append((char)abc[1][4]);
ab.append((char)abc[2][0]);
ab.append((char)abc[2][1]);
ab.append((char)abc[2][2]);
ab.append((char)abc[2][3]);
ab.append((char)abc[2][4]);

String abcd = new String(ab);
return abcd;

}
} // class
