package xmminer.xmlib.xmtable;

import java.io.*;
import java.util.Vector;

public class CXMNewDataQuerier
{
  
  private String root_path = System.getProperty("minerdir") + "/xmminer/user/";//"/XM-Miner/xmminer/user"); ¼öÁ¤ 2002.09.22
  private String index_path = "/index/";
  private String set_index_path;
  private String project;    
  
  private CXMTableReader ctr;
  private CXMMetaDataReader cmr;
  private File num_index_file;
  private RandomAccessFile nif;
  private Vector trans_v = new Vector();
  private Vector ass_trans_v = new Vector();
  private Vector eps_v = new Vector();
  private Vector col_v = new Vector(); 
  private String[] c_name_list;
  private String[] column_list;
  private String[] column_index;
  private String[] trans_value_arr;
  private String[] column_property;  
  private String new_value = "";
  private String f_name = "";
  private String c_name = "";
  private String sort_property = "sorted";
  private String column_seq;
  private String row_index;
  private String str_value;
  private int number_index;
  private int t_row_number;
  private int row_numbers;
  private int byte_size;
  private int column_length;
  private int select_column_length;
  private int start_int;
  private int end_int;
  private int time_int;
  private int out_int_value;
  private int temp_int_value;
  private int factor_int;
  private int column_number;
  private int[] selected_column_index;
  private double double_value;
  private double max;
  private double min;
  private long pos;
  private boolean column_set = false;
  private boolean cal_max_init = false;
  private boolean cal_min_init = false;
  private byte[] index_byte;

  public CXMNewDataQuerier()
  {
  }

  public void setFileStatus(String project_name, String arc_name, String column_name, String[] column_name_list)
  {
    
    project = project_name;
    set_index_path = root_path + project + index_path;
    
    cmr = new CXMMetaDataReader();
    cmr.setFileStatus(project,arc_name);
    f_name = cmr.getProfile("NEW_DATA_FILE");
    column_list = cmr.getProfiles("NEW_COLUMN_LIST");      
    column_index = cmr.getProfiles("NEW_COLUMN_INDEX");
    row_index = cmr.getProfile("NEW_ROW_INDEX");
    row_numbers = Integer.parseInt(cmr.getProfile("NUMBER_OF_ROWS"));
    column_length = column_list.length;
    if (!nullCheck(column_name))
    {
      c_name = column_name;
      column_property = cmr.getProfiles(c_name);
      for (int i=0; i<column_length; i++)
      {
        if (column_list[i].equals(c_name))
        {
          column_seq = "new_"+column_index[i];
          column_number = Integer.parseInt(column_seq);
        }
      }
      column_set = true;
    }
    if (!nullCheck(column_name_list))
    {
      c_name_list = column_name_list;
      select_column_length = c_name_list.length;
      selected_column_index = new int[select_column_length];
      for (int j=0; j<select_column_length; j++)
      {
        for (int k=0; k<column_length; k++)
        {
          if (column_list[k].equals(c_name_list[j]))
          {
            selected_column_index[j] = Integer.parseInt(column_index[k]);
            break;
          }
        }
      }
    }
    col_v = new Vector();
    for (int j=0; j<column_length; j++)
    {
      col_v.addElement(column_list[j]);
    }
    cmr.close();
    fileOpen();
    index_byte = new byte[byte_size];
  }
  
  public void setSelectColumnIndex(int i_column_index)
  {
       column_number = i_column_index;	
  }  
  
  public void setSelectionColumnListIndex(int[] i_column_list_index)
  {
      	selected_column_index = i_column_list_index;
  }

  private void fileOpen()
  {
    try
    { 
      if (column_set)
      {
        if (column_property[4].equals(sort_property))
        {
          num_index_file = new File(set_index_path+row_index+"_"+column_seq+".sidx");
        }
        else
        {
          num_index_file = new File(set_index_path+row_index+".nidx");
        }
      }
      else
      {
        num_index_file = new File(set_index_path+row_index+".nidx");
      }
      nif = new RandomAccessFile(num_index_file,"r");
      ctr = new CXMTableReader();
      ctr.setFileStatus(project,f_name);
      byte_size = (int) nif.length()/row_numbers;
      cmr.close();
    } catch(Exception re)
      {
        pm("file_open_error",re.getMessage());
      }
  }

