package xmminer.xmclient.xmgraph.xmxnode.xmxnodefile.filexscr;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import java.io.*;

import xmminer.xmclient.xmgraph.xmxnode.xmxnodefile.corbautil.*;
import xmminer.xmclient.xmgraph.xmxnode.xmxnodefile.xmscrutil.*;
import xmminer.xmclient.xmgraph.xmxnode.xmxnodefile.filextable.*;
import xmminer.xmclient.xmgraph.xmxnode.xmxnodefile.filextree.ServerDirectoryTreeModel;
import xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.*;

public class ModifyPanel extends JPanel {

  JFrame fr = null;
  public CorbaAgent agent = null;
  public XMXNodeFileInfo info = null;
  final String tmpfilename1 = "xmxnodefile.tmp";
  final String tmpfilename2 = "xmxnodefile2.tmp";
  public DirectoryType[] serverDirList = null;

  private Object[][] data = {{" ", " ", " "},{" ", " ", " "},{" ", " ", " "},{" ", " ", " "},{" ", " ", " "},{" ", " ", " "},{" ", " ", " "}};
  private String[] col = { "컬럼명","컬럼타입","사용여부"};

  Border border1;
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  GridLayout gridLayout1 = new GridLayout();
  JLabel jLabel3 = new JLabel();
  JTextField jText_srcname = new JTextField();
  JButton jBtn_srcSelect = new JButton();
  JPanel jPanel2 = new JPanel();
  JPanel jPanel3 = new JPanel();
  JPanel jPanel4 = new JPanel();
  JButton jBtn_colsInfoSet = new JButton();
  GridBagLayout gridBagLayout3 = new GridBagLayout();
  GridBagLayout gridBagLayout4 = new GridBagLayout();
  JButton jBtn_hdrSelect = new JButton();
  GridBagLayout gridBagLayout5 = new GridBagLayout();
  JLabel jLabel12 = new JLabel();
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  TitledBorder titledBorder2;

  //Icon findIcon = new ImageIcon("./xmminer/xmclient/xmgraph/xmxnode/xmxnodefile/filexscr/edit.gif");
  TitledBorder titledBorder1;
  JRadioButton jRadioButton_comma = new JRadioButton();
  JRadioButton jRadioButton_space = new JRadioButton();
  JRadioButton jRadioButton_semi = new JRadioButton();
  JRadioButton jRadioButton_etc = new JRadioButton();
  ButtonGroup delimeterGroup = new ButtonGroup();
  JTextField jTextField_etc = new JTextField();
  TitledBorder titledBorder3;
  JTextField jTextField_colNum = new JTextField();
  JLabel jLabel1 = new JLabel();
  JScrollPane jScrollPane1 = new JScrollPane();
  JTable jTable1 = new JTable(data, col);
  JPanel jPanel1 = new JPanel();
  TitledBorder titledBorder4;
  GridBagLayout gridBagLayout6 = new GridBagLayout();
  JRadioButton jRadio_ns_none = new JRadioButton();
  JRadioButton jRadio_ns_space = new JRadioButton();
  JRadioButton jRadio_ns_etc = new JRadioButton();
  ButtonGroup nullGroup = new ButtonGroup();
  JTextField jTextField_nullValue = new JTextField();

  Point location = null;
  JLabel jLabel2 = new JLabel();
  JLabel jLabel4 = new JLabel();
  JLabel jLabel5 = new JLabel();
  JLabel jLabel6 = new JLabel();
  JLabel jLabel7 = new JLabel();
  JLabel jLabel8 = new JLabel();
  JLabel jLabel9 = new JLabel();
  JLabel jLabel10 = new JLabel();
  JTextField jTextField_useNum = new JTextField();
  JLabel jLabel11 = new JLabel();

/*
  final SwingWorker worker =  new SwingWorker() {
      				public Object[] construct() {
						System.out.println("\n thread start : get Dir Info And Concate ...");
						DirectoryType[] sDirList = agent.bxnodefile.getDirectoryInfo();
						System.out.println("\n thread end : get Dir Info And Concate ...");
						return sDirList;
      				}
  				};
*/

