package xmminer.xmserver.xmgraph.xmdnode.xmdnodecalculate;

import java.util.*;

import xmminer.xmlib.xmtable.*;
import xmminer.xmlib.xmcompute.*;
import xmminer.xmserver.*;
import xmminer.xmserver.xmgraph.xmdnode.xmdatamanage.*;

public class XMBDNodeCalculate 
{ 
  private int total_rows;
  private int column_number;
  private String project;
  private String p_arc;
  private String n_arc;
  private String select_column;
  private String select_type;
  private String[] cal_column_list;
  private String[] cal_column_type;
  private CXMCalculater cc; 
  private CXMCalculateMetaDataManager cdm;  
  private Hashtable pre_cal_h = new Hashtable();
  private Hashtable post_cal_h = new Hashtable();
  private int m_pbMin;
  private int m_pbMax;
  private int m_pbValue;  
  private boolean m_bStop;
  private boolean m_bAlive;  
  
  private Hashtable cal_result_file = new Hashtable();
  
  public XMBDNodeCalculate () {
    super();
//	init();
  }

  public void setFileStatus (java.lang.String project_name, java.lang.String previous_arc) 
  {
    project = project_name;   
    p_arc = previous_arc;
    cdm = new CXMCalculateMetaDataManager();      
    cdm.setMetaData(project,p_arc);    
    total_rows = cdm.getTotalRows();          
  }

  public void close () 
  {
    cdm.setCalResult(post_cal_h);
    cdm.setCalResultFile(cal_result_file);   
    cdm.close();
  }
  
  public void setCalColumnList(java.lang.String[] column_list)
  {
  	cal_column_list = column_list;
  	column_number = cal_column_list.length;
  }
  
  public void setCalColumnType(java.lang.String[] column_type)
  {
  	cal_column_type = column_type;
  }

  public void setCalOptionList (java.lang.String column,int[] cal_option) 
  {  	
  	pre_cal_h.put(column,cal_option);
  }
  
  public java.lang.String[] getResultValue (java.lang.String column) 
  { 
      return (String[]) post_cal_h.get(column);
  }

  public int GetPBMin () 
  {   
    SetPBMin();
    return m_pbMin;
  }

  private void SetPBMin () 
  {
    m_pbMin = 1;
  }

  public int GetPBMax () 
  {   
    SetPBMax();
    return m_pbMax;
  }

  private void SetPBMax () 
  {      
      m_pbMax = total_rows*column_number;  
  }

  public int GetPBValue () 
  {  
    return m_pbValue;
  }   

  public int PerformRunning()
  {        
      cc = new CXMCalculater();     
      cc.setPreCalculateStatus(project, p_arc, pre_cal_h);           
      for (int i=0; i<column_number; i++)
      {
      	  select_column = cal_column_list[i];      
      	  select_type = cal_column_type[i];   
      	  cc.setColumnName(select_column);       
      	  if (numericCheck(select_type))
      	  {
      	  	for (int j = 1; KeepRunning()&&j<=total_rows;)
      	       {    	
      	  	     m_pbValue = i*total_rows + j;      	 	 
      	  	     cc.numericCalculate(j);          	           
      	  	     j++;
      	       }
      	       cc.numericSetCalResult();        
      	  }
      	  else
      	  {
      	  	for (int j = 1; KeepRunning()&&j<=total_rows;)
      	       {      	  	
      	  	     m_pbValue = i*total_rows + j;
      	  	     cc.nonNumericCalculate(j);    
      	  	     j++;
      	       }
      	       cc.nonNumericSetCalResult();   
      	  }      	   	            	       	    
      }           
      post_cal_h = cc.setPostCalculateStatus();         
      cal_result_file = cc.getCalResultFile();           
      return(0);
  }
  
  private boolean numericCheck(String i_str)
  {
  	if (i_str.equals("REAL")||i_str.equals("INTEGER"))
  	{
  	     return true;	
       }
       else
       {
       	     return false;
       }
  }

  public int Run () 
  { 
    m_bStop = false;  
    m_bAlive = true;   
    int r = PerformRunning();
    m_bAlive = false; 
    m_bStop = true;  
    return(r);   
  }

  public boolean KeepRunning () 
  {
    return(!m_bStop);
  }

  public void StopRunning () 
  {  
    m_bStop = true;
  }

  public boolean IsRunning () 
  {  
    return(m_bAlive);
  }  

//  public static ServerMonitorPage monitor = null;
//  private void init() {
//    java.util.ResourceBundle res = java.util.ResourceBundle.getBundle("xmminer.xmserver.ServerResources");
//    String name = "XMBDNodeCalculate";
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