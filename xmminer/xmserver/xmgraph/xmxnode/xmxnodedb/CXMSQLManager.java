package xmminer.xmserver.xmgraph.xmxnode.xmxnodedb;

import java.io.*;
import java.lang.*;
import java.util.Vector;

public class CXMSQLManager {
  Vector _V = new  Vector();
  String query = "";

  public String getTableSQL(){return null;}
  public String getFKTable(String tab_name) {return null;}    
  public String getName() {return null;} 
  public Vector getFKColumn(String tableName, String conName) {return _V ;}    
  public Vector getFKRKColumn(String tableName, String conName) {return _V ;}
  public String getCharSet(){return null;}
  public String getPKColumns(String tableName){return null;}
  public String getExeSql(String[] col_list, String[] tab_list, String sql, String fn,String ln ){return null;}
  public String getColumnName(String tableName){return null;}
  public String getSqlQuery(String query){return null;}
  public String getRowCount(String query){return null;}
}