package xmminer.xmclient.xmgraph.xmmnode.xmmnodeassociationrule;

import java.awt.*;
import javax.swing.*;
import java.io.*;
import javax.swing.border.*;
import javax.swing.table.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.util.StringTokenizer;
import java.util.Vector;
import java.lang.Integer;

public class assoInputDialog extends JDialog {
  JPanel panel1 = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel1 = new JPanel();
  JPanel jPanel3 = new JPanel();
  JPanel jPanel5 = new JPanel();
  ImageIcon imageBanner = new ImageIcon(xmminer.xmclient.xmgraph.xmmnode.xmmnodeassociationrule.assoInputDialog.class.getResource("../../../images/asso_banner.jpg"));
  JLabel jLabel1 = new JLabel(imageBanner);
  JTabbedPane jTabbedPane1 = new JTabbedPane();
  BorderLayout borderLayout2 = new BorderLayout();
  JPanel jPanel6 = new JPanel();
  JPanel jPanel7 = new JPanel();
  JPanel jPanel8 = new JPanel();
  BorderLayout borderLayout3 = new BorderLayout();
  JPanel jPanel9 = new JPanel();
  JLabel jLabel2 = new JLabel();
  JTextField jTextField1 = new JTextField();
  JPanel jPanel10 = new JPanel();
  JButton jButton1 = new JButton();
  JButton jButton2 = new JButton();
  TitledBorder titledBorder1;
  TitledBorder titledBorder2;
  TitledBorder titledBorder3;
  TitledBorder titledBorder4;
  JLabel jLabel4 = new JLabel();
  JTextField jTextField2 = new JTextField();
  JComboBox jComboBox1 = new JComboBox();
  JLabel jLabel5 = new JLabel();
  JTextField jTextField3 = new JTextField();
  JComboBox jComboBox2 = new JComboBox();
  JPanel jPanel11 = new JPanel();
  JPanel jPanel12 = new JPanel();
  TitledBorder titledBorder5;
  JPanel jPanel13 = new JPanel();
  JLabel jLabel6 = new JLabel();
  JLabel jLabel7 = new JLabel();
  JLabel jLabel8 = new JLabel();
  JLabel jLabel9 = new JLabel();
  JRadioButton jRadioButton1 = new JRadioButton();
  JRadioButton jRadioButton2 = new JRadioButton();
  FlowLayout flowLayout2 = new FlowLayout();
  JPanel jPanel14 = new JPanel();
  JPanel jPanel15 = new JPanel();
  JPanel jPanel16 = new JPanel();
  JButton jButton3 = new JButton();
  JButton jButton4 = new JButton();
  JButton jButton5 = new JButton();
  JButton jButton6 = new JButton();
  JButton jButton7 = new JButton();
  JLabel jLabel3 = new JLabel();
  JTextField jTextField4 = new JTextField();
  JLabel jLabel10 = new JLabel();
  JTextField jTextField5 = new JTextField();
  JScrollPane jScrollPane1 = new JScrollPane();
  JTable jTable1 = new JTable();   //table data input

  String modelName;     //모델명
  String projectName;   //project file name
  String metaFile;      //meta file name
  String projectpath;

  boolean bSaved = false;

  int num_instances;  //DB의 row수  //getAttributesList()에서 임시로 초기화
  int num_attributes; //DB의 Attributes수 //getAttributesList()에서 초기화
  int transNumber;    //transaction 수    //paramSetting()에서 num_instances로 초기화

  int imsi_transNumber = -1;  //dbType =2 일때의 트랜잭션 수를 기억한다.

  float min_sup = 0f;
  float min_conf = 0f;
  int min_sup_type;   //min_conf_type(0:"개", 1:"%")
  int dbType = 1;

  assoTableModel assoModel;  // = new assoTableModel();  //table model

  String selAttributes = new String();   //table에서 선택된 Attributes(column name을 ^로 구분)
  int num_selAttributes = 0;  //table에서 선택된 Attributes 수

  String transIDFld, targetFld;  //dbType =2일 경우의 필드들..

  XMMNodeAssociationRule m_xmmassociation;

  JLabel jLabel11 = new JLabel();
  JLabel jLabel12 = new JLabel();
  JPanel jPanel17 = new JPanel();
  JLabel jLabel13 = new JLabel();
  JLabel jLabel14 = new JLabel();
  FlowLayout flowLayout4 = new FlowLayout();
  Border border1;
  BorderLayout borderLayout4 = new BorderLayout();
  BorderLayout borderLayout5 = new BorderLayout();

