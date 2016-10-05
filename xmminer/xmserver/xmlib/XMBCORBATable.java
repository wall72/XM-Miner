package xmminer.xmserver.xmlib;

import java.io.*;

import xmminer.xmlib.xmtable.*;
import xmminer.xmserver.*;

public class XMBCORBATable {

  String m_projectPath, m_projectName, m_metaFilename;
  int m_id;

  CXMMetaDataReader m_metaDataReader;
  int m_nRow;

  CXMTableQuerier m_tableQuerier;

  public XMBCORBATable () {
    super();
//	init();
  }

  public int Initialize (java.lang.String projectPath, java.lang.String projectName, 
                         java.lang.String metaFilename, int id) {
    // IMPLEMENT:
	m_projectPath = projectPath;
    m_projectName = projectName;
    m_metaFilename = metaFilename;
	m_id = id;

    m_metaDataReader = new CXMMetaDataReader();
    m_metaDataReader.setFileStatus(m_projectName, m_metaFilename);

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

  public java.lang.String GetProfile (java.lang.String name) {
    // IMPLEMENT:
    return(m_metaDataReader.getProfile(name));
  }

  public java.lang.String[] GetProfiles (java.lang.String name) {
    // IMPLEMENT:
    return(m_metaDataReader.getProfiles(name));
  }

  public java.lang.String[] GetColumnNames () {
    // IMPLEMENT:
    return(m_metaDataReader.getProfiles("COLUMN_LIST"));
  }

  public int GetNumberOfRows () {
    // IMPLEMENT:
    return(m_nRow);
  }

  public int SetQueryColumns (java.lang.String[] columnNames) {
    // IMPLEMENT:
    m_tableQuerier.setFileStatus(m_projectName, m_metaFilename, null, columnNames);
    return (int)0;
  }

  public java.lang.String[][] GetRows (int nStart, 
                                       int nRows) {
    // IMPLEMENT:
    int i;
    if (nStart >= m_nRow)
    {
      nRows = 0;
    }
    else if (nStart + nRows > m_nRow)
    {
      nRows = m_nRow - nStart;
    }

    if (nRows <= 0)
    {
      return(null);
    }
    else
    {
      String rows[][] = new String[nRows][];
      for (i = 0; i < nRows; i++)
      {
        rows[i] = m_tableQuerier.getStringArrayInColArray(nStart + i + 1);
      }
      return(rows);
    }
  }

  public java.lang.String[][] GetAllRows(){ //대용량 처리 기능 추가!!!
    String rows[][] = new String[m_nRow][];
	CXMTableQuerier t_tableQuerier = new CXMTableQuerier();
    t_tableQuerier.setFileStatus(m_projectName, m_metaFilename, null, m_metaDataReader.getProfiles("COLUMN_LIST"));
    for (int i = 0; i < m_nRow; i++){
      rows[i] = t_tableQuerier.getStringArrayInColArray(i + 1);
    }
    return(rows);
  }

  public void SaveAllRows(){
	String formatted = null;
    String[] m_AllCols;
    String m_AllRows[][] = new String[m_nRow][];
    String c = "\n";
    byte[] enter = c.getBytes();

	try{
	  File dir = new File(m_projectPath+m_projectName+"/out");
	  if(!dir.exists()) dir.mkdir();
      FileOutputStream FOS = new FileOutputStream(m_projectPath+m_projectName+"/out/server_table"+m_id+".csv");

	  m_AllCols = GetColumnNames();
	  for(int k=0; k<m_AllCols.length; k++){
        if(k==0){
	      formatted = m_AllCols[k];
	      continue;
	    }
	    formatted = formatted + "," + m_AllCols[k];
	  }
      FOS.write(formatted.getBytes());
      FOS.write(enter);

      CXMTableQuerier t_tableQuerier = new CXMTableQuerier();
      t_tableQuerier.setFileStatus(m_projectName, m_metaFilename, null, m_metaDataReader.getProfiles("COLUMN_LIST"));
      for(int i = 0; i < m_nRow; i++){
        m_AllRows[i] = t_tableQuerier.getStringArrayInColArray(i + 1);
      }

      for(int i=0; i<m_AllRows.length; i++){
        for(int j=0; j<m_AllRows[i].length; j++){
          if(j==0){
	        formatted = m_AllRows[i][j];
            continue;
          }
          formatted = formatted + "," + m_AllRows[i][j];
        }
        FOS.write(formatted.getBytes());
        FOS.write(enter);
    }
      FOS.close();
	}catch(IOException ie){ ie.printStackTrace(); }
  }

//  public static ServerMonitorPage monitor = null;
//  private void init() {
//    java.util.ResourceBundle res = java.util.ResourceBundle.getBundle("xmminer.xmserver.ServerResources");
//    String name = "XMBCORBATable";
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