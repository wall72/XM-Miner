package xmminer.xmserver.xmgraph.xmmnode.xmmnodec45;

//import xmminer.xmvbj.xmgraph.xmmnode.xmmnodec45.XMBMNodeC45POA;
import java.io.*;
import java.lang.*;

import xmminer.xmlib.xmtable.*;
import xmminer.xmlib.xmcompute.*;
import xmminer.xmserver.*;
import xmminer.xmserver.xmgraph.xmmnode.xmmnodec45.treegen.*;
import xmminer.xmserver.xmgraph.xmmnode.xmmnodec45.rulegen.*;

public class XMBMNodeC45{
  protected static final int XMC45_TREE_BEFORE  = 0x0000;
  protected static final int XMC45_TREE_LEARN   = 0x0011;
  protected static final int XMC45_TREE_TEST    = 0x0012;
  protected static final int XMC45_RULE_LEARN   = 0x0021;
  protected static final int XMC45_RULE_TEST    = 0x0022;
  String m_sProjectPass  = new String("");

  public XMBMNodeC45(){
    super();
	//init();
  }

  /////////////////////////////////////////////////////////////////////
  //
  // progressBar관련 함수및 변수
  //
  // variable  : int m_pbMin,m_pbMax,m_pbValue
  // function  : GetPBMin,SetPBMin,GetPBMax,SetPBMax,GetPBValue
  /////////////////////////////////////////////////////////////////////

  int m_pbMin;
  int m_pbMax;
  int m_pbValue;

  String ms_modelname;//,m_sSelectedLearnData,m_sSelectedTestData;
  boolean TreeGen,MethodLearn,MethodTest;
  int m_iMethodStatus=0;
  XMCTreeEvaluation m_cXMTree =null;
  XMCGenRules  m_cXMRule=null;

  boolean TreegenEnd=false;
  String m_sProjectName=null;

  boolean m_bStart=false;
  int m_iReturn_PbValue=0;

  public int GetPBMin(){    // must be called by client and only once!
    // IMPLEMENT:
    SetPBMin();
    m_iReturn_PbValue=0;
    return m_pbMin;
  }

  public void SetPBMin(){
    m_pbMin = 1;
  }

  public int GetPBMax(){    // must be called by client and only once!
    // IMPLEMENT:
    SetPBMax();

    return m_pbMax;
  }

  public void SetPBMax(){
    m_pbMax = 50;
  }

  public int GetPBValue(){
    if(!m_bStop && m_bStart){
      if(TreeGen){
        if(m_cXMTree !=null) m_iReturn_PbValue = m_pbValue + m_cXMTree.GetPBValue();
      }else{
        if(m_cXMRule!=null) m_iReturn_PbValue = m_pbValue + m_cXMRule.GetPBValue();
      }
	}else m_iReturn_PbValue = m_pbMin;

	return m_iReturn_PbValue;
  }

  /////////////////////////////////////////////////////////////////////
  //
  // run 관련 함수및 변수
  //
  // variable  : boolean m_bStop,m_bAlive
  // function  : PerformRunning,Run,KeepRunning,StopRunning,IsRunning
  /////////////////////////////////////////////////////////////////////

  boolean m_bStop;
  boolean m_bAlive;

  public int PerformRunning(){
    try{
      m_iMethodStatus=Getm_iMethodStatus(m_sProjectName,ms_modelname);
      ReadParameter(m_sProjectName,ms_modelname);
      int m_iResult=0;
	  m_bStart=true;
	  m_pbValue=0;
      if(TreeGen){
        if(MethodLearn && MethodTest){//Learn & Test
          m_pbValue=0;
	      m_iResult=Tree_Learn_Test(m_sProjectName,ms_modelname);
        }else if(MethodLearn){//Only Learn
          m_iResult=Tree_Learn(m_sProjectName,ms_modelname);
        }else if (MethodTest){//Only Test
          if(m_iMethodStatus>= XMC45_TREE_LEARN) m_iResult=Tree_Test(m_sProjectName,ms_modelname);
        }
      }else{ // rule generator
        if(MethodLearn && MethodTest){//Learn & Test
          m_iResult=Rule_Learn_Test(m_sProjectName,ms_modelname);
        }else if(MethodLearn){//Only Learn
          m_iResult=Rule_Learn(m_sProjectName,ms_modelname);
        }else if (MethodTest){//Only Test
          if(m_iMethodStatus>= XMC45_RULE_LEARN){
            m_iResult=Rule_Test(m_sProjectName,ms_modelname);
          }
        }
      }
      m_bStart=false;
	  return(m_iResult);
    }catch(Exception e){
      e.printStackTrace();
      return(1);
    }
  }