  //constructor
  public assoInputDialog(Frame frame, String title, boolean modal, XMMNodeAssociationRule xmmnode) {

    super(frame, title, modal);
    m_xmmassociation = xmmnode;

    bSaved = m_xmmassociation.bSaved;

    projectName = m_xmmassociation.ProjectName;
    metaFile = m_xmmassociation.MetaFileName;
    modelName = m_xmmassociation.model;

    transNumber = m_xmmassociation.transNumber;    //transaction 수

    min_sup = m_xmmassociation.min_sup;
    min_conf = m_xmmassociation.min_conf;
    min_sup_type = m_xmmassociation.min_sup_type;
    dbType = m_xmmassociation.dbType;

	if( dbType == 1){
	  selAttributes = m_xmmassociation.selAttributes;   //table에서 선택된 Attributes(column name을 ^로 구분)
	}else{
      imsi_transNumber = m_xmmassociation.transNumber;
	  transIDFld = m_xmmassociation.transIDFld;
	  targetFld = m_xmmassociation.targetFld;  //dbType =2일 경우의 필드들..
	}

    try{
      jbInit();
      tableSetting();   //table setting
	  setParameter();
	  SwingUtilities.updateComponentTreeUI(this);
      pack();
    }catch(Exception ex){
      ex.printStackTrace();
    }
  }

  public assoInputDialog(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try{
      jbInit();
      pack();
    }catch(Exception ex){
      ex.printStackTrace();
    }
  }

  public assoInputDialog() {
    this(null, "", false);
  }

