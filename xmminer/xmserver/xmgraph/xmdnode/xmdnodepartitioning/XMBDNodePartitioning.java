package xmminer.xmserver.xmgraph.xmdnode.xmdnodepartitioning;
import java.util.Random;

import xmminer.xmserver.xmgraph.xmdnode.xmdatamanage.*;

public class XMBDNodePartitioning 
{
  private String current_name = "XMDNodePartitioning";
  private String project;
  private String p_arc;
  private String t_arc;
  private String v_arc;
  private String job;   
  private int[][] training_value_array;
  private int[][] validation_value_array;
  private CXMPartitioning cp = new CXMPartitioning();
  private CXMPartitioningMetaDataManager cdm;
  private CXMPartitioningRunMetaDataReader crr;
  private int total_rows;
  private int partition_index;  
  private int selected_index;
  private int training_number_value;
  private int validation_number_value;
  private int m_pbMin;
  private int m_pbMax;
  private int m_pbValue;  
  private boolean m_bStop;
  private boolean m_bAlive;
// Modified by Sun Jee Hun ///////////////////////////////////////////////////////
	private String[] column_list;
//////////////////////////////////////////////////////////////////////////////////

  public XMBDNodePartitioning () 
  {
    super();
//	init();
  }
  
  public int setFileStatus (java.lang.String project_name, 
                            java.lang.String previous_arc, 
                            java.lang.String training_arc, 
                            java.lang.String validation_arc, 
                            java.lang.String job_status) 
 {
    project = project_name;
    p_arc = previous_arc;
    t_arc = training_arc;
    v_arc = validation_arc;
    job = job_status;

	if (job_status.equals("edit"))
    {
         cdm = new CXMPartitioningMetaDataManager();     
         int r = cdm.setMetaData(project,p_arc,t_arc,v_arc,current_name); 

		 if (r!=0)
         {
             return(1);
         }    
         total_rows = cdm.getTotalRows();       
		 
		 // Modified by Sun Jee Hun ///////////////////////////////////////////////////////
		 column_list = cdm.getColumnList();
		 //////////////////////////////////////////////////////////////////////////////////

         cp.setTotalRows(total_rows);                    
    }
    else if (job_status.equals("run"))
    {
        crr = new CXMPartitioningRunMetaDataReader();
        int r = crr.setMetaData(project,p_arc,t_arc,v_arc,current_name);
        if (r!=0)
        {
            return(1);
        }    

		total_rows = crr.getTotalRows();   // crr = new CXMPartitioningRunMetaDataReader();
		
		// Modified by Sun Jee Hun ///////////////////////////////////////////////////////
		column_list = crr.getColumnList();
		//////////////////////////////////////////////////////////////////////////////////

		training_number_value = crr.getTrainingNumber();
        validation_number_value = crr.getValidationNumber();
        partition_index = crr.getPartitionIndex();
        if (partition_index==1)
        {
            training_value_array = crr.getTrainingValue();
            validation_value_array = crr.getValidationValue();    
        }
        cp.setTotalRows(total_rows);    
        cp.setArc(previous_arc, training_arc, validation_arc); 	
    }
    return (0);
  }
    
// Modified by Sun Jee Hun ///////////////////////////////////////////////////////
	public java.lang.String[] getColumnList()
	{
		return column_list;
	}
//////////////////////////////////////////////////////////////////////////////////
  public void close()
  {
  	cdm.close();
  }

  public int calNumToPer (int i_int) 
  {
    return cp.calNumToPer(i_int);
  }

  public int calPerToNum (int i_int) 
  {
    return cp.calPerToNum(i_int);
  }

  public int getTotalRows () 
  {     
     return cp.getTotalRows();
  }  
  
  public void setSequentialPartitioningNumber(String[][] training_number, String[][] validation_number)
  {
      cdm.setTrainingValue(training_number);
      cdm.setValidationValue(validation_number);
  }  
  
  public void setSelectedIndex (int i_index) 
  {
      selected_index = i_index;
      cdm.setSelectedIndex(selected_index);
  }
  
  public void setTrainingNumber (int training_number) 
  {
       training_number_value = training_number;
       cdm.setTrainingNumber(training_number);  
  }

  public void setValidationNumber (int validation_number) 
  {
      validation_number_value = validation_number;  
      cdm.setValidationNumber(validation_number);
  }
  
  public void setTrainingValue (java.lang.String[][] training_value) 
  {   
      cdm.setTrainingValue(training_value);  
  }

  public void setValidationValue (java.lang.String[][] validation_value) 
  {     
      cdm.setValidationValue(validation_value);  
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
     if (partition_index==0)
     {
         m_pbMax = 2*total_rows;
     }
     else if (partition_index==1)
     {
         m_pbMax = training_number_value + validation_number_value;    
     }
  }

  public int GetPBValue () 
  {    
      return m_pbValue;
  }  

  public int PerformRunning()
  {        
      cp.setPrePartitioningStatus(project,partition_index);  
      if (partition_index==0)
      {
          return randomPartition();
      }
      else if (partition_index==1)
      {
          return orderedPartition();
      }
      return(0);
  }
  
  private int randomPartition()
  {
      Random rand = new Random();      
      int rand_number = 0;  
           
      for (int x = m_pbMin; KeepRunning() && x <= m_pbMax;)
      {       
          m_pbValue = x;
          
          if (x<total_rows)
          {       	  
              cp.indexDataCreate(x);	
              x++;
          }   
          else if (x==total_rows)
          {       	    	
              cp.indexDataCreate(x);	
       	 cp.setIndexFile();
       	 x++;
          }
          else if (x<=total_rows+training_number_value)       	  
          {
              rand_number = rand.nextInt(total_rows)+1;                
              if (!cp.dataFind(rand_number))
              { 
                  cp.setTrainingNumberIndex(rand_number);                                 
                  x++;
              }                               
          }
          else
          {
                rand_number = rand.nextInt(total_rows)+1;
                if (!cp.dataFind(rand_number))
                {
                      cp.setValidationNumberIndex(rand_number);            
                      x++;
                 }
          }                       
      }          
      cp.setPostPartitioningStatus();         
      return(0);
  }
  
  private int orderedPartition()
  {       
      int init_num; 
      int end_num;    
      int x = m_pbMin;
      for (int t=0; t<training_value_array.length; t++)
      {
           init_num = training_value_array[t][0];
           end_num = training_value_array[t][1];
           for (int u=init_num; KeepRunning() && u<=end_num; u++)
           {
                m_pbValue = x;
                cp.setTrainingNumberIndex(u);           
                x++;                
           }
      }
      for (int v=0; v<validation_value_array.length;v++)
      {
           init_num = validation_value_array[v][0];
           end_num = validation_value_array[v][1];
           for (int w=init_num; KeepRunning() && w<=end_num; w++)
           {
                m_pbValue = x;
                cp.setValidationNumberIndex(w);
                x++;                 
           }
      }  
      cp.setPostPartitioningStatus();         
      return(0);    
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
//    String name = "XMBDNodePartitioning";
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