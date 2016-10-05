package xmminer.xmserver.xmgraph.xmdnode.xmdatamanage;

import java.util.*;
import java.util.Vector;
import xmminer.xmlib.xmfileprocess.*;
import xmminer.xmlib.xmtable.*;
import xmminer.xmserver.xmgraph.xmdnode.xmdatamanage.*;

public class CXMColumnSelectMetaDataManager
{
  private String arc = "arc";

  private CXMMetaDataReader cdr;  
  private CXMMetaDataSaver cds;
  private String project;
  private String p_arc;
  private String n_arc;
  private String node;  
  private String list_value;
  private String index_value;
  private String root_path = System.getProperty("minerdir") + "/xmminer/user/";//"/XM-Miner/xmminer/user"); 수정 2002.09.22
  private String meta_path = "/meta/";   
   private String index_path = "/index/";   
  private String set_meta_path;
  private String set_index_path;
  private String[] column_list;
  private String[] column_index;
  private String[] column_property;
  private String[] column_type;
  private String[] average_value;
  private String[] max_value;
  private String[] min_value;
  private String[] max_frequency_value;
  private String[] miss_percent;
  private String[] cal_result;
  private String[] new_column_list;
  private String[] new_column_index;
  private String[] new_column_property;
  private String[] new_column_type;
  private String[] new_average_value;
  private String[] new_max_value;
  private String[] new_min_value;
  private String[] new_max_frequency_value;
  private String[] new_miss_percent;
  private String[] new_cal_result;
  private String[] mix_column_list;
  private String[] mix_column_property;
  private String[] mix_column_type;
  private String[] mix_average_value;
  private String[] mix_max_value;
  private String[] mix_min_value;
  private String[] mix_max_frequency_value;
  private String[] mix_miss_percent;
  private String[] mix_cal_result;  
  private String[] temp_column_index;
  private int array_length = 0;
  private int new_array_length = 0;
  private int mix_array_length = 0;
  private int condFlag; 
  private boolean cal_dialog_open = false;  
  private Vector list_v;
  private Vector column_list_v;  
  private Vector new_column_list_v;  
  private Vector column_del_v;
  private Vector new_column_del_v;
  private Vector column_index_v;
  private Vector new_column_index_v;  
  private Vector not_calculate_col_v;
  
  private CXMNullCheck cnc = new	 CXMNullCheck();
  private CXMMetaDataReader cdr2;
  private String filtering_file;
  private boolean filtering_check;

  public CXMColumnSelectMetaDataManager()
  {}

  public int setMetaData(String project_name, String previous_arc, String next_arc, String node_name)
  {
       project = project_name;  
       p_arc = previous_arc;
       n_arc = next_arc;   
       node = node_name; 
       set_meta_path = root_path + project_name + meta_path;         
       set_index_path = root_path + project_name + index_path;              
       return(setInitStatus());  
  }
  
  public void close()
  {
  	saveMetaData();
  }  

  private int setInitStatus()
  {    
      try
      {
           not_calculate_col_v = new Vector();
           cdr = new CXMMetaDataReader();        
           cdr.setFileStatus(project,p_arc);              
           column_list = cdr.getProfiles("OLD_COLUMN_LIST");                       
           column_index = cdr.getProfiles("COLUMN_INDEX");        
           new_column_list = cdr.getProfiles("NEW_COLUMN_LIST");      
           new_column_index = cdr.getProfiles("NEW_COLUMN_INDEX");      
           filtering_file = cdr.getProfile("FILTERING_FILE");      
           if (cnc.nullCheck(filtering_file))
           {
               filtering_check = false;
           }
           else
           {
               filtering_check = true;
           }      
           setArrayValue();     
           setCalculateDialogOpen();     
           cdr.close();                 
           return(0); 
      } catch (Exception ie)
         {
             System.out.println(node+"의 이전 VNODE 메타 정보가 존재하지 않습니다");
             return(1);
         }    
  }
  
  private void saveMetaData()
  {   
     setPreviousArcMetaData();
     CXMFileCopy cfc = new CXMFileCopy();     
     cfc.fileCopy(set_meta_path+p_arc+".meta",set_meta_path+n_arc+".meta");          
     setNextArcMetaData();    
     cfc.fileCopy(set_index_path+p_arc+".nidx",set_index_path+n_arc+".nidx");     
  }    

 private void setArrayInit(String[] i_arr)
 {
 	i_arr = new String[1];	
       i_arr[0] = " ";
 }
 
