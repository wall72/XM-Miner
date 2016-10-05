package xmminer.xmserver.xmgraph.xmxnode.xmxnodedb;

import java.io.*;
import java.lang.*;
import java.util.Vector;

public class CXMMSSQL extends CXMSQLManager{

  public String getTableSQL(){
    String query = "select * from sysobjects where (type = 'V' and  status > 0) or (type = 'u' and  status >= 0) order by name";
    return query;
  }

  public String  getFKTable(String tab_name) {
    String query = "SELECT UPPER(OBJECT_NAME(constid)), OBJECT_NAME(fkeyid) FROM sysreferences WHERE OBJECT_NAME(rkeyid) = '"+tab_name+"'";
    return query;
  }

  //*--------------------------[getFKColumn]---------------------------------*//
  public Vector  getFKColumn(String tableName, String conName) {
    query = "SELECT B.name AS COL_NAME  FROM sysreferences A, syscolumns B  WHERE OBJECT_NAME(A.constid) = '"+conName+"' AND OBJECT_NAME(A.fkeyid) = '"+ tableName+"' AND ";
    String num = "";
    for(int i = 1; i <= 16; i++){
      Integer ii = new Integer(i);
      num = ii.toString();
      _V.addElement(query + " A.fkeyid = B.id AND A.fkey"+num+" = B.colid ");
    }
    return _V;
  }

  //******************************************************************
  public String  getName() {
    return "NAME";
  }            

  //*--------------------------[getCharSet]---------------------------------*//
  public String getCharSet(){
    query = "use master select name from syscharsets";
    return query;
  }

  public String getPKColumns(String tableName){
    return null;
  }

  public String getColumnName(String tableName){
    String query = "select top 1 * from "+tableName;
    return query;
  }

  //*--------------------------[getExeSql]---------------------------------*//
  public String getExeSql(String[] col_list, String[] tab_list, String sql, String fn,String ln ){
    return null;
  }

  public String getSqlQuery(String query){
    return null;
  }

  //*--------------------------[getRowCount]---------------------------------*//
  public String getRowCount(String query){
    query = "select top 40 * from "+query;
    return query;
  }

  //*--------------------------[getFKColumn]---------------------------------*//
  public Vector  getFKRKColumn(String tableName, String conName) {
    query = "SELECT B.name AS COL_NAME  FROM sysreferences A, syscolumns B  WHERE OBJECT_NAME(A.constid) = '"+conName+"' AND OBJECT_NAME(A.fkeyid) = '"+ tableName+"' AND ";
    String num = "";
    for(int i = 1; i <= 16; i++){
      Integer ii = new Integer(i);
      num = ii.toString();
      _V.addElement(query + " A.fkeyid = B.id AND A.fkey"+num+" = B.colid ");
      _V.addElement(query + " A.rkeyid = B.id AND A.rkey"+num+" = B.colid ");
    }
    return _V;
  }
}