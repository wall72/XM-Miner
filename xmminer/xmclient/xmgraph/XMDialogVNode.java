
//Title:        XM-Miner
//Version:
//Copyright:    Copyright (c) 1999
//Author:       Yong Uk Song
//Company:      DIS
//Description:  XM-Miner

package xmminer.xmclient.xmgraph;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

import xmminer.xmlib.*;
import xmminer.xmclient.*;
import xmminer.xmclient.xmlib.*;
import xmminer.xmclient.xmgraph.*;
import java.util.ResourceBundle;

public class XMDialogVNode extends JDialog
{
  static ResourceBundle res;
  JFrame m_frame;
  XMGraph m_graph;
  XMVNode m_node;

  String m_projectPath, m_projectName, m_metaFilename;

  String[] m_chartTypes =
  {
    "PLOT",
    "SCATTER_PLOT",
    "BAR",
    "STACKING_BAR",
    "AREA",
    "PIE",
    "HILO",
    "HILO_OPEN_CLOSE",
    "CANDLE"
  };

  String[] m_fchartTypes =
  {
    "PLOT",
    "SCATTER_PLOT",
    "BAR",
    "STACKING_BAR",
    "AREA"
  };

  String[] m_chartSelection =
  {
    "<NONE>",
    "Chart 1",
    "Chart 2"
  };

  XMChartTableModel m_chartTableModel;
  XMFChartTableModel m_fchartTableModel;

  JPanel panelMain = new JPanel();
  BorderLayout borderLayoutMain = new BorderLayout();
  JTabbedPane tabbedPaneVNode = new JTabbedPane();
  JPanel panelVNodeTable = new JPanel();
  JPanel panelVNodeStatistics = new JPanel();
  JPanel panelVNodeCharts = new JPanel();
  JPanel panelVNodeFChart = new JPanel();

  BorderLayout borderLayoutVNodeTable = new BorderLayout();
  JPanel panelVNodeTableCenter = new JPanel();
  JPanel panelVNodeTableSouth = new JPanel();
  JButton buttonVNodeTableShow = new JButton();
  JButton buttonVNodeTableExit = new JButton();
  GridBagLayout gridBagLayoutVNodeTableCenter = new GridBagLayout();
  JLabel labelVNodeTableWidth = new JLabel();
  JTextField textFieldVNodeTableWidth = new JTextField(5);
  JLabel labelVNodeTableHeight = new JLabel();
  JTextField textFieldVNodeTableHeight = new JTextField(5);
  JLabel labelVNodeTablePageSize = new JLabel();
  JTextField textFieldVNodeTablePageSize = new JTextField(5);

  BorderLayout borderLayoutVNodeStatistics = new BorderLayout();
  JPanel panelVNodeStatisticsCenter = new JPanel();
  JPanel panelVNodeStatisticsSouth = new JPanel();
  JButton buttonVNodeStatisticsShow = new JButton();
  JButton buttonVNodeStatisticsExit = new JButton();
  GridBagLayout gridBagLayoutVNodeStatisticsCenter = new GridBagLayout();
  JLabel labelVNodeStatisticsWidth = new JLabel();
  JTextField textFieldVNodeStatisticsWidth = new JTextField(5);
  JLabel labelVNodeStatisticsHeight = new JLabel();
  JTextField textFieldVNodeStatisticsHeight = new JTextField(5);

  BorderLayout borderLayoutVNodeCharts = new BorderLayout();
  JPanel panelVNodeChartsCenter = new JPanel();
  JPanel panelVNodeChartsSouth = new JPanel();
  JButton buttonVNodeChartsShow = new JButton();
  JButton buttonVNodeChartsExit = new JButton();
  GridBagLayout gridBagLayoutVNodeChartsCenterLeft = new GridBagLayout();
  JPanel panelVNodeChartsCenterLeft = new JPanel();
  GridBagLayout gridBagLayoutVNodeChartsCenterLeft1 = new GridBagLayout();
  JPanel panelVNodeChartsCenterLeft1 = new JPanel();
  JLabel labelVNodeChartsCenterLeft1Width = new JLabel();
  JTextField textFieldVNodeChartsCenterLeft1Width = new JTextField(5);
  JLabel labelVNodeChartsCenterLeft1Height = new JLabel();
  JTextField textFieldVNodeChartsCenterLeft1Height = new JTextField(5);
  JLabel labelVNodeChartsCenterLeft1HeaderText = new JLabel();
  JTextField textFieldVNodeChartsCenterLeft1HeaderText = new JTextField(5);
  JLabel labelVNodeChartsCenterLeft1FooterText = new JLabel();
  JTextField textFieldVNodeChartsCenterLeft1FooterText = new JTextField(5);
  JLabel labelVNodeChartsCenterLeft1XAxisText = new JLabel();
  JTextField textFieldVNodeChartsCenterLeft1XAxisText = new JTextField(5);
  JLabel labelVNodeChartsCenterLeft1YAxisText = new JLabel();
  JTextField textFieldVNodeChartsCenterLeft1YAxisText = new JTextField(5);
  JPanel panelVNodeChartsCenterLeft2 = new JPanel();
  JCheckBox checkBoxChartsCenterLeft2VerticalGrid = new JCheckBox();
  JCheckBox checkBoxChartsCenterLeft2HorizontalGrid = new JCheckBox();
  GridBagLayout gridBagLayoutVNodeChartsCenterLeft3 = new GridBagLayout();
  JPanel panelVNodeChartsCenterLeft3 = new JPanel();
  JLabel labelVNodeChartsCenterLeft3Chart1 = new JLabel();
  JLabel labelVNodeChartsCenterLeft3Chart2 = new JLabel();
  JTextField textFieldVNodeChartsCenterLeft3Chart1Name = new JTextField(5);
  JTextField textFieldVNodeChartsCenterLeft3Chart2Name = new JTextField(5);
  JComboBox comboBoxVNodeChartsCenterLeft3Chart1Type = new JComboBox(m_chartTypes);
  JComboBox comboBoxVNodeChartsCenterLeft3Chart2Type = new JComboBox(m_chartTypes);
  GridBagLayout gridBagLayoutVNodeChartsCenterRight = new GridBagLayout();
  JPanel panelVNodeChartsCenterRight = new JPanel();
  JLabel labelVNodeChartsCenterRightTable = new JLabel();
  JScrollPane scrollPaneVNodeChartsCenterRightTable = new JScrollPane();
  JTable panelVNodeChartsCenterRightTable;

