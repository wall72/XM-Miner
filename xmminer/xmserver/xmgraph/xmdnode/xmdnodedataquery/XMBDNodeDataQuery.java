package xmminer.xmserver.xmgraph.xmdnode.xmdnodedataquery;

import xmminer.xmlib.xmtable.*;
import xmminer.xmlib.xmfileprocess.*;
import xmminer.xmserver.*;
import xmminer.xmserver.xmgraph.xmdnode.xmdatamanage.*;
import java.util.Vector;

public class XMBDNodeDataQuery{
  
  private String current_name = "XMDNodeDataQuery";
  private String project;
  private String p_arc;
  private String n_arc;
  private String column_name;
  private String row_index;
  private String new_row_index;
  private String[] column_list;
  private String[] column_type;
  private String[] average_value;
  private String[] max_value;
  private String[] min_value;
  private String[] max_frequency_value;
  private String[] miss_percent;
  private String[] temp_data;
  private String[][] data;
  private int row_size;
  private int new_row_size;
  private int row_numbers;
  private int column_size;
  private int first_position;
  private int last_position;
  private int trans_status;
  private int new_row_number;
  private boolean cal_dialog_open;
  private boolean query_check;
  private boolean row_set;
  private Vector data_v;
  private CXMDataQueryMetaDataManager cdm;
  private CXMTableQuerier ctq;
  private XMDNodeFilterValueQuery fvq;
  private XMDNodeUserSetDataQuery udq;
  private CXMNullCheck cnc = new CXMNullCheck();

  private int m_pbMin;
  private int m_pbMax;
  private int m_pbValue;
  private boolean m_bStop;
  private boolean m_bAlive;
  
  public XMBDNodeDataQuery () {
    super();
//	init();
  }

  public int setFileStatus (java.lang.String project_name, 
                            java.lang.String previous_arc, 
                            java.lang.String next_arc) {   
    project = project_name;  
    p_arc = previous_arc;   
    n_arc = next_arc;
    cdm = new CXMDataQueryMetaDataManager();  
    int r = cdm.setMetaData(project_name, p_arc, n_arc, current_name);        
    if (r!=0)
    {
         return(1);
    }
    else
    {    
        row_numbers = cdm.getRowNumbers();     
        column_list = cdm.getColumnList();       
        column_type = cdm.getColumnType();     
        average_value = cdm.getAverageValue();       
        max_value = cdm.getMaxValue();        
        min_value = cdm.getMinValue();      
        max_frequency_value = cdm.getMaxFreqValue();       
        miss_percent = cdm.getMissPercent();       
        column_size = column_list.length;
        row_index = cdm.getRowIndex();
        new_row_index = cdm.getNewRowIndex();
        row_size = 100;
        row_set = false;
        //resetRowSize();
        return(0);
    }   
  }
  
  private void resetRowSize()
  {      
      if (row_numbers<=row_size)
      {
          row_size = row_numbers;
      }
      else
      {
         row_size = 100;
      }
  }

  public void close () {
    if (row_set)
    {
       cdm.setNewRowNumber(new_row_number);
    }
    cdm.close();
  }

  public void saveMetaData () {
    if (row_set)
    {
       cdm.setNewRowNumber(new_row_number);
    }
    cdm.saveTransMetaData();     
  }

 public void setReadStatus (int status, 
                             int query_option) {     
      resetRowSize();   
      if (status==0)
      {
          first_position = 0;
      }
      else if (status==1)
      {
          if (last_position>=row_size)
          {
              first_position = first_position - row_size;
          }
          else
          {
              first_position = 0;
          }
      }
      else if (status==2)
      {
          first_position = last_position;
      }
      else if (status==3)
      {
          if (row_numbers>=row_size)
          {
             first_position = row_numbers - row_size;
          }
          else
          {
             first_position = 0;
          }
      } 
      if (query_option==1)
      {
         setData();  
      }
      else if (query_option==2)
      {
         setSelectedData();
      }
      else if (query_option==3)
      {
         setNotSelectedData();
      }
  }

  private void setData()
  {   
    data = new String[row_size][column_size];     
    ctq = new CXMTableQuerier();   
    ctq.setFileStatus(project,p_arc,null,column_list);    
    last_position = first_position + row_size;    
    for (int i=first_position; i<last_position;i++)
    {
        temp_data = ctq.getStringArrayInColArray(i+1);
        for (int j=0; j<column_size; j++)
        {
           data[i-first_position][j] = temp_data[j];          
        }
    }       
  }

  private void setSelectedData()
  {
    data_v = new Vector();    
    ctq = new CXMTableQuerier();    
    ctq.setFileStatus(project,p_arc,null,column_list);   
    last_position = first_position + row_size;    
    int temp_cnt = 0;
    for (int i=first_position; i<row_numbers;i++)
    {
         if (temp_cnt > row_size)
         {
             break;
         }
         else
         {
             temp_data = ctq.getStringArrayInColArray(i+1);
             if (!cnc.nullCheck(temp_data))
             {                
                query_check = udq.queryColumnCheck(temp_data);                
                if (query_check)
                {
                      for (int j=0; j<column_size; j++)
                      {
                           data_v.addElement(temp_data[j]);
                           //data[i-first_position][j] = temp_data[j];
                      }
                      temp_cnt = temp_cnt + 1;
                }
            }
         }
    }
    resetData();   
  }

