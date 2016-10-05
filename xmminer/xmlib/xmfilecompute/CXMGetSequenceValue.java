package xmminer.xmlib.xmfilecompute;

import java.io.*;
import xmminer.xmlib.xmtable.*;
import xmminer.xmlib.xmfileprocess.*;
import java.util.Vector;
import java.util.*;

public class CXMGetSequenceValue
{

  private String root_path = System.getProperty("minerdir") + "/xmminer/user/";//"/XM-Miner/xmminer/user"); ¼öÁ¤ 2002.09.22
  private String index_path = "/index/";
  private String set_index_path;
  private String project;

  private byte[] index_byte;
  private String f_name;
  private String c_name;
 //private String trans_value;
  private String old_ord_value;
  private String new_ord_value;
  private String transaction_value;
  private String column_seq;
  private String[] trans_value_arr;
  private String[] column_list;
  private String[] new_column_list;
  private String[] hash_value;
  private String[] trans_field_arr;
  private int row_numbers;
  private int byte_size;
  private int out_int_value;
  private int temp_int_value;
  private int factor_int;
  private int number_index;
  private long pos;
  private File sort_index_file;
  private RandomAccessFile sif;
  private CXMTableQuerier ctq;
  private Vector result_v;
  private Hashtable column_h = new Hashtable();

  private CXMNullCheck cnc = new CXMNullCheck();
  private CXMMetaDataReader cdr;

  public void setFileStatus(String project_name, String file_name)
  {
    project = project_name;
    set_index_path = root_path + project + index_path;

    f_name = file_name;
    result_v = new Vector();
    getMetaData();
    trans_field_arr = new String[3];
  }

  public void setOrderingColumnName(String ordering_column_name)
  {
      c_name = ordering_column_name;
      hash_value = (String[]) column_h.get(c_name);
      column_seq = hash_value[0];
      if (hash_value[1].equals("not_sorted"))
      {
         CXMColumnSort ccs = new CXMColumnSort();
         ccs.sort(project,f_name,c_name);
         ccs.close();
      }
      fileOpen();
      index_byte = new byte[byte_size];
  }

  private void getMetaData()
  {
     cdr = new CXMMetaDataReader();
     cdr.setFileStatus(project,f_name);
     row_numbers = Integer.parseInt(cdr.getProfile("NUMBER_OF_ROWS"));
     column_list = cdr.getProfiles("OLD_COLUMN_LIST");
     new_column_list = cdr.getProfiles("NEW_COLUMN_LIST");
     if (!cnc.nullCheck(column_list))
     {
        getBasicMetaData();
     }
     if (!cnc.nullCheck(new_column_list))
     {
        getNewMetaData();
     }
     cdr.close();
  }

  private void getBasicMetaData()
  {
     String[] column_index_list = cdr.getProfiles("COLUMN_INDEX");
     String[] column_property;
     String column_name;
     for (int i=0; i<column_list.length; i++)
     {
        hash_value = new String[2];
        column_name = column_list[i];
        column_property = cdr.getProfiles(column_name);
        hash_value[0] = column_index_list[i];
        hash_value[1] = column_property[4];
        column_h.put(column_name, hash_value);
     }
  }

  private void getNewMetaData()
  {
     String[] new_column_index_list = cdr.getProfiles("NEW_COLUMN_INDEX_LIST");
     String[] column_property;
     String column_name;
     hash_value = new String[2];
     for (int i=0; i<new_column_list.length; i++)
     {
        hash_value = new String[2];
        column_name = new_column_list[i];
        column_property = cdr.getProfiles(column_name);
        hash_value[0] = new_column_index_list[i];
        hash_value[1] = column_property[4];
        column_h.put(column_name, hash_value);
     }
  }

  private void fileOpen()
  {
    try
    {
      sort_index_file = new File(set_index_path+f_name+"_"+column_seq+".sidx");
      sif = new RandomAccessFile(sort_index_file,"r");
      ctq = new CXMTableQuerier();
      byte_size = (int) sif.length()/row_numbers;
    } catch(Exception re)
      {
        pm("file_open_error=",re.getMessage());
      }
  }

  public void close()
  {
    try
    {
      ctq.close();
      sif.close();
    } catch(Exception ce)
      {
        pm("file_close_error=",ce.getMessage());
      }
  }

  public Vector getSequenceTransaction(String trans_field, String trans_field_val, String target_field)
  {
    result_v.removeAllElements();
  /*  hash_value = new String[2];
    hash_value = (String[]) column_h.get(c_name);
    trans_field_arr[0] = Integer.parseInt(hash_value[0]);
    hash_value = (String[]) column_h.get(trans_field);
    trans_field_arr[1] = Integer.parseInt(hash_value[0]);
    hash_value = (String[]) column_h.get(target_field);
    trans_field_arr[2] = Integer.parseInt(hash_value[0]); */
    trans_field_arr[0] = c_name;
    trans_field_arr[1] = trans_field;
    trans_field_arr[2] = target_field;
    old_ord_value = "";
    transaction_value = "";
    ctq.setFileStatus(project,f_name,null,trans_field_arr);
    int i;
    for (i=1; i<row_numbers; i++)
    {
      trans_value_arr = getValueSetInColSet(i);
      if (trans_field_val.equals(trans_value_arr[1]))
      {
         new_ord_value = trans_value_arr[0];
         if (new_ord_value.equals(old_ord_value))
         {
            transaction_value = transaction_value+","+trans_value_arr[2];
         }
         else
         {
            if (!transaction_value.equals(""))
            {
                result_v.addElement(transaction_value);
            }
            transaction_value = trans_value_arr[2];
            old_ord_value = new_ord_value;
         }
      }
      else
      {
         if (!transaction_value.equals(""))
         {
              result_v.addElement(transaction_value);
              transaction_value="";
         }
      }
    }
    trans_value_arr = getValueSetInColSet(i);
    if (trans_field_val.equals(trans_value_arr[1]))
    {
       new_ord_value = trans_value_arr[0];
       if (new_ord_value.equals(old_ord_value))
       {
          transaction_value = transaction_value+","+trans_value_arr[2];
       }
       else
       {
          transaction_value = trans_value_arr[2];
       }
       result_v.addElement(transaction_value);
    }
    else
    {
       if (!transaction_value.equals(""))
       {
            result_v.addElement(transaction_value);
       }
    }
    return result_v;
  }

  private String[] getValueSetInColSet(int i_num)
  {
    number_index = getSortedRowNumber(i_num);
    return ctq.getStringArrayInColArray(number_index);
  }

  private int getSortedRowNumber(int i_int)
  {
    pos = (long) (i_int-1)*byte_size;
    try
    {
      sif.seek(pos);
      for (int i=0; i<byte_size; i++)
      {
        index_byte[i] = sif.readByte();
      }
    } catch (Exception se)
      {
        pm("sorted_number_read_error",se.getMessage());
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


  private void pm(String i_title, String i_msg)
  {
    System.out.println(i_title+"="+i_msg);
  }


}