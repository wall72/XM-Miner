package xmminer.xmclient.xmgraph.xmdnode.xmdnodecolumnselect;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.table.TableColumn;
import xmminer.xmclient.xmgraph.*;
import xmminer.xmclient.xmgraph.XMDialogGraphElement;
import xmminer.xmclient.xmgraph.xmdnode.xmdnodecolumnselect.*;
import xmminer.xmclient.xmgraph.xmdnode.xmdnodetablemodel.*;
import xmminer.xmserver.xmgraph.xmdnode.xmdnodecolumnselect.*;
import xmminer.xmserver.xmgraph.xmdnode.xmdnodecolumnselect.XMBDNodeColumnSelect;
import xmminer.xmclient.xmgraph.xmdnode.xmdnodecalculate.*;

public class XMDialogDNodeColumnSelect extends JDialog
{
  //*** developer declared class&variable
  XMBDNodeColumnSelect scs;
  XMDNodeColumnSelect ccs;
  XMDNodeColumnSelectTableModel csm;
  JFrame m_frame;
  String column_value;
  String previous_arc;
  String next_arc;
  String arc = "arc";
  public String[] column_list;
  public String[] column_type;
  public String project_name;
  String[] average_value;
  String[] max_value;
  String[] min_value;
  String[] max_frequency_value;
  String[] miss_percent;
  String[] new_column_list;
  String[] cal_result_value;
  int meta_status;
  int column_index;
  boolean cal_dialog_open;
  DefaultListModel list_model = new DefaultListModel();
  JOptionPane optionPane;
  Hashtable post_cal_h;
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
  JPanel jPanel2 = new JPanel();
  BorderLayout borderLayout2 = new BorderLayout();
  JScrollPane jScrollPane2 = new JScrollPane();
  JList jList1 = new JList(list_model);
  JPanel jPanel3 = new JPanel();
  GridLayout gridLayout1 = new GridLayout();
  JPanel jPanel6 = new JPanel();
  BorderLayout borderLayout3 = new BorderLayout();
  JPanel jPanel7 = new JPanel();
  JPanel jPanel8 = new JPanel();
  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();
  JLabel jLabel3 = new JLabel();
  JTextField jTextField1 = new JTextField();
  JButton jButton1 = new JButton();
  JPanel jPanel4 = new JPanel();
  BorderLayout borderLayout4 = new BorderLayout();
  Border border2;


