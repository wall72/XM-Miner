package xmminer.xmlib.xmtable;

import java.util.*;
import java.util.Vector;

public class CXMGetEpisodeTransaction
{
  private String project;
  private String f_name; 
  private Vector eps_v;
  private int row_numbers;

  public CXMGetEpisodeTransaction()
  {}

  public void setFileStatus(String project_name, String arc_name)
  {
    project = project_name;
    f_name = arc_name;  
    CXMMetaDataReader cmr = new CXMMetaDataReader();
    cmr.setFileStatus(project,f_name);
    row_numbers = Integer.parseInt(cmr.getProfile("NUMBER_OF_ROWS"));
    cmr.close();    
  }
  
  public Vector getEpisodeTransaction(String time_field, String start_time, String end_time, String target_field)
  {
    CXMTableQuerier ctq = new CXMTableQuerier();
    String[] trans_field_arr = new String[2];    
    String[] trans_value_arr = new String[2];
    int time_int;
    int start_int;
    int end_int;
    eps_v = new Vector();   
    trans_field_arr[0] = time_field;
    trans_field_arr[1] = target_field;
    ctq.setFileStatus(project,f_name,null,trans_field_arr);
    start_int = Integer.parseInt(start_time);
    end_int = Integer.parseInt(end_time);
    for (int i=1; i<row_numbers+1; i++)
    {
      trans_value_arr = ctq.getStringArrayInColArray(i);
      time_int = Integer.parseInt(trans_value_arr[0]);
      if ((time_int>=start_int)&&(time_int<end_int))
      {
        eps_v.addElement(trans_value_arr[1]);
      }
    }
    ctq.close();
    return eps_v;
  } 
}