  public ModifyPanel(JFrame fr, CorbaAgent a, XMXNodeFileInfo i, Point loc) {
    try {
	  this.fr = fr;
      agent = a;
      info = i;
      location = loc;
      jbInit();
      fillContents();
      save(tmpfilename1);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
//    pack();
  }
  private void jbInit() throws Exception {
    border1 = BorderFactory.createRaisedBevelBorder();
    titledBorder2 = new TitledBorder(new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(142, 142, 142)),"대상 파일");
    titledBorder1 = new TitledBorder(new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(142, 142, 142)),"구분 기호");
    titledBorder3 = new TitledBorder(new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(142, 142, 142)),"컬럼 정보");
    titledBorder4 = new TitledBorder(new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(142, 142, 142)),"누락데이터 표시");
    this.setMinimumSize(new Dimension(380, 320));
    this.setPreferredSize(new Dimension(380, 320));
    this.setLayout(gridBagLayout2);
    gridLayout1.setHgap(4);
    jLabel3.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel3.setMaximumSize(new Dimension(60, 18));
    jLabel3.setPreferredSize(new Dimension(55, 18));
    jLabel3.setHorizontalTextPosition(SwingConstants.LEFT);
    jLabel3.setText("파일 이름");
    jBtn_srcSelect.setFont(new java.awt.Font("Dialog", 0, 12));
    jBtn_srcSelect.setBorder(BorderFactory.createEtchedBorder());
    jBtn_srcSelect.setMaximumSize(new Dimension(90, 22));
    jBtn_srcSelect.setMinimumSize(new Dimension(90, 22));
    jBtn_srcSelect.setPreferredSize(new Dimension(90, 22));
    jBtn_srcSelect.setText("수정");
    //jBtn_srcSelect.setIcon(findIcon);
    jBtn_srcSelect.addActionListener(new ModifyPanel_jBtn_srcSelect_actionAdapter(this));
    jBtn_colsInfoSet.setFont(new java.awt.Font("Dialog", 0, 12));
    jBtn_colsInfoSet.setBorder(BorderFactory.createEtchedBorder());
    jBtn_colsInfoSet.setMaximumSize(new Dimension(90, 22));
    jBtn_colsInfoSet.setMinimumSize(new Dimension(90, 22));
    jBtn_colsInfoSet.setPreferredSize(new Dimension(90, 22));
    jBtn_colsInfoSet.setText("수정");
    //jBtn_colsInfoSet.setIcon(findIcon);
    jBtn_colsInfoSet.addActionListener(new ModifyPanel_jBtn_colsInfoSet_actionAdapter(this));
    jPanel2.setBorder(titledBorder2);
    jPanel2.setMinimumSize(new Dimension(360, 65));
    jPanel2.setPreferredSize(new Dimension(360, 65));
    jPanel2.setLayout(gridBagLayout3);
    jPanel3.setBorder(titledBorder1);
    jPanel3.setMinimumSize(new Dimension(360, 65));
    jPanel3.setPreferredSize(new Dimension(360, 65));
    jPanel3.setLayout(gridBagLayout4);
    jBtn_hdrSelect.setFont(new java.awt.Font("Dialog", 0, 12));
    jBtn_hdrSelect.setBorder(BorderFactory.createEtchedBorder());
    jBtn_hdrSelect.setMaximumSize(new Dimension(90, 22));
    jBtn_hdrSelect.setMinimumSize(new Dimension(90, 22));
    jBtn_hdrSelect.setPreferredSize(new Dimension(90, 22));
    jBtn_hdrSelect.setText("수정");
    //jBtn_hdrSelect.setIcon(findIcon);
    jBtn_hdrSelect.addActionListener(new ModifyPanel_jBtn_hdrSelect_actionAdapter(this));
    jPanel4.setBorder(titledBorder3);
    jPanel4.setMinimumSize(new Dimension(360, 75));
    jPanel4.setPreferredSize(new Dimension(640, 120));
    jPanel4.setLayout(gridBagLayout5);
    jText_srcname.setBorder(BorderFactory.createLoweredBevelBorder());
    jText_srcname.setMinimumSize(new Dimension(100, 22));
    jText_srcname.setPreferredSize(new Dimension(280, 22));
    jText_srcname.setEditable(false);
    this.setBorder(BorderFactory.createEtchedBorder());
    this.setMinimumSize(new Dimension(400, 360));
    this.setPreferredSize(new Dimension(500, 360));
    this.addComponentListener(new java.awt.event.ComponentAdapter()
    {

      public void componentShown(ComponentEvent e)
      {
        this_componentShown(e);
      }
    });
    jLabel12.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel12.setText("총 컬럼 수");
