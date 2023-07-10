To Compile:

javac -classpath ./:./activation.jar:./commons-net-3.6.jar:./mail.jar *.java

To make JAR file:

jar -cvfm vnq.jar manifest.txt Filename.class vnq$1.class vnq$vnqPrint.class rubixByteArray.class encByteArray.class other.class getMsg.class cleanInbox.class getPw.class wsetup.class wdeleteMB.class wpostMsg.class wemail.class ABCD.class setUp.class removeMB.class postMsg.class SendBase64Email\$1.class SendBase64Email.class getAbout.class vnq.class

To run JAR file:

java -jar vnq.jar -classpath ./:./activation.jar:./commons-net-3.6.jar:./mail.jar 2>/dev/null &
