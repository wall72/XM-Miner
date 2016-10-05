package xmminer.xmserver.xmgraph.xmxnode.xmxnodedb;

import java.io.*;
import java.lang.*;
import java.awt.*;
import java.sql.*;
import java.util.Vector;
import java.util.Enumeration;
import java.text.*;
import xmminer.xmserver.xmgraph.xmxnode.xmxnodedb.*;
import xmminer.xmlib.xmtable.*;

public class CXMDataDBModule {

  CXMDBConn _CDC ;
  Connection _Con;
  Statement	_stmt= null ;
  Statement	_stmt2= null ;
  ResultSet	_rs = null ;
  ResultSet	_rs2 = null ;
  ResultSetMetaData _rsmd = null ;

  String[] _Tables;
  String _Val;
  String _charSet;
  String[][] obj;
  String _DBName;

  static  Vector _VEC3;
  static  Vector _VEC2 = new Vector();
  Enumeration _vEum;

  static  Vector _VEC = new Vector();
  CXMConvertType _CCT = new CXMConvertType();

  FileOutputStream FOS ;

  public CXMDataDBModule(CXMDBConn cdc, String DBName) {
    _CDC = cdc;
    _Con = cdc._con;
    _DBName = DBName;
  }

  private void createStmtQuery(String query) throws JavaException{
    try{
      if(_rs != null) _rs.close();
  	  if(_stmt != null) _stmt.close();
        _stmt  = _Con.createStatement();
     	_rs = _stmt.executeQuery(query);
        _rsmd = _rs.getMetaData();
    }catch(SQLException  e4){
      throw new JavaException(e4.getMessage());
  	}
  }

  public void ms_sqlVerifyCheck (String sql) throws JavaException{
    try{
      String text =  sql.trim();
      text = "SELECT TOP 1 "+sql.substring(6,sql.length());
      createStmtQuery(text);
    }catch(JavaException je){
      throw new JavaException(je.getMessage());
    }
  }

  public void ora_sqlVerifyCheck (String sql) throws JavaException{
    try{
      int idx = sql.indexOf("where");
      if(idx > 0){
        String select = sql.substring(0,idx);
        String where = "where rownum = 1 and "+sql.substring(idx + 6,sql.length());
        createStmtQuery(select + where);
      }else{
		createStmtQuery(sql + "where rownum = 1 ");
	  }
    }catch(JavaException je){
      throw new JavaException(je.getMessage());
    }
  }

  public int getRowCount(String a) throws JavaException{
    int rowCnt = 0;
    a = a.toUpperCase();
    int ind = a.indexOf("FROM");
    a = a.substring(ind+4, a.length());

    a = _DBName + "select count(*) from "+a;
    try{
      this.createStmtQuery(a);
      _rs.next();
      rowCnt = _rs.getInt(1);
      _rs.close();
      _stmt.close();
    }catch(SQLException e){
      System.out.print("sqlGen : ");
	  System.err.println(e.getMessage());
      throw new JavaException(e.getMessage());
	}catch(Exception e){
      System.out.print("sqlGen 2345: ");
	  System.err.println(e.getMessage());
      throw new JavaException(e.getMessage());
    }
    return rowCnt;
  }

  public String[] getTableNames() throws JavaException{
    try {
	  String query = _CDC.getSQLManager().getTableSQL();
	  String name = _CDC.getSQLManager().getName();

      createStmtQuery(_DBName+query);
      _VEC.removeAllElements();

	  while(_rs.next()) {
	    _Val = _rs.getString(name);
		_Val = _Val.trim();
		_VEC.addElement(new String(_Val));
	  }

	  _rs.close();
	  _stmt.close();
	  _Tables = _CCT.vec2Str(_VEC);
      _VEC.removeAllElements();
	}catch(SQLException  e5){
	  System.out.print("getTableNames : ");
	  System.out.println(e5.getMessage());
      throw new JavaException(e5.getMessage());
	}
	return _Tables;
  }

  //*--------------------------[getCharSet]---------------------------------*//
  public String getCharSet() throws JavaException{
	try{
	  String charSetQuery = _CDC.getSQLManager().getCharSet();

   	  if(charSetQuery != null) {
        this.createStmtQuery(charSetQuery);
		_rs.next();
	 	_Val = _rs.getString(1);
//	  	if(_Val.compareTo("KO16KSC5601") == 0)  _charSet = "KSC5601";
	  	if(_Val.compareTo("KO16MSWIN949") == 0)  _charSet = "KSC5601"; // 2002.10.11 한글버그수정
		else _charSet = "NOKSC5601";
		_rs.close();
   	  }else{
		_charSet = "NOKSC5601";
	  }

	  _stmt.close();

	}catch(SQLException e5){
      throw new JavaException(e5.getMessage());
	}catch (Exception e){
      throw new JavaException(e.getMessage());
	}
    return _charSet;
  }