//    this.add(jPanel2, new GridBagConstraints(0, 2, 1, 1, 1.0, 0.0
//            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
//    this.add(this, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
//            ,GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(2, 5, 2, 5), 0, 0));
    jRadioButton_comma.setText("쉼표");
    jRadioButton_comma.setFont(new java.awt.Font("Dialog", 0, 12));
    jRadioButton_space.setText("공백");
    jRadioButton_space.setFont(new java.awt.Font("Dialog", 0, 12));
    jRadioButton_semi.setText("세미콜론");
    jRadioButton_semi.setFont(new java.awt.Font("Dialog", 0, 12));
    jRadioButton_etc.setText("기타");
    jRadioButton_etc.setFont(new java.awt.Font("Dialog", 0, 12));
    jTextField_etc.setBorder(BorderFactory.createLoweredBevelBorder());
    jTextField_etc.setPreferredSize(new Dimension(60, 22));
    jTextField_etc.setEditable(false);
    jLabel1.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel1.setText("개");
    jTextField_colNum.setBorder(BorderFactory.createLoweredBevelBorder());
    jTextField_colNum.setMinimumSize(new Dimension(60, 22));
    jTextField_colNum.setPreferredSize(new Dimension(60, 22));
    jTextField_colNum.setEditable(false);
    jTextField_colNum.setHorizontalAlignment(SwingConstants.RIGHT);
//    jTable1.setPreferredSize(new Dimension(300, 100));
    jPanel1.setBorder(titledBorder4);
    jPanel1.setPreferredSize(new Dimension(360, 75));
    jPanel1.setLayout(gridBagLayout6);
    jRadio_ns_none.setText("연속된 구분기호 (a,,b)");
    jRadio_ns_none.setActionCommand("");
    jRadio_ns_none.setFont(new java.awt.Font("Dialog", 0, 12));
    jRadio_ns_none.addItemListener(new java.awt.event.ItemListener() {

      public void itemStateChanged(ItemEvent e) {
        jRadio_ns_none_itemStateChanged(e);
      }
    });
    jRadio_ns_space.setText("공백 (a, ,b)");
    jRadio_ns_space.setFont(new java.awt.Font("Dialog", 0, 12));
    jRadio_ns_space.addItemListener(new java.awt.event.ItemListener() {

      public void itemStateChanged(ItemEvent e) {
        jRadio_ns_space_itemStateChanged(e);
      }
    });
    jRadio_ns_etc.setText("특별한 누락데이터 표시기호");
    jRadio_ns_etc.setFont(new java.awt.Font("Dialog", 0, 12));
    jRadio_ns_etc.addItemListener(new java.awt.event.ItemListener() {

      public void itemStateChanged(ItemEvent e) {
        jRadio_ns_etc_itemStateChanged(e);
      }
    });
    jTextField_nullValue.setBorder(BorderFactory.createLoweredBevelBorder());
    jTextField_nullValue.setPreferredSize(new Dimension(60, 22));
