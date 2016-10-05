package xmminer.xmserver.xmgraph.xmxnode.xmxnodedb;

import java.io.*;
import java.lang.*;
import java.util.Vector;

public class CXMORASQL extends CXMSQLManager{

  //*--------------------------[getTableSQL]---------------------------------*//
  //전체 테이블 리스트를 가지고 오는 SQL문 생성
  public String getTableSQL(){
    query = "select * from tab";
    return query;
  }

  //*--------------------------[getFKTable]---------------------------------*//
  //Forign Key로 연결되어 있는 테이블 리스트를 가지고 오는 SQL 문
  public String  getFKTable(String tab_name) {
    query = "SELECT B.TABLE_NAME||'.'||B.COLUMN_NAME||'='||A.TABLE_NAME||'.'||A.COLUMN_NAME "+
    "FROM USER_CONS_COLUMNS A, USER_CONS_COLUMNS B, USER_CONSTRAINTS C "+
    " WHERE A.TABLE_NAME = '" + tab_name + "' " +
   	" AND A.CONSTRAINT_NAME = C.CONSTRAINT_NAME "+
    " AND B.CONSTRAINT_NAME = C.R_CONSTRAINT_NAME "+
    " AND A.POSITION = B.POSITION ";
    return query;
  }

  //*--------------------------[getName]---------------------------------*//
  //Forign Key로 연결되어 있는 컬럼 리스트를 가지고 오는 SQL 문
  public Vector getFKColumn(String tableName, String conName) {
    query =  "SELECT COLUMN_NAME  FROM USER_CONS_COLUMNS  " +
    "WHERE TABLE_NAME = '" + tableName + "' " +
    "AND CONSTRAINT_NAME = '" + conName + "' ORDER BY POSITION" ;
    _V.addElement(query);
    return _V;
  }
  
  //*--------------------------[getName]---------------------------------*//
  public Vector  getFKRKColumn(String tableName, String conName) {
    query =  "SELECT COLUMN_NAME  FROM USER_CONS_COLUMNS  " +
    "WHERE TABLE_NAME = '" + tableName + "' " +
    "AND CONSTRAINT_NAME = '" + conName + "' ORDER BY POSITION" ;
    _V.addElement(query);
    return _V;
  }

  //*--------------------------[getName]---------------------------------*//
  public String  getName() {
    return "TNAME";
  }

  //*--------------------------[getCharSet]---------------------------------*//
  public String getCharSet(){
    query = "select value$ from sys.props$ where name = 'NLS_CHARACTERSET'";
    return query;
  }

  //*--------------------------[getPKColumns]---------------------------------*//
  public String getPKColumns(String tableName){
    query = "SELECT COLUMN_NAME FROM USER_CONS_COLUMNS A, USER_CONSTRAINTS B " +
    "WHERE A.TABLE_NAME = '"+tableName+"' AND B.CONSTRAINT_TYPE ='P' AND A.CONSTRAINT_NAME = B.CONSTRAINT_NAME";
    return query;
  }

  //*--------------------------[getExeSql]---------------------------------*//
  public String getExeSql(String[] col_list, String[] tab_list, String sql, String fn,String ln ){
    CXMConvertType ct = new CXMConvertType();
    String listcol = "";
    for(int i = 0; i < col_list.length ; i++){
      String col = col_list[i];
      col = "list."+ct.strCut(col) + ",";
      listcol = listcol + col;
    }
    listcol = listcol.substring(0,listcol.length() - 1);
    String query = "select " + listcol + " from( select list.num, "+ listcol +
                   " from( select rownum num, "+listcol +" from( "+ sql +") list ) list ) list"+
                   " where num > "+fn+" and num < "+ln ;
    return query;
  }

  //*--------------------------[getColumnName]---------------------------------*//
  public String getColumnName(String tableName){
    String query = "select * from "+tableName+ " where rownum = 1";
    return query;
  }

  //*--------------------------[getColumnName]---------------------------------*//
  public String getSqlQuery(String query){
    query = query + " where rownum < 101 ";
    return query;
  }

  //*--------------------------[getRowCount]---------------------------------*//
  public String getRowCount(String query){
    query = "select * from "+query+ " where rownum < 41";
    return query;
  }

}