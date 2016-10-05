package xmminer.xmclient.xmgraph.xmxnode.xmxnodefile.filexscr;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.event.*;
import java.io.*;
import javax.swing.table.TableColumnModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.tree.*;
//import javax.swing.event.TableModelEvent;

import xmminer.xmclient.xmgraph.xmxnode.xmxnodefile.corbautil.*;
import xmminer.xmclient.xmgraph.xmxnode.xmxnodefile.filextable.*;
import xmminer.xmclient.xmgraph.xmxnode.xmxnodefile.xmscrutil.*;
import xmminer.xmclient.xmgraph.xmxnode.xmxnodefile.filextree.*;
import xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.*;

public class FileSelectPanel extends JPanel {
  JFrame fr = null;
  JTabbedPane jTabbedPane1 = new JTabbedPane();
  JPanel jPanel1 = new JPanel();
  JPanel jPanel2 = new JPanel();
  JSplitPane jSplitPane1 = new JSplitPane();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  JPanel jPanel3 = new JPanel();
  JLabel jLabel1 = new  JLabel();
  GridBagLayout gridBagLayout3 = new GridBagLayout();
  public JComboBox jComboBox_loc = new JComboBox();
  JLabel jLabel2 = new JLabel();
  JTextField jTextField_name = new JTextField();
  JButton jButton_preview = new JButton();
  JButton jButton_upload = new JButton();
  JPanel jPanel4 = new JPanel();
  JPanel jPanel5 = new JPanel();

 // ImageIcon up = new ImageIcon("./xmminer/xmclient/xmgraph/xmxnode/xmxnodefile/filexscr/gnicup.gif");
    ImageIcon folderIcon = new ImageIcon("./xmminer/xmclient/xmgraph/xmxnode/xmxnodefile/filexscr/cf.gif");
//  Icon folderIcon = UIManager.getIcon("Tree.closedIcon");
    ImageIcon openfolderIcon = new ImageIcon("./xmminer/xmclient/xmgraph/xmxnode/xmxnodefile/filexscr/of.gif");
//  Icon openfolderIcon = UIManager.getIcon("Tree.openIcon");
//  ImageIcon plusIcon = new ImageIcon("./xmminer/xmclient/xmgraph/xmxnode/xmxnodefile/filexscr/plus.gif");
//  ImageIcon minusIcon = new ImageIcon("./xmminer/xmclient/xmgraph/xmxnode/xmxnodefile/filexscr/minus.gif");
//  ImageIcon previewIcon = new ImageIcon("./xmminer/xmclient/xmgraph/xmxnode/xmxnodefile/filexscr/brws.gif");
 // ImageIcon myComIcon = new ImageIcon("./xmminer/xmclient/xmgraph/xmxnode/xmxnodefile/filexscr/sngcomp.gif");
 // ImageIcon serverIcon = new ImageIcon("./xmminer/xmclient/xmgraph/xmxnode/xmxnodefile/filexscr/house_t.gif");

  GridBagLayout gridBagLayout4 = new GridBagLayout();
  JLabel jLabel3 = new JLabel();
  JScrollPane jScrollPane1 = new JScrollPane();
  JTree jTree_sdir = new JTree();
  GridBagLayout gridBagLayout5 = new GridBagLayout();
  JLabel jLabel4 = new JLabel();
  JScrollPane jScrollPane2 = new JScrollPane();
  JTable jTable_sdir = null;//new JTable();
  GridBagLayout gridBagLayout6 = new GridBagLayout();
  JSplitPane jSplitPane2 = new JSplitPane();
  JPanel jPanel6 = new JPanel();
  JPanel jPanel7 = new JPanel();
  GridBagLayout gridBagLayout7 = new GridBagLayout();
  JLabel jLabel5 = new JLabel();
  JScrollPane jScrollPane3 = new JScrollPane();
  JScrollPane jScrollPane4 = new JScrollPane();
  JLabel jLabel6 = new JLabel();
  GridBagLayout gridBagLayout8 = new GridBagLayout();
  JTree jTree_cdir = new JTree();
  JTable jTable_cdir = null;//new JTable();

