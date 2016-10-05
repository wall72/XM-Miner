// This snippet creates a new dialog box
// with buttons on the bottom.

//Title:        XM-Miner
//Version:
//Copyright:    Copyright (c) 1999
//Author:       Yong Uk Song
//Company:      DIS
//Description:  XM-Miner

package xmminer.xmclient.xmlib;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class XMDialogProgress extends JDialog
{
  JPanel panelMain = new JPanel();
  JPanel panelCenter = new JPanel();
  JButton buttonStop = new JButton();
  Border border1;
  JPanel panelBottom = new JPanel();
  JProgressBar progressBarMain = new JProgressBar();
  BorderLayout borderLayout1 = new BorderLayout();
  BorderLayout borderLayout2 = new BorderLayout();
  BorderLayout borderLayout3 = new BorderLayout();
  JLabel labelDescription = new JLabel();

  // For thread
  XMCORBARunModel m_corbaRunModel;

  public XMDialogProgress(JFrame frame, XMCORBARunModel corbaRunModel, String title, boolean modal)
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
    m_corbaRunModel = corbaRunModel;
  }

  public XMDialogProgress(JFrame frame, XMCORBARunModel corbaRunModel, String title)
  {
    this(frame, corbaRunModel, title, true);
  }

  public XMDialogProgress(JFrame frame, XMCORBARunModel corbaRunModel)
  {
    this(frame, corbaRunModel, "", true);
  }

  private void jbInit() throws Exception
  {
    border1 = BorderFactory.createRaisedBevelBorder();
    panelCenter.setLayout(borderLayout3);
    buttonStop.setText("Stop");
    buttonStop.addActionListener(new XMDialogProgress_buttonStop_actionAdapter(this));
    this.addWindowListener(new XMDialogProgress_this_windowAdapter(this));
    panelMain.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    panelMain.setLayout(borderLayout1);
    panelBottom.setLayout(borderLayout2);
    progressBarMain.setPreferredSize(new Dimension(300, 30));
    borderLayout1.setVgap(5);
    borderLayout2.setHgap(5);
    labelDescription.setBorder(BorderFactory.createEtchedBorder());
    labelDescription.setText("Description");
    panelMain.add(panelBottom, BorderLayout.CENTER);
    panelBottom.add(buttonStop, BorderLayout.EAST);
    panelBottom.add(labelDescription, BorderLayout.CENTER);
    panelMain.add(panelCenter, BorderLayout.NORTH);
    panelCenter.add(progressBarMain, BorderLayout.CENTER);
    getContentPane().add(panelMain);
  }

  public void initProgress(int pbMin, int pbMax)
  {
    progressBarMain.setMinimum(pbMin);
    progressBarMain.setMaximum(pbMax);
  }

  public void setProgress(int pbValue, String pbDescription)
  {
    progressBarMain.setValue(pbValue);
    labelDescription.setText(pbDescription);
  }

  // Stop
  // For thread
  void buttonStop_actionPerformed(ActionEvent e)
  {
    //?button1.setText("Stopping ... ");
    m_corbaRunModel.StopRunning();
    // Do not dispose()
  }

  void this_windowClosing(WindowEvent e)
  {
    dispose();
  }
}

class XMDialogProgress_buttonStop_actionAdapter implements ActionListener
{
  XMDialogProgress adaptee;

  XMDialogProgress_buttonStop_actionAdapter(XMDialogProgress adaptee)
  {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e)
  {
    adaptee.buttonStop_actionPerformed(e);
  }
}

class XMDialogProgress_this_windowAdapter extends WindowAdapter
{
  XMDialogProgress adaptee;

  XMDialogProgress_this_windowAdapter(XMDialogProgress adaptee)
  {
    this.adaptee = adaptee;
  }

  public void windowClosing(WindowEvent e)
  {
    adaptee.this_windowClosing(e);
  }
}