  public int Run(){
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
    m_bStop = true;
  }

  public boolean IsRunning(){
    return(m_bAlive);
  }

  //Method의 Status를 return
  public int Getm_iMethodStatus (java.lang.String ProjectName,java.lang.String filename){
    m_iMethodStatus=0;
    CXMMetaDataReader cmds = new CXMMetaDataReader();
    cmds.setFileStatus(ProjectName,filename+"_par");

    //칼럼의 수를 저장
    m_iMethodStatus= (Integer.parseInt(new String(cmds.getProfile("METHODSTATUS"))));
    cmds.close();
    return m_iMethodStatus;
  }

////////////////////////////////////////////////////////////////////////////////
//
// 트리나 룰을 생성하거나 테스트 하는 함수
// public int Tree_Learn_Test(String m_sProjectName,String ms_modelname)
// public int Tree_Learn(String m_sProjectName,String ms_modelname)
// public int Tree_Test(String m_sProjectName,String ms_modelname)
// public int Rule_Learn_Test(String m_sProjectName,String ms_modelname)
// public int Rule_Learn(String m_sProjectName,String ms_modelname)
// public int Rule_Test(String m_sProjectName,String ms_modelname)
////////////////////////////////////////////////////////////////////////////////

  public int Tree_Learn_Test(String m_sProjectName,String ms_modelname){
    try{
	  //XMCTreeEvaluation
	  if(m_cXMTree !=null) m_cXMTree =null;
	  m_cXMTree=new XMCTreeEvaluation(m_sProjectName,ms_modelname);
	  m_cXMTree.SetDataUsage(false);// for Learn
	  m_cXMTree.ReadParameter_Data();
	  m_cXMTree.MakeDataTable();
	  m_cXMTree.SettingValue();
	  m_cXMTree.ReadParameter_tree();
	  m_cXMTree.InitialiseTreeData();
	  m_cXMTree.InitialiseWeights();
	  XMCTree Raw = new XMCTree() ;
	  m_cXMTree.AllKnown = true;
	  m_pbValue=1;
	  m_cXMTree.SetPBInterVal(29);
	  Raw = m_cXMTree.FormTree(0, m_cXMTree.MaxItem);
	  m_pbValue=30;
	  m_cXMTree.SetPBInterVal(11);

	  if(m_cXMTree.Prune(Raw)){
        System.out.println(m_sProjectPass+m_sProjectName+"/data/"+ms_modelname+".tree");
        Raw.SaveTree(m_sProjectPass+m_sProjectName+"/data/"+ms_modelname+".tree");
	  }else{
        System.out.println(m_sProjectPass+m_sProjectName+"/data/"+ms_modelname+".tree");
	    Raw.SaveTree(m_sProjectPass+m_sProjectName+"/data/"+ms_modelname+".tree");
	  }

	  m_pbValue=40;
      m_cXMTree.SetPBInterVal(10);
      m_cXMTree.Evaluate(true,Raw);

      CXMMetaDataSaver Rcmdr= new CXMMetaDataSaver();
      Rcmdr.setFileStatus(m_sProjectName,ms_modelname+"_par");
      Rcmdr.setProfile("LEARNED_ARCNAME", m_cXMTree.m_sArcName);
      Rcmdr.close();

      m_cXMTree.MakeResultTable();
      m_pbValue=45;
      m_cXMTree.SetPBInterVal(5);
      m_cXMTree.SetDataUsage(true);// for Learn
      m_cXMTree.ReadParameter_Data();
      m_cXMTree.MakeDataTable();
      m_cXMTree.SettingValue();

      m_cXMTree.Evaluate(true,Raw);
      m_cXMTree.MakeResultTable();
      m_pbValue=50;
      m_cXMTree.SetPBInterVal(1);
      Rcmdr.setFileStatus(m_sProjectName,ms_modelname+"_par");
      Rcmdr.setProfile("METHODSTATUS", new Integer(XMC45_TREE_TEST).toString());
      Rcmdr.close();
	  return(0);
    }catch(Exception e){
      e.printStackTrace();
      return(1);
    }
}

