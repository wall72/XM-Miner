package xmminer.xmserver.xmgraph.xmdnode.xmdnodedataquery;

import java.io.*;
import xmminer.xmlib.xmtable.*;
import xmminer.xmlib.xmcompute.*;
import xmminer.xmlib.xmfileprocess.*;


public class XMDNodeFilterValueQuery
{
  private byte[] index_byte;
  private byte[] value_byte;
  private String project;
  private String p_arc;
  private String column_name;
  private String unique_value;
  private String unique_file_name;
  private String filtering_file;
  private String filtering_arc;
  private String[] filtering_value;
  private String[] frequency_value;
  private String[] percent_value;
  private int status;
  private int row_size = 100;
  private int row_numbers;
  private int value_cnt;
  private int unique_value_numbers;
  private int filter_first_position;
  private int filter_last_position;
  private float value_percent;
  private boolean filtering_check;
  private CXMMetaDataReader cdr;
  private CXMUniqueValueTableReader uvr;
  private CXMNullCheck cnc = new CXMNullCheck();

  public XMDNodeFilterValueQuery()
  {

  }

  public int setFileStatus(String project_name, String previous_arc, String i_name)
  {
      project = project_name;
      p_arc = previous_arc;
      column_name = i_name;
      return setInitStatus();
  }

  public void close()
  {
     uniqueValueTableClose();
  }

  private int setInitStatus()
  {
      getPreviousArcMeta();
      if (filtering_check)
      {
         getFilteringMeta();
         uniqueValueTableOpen();
         unique_value_numbers = uvr.getUniqueValueNumber();
         resetRowSize();
         return(0);
      }
      else
      {
         return(-1);
      }
  }

  private void getPreviousArcMeta()
  {
      cdr = new CXMMetaDataReader();
      cdr.setFileStatus(project,p_arc);
      row_numbers = Integer.parseInt(cdr.getProfile("NUMBER_OF_ROWS"));
      filtering_file = cdr.getProfile("FILTERING_FILE");
      if (cnc.nullCheck(filtering_file))
      {
         filtering_check = false;
      }
      else
      {
         filtering_check = true;
      }
      cdr.close();
  }

  private void getFilteringMeta()
  {
      cdr = new CXMMetaDataReader();
      cdr.setFileStatus(project,filtering_file);
      filtering_arc = cdr.getProfile("filtering_arc");
      unique_file_name = cdr.getProfile(column_name+"_unique_value_file");
      cdr.close();
  }

  private void uniqueValueTableOpen()
  {
      uvr = new CXMUniqueValueTableReader();
      uvr.setFileStatus(project,unique_file_name);
  }

  private void uniqueValueTableClose()
  {
      uvr.close();
  }

  public void setFilterValue()
  {
     filtering_value = new String[row_size];
     frequency_value = new String[row_size];
     percent_value = new String[row_size];
     filter_last_position = filter_first_position + row_size;
     for (int i=0; i<row_size; i++)
     {
         unique_value = uvr.getUniqueValueAt(filter_first_position+i);
         value_cnt = uvr.getValueCountAt(filter_first_position+i);
         setValuePercent();
         filtering_value[i] = unique_value;
         frequency_value[i] = String.valueOf(value_cnt);
         percent_value[i] = String.valueOf(value_percent);
     }
  }

  private void resetRowSize()
  {
      if (unique_value_numbers<row_size)
      {
          row_size = unique_value_numbers;
      }
  }

  private void setValuePercent()
  {
      value_percent = value_cnt*100/row_numbers;
  }

  public void setFilterValueReadStatus (int status)
  {
      if (status==0)
      {
          filter_first_position = 1;
      }
      else if (status==1)
      {
          if (filter_last_position>=row_size)
          {
              filter_first_position = filter_first_position - row_size;
          }
          else
          {
              filter_first_position = 1;
          }
      }
      else if (status==2)
      {
          filter_first_position = filter_last_position;
      }
      else if (status==3)
      {
          if (unique_value_numbers>=row_size)
          {
             filter_first_position = unique_value_numbers - row_size;
          }
          else
          {
             filter_first_position = 1;
          }
      }
  }

  public String[] getFilterValue ()
  {
    return filtering_value;
  }

  public String[] getFrequencyValue ()
  {
    return frequency_value;
  }

  public String[] getPercentValue ()
  {
    return percent_value;
  }

}