  BorderLayout borderLayoutVNodeFChart = new BorderLayout();
  JPanel panelVNodeFChartCenter = new JPanel();
  JPanel panelVNodeFChartSouth = new JPanel();
  JButton buttonVNodeFChartShow = new JButton();
  JButton buttonVNodeFChartExit = new JButton();
  GridBagLayout gridBagLayoutVNodeFChartCenterLeft = new GridBagLayout();
  JPanel panelVNodeFChartCenterLeft = new JPanel();
  GridBagLayout gridBagLayoutVNodeFChartCenterLeft1 = new GridBagLayout();
  JPanel panelVNodeFChartCenterLeft1 = new JPanel();
  JLabel labelVNodeFChartCenterLeft1Width = new JLabel();
  JTextField textFieldVNodeFChartCenterLeft1Width = new JTextField(5);
  JLabel labelVNodeFChartCenterLeft1Height = new JLabel();
  JTextField textFieldVNodeFChartCenterLeft1Height = new JTextField(5);
  JLabel labelVNodeFChartCenterLeft1HeaderText = new JLabel();
  JTextField textFieldVNodeFChartCenterLeft1HeaderText = new JTextField(5);
  JLabel labelVNodeFChartCenterLeft1FooterText = new JLabel();
  JTextField textFieldVNodeFChartCenterLeft1FooterText = new JTextField(5);
  JLabel labelVNodeFChartCenterLeft1XAxisText = new JLabel();
  JTextField textFieldVNodeFChartCenterLeft1XAxisText = new JTextField(5);
  JLabel labelVNodeFChartCenterLeft1YAxisText = new JLabel();
  JTextField textFieldVNodeFChartCenterLeft1YAxisText = new JTextField(5);
  JPanel panelVNodeFChartCenterLeft2 = new JPanel();
  JCheckBox checkBoxFChartCenterLeft2VerticalGrid = new JCheckBox();
  JCheckBox checkBoxFChartCenterLeft2HorizontalGrid = new JCheckBox();
  GridBagLayout gridBagLayoutVNodeFChartCenterLeft3 = new GridBagLayout();
  JPanel panelVNodeFChartCenterLeft3 = new JPanel();
  JLabel labelVNodeFChartCenterLeft3Chart = new JLabel();
  JTextField textFieldVNodeFChartCenterLeft3Chart1Name = new JTextField(5);
  JComboBox comboBoxVNodeFChartCenterLeft3Chart1Type = new JComboBox(m_fchartTypes);
  GridBagLayout gridBagLayoutVNodeFChartCenterRight = new GridBagLayout();
  JPanel panelVNodeFChartCenterRight = new JPanel();
  JLabel labelVNodeFChartCenterRightTable = new JLabel();
  JScrollPane scrollPaneVNodeFChartCenterRightTable = new JScrollPane();
  JTable panelVNodeFChartCenterRightTable = new JTable();

  public XMDialogVNode(JFrame frame, String title, XMGraph graph, XMVNode node, boolean modal) throws Exception
  {
	super(frame, title, modal);
System.out.println("#1");

    try
    {
    if(((XMMinerFrame)frame).GetLanguage()==0) res = ResourceBundle.getBundle("xmminer.xmclient.xmgraph.XMDialogVNode_Res_En");
    else res = ResourceBundle.getBundle("xmminer.xmclient.xmgraph.XMDialogVNode_Res_Ko");
	}catch(Exception e){ e.printStackTrace(); }
System.out.println("#2");

    m_frame = frame;
    m_graph = graph;
    m_node = node;

    m_projectPath = m_graph.GetDirectory();
    m_projectName = m_graph.GetProjectName();
    m_metaFilename = m_node.GetMetaFilename();
System.out.println("#3");

    try
    {
      XMCORBATable corbaTable = new XMCORBATable(m_projectPath, m_projectName, m_metaFilename, m_node.GetUniqueId());
      m_chartTableModel = new XMChartTableModel(corbaTable, m_chartSelection);
      panelVNodeChartsCenterRightTable = new JTable(m_chartTableModel);
      m_fchartTableModel = new XMFChartTableModel(corbaTable);
      panelVNodeFChartCenterRightTable = new JTable(m_fchartTableModel);
      corbaTable.Finalize();
    }
    catch (Exception e)
    {
      throw e;
    }

    SetColumnWidth(panelVNodeChartsCenterRightTable, 0, 150);
    SetColumnWidth(panelVNodeChartsCenterRightTable, 2, 40);
    SetColumnComboEditor(panelVNodeChartsCenterRightTable, 3, m_chartSelection);

    SetColumnWidth(panelVNodeFChartCenterRightTable, 0, 150);
    SetColumnWidth(panelVNodeFChartCenterRightTable, 2, 40);
System.out.println("#4");

    try
    {
      jbInit();
      pack();
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
    }
  }

  void SetColumnWidth(JTable table, int col, int width)
  {
    TableColumn column = table.getColumnModel().getColumn(col);
    column.setPreferredWidth(width);
  }

  void SetColumnComboEditor(JTable table, int col, String[] chartTypes)
  {
    TableColumn column = table.getColumnModel().getColumn(col);
    JComboBox comboBox = new JComboBox(chartTypes);
    column.setCellEditor(new DefaultCellEditor(comboBox));
  }

  void jbInit() throws Exception
  {
    border1 = BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(Color.white,new Color(142, 142, 142)),BorderFactory.createEmptyBorder(5,5,5,5));
    panelMain.setLayout(borderLayoutMain);

