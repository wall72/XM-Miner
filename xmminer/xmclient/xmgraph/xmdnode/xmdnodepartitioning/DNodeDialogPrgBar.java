
//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1999
//Author:       Your Name
//Company:      Your Company
//Description:  Your description

package xmminer.xmclient.xmgraph.xmdnode.xmdnodepartitioning;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class DNodeDialogPrgBar extends JDialog {
  XMDNodePartitioning cp;

  JPanel panel1 = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  JProgressBar jProgressBar1 = new JProgressBar();
  JPanel jPanel1 = new JPanel();
  BorderLayout borderLayout2 = new BorderLayout();
  JButton jButton1 = new JButton();
  JScrollPane jScrollPane1 = new JScrollPane();
  JTextArea jTextArea1 = new JTextArea();

  public DNodeDialogPrgBar(Frame frame, String title, boolean modal, XMDNodePartitioning icp) {
    super(frame, title, modal);
    cp = icp;
    try  {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  void jbInit() throws Exception {
    panel1.setLayout(borderLayout1);
    borderLayout1.setVgap(5);
    jPanel1.setLayout(borderLayout2);
    jButton1.setText("√Îº“");
    jButton1.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        jButton1_actionPerformed(e);
      }
    });
    borderLayout2.setHgap(5);
    jTextArea1.setBackground(new Color(204, 204, 204));
    jProgressBar1.setPreferredSize(new Dimension(300, 30));
    getContentPane().add(panel1);
    panel1.add(jProgressBar1, BorderLayout.NORTH);
    panel1.add(jPanel1, BorderLayout.CENTER);
    jPanel1.add(jButton1, BorderLayout.EAST);
    jPanel1.add(jScrollPane1, BorderLayout.CENTER);
    jScrollPane1.getViewport().add(jTextArea1, null);
    panel1.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
	this.setResizable(false);
  }

  void jButton1_actionPerformed(ActionEvent e)
  {
     cp.StopRunning();
  }
}

