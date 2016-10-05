package xmminer.xmlib.xmtable;

import java.io.*;

public class CXMComputeDataSaver
{
  private String root_path = System.getProperty("minerdir") + "/xmminer/user/";//"/XM-Miner/xmminer/user"); ¼öÁ¤ 2002.09.22
  private String data_path = "/data/";   
  private String index_path = "/index/";
  private String set_data_path;
  private String set_index_path;
  private String project;  
  
  private byte[] row_index;
  private byte[] col_index;
  private byte[] num_index;
  private int row_index_size = 0;
  private int col_index_size = 0;
  private int int_size_value = 0;
  private int temp_int_value = 0;
  private int quot_int = 0;
  private int mod_int = 0;
  private int row_numbers = 0;  
  private int byte_size = 0;
  private long temp_long_value = 0;
  private long quot_long = 0;
  private long mod_long = 0;
  private long d_pos = 0;
  private long t_pos = 0;
  private File data_file;
  private File row_index_file;
  private File col_index_file;
  private File num_index_file;
  private FileOutputStream fos;
  private FileOutputStream ris;
  private FileOutputStream cis;
  private FileOutputStream nis;  

  ///*** declare and set init status ***
  public CXMComputeDataSaver()
  {}

  public void setFileStatus(String project_name, String file_name, int i_number)
  {   
    project = project_name;
    set_data_path = root_path + project + data_path;
    set_index_path = root_path + project + index_path;
    setInitStatus(5,i_number);   
    createFileOpen(file_name);
  }

  private void setInitStatus(int index_array_limit, int i_num)
  {
    d_pos = 0;
    row_index_size = index_array_limit;
    col_index_size = 2;
    row_index = new byte[row_index_size];
    col_index = new byte[col_index_size];
    byte_size = calByteSize(i_num);
    num_index = new byte[byte_size];
  }

 private void createFileOpen(String file_name)
  {
    try
    {
      data_file = new File(set_data_path+file_name+".data");
      row_index_file = new File(set_index_path+file_name+".ridx");
      col_index_file = new File(set_index_path+file_name+".cidx");
      num_index_file = new File(set_index_path+file_name+".nidx");
      fos = new FileOutputStream(data_file);
      ris = new FileOutputStream(row_index_file);
      cis = new FileOutputStream(col_index_file);
      nis = new FileOutputStream(num_index_file);
    } catch(Exception de)
      {System.out.println("create_file_open_error="+de.getMessage());}
  }

  //file close
  public void close()
  {
    //setNumberIndex();
    try
    {
      fos.close();
      ris.close();
      cis.close();
      nis.close();
    } catch(Exception de)
      {System.out.println("file_close_error="+de.getMessage());}
  }

  //data create and insert
  public void createRowData(byte[] i_data, int[] i_col_index)
  {
    try
    {
      setByteValue(d_pos);
      setByteValue(i_col_index);
      fos.write(i_data);
      t_pos = (long) i_data.length;
      d_pos = d_pos + t_pos;
      row_numbers = row_numbers + 1;
      setNumberIndex(row_numbers);
    } catch (Exception e)
      {
        System.out.println("create_row_data_error="+e.getMessage());
      }
  }

  //data convert and save
  private void setByteValue(long i_pos)
  {
    temp_long_value = i_pos;
    for (int i=0; i<row_index_size; i++)
    {
      quot_long = temp_long_value/128;
      mod_long = temp_long_value%128;
      row_index[i] = (byte) mod_long;
      temp_long_value = quot_long;
    }
    try
    {
      ris.write(row_index);
    } catch (Exception rie)
      {System.out.println("long_insert_error="+rie.getMessage());}
  }

  private void setByteValue(int[] i_int_arr)
  {
    int_size_value = 0;
    for (int i=0; i<i_int_arr.length; i++)
    {
      int_size_value = int_size_value+i_int_arr[i];
      temp_int_value = int_size_value;
      for (int j=0; j<col_index_size; j++)
      {
        quot_int = temp_int_value/128;
        mod_int = temp_int_value%128;
        col_index[j] = (byte) mod_int;
        temp_int_value = quot_int;
      }
      try
      {
        cis.write(col_index);
      } catch (Exception cie)
        {System.out.println("int_insert_error="+cie.getMessage());}
    }
  }

  private void setNumberIndex(int i_num)
  {   
      temp_int_value = i_num;
      for (int j=0; j<byte_size; j++)
      {
        quot_int = temp_int_value/128;
        mod_int = temp_int_value%128;
        num_index[j] = (byte) mod_int;
        temp_int_value = quot_int;
      }
      try
      {
        nis.write(num_index);
      } catch (Exception nie)
        {
          System.out.println("num_index_create_error="+nie.getMessage());
        }    
  }

  private int calByteSize(int i_int)
  {
    temp_int_value = i_int;
    quot_int = 0;
    mod_int = 0;
    int cnt = 0;
    do
    {
      quot_int = temp_int_value/128;
      mod_int = temp_int_value%128;
      temp_int_value = quot_int;
      cnt++;
    }
    while(quot_int!=0);
    return cnt;
  }
  //+++ end ***

}