  //*--------------------------[getColumnNames]---------------------------------*//
  //선택되어진 테이블의 컬럼을 리턴한다.
  //리턴값으로 성공하면 true, 실패하면 false를 리턴 한다.
  public String[] getColumnAll(String tableNames) throws JavaException{
	int         columnNum;
	_VEC.removeAllElements();
	try{
	  String query = _CDC.getSQLManager().getColumnName(tableNames);
      createStmtQuery(_DBName+query);
      columnNum = _rsmd.getColumnCount();
	  String val = "";
	  String test = "";
	  int leng = 0;
      String isNull = "";
	  for(int i = 1; i <= columnNum; i++){
		val = _rsmd.getColumnLabel(i);
		val= val+","+_rsmd.getColumnTypeName(i);
		leng = _rsmd.getColumnDisplaySize(i);
        test = _CCT.int2Str(leng);

        int nullable = _rsmd.isNullable(i);
        if(nullable == 0) isNull = "NOT NULL";
        else if(nullable == 1) isNull = "NULL";
        else isNull = "unKnown";

		val = val+","+test+","+isNull;
		_VEC.addElement(new String(val));
	  }

	  _rs.close();
	  _Tables = _CCT.vec2Str(_VEC);

	}catch(SQLException ee5){
	  System.out.print("getColumnNames : ");
	  System.out.println(ee5.getMessage());
      throw new JavaException(ee5.getMessage());
	}
	return _Tables;
  }

  //*--------------------------[getColumnNames]---------------------------------*//
  //선택되어진 테이블의 컬럼을 리턴한다.
  //리턴값으로 성공하면 true, 실패하면 false를 리턴 한다.
  public String[] getColumnNames(String tableNames) throws JavaException{
	int         columnNum;
	_VEC.removeAllElements();
	try{
	  String query = _CDC.getSQLManager().getColumnName(tableNames);

      createStmtQuery(_DBName+query);
      columnNum = _rsmd.getColumnCount();
	  String val = "";
	  int ll = 0;
	  for (int i = 0; i < columnNum; i++){
		val = _rsmd.getColumnLabel(i+1);
		_VEC.addElement(new String(val));
	  }
	  _rs.close();
	  _stmt.close();
	  _Tables = _CCT.vec2Str(_VEC);

	}catch(SQLException ee5){
      throw new JavaException(ee5.getMessage());
	}
	return _Tables;
  }

  //*--------------------------[getPrimaryKeyColumn]---------------------------------*//
  //선택되어진 테이블의조瓚見疸?키를 스트링 어레이로 리턴한다.
  public String[] ms_getPKColumn(String tableName) throws JavaException{
    try{
      DatabaseMetaData dbmd = _Con.getMetaData();
      _rs = dbmd.getPrimaryKeys(null,null,tableName);

      while (_rs.next()) {
	    String name = _rs.getString("column_name");
	 	_VEC.addElement(new String(name));
	  }

	  _rs.close();
   	  _Tables = _CCT.vec2Str(_VEC);

    }catch(SQLException ex) {
	   System.err.println("ms_getPKColumn");
	   System.err.println(ex.getMessage());
       throw new JavaException(ex.getMessage());
    }catch(Exception  e5){
       System.err.println("ms_getPKColumn");
	   System.out.println(e5.getMessage());
       throw new JavaException(e5.getMessage());
	}

	return _Tables;
  }

  public String[] ora_getPKColumn(String tableNames) throws JavaException{
    String  query = _CDC.getSQLManager().getPKColumns(tableNames);
    try{
      _Tables = null;
      createStmtQuery(query);
 	  String val;

	  while(_rs.next()){
	    val = _rs.getString(1);
    	_VEC.addElement(new String(val));
	  }

	  _rs.close();
      _Tables = _CCT.vec2Str(_VEC);

    }catch(SQLException ee5){
	  System.out.println(ee5.getMessage());
      throw new JavaException(ee5.getMessage());
    }catch(Exception e5){
	  System.out.println(e5.getMessage());
      throw new JavaException(e5.getMessage());
    }
	return _Tables;
  }

  private String sqlMaker(String[] col, String tab){
    String query = "SELECT ";
    for(int n = 0; n < col.length; n++){
      query = query + col[n]+" ,";
    }
    query = query.substring(0,query.length() - 1);
    return query.toUpperCase() + " FROM "+tab.toUpperCase();
  }