  public XMDialogDNodeColumnSelect(JFrame frame, String title, boolean modal, XMBDNodeColumnSelect server_module, XMDNodeColumnSelect client_module)
  {
    super(frame, title, modal);
    try {
      jbInit();
      m_frame = frame;
      ccs = client_module;
      setPreviousArc();
      setNextArc();
      project_name = ccs.project_name;
      scs = server_module;
      meta_status = scs.setFileStatus(project_name,previous_arc,next_arc);
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
    border2 = BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(Color.white,new Color(142, 142, 142)),BorderFactory.createEmptyBorder(5,5,5,5));
    jPanel1.setLayout(gridBagLayout2);
    button_ok.setFont(new java.awt.Font("Dialog", 0, 12));
    button_ok.setBorder(border1);
    button_ok.setMinimumSize(new Dimension(90, 30));
    button_ok.setPreferredSize(new Dimension(90, 30));
    button_ok.setText("확인");
    button_ok.addActionListener(new XMDialogDNodeColumnSelect_button_ok_actionAdapter(this));
    button_cancel.setFont(new java.awt.Font("Dialog", 0, 12));
    button_cancel.setBorder(border1);
    button_cancel.setMaximumSize(new Dimension(90, 30));
    button_cancel.setMinimumSize(new Dimension(90, 30));
    button_cancel.setPreferredSize(new Dimension(90, 30));
    button_cancel.setText("취소");
    button_cancel.addActionListener(new XMDialogDNodeColumnSelect_button_cancel_actionAdapter(this));
    this.addWindowListener(new XMDialogDNodeColumnSelect_this_windowClosingAdapter(this));
    panel1.setLayout(gridBagLayout1);
    panel1.setPreferredSize(new Dimension(700, 500));
    jPanel1.setBorder(BorderFactory.createEtchedBorder());
    jPanel1.setPreferredSize(new Dimension(700, 55));
    panel2.setLayout(borderLayout1);
    borderLayout1.setHgap(10);
    borderLayout1.setVgap(10);
    jPanel2.setPreferredSize(new Dimension(120, 10));
    jPanel2.setLayout(borderLayout2);
    jPanel3.setPreferredSize(new Dimension(10, 80));
    jPanel3.setLayout(gridLayout1);
    gridLayout1.setRows(3);
    jScrollPane1.setPreferredSize(new Dimension(530, 404));
    jPanel6.setLayout(borderLayout3);
    jLabel1.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel1.setText("칼럼 리스트 ");
    jLabel2.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel2.setText("분석대상칼럼");
    jLabel3.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel3.setBorder(BorderFactory.createEtchedBorder());
    jLabel3.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel3.setHorizontalTextPosition(SwingConstants.CENTER);
    jLabel3.setText("분석대상칼럼수");
    jTextField1.setHorizontalAlignment(SwingConstants.RIGHT);
    borderLayout2.setHgap(10);
    jTable1.setCellSelectionEnabled(true);
    jButton1.setFont(new java.awt.Font("Dialog", 0, 12));
    jButton1.setText("속성 정보 생성");
    jButton1.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        jButton1_actionPerformed(e);
      }
    });
    jPanel4.setLayout(borderLayout4);
    panel2.setBorder(border2);
    jPanel7.setBorder(BorderFactory.createEtchedBorder());
    jPanel8.setBorder(BorderFactory.createEtchedBorder());
    borderLayout4.setVgap(5);
    this.setResizable(false);
    panel1.add(panel2, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    panel2.add(jPanel2, BorderLayout.EAST);
    jPanel2.add(jButton1, BorderLayout.SOUTH);
    jPanel2.add(jPanel4, BorderLayout.CENTER);
    jPanel4.add(jPanel8, BorderLayout.NORTH);
    jPanel8.add(jLabel2, null);
    jPanel4.add(jPanel3, BorderLayout.SOUTH);
    jPanel3.add(jLabel3, null);
    jPanel3.add(jTextField1, null);
    jPanel4.add(jScrollPane2, BorderLayout.CENTER);
    jScrollPane2.getViewport().add(jList1, null);
    panel2.add(jPanel6, BorderLayout.CENTER);
    jPanel6.add(jScrollPane1, BorderLayout.CENTER);
    jPanel6.add(jPanel7, BorderLayout.NORTH);
    jPanel7.add(jLabel1, null);
    jScrollPane1.getViewport().add(jTable1, null);
    panel1.add(jPanel1, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 11, 0));
    jPanel1.add(button_ok, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.SOUTHEAST, GridBagConstraints.NONE, new Insets(10, 490, 10, 0), 0, 0));
    jPanel1.add(button_cancel, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 5, 10, 10), 0, 0));
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
         column_list = scs.getColumnList();
         column_v = new Vector();
         for (int i=0; i<column_list.length; i++)
         {
      	       column_v.addElement(column_list[i]);
         }
         column_type = scs.getColumnType();
         average_value = scs.getAverageValue();
         max_value = scs.getMaxValue();
         min_value = scs.getMinValue();
         max_frequency_value = scs.getMaxFreqValue();
         miss_percent = scs.getMissPercent();
         csm = new XMDNodeColumnSelectTableModel();
         setTableModel();
         csm.addTableModelListener(new javax.swing.event.TableModelListener() {
              public void tableChanged(TableModelEvent e) {
             jTable1_tableChanged(e);
            }
        });
        setColumnWidth();
    }
  }

  private void showWarningDialog()
  {
     optionPane.showMessageDialog(m_frame,"메타 정보가 존재하지 않습니다.","메타 정보 처리 확인", optionPane.WARNING_MESSAGE);
  }

  private void setTableModel()
  {
     csm.setColumnList(column_list);
     csm.setColumnType(column_type);
     csm.setAverageValue(average_value);
     csm.setMaxValue(max_value);
     csm.setMinValue(min_value);
     csm.setMaxFreqValue(max_frequency_value);
     csm.setMissPercent(miss_percent);

     csm.setModelValue();
     try
     {
        jTable1.setModel(csm);
     }catch(Exception e){}
  }


  // OK
  void button_ok_actionPerformed(ActionEvent e)
  {
     new_column_list = new String[list_model.size()];
     for (int i=0; i<list_model.size(); i++)
     {
         new_column_list[i] = (String) list_model.elementAt(i);
     }
     scs.setNewColumnList(new_column_list);
     scs.close();
     dispose();
  }

  // Cancel
  void button_cancel_actionPerformed(ActionEvent e) {
    dispose();
  }

  void this_windowClosing(WindowEvent e) {
    dispose();
  }

  public void setCalResultValue(Hashtable i_h)
  {
  	post_cal_h = i_h;

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
              max_frequency_value[column_index] = cal_result_value[3];
              miss_percent[column_index] = cal_result_value[4];
              setTableModel();
           }
       } catch(Exception e)
          {
          	System.out.println("table_reset_error");
          }

  }

  void jTable1_tableChanged(TableModelEvent e)
  {
    int selected_row_number = jTable1.getSelectedRow();
    int selected_column_number = jTable1.getSelectedColumn();

    if (selected_column_number==0)
    {
      int selected_row_cnt = 0;
      try
      {
        selected_row_cnt = Integer.parseInt(jTextField1.getText());
      } catch(Exception stringToint_e){}

      String selectFlag = String.valueOf(jTable1.getValueAt(selected_row_number,0));

      if (selectFlag.equals("true"))
      {
        list_model.addElement(jTable1.getValueAt(selected_row_number,1));
        jTextField1.setText(String.valueOf(selected_row_cnt+1));
      }
      else if (selectFlag.equals("false"))
      {
        int list_index = list_model.indexOf(jTable1.getValueAt(selected_row_number,1));
        list_model.removeElementAt(list_index);
        jTextField1.setText(String.valueOf(selected_row_cnt-1));
      }
    }
  }

  private void setColumnWidth() //table의 column width를 설정한다.
  {
      TableColumn c = null; //TableColumn 선언

      for (int i=0; i<8; i++)
      {
          c = jTable1.getColumnModel().getColumn(i);
          if (i==0)
          {
               c.setPreferredWidth(30);
          }
          else if (i==1)
          {
               c.setPreferredWidth(80);
          }
          else if (i==2)
          {
               c.setPreferredWidth(60);
          }
          else if (i==3)
          {
               c.setPreferredWidth(70);
          }
          else if (i==4)
          {
               c.setPreferredWidth(70);
          }
          else if (i==5)
          {
               c.setPreferredWidth(70);
          }
          else if (i==6)
          {
               c.setPreferredWidth(70);
          }
          else if (i==7)
          {
               c.setPreferredWidth(68);
          }
      }
  }

  private void openCalDialog()
  {
        Object[] options = {"예","아니오"};
        int ops = optionPane.showOptionDialog
        (this,"속성 정보가 없는 칼럼이 존재합니다. 속성 정보를 생성하시겠습니까?","속성 정보 생성",
        JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
        if (ops==0)
        {
            XMDNodeCalculate graphElement = new XMDNodeCalculate(0);
            graphElement.setDialog(this,ccs.previous_index,0);
            graphElement.Edit(m_frame, null);
        }
  }

  void jButton1_actionPerformed(ActionEvent e)
  {
    XMDNodeCalculate graphElement = new XMDNodeCalculate(0);
    graphElement.setDialog(this,ccs.previous_index,0);
    graphElement.Edit(m_frame, null);
  }

  private void setPreviousArc()
  {
      previous_arc = arc+ccs.previous_index;
  }

  private void setNextArc()
  {
      next_arc = arc+ccs.next_index;
  }

}