    panelVNodeTable.setLayout(borderLayoutVNodeTable);
    buttonVNodeTableExit.setBorder(BorderFactory.createRaisedBevelBorder());
	buttonVNodeTableExit.setPreferredSize(new Dimension(80, 30));
	buttonVNodeTableExit.setText(res.getString("Exit"));
    buttonVNodeTableExit.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        buttonVNodeTableExit_actionPerformed(e);
      }
    });
    buttonVNodeTableShow.setBorder(BorderFactory.createRaisedBevelBorder());
	buttonVNodeTableShow.setPreferredSize(new Dimension(80, 30));
	buttonVNodeTableShow.setText(res.getString("Show"));
    buttonVNodeTableShow.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        buttonVNodeTableShow_actionPerformed(e);
      }
    });
    panelVNodeTableCenter.setLayout(gridBagLayoutVNodeTableCenter);
    labelVNodeTableWidth.setText(res.getString("Window_Width"));
    textFieldVNodeTableWidth.setText(res.getString("800"));
    textFieldVNodeTableWidth.setHorizontalAlignment(SwingConstants.RIGHT);
    labelVNodeTableHeight.setText(res.getString("Window_Height"));
    textFieldVNodeTableHeight.setText("640");
    textFieldVNodeTableHeight.setHorizontalAlignment(SwingConstants.RIGHT);
    labelVNodeTablePageSize.setText(res.getString("Page_Size"));
    textFieldVNodeTablePageSize.setText("100");
    textFieldVNodeTablePageSize.setHorizontalAlignment(SwingConstants.RIGHT);

    panelVNodeStatistics.setLayout(borderLayoutVNodeStatistics);
    buttonVNodeStatisticsShow.setBorder(BorderFactory.createRaisedBevelBorder());
    buttonVNodeStatisticsShow.setPreferredSize(new Dimension(80, 30));
    buttonVNodeStatisticsShow.setText(res.getString("Show"));
    buttonVNodeStatisticsShow.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        buttonVNodeStatisticsShow_actionPerformed(e);
      }
    });
    buttonVNodeStatisticsExit.setBorder(BorderFactory.createRaisedBevelBorder());
    buttonVNodeStatisticsExit.setPreferredSize(new Dimension(80, 30));
    buttonVNodeStatisticsExit.setText(res.getString("Exit"));
    buttonVNodeStatisticsExit.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        buttonVNodeStatisticsExit_actionPerformed(e);
      }
    });
    panelVNodeStatisticsCenter.setLayout(gridBagLayoutVNodeStatisticsCenter);
    labelVNodeStatisticsWidth.setText(res.getString("Window_Width"));
    textFieldVNodeStatisticsWidth.setText("700");
    textFieldVNodeStatisticsWidth.setHorizontalAlignment(SwingConstants.RIGHT);
    labelVNodeStatisticsHeight.setText(res.getString("Window_Height"));
    textFieldVNodeStatisticsHeight.setText("500");
    textFieldVNodeStatisticsHeight.setHorizontalAlignment(SwingConstants.RIGHT);

    panelVNodeCharts.setLayout(borderLayoutVNodeCharts);
    buttonVNodeChartsShow.setBorder(BorderFactory.createRaisedBevelBorder());
    buttonVNodeChartsShow.setPreferredSize(new Dimension(80, 30));
    buttonVNodeChartsShow.setText(res.getString("Show"));
    buttonVNodeChartsShow.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        buttonVNodeChartsShow_actionPerformed(e);
      }
    });
    buttonVNodeChartsExit.setBorder(BorderFactory.createRaisedBevelBorder());
    buttonVNodeChartsExit.setPreferredSize(new Dimension(80, 30));
    buttonVNodeChartsExit.setText(res.getString("Exit"));
    buttonVNodeChartsExit.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        buttonVNodeChartsExit_actionPerformed(e);
      }
    });
    panelVNodeChartsCenterLeft.setLayout(gridBagLayoutVNodeChartsCenterLeft);
    panelVNodeChartsCenterLeft1.setLayout(gridBagLayoutVNodeChartsCenterLeft1);
    labelVNodeChartsCenterLeft1Width.setText(res.getString("Window_Width"));
    textFieldVNodeChartsCenterLeft1Width.setText("800");
    textFieldVNodeChartsCenterLeft1Width.setColumns(17);
    textFieldVNodeChartsCenterLeft1Width.setHorizontalAlignment(SwingConstants.RIGHT);
    labelVNodeChartsCenterLeft1Height.setText(res.getString("Window_Height"));
    textFieldVNodeChartsCenterLeft1Height.setText("640");
    textFieldVNodeChartsCenterLeft1Height.setColumns(17);
    textFieldVNodeChartsCenterLeft1Height.setHorizontalAlignment(SwingConstants.RIGHT);
    labelVNodeChartsCenterLeft1HeaderText.setText(res.getString("Header_Text"));
    textFieldVNodeChartsCenterLeft1HeaderText.setText(m_projectName + ": " + m_metaFilename);
    textFieldVNodeChartsCenterLeft1HeaderText.setColumns(17);
    labelVNodeChartsCenterLeft1FooterText.setText(res.getString("Footer_Text"));
    textFieldVNodeChartsCenterLeft1FooterText.setText("");
    textFieldVNodeChartsCenterLeft1FooterText.setColumns(17);
    labelVNodeChartsCenterLeft1XAxisText.setText(res.getString("XAxis_Text"));
    textFieldVNodeChartsCenterLeft1XAxisText.setText("X");
    textFieldVNodeChartsCenterLeft1XAxisText.setColumns(17);
    labelVNodeChartsCenterLeft1YAxisText.setText(res.getString("YAxis_Text"));
    textFieldVNodeChartsCenterLeft1YAxisText.setText("Y");
    textFieldVNodeChartsCenterLeft1YAxisText.setColumns(17);
    checkBoxChartsCenterLeft2VerticalGrid.setText(res.getString("Vertical_Grid"));
    checkBoxChartsCenterLeft2HorizontalGrid.setText(res.getString("Horizontal_Grid"));
    panelVNodeChartsCenterLeft3.setLayout(gridBagLayoutVNodeChartsCenterLeft3);
    labelVNodeChartsCenterLeft3Chart1.setFont(new java.awt.Font("Dialog", 1, 12));
    labelVNodeChartsCenterLeft3Chart1.setText(res.getString("Chart_1"));
    labelVNodeChartsCenterLeft3Chart2.setFont(new java.awt.Font("Dialog", 1, 12));
    labelVNodeChartsCenterLeft3Chart2.setText(res.getString("Chart_2"));
    textFieldVNodeChartsCenterLeft3Chart1Name.setText("Chart1");
    textFieldVNodeChartsCenterLeft3Chart1Name.setColumns(11);
    textFieldVNodeChartsCenterLeft3Chart2Name.setText("Chart2");
    textFieldVNodeChartsCenterLeft3Chart2Name.setColumns(11);
    comboBoxVNodeChartsCenterLeft3Chart1Type.setToolTipText(res.getString("Chart_1_Type"));
    comboBoxVNodeChartsCenterLeft3Chart2Type.setToolTipText(res.getString("Chart_2_Type"));
    panelVNodeChartsCenterRight.setLayout(gridBagLayoutVNodeChartsCenterRight);
    labelVNodeChartsCenterRightTable.setText(res.getString("Select_Series"));
    scrollPaneVNodeChartsCenterRightTable.setPreferredSize(new Dimension(300, 300));

    panelVNodeFChart.setLayout(borderLayoutVNodeFChart);
    buttonVNodeFChartShow.setBorder(BorderFactory.createRaisedBevelBorder());
    buttonVNodeFChartShow.setPreferredSize(new Dimension(80, 30));
    buttonVNodeFChartShow.setText(res.getString("Show"));
    buttonVNodeFChartShow.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        buttonVNodeFChartShow_actionPerformed(e);
      }
    });
    buttonVNodeFChartExit.setBorder(BorderFactory.createRaisedBevelBorder());
    buttonVNodeFChartExit.setPreferredSize(new Dimension(80, 30));
    buttonVNodeFChartExit.setText(res.getString("Exit"));
    buttonVNodeFChartExit.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        buttonVNodeFChartExit_actionPerformed(e);
      }
    });
    panelVNodeFChartCenterLeft.setLayout(gridBagLayoutVNodeFChartCenterLeft);
    panelVNodeFChartCenterLeft1.setLayout(gridBagLayoutVNodeFChartCenterLeft1);
    labelVNodeFChartCenterLeft1Width.setText(res.getString("Window_Width"));
    textFieldVNodeFChartCenterLeft1Width.setText("800");
    textFieldVNodeFChartCenterLeft1Width.setColumns(17);
    textFieldVNodeFChartCenterLeft1Width.setHorizontalAlignment(SwingConstants.RIGHT);
    labelVNodeFChartCenterLeft1Height.setText(res.getString("Window_Height"));
    textFieldVNodeFChartCenterLeft1Height.setText("640");
    textFieldVNodeFChartCenterLeft1Height.setColumns(17);
    textFieldVNodeFChartCenterLeft1Height.setHorizontalAlignment(SwingConstants.RIGHT);
    labelVNodeFChartCenterLeft1HeaderText.setText(res.getString("Header_Text"));
    textFieldVNodeFChartCenterLeft1HeaderText.setText(m_projectName + ": " + m_metaFilename);
    textFieldVNodeFChartCenterLeft1HeaderText.setColumns(17);
    labelVNodeFChartCenterLeft1FooterText.setText(res.getString("Footer_Text"));
    textFieldVNodeFChartCenterLeft1FooterText.setText("");
    textFieldVNodeFChartCenterLeft1FooterText.setColumns(17);
    labelVNodeFChartCenterLeft1XAxisText.setText(res.getString("XAxis_Text"));
    textFieldVNodeFChartCenterLeft1XAxisText.setText("X");
    textFieldVNodeFChartCenterLeft1XAxisText.setColumns(17);
    labelVNodeFChartCenterLeft1YAxisText.setText(res.getString("YAxis_Text"));
    textFieldVNodeFChartCenterLeft1YAxisText.setText("Y");
    textFieldVNodeFChartCenterLeft1YAxisText.setColumns(17);
    checkBoxFChartCenterLeft2VerticalGrid.setText(res.getString("Vertical_Grid"));
    checkBoxFChartCenterLeft2HorizontalGrid.setText(res.getString("Horizontal_Grid"));
    panelVNodeFChartCenterLeft3.setLayout(gridBagLayoutVNodeFChartCenterLeft3);
    labelVNodeFChartCenterLeft3Chart.setFont(new java.awt.Font("Dialog", 1, 12));
    labelVNodeFChartCenterLeft3Chart.setText(res.getString("Frequency_Chart"));
    textFieldVNodeFChartCenterLeft3Chart1Name.setText("Frequency Chart");
    textFieldVNodeFChartCenterLeft3Chart1Name.setColumns(11);
    comboBoxVNodeFChartCenterLeft3Chart1Type.setToolTipText(res.getString("Frequency_Chart_Type"));
    panelVNodeFChartCenterRight.setLayout(gridBagLayoutVNodeFChartCenterRight);
    labelVNodeFChartCenterRightTable.setText(res.getString("Select_Series"));
    scrollPaneVNodeFChartCenterRightTable.setPreferredSize(new Dimension(300, 300));

    this.setResizable(false);
    panelMain.setBorder(border1);
    panelVNodeFChartSouth.setLayout(flowLayout1);
    flowLayout1.setAlignment(FlowLayout.RIGHT);
    panelVNodeFChartSouth.setBorder(BorderFactory.createEtchedBorder());
    panelVNodeChartsSouth.setLayout(flowLayout2);
    flowLayout2.setAlignment(FlowLayout.RIGHT);
    panelVNodeChartsSouth.setBorder(BorderFactory.createEtchedBorder());
    panelVNodeStatisticsSouth.setLayout(flowLayout3);
    flowLayout3.setAlignment(FlowLayout.RIGHT);
    panelVNodeStatisticsSouth.setBorder(BorderFactory.createEtchedBorder());
    panelVNodeTableSouth.setLayout(flowLayout4);
	flowLayout4.setAlignment(FlowLayout.RIGHT);
	panelVNodeTableSouth.setBorder(BorderFactory.createEtchedBorder());
	getContentPane().add(panelMain);
    panelMain.add(tabbedPaneVNode, BorderLayout.CENTER);

    tabbedPaneVNode.add(panelVNodeTable, res.getString("Table"));
    panelVNodeTable.add(panelVNodeTableCenter, BorderLayout.CENTER);
    panelVNodeTableCenter.add(labelVNodeTableWidth, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));
    panelVNodeTableCenter.add(textFieldVNodeTableWidth, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(10, 10, 10, 10), 0, 0));
    panelVNodeTableCenter.add(labelVNodeTableHeight, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));
    panelVNodeTableCenter.add(textFieldVNodeTableHeight, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(10, 10, 10, 10), 0, 0));
    panelVNodeTableCenter.add(labelVNodeTablePageSize, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));
    panelVNodeTableCenter.add(textFieldVNodeTablePageSize, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(10, 10, 10, 10), 0, 0));
    panelVNodeTable.add(panelVNodeTableSouth, BorderLayout.SOUTH);
    panelVNodeTableSouth.add(buttonVNodeTableShow, null);
    panelVNodeTableSouth.add(buttonVNodeTableExit, null);

    tabbedPaneVNode.add(panelVNodeStatistics, res.getString("Statistics"));
    panelVNodeStatistics.add(panelVNodeStatisticsCenter, BorderLayout.CENTER);
    panelVNodeStatisticsCenter.add(labelVNodeStatisticsWidth, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));
    panelVNodeStatisticsCenter.add(textFieldVNodeStatisticsWidth, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(10, 10, 10, 10), 0, 0));
    panelVNodeStatisticsCenter.add(labelVNodeStatisticsHeight, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));
    panelVNodeStatisticsCenter.add(textFieldVNodeStatisticsHeight, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(10, 10, 10, 10), 0, 0));
    panelVNodeStatistics.add(panelVNodeStatisticsSouth, BorderLayout.SOUTH);
    panelVNodeStatisticsSouth.add(buttonVNodeStatisticsShow, null);
    panelVNodeStatisticsSouth.add(buttonVNodeStatisticsExit, null);

    tabbedPaneVNode.add(panelVNodeCharts, res.getString("Charts"));
    panelVNodeCharts.add(panelVNodeChartsCenter, BorderLayout.CENTER);
    panelVNodeChartsCenter.add(panelVNodeChartsCenterLeft, null);
    panelVNodeChartsCenterLeft.add(panelVNodeChartsCenterLeft1, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 10, 0, 5), 0, 0));
    panelVNodeChartsCenterLeft1.add(labelVNodeChartsCenterLeft1Width, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    panelVNodeChartsCenterLeft1.add(textFieldVNodeChartsCenterLeft1Width, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 10, 5, 10), 0, 0));
    panelVNodeChartsCenterLeft1.add(labelVNodeChartsCenterLeft1Height, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    panelVNodeChartsCenterLeft1.add(textFieldVNodeChartsCenterLeft1Height, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 10, 5, 10), 0, 0));
    panelVNodeChartsCenterLeft1.add(labelVNodeChartsCenterLeft1HeaderText, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    panelVNodeChartsCenterLeft1.add(textFieldVNodeChartsCenterLeft1HeaderText, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 10, 5, 10), 0, 0));
    panelVNodeChartsCenterLeft1.add(labelVNodeChartsCenterLeft1FooterText, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    panelVNodeChartsCenterLeft1.add(textFieldVNodeChartsCenterLeft1FooterText, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 10, 5, 10), 0, 0));
    panelVNodeChartsCenterLeft1.add(labelVNodeChartsCenterLeft1XAxisText, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    panelVNodeChartsCenterLeft1.add(textFieldVNodeChartsCenterLeft1XAxisText, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 10, 5, 10), 0, 0));
    panelVNodeChartsCenterLeft1.add(labelVNodeChartsCenterLeft1YAxisText, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    panelVNodeChartsCenterLeft1.add(textFieldVNodeChartsCenterLeft1YAxisText, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 10, 10, 10), 0, 0));
    panelVNodeChartsCenterLeft.add(panelVNodeChartsCenterLeft2, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 10, 0, 5), 0, 0));
    panelVNodeChartsCenterLeft2.add(checkBoxChartsCenterLeft2VerticalGrid, null);
    panelVNodeChartsCenterLeft2.add(checkBoxChartsCenterLeft2HorizontalGrid, null);
    panelVNodeChartsCenterLeft.add(panelVNodeChartsCenterLeft3, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 10, 10, 5), 0, 0));
    panelVNodeChartsCenterLeft3.add(labelVNodeChartsCenterLeft3Chart1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 5, 5, 5), 0, 0));
    panelVNodeChartsCenterLeft3.add(labelVNodeChartsCenterLeft3Chart2, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 5, 5, 5), 0, 0));
    panelVNodeChartsCenterLeft3.add(textFieldVNodeChartsCenterLeft3Chart1Name, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    panelVNodeChartsCenterLeft3.add(textFieldVNodeChartsCenterLeft3Chart2Name, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    panelVNodeChartsCenterLeft3.add(comboBoxVNodeChartsCenterLeft3Chart1Type, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    panelVNodeChartsCenterLeft3.add(comboBoxVNodeChartsCenterLeft3Chart2Type, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    panelVNodeChartsCenter.add(panelVNodeChartsCenterRight, null);
    panelVNodeChartsCenterRight.add(labelVNodeChartsCenterRightTable, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
	        ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 5, 5, 10), 0, 0));
    panelVNodeChartsCenterRight.add(scrollPaneVNodeChartsCenterRightTable, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 10, 10), 0, 0));
    scrollPaneVNodeChartsCenterRightTable.getViewport().add(panelVNodeChartsCenterRightTable, null);
    panelVNodeCharts.add(panelVNodeChartsSouth, BorderLayout.SOUTH);
    panelVNodeChartsSouth.add(buttonVNodeChartsShow, null);
    panelVNodeChartsSouth.add(buttonVNodeChartsExit, null);

    tabbedPaneVNode.add(panelVNodeFChart, res.getString("Frequency_Chart"));
    panelVNodeFChart.add(panelVNodeFChartCenter, BorderLayout.CENTER);
    panelVNodeFChartCenter.add(panelVNodeFChartCenterLeft, null);
    panelVNodeFChartCenterLeft.add(panelVNodeFChartCenterLeft1, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 10, 0, 5), 0, 0));
    panelVNodeFChartCenterLeft1.add(labelVNodeFChartCenterLeft1Width, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    panelVNodeFChartCenterLeft1.add(textFieldVNodeFChartCenterLeft1Width, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 10, 5, 10), 0, 0));
    panelVNodeFChartCenterLeft1.add(labelVNodeFChartCenterLeft1Height, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    panelVNodeFChartCenterLeft1.add(textFieldVNodeFChartCenterLeft1Height, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 10, 5, 10), 0, 0));
    panelVNodeFChartCenterLeft1.add(labelVNodeFChartCenterLeft1HeaderText, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    panelVNodeFChartCenterLeft1.add(textFieldVNodeFChartCenterLeft1HeaderText, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 10, 5, 10), 0, 0));
    panelVNodeFChartCenterLeft1.add(labelVNodeFChartCenterLeft1FooterText, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    panelVNodeFChartCenterLeft1.add(textFieldVNodeFChartCenterLeft1FooterText, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 10, 5, 10), 0, 0));
    panelVNodeFChartCenterLeft1.add(labelVNodeFChartCenterLeft1XAxisText, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    panelVNodeFChartCenterLeft1.add(textFieldVNodeFChartCenterLeft1XAxisText, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 10, 5, 10), 0, 0));
    panelVNodeFChartCenterLeft1.add(labelVNodeFChartCenterLeft1YAxisText, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    panelVNodeFChartCenterLeft1.add(textFieldVNodeFChartCenterLeft1YAxisText, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 10, 10, 10), 0, 0));
    panelVNodeFChartCenterLeft.add(panelVNodeFChartCenterLeft2, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 10, 0, 5), 0, 0));
    panelVNodeFChartCenterLeft2.add(checkBoxFChartCenterLeft2VerticalGrid, null);
    panelVNodeFChartCenterLeft2.add(checkBoxFChartCenterLeft2HorizontalGrid, null);
    panelVNodeFChartCenterLeft.add(panelVNodeFChartCenterLeft3, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 10, 10, 5), 0, 0));
    panelVNodeFChartCenterLeft3.add(labelVNodeFChartCenterLeft3Chart, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 5, 5, 5), 0, 0));
    panelVNodeFChartCenterLeft3.add(textFieldVNodeFChartCenterLeft3Chart1Name, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    panelVNodeFChartCenterLeft3.add(comboBoxVNodeFChartCenterLeft3Chart1Type, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    panelVNodeFChartCenter.add(panelVNodeFChartCenterRight, null);
    panelVNodeFChartCenterRight.add(labelVNodeFChartCenterRightTable, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 5, 5, 10), 0, 0));
    panelVNodeFChartCenterRight.add(scrollPaneVNodeFChartCenterRightTable, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 10, 10), 0, 0));
    scrollPaneVNodeFChartCenterRightTable.getViewport().add(panelVNodeFChartCenterRightTable, null);
    panelVNodeFChart.add(panelVNodeFChartSouth, BorderLayout.SOUTH);
    panelVNodeFChartSouth.add(buttonVNodeFChartShow, null);
    panelVNodeFChartSouth.add(buttonVNodeFChartExit, null);
  }

  // Table

  void buttonVNodeTableShow_actionPerformed(ActionEvent e)
  {
    int width, height, page;
    try
    {
      width = Integer.parseInt(textFieldVNodeTableWidth.getText());
      height = Integer.parseInt(textFieldVNodeTableHeight.getText());
      page = Integer.parseInt(textFieldVNodeTablePageSize.getText());
    }
    catch (Exception exception)
    {
      width = height = 0;
      page = 256;
    }
    try
    {
      XMCORBATable corbaTable = new XMCORBATable(m_projectPath, m_projectName, m_metaFilename, m_node.GetUniqueId());
      corbaTable.Initialize(null, page);
      XMDialogTable dialog = new XMDialogTable(m_frame, res.getString("Data"), new XMTableModel(corbaTable), true, m_projectPath, m_projectName, m_node.GetUniqueId());
      if (width > 0 && height > 0)
      {
        dialog.setSize(width, height);
      }
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      Dimension dialogSize = dialog.getSize();
      if (dialogSize.height > screenSize.height)
        dialogSize.height = screenSize.height;
      if (dialogSize.width > screenSize.width)
        dialogSize.width = screenSize.width;
      dialog.setLocation((screenSize.width - dialogSize.width) / 2, (screenSize.height - dialogSize.height) / 2);
      dialog.show();
      corbaTable.Finalize();
    }
    catch (Exception exception)
    {
      JOptionPane.showMessageDialog(m_frame, res.getString("Data_was_not_prepared"));
    }
  }

  void buttonVNodeTableExit_actionPerformed(ActionEvent e)
  {
    dispose();
  }

  // CORBA
  //transient xmminer.xmvbj.xmgraph.XMBDialogVNode m_sDialogVNode = null;
  transient xmminer.xmserver.xmgraph.XMBDialogVNode m_sDialogVNode = null;

  private void SetCORBA() throws org.omg.CORBA.OBJECT_NOT_EXIST
  {
    if (m_sDialogVNode == null)
    {
      // Get the manager Id
      // Locate an account manager. Give the full POA name and the servant ID.
      try
      {
        //m_sDialogVNode = xmminer.xmvbj.xmgraph.XMBDialogVNodeHelper.bind(XMMiner.m_orb, "/xmminer_poa", "XMDialogVNode".getBytes());
        m_sDialogVNode = new xmminer.xmserver.xmgraph.XMBDialogVNode();
      }
      catch (org.omg.CORBA.OBJECT_NOT_EXIST e)
      {
        m_sDialogVNode = null;
        System.out.println("CORBA object XMDialogVNode does not exist.");
        throw(e);
      }
    }
  }

  // Statistics
  // Statistics Calculate
  transient Thread m_threadRun = null;
  transient Thread m_threadProgress = null;
  transient JDialog m_dialog;
  transient boolean m_bStopped;
  transient int m_retRun;

  String m_data[][];
  boolean m_bStatisticsCalculated = false;

  public JDialog CreateDialog(JFrame frame)
  {
    System.out.println("XMDialogVNode.CreateDialog");
    return(new XMDialogDialogVNode(frame, this, res.getString("Statistics"), true));
  }

  public class ThreadProgress implements Runnable
  {
    public void run()
    {
      JProgressBar progressBar = ((XMDialogDialogVNode)m_dialog).progressBarMain;

      int x, prevx, gap, pbMin, pbMax;

      pbMin = m_sDialogVNode.GetPBMin();
      pbMax = m_sDialogVNode.GetPBMax();
      progressBar.setMinimum(pbMin);
      progressBar.setMaximum(pbMax);
      progressBar.setValue(pbMin);
      for (x = prevx = pbMin, gap = 100; IsRunning(); prevx = x)
      {
	x = m_sDialogVNode.GetPBValue();
	progressBar.setValue(x);
	if (x == prevx && gap < 5000)
	{
	  gap += 100;
	}
	else if (x != prevx && gap > 100)
	{
	  gap -= 100;
	}
	try
	{
	  Thread.sleep(gap);
        }
	catch (InterruptedException e)
	{
	}
      }
      m_dialog.dispose();
    }
  }

  public class ThreadRun implements Runnable
  {
    public void run()
    {
      m_retRun = m_sDialogVNode.Run();
    }
  }

  public boolean IsRunning()
  {
    return(m_sDialogVNode.IsRunning());
  }

  public void StopRunning()
  {
    m_sDialogVNode.StopRunning();
    m_bStopped = true;
  }

  public int Run(JFrame frame)
  {
    // Global Parameters
    m_bStopped = false;
    m_retRun = XMGraphElement.XMGESTAT_RUN_NORUN;

    try
    {
      // DialogVNode CORBA Server
      SetCORBA();
    }
    catch (Exception exception)
    {
      JOptionPane.showMessageDialog(m_frame, res.getString("Data_was_not_prepared"));
      return(XMGraphElement.XMGESTAT_RUN_ERROR);
    }

    // Dialog
    m_dialog = CreateDialog(frame);
    //m_dialog.setSize(700, 500);
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension dialogSize = m_dialog.getSize();
    if (dialogSize.height > screenSize.height)
      dialogSize.height = screenSize.height;
    if (dialogSize.width > screenSize.width)
      dialogSize.width = screenSize.width;
    m_dialog.setLocation((screenSize.width - dialogSize.width) / 2, (screenSize.height - dialogSize.height) / 2);

    m_sDialogVNode.Initialize(m_projectName, m_metaFilename);
    m_sDialogVNode.InitializeRunning();

    // Thread
    if (m_threadRun == null || !m_threadRun.isAlive())
    {
      m_threadRun = new Thread(new ThreadRun());
      m_threadRun.start();
    }
    if (m_threadProgress == null || !m_threadProgress.isAlive())
    {
      m_threadProgress = new Thread(new ThreadProgress());
      m_threadProgress.start();
    }

    m_dialog.show();

    m_data = (m_retRun == XMGraphElement.XMGESTAT_RUN_SUCCESS) ? m_sDialogVNode.GetResult() : null;

    m_sDialogVNode.FinalizeRunning();
    m_sDialogVNode.Finalize();

    if (m_retRun != XMGraphElement.XMGESTAT_RUN_SUCCESS)
    {
      JOptionPane.showMessageDialog(m_frame, res.getString("There_are_no_data_to"));
    }

    return(m_retRun);
  }

  boolean buttonVNodeStatisticsShow_Calculate()
  {
    if (!m_bStatisticsCalculated)
    {
      m_bStatisticsCalculated = (Run(m_frame) == XMGraphElement.XMGESTAT_RUN_SUCCESS && !m_bStopped);
    }
    return(m_bStatisticsCalculated);
  }

  // Statistics Show
  void buttonVNodeStatisticsShow_Show()
  {
    int width, height;
    try
    {
      width = Integer.parseInt(textFieldVNodeStatisticsWidth.getText());
      height = Integer.parseInt(textFieldVNodeStatisticsHeight.getText());
    }
    catch (Exception exception)
    {
      width = height = 0;
    }
    JDialog dialog = new XMDialogVNodeStatistics(m_frame, res.getString("Statistics"), m_data, true, m_projectPath, m_projectName, m_node.GetUniqueId());
    if (width > 0 && height > 0)
    {
      dialog.setSize(width, height);
    }
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension dialogSize = dialog.getSize();
    if (dialogSize.height > screenSize.height)
      dialogSize.height = screenSize.height;
    if (dialogSize.width > screenSize.width)
      dialogSize.width = screenSize.width;
    dialog.setLocation((screenSize.width - dialogSize.width) / 2, (screenSize.height - dialogSize.height) / 2);
    dialog.show();
  }

  // Statistics Action Performed
  void buttonVNodeStatisticsShow_actionPerformed(ActionEvent e)
  {
    if (buttonVNodeStatisticsShow_Calculate())
    {
      buttonVNodeStatisticsShow_Show();
    }
  }

  void buttonVNodeStatisticsExit_actionPerformed(ActionEvent e)
  {
    dispose();
  }

  // Charts

  void buttonVNodeChartsShow_actionPerformed(ActionEvent e)
  {
    int width, height, page = 512;
    try
    {
      width = Integer.parseInt(textFieldVNodeChartsCenterLeft1Width.getText());
      height = Integer.parseInt(textFieldVNodeChartsCenterLeft1Height.getText());
    }
    catch (Exception exception)
    {
      width = height = 0;
    }

    String headerText, footerText, xAxisText, yAxisText;
    boolean bVerticalGrid, bHorizontalGrid;
    String chart1Name, chart2Name;
    String chart1Type, chart2Type;

    headerText = textFieldVNodeChartsCenterLeft1HeaderText.getText();
    footerText = textFieldVNodeChartsCenterLeft1FooterText.getText();
    xAxisText = textFieldVNodeChartsCenterLeft1XAxisText.getText();
    yAxisText = textFieldVNodeChartsCenterLeft1YAxisText.getText();
    bVerticalGrid = checkBoxChartsCenterLeft2VerticalGrid.isSelected();
    bHorizontalGrid = checkBoxChartsCenterLeft2HorizontalGrid.isSelected();
    chart1Name = textFieldVNodeChartsCenterLeft3Chart1Name.getText();
    chart2Name = textFieldVNodeChartsCenterLeft3Chart2Name.getText();
    chart1Type = (String)comboBoxVNodeChartsCenterLeft3Chart1Type.getSelectedItem();
    chart2Type = (String)comboBoxVNodeChartsCenterLeft3Chart2Type.getSelectedItem();

    int i, k, n1, n2, nColumns, nSeries;
    String chartSelection;
//    int seriesMap1[];
	int seriesMap1[], seriesMap2[];
    String listColumn[];
    XMCORBATable corbaTable;

    try
    {
      corbaTable = new XMCORBATable(m_projectPath, m_projectName, m_metaFilename, m_node.GetUniqueId());
    }
    catch (Exception exception)
    {
      JOptionPane.showMessageDialog(m_frame, res.getString("Data_was_not_prepared"));
      return;
    }

    nColumns = corbaTable.GetColumnCount();
    for (i = nSeries = 0; i < nColumns; i++)
    {
      chartSelection = (String)m_chartTableModel.getValueAt(i, 3);
      if (((Boolean)m_chartTableModel.getValueAt(i, 2)).booleanValue()
          || !chartSelection.equalsIgnoreCase(m_chartSelection[0]))
      {
        nSeries++;
      }
    }
    if (nSeries <= 0)
    {
      corbaTable.Finalize();
      JOptionPane.showMessageDialog(m_frame, res.getString("There_are_no_data_to1"));
      return;
    }

    seriesMap1 = new int[nSeries];
    seriesMap2 = new int[nSeries];
    listColumn = new String[nSeries];
    for (i = k = 0, n1 = n2 = 1; i < nColumns; i++)
    {
      chartSelection = (String)m_chartTableModel.getValueAt(i, 3);
      if (((Boolean)m_chartTableModel.getValueAt(i, 2)).booleanValue())
      {
        seriesMap1[k] = 0;
        seriesMap2[k] = 0;
        listColumn[k] = corbaTable.GetColumnName(i);
        k++;
      }
      else if (!chartSelection.equalsIgnoreCase(m_chartSelection[0]))
      {
        if (chartSelection.equalsIgnoreCase(m_chartSelection[1]))
        {
          seriesMap1[k] = n1++;
          seriesMap2[k] = -1;
        }
        else
        {
          seriesMap1[k] = -1;
          seriesMap2[k] = n2++;
        }
        listColumn[k] = corbaTable.GetColumnName(i);
        k++;
      }
    }

    corbaTable.Initialize(listColumn, page);
    XMChartDataModel chartDataModel1 = new XMChartDataModel(chart1Name, corbaTable, seriesMap1, chart1Type);
    XMChartDataModel chartDataModel2 = new XMChartDataModel(chart2Name, corbaTable, seriesMap2, chart2Type);
    XMChartModel chartModel = new XMChartModel(chartDataModel1, chartDataModel2);
    chartModel.SetHeaderText(headerText);
    chartModel.SetFooterText(footerText);
    chartModel.SetAxisTitle(xAxisText, yAxisText, null, null);
    chartModel.SetAxisAnnotations("Value", "Value", "Value", "Value");
    chartModel.SetBAxisGrid(bVerticalGrid, bHorizontalGrid, false, false);

    XMDialogChart dialog = new XMDialogChart(m_frame, res.getString("Chart"), chartModel, true);
    if (width > 0 && height > 0)
    {
      dialog.setSize(width, height);
    }
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension dialogSize = dialog.getSize();
    if (dialogSize.height > screenSize.height)
      dialogSize.height = screenSize.height;
    if (dialogSize.width > screenSize.width)
      dialogSize.width = screenSize.width;
    dialog.setLocation((screenSize.width - dialogSize.width) / 2, (screenSize.height - dialogSize.height) / 2);
    dialog.show();
    corbaTable.Finalize();
  }

  void buttonVNodeChartsExit_actionPerformed(ActionEvent e)
  {
    dispose();
  }

  // Frequency Chart
  // Frequency Chart Calculate
  String m_columnFChartCalculated = null;
  Border border1;
  FlowLayout flowLayout1 = new FlowLayout();
  FlowLayout flowLayout2 = new FlowLayout();
  FlowLayout flowLayout3 = new FlowLayout();
  FlowLayout flowLayout4 = new FlowLayout();

  public int FChartRun(XMCORBAFTable corbaFTable, String columnName)
  {
    XMFChartRunModel fChartRunModel = new XMFChartRunModel(corbaFTable, columnName);
    XMCORBARun corbaRun = new XMCORBARun(fChartRunModel);
    int ret = corbaRun.Run(m_frame);

    if (ret != XMGraphElement.XMGESTAT_RUN_SUCCESS)
    {
      JOptionPane.showMessageDialog(m_frame, res.getString("There_are_no_data_to1"));
    }

    return(fChartRunModel.m_bStopped ? XMGraphElement.XMGESTAT_RUN_NORUN : ret);
  }

  boolean buttonVNodeFChartShow_Calculate(XMCORBAFTable corbaFTable, String columnName)
  {
    if (m_columnFChartCalculated == null || !m_columnFChartCalculated.equalsIgnoreCase(columnName))
    {
      m_columnFChartCalculated = (FChartRun(corbaFTable, columnName) == XMGraphElement.XMGESTAT_RUN_SUCCESS) ? columnName : null;
    }
    return(m_columnFChartCalculated != null);
  }

  // Frequency Chart Show
  void buttonVNodeFChartShow_Show(XMCORBAFTable corbaFTable, String columnName)
  {
    int width, height, page = 512;
    try
    {
      width = Integer.parseInt(textFieldVNodeFChartCenterLeft1Width.getText());
      height = Integer.parseInt(textFieldVNodeFChartCenterLeft1Height.getText());
    }
    catch (Exception exception)
    {
      width = height = 0;
    }

    String headerText, footerText, xAxisText, yAxisText;
    boolean bVerticalGrid, bHorizontalGrid;
    String chart1Name, chart2Name;
    String chart1Type, chart2Type;

    headerText = textFieldVNodeFChartCenterLeft1HeaderText.getText();
    footerText = textFieldVNodeFChartCenterLeft1FooterText.getText();
    xAxisText = textFieldVNodeFChartCenterLeft1XAxisText.getText();
    yAxisText = textFieldVNodeFChartCenterLeft1YAxisText.getText();
    bVerticalGrid = checkBoxFChartCenterLeft2VerticalGrid.isSelected();
    bHorizontalGrid = checkBoxFChartCenterLeft2HorizontalGrid.isSelected();
    chart1Name = textFieldVNodeFChartCenterLeft3Chart1Name.getText();
    chart2Name = "Chart 2";
    chart1Type = (String)comboBoxVNodeFChartCenterLeft3Chart1Type.getSelectedItem();
    chart2Type = "PLOT";

    XMFChartDataModel chartDataModel1 = new XMFChartDataModel(chart1Name, corbaFTable, columnName, chart1Type);
    XMFChartDataModel chartDataModel2 = new XMFChartDataModel(chart2Name, null, columnName, chart2Type);
    XMChartModel chartModel = new XMChartModel(chartDataModel1, chartDataModel2);
    chartModel.SetHeaderText(headerText);
    chartModel.SetFooterText(footerText);
    chartModel.SetAxisTitle(xAxisText, yAxisText, null, null);
    chartModel.SetAxisAnnotations("Point_Labels", "Value", "Value", "Value");
    chartModel.SetBAxisGrid(bVerticalGrid, bHorizontalGrid, false, false);

    XMDialogChart dialog = new XMDialogChart(m_frame, res.getString("Frequency_Chart"), chartModel, true);
    if (width > 0 && height > 0)
    {
      dialog.setSize(width, height);
    }
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension dialogSize = dialog.getSize();
    if (dialogSize.height > screenSize.height)
      dialogSize.height = screenSize.height;
    if (dialogSize.width > screenSize.width)
      dialogSize.width = screenSize.width;
    dialog.setLocation((screenSize.width - dialogSize.width) / 2, (screenSize.height - dialogSize.height) / 2);
    dialog.show();
  }

  // Frequency Chart Action Performed
  void buttonVNodeFChartShow_actionPerformed(ActionEvent e)
  {
    int nRows = m_fchartTableModel.getRowCount();
    String columnName = null;
    for (int i = 0; i < nRows; i++)
    {
      if (((Boolean)m_fchartTableModel.getValueAt(i, 2)).booleanValue())
      {
        columnName = (String)m_fchartTableModel.getValueAt(i, 0);
	break;
      }
    }
    if (columnName == null) return;

    XMCORBAFTable corbaFTable;
    try
    {
      corbaFTable = new XMCORBAFTable(m_projectName, m_metaFilename);
    }
    catch (Exception exception)
    {
      JOptionPane.showMessageDialog(m_frame, res.getString("CORBA_object1"));
      return;
    }

    corbaFTable.Initialize();

    if (buttonVNodeFChartShow_Calculate(corbaFTable, columnName))
    {
      buttonVNodeFChartShow_Show(corbaFTable, columnName);
    }

    corbaFTable.Finalize();
  }

  void buttonVNodeFChartExit_actionPerformed(ActionEvent e)
  {
    dispose();
  }
}

