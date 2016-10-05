package xmminer.xmclient.xmgraph.xmxnode.xmxnodedb;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import xmminer.xmserver.xmgraph.xmxnode.xmxnodedb.XNodeDBException;

public class DBLogin_Dialog extends JDialog {

  //start member variables declaration
  DBExt_Client_Module _DCM;
  boolean checkboolean = true;
  String m_projectname;
  //end of member variables declaration

  JPanel panel1 = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel1 = new JPanel();
  JPanel jPanel2 = new JPanel();
  JButton jButton1 = new JButton();
  JButton jButton2 = new JButton();
  JPanel jPanel3 = new JPanel();
  GridLayout gridLayout1 = new GridLayout();
  JPanel jPanel4 = new JPanel();
  JPanel jPanel5 = new JPanel();
  JPanel jPanel6 = new JPanel();
  JLabel jLabel1 = new JLabel();
  JComboBox jComboBox1 = new JComboBox();
  JLabel jLabel2 = new JLabel();
  JTextField jTextField1 = new JTextField();
  JLabel jLabel3 = new JLabel();
  JTextField jTextField2 = new JTextField();
  JLabel jLabel4 = new JLabel();
  JPasswordField jPasswordField1 = new JPasswordField();
  JButton jButton4 = new JButton();

  //Constructor
  public DBLogin_Dialog(JFrame frame, String title, boolean modal, DBExt_Client_Module d, String projectname) {
    super(frame, title, modal);
    _DCM = d;
    m_projectname = projectname;

    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    pack();
  }