  public void close()
  {
    try
    {
      nif.close();
      ctr.close();
    } catch(Exception ce)
      {
        pm("close_error",ce.getMessage());
      }
  }

 /* private void initVector()
  {
    trans_v.removeAllElements();
    ass_trans_v.removeAllElements();
    seq_v.removeAllElements();
    eps_v.removeAllElements();
    column_list.removeAllElements();
  }   */

  public Vector getTransaction(int row_number)
  {
    number_index = getNumberIndex(row_number);
    trans_v.removeAllElements();    
    trans_value_arr = ctr.getStringArray(number_index);
    for (int i=0; i<column_length; i++)
    {
      new_value = column_list[i]+"_"+trans_value_arr[i];
      trans_v.addElement(new_value);
    }
    return trans_v;
  }
  
  public Vector getTransactionInColumnList(int row_number)
  {
    number_index = getNumberIndex(row_number);
    trans_v.removeAllElements();    
    trans_value_arr = ctr.getStringArrayInColArray(number_index,selected_column_index);
    for (int i=0; i<select_column_length; i++)
    {
      new_value = c_name_list[i]+"_"+trans_value_arr[i];
      trans_v.addElement(new_value);
    }
    return trans_v;	
  }
  
  public String[] getTransactionValue(int row_number)
  {
    number_index = getNumberIndex(row_number);   
    trans_value_arr = ctr.getStringArray(number_index);  
    return trans_value_arr;
  }  
  
  public String[] getTransactionValueInColumnList(int row_number)
  {
    number_index = getNumberIndex(row_number);   
    trans_value_arr = ctr.getStringArrayInColArray(number_index,selected_column_index);
    return trans_value_arr;
  } 

  public Vector getAssociationTransaction
  (String trans_field, String trans_field_val, String target_field)
  {
    ass_trans_v.removeAllElements();
    int[] trans_field_arr = new int[2];
    trans_field_arr[0] = col_v.indexOf(trans_field)+1;
    trans_field_arr[1] = col_v.indexOf(target_field)+1;
    for (int i=1; i<row_numbers+1; i++)
    {
      trans_value_arr = getValueSetInColSet(i,trans_field_arr);
      if (trans_field_val.equals(trans_value_arr[0]))
      {
        if (!ass_trans_v.contains(trans_value_arr[1]))
        {
          ass_trans_v.addElement(trans_value_arr[1]);
        }
      }
    }
    return ass_trans_v;
  }
  
  public double getMaxValue()
  {    
    for (int i=1; i<row_numbers+1; i++)
    {
       number_index = getNumberIndex(i);
  	str_value = ctr.getStringInColumn(number_index,column_number);
  	if (!missCheck(str_value))
  	{
  	    double_value = Double.parseDouble(str_value);
  	    calMax(double_value);	
  	}
    }
    return max;
  }

  public double getMinValue()
  {
    for (int i=1; i<row_numbers+1; i++)
    {
       number_index = getNumberIndex(i);
  	str_value = ctr.getStringInColumn(number_index,column_number);
  	if (!missCheck(str_value))
  	{
  	    double_value = Double.parseDouble(str_value);
  	    calMin(double_value);	
  	}
    }
    return min; 	
  }
  
  private boolean missCheck(String i_value)
  {
       if (i_value==null)
       {
  	    return true;
       }
  	else
       {
  	    if (i_value.equals(""))
  	    {
  	        return true;	
  	    }
  	    else
  	    {
  	        return false;
  	    }
       }  		  	 	
  }  
  
  private void calMax(double i_value)
  {    
      if (!cal_max_init)
      {
          max = i_value;
          cal_max_init = true;
      }
      else
      {
          if (max<=i_value)
          {
              max = i_value;
          }
      }    
  }

