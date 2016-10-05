package xmminer.xmserver.xmgraph.xmxnode.xmxnodedb;

import java.io.*;
import java.lang.*;
import java.awt.*;
import java.sql.*;

import xmminer.xmserver.*;
import xmminer.xmserver.xmgraph.xmxnode.xmxnodedb.*;
import xmminer.xmlib.xmtable.*;
import xmminer.xmserver.xmgraph.xmxnode.xmxnodedb.*;
import xmminer.xmserver.xmgraph.xmxnode.xmxnodedb.XNodeDBException;

public class XMBXNodeDB{

  //start member variables declaration
  String[] _Tables;
  String _charSet = "NOKSC5601";
  String[][] obj;

  Statement _stmt = null ;

  CXMConvertType _CCT = new CXMConvertType();
  CXMDBConn _CDC ;
  CXMDataDBModule _CDDM;

  String _DBName;
  boolean lb_init = false;
  Connection _Con;

  String sql ;
  String colsNum;
  //end of member variables declaration

  //Constructor
  public XMBXNodeDB() {
    super();
// 	init();
   _CDC = new CXMDBConn();
  }

  public void classInit(){
    try{
      _DBName = _CDC.getDBName();
      _Con = _CDC._con;
      _CDDM = new CXMDataDBModule(_CDC, _DBName);
      this.getCharSet();
      lb_init = true;
    }catch(JavaException e){
      System.out.println("classInit"+e.getMessage());
    }
  }

  private void getCharSet() throws JavaException {
    try{ this._charSet = _CDDM.getCharSet(); }
    catch(JavaException e){ throw new JavaException(e.getMessage()); }
  }

  public String[] getFKTable(String tabName){
    try{
      if(_CDC.getDriverName().compareTo("oracle.jdbc.driver.OracleDriver") == 0) return _CDDM.ora_getFKTable(tabName);
      else return _CDDM.getFKTable(tabName);
    }catch(JavaException je){
      return null;
    }
  }

//*--------------------------[getColumnNames]---------------------------------*//
//선택되어진 테이블의 컬럼을 리턴한다.
//리턴값으로 성공하면 true, 실패하면 false를 리턴 한다.
  public String[] getColumnAll(String tabName){
    try{ return _CDDM.getColumnAll(tabName); }
    catch(JavaException je){ return null; }
  }

  public String[] getColumnList(String tabName){
    try{ return _CDDM.getColumnList(tabName); }
    catch(JavaException je){ return null; }
  }

  public String[][] ms_sqlGenSection(String sql, int row){
    try{ return _CDDM.ms_sqlGenSection(sql,row); }
    catch(JavaException je){ return null; }
  }

  public String[][] sqlGenView(String rowNum, String tabName, int rowSet){
    try{
      if(_CDC.getDriverName().compareTo("oracle.jdbc.driver.OracleDriver") == 0){
        int row = _CDDM.getRowCount("select * from "+tabName);
		System.out.println("XMBXNodeDB.sqlGenView: select * from "+tabName);
        return _CDDM.ora_sqlGenSection(tabName, rowSet, row);
      }else{
        return _CDDM.ms_sqlGenSection("select top "+rowNum+" * from "+tabName, rowSet);
      }
    }catch(JavaException je){
      String[][] ttt = new String[1][1];
      ttt[0][0] = "none";
      return ttt;
    }
  }

//SQL문 생성기를 통해서 생성된 SQL을 실행시 그 값을 부분적으로 넘겨준다.(현재 20개씩 보여준다.)
//colList는 컬럼 리스트를   tabList는 테이블 리스트를
//sql는 실제 sql문을   rowSet은 가지고 오는 로우의 시작점을 의미한다.
  public String[][] sqlGenPreview(String[] colList, String[] tabList, String sql, int rowSet) throws XNodeDBException {
    try{
      if(_CDC.getDriverName().compareTo("oracle.jdbc.driver.OracleDriver") == 0){
        _CDDM.ora_sqlVerifyCheck(sql);
        int rowCnt = _CDDM.getRowCount(sql);
        return _CDDM.ora_sqlGenSection(colList, tabList, sql, rowSet, rowCnt);
      }else{
        _CDDM.ms_sqlVerifyCheck(sql);
        return _CDDM.ms_sqlGenSection(sql,rowSet);
      }
    }catch(JavaException je){
      System.out.println("OK :"+je.message);
      throw new XNodeDBException(je.getMessage());
    }
  }

