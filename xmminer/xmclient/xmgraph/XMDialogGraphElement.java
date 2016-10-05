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

public class XMDialogGraphElement extends JDialog
{
  JPanel panelMain = new JPanel();
  JPanel panelCenter = new JPanel();
  JButton buttonStop = new JButton();
  Border border1;
  JPanel panelBottom = new JPanel();
  public JProgressBar jProgressBar1 = new JProgressBar();

  // For thread
  XMGraphElement m_graphElement;
  BorderLayout borderLayout1 = new BorderLayout();
  BorderLayout borderLayout2 = new BorderLayout();
  BorderLayout borderLayout3 = new BorderLayout();
  JLabel jLabel1 = new JLabel();

  public XMDialogGraphElement(JFrame frame, XMGraphElement graphElement, String title, boolean modal)
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
    m_graphElement = graphElement;

  }

  public XMDialogGraphElement(JFrame frame, XMGraphElement graphElement, String title)
  {
    this(frame, graphElement, title, false);
  }

  public XMDialogGraphElement(JFrame frame, XMGraphElement graphElement)
  {
    this(frame, graphElement, "", false);
  }

  private void jbInit() throws Exception
  {
    border1 = BorderFactory.createRaisedBevelBorder();
    panelCenter.setLayout(borderLayout3);
    buttonStop.setText("√Îº“");
    buttonStop.addActionListener(new XMDialogGraphElement_buttonStop_actionAdapter(this));
    this.addWindowListener(new XMDialogGraphElement_this_windowAdapter(this));
    panelMain.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    panelMain.setLayout(borderLayout1);
    panelBottom.setLayout(borderLayout2);
    jProgressBar1.setPreferredSize(new Dimension(300, 30));
    jLabel1.setBorder(BorderFactory.createEtchedBorder());
    borderLayout1.setVgap(5);
    borderLayout2.setHgap(5);
    this.setResizable(false);
    panelMain.add(panelBottom, BorderLayout.CENTER);
    panelBottom.add(buttonStop, BorderLayout.EAST);
    panelBottom.add(jLabel1, BorderLayout.CENTER);
    panelMain.add(panelCenter, BorderLayout.NORTH);
    panelCenter.add(jProgressBar1, BorderLayout.CENTER);
    getContentPane().add(panelMain);
  }

  // Stop
  // For thread
  void buttonStop_actionPerformed(ActionEvent e)
  {
    //?button1.setText("Stopping ... ");
    m_graphElement.StopRunning();
    // Do not dispose()
  }

  void this_windowClosing(WindowEvent e)
  {
    dispose();
  }
}

class XMDialogGraphElement_buttonStop_actionAdapter implements ActionListener
{
  XMDialogGraphElement adaptee;

  XMDialogGraphElement_buttonStop_actionAdapter(XMDialogGraphElement adaptee)
  {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e)
  {
    adaptee.buttonStop_actionPerformed(e);
  }
}


class XMDialogGraphElement_this_windowAdapter extends WindowAdapter
{
  XMDialogGraphElement adaptee;

  XMDialogGraphElement_this_windowAdapter(XMDialogGraphElement adaptee)
  {
    this.adaptee = adaptee;
  }

  public void windowClosing(WindowEvent e)
  {
    adaptee.this_windowClosing(e);
  }
}
