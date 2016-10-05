package xmminer.xmclient.xmgraph.xmdnode.xmdnodedataquery;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import xmminer.xmclient.xmgraph.*;
import xmminer.xmclient.xmgraph.XMDialogGraphElement;
import xmminer.xmclient.xmgraph.xmdnode.xmdnodedataquery.*;
import xmminer.xmclient.xmgraph.xmdnode.xmdnodetablemodel.*;
import xmminer.xmclient.xmgraph.xmdnode.xmdnodecalculate.*;
import xmminer.xmserver.xmgraph.xmdnode.xmdnodedataquery.*;
import xmminer.xmserver.xmgraph.xmdnode.xmdnodedataquery.XMBDNodeDataQuery;

public class XMDialogDNodeDataQuery extends JDialog
{
  //*** developer declared class&variable
  XMBDNodeDataQuery sdq;
  XMDNodeDataQuery cdq;
  XMDNodeDataQueryTableModel dtm;
  XMDNodeQueryTextParsing qtp;
  //XMDNodeUserSetDataQuery sdq;
  JFrame m_frame;

  String previous_arc;
  String next_arc;
  String arc = "arc";
  public String[] column_list;
  public String[] column_type;
  public String project_name;
  String column_name;
  String[] average_value;
  String[] max_value;
  String[] min_value;
  String[] max_frequence_value;
  String[] miss_percent;
  String[] valid_parse;
  String[] valid_opt;
  String[] valid_ser;
  String[] cbox2 = {" ","and","or"};
  String[][] data;
  int meta_status;
  int[]valid_index;
  boolean query_flag = false;
  boolean delete_flag = false;
  boolean save_flag = false;
  JOptionPane optionPane;
  Vector column_v;
  //***

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
  JScrollPane jScrollPane1 = new JScrollPane();
  JTable jTable1 = new JTable();
  JPanel jPanel6 = new JPanel();
  BorderLayout borderLayout3 = new BorderLayout();
  JPanel jPanel2 = new JPanel();
  BorderLayout borderLayout2 = new BorderLayout();
  JPanel jPanel3 = new JPanel();
  BorderLayout borderLayout4 = new BorderLayout();
  JPanel jPanel4 = new JPanel();
  BorderLayout borderLayout5 = new BorderLayout();
  JPanel jPanel7 = new JPanel();
  GridLayout gridLayout1 = new GridLayout();
  JPanel jPanel8 = new JPanel();
  JLabel jLabel3 = new JLabel();
  JTextField jTextField1 = new JTextField();
  BorderLayout borderLayout6 = new BorderLayout();
  JPanel jPanel9 = new JPanel();
  JPanel jPanel10 = new JPanel();
  JPanel jPanel11 = new JPanel();
  JPanel jPanel12 = new JPanel();
  JLabel jLabel4 = new JLabel();
  JTextField jTextField2 = new JTextField();
  BorderLayout borderLayout7 = new BorderLayout();
  JLabel jLabel5 = new JLabel();
  JTextField jTextField3 = new JTextField();
  BorderLayout borderLayout8 = new BorderLayout();
  JLabel jLabel6 = new JLabel();
  JTextField jTextField4 = new JTextField();
  BorderLayout borderLayout9 = new BorderLayout();
  JLabel jLabel7 = new JLabel();
  JTextField jTextField5 = new JTextField();
  BorderLayout borderLayout10 = new BorderLayout();
  JPanel jPanel5 = new JPanel();
  JLabel jLabel1 = new JLabel();
  JComboBox jComboBox1 = new JComboBox();
  JButton jButton1 = new JButton();
  JPanel jPanel15 = new JPanel();
  BorderLayout borderLayout11 = new BorderLayout();
  JPanel jPanel16 = new JPanel();
  BorderLayout borderLayout12 = new BorderLayout();
  JPanel jPanel18 = new JPanel();
  BorderLayout borderLayout13 = new BorderLayout();
  JLabel jLabel2 = new JLabel();
  JPanel jPanel19 = new JPanel();
  GridLayout gridLayout2 = new GridLayout();
  JPanel jPanel20 = new JPanel();
  GridLayout gridLayout3 = new GridLayout();
  JPanel jPanel21 = new JPanel();
  JPanel jPanel22 = new JPanel();
  JLabel jLabel8 = new JLabel();
  JTextField jTextField6 = new JTextField();
  BorderLayout borderLayout14 = new BorderLayout();
  BorderLayout borderLayout15 = new BorderLayout();
  JLabel jLabel9 = new JLabel();
  JTextField jTextField7 = new JTextField();
  JPanel jPanel23 = new JPanel();
  GridLayout gridLayout4 = new GridLayout();
  JPanel jPanel24 = new JPanel();
  JPanel jPanel25 = new JPanel();
  JLabel jLabel10 = new JLabel();
  JTextField jTextField8 = new JTextField();
  BorderLayout borderLayout16 = new BorderLayout();
  BorderLayout borderLayout17 = new BorderLayout();
  JLabel jLabel11 = new JLabel();
  JTextField jTextField9 = new JTextField();
  JPanel jPanel26 = new JPanel();
  GridLayout gridLayout5 = new GridLayout();
  JPanel jPanel27 = new JPanel();
  JPanel jPanel28 = new JPanel();
  JLabel jLabel12 = new JLabel();
  JTextField jTextField10 = new JTextField();
  BorderLayout borderLayout18 = new BorderLayout();
  JLabel jLabel13 = new JLabel();
  BorderLayout borderLayout19 = new BorderLayout();
  JComboBox jComboBox2 = new JComboBox(cbox2);
  JScrollPane jScrollPane2 = new JScrollPane();
  JTextArea jTextArea1 = new JTextArea();
  JPanel jPanel29 = new JPanel();
  JPanel jPanel30 = new JPanel();
  GridLayout gridLayout6 = new GridLayout();
  JButton jButton2 = new JButton();
  JButton jButton3 = new JButton();
  JButton jButton4 = new JButton();
  JPanel jPanel31 = new JPanel();
  BorderLayout borderLayout20 = new BorderLayout();
  JPanel jPanel32 = new JPanel();
  JPanel jPanel33 = new JPanel();
  BorderLayout borderLayout21 = new BorderLayout();
  JPanel jPanel34 = new JPanel();
  GridLayout gridLayout7 = new GridLayout();
  JButton jButton5 = new JButton();
  JButton jButton6 = new JButton();
  JButton jButton7 = new JButton();
  JButton jButton8 = new JButton();
  JPanel jPanel35 = new JPanel();
  JPanel jPanel36 = new JPanel();
  JButton jButton9 = new JButton();
  JButton jButton10 = new JButton();
  BorderLayout borderLayout23 = new BorderLayout();
  Border border2;


