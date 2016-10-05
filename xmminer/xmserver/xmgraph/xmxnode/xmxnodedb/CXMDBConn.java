package xmminer.xmserver.xmgraph.xmxnode.xmxnodedb;

import java.sql.*;

public class CXMDBConn {

  CXMSQLManager _SQLM ;

  Connection	_con = null ;
  String _Driver;
  String _DBName;

  boolean bb = false;

  public CXMDBConn() {}

  //*--------------------------[dbConnect]---------------------------------
  //DB에 접속하는 Method
  //리턴값으로 성공하면 true, 실패하면 false를 리턴 한다.
  public boolean dbConnect(String driver, String url, String loginID, String password) throws JavaException{
    try{
      _Driver = driver;
      Class.forName (driver);
      java.util.Properties props = new java.util.Properties();
	  props.put("user", loginID);      // 필요하면 설정한다.
	  props.put("password", password);  // 필요하면 설정한다.
	  System.out.println(_Driver + ", " + url + ", " + loginID + ", " + password);
	  _con = DriverManager.getConnection(url, props);
      _DBName = "use "+_con.getCatalog();
    }catch(SQLException e){
		e.printStackTrace();
      throw new JavaException(e.getMessage());
    }catch(ClassNotFoundException e){
		e.printStackTrace();
      throw new JavaException(e.getMessage());
    }
    DBMSDriverName(driver);
    bb = true;
	return true;
  }

  //*--------------------------[DBMSDriverName]---------------------------------*//
  //접속한 DBMS에 따라 SQL 클래스를 생성시킨다.
  void DBMSDriverName(String driver){
    if(driver.compareTo("oracle.jdbc.driver.OracleDriver") == 0){
      _SQLM = new CXMORASQL();
      _DBName = "";
    }else{
      _SQLM = new CXMMSSQL();
    }
  }

  //*--------------------------[dbDisConnect]---------------------------------*//
  //DB와의 접속을 끊는 Method
  //리턴값으로 성공하면 true, 실패하면 false를 리턴 한다.
  public boolean dbDisConnect() throws JavaException{
    try {
	  if (_con != null) _con.close();
      bb = false;
	}catch(SQLException  e4){
      throw new JavaException(e4.getMessage());
	}
	return true;
  }

  public Connection getConn(){
    return _con;
  }

  public boolean ConCheck(){
    return bb;
  }

  public String getDBName(){
    return _DBName+" ";
  }

  public String getDriverName(){
    return _Driver;
  }

  public CXMSQLManager getSQLManager(){
    return _SQLM;
  }

}