class XMChartTableModel extends AbstractTableModel
{
  String[] m_chartSelection;

  String[] m_columnNames = { "Series", "Type", "X-Axis", "Chart" };
  Object[][] m_data;
  int[] m_columnTypes;

  public XMChartTableModel(XMCORBATable corbaTable, String[] chartSelection)
  {
    super();

    m_chartSelection = chartSelection;

    int nSeries = corbaTable.GetColumnCount();
    m_data = new Object[nSeries][m_columnNames.length];
    m_columnTypes = new int[nSeries];
    int i;
    boolean bFirstNumeric;
    String columnInfo[];
    for (i = 0, bFirstNumeric = true; i < nSeries; i++)
    {
      m_data[i][0] = corbaTable.GetColumnName(i);
      columnInfo = corbaTable.GetProfiles(corbaTable.GetColumnName(i));
      m_data[i][1] = columnInfo[0];
      m_columnTypes[i] = XMLib.GetColumnType(columnInfo);
      switch (m_columnTypes[i])
      {
      case XMLib.XMDBTYPE_INTEGER:
      case XMLib.XMDBTYPE_REAL:
        m_data[i][2] = new Boolean(bFirstNumeric);
        bFirstNumeric = false;
        m_data[i][3] = m_chartSelection[1];
        break;
      case XMLib.XMDBTYPE_STRING:
      case XMLib.XMDBTYPE_ENUMERATION:
      case XMLib.XMDBTYPE_DATE:
      case XMLib.XMDBTYPE_UNKNOWN:
      default:
        m_data[i][2] = new Boolean(false);
        m_data[i][3] = m_chartSelection[0];
        break;
      }
    }
  }

