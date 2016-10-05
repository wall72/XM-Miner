package xmminer.xmclient.xmgraph.xmdnode.xmdnodedataquery;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.table.TableColumn;
import java.util.*;
import xmminer.xmclient.xmgraph.xmdnode.xmdnodetablemodel.*;

public class XMDialogDNodeFilterValueQuery extends JDialog
{
  //*** developer declared class&variable

  XMDNodeFilterValueQueryTableModel ftm;
  XMDialogDNodeDataQuery ddq;
  JFrame m_frame;
  String[] filtering_value;
  String[] frequency_value;
  String[] percent_value;

  JPanel panel1 = new JPanel();
  Border border1;
  TitledBorder titledBorder1;
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel1 = new JPanel();
  JPanel jPanel2 = new JPanel();
  BorderLayout borderLayout2 = new BorderLayout();
  JPanel jPanel3 = new JPanel();
  BorderLayout borderLayout3 = new BorderLayout();
  JPanel jPanel5 = new JPanel();
  JScrollPane jScrollPane1 = new JScrollPane();
  JTable jTable1 = new JTable();
  BorderLayout borderLayout9 = new BorderLayout();
  JPanel jPanel14 = new JPanel();
  GridLayout gridLayout1 = new GridLayout();
  JButton jButton3 = new JButton();
  JButton jButton4 = new JButton();
  JButton jButton5 = new JButton();
  JButton jButton6 = new JButton();
  JButton jButton1 = new JButton();

  public XMDialogDNodeFilterValueQuery(JFrame frame, String title, boolean modal, XMDialogDNodeDataQuery i_dialog)
  {
    super(frame, title, modal);
    try {
      jbInit();
      m_frame = frame;
      ddq = i_dialog;
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
    this.addWindowListener(new XMDialogDNodeCalculate_this_windowAdapter(this));
    panel1.setLayout(borderLayout1);
    panel1.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
    panel1.setPreferredSize(new Dimension(400, 400));
    jPanel1.setLayout(borderLayout2);
    jPanel2.setPreferredSize(new Dimension(10, 33));
    jPanel2.setLayout(borderLayout9);
    jPanel3.setLayout(borderLayout3);
    borderLayout2.setHgap(20);
    borderLayout2.setVgap(20);
    jPanel3.setPreferredSize(new Dimension(454, 420));
    jPanel5.setLayout(gridLayout1);
    gridLayout1.setColumns(4);
    jButton3.setFont(new java.awt.Font("Dialog", 0, 12));
    jButton3.setText("처음");
    jButton4.setFont(new java.awt.Font("Dialog", 0, 12));
    jButton4.setText("마지막");
    jButton5.setFont(new java.awt.Font("Dialog", 0, 12));
    jButton5.setText("다음");
    jButton6.setFont(new java.awt.Font("Dialog", 0, 12));
    jButton6.setText("이전");
    jPanel5.setPreferredSize(new Dimension(284, 22));
    jButton1.setFont(new java.awt.Font("Dialog", 0, 12));
    jButton1.setPreferredSize(new Dimension(75, 33));
    jButton1.setText("확인");
    jButton1.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        jButton1_actionPerformed(e);
      }
    });
    getContentPane().add(panel1);
    panel1.add(jPanel1, BorderLayout.CENTER);
    jPanel1.add(jPanel3, BorderLayout.CENTER);
    jPanel3.add(jPanel5, BorderLayout.NORTH);
    jPanel5.add(jButton3, null);
    jPanel5.add(jButton6, null);
    jPanel5.add(jButton5, null);
    jPanel5.add(jButton4, null);
    jPanel3.add(jScrollPane1, BorderLayout.CENTER);
    jScrollPane1.getViewport().add(jTable1, null);
    jPanel1.add(jPanel14, BorderLayout.SOUTH);
    panel1.add(jPanel2, BorderLayout.SOUTH);
    jPanel2.add(jButton1, BorderLayout.EAST);
}

  void setInitStatus()
  {
     filtering_value = ddq.getFilterValue();
     frequency_value = ddq.getFrequencyValue();
     percent_value = ddq.getPercentValue();

     setTableModel();
  }

  private void setTableModel()
  {
     ftm = new XMDNodeFilterValueQueryTableModel();
     ftm.setFilteringValue(filtering_value);
     ftm.setFrequencyValue(frequency_value);
     ftm.setPercentValue(percent_value);
     ftm.setModelValue();
     try
     {
        jTable1.setModel(ftm);
     }catch(Exception e){}
  }


  void this_windowClosing(WindowEvent e) {
    dispose();
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
      ddq.filerValueQueryStatusClose();
      dispose();
  }

}



class XMDialogDNodeCalculate_this_windowAdapter extends WindowAdapter {
  XMDialogDNodeFilterValueQuery adaptee;

  XMDialogDNodeCalculate_this_windowAdapter(XMDialogDNodeFilterValueQuery adaptee) {
    this.adaptee = adaptee;
  }

  public void windowClosing(WindowEvent e) {
    adaptee.this_windowClosing(e);
  }
}