  public int Tree_Learn(String m_sProjectName,String ms_modelname){
    try{
      //XMCTreeEvaluation

      if(m_cXMTree !=null) m_cXMTree = null;
      m_cXMTree=new XMCTreeEvaluation(m_sProjectName,ms_modelname);
      m_cXMTree.SetDataUsage(false);// for Learn
      m_cXMTree.ReadParameter_Data();
      m_cXMTree.MakeDataTable();
      m_cXMTree.SettingValue();
      m_cXMTree.ReadParameter_tree();
      m_cXMTree.InitialiseTreeData();
      m_cXMTree.InitialiseWeights();
      XMCTree Raw = new XMCTree() ;
      m_cXMTree.AllKnown = true;
      m_pbValue=1;
      m_cXMTree.SetPBInterVal(34);
      Raw = m_cXMTree.FormTree(0, m_cXMTree.MaxItem);
      m_pbValue=35;
      m_cXMTree.SetPBInterVal(10);
      if(m_cXMTree.Prune(Raw)){
		System.out.println(m_sProjectPass+m_sProjectName+"/data/"+ms_modelname+".tree");
		Raw.SaveTree(m_sProjectPass+m_sProjectName+"/data/"+ms_modelname+".tree");
      }else{
        System.out.println(m_sProjectPass+m_sProjectName+"/data/"+ms_modelname+".tree");
	    Raw.SaveTree(m_sProjectPass+m_sProjectName+"/data/"+ms_modelname+".tree");
	  }
	  m_pbValue=45;
	  m_cXMTree.SetPBInterVal(5);

	  m_cXMTree.Evaluate(true,Raw);

      CXMMetaDataSaver Rcmdr= new CXMMetaDataSaver();
      Rcmdr.setFileStatus(m_sProjectName,ms_modelname+"_par");
      Rcmdr.setProfile("METHODSTATUS",new Integer(XMC45_TREE_LEARN).toString());
      Rcmdr.setProfile("LEARNED_ARCNAME", m_cXMTree.m_sArcName);
      Rcmdr.close();
      m_cXMTree.MakeResultTable();
      m_pbValue=50;
      m_cXMTree.SetPBInterVal(1);

	  return 0;
    }catch(Exception e){
      e.printStackTrace();
      return(1);
    }
  }

  public int Tree_Test(String m_sProjectName,String ms_modelname){
    try{
	  //XMCTreeEvaluation
	  if(m_cXMTree !=null) m_cXMTree =null;
	  m_pbValue=0;

	  m_cXMTree=new XMCTreeEvaluation(m_sProjectName,ms_modelname);
	  m_cXMTree.SetPBInterVal(10);
	  m_cXMTree.SetDataUsage(true);// for Learn
	  m_cXMTree.ReadParameter_Data();
	  m_cXMTree.MakeDataTable();
	  m_cXMTree.SettingValue();
	  XMCTree Raw = new XMCTree((XMCData) m_cXMTree) ;

      System.out.println(m_sProjectPass+m_sProjectName+"/data/"+ms_modelname+".tree");
      Raw.GetTree(m_sProjectPass+m_sProjectName+"/data/"+ms_modelname+".tree");
      m_pbValue=10;
      m_cXMTree.SetPBInterVal(35);
      m_cXMTree.Evaluate(true,Raw);
      m_pbValue=45;
      m_cXMTree.SetPBInterVal(5);
      m_cXMTree.MakeResultTable();
      m_pbValue=50;
      m_cXMTree.SetPBInterVal(1);

      CXMMetaDataSaver Rcmdr= new CXMMetaDataSaver();
      Rcmdr.setFileStatus(m_sProjectName,ms_modelname+"_par");
      Rcmdr.setProfile("METHODSTATUS", new Integer(XMC45_TREE_TEST).toString());
      Rcmdr.close();
	  return 0;
    }catch(Exception e){
      e.printStackTrace();
      return(1);
    }
  }

