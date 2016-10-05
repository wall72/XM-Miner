package xmminer.xmclient.xmgraph.xmxnode.xmxnodedb;

import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionListener;
import java.util.StringTokenizer ;
import java.util.Vector;
import java.util.Enumeration;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.tree.*;
import javax.swing.table.*;
import javax.swing.event.*;
import xmminer.xmclient.*;
import xmminer.xmclient.xmgraph.*;
import xmminer.xmclient.xmgraph.XMDialogGraphElement;
import xmminer.xmclient.xmgraph.xmxnode.xmxnodedb.*;
import xmminer.xmserver.xmgraph.xmxnode.xmxnodedb.XNodeDBException;

public class XMDialogXNodeDB extends JDialog {

  //start member variables declaration
  DBExt_Client_Module DCM;
  XMXNodeDB _XND;
  XMGraph m_graph;

  p_Tree pTree;
  DefaultMutableTreeNode tableName;

  p_Table pTable = new p_Table();
  p_Table pt = new p_Table();
  MyTableModel det = new MyTableModel();
  TableModel tb;
  TableModel tb2;

  String tabName;
  int rowSet = 1;
  int rowSet2 = 1;
  Vector _vec = new Vector();
  Vector _vec2 = new Vector();
  Vector _vec3 = new Vector();
  String m_sql = "";

  JFrame m_frame;
  Thread t; // For thread
  //end of member variables declaration

  JPanel panel1 = new JPanel();
  JPanel panel2 = new JPanel();
  JButton button_ok = new JButton();
  JButton button_cancel = new JButton();
  Border border1;
  JPanel jPanel1 = new JPanel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  TitledBorder titledBorder1;
  BorderLayout borderLayout1 = new BorderLayout();
  JSplitPane jSplitPane1 = new JSplitPane();
  JTabbedPane jTabbedPane1 = new JTabbedPane();
  JPanel jPanel_colList = new JPanel();
  JPanel jPanel_view = new JPanel();
  JPanel jPanel_sqlMaker = new JPanel();
  JPanel jPanel6 = new JPanel();
  BorderLayout borderLayout2 = new BorderLayout();
  JSplitPane jSplitPane2 = new JSplitPane();
  JPanel jPanel8 = new JPanel();
  BorderLayout borderLayout3 = new BorderLayout();
  JPanel jPanel9 = new JPanel();
  JButton jButton1 = new JButton();
  JButton jButton2 = new JButton();
  JButton jButton3 = new JButton();
  JPanel jPanel10 = new JPanel();
  JScrollPane jScrollPane1 = new JScrollPane();
  BorderLayout borderLayout4 = new BorderLayout();
  JList jList1 = new JList();
  BorderLayout borderLayout5 = new BorderLayout();
  JPanel jPanel2 = new JPanel();
  JButton jButton4 = new JButton();
  JButton jButton5 = new JButton();
  GridLayout gridLayout1 = new GridLayout();
  BorderLayout borderLayout6 = new BorderLayout();
  JPanel jPanel3 = new JPanel();
  BorderLayout borderLayout8 = new BorderLayout();
  JPanel jPanel7 = new JPanel();
  JScrollPane jScrollPane3 = new JScrollPane();
  JTextArea jTextArea1 = new JTextArea();
  FlowLayout flowLayout1 = new FlowLayout();
  BorderLayout borderLayout9 = new BorderLayout();
  //JButton jButton8 = new JButton();
  JPanel jPanel5 = new JPanel();
  JSplitPane jSplitPane4 = new JSplitPane();
  BorderLayout borderLayout10 = new BorderLayout();
  JScrollPane jScrollPane2 = new JScrollPane();
  JTextArea jTextArea2 = new JTextArea();
  BorderLayout borderLayout7 = new BorderLayout();
  JSplitPane jSplitPane3 = new JSplitPane();
  JScrollPane jScrollPane4 = new JScrollPane();
  JPanel jPanel12 = new JPanel();
  p_Table pt_2;
  JButton jButton7 = new JButton();
  JButton jButton6 = new JButton();
  BorderLayout borderLayout12 = new BorderLayout();
  JPanel jPanel4 = new JPanel();
  GridLayout gridLayout2 = new GridLayout();
  JTextArea jTextArea3 = new JTextArea();