  public int getRowCount()
  {
    return(m_data.length);
  }

  public int getColumnCount()
  {
    return(m_columnNames.length);
  }

  public String getColumnName(int col)
  {
    return(m_columnNames[col]);
  }

  public Class getColumnClass(int col)
  {
    return(m_data[0][col].getClass());
  }

  public Object getValueAt(int row, int col)
  {
    return(m_data[row][col]);
  }

  public boolean isCellEditable(int row, int col)
  {
    switch (col)
    {
    case 2:
    case 3:
      switch (m_columnTypes[row])
      {
      case XMLib.XMDBTYPE_INTEGER:
      case XMLib.XMDBTYPE_REAL:
        return(true);
      case XMLib.XMDBTYPE_STRING:
      case XMLib.XMDBTYPE_ENUMERATION:
      case XMLib.XMDBTYPE_DATE:
      case XMLib.XMDBTYPE_UNKNOWN:
      default:
        return(false);
      }
    case 0:
    case 1:
    default:
      return(false);
    }
  }

  public void setValueAt(Object value, int row, int col)
  {
    m_data[row][col] = value;
    fireTableCellUpdated(row, col);
    if (col == 2 && ((Boolean)value).booleanValue())
    {
      for (int i = 0; i < m_data.length; i++)
      {
        if (i != row && ((Boolean)m_data[i][col]).booleanValue())
        {
          m_data[i][col] = new Boolean(false);
          fireTableCellUpdated(i, col);
        }
      }
    }
  }
}