  public int Rule_Learn_Test(String m_sProjectName,String ms_modelname){
    try{
      //XMCGenRules
      if(m_cXMRule !=null) m_cXMRule =null;
      m_cXMRule=new XMCGenRules(m_sProjectName,ms_modelname);
      m_cXMRule.SetDataUsage(false);// for Learn
      m_cXMRule.ReadParameter_Data();
      m_cXMRule.MakeDataTable();
      m_cXMRule.SettingValue();
      m_cXMRule.ReadParameter_tree();
      m_cXMRule.InitialiseTreeData();
      m_cXMRule.InitialiseWeights();
      XMCTree Raw = new XMCTree() ;
      m_cXMRule.AllKnown = true;
      m_pbValue=1;
      m_cXMRule.SetPBInterVal(19);
      Raw = m_cXMRule.FormTree(0, m_cXMRule.MaxItem);
      m_pbValue=20;
      m_cXMRule.SetPBInterVal(25);
      m_cXMRule.ReadParameter_rule();
      m_cXMRule.GenerateLogs();

	  //  Construct rules
      m_cXMRule.GenerateRules(Raw);
      //  Save current ruleset
      m_cXMRule.SaveRules(m_sProjectPass+m_sProjectName+"/data/"+ms_modelname+".rule");
      m_cXMRule.MakeRuleFile(m_sProjectName ,ms_modelname+"_rule");
      //  Evaluations
      m_cXMRule.EvaluateRulesets(false);
      m_pbValue=45;
      m_cXMRule.SetPBInterVal(2);
      m_cXMRule.MakeResultTable();
      m_pbValue=47;
      m_cXMRule.SetPBInterVal(3);

      CXMMetaDataSaver Rcmdr= new CXMMetaDataSaver();
      Rcmdr.setFileStatus(m_sProjectName,ms_modelname+"_par");
      Rcmdr.setProfile("LEARNED_ARCNAME", m_cXMRule.m_sArcName);
      Rcmdr.close();

      m_cXMRule.SetDataUsage(true);// for Learn
      m_cXMRule.ReadParameter_Data();
      m_cXMRule.MakeDataTable();
      m_cXMRule.SettingValue();
      m_cXMRule.EvaluateRulesets(false);
      m_cXMRule.MakeResultTable();
      m_pbValue=50;
      m_cXMRule.SetPBInterVal(1);
      Rcmdr.setFileStatus(m_sProjectName,ms_modelname+"_par");
      Rcmdr.setProfile("METHODSTATUS", new Integer(XMC45_RULE_TEST).toString());
      Rcmdr.close();
      return 0;
    }catch(Exception e){
      e.printStackTrace();
      return(1);
    }
  }

  public int Rule_Learn(String m_sProjectName,String ms_modelname){
    try{
      //XMCGenRules
      if(m_cXMRule !=null) m_cXMRule =null;
      m_cXMRule=new XMCGenRules(m_sProjectName,ms_modelname);
      m_cXMRule.SetDataUsage(false);// for Learn
      m_cXMRule.ReadParameter_Data();
      m_cXMRule.MakeDataTable();
      m_cXMRule.SettingValue();
      m_cXMRule.ReadParameter_tree();
      m_cXMRule.InitialiseTreeData();
      m_cXMRule.InitialiseWeights();
      XMCTree Raw = new XMCTree() ;
      m_cXMRule.AllKnown = true;
      m_pbValue=1;
      m_cXMRule.SetPBInterVal(24);
      Raw = m_cXMRule.FormTree(0, m_cXMRule.MaxItem);
      m_pbValue=25;
      m_cXMRule.SetPBInterVal(15);
      m_cXMRule.ReadParameter_rule();
      m_cXMRule.GenerateLogs();

	  //  Construct rules
      m_cXMRule.GenerateRules(Raw);
      m_pbValue=40;
      m_cXMRule.SetPBInterVal(8);
      //  Save current ruleset
      m_cXMRule.PrintIndexedRules();
      m_cXMRule.SaveRules(m_sProjectPass+m_sProjectName+"/data/"+ms_modelname+".rule");
      m_cXMRule.MakeRuleFile(m_sProjectName ,ms_modelname+"_rule");
      //  Evaluations
      m_cXMRule.EvaluateRulesets(false);
      m_pbValue=48;
      m_cXMRule.SetPBInterVal(2);

      CXMMetaDataSaver Rcmdr= new CXMMetaDataSaver();
      Rcmdr.setFileStatus(m_sProjectName,ms_modelname+"_par");
      Rcmdr.setProfile("METHODSTATUS", new Integer(XMC45_RULE_LEARN).toString());
      Rcmdr.setProfile("LEARNED_ARCNAME", m_cXMRule.m_sArcName);
      Rcmdr.close();

      m_cXMRule.MakeResultTable();
      m_pbValue=50;
      m_cXMRule.SetPBInterVal(1);
      return 1;
    }catch(Exception e){
      e.printStackTrace();
      return(1);
    }
  }