  //Constructor
  public XMDialogXNodeDB(JFrame frame,  String title, boolean modal, DBExt_Client_Module dcm, XMXNodeDB xnd, XMGraph graph) {
    super(frame, title, modal);
    m_frame = frame;
    DCM = dcm;
    _XND = xnd;
    m_graph = graph;
    try{
      jbInit();
    }catch(Exception e){
      e.printStackTrace();
    }
    pack();
  }

  private void jbInit() throws Exception {
    pTree = DCM.tree_prievew(this);

    border1 = BorderFactory.createRaisedBevelBorder();
    titledBorder1 = new TitledBorder("");
    pt_2 = new p_Table();
    jPanel1.setLayout(gridBagLayout2);
    button_ok.setFont(new java.awt.Font("Dialog", 0, 12));
	button_ok.setBorder(border1);
    button_ok.setMinimumSize(new Dimension(90, 30));
    button_ok.setPreferredSize(new Dimension(90, 30));
    button_ok.setText("확인");
    button_ok.addActionListener(new DBExtractor_dialog_button_ok_actionAdapter(this));
    button_cancel.setFont(new java.awt.Font("Dialog", 0, 12));
	button_cancel.setBorder(border1);
    button_cancel.setMaximumSize(new Dimension(90, 30));
    button_cancel.setMinimumSize(new Dimension(90, 30));
    button_cancel.setPreferredSize(new Dimension(90, 30));
    button_cancel.setText("취소");
    button_cancel.addActionListener(new DBExtractor_dialog_button_cancel_actionAdapter(this));
    addWindowListener(new DBExtractor_dialog_this_windowAdapter(this));
    panel1.setLayout(gridBagLayout1);
    panel1.setMinimumSize(new Dimension(703, 22));
    panel1.setPreferredSize(new Dimension(700, 500));
    jPanel1.setPreferredSize(new Dimension(700, 50));
    panel2.setLayout(borderLayout1);
    jPanel_colList.setLayout(borderLayout2);
    jPanel8.setLayout(borderLayout3);
    jButton1.setFont(new java.awt.Font("Dialog", 0, 12));
	jButton1.setMaximumSize(new Dimension(100, 40));
    jButton1.setMinimumSize(new Dimension(100, 40));
    jButton1.setPreferredSize(new Dimension(100, 40));
    jButton1.setMargin(new Insets(2, 2, 2, 2));
    jButton1.setText("전체삭제");
    jButton1.addActionListener(new java.awt.event.ActionListener(){
      public void actionPerformed(ActionEvent e){
        jButton1_actionPerformed(e);
      }
    });
    jButton2.setFont(new java.awt.Font("Dialog", 0, 12));
	jButton2.setMaximumSize(new Dimension(100, 40));
    jButton2.setMinimumSize(new Dimension(100, 40));
    jButton2.setPreferredSize(new Dimension(100, 40));
    jButton2.setText("삭제");
    jButton2.addActionListener(new java.awt.event.ActionListener(){
      public void actionPerformed(ActionEvent e){
        jButton2_actionPerformed(e);
      }
    });
    jButton3.setFont(new java.awt.Font("Dialog", 0, 12));
	jButton3.setMaximumSize(new Dimension(100, 40));
    jButton3.setMinimumSize(new Dimension(100, 40));
    jButton3.setPreferredSize(new Dimension(100, 40));
    jButton3.setText("추가");
    jButton3.addActionListener(new java.awt.event.ActionListener(){
      public void actionPerformed(ActionEvent e){
        jButton3_actionPerformed(e);
      }
    });
    jPanel10.setLayout(borderLayout4);
    jSplitPane1.setMinimumSize(new Dimension(223, 89));
    jSplitPane1.setLastDividerLocation(3);
    jSplitPane1.setOneTouchExpandable(true);
    jPanel_view.setLayout(borderLayout5);
    jPanel2.setMinimumSize(new Dimension(24, 28));
    jPanel2.setPreferredSize(new Dimension(20, 20));
    jPanel2.setLayout(gridLayout1);
    jButton4.setFont(new java.awt.Font("Dialog", 0, 12));
	jButton4.setText("jButton4");
    jButton4.addActionListener(new java.awt.event.ActionListener(){
      public void actionPerformed(ActionEvent e){
        jButton4_actionPerformed(e);
      }
    });
    jButton5.setFont(new java.awt.Font("Dialog", 0, 12));
	jButton5.setText("jButton5");
    jButton5.addActionListener(new java.awt.event.ActionListener(){
      public void actionPerformed(ActionEvent e) {
        jButton5_actionPerformed(e);
      }
    });
    gridLayout1.setRows(2);
    jPanel_sqlMaker.setLayout(borderLayout7);
    pt.setBorder(BorderFactory.createLoweredBevelBorder());
    jSplitPane2.setOrientation(JSplitPane.VERTICAL_SPLIT);
    jSplitPane2.setMinimumSize(new Dimension(24, 28));
    jSplitPane2.setOneTouchExpandable(true);
    jPanel3.setLayout(borderLayout8);
    jTextArea1.setEditable(false);
    jPanel7.setLayout(flowLayout1);
    flowLayout1.setAlignment(FlowLayout.RIGHT);
    jTabbedPane1.addAncestorListener(new javax.swing.event.AncestorListener(){
      public void ancestorAdded(AncestorEvent e){
        jTabbedPane1_ancestorAdded(e);
      }
      public void ancestorMoved(AncestorEvent e){}
      public void ancestorRemoved(AncestorEvent e){}
    });
    jPanel_sqlMaker.addAncestorListener(new javax.swing.event.AncestorListener(){
      public void ancestorAdded(AncestorEvent e){
        jPanel_sqlMaker_ancestorAdded(e);
      }
      public void ancestorMoved(AncestorEvent e){}
        public void ancestorRemoved(AncestorEvent e){}
    });
    jPanel6.setLayout(borderLayout9);
    jPanel8.setBorder(BorderFactory.createLoweredBevelBorder());
    jPanel8.setMinimumSize(new Dimension(24, 28));
    jTabbedPane1.setFont(new java.awt.Font("Dialog", 0, 12));
	jTabbedPane1.setMinimumSize(new Dimension(24, 28));
    jPanel9.setMinimumSize(new Dimension(24, 28));
    jPanel_view.setMinimumSize(new Dimension(24, 28));
    jPanel_sqlMaker.setMinimumSize(new Dimension(24, 28));
    jPanel3.setMinimumSize(new Dimension(24, 28));
    jPanel7.setMinimumSize(new Dimension(24, 28));
    jPanel6.setMinimumSize(new Dimension(24, 28));
    jPanel6.addAncestorListener(new javax.swing.event.AncestorListener(){
      public void ancestorAdded(AncestorEvent e){
        jPanel6_ancestorAdded(e);
      }
      public void ancestorMoved(AncestorEvent e){}
      public void ancestorRemoved(AncestorEvent e){}
    });
    //jButton8.setFont(new java.awt.Font("Dialog", 0, 12));
	//jButton8.setMaximumSize(new Dimension(90, 30));
    //jButton8.setMinimumSize(new Dimension(90, 30));
    //jButton8.setPreferredSize(new Dimension(90, 30));
    //jButton8.setText("수정");
    //jButton8.addActionListener(new java.awt.event.ActionListener(){
    //   public void actionPerformed(ActionEvent e){
    //    jButton8_actionPerformed(e);
    //  }
    //});
    jPanel5.setLayout(borderLayout10);
    jSplitPane4.setOrientation(JSplitPane.VERTICAL_SPLIT);
    jTextArea2.setEditable(false);
    jSplitPane3.setOrientation(JSplitPane.VERTICAL_SPLIT);
    jPanel12.setLayout(borderLayout12);
    jButton7.setFont(new java.awt.Font("Dialog", 0, 12));
	jButton7.setText("jButton7");
    jButton7.addActionListener(new java.awt.event.ActionListener(){
      public void actionPerformed(ActionEvent e){
        jButton7_actionPerformed(e);
      }
    });
    jButton6.setFont(new java.awt.Font("Dialog", 0, 12));
	jButton6.setText("jButton6");
    jButton6.addActionListener(new java.awt.event.ActionListener(){
      public void actionPerformed(ActionEvent e){
        jButton6_actionPerformed(e);
      }
    });
    jPanel4.setLayout(gridLayout2);
    jPanel4.setPreferredSize(new Dimension(20, 20));
    gridLayout2.setRows(2);
    jTextArea3.setText("jTextArea3");
    jTextArea3.setEditable(false);
    panel1.add(panel2, new GridBagConstraints(0, 0, -1000, -1000, 1.0, 1.0,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 10, 10, 10), 0, 0));
    panel2.add(jSplitPane1, BorderLayout.CENTER);
    jSplitPane1.add(pTree, JSplitPane.LEFT);
    jSplitPane1.add(jTabbedPane1, JSplitPane.RIGHT);
    jTabbedPane1.add(jPanel_colList, "컬럼 리스트");
    jPanel_colList.add(jSplitPane2, BorderLayout.CENTER);
    jSplitPane2.add(pTable, JSplitPane.LEFT);
    jSplitPane2.add(jPanel8, JSplitPane.RIGHT);
    jPanel8.add(jPanel9, BorderLayout.NORTH);
    jPanel9.add(jButton3, null);
    jPanel9.add(jButton2, null);
    jPanel9.add(jButton1, null);
    jPanel8.add(jPanel10, BorderLayout.CENTER);
    jPanel10.add(jScrollPane1, BorderLayout.CENTER);
    jScrollPane1.getViewport().add(jList1, null);
    jTabbedPane1.add(jPanel_view, "데이터 뷰");
    jPanel_view.add(jPanel2, BorderLayout.EAST);
    jPanel2.add(jButton5, null);
    jPanel2.add(jButton4, null);
    jPanel_view.add(pt, BorderLayout.CENTER);
    jTabbedPane1.add(jPanel_sqlMaker, "SQL문 보기");
    jPanel_sqlMaker.add(jPanel3, BorderLayout.CENTER);
    jPanel3.add(jPanel7, BorderLayout.NORTH);
    //jPanel7.add(jButton8, null);
    jPanel3.add(jPanel5, BorderLayout.CENTER);
    jPanel5.add(jSplitPane4, BorderLayout.CENTER);
    jSplitPane4.add(jScrollPane3, JSplitPane.TOP);
    jSplitPane4.add(jScrollPane2, JSplitPane.BOTTOM);
    jScrollPane2.getViewport().add(jTextArea2, null);
    jScrollPane3.getViewport().add(jTextArea1, null);
    jTabbedPane1.add(jPanel6, "미리보기");
    jPanel6.add(jSplitPane3, BorderLayout.CENTER);
    jSplitPane3.add(jScrollPane4, JSplitPane.BOTTOM);
    jScrollPane4.getViewport().add(jTextArea3, null);
    jSplitPane3.add(jPanel12, JSplitPane.TOP);
    jPanel12.add(jPanel4, BorderLayout.EAST);
    jPanel4.add(jButton6, null);
    jPanel4.add(jButton7, null);
    jPanel12.add(pt_2, BorderLayout.CENTER);
    panel1.add(jPanel1, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 8, 4, 0), 0, 0));
    jPanel1.add(button_ok, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 490, 20, 0), 0, 0));
    jPanel1.add(button_cancel, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(0, 5, 20, 20), 0, 0));
    getContentPane().add(panel1);
    jSplitPane1.setDividerLocation(200);
    jSplitPane2.setDividerLocation(100);

    String[] col  = DCM.XnodeDBMetaDataReads("colList", _XND.m_projectname);
    String[] type  = DCM.XnodeDBMetaDataReads("colType", _XND.m_projectname);
    if(col[0].compareTo("nonenull") != 0) {
        _vec = str2Vec(col);
        _vec3 = str2Vec(type);
        jList1.setListData(_vec);
    }

    String[] tab  = DCM.XnodeDBMetaDataReads("tabList", _XND.m_projectname);
    if(tab[0].compareTo("nonenull") != 0 ) _vec2 = str2Vec(tab);

    String row  = DCM.XnodeDBMetaDataRead("treeNode", _XND.m_projectname);
    if(row != "") row = "2";
    pTree.jTree1.setSelectionRow(str2Int(row));
    jSplitPane4.setDividerLocation(400);

    if(_vec.size() > 0) {
      m_sql = DCM.sqlGen(_vec, _vec2);
    }
  }

  void button_ok_actionPerformed(ActionEvent e){
    System.out.println("#[TRACE MSG: XMXNodeDB] Saving Meta files...");

    if(_vec.size() > 0){
// ★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
// ★ 2000.10.22 이창호 수정
      DCM.XnodeDBMetaDataSave("colList",vec2Str(_vec), _XND.m_projectname);
      DCM.XnodeDBMetaDataSave("colnum",int2Str(_vec.size()), _XND.m_projectname);
      DCM.XnodeDBMetaDataSave("colType",vec2Str(_vec3), _XND.m_projectname);
      String sql = "";
      if(DCM.XnodeDBMetaDataRead("DB_info", _XND.m_projectname).compareTo("oracle.jdbc.driver.OracleDriver") == 0)
         sql = DCM.ora_realSqlGen(_vec, _vec2);
      else sql = DCM.ms_realSqlGen(_vec, _vec2);
      DCM.XnodeDBMetaDataSave("sql",sql, _XND.m_projectname);
      DCM.MetaDataCreate(vec2Str(_vec), vec2Str(_vec3), _XND.m_index, _XND.m_projectname);
    }else{
      _vec.removeAllElements();
      _vec2.removeAllElements();
      _vec3.removeAllElements();
      DCM.XnodeDBMetaDataSave("colList", "nonenull", _XND.m_projectname);
      DCM.XnodeDBMetaDataSave("colnum","nonenull", _XND.m_projectname);
      DCM.XnodeDBMetaDataSave("colType","nonenull", _XND.m_projectname);
      DCM.XnodeDBMetaDataSave("sql","nonenull", _XND.m_projectname);
    }
    if(_vec2.size() > 0) DCM.XnodeDBMetaDataSave("tabList",vec2Str(_vec2), _XND.m_projectname);
    else DCM.XnodeDBMetaDataSave("tabList", "nonenull", _XND.m_projectname);

    int[] treeRow = pTree.jTree1.getSelectionRows();
    DCM.XnodeDBMetaDataSave("treeNode",int2Str(treeRow[0]), _XND.m_projectname);

	_XND.SetChanged(true);
    dispose();
  }

  public boolean SetSchema(){
    if(_vec.size() > 0){
      DCM.XnodeDBMetaDataSave("colList",vec2Str(_vec), _XND.m_projectname);
      DCM.XnodeDBMetaDataSave("colnum",int2Str(_vec.size()), _XND.m_projectname);
      DCM.XnodeDBMetaDataSave("colType",vec2Str(_vec3), _XND.m_projectname);
      String sql = "";
      if(DCM.XnodeDBMetaDataRead("DB_info", _XND.m_projectname).compareTo("oracle.jdbc.driver.OracleDriver") == 0) sql = DCM.ora_realSqlGen(_vec, _vec2);
      else sql = DCM.ms_realSqlGen(_vec, _vec2);
        DCM.XnodeDBMetaDataSave("sql",sql, _XND.m_projectname);
        DCM.MetaDataCreate(vec2Str(_vec), vec2Str(_vec3), _XND.m_index, _XND.m_projectname);
    }else{
      _vec.removeAllElements();
      _vec2.removeAllElements();
      _vec3.removeAllElements();
      DCM.XnodeDBMetaDataSave("colList", "nonenull", _XND.m_projectname);
      DCM.XnodeDBMetaDataSave("colnum","nonenull", _XND.m_projectname);
      DCM.XnodeDBMetaDataSave("colType","nonenull", _XND.m_projectname);
      DCM.XnodeDBMetaDataSave("sql","nonenull", _XND.m_projectname);
    }

    if(_vec2.size() > 0) DCM.XnodeDBMetaDataSave("tabList",vec2Str(_vec2), _XND.m_projectname);
    else DCM.XnodeDBMetaDataSave("tabList", "nonenull", _XND.m_projectname);

    int[] treeRow = pTree.jTree1.getSelectionRows();
    DCM.XnodeDBMetaDataSave("treeNode",int2Str(treeRow[0]), _XND.m_projectname);
    return true;
  }

  public String int2Str(int i){
    Integer ii = new Integer(i);
    return ii.toString();
  }

  public int str2Int(String str){
    Integer i = new Integer(str);
    return i.intValue();
  }

  private Vector str2Vec(String input[]){
    Vector v = new Vector(3,2);
    int size = input.length;
    for (int i=0; i<size; i++) v.addElement(input[i]);
    return v;
  }

  private String[] vec2Str(Vector input)    {
    int size = input.size();
    String s[] = new String[size];
    for (int i=0; i<size; i++) s[i] = (String)input.elementAt(i);
    return s;
  }

  // Cancel
  void button_cancel_actionPerformed(ActionEvent e) {
    DCM.dbDisConnect();
	_XND.SetChanged(false);
    dispose();
  }

  void this_windowClosing(WindowEvent e) {
    DCM.dbDisConnect();
    dispose();
  }

  public void startTableView(String tab_name) {
    tb = new TableModel();
    String[] test;
    test = null;
    tabName = tab_name;
    test = DCM.getColumnAll(tabName);
    String val = "";
    int k = test.length;
    Object[][] str2Arr = new Object[k][5];

    for(int i = 0; i < k; i++){
      val = test[i];
      str2Arr = returnIndexOfString(val,str2Arr, i);
    }

    String[] cn = {"","Column Name","Type","Length","Null"};
    det.setHeaderList(cn,str2Arr);

    pTable.jTable1.tableChanged(new TableModelEvent(det));
    pTable.jTable1.setModel(det);
    pTable.setColumnSize();

    rowSet = 1;
    tableFirstPreview();
    String a[] = DCM.getFKTable(tabName);
    pTree.jList1.setListData(a);
  }

  private Object[][] returnIndexOfString(String arg, Object[][] arr, int num){
    try{
      StringTokenizer st = new StringTokenizer(arg,",");
      arr[num][0] = new Boolean(true);
      for(int i = 1; i < 5 ; i++){
        String val = st.nextToken();
        arr[num][i] = val;
      }
      return arr;
    }catch(Exception e){
      System.out.print("IndexOfString : ");
      System.out.println(e.getMessage());
      return arr;
    }
  }

  private void tableDynamicPreview(){
    tb = new TableModel();
    String[] tabList = DCM.getColumnList(tabName);
    String rowNum = "";
    rowNum = int2Str(rowSet+20);
    Object[][] obj = DCM.sqlGenView(rowNum ,tabName, rowSet);
    String strObj = (String)obj[0][0];
    if(strObj.compareTo("none") == 0) {
      String a[] = {"data"};
      tb.setHeaderList(a,obj);
      pt.jTable1.tableChanged(new TableModelEvent(tb));
      pt.jTable1.setModel(tb);
    }else if(strObj.compareTo("NONE") != 0) {
      tb.setHeaderList(tabList,obj);
      pt.jTable1.tableChanged(new TableModelEvent(tb));
      pt.jTable1.setModel(tb);
    }else rowSet = rowSet - 20;
  }

  private void tableDynamicPreview2()  throws XNodeDBException {
    String[] colList = null;
    String[] tabList = null;

    if(_vec.size() > 0) {
      colList = vec2Str(_vec);
      tabList = vec2Str(_vec2);
    }else return;
    tb2 = new TableModel();
    String rowNum = "";
    rowNum = int2Str(rowSet2+20);
    Object[][] obj = DCM.sqlGenPreview(colList, tabList, m_sql, rowSet2);
    String strObj = (String)obj[0][0];
    if(strObj.compareTo("none") == 0){
      String a[] = {"data"};
      tb2.setHeaderList(a,obj);
      pt_2.jTable1.tableChanged(new TableModelEvent(tb2));
      pt_2.jTable1.setModel(tb2);
    }else if(strObj.compareTo("NONE") != 0){
      tb2.setHeaderList(colList,obj);
      pt_2.jTable1.tableChanged(new TableModelEvent(tb2));
      pt_2.jTable1.setModel(tb2);
    }
  }

  private void tableFirstPreview(){
    tb = new TableModel();
    String[] tabList = DCM.getColumnList(tabName);
    Object[][] obj = DCM.sqlQuery(tabName);

    String strObj = (String)obj[0][0];
    if(strObj.compareTo("none") == 0){
      String a[] = {"data"};
      tb.setHeaderList(a,obj);
      pt.jTable1.tableChanged(new TableModelEvent(tb));
      pt.jTable1.setModel(tb);
    }else if(strObj.compareTo("NONE") != 0){
      tb.setHeaderList(tabList,obj);
      pt.jTable1.tableChanged(new TableModelEvent(tb));
      pt.jTable1.setModel(tb);
    }
  }

  void jButton3_actionPerformed(ActionEvent e){
    int rowCount = pTable.jTable1.getRowCount();

    for (int i = 0; i < rowCount; i++){
      Boolean r = (Boolean)det.getValueAt(i, 0);
      boolean b = r.booleanValue();

      if(b){
        String colName = (String)det.getValueAt(i, 1);
        String colType = (String)det.getValueAt(i, 2);
        colName = tabName+"."+colName ;

        if(equalStr(tabName, _vec2) == false){
          _vec2.addElement(tabName);
        }

        if(equalStr(colName, _vec) == false){
          _vec.addElement(colName);
          _vec3.addElement(colType);
        }

      }
    }
    jList1.setListData(_vec);

    if(_vec.size() > 0) {
      m_sql = DCM.sqlGen(_vec, _vec2);
    }
  }

  private void equalStrRemove(String val){
    try{
      Enumeration vEum = _vec.elements();
      String valData = "";
      int i = 0;
      while(vEum.hasMoreElements()) {
        valData = (String)vEum.nextElement();
        if(val.compareTo(valData) == 0) {
          _vec.remove(valData);
          _vec3.remove(i);
        }
        i++;
      }
    }catch(Exception e){
      System.out.println(e.getMessage());
    }
  }

  private boolean equalStr(String val, Vector v){
    try{
      Enumeration vEum = v.elements();
      String valData = "";
      int i = 0;
      while(vEum.hasMoreElements()){
        valData = (String)vEum.nextElement();
        if(val.compareTo(valData) == 0) return true;
      }
      return false;
    }catch(Exception e){
      System.out.println(e.getMessage());
    }
    return false;
  }

  void jButton2_actionPerformed(ActionEvent e){
    String col_name;
    String tab_name = "";
    String col;
    int chk = 0;
    if((col_name = (String)jList1.getSelectedValue()) != null){
      tab_name = col_name.substring(0,col_name.indexOf(".",0));
      int size = _vec.size();
      for (int i=0; i<size; i++){
        col= (String)_vec.elementAt(i);
        if(col_name == col){
          _vec.removeElementAt(i);
          _vec3.removeElementAt(i);
          i = i - 1;
          size = size - 1;
        }
        if(tab_name.compareTo(col.substring(0,col.indexOf(".",0))) == 0) chk = chk+1;
      }
    }

    int size2 = _vec2.size();

    if(chk < 2){
      for (int j=0; j<size2; j++) {
        if(tab_name.compareTo((String)_vec2.elementAt(j)) == 0){
          _vec2.removeElementAt(j);
          break;
        }
      }
    }

    jList1.setListData(_vec);
  }

  void jButton1_actionPerformed(ActionEvent e){
    _vec.removeAllElements();
    _vec3.removeAllElements();
    _vec2.removeAllElements();
    jList1.setListData(_vec2);
  }

  void jButton5_actionPerformed(ActionEvent e){
    if(rowSet < 21) return;
    rowSet = rowSet-20;
    tableDynamicPreview();
  }

  void jButton4_actionPerformed(ActionEvent e){
    rowSet = rowSet+20;
    tableDynamicPreview();
  }

  void jTabbedPane1_ancestorAdded(AncestorEvent e){ }

  void jPanel_sqlMaker_ancestorAdded(AncestorEvent e) {
    Cursor oldCursor = getCursor();
    setCursor(new Cursor(Cursor.WAIT_CURSOR));
    jTextArea1.setText(m_sql);
    jSplitPane4.setDividerLocation(jSplitPane4.getWidth());
    setCursor(oldCursor);
  }

  void jList2_valueChanged(ListSelectionEvent e){}

  void jButton6_actionPerformed(ActionEvent e){
    if(rowSet2 < 21) return;
    rowSet2 = rowSet2-20;
    try{
      tableDynamicPreview2();
    } catch(XNodeDBException xe){
    }
  }

  void jButton7_actionPerformed(ActionEvent e) {
    rowSet2 = rowSet2+20;
    try{
      tableDynamicPreview2() ;
    }catch(XNodeDBException xe){
    }
  }

  //void jButton8_actionPerformed(ActionEvent e){
  //  jTextArea1.setEditable(true);
  //}

  void jPanel6_ancestorAdded(AncestorEvent e)
  {
    Cursor oldCursor = getCursor();
    setCursor(new Cursor(Cursor.WAIT_CURSOR));
    rowSet2 = 1;
    try{
      jTextArea3.setText("");
      tableDynamicPreview2() ;
      jSplitPane3.setDividerLocation(jSplitPane3.getWidth());
    }catch(XNodeDBException xe){
      jSplitPane3.setDividerLocation(300);
      jTextArea2.setText(xe.message);
    }
    setCursor(oldCursor);
  }

}//end of Class XMDialogXNodeDB

class DBExtractor_dialog_button_ok_actionAdapter implements ActionListener {
  XMDialogXNodeDB adaptee;

  DBExtractor_dialog_button_ok_actionAdapter(XMDialogXNodeDB adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.button_ok_actionPerformed(e);
  }
}

class DBExtractor_dialog_button_cancel_actionAdapter implements ActionListener {
  XMDialogXNodeDB adaptee;

  DBExtractor_dialog_button_cancel_actionAdapter(XMDialogXNodeDB adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.button_cancel_actionPerformed(e);
  }
}

class DBExtractor_dialog_this_windowAdapter extends WindowAdapter {
  XMDialogXNodeDB adaptee;

  DBExtractor_dialog_this_windowAdapter(XMDialogXNodeDB adaptee) {
    this.adaptee = adaptee;
  }

  public void windowClosing(WindowEvent e) {
    adaptee.this_windowClosing(e);
  }
}

class MyTableModel extends TableModel{
  public Class getColumnClass(int c){
    return getValueAt(0,c).getClass();
  }
  public boolean isCellEditable(int ro, int col){
    if (col > 0) return false;
    return true;
  }
}