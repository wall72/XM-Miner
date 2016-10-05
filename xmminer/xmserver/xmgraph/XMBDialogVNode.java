package xmminer.xmserver.xmgraph;

import xmminer.xmlib.*;
import xmminer.xmlib.xmtable.*;
import xmminer.xmserver.*;
import xmminer.xmclient.xmgraph.XMGraphElement;

public class XMBDialogVNode {

  String m_projectName, m_metaFilename;

  CXMMetaDataReader m_metaDataReader;
  String m_listColumn[];
  int m_nColumn, m_nRow;

  CXMTableQuerier m_tableQuerier;

  String m_result[][];

  public XMBDialogVNode () {
    super();
//    init();
  }

  public int Initialize (java.lang.String projectName,
                         java.lang.String metaFilename) {
    // IMPLEMENT:
    m_projectName = projectName;
    m_metaFilename = metaFilename;

    m_metaDataReader = new CXMMetaDataReader();
    m_metaDataReader.setFileStatus(m_projectName, m_metaFilename);

    m_listColumn = m_metaDataReader.getProfiles("COLUMN_LIST");
    m_nColumn = (m_listColumn == null) ? 0 : m_listColumn.length;
    m_nRow = Integer.parseInt(m_metaDataReader.getProfile("NUMBER_OF_ROWS"));

    m_tableQuerier = new CXMTableQuerier();

    return (int)0;
  }

  public void Finalize () {
    // IMPLEMENT:
    m_tableQuerier.close();
    m_metaDataReader.close();
    return;
  }

  // Table

  // Statistics
  boolean m_bAlive;
  boolean m_bStop;
  int m_pbMin;
  int m_pbMax;
  int m_pbValue;

  public int PerformRunning()
  {
    int i, j, n, statColumn[];
    String columnInfo[], tempListColumn[], listColumn[];

    statColumn = new int[m_nColumn];
    tempListColumn = new String[m_nColumn];
    for (i = n = 0; i < m_nColumn; i++)
    {
      columnInfo = m_metaDataReader.getProfiles(m_listColumn[i]);
      switch (XMLib.GetColumnType(columnInfo))
      {
        case XMLib.XMDBTYPE_INTEGER:
        case XMLib.XMDBTYPE_REAL:
          tempListColumn[n++] = m_listColumn[i];
          statColumn[i] = 1;
          break;
        case XMLib.XMDBTYPE_STRING:
        case XMLib.XMDBTYPE_ENUMERATION:
        case XMLib.XMDBTYPE_DATE:
        case XMLib.XMDBTYPE_UNKNOWN:
          statColumn[i] = 0;
          break;
      }
    }

    if (n <= 0)
    {
      return(XMGraphElement.XMGESTAT_RUN_NORUN);
    }

    listColumn = XMLib.SqueezeArray(tempListColumn, n);

    String row[];
    double d;
    double s[] = new double[n];
    double ss[] = new double[n];
    double min[] = new double[n];
    double max[] = new double[n];
    m_tableQuerier.setFileStatus(m_projectName, m_metaFilename, null, listColumn);
    for (i = 0; KeepRunning() && i < m_nRow; i++)
    {
      row = m_tableQuerier.getStringArrayInColArray(i + 1);
      for (j = 0; j < n; j++)
      {
        d = Double.parseDouble(row[j]);
        if (i == 0)
        {
          s[j] = d;
          ss[j] = d * d;
	  min[j] = max[j] = d;
        }
        else
        {
          s[j] += d;
          ss[j] += d * d;
	  if (d < min[j]) min[j] = d;
	  if (d > max[j]) max[j] = d;
        }
      }

      m_pbValue = i * m_pbMax / m_nRow;
      /*
      try
      {
        Thread.sleep(1);
      }
      catch (InterruptedException e)
      {
      }
      */
    }

    m_result = new String[m_nColumn][5];
    double average, variance;
    for (i = j = 0; i < m_nColumn; i++)
    {
      if (statColumn[i] > 0)
      {
        average = s[j] / m_nRow;
	variance = ss[j] / m_nRow - average * average;
        if (max[j] > 1.0)
        {
          average = (int)(average * 100.0) / 100.0;
          variance = (int)(variance * 100.0) / 100.0;
        }
        else
        {
          average = (int)(average * 10000.0) / 10000.0;
          variance = (int)(variance * 10000.0) / 10000.0;
        }
        m_result[i][0] = m_listColumn[i];
        m_result[i][1] = String.valueOf(average);
        m_result[i][2] = String.valueOf(variance);
        m_result[i][3] = String.valueOf(min[j]);
        m_result[i][4] = String.valueOf(max[j]);
        j++;
      }
      else
      {
        m_result[i][0] = m_listColumn[i];
        m_result[i][1] = "-";
        m_result[i][2] = "-";
        m_result[i][3] = "-";
        m_result[i][4] = "-";
      }
    }

    return(XMGraphElement.XMGESTAT_RUN_SUCCESS);
  }

  public int InitializeRunning () {
    // IMPLEMENT:
    m_bAlive = true;
    m_bStop = false;
    m_pbMin = m_pbValue = 1;
    m_pbMax = 100;
    return(0);
  }

  public int Run () {
    // IMPLEMENT:
    int r = PerformRunning();
    m_bStop = true;
    m_bAlive = false;
    return(r);
  }

  public boolean IsRunning () {
    // IMPLEMENT:
    return(m_bAlive);
  }

  public boolean KeepRunning () {
    return(!m_bStop);
  }

  public void StopRunning () {
    // IMPLEMENT:
    m_bStop = true;
  }

  public int GetPBMin () {
    // IMPLEMENT:
    return m_pbMin;
  }

  public int GetPBMax () { 
    // IMPLEMENT:
    return m_pbMax;
  }

  public int GetPBValue () {
    // IMPLEMENT:
    return m_pbValue;
  }

  public void FinalizeRunning () {
    // IMPLEMENT:
    return;
  }

  public java.lang.String[][] GetResult () {
    // IMPLEMENT:
    return m_result;
  }

  // Charts

//  public static ServerMonitorPage monitor = null;
//  private void init(){
//    java.util.ResourceBundle res = java.util.ResourceBundle.getBundle("xmminer.xmserver.ServerResources");
//    String name = "XMBDialogVNode";
//    if(monitor==null){
////    monitor = ServerMonitor.addPage(this, name);
////    monitor.showObjectCounter(true);
////	  monitor.showReadCounter(true);
////	  monitor.showWriteCounter(true);
//      ServerMonitor.register(name);
//    }
////    monitor.updateObjectCounter(1);
//    ServerMonitor.log(ServerResources.format(res.getString("created"), name));
//  }
}