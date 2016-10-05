package xmminer.xmserver.xmgraph.xmmnode.xmmnodeassociationrule;

import xmminer.xmserver.*;
import xmminer.xmserver.xmgraph.*;
import xmminer.xmserver.xmgraph.xmmnode.*;
import xmminer.xmserver.xmgraph.xmmnode.xmmnodeassociationrule.*;
import xmminer.xmlib.xmtable.*;
import xmminer.xmlib.xmcompute.*;
//import xmminer.xmvbj.xmgraph.xmmnode.xmmnodeassociationrule.XMBMNodeAssociationRulePOA;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
import java.lang.*;

public class XMBMNodeAssociationRule {
  String IsRuleComplete = "false";
  String ParameterFileName;
  String projectName;  //projectName
  String projectPath;  //projectName
  String dataFile;
  String nextArcFile;
  String modelName;
  String path;

  int min_sup;        //min_sup
  int min_sup_type;   //min_sup_type
  float min_conf;     //min_conf
  int transNumber;    //transaction 수

  int dbType ;   //(frolle)

  String[] selAttributes; //dbType =1에 필요한 변수
  String transIDFld;  //Type 2에서 기준이 되는 필드명(transaction ID)
  String targetFld;   //Type 2에서 item 필드명

  String[] columnList;  //dbType =2에 필요한 변수

  public static int num_rows;  ///  arc xxx  에 rule에 저장될 row의 수

  //public XMBMNodeAssociationRuleImpl () {
  public XMBMNodeAssociationRule () {
    super();
//	init();
  }

  public void setParameter(){
    String ParameterFileName = getParameterFileName();

  	CXMMetaDataReader pdata = new CXMMetaDataReader();
	pdata.setFileStatus(projectName,ParameterFileName);

	String temp = pdata.getProfile("METAFILE_NAME");
    dataFile = temp.toString();

    temp = pdata.getProfile("PROJECT_PATH");
    projectPath = temp.toString();

    temp = pdata.getProfile("MODEL_NAME");
	modelName = temp.toString();

    temp = pdata.getProfile("MIN_SUP_TYPE");
	min_sup_type = str2Int(temp);

    float min_sup_float = 0f;
    if(min_sup_type == 0){  //"개"
	  temp = pdata.getProfile("MIN_SUP");
	  min_sup = str2Int(temp);
    }else{  //"%"
	  temp = pdata.getProfile("MIN_SUP");
	  min_sup_float = str2Float(temp);
    }

 	temp = pdata.getProfile("NUMBER_OF_TRANS");
	transNumber = str2Int(temp);

	temp = pdata.getProfile("MIN_CONF");
	min_conf = str2Float(temp);

	temp = pdata.getProfile("DB_TYPE");
	dbType = str2Int(temp);

    CXMMetaDataReader mdata = new CXMMetaDataReader();
	mdata.setFileStatus(projectName, dataFile);

	if( dbType == 1){
	  temp = pdata.getProfile("SELECTED_ATTRIBUTES");
	  String attributes = temp.toString();  //사용자가 선택된 필드들

      //선택된 attributes를 "^"로 파싱한다.
      StringTokenizer tokened_attributes = new StringTokenizer(attributes, "^");
      int num_attributes = tokened_attributes.countTokens();
      selAttributes = new String[num_attributes];
      int i = 0;
      while(tokened_attributes.hasMoreElements()){
        selAttributes[i] = (String)tokened_attributes.nextElement();
        i++;
      }

      String strRow = mdata.getProfile("NUMBER_OF_ROWS");
      transNumber= str2Int(strRow); //transaction 수
      //transaction을 구해 cxmtable로 저장한다.
      //getTransactions();  //dbType = 2의 getTransactionNumber()와 비슷..

    }else{  //dbType == 2
	  temp = pdata.getProfile("TRANSID_FLD");
	  transIDFld = temp.toString();
	  temp = pdata.getProfile("TARGET_FLD");
	  targetFld = temp.toString();

      columnList = mdata.getProfiles("COLUMN_LIST");  //모든 필드들
	  transNumber= getTransactionNumber(projectName, dataFile, transIDFld);  //transaction 수
	}
    mdata.close();

	if( min_sup_type == 1)
	  min_sup = (int)(min_sup_float *(float)transNumber); //"%"

    pdata.close();
  }

  private int str2Int(String str){
    Integer i = new Integer(str);
   	return i.intValue();
  }

  private float str2Float(String str){
    Float f = new Float(str);
   	return f.floatValue();
  }

  public void StartRule(){
  }

