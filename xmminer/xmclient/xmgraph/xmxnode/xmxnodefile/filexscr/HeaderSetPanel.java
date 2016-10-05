package xmminer.xmclient.xmgraph.xmxnode.xmxnodefile.filexscr;

import java.util.Vector;
import java.util.Hashtable;
import java.awt.*;
import java.io.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.event.TableModelEvent;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.JTableHeader;
import javax.swing.border.*;

import xmminer.xmclient.xmgraph.xmxnode.xmxnodefile.corbautil.*;
import xmminer.xmclient.xmgraph.xmxnode.xmxnodefile.xmscrutil.*;
import xmminer.xmclient.xmgraph.xmxnode.xmxnodefile.filextable.*;
import xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.*;

public class HeaderSetPanel extends JPanel {

 // ImageIcon brwsIcon = new ImageIcon("./xmminer/xmclient/xmgraph/xmxnode/xmxnodefile/filexscr/brws.gif");
 // ImageIcon undoIcon = new ImageIcon("./xmminer/xmclient/xmgraph/xmxnode/xmxnodefile/filexscr/roll.gif");
 // ImageIcon allIcon = new ImageIcon("./xmminer/xmclient/xmgraph/xmxnode/xmxnodefile/filexscr/about.gif");
 // ImageIcon saveIcon = new ImageIcon("./xmminer/xmclient/xmgraph/xmxnode/xmxnodefile/filexscr/save.gif");

  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel5 = new JPanel();
  JPanel jPanel7 = new JPanel();
  JPanel jPanel9 = new JPanel();
  JLabel jLabel3 = new JLabel();
  JLabel jLabel4 = new JLabel();
  JButton jButton_ref = new JButton();
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  GridBagLayout gridBagLayout3 = new GridBagLayout();
  JScrollPane jScrollPane2 = new JScrollPane();
  JPanel jPanel6 = new JPanel();
  JLabel jLabel5 = new JLabel();
  JTable jTable_cInfo = new JTable();
  JButton jButton_all = new JButton();
  GridBagLayout gridBagLayout4 = new GridBagLayout();
  JButton jButton_undo = new JButton();
  JPanel jPanel1 = new JPanel();
  JPanel jPanel2 = new JPanel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JLabel jLabel1 = new JLabel();
  JScrollPane jScrollPane1 = new JScrollPane();
  JList jList1 = new JList();
  GridBagLayout gridBagLayout5 = new GridBagLayout();
  JLabel jLabel2 = new JLabel();
  String[] typeList = {"INTEGER", "FLOAT", "ENUMERATION", "STRING", "DATE" };
  JComboBox jComboBox_type = new JComboBox(typeList);
  JLabel jLabel6 = new JLabel();
  JButton jButton_reflect = new JButton();
  GridBagLayout gridBagLayout6 = new GridBagLayout();
  JScrollPane jScrollPane4 = new JScrollPane();
  JTable jTable_data = new JTable();
  GridBagLayout gridBagLayout7 = new GridBagLayout();
  JRadioButton jRadioButton_notuse = new JRadioButton();
  JRadioButton jRadioButton_use = new JRadioButton();
  JLabel jLabel7 = new JLabel();
  JButton jButton_delete = new JButton();
  JScrollPane jScrollPane3 = new JScrollPane();
  JButton jButton_add = new JButton();
  JTextArea jTextArea1 = new JTextArea();

  int selectedIndex = 0;
  CorbaAgent agent = null;
  public XMXNodeFileInfo info = null;
  int selectedRowCount = -1;
  int selectedRowIndices[] = null;
  Hashtable enumListTable = null;
  EnumListTableFiller filler = null;
  boolean fillOK = false;
  JLabel jLabel8 = new JLabel();
  JRadioButton jRadio_dSelect = new JRadioButton();
  JComboBox jComboBox_dPattern = new JComboBox();
  JRadioButton jRadio_dEdit = new JRadioButton();
  JTextField jTextField_dPattern = new JTextField();
  JLabel jLabel9 = new JLabel();
  String tmpfilename = "xnodefilehsp.tmp";
  Border border1;

