package xmminer.xmclient.xmgraph.xmxnode.xmxnodefile.filexscr;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

import xmminer.xmclient.xmgraph.xmxnode.xmxnodefile.corbautil.*;
import xmminer.xmclient.xmgraph.xmxnode.xmxnodefile.xmscrutil.*;
import xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.*;

public class Dialog_Preview extends JDialog {
  JPanel panel1 = new JPanel();
  JPanel panel2 = new JPanel();
  JButton button_ok = new JButton();
  JButton button_cancel = new JButton();
  JPanel jPanel1 = new JPanel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  GridBagLayout gridBagLayout3 = new GridBagLayout();
  JLabel jLabel1 = new JLabel();
  JTextField jTextField1 = new JTextField();
  JButton jButton_previous = new JButton();
  JButton jButton_next = new JButton();
  JScrollPane jScrollPane1 = new JScrollPane();
  JList jList_preview = new JList();

  Icon previous = new ImageIcon("./xmminer/xmclient/xmgraph/xmxnode/xmxnodefile/filexscr/Rewind16.gif");
  Icon next = new ImageIcon("./xmminer/xmclient/xmgraph/xmxnode/xmxnodefile/filexscr/FastForward16.gif");

  boolean existClient = true;
  FilePath file = null;
  FileIndex findex = null;
  CorbaAgent agent= null;
  String filename = null;

  public Dialog_Preview(JFrame frame, String title, boolean modal, String f) {
    super(frame, title, modal);
    filename = f;
    System.out.println("preview data : " + f);
    try {
      jbInit();
      previewClientData();
      this.jTextField1.setText(filename);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    pack();
  }

  public Dialog_Preview(JFrame frame, String title, boolean modal, FilePath f, CorbaAgent a) {
    super(frame, title, modal);
    existClient = false;
    file = f;
    agent = a;
    try {
      jbInit();
      this.jTextField1.setText(file.filename);
      previewServerData();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    pack();
  }

  void previewServerData()
  {
      try {
        String lines[] = agent.bxnodefile.getLines(file,50);
        jList_preview.setListData(lines);
      } catch (FaultDataException fde) {
        CXMDialog.errorDialog(panel2, fde.message);
      }
  }

  void previewNextServerData()
  {
      try {
        String lines[] = agent.bxnodefile.getDefaultNextLines();
        jList_preview.setListData(lines);
      } catch (FaultDataException fde) {
        CXMDialog.errorDialog(panel2, fde.message);
      }
  }

  void previewPreviousServerData()
  {
      try {
        String lines[] = agent.bxnodefile.getDefaultPreviousLines();
        jList_preview.setListData(lines);
      } catch (FaultDataException fde) {
        CXMDialog.errorDialog(panel2, fde.message);
      }
  }

  void previewClientData()
  {
      PointAccessFileReader freader = null;
      if (findex == null){
          System.out.println("1");
          freader = new PointAccessFileReader(filename,50);
      } else {
          System.out.println("2");
          freader = new PointAccessFileReader(filename,50,findex);
      }
      String lines[] = freader.readNextLines();
      findex = freader.close();
      jList_preview.setListData(lines);
  }

  void previewPreviousClientData()
  {
      PointAccessFileReader freader = null;
      if (findex == null){
          System.out.println("Error "); return;
      } else {
          freader = new PointAccessFileReader(filename,50,findex);
      }
      String lines[] = freader.readPreviousLines();
      findex = freader.close();
      jList_preview.setListData(lines);
  }

  private void jbInit() throws Exception {
	jLabel1.setText("파일 이름");
    jTextField1.setBorder(BorderFactory.createLoweredBevelBorder());
    jTextField1.setPreferredSize(new Dimension(120, 22));
    jButton_previous.setBorder(BorderFactory.createEtchedBorder());
    jButton_previous.setPreferredSize(new Dimension(90, 22));
    jButton_previous.setIcon(previous);
    jButton_previous.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton_previous_actionPerformed(e);
      }
    });