class XMDialogDNodeColumnSelect_button_ok_actionAdapter implements ActionListener {
  XMDialogDNodeColumnSelect adaptee;

  XMDialogDNodeColumnSelect_button_ok_actionAdapter(XMDialogDNodeColumnSelect adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.button_ok_actionPerformed(e);
  }
}

class XMDialogDNodeColumnSelect_button_cancel_actionAdapter implements ActionListener {
  XMDialogDNodeColumnSelect adaptee;

  XMDialogDNodeColumnSelect_button_cancel_actionAdapter(XMDialogDNodeColumnSelect adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.button_cancel_actionPerformed(e);
  }
}

class XMDialogDNodeColumnSelect_this_windowClosingAdapter extends WindowAdapter {
  XMDialogDNodeColumnSelect adaptee;

  XMDialogDNodeColumnSelect_this_windowClosingAdapter(XMDialogDNodeColumnSelect adaptee) {
    this.adaptee = adaptee;
  }

  public void windowClosing(WindowEvent e) {
    adaptee.this_windowClosing(e);
  }
}

/*class XMDialogDNodeColumnSelect_this_windowOpenedAdapter extends WindowAdapter {
  XMDialogDNodeColumnSelect adaptee;

  XMDialogDNodeColumnSelect_this_windowOpenedAdapter(XMDialogDNodeColumnSelect adaptee) {
    this.adaptee = adaptee;
  }

  public void windowOpening(WindowEvent e) {
    adaptee.this_windowOpened(e);
  }
}*/