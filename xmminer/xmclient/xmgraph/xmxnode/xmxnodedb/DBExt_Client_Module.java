package xmminer.xmclient.xmgraph.xmxnode.xmxnodedb;

import xmminer.xmclient.*;
import xmminer.xmclient.xmgraph.xmxnode.xmxnodedb.*;
import xmminer.xmserver.xmgraph.xmxnode.xmxnodedb.*;
import xmminer.xmserver.xmgraph.xmxnode.xmxnodedb.XMBXNodeDB;

import javax.swing.*;
import java.io.*;
import java.awt.*;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Vector;
import java.util.Enumeration;

public class DBExt_Client_Module {

  //start member variables declaration
  XMBXNodeDB _BND ;
  XMDialogXNodeDB _DD;
  JFrame _Frame;
  p_Tree pt;
  Enumeration _vEum;
  boolean bb = false;
  //end of member variables declaration

  //Constructor
  public DBExt_Client_Module(JFrame frame, XMBXNodeDB bnd){
    _Frame = frame;
    _BND = bnd;
  }

  public void setCORBA(XMBXNodeDB bnd){
    _BND = bnd;
  }

  public void startDialog(String projectname){
    DBLogin_Dialog s = new DBLogin_Dialog(_Frame, "Connect to DataBase",true,this, projectname);
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = s.getSize();
    if (frameSize.height > screenSize.height) frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width) frameSize.width = screenSize.width;
    s.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    s.setVisible(true);
  }

  public p_Tree tree_prievew(XMDialogXNodeDB _DD){
    pt = new p_Tree(_DD, this);
    return pt;
  }

  // sql문을 생성한다...
  public String sqlGen(Vector a, Vector b){
    try {
      StringWriter sw = new StringWriter();
      PrintWriter pw = new PrintWriter(sw);
      pw.print("SELECT ");
      _vEum = a.elements();
      String val = "";
      int indexCount = a.size();
      int indexCnt = b.size();
      int i = 1;

      while(_vEum.hasMoreElements()){
        val = (String)_vEum.nextElement();
        pw.print("      ");
        if(indexCount == i) pw.println(val);
        else pw.println(val+",");
        i++;
      }

      pw.print(" FROM ");
      _vEum = b.elements();
      String value = "";
      String forign2 = "";
      String[] forignList = null;
      i = 1;

      while(_vEum.hasMoreElements()){
        value = (String)_vEum.nextElement();
        forignList = _BND.getFKTable(value);
        pw.print("      ");
        if(indexCnt == i)    pw.println(value);
        else pw.println(value+",");
        if(forignList[0].compareTo("none") != 0){
          for(int num = 0; num < forignList.length;num++){
            String values = forignList[num].substring(0,forignList[num].indexOf("."));
            if(b.contains(values)){
              if(num == forignList.length - 1){
                forign2 = forign2 + forignList[num]+" and "+"\n";
              }else forign2 = forign2 + forignList[num]+" and "+"\n";
            }
          }
        }else forign2 = forign2  + "";

        i++;
      }

      if(forign2.compareTo("") != 0)  pw.print("where "+forign2.substring(0, forign2.length() - 5));
      return sw.toString();
    }catch(Exception e){
      return "error";
    }
  }

/*------------------------ / Server Module 호출하는 Function 모음 / ------------------------*/
  public String[] getColumnAll(String tab_name){
    return _BND.getColumnAll(tab_name);
  }

  public String[] getFKTable(String tab_name){
    return _BND.getFKTable(tab_name);
  }

  public String[] getColumnList(String tabName){
    return _BND.getColumnList(tabName);
  }

  public Object[][] ms_sqlGenSection(String tabName, int rowSet){
    return _BND.ms_sqlGenSection(tabName, rowSet);
  }

  //한개 테이블의 모든 컬럼을 로우 단위로 부분 select 할때 쓰는 펑션
  public Object[][] sqlGenView(String rowNum, String tabName, int rowSet){
    return _BND.sqlGenView(rowNum, tabName, rowSet);
  }

  //설정되어 있는 sql을 로우 단위로 부분 select 할때 쓰는 펑션
  public Object[][] sqlGenPreview(String[] colList, String[] tabList, String sql, int rowSet) throws XNodeDBException{
    return _BND.sqlGenPreview(colList, tabList, sql, rowSet);
  }

  public String[][] sqlQuery(String sql){
    return _BND.sqlQuery(sql);
  }

