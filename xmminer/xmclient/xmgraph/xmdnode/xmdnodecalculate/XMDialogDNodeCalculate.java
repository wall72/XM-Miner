package xmminer.xmclient.xmgraph.xmdnode.xmdnodecalculate;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.table.TableColumn;
import java.util.*;
import xmminer.xmclient.xmgraph.*;
import xmminer.xmclient.xmgraph.XMDialogGraphElement;
import xmminer.xmclient.xmgraph.xmdnode.xmdnodecalculate.*;
import xmminer.xmclient.xmgraph.xmdnode.xmdnodetablemodel.*;
import xmminer.xmclient.xmgraph.xmdnode.xmdnodecolumnselect.*;
import xmminer.xmclient.xmgraph.xmdnode.xmdnodedataquery.*;
import xmminer.xmserver.xmgraph.xmdnode.xmdnodecalculate.*;
import xmminer.xmserver.xmgraph.xmdnode.xmdnodecalculate.XMBDNodeCalculate;
import java.beans.*;

public class XMDialogDNodeCalculate extends JDialog
{
  //*** developer declared class&variable
  XMBDNodeCalculate sc;
  XMDNodeCalculate cc;
  XMDNodeCalculateTableModel ccm;
  XMDialogDNodeColumnSelect dcs;
  XMDialogDNodeDataQuery ddq;
  JFrame m_frame;
  String cal_value;
  String select_flag;
  String select_column;
  String select_type;
  String pre_select_flag;
  String pre_select_column;
  String pre_select_type;
  String previous_arc;
  String next_arc;
  String arc = "arc";
  String[] c_list;
  String[] c_type;
  String[] cal_column_list;
  String[] cal_column_type;
  int[] cal_option_list = new int[5];
  int h_size;
  int pre_selected_row_number = -1;
  int dialog_status;
  boolean select_opt;
  boolean pre_select_opt;
  boolean[] cal_opt = new boolean[5];
  boolean[] pre_cal_opt = new boolean[5];
  Hashtable column_h;
  Hashtable type_h;
  Hashtable set_property_h;
  JOptionPane optionPane;


  JPanel panel1 = new JPanel();
  Border border1;
  TitledBorder titledBorder1;
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel1 = new JPanel();
  JPanel jPanel2 = new JPanel();
  BorderLayout borderLayout2 = new BorderLayout();
  JPanel jPanel3 = new JPanel();
  JPanel jPanel4 = new JPanel();
  BorderLayout borderLayout3 = new BorderLayout();
  JPanel jPanel5 = new JPanel();
  JScrollPane jScrollPane1 = new JScrollPane();
  JTable jTable1 = new JTable();
  BorderLayout borderLayout4 = new BorderLayout();
  JLabel jLabel1 = new JLabel();
  JPanel jPanel7 = new JPanel();
  JPanel jPanel8 = new JPanel();
  BorderLayout borderLayout6 = new BorderLayout();
  JPanel jPanel9 = new JPanel();
  JPanel jPanel10 = new JPanel();
  GridLayout gridLayout1 = new GridLayout();
  JPanel jPanel11 = new JPanel();
  BorderLayout borderLayout7 = new BorderLayout();
  JPanel jPanel12 = new JPanel();
  BorderLayout borderLayout8 = new BorderLayout();
  JLabel jLabel3 = new JLabel();
  JPanel jPanel13 = new JPanel();
  GridLayout gridLayout2 = new GridLayout();
  JButton jButton1 = new JButton();
  JButton jButton2 = new JButton();
  JCheckBox jCheckBox6 = new JCheckBox();
  JRadioButton jRadioButton1 = new JRadioButton();
  JRadioButton jRadioButton2 = new JRadioButton();
  JRadioButton jRadioButton3 = new JRadioButton();
  JRadioButton jRadioButton4 = new JRadioButton();
  JRadioButton jRadioButton5 = new JRadioButton();
  FlowLayout flowLayout1 = new FlowLayout();
  BorderLayout borderLayout5 = new BorderLayout();
  Border border2;


