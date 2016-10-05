
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

public class XMDialogShowMNodeRegression extends JDialog
{
  JFrame m_frame;
  XMGraph m_graph;
  XMMNodeRegression m_node;

  XMShowRegressionTableModel m_showRegressionTableModel;

  JPanel panelMain = new JPanel();
  BorderLayout borderLayoutMain = new BorderLayout();
  JTabbedPane tabbedPaneShowMNodeRegression = new JTabbedPane();
  JPanel panelShowMNodeRegressionBasic = new JPanel();

  BorderLayout borderLayoutShowMNodeRegressionBasic = new BorderLayout();
  JPanel panelShowMNodeRegressionBasicSouth = new JPanel();
  JButton buttonShowMNodeRegressionBasicOK = new JButton();
  JPanel panelShowMNodeRegressionBasicCenter = new JPanel();
  GridBagLayout gridBagLayoutShowMNodeRegressionBasicCenter = new GridBagLayout();
  JLabel labelShowMNodeRegressionBasicCenterTable = new JLabel();
  JScrollPane scrollPaneShowMNodeRegressionBasicCenterTable = new JScrollPane();
  JTable panelShowMNodeRegressionBasicCenterTable;
  JPanel panelShowMNodeRegressionSS = new JPanel();
  BorderLayout borderLayoutShowMNodeRegressionSS = new BorderLayout();
  JPanel panelShowMNodeRegressionSSSouth = new JPanel();
  JButton buttonShowMNodeRegressionSSOK = new JButton();
  JPanel panelShowMNodeRegressionSSCenter = new JPanel();
  GridBagLayout gridBagLayoutShowMNodeRegressionSSCenter = new GridBagLayout();
  JLabel labelShowMNodeRegressionSSCenterSource = new JLabel();
  JLabel labelShowMNodeRegressionSSCenterDF = new JLabel();
  JLabel labelShowMNodeRegressionSSCenterSS = new JLabel();
  JLabel labelShowMNodeRegressionSSCenterRegression = new JLabel();
  JLabel labelShowMNodeRegressionSSCenterResidual = new JLabel();
  JLabel labelShowMNodeRegressionSSCenterTotal = new JLabel();
  JTextField textFieldShowMNodeRegressionSSCenter2 = new JTextField();
  JTextField textFieldShowMNodeRegressionSSCenterBXY = new JTextField();
  JTextField textFieldShowMNodeRegressionSSCenterN2 = new JTextField();
  JTextField textFieldShowMNodeRegressionSSCenterYYBXY = new JTextField();
  JTextField textFieldShowMNodeRegressionSSCenterN = new JTextField();
  JTextField textFieldShowMNodeRegressionSSCenterYY = new JTextField();