  DefaultTableCellRenderer right = new DefaultTableCellRenderer();

  CorbaAgent agent = null;
  DirectoryType[] sDirList = null;
  int selectedServerDirTreeIndex = 1;
  String selectedClientDirTreeNode = null;
  int selectedServerFileIndex = -1;
  public FilePath selectedServerFile = null;
  String selectedClientFile = null;
  String dirDel = System.getProperty("file.separator");

  Point location = null;
  public ClientTreeMakerThread ctreeThread = null;
  ClientDirectoryTreeModel ctreeModel = null;

  public FileSelectPanel(JFrame fr, CorbaAgent a, Point loc, FilePath selectedFile, DirectoryType[] dList)
  {
    	try  {
		    this.fr = fr;
      		agent = a;
      		location = loc;
      		init(dList);
      		sDirList = dList;
      		jTree_sdir = new JTree(new ServerDirectoryTreeModel(new DefaultMutableTreeNode(dList[0].nodeName), dList));
      		jbInit();
      		fillContents(selectedFile);
    	} catch(Exception ex) {
      		ex.printStackTrace();
    	}
  }

  private void init(DirectoryType[] dList)
  {
    	sDirList = dList;
    	ServerDirectoryTreeModel streeModel = new ServerDirectoryTreeModel(new DefaultMutableTreeNode(sDirList[0].nodeName), sDirList);
    	jTree_sdir = new JTree((TreeModel)streeModel);
  }
  private void jbInit() throws Exception
  {
    ctreeModel = new ClientDirectoryTreeModel(new DefaultMutableTreeNode("내 컴퓨터"));
    jTree_cdir = new JTree((TreeModel)ctreeModel);
    jTree_cdir.addTreeWillExpandListener(new DirWillExpandListener());
    jTree_cdir.setShowsRootHandles(true);
    jTree_cdir.setCellRenderer(new DirectoryCellRenderer());

    jTable_cdir = new JTable()
	 {
			public TableCellRenderer getCellRenderer(int row, int column)
			{
				TableColumn tableColumn = getColumnModel().getColumn(column);
				TableCellRenderer renderer = tableColumn.getCellRenderer();
				if (renderer == null) {
					Class c = getColumnClass(column);
					if( c.equals(Object.class) )
					{
						Object o = getValueAt(row,column);
						if( o != null )
							c = getValueAt(row,column).getClass();
					}
					renderer = getDefaultRenderer(c);
				}
				return renderer;
			}// end of getCellRenderer

		};
		jTable_cdir.setDefaultRenderer( JComponent.class, new JComponentCellRenderer() );

    	jTable_sdir = new JTable()
	 	{
			public TableCellRenderer getCellRenderer(int row, int column)
			{
				TableColumn tableColumn = getColumnModel().getColumn(column);
				TableCellRenderer renderer = tableColumn.getCellRenderer();
				if (renderer == null) {
					Class c = getColumnClass(column);
					if( c.equals(Object.class) )
					{
						Object o = getValueAt(row,column);
						if( o != null )
							c = getValueAt(row,column).getClass();
					}
					renderer = getDefaultRenderer(c);
				}
				return renderer;
			}// end of getCellRenderer

		};
		jTable_sdir.setDefaultRenderer( JComponent.class, new JComponentCellRenderer() );

    this.setLayout(gridBagLayout2);
    jPanel1.setLayout(gridBagLayout1);
    jPanel3.setPreferredSize(new Dimension(400, 30));
    jPanel3.setLayout(gridBagLayout3);
    jLabel1.setFont(new java.awt.Font("Dialog", 0, 12));
	jLabel1.setText("선택된 파일 위치");
    jLabel2.setFont(new java.awt.Font("Dialog", 0, 12));
	jLabel2.setText("선택된 파일 이름");
    jTextField_name.setBorder(BorderFactory.createLoweredBevelBorder());
    jTextField_name.setMinimumSize(new Dimension(120, 22));
    jTextField_name.setPreferredSize(new Dimension(120, 22));
    jTextField_name.setEditable(false);
    jButton_preview.setFont(new java.awt.Font("Dialog", 0, 12));
	jButton_preview.setBorder(BorderFactory.createEtchedBorder());
    jButton_preview.setMaximumSize(new Dimension(90, 22));
    jButton_preview.setMinimumSize(new Dimension(90, 22));
    jButton_preview.setPreferredSize(new Dimension(90, 22));
    jButton_preview.setText("미리보기");
    // jButton_preview.setIcon(previewIcon);
    jButton_preview.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        jButton_preview_actionPerformed(e);
      }
    });
    jButton_upload.setFont(new java.awt.Font("Dialog", 0, 12));
	jButton_upload.setBorder(BorderFactory.createEtchedBorder());
    jButton_upload.setMaximumSize(new Dimension(90, 22));
    jButton_upload.setMinimumSize(new Dimension(90, 22));
    jButton_upload.setPreferredSize(new Dimension(90, 22));
    jButton_upload.setText("Upload");
    //jButton_upload.setIcon(up);
    jButton_upload.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        jButton_upload_actionPerformed(e);
      }
    });
    jComboBox_loc.setEnabled(false);
	jComboBox_loc.setFont(new java.awt.Font("Dialog", 0, 12));
    jComboBox_loc.setMinimumSize(new Dimension(100, 22));
    jComboBox_loc.setPreferredSize(new Dimension(100, 22));
    jPanel4.setLayout(gridBagLayout4);
    jLabel3.setFont(new java.awt.Font("Dialog", 0, 12));
	jLabel3.setText("모든 폴더");
    jPanel5.setLayout(gridBagLayout5);
    jLabel4.setFont(new java.awt.Font("Dialog", 0, 12));
	jLabel4.setText("내용");
    jPanel2.setLayout(gridBagLayout6);
    jPanel6.setLayout(gridBagLayout7);
    jLabel5.setFont(new java.awt.Font("Dialog", 0, 12));
	jLabel5.setText("모든 폴더");
    jLabel6.setFont(new java.awt.Font("Dialog", 0, 12));
	jLabel6.setText("내용");
    jPanel7.setLayout(gridBagLayout8);
    jTree_sdir.addMouseListener(new java.awt.event.MouseAdapter() {

      public void mouseClicked(MouseEvent e) {
        jTree_sdir_mouseClicked(e);
      }
    });
    jTable_sdir.addMouseListener(new java.awt.event.MouseAdapter() {

      public void mouseClicked(MouseEvent e) {
        jTable_sdir_mouseClicked(e);
      }
    });
    jTree_cdir.addMouseListener(new java.awt.event.MouseAdapter() {

      public void mouseClicked(MouseEvent e) {
        jTree_cdir_mouseClicked(e);
      }
    });
    jTable_cdir.addMouseListener(new java.awt.event.MouseAdapter() {

      public void mouseClicked(MouseEvent e) {
        jTable_cdir_mouseClicked(e);
      }
    });
    jTree_cdir.addTreeExpansionListener(new javax.swing.event.TreeExpansionListener() {

      public void treeCollapsed(TreeExpansionEvent e) {
      }

      public void treeExpanded(TreeExpansionEvent e) {
        jTree_cdir_treeExpanded(e);
      }
    });
    jTable_cdir.setShowHorizontalLines(false);
    jTable_cdir.setShowVerticalLines(false);
    jTable_sdir.setShowHorizontalLines(false);
    jTable_sdir.setShowVerticalLines(false);
    jScrollPane2.getViewport().setBackground(Color.white);
    jScrollPane2.setBorder(BorderFactory.createLoweredBevelBorder());
    jScrollPane4.getViewport().setBackground(Color.white);
    jTree_sdir.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener()
    {

      public void valueChanged(TreeSelectionEvent e)
      {
        jTree_sdir_valueChanged(e);
      }
    });
    jTree_sdir.addTreeExpansionListener(new javax.swing.event.TreeExpansionListener()
    {

      public void treeCollapsed(TreeExpansionEvent e)
      {
      }

      public void treeExpanded(TreeExpansionEvent e)
      {
        jTree_sdir_treeExpanded(e);
      }
    });
    this.setBorder(BorderFactory.createEtchedBorder());
	jTabbedPane1.setFont(new java.awt.Font("Dialog", 0, 12));
	this.add(jTabbedPane1, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    jTabbedPane1.add(jPanel1, " 마이닝 서버 ");
    jPanel1.add(jSplitPane1, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 10, 10, 10), 0, 0));
    jSplitPane1.add(jPanel4, JSplitPane.TOP);
    jPanel4.add(jLabel3, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));
    jPanel4.add(jScrollPane1, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    jScrollPane1.getViewport().add(jTree_sdir, null);
    jSplitPane1.add(jPanel5, JSplitPane.BOTTOM);
    jPanel5.add(jLabel4, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 5, 0, 0), 0, 0));
    jPanel5.add(jScrollPane2, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    jScrollPane2.getViewport().add(jTable_sdir, null);
    jTabbedPane1.add(jPanel2, " 내 컴퓨터 ");
    jPanel2.add(jSplitPane2, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 10, 10, 10), 0, 0));
    jSplitPane2.add(jPanel6, JSplitPane.LEFT);
    jPanel6.add(jLabel5, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));
    jPanel6.add(jScrollPane3, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    jScrollPane3.getViewport().add(jTree_cdir, null);
    jSplitPane2.add(jPanel7, JSplitPane.RIGHT);
    jPanel7.add(jScrollPane4, new GridBagConstraints(0, 1, GridBagConstraints.REMAINDER, GridBagConstraints.REMAINDER, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    jScrollPane4.getViewport().add(jTable_cdir, null);
    jPanel7.add(jLabel6, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));
    this.add(jPanel3, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 10, 10, 10), 0, 0));
    jPanel3.add(jLabel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 5), 0, 0));
    jPanel3.add(jComboBox_loc, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel3.add(jLabel2, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 10, 0, 5), 0, 0));
    jPanel3.add(jTextField_name, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel3.add(jButton_preview, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 10, 0, 5), 0, 0));
    jPanel3.add(jButton_upload, new GridBagConstraints(5, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jSplitPane1.setDividerLocation(200);

    /** added by YOUNG
       initialize comboBox */
    this.jComboBox_loc.addItem("마이닝 서버");
    this.jComboBox_loc.addItem("내 컴퓨터");
    this.jComboBox_loc.setSelectedIndex(0);

    /** added by YOUNG
       replace tree node icon */
    DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
    renderer.setLeafIcon(folderIcon);
    renderer.setOpenIcon(openfolderIcon);
    renderer.setClosedIcon(folderIcon);
    renderer.setIcon(folderIcon);
    this.jTree_cdir.setCellRenderer(renderer);
    DefaultTreeCellRenderer renderer1 = new DefaultTreeCellRenderer();
    renderer1.setLeafIcon(folderIcon);
    renderer1.setOpenIcon(openfolderIcon);
    renderer1.setClosedIcon(folderIcon);
    renderer1.setIcon(folderIcon);
    this.jTree_sdir.setCellRenderer(renderer1);
    jSplitPane2.setDividerLocation(200);

    right.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    // end of replace part

  }//end of jbinit

  private TreePath makeMatchedTreePath(String[] path)
  {
      int i;
      DefaultMutableTreeNode[] nodeList =  new DefaultMutableTreeNode[path.length+1];
      TreePath currentPath = this.jTree_sdir.getPathForRow(0);
      DefaultMutableTreeNode cnode = (DefaultMutableTreeNode)currentPath.getLastPathComponent();
      nodeList[0] = cnode;
      DefaultMutableTreeNode nnode = null;
      for (i=0; i<path.length; i++) {
              int count = cnode.getChildCount();
              int index = 0;
              // 현재 노드의 자식노드중 값이 맞는 것을 찾아서 다음 노드로 선택
              do {
                  nnode = (DefaultMutableTreeNode)cnode.getChildAt(index);
                  if (nnode.toString().equals(path[i])) {
                      cnode = nnode;
                      nodeList[i+1] = cnode;
                      break;
                  } else {
                      index++;
                  }
              } while (index < count);
              // 없으면 실패
              if (index >= count) return null;
      }// end of for
      // 찾았으면 패쓰를 리턴
      return new TreePath(nodeList);
  }// end of makeMatchedTreePath

  private void fillContents(FilePath file)
  {
    TreePath matchedPath = null;
    System.out.println("fill contents ... filename is " + file.filename);
    if (file.path.length > 1)   System.out.println(file.path[0] + " - " + file.path[1]);
    // initialize
    if (      ( file.path.length > 0 )
           && ( !file.filename.equals("") )
           && ( (matchedPath=makeMatchedTreePath(file.path)) != null )         ) {
      try {
          DefaultTreeSelectionModel treeSelectionModel = (DefaultTreeSelectionModel)jTree_sdir.getSelectionModel();
          treeSelectionModel.setSelectionPath(matchedPath);

          // set server tree
          this.selectedServerDirTreeIndex = catchSelectedDirectory();
          refreshServerTable(selectedServerDirTreeIndex);

          // set server table
          DefaultListSelectionModel model = (DefaultListSelectionModel)jTable_sdir.getSelectionModel();
          int tindex = searchSelctedIndex(selectedServerDirTreeIndex, file.filename);
          model.setSelectionInterval(tindex,tindex);
          int selectedIndex = jTable_sdir.getSelectedRow();
          JLabel label = (JLabel) jTable_sdir.getValueAt(selectedIndex,2);
          if (label.getText().equals("file")) {
              String index = sDirList[selectedServerDirTreeIndex].childnodeNames[selectedIndex];
              selectedServerFileIndex = new Integer(index).intValue();
              selectedServerFile = getSelectedServerFile();
              prnSelectedServerFile(selectedServerFileIndex);
          }
      } catch (Exception e) {
          e.printStackTrace();
      }
    } else {
          this.selectedServerDirTreeIndex = 0;
//          this.jTree_sdir.setSelectionPath(this.jTree_sdir.getPathForRow(0));
          refreshServerTable(selectedServerDirTreeIndex);

          this.jButton_preview.setEnabled(false);
          this.jButton_upload.setEnabled(false);
    }// end of if else
    this.selectedClientDirTreeNode = "C:";
    this.jTree_cdir.setSelectionPath(this.jTree_cdir.getPathForRow(2));
    refreshClientTable(selectedClientDirTreeNode);
  }// end of fillContents

  void jButton_preview_actionPerformed(ActionEvent e) {
      int loc = jTabbedPane1.getSelectedIndex();
      if (loc == 0) {
          Dialog_Preview dial = new Dialog_Preview(fr, "Data Preview", true, selectedServerFile, agent);
          Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
          Dimension dialogSize = dial.getSize();
          if (dialogSize.height > screenSize.height) dialogSize.height = screenSize.height;
          if (dialogSize.width > screenSize.width) dialogSize.width = screenSize.width;
          dial.setLocation((screenSize.width - dialogSize.width) / 2, (screenSize.height - dialogSize.height) / 2);
          dial.setVisible(true);
      } else {
          Dialog_Preview dial = new Dialog_Preview(fr, "Data Preview", true, this.jTextField_name.getText());
          dial.setLocation(50,50);
          dial.setVisible(true);
      }
  }// end of jButton_preview_actionPerformed

  void jButton_upload_actionPerformed(ActionEvent e) {
     int loc = jTabbedPane1.getSelectedIndex();
      if (loc == 1) {
          FileUploader loader = new FileUploader(agent.bxnodefile);
          int index = jTable_cdir.getSelectedRow();
          JLabel label = (JLabel) jTable_cdir.getValueAt(index,0);
          boolean b = loader.upload(selectedClientFile, label.getText());
          if (b) {
              sDirList = agent.bxnodefile.getDirectoryInfo();
              DirectoryInfoMaker dirInfo = new DirectoryInfoMaker();
              jTree_sdir = dirInfo.makeServerDirectoryInfo(sDirList);
              DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
               renderer.setLeafIcon(folderIcon);
               renderer.setOpenIcon(folderIcon);
               renderer.setClosedIcon(folderIcon);
              this.jTree_sdir.setCellRenderer(renderer);
              jScrollPane1.getViewport().add(jTree_sdir, null);
              String[] t = {"DIR1", this.agent.prjname};
              fillContents(new FilePath(t, label.getText()));
          }
          CXMDialog.msgDialog(this.jPanel1, "파일 업로드가 끝났습니다. 서버에서 파일을 선택해 주십시오.");
          this.jTabbedPane1.setSelectedIndex(0);
      } else {
          CXMDialog.errorDialog(this.jPanel1, "파일 업로드는 클라이언트에 있는 파일에 대해서만 가능합니다.");
      }
  }// end of jButton_upload_actionPerformed

  void jTree_sdir_mouseClicked(MouseEvent e) {
  } // end of jTree_sdir_mouseClicked

  void jTable_sdir_mouseClicked(MouseEvent e) throws NumberFormatException {
//      System.out.println("jTable_sdir_mouseClicked");
      int selectedIndex = jTable_sdir.getSelectedRow();
		JLabel label = (JLabel) jTable_sdir.getValueAt(selectedIndex,2);
      if (label.getText().equals("file")) {
          String index = sDirList[selectedServerDirTreeIndex].childnodeNames[selectedIndex];
          selectedServerFileIndex = new Integer(index).intValue();
          selectedServerFile = getSelectedServerFile();
          prnSelectedServerFile(selectedServerFileIndex);
      }
  }// end of jTable_sdir_mouseClicked

   void jTree_cdir_mouseClicked(MouseEvent e) {
      String selectedfilename = getSelectedClientFilename();
      System.out.println("jTree_cdir_mouseClicked : " + selectedfilename);
      if (selectedfilename != null) {
        if (!selectedfilename.equals(selectedClientDirTreeNode)) {
          selectedClientDirTreeNode = selectedfilename;
          refreshClientTable(selectedfilename);
        }
      }
  }// end of jTree_cdir_mouseClicked

  void  refreshClientTable(String selectedfilename) {
     System.out.println("refreshClientTable " + selectedfilename);
     DirectoryTableModel model = new DirectoryTableModel(selectedfilename);
     jTable_cdir.setModel((TableModel)model);
  }

  void jTree_cdir_treeExpanded(TreeExpansionEvent e) {
//      DefaultTreeModel tmodel = (DefaultTreeModel)jTree_cdir.getModel();
//      tmodel.reload();
  }

  void jTable_cdir_mouseClicked(MouseEvent e) {
//      System.out.println("jTable_cdir_mouseClicked");
      int selectedIndex = jTable_cdir.getSelectedRow();
      JLabel label = (JLabel)jTable_cdir.getValueAt(selectedIndex,2);
//      JLabel nlabel = (JLabel)jTable_cdir.getValueAt(selectedIndex,0);
//     nlabel.setForeground(Color.darkGray);
//     jTable_cdir.setValueAt(nlabel, selectedIndex,0);
//      jTable_cdir.tableChanged(null);
      if (label.getText().equals("file")) {
      		label = (JLabel)jTable_cdir.getValueAt(selectedIndex,0);
          selectedClientFile =  this.selectedClientDirTreeNode + dirDel
                              + label.getText();
          prnSelectedClientFile(selectedClientFile);
      }
  }// end of jTable_cdir_mouseClicked

  int catchSelectedDirectory() throws NumberFormatException
  {
      int nextIndex = 0;
      TreePath tpath = jTree_sdir.getLeadSelectionPath();
      Object[] path = tpath.getPath();
      int currentIndex = 0;
      for (int i=1; i<path.length; i++) {
          for (int j=0; j<sDirList[currentIndex].childnodeNames.length; j++) {
              nextIndex = new Integer(sDirList[currentIndex].childnodeNames[j]).intValue();
              if ( sDirList[nextIndex].nodeName.equals(path[i].toString()) ) {
                currentIndex = nextIndex;
                break;
              }
          }
      }
      return currentIndex;
  }

  void prnSelectedServerFile(int index)
  {
      this.jComboBox_loc.setSelectedIndex(0);
      this.jTextField_name.setText(sDirList[selectedServerFileIndex].nodeName);
      this.jButton_preview.setEnabled(true);
      this.jButton_upload.setEnabled(false);
  }
  void prnSelectedClientFile(String name)
  {
      this.jComboBox_loc.setSelectedIndex(1);
      this.jTextField_name.setText(name);
      this.jButton_preview.setEnabled(true);
      this.jButton_upload.setEnabled(true);
  }

  String getSelectedClientFilename()
  {
      TreePath selectedPath = jTree_cdir.getLeadSelectionPath();
      if (selectedPath == null) return null;
      Object[] path = selectedPath.getPath();
      String selectedfilename = path[1].toString();
      for (int i=2; i<path.length; i++)
          selectedfilename += dirDel + path[i].toString();
      System.out.println("selected file name : " + selectedfilename);
      return selectedfilename;
//      	} catch(InterruptedException e) { return null;}
  }

  private int searchSelctedIndex(int index, String filename)
  {
      int size = sDirList[index].childnodeNames.length;
      int j;
      int i=0;
      while ( i < size ) {
      			j = new Integer(sDirList[index].childnodeNames[i]).intValue();
      			if (sDirList[j].nodeName.equals(filename)) break;
      			i++;
      	}
      return i;
  }

  void  refreshServerTable(int selectedIndex) {
      DirectoryTableModel model1 = new DirectoryTableModel(sDirList, selectedIndex);
      jTable_sdir.setModel((TableModel)model1);
 }

  private FilePath getSelectedServerFile()
  {
      TreePath tpath = jTree_sdir.getLeadSelectionPath();
      Object[] p = tpath.getPath();
      String[] path = new String[p.length-1];
      for (int i=0; i<path.length; i++) {
           path[i] = (String)p[i+1].toString();
      }
      return new FilePath(path, sDirList[selectedServerFileIndex].nodeName);
  }

    private String getFullpath(DefaultMutableTreeNode cnode)
    {
          TreeNode[] tpath = cnode.getPath();
          StringBuffer fullpath = new StringBuffer(64);
          fullpath.append(tpath[1].toString());
          for (int i=2; i<tpath.length; i++) fullpath.append(dirDel).append(tpath[i]);
          return fullpath.toString();
    }

    private void addChild(DefaultMutableTreeNode cnode)
	 {
          if (cnode.isLeaf()) {
                String fullpath = getFullpath(cnode);
                File[] filelist = new File(fullpath).listFiles();
				if(filelist != null){ // add 2002.09.12 by cliff w. lee
					for (int i=0; i<filelist.length; i++) {
                  		if (filelist[i].isDirectory()) {
                      			cnode.add(new DefaultMutableTreeNode(filelist[i].getName()));
                  		}
					}// end of for
				}
          } // end of if
    }// end of addChild

  class DirWillExpandListener implements TreeWillExpandListener
  {
  		public void treeWillExpand(TreeExpansionEvent e)
  		{
  				System.out.println("treeWillExpand is called....");
  				DefaultMutableTreeNode nnode = null;
  				TreePath path = e.getPath();
      			DefaultMutableTreeNode pnode = (DefaultMutableTreeNode) path.getLastPathComponent();
      			if (!pnode.isLeaf()) {
      					int childCount = pnode.getChildCount();
      					for (int i=0; i<childCount; i++)
      							addChild((DefaultMutableTreeNode)pnode.getChildAt(i));
      			}// end of if
  		}// end of treeWillExpand

  		public void treeWillCollapse(TreeExpansionEvent e)
  		{
   		}
  }// end of inner class DirWillExpandListener

	class JComponentCellRenderer implements TableCellRenderer
	{
    public Component getTableCellRendererComponent(JTable table, Object value,
		boolean isSelected, boolean hasFocus, int row, int column) {
        return (JComponent)value;
    }
	}

  void jTree_sdir_valueChanged(TreeSelectionEvent e)
  {
			System.out.println("jTree_sdir_valueChanged");
      		this.selectedServerDirTreeIndex = catchSelectedDirectory();
      		refreshServerTable(selectedServerDirTreeIndex);
  }

  void jTree_sdir_treeExpanded(TreeExpansionEvent e)
  {
			System.out.println("jTree_sdir_treeExpanded");
  }

}// end of CLASS
