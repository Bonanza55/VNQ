class ftpPwd {
public ftpPwd() { }

public String getFtpPwd(String A, String B) {

otherPw okv = new otherPw();

String C = A;
String D = B;

String a = C.substring(0,1);
String b = C.substring(79,80);

int c = Integer.parseInt(b+a);

C = okv.DecOtherPwd(C,c,D).trim();

char[][] pwd = new char[3][5];
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

/*
tmp[0][0] = 'V';
tmp[0][1] = '7';
tmp[0][2] = 'p';
tmp[0][3] = '2';
tmp[0][4] = 'X';
tmp[1][0] = 'U';
tmp[1][1] = 'C';
tmp[1][2] = '5';
tmp[1][3] = '7';
tmp[1][4] = 'P';
tmp[2][0] = '5';
tmp[2][1] = 'k';
tmp[2][2] = '7';
tmp[2][3] = '3';
tmp[2][4] = '8';
*/

pwd[0][0] = tmp[2][1];
pwd[0][1] = tmp[1][4]; 
pwd[0][2] = tmp[2][0];  
pwd[0][3] = tmp[2][2];
pwd[0][4] = tmp[0][0]; 
pwd[1][0] = tmp[2][4];
pwd[1][1] = tmp[0][4];
pwd[1][2] = tmp[2][3]; 
pwd[1][3] = tmp[0][2]; 
pwd[1][4] = tmp[1][2];
pwd[2][0] = tmp[0][1];
pwd[2][1] = tmp[1][1];
pwd[2][2] = tmp[0][3];
pwd[2][3] = tmp[1][3]; 
pwd[2][4] = tmp[1][0];

StringBuffer pw = new StringBuffer();
pw.append((char)pwd[0][0]);
pw.append((char)pwd[0][1]);
pw.append((char)pwd[0][2]);
pw.append((char)pwd[0][3]);
pw.append((char)pwd[0][4]);
pw.append((char)pwd[1][0]);
pw.append((char)pwd[1][1]);
pw.append((char)pwd[1][2]);
pw.append((char)pwd[1][3]);
pw.append((char)pwd[1][4]);
pw.append((char)pwd[2][0]);
pw.append((char)pwd[2][1]);
pw.append((char)pwd[2][2]);
pw.append((char)pwd[2][3]);
pw.append((char)pwd[2][4]);

String pass = new String(pw);
return pass;

}
} // class
