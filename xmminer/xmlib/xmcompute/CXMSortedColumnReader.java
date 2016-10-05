package xmminer.xmlib.xmcompute;

import java.io.*;
import xmminer.xmlib.xmtable.*;

public class CXMSortedColumnReader
{
  
  private String root_path = System.getProperty("minerdir") + "/xmminer/user/";//"/XM-Miner/xmminer/user"); ¼öÁ¤ 2002.09.22
  private String index_path = "/index/";
  private String set_index_path;
  private String project;
  
  private byte[] index_byte;
  private String f_name = "";
  private String c_name = ""; 
  private String column_seq;
  private int column_number;
  private int row_numbers;
  private int byte_size;
  private int sorted_row_number;
  private int out_int_value;
  private int temp_int_value;
  private int factor_int;
  private long pos;
  private File sort_index_file;
  private RandomAccessFile sif;
  private CXMTableReader ctr;

  public void setFileStatus(String project_name, String file_name, String column_name)
  {
    
    project = project_name;
    set_index_path = root_path + project + index_path;    
    
    f_name = file_name;
    c_name = column_name;
    CXMMetaDataReader cmr = new CXMMetaDataReader();
    cmr.setFileStatus(project,f_name);
    row_numbers = Integer.parseInt(cmr.getProfile("NUMBER_OF_ROWS"));
    String[] column_list = cmr.getProfiles("COLUMN_LIST");
    String[] column_index = cmr.getProfiles("COLUMN_INDEX");
    for (int i=0; i<column_list.length; i++)
    {
      if (column_list[i].equals(c_name))
      {
        column_seq = column_index[i];
        column_number = Integer.parseInt(column_seq);
        break;
      }
    }
    cmr.close();
    fileOpen();
    index_byte = new byte[byte_size];
  }

  private void fileOpen()
  {
    try
    {
      sort_index_file = new File(set_index_path+f_name+"_"+column_seq+".sidx");
      sif = new RandomAccessFile(sort_index_file,"r");
      ctr = new CXMTableReader();
      ctr.setFileStatus(project,f_name);
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
      ctr.close();
      sif.close();
    } catch(Exception ce)
      {
        pm("file_close_error=",ce.getMessage());
      }
  }

  public byte[] getByte(int row_number)
  {
    sorted_row_number = getSortedRowNumber(row_number);
    return ctr.getByteInColumn(sorted_row_number,column_number);
  }

  public double getDouble(int row_number)
  {
    sorted_row_number = getSortedRowNumber(row_number);
    return ctr.getDoubleInColumn(sorted_row_number,column_number);
  }

  public int getInt(int row_number)
  {
    sorted_row_number = getSortedRowNumber(row_number);
    return ctr.getIntInColumn(sorted_row_number,column_number);
  }

  public String getString(int row_number)
  {
    sorted_row_number = getSortedRowNumber(row_number);
    return ctr.getStringInColumn(sorted_row_number,column_number);
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
        pm("sorted_number_read_error=",se.getMessage());
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