  public String[][] sqlQuery(String sql){
    try{
      String[][] str2Arr;
      if(_CDC.getDriverName().compareTo("oracle.jdbc.driver.OracleDriver") == 0){
        sql = "select * from "+sql +" where rownum < 21";
        int rcnt = this.getRowCount(sql);
        if (rcnt > 20) rcnt = 20;
        str2Arr = _CDDM.ora_sqlQuery(sql, rcnt);
      }else{
        int rcnt = this.getRowCount("select * from "+sql);
        if (rcnt > 20) rcnt = 20;
        sql = "select top 20 * from "+sql ;
        str2Arr = _CDDM.ms_sqlQuery(_DBName+sql, rcnt);
      }
      return str2Arr;
    }catch(JavaException je){
       return null;
    }
  }

  public boolean dbDisConnect(){
    try{ return _CDC.dbDisConnect(); }
    catch(JavaException je){ return false; }
  }

  public String[] getTableNames(){
    try{
      classInit();
      return _CDDM.getTableNames();
    }catch(JavaException je){
      return null;
    }
  }

  public String[] ColumnOnlyOnesqlQuery(String query){ // throws JavaException {
    try{
      String[] str2Arr;
      if(_CDC.getDriverName().compareTo("oracle.jdbc.driver.OracleDriver") == 0){
        int rcnt = this.getRowCount(sql);
        str2Arr = _CDDM.ora_ColumnOnlyOnesqlQuery(sql, rcnt);
      }else{
        int rcnt = this.getRowCount(sql);
        str2Arr = _CDDM.ms_ColumnOnlyOnesqlQuery(_DBName,sql, rcnt);
      }
      return str2Arr;
    }catch(JavaException je){
      return null;
    }
  }

  public String getProfile(String key, String projectname){
    CXMMetaDataReader mdr = new CXMMetaDataReader();
    mdr.setFileStatus(projectname, "xnodedb");
    String value = mdr.getProfile(key);
    mdr.close();
    return value;
  }

  public String[] getProfiles(String key, String projectname){
    CXMMetaDataReader mdr = new CXMMetaDataReader();
    mdr.setFileStatus(projectname, "xnodedb");
    String[] values = mdr.getProfiles(key);
    mdr.close();
    return values;
  }

  public void setProfile(String key, String value, String projectname){
    CXMMetaDataSaver mds = new CXMMetaDataSaver();
    mds.setFileStatus(projectname, "xnodedb");
    mds.setProfile(key, value);
    mds.close();
  }

  public void setProfiles(String key, String[] values, String projectname){
    CXMMetaDataSaver mds = new CXMMetaDataSaver();
    mds.setFileStatus(projectname, "xnodedb");
    mds.setProfiles(key, values);
    mds.close();
  }

  public  boolean dbConnect(String driver, String url, String loginID, String password) throws XNodeDBException {
    try{
      return _CDC.dbConnect(driver, url, loginID, password);
    }catch(JavaException e){
      throw new XNodeDBException(e.getMessage());
    }
  }

  public int getRowCount(String a) throws JavaException {
    this.classInit();
    return _CDDM.getRowCount(a);
  }

  int m_pbMin ;
  int m_pbMax ;
  int m_pbValue;
  int max ;
  int min = 1;

  public int GetPBMin () {    // must be called by client and only once!
    // IMPLEMENT:
    SetPBMin();
    return m_pbMin;
  }

  public void SetPBMin () {
    m_pbMin = min;
  }

  public int GetPBMax (int index, String projectname) {    // must be called by client and only once!
    // IMPLEMENT:
    SetPBMax(index, projectname);
    return m_pbMax;
  }

  public void SetPBMax (int index, String projectname) {
    CXMMetaDataReader cdr = new CXMMetaDataReader();
    cdr.setFileStatus(projectname, "xnodedb");
  	sql = cdr.getProfile("sql");
   	colsNum = cdr.getProfile("colnum");
   	cdr.close();
   	try{
      m_pbMax  = getRowCount(sql);
   	} catch (Exception e) {
      e.printStackTrace();
   	}
  }

