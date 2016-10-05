package xmminer.xmclient.xmgraph.xmxnode.xmxnodefile.filexscr;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Vector;
import javax.swing.table.TableModel;
//2000/10/13 이창호 수정
import java.lang.String;
import javax.swing.border.*;
import javax.swing.tree.*;

import xmminer.xmclient.xmgraph.xmxnode.xmxnodefile.corbautil.*;
import xmminer.xmclient.xmgraph.xmxnode.xmxnodefile.xmscrutil.*;
import xmminer.xmclient.xmgraph.xmxnode.xmxnodefile.filextable.*;
import xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.*;

public class HeaderSelectPanel extends JPanel {

  //ImageIcon previewIcon = new ImageIcon("./xmminer/xmclient/xmgraph/xmxnode/xmxnodefile/filexscr/brws.gif");
  //ImageIcon searchIcon = new ImageIcon("./xmminer/xmclient/xmgraph/xmxnode/xmxnodefile/filexscr/search.gif");

  JPanel jPanel6 = new JPanel();
  JPanel jPanel8 = new JPanel();
  JLabel jLabel2 = new JLabel();
  JRadioButton jRadioButton_header = new JRadioButton();
  JRadioButton jRadioButton_del = new JRadioButton();
  JLabel jLabel3 = new JLabel();
  JTextField jTextField_hdr = new JTextField();
  JButton jButton_hdrSearch = new JButton();
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  JLabel jLabel5 = new JLabel();
  JLabel jLabel_fname = new JLabel();
  JScrollPane jScrollPane_list = new JScrollPane();
  JList jList_src = new JList();
  JPanel jPanel9 = new JPanel();
  CardLayout cardLayout1 = new CardLayout();
  JButton jButton_preview = new JButton();
  JScrollPane jScrollPane_table = new JScrollPane();
  JTable jTable_src = new JTable();
  JPanel jPanel1 = new JPanel();
  JPanel jPanel2 = new JPanel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  GridBagLayout gridBagLayout3 = new GridBagLayout();
  GridBagLayout gridBagLayout4 = new GridBagLayout();
  GridBagLayout gridBagLayout5 = new GridBagLayout();
  JRadioButton jRadioButton_comma = new JRadioButton();
  JRadioButton jRadioButton_space = new JRadioButton();
  JRadioButton jRadioButton_semi = new JRadioButton();
  JRadioButton jRadioButton_etc = new JRadioButton();
  JTextField jTextField_etcdel = new JTextField();
  JLabel jLabel1 = new JLabel();

  ButtonGroup group = new ButtonGroup();
  ButtonGroup delGroup = new ButtonGroup();

  CorbaAgent agent = null;
  XMXNodeFileInfo info = null;
  public String delimiter = null;
  public boolean hdrfileExist = false;
  public boolean delimiterSelect = false;
  ModifyPanel upperPanel = null;

  Point location = null;
  JButton jButton_setCName = new JButton();
  JButton jButton_setComment = new JButton();
  Border border1;
  JLabel jLabel4 = new JLabel();
  JLabel jLabel6 = new JLabel();
  JTextField jTextField_nameline = new JTextField();
  JLabel jLabel7 = new JLabel();
  JTextField jTextField_comline = new JTextField();
  JLabel jLabel8 = new JLabel();
  JLabel jLabel9 = new JLabel();
  JLabel jLabel10 = new JLabel();

