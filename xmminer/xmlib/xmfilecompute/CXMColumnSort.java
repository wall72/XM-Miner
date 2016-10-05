package xmminer.xmlib.xmfilecompute;

import java.io.*;
import java.util.Vector;
import xmminer.xmlib.xmtable.*;

public class CXMColumnSort
{

  private String root_path = System.getProperty("minerdir") + "/xmminer/user/";//"/XM-Miner/xmminer/user"); ¼öÁ¤ 2002.09.22
  private String index_path = "/index/";
  private String set_index_path;
  private String project;

  private byte[] left_byte;
  private byte[] right_byte;
  private String f_name = "";
  private String c_name = "";
  private String left_str = "";
  private String right_str = "";
  private String center_str = "";
  private String type = "";
  private String STRING = "STRING";
  private String column_seq;
  private String[] column_list;
  private String[] new_column_list;
  private String[] column_index_list;
  private String[] new_column_index_list;
  private String[] column_property;
  private int row_numbers;
  private int byte_size;
  private int pivot;
  private int out_int_value;
  private int factor_int;
  private int temp_int_value;
  private long left_pos;
  private long right_pos;
  private double left_double;
  private double right_double;
  private double center_double;
  private File data_file;
  private File row_index_file;
  private File col_index_file;
  private File sort_index_file;
  private RandomAccessFile sif;
  private CXMMetaDataReader cmr;
  private CXMTableQuerier ctq;
  private Vector column_v;
  private Vector new_column_v;

  public void sort(String project_name, String file_name, String column_name)
  {

    project = project_name;
    set_index_path = root_path + project + index_path;

    f_name = file_name;
    c_name = column_name;
    cmr = new CXMMetaDataReader();
    cmr.setFileStatus(project,f_name);
    column_list = cmr.getProfiles("OLD_COLUMN_LIST");
    if (!nullCheck(column_list))
    {
        column_v = new Vector();
        for (int i=0; i<column_list.length; i++)
        {
            column_v.addElement(column_list[i]);
        }
        column_index_list = cmr.getProfiles("COLUMN_INDEX");
    }

    new_column_list = cmr.getProfiles("NEW_COLUMN_LIST");
    if (!nullCheck(new_column_list))
    {
        new_column_v = new Vector();
        for (int j=0; j<new_column_list.length; j++)
        {
            new_column_v.addElement(new_column_list[j]);
        }
        new_column_index_list = cmr.getProfiles("NEW_COLUMN_INDEX");
    }

    row_numbers = Integer.parseInt(cmr.getProfile("NUMBER_OF_ROWS"));
    column_property = cmr.getProfiles(c_name);
    type = column_property[0];
    cmr.close();

    if (column_v.contains(c_name))
    {
    	  column_seq = column_index_list[column_v.indexOf(c_name)];
    }
    else
    {
    	  if (new_column_v.contains(c_name))
    	  {
    	      	column_seq = "new_"+new_column_index_list[new_column_v.indexOf(c_name)];
    	  }
    }

    fileOpen();
    setPivot();
    if (type.equals("REAL")||type.equals("INTEGER"))
    {
      doubleSort(1,row_numbers);
    }
    else
    {
      stringSort(1,row_numbers);
    }
    column_property[4] = "sorted";
    CXMMetaDataSaver cms = new CXMMetaDataSaver();
    cms.setFileStatus(project,f_name);
    cms.setProfiles(c_name,column_property);
    cms.close();
  }

  private void fileOpen()
  {
    try
    {
    	row_index_file = new File(set_index_path+f_name+".nidx");
             byte_size = (int) row_index_file.length()/row_numbers;
    	sort_index_file = new File(set_index_path+f_name+"_"+column_seq+".sidx");
    	sif = new RandomAccessFile(sort_index_file,"rw");
    	setNumberIndex();
    	ctq = new CXMTableQuerier();
    	ctq.setFileStatus(project,f_name,c_name,null);
    } catch(Exception foe)
      {
        pm("file_open_error=",foe.getMessage());
      }
  }

  private void setNumberIndex()
  {
      int quot_int = 0;
      int mod_int = 0;
      int temp_int_value = 0;
      byte[] num_index = new byte[byte_size];
      for (int i=1; i<row_numbers+1; i++)
      {
          temp_int_value = i;
          for (int j=0; j<byte_size; j++)
          {
               quot_int = temp_int_value/128;
               mod_int = temp_int_value%128;
               num_index[j] = (byte) mod_int;
               temp_int_value = quot_int;
          }
          try
          {
               sif.write(num_index);
          } catch (Exception nie)
             {
                  System.out.println("sort_index_create_error="+nie.getMessage());
             }
      }
  }


  public void close()
  {
    try
    {
    	 ctq.close();
    	 sif.close();
    } catch(Exception fce)
      {
        pm("file_close_error=",fce.getMessage());
      }
  }