 // public XMDialogDNodeCalculate(JFrame frame, String title, boolean modal, XMBDNodeCalculate server_module, XMDNodeCalculate client_module, String[] column_list, String[] column_type,XMDialogDNodeColumnSelect i_dialog)
  public XMDialogDNodeCalculate(JFrame frame, String title, boolean modal, XMBDNodeCalculate server_module, XMDNodeCalculate client_module, XMDialogDNodeColumnSelect i_dialog)
  {
    super(frame, title, modal);
    try {
      System.out.println("d1");
      jbInit();
      System.out.println("d2");
      m_frame = frame;
      System.out.println("d3");
      cc = client_module;
      System.out.println("d4");
      setPreviousArc();
      System.out.println("d5");

      System.out.println("d6");
      dcs = i_dialog;
      System.out.println("d7");
      c_list = dcs.column_list;
      for (int i=0; i<c_list.length; i++)
      {
          System.out.println("c_list="+c_list[i]);
      }
      c_type = dcs.column_type;
      for (int i=0; i<c_type.length; i++)
      {
          System.out.println("c_type="+c_type[i]);
      }
      String temp = dcs.project_name;
       System.out.println("temp="+temp);
      sc = server_module;
      System.out.println("previous_arc="+previous_arc);
      sc.setFileStatus(dcs.project_name,previous_arc);
        System.out.println("d9");
      setInitStatus();
        System.out.println("d10");
      dialog_status = 0;
        System.out.println("d11");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
System.out.println("d12");
    pack();
    System.out.println("d13");
  }

  public XMDialogDNodeCalculate(JFrame frame, String title, boolean modal, XMBDNodeCalculate server_module, XMDNodeCalculate client_module, XMDialogDNodeDataQuery i_dialog)
  {
    super(frame, title, modal);
    try {
      jbInit();
      m_frame = frame;
      cc = client_module;
      setPreviousArc();
      ddq = i_dialog;
      c_list = ddq.column_list;
      c_type = ddq.column_type;
      sc = server_module;
      sc.setFileStatus(ddq.project_name,previous_arc);
      setInitStatus();
      dialog_status = 1;
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    pack();
  }

  private void jbInit() throws Exception {
    border1 = BorderFactory.createRaisedBevelBorder();
    titledBorder1 = new TitledBorder("");
    border2 = BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(Color.white,new Color(142, 142, 142)),BorderFactory.createEmptyBorder(5,5,5,5));
    this.addWindowListener(new XMDialogDNodeCalculate_this_windowAdapter(this));
    panel1.setLayout(borderLayout1);
    panel1.setFont(new java.awt.Font("Dialog", 0, 14));
    panel1.setPreferredSize(new Dimension(500, 400));
    jPanel1.setLayout(borderLayout2);
    jPanel2.setBorder(BorderFactory.createEtchedBorder());
    jPanel2.setMinimumSize(new Dimension(87, 53));
    jPanel2.setPreferredSize(new Dimension(10, 53));
    jPanel2.setLayout(flowLayout1);
    jPanel3.setLayout(borderLayout3);
    jPanel4.setPreferredSize(new Dimension(160, 10));
    jPanel4.setLayout(borderLayout4);
    jLabel1.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel1.setText("칼럼 리스트");
    jPanel8.setPreferredSize(new Dimension(10, 252));
    jPanel8.setLayout(borderLayout6);
    borderLayout2.setHgap(5);
    borderLayout2.setVgap(5);
    jPanel9.setPreferredSize(new Dimension(10, 200));
    jPanel9.setLayout(gridLayout1);
    jPanel10.setPreferredSize(new Dimension(10, 30));
    jPanel10.setLayout(borderLayout8);
    gridLayout1.setRows(5);
    jPanel11.setLayout(borderLayout7);
    jPanel11.setBorder(BorderFactory.createEtchedBorder());
    jPanel11.setPreferredSize(new Dimension(34, 180));
    jLabel3.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel3.setBorder(BorderFactory.createEtchedBorder());
    jLabel3.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel3.setText("[  계산 조건  ]");
    borderLayout6.setVgap(5);
    jPanel13.setLayout(gridLayout2);
    gridLayout2.setColumns(2);
    gridLayout2.setHgap(5);
    jButton1.setFont(new java.awt.Font("Dialog", 0, 12));
    jButton1.setBorder(BorderFactory.createRaisedBevelBorder());
    jButton1.setPreferredSize(new Dimension(90, 29));
    jButton1.setText("확인");
    jButton1.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        jButton1_actionPerformed(e);
      }
    });
    jButton2.setFont(new java.awt.Font("Dialog", 0, 12));
    jButton2.setBorder(BorderFactory.createRaisedBevelBorder());
    jButton2.setPreferredSize(new Dimension(90, 29));
    jButton2.setText("취소");
    jButton2.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        jButton2_actionPerformed(e);
      }
    });
    jPanel13.setPreferredSize(new Dimension(159, 30));
    jPanel7.setPreferredSize(new Dimension(138, 24));
    jPanel7.setLayout(borderLayout5);
    jPanel3.setPreferredSize(new Dimension(454, 420));
    jCheckBox6.setText("모든 칼럼 선택");
    jCheckBox6.setFont(new java.awt.Font("Dialog", 0, 12));
    jCheckBox6.addChangeListener(new javax.swing.event.ChangeListener() {

      public void stateChanged(ChangeEvent e) {
        jCheckBox6_stateChanged(e);
      }
    });
    jRadioButton1.setText("평균값");
    jRadioButton1.setFont(new java.awt.Font("Dialog", 0, 12));
    jRadioButton1.addChangeListener(new javax.swing.event.ChangeListener() {

      public void stateChanged(ChangeEvent e) {
        jRadioButton1_stateChanged(e);
      }
    });
    jRadioButton2.setText("최대값");
    jRadioButton2.setFont(new java.awt.Font("Dialog", 0, 12));
    jRadioButton2.addChangeListener(new javax.swing.event.ChangeListener() {

      public void stateChanged(ChangeEvent e) {
        jRadioButton2_stateChanged(e);
      }
    });
    jRadioButton3.setText("최소값");
    jRadioButton3.setFont(new java.awt.Font("Dialog", 0, 12));
    jRadioButton3.addChangeListener(new javax.swing.event.ChangeListener() {

      public void stateChanged(ChangeEvent e) {
        jRadioButton3_stateChanged(e);
      }
    });
    jRadioButton4.setText("필터링");
    jRadioButton4.setFont(new java.awt.Font("Dialog", 0, 12));
    jRadioButton4.addChangeListener(new javax.swing.event.ChangeListener() {

      public void stateChanged(ChangeEvent e) {
        jRadioButton4_stateChanged(e);
      }
    });
    jRadioButton5.setText("Missing비율(%)");
    jRadioButton5.setFont(new java.awt.Font("Dialog", 0, 12));
    jRadioButton5.addChangeListener(new javax.swing.event.ChangeListener() {

      public void stateChanged(ChangeEvent e) {
        jRadioButton5_stateChanged(e);
      }
    });
    flowLayout1.setAlignment(FlowLayout.RIGHT);
    flowLayout1.setHgap(10);
    flowLayout1.setVgap(10);
    jPanel5.setBorder(BorderFactory.createEtchedBorder());
    this.setResizable(false);
    jPanel1.setBorder(border2);
    getContentPane().add(panel1);
    panel1.add(jPanel1, BorderLayout.CENTER);
    jPanel1.add(jPanel3, BorderLayout.CENTER);
    jPanel3.add(jPanel5, BorderLayout.NORTH);
    jPanel5.add(jLabel1, null);
    jPanel3.add(jScrollPane1, BorderLayout.CENTER);
    jScrollPane1.getViewport().add(jTable1, null);
    jPanel1.add(jPanel4, BorderLayout.EAST);
    jPanel4.add(jPanel7, BorderLayout.CENTER);
    jPanel7.add(jCheckBox6, BorderLayout.CENTER);
    jPanel4.add(jPanel8, BorderLayout.SOUTH);
    jPanel8.add(jPanel10, BorderLayout.NORTH);
    jPanel10.add(jLabel3, BorderLayout.CENTER);
    jPanel8.add(jPanel11, BorderLayout.CENTER);
    jPanel11.add(jPanel9, BorderLayout.CENTER);
    jPanel9.add(jRadioButton1, null);
    jPanel9.add(jRadioButton2, null);
    jPanel9.add(jRadioButton3, null);
    jPanel9.add(jRadioButton4, null);
    jPanel9.add(jRadioButton5, null);
    jPanel11.add(jPanel12, BorderLayout.WEST);
    panel1.add(jPanel2, BorderLayout.SOUTH);
    jPanel2.add(jPanel13, null);
    jPanel13.add(jButton1, null);
    jPanel13.add(jButton2, null);
}

  void setInitStatus()
  {
         column_h = new Hashtable();
         type_h = new Hashtable();
         set_property_h = new Hashtable();
         for (int i=0; i<c_list.length; i++)
         {
     	      type_h.put(c_list[i],c_type[i]);
         }
         setNotSelectedTableModel();
         setStartColumnWidth();
  }

  void setNotSelectedTableModel()
  {
     ccm = new XMDNodeCalculateTableModel();
     ccm.setColumnList(c_list);
     ccm.setColumnType(c_type);
     ccm.setNotSelectedModelValue();
     try
     {
        jTable1.setModel(ccm);
     }catch(Exception e){}

     ccm.addTableModelListener(new javax.swing.event.TableModelListener() {
        public void tableChanged(TableModelEvent e) {
          jTable1_tableChanged(e);
        }
     });
  }

  void setSelectedTableModel()
  {
     ccm = new XMDNodeCalculateTableModel();
     ccm.setColumnList(c_list);
     ccm.setColumnType(c_type);
     ccm.setSelectedModelValue();
     try
     {
        jTable1.setModel(ccm);
     }catch(Exception e){}

     ccm.addTableModelListener(new javax.swing.event.TableModelListener() {
        public void tableChanged(TableModelEvent e) {
          jTable1_tableChanged(e);
        }
     });
  }

  void setCalColumnListArray()
  {
     h_size = column_h.size();
     cal_column_list = new String[h_size];
     try
     {
        Enumeration hEum = column_h.keys();
        int i=0;
        while(hEum.hasMoreElements())
        {
           cal_column_list[i] = (String) hEum.nextElement();
           i++;
        }
     } catch(Exception e)
       {}
  }

  void setCalColumnTypeArray()
  {
     cal_column_type = new String[h_size];
     for (int i=0; i<h_size; i++)
     {
     	  cal_column_type[i] = (String) type_h.get(cal_column_list[i]);
     }
  }

  void setCalOptionListArray(String i_column)
  {
     cal_opt = (boolean[]) column_h.get(i_column);

     for (int i=0; i<cal_opt.length; i++)
     {
       cal_option_list[i] = resetValue(cal_opt[i]);
     }
  }

  int resetValue(boolean i_bool)
  {
     if (i_bool==true)
     {
       return 1;
     }
     else
     {
       return 0;
     }
  }

  void this_windowClosing(WindowEvent e) {
    dispose();
  }

  void jTable1_tableChanged(TableModelEvent e)
  {

    int selected_row_number = jTable1.getSelectedRow();
    int selected_column_number = jTable1.getSelectedColumn();
    int r = 0;

    if (selected_column_number==0)
    {
      if (pre_selected_row_number!=-1)
      {
          r = checkPreValue();
      }
      if (r==0)
      {
         //selected_row_number = pre_selected_row_number;
      }
      else
      {
         selected_row_number = pre_selected_row_number;
      }
          select_opt = true;
          jCheckBox6.setSelected(false);
          calOptInit();
          select_flag = String.valueOf(jTable1.getValueAt(selected_row_number,0));
          select_column = String.valueOf(jTable1.getValueAt(selected_row_number,1));
          select_type = String.valueOf(jTable1.getValueAt(selected_row_number,2));

          if (select_flag.equals("true"))
          {
             setRadioButtons(select_type);
          }
          if (column_h.containsKey(select_column))
          {
             cal_opt = (boolean[]) column_h.get(select_column);
          }
          setCalRadioButtons();
      }
      else if (select_flag.equals("false"))
      {
          setRadioButtonDisabled();
          column_h.remove(select_column);
      }
      pre_selected_row_number = selected_row_number;
   // }
  }

  void calOptInit()
  {
      cal_opt = new boolean[5];
      for (int i=0; i<cal_opt.length; i++)
      {
         cal_opt[i] = false;
      }
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

  int checkPreValue()
  {
      pre_select_flag = String.valueOf(jTable1.getValueAt(pre_selected_row_number,0));
      pre_select_column = String.valueOf(jTable1.getValueAt(pre_selected_row_number,1));
      pre_select_type = String.valueOf(jTable1.getValueAt(pre_selected_row_number,2));
      if (pre_select_flag.equals("true"))
      {
         pre_cal_opt = (boolean[]) column_h.get(pre_select_column);
      }
      return validatePreCalOpt();
  }

  int validatePreCalOpt()
  {
      int v = 0;
      for (int i=0; i<pre_cal_opt.length; i++)
      {
         pre_select_opt = pre_cal_opt[i];
         if (pre_select_opt)
         {
            v = 1;
         }
      }
      if (v==0)
      {
         Object[] options={"확인"};
         int ops = optionPane.showOptionDialog
         (this,"최소한 한 가지 이상 계산 조건을 선택하셔야 합니다.","계산 조건 선택 오류",
         JOptionPane.OK_OPTION,JOptionPane.WARNING_MESSAGE,null,options,options[0]);
         jTable1.setRowSelectionInterval(pre_selected_row_number,pre_selected_row_number);
        // setRadioButtons(pre_select_type);


         return(-1);
      }
      else
      {
        return(0);
      }
  }

  void setRadioButtons(String i_str)
  {
      if (numberCheck(i_str))
      {
         jRadioButton1.setEnabled(true);
         jRadioButton2.setEnabled(true);
         jRadioButton3.setEnabled(true);
         jRadioButton4.setEnabled(false);
         jRadioButton4.setSelected(false);
         jRadioButton5.setEnabled(true);

      }
      else
      {
         jRadioButton1.setEnabled(false);
         jRadioButton2.setEnabled(false);
         jRadioButton3.setEnabled(false);
         jRadioButton1.setSelected(false);
         jRadioButton2.setSelected(false);
         jRadioButton3.setSelected(false);
         jRadioButton4.setEnabled(true);
         jRadioButton5.setEnabled(true);
      }
  }

  void setCalRadioButtons()
  {

      jRadioButton1.setSelected(cal_opt[0]);
      jRadioButton2.setSelected(cal_opt[1]);
      jRadioButton3.setSelected(cal_opt[2]);
      jRadioButton4.setSelected(cal_opt[3]);
      jRadioButton5.setSelected(cal_opt[4]);
  }

 /* void setRadioButtons()
  {

      jRadioButton1.setSelected(cal_opt[0]);
      jRadioButton2.setSelected(cal_opt[1]);
      jRadioButton3.setSelected(cal_opt[2]);
      jRadioButton4.setSelected(cal_opt[3]);
      jRadioButton5.setSelected(cal_opt[4]);
  } */

  void jRadioButton1_stateChanged(ChangeEvent e)
  {
      if (select_opt)
      {
         cal_opt[0] = jRadioButton1.isSelected();
         column_h.put(select_column,cal_opt);
      }
  }

  void jRadioButton2_stateChanged(ChangeEvent e)
  {
      if (select_opt)
      {
         cal_opt[1] = jRadioButton2.isSelected();
         column_h.put(select_column,cal_opt);
      }
  }

  void jRadioButton3_stateChanged(ChangeEvent e)
  {
      if (select_opt)
      {
         cal_opt[2] = jRadioButton3.isSelected();
         column_h.put(select_column,cal_opt);
      }
  }

  void jRadioButton4_stateChanged(ChangeEvent e)
  {
      if (select_opt)
      {
         cal_opt[3] = jRadioButton4.isSelected();
         column_h.put(select_column,cal_opt);
      }
  }

  void jRadioButton5_stateChanged(ChangeEvent e)
  {
      if (select_opt)
      {
         cal_opt[4] = jRadioButton5.isSelected();
         column_h.put(select_column,cal_opt);
      }
  }

  private void setColumnWidth() //table의 column width를 설정한다.
  {
      TableColumn c = null; //TableColumn 선언
      for (int i=0; i<3; i++)
      {
          c = jTable1.getColumnModel().getColumn(i);
          if (i==0)
          {
               c.setWidth(36);
          }
          else if (i==1)
          {
               c.setWidth(120);
          }
          else if (i==2)
          {
               c.setWidth(100);
          }
      }
  }

  private void setStartColumnWidth() //table의 column width를 설정한다.
  {
      TableColumn c = null; //TableColumn 선언
      for (int i=0; i<3; i++)
      {
          c = jTable1.getColumnModel().getColumn(i);
          if (i==0)
          {
               c.setPreferredWidth(36);
          }
          else if (i==1)
          {
               c.setPreferredWidth(120);
          }
          else if (i==2)
          {
               c.setPreferredWidth(100);
          }
      }
  }

  void jButton1_actionPerformed(ActionEvent e)
  {
     setCalColumnListArray();
     setCalColumnTypeArray();
     sc.setCalColumnList(cal_column_list);
     sc.setCalColumnType(cal_column_type);

     for (int i=0; i<h_size; i++)
     {
        setCalOptionListArray(cal_column_list[i]);
        sc.setCalOptionList(cal_column_list[i],cal_option_list);
     }
     cc.Run(m_frame,null);
     getCalResult();
     setCalResult();
     sc.close();
     dispose();
  }

  void jButton2_actionPerformed(ActionEvent e)
  {
     dispose();
  }

  private void getCalResult()
  {
  	String[] cal_result;
  	for (int i=0; i<cal_column_list.length; i++)
  	{
  		cal_result = sc.getResultValue(cal_column_list[i]);
  		set_property_h.put(cal_column_list[i],cal_result);
  	}
  }

  private void setCalResult()
  {
         if (dialog_status==0)
         {
         	dcs.setCalResultValue(set_property_h);
         }
         else if (dialog_status==1)
         {
              ddq.setCalResultValue(set_property_h);
         }
  }

  void jCheckBox6_stateChanged(ChangeEvent e)
  {

      if (jCheckBox6.isSelected())
      {
          select_opt = false;
          pre_selected_row_number = -1;
          setSelectedTableModel();
          setRadioButtonEnabled();
          for (int i=0; i<c_list.length; i++)
          {
              calOptInit();
              if (numberCheck((String) type_h.get(c_list[i])))
              {
                  cal_opt[0] = true;
                  cal_opt[1] = true;
                  cal_opt[2] = true;
                  cal_opt[3] = false;
                  cal_opt[4] = true;
              }
              else
              {
                  cal_opt[0] = false;
                  cal_opt[1] = false;
                  cal_opt[2] = false;
                  cal_opt[3] = true;
                  cal_opt[4] = true;
              }
              column_h.put(c_list[i],cal_opt);
          }
      }
      else
      {
          if (select_opt)
          {
             //ccm.setNotSelectedModelValue();
             //setRadioButtonDisabled();
          }
          else
          {
             pre_selected_row_number = -1;
             setNotSelectedTableModel();
             setRadioButtonDisabled();
             column_h.clear();
          }
      }
      setColumnWidth();

  }

  private void setRadioButtonDisabled()
  {
      jRadioButton1.setEnabled(false);
      jRadioButton2.setEnabled(false);
      jRadioButton3.setEnabled(false);
      jRadioButton4.setEnabled(false);
      jRadioButton5.setEnabled(false);
      jRadioButton1.setSelected(false);
      jRadioButton2.setSelected(false);
      jRadioButton3.setSelected(false);
      jRadioButton4.setSelected(false);
      jRadioButton5.setSelected(false);
  }

  private void setRadioButtonEnabled()
  {
      jRadioButton1.setEnabled(true);
      jRadioButton2.setEnabled(true);
      jRadioButton3.setEnabled(true);
      jRadioButton4.setEnabled(true);
      jRadioButton5.setEnabled(true);
      jRadioButton1.setSelected(true);
      jRadioButton2.setSelected(true);
      jRadioButton3.setSelected(true);
      jRadioButton4.setSelected(true);
      jRadioButton5.setSelected(true);
  }

  private void setPreviousArc()
  {
      previous_arc = arc+cc.previous_index;
  }


}



class XMDialogDNodeCalculate_this_windowAdapter extends WindowAdapter {
  XMDialogDNodeCalculate adaptee;

  XMDialogDNodeCalculate_this_windowAdapter(XMDialogDNodeCalculate adaptee) {
    this.adaptee = adaptee;
  }

  public void windowClosing(WindowEvent e) {
    adaptee.this_windowClosing(e);
  }
}


