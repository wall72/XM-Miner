
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

public class XMChartModel
{
  XMChartable m_chartDataModel1, m_chartDataModel2;
  String m_annotationsXAxis1, m_annotationsYAxis1, m_annotationsXAxis2, m_annotationsYAxis2;
  boolean m_bGridXAxis1, m_bGridYAxis1, m_bGridXAxis2, m_bGridYAxis2;
  String m_titleXAxis1, m_titleYAxis1, m_titleXAxis2, m_titleYAxis2;
  String m_headerText, m_footerText;

  public XMChartModel(XMChartable chartDataModel1, XMChartable chartDataModel2)
  {
    m_chartDataModel1 = chartDataModel1;
    m_chartDataModel2 = chartDataModel2;
    m_annotationsXAxis1 = "Value";
    m_annotationsYAxis1 = "Value";
    m_annotationsXAxis2 = "Value";
    m_annotationsYAxis2 = "Value";
    m_bGridXAxis1 = false;
    m_bGridYAxis1 = false;
    m_bGridXAxis2 = false;
    m_bGridYAxis2 = false;
    m_titleXAxis1 = "X";
    m_titleYAxis1 = "Y";
    m_titleXAxis2 = null;
    m_titleYAxis2 = null;
    m_headerText = null;
    m_footerText = null;
  }

  public XMChartable GetChartDataModel(int n)
  {
    return(n == 0 ? m_chartDataModel1 : m_chartDataModel2);
  }

  public String GetDataChart()
  {
    return("(data1 " + ((m_chartDataModel1 == null) ? "PLOT" : m_chartDataModel1.GetChartType()) + ")"
           + "(data2 " + ((m_chartDataModel2 == null) ? "PLOT" : m_chartDataModel2.GetChartType()) + ")");
  }

  // Value, Point_Labels
  public void SetAxisAnnotations(String annotationsXAxis1, String annotationsYAxis1, String annotationsXAxis2, String annotationsYAxis2)
  {
    m_annotationsXAxis1 = annotationsXAxis1;
    m_annotationsYAxis1 = annotationsYAxis1;
    m_annotationsXAxis2 = annotationsXAxis2;
    m_annotationsYAxis2 = annotationsYAxis2;
  }

  public String GetAxisAnnotations()
  {
    return("(x1 " + m_annotationsXAxis1 + ")(y1 " + m_annotationsYAxis1 + ")"
           + "(x2 " + m_annotationsXAxis2 + ")(y2 " + m_annotationsYAxis2 + ")");
  }

  public void SetBAxisGrid(boolean bGridXAxis1, boolean bGridYAxis1, boolean bGridXAxis2, boolean bGridYAxis2)
  {
    m_bGridXAxis1 = bGridXAxis1;
    m_bGridYAxis1 = bGridYAxis1;
    m_bGridXAxis2 = bGridXAxis2;
    m_bGridYAxis2 = bGridYAxis2;
  }

  public String GetBAxisGrid()
  {
    return("(x1 " + m_bGridXAxis1 + ")(y1 " + m_bGridYAxis1 + ")"
           + "(x2 " + m_bGridXAxis2 + ")(y2 " + m_bGridYAxis2 + ")");
  }

  public void SetAxisTitle(String titleXAxis1, String titleYAxis1, String titleXAxis2, String titleYAxis2)
  {
    m_titleXAxis1 = titleXAxis1;
    m_titleYAxis1 = titleYAxis1;
    m_titleXAxis2 = titleXAxis2;
    m_titleYAxis2 = titleYAxis2;
  }

  public String GetAxisTitle()
  {
    return("(x1 " + m_titleXAxis1 + ")(y1 " + m_titleYAxis1 + ")"
           + "(x2 " + m_titleXAxis2 + ")(y2 " + m_titleYAxis2 + ")");
  }

  public void SetHeaderText(String headerText)
  {
    m_headerText = headerText;
  }

  public String GetHeaderText()
  {
    return(m_headerText);
  }

  public void SetFooterText(String footerText)
  {
    m_footerText = footerText;
  }

  public String GetFooterText()
  {
    return(m_footerText);
  }
}
