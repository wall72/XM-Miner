package xmminer.xmserver.xmgraph.xmmnode.xmmnodeneuralnetwork;

import xmminer.xmserver.*;
import xmminer.xmserver.xmgraph.*;
import xmminer.xmserver.xmgraph.xmmnode.*;
import xmminer.xmserver.xmgraph.xmmnode.xmmnodeneuralnetwork.*;
import xmminer.xmlib.xmtable.*;
import xmminer.xmlib.xmcompute.*;

//import xmminer.xmvbj.xmgraph.xmmnode.xmmnodeneuralnetwork.XMBMNodeNeuralNetworkPOA;


import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
import java.lang.*;


public class XMBMNodeNeuralNetwork{

 String projectpath;
 String project;
 String ParameterFileName;

 String nextArcFile;

  public XMBMNodeNeuralNetwork(){
      super();
//	init();
   }

	private int str2Int(String str)
    {
        Integer i = new Integer(str);
       	return i.intValue();
    }

    private float str2Float(String str)
    {
        Float f = new Float(str);
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

 private int getMaxValue()
 { 
   int Learn_rows,Test_rows;
   int Epoche;
   String sLearn;
   String sTest;
   CXMMetaDataReader cmr = new CXMMetaDataReader();
   cmr.setFileStatus(project,ParameterFileName);
   Learn_rows   = str2Int(cmr.getProfile("NUMBER_OF_LEARN_DATA_SET"));
   Test_rows    = str2Int(cmr.getProfile("NUMBER_OF_TEST_DATA_SET"));

   Epoche = str2Int(cmr.getProfile("ENDINGEPOCH"));
   sLearn = cmr.getProfile("METHODLEARN");
   sTest  = cmr.getProfile("METHODTEST");

   cmr.close();   
   if (sLearn.equals("true") && sTest.equals("true"))
      max = (Epoche+1) * Learn_rows + Test_rows;
   else if (!sLearn.equals("true") && sTest.equals("true"))
      max =  Test_rows;
   else if(sLearn.equals("true") && !sTest.equals("true"))
      max = (Epoche+1) * Learn_rows;

   return max;

 }
  static int pbValue = 0;
  int m_pbMin ;
  int m_pbMax ;
  int m_pbValue=0;
  int max ;
  int min = 1;

  public int GetPBMin () {    // must be called by client and only once!
    // IMPLEMENT:
    SetPBMin();

    return m_pbMin;
  }

  public void SetPBMin () {
    m_pbMin = min;
  }

  public int GetPBMax () {    // must be called by client and only once!
    // IMPLEMENT:
    SetPBMax();

    return m_pbMax;
  }

  
  public void SetPBMax () {
      m_pbMax= getMaxValue();
  }

  public int GetPBValue () {
    // IMPLEMENT:
    return m_pbValue;
  }


  boolean m_bStop;
  boolean m_bAlive;

  public int PerformRunning ()
  { 
	try{ 
	 pbValue = 0;
     XMNNData NNData = new XMNNData(project,ParameterFileName);
	 XMNeuralNetwork neural = new XMNeuralNetwork(NNData,this);
     if(NNData.MethodLearn && !NNData.MethodTest )
	    neural.Learn();  	
     else if (!NNData.MethodLearn && NNData.MethodTest )
	   	    neural.Test();   
	 else if (NNData.MethodLearn && NNData.MethodTest)
	   {   neural.Learn();
		   neural.Test();
		}
	 return(0);
	 }
	catch (Exception e) {
    	e.printStackTrace();
    	System.out.println(" ? ");
    	return(-1);
    }
  }

  public int Run () {
    // IMPLEMENT:
    m_bStop = false;
    m_bAlive = true;
    int r = PerformRunning();
    m_bAlive = false;
    m_bStop = true;
    return(r);
  }

 float m_TSS = 0.0f;

 public void setTSSValue(double _TSS){ 
   m_TSS = (float) _TSS;
  }

 public float GetTSSValue()
 {
   return m_TSS;
 }

public void SetPBvalue(int _pbValue)
{  
  m_pbValue = _pbValue;
}

public void setPBvalue(int _pbValue)
{  
  pbValue = pbValue + _pbValue;
  SetPBvalue(pbValue);
}
  public boolean KeepRunning () {
    return(!m_bStop);
  }

  public void StopRunning () {
    // IMPLEMENT:
    m_bStop = true;
  }

  public boolean IsRunning () {
    // IMPLEMENT:
    return(m_bAlive);
  }

public String getProfile (String ProjectName,String Metafile, String key) {
    // IMPLEMENT:
   CXMMetaDataReader metadata = new CXMMetaDataReader();
   metadata.setFileStatus(ProjectName,Metafile);

   String value = metadata.getProfile(key);
   if(value == null)
       value= "null";

   metadata.close();
   return value;
  }

  public String[] getProfiles (String ProjectName,String Metafile,String key) {
    // IMPLEMENT:
   CXMMetaDataReader metadata = new CXMMetaDataReader();
    metadata.setFileStatus(ProjectName,Metafile);
   
   String[] values = metadata.getProfiles(key);
   metadata.close();
   return values;
  }

public void setProfile (String ProjectName,String Metafile, String key, String value) {
    // IMPLEMENT:
   CXMMetaDataSaver metadata = new CXMMetaDataSaver();
   metadata.setFileStatus(ProjectName,Metafile);
   
   if(key.equals("PROJECT_PATH")) value = System.getProperty("minerdir") + "/xmminer/user/";

   metadata.setProfile(key,value);
   metadata.close();
   return ;
  }

  public void setProfiles (String ProjectName,String Metafile,String key, String[] value) {
    // IMPLEMENT:
   CXMMetaDataSaver metadata = new CXMMetaDataSaver();
   metadata.setFileStatus(ProjectName,Metafile);
   
   metadata.setProfiles(key,value);
   metadata.close();
   return ;
  }

  public void setParameterFileName(String _ParameterFileName)
  {  ParameterFileName = _ParameterFileName;
    return ; 
  }

  public String getParameterFileName()
  {
    return ParameterFileName; 
  }

  public void setProjectName(String _ProjectName,String NextArcfile)
  {  project = _ProjectName;
     nextArcFile = NextArcfile;
    return ; 
  }

  public String getProjectName()
  {
    return project; 
  }

  public void setSchema(String _ProjectName,String _ParameterFile)
  {
    String Pre_Learn_arc;
	String Pre_Test_arc;
    String result_Learn_arc;
    String result_Test_arc;
    String MethodLearn;
	String MethodTest;
    String[] column_list ;
    String[] input_column_list;
    String[] output_column_list;
    String[] column_index;
    String[] column_property;
    String  Number_of_rows;
	Integer int_val;

    CXMMetaDataReader cmr = new CXMMetaDataReader();
    cmr.setFileStatus(_ProjectName,_ParameterFile);
    MethodLearn = cmr.getProfile("METHODLEARN");
	MethodTest  = cmr.getProfile("METHODTEST");
	if (MethodLearn.equals("true")) 
     {
	   Pre_Learn_arc = cmr.getProfile("LEARNDATA");
   	   result_Learn_arc = cmr.getProfile("LEARNRESULT");
       input_column_list  = cmr.getProfiles("INPUT_COLUMN_LIST");   
       output_column_list  = cmr.getProfiles("OUTPUT_COLUMN_LIST");   
	   column_list  = new String[input_column_list.length + output_column_list.length];
   	   column_index = new String[input_column_list.length + output_column_list.length];
       
       for (int i = 0; i< input_column_list.length ; i++)
            column_list[i] = input_column_list[i];
       for (int i = 0; i< output_column_list.length ; i++)
            column_list[input_column_list.length + i] = output_column_list[i];
       
	   for (int i=0;i<column_list.length ;i++ )
	       column_index[i] = String.valueOf(i+1);
	   

	   int_val = new Integer(input_column_list.length + output_column_list.length);
	   
       Number_of_rows  = cmr.getProfile("NUMBER_OF_LEARN_DATA_SET");   
        
       CXMMetaDataSaver cms = new CXMMetaDataSaver();
	   cms.setFileStatus(_ProjectName,result_Learn_arc);
	   cms.setProfile("DATA_FILE",result_Learn_arc);
   	   cms.setProfile("ROW_INDEX",result_Learn_arc);
	   cms.setProfile("NUMBER_OF_COLUMNS",int_val.toString());
	   cms.setProfile("NUMBER_OF_ROWS",Number_of_rows);
	   cms.setProfiles("COLUMN_LIST",column_list);
	   cms.setProfiles("COLUMN_INDEX",column_index);

	   
	   CXMMetaDataReader cmr_in_node = new CXMMetaDataReader();
	   cmr_in_node.setFileStatus(_ProjectName,Pre_Learn_arc);
	   for(int i=0 ; i < column_list.length ;i++)
		   {	column_property =  cmr_in_node.getProfiles(column_list[i]);
				cms.setProfiles(column_list[i],column_property);
		   }
       cms.close();
	   cmr_in_node.close();
      }

	if (MethodTest.equals("true")) 
   	 {
	   Pre_Test_arc = cmr.getProfile("TESTDATA");
   	   result_Test_arc = cmr.getProfile("TESTRESULT");
       input_column_list  = cmr.getProfiles("INPUT_COLUMN_LIST");   
       output_column_list  = cmr.getProfiles("OUTPUT_COLUMN_LIST");   
	   column_list  = new String[input_column_list.length + output_column_list.length];
   	   column_index = new String[input_column_list.length + output_column_list.length];
       
       for (int i = 0; i< input_column_list.length ; i++)
            column_list[i] = input_column_list[i];
       for (int i = 0; i< output_column_list.length ; i++)
            column_list[input_column_list.length + i] = output_column_list[i];
       
	   for (int i=0;i<column_list.length ;i++ )
	       column_index[i] = String.valueOf(i+1);
	   

	   int_val = new Integer(input_column_list.length + output_column_list.length);
	   
       Number_of_rows  = cmr.getProfile("NUMBER_OF_TEST_DATA_SET");   
        
       CXMMetaDataSaver cms_result = new CXMMetaDataSaver();
	   cms_result.setFileStatus(_ProjectName,result_Test_arc);
	   cms_result.setProfile("DATA_FILE",result_Test_arc);
   	   cms_result.setProfile("ROW_INDEX",result_Test_arc);
	   cms_result.setProfile("NUMBER_OF_COLUMNS",int_val.toString());
	   cms_result.setProfile("NUMBER_OF_ROWS",Number_of_rows);
	   cms_result.setProfiles("COLUMN_LIST",column_list);
	   cms_result.setProfiles("COLUMN_INDEX",column_index);

	   
	   CXMMetaDataReader cmr_out_node = new CXMMetaDataReader();
	   cmr_out_node.setFileStatus(_ProjectName,Pre_Test_arc);
	   for(int i=0 ; i < column_list.length;i++)
		   {	column_property =  cmr_out_node.getProfiles(column_list[i]);
				cms_result.setProfiles(column_list[i],column_property);
		   }
	   cms_result.close();
	   cmr_out_node.close();
	  }
   cmr.close();

   return ;
  }

//  public static ServerMonitorPage monitor = null;
//  private void init() {
//    java.util.ResourceBundle res = java.util.ResourceBundle.getBundle("xmminer.xmserver.ServerResources");
//    String name = "XMBMNodeNeuralNetwork";
//    if (monitor == null) {
//    monitor = ServerMonitor.addPage(this, name);
//    monitor.showObjectCounter(true);
//	  monitor.showReadCounter(true);
//	  monitor.showWriteCounter(true);
//      ServerMonitor.register(name);
//    }
//    monitor.updateObjectCounter(1);
//    ServerMonitor.log(ServerResources.format(res.getString("created"), name));
//  }
}