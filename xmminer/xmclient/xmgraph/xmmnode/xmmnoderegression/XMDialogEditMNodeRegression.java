
//Title:        XM-Miner
//Version:
//Copyright:    Copyright (c) 1999
//Author:       Yong Uk Song
//Company:      DIS
//Description:  XM-Miner

package xmminer.xmclient.xmgraph.xmmnode.xmmnoderegression;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
//import com.borland.jbcl.layout.*;

import xmminer.xmlib.*;
import xmminer.xmclient.*;
import xmminer.xmclient.xmlib.*;
import xmminer.xmclient.xmgraph.*;
import xmminer.xmclient.xmgraph.xmmnode.xmmnoderegression.*;

public class XMDialogEditMNodeRegression extends JDialog
{
  JFrame m_frame;
  XMGraph m_graph;
  XMMNodeRegression m_node;
  XMVNode m_inNode;

  String m_projectPath, m_projectName, m_metaFilename;

  String[] m_variableSelection =
  {
    "<NONE>",
    "Y",
    "X"
  };

  XMEditRegressionTableModel m_editRegressionTableModel;

  JPanel panelMain = new JPanel();
  BorderLayout borderLayoutMain = new BorderLayout();
  JTabbedPane tabbedPaneEditMNodeRegression = new JTabbedPane();
  JPanel panelEditMNodeRegressionBasic = new JPanel();

  BorderLayout borderLayoutEditMNodeRegressionBasic = new BorderLayout();
  JPanel panelEditMNodeRegressionBasicSouth = new JPanel();
  JButton buttonEditMNodeRegressionBasicOK = new JButton();
  JButton buttonEditMNodeRegressionBasicCancel = new JButton();
  JPanel panelEditMNodeRegressionBasicCenter = new JPanel();
  JLabel labelEditMNodeRegressionBasicCenterTable = new JLabel();
  JScrollPane scrollPaneEditMNodeRegressionBasicCenterTable = new JScrollPane();
  JTable panelEditMNodeRegressionBasicCenterTable;
  JPanel jPanel1 = new JPanel();
  FlowLayout flowLayout1 = new FlowLayout();
  ImageIcon imageBanner = new ImageIcon(xmminer.xmclient.xmgraph.xmmnode.xmmnoderegression.XMDialogEditMNodeRegression.class.getResource("../../../images/reg_banner.jpg"));
  JLabel jLabel1 = new JLabel(imageBanner);
  BorderLayout borderLayout1 = new BorderLayout();
  BorderLayout borderLayout2 = new BorderLayout();
  Border border1;
  Border border2;
  JPanel jPanel2 = new JPanel();
  JCheckBox checkBoxEditMNodeRegressionBasicCenterIntercept = new JCheckBox();
  BorderLayout borderLayout3 = new BorderLayout();

  public XMDialogEditMNodeRegression(JFrame frame, String title, XMGraph graph, XMMNodeRegression node, boolean modal) throws Exception
  {
    super(frame, title, modal);

    m_frame = frame;
    m_graph = graph;
    m_node = node;

    try
    {
      m_inNode = (XMVNode)m_node.GetInElement(0, m_graph);
	  m_projectPath = m_graph.GetDirectory();
      m_projectName = m_graph.GetProjectName();
      m_metaFilename = m_inNode.GetMetaFilename();

      XMCORBATable corbaTable = new XMCORBATable(m_projectPath, m_projectName, m_metaFilename, m_node.GetUniqueId());
      m_editRegressionTableModel = new XMEditRegressionTableModel(m_node, corbaTable, m_variableSelection);
      panelEditMNodeRegressionBasicCenterTable = new JTable(m_editRegressionTableModel);
      corbaTable.Finalize();
    }
    catch (Exception e)
    {
      throw e;
    }

    SetColumnWidth(panelEditMNodeRegressionBasicCenterTable, 0, 150);
    //SetColumnWidth(panelEditMNodeRegressionBasicCenterTable, 2, 40);
    SetColumnComboEditor(panelEditMNodeRegressionBasicCenterTable, 2, m_variableSelection);

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
    border1 = BorderFactory.createEtchedBorder(Color.white,new Color(142, 142, 142));
    border2 = BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(Color.white,new Color(142, 142, 142)),BorderFactory.createEmptyBorder(0,5,0,0));
    panelMain.setLayout(borderLayoutMain);

