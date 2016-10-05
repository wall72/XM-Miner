package xmminer.xmserver.xmgraph.xmdnode.xmdatamanage;

import java.util.*;
import xmminer.xmlib.xmtable.*;

public class CXMPartitioningRunMetaDataReader
{ 

  private CXMMetaDataReader cdr1;
  private CXMMetaDataReader cdr2;   
  private CXMMetaDataReader cdr3;   
   
  private String project;
  private String node;
  private String p_arc;
  private String t_arc; 
  private String v_arc;
  private int[][] training_value;
  private int[][] validation_value;
  private int total_rows;
  private int partition_index;
  private int training_number;
  private int validation_number; 

// Modified by Sun Jee Hun ///////////////////////////////////////////////////////
	private String[] column_list;
//////////////////////////////////////////////////////////////////////////////////

  public CXMPartitioningRunMetaDataReader()
  {}

  public int setMetaData(String project_name,String previous_arc, String training_arc, String validation_arc, String node_name)
  {
      project = project_name;   
      p_arc = previous_arc;
      t_arc = training_arc;
      v_arc = validation_arc;
      node = node_name;                  
      return(setInitStatus());          
  }  

  private int setInitStatus()
  {   
      try
      {
          
          cdr1 = new CXMMetaDataReader();
          cdr1.setFileStatus(project, p_arc);      
          total_rows = Integer.parseInt(cdr1.getProfile("NUMBER_OF_ROWS"));
		
		  // Modified by Sun Jee Hun ///////////////////////////////////////////////////////
		  column_list = cdr1.getProfiles("COLUMN_LIST");
		  //////////////////////////////////////////////////////////////////////////////////

          partition_index = Integer.parseInt(cdr1.getProfile("partition_index")); 
          cdr1.close();
          cdr2 = new CXMMetaDataReader();
          cdr2.setFileStatus(project, t_arc);      
          cdr3 = new CXMMetaDataReader();
          cdr3.setFileStatus(project, v_arc);            
          training_number = Integer.parseInt(cdr2.getProfile("NUMBER_OF_ROWS"));
          validation_number = Integer.parseInt(cdr3.getProfile("NUMBER_OF_ROWS"));
          if (partition_index==1)
          {
              setPartitionNumberArray();
          }
          cdr2.close();
          cdr3.close();  
         
      } catch (Exception ie)
        {
            System.out.println(node+"의 실행을 위한 메타 정보가 존재하지 않습니다");
            return(1);
        }
        return(0);      
  }     
  
  private void setPartitionNumberArray()
  {
      String[] training_number_array = cdr2.getProfiles("ordered_training_number");
      String[] validation_number_array = cdr3.getProfiles("ordered_validation_number");
      int training_array_size = training_number_array.length/2;
      int validation_array_size = validation_number_array.length/2;
      training_value = new int[training_array_size][2];
      validation_value = new int[validation_array_size][2];
      for (int i=0; i<training_array_size; i++)
      {
          training_value[i][0] = Integer.parseInt(training_number_array[i*2]);
          training_value[i][1] = Integer.parseInt(training_number_array[i*2+1]);
      }
      for (int j=0; j<validation_array_size; j++)
      {
          validation_value[j][0] = Integer.parseInt(validation_number_array[j*2]);
          validation_value[j][1] = Integer.parseInt(validation_number_array[j*2+1]);
      }
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
  
  public int getPartitionIndex()
  {
      return partition_index;
  }

  public int getTrainingNumber()
  {
    return training_number;
  } 
  
  public int getValidationNumber()
  {
    return validation_number;
  } 
  
  public int[][] getTrainingValue()
  {
    return training_value; 
  }

  public int[][] getValidationValue()
  {
    return validation_value;
  }

}