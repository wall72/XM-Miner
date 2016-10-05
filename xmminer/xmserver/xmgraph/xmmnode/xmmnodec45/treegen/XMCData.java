
//Title:        C45_Classifier
//Version:
//Copyright:    Copyright (c) 1999
//Author:       최대우
//Company:      전북대학교 산업공학과 지능정보시스템
//Description:   data를 일고 저장하기 위한 클래스 C45의 최상위 클래스

////////////////////////////////////////////////////////////////////////////////
//
//
//
////////////////////////////////////////////////////////////////////////////////

package xmminer.xmserver.xmgraph.xmmnode.xmmnodec45.treegen;

import java.util.*;
import java.io.*;
import java.lang.*;
import xmminer.xmlib.xmtable.*;
import xmminer.xmlib.xmcompute.*;

public class XMCData
{
  public final static int    IGNORE1=1;       //특정한 칼럼을 사용하지 않을 경우
  public final static int    DISCRETE=2;      //칼럼의 Data Type이 숫자인지 문자인지 구별
  public final static int    CONTINUOUS=3;    //칼럼의 Data Type이 숫자인지 문자인지 구별
  public final static double Unknown= -999;   //data값을 모를 경우 -999로 할당
  public final static int    LEARN=101;       //data가 tree을 생성하기 위한 data인 경우
  public final static int    TEST=102;        //data가 tree을 테스트하기 위한 data인 경우
  public final static int    EXCUTION=103;    //실행을 위한 data인 경우

  public final static int    TREE_BEFORE=1000; //Tree를 생성하기 전
  public final static int    TREE_LEARN=1001;  //Tree가 생성되고, 파일에 저장된 상태
  public final static int    TREE_LEARN_WITH_PRUNE=1002;  ////Tree가 생성되고, pruning되어 파일에 저장된 상태
  public final static int    RULE_BEFORE=2000;
  public final static int    RULE_LEARN_MODE=2001;
  public final static int    RULE_LEARN_WITH_PRUNE=2002;
  public static final String NOINPUTDATA                      = new String("NO INPUT DATA");
  public static final String NOOUTPUTDATA                     = new String("NO OUTPUT DATA");

  //meta 파일의 정보를 저장하기 위한 부분
  public  int		   MaxAtt,          // input 칼럼의 수를 저장
                   MaxClass,        // output 칼럼의 data set의 크기
                   MaxDiscrVal;     // 각 칼럼의 값중에서 가장 큰 data set 값
                                     // 가장 큰 값으로 배열에 메모리를 할당하기 위해
  public  int      MaxAttVal[];      //각 칼럼의 data set의 크기를 저장
  public  int		   MaxItem;          //CXMTable의 row number
  public  int      SpecialStatus[];  //각 칼럼의 data type :DISCRETE,CONTINUOUS,IGNORE1
  public  String   ClassName[];      //OUPUT 칼럼의 data set를 저장
  public  String   ClassColName;     //OUPUT 칼럼의 이름을 저장
  public  String   AttName[];        //INPUT 칼럼의 이름을 저장
  public  String   AttValName[][];   //INPUT 칼럼의 data set를 저장

  public  int      DataMode=LEARN;           //data가 트리을 생성하기 위한 것인지 아니면
                                             //테스트를 위한 데이터 인지 를 판단
  public  int      TreeMode=TREE_BEFORE;     //data가 트리을 생성하기 위한 것인지 아니면
                                             //테스트를 위한 데이터 인지 를 판단

  public String m_sProjectName=new String("testproject1");
  public String m_sModelName=new String("UtitledModel");
  public String m_sArcName;
  public String m_sLearnedArcName;
  public String m_sOutputArcName;
  public String[] m_sColumnList;
  public boolean m_bTestFlag;
  public int    m_iClassColumn;

  CXMC45TableSaver CDataSaver = null;
  CXMC45TableManager m_cC45DataTableManager=null;



////////////////////////////////////////////////////////////////////////////////
//
// 생성자
// public XMCData()
// public XMCData(String ProjectName, String Modelname)
// public XMCData(String ProjectName, String Modelname, String ArcName,String[] ColumnList,boolean test_flag)
//
////////////////////////////////////////////////////////////////////////////////


  public XMCData()
  {

  }
  public XMCData(String ProjectName, String Modelname)
  {
        m_sProjectName = ProjectName;
        m_sModelName= Modelname;
  }
  public XMCData(String ProjectName, String Modelname, String ArcName,String[] ColumnList,boolean test_flag)
  {

        m_sProjectName = ProjectName;
        m_sModelName= Modelname;
        m_sArcName=ArcName;
        m_sColumnList=ColumnList;
        m_bTestFlag=test_flag;
  }

