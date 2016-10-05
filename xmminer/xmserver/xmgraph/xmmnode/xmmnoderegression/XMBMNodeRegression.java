package xmminer.xmserver.xmgraph.xmmnode.xmmnoderegression;

//import xmminer.xmvbj.xmgraph.xmmnode.xmmnoderegression.XMBMNodeRegressionPOA;

import xmminer.xmlib.*;
import xmminer.xmlib.xmmath.*;
import xmminer.xmlib.xmstat.*;
import xmminer.xmlib.xmtable.*;
import xmminer.xmserver.*;
import xmminer.xmserver.xmlib.*;
import xmminer.xmclient.xmgraph.XMGraphElement;

public class XMBMNodeRegression implements XMProgress{
  String m_projectName, m_metaFilename;

  CXMMetaDataReader m_metaDataReader;
  String m_listColumn[];
  int m_nColumn, m_nRow;

  CXMTableQuerier m_tableQuerier;

  public XMBMNodeRegression(){
    super();
//	init();
  }

  public int Initialize (java.lang.String projectName,
                         java.lang.String metaFilename)
  {
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

  public void Finalize ()
  {
    // IMPLEMENT:
    m_tableQuerier.close();
    m_metaDataReader.close();
    return;
  }

  public int SetSchema (java.lang.String nextMetaFilename, 
                        java.lang.String y, 
                        java.lang.String[] x, 
                        boolean intercept)
  {
    // IMPLEMENT:
    int i;
    CXMMetaDataSaver metaDataSaver = new CXMMetaDataSaver();
    int newNColumn = x.length + 2;
    metaDataSaver.setFileStatus(m_projectName, nextMetaFilename);
    metaDataSaver.deleteAllProfile();
    metaDataSaver.setProfile("PREVIOUS_NODE", "XMMNodeRegression");
    metaDataSaver.setProfile("DATA_FILE", nextMetaFilename);
    metaDataSaver.setProfile("NUMBER_OF_COLUMNS", "" + newNColumn);
    String newListColumn[] = new String[newNColumn];
    newListColumn[0] = y;
    for (i = 0; i < x.length; i++)
    {
      newListColumn[i + 1] = x[i];
    }
    newListColumn[x.length + 1] = y + "_estimated";
    metaDataSaver.setProfiles("COLUMN_LIST", newListColumn);
    metaDataSaver.setProfile("ROW_INDEX", nextMetaFilename);
    String columnIndex[] = new String[newListColumn.length];
    for (i = 0; i < newListColumn.length; i++)
    {
      columnIndex[i] = "" + (i + 1);
      metaDataSaver.setProfile(newListColumn[i], "REAL");
    }
    metaDataSaver.setProfiles("COLUMN_INDEX", columnIndex);
    metaDataSaver.setProfile("NUMBER_OF_ROWS", "" + m_nRow);
    metaDataSaver.close();
    CXMSetMetaDataColumnProperty setMetaDataColumnProperty = new CXMSetMetaDataColumnProperty();
    setMetaDataColumnProperty.setColumnProperty(m_projectName, nextMetaFilename);

    return (int)0;
  }

  boolean m_bAlive;
  boolean m_bStop;
  int m_pbMin;
  int m_pbMax;
  int m_pbValue;
  String m_pbDescription = "";

  public boolean setProgress(int pbValue, String pbDescription)
  {
    m_pbValue = pbValue;
    m_pbDescription = pbDescription;
    return(KeepRunning());
  }

  double m_b[];
  double m_ss[];

  public int PerformRunning(String nextMetaFilename, String y, String[] x, boolean intercept)
  {
    setProgress(1, "Preparing ...");
    int i;
    String listColumn[] = new String[x.length + 1];
    listColumn[0] = y;
    for (i = 0; i < x.length; i++)
    {
      listColumn[i + 1] = x[i];
    }
    m_tableQuerier.setFileStatus(m_projectName, m_metaFilename, null, listColumn);
    XMTableMatrixQuerier yQuerier = new XMTableMatrixQuerier(m_tableQuerier, m_nRow, 128, 0, 1, false);
    XMTableMatrixQuerier xQuerier = new XMTableMatrixQuerier(m_tableQuerier, m_nRow, 128, 1, x.length, intercept);
    XMMatrixR yMatrix = new XMMatrixR(yQuerier);
    XMMatrixR xMatrix = new XMMatrixR(xQuerier);
    XMRegression regression = new XMRegression(xMatrix, yMatrix);
    boolean bSuccess = regression.run(false, this);

    // Save to next VNode
    if (!setProgress(90, "Estimating ..."))
    {
      return(XMGraphElement.XMGESTAT_RUN_NORUN);
    }
    if (!bSuccess)
    {
      return(XMGraphElement.XMGESTAT_RUN_ERROR);
    }
    XMMatrix bMatrix = regression.getB();
    m_b = bMatrix.toVector();

    XMMatrix yyMatrix = regression.getYY();
    XMMatrix bxyMatrix = regression.getBXY();
    m_ss = new double[3];
    m_ss[0] = yyMatrix.get(0, 0);
    m_ss[1] = bxyMatrix.get(0, 0);
    m_ss[2] = (double)regression.getDF();

    // Meta File
    SetSchema(nextMetaFilename, y, x, intercept);

    //  Data File
    CXMTableSaver tableSaver = new CXMTableSaver();
    tableSaver.setFileStatus(m_projectName, nextMetaFilename);
    double y_estd;
    int k;
    String elementData, rowData;
    int columnSize[] = new int[x.length + 2];
    for (k = 0; k < m_nRow; k++)
    {
      elementData = "" + yMatrix.get(k, 0);
      rowData = "" + elementData;
      columnSize[0] = elementData.length();
      for (i = 0; i < x.length; i++)
      {
        elementData = "" + xMatrix.get(k, i + (intercept ? 1 : 0));
        rowData += elementData;
        columnSize[i + 1] = elementData.length();
      }
      y_estd = xMatrix.multiplyAt(bMatrix, k, 0);
      elementData = "" + y_estd;
      rowData += elementData;
      columnSize[x.length + 1] = elementData.length();

      tableSaver.createRowData(rowData.getBytes(), columnSize);
    }
    tableSaver.close();

    setProgress(100, "Finished!");
    return(XMGraphElement.XMGESTAT_RUN_SUCCESS);
  }

  public int InitializeRunning ()
  {
    // IMPLEMENT:
    m_bAlive = true;
    m_bStop = false;
    m_pbMin = m_pbValue = 1;
    m_pbMax = 100;
    return(0);
  }

  public int Run (java.lang.String nextMetaFilename,
		  java.lang.String y,
                  java.lang.String[] x,
                  boolean intercept)
  {
    // IMPLEMENT:
    int r = PerformRunning(nextMetaFilename, y, x, intercept);
    m_bStop = true;
    m_bAlive = false;
    return(r);
  }

  public boolean IsRunning ()
  {
    // IMPLEMENT:
    return(m_bAlive);
  }

  public boolean KeepRunning ()
  {
    return(!m_bStop);
  }

  public void StopRunning ()
  {
    // IMPLEMENT:
    m_bStop = true;
  }

  public int GetPBMin ()
  {
    // IMPLEMENT:
    return m_pbMin;
  }

  public int GetPBMax ()
  {
    // IMPLEMENT:
    return m_pbMax;
  }

  public int GetPBValue ()
  {
    // IMPLEMENT:
    return m_pbValue;
  }

  public java.lang.String GetPBDescription ()
  {
    // IMPLEMENT:
    return m_pbDescription;
  }

  public void FinalizeRunning ()
  {
    // IMPLEMENT:
    return;
  }

  public double[] GetResultB () {
    // IMPLEMENT:
    return m_b;
  }

  public double[] GetResultSS () {
    // IMPLEMENT:
    return m_ss;
  }

//  public static ServerMonitorPage monitor = null;
//  private void init() {
//    java.util.ResourceBundle res = java.util.ResourceBundle.getBundle("xmminer.xmserver.ServerResources");
//    String name = "XMBMNodeRegression";
//    if (monitor == null) {
//    monitor = ServerMonitor.addPage(this, name);
//    monitor.showObjectCounter(true);
//	  monitor.showReadCounter(true);
//	  monitor.showWriteCounter(true);
//    ServerMonitor.register(name);
//    }
//    monitor.updateObjectCounter(1);
//    ServerMonitor.log(ServerResources.format(res.getString("created"), name));
//  }
}