  private void setPivot()
  {

    left_byte = new byte[byte_size];
    right_byte = new byte[byte_size];
    int center_number = java.lang.Math.round((1+row_numbers)/2);
    if (type.equals(STRING))
    {
      left_str = ctq.getStringInColumn(1);
      right_str = ctq.getStringInColumn(row_numbers);
      center_str = ctq.getStringInColumn(center_number);
      if ((left_str.compareTo(center_str)) > 0)
      {
        swap(1,center_number);
      }
      if ((left_str.compareTo(right_str)) > 0)
      {
        swap(1,row_numbers);
      }
      if ((center_str.compareTo(right_str)) > 0)
      {
        swap(center_number,row_numbers);
      }
      swap(center_number,row_numbers-1);
    }
    else
    {
      left_double = ctq.getDoubleInColumn(1);
      right_double = ctq.getDoubleInColumn(row_numbers);
      center_double = ctq.getDoubleInColumn(center_number);
      if (left_double > center_double)
      {
        swap(1,center_number);
      }
      if (left_double > right_double)
      {
        swap(1,row_numbers);
      }
      if (center_double > right_double)
      {
        swap(center_number,row_numbers);
      }
      swap(center_number,row_numbers-1);
    }
  }

 private void stringSort(int left_number, int right_number)
 {
  /*  try
    {
      sif.seek((long)(right_number-1)*byte_size);
      for (int i=0; i<byte_size; i++)
      {
        right_byte[i] = sif.readByte();
      }
      pivot = setIntValue(right_byte);
    } catch(Exception ne)
      {
       // pm("pivot_error=",ne.getMessage());
      } */
    pivot = right_number;

    if(right_number > left_number)
    {
      int i = left_number;
      int j = right_number;
      while(true)
      {
        for (;i<right_number;)
        {
          if (compareStringMoreThanOrEqual(i,pivot))
          {
            break;
          }
          else
          {
            i++;
          }
        }
        for (;j>left_number;)
        {
          j--;
          if (compareStringLessThanOrEqual(j,pivot))
          {
            break;
          }
        }

        if(i >= j)
        {
          break;
        }
        swap(i, j);
      }

      if (compareStringMoreThanOrEqual(i,pivot))
      {
        swap(i , right_number);
      }
      stringSort(left_number, i-1);
      stringSort(i+1, right_number);
    }
  }

  private void doubleSort(int left_number, int right_number)
  {

    pivot = right_number;

    if(right_number > left_number)
    {
      int i = left_number;
      int j = right_number;
      while(true)
      {
        for (;i<right_number;)
        {
          if (compareDoubleMoreThanOrEqual(i,pivot))
          {
            break;
          }
          else
          {
            i++;
          }
        }
        for (;j>left_number;)
        {
          j--;
          if (compareDoubleLessThanOrEqual(j,pivot))
          {
            break;
          }
        }

        if(i >= j)
        {
          break;
        }
        swap(i, j);
      }

      if (compareDoubleMoreThanOrEqual(i,pivot))
      {
        swap(i , right_number);
      }
      doubleSort(left_number, i-1);
      doubleSort(i+1, right_number);
    }
  }

  private boolean compareStringMoreThanOrEqual(int left_number, int right_number)
  {

   /* try
    {
      sif.seek((long)(left_number-1)*byte_size);
      for (int i=0; i<byte_size; i++)
      {
        left_byte[i] = sif.readByte();
      }
      left_number = setIntValue(left_byte);
    } catch(Exception ne)
      {
        pm("left_number_error=",ne.getMessage());
      } */

    left_str = ctq.getStringInColumn(left_number);
    right_str = ctq.getStringInColumn(right_number);
    return (left_str.compareTo(right_str)) >= 0;
  }

  private boolean compareStringLessThanOrEqual(int left_number, int right_number)
  {
   /* try
    {
      sif.seek((long)(left_number-1)*byte_size);
      for (int i=0; i<byte_size; i++)
      {
        left_byte[i] = sif.readByte();
      }
      left_number = setIntValue(left_byte);
    } catch(Exception ne)
      {
        pm("left_number_error=",ne.getMessage());
      } */

    left_str = ctq.getStringInColumn(left_number);
    right_str = ctq.getStringInColumn(right_number);
    return (left_str.compareTo(right_str)) <= 0;
  }

  private boolean compareDoubleMoreThanOrEqual(int left_number, int right_number)
  {

    left_double = ctq.getDoubleInColumn(left_number);
    right_double = ctq.getDoubleInColumn(right_number);
    return (left_double >= right_double);
  }

  private boolean compareDoubleLessThanOrEqual(int left_number, int right_number)
  {
    left_double = ctq.getDoubleInColumn(left_number);
    right_double = ctq.getDoubleInColumn(right_number);
    return (left_double <= right_double);
  }

  private void swap(int left_number, int right_number)
  {
    left_pos = (left_number-1)*byte_size;
    right_pos = (right_number-1)*byte_size;
    try
    {
      sif.seek(left_pos);
      for (int i=0; i<byte_size; i++)
      {
        left_byte[i] = sif.readByte();
      }
      sif.seek(right_pos);
      for (int j=0; j<byte_size; j++)
      {
        right_byte[j] = sif.readByte();
      }
      sif.seek(left_pos);
      sif.write(right_byte);
      sif.seek(right_pos);
      sif.write(left_byte);
    } catch (Exception se)
      {
        pm("swap_error=",se.getMessage());
      }
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

  private void pm(String i_title, String i_msg)
  {
    System.out.println(i_title+"="+i_msg);
  }

  private void pba(String i_title, byte[] i_byte)
  {
    System.out.print(i_title + " : ");
    for (int i=0; i<i_byte.length; i++)
    {
    	System.out.print(i_byte[i]+",");
    }
    System.out.print("\n");
  }

  private boolean nullCheck(String[] i_str)
  {
     if (i_str==null)
    {
      return true;
    }
    else
    {
       if(i_str.length == 0)
      {
        return true;
      }
        else
        {
          return false;
        }
    }
  }
}