 
//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1999
//Author:       최대우
//Company:      전북대학교 산업공학과 지능정보시스템
//Description:  Your description
package xmminer.xmserver.xmgraph.xmmnode.xmmnodec45.rulegen;
import  xmminer.xmserver.xmgraph.xmmnode.xmmnodec45.treegen.*;
import xmminer.xmserver.xmgraph.xmmnode.xmmnodec45.rulegen.typedef.*;
import xmminer.xmlib.xmtable.*;
import xmminer.xmlib.xmcompute.*;

public class XMCGenLogs extends XMCTreeEvaluation
{
   public final static int    C_LOGITEMNO=1;       //특정한 칼럼을 사용하지 않을 경우
   public final static int    C_LOGFACT=2;
   XMCPR	Rule[];		        // production rules

   int	        NRules,		// number of production rules 
		RuleIndex[];	// index to production rules

   int	RuleSpace;	        // space currently allocated for rules 

   XMCRuleSet	PRSet[];	// set of rulesets 

   int	DefaultClass;	        // default class associated with ruleset

   boolean	SIGTEST;	// use Fisher's test in rule pruning

   double	SIGTHRESH=0.05,	// sig level used in rule pruning
	        REDUNDANCY=1.0,	// factor governing encoding tradeoff
		 	        // between rules and exceptions
	        AttTestBits;	// average bits to encode tested attribute
   double[]	BranchBits;	// ditto attribute value

   //double	LogItemNo;	// LogItemNo[i] = log2(i)
   //double	LogFact;	// LogFact[i] = log2(i!)


   CXMWeightTableSaver m_fLogItemNo_LogFact_Saver=null;
   CXMWeightTableManager m_fLogItemNo_LogFact_Manager=null;

//
//
//genlogs.c
//
//
////////////////////////////////////////////////////////////////////////////////
//
// 생성자
// public XMCGenLogs()
// public XMCGenLogs(String ProjectName, String Modelname)
// 
////////////////////////////////////////////////////////////////////////////////
  public XMCGenLogs()
  {

  }
  
  public XMCGenLogs(String ProjectName, String Modelname)
  {
        m_sProjectName = ProjectName;
        m_sModelName= Modelname;
  }


////////////////////////////////////////////////////////////////////////////////
//
// public void  GenerateLogs()
//
//  Set up the array LogItemNo to contain the logs of integers and
//  the array LogFact to contain logs of factorials (all to base 2)
//
////////////////////////////////////////////////////////////////////////////////

public void  GenerateLogs()

{
    int i;
    int m_iColIndex[];
    m_iColIndex =new int[2];
    m_iColIndex[0]=8;
    m_iColIndex[1]=8;

    String m_sLogItemNo_LogFactName=new String(m_sModelName+"LogItemNo_LogFact");
    m_fLogItemNo_LogFact_Saver = new CXMWeightTableSaver();
    m_fLogItemNo_LogFact_Saver.setFileStatus(m_sProjectName,m_sLogItemNo_LogFactName);
    m_fLogItemNo_LogFact_Saver.createWeightTable(m_iColIndex, MaxItem+100);
    m_fLogItemNo_LogFact_Saver.close();

    m_fLogItemNo_LogFact_Manager = new CXMWeightTableManager();
    //m_cRWTableManager.setFileStatus(m_sProjectName,m_sWeightFileName);
    m_fLogItemNo_LogFact_Manager.setFileStatus(m_sProjectName, m_sLogItemNo_LogFactName, m_iColIndex, MaxItem+100);


    //LogItemNo = double[MaxItem+100]; //(double *) malloc((MaxItem+100) * sizeof(double));
    //LogFact = double[MaxItem+100];   //(double *) malloc((MaxItem+100) * sizeof(double));
    m_iPbCount_num++;
    m_fLogItemNo_LogFact_Manager.setDouble(1,C_LOGITEMNO,-1E38); //LogItemNo[0] = -1E38;
    m_fLogItemNo_LogFact_Manager.setDouble(2,C_LOGITEMNO,0); //LogItemNo[1] = 0;
    m_fLogItemNo_LogFact_Manager.setDouble(1,C_LOGFACT,0);//LogFact[0] = LogFact[1] = 0;
    m_fLogItemNo_LogFact_Manager.setDouble(2,C_LOGFACT,0);
    double m_dLogItemNo=0.0,m_dLogFact=0.0;
    for(i=3;i<=MaxItem+100;i++)//    ForEach(i, 2, MaxItem+99)
    {
    	m_dLogItemNo=java.lang.Math.log((double)(i-1))/java.lang.Math.log(java.lang.Math.E);
	    m_fLogItemNo_LogFact_Manager.setDouble(i,C_LOGITEMNO,m_dLogItemNo/Log2); //[i] = m_dTemp / Log2;
        m_dLogFact=m_fLogItemNo_LogFact_Manager.getDouble(i-1,C_LOGFACT) + m_dLogItemNo/Log2;//LogFact[i] = LogFact[i-1] + LogItemNo[i];
		m_fLogItemNo_LogFact_Manager.setDouble(i,C_LOGFACT,m_dLogFact);
    }
}
public int ReadParameter_rule()
{
    try
    {
      int i;
      CXMMetaDataReader Rcmdr= new CXMMetaDataReader();
      Rcmdr.setFileStatus(m_sProjectName,m_sModelName+"_par");
      //PARAMETER SETTING(RULE)
      REDUNDANCY= (double) Integer.parseInt(Rcmdr.getProfile("REDUNDANCY"));
      SIGTHRESH= Double.parseDouble(Rcmdr.getProfile("SIGTHRESH"));
      //SIGTEST = Boolean.getBoolean(Rcmdr.getProfile("SIGTEST"));
      if(Rcmdr.getProfile("SIGTEST").equals("true"))SIGTEST=true;
	  else SIGTEST=false;
	  Rcmdr.close();

//      System.out.println("REDUNDANCY : "+REDUNDANCY+"  SIGTHRESH : "
 //                        +SIGTHRESH+" SIGTEST : "+ SIGTEST);

      return(0);
    }
      catch (Exception e) {
    	e.printStackTrace();
    	return(1);
    }
  }

/////////////////////////////////////////////////////////////////////////
//
//  End of Class
//
/////////////////////////////////////////////////////////////////////////
}
