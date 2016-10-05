package xmminer.xmserver.xmgraph.xmdnode.xmdatamanage;

import java.util.*;
import xmminer.xmlib.xmfileprocess.*;
import xmminer.xmlib.xmtable.*;

public class CXMColumnComputeMetaDataManager
{
  private String arc = "arc";

  private CXMMetaDataReader cmr;
  private CXMMetaDataSaver cms;  
  private CXMSetMetaDataNewColumnProperty cnp;
  
  private String project;
  private String node;
  private String p_arc;
  private String n_arc;  
  private String path;
  private String root_path = System.getProperty("minerdir") + "/xmminer/user/";//"/XM-Miner/xmminer/user"); 수정 2002.09.22
  private String meta_path = "/meta/";   
  
  private String compute_text;   
  private String total_rows;
 
  private String[] column_list;
  private String[] new_column_list;
  private String[] column_type;
  private String[] column_property;
  private String[] function_list; 
  private String[] new_column_index; 
  private String[] compute_text_list; 
  
  private Hashtable h;

  public CXMColumnComputeMetaDataManager()
  {} 
  
  public int setMetaData(String project_name, String previous_arc, String next_arc, String node_name)
  {
      project = project_name;
      p_arc = previous_arc;
      n_arc = next_arc;   
      node = node_name;                  
      path = root_path + project + meta_path;
      return(setInitStatus());          
  }
  
  public void close()
  {
  	saveMetaData();
  } 
  
  private int setInitStatus()
  {                 
      int p_r = getPreviousArcMetaData();
      if (p_r==0)
      {
          CXMFileCopy cfc = new CXMFileCopy();
          cfc.fileCopy(path+p_arc+".meta",path+n_arc+".meta"); 
          int n_r = getNextArcMetaData();     
          getFunctionMetaData();
          return n_r;       	
      }
      else
      {
          return p_r;
      }         
  }
  
  private void saveMetaData()
  {   
     setPreviousArcMetaData();     
     setNextArcMetaData();      
     setNewArcMetaData();
 }  
  
  private int getPreviousArcMetaData()
  {
      try
      {    
          cmr = new CXMMetaDataReader();
          cmr.setFileStatus(project,p_arc);                           
          column_list = cmr.getProfiles("OLD_COLUMN_LIST");          
          if (nullCheck(column_list))
          {
                column_list = new String[1];	
                column_list[0] = "";
          }    
          column_type = new String[column_list.length];
          for (int j=0; j<column_list.length; j++)
          {
                column_property = cmr.getProfiles(column_list[j]);
                column_type[j] = column_property[0];
          }
          cmr.close(); 
          return(0);
      } catch (Exception ie)
         {
             System.out.println(node+"의 이전 VNODE 메타 정보가 존재하지 않습니다");
             return(1);
         }
  }
  
  private int getNextArcMetaData()
  {
      try
      {
          cmr = new CXMMetaDataReader();
          cmr.setFileStatus(project,n_arc);
    	   new_column_list = cmr.getProfiles("NEW_COLUMN_LIST");
    	   validateNewColumnList();
    	   cmr.close();    	                     
          return(0); 
      } catch (Exception ie)
         {
             System.out.println(node+"의 다음 VNODE 메타 정보 생성시 문제가 발생했습니다.");
             return(1);
         }       
  } 
  
 private void validateNewColumnList()
 {  
    if (nullCheck(new_column_list))
    {
       new_column_list = new String[1];	
       new_column_list[0] = "";
       compute_text_list = new String[1];
       compute_text_list[0] = "";
    }    
    else
    {
    	compute_text_list = new String[new_column_list.length];
    	for (int i=0; i<new_column_list.length; i++)
    	{
    	    column_property = cmr.getProfiles(new_column_list[i]);    	        
    	    compute_text_list[i] = column_property[7];    	       
    	}
    }           
 }
  
  private void getFunctionMetaData()
  {
     h = new Hashtable();
     cmr = new CXMMetaDataReader();
     cmr.setFileStatus(project,"function");
     function_list = cmr.getProfiles("FUNCTION_LIST");
     for (int i=0; i<function_list.length; i++)
     {
           h.put(function_list[i],cmr.getProfile(function_list[i]));
     }
     cmr.close();  	
  }  
    