  public HeaderSelectPanel(CorbaAgent a, Point loc, ModifyPanel panel) {
    try  {
      agent = a;
      info = panel.info;
      upperPanel = panel;
      location = loc;
      jbInit();
    fillContents();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    border1 = BorderFactory.createBevelBorder(BevelBorder.RAISED,Color.white,Color.white,new Color(134, 134, 134),new Color(93, 93, 93));
    this.setBorder(BorderFactory.createEtchedBorder());
    this.setMinimumSize(new Dimension(540, 400));
    this.setPreferredSize(new Dimension(640, 420));
    this.setLayout(gridBagLayout1);
    jPanel6.setPreferredSize(new Dimension(520, 140));
    jPanel6.setLayout(gridBagLayout5);
    jPanel8.setPreferredSize(new Dimension(520, 180));
    jPanel8.setLayout(gridBagLayout2);
    jLabel2.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel2.setPreferredSize(new Dimension(294, 30));
    jLabel2.setText("헤더파일과 구분자를 하나를 선택하여 지정해 주십시오.");
    jRadioButton_header.setPreferredSize(new Dimension(120, 22));
    jRadioButton_header.setText("헤더파일 있음");
    jRadioButton_header.setFont(new java.awt.Font("Dialog", 0, 12));
    jRadioButton_header.addMouseListener(new java.awt.event.MouseAdapter()
    {

      public void mouseClicked(MouseEvent e)
      {
        jRadioButton_header_mouseClicked(e);
      }
    });
    jRadioButton_del.setPreferredSize(new Dimension(120, 22));
    jRadioButton_del.setMaximumSize(new Dimension(120, 26));
    jRadioButton_del.setText("구분자 사용");
    jRadioButton_del.setFont(new java.awt.Font("Dialog", 0, 12));
    jRadioButton_del.addMouseListener(new java.awt.event.MouseAdapter()
    {

      public void mouseClicked(MouseEvent e)
      {
        jRadioButton_del_mouseClicked(e);
      }
    });
    jLabel3.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel3.setPreferredSize(new Dimension(80, 22));
    jLabel3.setText("헤더파일 이름");
    jTextField_hdr.setBorder(BorderFactory.createLoweredBevelBorder());
    jTextField_hdr.setMinimumSize(new Dimension(160, 25));
    jTextField_hdr.setPreferredSize(new Dimension(160, 22));
    jButton_hdrSearch.setFont(new java.awt.Font("Dialog", 0, 12));
    jButton_hdrSearch.setBorder(BorderFactory.createEtchedBorder());
    jButton_hdrSearch.setPreferredSize(new Dimension(90, 22));
    jButton_hdrSearch.setText("찾기");
    //jButton_hdrSearch.setIcon(searchIcon);
    jButton_hdrSearch.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        jButton_hdrSearch_actionPerformed(e);
      }
    });
    jLabel_fname.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel_fname.setMinimumSize(new Dimension(30, 22));
    jLabel_fname.setPreferredSize(new Dimension(60, 22));
    jScrollPane_list.setPreferredSize(new Dimension(510, 120));
    jPanel9.setLayout(cardLayout1);
    jButton_preview.setFont(new java.awt.Font("Dialog", 0, 12));
    jButton_preview.setBorder(BorderFactory.createEtchedBorder());
    jButton_preview.setMaximumSize(new Dimension(90, 22));
    jButton_preview.setMinimumSize(new Dimension(83, 22));
    jButton_preview.setPreferredSize(new Dimension(120, 22));
    jButton_preview.setText("미리보기");
    //jButton_preview.setIcon(previewIcon);
    jButton_preview.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        jButton_preview_actionPerformed(e);
      }
    });
    jPanel9.setPreferredSize(new Dimension(510, 150));
    jPanel1.setLayout(gridBagLayout3);
    jPanel1.setBorder(BorderFactory.createEtchedBorder());
    jPanel2.setLayout(gridBagLayout4);
    jPanel2.setBorder(BorderFactory.createEtchedBorder());
    jRadioButton_comma.setText("쉼표");
    jRadioButton_comma.setFont(new java.awt.Font("Dialog", 0, 12));
    jRadioButton_space.setText("공백");
    jRadioButton_space.setFont(new java.awt.Font("Dialog", 0, 12));
    jRadioButton_semi.setText("세미콜론");
    jRadioButton_semi.setFont(new java.awt.Font("Dialog", 0, 12));
    jRadioButton_etc.setText("기타");
    jRadioButton_etc.setFont(new java.awt.Font("Dialog", 0, 12));
    jTextField_etcdel.setMinimumSize(new Dimension(30, 22));
    jTextField_etcdel.setPreferredSize(new Dimension(40, 22));
    jLabel1.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel1.setText("구분자 종류");
    jButton_setCName.setFont(new java.awt.Font("Dialog", 0, 12));
    jButton_setCName.setBorder(BorderFactory.createEtchedBorder());
    jButton_setCName.setPreferredSize(new Dimension(40, 22));
    jButton_setCName.setText("지정");
    jButton_setCName.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        jButton_setCName_actionPerformed(e);
      }
    });
    jButton_setComment.setFont(new java.awt.Font("Dialog", 0, 12));
    jButton_setComment.setBorder(BorderFactory.createEtchedBorder());
    jButton_setComment.setPreferredSize(new Dimension(120, 22));
    jButton_setComment.setText("지정");
    jButton_setComment.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        jButton_setComment_actionPerformed(e);
      }
    });
    jLabel4.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel4.setText("해당 줄을 선택하세요.");
    jLabel6.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel6.setText("변수이름 데이터");
    jLabel7.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel7.setText("커멘트 데이터");
    jTextField_comline.setMinimumSize(new Dimension(30, 22));
    jTextField_comline.setPreferredSize(new Dimension(30, 22));
    jTextField_nameline.setMinimumSize(new Dimension(30, 22));
    jTextField_nameline.setPreferredSize(new Dimension(30, 22));
    jLabel8.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel8.setText("해당 줄을 선택하세요.");
    jLabel9.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel9.setText("결과 미리보기");
    jLabel10.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel10.setText("대상 데이터");
    jLabel5.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel5.setMinimumSize(new Dimension(100, 2));
    jLabel5.setPreferredSize(new Dimension(100, 2));
    this.add(jPanel6, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.5
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 10, 10, 10), 0, 0));
    jPanel6.add(jLabel2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 10, 0, 0), 272, 30));
    jPanel6.add(jPanel1, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.5
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    jPanel1.add(jLabel3, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 10, 10, 0), 0, 0));
    jPanel6.add(jPanel2, new GridBagConstraints(0, 2, 1, 1, 1.0, 0.5
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 0, 0, 0), 0, 0));
    jPanel1.add(jButton_hdrSearch, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));
    jPanel1.add(jRadioButton_header, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 10, 10, 0), 0, 0));
    jPanel1.add(jTextField_hdr, new GridBagConstraints(2, 0, 1, 1, 0.5, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(10, 0, 10, 0), 0, 0));
    jPanel2.add(jRadioButton_etc, new GridBagConstraints(5, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 20, 10, 0), 0, 0));
    jPanel2.add(jTextField_etcdel, new GridBagConstraints(6, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 5, 10, 10), 0, 0));
    jPanel2.add(jLabel1, new GridBagConstraints(1, 0, 1, 1, 0.2, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(10, 15, 10, 10), 0, 0));
    jPanel2.add(jRadioButton_del, new GridBagConstraints(0, 0, 1, 1, 0.2, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 10, 10, 0), 0, 0));
    jPanel2.add(jRadioButton_comma, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 10, 10, 0), 0, 0));
    jPanel2.add(jRadioButton_space, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 20, 10, 0), 0, 0));
    jPanel2.add(jRadioButton_semi, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 20, 10, 0), 0, 0));
    this.add(jPanel8, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.5
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 10, 10, 10), 0, 0));
    jPanel8.add(jLabel5, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel8.add(jPanel9, new GridBagConstraints(0, 2, 9, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    jPanel9.add(jScrollPane_list, "jScrollPane1");
    jScrollPane_list.getViewport().add(jList_src, null);
    jPanel9.add(jScrollPane_table, "jScrollPane2");
    jScrollPane_table.getViewport().add(jTable_src, null);
    jPanel8.add(jButton_preview, new GridBagConstraints(8, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 10, 0), 0, 0));
    jPanel8.add(jButton_setCName, new GridBagConstraints(4, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 10, 15), 0, 0));
    jPanel8.add(jButton_setComment, new GridBagConstraints(7, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 10, 15), 0, 0));
    jPanel8.add(jLabel4, new GridBagConstraints(2, 0, 3, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 0), 0, 0));
    jPanel8.add(jLabel6, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 5, 10, 0), 0, 0));
    jPanel8.add(jTextField_nameline, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 5, 10, 5), 0, 0));
    jPanel8.add(jLabel7, new GridBagConstraints(5, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 5, 10, 0), 0, 0));
    jPanel8.add(jTextField_comline, new GridBagConstraints(6, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 5, 10, 5), 0, 0));
    jPanel8.add(jLabel8, new GridBagConstraints(5, 0, 3, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 0), 0, 0));
    jPanel8.add(jLabel9, new GridBagConstraints(8, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel8.add(jLabel10, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 5), 0, 0));
    jPanel8.add(jLabel_fname, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.SOUTHEAST, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 10, 5), 0, 0));
    jTable_src.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

    group.add(jRadioButton_header);
    group.add(jRadioButton_del);
    delGroup.add(this.jRadioButton_comma);
    delGroup.add(this.jRadioButton_space);
    delGroup.add(this.jRadioButton_semi);
    delGroup.add(this.jRadioButton_etc);