  public XMDialogShowMNodeRegression(JFrame frame, String title, XMGraph graph, XMMNodeRegression node, boolean modal)
  {
    super(frame, title, modal);

    m_frame = frame;
    m_graph = graph;
    m_node = node;

    m_showRegressionTableModel = new XMShowRegressionTableModel(m_node);
    panelShowMNodeRegressionBasicCenterTable = new JTable(m_showRegressionTableModel);

    SetColumnWidth(panelShowMNodeRegressionBasicCenterTable, 0, 150);
    //SetColumnWidth(panelShowMNodeRegressionBasicCenterTable, 1, 100);

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

  void jbInit() throws Exception
  {
    panelMain.setLayout(borderLayoutMain);

    panelShowMNodeRegressionBasic.setLayout(borderLayoutShowMNodeRegressionBasic);
    buttonShowMNodeRegressionBasicOK.setText("OK");
    buttonShowMNodeRegressionBasicOK.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        buttonShowMNodeRegressionBasicOK_actionPerformed(e);
      }
    });

    labelShowMNodeRegressionBasicCenterTable.setText("Estimated B");
    scrollPaneShowMNodeRegressionBasicCenterTable.setPreferredSize(new Dimension(300, 300));
    panelShowMNodeRegressionBasicCenter.setLayout(gridBagLayoutShowMNodeRegressionBasicCenter);
    panelShowMNodeRegressionSS.setLayout(borderLayoutShowMNodeRegressionSS);
    buttonShowMNodeRegressionSSOK.setText("OK");
    buttonShowMNodeRegressionSSOK.addActionListener(new java.awt.event.ActionListener()
    {

      public void actionPerformed(ActionEvent e)
      {
        buttonShowMNodeRegressionSSOK_actionPerformed(e);
      }
    });
    panelShowMNodeRegressionSSCenter.setLayout(gridBagLayoutShowMNodeRegressionSSCenter);
    labelShowMNodeRegressionSSCenterSource.setText("Source");
    labelShowMNodeRegressionSSCenterDF.setText("df");
    labelShowMNodeRegressionSSCenterSS.setText("SS");
    labelShowMNodeRegressionSSCenterRegression.setText("Regression");
    labelShowMNodeRegressionSSCenterResidual.setText("Residual");
    labelShowMNodeRegressionSSCenterTotal.setText("Total");
    textFieldShowMNodeRegressionSSCenter2.setToolTipText("2");
    textFieldShowMNodeRegressionSSCenter2.setEditable(false);
    textFieldShowMNodeRegressionSSCenter2.setText("2");
    textFieldShowMNodeRegressionSSCenter2.setColumns(5);
    textFieldShowMNodeRegressionSSCenter2.setHorizontalAlignment(SwingConstants.RIGHT);
    textFieldShowMNodeRegressionSSCenterBXY.setToolTipText("b\'X\'Y");
    textFieldShowMNodeRegressionSSCenterBXY.setEditable(false);
    textFieldShowMNodeRegressionSSCenterBXY.setText("" + m_node.m_bxy);
    textFieldShowMNodeRegressionSSCenterBXY.setColumns(10);
    textFieldShowMNodeRegressionSSCenterBXY.setHorizontalAlignment(SwingConstants.RIGHT);
    textFieldShowMNodeRegressionSSCenterN2.setToolTipText("n - 2");
    textFieldShowMNodeRegressionSSCenterN2.setEditable(false);
    textFieldShowMNodeRegressionSSCenterN2.setText("" + (m_node.m_df - 2));
    textFieldShowMNodeRegressionSSCenterN2.setColumns(5);
    textFieldShowMNodeRegressionSSCenterN2.setHorizontalAlignment(SwingConstants.RIGHT);
    textFieldShowMNodeRegressionSSCenterYYBXY.setToolTipText("Y\'Y - b\'X\'Y");
    textFieldShowMNodeRegressionSSCenterYYBXY.setEditable(false);
    textFieldShowMNodeRegressionSSCenterYYBXY.setText("" + (m_node.m_yy - m_node.m_bxy));
    textFieldShowMNodeRegressionSSCenterYYBXY.setColumns(10);
    textFieldShowMNodeRegressionSSCenterYYBXY.setHorizontalAlignment(SwingConstants.RIGHT);
    textFieldShowMNodeRegressionSSCenterN.setToolTipText("n");
    textFieldShowMNodeRegressionSSCenterN.setEditable(false);
    textFieldShowMNodeRegressionSSCenterN.setText("" + m_node.m_df);
    textFieldShowMNodeRegressionSSCenterN.setColumns(5);
    textFieldShowMNodeRegressionSSCenterN.setHorizontalAlignment(SwingConstants.RIGHT);
    textFieldShowMNodeRegressionSSCenterYY.setToolTipText("Y\'Y");
    textFieldShowMNodeRegressionSSCenterYY.setEditable(false);
    textFieldShowMNodeRegressionSSCenterYY.setText("" + m_node.m_yy);
    textFieldShowMNodeRegressionSSCenterYY.setColumns(10);
    textFieldShowMNodeRegressionSSCenterYY.setHorizontalAlignment(SwingConstants.RIGHT);
    getContentPane().add(panelMain);

    panelMain.add(tabbedPaneShowMNodeRegression, BorderLayout.SOUTH);
    tabbedPaneShowMNodeRegression.add(panelShowMNodeRegressionBasic, "B (est\'d)");
    panelShowMNodeRegressionBasic.add(panelShowMNodeRegressionBasicSouth, BorderLayout.SOUTH);
    panelShowMNodeRegressionBasicSouth.add(buttonShowMNodeRegressionBasicOK, null);
    panelShowMNodeRegressionBasic.add(panelShowMNodeRegressionBasicCenter, BorderLayout.CENTER);
    panelShowMNodeRegressionBasicCenter.add(labelShowMNodeRegressionBasicCenterTable, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 10, 5, 10), 0, 0));
    panelShowMNodeRegressionBasicCenter.add(scrollPaneShowMNodeRegressionBasicCenterTable, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 10, 5, 10), 0, 0));
    tabbedPaneShowMNodeRegression.add(panelShowMNodeRegressionSS, "Analysis of Variance");
    panelShowMNodeRegressionSS.add(panelShowMNodeRegressionSSSouth, BorderLayout.SOUTH);
    panelShowMNodeRegressionSSSouth.add(buttonShowMNodeRegressionSSOK, null);
    panelShowMNodeRegressionSS.add(panelShowMNodeRegressionSSCenter, BorderLayout.CENTER);
    panelShowMNodeRegressionSSCenter.add(labelShowMNodeRegressionSSCenterSource, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));
    panelShowMNodeRegressionSSCenter.add(labelShowMNodeRegressionSSCenterDF, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));
    panelShowMNodeRegressionSSCenter.add(labelShowMNodeRegressionSSCenterSS, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));
    panelShowMNodeRegressionSSCenter.add(labelShowMNodeRegressionSSCenterRegression, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));
    panelShowMNodeRegressionSSCenter.add(labelShowMNodeRegressionSSCenterResidual, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));
    panelShowMNodeRegressionSSCenter.add(labelShowMNodeRegressionSSCenterTotal, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));
    panelShowMNodeRegressionSSCenter.add(textFieldShowMNodeRegressionSSCenter2, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));
    panelShowMNodeRegressionSSCenter.add(textFieldShowMNodeRegressionSSCenterBXY, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));
    panelShowMNodeRegressionSSCenter.add(textFieldShowMNodeRegressionSSCenterN2, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));
    panelShowMNodeRegressionSSCenter.add(textFieldShowMNodeRegressionSSCenterYYBXY, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));
    panelShowMNodeRegressionSSCenter.add(textFieldShowMNodeRegressionSSCenterN, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));
    panelShowMNodeRegressionSSCenter.add(textFieldShowMNodeRegressionSSCenterYY, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));
    scrollPaneShowMNodeRegressionBasicCenterTable.getViewport().add(panelShowMNodeRegressionBasicCenterTable, null);
	this.setResizable(false);
  }

  // Basic

  void buttonShowMNodeRegressionBasicOK_actionPerformed(ActionEvent e)
  {
    dispose();
  }

  void buttonShowMNodeRegressionSSOK_actionPerformed(ActionEvent e)
  {
    dispose();
  }
}