  private void setPreviousArcMetaData()
  {
      cms = new CXMMetaDataSaver();
      cms.setFileStatus(project,p_arc);    
      cms.setProfile("NEXT_NODE",node);  
      cms.close();    	
  }
  
  private void setNextArcMetaData()
  {
      cms = new CXMMetaDataSaver();
      cms.setFileStatus(project,n_arc);
      cms.setProfile("PREVIOUS_NODE",node);
      cms.deleteProfile("NEXT_NODE");      
      cms.setProfiles("NEW_COLUMN_LIST",new_column_list);  
      for (int i=0; i<new_column_list.length; i++)
      {
            cms.setProfile(new_column_list[i],"REAL");
      }	  
      String new_data_file = "new_"+n_arc;
      cms.setProfile("NEW_DATA_FILE",new_data_file);
      cms.setProfile("NEW_ROW_INDEX",new_data_file);
      cms.setProfiles("NEW_COLUMN_INDEX",setNewColumnIndex());         
      cms.setProfile("NUMBER_OF_NEW_COLUMNS",String.valueOf(new_column_list.length));
      cms.close(); 
      setNewColumnProperty();    
      setComputeProperty();  
  }
  
  private void setNewArcMetaData()
  {
  	cms = new CXMMetaDataSaver();
  	cms.setFileStatus(project,"new_"+n_arc);
  	cms.setProfiles("COLUMN_LIST",new_column_list);  
  	for (int i=0; i<new_column_list.length; i++)
       {
            cms.setProfile(new_column_list[i],"REAL");
       }	
       String new_data_file = "new_"+n_arc;
       cms.setProfile("DATA_FILE",new_data_file);
       cms.setProfile("ROW_INDEX",new_data_file);
       cms.setProfiles("COLUMN_INDEX",setNewColumnIndex());         
       cms.setProfile("NUMBER_OF_COLUMNS",String.valueOf(new_column_list.length));
       cms.close();
  }
  
   private void setNewColumnProperty()
  {     
     cnp = new CXMSetMetaDataNewColumnProperty();
     cnp.setColumnProperty(project,n_arc);          
  }
  
  private void setComputeProperty()
  {    
     for (int i=0; i<compute_text_list.length; i++)
     {
        String compute_text = compute_text_list[i];       
        cmr = new CXMMetaDataReader();
        cmr.setFileStatus(project,n_arc);
        column_property=cmr.getProfiles(new_column_list[i]);
        cmr.close();
        column_property[7] = compute_text;
        cms = new CXMMetaDataSaver();
        cms.setFileStatus(project,n_arc);
        cms.setProfiles(new_column_list[i],column_property);
        cms.close();                       
     }                   
  }

  private boolean nullCheck(String i_str)
  {
    boolean out_bool = false;
    if (i_str==null)
    {
      out_bool = true;
    }
    else
    {
      if (i_str.equals(""))
      {
        out_bool = true;
      }
    }
    return out_bool;
  }
  
  private boolean nullCheck(String[] i_str)
  {
    if (i_str==null)
    {
      return true;
    }
    else
    {
      if (i_str.length==0)
      {
        return true;
      }
      else
      {
        return false;
      }
    }
  }

  public String[] getColumnList()
  {
    return column_list;
  }

  public String[] getColumnType()
  {
    return column_type;
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

  public int getParameterCount(String i_function)
  {
    int param_cnt = Integer.parseInt(String.valueOf(h.get(i_function)));
    return param_cnt;
  }
 
  
  private String[] setNewColumnIndex()
  {
    new_column_index = new String[new_column_list.length];
    for (int i=0; i<new_column_list.length; i++)
    {
       new_column_index[i] = String.valueOf(i+1);  	
    }	
    return new_column_index;
  } 

  public void setNewColumn(String[] i_col_arr)
  {    
    new_column_list = i_col_arr;   
  }  

  public void setComputeText(String[] i_text)
  {    
     compute_text_list = i_text;     
  }
  
  
}