  public void SetXMCData(String ProjectName, String Modelname, String ArcName,String[] ColumnList,boolean test_flag)
  {

        m_sProjectName = ProjectName;
        m_sModelName= Modelname;
        m_sArcName=ArcName;
        m_sColumnList=ColumnList;
        m_bTestFlag=test_flag;
  }
  public void SetDataUsage(boolean test_flag)
  {
       m_bTestFlag=test_flag;
  }



  public int ReadParameter_Data()//String m_sProjectName,String ms_modelname)
  {
    try
    {
      int i;
      CXMMetaDataReader Rcmdr= new CXMMetaDataReader();
      Rcmdr.setFileStatus(m_sProjectName,m_sModelName+"_par");
      if(!m_bTestFlag)
      {
          m_sArcName = new String(Rcmdr.getProfile("LEARNDATA"));
          m_sOutputArcName= new String(Rcmdr.getProfile("LEARNRESULT"));
      }
      else
      {
          m_sArcName= new String(Rcmdr.getProfile("TESTDATA"));
          m_sLearnedArcName= new String(Rcmdr.getProfile("LEARNED_ARCNAME"));
          m_sOutputArcName= new String(Rcmdr.getProfile("TESTRESULT"));
      }


      m_sColumnList=Rcmdr.getProfiles("COLUMN_LIST");

      Rcmdr.close();

      System.out.println( "m_sArcName  :"+m_sArcName);
      for(i=0;i<m_sColumnList.length;i++)
      {
          System.out.println( i+"'s cloumn  :"+m_sColumnList[i]);
      }
      
      return(0);
    }
      catch (Exception e) {
    	e.printStackTrace();
    	return(1);
    }
  }



  public void MakeDataTable()
  {
      CDataSaver = new CXMC45TableSaver();
      CDataSaver.setFileStatus(m_sProjectName, m_sArcName,m_sModelName, m_bTestFlag);
      if(m_bTestFlag)CDataSaver.setLearnedArcName(m_sLearnedArcName);
      CDataSaver.createC45Table(m_sColumnList);
      m_cC45DataTableManager = new  CXMC45TableManager();
      m_cC45DataTableManager.setFileStatus(m_sProjectName, m_sArcName,m_sModelName, m_bTestFlag);
  }