  public XMDialogDNodeDataQuery(JFrame frame, String title, boolean modal, XMBDNodeDataQuery server_module, XMDNodeDataQuery client_module)
  {
    super(frame, title, modal);
    try {
      jbInit();
      m_frame = frame;
      cdq = client_module;
      setPreviousArc();
      setNextArc();
      sdq = server_module;
      project_name = cdq.project_name;
      meta_status = sdq.setFileStatus(project_name,previous_arc,next_arc);
      setInitStatus();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    pack();
  }

  private void jbInit() throws Exception {
    border1 = BorderFactory.createRaisedBevelBorder();
    titledBorder1 = new TitledBorder("");
    border2 = BorderFactory.createEmptyBorder(5,5,5,5);
    jPanel1.setLayout(gridBagLayout2);
    button_ok.setFont(new java.awt.Font("Dialog", 0, 12));
    button_ok.setBorder(border1);
    button_ok.setMinimumSize(new Dimension(90, 30));
    button_ok.setPreferredSize(new Dimension(90, 30));
    button_ok.setText("확인");
    button_ok.addActionListener(new XMDialogDNodeDataQuery_button_ok_actionAdapter(this));
    button_cancel.setFont(new java.awt.Font("Dialog", 0, 12));
    button_cancel.setBorder(border1);
    button_cancel.setMaximumSize(new Dimension(90, 30));
    button_cancel.setMinimumSize(new Dimension(90, 30));
    button_cancel.setPreferredSize(new Dimension(90, 30));
    button_cancel.setText("취소");
    button_cancel.addActionListener(new XMDialogDNodeDataQuery_button_cancel_actionAdapter(this));
    this.addWindowListener(new XMDialogDNodeDataQuery_this_windowClosingAdapter(this));
    //this.addWindowListener(new XMDialogDNodeDataQuery_this_windowOpenedAdapter(this));
    panel1.setLayout(gridBagLayout1);
    panel1.setPreferredSize(new Dimension(700, 500));
    jPanel1.setBorder(BorderFactory.createEtchedBorder());
    jPanel1.setPreferredSize(new Dimension(700, 55));
    panel2.setLayout(borderLayout1);
    borderLayout1.setHgap(10);
    borderLayout1.setVgap(10);
    jScrollPane1.setPreferredSize(new Dimension(530, 404));
    jPanel6.setLayout(borderLayout3);
    jTable1.setCellSelectionEnabled(true);
    jPanel6.setBorder(border2);
    jPanel6.setPreferredSize(new Dimension(530, 250));
    jPanel2.setPreferredSize(new Dimension(200, 10));
    jPanel2.setLayout(borderLayout2);
    borderLayout3.setHgap(10);
    jPanel3.setLayout(borderLayout4);
    jPanel4.setBorder(BorderFactory.createEtchedBorder());
    jPanel4.setPreferredSize(new Dimension(14, 175));
    jPanel4.setLayout(borderLayout5);
    jPanel7.setLayout(gridLayout1);
    jPanel7.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
    gridLayout1.setHgap(5);
    gridLayout1.setRows(7);
    jLabel3.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel3.setPreferredSize(new Dimension(89, 18));
    jLabel3.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel3.setText("평균값");
    jPanel8.setLayout(borderLayout6);
    jLabel4.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel4.setPreferredSize(new Dimension(89, 18));
    jLabel4.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel4.setText("최대값");
    jPanel9.setLayout(borderLayout7);
    jLabel5.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel5.setPreferredSize(new Dimension(89, 18));
    jLabel5.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel5.setText("최소값");
    jPanel10.setLayout(borderLayout8);
    jLabel6.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel6.setPreferredSize(new Dimension(89, 18));
    jLabel6.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel6.setText("최다빈도값");
    jPanel11.setLayout(borderLayout9);
    jLabel7.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel7.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel7.setText("Missing 비율(%)");
    jPanel12.setLayout(borderLayout10);
    jPanel7.setPreferredSize(new Dimension(93, 138));
    jLabel1.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel1.setText("검색 칼럼");
    jButton1.setFont(new java.awt.Font("Dialog", 0, 12));
    jButton1.setText("칼럼 속성 생성");
    jButton1.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        jButton1_actionPerformed(e);
      }
    });
    jPanel15.setPreferredSize(new Dimension(10, 200));
    jPanel15.setLayout(borderLayout12);
    jPanel3.setPreferredSize(new Dimension(194, 190));
    jPanel5.setLayout(borderLayout11);
    borderLayout11.setHgap(5);
    jPanel16.setPreferredSize(new Dimension(10, 20));
    jPanel18.setLayout(borderLayout13);
    jLabel2.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel2.setText("검색 조건");
    jPanel19.setLayout(gridLayout2);
    gridLayout2.setRows(3);
    jPanel20.setLayout(gridLayout3);
    gridLayout3.setColumns(2);
    gridLayout3.setHgap(10);
    jLabel8.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel8.setText(">");
    jPanel21.setLayout(borderLayout14);
    jPanel22.setLayout(borderLayout15);
    jLabel9.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel9.setText(">=");
    jPanel23.setLayout(gridLayout4);
    gridLayout4.setColumns(2);
    gridLayout4.setHgap(10);
    jLabel10.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel10.setText("<");
    jPanel24.setLayout(borderLayout16);
    jPanel25.setLayout(borderLayout17);
    jLabel11.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel11.setText("<=");
    jPanel26.setLayout(gridLayout5);
    gridLayout5.setColumns(2);
    gridLayout5.setHgap(10);
    jLabel12.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel12.setText("=");
    jPanel27.setLayout(borderLayout18);
    jLabel13.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel13.setText("선택조건");
    jPanel28.setLayout(borderLayout19);
    jPanel18.setPreferredSize(new Dimension(0, 120));
    borderLayout14.setHgap(5);
    borderLayout15.setHgap(5);
    borderLayout16.setHgap(5);
    borderLayout17.setHgap(5);
    borderLayout18.setHgap(5);
    jPanel29.setLayout(gridLayout6);
    gridLayout6.setColumns(3);
    jButton2.setFont(new java.awt.Font("Dialog", 0, 12));
    jButton2.setText("검색");
    jButton2.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        jButton2_actionPerformed(e);
      }
    });
    jButton3.setFont(new java.awt.Font("Dialog", 0, 12));
    jButton3.setText("저장");
    jButton3.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        jButton3_actionPerformed(e);
      }
    });
    jButton4.setFont(new java.awt.Font("Dialog", 0, 12));
    jButton4.setText("삭제");
    jButton4.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        jButton4_actionPerformed(e);
      }
    });
    jPanel31.setLayout(borderLayout20);
    jPanel29.setPreferredSize(new Dimension(177, 22));
    jPanel33.setLayout(borderLayout21);
    jPanel34.setLayout(gridLayout7);
    jButton5.setFont(new java.awt.Font("Dialog", 0, 12));
    jButton5.setText("이전");
    jButton5.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        jButton5_actionPerformed(e);
      }
    });
    jButton6.setFont(new java.awt.Font("Dialog", 0, 12));
    jButton6.setText("마지막");
    jButton6.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        jButton6_actionPerformed(e);
      }
    });
    jButton7.setFont(new java.awt.Font("Dialog", 0, 12));
    jButton7.setText("다음");
    jButton7.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        jButton7_actionPerformed(e);
      }
    });
    jButton8.setFont(new java.awt.Font("Dialog", 0, 12));
    jButton8.setText("처음");
    jButton8.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        jButton8_actionPerformed(e);
      }
    });
    jPanel34.setPreferredSize(new Dimension(474, 22));
    jComboBox1.addItemListener(new XMDialogDNodeDataQuery_jComboBox1_itemAdapter(this));
    jButton9.setFont(new java.awt.Font("Dialog", 0, 12));
    jButton9.setText("필터링값 보기");
    jButton9.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        jButton9_actionPerformed(e);
      }
    });
    jTextField6.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        jTextField6_actionPerformed(e);
      }
    });
    jTextField7.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        jTextField7_actionPerformed(e);
      }
    });
    jTextField8.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        jTextField8_actionPerformed(e);
      }
    });
    jTextField9.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        jTextField9_actionPerformed(e);
      }
    });
    jTextField10.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        jTextField10_actionPerformed(e);
      }
    });
    jComboBox2.addItemListener(new java.awt.event.ItemListener() {

      public void itemStateChanged(ItemEvent e) {
        jComboBox2_itemStateChanged(e);
      }
    });
    jButton10.setFont(new java.awt.Font("Dialog", 0, 12));
    jButton10.setText("검색조건초기화");
    jButton10.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        jButton10_actionPerformed(e);
      }
    });
    jPanel30.setLayout(borderLayout23);
    //jTextArea1.setPreferredSize(new Dimension(0, 25));
    jPanel30.setPreferredSize(new Dimension(87, 20));
    jComboBox1.setFont(new java.awt.Font("Dialog", 0, 12));
    jComboBox2.setFont(new java.awt.Font("Dialog", 0, 12));
    this.setResizable(false);
    panel2.setBorder(BorderFactory.createEtchedBorder());
    panel1.add(panel2, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    panel2.add(jPanel6, BorderLayout.CENTER);
    jPanel6.add(jPanel2, BorderLayout.WEST);
    jPanel2.add(jPanel3, BorderLayout.NORTH);
    jPanel3.add(jPanel4, BorderLayout.CENTER);
    jPanel4.add(jPanel7, BorderLayout.CENTER);
    jPanel7.add(jPanel8, null);
    jPanel8.add(jLabel3, BorderLayout.WEST);
    jPanel8.add(jTextField1, BorderLayout.CENTER);
    jPanel7.add(jPanel9, null);
    jPanel9.add(jLabel4, BorderLayout.WEST);
    jPanel9.add(jTextField2, BorderLayout.CENTER);
    jPanel7.add(jPanel10, null);
    jPanel10.add(jLabel5, BorderLayout.WEST);
    jPanel10.add(jTextField3, BorderLayout.CENTER);
    jPanel7.add(jPanel11, null);
    jPanel11.add(jLabel6, BorderLayout.WEST);
    jPanel11.add(jTextField4, BorderLayout.CENTER);
    jPanel7.add(jPanel12, null);
    jPanel12.add(jLabel7, BorderLayout.WEST);
    jPanel12.add(jTextField5, BorderLayout.CENTER);
    jPanel7.add(jButton1, null);
    jPanel7.add(jButton9, null);
    jPanel3.add(jPanel5, BorderLayout.NORTH);
    jPanel5.add(jLabel1, BorderLayout.WEST);
    jPanel5.add(jComboBox1, BorderLayout.CENTER);
    jPanel2.add(jPanel15, BorderLayout.SOUTH);
    jPanel15.add(jPanel18, BorderLayout.SOUTH);
    jPanel18.add(jScrollPane2, BorderLayout.CENTER);
    jPanel18.add(jPanel30, BorderLayout.NORTH);
    jPanel30.add(jButton10, BorderLayout.WEST);
    jPanel18.add(jPanel31, BorderLayout.SOUTH);
    jPanel31.add(jPanel29, BorderLayout.CENTER);
    jPanel29.add(jButton2, null);
    jPanel29.add(jButton4, null);
    jPanel29.add(jButton3, null);
    jPanel31.add(jPanel32, BorderLayout.NORTH);
    jScrollPane2.getViewport().add(jTextArea1, null);
    jPanel15.add(jPanel19, BorderLayout.CENTER);
    jPanel19.add(jPanel20, null);
    jPanel20.add(jPanel21, null);
    jPanel21.add(jTextField6, BorderLayout.CENTER);
    jPanel21.add(jLabel8, BorderLayout.WEST);
    jPanel20.add(jPanel22, null);
    jPanel22.add(jLabel9, BorderLayout.WEST);
    jPanel22.add(jTextField7, BorderLayout.CENTER);
    jPanel19.add(jPanel23, null);
    jPanel23.add(jPanel24, null);
    jPanel24.add(jTextField8, BorderLayout.CENTER);
    jPanel24.add(jLabel10, BorderLayout.WEST);
    jPanel23.add(jPanel25, null);
    jPanel25.add(jLabel11, BorderLayout.WEST);
    jPanel25.add(jTextField9, BorderLayout.CENTER);
    jPanel19.add(jPanel26, null);
    jPanel26.add(jPanel27, null);
    jPanel27.add(jTextField10, BorderLayout.CENTER);
    jPanel27.add(jLabel12, BorderLayout.WEST);
    jPanel26.add(jPanel28, null);
    jPanel28.add(jLabel13, BorderLayout.WEST);
    jPanel28.add(jComboBox2, BorderLayout.CENTER);
    jPanel15.add(jLabel2, BorderLayout.NORTH);
    jPanel2.add(jPanel16, BorderLayout.CENTER);
    jPanel6.add(jPanel33, BorderLayout.CENTER);
    jPanel33.add(jScrollPane1, BorderLayout.CENTER);
    jPanel33.add(jPanel34, BorderLayout.NORTH);
    jPanel34.add(jButton8, null);
    jPanel34.add(jButton5, null);
    jPanel34.add(jButton7, null);
    jPanel34.add(jButton6, null);
    jPanel34.add(jPanel36, null);
    jPanel34.add(jPanel35, null);
    jScrollPane1.getViewport().add(jTable1, null);
    panel1.add(jPanel1, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel1.add(button_ok, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.SOUTHEAST, GridBagConstraints.NONE, new Insets(11, 490, 9, 0), 0, 0));
    jPanel1.add(button_cancel, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(11, 5, 9, 10), 0, 0));
    getContentPane().add(panel1);
    jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
}

  void setInitStatus()
  {
    if (meta_status!=0)
    {
       showWarningDialog();
       dispose();
    }
    else
    {
       column_list = sdq.getColumnList();
       column_v = new Vector();
       for (int i=0; i<column_list.length; i++)
       {
           column_v.addElement(column_list[i]);
       }
       column_type = sdq.getColumnType();
       average_value = sdq.getAverageValue();
       max_value = sdq.getMaxValue();
       min_value = sdq.getMinValue();
       max_frequence_value = sdq.getMaxFreqValue();
       miss_percent = sdq.getMissPercent();
       sdq.setReadStatus(0,1);
       data = sdq.getData();
       dtm = new XMDNodeDataQueryTableModel();
       setTableModel();
       qtp = new XMDNodeQueryTextParsing();
       qtp.setInitStatus(jTable1.getColumnCount());
       setComboBox();
    }
  }

  private void showWarningDialog()
  {
     optionPane.showMessageDialog(m_frame,"메타 정보가 존재하지 않습니다.","메타 정보 처리 확인", optionPane.WARNING_MESSAGE);
  }

  private void setComboBox()
  {
     for (int i=0; i<column_list.length; i++)
     {
        jComboBox1.addItem(column_list[i]);
     }
  }

  private void setTableModel()
  {
     dtm.setColumnList(column_list);
     dtm.setModelValue(data);
     try
     {
        jTable1.setModel(dtm);
     }catch(Exception e){}
  }


  // OK
  void button_ok_actionPerformed(ActionEvent e)
  {
     if ((delete_flag||save_flag))
     {
        sdq.newRowSet(true);
     }
     sdq.close();
     dispose();
  }

  // Cancel
  void button_cancel_actionPerformed(ActionEvent e) {
    dispose();
  }

  void this_windowClosing(WindowEvent e) {
    dispose();
  }

  void jButton8_actionPerformed(ActionEvent e)
  {
      if (delete_flag)
      {
          sdq.setReadStatus(0,3);
          data = sdq.getData();
          dtm = new XMDNodeDataQueryTableModel();
          setTableModel();
      }
      else if (save_flag||query_flag)
      {
          sdq.setReadStatus(0,2);
          data = sdq.getData();
          dtm = new XMDNodeDataQueryTableModel();
          setTableModel();
      }
      else
      {
          sdq.setReadStatus(0,1);
          data = sdq.getData();
          dtm = new XMDNodeDataQueryTableModel();
          setTableModel();
      }
  }

  void jButton5_actionPerformed(ActionEvent e)
  {
      if (delete_flag)
      {
          sdq.setReadStatus(1,3);
          data = sdq.getData();
          dtm = new XMDNodeDataQueryTableModel();
          setTableModel();
      }
      else if (save_flag||query_flag)
      {
          sdq.setReadStatus(1,2);
          data = sdq.getData();
          dtm = new XMDNodeDataQueryTableModel();
          setTableModel();
      }
      else
      {
          sdq.setReadStatus(1,1);
          data = sdq.getData();
          dtm = new XMDNodeDataQueryTableModel();
          setTableModel();
      }
  }

  void jButton7_actionPerformed(ActionEvent e)
  {
      if (delete_flag)
      {
          sdq.setReadStatus(2,3);
          data = sdq.getData();
          dtm = new XMDNodeDataQueryTableModel();
          setTableModel();
      }
      else if (save_flag||query_flag)
      {
          sdq.setReadStatus(2,2);
          data = sdq.getData();
          dtm = new XMDNodeDataQueryTableModel();
          setTableModel();
      }
      else
      {
          sdq.setReadStatus(2,1);
          data = sdq.getData();
          dtm = new XMDNodeDataQueryTableModel();
          setTableModel();
      }
  }

  void jButton6_actionPerformed(ActionEvent e)
  {
      if (delete_flag)
      {
          sdq.setReadStatus(3,3);
          data = sdq.getData();
          dtm = new XMDNodeDataQueryTableModel();
          setTableModel();
      }
      else if (save_flag||query_flag)
      {
          sdq.setReadStatus(3,2);
          data = sdq.getData();
          dtm = new XMDNodeDataQueryTableModel();
          setTableModel();
      }
      else
      {
          sdq.setReadStatus(3,1);
          data = sdq.getData();
          dtm = new XMDNodeDataQueryTableModel();
          setTableModel();
      }
  }

  private void setFilteringValue()
  {
      int combo_index = jComboBox1.getSelectedIndex();
      jTextField1.setText(average_value[combo_index]);
      jTextField2.setText(max_value[combo_index]);
      jTextField3.setText(min_value[combo_index]);
      jTextField4.setText(max_frequence_value[combo_index]);
      jTextField5.setText(miss_percent[combo_index]);
      if (numberCheck(column_type[combo_index]))
      {
          jButton9.setEnabled(false);
      }
      else
      {
          jButton9.setEnabled(true);
      }
      column_name = (String) jComboBox1.getSelectedItem();
      qtp.setSelectedColumnStatus(column_name,combo_index);
      initTextField();
      jComboBox2.setSelectedIndex(0);
  }

  void jComboBox1_itemStateChanged(ItemEvent e)
  {
      setFilteringValue();
  }

  boolean numberCheck(String i_str)
  {
      if (i_str.equals("REAL")||i_str.equals("INTEGER"))
      {
         return true;
      }
      else
      {
         return false;
      }
  }

  void jButton1_actionPerformed(ActionEvent e)
  {
    XMDNodeCalculate graphElement = new XMDNodeCalculate(0);
    graphElement.setDialog(this,cdq.previous_index,1);
    graphElement.Edit(m_frame, null);
  }

  void jButton9_actionPerformed(ActionEvent e)
  {
      int r = sdq.setFilterValueQueryInitStatus(column_name);
      sdq.setFilterValueReadStatus(0);
      sdq.setFilterValue();
      XMDialogDNodeFilterValueQuery fvq = new XMDialogDNodeFilterValueQuery(m_frame,"필터링값 보기",true,this);
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      Dimension frameSize = fvq.getSize();
      if (frameSize.height > screenSize.height)
          frameSize.height = screenSize.height;
      if (frameSize.width > screenSize.width)
          frameSize.width = screenSize.width;
      fvq.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
      fvq.setVisible(true);
  }


  void setFilterValueReadStatus(int i_number)
  {
      sdq.setFilterValueReadStatus(i_number);
  }

  void setFilterValue()
  {
      sdq.setFilterValue();
  }

  void filerValueQueryStatusClose()
  {
      sdq.filterValueQueryStatusClose();
  }

  String[] getFilterValue()
  {
      return sdq.getFilterValue();
  }

  String[] getFrequencyValue()
  {
      return sdq.getFrequencyValue();
  }

  String[] getPercentValue()
  {
      return sdq.getPercentValue();
  }

  private void setPreviousArc()
  {
      previous_arc = arc+cdq.previous_index;
  }

  private void setNextArc()
  {
      next_arc = arc+cdq.next_index;
  }

  void jTextField6_actionPerformed(ActionEvent e)
  {
      jTextField7.setText("");
      String str1 = jTextField6.getText();
      String str2 = jTextField8.getText();
      String str3 = jTextField9.getText();
      jTextArea1.setText(qtp.getTextArea(str1, str2, str3, "A"));
      query_flag = false;
  }

  void jTextField7_actionPerformed(ActionEvent e)
  {
      jTextField6.setText("");
      String str1 = jTextField7.getText();
      String str2 = jTextField8.getText();
      String str3 = jTextField9.getText();
      jTextArea1.setText(qtp.getTextArea(str1, str2, str3, "B"));
      query_flag = false;
  }

  void jTextField8_actionPerformed(ActionEvent e)
  {
      jTextField9.setText("");
      String str1 = jTextField8.getText();
      String str2 = jTextField6.getText();
      String str3 = jTextField7.getText();
      jTextArea1.setText(qtp.getTextArea(str1, str2, str3, "C"));
      query_flag = false;
  }

  void jTextField9_actionPerformed(ActionEvent e)
  {
      jTextField8.setText("");
      String str1 = jTextField9.getText();
      String str2 = jTextField6.getText();
      String str3 = jTextField7.getText();
      jTextArea1.setText(qtp.getTextArea(str1, str2, str3, "D"));
  }

  void jTextField10_actionPerformed(ActionEvent e)
  {
      jTextField6.setText("");
      jTextField7.setText("");
      jTextField8.setText("");
      jTextField9.setText("");
      jTextArea1.setText(qtp.getTextArea(jTextField10.getText()));
      query_flag = false;
  }

  void jButton2_actionPerformed(ActionEvent e)
  {
      if ((!delete_flag)&&(!save_flag)&&(!query_flag))
      {
         qtp.setValidArray();
         valid_parse = qtp.getValidParse();
         valid_index = qtp.getValidIndex();
         valid_opt = qtp.getValidOpt();
         valid_ser = qtp.getValidSer();
         sdq.setQueryArray(valid_parse,valid_index,valid_opt,valid_ser);
      }
      sdq.setReadStatus(0,2);
      data = sdq.getData();
      dtm = new XMDNodeDataQueryTableModel();
      setTableModel();
      qtp.setInitStatus(jTable1.getColumnCount());
      query_flag = true;
      delete_flag = false;
      save_flag = false;
  }

  void jComboBox2_itemStateChanged(ItemEvent e)
  {
      String event_item = String.valueOf(e.getItem());
      String cbox_item = String.valueOf(jComboBox2.getSelectedItem());
      qtp.setSelectOption(event_item, cbox_item);
  }

  void initTextField()
  {
    jTextField6.setText("");
    jTextField7.setText("");
    jTextField8.setText("");
    jTextField9.setText("");
    jTextField10.setText("");
  }

  void jButton10_actionPerformed(ActionEvent e)
  {
      qtp.setInitStatus(jTable1.getColumnCount());
      jTextArea1.setText("");
  }

  void jButton4_actionPerformed(ActionEvent e)
  {
      if (save_flag)
      {
          badSelectionWarningDialog();
      }
      else
      {
          if (!query_flag)
          {
             qtp.setValidArray();
             valid_parse = qtp.getValidParse();
             valid_index = qtp.getValidIndex();
             valid_opt = qtp.getValidOpt();
             valid_ser = qtp.getValidSer();
             sdq.setQueryArray(valid_parse,valid_index,valid_opt,valid_ser);
             query_flag = true;
          }
          sdq.setReadStatus(0,2);
          data = sdq.getData();
          dtm = new XMDNodeDataQueryTableModel();
          setTableModel();
          sdq.setTransStatus(0);
          openDeleteWarningDialog();
          sdq.setReadStatus(0,3);
          data = sdq.getData();
          dtm = new XMDNodeDataQueryTableModel();
          setTableModel();
          qtp.setInitStatus(jTable1.getColumnCount());
          delete_flag = true;
          save_flag = false;
          if ((delete_flag||save_flag))
          {
              sdq.newRowSet(true);
          }
          sdq.saveMetaData();
          execCalQuestionDialog();
      }
  }

  private void openDeleteWarningDialog()
  {
        Object[] options = {"예","아니오"};
        int ops = optionPane.showOptionDialog
        (this,"현재 입력된  검색 조건에 해당하는 데이터를 삭제하시겠습니까?","삭제 확인",
        JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
        if (ops==0)
        {
            cdq.setTransStatus(0);
            cdq.Run(m_frame,null);
        }
  }

  void jButton3_actionPerformed(ActionEvent e)
  {
      if (delete_flag)
      {
          badSelectionWarningDialog();
      }
      else
      {
          if (!query_flag)
          {
              qtp.setValidArray();
              valid_parse = qtp.getValidParse();
              valid_index = qtp.getValidIndex();
              valid_opt = qtp.getValidOpt();
              valid_ser = qtp.getValidSer();
              sdq.setQueryArray(valid_parse,valid_index,valid_opt,valid_ser);
              query_flag = true;
          }
          sdq.setReadStatus(0,2);
          data = sdq.getData();
          dtm = new XMDNodeDataQueryTableModel();
          setTableModel();
          sdq.setTransStatus(1);
          openSaveWarningDialog();
          qtp.setInitStatus(jTable1.getColumnCount());
          delete_flag = false;
          save_flag = true;
          if ((delete_flag||save_flag))
          {
              sdq.newRowSet(true);
          }
          sdq.saveMetaData();
          execCalQuestionDialog();
      }
  }

  private void execCalQuestionDialog()
  {
        Object[] options = {"예","아니오"};
        int ops = optionPane.showOptionDialog
        (this,"칼럼 속성 정보를 새로 생성하시겠습니까?","칼럼 속성 정보 생성 확인",
        JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
        if (ops==0)
        {
            XMDNodeCalculate graphElement = new XMDNodeCalculate(0);
            graphElement.setDialog(this,cdq.next_index,1);
            graphElement.Edit(m_frame, null);
        }
  }

  private void openSaveWarningDialog()
  {
        Object[] options = {"예","아니오"};
        int ops = optionPane.showOptionDialog
        (this,"현재 입력된  검색 조건에 해당하는 데이터를 저장하시겠습니까?","저장 확인",
        JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
        if (ops==0)
        {
            cdq.setTransStatus(1);
            cdq.Run(m_frame,null);
        }
  }

  private void badSelectionWarningDialog()
  {
       Object[] options={"확인"};
       int ops = optionPane.showOptionDialog
       (this,"현재의 작업을 선택할 수 없습니다.","작업 선택 오류",
       JOptionPane.OK_OPTION,JOptionPane.WARNING_MESSAGE,null,options,options[0]);
  }

  public void setCalResultValue(Hashtable i_h)
  {
       Hashtable post_cal_h = i_h;
       String column_value;
       String[] cal_result_value;
       int column_index;
       try
       {
           Enumeration hEum = post_cal_h.keys();
           while(hEum.hasMoreElements())
           {
              column_value = (String) hEum.nextElement();
              cal_result_value = (String[]) post_cal_h.get(column_value);
              column_index = column_v.indexOf(column_value);
              average_value[column_index] = cal_result_value[0];
              max_value[column_index] = cal_result_value[1];
              min_value[column_index] = cal_result_value[2];
              max_frequence_value[column_index] = cal_result_value[3];
              miss_percent[column_index] = cal_result_value[4];
              setFilteringValue();
           }
       } catch(Exception e)
          {
          	System.out.println("table_reset_error");
          }

  }


}

class XMDialogDNodeDataQuery_button_ok_actionAdapter implements ActionListener {
  XMDialogDNodeDataQuery adaptee;

  XMDialogDNodeDataQuery_button_ok_actionAdapter(XMDialogDNodeDataQuery adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.button_ok_actionPerformed(e);
  }
}

class XMDialogDNodeDataQuery_button_cancel_actionAdapter implements ActionListener {
  XMDialogDNodeDataQuery adaptee;

  XMDialogDNodeDataQuery_button_cancel_actionAdapter(XMDialogDNodeDataQuery adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.button_cancel_actionPerformed(e);
  }
}

class XMDialogDNodeDataQuery_this_windowClosingAdapter extends WindowAdapter {
  XMDialogDNodeDataQuery adaptee;

  XMDialogDNodeDataQuery_this_windowClosingAdapter(XMDialogDNodeDataQuery adaptee) {
    this.adaptee = adaptee;
  }

  public void windowClosing(WindowEvent e) {
    adaptee.this_windowClosing(e);
  }
}

/*class XMDialogDNodeDataQuery_this_windowOpenedAdapter extends WindowAdapter {
  XMDialogDNodeDataQuery adaptee;

  XMDialogDNodeDataQuery_this_windowOpenedAdapter(XMDialogDNodeDataQuery adaptee) {
    this.adaptee = adaptee;
  }

  public void windowOpening(WindowEvent e) {
    adaptee.this_windowOpened(e);
  }
}*/

class XMDialogDNodeDataQuery_jComboBox1_itemAdapter implements java.awt.event.ItemListener {
  XMDialogDNodeDataQuery adaptee;

  XMDialogDNodeDataQuery_jComboBox1_itemAdapter(XMDialogDNodeDataQuery adaptee) {
    this.adaptee = adaptee;
  }

  public void itemStateChanged(ItemEvent e) {
    adaptee.jComboBox1_itemStateChanged(e);
  }
}

/*

    CXMMetaDataManager cmm = new CXMMetaDataManager(file_name+".meta");
    String[] column_name;
    String[] modif_column_name = cmm.getProfiles("modif_column_name");
    String[] modif_column_number = cmm.getProfiles("modif_column_number");
    int modif_check = modif_column_name.length;
    data_index1 = 1;
    mtm1 = new CXMTableModel();
    mtm1.setIndex(6);
    mtm1.setDataRow(data_limit);
    mtm1.setDataIndex(data_index1);
    mtm1.setDataQuerier(cdq);
    mtm1.setFileName(file_name);
    mtm1.setTableReader(file_name+".txt");
    cdq.setQueryArray(valid_parse,valid_index,valid_opt,valid_ser);
    if (modif_check==0)
    {
      column_name = cmm.getProfiles("column_name");
    }
    else
    {
      column_name = modif_column_name;
      mtm1.setDataColumn(modif_column_number);
    }
    mtm1.setModelValue();
    jTable1.setModel(mtm1);
*/