  public HeaderSetPanel(CorbaAgent a, XMXNodeFileInfo i) {
    try  {
      agent = a;
      info = i;
      save(tmpfilename);
      filler = new EnumListTableFiller(i.enumList);
      filler.start();
      jbInit();
    	fillContents();
    } catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  private void jbInit() throws Exception {

    String[] patterns = agent.bxnodefile.getAvailablePatterns();
    this.jComboBox_dPattern = new JComboBox(patterns);
    border1 = BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140)),BorderFactory.createEmptyBorder(5,5,5,5));
    this.setBorder(border1);
    this.setMinimumSize(new Dimension(600, 480));
    this.setPreferredSize(new Dimension(600, 480));
    this.setLayout(borderLayout1);
    jPanel5.setMinimumSize(new Dimension(540, 380));
    jPanel5.setPreferredSize(new Dimension(640, 400));
    jPanel5.setLayout(gridBagLayout3);
    jPanel7.setPreferredSize(new Dimension(520, 25));
    jPanel7.setLayout(gridBagLayout7);
    jPanel9.setPreferredSize(new Dimension(200, 230));
    jPanel9.setLayout(gridBagLayout4);
    jLabel3.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel3.setText("컬럼 정보를 수정하세요...");
    jLabel4.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel4.setPreferredSize(new Dimension(60, 18));
    jLabel4.setText("컬럼정보");
    jButton_ref.setFont(new java.awt.Font("Dialog", 0, 12));
    jButton_ref.setBorder(BorderFactory.createEtchedBorder());
    jButton_ref.setPreferredSize(new Dimension(113, 20));
    jButton_ref.setText("다른 파일 참고");
   // jButton_ref.setIcon(brwsIcon);
    jButton_ref.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        jButton_ref_actionPerformed(e);
      }
    });
    jLabel5.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel5.setText("데이터 샘플");
    jPanel6.setLayout(gridBagLayout6);
    jPanel6.setPreferredSize(new Dimension(650, 180));

    jTable_cInfo.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    jTable_cInfo.addMouseListener(new java.awt.event.MouseAdapter()
    {

      public void mouseClicked(MouseEvent e)
      {
        jTable_cInfo_mouseClicked(e);
      }
    });

    jPanel1.setPreferredSize(new Dimension(200, 230));
    jPanel1.setLayout(gridBagLayout5);
    jPanel2.setPreferredSize(new Dimension(200, 230));
    jPanel2.setLayout(gridBagLayout1);
    jButton_all.setFont(new java.awt.Font("Dialog", 0, 12));
    jButton_all.setActionCommand("모두 선택");
    jLabel1.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel1.setText("Enumeration 목록");
    jScrollPane1.setPreferredSize(new Dimension(180, 220));
    jLabel2.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel2.setText("컬럼 타입 수정");
    jLabel6.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel6.setText(" 사용여부 수정");
    jButton_reflect.setFont(new java.awt.Font("Dialog", 0, 12));
    jButton_reflect.setBorder(BorderFactory.createEtchedBorder());
    jButton_reflect.setMaximumSize(new Dimension(60, 22));
    jButton_reflect.setMinimumSize(new Dimension(60, 22));
    jButton_reflect.setPreferredSize(new Dimension(60, 22));
    jButton_reflect.setText("적용");
    //jButton_reflect.setIcon(saveIcon);
    jButton_reflect.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        jButton_reflect_actionPerformed(e);
      }
    });
    jScrollPane4.setPreferredSize(new Dimension(650, 110));