    panelEditMNodeRegressionBasic.setLayout(borderLayoutEditMNodeRegressionBasic);
    buttonEditMNodeRegressionBasicOK.setBorder(BorderFactory.createRaisedBevelBorder());
    buttonEditMNodeRegressionBasicOK.setPreferredSize(new Dimension(90, 29));
    buttonEditMNodeRegressionBasicOK.setText("확인");
    buttonEditMNodeRegressionBasicOK.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        buttonEditMNodeRegressionBasicOK_actionPerformed(e);
      }
    });
    buttonEditMNodeRegressionBasicCancel.setBorder(BorderFactory.createRaisedBevelBorder());
    buttonEditMNodeRegressionBasicCancel.setPreferredSize(new Dimension(90, 29));
    buttonEditMNodeRegressionBasicCancel.setText("취소");
    buttonEditMNodeRegressionBasicCancel.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        buttonEditMNodeRegressionBasicCancel_actionPerformed(e);
      }
    });

    labelEditMNodeRegressionBasicCenterTable.setBorder(border2);
	labelEditMNodeRegressionBasicCenterTable.setPreferredSize(new Dimension(76, 30));
	labelEditMNodeRegressionBasicCenterTable.setText("Select Series");
    scrollPaneEditMNodeRegressionBasicCenterTable.setPreferredSize(new Dimension(300, 300));
    panelEditMNodeRegressionBasicCenter.setLayout(borderLayout2);
    jPanel1.setPreferredSize(new Dimension(500, 50));
    jPanel1.setLayout(borderLayout1);
    panelEditMNodeRegressionBasicSouth.setLayout(flowLayout1);
    flowLayout1.setAlignment(FlowLayout.RIGHT);
    flowLayout1.setHgap(10);
    flowLayout1.setVgap(10);
    panelEditMNodeRegressionBasicSouth.setBorder(BorderFactory.createEtchedBorder());
    jLabel1.setBorder(BorderFactory.createEtchedBorder());
    checkBoxEditMNodeRegressionBasicCenterIntercept.setPreferredSize(new Dimension(76, 30));
	checkBoxEditMNodeRegressionBasicCenterIntercept.setBorder(null);
	checkBoxEditMNodeRegressionBasicCenterIntercept.setSelected(m_node.m_bIntercept);
	checkBoxEditMNodeRegressionBasicCenterIntercept.setText("Intercept");
	checkBoxEditMNodeRegressionBasicCenterIntercept.setHorizontalAlignment(SwingConstants.CENTER);
	checkBoxEditMNodeRegressionBasicCenterIntercept.setActionCommand("checkBoxEditMNodeRegressionBasicCenterIntercept");
	jPanel2.setBorder(BorderFactory.createEtchedBorder());
	jPanel2.setLayout(borderLayout3);
	this.setResizable(false);
	getContentPane().add(panelMain);

    panelMain.add(tabbedPaneEditMNodeRegression, BorderLayout.CENTER);
    panelMain.add(jPanel1, BorderLayout.NORTH);
    jPanel1.add(jLabel1, BorderLayout.CENTER);
    tabbedPaneEditMNodeRegression.add(panelEditMNodeRegressionBasic, "Basic");
    panelEditMNodeRegressionBasic.add(panelEditMNodeRegressionBasicSouth, BorderLayout.SOUTH);
    panelEditMNodeRegressionBasicSouth.add(buttonEditMNodeRegressionBasicOK, null);
    panelEditMNodeRegressionBasicSouth.add(buttonEditMNodeRegressionBasicCancel, null);
    panelEditMNodeRegressionBasic.add(panelEditMNodeRegressionBasicCenter, BorderLayout.CENTER);
    panelEditMNodeRegressionBasicCenter.add(labelEditMNodeRegressionBasicCenterTable, BorderLayout.NORTH);
    panelEditMNodeRegressionBasicCenter.add(scrollPaneEditMNodeRegressionBasicCenterTable, BorderLayout.CENTER);
	panelEditMNodeRegressionBasicCenter.add(jPanel2, BorderLayout.SOUTH);
	jPanel2.add(checkBoxEditMNodeRegressionBasicCenterIntercept, BorderLayout.CENTER);
    scrollPaneEditMNodeRegressionBasicCenterTable.getViewport().add(panelEditMNodeRegressionBasicCenterTable, null);
  }

  // Basic

  void buttonEditMNodeRegressionBasicOK_actionPerformed(ActionEvent e)
  {
    XMCORBATable corbaTable;
    int i, nColumns, nSeries;
    String x[], variableSelection;

    try
    {
      corbaTable = new XMCORBATable(m_projectPath, m_projectName, m_metaFilename, m_node.GetUniqueId());
    }
    catch (Exception exception)
    {
      JOptionPane.showMessageDialog(m_frame, "Data was not prepared.");
      return;
    }

    nColumns = corbaTable.GetColumnCount();
    x = new String[nColumns];
    m_node.m_y = null;
    for (i = nSeries = 0; i < nColumns; i++)
    {
      variableSelection = (String)m_editRegressionTableModel.getValueAt(i, 2);
      if (variableSelection.equalsIgnoreCase(m_variableSelection[1]))
      {
        m_node.m_y = corbaTable.GetColumnName(i);
      }
      else if (variableSelection.equalsIgnoreCase(m_variableSelection[2]))
      {
        x[nSeries++] = corbaTable.GetColumnName(i);
      }
    }
    m_node.m_x = XMLib.SqueezeArray(x, nSeries);
    m_node.m_bIntercept = checkBoxEditMNodeRegressionBasicCenterIntercept.isSelected();

    corbaTable.Finalize();
    if (m_node.m_y == null)
    {
      JOptionPane.showMessageDialog(m_frame, "Variable Y is not specified.");
    }
    else if (nSeries <= 0)
    {
      JOptionPane.showMessageDialog(m_frame, "Variables Xs are not specified.");
    }
    else
    {
      m_node.m_bEditOK = true;
      dispose();
    }
  }

  void buttonEditMNodeRegressionBasicCancel_actionPerformed(ActionEvent e)
  {
    m_node.m_bEditOK = false;
    dispose();
  }
}