class XMFChartTableModel extends AbstractTableModel
{
  String[] m_columnNames = { "Series", "Type", "Frequency" };
  Object[][] m_data;
  int[] m_columnTypes;

  public XMFChartTableModel(XMCORBATable corbaTable)
  {
    super();

    int nSeries = corbaTable.GetColumnCount();
    m_data = new Object[nSeries][m_columnNames.length];
    m_columnTypes = new int[nSeries];
    int i;
    boolean bFirstNumeric;
    String columnInfo[];
    for (i = 0, bFirstNumeric = true; i < nSeries; i++)
    {
      m_data[i][0] = corbaTable.GetColumnName(i);
      columnInfo = corbaTable.GetProfiles(corbaTable.GetColumnName(i));
      m_data[i][1] = columnInfo[0];
      m_columnTypes[i] = XMLib.GetColumnType(columnInfo);
      switch (m_columnTypes[i])
      {
      case XMLib.XMDBTYPE_INTEGER:
      case XMLib.XMDBTYPE_REAL:
        m_data[i][2] = new Boolean(false);
        break;
      case XMLib.XMDBTYPE_STRING:
      case XMLib.XMDBTYPE_ENUMERATION:
      case XMLib.XMDBTYPE_DATE:
      case XMLib.XMDBTYPE_UNKNOWN:
      default:
        m_data[i][2] = new Boolean(bFirstNumeric);
        bFirstNumeric = false;
        break;
      }
    }
  }

