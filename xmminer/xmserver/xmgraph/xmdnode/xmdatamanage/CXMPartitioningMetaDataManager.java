package xmminer.xmserver.xmgraph.xmdnode.xmdatamanage;

import java.util.*;
import xmminer.xmlib.xmfileprocess.*;
import xmminer.xmlib.xmtable.*;

public class CXMPartitioningMetaDataManager
{
  
  private CXMMetaDataReader cdr;  
  private CXMMetaDataSaver cds1; 
  private CXMMetaDataSaver cds2; 
  private CXMMetaDataSaver cds3; 
  private String project;
  private String node;
  private String p_arc;
  private String t_arc;  
  private String v_arc;
  private String path; 
  private String[] training_number_array;
  private String[] validation_number_array;
  private String root_path = System.getProperty("minerdir") + "/xmminer/user/";//"/XM-Miner/xmminer/user"); 수정 2002.09.22
  private String meta_path = "/meta/";   
  private int total_rows;
  private int training_number;
  private int validation_number;   
  private int selected_index;
// Modified by Sun Jee Hun ///////////////////////////////////////////////////////
	private String[] column_list;
//////////////////////////////////////////////////////////////////////////////////

  public CXMPartitioningMetaDataManager()
  {}

  public int setMetaData(String project_name,String previous_arc, String training_arc, String validation_arc, String node_name)
  {
    project = project_name;   
    node = node_name;       
    p_arc = previous_arc;
    t_arc = training_arc;
    v_arc = validation_arc;
    path = root_path + project + meta_path;            
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
           cdr = new CXMMetaDataReader();
           cdr.setFileStatus(project, p_arc);
           total_rows = Integer.parseInt(cdr.getProfile("NUMBER_OF_ROWS"));

		   // Modified by Sun Jee Hun ///////////////////////////////////////////////////////
		   column_list = cdr.getProfiles("COLUMN_LIST");
		   //////////////////////////////////////////////////////////////////////////////////
		   
           cdr.close();            
           CXMFileCopy cfc = new CXMFileCopy();
           cfc.fileCopy(path+p_arc+".meta",path+t_arc+".meta");                             
           cfc.fileCopy(path+p_arc+".meta",path+v_arc+".meta");  
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
       setTrainingArcMetaData();
       setValidationArcMetaData();   
  }  

  private void setPreviousArcMetaData()
  {
      cds1 = new CXMMetaDataSaver();
      cds1.setFileStatus(project, p_arc);    
      cds1.setProfile("NEXT_NODE",node);
      cds1.setProfile("partition_index",String.valueOf(selected_index));  
      cds1.close();
  }

  private void setTrainingArcMetaData()
  {
      cds2 = new CXMMetaDataSaver();
      cds2.setFileStatus(project, t_arc);
      cds2.setProfile("PREVIOUS_NODE",node);
      cds2.deleteProfile("NEXT_NODE");       
      cds2.setProfile("ROW_INDEX",t_arc);
      cds2.setProfile("NUMBER_OF_ROWS",String.valueOf(training_number)); 
      if (selected_index==1)
      {
          cds2.setProfiles("ordered_training_number",training_number_array);
      }
      cds2.close();	
  }

  private void setValidationArcMetaData()
  {
      cds3 = new CXMMetaDataSaver();
      cds3.setFileStatus(project, v_arc);         
      cds3.setProfile("PREVIOUS_NODE",node);
      cds3.deleteProfile("NEXT_NODE");
      cds3.setProfile("ROW_INDEX",v_arc);     
      cds3.setProfile("NUMBER_OF_ROWS",String.valueOf(validation_number));                 
      if (selected_index==1)
      {
          cds3.setProfiles("ordered_validation_number",validation_number_array);
      }
      cds3.close();	
  }

	// Modified by Sun Jee Hun ///////////////////////////////////////////////////////
	public String[] getColumnList()
	{
		return column_list;
	}
	//////////////////////////////////////////////////////////////////////////////////
  public int getTotalRows()
  {
    return total_rows;
  }

  public void setTrainingNumber(int i_num)
  {
    training_number = i_num;
  }  
  
  public void setValidationNumber(int i_num)
  {
    validation_number = i_num;
  }  
  
  public void setSelectedIndex(int i_num)
  {
      selected_index = i_num;    
  }
  
  public void setTrainingValue(String[][] training_value)
  {
      int value_size = training_value.length;
      int array_size = value_size*2;
      training_number_array = new  String[array_size];
      for (int i=0; i<value_size; i++)
      {
           training_number_array[i*2] = training_value[i][0]; 
           training_number_array [i*2+1] =  training_value[i][1];
      }      
  }
  
  public void setValidationValue(String[][] validation_value)
  {
      int value_size = validation_value.length;
      int array_size = value_size*2;
      validation_number_array = new  String[array_size];        
      for (int i=0; i<value_size; i++)
      {
           validation_number_array[i*2] = validation_value[i][0]; 
           validation_number_array [i*2+1] =  validation_value[i][1];
      }      
  } 
  
}