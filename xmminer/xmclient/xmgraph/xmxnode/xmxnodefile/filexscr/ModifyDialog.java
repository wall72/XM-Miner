package xmminer.xmclient.xmgraph.xmxnode.xmxnodefile.filexscr;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import xmminer.xmclient.xmgraph.xmxnode.xmxnodefile.corbautil.*;
import xmminer.xmclient.xmgraph.xmxnode.xmxnodefile.xmscrutil.*;
import xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.*;

public class ModifyDialog extends JDialog {
  JPanel panel1 = new JPanel();
  ModifyPanel main;
  JButton button_ok = new JButton();
  JButton button_cancel = new JButton();
  Border border1;
  JPanel jPanel1 = new JPanel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  TitledBorder titledBorder1;

  JFrame fr = null;
  InputFrame frame = null;

  public ModifyDialog(JFrame fr, InputFrame f, String title, boolean modal, CorbaAgent a, XMXNodeFileInfo i, Point loc) {
    super(fr, title, modal);
	this.fr = fr;
    frame = f;
    try {
      main = new ModifyPanel(fr, a, i, loc);
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    pack();
  }

  private void jbInit() throws Exception {
    border1 = BorderFactory.createRaisedBevelBorder();
    titledBorder1 = new TitledBorder("");
    jPanel1.setLayout(gridBagLayout2);
    button_ok.setFont(new java.awt.Font("Dialog", 0, 12));
	button_ok.setBorder(BorderFactory.createRaisedBevelBorder());
    button_ok.setMinimumSize(new Dimension(90, 30));
    button_ok.setPreferredSize(new Dimension(90, 30));
    button_ok.setText("확인");
    button_ok.addActionListener(new ModifyDialog_button_ok_actionAdapter(this));
    button_cancel.setFont(new java.awt.Font("Dialog", 0, 12));
	button_cancel.setBorder(BorderFactory.createRaisedBevelBorder());
    button_cancel.setMaximumSize(new Dimension(90, 30));
    button_cancel.setMinimumSize(new Dimension(90, 30));
    button_cancel.setPreferredSize(new Dimension(90, 30));
    button_cancel.setText("취소");
    button_cancel.addActionListener(new ModifyDialog_button_cancel_actionAdapter(this));
    this.addWindowListener(new ModifyDialog_this_windowAdapter(this));
    panel1.setLayout(gridBagLayout1);
    panel1.setPreferredSize(new Dimension(700, 500));
    jPanel1.setBorder(BorderFactory.createEtchedBorder());
    jPanel1.setPreferredSize(new Dimension(700, 55));
    this.setResizable(false);
    panel1.add(main, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    panel1.add(jPanel1, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel1.add(button_ok, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(10, 490, 10, 0), 0, 0));
    jPanel1.add(button_cancel, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(10, 5, 10, 20), 0, 0));
    getContentPane().add(panel1);
  }

  // OK
  void button_ok_actionPerformed(ActionEvent e) {
    main.setAllInfo();
    frame.inputValues.add(new String("OK"));
    frame.inputValues.add(main.info);
    dispose();
  }

  // Cancel
  void button_cancel_actionPerformed(ActionEvent e) {
    main.restore();
    main.setAllInfo();
    frame.inputValues.add(new String("CANCEL"));
    dispose();
  }

  void this_windowClosing(WindowEvent e) {
    dispose();
  }
}

class ModifyDialog_button_ok_actionAdapter implements ActionListener {
  ModifyDialog adaptee;

  ModifyDialog_button_ok_actionAdapter(ModifyDialog adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.button_ok_actionPerformed(e);
  }
}

class ModifyDialog_button_cancel_actionAdapter implements ActionListener {
  ModifyDialog adaptee;

  ModifyDialog_button_cancel_actionAdapter(ModifyDialog adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.button_cancel_actionPerformed(e);
  }
}

class ModifyDialog_this_windowAdapter extends WindowAdapter {
  ModifyDialog adaptee;

  ModifyDialog_this_windowAdapter(ModifyDialog adaptee) {
    this.adaptee = adaptee;
  }

  public void windowClosing(WindowEvent e) {
    adaptee.this_windowClosing(e);
  }
}