  public String[][] ora_sqlGenSection(String tabName, int rowSet ,int rowCnt) throws JavaException{
    if(rowCnt < 1){
      obj = new String[1][1];
      obj[0][0] = "NONE";
      return obj;
    }

    String[] tableName = {tabName};
    String sql = sqlMaker(getColumnNames(tabName),tabName)   ;
    int rowUntil = rowSet + 20;

	try{
      if (rowCnt < rowUntil) rowUntil = rowCnt + 1;
      if(rowSet >= rowUntil) {
      obj = new String[1][1];
      obj[0][0] = "NONE";
      return obj;
    }

    String query = _CDC.getSQLManager().getExeSql(getColumnNames(tabName),tableName,sql,_CCT.int2Str(rowSet - 1), _CCT.int2Str(rowUntil));
    this.createStmtQuery(query);
	int k = _rsmd.getColumnCount();

    obj = null;
    obj = new String[rowUntil - rowSet][k];
    String t ="";
    int tt = 0;

    String columnValue = "";

    while(_rs.next()){
      if(tt == rowUntil - rowSet) break;
      for (int i = 0; i < k; i++) {
        if(_rs.getString(i+1) != null) {
          columnValue = _rs.getString(i+1);
          columnValue = _CCT.ascii2ksc(columnValue, _charSet);
        }else columnValue = "값이 없어요!!!";
        obj[tt][i] = columnValue;
      }
      tt++;
    }

    if(tt == 0) {
      obj = new String[1][1];
      obj[0][0] = "NONE";
      return obj;
    }

    _rs.close();
    _stmt.close();
	
	}catch(SQLException ex){
      System.out.print("sqlGen : ");
	  System.err.println(ex.getMessage());
      throw new JavaException(ex.getMessage());
	}catch(Exception e){
      System.out.print("sqlGen 2ora: ");
	  System.err.println(e.getMessage());
      throw new JavaException(e.getMessage());
    }
    return obj;
  }

  public String[][] ora_sqlGenSection(String[] colList, String[] tabList, String sql, int rowSet ,int rowCnt) throws JavaException{
    if(rowCnt < 1){
      obj = new String[1][1];
      obj[0][0] = "NONE";
      return obj;
    }

    int rowUntil = rowSet + 20;
 
	try{
      if(rowCnt < rowUntil) rowUntil = rowCnt + 1;
      if(rowSet >= rowUntil){
        obj = new String[1][1];
        obj[0][0] = "NONE";
        return obj;
      }

      String query = _CDC.getSQLManager().getExeSql(colList,tabList,sql,_CCT.int2Str(rowSet - 1), _CCT.int2Str(rowUntil));
      this.createStmtQuery(query);
      int k = _rsmd.getColumnCount();

      obj = null;
      obj = new String[rowUntil - rowSet][k];
      String t ="";
      int tt = 0;

      String columnValue = "";

      while(_rs.next()) {
        if(tt == rowUntil - rowSet) break;
        for(int i = 0; i < k; i++){
          if(_rs.getString(i+1) != null) {
            columnValue = _rs.getString(i+1);
            columnValue = _CCT.ascii2ksc(columnValue, _charSet);
          }else columnValue = "값이 없어요!!!";
            obj[tt][i] = columnValue;
        }
        tt++;
      }

      if(tt == 0) {
        obj = new String[1][1];
        obj[0][0] = "NONE";
        return obj;
      }

      _rs.close();
      _stmt.close();

    }catch(SQLException ex){
      System.out.print("sqlGen : ");
	  System.err.println(ex.getMessage());
      throw new JavaException(ex.getMessage());
	}catch(Exception e){
      System.out.print("sqlGen 2ora: ");
	  System.err.println(e.getMessage());
      throw new JavaException(e.getMessage());
    }
    return obj;
  }

  public String[][] ora_sqlQuery(String sql, int rowCnt) throws JavaException{
    if(rowCnt < 1){
      obj = new String[1][1];
      obj[0][0] = "none";
      return obj;
    }

    try{
      this.createStmtQuery(sql);
	  int k = _rsmd.getColumnCount();

      if(rowCnt > 20) rowCnt = 20;
      obj = null;
      obj = new String[rowCnt][k];
      String t ="";
      int tt = 0;

      String columnValue = "";

      while(_rs.next()){
        if(tt == rowCnt) break;
        for(int i = 0; i < k; i++){
          if(_rs.getString(i+1) != null){
            columnValue = _rs.getString(i+1);
            columnValue = _CCT.ascii2ksc(columnValue, _charSet);
          }else columnValue = "값이 없어요!!!";
          obj[tt][i] = columnValue;
        }
        tt++;
      }

      _rs.close();
      _stmt.close();

    }catch(SQLException ex){
	   System.out.print("sqlGen : ");
	   System.err.println(ex.getMessage());
       throw new JavaException(ex.getMessage());
	}catch(Exception e){
      System.out.print("sqlGen 2ora: ");
	  System.err.println(e.getMessage());
      throw new JavaException(e.getMessage());
    }
    return obj;
  }

