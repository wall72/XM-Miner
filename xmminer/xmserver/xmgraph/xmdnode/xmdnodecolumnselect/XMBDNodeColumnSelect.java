package xmminer.xmserver.xmgraph.xmdnode.xmdnodecolumnselect;

import xmminer.xmlib.xmtable.*;
import xmminer.xmserver.*;
import xmminer.xmserver.xmgraph.xmdnode.xmdatamanage.*;

public class XMBDNodeColumnSelect 
{
  private String current_name = "XMDNodeColumnSelect";  
  private String[] column_list;
  private String[] column_type;
  private String[] average_value;
  private String[] max_value;
  private String[] min_value;
  private String[] max_frequency_value;
  private String[] miss_percent;
  private boolean cal_dialog_open;
  private CXMColumnSelectMetaDataManager cdm;  
  	
  public XMBDNodeColumnSelect(){
    super();
//	init();
  }


  public int setFileStatus (java.lang.String project_name, java.lang.String previous_arc, java.lang.String next_arc) 
  {      
      cdm = new CXMColumnSelectMetaDataManager();      
      int r = cdm.setMetaData(project_name,previous_arc, next_arc, current_name);   
      if (r!=0)
      {
           return(1);
      }
      else
      {
          column_list = cdm.getColumnList();      
          column_type = cdm.getColumnType();
          average_value = cdm.getAverageValue();    
          max_value = cdm.getMaxValue();    
          min_value = cdm.getMinValue();    
          max_frequency_value = cdm.getMaxFreqValue();      
          miss_percent = cdm.getMissPercent();      
          cal_dialog_open = cdm.getCalDialogOpen();	
          return(0);
      }
  }

  public void close () 
  {
    cdm.close();
  }

  public java.lang.String[] getColumnList () 
  {
    return column_list;
  }

  public java.lang.String[] getColumnType () 
  {
    return column_type;
  }

  public java.lang.String[] getAverageValue () 
  {
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
  
  public void setNewColumnList (java.lang.String[] i_str) 
  {
    cdm.setNewColumnList(i_str);
  }
  
  public boolean getCalDialogOpen () 
  {
    return cal_dialog_open;
  }

//  public static ServerMonitorPage monitor = null;
//  private void init() {
//    java.util.ResourceBundle res = java.util.ResourceBundle.getBundle("xmminer.xmserver.ServerResources");
//    String name = "XMBDNodeColumnSelect";
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