 private void setArrayValue()
 {
    if (filtering_check)
    {
        cdr2 = new CXMMetaDataReader();
        cdr2.setFileStatus(project,filtering_file);    
    }
    if (cnc.nullCheck(column_list)&&cnc.nullCheck(new_column_list))
    {       
       setArrayInit(column_list);
       setArrayInit(column_type);
       setArrayInit(average_value);
       setArrayInit(max_value);
       setArrayInit(min_value);         
       setArrayInit(max_frequency_value);        
       setArrayInit(miss_percent);    
       condFlag = 1;
    }     
    else if ((!cnc.nullCheck(column_list))&&cnc.nullCheck(new_column_list))
    {     	
    	setCondOneValue();    	
    	condFlag = 1;
    }   	
    else if (cnc.nullCheck(column_list)&&(!cnc.nullCheck(new_column_list)))
    {
    	setCondTwoValue();
    	condFlag = 2;
    }
    else
    {    	
    	setCondOneValue();    
    	setCondTwoValue();    	
    	resetArrayValue();    	
    	condFlag = 3;
    }
    if (filtering_check)
    {    
        cdr2.close();
    }
 }
 
 private void setCalculateDialogOpen()
 {
    int v_size = not_calculate_col_v.size();    
    if (condFlag==1)
    {    	
    	if (array_length==v_size)
    	{
    	    	cal_dialog_open = false;
    	}
    	else
    	{
    		cal_dialog_open = true;
    	}
    }   	
    else if (condFlag==2)
    {
    	if (new_array_length==v_size)
    	{
    	    	cal_dialog_open = false;
    	}
    	else
    	{
    		cal_dialog_open = true;
    	}
    }
    else
    {
    	if (mix_array_length==v_size)
    	{
    	    	cal_dialog_open = false;
    	}
    	else
    	{
    		cal_dialog_open = true;
    	}
    }    
 }
 
 private void setCondOneValue()
 {
 	array_length = column_list.length;
    	column_type = new String[array_length];
    	average_value = new String[array_length];
    	max_value = new String[array_length];
    	min_value = new String[array_length];
    	max_frequency_value = new String[array_length];
    	miss_percent = new String[array_length];
    	
    	for (int i=0; i<array_length; i++)
    	{
    	    column_property = cdr.getProfiles(column_list[i]);
    	    if (cnc.nullCheck(column_property))
    	    {
    	    	column_type[i] = " ";
    	    }
    	    else
    	    {
    	    	column_type[i] = column_property[0];
    	    }
    	    if (filtering_check)
    	    {    	    
    	        cal_result = cdr2.getProfiles(column_list[i]+"_cal_result");    	    
    	    }
    	    if (cnc.nullCheck(cal_result))
    	    {  	   
    	       average_value[i] = " ";
    	       max_value[i] = " ";
    	       min_value[i] = " ";
    	       max_frequency_value[i] = " ";
    	       miss_percent[i] = " ";
    	    }
    	    else
    	    {
    	    	not_calculate_col_v.addElement(column_list[i]);     	   
    	    	average_value[i] = cal_result[0];    	    
    	       max_value[i] = cal_result[1];    	       
    	       min_value[i] = cal_result[2];    	       
    	       max_frequency_value[i] = cal_result[3];    	       
    	    	miss_percent[i] = cal_result[4];    
    	    }
       }
 }
 
 private void setCondTwoValue()
 {
 	new_array_length = new_column_list.length;
    	new_column_type = new String[new_array_length];
    	new_average_value = new String[new_array_length];
    	new_max_value = new String[new_array_length];
    	new_min_value = new String[new_array_length];
    	new_max_frequency_value = new String[new_array_length];
    	new_miss_percent = new String[new_array_length];
    	
    	for (int i=0; i<new_array_length; i++)
    	{
    	    new_column_property = cdr.getProfiles(new_column_list[i]);
    	    if (cnc.nullCheck(new_column_property))
    	    {
    	    	new_column_type[i] = " ";
    	    }
    	    else
    	    {
    	    	new_column_type[i] = new_column_property[0];
    	    }
    	    if (filtering_check)
    	    {
    	        new_cal_result = cdr2.getProfiles(new_column_list[i]+"_cal_result");
    	    }
    	    if (cnc.nullCheck(new_cal_result))
    	    {  	    
    	       new_average_value[i] = " ";
    	       new_max_value[i] = " ";
    	       new_min_value[i] = " ";
    	       new_max_frequency_value[i] = " ";
    	       new_miss_percent[i] = " ";
    	    }
    	    else
    	    {
    	    	not_calculate_col_v.addElement(column_list[i]); 
    	    	new_average_value[i] = new_cal_result[0];
    	       new_max_value[i] = new_cal_result[1];
    	       new_min_value[i] = new_cal_result[2];
    	       new_max_frequency_value[i] = new_cal_result[3];
    	       new_miss_percent[i] = new_cal_result[4];
    	    }
       }
 } 
  
