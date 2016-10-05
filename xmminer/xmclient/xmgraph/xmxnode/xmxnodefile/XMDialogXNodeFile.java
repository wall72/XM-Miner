package xmminer.xmclient.xmgraph.xmxnode.xmxnodefile;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import xmminer.xmclient.xmgraph.*;
import xmminer.xmclient.xmgraph.xmxnode.xmxnodefile.*;

public class XMDialogXNodeFile extends JDialog
{
  JPanel panel1 = new JPanel();
  JButton button1 = new JButton();
  JPanel jPanel1 = new JPanel();
  XMXNodeFile myElement = null;
  BorderLayout borderLayout1 = new BorderLayout();
  BorderLayout borderLayout2 = new BorderLayout();
  JProgressBar jProgressBar1 = new JProgressBar();
  JTextField jTextField1 = new JTextField();

  public XMDialogXNodeFile(Frame frame, XMXNodeFile mine, String title, boolean modal)
  {
    super(frame, title, modal);
    myElement = mine;
    try
    {
      jbInit();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    pack();
  }

  private void jbInit() throws Exception
  {
    jPanel1.setLayout(borderLayout2);
    button1.setText("√Îº“");
    button1.addActionListener(new XMDialogXNodeFile_button1_actionAdapter(this));
    this.addWindowListener(new XMDialogXNodeFile_this_windowAdapter(this));
	panel1.setLayout(borderLayout1);
    panel1.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
    jTextField1.setBackground(new Color(204, 204, 204));
	jTextField1.setBorder(BorderFactory.createEtchedBorder());
	jTextField1.setPreferredSize(new Dimension(250, 22));
	borderLayout1.setVgap(5);
	jProgressBar1.setPreferredSize(new Dimension(300, 30));
	borderLayout2.setHgap(5);
	this.setResizable(false);
	panel1.add(jPanel1, BorderLayout.CENTER);
    jPanel1.add(button1, BorderLayout.EAST);
	jPanel1.add(jTextField1, BorderLayout.CENTER);
	panel1.add(jProgressBar1, BorderLayout.NORTH);
    getContentPane().add(panel1);
  }

  // Stop
  // For thread
  void button1_actionPerformed(ActionEvent e)
  {
    //?button1.setText("Stopping ... ");
    myElement.StopRunning();
    this.dispose();
    // Do not dispose()
  }

  public void quit() {
    this.dispose();
  }
  void this_windowClosing(WindowEvent e)
  {
    dispose();
  }
}

class XMDialogXNodeFile_button1_actionAdapter implements ActionListener
{
  XMDialogXNodeFile adaptee;

  XMDialogXNodeFile_button1_actionAdapter(XMDialogXNodeFile adaptee)
  {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e)
  {
    adaptee.button1_actionPerformed(e);
  }
}


class XMDialogXNodeFile_this_windowAdapter extends WindowAdapter
{
  XMDialogXNodeFile adaptee;

  XMDialogXNodeFile_this_windowAdapter(XMDialogXNodeFile adaptee)
  {
    this.adaptee = adaptee;
  }

  public void windowClosing(WindowEvent e)
  {
    adaptee.this_windowClosing(e);
  }
}
