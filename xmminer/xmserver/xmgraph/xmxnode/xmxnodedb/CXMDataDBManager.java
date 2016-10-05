package xmminer.xmserver.xmgraph.xmxnode.xmxnodedb;

import java.io.*;
import java.lang.*;
import java.awt.*;
import java.sql.*;
import java.util.Vector;
import java.util.Enumeration;
import xmminer.xmserver.xmgraph.xmxnode.xmxnodedb.*;

public class CXMDataDBManager {

  static  Vector _VEC = new Vector();
  static  Vector _VEC2 = new Vector();
  Enumeration _vEum;

  String[] _Tables;
  String _charSet = "NOKSC5601";
  String[][] obj;

  Statement	_stmt = null ;

  CXMConvertType _CCT = new CXMConvertType();
  CXMDBConn _CDC ;
  CXMDataDBModule _CDDM;

  String _DBName;

  Connection _Con;

  public void classInit(CXMDBConn cdc) throws JavaException{
    try{
      _CDC = cdc;
      _DBName = _CDC.getDBName();
      _Con = cdc._con;
      _CDDM = new CXMDataDBModule(_CDC, _DBName);
      this.getCharSet();
    }catch(JavaException e){
      System.out.println("classInit"+e.getMessage());
    }
  }

  //*--------------------------[getCharSet]---------------------------------*//
  private void getCharSet() throws JavaException{
    try{
      this._charSet = _CDDM.getCharSet();
    }catch(JavaException e){
      throw new JavaException(e.getMessage());
    }
  }

  //*--------------------------[getTableNames]---------------------------------*//
  //선택되어진 디비의 테이블 리스트를 배열로 리턴한다.
  //리턴값으로 성공하면 true, 실패하면 false를 리턴 한다.
  public String[] getTableNames() throws JavaException {
	return _CDDM.getTableNames();
  }

  //*--------------------------[getColumnNames]---------------------------------*//
  //선택되어진 테이블의 컬럼을 리턴한다.
  //리턴값으로 성공하면 true, 실패하면 false를 리턴 한다.
  public String[] getColumnAll(String tableNames) throws JavaException{
    return _CDDM.getColumnAll(tableNames);
  }

  //*--------------------------[getColumnNames]---------------------------------*//
  //선택되어진 테이블의 컬럼을 리턴한다.
  //리턴값으로 성공하면 true, 실패하면 false를 리턴 한다.
  public String[] getColumnNames(String tableName) throws JavaException{
    return _CDDM.getColumnNames(tableName);
  }

  public int getRowCount(String a) throws JavaException{
    return _CDDM.getRowCount(a);
  }

  //*--------------------------[getPrimaryKeyColumn]---------------------------------*//
  //선택되어진 테이블의조瓚見疸?키를 스트링 어레이로 리턴한다.
  public String[] getPKColumn(String tableName) throws JavaException{
    if(_CDC.getDriverName().compareTo("oracle.jdbc.driver.OracleDriver") == 0){
      return _CDDM.ora_getPKColumn(tableName);
    }else return _CDDM.ms_getPKColumn(tableName);
  }

  public String[][] sqlGenPreview(String[] c, String[] d, String str) throws JavaException{
    return _CDDM.sqlGenPreview(c, d, str);
  }

  //*--------------------------[getFKTable]---------------------------------*//
  //선택된 테이블의 외부키가 걸려 있는 것을 가지고 온다.
  //리턴값으로 성공하면 String[] 를 넘기고 실패하면 SQLException 처리한다.
  public String[]  getFKTable(String tableName)  throws JavaException{
    return _CDDM.getFKTable(tableName);
  }

  public String[][] sqlQuery(String sql) throws JavaException{
    String[][] str2Arr;
    if(_CDC.getDriverName().compareTo("oracle.jdbc.driver.OracleDriver") == 0){
      int rcnt = this.getRowCount(sql);
      str2Arr = _CDDM.ora_sqlQuery(sql, rcnt);
    }else{
      int rcnt = this.getRowCount(sql);
      str2Arr = _CDDM.ms_sqlQuery(_DBName+sql, rcnt);
    }
    return str2Arr;
  }

  public String[][] sqlQuery(String sql, int rcnt) throws JavaException{
    String[][] str2Arr;
    if(_CDC.getDriverName().compareTo("oracle.jdbc.driver.OracleDriver") == 0){
      rcnt = this.getRowCount(sql);
      str2Arr = _CDDM.ora_sqlQuery(sql, rcnt);
    }else{
      str2Arr = _CDDM.ms_sqlQuery(_DBName+sql, rcnt);
    }
    return str2Arr;
  }

  public String[] ColumnOnlyOnesqlQuery(String sql) throws JavaException{
    String[] str2Arr;
    if(_CDC.getDriverName().compareTo("oracle.jdbc.driver.OracleDriver") == 0){
      int rcnt = this.getRowCount(sql);
      str2Arr = _CDDM.ora_ColumnOnlyOnesqlQuery(sql, rcnt);
    }else{
      int rcnt = this.getRowCount(sql);
      str2Arr = _CDDM.ms_ColumnOnlyOnesqlQuery(_DBName,sql, rcnt);
    }
    return str2Arr;
  }

  public String[][] getTableDataView(String tableName,String[] test1) throws JavaException{
    String sql = _CDDM.getTableDataViewSQL(tableName);
    int k = test1.length;

    int rowCnt = getRowCount(sql);
    if(rowCnt > 41) rowCnt = 40;
    String[][] str2Arr2 = new String[k][rowCnt];
    String[] str = new String[1];
    str[0] = tableName;
    str2Arr2 = sqlQuery(sql, rowCnt);
    return str2Arr2;
  }

  public String[] getColumnList(String tableName) throws JavaException{
    return _CDDM.getColumnList(tableName);
  }

  public String[][]  ms_sqlGenSection(String sql, int row) throws JavaException{
    return _CDDM.ms_sqlGenSection(sql, row);
  }

}