  public String[] ora_ColumnOnlyOnesqlQuery(String sql, int rowCnt)  throws JavaException{
    try{
      this.createStmtQuery(sql);

      _Tables = null;
      _Tables = new String[rowCnt];
      String t ="";
      int tt = 0;

      String columnValue = "";

      while(_rs.next()){
        if(_rs.getString(1) != null){
          columnValue = _rs.getString(1);
          columnValue = _CCT.ascii2ksc(columnValue, _charSet);
        }else columnValue = " ";
          _Tables[tt] = columnValue;
          tt++;
      }

      _rs.close();
      _stmt.close();

    }catch(SQLException ex){
      System.out.print("ora_ColumnOnlyOnesqlQuery : ");
      System.err.println(ex.getMessage());
      throw new JavaException(ex.getMessage());
    }catch(Exception e){
      System.out.print("sqlGen 2ora: ");
      System.err.println(e.getMessage());
      throw new JavaException(e.getMessage());
    }
    return _Tables;
  }

  public String[][] ms_sqlQuery(String sql, int rowCnt) throws JavaException{
    if(rowCnt == 0) {
      obj = new String[1][1];
      obj[0][0] = "none";
      return obj;
    }

	try{
      this.createStmtQuery(sql);
      int k = _rsmd.getColumnCount();
      if(rowCnt > 20) rowCnt = 20;
      obj = null;
      obj = new String[rowCnt][k];
      int tt = 0;
      String columnValue = "";

      while(_rs.next()){
        if(tt == rowCnt) break;
        for (int i = 0; i < k; i++){
          if(_rs.getString(i+1) != null){
            columnValue = _CCT.ascii2ksc(_rs.getString(i+1), _charSet);
          }else columnValue = "값이 없어요!!!";
            obj[tt][i] = columnValue;
        }
        tt++;
      }

      _rs.close();
      _stmt.close();

    }catch(SQLException ex) {
      System.out.println("SQLQUERY"+ex.getMessage());
      try{
        _rs.close();
      }catch(SQLException  ee){
        System.out.print("getFKTable : ");
        System.out.println(ee.getMessage());
      }
      throw new JavaException(ex.getMessage());
    }catch(Exception e){
	  System.out.print("sqlGen 2_query: ");
      System.err.println(e.getMessage());
      throw new JavaException(e.getMessage());
    }
    return obj;
  }

  public String[] ms_ColumnOnlyOnesqlQuery(String DBName,String sql, int rowCnt) throws JavaException{
    try{
      rowCnt = this.getRowCount(DBName+"select count(*) from ("+sql+") a");
      this.createStmtQuery(sql);
      _Tables = null;
      _Tables = new String[rowCnt];
      int tt = 0;
      String columnValue = "";

      while(_rs.next()){
        if(_rs.getString(1) != null){
          columnValue = _CCT.ascii2ksc(_rs.getString(1), _charSet);
        }else columnValue = "값이 없어요!!!";
        _Tables[tt] = columnValue;
        tt++;
      }

      _rs.close();
      _stmt.close();

    }catch(SQLException ex){
      System.out.println("SQLQUERY"+ex.getMessage());
      try{
        _rs.close();
      }catch(SQLException ee){
	    System.out.print("getFKTable : ");
        System.out.println(ee.getMessage());
        throw new JavaException(ee.getMessage());
      }
      throw new JavaException(ex.getMessage());
	}catch(Exception e){
      System.out.print("sqlGen 2_onesql: ");
	  System.err.println(e.getMessage());
      throw new JavaException(e.getMessage());
    }
    return _Tables;
  }

  public boolean  ora_sqlExeEnd(int colsNum, String projectpath, String projectname, int index) throws JavaException{
    try{
      FOS.close();

      CXMDBExtractRunner cder = new CXMDBExtractRunner();
      String devider = ";";

      cder.extract("temp.txt","arc"+String.valueOf(index),colsNum,devider.getBytes(), System.getProperty("minerdir") + "/xmminer/user/" + projectname + "/");
    }catch(Exception je){
      System.out.println("ora_sqlExeEnd : ");
      System.out.println(je.getMessage());
    }

    return true;
  }