  private void calMin(double i_value)
  {
      if (!cal_min_init)
      {
          min = i_value;
          cal_min_init = true;
      }
      else
      {
          if (min>=i_value)
          {
             min = i_value;
          }    
      }
  }

  private String[] getValueSetInColSet(int i_num, int[] i_arr)
  {
    number_index = getNumberIndex(i_num);
    return ctr.getStringArrayInColArray(number_index,i_arr);
  }
  
  public String getValueInColumn(int row_num, int col_num)
  {
    number_index = getNumberIndex(row_num);
    return ctr.getStringInColumn(number_index,col_num);
  }

  public Vector getEpisodeTransaction(String time_field, String start_time, String end_time, String target_field)
  {
    eps_v.removeAllElements();
    int[] trans_field_arr = new int[2];
    trans_field_arr[0] = col_v.indexOf(time_field)+1;
    trans_field_arr[1] = col_v.indexOf(target_field)+1;
    start_int = Integer.parseInt(start_time);
    end_int = Integer.parseInt(end_time);
    for (int i=1; i<row_numbers+1; i++)
    {
      trans_value_arr = getValueSetInColSet(i,trans_field_arr);
      time_int = Integer.parseInt(trans_value_arr[0]);
      if ((time_int>=start_int)&&(time_int<end_int))
      {
        eps_v.addElement(trans_value_arr[1]);
      }
    }
    return eps_v;
  }

  public byte[] getByte(int row_number)
  {
    number_index = getNumberIndex(row_number);
    return ctr.getByte(number_index);
  }

  public String getString(int row_number)
  {
    number_index = getNumberIndex(row_number);
    return ctr.getString(number_index);
  }

  public byte[] getByteInColumn(int row_number)
  {
    number_index = getNumberIndex(row_number);
    return ctr.getByteInColumn(number_index,column_number);
  }

  public double getDoubleInColumn(int row_number)
  {
    number_index = getNumberIndex(row_number);
    return ctr.getDoubleInColumn(number_index,column_number);
  }

  public int getIntInColumn(int row_number)
  {
    number_index = getNumberIndex(row_number);
    return ctr.getIntInColumn(number_index,column_number);
  }

  public String getStringInColumn(int row_number)
  {
    number_index = getNumberIndex(row_number);
    return ctr.getStringInColumn(number_index,column_number);
  }

  public byte[] getByteInColArray(int row_number)
  {
    number_index = getNumberIndex(row_number);
    return ctr.getByteInColArray(number_index,selected_column_index);
  }

  public double[] getDoubleArrayInColArray(int row_number)
  {
    number_index = getNumberIndex(row_number);
    return ctr.getDoubleArrayInColArray(number_index,selected_column_index);
  }

  public int[] getIntArrayInColArray(int row_number)
  {
    number_index = getNumberIndex(row_number);
    return ctr.getIntArrayInColArray(number_index,selected_column_index);
  }

  public String[] getStringArrayInColArray(int row_number)
  {
    number_index = getNumberIndex(row_number);
    return ctr.getStringArrayInColArray(number_index,selected_column_index);
  }

  private int getNumberIndex(int i_int)
  {
    pos = (long) (i_int-1)*byte_size;
    try
    {
      nif.seek(pos);
      for (int i=0; i<byte_size; i++)
      { 
        index_byte[i] = nif.readByte();
      }
    } catch (Exception ne)
      {
        pm("number_index_read_error",ne.getMessage());
      }
    return setIntValue(index_byte);
  }

  private int setIntValue(byte[] i_byte)
  {
    out_int_value = 0;
    factor_int = 1;
    for (int i=0; i<byte_size; i++)
    {
    	   temp_int_value = (int) i_byte[i];
    	   out_int_value = out_int_value + temp_int_value*factor_int;
    	   factor_int = factor_int*128;
     }
     return out_int_value;
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

  private void pm(String i_title, String i_msg)
  {
    System.out.println(i_title+"="+i_msg);
  }
}