  public int GetPBValue () {
    // IMPLEMENT:
    return m_pbValue;
  }

  public void SetPBValue (int x) {
    // IMPLEMENT:
    m_pbValue = x;
  }

  boolean m_bStop;
  boolean m_bAlive;

  public int PerformRunning(int index, String projectpath, String projectname){
   	m_pbMin = 1;
    System.out.println("#[TRACE MSG: XMXNodeDB] PerformRunning ::: "+colsNum);
    if(_CDC.getDriverName().compareTo("oracle.jdbc.driver.OracleDriver") == 0){
      try{
        _CDDM.ora_sqlExeStart(sql, _CCT.str2Int(colsNum), projectpath, projectname);
        _CDDM.ora_sqlExeRun(sql, this);
        _CDDM.ora_sqlExeEnd(_CCT.str2Int(colsNum), projectpath, projectname,index);
       	return(0);
      }catch(JavaException je){
        System.out.println("JavaException : "+je.getMessage());
        return -1;
      }
    }else{
      try{
        _CDDM.ms_sqlExeStart(sql, _CCT.str2Int(colsNum), projectpath, projectname);
        int y=0;
        for(int x = m_pbMin; KeepRunning() && x <  m_pbMax; x++,y++){
          m_pbValue = x;
          _CDDM.ms_sqlExeRun(y);
        }
        _CDDM.ms_sqlExeEnd(_CCT.str2Int(colsNum), projectpath, projectname, index);
       	return(0);
      }catch(JavaException je){
        System.out.println("JavaException : "+je.getMessage());
        return -1;
      }
    }
  }


  public int Run (int index, String projectpath, String projectname) {
    // IMPLEMENT:
    m_bStop = false;
    m_bAlive = true;
    int r = PerformRunning(index, projectpath, projectname);
    //Stop 처리 필요...e.g. meta-data 처리...
    m_bAlive = false;
    m_bStop = true;
    return(r);
  }

  public boolean KeepRunning () {
    return(!m_bStop);
  }

  public void StopRunning () {
    // IMPLEMENT:
    m_bStop = true;
  }

  public boolean IsRunning () {
    // IMPLEMENT:
    return(m_bAlive);
  }

  public boolean MetaDataCreate(String[] colList, String[] typeList, int index, String projectname){
    try{
      CXMMetaDataReader cdr = new CXMMetaDataReader();
      cdr.setFileStatus(projectname, "xnodedb");
      String sql = cdr.getProfile("sql");
      cdr.close();
      CXMMetaDataSaver cmds = new CXMMetaDataSaver();
      cmds.deleteAllProfile();
      cmds.setFileStatus(projectname, "arc"+index);
      cmds.setProfile("PREVIOUS_NODE", "XMXNodeDB");
      cmds.setProfile("DATA_FILE", "arc"+index);
      cmds.setProfile("NUMBER_OF_COLUMNS", _CCT.int2Str(colList.length));
      cmds.setProfiles("COLUMN_LIST", colList);
      cmds.setProfile("ROW_INDEX", "arc"+index);
      String[] a = new String[colList.length];
      for(int i = 0; i < colList.length;i++){
        a[i] = _CCT.int2Str(i+1);
        cmds.setProfile(colList[i],typeList[i]);
      }
      cmds.setProfiles("COLUMN_INDEX", a);
      int numRows  = getRowCount(sql);
      cmds.setProfile("NUMBER_OF_ROWS",_CCT.int2Str(numRows));
      cmds.close();
      CXMSetMetaDataColumnProperty c = new  CXMSetMetaDataColumnProperty();
      c.setColumnProperty(projectname, "arc"+index);
    }catch(Exception eee){
      System.out.println(eee.getMessage());
    }
    return true;
  }

//  public static ServerMonitorPage monitor = null;
//  private void init() {
//    java.util.ResourceBundle res = java.util.ResourceBundle.getBundle("xmminer.xmserver.ServerResources");
//    String name = "XMBXNodeDB";
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