  public boolean ora_sqlExeRun(String sql, XMBXNodeDB xx) throws JavaException{
    xx.SetPBValue(0);
    try{
      if(_rs != null) _rs.close();
	  if(_stmt != null) _stmt.close();
      _stmt  = _Con.createStatement();
      _rs = _stmt.executeQuery(sql);
	  System.out.println("CXMDataDBModule.ora_sqlExeRun: " + sql);
      _rsmd = _rs.getMetaData();
      int k = _rsmd.getColumnCount();
      int u = 0;
      String c = "\n";
      byte[] enter = c.getBytes();

      while(_rs.next()){
        for(int ii = 0; ii < k ; ii++){
          if(xx.KeepRunning() == false) {
            _rs.close();
            _stmt.close();
            return false;
          }
// ★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
// ★ 날짜는 이 부분을 고쳐야 한다 DB의 메타데이터(_rsmd)를 읽어서 datetime형이 있는 경우 이를 변환해서 넣는다
// ★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
//        if(u!=0) FOS.write(enter);
          FOS.write(_rs.getString(ii+1).getBytes());
		  System.out.println("u = " + u + ", Value = " + _rs.getString(ii+1));
          if(ii != k-1) FOS.write(";".getBytes());
          //xx.SetRunMessage(_rs.getString(ii+1));
        }
		FOS.write(enter);
        xx.SetPBValue(u);
        u++;
      }
      _rs.close();
      _stmt.close();
    }catch(SQLException ex){
      throw new JavaException(ex.getMessage());
    }catch(Exception e){}
    return true;
  }

  public boolean  ora_sqlExeStart(String sql,int colsNum, String projectpath, String projectname) throws JavaException{
    String projectfullpath = System.getProperty("minerdir") + "/xmminer/user/" + projectname + "/data/";
    CXMMetaDataSaver cmds = new CXMMetaDataSaver();
    cmds.setFileStatus(projectname, "xnodedb");

    cmds.setProfile("sql",sql);
    cmds.setProfile("colnum",_CCT.int2Str(colsNum));
    cmds.close();

    File dir = new File(projectfullpath);
 	if (!dir.exists()) dir.mkdir();
 
	try{
      FOS = new  FileOutputStream(projectfullpath+"temp.txt");
    }catch (FileNotFoundException cte){
      throw new JavaException(cte.getMessage());
    }
    return true;
  }

  public boolean  ms_sqlExeStart(String sql,int colsNum, String projectpath, String projectname) throws JavaException{
    String projectfullpath = System.getProperty("minerdir") + "/xmminer/user/" + projectname + "/data/";
    CXMMetaDataSaver cmds = new CXMMetaDataSaver();
    cmds.setFileStatus(projectname, "xnodedb");

    cmds.setProfile("sql",sql);
    cmds.setProfile("colnum",_CCT.int2Str(colsNum));
    cmds.close();

    File dir = new File(projectfullpath);
 	if (!dir.exists()) dir.mkdir();
 
	try{
      FOS = new  FileOutputStream(projectfullpath+"temp.txt");
    }catch(FileNotFoundException cte){
      throw new JavaException(cte.getMessage());
    }

    int rcnt = this.getRowCount(sql);
    int colNum = 0;
    String c = "\n";
    byte[] enter = c.getBytes();

    String title = _DBName+"DECLARE Employee_Cursor CURSOR FOR ";
    String open = " OPEN Employee_Cursor";
    String fetch = " FETCH NEXT FROM Employee_Cursor ";

    String end = " CLOSE Employee_Cursor DEALLOCATE Employee_Cursor";
    sql = title + sql + open + fetch;
    ResultSet sub_ResultSet;

    try{
      if(_rs != null) _rs.close();
 	  if(_stmt != null) _stmt.close();
      _stmt  = _Con.createStatement();
      _rs = _stmt.executeQuery(sql);
      _rsmd = _rs.getMetaData();
      int k = _rsmd.getColumnCount() - 1;
      int kk = k;
      int num = 0;

      _rs.next();
      int u = 1;
                				
      long timeValue; // 2000.10.22 이창호 추가

	  while (k > 0){
// ★★★★★★★★★★★★★★★★★★★★★★★★★★★
// ★ 2000.10.22 이창호 수정
// ★ source :	 FOS.write(_rs.getBytes(u));
// ★            k = k - 1;
// ★            u++;
// ★★★★★★★★★★★★★★★★★★★★★★★★★★★
		if(_rsmd.getColumnTypeName(u).compareTo("varchar")==0) FOS.write(_rs.getBytes(u));
 		else if(_rsmd.getColumnTypeName(u).compareTo("char")==0) FOS.write(_rs.getBytes(u));
		else if(_rsmd.getColumnTypeName(u).compareTo("smallint")==0) FOS.write(String.valueOf(_rs.getInt(u)).getBytes());
		else if(_rsmd.getColumnTypeName(u).compareTo("int")==0) FOS.write(String.valueOf(_rs.getLong(u)).getBytes());
		else if(_rsmd.getColumnTypeName(u).compareTo("real")==0) FOS.write(String.valueOf(_rs.getDouble(u)).getBytes());
		else if(_rsmd.getColumnTypeName(u).compareTo("datetime")==0){
          timeValue = _rs.getTimestamp(u).getTime();
		  FOS.write(String.valueOf(timeValue/1000).getBytes());
		}else System.out.println("#[XMXNodeDB] Unknown DataType Error!");

		if(k==1) break;
		FOS.write(";".getBytes());
        k = k - 1;
        u++;
      }
			
      k = kk;
      FOS.write(enter);

      }catch(SQLException ex){
        System.out.print("ms_sqlExeStart sqlGen : ");
	    System.err.println(ex.getMessage());
        throw new JavaException(ex.getMessage());
      }catch(Exception e){
        try{
          _stmt  = _Con.createStatement();
          _stmt.execute(end);
          _stmt.close();
          _rs.close();
        }catch(SQLException iie){
      }
      System.err.println(e.getMessage());
      throw new JavaException(e.getMessage());
	}
    return true;
  }

