package xmminer.xmclient.xmgraph.xmxnode.xmxnodefile.filexscr;

import xmminer.xmclient.xmgraph.xmxnode.xmxnodefile.corbautil.*;
import xmminer.xmclient.xmgraph.xmxnode.xmxnodefile.xmscrutil.*;
import xmminer.xmclient.xmgraph.xmxnode.xmxnodefile.filextree.ServerDirectoryTreeModel;
import xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.border.*;
import java.lang.Thread;

public class Dialog_FileSelect extends JDialog
{
  JPanel panel1 = new JPanel();
  public FileSelectPanel panel2 = null;
  JButton button_ok = new JButton();
  JButton button_cancel = new JButton();
  Border border1;
  JPanel jPanel1 = new JPanel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  TitledBorder titledBorder1;
  CorbaAgent agent = null;
  JFrame fr = null;
  InputFrame frame = null;

  public Dialog_FileSelect(JFrame fr, InputFrame f, boolean modal, CorbaAgent a, Point loc, FilePath selectedFile, DirectoryType[] dList)
  {
    	super(fr, "File Select", modal);
		this.fr = fr;
    	frame = f;
    	agent = a;
    	panel2 = new FileSelectPanel(fr, agent, loc, selectedFile, dList);
    	try {
      		jbInit();
    	} catch (Exception e) {
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
    button_ok.addActionListener(new Dialog_FileSelect_button_ok_actionAdapter(this));
    button_cancel.setFont(new java.awt.Font("Dialog", 0, 12));
    button_cancel.setBorder(BorderFactory.createRaisedBevelBorder());
    button_cancel.setMaximumSize(new Dimension(90, 30));
    button_cancel.setMinimumSize(new Dimension(90, 30));
    button_cancel.setPreferredSize(new Dimension(90, 30));
    button_cancel.setText("취소");
    button_cancel.addActionListener(new Dialog_FileSelect_button_cancel_actionAdapter(this));
    this.addWindowListener(new Dialog_FileSelect_this_windowAdapter(this));
    panel1.setLayout(gridBagLayout1);
    panel1.setPreferredSize(new Dimension(700, 500));
    jPanel1.setBorder(BorderFactory.createEtchedBorder());
    jPanel1.setPreferredSize(new Dimension(700, 55));
    this.setResizable(false);
    panel1.add(panel2, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    panel1.add(jPanel1, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel1.add(button_cancel, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(10, 4, 10, 20), 0, 0));
    jPanel1.add(button_ok, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(10, 486, 10, 4), 0, 0));
    getContentPane().add(panel1);

  }

  // OK
  void button_ok_actionPerformed(ActionEvent e) {
      frame.inputValues.add(new String("OK"));
      if (panel2.jComboBox_loc.getSelectedIndex() == 0) {
          if (panel2.selectedServerFile != null) {
              frame.inputValues.add(panel2.selectedServerFile);
              dispose();
          } else {
              CXMDialog.errorDialog(panel2, "대상 파일을 선택해 주십시오.");
          }
      } else {
          CXMDialog.errorDialog(panel2, "마이닝 서버에서 파일을 선택해 주십시오.");
      }
  }

  // Cancel
  void button_cancel_actionPerformed(ActionEvent e) {
      frame.inputValues.add(new String("CANCEL"));
      dispose();
  }

  void this_windowClosing(WindowEvent e) {
      dispose();
  }
}

class Dialog_FileSelect_button_ok_actionAdapter implements ActionListener {
  Dialog_FileSelect adaptee;

  Dialog_FileSelect_button_ok_actionAdapter(Dialog_FileSelect adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.button_ok_actionPerformed(e);
  }
}

class Dialog_FileSelect_button_cancel_actionAdapter implements ActionListener {
  Dialog_FileSelect adaptee;

  Dialog_FileSelect_button_cancel_actionAdapter(Dialog_FileSelect adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.button_cancel_actionPerformed(e);
  }
}

class Dialog_FileSelect_this_windowAdapter extends WindowAdapter {
  Dialog_FileSelect adaptee;

  Dialog_FileSelect_this_windowAdapter(Dialog_FileSelect adaptee) {
    this.adaptee = adaptee;
  }

  public void windowClosing(WindowEvent e) {
    adaptee.this_windowClosing(e);
  }

   public void windowOpened(WindowEvent e) {
/*    final SwingWorker worker = new SwingWorker() {
     		public Object construct() {
				System.out.println("\n get Dir Info And Concate ...");
				DirectoryType[] sDirList = adaptee.agent.bxnodefile.getDirectoryInfo();
    			return new ServerDirectoryTreeModel(new DefaultMutableTreeNode(sDirList[0].nodeName), sDirList);
     		}
     };

     try {
     		Thread.sleep(5000);
     } catch (InterruptedException ee) {}

     worker.start();
     ServerDirectoryTreeModel streeModel = (ServerDirectoryTreeModel)worker.get();
 		adaptee.panel2.jTree_sdir_valueChanged( new TreeSelectionEvent(streeModel,
                          null, //TreePath path,
                          true, //boolean isNew,
                          null, //TreePath oldLeadSelectionPath,
                          null //TreePath newLeadSelectionPath
                          ));

     System.out.println("\n SwingWorker Thread end...");*/
  }

  public void windowActivated(WindowEvent e) {
     System.out.println("\n windowActivated \n");
  }

}// end of class
