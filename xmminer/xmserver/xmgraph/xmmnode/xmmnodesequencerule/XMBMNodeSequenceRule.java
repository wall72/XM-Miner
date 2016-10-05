package xmminer.xmserver.xmgraph.xmmnode.xmmnodesequencerule;

import xmminer.xmserver.*;
import xmminer.xmserver.xmgraph.*;
import xmminer.xmserver.xmgraph.xmmnode.*;
import xmminer.xmserver.xmgraph.xmmnode.xmmnodesequencerule.*;
import xmminer.xmlib.xmtable.*;
import xmminer.xmlib.xmcompute.*;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
import java.lang.*;

public class XMBMNodeSequenceRule{
  String IsRuleComplete = "false";
  String ParameterFileName;
  String projectName;  //projectName
  String projectPath;
  String dataFile;
  String modelName;   //modelName
  String nextArcFile;
  String path;

  int min_sup;        //min_sup
  int min_sup_type;   //min_sup_type
  float min_conf;     //min_conf
  int transNumber;    //transaction 수

  String transIDFld = "IP" ;  //기준이 되는 필드명(transaction ID)
  String targetFld = "LOG" ;  //item 필드명
  String timeFld = "TIME" ;   //"time";

  public static int num_rows;  ///  arc xxx  에 rule에 저장될 row의 수

  public XMBMNodeSequenceRule() {
    super();
//	init();
  }

  public String int2Str(int i){
    Integer ii = new Integer(i);
    return ii.toString();
  }

  public String float2Str(float f){
    Float ff = new Float(f);
    return ff.toString();
  }

  public int str2Int(String str){
    Integer i = new Integer(str);
   	return i.intValue();
  }

  public float str2Float(String str){
    Float f = new Float(str);
   	return f.floatValue();
  }

  public void setParameter(){
    String ParameterFileName = getParameterFileName();
    String temp  = new String();

    CXMMetaDataReader pdata = new CXMMetaDataReader();
    pdata.setFileStatus(projectName, ParameterFileName);

    temp = pdata.getProfile("METAFILE_NAME");
    dataFile = temp.toString();

    temp = pdata.getProfile("PROJECT_PATH");
    projectPath = temp.toString();

    temp = pdata.getProfile("MODEL_NAME");
    modelName = temp.toString();

    temp = pdata.getProfile("MIN_SUP_TYPE");
    min_sup_type = str2Int(temp);

    float min_sup_float = 0f;
    if(min_sup_type == 0){ //개
      temp = pdata.getProfile("MIN_SUP");
      min_sup = str2Int(temp);
    }else{
      temp = pdata.getProfile("MIN_SUP");
      min_sup_float = str2Float(temp);
    }

    temp = pdata.getProfile("NUMBER_OF_TRANS");
    transNumber = str2Int(temp);

    temp = pdata.getProfile("MIN_CONF");
    min_conf = str2Float(temp);

    temp = pdata.getProfile("TRANSID_FLD");
    transIDFld = temp.toString();

    temp = pdata.getProfile("TARGET_FLD");
    targetFld = temp.toString();

    temp = pdata.getProfile("TIME_FLD");
    timeFld = temp.toString();

    transNumber= getTransactionNumber(projectName, dataFile, transIDFld);
    if(min_sup_type == 1){
      min_sup = (int)(min_sup_float * (float)transNumber); //"%"
    }

    pdata.close();
  }