  void jbInit() throws Exception {
    panel1.setLayout(borderLayout1);
    jButton1.setActionCommand("CANCEL_Btn");
    jButton1.setText("Cancel");
    jButton1.addActionListener(new DBLogin_Dialog_jButton1_actionAdapter(this));
    jButton2.setMaximumSize(new Dimension(73, 29));
    jButton2.setMinimumSize(new Dimension(73, 29));
    jButton2.setPreferredSize(new Dimension(73, 29));
    jButton2.setActionCommand("OK_Btn");
    jButton2.setText("OK");
    jButton2.addActionListener(new DBLogin_Dialog_jButton2_actionAdapter(this));
    jPanel1.setLayout(gridLayout1);
    gridLayout1.setRows(4);
    jLabel1.setBackground(Color.darkGray);
    jLabel1.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel1.setHorizontalTextPosition(SwingConstants.LEFT);
    jLabel1.setText("Driver Name");
    jLabel2.setBackground(Color.darkGray);
    jLabel2.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel2.setMaximumSize(new Dimension(69, 18));
    jLabel2.setMinimumSize(new Dimension(69, 18));
    jLabel2.setPreferredSize(new Dimension(69, 18));
    jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel2.setHorizontalTextPosition(SwingConstants.LEFT);
    jLabel2.setText("URL");
    jTextField1.setBorder(BorderFactory.createLoweredBevelBorder());
    jTextField1.setMaximumSize(new Dimension(280, 24));
    jTextField1.setMinimumSize(new Dimension(280, 24));
    jTextField1.setPreferredSize(new Dimension(280, 24));
    jTextField1.setText("jdbc:jtds:sqlserver://127.0.0.1:1433");
    jLabel3.setBackground(Color.darkGray);
    jLabel3.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel3.setMaximumSize(new Dimension(69, 18));
    jLabel3.setMinimumSize(new Dimension(69, 18));
    jLabel3.setPreferredSize(new Dimension(69, 18));
    jLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel3.setHorizontalTextPosition(SwingConstants.LEFT);
    jLabel3.setText("Login ID");
    jTextField2.setBorder(BorderFactory.createLoweredBevelBorder());
    jTextField2.setMaximumSize(new Dimension(280, 24));
    jTextField2.setMinimumSize(new Dimension(280, 24));
    jTextField2.setPreferredSize(new Dimension(280, 24));
    jTextField2.setText("miner");
    jLabel4.setBackground(Color.darkGray);
    jLabel4.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel4.setMaximumSize(new Dimension(69, 18));
    jLabel4.setMinimumSize(new Dimension(69, 18));
    jLabel4.setPreferredSize(new Dimension(69, 18));
    jLabel4.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel4.setHorizontalTextPosition(SwingConstants.LEFT);
    jLabel4.setText("Password");
    jPasswordField1.setText("1");
    jPasswordField1.setPreferredSize(new Dimension(280, 24));
    jPasswordField1.setMaximumSize(new Dimension(280, 24));
    jPasswordField1.setBorder(BorderFactory.createLoweredBevelBorder());
    jPasswordField1.setMinimumSize(new Dimension(280, 24));
    jComboBox1.setFont(new java.awt.Font("Dialog", 0, 12));
    jComboBox1.setMaximumSize(new Dimension(280, 24));
    jComboBox1.setMinimumSize(new Dimension(280, 24));
    jComboBox1.setPreferredSize(new Dimension(240, 24));
    jComboBox1.addItemListener(new java.awt.event.ItemListener(){
      public void itemStateChanged(ItemEvent e) {
        jComboBox1_itemStateChanged(e);
      }
    });
    jPanel1.setBorder(BorderFactory.createEtchedBorder());
    jPanel2.setBackground(SystemColor.activeCaptionBorder);
    panel1.setMaximumSize(new Dimension(400, 200));
    panel1.setMinimumSize(new Dimension(400, 200));
    panel1.setPreferredSize(new Dimension(400, 200));
    jButton4.setPreferredSize(new Dimension(59, 29));
    jButton4.setText("New");
    jButton4.addActionListener(new DBLogin_Dialog_jButton4_actionAdapter(this));
    jPanel3.setPreferredSize(new Dimension(350, 39));
    jPanel6.setMinimumSize(new Dimension(350, 34));
    jPanel6.setPreferredSize(new Dimension(360, 34));
    getContentPane().add(panel1);
    panel1.add(jPanel1, BorderLayout.CENTER);
    jPanel1.add(jPanel3, null);
    jPanel3.add(jLabel1, null);
    jPanel3.add(jComboBox1, null);
    jPanel3.add(jButton4, null);
    jPanel1.add(jPanel6, null);
    jPanel6.add(jLabel2, null);
    jPanel6.add(jTextField1, null);
    jPanel1.add(jPanel5, null);
    jPanel5.add(jLabel3, null);
    jPanel5.add(jTextField2, null);
    jPanel1.add(jPanel4, null);
    jPanel4.add(jLabel4, null);
    jPanel4.add(jPasswordField1, null);
    panel1.add(jPanel2, BorderLayout.SOUTH);
    jPanel2.add(jButton2, null);
    jPanel2.add(jButton1, null);
    jComboBox1.addItem("Driver를 선택하시오");
    jComboBox1.addItem("net.sourceforge.jtds.jdbc.Driver");
    jComboBox1.addItem("oracle.jdbc.driver.OracleDriver");

    setResizable(false);

    //meta data 가 없을때 호출하도록 해야함...
	_DCM.XnodeDBMetaDataSave("Mdriver", "net.sourceforge.jtds.jdbc.Driver", m_projectname);
    _DCM.XnodeDBMetaDataSave("Murl", "jdbc:jtds:sqlserver://127.0.0.1:1433", m_projectname);
    _DCM.XnodeDBMetaDataSave("Mlog", "miner", m_projectname);
    _DCM.XnodeDBMetaDataSave("Mps", "1", m_projectname);
    _DCM.XnodeDBMetaDataSave("Odriver", "oracle.jdbc.driver.OracleDriver", m_projectname);
    _DCM.XnodeDBMetaDataSave("Ourl", "jdbc:oracle:thin:@127.0.0.1:1521:ORCL", m_projectname);
    _DCM.XnodeDBMetaDataSave("Olog", "miner", m_projectname);
    _DCM.XnodeDBMetaDataSave("Ops", "1", m_projectname);

    try{
	  String[] LoginInfo = _DCM.XnodeDBMetaDataReads("Login_info", m_projectname);
      if(LoginInfo[0] != null){
        jComboBox1.setSelectedItem(LoginInfo[0]);
        jComboBox1.setEnabled(false);
        jTextField1.setText(LoginInfo[1]);
        jTextField1.setEditable(false);
        jTextField2.setText(LoginInfo[2]);
        jTextField2.setEditable(false);
        jPasswordField1.setText("");
      }
	}catch(Exception e){}

    jComboBox1.setEnabled(false);
    jTextField1.setEditable(false);
    jTextField2.setEditable(false);
  }

