package xmminer.xmserver.xmgraph.xmmnode.xmmnodeneuralnetwork;

import xmminer.xmserver.xmgraph.xmmnode.xmmnodeneuralnetwork.*;
import xmminer.xmlib.xmtable.*;
import xmminer.xmlib.xmcompute.*;


import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.lang.*;


public class XMNNData {

public String project;
public String projectpath;
public String ParameterFileName;

public String ms_networkname ;  //Model 명...
 
public boolean MethodLearn ; // True :Learn, False :
public boolean MethodTest ; // True :Test, False :

public String m_sSelectedLearnData  ;
public String m_sSelectedLearnOutput  ;

  //TEST DATA INOUT SELECTION
public  String m_sSelectedTestData  ;
public  String m_sSelectedTestOutput ;
  
  //*******************************
  //PARAMETER SETTING(전략)
  //*******************************
public   double m_ErrorCriteria ;
public   int m_EndingEpoch ;

public   double m_LearningRate ;
public   double m_MomentumRate ;

  //*******************************
  //PARAMETER SETTING(토폴로지)
  //*******************************
public   String [] m_HiddenNode  ;
public   int   m_HiddenLayerNum  ;
  //*******************************
  //COLUMN SELECTION
  //*******************************
public 	String[] InputColumnList   ;
public 	String[] OutputColumnList  ;
public 	String[] InputColumnType   ;
public 	String[] OutputColumnType  ;
public 	String[] InputTransMethod  ;
public  String[] OutputTransMethod ;

public  int[]  iInputTransMethod;
public  int[]  iOutputTransMethod;

  public XMNNData(String _projectName, String _parameterFile) {
    ParameterFileName = _parameterFile;
    project = _projectName;
	setParameter();
   }

  public void setParameter(){
   	 
	CXMMetaDataReader pdata = new CXMMetaDataReader();
	pdata.setFileStatus(project,ParameterFileName);

    String temp ;
    temp  =  pdata.getProfile("PROJECT_NAME");
	project = temp.toString();

    temp  =  pdata.getProfile("PROJECT_PATH");
	projectpath = temp.toString();

	temp  = pdata.getProfile("NETWORK_NAME");  
	ms_networkname		= temp.toString();

    temp  = pdata.getProfile("METHODLEARN");
    if (temp.equals("true")) MethodLearn = true;
    else MethodLearn = false;

	temp  = pdata.getProfile("METHODTEST");
    if (temp.equals("true")) MethodTest = true;
    else MethodTest = false;

	temp  = pdata.getProfile("LEARNDATA");
	 m_sSelectedLearnData  = temp.toString();

	temp  = pdata.getProfile("LEARNRESULT");
    m_sSelectedLearnOutput  = temp.toString();

	temp  = pdata.getProfile("TESTDATA");
     m_sSelectedTestData  = temp.toString();

	temp  = pdata.getProfile("TESTRESULT");
     m_sSelectedTestOutput 	= temp.toString();

	temp  = pdata.getProfile("ERRORCRITERIA");
	m_ErrorCriteria	= str2Double(temp);

	temp  = pdata.getProfile("ENDINGEPOCH");
	m_EndingEpoch = str2Int(temp);

	temp  = pdata.getProfile("LEARNINGRATE");
	m_LearningRate 	= str2Double(temp);

	temp  = pdata.getProfile("MOMENTUMRATE");
	m_MomentumRate 	= str2Double(temp);

	temp  = pdata.getProfile("HIDDENLAYERNUM");
	m_HiddenLayerNum = str2Int(temp);

	m_HiddenNode       = pdata.getProfiles("HIDDENNODE");

	InputColumnList    = pdata.getProfiles("INPUT_COLUMN_LIST");
	OutputColumnList   = pdata.getProfiles("OUTPUT_COLUMN_LIST");
	InputColumnType    = pdata.getProfiles("INPUT_COLUMN_TYPE");
	OutputColumnType   = pdata.getProfiles("OUTPUT_COLUMN_TYPE");
	InputTransMethod   = pdata.getProfiles("INPUT_TRANS_METHOD");
	OutputTransMethod  = pdata.getProfiles("OUTPUT_TRANS_METHOD");

	pdata.close();
	int val;
	iInputTransMethod  = new int[InputTransMethod.length];
    iOutputTransMethod = new int[OutputTransMethod.length];

    for(int i=0;i < InputTransMethod.length ; i ++){
      val = category(InputTransMethod[i]);    
      iInputTransMethod[i] =  val;
      }
   for(int i=0;i < OutputTransMethod.length ; i ++){
      val = category(OutputTransMethod[i]);    
      iOutputTransMethod[i] =  val;
      }
   }

//  
private int category(String category){
  String[] categories = { "One of N Codes","Tran Binary","NMapping", "Boolean", "Normalize" };
//  String[] categories = { "One of N Codes","Boolean", "Normalize" };
  int i;
  for( i=0;i < 5 ;i ++)
	 if(categories[i].equals(category))
		  break;
  return i+1;
}


public void setLearningData()
{
  CXMMetaDataReader ctr = new CXMMetaDataReader();
  ctr.setFileStatus(project,m_sSelectedLearnData);
  ctr.close();   
}

public void setTestData()
{
  CXMMetaDataReader ctr = new CXMMetaDataReader();
  ctr.setFileStatus(project,m_sSelectedTestData);
  ctr.close();   
}

private int str2Int(String str)
    {   Integer i = new Integer(str);
       	return i.intValue();
    }

private float str2Float(String str)
    {   Float f = new Float(str);
       	return f.floatValue();
    }

private double str2Double(String str)
    {
        Double d = new Double(str);
       	return d.doubleValue();
    }

private long  str2Long(String str)
    {
        Long l = new Long(str);
       	return l.longValue();
    }

}