//    jScrollPane1.setPreferredSize(new Dimension(300, 100));
    jLabel4.setText(" ");
    jLabel2.setText(" ");
    jLabel5.setText(" ");
    jLabel6.setText(" ");
    jLabel7.setText(" ");
    jLabel8.setText(" ");
    jScrollPane1.setPreferredSize(new Dimension(500, 160));
    jLabel9.setText(" ");
    jLabel10.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel10.setText("사용 컬럼 수 ");
    jTextField_useNum.setBorder(BorderFactory.createLoweredBevelBorder());
    jTextField_useNum.setMinimumSize(new Dimension(60, 22));
    jTextField_useNum.setPreferredSize(new Dimension(60, 22));
    jTextField_useNum.setEditable(false);
    jTextField_useNum.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel11.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel11.setText("개");
    jTable1.setEnabled(false);
    jTable1.setPreferredSize(new Dimension(500, 160));

    this.add(jPanel2, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(jText_srcname, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 0, 0), 0, 0));
    jPanel2.add(jBtn_srcSelect, new GridBagConstraints(4, 0, 1, 1, 0.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 5), 0, 0));
    jPanel2.add(jLabel3, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel2.add(jLabel2, new GridBagConstraints(0, 0, 1, 1, 0.5, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel2.add(jLabel4, new GridBagConstraints(3, 0, 1, 1, 0.5, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
    this.add(jPanel3, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    jPanel3.add(jBtn_hdrSelect, new GridBagConstraints(7, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 5), 0, 0));
    jPanel3.add(jRadioButton_comma, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 20), 0, 0));
    jPanel3.add(jRadioButton_space, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 20), 0, 0));
    jPanel3.add(jRadioButton_semi, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 20), 0, 0));
    jPanel3.add(jRadioButton_etc, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel3.add(jTextField_etc, new GridBagConstraints(5, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel3.add(jLabel5, new GridBagConstraints(0, 0, 1, 1, 0.5, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
    jPanel3.add(jLabel6, new GridBagConstraints(6, 0, 1, 1, 0.5, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
    this.add(jPanel4, new GridBagConstraints(0, 2, 1, 1, 1.0, 0.5
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 245, 23));
    jPanel4.add(jLabel12, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));
    jPanel4.add(jTextField_colNum, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));
    jPanel4.add(jLabel1, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));
    jPanel4.add(jBtn_colsInfoSet, new GridBagConstraints(8, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 5), 0, 0));
    jPanel4.add(jScrollPane1, new GridBagConstraints(1, 1, 8, 1, 0.5, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 0, 10, 5), 0, 0));
    jPanel4.add(jLabel7, new GridBagConstraints(7, 0, 1, 1, 0.3, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel4.add(jLabel8, new GridBagConstraints(0, 0, 1, 2, 0.3, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
    jPanel4.add(jLabel10, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 15, 0, 0), 0, 0));
    jPanel4.add(jTextField_useNum, new GridBagConstraints(5, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 5, 0, 5), 0, 0));
    jPanel4.add(jLabel11, new GridBagConstraints(6, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    this.add(jPanel1, new GridBagConstraints(0, 3, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(jRadio_ns_none, new GridBagConstraints(1, 0, 1, 1, 0.08, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 25), 0, 0));
    jPanel1.add(jRadio_ns_space, new GridBagConstraints(2, 0, 1, 1, 0.08, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 25), 0, 0));
    jPanel1.add(jRadio_ns_etc, new GridBagConstraints(3, 0, 1, 1, 0.08, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel1.add(jTextField_nullValue, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 5), 0, 0));
    jPanel1.add(jLabel9, new GridBagConstraints(0, 0, 1, 1, 0.7, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
    jScrollPane1.getViewport().add(jTable1, null);

    /** added by YOUNG
        initialize delimetierGroup for column delimiter
        initialize nullGroup for null sign */
    delimeterGroup.add(jRadioButton_comma);
    delimeterGroup.add(jRadioButton_space);
    delimeterGroup.add(jRadioButton_semi);
    delimeterGroup.add(jRadioButton_etc);
    nullGroup.add(jRadio_ns_none);
    nullGroup.add(jRadio_ns_space);
    nullGroup.add(jRadio_ns_etc);
  }

  private void fillContents()
  {
      save(tmpfilename1);
      jText_srcname.setText(info.srcdata.filename);
      if (!info.delimiter.equals("")) prnDelimiter(info.delimiter);
      if (info.numberOfColumns > 0)
          jTextField_colNum.setText(new Integer(info.numberOfColumns).toString());
      if (info.numberOfColumns > 0) printColumnInfoToTable(this.jTable1,120,100,80);
      prnNullValue(info.nullvalue);
  }
  private void prnNullValue(String nullvalue)
  {
      if (nullvalue.equals("")) {
          this.jRadio_ns_none.setSelected(true);
      } else if (nullvalue.equals(" ")) {
          this.jRadio_ns_space.setSelected(true);
      } else {
          this.jRadio_ns_etc.setSelected(true);
          this.jTextField_nullValue.setText(nullvalue);
      }
  }

  private void printColumnInfoToTable(JTable table, int namesize, int typesize, int usesize)
  {
      // column name table
      ColumnNameTableModel model1 = new ColumnNameTableModel(makeColumnNameList(info));
      table.setModel((TableModel)model1);
      TableColumn column = null;
      column = table.getColumnModel().getColumn(0);
      column.setPreferredWidth(namesize);
      column = table.getColumnModel().getColumn(1);
      column.setPreferredWidth(typesize);
      column = table.getColumnModel().getColumn(2);
      column.setPreferredWidth(usesize);
      setupTypeColumn(table.getColumnModel().getColumn(1));
  }
  public void setupTypeColumn(TableColumn columnModel)
  {
        String list[] = {"INTEGER", "FLOAT", "ENUMERATION", "STRING", "DATE" };
        JComboBox comboBox = new JComboBox(list);
        columnModel.setCellEditor(new DefaultCellEditor(comboBox));
  }

	Object[][] makeColumnNameList(XMXNodeFileInfo info)
  {
        Object con[][] = new Object[info.numberOfColumns][3];
        for (int i=0; i<info.numberOfColumns; i++) {
            con[i][0] = (Object)info.columnInfo[i].name;
            con[i][1] = (Object)info.columnInfo[i].type;
            con[i][2] = (Object)(new Boolean(info.columnInfo[i].use));
        }  // end of for

        return con;
  }// end of makeColumnInfoList

  public FilePath createFileSelectDialog(FilePath file, Point loc)
  {
        if (serverDirList == null) {
/*            SwingWorker worker = new SwingWorker() {
              public Object[] construct() {
                return agent.bxnodefile.getDirectoryInfo();
              }
            };
            worker.start();
            CXMDialog.msgDialog(this. "서버에서 디렉토리 정보를 가져오는 중입니다.");
            serverDirList = (DirectoryType[])worker.get();
            */
            serverDirList = agent.bxnodefile.getDirectoryInfo();
        }

        InputFrame frame = new InputFrame();
     	Dialog_FileSelect dial = new Dialog_FileSelect(fr, frame, true, agent, loc, file, serverDirList);

      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
     Dimension dialogSize = dial.getSize();
     if (dialogSize.height > screenSize.height) dialogSize.height = screenSize.height;
     if (dialogSize.width > screenSize.width) dialogSize.width = screenSize.width;
     dial.setLocation((screenSize.width - dialogSize.width) / 2, (screenSize.height - dialogSize.height) / 2);
     dial.setVisible(true);

 //    	dial.setLocation(loc);
 //    	dial.setVisible(true);

	if (frame.inputValues.size() > 0) {
   	    String s = (String)frame.inputValues.get(0);
       	    if (s.equals("OK")) {
          	file = (FilePath)frame.inputValues.get(1);
            	return file;
            }
	}// end of if
	return null;

  }// end of createFileSelectDialog

  void jBtn_srcSelect_actionPerformed(ActionEvent e)
  {
     	Point newLoc = new Point((int)location.getX()+50, (int)location.getY()+50);
		 FilePath file = createFileSelectDialog(info.srcdata, newLoc);


		if (file != null) {
            info.srcdata = file;
            this.jText_srcname.setText(file.filename);
            agent.setAllInfo(info);
        }// end of if
  }// end of jBtn_srcSelect_actionPerformed

  void jBtn_hdrSelect_actionPerformed(ActionEvent e)
  {
     save(tmpfilename2);

     InputFrame frame = new InputFrame();
     Point newLoc = new Point((int)location.getX()+50, (int)location.getY()+50);
     Dialog_HeaderSelect dial = new Dialog_HeaderSelect(fr, frame, "File Extraction", true, agent, newLoc, this);

     Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
     Dimension dialogSize = dial.getSize();
      if (dialogSize.height > screenSize.height) dialogSize.height = screenSize.height;
      if (dialogSize.width > screenSize.width) dialogSize.width = screenSize.width;
            dial.setLocation((screenSize.width - dialogSize.width) / 2, (screenSize.height - dialogSize.height) / 2);
            dial.setVisible(true);


     if (frame.inputValues.size() > 0) {
        String s = (String)frame.inputValues.get(0);
        if (s.equals("OK")) {
            String dtype = (String)frame.inputValues.get(1);
            if (dtype.equals("HEADERFILE")) {
                FilePath t = (FilePath)frame.inputValues.get(2);
                agent.parcing((FilePath)frame.inputValues.get(2));
//                System.out.println("headerfilename is " + t.filename);
            } else {
                agent.parcing((String)frame.inputValues.get(2), info.columnNameLinePointer, info.ignoreLinePointer);
//                System.out.println("delimiter is " + (String)frame.inputValues.get(2));
            }
            info = agent.getAllInfo();

            prnDelimiter(info.delimiter);

        } else {
            restore(tmpfilename2);
            agent.setAllInfo(info);
        }
     }// end of if

  }// end of jBtn_hdrSelect_actionPerformed

  private void prnDelimiter(String del)
  {
      if (del.equals(","))  this.jRadioButton_comma.setSelected(true);
      else if (del.equals(" ")) this.jRadioButton_space.setSelected(true);
      else if (del.equals(";")) this.jRadioButton_semi.setSelected(true);
      else {
                this.jRadioButton_etc.setSelected(true);
                this.jTextField_etc.setText(del);
      }
  }// end of prnDelimiter


  void jBtn_colsInfoSet_actionPerformed(ActionEvent e) {
     InputFrame frame = new InputFrame();
     Dialog_HeaderSet dial = new Dialog_HeaderSet(fr, frame, "File Extraction", true, agent, info);
   //  dial.setLocation(new Point((int)location.getX()+50, (int)location.getY()+50));
     Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
     Dimension dialogSize = dial.getSize();
     if (dialogSize.height > screenSize.height) dialogSize.height = screenSize.height;
     if (dialogSize.width > screenSize.width) dialogSize.width = screenSize.width;
     dial.setLocation((screenSize.width - dialogSize.width) / 2, (screenSize.height - dialogSize.height) / 2);
     dial.setVisible(true);



     String s = (String)frame.inputValues.get(0);
     if (s.equals("OK")) {
        info = (XMXNodeFileInfo)frame.inputValues.get(1);
        this.jTextField_colNum.setText(new Integer(info.numberOfColumns).toString());
        this.printColumnInfoToTable(jTable1,120,100,80);

        int useCount = 0;
        for (int i=0; i<info.numberOfColumns; i++)
            if (info.columnInfo[i].use) useCount++;
        this.jTextField_useNum.setText(new Integer(useCount).toString());
     }// end of if
   }// end of jBtn_colsInfoSet_actionPerformed

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
      try {
          FileInputStream fin = new FileInputStream(tmpfilename1);
          ObjectInputStream in = new ObjectInputStream(fin);
          info = (XMXNodeFileInfo)in.readObject();
          in.close();
      } catch (Exception e) {}
  }
  public void setAllInfo() {
      agent.setAllInfo(info);
  }// end of setAllInfo

  protected void finalize()
  {
      try {
          File file = new File(tmpfilename1);
          file.delete();
          file = new File(tmpfilename2);
          file.delete();
      } catch (Exception e) {
      }
  }// end of finalize

  void this_componentShown(ComponentEvent e)
  {
//      worker.start();
  }

  void jRadio_ns_none_itemStateChanged(ItemEvent e) {
      if (jRadio_ns_none.isSelected()) info.nullvalue = "";
  }

  void jRadio_ns_space_itemStateChanged(ItemEvent e) {
      if (jRadio_ns_space.isSelected()) info.nullvalue = " ";
  }

  void jRadio_ns_etc_itemStateChanged(ItemEvent e) {
      if (jRadio_ns_etc.isSelected()) info.nullvalue = this.jTextField_nullValue.getText();
  }//////////
}

class ModifyPanel_jBtn_srcSelect_actionAdapter implements java.awt.event.ActionListener {
  ModifyPanel adaptee;

  ModifyPanel_jBtn_srcSelect_actionAdapter(ModifyPanel adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jBtn_srcSelect_actionPerformed(e);
  }
}

class ModifyPanel_jBtn_hdrSelect_actionAdapter implements java.awt.event.ActionListener {
  ModifyPanel adaptee;

  ModifyPanel_jBtn_hdrSelect_actionAdapter(ModifyPanel adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jBtn_hdrSelect_actionPerformed(e);
  }
}

class ModifyPanel_jBtn_colsInfoSet_actionAdapter implements java.awt.event.ActionListener {
  ModifyPanel adaptee;

  ModifyPanel_jBtn_colsInfoSet_actionAdapter(ModifyPanel adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jBtn_colsInfoSet_actionPerformed(e);
  }
}