  int m_pbMin ;
  int m_pbMax ;
  static int m_pbValue = 0;
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
    }catch (IOException ce){
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

  public void start()  throws IOException {
    int num_trans = transNumber;
    int sup = min_sup;
    float conf = min_conf;
    String model = modelName;

    Hashtable largeHash = new Hashtable();

    try{
      //large 1-sequence를 구한다.
      SeqPattern sequen = new SeqPattern(1, sup, model, num_trans, projectName, dataFile, projectPath);
      Hashtable L1sequence = sequen.genL1Sequence(transIDFld, targetFld, timeFld);  //구해진 large 1-sequence의 수
      addHashtable(L1sequence, largeHash);
      int num_litems = L1sequence.size();

      m_pbValue = 52;

      int k = 1;
      LSitemset litemsetTmp = new LSitemset(k);

      //AprioriAll
      do{
        //sup, model, num_trans, projectName, dataFile 는 항상 같다. => static으로 바꾸기
        LSitemset litemset = new LSitemset(k, sup, model, num_trans, projectName, dataFile,projectPath);   //num_items, support
        if(k == 1) litemset.putLitem(num_litems);    //Candidate 1-Itemset을 구한다.
        else{
          num_litems = litemsetTmp.aprioriGen2(litemset);  //Candidate itemset을 생성한다.
          if(num_litems == 0) break;
          num_litems = litemset.genLitemset2();
          addHashtable(litemset.table, largeHash);
          if(num_litems == 0) break;
        }
        litemsetTmp = litemset;

        k++;    //break한거하고.. k++하고 상관있나...

        if(KeepRunning()){ //52-66
		  if(k <= 7) m_pbValue += 2;
          else return;   // KeepRunning 이 false
		}
      }while(num_litems != 0);

      m_pbValue = 68;

      CXMMetaDataSaver ruleMetaData = new CXMMetaDataSaver();
      ruleMetaData.setFileStatus(projectName,nextArcFile);

      CXMTableSaver ruleData = new CXMTableSaver();   //litemset data file
      ruleData.setFileStatus(projectName,nextArcFile);

      Vector maximal = sequen.maximalSequence(k-1, largeHash);  //68-74
      m_pbValue = 74;

      String litem = new String();
	  num_rows = 0;

      SequenRule.setParam(largeHash, conf, model, num_trans, projectName, dataFile, projectPath);

      int size = maximal.size();
      for(int j = 0; j < size; j++){
        litem = (String)maximal.elementAt(j);
        Integer litem_sup = (Integer)largeHash.get(litem);
        sup = litem_sup.intValue();
        int i = getNumItems(litem);
        if(i >= 2){ //litem이 2개 이상의 item로 이루어진 경우만
          SequenRule Lkset_rules = new SequenRule(litem, i, sup);
          Lkset_rules.gen_rule(i, ruleData);
        }
      }//end for

	  m_pbValue = 100;

	  ruleMetaData.setProfile("NUMBER_OF_ROWS",String.valueOf(num_rows));
      ruleMetaData.close();
	  ruleData.close();
      RuleComplete();
    }catch(IOException ex){
      System.out.println("파일 입출력 에러");
    }finally{}

	return;
  }

  public void fileUpload(String filename, byte[] buf){
    try{
      FileOutputStream fout=  new FileOutputStream(filename);
      ObjectOutputStream outStream = new ObjectOutputStream(fout);
      outStream.write(buf);
	  outStream.close();
	  fout.close();
    }catch(Exception e){
      e.printStackTrace();
    }
  }

  public String getProfile(String _ProjectName,String Metafile, String key){
    // IMPLEMENT:
    CXMMetaDataReader metadata = new CXMMetaDataReader();
    metadata.setFileStatus(_ProjectName,Metafile);

    String value = metadata.getProfile(key);
    metadata.close();

    if(value == null || value.length() == 0) value = "null";

    return value;
  }

  public String[] getProfiles(String _ProjectName,String Metafile,String key){
    // IMPLEMENT:
    CXMMetaDataReader metadata = new CXMMetaDataReader();
    metadata.setFileStatus(_ProjectName,Metafile);

    String[] values = metadata.getProfiles(key);
    metadata.close();

    if(values == null || values.length == 0) values[0] = "null";

    return values;
  }

  public void setProfile(String _ProjectName,String Metafile, String key, String value){
    // IMPLEMENT:
    CXMMetaDataSaver metadata = new CXMMetaDataSaver();
    metadata.setFileStatus(_ProjectName,Metafile);

	if(key.equals("PROJECT_PATH")) value = System.getProperty("minerdir") + "/xmminer/user/";

	metadata.setProfile(key,value);
    metadata.close();
  }

  public void setProfiles(String _ProjectName,String Metafile,String key, String[] value){
    // IMPLEMENT:
    CXMMetaDataSaver metadata = new CXMMetaDataSaver();
    metadata.setFileStatus(_ProjectName,Metafile);

    metadata.setProfiles(key,value);
    metadata.close();
  }

  public void setParameterFileName(String _ParameterFileName){
    ParameterFileName = _ParameterFileName;
  }

  public String getParameterFileName(){
    return ParameterFileName;
  }

  public void setProjectName(String _ProjectName,String NextArcfile){
    projectName = _ProjectName;
    nextArcFile = NextArcfile;
  }

  public int getTransNumber(){
    return transNumber;
  }

  public String getProjectName(){
    return projectName;
  }


  public int getTransactionNumber(String _ProjectName,String Arcfile,String transIDfld){
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

    Vector transaction;   //customer sequence
    int size; //하나의 transaction을 이루고 있는 item들의 수
    String transLine = new String();

    int[] colIndex = new int[1];  //1개의 column
    byte[] row, column;   
    CXMGetSequenceTransaction transData2 = new CXMGetSequenceTransaction();  //transaction data file     
    transData2.setFileStatus(_ProjectName, Arcfile,timeFld,transIDFld,targetFld);  
    CXMTableSaver transData3 = new CXMTableSaver();    
    transData3.setFileStatus(_ProjectName, modelName + "_seq_trans"); 

    m = 1;
    
    try{
      Enumeration hEum = colValues.keys();
      while(hEum.hasMoreElements()){
        colUniqueValue = (String) hEum.nextElement();              
        transaction = transData2.getSequenceTransaction((byte[])colValues.get(colUniqueValue));              
        size = transaction.size();
        transLine = new String("");
        for(int s = 0; s <size; s++) transLine += (String)transaction.elementAt(s) + ";";
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
      }//end while
    }catch(Exception cte){System.out.println("create_seq_rule_table_error="+cte.getMessage());}
  
    m_pbValue = 40;

    colValues.clear();
    transData3.close();
    transData2.close();

    CXMMetaDataSaver metaData = new CXMMetaDataSaver();
    metaData.setFileStatus(_ProjectName, modelName + "_seq_trans");
    metaData.setProfile("NUMBER_OF_ROWS", Integer.toString(n_trans)); //transaction 수
    metaData.setProfile("NUMBER_OF_COLUMNS", Integer.toString(1));
    metaData.setProfile("COLUMN_LIST", "trans");
    metaData.setProfile("COLUMN_INDEX", "1");
    metaData.setProfile("trans", "STRING^INFINITE^DISCRETE^NOMINAL^not_sorted^not_filtered^not_transformed^not_computed");
    metaData.close();

    return n_trans;
  }

  public void setSchema(String _ProjectName,String NextArcfile){
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
  }

  public String getIsRuleComplete(String _ProjectName,String _ParameterFile){
    try{
      CXMMetaDataReader ParameterMetaData = new CXMMetaDataReader();
      ParameterMetaData.setFileStatus(_ProjectName,_ParameterFile);
      IsRuleComplete = ParameterMetaData.getProfile("RULE");
      ParameterMetaData.close();
      if(IsRuleComplete == null) IsRuleComplete= "false";
      return IsRuleComplete;
    }catch(Exception e){
      return "false";
    }
  }

//  public static ServerMonitorPage monitor = null;
//  private void init() {
//    java.util.ResourceBundle res = java.util.ResourceBundle.getBundle("xmminer.xmserver.ServerResources");
//    String name = "XMBMNodeSequenceRule";
//    if (monitor == null) {
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