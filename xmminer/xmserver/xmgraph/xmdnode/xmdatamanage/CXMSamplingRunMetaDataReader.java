package xmminer.xmserver.xmgraph.xmdnode.xmdatamanage;

import java.util.*;
import xmminer.xmlib.xmfileprocess.*;
import xmminer.xmlib.xmtable.*;

public class CXMSamplingRunMetaDataReader
{  
  private CXMMetaDataReader cdr1;
  private CXMMetaDataReader cdr2; 
  private String project;
  private String node;
  private String p_arc;
  private String n_arc; 
  private String select_option;     

// Modified by Sun Jee Hun ------------------------------------------------------//  
  private String[] column_list;
// ------------------------------------------------------------------------------//

  private String path;
  private String root_path = System.getProperty("minerdir") + "/xmminer/user";//"/XM-Miner/xmminer/user"); 수정 2002.09.22
  private String meta_path = "/meta/";
  
  private int total_rows;
  private int sampling_number;
  private int selected_index;
  private int cbox_idx;  
  private int clustering_number;

  public CXMSamplingRunMetaDataReader()
  {}

  public int setMetaData(String project_name, String previous_arc, String next_arc, String node_name)
  {
    project = project_name;    
    p_arc = previous_arc;
    n_arc = next_arc;
    node = node_name;                 

	path = root_path + project + meta_path;
    
	return (setInitStatus());          
  }     

  private int setInitStatus()
  {   
      try
      {
          cdr1 = new CXMMetaDataReader();
          cdr1.setFileStatus(project,p_arc);      
          total_rows = Integer.parseInt(cdr1.getProfile("NUMBER_OF_ROWS"));

// Modified by Sun Jee Hun ------------------------------------------------------//
		  column_list = cdr1.getProfiles("COLUMN_LIST");
// ------------------------------------------------------------------------------//
          cdr1.close();

		  cdr2 = new CXMMetaDataReader();
          cdr2.setFileStatus(project,n_arc);      
          sampling_number = Integer.parseInt(cdr2.getProfile("NUMBER_OF_ROWS"));      
          selected_index = Integer.parseInt(cdr2.getProfile("sampling_index"));    
          cbox_idx = Integer.parseInt(cdr2.getProfile("sampling_select_option"));     
          if (selected_index==1)
          {
              clustering_number = Integer.parseInt(cdr2.getProfile("sampling_cluster_number"));       
          }          
          cdr2.close();
          return (0);
     } catch (Exception ie)
        {
            System.out.println(node+"의 실행을 위한 메타 정보가 존재하지 않습니다");
            return(1);
        }
     
       
  }
  
  public int getTotalRows()
  {
    return total_rows;
  }
  
  public int getSamplingNumber()
  {
    return sampling_number;
  }
  
  public int getSelectedIndex()
  {
     return selected_index;
  }

  public int getClusteringNumber()
  {
     return clustering_number;
  }
  
  public int getCboxIndex()
  {
    return cbox_idx;
  }

// Modified by Sun Jee Hun ----------------------------------------------------//
  public String [] getColumnList()
  {
	  return column_list;
  }
// ----------------------------------------------------------------------------//
}