  public boolean  ms_sqlExeRun(int y) throws JavaException{
    try{
      String fetch = " FETCH NEXT FROM Employee_Cursor ";
      ResultSet sub_ResultSet = _stmt.executeQuery(fetch);
      sub_ResultSet.next();
      String c = "\n";
      byte[] enter = c.getBytes();
      if(y!=0) FOS.write(enter);
      int u = 1;
      int k = _rsmd.getColumnCount() - 1;

	  long timeValue; // 2000.10.22 이창호 추가
            
	  while (k > 0){
// ★★★★★★★★★★★★★★★★★★★★★★★★★★★
// ★ 2000.10.22 이창호 수정
// ★ source :	 FOS.write(sub_ResultSet.getBytes(u));
// ★            k = k - 1;
// ★            u++;
// ★★★★★★★★★★★★★★★★★★★★★★★★★★★
                				
		if(_rsmd.getColumnTypeName(u).compareTo("varchar")==0) FOS.write(sub_ResultSet.getBytes(u));
		else if(_rsmd.getColumnTypeName(u).compareTo("char")==0) FOS.write(sub_ResultSet.getBytes(u));
		else if(_rsmd.getColumnTypeName(u).compareTo("smallint")==0) FOS.write(String.valueOf(sub_ResultSet.getInt(u)).getBytes());
		else if(_rsmd.getColumnTypeName(u).compareTo("int")==0) FOS.write(String.valueOf(sub_ResultSet.getLong(u)).getBytes());
		else if(_rsmd.getColumnTypeName(u).compareTo("real")==0) FOS.write(String.valueOf(sub_ResultSet.getDouble(u)).getBytes());
		else if(_rsmd.getColumnTypeName(u).compareTo("datetime")==0){
          timeValue = sub_ResultSet.getTimestamp(u).getTime();
	      FOS.write(String.valueOf(timeValue/1000).getBytes());
		}else System.out.println("#[XMXNodeDB] Unknown DataType Error!");

		if(k==1) break;
		FOS.write(";".getBytes());
        k = k - 1;
        u++;
      }
      sub_ResultSet.close();
	}catch(SQLException ex) {
        System.out.print("ms_sqlExeRun sqlGen : ");
	    System.err.println(ex.getMessage());
      throw new JavaException(ex.getMessage());
    }catch(Exception e){}
    return true;
  }

  public boolean  ms_sqlExeEnd(int colsNum, String projectpath, String projectname, int index) throws JavaException{
    try{
      String end = " CLOSE Employee_Cursor DEALLOCATE Employee_Cursor";
      FOS.close();
      _stmt  = _Con.createStatement();
      _stmt.execute(end);
      _stmt.close();
      _rs.close();

      CXMDBExtractRunner cder = new CXMDBExtractRunner();
      String devider = ";";

      cder.extract("temp.txt","arc"+String.valueOf(index),colsNum,devider.getBytes(), System.getProperty("minerdir") + "/xmminer/user/" + projectname + "/");
    }catch(Exception je){
         System.out.print("ms_sqlExeEnd sqlGen : ");
    System.out.println(je.getMessage());
	throw new JavaException(je.getMessage());
    }

    return true;
  }

