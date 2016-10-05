package xmminer.xmclient.xmgraph.xmxnode.xmxnodedb;

import java.awt.*;
import javax.swing.*;
import javax.swing.Icon;
import java.awt.event.*;
import javax.swing.border.*;

public class XNodeDBDialogPrgBar extends JDialog {

  //start member variables declaraion
  XMXNodeDB _XND;
  //end of member variables declaration

  JPanel panel1 = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  JProgressBar jProgressBar1 = new JProgressBar();
  JButton jButton1 = new JButton();
  JPanel jPanel6 = new JPanel();
  BorderLayout borderLayout2 = new BorderLayout();
  JLabel jLabel2 = new JLabel();
  Border border1;

  //Constructor
  public XNodeDBDialogPrgBar(Frame frame, XMXNodeDB xnd, String title, boolean modal) {//,String sql) {
    super(frame, title, modal);
    _XND = xnd;
    try  {
      jbInit();
      pack();
    }catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  void jbInit() throws Exception {
    panel1.setLayout(borderLayout1);
    panel1.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
	panel1.setMinimumSize(new Dimension(120, 68));
    panel1.setPreferredSize(new Dimension(320, 84));
	jProgressBar1.setMaximumSize(new Dimension(32767, 14));
	jProgressBar1.setMinimumSize(new Dimension(10, 14));
	jProgressBar1.setPreferredSize(new Dimension(300, 30));
	jProgressBar1.setStringPainted(true);
	jButton1.setText("Cancel");
	jButton1.addActionListener(new java.awt.event.ActionListener(){
      public void actionPerformed(ActionEvent e){
        jButton1_actionPerformed(e);
      }
    });
	jPanel6.setLayout(borderLayout2);
	jLabel2.setBorder(BorderFactory.createEtchedBorder());
	jLabel2.setMaximumSize(new Dimension(100, 18));
	jLabel2.setPreferredSize(new Dimension(200, 18));
	borderLayout1.setVgap(5);
	jPanel6.setMinimumSize(new Dimension(100, 29));
	jPanel6.setPreferredSize(new Dimension(81, 29));
	borderLayout2.setHgap(5);
	this.setResizable(false);
    getContentPane().add(panel1);
	panel1.add(jProgressBar1, BorderLayout.NORTH);
	panel1.add(jPanel6, BorderLayout.CENTER);
	jPanel6.add(jButton1, BorderLayout.EAST);
	jPanel6.add(jLabel2, BorderLayout.CENTER);
  }

  public void DialogClose(){
    this.dispose();
  }

  //event listener Stop Button: stopp running...
  void jButton1_actionPerformed(ActionEvent e){
     _XND.m_sXNodeDB.StopRunning();
  }
}
