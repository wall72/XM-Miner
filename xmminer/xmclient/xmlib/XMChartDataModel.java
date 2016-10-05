
//Title:        XM-Miner
//Version:
//Copyright:    Copyright (c) 1999
//Author:       Yong Uk Song
//Company:      DIS
//Description:  XM-Miner

package xmminer.xmclient.xmlib;

import jclass.chart.*;
import java.util.*;

import xmminer.xmclient.xmlib.*;

public class XMChartDataModel implements XMChartable
{
  transient String m_name;
  transient XMCORBATable m_corbaTable;
  transient int[] m_seriesMap;
  transient String m_chartType;

  transient int m_nSeries;
  transient boolean m_bXSeries;
  transient String[] m_seriesNames;

  public XMChartDataModel(String name, XMCORBATable corbaTable, int[] seriesMap, String chartType)
  {
    m_name = name;
    m_corbaTable = corbaTable;
    m_seriesMap = seriesMap;
    m_chartType = chartType;

    m_nSeries = 0;
    m_bXSeries = false;
    int i;
    for (i = 0; i < m_seriesMap.length; i++)
    {
      if (m_seriesMap[i] >= 0)
      {
        m_nSeries++;
        if (m_seriesMap[i] == 0)
        {
          m_bXSeries = true;
        }
      }
    }
    if (m_bXSeries == false)
    {
      m_nSeries++;
    }
    m_seriesNames = new String[m_nSeries];
    for (i = 0; i < m_seriesMap.length; i++)
    {
      if (m_seriesMap[i] >= 0)
      {
        m_seriesNames[m_seriesMap[i]] = m_corbaTable.GetColumnName(i);
      }
    }
  }

  public String GetChartType()
  {
    return(m_chartType);
  }

  private int GetSeries(int row)
  {
    int i;
    for (i = 0; i < m_seriesMap.length; i++)
    {
      if (m_seriesMap[i] == row)
      {
        return(i);
      }
    }
    return(-1);
  }

  public Object getDataItem(int row, int column)        // row - 0 : X-Series, > 1 : Y-Series
  {
    int series;
    if ((series = GetSeries(row)) < 0)
    {
      return((row == 0) ? (new Integer(column)) : null);
    }
    else
    {
      return(m_corbaTable.GetValueAt(column, series));
    }
  }

  public Vector getRow(int row)                         // row - 0 : X-Series, > 1 : Y-Series
  {
    Vector val;
    int series = GetSeries(row);
    if (series < 0)
    {
      if (row == 0)
      {
        int i;
        val = new Vector();
        for (i = 0; i < m_corbaTable.GetRowCount(); i++)
        {
          val.addElement(new Integer(i));
        }
        return(val);
      }
      else
      {
        return(null);
      }
    }
    else
    {
      int i;
      val = new Vector();
      for (i = 0; i < m_corbaTable.GetRowCount(); i++)
      {
        val.addElement(m_corbaTable.GetValueAt(i, series));
      }
      return(val);
    }
  }

  public int getDataInterpretation()
  {
    return(ARRAY);
  }

  public int getNumRows()
  {
    return(m_nSeries);
  }

  public String[] getPointLabels()
  {
    return(null);
  }

  public String getSeriesName(int row)                  // row - > 0 : Y-Series
  {
    return(m_seriesNames[row + 1]);
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