 private void resetArrayValue()
 {
 	mix_array_length = array_length + new_array_length;
 	mix_column_list = new String[mix_array_length];
    	mix_column_type = new String[mix_array_length];
    	mix_average_value = new String[mix_array_length];
    	mix_max_value = new String[mix_array_length];
    	mix_min_value = new String[mix_array_length];
    	mix_max_frequency_value = new String[mix_array_length];
    	mix_miss_percent = new String[mix_array_length];
    	
    	int i;
    	for (i=0; i<array_length; i++)
    	{
    	    mix_column_list[i] = column_list[i];
    	    mix_column_type[i] = column_type[i];
    	    mix_average_value[i] = average_value[i];
    	    mix_max_value[i] = max_value[i];
    	    mix_min_value[i] = min_value[i];
    	    mix_max_frequency_value[i] = max_frequency_value[i];
    	    mix_miss_percent[i] = miss_percent[i];
    	}    	
    	
    	for (i=array_length; i<mix_array_length; i++)
    	{
    	    mix_column_list[i] = new_column_list[i-array_length];
    	    mix_column_type[i] = new_column_type[i-array_length];
    	    mix_average_value[i] = new_average_value[i-array_length];
    	    mix_max_value[i] = new_max_value[i-array_length];
    	    mix_min_value[i] = new_min_value[i-array_length];
    	    mix_max_frequency_value[i] = new_max_frequency_value[i-array_length];
    	    mix_miss_percent[i] = new_miss_percent[i-array_length];
    	}   
 }
 
  
  private void setPreviousArcMetaData()
  {
    cds = new CXMMetaDataSaver();
    cds.setFileStatus(project,p_arc);    
    cds.setProfile("NEXT_NODE",node);  
    cds.close();    	
  }
  
  private void setNextArcMetaData()
  {
    cds = new CXMMetaDataSaver();
    cds.setFileStatus(project,n_arc);
    cds.setProfile("PREVIOUS_NODE",node);
    cds.setProfile("ROW_INDEX",n_arc);
    cds.deleteProfile("NEXT_NODE"); 
    if (condFlag==1)
    {
    	setCondOneMetaData();
    	cds.deleteProfile("NEW_COLUMN_LIST");
    	cds.deleteProfile("NEW_DATA_FILE");
    	cds.deleteProfile("NEW_ROW_INDEX");
    	cds.deleteProfile("NEW_COLUMN_INDEX");
    	cds.deleteProfile("NUMBER_OF_NEW_COLUMNS");
    }
    else if (condFlag==2)
    {
    	setCondTwoMetaData();
    	cds.deleteProfile("COLUMN_LIST");
    	cds.deleteProfile("DATA_FILE");
    	cds.deleteProfile("ROW_INDEX");
    	cds.deleteProfile("COLUMN_INDEX");
    	cds.deleteProfile("NUMBER_OF_COLUMNS");
    }
    else if (condFlag==3)
    {
    	setCondOneMetaData();
    	setCondTwoMetaData();
    }	
    cds.close();
  }

  public String[] getColumnList()
  {
    if (condFlag==1)
    { 
       return column_list;
    }
    else if (condFlag==2)
    {
    	return new_column_list;
    }
    else
    {
    	return mix_column_list;
    }
  }

  public String[] getColumnType()
  {
    if (condFlag==1)
    { 
       return column_type;
    }
    else if (condFlag==2)
    {
    	return new_column_type;
    }
    else
    {
    	return mix_column_type;
    }
  } 
  
  public String[] getAverageValue()
  {
    if (condFlag==1)
    { 
       return average_value;
    }
    else if (condFlag==2)
    {
    	return new_average_value;
    }
    else
    {
    	return mix_average_value;
    }
  }
  
  public String[] getMaxValue()
  {
    if (condFlag==1)
    { 
       return max_value;
    }
    else if (condFlag==2)
    {
    	return new_max_value;
    }
    else
    {
    	return mix_max_value;
    }
  }
  
  public String[] getMinValue()
  {
    if (condFlag==1)
    { 
       return min_value;
    }
    else if (condFlag==2)
    {
    	return new_min_value;
    }
    else
    {
    	return mix_min_value;
    }
  }
  
  public String[] getMaxFreqValue()
  {    
    if (condFlag==1)
    { 
       return max_frequency_value;
    }
    else if (condFlag==2)
    {
    	return new_max_frequency_value;
    }
    else
    {
    	return mix_max_frequency_value;
    }
  }
    
  public String[] getMissPercent()
  {
    if (condFlag==1)
    { 
       return miss_percent;
    }
    else if (condFlag==2)
    {
    	return new_miss_percent;
    }
    else
    {
    	return mix_miss_percent;
    }
  }
  
