
//Title:        XM-Miner
//Version:
//Copyright:    Copyright (c) 1999
//Author:       Yong Uk Song
//Company:      DIS
//Description:  XM-Miner

package xmminer.xmclient.xmlib;

import jclass.chart.*;
import java.awt.*;
import java.util.*;

import xmminer.xmclient.xmlib.*;

public class XMFChartDataModel implements XMChartable
{
  transient String m_name;
  transient XMCORBAFTable m_corbaFTable;
  transient String m_columnName;
  transient String m_chartType;

  transient Object m_data[][];
  transient String m_pointLabels[];

  public XMFChartDataModel(String name, XMCORBAFTable corbaFTable, String columnName, String chartType)
  {
    m_name = name;
    m_corbaFTable = corbaFTable;
    m_chartType = chartType;

    if (corbaFTable != null)
    {
      String data[][] = corbaFTable.GetUniqueValueCount(columnName);
      int i;
      m_data = new Object[data.length][2];
      m_pointLabels = new String[data.length];
      for (i = 0; i < data.length; i++)
      {
        m_pointLabels[i] = data[i][0];
        m_data[i][0] = new Integer(i);
        m_data[i][1] = new Integer(Integer.parseInt(data[i][1]));
      }
    }
  }

  public String GetChartType()
  {
    return(m_chartType);
  }

  public Object getDataItem(int row, int column)        // row - 0 : X-Series, > 1 : Y-Series
  {
    return((m_data == null) ? (new Integer(column)) : m_data[column][row]);
  }

  public Vector getRow(int row)                         // row - 0 : X-Series, > 1 : Y-Series
  {
    Vector val = new Vector();
    if (m_data != null)
    {
      for (int i = 0; i < m_data.length; i++)
      {
        val.addElement(getDataItem(row, i));
      }
    }
    return(val);
  }

  public int getDataInterpretation()
  {
    return(ARRAY);
  }

  public int getNumRows()
  {
    return(m_data == null ? 0 : 2);
  }

  public String[] getPointLabels()
  {
    return(m_pointLabels);
  }

  public String getSeriesName(int row)                  // row - > 0 : Y-Series
  {
    return("Frequency");
  }

  public String getSeriesLabel(int row)
  {
    return(getSeriesName(row));
  }

  public String getName()
  {
    return(m_name);
  }
}