    jButton_next.setBorder(BorderFactory.createEtchedBorder());
    jButton_next.setPreferredSize(new Dimension(90, 22));
    jButton_next.setIcon(next);
    jButton_next.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton_next_actionPerformed(e);
      }
    });

    jList_preview.addAncestorListener(new Dialog_Preview_jList_preview_ancestorAdapter(this));

    jPanel1.setBorder(BorderFactory.createEtchedBorder());
    button_ok.setFont(new java.awt.Font("Dialog", 0, 12));
    button_cancel.setFont(new java.awt.Font("Dialog", 0, 12));
    this.setResizable(false);
    panel2.setBorder(BorderFactory.createEtchedBorder());
    jScrollPane1.getViewport().add(jList_preview, null);

	panel2.setLayout(gridBagLayout3);
    panel2.setPreferredSize(new Dimension(403, 200));
    panel2.add(jLabel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 0, 5), 0, 0));
    panel2.add(jTextField1, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 0, 0, 30), 0, 0));
    panel2.add(jButton_previous, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 0, 0, 5), 0, 0));
    panel2.add(jButton_next, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 0, 0, 5), 0, 0));
    panel2.add(jScrollPane1, new GridBagConstraints(0, 1, 4, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

    button_ok.setBorder(BorderFactory.createRaisedBevelBorder());
    button_ok.setPreferredSize(new Dimension(90, 30));
    button_ok.setText("확인");
    button_ok.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        button_ok_actionPerformed(e);
      }
    });

    button_cancel.setBorder(BorderFactory.createRaisedBevelBorder());
    button_cancel.setPreferredSize(new Dimension(110, 50));
    button_cancel.setText("취소");
    button_cancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        button_cancel_actionPerformed(e);
      }
    });

	jPanel1.setLayout(gridBagLayout2);
    jPanel1.setPreferredSize(new Dimension(700, 55));
	jPanel1.add(button_ok, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.SOUTHEAST, GridBagConstraints.NONE, new Insets(10, 500, 10, 4), 60, 6));
    jPanel1.add(button_cancel, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 4, 10, 10), 60, 6));

	panel1.setLayout(gridBagLayout1);
    panel1.setPreferredSize(new Dimension(700, 500));
    panel1.add(panel2, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    panel1.add(jPanel1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 85, 4));

    this.getContentPane().add(panel1);
    this.addWindowListener(new Dialog_Preview_this_windowAdapter(this));
}

  // OK
  void button_ok_actionPerformed(ActionEvent e) {
    dispose();
  }

  // Cancel
  void button_cancel_actionPerformed(ActionEvent e) {
    dispose();
  }

  void this_windowClosing(WindowEvent e) {
    dispose();
  }

  void jButton_previous_actionPerformed(ActionEvent e) {
      if (existClient) previewPreviousClientData();
      else previewPreviousServerData();
  }

  void jButton_next_actionPerformed(ActionEvent e) {
      if (existClient) previewClientData();
      else previewNextServerData();
  }

  void jList_preview_ancestorAdded(AncestorEvent e) {

  }
}




class Dialog_Preview_this_windowAdapter extends WindowAdapter {
  Dialog_Preview adaptee;

  Dialog_Preview_this_windowAdapter(Dialog_Preview adaptee) {
    this.adaptee = adaptee;
  }

  public void windowClosing(WindowEvent e) {
    adaptee.this_windowClosing(e);
  }
}

class Dialog_Preview_jList_preview_ancestorAdapter implements AncestorListener {
  Dialog_Preview adaptee;

  Dialog_Preview_jList_preview_ancestorAdapter(Dialog_Preview adaptee) {
    this.adaptee = adaptee;
  }

  public void ancestorAdded(AncestorEvent e) {
    adaptee.jList_preview_ancestorAdded(e);
  }

  public void ancestorMoved(AncestorEvent e) {
  }

  public void ancestorRemoved(AncestorEvent e) {
  }
}
