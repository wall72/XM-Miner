package xmminer.xmserver.xmgraph.xmdnode.xmdatamanage;

import java.util.*;
import java.util.Vector;
import xmminer.xmlib.xmfileprocess.*;
import xmminer.xmlib.xmtable.*;

public class CXMCalculateMetaDataManager
{ 

  private CXMMetaDataReader cdr;    
  private CXMMetaDataSaver cds;
  private String project;  
  private String p_arc;  
  private String column_value;  
  private String[] column_list;  
  private String[] column_property;
  private String[] cal_result_value;  
  private int cal_filter_index = 3;
  private int column_filter_index = 5;
  private int total_rows;
  private Hashtable cal_result_h = new Hashtable();
  private Hashtable column_property_h = new Hashtable();
  private CXMNullCheck cnc = new CXMNullCheck();
  
  private Hashtable cal_result_file = new Hashtable();
  private String unique_file_name;

  public CXMCalculateMetaDataManager()
  {}

  public void setMetaData(String project_name, String previous_arc)
  {   
    project = project_name;              
    p_arc = previous_arc;
    getPreviousArcMeta();     
  }
  
  public void setCalResult(Hashtable i_h)
  {  	
  	cal_result_h = i_h; 	  	
  }
  
  public int getTotalRows()
  {
  	return total_rows;
  } 
  
  public void setCalResultFile(Hashtable i_h)
  {
  	cal_result_file = i_h;  
  }  
  
  public void close()
  {
  	saveMetaData();
  } 
  
  private void saveMetaData()
  {       
       setPreviousArcMetaData();    
       setFilteringMetaData();
  } 
    
  private void getPreviousArcMeta()
  {
    cdr = new CXMMetaDataReader();    
    cdr.setFileStatus(project,p_arc);
    column_list = cdr.getProfiles("COLUMN_LIST");   
    total_rows = Integer.parseInt(cdr.getProfile("NUMBER_OF_ROWS"));    
    if (!cnc.nullCheck(column_list))
    {
         for (int i=0; i<column_list.length; i++)
         { 
    	       column_property = cdr.getProfiles(column_list[i]);
    	       column_property_h.put(column_list[i],column_property);
         }
    }  
    cdr.close();    	
  }
 
  private void setPreviousArcMetaData()
  {   
    try
    {   
    	cds = new CXMMetaDataSaver();   
        cds.setFileStatus(project,p_arc);      
        Enumeration hEum = cal_result_h.keys();           
        while(hEum.hasMoreElements())
        {         
           column_value = (String) hEum.nextElement();                    
           cal_result_value = (String[]) cal_result_h.get(column_value);                                                         
           if (!cnc.nullCheck(cal_result_value[cal_filter_index]))
           {           	    
           	column_property = (String[]) column_property_h.get(column_value);           
           	column_property[column_filter_index] = "filtered";           
           	cds.setProfiles(column_value,column_property);     
           }             
        }
        cds.setProfile("FILTERING_FILE",p_arc+"_filtering");
        cds.close();    
    } catch(Exception e)
       {System.out.println("set_previous_arc_meta_error");} 
    cds.setProfile("FILTERING_FILE",p_arc+"_filtering");
    cds.close();    	
  }    

  private void setFilteringMetaData()
  {
    cds = new CXMMetaDataSaver();
    cds.setFileStatus(project,p_arc+"_filtering");  
    cds.setProfile("filtering_arc",p_arc);   
    try
    {      
        Enumeration hEum = cal_result_h.keys();      
        while(hEum.hasMoreElements())
        {  
           column_value = (String) hEum.nextElement();    
           cal_result_value = (String[]) cal_result_h.get(column_value);                                                         
           cds.setProfiles(column_value+"_cal_result",cal_result_value);                         
        }
    } catch(Exception e)
       {System.out.println("set_cal_result_error");}  
    try
    {  
        Enumeration hEum = cal_result_file.keys();         
        while(hEum.hasMoreElements())
        {     
           column_value = (String) hEum.nextElement();        
           unique_file_name = (String) cal_result_file.get(column_value);                                                         
           cds.setProfile(column_value+"_unique_value_file",unique_file_name);                      
        }
    } catch(Exception e)
       {System.out.println("set_unique_file_error");}     
    cds.close();    	
  }
}
  