  public static byte[] addColAtRow(byte col[], byte row[], int[] colIndex, int j){
    //현재 row에다 col을 덧붙이고, colIndex[j]에 col의 length를 추가하는 루틴이다.

    int colLength = col.length;
    int rowLength = row.length;
    int newCapacity = colLength + rowLength;

    byte[] row_tmp = new byte[newCapacity];

    System.arraycopy(row, 0, row_tmp, 0, rowLength);
    System.arraycopy(col, 0, row_tmp, rowLength, colLength);

    colIndex[j] = colLength;

    return row_tmp;
  }

  int m_pbMin ;
  int m_pbMax ;
  int m_pbValue = 0;
  int max ;
  int min = 1;

  public int GetPBMin(){    // must be called by client and only once!
    // IMPLEMENT:
    SetPBMin();

    return m_pbMin;
  }

  public void SetPBMin(){
    m_pbMin = min;
  }

  public int GetPBMax(){    // must be called by client and only once!
    // IMPLEMENT:
    SetPBMax();

    return m_pbMax;
  }

  
  public void SetPBMax(){
      m_pbMax= 100;
  }

  public int GetPBValue(){
    // IMPLEMENT:
    return m_pbValue;
  }

  boolean m_bStop;
  boolean m_bAlive;

  public int PerformRunning(){ 
    setParameter();
	 
    try{
      start();
    }catch(IOException ce){
      return(0);
    }
	
    return(0);
  }

  public int Run(){
    // IMPLEMENT:
    m_bStop = false;
    m_bAlive = true;
    int r = PerformRunning();
    m_bAlive = false;
    m_bStop = true;
    return(r);
  }

  public boolean KeepRunning(){
    return(!m_bStop);
  }

  public void StopRunning(){
    // IMPLEMENT:
    m_bStop = true;
  }

  public boolean IsRunning(){
    // IMPLEMENT:
    return(m_bAlive);
  }

  public Hashtable addHashtable(Hashtable child, Hashtable parent){
    //child의 item을 parent에 추가한다.

    String itemset = new String();
    Integer support;

    Enumeration keySet = child.keys();

    while(keySet.hasMoreElements()){
      itemset = (String)keySet.nextElement();
      support = (Integer)child.get(itemset);

      parent.put(itemset, support);
    }

    return parent;
  }

  public int getNumItems(String litemset){
    //litemset이 몇개의 item으로 이루어졌는지 개수를 리턴한다.

    StringTokenizer tokend_litem = new StringTokenizer(litemset, ",");
    int num = tokend_litem.countTokens();

    return num;
  }

  public void start()  throws IOException{
    m_pbValue = 2;

    try{
      //Discovering Large Itemsets --------------------------------------------------

      int num_litems = 0;         //구해진 large itemset의 개수
      int num_trans = transNumber;          //총 transaction의 수
      int sup = min_sup;
      float conf = min_conf;   //String to float

      String model =  modelName;

      Hashtable largeHash = new Hashtable();

      int k = 1;
      Litemset litemset_tmp = new Litemset(k);

      if(dbType == 1){
		do{
          Litemset litemset = new Litemset(k, sup, model, num_trans, projectName, dataFile, projectPath);   //num_items, min_sup
          litemset.setParam1(selAttributes);  //k가 증가할때마다 Litemset이 다시 만들어지므로..

          if(k == 1){
            litemset.itemsDataSaver();
            m_pbValue = 7;
            litemset.read_allItem();    //Candidate 1-Itemset을 구한다.
            m_pbValue = 14;
          }else{
            litemset_tmp.apriori_gen(litemset);  //Candidate itemset을 생성한다.
          }//end else

          num_litems = litemset.gen_litemset();
          addHashtable(litemset.table, largeHash);

          litemset_tmp = litemset;
          k++;

          if(KeepRunning()){
		    if(k <= 11){
			  m_pbValue += 6;
			}
		  }else{   // KeepRunning 이 false
 		    return;
		  }
        }while(num_litems != 0);   //litemset_tmp.table.isEmpty() == false
      }else{  //dbType == 2
        do{
          Litemset litemset = new Litemset(k, sup, model, num_trans, projectName, dataFile, projectPath);   //num_items, min_sup
          litemset.setParam2(transIDFld, targetFld, columnList);  //k가 증가할때마다 Litemset이 다시 만들어지므로..

          if(k == 1){
            litemset.itemsDataSaver2();
            m_pbValue = 42;
            litemset.read_allItem();    //Candidate 1-Itemset을 구한다.
            m_pbValue = 44;
          }else{
            litemset_tmp.apriori_gen(litemset);  //Candidate itemset을 생성한다.
          }//end else

          num_litems = litemset.gen_litemset2();
          addHashtable(litemset.table, largeHash);

          litemset_tmp = litemset;
          k++;

          if(KeepRunning()){
			if(k <= 10){
			  m_pbValue += 3;
			}
		  }else{   // KeepRunning 이 false
		    return;
		  }

        }while(num_litems != 0);   //litemset_tmp.table.isEmpty() == false
      }//end else

      m_pbValue = 74;

	  CXMMetaDataSaver ruleMetaData = new CXMMetaDataSaver();
      ruleMetaData.setFileStatus(projectName,nextArcFile);

      CXMTableSaver ruleData = new CXMTableSaver();   //litemset data file
      ruleData.setFileStatus(projectName,nextArcFile);

      //Discovering Rules
      String litem = new String();
	  num_rows = 0;

      AssoRule.setParam(largeHash, conf, model, num_trans, projectName, dataFile, projectPath);

      Enumeration keySet = largeHash.keys();
      int size = largeHash.size();

      while(keySet.hasMoreElements()){
        litem = (String)keySet.nextElement();
        Integer litem_sup = (Integer)largeHash.get(litem);
        sup = litem_sup.intValue();

        int i = getNumItems(litem);
        if(i >= 2){ //litem이 2개 이상의 item로 이루어진 경우만
          AssoRule Lkset_rules = new AssoRule(litem, i, sup);
          Lkset_rules.gen_rule2(litem, i, ruleData);
        }
      }

	  m_pbValue = 100;

	  ruleMetaData.setProfile("NUMBER_OF_ROWS", String.valueOf(num_rows));
	  ruleMetaData.close();
	  ruleData.close();
      RuleComplete();
   }catch(IOException ex){
   }finally{
   }
	return;
  }


