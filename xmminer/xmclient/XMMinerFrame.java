
//Title:      XM-BRENIC/Miner
//Version:
//Copyright:  Copyright (c) 1999
//Author:     Yong Uk Song
//Company:    DIS
//Description:XM-BRENIC/Miner

package xmminer.xmclient;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

import xmminer.xmlib.*;
import xmminer.xmclient.xmgraph.*;
import java.util.ResourceBundle;

public class XMMinerFrame extends JFrame{
  static ResourceBundle res;
  Image imageMain;
  // Menu
  ImageIcon imageMenuBlank = new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/DLGBLANK.gif"));
  JMenuBar menuBar = new JMenuBar();
  JMenu menuFile = new JMenu();
  JMenuItem menuFileNew = new JMenuItem(new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/new.gif")));
  JMenuItem menuFileOpen = new JMenuItem(new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/open.gif")));
  JMenuItem menuFileSave = new JMenuItem(new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/save.gif")));
  JMenuItem menuFileSaveAs = new JMenuItem(new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/saveas.gif")));
  JMenuItem menuFilePrint = new JMenuItem(new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/print.gif")));
  JMenuItem menuFileExit = new JMenuItem(new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/exit.gif")));
  JMenu menuEdit = new JMenu();
  JMenuItem menuEditDelete = new JMenuItem(new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/del.gif")));
  JMenuItem menuEditSelectAll = new JMenuItem(new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/selectall.gif")));
  JMenuItem menuEditUnselect = new JMenuItem(new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/unselect.gif")));
  JMenu menuDraw = new JMenu();
  JMenu menuDrawAdd = new JMenu();
  JMenuItem menuDrawAddExtractFile = new JMenuItem(new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/file_s.gif")));
  JMenuItem menuDrawAddExtractDB = new JMenuItem(new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/DB_s.gif")));
  JMenuItem menuDrawAddPartitioning = new JMenuItem(new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/part_s.gif")));
  JMenuItem menuDrawAddSampling = new JMenuItem(new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/samp_s.gif")));
  JMenuItem menuDrawAddColumnCompute = new JMenuItem(new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/comp_s.gif")));
  JMenuItem menuDrawAddColumnSelect = new JMenuItem(new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/sel_s.gif")));
  JMenuItem menuDrawAddDataQuery = new JMenuItem(new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/query_s.gif")));
  JMenuItem menuDrawAddRegression = new JMenuItem(new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/reg_s.gif")));
  JMenuItem menuDrawAddCorrelation = new JMenuItem(new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/cor_s.gif")));
  JMenuItem menuDrawAddAssociationRule = new JMenuItem(new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/asso_s.gif")));
  JMenuItem menuDrawAddSequenceRule = new JMenuItem(new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/seq_s.gif")));
  JMenuItem menuDrawAddEpisodeRule = new JMenuItem(new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/epi_s.gif")));
  JMenuItem menuDrawAddC45 = new JMenuItem(new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/c45_s.gif")));
  JMenuItem menuDrawAddNeuralNetwork = new JMenuItem(new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/nn_s.gif")));
  JMenuItem menuDrawAddPrediction = new JMenuItem(new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/pre_s.gif")));
  JMenuItem menuDrawAddVisual = new JMenuItem(new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/data_s.gif")));
  JMenuItem menuDrawConnect = new JMenuItem(new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/connect.gif")));
  JMenuItem menuDrawProperties = new JMenuItem(new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/properties.gif")));
  JMenuItem menuDrawSelect = new JMenuItem(new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/select.gif")));
  JMenu menuRun = new JMenu();
  JMenuItem menuRunRun = new JMenuItem(new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/run.gif")));
  JMenuItem menuRunRerun = new JMenuItem(new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/rerun.gif")));
  JMenuItem menuRunRunToHere = new JMenuItem(new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/runtohere.gif")));
  JMenuItem menuRunRunAlone = new JMenuItem(new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/runalone.gif")));
  JMenu menuHelp = new JMenu();
  JMenuItem menuHelpAbout = new JMenuItem(new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/about.gif")));
  // Tool Bar
  JToolBar toolBar = new JToolBar();
  JButton toolBarOpen = new JButton();
  JButton toolBarSave = new JButton();
  JButton toolBarPrint = new JButton();
  JButton toolBarHelp = new JButton();
  JButton toolBarProperties = new JButton();
  JButton toolBarConnect = new JButton();
  JButton toolBarRun = new JButton();
  JButton toolBarRunAlone = new JButton();
  ImageIcon imageOpenFile;
  ImageIcon imageSaveFile;
  ImageIcon imagePrintFile;
  ImageIcon imageHelp;
  ImageIcon imageProperties;
  ImageIcon imageConnect;
  ImageIcon imageRun;
  ImageIcon imageRunAlone;
  JToolBar nodeBar = new JToolBar();
  JButton nodeBarAddExtractFile = new JButton();
  JButton nodeBarAddExtractDB = new JButton();
  JButton nodeBarAddPartitioning = new JButton();
  JButton nodeBarAddSampling = new JButton();
  JButton nodeBarAddColumnCompute = new JButton();
  JButton nodeBarAddColumnSelect = new JButton();
  JButton nodeBarAddDataQuery = new JButton();
  JButton nodeBarAddRegression = new JButton();
  JButton nodeBarAddCorrelation = new JButton();
  JButton nodeBarAddAssociationRule = new JButton();
  JButton nodeBarAddSequenceRule = new JButton();
  JButton nodeBarAddEpisodeRule = new JButton();
  JButton nodeBarAddC45 = new JButton();
  JButton nodeBarAddNeuralNetwork = new JButton();
  JButton nodeBarAddPrediction = new JButton();
  JButton nodeBarAddVisual = new JButton();
  ImageIcon imageAddExtractFile;
  ImageIcon imageAddExtractDB;
  ImageIcon imageAddPartitioning;
  ImageIcon imageAddSampling;
  ImageIcon imageAddColumnCompute;
  ImageIcon imageAddColumnSelect;
  ImageIcon imageAddDataQuery;
  ImageIcon imageAddRegression;
  ImageIcon imageAddCorrelation;
  ImageIcon imageAddAssociationRule;
  ImageIcon imageAddSequenceRule;
  ImageIcon imageAddEpisodeRule;
  ImageIcon imageAddC45;
  ImageIcon imageAddNeuralNetwork;
  ImageIcon imageAddPrediction;
  ImageIcon imageAddVisual;
  // Status Bar
  JLabel statusBar = new JLabel();
  // Window
  BorderLayout layout = new BorderLayout();
  XMGraph m_graph;
  private int flgLan;

  class XMGraphPanel extends JPanel //implements java.awt.print.Printable
  {
    Frame m_frame;

    public XMGraphPanel(Frame frame)
    {
      super();
      m_frame = frame;
    }

    public void paintComponent(Graphics g)
    {
      super.paintComponent(g);
      if (m_graph != null)
      {
        m_graph.Paint(m_frame, g);
      }
    }

	public void Print(){
      java.awt.Toolkit toolkit = this.getToolkit();
      java.awt.PrintJob job = toolkit.getPrintJob(m_frame, "XM-Miner V1.5", null);

      if(job == null) return;
      java.awt.Graphics g = job.getGraphics();

      g.translate(10, 10);
      java.awt.Dimension size = this.getSize();
      g.setClip(0, 0, size.width, size.height);

      this.print(g);

      g.dispose();
      job.end();
	}

/* 페이지에 맞게 출력... 잠시 유보됨... 색깔문제...
	public void Print(){
	  java.awt.print.PrinterJob job = java.awt.print.PrinterJob.getPrinterJob();

	  java.awt.print.PageFormat pf = job.pageDialog(job.defaultPage());
	  
	  job.setPrintable(this, pf);

	  if(job.printDialog()){
	    try{
		  job.print();
		}catch(Exception e){
		  e.printStackTrace();
		}
	  }
	}

	public int print(Graphics g, java.awt.print.PageFormat pf, int pageIndex){
	  if(pageIndex != 0){
	    return NO_SUCH_PAGE;
	  }
	  Graphics2D g2 = (Graphics2D)g;

      java.awt.Dimension size = this.getSize();//      size.width, size.height
	  g2.translate((int)pf.getImageableX(), (int)pf.getImageableY());

	  if(((size.width-300.0f)>pf.getImageableWidth())&((size.height-300.0f)>pf.getImageableHeight())){
	    //둘다 크다면...
	    g2.scale((float)pf.getImageableWidth()/(size.width-300.0f),(float)pf.getImageableHeight()/(size.height-300.0f));
	  }else if((size.width-300.0f)>pf.getImageableWidth()){
 	    //폭만 크다면...
	    g2.scale((float)pf.getImageableWidth()/(size.width-300.0f),1);
	  }else if((size.height-300.0f)>pf.getImageableHeight()){
	    //길이만 크다면...
	    g2.scale(1,(float)pf.getImageableHeight()/(size.height-300.0f));
	  }else{
	    //둘다 작다면...
	    g2.scale(1,1);
	  }

      g2.setClip(0, 0, size.width, size.height);
	  paint(g);
	  return PAGE_EXISTS;
	}
*/
  }

  public XMGraphPanel graphPanel = new XMGraphPanel(this);
  public JScrollPane scrollPane = new JScrollPane(graphPanel);

  public void AddToGraphPanel(XMGraphPanel graphPanel, Component comp)
  {
    graphPanel.add(comp);
  }

  public void RemoveFromGraphPanel(XMGraphPanel graphPanel, Component comp)
  {
    graphPanel.remove(comp);
  }

  // Popup Menu
  JPopupMenu popupArc = new JPopupMenu();
  JMenuItem popupArcProperties = new JMenuItem();

  JPopupMenu popupOW = new JPopupMenu();
  JMenu popupOWAdd = new JMenu();
  JMenuItem popupOWAddExtractFile = new JMenuItem(new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/file_s.gif")));
  JMenuItem popupOWAddExtractDB = new JMenuItem(new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/DB_s.gif")));
  JMenuItem popupOWAddPartitioning = new JMenuItem(new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/part_s.gif")));
  JMenuItem popupOWAddSampling = new JMenuItem(new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/samp_s.gif")));
  JMenuItem popupOWAddColumnCompute = new JMenuItem(new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/comp_s.gif")));
  JMenuItem popupOWAddColumnSelect = new JMenuItem(new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/sel_s.gif")));
  JMenuItem popupOWAddDataQuery = new JMenuItem(new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/query_s.gif")));
  JMenuItem popupOWAddRegression = new JMenuItem(new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/reg_s.gif")));
  JMenuItem popupOWAddCorrelation = new JMenuItem(new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/cor_s.gif")));
  JMenuItem popupOWAddAssociationRule = new JMenuItem(new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/asso_s.gif")));
  JMenuItem popupOWAddSequenceRule = new JMenuItem(new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/seq_s.gif")));
  JMenuItem popupOWAddEpisodeRule = new JMenuItem(new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/epi_s.gif")));
  JMenuItem popupOWAddC45 = new JMenuItem(new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/c45_s.gif")));
  JMenuItem popupOWAddNeuralNetwork = new JMenuItem(new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/nn_s.gif")));
  JMenuItem popupOWAddPrediction = new JMenuItem(new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/pre_s.gif")));
  JMenuItem popupOWAddVisual = new JMenuItem(new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/data_s.gif")));
  JMenuItem popupOWConnect = new JMenuItem(new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/connect.gif")));

  // Etc
  JFileChooser m_fileChooser;

  //Construct the frame
  public XMMinerFrame(int Lan)
  {
    flgLan = Lan; //default language = english
	if (flgLan==1) res = ResourceBundle.getBundle("xmminer.xmclient.XMMinerFrame_Res_Ko");
	else res = ResourceBundle.getBundle("xmminer.xmclient.XMMinerFrame_Res_En");

    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try
    {
      jbInit();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

  //Component initialization
  private void jbInit() throws Exception
  {
    // Window
    this.getContentPane().setLayout(layout);
    this.setSize(new Dimension(800, 640));
    this.setTitle("XM-Miner V1.5");

    //icon
    imageMain = Toolkit.getDefaultToolkit().getImage(xmminer.xmclient.XMMinerFrame.class.getResource("images/title_small.gif"));
    this.setIconImage(imageMain);

    // Menu
    menuFile.setMnemonic('F');
    menuFile.setText(res.getString("File"));
    menuFileNew.setMnemonic('N');
    menuFileNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
    menuFileNew.setText(res.getString("New"));
    menuFileNew.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        menuFileNew_actionPerformed(e);
      }
    });
    menuFileOpen.setMnemonic('O');
    menuFileOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
    menuFileOpen.setText(res.getString("Open"));
    menuFileOpen.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        menuFileOpen_actionPerformed(e);
      }
    });
    menuFileSave.setMnemonic('S');
    menuFileSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
    menuFileSave.setText(res.getString("Save"));
    menuFileSave.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        menuFileSave_actionPerformed(e);
      }
    });
    menuFileSaveAs.setMnemonic('A');
    menuFileSaveAs.setText(res.getString("Save_as_"));
    menuFileSaveAs.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        menuFileSaveAs_actionPerformed(e);
      }
    });
    menuFilePrint.setMnemonic('P');
    menuFilePrint.setText(res.getString("Print")); //need language setting
    menuFilePrint.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        menuFilePrint_actionPerformed(e);
      }
    });
    menuFileExit.setMnemonic('X');
    menuFileExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.ALT_MASK));
    menuFileExit.setText(res.getString("Exit"));
    menuFileExit.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        fileExit_actionPerformed(e);
      }
    });
    menuEdit.setMnemonic('E');
    menuEdit.setText(res.getString("Edit"));
    menuEditDelete.setMnemonic('D');
    menuEditDelete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, ActionEvent.SHIFT_MASK));
    menuEditDelete.setText(res.getString("Delete"));
    menuEditDelete.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        menuEditDelete_actionPerformed(e);
      }
    });
    menuEditSelectAll.setMnemonic('A');
    menuEditSelectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
    menuEditSelectAll.setText(res.getString("Select_all"));
    menuEditSelectAll.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        menuEditSelectAll_actionPerformed(e);
      }
    });
    menuEditUnselect.setMnemonic('U');
    menuEditUnselect.setAccelerator(KeyStroke.getKeyStroke(KeyEvent. VK_ESCAPE, ActionEvent.SHIFT_MASK));
    menuEditUnselect.setText(res.getString("Unselect"));
    menuEditUnselect.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        menuEditUnselect_actionPerformed(e);
      }
    });
    menuDraw.setMnemonic('D');
    menuDraw.setText(res.getString("Draw"));
    menuDrawAdd.setMnemonic('A');
    menuDrawAdd.setText(res.getString("Add"));
    menuDrawAdd.setIcon(new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/add.gif")));
    menuDrawAddExtractFile.setMnemonic('F');
    menuDrawAddExtractFile.setText(res.getString("Extract_from_file"));
    menuDrawAddExtractFile.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        menuDrawAddExtractFile_actionPerformed(e);
      }
    });
    menuDrawAddExtractDB.setMnemonic('X');
    menuDrawAddExtractDB.setText(res.getString("Extract_from_DB"));
    menuDrawAddExtractDB.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        menuDrawAddExtractDB_actionPerformed(e);
      }
    });
    menuDrawAddPartitioning.setMnemonic('P');
    menuDrawAddPartitioning.setText(res.getString("Partitioning"));
    menuDrawAddPartitioning.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        menuDrawAddPartitioning_actionPerformed(e);
      }
    });
    menuDrawAddSampling.setMnemonic('S');
    menuDrawAddSampling.setText(res.getString("Sampling"));
    menuDrawAddSampling.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        menuDrawAddSampling_actionPerformed(e);
      }
    });
    menuDrawAddColumnCompute.setMnemonic('U');
    menuDrawAddColumnCompute.setText(res.getString("Column_Compute"));
    menuDrawAddColumnCompute.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        menuDrawAddColumnCompute_actionPerformed(e);
      }
    });
    menuDrawAddColumnSelect.setMnemonic('L');
    menuDrawAddColumnSelect.setText(res.getString("Column_Select"));
    menuDrawAddColumnSelect.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        menuDrawAddColumnSelect_actionPerformed(e);
      }
    });
    menuDrawAddDataQuery.setMnemonic('Y');
    menuDrawAddDataQuery.setText(res.getString("Data_Query"));
    menuDrawAddDataQuery.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        menuDrawAddDataQuery_actionPerformed(e);
      }
    });
    menuDrawAddRegression.setMnemonic('R');
    menuDrawAddRegression.setText(res.getString("Regression"));
    menuDrawAddRegression.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        menuDrawAddRegression_actionPerformed(e);
      }
    });
    menuDrawAddCorrelation.setMnemonic('O');
    menuDrawAddCorrelation.setText(res.getString("Correlation"));
    menuDrawAddCorrelation.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        menuDrawAddCorrelation_actionPerformed(e);
      }
    });
	menuDrawAddAssociationRule.setMnemonic('A');
    menuDrawAddAssociationRule.setText(res.getString("Association_rule"));
    menuDrawAddAssociationRule.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        menuDrawAddAssociationRule_actionPerformed(e);
      }
    });
    menuDrawAddSequenceRule.setMnemonic('Q');
    menuDrawAddSequenceRule.setText(res.getString("Sequence_rule"));
    menuDrawAddSequenceRule.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        menuDrawAddSequenceRule_actionPerformed(e);
      }
    });
    menuDrawAddEpisodeRule.setMnemonic('E');
    menuDrawAddEpisodeRule.setText(res.getString("Episode_rule"));
    menuDrawAddEpisodeRule.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        menuDrawAddEpisodeRule_actionPerformed(e);
      }
    });
    menuDrawAddC45.setMnemonic('4');
    menuDrawAddC45.setText(res.getString("C4_5"));
    menuDrawAddC45.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        menuDrawAddC45_actionPerformed(e);
      }
    });
    menuDrawAddNeuralNetwork.setMnemonic('N');
    menuDrawAddNeuralNetwork.setText(res.getString("Neural_network"));
    menuDrawAddNeuralNetwork.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        menuDrawAddNeuralNetwork_actionPerformed(e);
      }
    });
    menuDrawAddPrediction.setMnemonic('P');
    menuDrawAddPrediction.setText(res.getString("Prediction"));
    menuDrawAddPrediction.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        menuDrawAddPrediction_actionPerformed(e);
      }
    });
    menuDrawAddVisual.setMnemonic('D');
    menuDrawAddVisual.setText(res.getString("Data"));
    menuDrawAddVisual.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        menuDrawAddVisual_actionPerformed(e);
      }
    });
    menuDrawConnect.setMnemonic('C');
    menuDrawConnect.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.CTRL_MASK));
    menuDrawConnect.setText(res.getString("Connect"));
    menuDrawConnect.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        menuDrawConnect_actionPerformed(e);
      }
    });
    menuDrawProperties.setMnemonic('P');
    menuDrawProperties.setText(res.getString("Properties"));
    menuDrawProperties.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        menuDrawProperties_actionPerformed(e);
      }
    });
    menuDrawSelect.setMnemonic('S');
    menuDrawSelect.setText(res.getString("Select"));
    menuDrawSelect.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        menuDrawSelect_actionPerformed(e);
      }
    });
    menuRun.setMnemonic('R');
    menuRun.setText(res.getString("Run_menu"));
    menuRunRun.setMnemonic('R');
    menuRunRun.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
    menuRunRun.setText(res.getString("Run"));
    menuRunRun.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        menuRunRun_actionPerformed(e);
      }
    });
    menuRunRerun.setMnemonic('E');
    menuRunRerun.setText(res.getString("Rerun"));
    menuRunRerun.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        menuRunRerun_actionPerformed(e);
      }
    });
    menuRunRunToHere.setMnemonic('H');
    menuRunRunToHere.setText(res.getString("Run_to_here"));
    menuRunRunToHere.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        menuRunRunToHere_actionPerformed(e);
      }
    });
    menuRunRunAlone.setMnemonic('A');
    menuRunRunAlone.setText(res.getString("Run_alone"));
    menuRunRunAlone.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        menuRunRunAlone_actionPerformed(e);
      }
    });
    menuHelp.setMnemonic('H');
    menuHelp.setText(res.getString("Help"));
    menuHelpAbout.setMnemonic('A');
    menuHelpAbout.setText(res.getString("About"));
    menuHelpAbout.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        helpAbout_actionPerformed(e);
      }
    });
    menuFile.add(menuFileNew);
    menuFile.add(menuFileOpen);
    menuFile.add(menuFileSave);
    menuFile.add(menuFileSaveAs);
    menuFile.addSeparator();
    menuFile.add(menuFilePrint);
    menuFile.addSeparator();
    menuFile.add(menuFileExit);
    menuEdit.add(menuEditDelete);
    menuEdit.addSeparator();
    menuEdit.add(menuEditSelectAll);
    menuEdit.add(menuEditUnselect);
    menuDraw.add(menuDrawAdd);
    menuDrawAdd.add(menuDrawAddExtractFile);
    menuDrawAdd.add(menuDrawAddExtractDB);
    menuDrawAdd.addSeparator();
    menuDrawAdd.add(menuDrawAddPartitioning);
    menuDrawAdd.add(menuDrawAddSampling);
    menuDrawAdd.add(menuDrawAddColumnSelect);
    menuDrawAdd.add(menuDrawAddDataQuery);
    menuDrawAdd.addSeparator();
    menuDrawAdd.add(menuDrawAddRegression);
    menuDrawAdd.add(menuDrawAddAssociationRule);
    menuDrawAdd.add(menuDrawAddSequenceRule);
    menuDrawAdd.add(menuDrawAddC45);
    menuDrawAdd.add(menuDrawAddNeuralNetwork);
    menuDrawAdd.addSeparator();
    menuDrawAdd.add(menuDrawAddVisual);
    menuDraw.add(menuDrawConnect);
    menuDraw.add(menuDrawProperties);
    menuDraw.add(menuDrawSelect);
    menuRun.add(menuRunRun);
    menuRun.add(menuRunRerun);
    menuRun.addSeparator();
    menuRun.add(menuRunRunToHere);
    menuRun.add(menuRunRunAlone);
    menuHelp.add(menuHelpAbout);
    menuBar.add(menuFile);
    menuBar.add(menuEdit);
    menuBar.add(menuDraw);
    menuBar.add(menuRun);
    menuBar.add(menuHelp);
    this.setJMenuBar(menuBar);

    toolBarOpen.setBorder(BorderFactory.createEtchedBorder());
    toolBarSave.setBorder(BorderFactory.createEtchedBorder());
    toolBarPrint.setBorder(BorderFactory.createEtchedBorder());
    toolBarProperties.setBorder(BorderFactory.createEtchedBorder());
    toolBarConnect.setBorder(BorderFactory.createEtchedBorder());
    toolBarRun.setBorder(BorderFactory.createEtchedBorder());
    toolBarRunAlone.setBorder(BorderFactory.createEtchedBorder());
    toolBarHelp.setBorder(BorderFactory.createEtchedBorder());
    nodeBarAddExtractFile.setBorder(BorderFactory.createEtchedBorder());
    nodeBarAddExtractDB.setBorder(BorderFactory.createEtchedBorder());
    nodeBarAddPartitioning.setBorder(BorderFactory.createEtchedBorder());
    nodeBarAddSampling.setBorder(BorderFactory.createEtchedBorder());
    nodeBarAddColumnCompute.setBorder(BorderFactory.createEtchedBorder());
    nodeBarAddColumnSelect.setBorder(BorderFactory.createEtchedBorder());
    nodeBarAddDataQuery.setBorder(BorderFactory.createEtchedBorder());
    nodeBarAddRegression.setBorder(BorderFactory.createEtchedBorder());
    nodeBarAddCorrelation.setBorder(BorderFactory.createEtchedBorder());
    nodeBarAddAssociationRule.setBorder(BorderFactory.createEtchedBorder());
    nodeBarAddSequenceRule.setBorder(BorderFactory.createEtchedBorder());
    nodeBarAddEpisodeRule.setBorder(BorderFactory.createEtchedBorder());
    nodeBarAddC45.setBorder(BorderFactory.createEtchedBorder());
    nodeBarAddNeuralNetwork.setBorder(BorderFactory.createEtchedBorder());
    nodeBarAddPrediction.setBorder(BorderFactory.createEtchedBorder());
    nodeBarAddVisual.setBorder(BorderFactory.createEtchedBorder());
    // Tool Bar
    imageOpenFile = new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/open.gif"));
    imageSaveFile = new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/save.gif"));
    imagePrintFile = new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/print.gif"));
    imageHelp = new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/about.gif"));
    imageProperties = new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/properties.gif"));
    imageConnect = new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/connect.gif"));
    imageRun = new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/run.gif"));
    imageRunAlone = new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/runalone.gif"));
    imageAddExtractFile = new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/file_s.gif"));
    imageAddExtractDB = new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/DB_s.gif"));
    imageAddPartitioning = new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/part_s.gif"));
    imageAddSampling = new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/samp_s.gif"));
    imageAddColumnCompute = new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/comp_s.gif"));
    imageAddColumnSelect = new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/sel_s.gif"));
    imageAddDataQuery = new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/query_s.gif"));
    imageAddRegression = new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/reg_s.gif"));
    imageAddCorrelation = new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/cor_s.gif"));
    imageAddAssociationRule = new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/asso_s.gif"));
    imageAddSequenceRule = new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/seq_s.gif"));
    imageAddEpisodeRule = new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/epi_s.gif"));
    imageAddC45 = new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/c45_s.gif"));
    imageAddNeuralNetwork = new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/nn_s.gif"));
    imageAddPrediction = new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/pre_s.gif"));
    imageAddVisual = new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/data_s.gif"));
    toolBarOpen.setIcon(imageOpenFile);
    toolBarOpen.setToolTipText(res.getString("Open_File"));
    toolBarOpen.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        toolBarOpen_actionPerformed(e);
      }
    });
    toolBarSave.setIcon(imageSaveFile);
    toolBarSave.setToolTipText(res.getString("Save_File"));
    toolBarSave.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        toolBarSave_actionPerformed(e);
      }
    });
    toolBarPrint.setIcon(imagePrintFile);
    toolBarPrint.setToolTipText(res.getString("Print"));
    toolBarPrint.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        toolBarPrint_actionPerformed(e);
      }
    });
    toolBarHelp.setIcon(imageHelp);
    toolBarHelp.setToolTipText(res.getString("Help_about"));
    toolBarHelp.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        toolBarHelp_actionPerformed(e);
      }
    });
    toolBarProperties.setIcon(imageProperties);
    toolBarProperties.setToolTipText(res.getString("Properties_node"));
    toolBarProperties.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        toolBarProperties_actionPerformed(e);
      }
    });
    toolBarConnect.setIcon(imageConnect);
    toolBarConnect.setToolTipText(res.getString("Connect_node"));
    toolBarConnect.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        toolBarConnect_actionPerformed(e);
      }
    });
    toolBarRun.setIcon(imageRun);
    toolBarRun.setToolTipText(res.getString("Run_all"));
    toolBarRun.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        toolBarRun_actionPerformed(e);
      }
    });
    toolBarRunAlone.setIcon(imageRunAlone);
    toolBarRunAlone.setToolTipText(res.getString("RunAlone"));
    toolBarRunAlone.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        toolBarRunAlone_actionPerformed(e);
      }
    });

	toolBar.addSeparator();
    toolBar.add(toolBarOpen);
    toolBar.add(toolBarSave);
    toolBar.add(toolBarPrint);
	toolBar.addSeparator();
    toolBar.add(toolBarProperties);
    toolBar.add(toolBarConnect);
    toolBar.add(toolBarRun);
    toolBar.add(toolBarRunAlone);
	toolBar.addSeparator();
    toolBar.add(toolBarHelp);
	toolBar.setFloatable(false);

    nodeBarAddExtractFile.setIcon(imageAddExtractFile);
    nodeBarAddExtractFile.setToolTipText(res.getString("Extract_From_File"));
    nodeBarAddExtractFile.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        nodeBarAddExtractFile_actionPerformed(e);
      }
    });
    nodeBarAddExtractDB.setIcon(imageAddExtractDB);
    nodeBarAddExtractDB.setToolTipText(res.getString("Extract_From_DB"));
    nodeBarAddExtractDB.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        nodeBarAddExtractDB_actionPerformed(e);
      }
    });
    nodeBarAddPartitioning.setIcon(imageAddPartitioning);
    nodeBarAddPartitioning.setToolTipText(res.getString("Partitioning"));
    nodeBarAddPartitioning.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        nodeBarAddPartitioning_actionPerformed(e);
      }
    });
    nodeBarAddSampling.setIcon(imageAddSampling);
    nodeBarAddSampling.setToolTipText(res.getString("Sampling"));
    nodeBarAddSampling.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        nodeBarAddSampling_actionPerformed(e);
      }
    });
    nodeBarAddColumnCompute.setIcon(imageAddColumnCompute);
    nodeBarAddColumnCompute.setToolTipText(res.getString("Column_Compute"));
    nodeBarAddColumnCompute.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        nodeBarAddColumnCompute_actionPerformed(e);
      }
    });
    nodeBarAddColumnSelect.setIcon(imageAddColumnSelect);
    nodeBarAddColumnSelect.setToolTipText(res.getString("Column_Select"));
    nodeBarAddColumnSelect.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        nodeBarAddColumnSelect_actionPerformed(e);
      }
    });
    nodeBarAddDataQuery.setIcon(imageAddDataQuery);
    nodeBarAddDataQuery.setToolTipText(res.getString("Data_Query"));
    nodeBarAddDataQuery.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        nodeBarAddDataQuery_actionPerformed(e);
      }
    });
    nodeBarAddRegression.setIcon(imageAddRegression);
    nodeBarAddRegression.setToolTipText(res.getString("Regression"));
    nodeBarAddRegression.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        nodeBarAddRegression_actionPerformed(e);
      }
    });
    nodeBarAddCorrelation.setIcon(imageAddCorrelation);
    nodeBarAddCorrelation.setToolTipText(res.getString("Correlation"));
    nodeBarAddCorrelation.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        nodeBarAddCorrelation_actionPerformed(e);
      }
    });
    nodeBarAddAssociationRule.setIcon(imageAddAssociationRule);
    nodeBarAddAssociationRule.setToolTipText(res.getString("Association_Rule"));
    nodeBarAddAssociationRule.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        nodeBarAddAssociationRule_actionPerformed(e);
      }
    });
    nodeBarAddSequenceRule.setIcon(imageAddSequenceRule);
    nodeBarAddSequenceRule.setToolTipText(res.getString("Sequence_Rule"));
    nodeBarAddSequenceRule.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        nodeBarAddSequenceRule_actionPerformed(e);
      }
    });
    nodeBarAddEpisodeRule.setIcon(imageAddEpisodeRule);
    nodeBarAddEpisodeRule.setToolTipText(res.getString("Episode_Rule"));
    nodeBarAddEpisodeRule.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        nodeBarAddEpisodeRule_actionPerformed(e);
      }
    });
    nodeBarAddC45.setIcon(imageAddC45);
    nodeBarAddC45.setToolTipText(res.getString("C4_51"));
    nodeBarAddC45.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        nodeBarAddC45_actionPerformed(e);
      }
    });
    nodeBarAddNeuralNetwork.setIcon(imageAddNeuralNetwork);
    nodeBarAddNeuralNetwork.setToolTipText(res.getString("Neural_Network"));
    nodeBarAddNeuralNetwork.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        nodeBarAddNeuralNetwork_actionPerformed(e);
      }
    });
    nodeBarAddPrediction.setIcon(imageAddPrediction);
    nodeBarAddPrediction.setToolTipText(res.getString("Prediction"));
    nodeBarAddPrediction.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        nodeBarAddPrediction_actionPerformed(e);
      }
    });
    nodeBarAddVisual.setIcon(imageAddVisual);
    nodeBarAddVisual.setToolTipText(res.getString("Data"));
    nodeBarAddVisual.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        nodeBarAddVisual_actionPerformed(e);
      }
    });

	nodeBar.addSeparator();
	nodeBar.add(nodeBarAddExtractFile);
	nodeBar.add(nodeBarAddExtractDB);
	nodeBar.addSeparator();
	nodeBar.add(nodeBarAddPartitioning);
	nodeBar.add(nodeBarAddSampling);
	nodeBar.add(nodeBarAddColumnSelect);
	nodeBar.add(nodeBarAddDataQuery);
	nodeBar.addSeparator();
	nodeBar.add(nodeBarAddRegression);
	nodeBar.add(nodeBarAddAssociationRule);
	nodeBar.add(nodeBarAddSequenceRule);
	nodeBar.add(nodeBarAddC45);
	nodeBar.add(nodeBarAddNeuralNetwork);
	nodeBar.addSeparator();
	nodeBar.add(nodeBarAddVisual);
	nodeBar.setOrientation(javax.swing.SwingConstants.VERTICAL);
	nodeBar.setFloatable(false);
    // Status Bar
    statusBar.setText(res.getString("Select_a_menu"));
    // Window
    graphPanel.setBackground(Color.white);
    graphPanel.setLayout(null);
    graphPanel.addMouseListener(new java.awt.event.MouseAdapter()
    {
      public void mousePressed(MouseEvent e)
      {
        graphPanel_mousePressed(e);
      }
      public void mouseReleased(MouseEvent e)
      {
        graphPanel_mouseReleased(e);
      }
    });
    graphPanel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter()
    {
      public void mouseDragged(MouseEvent e)
      {
        graphPanel_mouseDragged(e);
      }
    });
    this.getContentPane().add(toolBar, BorderLayout.NORTH);
    this.getContentPane().add(nodeBar, BorderLayout.WEST);
    this.getContentPane().add(statusBar, BorderLayout.SOUTH);
    this.getContentPane().add(scrollPane, BorderLayout.CENTER);
    // Popup Menu
    popupArcProperties.setMnemonic('P');
    popupArcProperties.setText(res.getString("Properties"));
    popupArcProperties.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        popupArcProperties_actionPerformed(e);
      }
    });

    popupOWAdd.setMnemonic('A');
    popupOWAdd.setText(res.getString("Add"));
    popupOWAdd.setIcon(new ImageIcon(xmminer.xmclient.XMMinerFrame.class.getResource("images/add.gif")));

    popupOWAddExtractFile.setMnemonic('F');
    popupOWAddExtractFile.setText(res.getString("Extract_from_file"));
    popupOWAddExtractFile.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        popupOWAddExtractFile_actionPerformed(e);
      }
    });
    popupOWAddExtractDB.setMnemonic('X');
    popupOWAddExtractDB.setText(res.getString("Extract_from_DB"));
    popupOWAddExtractDB.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        popupOWAddExtractDB_actionPerformed(e);
      }
    });
    popupOWAddPartitioning.setMnemonic('P');
    popupOWAddPartitioning.setText(res.getString("Partitioning"));
    popupOWAddPartitioning.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        popupOWAddPartitioning_actionPerformed(e);
      }
    });
    popupOWAddSampling.setMnemonic('S');
    popupOWAddSampling.setText(res.getString("Sampling"));
    popupOWAddSampling.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        popupOWAddSampling_actionPerformed(e);
      }
    });
    popupOWAddColumnCompute.setMnemonic('U');
    popupOWAddColumnCompute.setText(res.getString("Column_Compute"));
    popupOWAddColumnCompute.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        popupOWAddColumnCompute_actionPerformed(e);
      }
    });
    popupOWAddColumnSelect.setMnemonic('L');
    popupOWAddColumnSelect.setText(res.getString("Column_Select"));
    popupOWAddColumnSelect.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        popupOWAddColumnSelect_actionPerformed(e);
      }
    });
    popupOWAddDataQuery.setMnemonic('Y');
    popupOWAddDataQuery.setText(res.getString("Data_Query"));
    popupOWAddDataQuery.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        popupOWAddDataQuery_actionPerformed(e);
      }
    });
    popupOWAddRegression.setMnemonic('R');
    popupOWAddRegression.setText(res.getString("Regression"));
    popupOWAddRegression.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        popupOWAddRegression_actionPerformed(e);
      }
    });
    popupOWAddCorrelation.setMnemonic('O');
    popupOWAddCorrelation.setText(res.getString("Correlation"));
    popupOWAddCorrelation.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        popupOWAddCorrelation_actionPerformed(e);
      }
    });
    popupOWAddAssociationRule.setMnemonic('A');
    popupOWAddAssociationRule.setText(res.getString("Association_rule"));
    popupOWAddAssociationRule.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        popupOWAddAssociationRule_actionPerformed(e);
      }
    });
    popupOWAddSequenceRule.setMnemonic('Q');
    popupOWAddSequenceRule.setText(res.getString("Sequence_rule"));
    popupOWAddSequenceRule.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        popupOWAddSequenceRule_actionPerformed(e);
      }
    });
    popupOWAddEpisodeRule.setMnemonic('E');
    popupOWAddEpisodeRule.setText(res.getString("Episode_rule"));
    popupOWAddEpisodeRule.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        popupOWAddEpisodeRule_actionPerformed(e);
      }
    });
    popupOWAddC45.setMnemonic('4');
    popupOWAddC45.setText(res.getString("C4_5"));
    popupOWAddC45.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        popupOWAddC45_actionPerformed(e);
      }
    });
    popupOWAddNeuralNetwork.setMnemonic('N');
    popupOWAddNeuralNetwork.setText(res.getString("Neural_network"));
    popupOWAddNeuralNetwork.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        popupOWAddNeuralNetwork_actionPerformed(e);
      }
    });
    popupOWAddPrediction.setMnemonic('P');
    popupOWAddPrediction.setText(res.getString("Prediction"));
    popupOWAddPrediction.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        popupOWAddPrediction_actionPerformed(e);
      }
    });
    popupOWAddVisual.setMnemonic('D');
    popupOWAddVisual.setText(res.getString("Data"));
    popupOWAddVisual.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        popupOWAddVisual_actionPerformed(e);
      }
    });
    popupOWConnect.setMnemonic('C');
    popupOWConnect.setText(res.getString("Connect"));
    popupOWConnect.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        popupOWConnect_actionPerformed(e);
      }
    });
    popupArc.add(popupArcProperties);
    popupOW.add(popupOWAdd);
    popupOWAdd.add(popupOWAddExtractFile);
    popupOWAdd.add(popupOWAddExtractDB);
    popupOWAdd.addSeparator();
    popupOWAdd.add(popupOWAddPartitioning);
    popupOWAdd.add(popupOWAddSampling);
    popupOWAdd.add(popupOWAddColumnSelect);
    popupOWAdd.add(popupOWAddDataQuery);
    popupOWAdd.addSeparator();
    popupOWAdd.add(popupOWAddRegression);
    popupOWAdd.add(popupOWAddAssociationRule);
    popupOWAdd.add(popupOWAddSequenceRule);
    popupOWAdd.add(popupOWAddC45);
    popupOWAdd.add(popupOWAddNeuralNetwork);
    popupOWAdd.addSeparator();
    popupOWAdd.add(popupOWAddVisual);
    popupOW.add(popupOWConnect);

    // Etc 
    m_fileChooser = new JFileChooser(System.getProperty("minerdir") + "/xmminer/user"); //"/XM-Miner/xmminer/user"); 수정 2002.09.22
	m_fileChooser.setDialogTitle(res.getString("ChooserMsg")); //"Please choose a file or project: ");
    m_fileChooser.addChoosableFileFilter(new XMFileFilter("xmf", res.getString("ScenarioFile"))); //"Scenario Project"));
  }

  // Window
  //Overridden so we can exit on System Close
  protected void processWindowEvent(WindowEvent e)
  {
    super.processWindowEvent(e);
    if(e.getID() == WindowEvent.WINDOW_CLOSING)
    {
      fileExit_actionPerformed(null);
    }
  }

  // Menu
  void menuFileNew_actionPerformed(ActionEvent e)
  {
    menuFileOpen_actionPerformed(e);
  }

  void menuFileOpen_actionPerformed(ActionEvent e)
  {
    if (m_graph != null && m_graph.GetModified())
    {
      switch (JOptionPane.showConfirmDialog(this, res.getString("Project_changed_Save"), res.getString("Project_changed"), JOptionPane.YES_NO_CANCEL_OPTION))
      {
      case JOptionPane.YES_OPTION:
	menuFileSave_actionPerformed(e);
	break;
      case JOptionPane.NO_OPTION:
	break;
      case JOptionPane.CANCEL_OPTION:
      case JOptionPane.CLOSED_OPTION:
      default:
	return;
      }
    }
    File file;
    if (m_fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION
        && (file = m_fileChooser.getSelectedFile()) != null)
    {
      if (file.exists())
      {
        try
        {
          FileInputStream fin = new FileInputStream(file);
          ObjectInputStream objin = new ObjectInputStream(fin);
          m_graph = (XMGraph)objin.readObject();
        }
        catch (Exception ioe)
        {
          ioe.printStackTrace(System.err);
          System.out.println(res.getString("XM_Error_can_t_open") + file);
        }
      }
      else
      {
        m_graph = new XMGraph();
      }
      m_graph.m_file = file;
      setTitle("XM-Miner: " + file);
      InvalidateAll();
    }
  }

  void menuFileSave_actionPerformed(ActionEvent e)
  {
    if (m_graph.m_file != null)
    {
      try
      {
        FileOutputStream fout = new FileOutputStream(m_graph.m_file);
        ObjectOutputStream objout = new ObjectOutputStream(fout);
        m_graph.SetModified(false);
        objout.writeObject(m_graph);
        m_fileChooser.rescanCurrentDirectory();
      }
      catch (Exception ioe)
      {
        ioe.printStackTrace(System.err);
        System.out.println(res.getString("XM_Error_can_t_write") + m_graph.m_file);
      }
    }
  }

  void menuFileSaveAs_actionPerformed(ActionEvent e)
  {
    File file;
    if (m_fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION
        && (file = m_fileChooser.getSelectedFile()) != null)
    {
      if (file.exists())
      {
	switch (JOptionPane.showConfirmDialog(this, res.getString("Project_already"), res.getString("File_exist"), JOptionPane.YES_NO_OPTION))
	{
	case JOptionPane.YES_OPTION:
	  break;
	case JOptionPane.NO_OPTION:
	case JOptionPane.CANCEL_OPTION:
	case JOptionPane.CLOSED_OPTION:
	default:
	  return;
	}
      }
      m_graph.m_file = file;
      setTitle("XM-Miner: " + file);
      menuFileSave_actionPerformed(e);
    }
  }

  void menuFilePrint_actionPerformed(ActionEvent e)
  {
    graphPanel.Print();
  }

  public void fileExit_actionPerformed(ActionEvent e)
  {
    if (m_graph != null && m_graph.GetModified())
    {
      switch (JOptionPane.showConfirmDialog(this, res.getString("Project_changed_Save"), res.getString("Project_changed"), JOptionPane.YES_NO_CANCEL_OPTION))
      {
      case JOptionPane.YES_OPTION:
	menuFileSave_actionPerformed(e);
	break;
      case JOptionPane.NO_OPTION:
	break;
      case JOptionPane.CANCEL_OPTION:
      case JOptionPane.CLOSED_OPTION:
      default:
	return;
      }
    }
    System.exit(0);
  }

  void menuEditDelete_actionPerformed(ActionEvent e)
  {
    if (m_graph != null)
    {
      m_graph.RemoveElements(this, true);
      m_graph.SetModified(true);
      Invalidate();
    }
  }

  void menuEditSelectAll_actionPerformed(ActionEvent e)
  {
    if (m_graph != null)
    {
      m_graph.SelectElements(true);
      Invalidate();
    }
  }

  void menuEditUnselect_actionPerformed(ActionEvent e)
  {
    if (m_graph != null)
    {
      m_graph.SelectElements(false);
      Invalidate();
    }
  }

  void menuDrawAddExtractFile_actionPerformed(ActionEvent e)
  {
    SetDrawAction(e, XMDRAWACTION_ADDEXTRACTFILE);
  }

  void menuDrawAddExtractDB_actionPerformed(ActionEvent e)
  {
    SetDrawAction(e, XMDRAWACTION_ADDEXTRACTDB);
  }

  void menuDrawAddPartitioning_actionPerformed(ActionEvent e)
  {
    SetDrawAction(e, XMDRAWACTION_ADDPARTITIONING);
  }

  void menuDrawAddSampling_actionPerformed(ActionEvent e)
  {
    SetDrawAction(e, XMDRAWACTION_ADDSAMPLING);
  }

  void menuDrawAddColumnCompute_actionPerformed(ActionEvent e)
  {
    SetDrawAction(e, XMDRAWACTION_ADDCOLUMNCOMPUTE);
  }

  void menuDrawAddColumnSelect_actionPerformed(ActionEvent e)
  {
    SetDrawAction(e, XMDRAWACTION_ADDCOLUMNSELECT);
  }

  //void menuDrawAddCalculate_actionPerformed(ActionEvent e)
  //{
  //  SetDrawAction(e, XMDRAWACTION_ADDCALCULATE);
  //}

  void menuDrawAddDataQuery_actionPerformed(ActionEvent e)
  {
    SetDrawAction(e, XMDRAWACTION_ADDDATAQUERY);
  }

  void menuDrawAddRegression_actionPerformed(ActionEvent e)
  {
    SetDrawAction(e, XMDRAWACTION_ADDREGRESSION);
  }

  void menuDrawAddCorrelation_actionPerformed(ActionEvent e)
  {
    SetDrawAction(e, XMDRAWACTION_ADDCORRELATION);
  }

  void menuDrawAddAssociationRule_actionPerformed(ActionEvent e)
  {
    SetDrawAction(e, XMDRAWACTION_ADDASSOCIATIONRULE);
  }

  void menuDrawAddSequenceRule_actionPerformed(ActionEvent e)
  {
    SetDrawAction(e, XMDRAWACTION_ADDSEQUENCERULE);
  }

  void menuDrawAddEpisodeRule_actionPerformed(ActionEvent e)
  {
    SetDrawAction(e, XMDRAWACTION_ADDEPISODERULE);
  }

  void menuDrawAddC45_actionPerformed(ActionEvent e)
  {
    SetDrawAction(e, XMDRAWACTION_ADDC45);
  }

  void menuDrawAddNeuralNetwork_actionPerformed(ActionEvent e)
  {
    SetDrawAction(e, XMDRAWACTION_ADDNEURALNETWORK);
  }

  void menuDrawAddPrediction_actionPerformed(ActionEvent e)
  {
    SetDrawAction(e, XMDRAWACTION_ADDPREDICTION);
  }

  void menuDrawAddVisual_actionPerformed(ActionEvent e)
  {
    SetDrawAction(e, XMDRAWACTION_ADDVISUAL);
  }

  void menuDrawConnect_actionPerformed(ActionEvent e)
  {
    SetDrawAction(e, XMDRAWACTION_CONNECT);
  }

  void menuDrawProperties_actionPerformed(ActionEvent e)
  {
    XMGraphElement element;
    int statEdit;
    if (m_graph != null
        && (element = m_graph.GetFrontElement(true)) != null
        && (statEdit = element.Edit(this, m_graph)) != XMGraphElement.XMGESTAT_EDIT_NOCHANGE)
    {
      m_graph.SetModified(true);
      if (element instanceof XMNode)
      {
	((XMNode)element).SetStatEdit(statEdit);
	((XMNode)element).SetStatRun(XMGraphElement.XMGESTAT_RUN_NORUN);
      }
      Invalidate();
    }
  }

  void menuDrawSelect_actionPerformed(ActionEvent e)
  {
    SetDrawAction(e, XMDRAWACTION_SELECT);
  }

  void menuRunRun_actionPerformed(ActionEvent e)
  {
    if (m_graph != null)
    {
      XMNode errNode;
      if ((errNode = m_graph.Run(this, false)) != null)
      {
	ShowRunError(errNode);
      }
      else
      {
	m_graph.SetModified(true);
      }
      Invalidate();
    }
  }

  void menuRunRerun_actionPerformed(ActionEvent e)
  {
    if (m_graph != null)
    {
      XMNode errNode;
      if ((errNode = m_graph.Run(this, true)) != null)
      {
	ShowRunError(errNode);
      }
      else
      {
        m_graph.SetModified(true);
      }
      Invalidate();
    }
  }

  void menuRunRunToHere_actionPerformed(ActionEvent e)
  {
    XMNode node;
    if (m_graph != null && (node = (XMNode)m_graph.GetFrontNode(true)) != null)
    {
      XMNode errNode;
      node.InitRun(m_graph, true);
      if ((errNode = node.Run2Here(this, m_graph)) != null)
      {
	ShowRunError(errNode);
      }
      else
      {
        m_graph.SetModified(true);
      }
      Invalidate();
    }
  }

  void menuRunRunAlone_actionPerformed(ActionEvent e)
  {
    XMNode node;
    if (m_graph != null && (node = (XMNode)m_graph.GetFrontNode(true)) != null)
    {
      if (node.Run(this, m_graph) == XMGraphElement.XMGESTAT_RUN_SUCCESS)
      {
        m_graph.SetModified(true);
	node.SetStatRun(XMGraphElement.XMGESTAT_RUN_SUCCESS);
    	node.SetStatEdit(XMGraphElement.XMGESTAT_EDIT_NOCHANGE);
      }
      else
      {
      }
      Invalidate();
    }
  }

  public void helpAbout_actionPerformed(ActionEvent e)
  {
    XMMinerFrame_AboutBox dlg = new XMMinerFrame_AboutBox(this);
    Dimension dlgSize = dlg.getPreferredSize();
    Dimension frmSize = getSize();
    Point loc = getLocation();
    dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
    dlg.setModal(true);
    dlg.show();
  }

  // Tool Bar
  void toolBarOpen_actionPerformed(ActionEvent e)
  {
    menuFileOpen_actionPerformed(e);
  }

  void toolBarSave_actionPerformed(ActionEvent e)
  {
    menuFileSave_actionPerformed(e);
  }

  void toolBarPrint_actionPerformed(ActionEvent e)
  {
    menuFilePrint_actionPerformed(e);
  }

  void toolBarHelp_actionPerformed(ActionEvent e)
  {
    helpAbout_actionPerformed(e);
  }

  void toolBarProperties_actionPerformed(ActionEvent e)
  {
    menuDrawProperties_actionPerformed(e);
  }

  void toolBarConnect_actionPerformed(ActionEvent e)
  {
    menuDrawConnect_actionPerformed(e);
  }

  void toolBarRun_actionPerformed(ActionEvent e)
  {
    menuRunRun_actionPerformed(e);
  }

  void toolBarRunAlone_actionPerformed(ActionEvent e)
  {
    menuRunRunAlone_actionPerformed(e);
  }

  void nodeBarAddExtractFile_actionPerformed(ActionEvent e)
  {
    menuDrawAddExtractFile_actionPerformed(e);
  }

  void nodeBarAddExtractDB_actionPerformed(ActionEvent e)
  {
    menuDrawAddExtractDB_actionPerformed(e);
  }

  void nodeBarAddPartitioning_actionPerformed(ActionEvent e)
  {
    menuDrawAddPartitioning_actionPerformed(e);
  }

  void nodeBarAddSampling_actionPerformed(ActionEvent e)
  {
    menuDrawAddSampling_actionPerformed(e);
  }

  void nodeBarAddColumnCompute_actionPerformed(ActionEvent e)
  {
    menuDrawAddColumnCompute_actionPerformed(e);
  }

  void nodeBarAddColumnSelect_actionPerformed(ActionEvent e)
  {
    menuDrawAddColumnSelect_actionPerformed(e);
  }

  void nodeBarAddDataQuery_actionPerformed(ActionEvent e)
  {
    menuDrawAddDataQuery_actionPerformed(e);
  }

  void nodeBarAddRegression_actionPerformed(ActionEvent e)
  {
    menuDrawAddRegression_actionPerformed(e);
  }

  void nodeBarAddCorrelation_actionPerformed(ActionEvent e)
  {
    menuDrawAddCorrelation_actionPerformed(e);
  }

  void nodeBarAddAssociationRule_actionPerformed(ActionEvent e)
  {
    menuDrawAddAssociationRule_actionPerformed(e);
  }

  void nodeBarAddSequenceRule_actionPerformed(ActionEvent e)
  {
    menuDrawAddSequenceRule_actionPerformed(e);
  }

  void nodeBarAddEpisodeRule_actionPerformed(ActionEvent e)
  {
    menuDrawAddEpisodeRule_actionPerformed(e);
  }

  void nodeBarAddC45_actionPerformed(ActionEvent e)
  {
    menuDrawAddC45_actionPerformed(e);
  }

  void nodeBarAddNeuralNetwork_actionPerformed(ActionEvent e)
  {
    menuDrawAddNeuralNetwork_actionPerformed(e);
  }

  void nodeBarAddPrediction_actionPerformed(ActionEvent e)
  {
    menuDrawAddPrediction_actionPerformed(e);
  }

  void nodeBarAddVisual_actionPerformed(ActionEvent e)
  {
    menuDrawAddVisual_actionPerformed(e);
  }
  // Window

  //  Drawing
  public void Invalidate()
  {
    graphPanel.repaint();
  }

  public void InvalidateComponents()
  {
    graphPanel.revalidate();
    graphPanel.repaint();
  }

  public void InvalidateAll()
  {
    if (m_graph != null)
    {
      Dimension frmSize;
      Rectangle boundary;
      if ((frmSize = getSize()) != null && (boundary = m_graph.GetBoundary(false)) != null)
      {
        graphPanel.setPreferredSize(new Dimension(boundary.x + boundary.width + frmSize.width / 2,
                                                  boundary.y + boundary.height + frmSize.height / 2));
        graphPanel.revalidate();
        graphPanel.repaint();
      }
    }
  }

  void SetDrawAction(ActionEvent e, int action)
  {
    if (m_graph == null)
    {
      JOptionPane.showMessageDialog(this, res.getString("Project_name_must_be"));
      menuFileNew_actionPerformed(e);
    }
    m_drawAction = action;
    switch (action)
    {
    case XMDRAWACTION_ADDEXTRACTFILE:
    case XMDRAWACTION_ADDEXTRACTDB:
    case XMDRAWACTION_ADDPARTITIONING:
    case XMDRAWACTION_ADDSAMPLING:
    case XMDRAWACTION_ADDCOLUMNCOMPUTE:
    case XMDRAWACTION_ADDCOLUMNSELECT:
    case XMDRAWACTION_ADDCALCULATE:
    case XMDRAWACTION_ADDDATAQUERY:
    case XMDRAWACTION_ADDDATAMERGE:
    case XMDRAWACTION_ADDDATATRANSFORM:
    case XMDRAWACTION_ADDREGRESSION:
    case XMDRAWACTION_ADDCORRELATION:
    case XMDRAWACTION_ADDASSOCIATIONRULE:
    case XMDRAWACTION_ADDSEQUENCERULE:
    case XMDRAWACTION_ADDEPISODERULE:
    case XMDRAWACTION_ADDC45:
    case XMDRAWACTION_ADDNEURALNETWORK:
    case XMDRAWACTION_ADDPREDICTION:
//    case XMDRAWACTION_ADDSOFM:
    case XMDRAWACTION_ADDVISUAL:
      graphPanel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
      break;
    case XMDRAWACTION_CONNECT:
      graphPanel.setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
      break;
    case XMDRAWACTION_SELECT:
    case XMDRAWACTION_DISCONNECT:
    case XMDRAWACTION_NONE:
    default:
      graphPanel.setCursor(Cursor.getDefaultCursor());
      break;
    }
  }

  public void SetDrawActionConnect(ActionEvent e)
  {
    SetDrawAction(e, XMDRAWACTION_CONNECT);
  }

  public void ShowRunError(XMNode errNode)
  {
    String msg;
    if (errNode.GetStatEdit() == XMGraphElement.XMGESTAT_EDIT_CRITICALCHANGE)
    {
      msg = res.getString("Crtical_change_found") + errNode.GetName() + ".";
    }
    else
    {
      msg = res.getString("Running_error_at") + errNode.GetName() + ".";
    }
    JOptionPane.showMessageDialog(this, msg);
  }

  public void DisplayMousePosition(Point point)
  {
    String msg = "(" + point.x + ", " + point.y + ")";
    statusBar.setText(msg);
  }

  public void DisplayMousePosition()
  {
    statusBar.setText("");
  }

  //  Action
  int	m_drawAction = XMDRAWACTION_SELECT;
  protected static final int XMDRAWACTION_NONE			= 0;
  protected static final int XMDRAWACTION_SELECT		= 1;
  protected static final int XMDRAWACTION_ADDEXTRACTFILE	= 11;
  protected static final int XMDRAWACTION_ADDEXTRACTDB		= 12;
  protected static final int XMDRAWACTION_ADDPARTITIONING	= 21;
  protected static final int XMDRAWACTION_ADDSAMPLING		= 22;
  protected static final int XMDRAWACTION_ADDCOLUMNCOMPUTE	= 23;
  protected static final int XMDRAWACTION_ADDCOLUMNSELECT	= 24;
  protected static final int XMDRAWACTION_ADDCALCULATE		= 25;
  protected static final int XMDRAWACTION_ADDDATAQUERY		= 26;
  protected static final int XMDRAWACTION_ADDDATAMERGE		= 27;
  protected static final int XMDRAWACTION_ADDDATATRANSFORM	= 28;
  protected static final int XMDRAWACTION_ADDREGRESSION		= 31;
  protected static final int XMDRAWACTION_ADDASSOCIATIONRULE= 32;
  protected static final int XMDRAWACTION_ADDSEQUENCERULE	= 33;
  protected static final int XMDRAWACTION_ADDEPISODERULE	= 34;
  protected static final int XMDRAWACTION_ADDC45		    = 35;
  protected static final int XMDRAWACTION_ADDNEURALNETWORK	= 36;
