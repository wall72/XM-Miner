package xmminer.xmserver.xmgraph.xmdnode.xmdatamanage;

import java.util.*;
import xmminer.xmlib.xmtable.*;

public class CXMColumnComputeRunMetaDataReader
{

  private CXMMetaDataReader cmr;
  
  private String project;
  private String node;
  private String p_arc;
  private String n_arc;  
  private String total_rows;  
  private String[] column_index;
  private String[] column_list;
  private String[] new_column_list;
  private String[] compute_text_list; 
  private String[] column_property;
  
  public CXMColumnComputeRunMetaDataReader()
  {}
  
  public int setMetaData(String project_name, String previous_arc, String next_arc, String node_name)
  {
    project = project_name;    
    p_arc = previous_arc;
    n_arc = next_arc;
    node = node_name;                 
    return (setInitStatus());          
  }      

  private int setInitStatus()
  {    
      try
      {
            cmr = new CXMMetaDataReader();
            cmr.setFileStatus(project,p_arc);      
            total_rows = cmr.getProfile("NUMBER_OF_ROWS");
            column_index = cmr.getProfiles("COLUMN_INDEX");       
            cmr.close();
            cmr = new CXMMetaDataReader();
            cmr.setFileStatus(project,n_arc);      
            column_list = cmr.getProfiles("OLD_COLUMN_LIST");
            for (int a=0; a<column_list.length; a++)
            {
                 System.out.println("old_column_list="+column_list[a]);
            }
            new_column_list = cmr.getProfiles("NEW_COLUMN_LIST");   
            compute_text_list = new String[new_column_list.length];
            for (int i=0; i<new_column_list.length; i++)
            {
                   column_property = cmr.getProfiles(new_column_list[i]);
                   compute_text_list[i] = column_property[7];    
            }
            cmr.close();
            return(0);
      } catch (Exception ie)
        {
            System.out.println(node+"의 실행을 위한 메타 정보가 존재하지 않습니다");
            return(1);
        }        
  }
  
  public String getTotalRows()
  {
    return total_rows;
  }

  public String[] getColumnIndex()
  {
    return column_index;
  }
  
  public String[] getColumnList()
  {
    return column_list;	
  }  
  
  public String[] getNewColumnList()
  {
    return new_column_list;	
  }
  
  public String[] getComputeText()
  {
    return compute_text_list;	
  }
  
  public String getNextArc()
  {
    return n_arc;
  }
  
}