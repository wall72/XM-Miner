// This snippet creates a new dialog box
// with buttons on the bottom.

//Title:        XM-Miner
//Version:
//Copyright:    Copyright (c) 1999
//Author:       Yong Uk Song
//Company:      DIS
//Description:  XM-Miner

package xmminer.xmclient.xmgraph;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import xmminer.xmclient.xmgraph.*;

public class XMDialogDialogVNode extends JDialog
{
  JPanel panelMain = new JPanel();
  JPanel panelCenter = new JPanel();
  JButton buttonStop = new JButton();
  Border border1;
  JPanel panelBottom = new JPanel();
  public JProgressBar progressBarMain = new JProgressBar();

  // For thread
  XMDialogVNode m_parentDialog;
  BorderLayout borderLayout1 = new BorderLayout();
  BorderLayout borderLayout2 = new BorderLayout();
  BorderLayout borderLayout3 = new BorderLayout();
  JLabel jLabel1 = new JLabel();

  public XMDialogDialogVNode(JFrame frame, JDialog parentDialog, String title, boolean modal)
  {
    super(frame, title, modal);

    try
    {
      jbInit();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }

    pack();

    // For thread
    m_parentDialog = (XMDialogVNode)parentDialog;

  }

  public XMDialogDialogVNode(JFrame frame, JDialog parentDialog, String title)
  {
    this(frame, parentDialog, title, false);
  }

  public XMDialogDialogVNode(JFrame frame, JDialog parentDialog)
  {
    this(frame, parentDialog, "", false);
  }

  private void jbInit() throws Exception
  {
    border1 = BorderFactory.createRaisedBevelBorder();
    panelCenter.setLayout(borderLayout3);
    buttonStop.setText("Stop");
    buttonStop.addActionListener(new XMDialogDialogVNode_buttonStop_actionAdapter(this));
    this.addWindowListener(new XMDialogDialogVNode_this_windowAdapter(this));
    panelMain.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    panelMain.setLayout(borderLayout1);
    panelBottom.setLayout(borderLayout2);
    progressBarMain.setPreferredSize(new Dimension(300, 30));
    borderLayout1.setVgap(5);
    borderLayout2.setHgap(5);
    jLabel1.setBorder(BorderFactory.createEtchedBorder());
	jLabel1.setText("Calculating...");
    panelMain.add(panelBottom, BorderLayout.CENTER);
    panelBottom.add(buttonStop, BorderLayout.EAST);
    panelBottom.add(jLabel1, BorderLayout.CENTER);
    panelMain.add(panelCenter, BorderLayout.NORTH);
    panelCenter.add(progressBarMain, BorderLayout.CENTER);
    getContentPane().add(panelMain);
  }

  // Stop
  // For thread
  void buttonStop_actionPerformed(ActionEvent e)
  {
    //?button1.setText("Stopping ... ");
    m_parentDialog.StopRunning();
    // Do not dispose()
  }

  void this_windowClosing(WindowEvent e)
  {
    dispose();
  }
}

class XMDialogDialogVNode_buttonStop_actionAdapter implements ActionListener
{
  XMDialogDialogVNode adaptee;

  XMDialogDialogVNode_buttonStop_actionAdapter(XMDialogDialogVNode adaptee)
  {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e)
  {
    adaptee.buttonStop_actionPerformed(e);
  }
}


class XMDialogDialogVNode_this_windowAdapter extends WindowAdapter
{
  XMDialogDialogVNode adaptee;

  XMDialogDialogVNode_this_windowAdapter(XMDialogDialogVNode adaptee)
  {
    this.adaptee = adaptee;
  }

  public void windowClosing(WindowEvent e)
  {
    adaptee.this_windowClosing(e);
  }
}
