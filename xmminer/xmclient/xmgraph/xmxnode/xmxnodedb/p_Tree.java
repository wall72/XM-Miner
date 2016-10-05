package xmminer.xmclient.xmgraph.xmxnode.xmxnodedb;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.util.Vector;
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.awt.event.*;

public class p_Tree extends JPanel {
  BorderLayout borderLayout1 = new BorderLayout();
  JScrollPane jScrollPane1 = new JScrollPane();
  JTree jTree1;

  DBExt_Client_Module _DCM;

  boolean gb = true;

  DefaultMutableTreeNode tableName;
  DefaultMutableTreeNode DMT;
  DefaultMutableTreeNode DBList;
  DefaultMutableTreeNode columnName;

  String _tabName;

  Vector _vec = new Vector();
  Vector _vec2 = new Vector();
  XMDialogXNodeDB frame1;
  JLabel jLabel1 = new JLabel();
  JSplitPane jSplitPane1 = new JSplitPane();
  JScrollPane jScrollPane2 = new JScrollPane();
  JList jList1 = new JList();
  JPanel jPanel1 = new JPanel();
  JPanel jPanel2 = new JPanel();
  GridLayout gridLayout2 = new GridLayout();
  BorderLayout borderLayout2 = new BorderLayout();
  JLabel jLabel2 = new JLabel();

  public p_Tree(XMDialogXNodeDB f, DBExt_Client_Module dcm) {
    try{
      _DCM = dcm;
      frame1 = f;
      jbInit();
    }catch(Exception ex){
      ex.printStackTrace();
    }
  }

  public p_Tree(){
  }

  private void jbInit() throws Exception {
    DMT = new DefaultMutableTreeNode("Database Extract");
    String val = "net.sourceforge.jtds.jdbc.Driver";
    DBList = new  DefaultMutableTreeNode(val)  ;

    jLabel1.setBorder(BorderFactory.createLoweredBevelBorder());
    jLabel1.setText("  테이블 리스트");
    jSplitPane1.setOrientation(JSplitPane.VERTICAL_SPLIT);
    jSplitPane1.setMinimumSize(new Dimension(240, 64));
    jSplitPane1.setLastDividerLocation(300);
    jSplitPane1.setOneTouchExpandable(true);
    jPanel2.setLayout(borderLayout2);
    jPanel1.setLayout(gridLayout2);
    jLabel2.setMinimumSize(new Dimension(10, 10));
    jLabel2.setText("관련 테이블 및 컬럼 ");
    this.setBorder(BorderFactory.createRaisedBevelBorder());
    this.setMinimumSize(new Dimension(100, 100));
    this.setPreferredSize(new Dimension(350, 501));
    DMT.add(DBList);
    test(DBList);

    jTree1 = new JTree(DMT);

    this.setLayout(borderLayout1);
    this.add(jLabel1, BorderLayout.NORTH);
    this.add(jSplitPane1, BorderLayout.CENTER);
    jSplitPane1.add(jPanel2, JSplitPane.BOTTOM);
    jPanel2.add(jScrollPane2, BorderLayout.CENTER);
    jPanel2.add(jLabel2, BorderLayout.NORTH);
    jScrollPane2.getViewport().add(jList1, null);
    jSplitPane1.add(jPanel1, JSplitPane.TOP);
    jPanel1.add(jScrollPane1, null);
    jScrollPane1.getViewport().add(jTree1, null);

    jTree1.addMouseListener(new java.awt.event.MouseAdapter(){
        public void mouseClicked(MouseEvent e) {
            jTree1_mouseClicked(e);
        }
    });

    jTree1.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener(){
        public void valueChanged(TreeSelectionEvent e) {
            jTree1_valueChanged(e);
        }
    });
    jTree1.expandRow(1);
    jSplitPane1.setDividerLocation(300);
  }

  private void test(DefaultMutableTreeNode top) throws JavaException{
    String[] test;
    String val;
    test = null;
    test = _DCM.getTableNames();

    int k = test.length;
    int i;
    top.removeAllChildren();
    for( i = 0; k > i ; i++){
      val = test[i];
      tableName = new  DefaultMutableTreeNode(val)  ;
      top.add(tableName);
    }
  }

  void jTree1_valueChanged(TreeSelectionEvent e){
    Cursor oldCursor = frame1.getCursor();
    frame1.setCursor(new Cursor(Cursor.WAIT_CURSOR));
    tableName = (DefaultMutableTreeNode)jTree1.getLastSelectedPathComponent();
    int lev = tableName.getLevel();
    if(lev != 2) return;

    String tab = (String)tableName.getUserObject();
    frame1.startTableView(tab);
    frame1.setCursor(oldCursor);
  }

  void jTree1_mouseClicked(MouseEvent e){
    if(e.getClickCount() == 2) System.out.println("거긴 클릭하지 마세요!");
  }
}