class XMShowRegressionTableModel extends AbstractTableModel
{
  XMMNodeRegression m_node;

  String[] m_columnNames = { "Variables", "B(estimated)" };
  Object[][] m_data = null;

  public XMShowRegressionTableModel(XMMNodeRegression node)
  {
    super();

    m_node = node;
    if (m_node != null && m_node.m_x != null && m_node.m_b != null)
    {
      int i;
      int n = m_node.m_b.length;
      m_data = new Object[n][m_columnNames.length];
      for (i = 0; i < n; i++)
      {
        if (i == 0 && m_node.m_bIntercept)
        {
          m_data[i][0] = "Intercept";
        }
        else
        {
          m_data[i][0] = m_node.m_x[i - (m_node.m_bIntercept ? 1 : 0)];
        }
        m_data[i][1] = new Double(m_node.m_b[i]);
      }
    }
  }

  public int getRowCount()
  {
    return(m_data == null ? 0 : m_data.length);
  }

  public int getColumnCount()
  {
    return(m_columnNames == null ? 0 : m_columnNames.length);
  }

  public String getColumnName(int col)
  {
    return(m_columnNames == null ? "" : m_columnNames[col]);
  }

  public Class getColumnClass(int col)
  {
    return(m_data[0][col].getClass());
  }

  public Object getValueAt(int row, int col)
  {
    return(m_data == null ? "" : m_data[row][col]);
  }

  public boolean isCellEditable(int row, int col)
  {
    return(false);
  }

  public void setValueAt(Object value, int row, int col)
  {
  }
}