  void jbInit() throws Exception {
    titledBorder1 = new TitledBorder(new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(142, 142, 142)),"데이타베이스 타입 선택");
    titledBorder2 = new TitledBorder(new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(142, 142, 142)),"데이타베이스 정보");
    titledBorder3 = new TitledBorder(new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(142, 142, 142)),"필드 선택");
    titledBorder4 = new TitledBorder(new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(142, 142, 142)),"변수");
    titledBorder5 = new TitledBorder(new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(142, 142, 142)),"트랜잭션 정보");
    border1 = BorderFactory.createBevelBorder(BevelBorder.RAISED,Color.white,Color.white,new Color(142, 142, 142),new Color(99, 99, 99));
    panel1.setLayout(borderLayout1);
    jLabel1.setFont(new java.awt.Font("Dialog", 0, 18));
    jLabel1.setBorder(BorderFactory.createEtchedBorder());
    jLabel1.setText("");
    jPanel5.setLayout(borderLayout4);
    jPanel3.setLayout(borderLayout2);
    jPanel6.setLayout(borderLayout3);
    jLabel2.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel2.setPreferredSize(new Dimension(70, 30));
    jLabel2.setText("모델명");
    jTextField1.setPreferredSize(new Dimension(120, 22));
    jTextField1.setText("model01");
    jTextField1.addActionListener(new java.awt.event.ActionListener(){
      public void actionPerformed(ActionEvent e) {
        jTextField1_actionPerformed(e);
      }
    });
    jPanel9.setLayout(borderLayout5);
    jPanel10.setBorder(titledBorder4);
    jPanel10.setPreferredSize(new Dimension(480, 130));
    jButton1.setFont(new java.awt.Font("Dialog", 0, 12));
    jButton1.setBorder(BorderFactory.createRaisedBevelBorder());
    jButton1.setPreferredSize(new Dimension(90, 29));
    jButton1.setText("확인");
    jButton1.addActionListener(new java.awt.event.ActionListener(){
      public void actionPerformed(ActionEvent e) {
        jButton1_actionPerformed(e);
      }
    });
    jButton2.setFont(new java.awt.Font("Dialog", 0, 12));
    jButton2.setBorder(border1);
    jButton2.setPreferredSize(new Dimension(90, 29));
    jButton2.setText("취소");
    jButton2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton2_actionPerformed(e);
      }
    });
    jLabel4.setText("Minimum Support                          ");
    jTextField2.setPreferredSize(new Dimension(100, 22));
    jTextField2.setText("20");
    jTextField2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jTextField2_actionPerformed(e);
      }
    });
    jComboBox1.setPreferredSize(new Dimension(70, 27));
    String items[] ={"개","%"};
    for (int i = 0; i < 2; i++) jComboBox1.addItem(items[i]);
    jComboBox1.setSelectedItem("개");
    jComboBox1.setEditable(false);

    jLabel5.setText("Minimum Confidence                    ");
    jTextField3.setPreferredSize(new Dimension(100, 22));
    jTextField3.setText("60");
    jTextField3.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jTextField3_actionPerformed(e);
      }
    });
    jComboBox2.setPreferredSize(new Dimension(70, 27));
    jComboBox2.addItem("%");
    jComboBox2.setSelectedItem("%");
    jComboBox2.setEditable(false);

    jPanel11.setBorder(titledBorder1);
    jPanel11.setMinimumSize(new Dimension(500, 64));
    jPanel11.setPreferredSize(new Dimension(260, 100));
    jPanel12.setBorder(titledBorder2);
    jPanel12.setPreferredSize(new Dimension(203, 100));
    jPanel13.setBorder(titledBorder3);
	jPanel13.setPreferredSize(new Dimension(470, 175));
    jPanel13.setLayout(flowLayout2);
    jLabel6.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel6.setPreferredSize(new Dimension(81, 18));
    jLabel6.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel6.setText("인스턴스 수 :");
    jLabel7.setPreferredSize(new Dimension(70, 18));
    jLabel8.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel8.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel8.setText("어트리뷰트 수 :");
    jLabel9.setPreferredSize(new Dimension(70, 18));
    jRadioButton1.setPreferredSize(new Dimension(200, 26));
    jRadioButton1.setSelected(true);
    jButton6.setEnabled(false);   //">" button
    jButton7.setEnabled(false);   //">" button
    jTextField4.setEnabled(false);  //transIDFld
    jTextField5.setEnabled(false);  //targetFld

    jRadioButton1.setText("행을 기준으로 이루어진 트랜잭션");
    jRadioButton1.setFont(new java.awt.Font("Dialog", 0, 12));
    jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jRadioButton1_actionPerformed(e);
      }
    });
    jRadioButton2.setPreferredSize(new Dimension(200, 26));
    jRadioButton2.setText("열을 기준으로 이루어진 트랜잭션");
    jRadioButton2.setFont(new java.awt.Font("Dialog", 0, 12));
    jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jRadioButton2_actionPerformed(e);
      }
    });
    jPanel14.setPreferredSize(new Dimension(220, 140));
    jPanel15.setPreferredSize(new Dimension(45, 80));
    jPanel16.setPreferredSize(new Dimension(160, 80));

    jButton3.setPreferredSize(new Dimension(59, 29));
    jButton3.setText("All");
    jButton3.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton3_actionPerformed(e);
      }
    });
    jButton4.setPreferredSize(new Dimension(59, 29));
    jButton4.setMargin(new Insets(2, 10, 2, 10));
    jButton4.setText("None");
    jButton4.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton4_actionPerformed(e);
      }
    });
    jButton5.setMargin(new Insets(2, 10, 2, 10));
    jButton5.setText("Invert");
    jButton5.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton5_actionPerformed(e);
      }
    });
    jButton6.setPreferredSize(new Dimension(33, 22));
    jButton6.setMargin(new Insets(2, 10, 2, 10));
    jButton6.setText(">");
    jButton6.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton6_actionPerformed(e);
      }
    });
    jButton7.setPreferredSize(new Dimension(33, 22));
    jButton7.setMargin(new Insets(2, 10, 2, 10));
    jButton7.setText(">");
    jButton7.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton7_actionPerformed(e);
      }
    });
    jLabel3.setFont(new java.awt.Font("Dialog", 0, 12));
	jLabel3.setPreferredSize(new Dimension(35, 18));
    jLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel3.setText("TID");
    jTextField4.setPreferredSize(new Dimension(100, 22));
    jLabel10.setFont(new java.awt.Font("Dialog", 0, 12));
	jLabel10.setPreferredSize(new Dimension(35, 18));
    jLabel10.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel10.setText("Items");
    jTextField5.setPreferredSize(new Dimension(100, 22));
    jScrollPane1.setPreferredSize(new Dimension(210, 100));

    jLabel11.setFont(new java.awt.Font("Dialog", 0, 12));
	jLabel11.setPreferredSize(new Dimension(81, 18));
    jLabel11.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel11.setText("트랜잭션 수 :");
    jLabel12.setPreferredSize(new Dimension(70, 18));
    jPanel17.setBorder(titledBorder5);
    jPanel17.setPreferredSize(new Dimension(480, 90));
    jLabel13.setFont(new java.awt.Font("Dialog", 0, 12));
	jLabel13.setText("트랙잭션 수 :");
    jLabel14.setPreferredSize(new Dimension(70, 18));
    jTabbedPane1.setFont(new java.awt.Font("Dialog", 0, 12));
	jPanel1.setLayout(flowLayout4);
	flowLayout4.setAlignment(FlowLayout.RIGHT);
	flowLayout4.setHgap(10);
	flowLayout4.setVgap(10);
	panel1.setPreferredSize(new Dimension(500, 460));
	jPanel1.setBorder(BorderFactory.createEtchedBorder());
	jPanel8.setBorder(BorderFactory.createEtchedBorder());
	jPanel8.setPreferredSize(new Dimension(205, 50));
	jPanel9.setBorder(BorderFactory.createEtchedBorder());
	jPanel7.setBorder(BorderFactory.createEtchedBorder());
	jPanel5.setPreferredSize(new Dimension(500, 50));
	getContentPane().add(panel1);
	panel1.add(jPanel3, BorderLayout.CENTER);
    jPanel3.add(jTabbedPane1, BorderLayout.CENTER);
    jTabbedPane1.add(jPanel6, "입력변수 세팅");
    jPanel6.add(jPanel8, BorderLayout.NORTH);
    jPanel8.add(jLabel2, null);
    jPanel8.add(jTextField1, null);
    jPanel6.add(jPanel9, BorderLayout.CENTER);
    jPanel9.add(jPanel17, BorderLayout.NORTH);
    jPanel17.add(jLabel13, null);
    jPanel17.add(jLabel14, null);
    jPanel9.add(jPanel10, BorderLayout.CENTER);
    jPanel10.add(jLabel4, null);
    jPanel10.add(jTextField2, null);
    jPanel10.add(jComboBox1, null);
    jPanel10.add(jLabel5, null);
    jPanel10.add(jTextField3, null);
    jPanel10.add(jComboBox2, null);
    jTabbedPane1.add(jPanel7, "컬럼 선택");
    jPanel7.add(jPanel11, null);
    jPanel11.add(jRadioButton1, null);
    jPanel11.add(jRadioButton2, null);
    jPanel7.add(jPanel12, null);
    jPanel12.add(jLabel6, null);
    jPanel12.add(jLabel7, null);
    jPanel12.add(jLabel8, null);
    jPanel12.add(jLabel9, null);
    jPanel12.add(jLabel11, null);
    jPanel12.add(jLabel12, null);
    jPanel7.add(jPanel13, null);
    jPanel13.add(jPanel14, null);
    jPanel14.add(jScrollPane1, null);
    jScrollPane1.getViewport().add(jTable1, null);
    jPanel14.add(jButton3, null);
    jPanel14.add(jButton4, null);
    jPanel14.add(jButton5, null);
    jPanel13.add(jPanel15, null);
    jPanel15.add(jButton6, null);
    jPanel15.add(jButton7, null);
    jPanel13.add(jPanel16, null);
    jPanel16.add(jLabel3, null);
    jPanel16.add(jTextField4, null);
    jPanel16.add(jLabel10, null);
    jPanel16.add(jTextField5, null);
    panel1.add(jPanel5, BorderLayout.NORTH);
    jPanel5.add(jLabel1, BorderLayout.CENTER);
    panel1.add(jPanel1, BorderLayout.SOUTH);
    jPanel1.add(jButton1, null);
    jPanel1.add(jButton2, null);
    this.setResizable(false);
  }

  public String int2Str(int i){
    Integer ii = new Integer(i);
    return ii.toString();
  }

  public String float2Str(float f){
    Float ff = new Float(f);
    return ff.toString();
  }

  public int str2Int(String str){
    Integer i = new Integer(str);
   	return i.intValue();
  }

  public float str2Float(String str){
    Float f = new Float(str);
   	return f.floatValue();
  }

  private void tableSetting(){
    //한번에 하나의 row만 선택되도록 한다.(default는 멀티 row 선택 가능)
    jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    jTable1.setEditingColumn(1);

    Vector attriName = new Vector();  //attribute name을 저장할 Vector
    Vector attriType = new Vector();  //attribute type을 저장할 Vector
    getAttributesList(attriName, attriType);

    assoModel = new assoTableModel();
    assoModel.setData(attriName, attriType);  //tableModel의 data값을 setting
    jTable1.tableChanged(new TableModelEvent(assoModel));
    jTable1.setModel(assoModel);

    //column width 조절하기
    TableColumn column = null;
    for (int i = 0; i < 4; i++) {
      column = jTable1.getColumnModel().getColumn(i); //return : TableColumnModel, TableColumn
      if(i == 0){
        column.setPreferredWidth(40); //No. column
      }else if(i == 1) {
        column.setPreferredWidth(30); //checkbox column
      }else if(i == 2) {
        column.setPreferredWidth(120);
      }else{
        column.setPreferredWidth(80);
      }
    }//end for
  }


  private void getAttributesList(Vector attriName, Vector attriType){
    //meta파일을 읽어 num_attributes을 초기화한다.
    String rowNumStr = m_xmmassociation.m_sMNodeAssociation.getProfile(projectName ,metaFile, "NUMBER_OF_ROWS");
    String colNumStr = m_xmmassociation.m_sMNodeAssociation.getProfile(projectName ,metaFile, "NUMBER_OF_COLUMNS"); //attributes 수
    String colList =  m_xmmassociation.m_sMNodeAssociation.getProfile(projectName ,metaFile, "COLUMN_LIST");
    String colIndex = m_xmmassociation.m_sMNodeAssociation.getProfile(projectName ,metaFile, "COLUMN_INDEX");
    String newColList = m_xmmassociation.m_sMNodeAssociation.getProfile(projectName ,metaFile, "NEW_COLUMN_LIST");
    String newColIndex = new String();

    if(!newColList.equals("null")){
      newColIndex = m_xmmassociation.m_sMNodeAssociation.getProfile(projectName ,metaFile, "NEW_COLUMN_INDEX");
    }

    //DB의 instances 수를 set한다.
    if(!rowNumStr.equals("null")){
      num_instances = Integer.parseInt(rowNumStr);
      if(transNumber == -1) transNumber = num_instances;
    }else{
      num_instances = -1; //임시 세팅
    }

    //table의 row수를 setting한다.(table의 column 수는 4개로 일정)

    //colList와 colIndex를 가지고 attribute name을 구한다.
    StringTokenizer tokend_colList = new StringTokenizer(colList, "^");
    while(tokend_colList.hasMoreElements()){
      attriName.add( (String)tokend_colList.nextElement() ); //column list를 temp 벡터에 넣는다.
    }
    StringTokenizer tokend_colIndex = new StringTokenizer(colIndex, "^");
    int num_rows = tokend_colIndex.countTokens();

    if(!newColList.equals("null")){
	  StringTokenizer tokend_newColList = new StringTokenizer(newColList, "^");
	  while(tokend_newColList.hasMoreElements()) attriName.add((String)tokend_newColList.nextElement()); //column list를 temp 벡터에 넣는다.
	  StringTokenizer tokend_newColIndex = new StringTokenizer(newColIndex, "^");
      num_rows += tokend_newColIndex.countTokens();
    }

    //DB의 Attributes 수를 set한다.
    num_attributes = num_rows;    //table의 row수(= DB의 Attributes수)

    //column의 type을 구해 attriType Vector에 저장한다.
    String colName, colType, colProperty;
    int size = attriName.size();
    for(int i = 0; i < size; i++){
      colName = (String)attriName.elementAt(i);
      colProperty = m_xmmassociation.m_sMNodeAssociation.getProfile(projectName ,metaFile,colName); //해당 column 속성을 읽는다.
      StringTokenizer tokend_colProperty = new StringTokenizer(colProperty, "^");
      colType = (String)tokend_colProperty.nextElement(); //column 속성에서 column type을 얻는다.
      attriType.add(colType);
    }
  }

  private void setParameter(){   //파일에서 읽어 파라미터 값을 세팅한다~~
    jTextField1.setText(modelName);
    jComboBox1.setSelectedIndex(min_sup_type);  //0: 개, 1: %

    if( min_sup_type == 0){  //"개"
      jTextField2.setText(int2Str((int)min_sup));  //min_sup
    }else{  //"%"
      Float ff = new Float(min_sup);
      jTextField2.setText(ff.toString()); //min_sup
    }

    Float ff = new Float(min_conf);
    jTextField3.setText(ff.toString());   //min_conf

    if(transNumber != -1){
      String num = int2Str(transNumber);
      jLabel14.setText(num);  //첫번째 탭의 transaction 수
      jLabel7.setText(num);   //두번째 탭의 instance 수
      jLabel12.setText(num);  //두번째 탭의 transaction 수
    }else{
      jLabel14.setText("");  //첫번째 탭의 transaction 수
      jLabel7.setText("");   //두번째 탭의 instance 수
      jLabel12.setText("");  //두번째 탭의 transaction 수
    }
    jLabel9.setText(int2Str(num_attributes));    //두번째 탭의 Attributes 수

    if( dbType == 1){
      jRadioButton1.setSelected(true);
      jRadioButton2.setSelected(false);

      if(bSaved == true){
        Vector attri = new Vector();  //selected attributes를 저장..

        //선택된 attributes에 해당하는 checkBox를 체크해준다.
        StringTokenizer tokend_selAttributes = new StringTokenizer(selAttributes, "^");
        while(tokend_selAttributes.hasMoreElements()) attri.add(tokend_selAttributes.nextElement());

        for(int i = 0; i < num_attributes; i++){
          String temp = (String)assoModel.getValueAt(i, 2);	//필드명

          if(attri.contains(temp)){
            assoModel.setValueAt(new Boolean(true), i, 1);  //checkbox column
          }else{
            assoModel.setValueAt(new Boolean(false), i, 1);  //checkbox column
          }
        }//end for
      }//end if
    }else{  //dbType == 2
      jRadioButton1.setSelected(false);
      jRadioButton2.setSelected(true);

      jButton3.setEnabled(false);   //"All" button
      jButton4.setEnabled(false);   //"None" button
      jButton5.setEnabled(false);   //"Invert" button

      jButton6.setEnabled(true);    //">" button
      jButton7.setEnabled(true);    //">" button

      jTextField4.setText(transIDFld);
      jTextField4.setEnabled(true);  //transIDFld
      jTextField4.setEditable(false);
      jTextField5.setText(targetFld);
      jTextField5.setEnabled(true);  //targetFld
      jTextField5.setEditable(false);
    }
  }

  void jTextField2_actionPerformed(ActionEvent e) { //minimum support check
    String temp = jTextField2.getText();
    if(temp.length() > 0) min_sup = Float.parseFloat(temp);
    int SelectedIndex = jComboBox1.getSelectedIndex();

    if( SelectedIndex == 0){  //개
      //범위가 맞지 않다면 MessageBox를 출력한다.
      if( min_sup <= 0f){
        MessageBox  err_box = new MessageBox(this,"에러메시지",true,"입력 범위 에러","0이상의 정수를 입력하세요");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = err_box.getSize();
        if (frameSize.height > screenSize.height) frameSize.height = screenSize.height;
        if (frameSize.width > screenSize.width) frameSize.width = screenSize.width;
        err_box.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
        err_box.setVisible(true);
        return;
      }
    }else{ //%
      //범위가 맞지 않다면 MessageBox를 출력한다.
      if( min_sup <= 0f || min_sup > 100f ){
        MessageBox  err_box = new MessageBox(this,"에러메시지",true,"입력 범위 에러","0에서 100사이의 수를 입력하세요");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = err_box.getSize();
        if(frameSize.height > screenSize.height) frameSize.height = screenSize.height;
        if(frameSize.width > screenSize.width) frameSize.width = screenSize.width;
        err_box.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
        err_box.setVisible(true);
        return;
      }
    }//end else
  }

  void jTextField3_actionPerformed(ActionEvent e){   //minimum confidence check
    String temp = jTextField3.getText();
    if(temp.length() > 0) min_conf = Float.parseFloat(temp);

    //범위가 맞지 않다면 MessageBox를 출력한다.
    if( min_conf <= 0f || min_conf > 100f ){
      MessageBox  err_box = new MessageBox(this,"에러메시지",true,"입력 범위 에러","0에서 100사이의 수를 입력하세요");
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      Dimension frameSize = err_box.getSize();
      if(frameSize.height > screenSize.height) frameSize.height = screenSize.height;
      if(frameSize.width > screenSize.width) frameSize.width = screenSize.width;
      err_box.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
      err_box.setVisible(true);
      return;
    }
  }

  void jRadioButton1_actionPerformed(ActionEvent e) { //transaction based row(dbType=1)
    jRadioButton1.setSelected(true);
    jRadioButton2.setSelected(false);
    dbType = 1;

    //Button과 TextField을 enable시키거나 disable시키기
    jButton3.setEnabled(true);  //"All" button
    jButton4.setEnabled(true);  //"None" button
    jButton5.setEnabled(true);  //"Invert" button
    jButton6.setEnabled(false);   //">" button
    jButton7.setEnabled(false);   //">" button
    jTextField4.setEnabled(false);  //transIDFld
    jTextField5.setEnabled(false);  //targetFld

    //트랜잭션 수 바꿔주기
    imsi_transNumber = transNumber;  //dbType =2 일때의 트랜잭션수를 기억한다.
    transNumber = num_instances;

    if(transNumber == -1){
      jLabel14.setText("");    //첫번째 탭의 transaction 수
      jLabel12.setText("");    //두번째 탭의 transaction 수
    }else{
      jLabel14.setText(int2Str(transNumber));    //첫번째 탭의 transaction 수
      jLabel12.setText(int2Str(transNumber));    //두번째 탭의 transaction 수
    }
  }

  void jRadioButton2_actionPerformed(ActionEvent e){ //transaction based column(dbType=2)
    jRadioButton2.setSelected(true);
    jRadioButton1.setSelected(false);
    dbType = 2;

    //Button과 TextField을 enable시키거나 disable시키기
    jButton3.setEnabled(false);   //"All" button
    jButton4.setEnabled(false);   //"None" button
    jButton5.setEnabled(false);   //"Invert" button

    jButton6.setEnabled(true);    //">" button
    jButton7.setEnabled(true);    //">" button

    jTextField4.setEnabled(true);  //transIDFld
    jTextField4.setEditable(false);
    jTextField5.setEnabled(true);  //targetFld
    jTextField5.setEditable(false);

    transNumber = imsi_transNumber;

    //트랜잭션 수 바꿔주기
    if(transNumber == -1){
      jLabel14.setText("");    //첫번째 탭의 transaction 수
      jLabel12.setText("");    //두번째 탭의 transaction 수
    }else{
      //transNumber = imsi_transNumber;
      jLabel14.setText(int2Str(transNumber));    //첫번째 탭의 transaction 수
      jLabel12.setText(int2Str(transNumber));    //두번째 탭의 transaction 수
    }
  }

  void jTextField1_actionPerformed(ActionEvent e){ //model name
    modelName = jTextField1.getText();
  }

  void jButton6_actionPerformed(ActionEvent e){ //transIDFld
    //transIDFld에 table에서 선택된 attribute name을 넣는다.
    int selectColumn = jTable1.getSelectedRow();
    String columnName = (String)assoModel.getValueAt(selectColumn, 2);
    transIDFld = columnName;
    jTextField4.setText(columnName);
  }

  void jButton7_actionPerformed(ActionEvent e){  //targetFld
    //targetFld에 table에서 선택된 attribute name을 넣는다.
    int selectColumn = jTable1.getSelectedRow();
    String columnName = (String)assoModel.getValueAt(selectColumn, 2);
    targetFld = columnName;
    jTextField5.setText(columnName);
  }


  void jButton3_actionPerformed(ActionEvent e){  //"All" Button
    assoModel.includeAll();
  }

  void jButton4_actionPerformed(ActionEvent e){  //"None" Button
    assoModel.removeAll();
  }

  void jButton5_actionPerformed(ActionEvent e){  //"Invert" Button
    assoModel.invert();
  }

  //"OK" Button
  void jButton1_actionPerformed(ActionEvent e){  //"OK" Button
    //선택된 column의 name을 String[] selAttributes에 넣는다.
    modelName = jTextField1.getText();
    String sup_temp = jTextField2.getText();  //min_sup
    min_sup_type = jComboBox1.getSelectedIndex(); //0 :개, 1: %
    if( sup_temp.length() > 0 ){
      min_sup = Float.parseFloat(sup_temp);
      if(min_sup_type == 1) min_sup = min_sup/100f; //%
    }

    String conf_temp = jTextField3.getText();     //min_conf
    if( conf_temp.length() > 0 ){
      min_conf = Float.parseFloat(conf_temp);
      min_conf = min_conf/100f; //%
    }

    if(dbType == 1){  //transaction based row
      Boolean check;
      selAttributes = new String("");
      for(int i = 0; i < num_attributes; i++){
        check = (Boolean)assoModel.getValueAt(i , 1);
        if(check.booleanValue() == true){
          selAttributes += (String)assoModel.getValueAt(i, 2) + "^";
          num_selAttributes++;
        }
      }//end for
      if(selAttributes.length() > 2){
        selAttributes = selAttributes.substring(0, selAttributes.length()-1 );
      }

      //비어있는 필드가 있으면 에러메시지 출력
      if((modelName.length() == 0) || (sup_temp.length() == 0) || (conf_temp.length() == 0) || (selAttributes.length() == 0)){
        MessageBox  err_box = new MessageBox(this,"에러메시지",true,"입력 에러","  비어있는 필드가 있습니다. 값을 입력하세요");
        java.awt.Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        java.awt.Dimension frameSize = err_box.getSize();
        if(frameSize.height > screenSize.height) frameSize.height = screenSize.height;
        if(frameSize.width > screenSize.width) frameSize.width = screenSize.width;
        err_box.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
        err_box.setVisible(true);
        return;
      }
    }else{   //dbType == 2
      transIDFld = jTextField4.getText();
      targetFld = jTextField5.getText();

      //비어있는 필드가 있으면 에러메시지 출력
      if((modelName.length() == 0) || (sup_temp.length() == 0) || (conf_temp.length() == 0) || (transIDFld.length() == 0) || (targetFld.length() == 0)){
        MessageBox  err_box = new MessageBox(this,"에러메시지",true,"입력 에러","  비어있는 필드가 있습니다. 값을 입력하시오 ");
        java.awt.Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        java.awt.Dimension frameSize = err_box.getSize();
        if(frameSize.height > screenSize.height) frameSize.height = screenSize.height;
        if(frameSize.width > screenSize.width) frameSize.width = screenSize.width;
        err_box.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
        err_box.setVisible(true);
        return;
      }
    }

    String parameterFile = modelName+"_asso_par";
    m_xmmassociation.m_sMNodeAssociation.setProfile(projectName, parameterFile,"PROJECT_NAME", projectName);
    m_xmmassociation.m_sMNodeAssociation.setProfile(projectName, parameterFile,"PROJECT_PATH", m_xmmassociation.ProjectPath);
    m_xmmassociation.m_sMNodeAssociation.setProfile(projectName, parameterFile,"METAFILE_NAME", metaFile);
    m_xmmassociation.m_sMNodeAssociation.setProfile(projectName, parameterFile,"MODEL_NAME", modelName);
    m_xmmassociation.m_sMNodeAssociation.setProfile(projectName, parameterFile,"MIN_SUP_TYPE", int2Str(min_sup_type));
    if(min_sup_type == 0){
      m_xmmassociation.m_sMNodeAssociation.setProfile(projectName, parameterFile,"MIN_SUP",  int2Str((int)min_sup));
    }else{
      m_xmmassociation.m_sMNodeAssociation.setProfile(projectName, parameterFile,"MIN_SUP",  float2Str(min_sup));
    }
    m_xmmassociation.m_sMNodeAssociation.setProfile(projectName, parameterFile,"NUMBER_OF_TRANS",  int2Str(transNumber));
    m_xmmassociation.m_sMNodeAssociation.setProfile(projectName, parameterFile,"MIN_CONF", float2Str(min_conf));
    m_xmmassociation.m_sMNodeAssociation.setProfile(projectName, parameterFile,"DB_TYPE", int2Str(dbType));
    if(dbType == 1){
      m_xmmassociation.m_sMNodeAssociation.setProfile(projectName, parameterFile,"SELECTED_ATTRIBUTES", selAttributes);
    }else{
      m_xmmassociation.m_sMNodeAssociation.setProfile(projectName, parameterFile,"TRANSID_FLD", transIDFld);
      m_xmmassociation.m_sMNodeAssociation.setProfile(projectName, parameterFile,"TARGET_FLD", targetFld);
    }
    m_xmmassociation.m_sMNodeAssociation.setSchema(m_xmmassociation.ProjectName,modelName+"_asso_rule");
    m_xmmassociation.setParameterName(parameterFile,true,modelName);

    //XMMNodeAssociationRule 클래스의 멤버변수에 값을 저장한다.
    m_xmmassociation.bSaved = true;

    m_xmmassociation.ProjectName = projectName;
    m_xmmassociation.MetaFileName = metaFile;
    m_xmmassociation.model = modelName;

    m_xmmassociation.transNumber = transNumber;
    min_sup = Float.parseFloat(sup_temp); //입력받은 그대로 저장하기위해..
    m_xmmassociation.min_sup = min_sup;
    min_conf = Float.parseFloat(conf_temp); //입력받은 그대로 저장하기위해..
    m_xmmassociation.min_conf = min_conf;
    m_xmmassociation.min_sup_type = min_sup_type;
    m_xmmassociation.dbType = dbType;

    if(dbType == 1){
      m_xmmassociation.selAttributes = selAttributes;
	  }else{
      m_xmmassociation.transIDFld = transIDFld;
      m_xmmassociation.targetFld = targetFld;
    }
    this.dispose();
  }

  void jButton2_actionPerformed(ActionEvent e){
    this.dispose();
  }

  private boolean nullCheck(String i_str){
    boolean out_bool = false;
    if (i_str==null) out_bool = true;
    else{
      if (i_str.equals("")) out_bool = true;
    }
    return out_bool;
  }

  private boolean nullCheck(String[] i_str){
    if (i_str.length==0) return true;
    else return false;
  }

}