  public boolean  ms_sqlGenExe(String sql,int colsNum, String projectpath, String projectname, int index) throws JavaException{
    CXMMetaDataSaver cmds = new CXMMetaDataSaver();
    cmds.setFileStatus(projectpath, "xnodedb");
    cmds.setProfile("sql",sql);
    cmds.setProfile("colnum",_CCT.int2Str(colsNum));
    cmds.close();

    FileOutputStream FOS ;
    try{
      FOS = new  FileOutputStream(System.getProperty("minerdir") + "/xmminer/user/" + projectname + "/data/" + "temp.txt");
    }catch(FileNotFoundException cte){
      throw new JavaException(cte.getMessage());
    }

    int rcnt = this.getRowCount(sql);
    int colNum = 0;
    String c = "\n";
    byte[] enter = c.getBytes();

    String title = _DBName+"DECLARE Employee_Cursor CURSOR FOR ";
    String open = " OPEN Employee_Cursor";
    String fetch = " FETCH NEXT FROM Employee_Cursor ";

    String end = " CLOSE Employee_Cursor DEALLOCATE Employee_Cursor";
    sql = title + sql + open + fetch;
    ResultSet sub_ResultSet;

    try{
      if(_rs != null) _rs.close();
      if(_stmt != null) _stmt.close();
      _stmt  = _Con.createStatement();
      _rs = _stmt.executeQuery(sql);
      _rsmd = _rs.getMetaData();
      int k = _rsmd.getColumnCount() - 1;
      int kk = k;
      int num = 0;

      _rs.next();
      int u = 1;
      byte[] tebyte;
      String rr = "NULL";
      byte[] bbbbb = rr.getBytes();

      while(k > 0){
        FOS.write(_rs.getBytes(u));
        k = k - 1;
        u++;
      }
      k = kk;
      FOS.write(enter);

      for(int i = 1;i< rcnt; i++){
        sub_ResultSet = _stmt.executeQuery(fetch);
        sub_ResultSet.next();
        u = 1;
        while (k > 0){
          FOS.write(sub_ResultSet.getBytes(u));
          k = k - 1;
          u++;
        }
        k = kk;
        FOS.write(enter);
        _stmt.close();
        sub_ResultSet.close();
      }
      FOS.close();
      _stmt  = _Con.createStatement();
      _stmt.execute(end);
      _stmt.close();
      _rs.close();

      CXMDBExtractRunner cder = new CXMDBExtractRunner();
      String devider = ";";

      cder.extract("temp.txt","arc"+String.valueOf(index),colsNum,devider.getBytes(), System.getProperty("minerdir") + "/xmminer/user/" + projectname + "/");

      return true;
    }catch(SQLException ex){
      System.out.print("sqlGen : ");
	  System.err.println(ex.getMessage());
      throw new JavaException(ex.getMessage());
    }catch(Exception e){
      try{
      	_stmt  = _Con.createStatement();
        _stmt.execute(end);
        _stmt.close();
        _rs.close();
      }catch(SQLException iie){}
      System.err.println(e.getMessage());
      throw new JavaException(e.getMessage());
    }
  }

  public String[][] sqlGenPreview(String[] c, String[] d, String str) throws JavaException{
    String a = _CDC.getSQLManager().getExeSql(c, d, str, "0", "101");
    int rowCount = this.getRowCount(a);

    try{
      this.createStmtQuery(a);

      int k = _rsmd.getColumnCount();

      obj = null;
      obj = new String[rowCount][k];
      String t ="";
      int tt = 0;

      String columnValue = "";

      while(_rs.next()){
        if(tt == rowCount) break;
        for(int i = 0; i < k; i++){
          if(_rs.getString(i+1) != null){
            columnValue = _rs.getString(i+1);
            columnValue = _CCT.ascii2ksc(columnValue, _charSet);
          }else columnValue = "값이 없어요!!!";
          obj[tt][i] = columnValue;
        }
        tt++;
      }
      _rs.close();
      _stmt.close();
    }catch(SQLException ex){
      System.out.print("sqlGen : ");
      System.err.println(ex.getMessage());
      throw new JavaException(ex.getMessage());
    }catch(Exception e){
      System.out.print("sqlGen 2_preview: ");
      System.err.println(e.getMessage());
      obj = new String[1][1];
      obj[0][0] = "Null";
      throw new JavaException(e.getMessage());
    }
    return obj;
  }