//    setHeaderSelected(true);

//2000/10/13 이창호 수정
//    cardLayout1.addLayoutComponent("LIST", jScrollPane_list);
//    cardLayout1.addLayoutComponent("TABLE", jScrollPane_table);
    cardLayout1.addLayoutComponent(jScrollPane_list, (Object)(new String("LIST")));
    cardLayout1.addLayoutComponent(jScrollPane_table, (Object)(new String("TABLE")));
    cardLayout1.show(jPanel9,"LIST");

    this.setHeaderInputEnabled(false);
    this.setDelimiterInputEnabled(false);
  }

  public void fillContents()
  {
      try {
        String[] lines = agent.getInterface().getLines(info.srcdata, 10);
        jList_src.setListData(lines);
        this.jLabel_fname.setText(info.srcdata.filename);
      } catch (Exception e) {
          CXMDialog.errorDialog(this, e.getMessage());
      }
      if (!info.hdrdata.filename.equals("")) {
          this.jTextField_hdr.setText(info.hdrdata.filename);
      } else if (!info.delimiter.equals("")) {
          prnDelimiter(info.delimiter);
      }
  }

  private void prnDelimiter(String del)
  {
      if (del.equals(","))  this.jRadioButton_comma.setSelected(true);
      else if (del.equals(" ")) this.jRadioButton_space.setSelected(true);
      else if (del.equals(";")) this.jRadioButton_semi.setSelected(true);
      else {
                this.jRadioButton_etc.setSelected(true);
                this.jTextField_etcdel.setText(del);
      }
  }// end of prnDelimiter

  void setHeaderSelected(boolean b)
  {
      hdrfileExist = b;
      if (b) delimiterSelect = false;
      else delimiterSelect = true;
      jRadioButton_header.setSelected(b);
      setHeaderInputEnabled(b);
      setDelimiterInputEnabled(!b);
      jRadioButton_del.setSelected(!b);
  }

  void setHeaderInputEnabled(boolean b)
  {
      jTextField_hdr.setEnabled(b);
      jTextField_hdr.setEditable(b);
      jButton_hdrSearch.setEnabled(b);
  }

  void setDelimiterInputEnabled(boolean b)
  {
      this.jRadioButton_comma.setEnabled(b);
      this.jRadioButton_space.setEnabled(b);
      this.jRadioButton_semi.setEnabled(b);
      this.jRadioButton_etc.setEnabled(b);
  }

  void jButton_hdrSearch_actionPerformed(ActionEvent e)
  {
      Point newLoc = new Point((int)location.getX()+50, (int)location.getY()+50);
      FilePath file = upperPanel.createFileSelectDialog(info.hdrdata, newLoc);
      if (file != null) {
      		info.hdrdata = file;
          System.out.println("selected file : " + info.hdrdata.filename);
          jTextField_hdr.setText(info.hdrdata.filename);
      }
  }
  public FilePath getHeaderFile() { return info.hdrdata; }
  public String getColumnDelimiter()
  {
      if (this.jRadioButton_comma.isSelected()) {
          return ",";
      } else if (this.jRadioButton_space.isSelected()) {
          return " ";
      } else if (this.jRadioButton_semi.isSelected()) {
          return ";";
      } else if (this.jRadioButton_etc.isSelected()) {
          return this.jTextField_etcdel.getText();
      } else {
          return "";
      }
  }

  void jButton_preview_actionPerformed(ActionEvent e)
  {
      System.out.println("preview button is pressed");

      if (jRadioButton_header.isSelected()) {
          if ( (info.hdrdata.path != null) && !(jTextField_hdr.getText().equals("")) )  {
              agent.parcing(info.hdrdata);
              tableRepaint();
          } else CXMDialog.errorDialog(this, "헤더파일 선택버튼을 이용하여 헤더파일을 선택해 주십시오.");
      } else  {                                  //if (jRadioButton_del.isSelected())
          delimiter = getColumnDelimiter();
          if (delimiter.equals(""))  CXMDialog.errorDialog(this, "구분자를 입력해 주십시오.");
          else {
              agent.parcing(delimiter, info.columnNameLinePointer, info.ignoreLinePointer);
              tableRepaint();
          }
      }
  }// end of jButton_preview_actionPerformed

  public void tableRepaint()
  {
      System.out.println("tableRepaint is called");

      try {
          // 서버에서 데이틀 가져와서 테일블에 보여준다.
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

//          String rows[][] = agent.getInterface().getRows(info.srcdata, 10);
          System.out.println("get row : " + sampleData[0][0]);
          MyTableModel model = new MyTableModel(sampleData, makeColumnNameList(sampleData[0].length));
          jTable_src.setModel((TableModel)model);
          cardLayout1.show(jPanel9, "TABLE");
      } catch (Exception e) {
          e.printStackTrace();
          CXMDialog.errorDialog(this, e.getMessage());
      }
	}// end of tableRepaint

  String[] makeColumnNameList(int numOfCols)
  {
        Utility util = new Utility();
        String line[] = new String[numOfCols];
        for (int i=0; i<numOfCols; i++) line[i] = "CName_" + util.int2Str(numOfCols);
        return line;
  }

  void jRadioButton_del_mouseClicked(MouseEvent e)
  {
       setHeaderSelected(false);
  }

  void jRadioButton_header_mouseClicked(MouseEvent e)
  {
       setHeaderSelected(true);
  }

  void jButton_setCName_actionPerformed(ActionEvent e) {
      int count = this.jTable_src.getSelectedRowCount();
      if (count == 1) {
          info.columnNameLinePointer = jTable_src.getSelectedRow();
          info.ignoreLinePointer = info.columnNameLinePointer;
      } else {
          count = this.jList_src.getSelectedIndex();
          if (count >= 0) {
              info.columnNameLinePointer = count;
              info.ignoreLinePointer = info.columnNameLinePointer;
          }
      }
      this.jTextField_nameline.setText(Integer.toString(info.columnNameLinePointer+1));
      this.jTextField_comline.setText(Integer.toString(info.ignoreLinePointer+1));
  }

  void jButton_setComment_actionPerformed(ActionEvent e) {
      int count = this.jTable_src.getSelectedRowCount();
      if (count > 0) {
        if (count > 1) {
          int[] index = jTable_src.getSelectedRows();
          info.ignoreLinePointer = index[count-1];
        } else {
          info.ignoreLinePointer = jTable_src.getSelectedRow();
        }
        return;
      }
      int[] index = this.jList_src.getSelectedIndices();
      info.ignoreLinePointer = index[index.length-1];
      this.jTextField_comline.setText("1 - " + Integer.toString(info.ignoreLinePointer+1));
  }

}// end of HeaderSelectPanel CLASS
