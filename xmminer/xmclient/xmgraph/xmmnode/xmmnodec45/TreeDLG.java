package xmminer.xmclient.xmgraph.xmmnode.xmmnodec45;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.tree.*;
import javax.swing.border.*;

public class TreeDLG extends JDialog{
  DefaultTreeCellRenderer TreeRenderer;
  ImageIcon iconClosedBranch = new ImageIcon(xmminer.xmclient.xmgraph.xmmnode.xmmnodec45.TreeDLG.class.getResource("../../../images/closedbranch.gif"));
  ImageIcon iconOpenBranch = new ImageIcon(xmminer.xmclient.xmgraph.xmmnode.xmmnodec45.TreeDLG.class.getResource("../../../images/openbranch.gif"));
  ImageIcon iconLeaf = new ImageIcon(xmminer.xmclient.xmgraph.xmmnode.xmmnodec45.TreeDLG.class.getResource("../../../images/leaf.gif"));
  JMenu jMenu1 = new JMenu();
  JMenuItem jMenuItem1 = new JMenuItem();
  JMenuItem jMenuItem2 = new JMenuItem();
  Frame m_frame;
  JMenuBar jMenuBar1 = new JMenuBar();
  JTree jTree1 = new JTree();
  JScrollPane jScrollPane1;
  JPanel panel1 = new JPanel();
  BorderLayout borderLayout2 = new BorderLayout();
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel2 = new JPanel();

  public TreeDLG(Frame frame, String title, boolean modal){
    super(frame, title, modal);
	m_frame = frame;
    try{
      jbInit();
      pack();
    }catch(Exception ex){
      ex.printStackTrace();
    }
  }

  public TreeDLG(JFrame frame, String title, boolean modal, TreeNode tree){
    super(frame, title, modal);
	m_frame = frame;
    try{
      jTree1  = new JTree(tree);
      jbInit();
      pack();
    }catch(Exception ex){
      ex.printStackTrace();
    }
  }

  void jbInit() throws Exception {
    TreeRenderer = new DefaultTreeCellRenderer();
    jScrollPane1 = new JXMScrollPane(m_frame);
	TreeRenderer.setClosedIcon(iconClosedBranch);
    TreeRenderer.setOpenIcon(iconOpenBranch);
    TreeRenderer.setLeafIcon(iconLeaf);
    jMenu1.setText("File");
    jMenuItem1.setText("Print...");
    jMenuItem1.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        jMenuItem1_actionPerformed(e);
      }
    });
    jMenuItem2.setText("Close");
    jMenuItem2.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        jMenuItem2_actionPerformed(e);
      }
    });

    jTree1.setCellRenderer(TreeRenderer);
    panel1.setLayout(borderLayout1);
    jPanel2.setLayout(borderLayout2);

	jMenu1.add(jMenuItem1);
    jMenu1.addSeparator();
    jMenu1.add(jMenuItem2);
    jMenuBar1.add(jMenu1);
    jScrollPane1.getViewport().add(jTree1, null);
    jPanel2.add(jScrollPane1, BorderLayout.CENTER);
    panel1.add(jPanel2, BorderLayout.CENTER);
    panel1.add(jMenuBar1, BorderLayout.NORTH);
    this.getContentPane().add(panel1, BorderLayout.CENTER);
  }

  void jButton1_actionPerformed(ActionEvent e){
    this.dispose();
  }

  void jMenuItem1_actionPerformed(ActionEvent e){
    ((JXMScrollPane)jScrollPane1).Print();
  }

  void jMenuItem2_actionPerformed(ActionEvent e){
    dispose();
  }

  class JXMScrollPane extends JScrollPane{
    Frame m_frame;

    public JXMScrollPane(Frame frame)
    {
      super();
      m_frame = frame;
    }

	public void Print(){
      java.awt.Toolkit toolkit = this.getToolkit();
      java.awt.PrintJob job = toolkit.getPrintJob(m_frame, "XM-BRENIC/Miner V1.0", null);

      if(job == null) return;
      java.awt.Graphics g = job.getGraphics();
      g.translate(20, 20);
      java.awt.Dimension size = this.getSize();
      g.setClip(0, 0, size.width, size.height);

      this.print(g);

      g.dispose();
      job.end();
	}
  }
}