  public void fileUpload(String filename, byte[] buf){
    try {
      FileOutputStream fout=  new FileOutputStream(filename);
   	  ObjectOutputStream outStream = new ObjectOutputStream(fout);
      outStream.write(buf);
      outStream.close();
	  fout.close();
    }catch(Exception e){
   	  e.printStackTrace();
    }
  }

  public String getProfile(String _ProjectName,String Metafile, String key) {
    // IMPLEMENT:
    CXMMetaDataReader metadata = new CXMMetaDataReader();
    metadata.setFileStatus(_ProjectName,Metafile);

    String value = metadata.getProfile(key);
    metadata.close();

    if(value == null || value.length() == 0){
      value = "null";
    }
    return value;
  }

  public String[] getProfiles(String _ProjectName,String Metafile,String key) {
    // IMPLEMENT:
    CXMMetaDataReader metadata = new CXMMetaDataReader();
    metadata.setFileStatus(_ProjectName,Metafile);
   
    String[] values = metadata.getProfiles(key);
    metadata.close();

    if(values == null || values.length == 0){
      values[0] = "null";
    }
    return values;
  }

  public void setProfile (String _ProjectName,String Metafile, String key, String value) {
    // IMPLEMENT:
    CXMMetaDataSaver metadata = new CXMMetaDataSaver();
    metadata.setFileStatus(_ProjectName,Metafile);
   
	if(key.equals("PROJECT_PATH")) value = System.getProperty("minerdir") + "/xmminer/user/";

	metadata.setProfile(key,value);
    metadata.close();
    return ;
  }

  public void setProfiles (String _ProjectName,String Metafile,String key, String[] value) {
    // IMPLEMENT:
    CXMMetaDataSaver metadata = new CXMMetaDataSaver();
    metadata.setFileStatus(_ProjectName,Metafile);
   
    metadata.setProfiles(key,value);
    metadata.close();
    return ;
  }

  public void setParameterFileName(String _ParameterFileName){
    ParameterFileName = _ParameterFileName;
    return;
  }

  public String getParameterFileName(){
    return ParameterFileName;
  }

  public void setProjectName(String _ProjectName,String NextArcfile){
    projectName = _ProjectName;
    nextArcFile = NextArcfile;
    return ; 
  }

  public int getTransNumber(){
    return transNumber;
  }

  public String getProjectName(){
    return projectName; 
  }