  public int Rule_Test(String m_sProjectName,String ms_modelname){
    try{
      //XMCGenRules
      if(m_cXMRule !=null) m_cXMRule =null;
      m_pbValue=1;

      m_cXMRule=new XMCGenRules(m_sProjectName,ms_modelname);
      m_cXMRule.SetPBInterVal(4);
      m_cXMRule.SetDataUsage(true);// for Learn
      m_cXMRule.ReadParameter_Data();
      m_cXMRule.MakeDataTable();
      m_cXMRule.SettingValue();
      m_cXMRule.GetRules(m_sProjectPass+m_sProjectName+"/data/"+ms_modelname+".rule");
      m_pbValue=5;
      m_cXMRule.SetPBInterVal(35);
      m_cXMRule.EvaluateRulesets(true);
      m_pbValue=40;
      m_cXMRule.SetPBInterVal(10);
      CXMMetaDataSaver Rcmdr= new CXMMetaDataSaver();
      Rcmdr.setFileStatus(m_sProjectName,ms_modelname+"_par");
      Rcmdr.setProfile("METHODSTATUS", new Integer(XMC45_RULE_TEST).toString());
      Rcmdr.close();
      m_cXMRule.MakeResultTable();
      m_pbValue=50;
      m_cXMRule.SetPBInterVal(1);
      return(1);
    }catch(Exception e){
      e.printStackTrace();
      return(1);
    }
  }

  ////////////////////////////////////////////////////////////////////////////////
  //
  // MetaFile을 생성하는 함수
  //
  // Write_setFileStatus(String MetaFileName)
  ////////////////////////////////////////////////////////////////////////////////
  CXMMetaDataSaver cmds = null;
  public void Write_setFileStatus (java.lang.String ProjectName,java.lang.String MetaFileName){
    cmds = new CXMMetaDataSaver();
    cmds.setFileStatus(ProjectName,MetaFileName);
  }

  public void Write_setProfile (java.lang.String m_sKey, java.lang.String m_sValue){
	if(m_sKey.equals("PROJECT_PASS")) m_sValue = System.getProperty("minerdir") + "/xmminer/user/";
    if(cmds!=null) cmds.setProfile(m_sKey,new String(m_sValue) );
  }

  public void Write_setProfiles (java.lang.String m_sKey, java.lang.String[] m_sValues){
    if(cmds!=null) cmds.setProfiles(m_sKey,m_sValues);
  }

  public void Write_close(){
    if(cmds!=null) cmds.close();
  }


  ////////////////////////////////////////////////////////////////////////////////
  //
  // MetaFile을 읽어오는 함수
  //
  // Read_setFileStatus(String MetaFileName)
  ////////////////////////////////////////////////////////////////////////////////
  CXMMetaDataReader cmdr= null;
  public void Read_setFileStatus (java.lang.String ProjectName,java.lang.String MetaFileName){
    cmdr = new CXMMetaDataReader();
    cmdr.setFileStatus(ProjectName,MetaFileName);
  }