//    jTable_data.setMaximumSize(new Dimension(600, 150));
//    jTable_data.setMinimumSize(new Dimension(500, 80));
//    jTable_data.setPreferredSize(new Dimension(550, 110));
    jTable_data.setColumnSelectionAllowed(true);
    jTable_data.setRowSelectionAllowed(false);
    jComboBox_type.setFont(new java.awt.Font("Dialog", 0, 12));
    jComboBox_type.setMinimumSize(new Dimension(110, 22));
    jComboBox_type.setPreferredSize(new Dimension(180, 22));
    jComboBox_type.addItemListener(new java.awt.event.ItemListener()
    {

      public void itemStateChanged(ItemEvent e)
      {
        jComboBox_type_itemStateChanged(e);
      }
    });

    jRadioButton_notuse.setPreferredSize(new Dimension(73, 22));
    jRadioButton_notuse.setMinimumSize(new Dimension(73, 22));
    jRadioButton_notuse.setText("사용안함");
    jRadioButton_notuse.setFont(new java.awt.Font("Dialog", 0, 12));
    jRadioButton_notuse.addItemListener(new java.awt.event.ItemListener()
    {

      public void itemStateChanged(ItemEvent e)
      {
        jRadioButton_notuse_itemStateChanged(e);
      }
    });
    jRadioButton_use.setPreferredSize(new Dimension(49, 22));
    jRadioButton_use.setMinimumSize(new Dimension(49, 22));
    jRadioButton_use.setText("사용");
    jRadioButton_use.setFont(new java.awt.Font("Dialog", 0, 12));
    jRadioButton_use.addItemListener(new java.awt.event.ItemListener()
    {

      public void itemStateChanged(ItemEvent e)
      {
        jRadioButton_use_itemStateChanged(e);
      }
    });
    jLabel7.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel7.setText("Enumeration 목록 편집");
    jButton_delete.setFont(new java.awt.Font("Dialog", 0, 12));
    jButton_delete.setBorder(BorderFactory.createEtchedBorder());
    jButton_delete.setMaximumSize(new Dimension(180, 22));
    jButton_delete.setMinimumSize(new Dimension(120, 22));
    jButton_delete.setPreferredSize(new Dimension(180, 22));
    jButton_delete.setText("선택된 타입 삭제");
    jButton_delete.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        jButton_delete_actionPerformed(e);
      }
    });
    jButton_add.setFont(new java.awt.Font("Dialog", 0, 12));
    jButton_add.setBorder(BorderFactory.createEtchedBorder());
    jButton_add.setMaximumSize(new Dimension(180, 22));
    jButton_add.setMinimumSize(new Dimension(97, 22));
    jButton_add.setPreferredSize(new Dimension(180, 22));
    jButton_add.setText("목록에 추가");
    jButton_add.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        jButton_add_actionPerformed(e);
      }
    });
    jScrollPane3.setPreferredSize(new Dimension(180, 100));
    jTextArea1.setLineWrap(true);
    jTextArea1.setWrapStyleWord(true);
    jLabel8.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel8.setText("날짜(Date) 타입 선택");
    jRadio_dSelect.setText("권장 타입 중 택 1");
    jRadio_dSelect.setFont(new java.awt.Font("Dialog", 0, 12));
    jRadio_dSelect.addItemListener(new java.awt.event.ItemListener() {

      public void itemStateChanged(ItemEvent e) {
        jRadio_dSelect_itemStateChanged(e);
      }
    });
    jRadio_dEdit.setText("사용자 편집");
    jRadio_dEdit.setFont(new java.awt.Font("Dialog", 0, 12));
    jRadio_dEdit.addItemListener(new java.awt.event.ItemListener() {

      public void itemStateChanged(ItemEvent e) {
        jRadio_dEdit_itemStateChanged(e);
      }
    });
    jTextField_dPattern.setPreferredSize(new Dimension(100, 22));
    jComboBox_dPattern.addItemListener(new java.awt.event.ItemListener() {

      public void itemStateChanged(ItemEvent e) {
        jComboBox_dPattern_itemStateChanged(e);
      }
    });
    jButton_undo.setFont(new java.awt.Font("Dialog", 0, 12));
    jComboBox_dPattern.setFont(new java.awt.Font("Dialog", 0, 12));
    jScrollPane2.getViewport().add(jTable_cInfo, null);
    jScrollPane2.setAutoscrolls(true);
    jScrollPane2.setPreferredSize(new Dimension(200, 220));

    jButton_all.setBorder(BorderFactory.createEtchedBorder());
    jButton_all.setMaximumSize(new Dimension(60, 22));
    jButton_all.setMinimumSize(new Dimension(60, 22));
    jButton_all.setPreferredSize(new Dimension(60, 22));
    jButton_all.setText("모두 선택");
    //jButton_all.setIcon(allIcon);
    jButton_all.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        jButton_all_actionPerformed(e);
      }
    });
    jButton_undo.setBorder(BorderFactory.createEtchedBorder());
    jButton_undo.setMaximumSize(new Dimension(60, 22));
    jButton_undo.setMinimumSize(new Dimension(60, 22));
    jButton_undo.setPreferredSize(new Dimension(60, 22));
    jButton_undo.setActionCommand("선택");
    jButton_undo.setText("되돌리기");
    //jButton_undo.setIcon(undoIcon);
    jButton_undo.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        jButton_undo_actionPerformed(e);
      }
    });
    this.add(jPanel5, BorderLayout.CENTER);
    jPanel5.add(jPanel7, new GridBagConstraints(0, 0, 3, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 10, 0), 0, 0));
    jPanel7.add(jLabel3, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));
    jPanel7.add(jButton_ref, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));
    jPanel5.add(jPanel9, new GridBagConstraints(0, 1, 1, 1, 0.4, 0.5
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    jPanel9.add(jScrollPane2, new GridBagConstraints(0, 1, 4, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 0, 0, 0), 0, 0));
    jPanel9.add(jButton_reflect, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));
    jPanel9.add(jLabel4, new GridBagConstraints(0, 0, 1, 1, 0.3, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 0, 0), 0, 0));
    jPanel9.add(jButton_all, new GridBagConstraints(1, 0, 1, 1, 0.4, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 0, 0), 0, 0));
    jPanel9.add(jButton_undo, new GridBagConstraints(2, 0, 1, 1, 0.4, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 0, 0), 0, 0));
    jPanel5.add(jPanel6, new GridBagConstraints(0, 2, 3, 1, 1.0, 0.5
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 0, 0, 0), 0, 0));
    jPanel6.add(jLabel5, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 511, 0));
    jPanel6.add(jScrollPane4, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    jScrollPane4.getViewport().add(jTable_data, null);
    jPanel5.add(jPanel1, new GridBagConstraints(1, 1, 1, 1, 0.3, 0.5
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 10, 0, 0), 0, 0));
    jPanel1.add(jLabel2, new GridBagConstraints(0, 0, 3, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));
    jPanel1.add(jComboBox_type, new GridBagConstraints(0, 1, 2, 1, 0.3, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 10, 0), 0, 0));
    jPanel1.add(jLabel6, new GridBagConstraints(0, 2, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));
    jPanel1.add(jRadioButton_notuse, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel1.add(jRadioButton_use, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel1.add(jLabel8, new GridBagConstraints(0, 5, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));
    jPanel1.add(jRadio_dSelect, new GridBagConstraints(0, 6, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 0), 0, 0));
    jPanel1.add(jComboBox_dPattern, new GridBagConstraints(0, 7, 2, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
    jPanel1.add(jRadio_dEdit, new GridBagConstraints(0, 8, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 0), 0, 0));
    jPanel1.add(jTextField_dPattern, new GridBagConstraints(0, 9, 2, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
    jPanel1.add(jLabel9, new GridBagConstraints(0, 4, 2, 1, 0.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel5.add(jPanel2, new GridBagConstraints(2, 1, 1, 1, 0.3, 0.5
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 10, 0, 0), 0, 0));
    jPanel2.add(jLabel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 0), 0, 0));
    jPanel2.add(jScrollPane1, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.5
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    jPanel2.add(jLabel7, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 5, 5, 0), 0, 0));
    jPanel2.add(jButton_delete, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 5), 0, 0));
    jPanel2.add(jScrollPane3, new GridBagConstraints(0, 4, 1, 1, 1.0, 0.5
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 5, 0), 0, 0));
    jPanel2.add(jButton_add, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 0, 5), 0, 0));
    jScrollPane3.getViewport().add(jTextArea1, null);
    jScrollPane1.getViewport().add(jList1, null);

    ButtonGroup useGroup = new ButtonGroup();
    useGroup.add(this.jRadioButton_use);
    useGroup.add(this.jRadioButton_notuse);

    ButtonGroup dateGroup = new ButtonGroup();
    dateGroup.add(this.jRadio_dEdit);
    dateGroup.add(this.jRadio_dSelect);
