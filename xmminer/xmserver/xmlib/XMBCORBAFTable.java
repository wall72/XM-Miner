package xmminer.xmserver.xmlib;

import xmminer.xmlib.*;
import xmminer.xmlib.xmcompute.*;
import xmminer.xmlib.xmtable.*;
import xmminer.xmserver.*;
import xmminer.xmclient.xmgraph.*;

public class XMBCORBAFTable implements XMProgress
{

  String m_projectName, m_metaFilename;

  public XMBCORBAFTable () {
    super();
//	init();
  }

  public int Initialize (java.lang.String projectName, 
                         java.lang.String metaFilename) {
    // IMPLEMENT:
    m_projectName = projectName;
    m_metaFilename = metaFilename;

    return (int)0;
  }

  public void Finalize () {
    // IMPLEMENT:
    return;
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

  public int PerformRunning(String columnName)
  {
    setProgress(1, "Preparing ...");

    setProgress(10, "Calculating ...");
    CXMSetUniqueValueCount suv = new CXMSetUniqueValueCount();
    suv.setFileStatus(m_projectName, m_metaFilename, columnName);
    suv.createUniqueValueTable();
    suv.close();

    setProgress(100, "Finished!");
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

  public int Run (java.lang.String columnName) {
    // IMPLEMENT:
    int r = PerformRunning(columnName);
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

  public java.lang.String[][] GetUniqueValueCount (java.lang.String columnName) {
    // IMPLEMENT:
    CXMGetUniqueValueCount guv = new CXMGetUniqueValueCount();
    guv.setFileStatus(m_projectName, m_metaFilename, columnName);
    String[][] uv_array = guv.getUniqueValueCount();
    return uv_array;
  }

//  public static ServerMonitorPage monitor = null;
//  private void init() {
//    java.util.ResourceBundle res = java.util.ResourceBundle.getBundle("xmminer.xmserver.ServerResources");
//    String name = "XMBCORBAFTable";
//    if (monitor == null) {
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