/*  public void sqlGenExe(String[] colList, String[] tabList, String sql, int index){
    _BND.sqlGenExe(colList, tabList,sql, index);
  }*/

  public void dbConnect(String driver, String url, String loginID, String password)  throws XNodeDBException{
    _BND.dbConnect(driver, url, loginID, password);
    bb = true;
  }

  public void dbDisConnect(){
    _BND.dbDisConnect();
  }

  public String[] getTableNames(){
    return _BND.getTableNames();
  }

  public String  ora_realSqlGen(Vector a, Vector b){
    try {
      StringWriter sw = new StringWriter();
      PrintWriter pw = new PrintWriter(sw);
      pw.print("SELECT ");
      _vEum = a.elements();
      String val = "";
      String data_length = "";

      int indexCount = a.size();
      int indexCnt = b.size();
      int i = 1;

      while(_vEum.hasMoreElements()){
        val = (String)_vEum.nextElement();
//        val = val+" ||';'|| "; //이창호 삭제
//        if(indexCount == i) pw.print(val.substring(0,val.length() - 9)); //이창호 수정
        if(indexCount == i) pw.print(val);
//        else if ((i% 30) == 0) pw.print(val+","); //이창호 삭제
//        else pw.print(val+"+"); //이창호 수정
        else pw.print(val+",");
        i++;
      }

      pw.print(" FROM ");
      _vEum = b.elements();
      String value = "";
      String forign = "";
      String forign2 = "";
      String[] forignList = null;
      i = 1;

      while(_vEum.hasMoreElements()) {
        value = (String)_vEum.nextElement();
        forignList = _BND.getFKTable(value);
        pw.print('\t');
        if(indexCnt == i) pw.print(value+" ");
        else pw.print(value+",");
        if(forignList[0].compareTo("none") != 0){
          for(int num = 0; num < forignList.length;num++){
            String values = forignList[num].substring(0,forignList[num].indexOf("."));
            if(b.contains(values)) {
              if(num == forignList.length - 1){
                forign2 = forign2 + forignList[num]+" and "+" ";
              }else forign2 = forign2 + forignList[num]+" and "+" ";
            }
          }
        }else forign2 = "";

        i++;
      }

      if(forign2.compareTo("") != 0) pw.print("where "+forign2.substring(0, forign2.length() - 5) );
      return sw.toString();
    }catch(Exception e){ return "error";}
  }

  public String  ms_realSqlGen(Vector a, Vector b){
    try{
      StringWriter sw = new StringWriter();
      PrintWriter pw = new PrintWriter(sw);
      pw.print("SELECT ");
      _vEum = a.elements();
      String val = "";
      String data_length = "";

      int indexCount = a.size();
      int indexCnt = b.size();
      int i = 1;

      while(_vEum.hasMoreElements()){
        val = (String)_vEum.nextElement();
//        val = "CAST("+val+" as varchar)+';'"; //이창호 삭제
//        if(indexCount == i) pw.print(val.substring(0,val.length() - 4)); //이창호 수정
        if(indexCount == i) pw.print(val);
//        else if ((i% 30) == 0) pw.print(val+","); //이창호 삭제
//        else pw.print(val+"+"); //이창호 수정
        else pw.print(val+","); 
        i++;
      }

      pw.print(" FROM ");
      _vEum = b.elements();
      String         value = "";
      String forign = "";
      String forign2 = "";
      String[] forignList = null;
      i = 1;

      while(_vEum.hasMoreElements()) {
        value = (String)_vEum.nextElement();
        forignList = _BND.getFKTable(value);
        pw.print('\t');
        if(indexCnt == i)    pw.print(value+" ");
        else pw.print(value+",");
        if(forignList[0].compareTo("none") != 0){
          for(int num = 0; num < forignList.length;num++){
            String values = forignList[num].substring(0,forignList[num].indexOf("."));
            if(b.contains(values)){
              if(num == forignList.length - 1){
                forign2 = forign2 + forignList[num]+" and "+" ";
              }else forign2 = forign2 + forignList[num]+" and "+" ";
            }
          }
        }else forign2 = "";

        i++;
      }

      if(forign2.compareTo("") != 0)  pw.print("where "+forign2.substring(0, forign2.length() - 5) );
      return sw.toString();
    }catch(Exception e){
      return "error";
    }
  }

  public String[] ColumnOnlyOneSQLQuery(String tab, String col){
    String query;
    query = "Select Distinct "+ col+" from "+tab;
    return this._BND.ColumnOnlyOnesqlQuery(query);
  }

  public boolean check(){
    return bb;
  }

  public Vector str2Vec(String input[]) {
    Vector v = new Vector();
    int size = input.length;
    for (int i=0; i<size; i++) v.addElement(input[i]);
    return v;
  }

  public void XnodeDBMetaDataSave(String key, String val, String projectname){
    _BND.setProfile(key, val, projectname);
  }

  public void XnodeDBMetaDataSave(String key, String[] val, String projectname){
    _BND.setProfiles(key, val, projectname);
  }

  public String XnodeDBMetaDataRead(String key, String projectname){
    return _BND.getProfile(key, projectname);
  }

  public String[] XnodeDBMetaDataReads(String key, String projectname){
    return _BND.getProfiles(key, projectname);
  }

  public boolean MetaDataCreate(String[] colList, String[] typeList, int index, String projectname){
    return _BND.MetaDataCreate(colList, typeList, index, projectname);
  }
}