  public boolean getCalDialogOpen()
  {
  	return cal_dialog_open;
  }
    
  public void setNewColumnList(String[] i_list)
  {
      list_v  = new Vector();
      column_list_v = new Vector();
      new_column_list_v = new Vector();  
      column_del_v = new Vector();
      new_column_del_v = new Vector();  
      column_index_v = new Vector();
      new_column_index_v = new Vector();         
      
      for (int i=0; i<i_list.length; i++)
      {
      	   list_v.addElement(i_list[i]);
      }   
      
      if (condFlag==1)
      {
          setCondOneColumnList(); 
      }     
      else if (condFlag==2)
      {
      	  setCondTwoColumnList();
      }
      else if (condFlag==3)
      {      	  
         setCondThreeColumnList();
      }      
  }
  
  private void setCondOneColumnList()
  {
       for (int i=0; i<array_length; i++)
       {
           list_value = column_list[i];
           index_value = column_index[i];
           if (list_v.contains(list_value))
           {
                column_list_v.addElement(list_value);
      	         column_index_v.addElement(index_value);
      	    }
      	    else
      	    {
      	         column_del_v.addElement(list_value);
      	    }      	       
      }
  }
      
  private void setCondTwoColumnList()
  {
       for (int j=0; j<new_array_length; j++)
       {
      	     list_value = new_column_list[j];
      	     index_value = new_column_index[j];
      	     if (list_v.contains(list_value))
      	     {
      	          new_column_list_v.addElement(list_value);
      	          new_column_index_v.addElement(index_value);
      	     }
      	     else
      	     {
      	          new_column_del_v.addElement(list_value);
      	      }      	       
       }
  }
      
  private void setCondThreeColumnList()
  {
       int k;
       for (k=0;k<array_length;k++)
       {
             list_value = mix_column_list[k];             
             index_value = column_index[k];             
      	      if (list_v.contains(list_value))
      	      {      	         
      	           column_list_v.addElement(list_value);
      	           column_index_v.addElement(index_value);      	         
      	      }
      	      else
      	      {      	           
      	           column_del_v.addElement(list_value);  
      	      }      	       
       }
       for (k=array_length;k<mix_array_length;k++)
       {
             list_value = mix_column_list[k];            
             index_value = new_column_index[k-array_length];           
      	      if (list_v.contains(list_value))
      	      {
      	           new_column_list_v.addElement(list_value);
      	           new_column_index_v.addElement(index_value);
      	      }
      	      else
      	      {
      	      	    new_column_del_v.addElement(list_value);
      	      }      	       
       }
   }   
      
   private void setCondOneMetaData()
   {
       array_length = column_list_v.size();
       if (array_length>0)
    	{
    	    column_list = new String[array_length];
    	    column_index = new String[array_length];    	       	
           for (int i=0; i<array_length; i++)
    	    {
    	        column_list[i] = (String) column_list_v.elementAt(i);
    	        column_index[i] = (String) column_index_v.elementAt(i);
    	    } 
    	    cds.setProfiles("COLUMN_LIST",column_list);
    	    cds.setProfiles("COLUMN_INDEX",column_index);   	
    	    cds.setProfile("NUMBER_OF_COLUMNS",String.valueOf(array_length));
    	}
    	array_length = column_del_v.size();
    	if (array_length>0)
    	{    	  
           for (int j=0; j<array_length; j++)
    	    {
    	        cds.deleteProfile((String) column_del_v.elementAt(j));    	     
    	    }    	
    	}
   }
      
   private void setCondTwoMetaData()
   {
        new_array_length = new_column_list_v.size();
    	 if (new_array_length>0)
    	 {
    	    new_column_list = new String[new_array_length];
    	    new_column_index = new String[new_array_length];    	       	
           for (int i=0; i<new_array_length; i++)
    	    {
    	         new_column_list[i] = (String) new_column_list_v.elementAt(i);
    	         new_column_index[i] = (String) new_column_index_v.elementAt(i);
    	     } 
    	     cds.setProfiles("NEW_COLUMN_LIST",new_column_list);
    	     cds.setProfiles("NEW_COLUMN_INDEX",new_column_index);   
    	     cds.setProfile("NUMBER_OF_NEW_COLUMNS",String.valueOf(new_array_length));	
    	 }
    	 new_array_length = new_column_del_v.size();
    	 if (new_array_length>0)
    	 {    	  
            for (int j=0; j<new_array_length; j++)
    	     {
    	         cds.deleteProfile((String) new_column_del_v.elementAt(j));    	     
    	     }    	
    	 }
   }
}
  
