package xmminer.xmlib.xmfilecompute;

import java.io.*;
import xmminer.xmlib.xmtable.*;

public class CXMGetUniqueColumnValue
{

  private String root_path = System.getProperty("minerdir") + "/xmminer/user/";//"/XM-Miner/xmminer/user"); ¼öÁ¤ 2002.09.22
  private String index_path = "/index/";
  private String set_index_path;
  private String project;

  private byte[] unique_byte;
  private byte[] null_byte;
  private byte[] out_byte;
  private byte[] index_byte;
  private String f_name = "";
  private String c_name = "";
  private String out_string = "";
  private int byte_size;
  private int t_row_number;
  private int row_numbers;
  private int number_index;
  private int column_number;
  private int ins_number;
  private int out_int_value;
  private int temp_int_value;
  private int factor_int;
  private int new_size;
  private int old_size;
  private long pos;
  private boolean tree_create_check;
  private File num_index_file;
  private RandomAccessFile nif;
  private CXMTableQuerier ctq;
  private CXMFilteredValueCount cfc;

  public void setFileStatus(String project_name, String file_name, String column_name)
  {
    project = project_name;
    set_index_path = root_path + project + index_path;

    f_name = file_name;
    c_name = column_name;

	fileOpen();
    index_byte = new byte[byte_size];
  }

  private void fileOpen()
  {
    try
    {
      num_index_file = new File(set_index_path+f_name+".nidx");
      nif = new RandomAccessFile(num_index_file,"r");
      ctq = new CXMTableQuerier();
      ctq.setFileStatus(project,f_name,c_name,null);
      CXMMetaDataReader cmr = new CXMMetaDataReader();
      cmr.setFileStatus(project,f_name);
      String[] column_list = cmr.getProfiles("COLUMN_LIST");
    /*  String[] column_index = cmr.getProfiles("COLUMN_INDEX");
      for (int i=0; i<column_list.length; i++)
      {
        if (column_list[i].equals(c_name))
        {
          column_number = Integer.parseInt(column_index[i]);
          break;
        }
      } */
      row_numbers = Integer.parseInt(cmr.getProfile("NUMBER_OF_ROWS"));
      byte_size = (int) nif.length()/row_numbers;
      cmr.close();
      null_byte = new byte[byte_size];
      for (int j=0; j<byte_size; j++)
      {
        null_byte[j] = 0;
      }
      cfc = new CXMFilteredValueCount();
      cfc.setFileStatus(project,f_name,c_name,row_numbers,true);

    } catch (Exception foe)
      {
        pm("4444 file_open_error",foe.getMessage());
      }
  }

  public void close()
  {
    try
    {
      nif.close();
      ctq.close();
      cfc.close();
    } catch (Exception fce)
      {
        pm("file_close_error",fce.getMessage());
      }
  }

  public String getUniqueValueSet(int row_number)
  {
    out_byte = getByte(row_number);
    if (compareByte(out_byte,null_byte)==0)
    {
      out_string = null;
    }
    else
    {
      out_string = new String(out_byte);
    }
    return out_string;
  }

  public String nextValueSet()
  {
    out_byte = nextByte();
    if (compareByte(out_byte,null_byte)==0)
    {
      out_string = null;
    }
    else
    {
      out_string = new String(out_byte);
    }
    return out_string;
  }


  private byte[] getByte(int row_number)
  {
    t_row_number = row_number;
    if (t_row_number > row_numbers)
    {
      return null_byte;
    }
    //number_index = getNumberIndex(t_row_number);
    //System.out.println("number_index="+number_index);
    unique_byte = ctq.getByteInColumn(t_row_number);
    ins_number = cfc.find(unique_byte);
    if (ins_number==0)
    {
      cfc.insert(unique_byte);
      return unique_byte;
    }
    else
    {
      return nextByte();
    }
  }

  private byte[] nextByte()
  {
    t_row_number = t_row_number+1;
    if (t_row_number > row_numbers)
    {
      return null_byte;
    }
    //number_index = getNumberIndex(t_row_number);
    unique_byte = ctq.getByteInColumn(t_row_number);
    ins_number = cfc.find(unique_byte);
    if (ins_number==0)
    {
      cfc.insert(unique_byte);
      return unique_byte;
    }
    else
    {
      return nextByte();
    }
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

  private int compareByte(byte[] new_byte, byte[] old_byte)
  {
    new_size = new_byte.length;
    old_size = old_byte.length;
    if (new_size>old_size)
    {
    	return 1;
    }
    else if (new_size<old_size)
    {
    	return -1;
    }
    else
    {
    	for (int i=0; i<new_size;)
    	{
    	    if (new_byte[i]>old_byte[i])
    	    {
    	    	return 1;
    	    }
    	    else if (new_byte[i]<old_byte[i])
    	    {
    	    	return -1;
    	    }
    	    else
    	    {
    	    	i++;
    	    }
    	}
    }
    return 0;
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

}