  public java.lang.String Read_getProfile (java.lang.String m_sKey){
    String m_sValue=null,m_sValueTemp=null;
    m_sValueTemp=cmdr.getProfile(m_sKey);
    if(m_sValueTemp==null){
      m_sValue= new String("null");
    }else{
      m_sValue= new String(m_sValueTemp);
    }
	return (java.lang.String)m_sValue;
  }

  public java.lang.String[] Read_getProfiles (java.lang.String m_sKey){
    String[] m_sValues = cmdr.getProfiles(m_sKey);
    return m_sValues;
  }

  public void Read_close(){
    cmdr.close();
  }

  private int ReadParameter(String m_sProjectName,String ms_modelname){
    try{
      int i;
      CXMMetaDataReader Rcmdr= new CXMMetaDataReader();
      Rcmdr.setFileStatus(m_sProjectName,ms_modelname+"_par");

      m_sProjectPass= new String( Rcmdr.getProfile("PROJECT_PASS"));

	  //METHOD SETTING
	  if(Rcmdr.getProfile("TREEGEN").equals("true"))TreeGen=true;
	  else TreeGen=false;

	  if(Rcmdr.getProfile("METHODLEARN").equals("true"))MethodLearn=true;
	  else MethodLearn=false;

	  if(Rcmdr.getProfile("METHODTEST").equals("true"))MethodTest=true;
	  else MethodTest=false;
      Rcmdr.close();

      return(0);
    }catch(Exception e){
      e.printStackTrace();
      return(1);
    }
  }


  public void Setms_modelname(java.lang.String modelname){
    ms_modelname = new String(modelname);
  }

  public void Setm_sProjectName (java.lang.String ProjectName) {
    // IMPLEMENT:
    m_sProjectName = new String(ProjectName);
  }

  ////////////////////////////////////////////////////////////////////////////////
  //
  // Tree Visualization 관련 함수
  //
  // public int Tree_Test(String m_sProjectName,String ms_modelname)
  ////////////////////////////////////////////////////////////////////////////////
  XMCTreeView m_cTreeView = null;
  public int ForViewTree(String m_sProjectName,String ms_modelname){
    try{
      XMCData m_cXMTree=new XMCData(m_sProjectName,ms_modelname);
      m_cXMTree.SetDataUsage(false);// for Learn
      m_cXMTree.ReadParameter_Data();
      m_cXMTree.MakeDataTable();
      m_cXMTree.SettingValue();
      XMCTree Raw = new XMCTree((XMCData) m_cXMTree);
	  System.out.println("트리를 출력합니다.");
	  System.out.println(m_sProjectPass+m_sProjectName+"/data/"+ms_modelname+".tree");

      Raw.GetTree(m_sProjectPass+m_sProjectName+"/data/"+ms_modelname+".tree");
      m_cTreeView= new  XMCTreeView( m_cXMTree,Raw);
      return 0;
    }catch(Exception e){
  	  e.printStackTrace();
   	  return(1);
    }
  }

  ////////////////////////////////////////////////////////////////////////////////
  //
  // Tree Visualization 관련 값을 client로 보내기 위한 함수
  //
  // public int GetTreeSize()
  // public int GetIndentNum(int index)
  // public String GetTreeString(int index)
  ////////////////////////////////////////////////////////////////////////////////
  public int GetTreeSize(){
    m_cTreeView=null;
	if(m_cTreeView==null) ForViewTree(m_sProjectName, ms_modelname);
    return m_cTreeView.GetStringSize();
  }

  public int GetIndentNum(int index){
    if(m_cTreeView==null) ForViewTree(m_sProjectName, ms_modelname);
    return m_cTreeView.GetIndentNum(index);
  }

  public java.lang.String GetTreeString(int index){
    if(m_cTreeView==null) ForViewTree(m_sProjectName, ms_modelname);
    return m_cTreeView.GetTreeString(index);
  }

//  public static ServerMonitorPage monitor = null;
//  private void init() {
//    java.util.ResourceBundle res = java.util.ResourceBundle.getBundle("xmminer.xmserver.ServerResources");
//    String name = "XMBMNodeC45";
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
}//end of class