//  protected static final int XMDRAWACTION_ADDSOFM		    = 37;
  protected static final int XMDRAWACTION_ADDCORRELATION	= 38;
  protected static final int XMDRAWACTION_ADDPREDICTION	    = 39;
  protected static final int XMDRAWACTION_ADDVISUAL		    = 41;
  protected static final int XMDRAWACTION_CONNECT		    = 91;
  protected static final int XMDRAWACTION_DISCONNECT		= 92;

  boolean	m_bClickDown = false;
  XMNode	m_clickNode = null;
  XMArc		m_clickArc = null;
  boolean	m_bRoamFirst = false;
  Point		m_tRoamStart = null;
  Point		m_tRoamPrev = null;

  XMNode	m_copyNode = null;
  boolean	m_bSubstantialCopyNode = false;

  void graphPanel_lMousePressed(MouseEvent e)
  {
    if (m_graph != null)
    {
      Point point = e.getPoint();

      m_bClickDown = true;
      m_clickNode = (XMNode)m_graph.GetNodeAt(point);
      m_clickArc = (XMArc)m_graph.GetArcAt(point);
      m_bRoamFirst = true;
      m_tRoamStart = point;
      m_tRoamPrev = point;

      switch (m_drawAction)
      {
      case XMDRAWACTION_SELECT:
	if (m_clickNode != null && !e.isControlDown())
	{
          if (!m_clickNode.GetSelected())
          {
	    m_graph.SelectElements(false);
	    m_clickNode.SetSelected(true);
	    Invalidate();
          }
	}
 	else if (m_clickArc != null && !e.isControlDown())
	{
          if (!m_clickArc.GetSelected())
          {
	    m_graph.SelectElements(false);
	    m_clickArc.SetSelected(true);
	    Invalidate();
          }
	}
	break;
      case XMDRAWACTION_CONNECT:
	if (m_clickNode == null)
	{
          m_drawAction = XMDRAWACTION_SELECT;
          graphPanel.setCursor(Cursor.getDefaultCursor());
	}
	break;
      case XMDRAWACTION_NONE:
      case XMDRAWACTION_ADDEXTRACTFILE:
      case XMDRAWACTION_ADDEXTRACTDB:
      case XMDRAWACTION_ADDPARTITIONING:
      case XMDRAWACTION_ADDSAMPLING:
      case XMDRAWACTION_ADDCOLUMNCOMPUTE:
      case XMDRAWACTION_ADDCOLUMNSELECT:
      case XMDRAWACTION_ADDCALCULATE:
      case XMDRAWACTION_ADDDATAQUERY:
      case XMDRAWACTION_ADDDATAMERGE:
      case XMDRAWACTION_ADDDATATRANSFORM:
      case XMDRAWACTION_ADDREGRESSION:
      case XMDRAWACTION_ADDCORRELATION:
      case XMDRAWACTION_ADDASSOCIATIONRULE:
      case XMDRAWACTION_ADDSEQUENCERULE:
      case XMDRAWACTION_ADDEPISODERULE:
      case XMDRAWACTION_ADDC45:
      case XMDRAWACTION_ADDNEURALNETWORK:
      case XMDRAWACTION_ADDPREDICTION:
//      case XMDRAWACTION_ADDSOFM:
      case XMDRAWACTION_ADDVISUAL:
      case XMDRAWACTION_DISCONNECT:
      default:
	break;
      }
    }
  }

  void graphPanel_lMouseDoublePressed(MouseEvent e)
  {
    if (m_graph != null)
    {
      if (m_clickNode != null)
      {
	int statEdit;
	if ((statEdit = m_clickNode.Edit(this, m_graph)) != XMGraphElement.XMGESTAT_EDIT_NOCHANGE)
	{
	  m_graph.SetModified(true);
	  m_clickNode.SetStatEdit(statEdit);
	  m_clickNode.SetStatRun(XMGraphElement.XMGESTAT_RUN_NORUN);
	  Invalidate();
	}
      }
      else if (m_clickArc != null)
      {
	if (m_clickArc.Edit(this, m_graph) != XMGraphElement.XMGESTAT_EDIT_NOCHANGE)
	{
	  m_graph.SetModified(true);
	  Invalidate();
	}
      }
      else
      {
	if (m_graph.Edit(this) != XMGraphElement.XMGESTAT_EDIT_NOCHANGE)
	{
	  m_graph.SetModified(true);
	  Invalidate();
	}
      }
    }
  }

  void graphPanel_mousePressed(MouseEvent e)
  {
    //if (SwingUtilities.isLeftMouseButton(e))
    {
      switch (e.getClickCount())
      {
      case 1:
	graphPanel_lMousePressed(e);
	break;
      default:
	graphPanel_lMouseDoublePressed(e);
	break;
      }
    }
  }

  float dragDash[] = { 3.0F, 3.0F };
  BasicStroke dragStroke = new BasicStroke(1.0F, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0.0F, dragDash, 0.0F);

  void graphPanel_mouseDraggedSelect(MouseEvent e)
  {
    Graphics2D g = (Graphics2D)graphPanel.getGraphics();
    if (m_graph != null && g != null)
    {
      Point point = e.getPoint();

      // Pen
      g.setXORMode(getBackground());
      g.setStroke(dragStroke);

      // Draw roaming
      if ((m_clickNode != null && m_clickNode.GetSelected())
	  || (m_clickArc != null && m_clickArc.GetSelected()))	// moving
      {
	if (m_bRoamFirst)
	{
	  m_bRoamFirst = false;
	}
	else
	{
	  m_graph.DrawRoamingElements(g, m_tRoamPrev, m_tRoamStart, true);
	}
	m_graph.DrawRoamingElements(g, point, m_tRoamStart, true);
	m_tRoamPrev = point;
      }
      else							// dragging
      {
	if (m_bRoamFirst)
	{
	  m_bRoamFirst = false;
	}
	else
	{
	  XMLib.DrawRoamingRectangle(g, m_tRoamStart, m_tRoamPrev);
	}
	XMLib.DrawRoamingRectangle(g, m_tRoamStart, point);
	m_tRoamPrev = point;
      }
      DisplayMousePosition(point);

      // Recover Paint Mode
      //g.setPaintMode();
    }
  }

  void graphPanel_mouseDraggedConnect(MouseEvent e)
  {
    Graphics2D g = (Graphics2D)graphPanel.getGraphics();
    if (m_graph != null && m_clickNode != null && g != null)
    {
      Point point = e.getPoint();

      // Pen
      g.setXORMode(getBackground());
      g.setStroke(dragStroke);

      // Draw roaming line
      if (m_bRoamFirst)
      {
	m_bRoamFirst = false;
      }
      else
      {
	m_clickNode.DrawRoamingLine(g, m_clickNode, m_tRoamPrev, m_graph.GetScale());
      }
      m_clickNode.DrawRoamingLine(g, m_clickNode, point, m_graph.GetScale());
      m_tRoamPrev = point;
      DisplayMousePosition(point);

      // Recover Paint Mode
      //g.setPaintMode();
    }
  }

  void graphPanel_mouseDragged(MouseEvent e)
  {
    if (m_bClickDown)
    {
      switch (m_drawAction)
      {
      case XMDRAWACTION_SELECT:		// dragging
	graphPanel_mouseDraggedSelect(e);
	break;
      case XMDRAWACTION_CONNECT:	// connecting
	graphPanel_mouseDraggedConnect(e);
	break;
      case XMDRAWACTION_NONE:
      case XMDRAWACTION_ADDEXTRACTFILE:
      case XMDRAWACTION_ADDEXTRACTDB:
      case XMDRAWACTION_ADDPARTITIONING:
      case XMDRAWACTION_ADDSAMPLING:
      case XMDRAWACTION_ADDCOLUMNCOMPUTE:
      case XMDRAWACTION_ADDCOLUMNSELECT:
      case XMDRAWACTION_ADDCALCULATE:
      case XMDRAWACTION_ADDDATAQUERY:
      case XMDRAWACTION_ADDDATAMERGE:
      case XMDRAWACTION_ADDDATATRANSFORM:
      case XMDRAWACTION_ADDREGRESSION:
      case XMDRAWACTION_ADDCORRELATION:
      case XMDRAWACTION_ADDASSOCIATIONRULE:
      case XMDRAWACTION_ADDSEQUENCERULE:
      case XMDRAWACTION_ADDEPISODERULE:
      case XMDRAWACTION_ADDC45:
      case XMDRAWACTION_ADDNEURALNETWORK:
      case XMDRAWACTION_ADDPREDICTION:
//      case XMDRAWACTION_ADDSOFM:
      case XMDRAWACTION_ADDVISUAL:
      case XMDRAWACTION_DISCONNECT:
      default:
	break;
      }
    }
  }

  void graphPanel_mouseReleasedAddNode(MouseEvent e, int stat)
  {
    if (m_graph != null && m_bRoamFirst)	// just click
    {
      Point point = e.getPoint();
      XMNode node = (XMNode)m_graph.CreateNodeAt(point, stat, true, true);
      m_graph.SetModified(true);
      InvalidateAll();
    }
  }

  void graphPanel_mouseReleasedSelect(MouseEvent e)
  {
    if (m_graph != null)
    {
      if (m_bRoamFirst)	// just click
      {
	if (m_clickNode != null)
	{
	  if (e.isControlDown())
	  {
	    m_clickNode.SetSelected(!m_clickNode.GetSelected());
	    Invalidate();
	  }
	  else if (!m_clickNode.GetSelected())
	  {
	    m_graph.SelectElements(false);
	    m_clickNode.SetSelected(true);
	    Invalidate();
	  }
	}
	else if (m_clickArc != null)
	{
	  if (e.isControlDown())
	  {
	    m_clickArc.SetSelected(!m_clickArc.GetSelected());
	    Invalidate();
	  }
	  else if (!m_clickArc.GetSelected())
	  {
	    m_graph.SelectElements(false);
	    m_clickArc.SetSelected(true);
	    Invalidate();
	  }
	}
	else
	{
	  m_graph.SelectElements(false);
	  Invalidate();
	}
      }
      else
      {
        Point point = e.getPoint();
	if ((m_clickNode != null && m_clickNode.GetSelected())
	    || (m_clickArc != null && m_clickArc.GetSelected()))	// moving
	{
	  Dimension offset = new Dimension(point.x - m_tRoamStart.x, point.y - m_tRoamStart.y);
	  m_graph.MoveNodes(offset, true);
	  m_graph.SetModified(true);
	  InvalidateAll();
	}
	else						// dragging
	{
	  Rectangle boundary = new Rectangle(Math.min(m_tRoamStart.x, point.x), Math.min(m_tRoamStart.y, point.y),
			                     Math.abs(m_tRoamStart.x - point.x), Math.abs(m_tRoamStart.y - point.y));
	  m_graph.SelectElements(boundary, true);
	  Invalidate();
	}
      }
    }
  }

  void graphPanel_mouseReleasedConnect(MouseEvent e)
  {
    if (m_graph != null && m_clickNode != null && !m_bRoamFirst)
    {	// moving after click-down
      Point point = e.getPoint();
      XMNode toNode;
      if ((toNode = (XMNode)m_graph.GetNodeAt(point)) != null)
      {
	XMArc arc;
	if ((arc = (XMArc)m_graph.CreateArc(this, XMGraphElement.XMGESTAT_SARC_NORMAL, m_clickNode, toNode, false, true, true)) != null)
	{
	  m_graph.SetModified(true);
	  Invalidate();
	}
	else
	{
	  JOptionPane.showMessageDialog(this, res.getString("Can_t_create_the_edge"));
	  Invalidate();
	}
      }
      else
      {
	JOptionPane.showMessageDialog(this, res.getString("Destination_unknown"));
	Invalidate();
      }
    }
  }

  void graphPanel_mouseReleased(MouseEvent e)
  {
    if (e.isPopupTrigger())
    {
      JPopupMenu popupMenu;
      if (m_clickNode != null)
      {
        popupMenu = m_clickNode.CreatePopupMenu(this, m_graph);
        popupMenu.show(e.getComponent(), e.getX(), e.getY());
      }
      else if (m_clickArc != null)
      {
        popupArc.show(e.getComponent(), e.getX(), e.getY());
      }
      else
      {
        popupOW.show(e.getComponent(), e.getX(), e.getY());
      }
      Invalidate();
    }
    else if (m_bClickDown)
    {
      switch (m_drawAction)
      {
      case XMDRAWACTION_SELECT:
	graphPanel_mouseReleasedSelect(e);
	break;
      case XMDRAWACTION_ADDEXTRACTFILE:
	graphPanel_mouseReleasedAddNode(e, XMGraphElement.XMGESTAT_XNODE_FILE);
	break;
      case XMDRAWACTION_ADDEXTRACTDB:
	graphPanel_mouseReleasedAddNode(e, XMGraphElement.XMGESTAT_XNODE_DB);
	break;
      case XMDRAWACTION_ADDPARTITIONING:
	graphPanel_mouseReleasedAddNode(e, XMGraphElement.XMGESTAT_DNODE_PARTITIONING);
	break;
      case XMDRAWACTION_ADDSAMPLING:
	graphPanel_mouseReleasedAddNode(e, XMGraphElement.XMGESTAT_DNODE_SAMPLING);
	break;
      case XMDRAWACTION_ADDCOLUMNCOMPUTE:
	graphPanel_mouseReleasedAddNode(e, XMGraphElement.XMGESTAT_DNODE_COLUMNCOMPUTE);
	break;
      case XMDRAWACTION_ADDCOLUMNSELECT:
	graphPanel_mouseReleasedAddNode(e, XMGraphElement.XMGESTAT_DNODE_COLUMNSELECT);
	break;
      case XMDRAWACTION_ADDCALCULATE:
	graphPanel_mouseReleasedAddNode(e, XMGraphElement.XMGESTAT_DNODE_CALCULATE);
	break;
      case XMDRAWACTION_ADDDATAQUERY:
	graphPanel_mouseReleasedAddNode(e, XMGraphElement.XMGESTAT_DNODE_DATAQUERY);
	break;
      case XMDRAWACTION_ADDDATAMERGE:
	graphPanel_mouseReleasedAddNode(e, XMGraphElement.XMGESTAT_DNODE_DATAMERGE);
	break;
      case XMDRAWACTION_ADDDATATRANSFORM:
	graphPanel_mouseReleasedAddNode(e, XMGraphElement.XMGESTAT_DNODE_DATATRANSFORM);
	break;
      case XMDRAWACTION_ADDREGRESSION:
	graphPanel_mouseReleasedAddNode(e, XMGraphElement.XMGESTAT_MNODE_REGRESSION);
	break;
      case XMDRAWACTION_ADDCORRELATION:
	graphPanel_mouseReleasedAddNode(e, XMGraphElement.XMGESTAT_MNODE_CORRELATION);
	break;
      case XMDRAWACTION_ADDASSOCIATIONRULE:
	graphPanel_mouseReleasedAddNode(e, XMGraphElement.XMGESTAT_MNODE_ASSOCIATIONRULE);
	break;
      case XMDRAWACTION_ADDSEQUENCERULE:
	graphPanel_mouseReleasedAddNode(e, XMGraphElement.XMGESTAT_MNODE_SEQUENCERULE);
	break;
      case XMDRAWACTION_ADDEPISODERULE:
	graphPanel_mouseReleasedAddNode(e, XMGraphElement.XMGESTAT_MNODE_EPISODERULE);
	break;
      case XMDRAWACTION_ADDC45:
	graphPanel_mouseReleasedAddNode(e, XMGraphElement.XMGESTAT_MNODE_C45);
	break;
      case XMDRAWACTION_ADDNEURALNETWORK:
	graphPanel_mouseReleasedAddNode(e, XMGraphElement.XMGESTAT_MNODE_NEURALNETWORK);
	break;
      case XMDRAWACTION_ADDPREDICTION:
	graphPanel_mouseReleasedAddNode(e, XMGraphElement.XMGESTAT_MNODE_PREDICTION);
	break;
//      case XMDRAWACTION_ADDSOFM:
//	graphPanel_mouseReleasedAddNode(e, XMGraphElement.XMGESTAT_MNODE_SOFM);
//	break;
      case XMDRAWACTION_ADDVISUAL:
	graphPanel_mouseReleasedAddNode(e, XMGraphElement.XMGESTAT_VNODE_NORMAL);
	break;
      case XMDRAWACTION_CONNECT:
	graphPanel_mouseReleasedConnect(e);
	break;
      case XMDRAWACTION_DISCONNECT:
      case XMDRAWACTION_NONE:
      default:
	break;
      }
      m_drawAction = XMDRAWACTION_SELECT;
      graphPanel.setCursor(Cursor.getDefaultCursor());
      m_bClickDown = false;
      m_bRoamFirst = false;
      //DisplayMousePosition();
    }
  }

  // Popup Menu
  void popupArcProperties_actionPerformed(ActionEvent e)
  {
    if (m_graph != null
	&& m_clickArc != null
	&& m_clickArc.Edit(this, m_graph) != XMGraphElement.XMGESTAT_EDIT_NOCHANGE)
    {
      m_graph.SetModified(true);
      Invalidate();
    }
  }

  void popupOWAddExtractFile_actionPerformed(ActionEvent e)
  {
    SetDrawAction(e, XMDRAWACTION_ADDEXTRACTFILE);
  }

  void popupOWAddExtractDB_actionPerformed(ActionEvent e)
  {
    SetDrawAction(e, XMDRAWACTION_ADDEXTRACTDB);
  }

  void popupOWAddPartitioning_actionPerformed(ActionEvent e)
  {
    SetDrawAction(e, XMDRAWACTION_ADDPARTITIONING);
  }

  void popupOWAddSampling_actionPerformed(ActionEvent e)
  {
    SetDrawAction(e, XMDRAWACTION_ADDSAMPLING);
  }

  void popupOWAddColumnCompute_actionPerformed(ActionEvent e)
  {
    SetDrawAction(e, XMDRAWACTION_ADDCOLUMNCOMPUTE);
  }

  void popupOWAddColumnSelect_actionPerformed(ActionEvent e)
  {
    SetDrawAction(e, XMDRAWACTION_ADDCOLUMNSELECT);
  }

  void popupOWAddDataQuery_actionPerformed(ActionEvent e)
  {
    SetDrawAction(e, XMDRAWACTION_ADDDATAQUERY);
  }

  void popupOWAddRegression_actionPerformed(ActionEvent e)
  {
    SetDrawAction(e, XMDRAWACTION_ADDREGRESSION);
  }

  void popupOWAddCorrelation_actionPerformed(ActionEvent e)
  {
    SetDrawAction(e, XMDRAWACTION_ADDCORRELATION);
  }

  void popupOWAddAssociationRule_actionPerformed(ActionEvent e)
  {
    SetDrawAction(e, XMDRAWACTION_ADDASSOCIATIONRULE);
  }

  void popupOWAddSequenceRule_actionPerformed(ActionEvent e)
  {
    SetDrawAction(e, XMDRAWACTION_ADDSEQUENCERULE);
  }

  void popupOWAddEpisodeRule_actionPerformed(ActionEvent e)
  {
    SetDrawAction(e, XMDRAWACTION_ADDEPISODERULE);
  }

  void popupOWAddC45_actionPerformed(ActionEvent e)
  {
    SetDrawAction(e, XMDRAWACTION_ADDC45);
  }

  void popupOWAddNeuralNetwork_actionPerformed(ActionEvent e)
  {
    SetDrawAction(e, XMDRAWACTION_ADDNEURALNETWORK);
  }

  void popupOWAddPrediction_actionPerformed(ActionEvent e)
  {
    SetDrawAction(e, XMDRAWACTION_ADDPREDICTION);
  }

  void popupOWAddVisual_actionPerformed(ActionEvent e)
  {
    SetDrawAction(e, XMDRAWACTION_ADDVISUAL);
  }

  void popupOWConnect_actionPerformed(ActionEvent e)
  {
    SetDrawAction(e, XMDRAWACTION_CONNECT);
  }

  void menuEditLanEn_actionPerformed(ActionEvent e)
  {
    flgLan = 0;
    res = ResourceBundle.getBundle("xmminer.xmclient.XMMinerFrame_Res_En");
    System.out.println("flag language"+flgLan);
  }

  void menuEditLanKo_actionPerformed(ActionEvent e)
  {
    flgLan = 1;
    res = ResourceBundle.getBundle("xmminer.xmclient.XMMinerFrame_Res_Ko");
    System.out.println("flag language"+flgLan);
  }

  public void SetLanguage(int set){ flgLan = set; }
  public int GetLanguage(){ return flgLan; }
}