  void jButton2_actionPerformed(ActionEvent e) {
    Cursor oldCursor = getCursor();
    setCursor(new Cursor(Cursor.WAIT_CURSOR));
    _DCM.dbDisConnect();

    String[] LoginInfo = new String[4];
    LoginInfo[0] = (String)jComboBox1.getSelectedItem();
    LoginInfo[1] = jTextField1.getText();
    LoginInfo[2] = jTextField2.getText();
    LoginInfo[3] = new String(jPasswordField1.getPassword());
    if(LoginInfo[0] == "Driver를 선택하시오") LoginInfo[0] = "net.sourceforge.jtds.jdbc.Driver";

    try{
      _DCM.dbConnect(LoginInfo[0],LoginInfo[1],LoginInfo[2],LoginInfo[3]);
      _DCM.XnodeDBMetaDataSave("DB_info",LoginInfo[0], m_projectname);
      _DCM.XnodeDBMetaDataSave("Login_info",LoginInfo, m_projectname);

      if(checkboolean == false){
        _DCM.XnodeDBMetaDataSave("colList", "nonenull", m_projectname);
        _DCM.XnodeDBMetaDataSave("colType", "nonenull", m_projectname);
        _DCM.XnodeDBMetaDataSave("tabList", "nonenull", m_projectname);
        _DCM.XnodeDBMetaDataSave("treeNode", "2", m_projectname);
        _DCM.XnodeDBMetaDataSave("sql", "nonenull", m_projectname);
      }

      setCursor(oldCursor);
      dispose();
    }catch(XNodeDBException xe){
      setCursor(oldCursor);
    }
  }

  void jButton1_actionPerformed(ActionEvent e) {
    dispose();
  }

  void jComboBox1_itemStateChanged(ItemEvent e) {
    String ttt = (String)jComboBox1.getSelectedItem();
    if(ttt.compareTo("oracle.jdbc.driver.OracleDriver") == 0){
      ttt = _DCM.XnodeDBMetaDataRead("Ourl", m_projectname);
      jTextField1.setText(ttt);
      ttt = _DCM.XnodeDBMetaDataRead("Olog", m_projectname);
      jTextField2.setText(ttt);
      ttt = _DCM.XnodeDBMetaDataRead("Ops", m_projectname);
      jPasswordField1.setText(ttt);
    }else if(ttt.compareTo("net.sourceforge.jtds.jdbc.Driver") == 0){
      ttt = _DCM.XnodeDBMetaDataRead("Murl", m_projectname);
      jTextField1.setText(ttt);
      ttt = _DCM.XnodeDBMetaDataRead("Mlog", m_projectname);
      jTextField2.setText(ttt);
      ttt = _DCM.XnodeDBMetaDataRead("Mps", m_projectname);
      jPasswordField1.setText(ttt);
    }
  }

  void jButton4_actionPerformed(ActionEvent e) {
    jComboBox1.setSelectedIndex(0);
    jComboBox1.setEnabled(true);
    jTextField1.setText("");
    jTextField1.setEditable(true);
    jTextField2.setText("");
    jTextField2.setEditable(true);
    jPasswordField1.setText("");
    checkboolean = false ;
  }
}

class DBLogin_Dialog_jButton2_actionAdapter implements java.awt.event.ActionListener {
  DBLogin_Dialog adaptee;

  DBLogin_Dialog_jButton2_actionAdapter(DBLogin_Dialog adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jButton2_actionPerformed(e);
  }
}

class DBLogin_Dialog_jButton1_actionAdapter implements java.awt.event.ActionListener {
  DBLogin_Dialog adaptee;

  DBLogin_Dialog_jButton1_actionAdapter(DBLogin_Dialog adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jButton1_actionPerformed(e);
  }
}

class DBLogin_Dialog_jButton4_actionAdapter implements java.awt.event.ActionListener {
  DBLogin_Dialog adaptee;

  DBLogin_Dialog_jButton4_actionAdapter(DBLogin_Dialog adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jButton4_actionPerformed(e);
  }
}