  public int getTransactionNumber(String _ProjectName,String Arcfile, String transIDfld){
    // IMPLEMENT:
    String colUniqueValue;
    int n_trans = 0;

    Hashtable colValues = new Hashtable();

    int m = 1;
    m_pbValue = 1;
    CXMGetUniqueColumnValue transData = new CXMGetUniqueColumnValue();
    //transIDFld에 대한 유일한 값들을 Vector에 저장한다.
    transData.setFileStatus(_ProjectName, Arcfile, transIDfld);
    colValues = transData.getUniqueValueSet();
    transData.close();
    n_trans = colValues.size();
    m_pbValue = 10;

    Vector transaction;
    int size; //하나의 transaction을 이루고 있는 item들의 수
    String transLine;

    int[] colIndex = new int[1];  //1개의 column
    byte[] row, column;

    CXMGetAssociationTransaction transData2 = new CXMGetAssociationTransaction();  //transaction data file
    transData2.setFileStatus(_ProjectName, Arcfile, transIDfld, targetFld);
    CXMTableSaver transData3 = new CXMTableSaver();
    transData3.setFileStatus(_ProjectName, modelName + "_asso_trans");

    m = 1;
    
    try{
      Enumeration hEum = colValues.keys();
      while(hEum.hasMoreElements()){
        colUniqueValue = (String) hEum.nextElement();              
        transaction = transData2.getAssociationTransaction((byte[])colValues.get(colUniqueValue));
        size = transaction.size();
        transLine = new String("");
        for(int s = 0; s <size; s++) transLine += (String)transaction.elementAt(s) + ",";
        transLine = transLine.substring(0, transLine.length()-1); //ex)apples,carrots,tomatoes
        row = transLine.getBytes();   //transaction
        colIndex[0] = row.length;
        transData3.createRowData(row, colIndex);  //하나의 transaction을 저장한다.

        if(n_trans == 100*m){
          if(n_trans <= 2900){
            m_pbValue += 1;
            m++;
          }
        }//end if
      }//end for
    }catch(Exception cte){ System.out.println("create_asso_rule_table_error="+cte.getMessage());}
   
    m_pbValue = 40;

    colValues.clear();
    transData3.close();
    transData2.close();

    CXMMetaDataSaver metaData = new CXMMetaDataSaver();
    metaData.setFileStatus(_ProjectName, modelName + "_asso_trans");
    metaData.setProfile("NUMBER_OF_ROWS", Integer.toString(n_trans)); //transaction 수
    metaData.setProfile("NUMBER_OF_COLUMNS", Integer.toString(1));
    metaData.setProfile("COLUMN_LIST", "trans");
    metaData.setProfile("COLUMN_INDEX", "1");
    metaData.setProfile("trans", "STRING^INFINITE^DISCRETE^NOMINAL^not_sorted^not_filtered^not_transformed^not_computed");
    metaData.close();

    return n_trans;
  }

  public void setSchema(String _ProjectName, String NextArcfile){
    CXMMetaDataSaver ruleMetaData = new CXMMetaDataSaver();
    ruleMetaData.setFileStatus(_ProjectName,NextArcfile);

    ruleMetaData.setProfile("NUMBER_OF_COLUMNS", "5");
    String colNames = "premise^consequent^support_num^support_per^confidence";
    ruleMetaData.setProfile("COLUMN_LIST", colNames);
    ruleMetaData.setProfile("DATA_FILE", NextArcfile);
    ruleMetaData.setProfile("ROW_INDEX", NextArcfile);

    colNames = "1^2^3^4^5";
    ruleMetaData.setProfile("COLUMN_INDEX", colNames);
    String property = "STRING^INFINITE^DISCRETE^NOMINAL^not_sorted^not_filtered^not_transformed^not_computed";
    ruleMetaData.setProfile("premise", property);
    ruleMetaData.setProfile("consequent", property);
    property = "INTEGER^INFINITE^DISCRETE^CARDINAL^not_sorted^not_filtered^not_transformed^not_computed";
    ruleMetaData.setProfile("support_num", property);
    property = "REAL^INFINITE^DISCRETE^CARDINAL^not_sorted^not_filtered^not_transformed^not_computed";
    ruleMetaData.setProfile("support_per", property);
    ruleMetaData.setProfile("confidence", property);
    ruleMetaData.close();
  }

  private void RuleComplete(){
    CXMMetaDataSaver ParameterMetaData = new CXMMetaDataSaver();
    ParameterMetaData.setFileStatus(projectName,ParameterFileName);
    ParameterMetaData.setProfile("RULE","true");
    ParameterMetaData.close();
    return;
  }

  public String getIsRuleComplete(String _ProjectName,String _ParameterFile){
    try{
      CXMMetaDataReader ParameterMetaData = new CXMMetaDataReader();
      ParameterMetaData.setFileStatus(_ProjectName,_ParameterFile);
      IsRuleComplete = ParameterMetaData.getProfile("RULE");
      ParameterMetaData.close();
      if(IsRuleComplete == null) IsRuleComplete= "false";
      return IsRuleComplete;
    }
    catch (Exception e){
      return "false";
    }
  }

//  public static ServerMonitorPage monitor = null;
//  private void init() {
//    java.util.ResourceBundle res = java.util.ResourceBundle.getBundle("xmminer.xmserver.ServerResources");
//    String name = "XMBMNodeAssociationRule";
//    if (monitor == null) {
//    monitor = ServerMonitor.addPage(this, name);
//    monitor.showObjectCounter(true);
//	  monitor.showReadCounter(true);
//	  monitor.showWriteCounter(true);
//    ServerMonitor.register(name);
//    }
//    monitor.updateObjectCounter(1);
//    ServerMonitor.log(ServerResources.format(res.getString("created"), name));
//  }
}