  public void SettingValue()
  {
      int i;
	  if( CDataSaver==null)  MakeDataTable();
      MaxAtt =  m_sColumnList.length-2;
      MaxItem = CDataSaver.GetMaxItem()-1;
      SpecialStatus= CDataSaver.GetSpecialStatus();
      MaxAttVal= CDataSaver.GetMaxAttVal();
      MaxClass= MaxAttVal[MaxAtt+1];
      for(i=0;i<SpecialStatus.length;i++)
	        System.out.println("SpecialStatus"+i+"="+SpecialStatus[i]);   
		 
	  System.out.println("MaxAtt="+MaxAtt+" MaxItem="+MaxItem+ "MaxAttVal="+MaxAttVal);
      ClassColName= new String(m_sColumnList[m_sColumnList.length-1]);

      AttName= new String[MaxAtt+2];
      MaxDiscrVal=-1;
      m_iClassColumn=MaxAtt+2;
      for(i=0;i<=MaxAtt;i++)
      {
          AttName[i]=new String(m_sColumnList[i]);
          if( MaxAttVal[i]>=  MaxDiscrVal)   MaxDiscrVal=MaxAttVal[i];
          System.out.println("MaxAttVal["+i+"]="+MaxAttVal[i]);
      }
      System.out.println("MaxAtt="+MaxAtt+" MaxItem="+MaxItem+ " m_iClassColumn="+m_iClassColumn+ "MaxClass="+MaxClass);
      CDataSaver.close();


  }

public String GetAttValName(int m_iAtt,int m_iAttVal)
{
    //System.out.println("GetAttValName#1"+ " m_iAtt   "+AttName[m_iAtt]+" m_iAttVal"+m_iAttVal+"  m_sLearnedArcName="+m_sLearnedArcName);
    String m_sReturn=null;
    CXMC45TransValueReader m_cTransValueReader = new CXMC45TransValueReader();
    //System.out.println("GetAttValName#2"+ " m_iAtt   "+AttName[m_iAtt]+" m_iAttVal"+m_iAttVal);
    if(m_bTestFlag)  m_cTransValueReader.setLearnedArcName(m_sLearnedArcName);
    m_cTransValueReader.setFileStatus(m_sProjectName,m_sArcName,m_sModelName,AttName[m_iAtt],m_bTestFlag);
    //System.out.println("GetAttValName#3"+ " m_iAtt   "+AttName[m_iAtt]+" m_iAttVal"+m_iAttVal);

	   m_sReturn=m_cTransValueReader.getTransValue(m_iAttVal);
     //System.out.println("GetAttValName#4"+ " m_iAtt   "+AttName[m_iAtt]+" m_iAttVal"+m_iAttVal);
	if(m_sReturn==null)
    m_sReturn= new String("null");
  //System.out.println("GetAttValName#5"+ " m_sReturn=   "+m_sReturn+" m_iAttVal"+m_iAttVal);
	m_cTransValueReader.close();
    return m_sReturn;
}
public String GetClassValName(int m_iAttVal)
{
    //System.out.println("GetClassValName#1"+" m_iAttVal"+m_iAttVal+"   ClassColName="+ClassColName+"  m_sLearnedArcName="+m_sLearnedArcName);
    String m_sReturn=null;
    CXMC45TransValueReader m_cTransValueReader = new CXMC45TransValueReader();
    if(m_bTestFlag)  m_cTransValueReader.setLearnedArcName(m_sLearnedArcName);
    //System.out.println("GetClassValName#2"+" m_iAttVal"+m_iAttVal+"   ClassColName="+ClassColName);
    m_cTransValueReader.setFileStatus(m_sProjectName,m_sArcName,m_sModelName,ClassColName,m_bTestFlag);
    //System.out.println("GetClassValName#3"+" m_iAttVal"+m_iAttVal+"   ClassColName="+ClassColName);


	  m_sReturn=m_cTransValueReader.getTransValue(m_iAttVal);
    //System.out.println("GetClassValName#4"+" m_sReturn="+m_sReturn+"   ClassColName="+ClassColName);
	  if(m_sReturn==null)
      m_sReturn= new String("null");
	    m_cTransValueReader.close();
    return m_sReturn;
}

public void MakeResultTable()
{
//      System.out.println("MakeResultTable in  #1");
      CXMC45ResultFileSaver CResultSaver = new CXMC45ResultFileSaver();
//      System.out.println("MakeResultTable in  #2");
      CResultSaver.setFileStatus(m_sProjectName,m_sArcName,m_sModelName,m_bTestFlag);
//      System.out.println("MakeResultTable in  #3");
      if(m_bTestFlag)CResultSaver.setLearnedArcName(m_sLearnedArcName);
//      System.out.println("MakeResultTable in  #4");
      CResultSaver.setTrainingColumn(ClassColName);
//      System.out.println("MakeResultTable in  #5");
      CResultSaver.createResultFile();
//      System.out.println("MakeResultTable in  #6");
      CResultSaver.close();

//      System.out.println("MakeResultTable in  #7");
      CXMC45OutputTableSaver OutputTableSaver= new  CXMC45OutputTableSaver();
//      System.out.println("MakeResultTable in  #8");
      OutputTableSaver.setFileStatus(m_sProjectName,m_sArcName,m_sModelName,m_sOutputArcName,m_bTestFlag);
//      System.out.println("MakeResultTable in  #9");
      OutputTableSaver.createC45OutputTable();
//      System.out.println("MakeResultTable in  #10");
      OutputTableSaver.close();
//      System.out.println("MakeResultTable in  #11");
//      readData();
}

void readData()
{

    CXMTableReader ctr = new CXMTableReader();
    ctr.setFileStatus(m_sProjectName,m_sOutputArcName);
    System.out.println(  "m_sProjectName="+m_sProjectName+"   m_sOutputArcName="+m_sOutputArcName);
    for (int i=0; i<14; i++)
    {
       System.out.println(ctr.getStringInColumn(i+1,1));
       System.out.println(ctr.getIntInColumn(i+1,2));
       System.out.println(ctr.getIntInColumn(i+1,3));
       System.out.println(ctr.getStringInColumn(i+1,4));
       System.out.println(ctr.getStringInColumn(i+1,5));
       System.out.println(ctr.getStringInColumn(i+1,6));
    }

}
public int m_iPbCount_num=0;
int m_iPbInterVal=1;
int m_idivice=1;
int m_iOldReturn=0;

public int GetPBValue()
{
  int m_iReturn=0;
  m_iReturn=m_iPbCount_num/m_idivice;

  int temp=(m_iPbInterVal*5-10>0?m_iPbInterVal*5-10-10:0);
  if(m_iReturn>temp)
  {
      m_iReturn=m_iOldReturn;
	  m_idivice+=100;
  }
  else
  {
     if(m_iOldReturn>m_iReturn)
	   m_iReturn=m_iOldReturn;
     else
	   m_iOldReturn=m_iReturn;  
  }
   return m_iReturn;
}

public void SetPBInterVal(int interval)
{
   m_iPbCount_num=0;
   m_iPbInterVal=interval;
   m_idivice=1000;
   m_iOldReturn=0;
}

//end of class
}