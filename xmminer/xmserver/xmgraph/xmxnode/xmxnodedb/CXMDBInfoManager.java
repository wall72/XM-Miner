package xmminer.xmserver.xmgraph.xmxnode.xmxnodedb;

import java.sql.*;
import java.util.Vector;
import java.util.Enumeration;

public class CXMDBInfoManager {

  CXMDBConn   _CDC ;
  Connection  _Con;

  Statement	_stmt= null ;
  Statement	_stmt2= null ;

  ResultSet	_rs = null ;
  ResultSet	_rs2 = null ;

  ResultSetMetaData       _rsmd = null ;

  String      _DBName;
  String[]    _Tables;

  static  Vector _VEC3;
  static  Vector _VEC2 = new Vector();
  static  Vector _VEC = new Vector();
  Enumeration _vEum;

  CXMConvertType _CCT = new CXMConvertType();

  public CXMDBInfoManager(CXMDBConn cdc) {
    _CDC = cdc;
    _Con = cdc._con;
    _DBName = cdc.getDBName();
  }

  private void createStmtQuery(String query) throws JavaException{
    try{
      if (_rs != null) _rs.close();
  	  if (_stmt != null) _stmt.close();
      _stmt  = _Con.createStatement();
	  _rs = _stmt.executeQuery(query);
   	  _rsmd = _rs.getMetaData();
    }catch(SQLException e4){
  	  System.out.println(e4.getMessage());
      throw new JavaException(e4.getMessage());
  	}
  }

  public String[] getFKTable(String tab_name) throws JavaException {
    String query = _CDC.getSQLManager().getFKTable(tab_name);
    try{
      this.createStmtQuery(query);
 	  int i = 0;
	  String tName = "";
	  String con = "";

      _VEC.removeAllElements();

 	  while(_rs.next()) {
        i++;
		con = _rs.getString(1);
		tName = _rs.getString(2);
		_VEC2 = getFKColumn(tName, con);
        _vEum = _VEC2.elements();
        String         val = "";
        while(_vEum.hasMoreElements()) {
          val = (String)_vEum.nextElement();
          val = tName+"."+val;
        }
        _VEC2.removeAllElements();
	    _VEC.addElement(val);
	  }

	  _rs.close();
	  _stmt.close();

	  if(i > 0){
        _Tables = _CCT.vec2Str(_VEC);
      }else{
        String[] er = new String[1];
        er[0] = "none";
        _Tables = er;
      }

	}catch(SQLException e){
       System.out.print("getFKTable : ");
       System.out.println(e.getMessage());
       throw new JavaException(e.getMessage());
    }
    return _Tables;
  }

  private Vector getFKColumn(String tab_name, String con_name) throws JavaException{
    Vector _v = new Vector();
    try{
      _VEC3 = _CDC.getSQLManager().getFKColumn(tab_name, con_name);
      _stmt2 = _CDC.getConn().createStatement();
      _vEum = _VEC3.elements();
      String query = "";

	  while(_vEum.hasMoreElements()) {
        query = (String)_vEum.nextElement();
        _rs2 = _stmt2.executeQuery(query);
        String val = " " ;

        int chkNum = 0;
        if(_rs2.next()) {
          val = _rs2.getString(1);
          if(_rs2.next()) {
  	        val = val+" -> "+_rs2.getString(1);
          }
  	    _v.addElement(val);
        }else{
	      _rs2.close();
	      _stmt2.close();
          break;
        }
        _rs2.close();
        _stmt2.close();
      }
      _VEC3.removeAllElements();
    }catch(SQLException e){
      System.out.print("getFKColumn : ");
      System.out.println(e.getMessage());
      throw new JavaException(e.getMessage());
    }catch(Exception ee){
      System.out.print("getFKColumn 2: ");
      System.out.println(ee.getMessage());
      throw new JavaException(ee.getMessage());
	}
    return _v;
  }

  private Vector getFKRKColumn(String tab_name, String con_name) throws JavaException{
    Vector _v = new Vector();
    try{
      _VEC3 = _CDC.getSQLManager().getFKColumn(tab_name, con_name);
      _stmt2 = _CDC.getConn().createStatement();
      _vEum = _VEC3.elements();
      String query = "";

	  while(_vEum.hasMoreElements()) {
        query = (String)_vEum.nextElement();
        _rs2 = _stmt2.executeQuery(query);
	    String val = " " ;

        int chkNum = 0;
	    if(_rs2.next()) {
          val = _rs2.getString(1);
	      if(_rs2.next()) {
    	    val = val+" -> "+_rs2.getString(1);
          }
    	  _v.addElement(val);
        }else{
          _rs2.close();
	      _stmt2.close();
          break;
        }
		_rs2.close();
		_stmt2.close();
	  }
	  _VEC3.removeAllElements();
   	}catch(SQLException e){
      System.out.print("getFKColumn : ");
      System.out.println(e.getMessage());
      throw new JavaException(e.getMessage());
    }catch(Exception ee){
   	  System.out.print("getFKColumn 2: ");
      System.out.println(ee.getMessage());
      throw new JavaException(ee.getMessage());
	}
    return _v;
  }

}