//    jPanel6.add(colsInfoPanel, BorderLayout.CENTER);

    this.setModifiedAreaEnabled(false);
    this.setEnumListAreaEnabled(false);

    this.jRadio_dSelect.setSelected(true);
    this.jComboBox_dPattern.setSelectedIndex(0);
    this.jTextField_dPattern.setEnabled(false);

  }// end of jbInit

  private void setModifiedAreaEnabled(boolean b)
  {
      this.jComboBox_type.setEnabled(b);
      this.jRadioButton_notuse.setEnabled(b);
      this.jRadioButton_use.setEnabled(b);
  }

  private void setEnumListAreaEnabled(boolean b)
  {
      this.jButton_add.setEnabled(b);
      this.jButton_delete.setEnabled(b);
      this.jTextArea1.setEnabled(b);
      this.jTextArea1.setEditable(b);
  }

  public void fillContents()
  {
      System.out.println("fill contents..");
      try {
          refreshColumnInfoToTable(info.columnInfo, jTable_cInfo);
          printSampleDataToTable(jTable_data);
          //sampleData = transpose();
          //jList_example.setListData(sampleData[0]);
      } catch (Exception e) {
      		System.out.println("fillContents error! " + e.getMessage());
      		e.printStackTrace();
      	}
	}// end of fillContents

  private void printSampleDataToTable(JTable table)
  {
      try {
          System.out.println("info.columnNameLinePointer : " + info.columnNameLinePointer);
          System.out.println("info.ignoreLinePointer : " + info.ignoreLinePointer);

      	  int lineCount = 0;
          if (info.ignoreLinePointer != -1) {
              lineCount = info.ignoreLinePointer;
              if (info.columnNameLinePointer > info.ignoreLinePointer) lineCount++;
          } else if (info.columnNameLinePointer != -1) {
              lineCount = 1;
          }
          System.out.println("lineCount is " + lineCount);
          String[][] tData = agent.getInterface().getRows(info.srcdata, lineCount+10);
          String[][] sampleData = null;
          if (tData.length == (lineCount+10)) {
              sampleData = new String[10][tData[0].length];
              for (int i=0; i<10; i++)
                  for (int j=0; j<tData[i].length; j++) {
                      sampleData[i][j] = tData[lineCount+i][j];
                  }
          } else {
              sampleData = new String[tData[0].length - lineCount][tData[0].length];
              for (int i=0; i<sampleData.length; i++)
                  for (int j=0; j<tData[i].length; i++)
                      sampleData[i][j] = tData[lineCount+i][j];
          }

      	  String[] cnames = new String[info.numberOfColumns];
      	  for (int i=0; i<info.numberOfColumns; i++)
          	cnames[i] = new String(info.columnInfo[i].name);
      	  MyTableModel tmodel = new MyTableModel(sampleData, cnames);
      	  table.setModel((TableModel)tmodel);
          TableColumn column = null;
          for (int i=0; i<info.numberOfColumns; i++) {
//System.out.println(" [ " + i + " ] ");
              column = table.getColumnModel().getColumn(i);
              column.setPreferredWidth(80);
          }
    	} catch (FaultDataException e) {
          System.out.println("printSamleData Error ! " + e.getMessage());
    	}
  }

  private void refreshColumnInfoToTable(ColumnInfoType[] cList, JTable table)
  {
      ColumnInfoTableModel cmodel = new ColumnInfoTableModel(cList);
      table.setModel((TableModel)cmodel);
      TableColumn column = null;
      column = jTable_cInfo.getColumnModel().getColumn(0);
      column.setPreferredWidth(80);
      column = jTable_cInfo.getColumnModel().getColumn(1);
      column.setPreferredWidth(80);
      column = jTable_cInfo.getColumnModel().getColumn(2);
      column.setPreferredWidth(40);
      setupTypeColumn(table.getColumnModel().getColumn(1));
  }// end of printColumnInfoToTable

  private void setupTypeColumn(TableColumn columnModel)
  {
        String list[] = {"INTEGER", "FLOAT", "ENUMERATION", "STRING", "DATE" };
        JComboBox comboBox = new JComboBox(list);
        columnModel.setCellEditor(new DefaultCellEditor(comboBox));
  }

  void jTable_cInfo_mouseClicked(MouseEvent e)
  {
      this.setModifiedAreaEnabled(true);
      selectedRowCount = jTable_cInfo.getSelectedRowCount();
      selectedRowIndices = new int[selectedRowCount];

      if (selectedRowCount == 1) {
          selectedRowIndices[0] = jTable_cInfo.getSelectedRow();
          int selectedRowIndex = selectedRowIndices[0];

          String selectedType = (String)jTable_cInfo.getValueAt(selectedRowIndex,1);
          jComboBox_type.setSelectedItem(selectedType);
          Boolean use = (Boolean)jTable_cInfo.getValueAt(selectedRowIndex, 2);
         	jRadioButton_use.setSelected(use.booleanValue());

       	refreshEnumList(selectedRowIndex);

          jTable_data.setColumnSelectionInterval(selectedRowIndex,selectedRowIndex);

          if (selectedType.equals("ENUMERATION")) {
              this.setEnumListAreaEnabled(true);
          }
      } else if (selectedRowCount > 1) {
          selectedRowIndices = jTable_cInfo.getSelectedRows();
      }
  }

  private Hashtable fillEnumListTable(EnumerationType[] enumList)
  {
      Hashtable table = new Hashtable();
      for (int i=0; i<enumList.length; i++) {
          table.put(enumList[i].key, strArrayToVector(enumList[i].values));
      }
      return table;
  }
  private EnumerationType[] getEnumListFromTable(Hashtable table)
  {
      if (fillOK) {
      		int size = table.size();
      		EnumerationType[] eList = new EnumerationType[size];
      		Object[] keys = table.keySet().toArray();
      		for (int i=0; i<size; i++) {
      				eList[i] = new EnumerationType( (String)keys[i], vectorToStrArray((Vector)table.get(keys[i])));
//          eList[i].key = ;
//          eList[i].values = vectorToStrArray((Vector)table.get(keys[i]));
      		}
      		return eList;
      	} else {
      		return null;
      	}
  }

  private void refreshEnumList(int index)
  {
      String key = info.columnInfo[index].name;
      if (fillOK) {
      			System.out.println("fill OK using vector");
      			Vector values = (Vector)enumListTable.get(key);
//      		System.out.println("refresh Enumeration List");
      			if (values != null) {
         				jList1.setListData(values);
      			} else {
        				String[] emptyvalues = {" "};
        				jList1.setListData(emptyvalues);
      			}
      } else if (!filler.isAlive()) {
      			System.out.println("thread is dead. using vector");
      			fillOK = true;
      			enumListTable = filler.getTable();
      			Vector values = (Vector)enumListTable.get(key);
//      		System.out.println("refresh Enumeration List");
      			if (values != null) {
         				jList1.setListData(values);
      			} else {
        				String[] emptyvalues = {" "};
        				jList1.setListData(emptyvalues);
      			}
      	} else {
      			System.out.println("thread is alive. using array");
      			int i=0;
      			for (; i<info.enumList.length; i++) {
      					if (info.enumList[i].key.equals(key)) break;
      			}
      			if (i < info.enumList.length) {
      					jList1.setListData(info.enumList[i].values);
      			} else {
        				String[] emptyvalues = {" "};
        				jList1.setListData(emptyvalues);
      			}
      	}// end of else
  }// end of refreshEnumList

  void jComboBox_type_itemStateChanged(ItemEvent e)
  {
//      System.out.println("jComboBox_type_itemStateChanged");
      ColumnInfoTableModel tmodel = (ColumnInfoTableModel)jTable_cInfo.getModel();
      String newItemState = (String)jComboBox_type.getSelectedItem();
      for (int i=0; i<selectedRowCount; i++) {
          tmodel.setValueAt(newItemState, selectedRowIndices[i], 1);
      }
      jTable_cInfo.tableChanged(new TableModelEvent(tmodel, selectedRowIndices[0], 1));
      if (newItemState.equals("ENUMERATION")) {
          this.setEnumListAreaEnabled(true);
      } else {
          this.setEnumListAreaEnabled(false);
      }
  }

  void jRadioButton_use_itemStateChanged(ItemEvent e)
  {
//      System.out.println("jRadioButton_use_itemStateChanged");
      ColumnInfoTableModel tmodel = (ColumnInfoTableModel)jTable_cInfo.getModel();
      boolean use = jRadioButton_use.isSelected();
      for (int i=0; i<selectedRowCount; i++) {
          tmodel.setValueAt(new Boolean(use), selectedRowIndices[i], 2);
      }
      jTable_cInfo.tableChanged(new TableModelEvent(tmodel, selectedRowIndices[0], 2));
  }

  void jRadioButton_notuse_itemStateChanged(ItemEvent e)
  {
//      System.out.println("jRadioButton_notuse_itemStateChanged");
      ColumnInfoTableModel tmodel = (ColumnInfoTableModel)jTable_cInfo.getModel();
      boolean use = jRadioButton_notuse.isSelected();
      for (int i=0; i<selectedRowCount; i++) {
          tmodel.setValueAt(new Boolean(!use), selectedRowIndices[i], 2);
      }
      jTable_cInfo.tableChanged(new TableModelEvent(tmodel, selectedRowIndices[0], 2));
  }

  void jButton_all_actionPerformed(ActionEvent e) {
      jTable_cInfo.setRowSelectionInterval(0, info.numberOfColumns-1);
      jTable_cInfo_mouseClicked(null);
  }

  void jButton_undo_actionPerformed(ActionEvent e) {
      refreshColumnInfoToTable(info.columnInfo, jTable_cInfo);
      enumListTable = fillEnumListTable(info.enumList);
  }

  void jButton_reflect_actionPerformed(ActionEvent e)
  {
      info.columnInfo = getCurrentTableValues();
      info.enumList = getEnumListFromTable(enumListTable);
  }

  private ColumnInfoType[] getCurrentTableValues()
  {
      ColumnInfoType[] cInfo = new ColumnInfoType[info.numberOfColumns];
      Boolean b;
      for (int i=0; i<info.numberOfColumns; i++) {
          b = (Boolean)jTable_cInfo.getValueAt(i,2);
          cInfo[i] = new ColumnInfoType((String)jTable_cInfo.getValueAt(i,0),
                                        (String)jTable_cInfo.getValueAt(i,1), b.booleanValue());
      }// end of for
      return cInfo;
  }

  public void stopThread() {
  		if (filler.isAlive()) 		filler.requestStop();
  }
  public void reflectModifiedInfo()
  {
      info.columnInfo = getCurrentTableValues();
      EnumerationType[] elist =  getEnumListFromTable(enumListTable);
      if (elist != null)     info.enumList = elist;
      if (jRadio_dSelect.isSelected()) {
          info.datePattern = (String) jComboBox_dPattern.getSelectedItem();
      } else {
          info.datePattern = jTextField_dPattern.getText();
      }
  }

  void jButton_delete_actionPerformed(ActionEvent e)
  {
      Vector enumList = getCurrentEnumList(jList1);
      int selectedIndices[] = jList1.getSelectedIndices();
      for (int i=selectedIndices.length-1; i>=0; i--)
          enumList.removeElementAt(selectedIndices[i]);
      jList1.setListData(enumList);
      modifyColumnInfoEnumList();
  }
  private Vector getCurrentEnumList(JList list)
  {
      int size = list.getModel().getSize();
      Vector strlist = new Vector(10,3);
      for (int i=0; i<size; i++)
          strlist.addElement(list.getModel().getElementAt(i));
      return strlist;
  }

  private void modifyColumnInfoEnumList()
  {
      Vector eList = getCurrentEnumList(jList1);
      if (this.selectedRowCount ==1) {
          int selectedIndex = this.selectedRowIndices[0];
          String key = (String)jTable_cInfo.getValueAt(selectedIndex, 0);
          enumListTable.put(key, eList);
      }
  }

  void jButton_add_actionPerformed(ActionEvent e)
  {
      Vector eList = getCurrentEnumList(jList1);
      String[] userInput = parcingUserInput( jTextArea1.getLineCount(), jTextArea1.getText() );
      for (int i=0; i<userInput.length; i++){
          if (!eList.contains(userInput[i]))
      		    eList.addElement(userInput[i]);
      }
      jList1.setListData(eList);
      modifyColumnInfoEnumList();
      jTextArea1.setText("");
  }

  private String[] parcingUserInput(int lineCount, String input)
  {
      byte[] bs = "\n".getBytes();
      int crlf = (int)bs[0];
      String output[] = new String[lineCount];

      for (int i=0; i<lineCount-1; i++) {
          int endP = input.indexOf(crlf);
          output[i] = input.substring(0, endP);
          input = input.substring(endP+1);
      }
      output[lineCount-1] = input;
      return output;
  }

  private Vector strArrayToVector(String input[])
  {
      Vector v = new Vector(3,2);
      int size = input.length;
      for (int i=0; i<size; i++) {
          v.addElement(input[i]);
      }
      return v;
  }

  private String[] vectorToStrArray(Vector input)
  {
      int size = input.size();
      String s[] = new String[size];
      for (int i=0; i<size; i++) {
          s[i] = (String)input.elementAt(i);
      }
      return s;
  }

  void jButton_ref_actionPerformed(ActionEvent e)
  {

  }

  void jRadio_dSelect_itemStateChanged(ItemEvent e) {
      this.jComboBox_dPattern.setEnabled(true);
      this.jTextField_dPattern.setEnabled(false);
  }

  void jRadio_dEdit_itemStateChanged(ItemEvent e) {
      this.jComboBox_dPattern.setEnabled(false);
      this.jTextField_dPattern.setEnabled(true);
  }

  void jComboBox_dPattern_itemStateChanged(ItemEvent e) {

  }

  public void save(String filename)
  {
      try {
          FileOutputStream fout = new FileOutputStream(filename);
          ObjectOutputStream out = new ObjectOutputStream(fout);
          out.writeObject(info);
          out.close();
      } catch (Exception e) {}
  }
  public void restore(String filename)
  {
      try {
          FileInputStream fin = new FileInputStream(filename);
          ObjectInputStream in = new ObjectInputStream(fin);
          info = (XMXNodeFileInfo)in.readObject();
          in.close();
      } catch (Exception e) {}
  }
  public void restore()
  {
     restore(tmpfilename);
  }

}// end of HeaderSetPanel CLASS