  private void setNotSelectedData()
  {
    data_v = new Vector();
    ctq = new CXMTableQuerier();
    ctq.setFileStatus(project,p_arc,null,column_list);
    last_position = first_position + row_size;
    int temp_cnt = 0;
    for (int i=first_position; i<row_numbers;i++)
    {
         if (temp_cnt > row_size)
         {
             break;
         }
         else
         {
             temp_data = ctq.getStringArrayInColArray(i+1);
             if (!cnc.nullCheck(temp_data))
             {               
                query_check = udq.queryColumnCheck(temp_data);              
                if (!query_check)
                {
                      for (int j=0; j<column_size; j++)
                      {
                           data_v.addElement(temp_data[j]);
                           //data[i-first_position][j] = temp_data[j];
                      }
                      temp_cnt = temp_cnt + 1;
                }
            }
         }
    }
    resetData();    
  }

  private void resetData()
  {
     row_size = data_v.size()/column_size;
     data = new String[row_size][column_size];
     String value;
     int row_cnt = 0;
     int column_cnt = 0;
     for (int i=0; i<data_v.size(); i++)
     {
         if (column_cnt == column_size)
         {
             column_cnt = 0;
             row_cnt = row_cnt + 1;  
         }
         value = (String) data_v.elementAt(i);         
         data[row_cnt][column_cnt] = value;
         column_cnt = column_cnt + 1;        
     }

  }

  public int setFilterValueQueryInitStatus (java.lang.String i_name) {
    column_name = i_name;
    fvq = new XMDNodeFilterValueQuery();
    return fvq.setFileStatus(project, p_arc, column_name);
  }

  public void setFilterValueReadStatus (int status) {
    fvq.setFilterValueReadStatus(status);
  }

  public void setFilterValue () {
    fvq.setFilterValue();
  }

  public void filterValueQueryStatusClose () {
    fvq.close();
  }

  public void setQueryArray(String[] valid_parse, int[] valid_index, String[] valid_opt, String[] valid_ser)
  {     
     udq = new XMDNodeUserSetDataQuery();  
     udq.setQueryArray(valid_parse, valid_index, valid_opt, valid_ser);   
  }

  public void newRowSet (boolean new_row_set)
  {
    row_set = new_row_set;
  }

  public java.lang.String[] getColumnList () {
    return column_list;
  }

  public java.lang.String[][] getData () {      
    return data;
  }

  public java.lang.String[] getColumnType () {
    return column_type;
  }

  public java.lang.String[] getAverageValue () {
    return average_value;
  }

  public java.lang.String[] getMaxValue ()
  {
    return max_value;
  }

  public java.lang.String[] getMinValue ()
  {
    return min_value;
  }

  public java.lang.String[] getMaxFreqValue ()
  {
    return max_frequency_value;
  }

  public java.lang.String[] getMissPercent ()
  {
    return miss_percent;
  }

  public java.lang.String[] getFilterValue ()
  {
    // IMPLEMENT:
    return fvq.getFilterValue();
  }

  public java.lang.String[] getFrequencyValue () {
    // IMPLEMENT:
    return fvq.getFrequencyValue();
  }

  public java.lang.String[] getPercentValue () {
    // IMPLEMENT:
    return fvq.getPercentValue();
  }

  public void setTransStatus (int status) {
    trans_status = status;
  }

  public int GetPBMin () {
    SetPBMin();
    return m_pbMin;
  }

  public int GetPBMax () {
      SetPBMax();
    return m_pbMax;
  }

  public int GetPBValue () {
    return m_pbValue;
  }

  public int PerformRunning () {    
    if (trans_status==0)
    {
        return delete();
    }
    else 
    {
        return save();
    }
  }

  public int Run () {
      m_bStop = false;
      m_bAlive = true;
      int r = PerformRunning();
      m_bAlive = false;
      m_bStop = true;
      return(r);
  }

  private int delete() {

      new_row_number = 0;

      ctq = new CXMTableQuerier();
      ctq.setFileStatus(project,p_arc,null,column_list);
      XMDNodeNewIndexCreate nic = new XMDNodeNewIndexCreate();
      nic.setFileStatus(project, n_arc, row_index, new_row_index, row_numbers);
      for (int x = m_pbMin; KeepRunning()&&x <= m_pbMax; x++)
      {
          m_pbValue = x;
          temp_data = ctq.getStringArrayInColArray(x);
          if (!cnc.nullCheck(temp_data))
          {
              query_check = udq.queryColumnCheck(temp_data);              
              if (!query_check)
              {
                 nic.setNewRowIndex(x);
                 new_row_number = new_row_number + 1;
              }
          }
      }
      nic.close();
      return 0;
  }

  private int save()
  {
      new_row_number = 0;
      ctq = new CXMTableQuerier();
      ctq.setFileStatus(project,p_arc,null,column_list);
      XMDNodeNewIndexCreate nic = new XMDNodeNewIndexCreate();
      nic.setFileStatus(project, n_arc, row_index, new_row_index, row_numbers);
      for (int x = m_pbMin; KeepRunning()&&x <= m_pbMax; x++)
      {
          m_pbValue = x;
          temp_data = ctq.getStringArrayInColArray(x);
          if (!cnc.nullCheck(temp_data))
          {
              query_check = udq.queryColumnCheck(temp_data);             
              if (query_check)
              {
                 nic.setNewRowIndex(x);
                 new_row_number = new_row_number + 1;
              }
          }
      }
      nic.close();
      return 0;
  }
  
  public boolean KeepRunning () 
  {
    return(!m_bStop);
  }

  public boolean IsRunning () {
    return(m_bAlive);
  }

  public void StopRunning () {
     m_bStop = true;
  }

  private void SetPBMin ()
  {
    m_pbMin = 1;
  }

  private void SetPBMax ()
  {
    m_pbMax = row_numbers;
  }
 
//  public static ServerMonitorPage monitor = null;
//  private void init() {
//    java.util.ResourceBundle res = java.util.ResourceBundle.getBundle("xmminer.xmserver.ServerResources");
//    String name = "XMBDNodeDataQuery";
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