  public String[] getFKTable(String tab_name) throws JavaException{
    String query = _CDC.getSQLManager().getFKTable(tab_name);
    String[] value;
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
        String val = "";
     
		while(_vEum.hasMoreElements()) {
          val = (String)_vEum.nextElement();
          val = tName+"."+val;
          val = val+" = "+tab_name+"."+(String)_vEum.nextElement();
	 	  _VEC.addElement(val);
        }
        _VEC2.removeAllElements();
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

  public String[] ora_getFKTable(String tab_name) throws JavaException{
    String query = _CDC.getSQLManager().getFKTable(tab_name);
    String[] value;
    try{
      this.createStmtQuery(query);
	  int i = 0;
	  String tName = "";
	  String con = "";

      _VEC.removeAllElements();

	  while(_rs.next()) {
        i++;
        _VEC.addElement(_rs.getString(1));
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
      System.out.print("ora_getFKTable : ");
      System.out.println(e.getMessage());
      throw new JavaException(e.getMessage());
    }
    return _Tables;
  }

  private Vector getFKColumn(String tab_name, String con_name) throws JavaException{
    Vector _v = new Vector();
    try{
      _VEC3 = _CDC.getSQLManager().getFKRKColumn(tab_name, con_name);
      _stmt2 = _CDC.getConn().createStatement();
      _vEum = _VEC3.elements();
      String query = "";

      while(_vEum.hasMoreElements()) {
        query = (String)_vEum.nextElement();
        _rs2 = _stmt2.executeQuery(query);
	    String val = " " ;
        int chkNum = 0;
        if(_rs2.next()){
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
    }catch(Exception  ee){
   	  System.out.print("getFKColumn 2: ");
      System.out.println(ee.getMessage());
      throw new JavaException(ee.getMessage());
	}
      return _v;
  }

  public String  getTableDataViewSQL(String tableName){
    return _CDC.getSQLManager().getRowCount(tableName);
  }

  public String[] getColumnList(String tableName) throws JavaException {
    String[] test1;
    test1 = null;
    try{
      test1 = getColumnNames(tableName);
    }catch(JavaException e){
      throw new JavaException(e.getMessage());
    }
    return test1;
  }

  public String[][]  ms_sqlGenSection(String sql, int row) throws JavaException{
    int rcnt = this.getRowCount(sql);
    if(rcnt < 1) {
      String[][] obj = new String[1][1];
      obj[0][0] = "NONE";
      return obj;
    }
    
	if(rcnt - row > 21)        rcnt = 20;
    else rcnt = rcnt - row + 1;

    if(rcnt < 1) {
      String[][] obj = new String[1][1];
      obj[0][0] = "NONE";
      return obj;
    }

	int colNum = 0;
    String title = _DBName+"DECLARE Employee_Cursor SCROLL CURSOR FOR ";
    String open = " OPEN Employee_Cursor";
    String nextFetch = " FETCH NEXT FROM Employee_Cursor ";
    String absoluteFetch = " FETCH ABSOLUTE "+_CCT.int2Str(row) +" FROM Employee_Cursor ";
    String end = " CLOSE Employee_Cursor DEALLOCATE Employee_Cursor";
    sql = title + sql + open + absoluteFetch;
    ResultSet sub_ResultSet;

    try{
      if(_rs != null) _rs.close();
	  if (_stmt != null) _stmt.close();
      _stmt  = _Con.createStatement();
      _rs = _stmt.executeQuery(sql);
      _rsmd = _rs.getMetaData();
      _rs.next();
      int k = _rsmd.getColumnCount() - 1;
      colNum = k;
      String[][] data = new String[rcnt][k];
      String columnValue = "";
      String coldata;
      int iType =  0;

      for(int u = 1; u <= k; u++){
        if((coldata = _rs.getString(u)) == null) coldata = "NULL";
        data[0][u - 1] = _CCT.ascii2ksc(coldata, _charSet);;
      }

      for(int i = 1;i< rcnt; i++){
        sub_ResultSet = _stmt.executeQuery(nextFetch);
        sub_ResultSet.next();
        for(int u = 1; u <= k; u++){
          if((coldata = sub_ResultSet.getString(u)) == null) coldata = "NULL";
          data[i][u-1] = _CCT.ascii2ksc(coldata, _charSet);
        }
        //_stmt.close();
        sub_ResultSet.close();
      }
	  _stmt.close();
      _rs.close();
   	  _stmt  = _Con.createStatement();
      _stmt.execute(end);
      _stmt.close();
      _rs.close();
      return data;

    }catch(SQLException ex){
      try{
       	_stmt  = _Con.createStatement();
        _stmt.execute(end);
        _stmt.close();
        _rs.close();
      }catch(SQLException iie){
        throw new JavaException(iie.getMessage());
      }
      System.out.print("sqlGen : ");
      System.err.println(ex.getMessage());
	  ex.printStackTrace();
      throw new JavaException(ex.getMessage());
	}catch(Exception e){
      try{
       	_stmt  = _Con.createStatement();
        _stmt.execute(end);
        _stmt.close();
        _rs.close();
      }catch(SQLException iie){
        throw new JavaException(iie.getMessage());
      }
      System.out.print("sqlGen 2_section: ");
      System.err.println(e.getMessage());
      throw new JavaException(e.getMessage());
    }
  }

}