class XMEditRegressionTableModel extends AbstractTableModel
{
  XMMNodeRegression m_node;
  String[] m_variableSelection;

  String[] m_columnNames = { "Series", "Type", "Axis" };
  Object[][] m_data;
  int[] m_columnTypes;

  public XMEditRegressionTableModel(XMMNodeRegression node, XMCORBATable corbaTable, String[] variableSelection)
  {
    super();

    m_node = node;
    m_variableSelection = variableSelection;

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
        if (m_node.m_y == null || m_node.m_x == null)
        {
          m_data[i][2] = bFirstNumeric ? m_variableSelection[1] : m_variableSelection[2];
          bFirstNumeric = false;
        }
        else
        {
          if (m_node.m_y.equalsIgnoreCase((String)m_data[i][0]))
          {
            m_data[i][2] = m_variableSelection[1];
          }
          else if (XMLib.IsMemberIgnoreCase(m_node.m_x, (String)m_data[i][0]))
          {
            m_data[i][2] = m_variableSelection[2];
          }
          else
          {
            m_data[i][2] = m_variableSelection[0];
          }
        }
        break;
      case XMLib.XMDBTYPE_STRING:
      case XMLib.XMDBTYPE_ENUMERATION:
      case XMLib.XMDBTYPE_DATE:
      case XMLib.XMDBTYPE_UNKNOWN:
      default:
        m_data[i][2] = m_variableSelection[0];
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
    if (col == 2 && ((String)value).equalsIgnoreCase(m_variableSelection[1]))
    {
      for (int i = 0; i < m_data.length; i++)
      {
        if (i != row && ((String)m_data[i][col]).equalsIgnoreCase(m_variableSelection[1]))
        {
          m_data[i][col] = m_variableSelection[2];
          fireTableCellUpdated(i, col);
        }
      }
    }
  }
}
