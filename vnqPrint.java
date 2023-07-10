import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.print.*;
 
public class vnqPrint implements Printable, ActionListener {
 
public int print(Graphics g, PageFormat pf, int page) throws PrinterException {
  if (page > 0) { /* We have only one page, and 'page' is zero-based */
    return NO_SUCH_PAGE;
  }
    return 0;
}
 
public void actionPerformed(ActionEvent e) {
  PrinterJob job = PrinterJob.getPrinterJob();
  job.setPrintable(this);
  boolean ok = job.printDialog();
  if (ok) {
  try {
     job.print();
  } catch (PrinterException ex) {
       /* The job did not successfully complete */
  }
  }
 }

}