  public int getRowCount()
  {
    return(m_data.length);
  }

  public int getColumnCount()
  {
    return(m_columnNames.length);
  }

  public String getColumnName(int col)
  {
    return(m_columnNames[col]);
  }

  public Class getColumnClass(int col)
  {
    return(m_data[0][col].getClass());
  }

  public Object getValueAt(int row, int col)
  {
    return(m_data[row][col]);
  }

  public boolean isCellEditable(int row, int col)
  {
    switch (col)
    {
    case 2:
      switch (m_columnTypes[row])
      {
      case XMLib.XMDBTYPE_INTEGER:
      case XMLib.XMDBTYPE_REAL:
        return(true);
      case XMLib.XMDBTYPE_STRING:
      case XMLib.XMDBTYPE_ENUMERATION:
      case XMLib.XMDBTYPE_DATE:
      case XMLib.XMDBTYPE_UNKNOWN:
      default:
        return(true);
      }
    case 0:
    case 1:
    default:
      return(false);
    }
  }

  public void setValueAt(Object value, int row, int col)
  {
    m_data[row][col] = value;
    fireTableCellUpdated(row, col);
    if (col == 2 && ((Boolean)value).booleanValue())
    {
      for (int i = 0; i < m_data.length; i++)
      {
        if (i != row && ((Boolean)m_data[i][col]).booleanValue())
        {
          m_data[i][col] = new Boolean(false);
          fireTableCellUpdated(i, col);
        }
      }
    }
  }
}

