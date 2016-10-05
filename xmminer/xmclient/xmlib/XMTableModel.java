
//Title:        XM-Miner
//Version:
//Copyright:    Copyright (c) 1999
//Author:       Yong Uk Song
//Company:      DIS
//Description:  XM-Miner

package xmminer.xmclient.xmlib;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

import xmminer.xmclient.xmlib.*;

public class XMTableModel extends AbstractTableModel
{
  XMCORBATable m_corbaTable;

  public XMTableModel(XMCORBATable corbaTable)
  {
    super();
    m_corbaTable = corbaTable;
  }

  public int getRowCount()
  {
    return(m_corbaTable.GetRowCount());
  }

  public int getColumnCount()
  {
    return(m_corbaTable.GetColumnCount());
  }

  public String getColumnName(int col)
  {
    return(m_corbaTable.GetColumnName(col));
  }

  public Class getColumnClass(int c)
  {
    return(m_corbaTable.GetColumnClass(c));
  }

  public Object getValueAt(int row, int col)
  {
    return(m_corbaTable.GetValueAt(row, col));
  }

  public boolean isCellEditable(int row, int column)
  {
    return(false);
  }

  public void setValueAt(Object value, int row, int col)
  {
  }

  public String[][] GetAllRows(){
    return m_corbaTable.GetAllRows();
  }

  public String[] GetAllCols(){
    return m_corbaTable.GetAllCols();
  }

  public void SaveAllRows(){
    m_corbaTable.SaveAllRows();
  }
}