class XMFChartRunModel extends XMCORBARunModel
{
  static ResourceBundle res = ResourceBundle.getBundle("xmminer.xmclient.xmgraph.XMDialogVNode_Res_En");
  XMCORBAFTable m_corbaFTable;
  String m_columnName;

  public XMFChartRunModel(XMCORBAFTable corbaFTable, String columnName)
  {
    m_corbaFTable = corbaFTable;
    m_columnName = columnName;
  }

  // Override
  public int InitializeRunning()
  {
    return(m_corbaFTable.InitializeRunning());
  }

  // Override
  public boolean IsRunning()
  {
    return(m_corbaFTable.IsRunning());
  }

  // Override
  public void OnStopRunning()
  {
    m_corbaFTable.StopRunning();
  }

  // Override
  public int OnRun()
  {
    return(m_corbaFTable.Run(m_columnName));
  }

  // Override
  public XMDialogProgress CreateDialog(JFrame frame)
  {
    return(new XMDialogProgress(frame, this, res.getString("Frequency_Chart"), true));
  }

  // Override
  public int GetPBMin()
  {
    return(m_corbaFTable.GetPBMin());
  }

  // Override
  public int GetPBMax()
  {
    return(m_corbaFTable.GetPBMax());
  }

  // Override
  public int GetPBValue()
  {
    return(m_corbaFTable.GetPBValue());
  }

  // Override
  public String GetPBDescription()
  {
    return(m_corbaFTable.GetPBDescription());
  }

  // Override
  public void FinalizeRunning()
  {
    m_corbaFTable.FinalizeRunning();
  }
}
