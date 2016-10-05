package xmminer.xmlib.xmtable;

import java.io.*;

public class CXMTableReader
{
  
  private String root_path = System.getProperty("minerdir") + "/xmminer/user/";//"/XM-Miner/xmminer/user"); ¼öÁ¤ 2002.09.22
  private String data_path = "/data/";   
  private String index_path = "/index/";
  private String set_data_path;
  private String set_index_path;
  private String project;    
  
  private byte[] row_index;
  private byte[] col_index;
  private byte[] a_row_index;
  private byte[] a_col_index;
  private byte[] out_value_byte;
  private int row_index_size = 0;
  private int col_index_size = 0;
  private int col_index_num = 0;
  private int temp_int_value = 0;
  private int out_int_value = 0;
  private int col_data_size = 0;
  private int arr_data_size = 0;
  private int row_data_size = 0;
  private int init_value = 0;
  private int end_value = 0;
  private int factor_int = 1;
  private int col_num = 0;
  private int col_num_size = 0;
  private int cnt = 0;
  private int outInt;
  private int[] col_num_arr;
  private int[] init_value_arr;
  private int[] data_size_arr;
  private int[] outIntArray;
  private long temp_long_value = 0;
  private long c_pos = 0;
  private long r_pos = 0;
  private long s_pos = 0;
  private long i_pos = 0;
  private long e_pos = 0;
  private long out_long_value = 0;
  private long factor_long = 1;
  private double outDouble = 0;
  private double[] outDoubleArray;
  private String file_name;
  private String outString;
  private String[] outStringArray;
  private File data_file;
  private File index_file;
  private File row_index_file;
  private File col_index_file;
  private RandomAccessFile rdf;
  private RandomAccessFile rif;
  private RandomAccessFile cif; 

  //append variables
  private long ri_pos = 0;
  private long ci_pos = 0;
  private long d_pos = 0;
  private long quot_long = 0;
  private long mod_long = 0;
  private int quot_int = 0;
  private int mod_int = 0;

  ///*** declare and start ***
  public CXMTableReader()
  {}

  public void setFileStatus(String project_name, String filename)
  {
    project = project_name;
    set_data_path = root_path + project + data_path;
    set_index_path = root_path + project + index_path;
    
    //System.out.println("file_name="+filename);
    file_name = filename;
    //System.out.println("file_name="+filename);
    setInitStatus(5);
    fileOpen();
  }
  
  //set init status
  private void setInitStatus(int index_array_limit)
  {
    row_index_size = index_array_limit;
    col_index_size = 2;
    //System.out.println("file_name="+file_name);
    try
    {
      CXMMetaDataReader cmr = new CXMMetaDataReader();      
      cmr.setFileStatus(project,file_name);
      col_index_num = Integer.parseInt(cmr.getProfile("NUMBER_OF_COLUMNS"));
      cmr.close();
    } catch(Exception ce)
      {System.out.println("CXMTableReader : setInitStatus="+ce.getMessage());}
    row_index = new byte[row_index_size];
    col_index = new byte[col_index_size];
  }

  //file open
  private void fileOpen()
  {
    try
    {
      data_file = new File(set_data_path+file_name+".data");
      row_index_file = new File(set_index_path+file_name+".ridx");
      col_index_file = new File(set_index_path+file_name+".cidx");
      rdf = new RandomAccessFile(data_file,"r");
      rif = new RandomAccessFile(row_index_file,"r");
      cif = new RandomAccessFile(col_index_file,"r");
      d_pos = rdf.length();
      ri_pos = rif.length();
      ci_pos = cif.length();
    } catch(Exception de)
      {System.out.println("CXMTableReader : fileOpen_error="+de.getMessage());}
  }

  //file close
  public void close()
  {
    try
    {
      cif.close();
      rif.close();
      rdf.close();
    } catch(Exception de)
      {System.out.println("CXMTableReader : close_error="+de.getMessage());}
  }

  //data read
  public byte[] getByte(int i_row_num)
  {
    r_pos = (long) (i_row_num-1)*row_index_size;
    c_pos = (long) (i_row_num*col_index_num*col_index_size)-col_index_size;
    setRowIndexArray(r_pos);
    s_pos =  setLongValue(row_index);
    setRowOutValueByte();
    return out_value_byte;
  }

  public byte[] nextByte()
  {
    r_pos = r_pos + row_index_size;
    c_pos = c_pos + col_index_num*col_index_size;
    setRowIndexArray(r_pos);
    s_pos =  setLongValue(row_index);
    setRowOutValueByte();
    return out_value_byte;
  }

  public String getString(int i_row_num)
  {
    outString = new String(getByte(i_row_num));
    return outString;
  }

  public String nextString()
  {
    outString = new String(nextByte());
    return outString;
  }

  public byte[] getByteInColumn(int i_row_num, int i_col_num)
  {
    setColNum(i_col_num);
    r_pos = (long) (i_row_num-1)*row_index_size;
    c_pos = (long) (i_row_num-1)*col_index_num*col_index_size;
    setRowIndexArray(r_pos);
    s_pos =  setLongValue(row_index);
    setColOutValueByte();
    return out_value_byte;
  }

  public byte[] nextByteInColumn()
  {
    r_pos = r_pos + row_index_size;
    c_pos = c_pos + col_index_num*col_index_size;
    setRowIndexArray(r_pos);
    s_pos =  setLongValue(row_index);
    setColOutValueByte();
    return out_value_byte;
  }

  public String getStringInColumn(int i_row_num, int i_col_num)
  {
    outString = new String(getByteInColumn(i_row_num,i_col_num));
    return outString;
  }

  public String nextStringInColumn()
  {
    outString = new String(nextByteInColumn());
    return outString;
  }

  public int getIntInColumn(int i_row_num, int i_col_num)
  {
    outInt = Integer.parseInt(new String(getByteInColumn(i_row_num, i_col_num)));
    return outInt;
  }

  public int nextIntInColumn()
  {
    outInt = Integer.parseInt(new String(nextByteInColumn()));
    return outInt;
  }

  public double getDoubleInColumn(int i_row_num, int i_col_num)
  {
    outDouble = Double.parseDouble(new String(getByteInColumn(i_row_num, i_col_num)));
    return outDouble;
  }

  public double nextDoubleInColumn()
  {
    outDouble = Double.parseDouble(new String(nextByteInColumn()));
    return outDouble;
  }

  public byte[] getByteInColArray(int i_row_num, int[] i_col_num_arr)
  {
    setColNumArray(i_col_num_arr);
    r_pos = (long) (i_row_num-1)*row_index_size;
    c_pos = (long) (i_row_num-1)*col_index_num*col_index_size;
    setRowIndexArray(r_pos);
    s_pos =  setLongValue(row_index);
    setColArrOutValueByte();
    return out_value_byte;
  }

  public byte[] nextByteInColArray()
  {
    r_pos = r_pos + row_index_size;
    c_pos = c_pos + col_index_num*col_index_size;
    setRowIndexArray(r_pos);
    s_pos =  setLongValue(row_index);
    setColArrOutValueByte();
    return out_value_byte;
  }

  public String getStringInColArray(int i_row_num, int[] i_col_num_arr)
  {
    outString = new String(getByteInColArray(i_row_num, i_col_num_arr));
    return outString;
  }

  public String nextStringInColArray()
  {
    outString = new String(nextByteInColArray());
    return outString;
  }

  public String[] getStringArray(int i_row_num)
  {
    setColNumOfAll(col_index_num);
    setOutStringArray(getByteInColArray(i_row_num,col_num_arr));
    return outStringArray;
  }

  public String[] nextStringArray()
  {
    setOutStringArray(nextByteInColArray());
    return outStringArray;
  }

  public String[] getStringArrayInColArray(int i_row_num, int[] i_col_num_arr)
  {
    setOutStringArray(getByteInColArray(i_row_num, i_col_num_arr));
    return outStringArray;
  }

  public String[] nextStringArrayInColArray()
  {
    setOutStringArray(nextByteInColArray());
    return outStringArray;
  }

  public int[] getIntArrayInColArray(int i_row_num, int[] i_col_num_arr)
  {
    outString = new String(getByteInColArray(i_row_num, i_col_num_arr));
    setOutIntArray();
    return outIntArray;
  }

  public int[] nextIntArrayInColArray()
  {
    outString = new String(nextByteInColArray());
    setOutIntArray();
    return outIntArray;
  }

  public double[] getDoubleArrayInColArray(int i_row_num, int[] i_col_num_arr)
  {
    outString = new String(getByteInColArray(i_row_num, i_col_num_arr));
    setOutDoubleArray();
    return outDoubleArray;
  }

  public double[] nextDoubleArrayInColArray()
  {
    outString = new String(nextByteInColArray());
    setOutDoubleArray();
    return outDoubleArray;
  }  

  private void setColNum(int i_col_num)
  {
    col_num = i_col_num;
  }

  private void setColNumArray(int[] i_col_num_arr)
  {
    col_num_size = i_col_num_arr.length;
    col_num_arr = new int[col_num_size];
    for (int i=0; i<col_num_size; i++)
    {
      col_num_arr[i] = i_col_num_arr[i];
    }
    init_value_arr = new int[col_num_size];
    data_size_arr = new int[col_num_size];
    outStringArray = new String[col_num_size];
  }

  private void setColNumOfAll(int i_last_col_num)
  {
    col_num_size = i_last_col_num;
    col_num_arr = new int[i_last_col_num];
    for (int i=0; i<col_num_size; i++)
    {
      col_num_arr[i] = i+1;
    }
    init_value_arr = new int[col_num_size];
    data_size_arr = new int[col_num_size];
    outStringArray = new String[col_num_size];
  }

  //set OutValueByte
  private void setRowOutValueByte()
  {
    try
    {
      setColIndexArray(c_pos);
      row_data_size = setIntValue(col_index);
      rdf.seek(s_pos);
      out_value_byte = new byte[row_data_size];
      for (int i=0; i<row_data_size; i++)
      {
        out_value_byte[i] = rdf.readByte();
      }
    } catch(Exception re)
      {System.out.println("CXMTableReader : setRowOutValueByte="+re.getMessage());}
  }

  private void setColOutValueByte()
  {
    setDataSize(col_num);
    try
    {
      out_value_byte = new byte[col_data_size];
      rdf.seek(s_pos+init_value);
      for (int i=0; i<col_data_size; i++)
      {
        out_value_byte[i] = rdf.readByte();
      }
    } catch(Exception rse)
      {System.out.println("CXMTableReader : setColOutValueByte="+rse.getMessage());}
  }

  private void setColArrOutValueByte()
  {
    arr_data_size = 0;
    for (int i=0; i<col_num_size; i++)
    {
      setDataSize(col_num_arr[i]);
      arr_data_size = arr_data_size + col_data_size;
      init_value_arr[i] = init_value;
      data_size_arr[i] = col_data_size;
    }


    try
    {
      out_value_byte = new byte[arr_data_size];
      cnt = 0;
      for (int j=0; j<col_num_size; j++)
      {
        rdf.seek(s_pos+init_value_arr[j]);
        for (int k=0; k<data_size_arr[j]; k++)
        {
          out_value_byte[cnt] = rdf.readByte();
          cnt++;
        }
      }
    } catch(Exception rse)
      {System.out.println("CXMTableReader : setColOutValueByte_error="+rse.getMessage());}
  }

  private void setDataSize(int i_col_num)
  { 
    try
    {
      if (i_col_num==1)
      {
        init_value = 0;
        setColIndexArray(c_pos);
        col_data_size = setIntValue(col_index);
      }
      else
      {
        i_pos = 0;
        e_pos = 0;
        i_pos = c_pos + col_index_size*(i_col_num-2);
        setColIndexArray(i_pos);
        init_value = setIntValue(col_index);
        e_pos = i_pos + col_index_size;
        setColIndexArray(e_pos);
        end_value = setIntValue(col_index);
        col_data_size = end_value - init_value;
      }
    } catch(Exception re)
      {System.out.println("CXMTableReader : setDataSize_error="+re.getMessage());}
  }
  // set outStringArray
  private void setOutStringArray(byte[] i_byte)
  {
    byte[] value_byte;
    int i;
    int cnt;
    int a = 0;
    for (i=0; i<col_num_size; i++)
    {
      int b = data_size_arr[i];
      value_byte = new byte[b];
      cnt = 0;
      for (int j=a; j<a+b; j++)
      {
        value_byte[cnt] = i_byte[j];
        cnt++;
      }
      outStringArray[i] = new String(value_byte);
      a = a+b;
    }
  }

  private void setOutIntArray()
  {
    int i;
    for (i=0; i<col_num_size-1; i++)
    {
      outIntArray[i] = Integer.parseInt(outString.substring(init_value_arr[i],init_value_arr[i+1]));
    }
    outIntArray[i] = Integer.parseInt(outString.substring(init_value_arr[i],outString.length()));
  }

  private void setOutDoubleArray()
  {
    int i;
    for (i=0; i<col_num_size-1; i++)
    {
      outDoubleArray[i] = Double.parseDouble(outString.substring(init_value_arr[i],init_value_arr[i+1]));
    }
    outDoubleArray[i] = Double.parseDouble(outString.substring(init_value_arr[i],outString.length()));
  }

  // data convert
  private long setLongValue(byte[] i_byte)
  {
    out_long_value = 0;
    factor_long = 1;
    for (int i=0; i<row_index_size; i++)
    {
      temp_long_value = (long) i_byte[i];
      out_long_value = out_long_value + temp_long_value*factor_long;
      factor_long = factor_long*128;
    }
    return out_long_value;
  }

  private int setIntValue(byte[] i_byte)
  {
    out_int_value = 0;
    factor_int = 1;
    for (int i=0; i<col_index_size; i++)
    {
      temp_int_value = (int) i_byte[i];
      out_int_value = out_int_value + temp_int_value*factor_int;
      factor_int = factor_int*128;
    }
    return out_int_value;
  }

  private void setRowIndexArray(long i_pos)
  {
    try
    {
      rif.seek(i_pos);
      for (int i=0; i<row_index_size; i++)
      {
        row_index[i] = rif.readByte();       
      }     
    } catch(Exception se)
      {System.out.println("CXMTableReader : setRowIndexArray_error="+se.getMessage());}
  }

  private void setColIndexArray(long i_pos)
  {
    try
    {
      cif.seek(i_pos);
      for (int i=0; i<col_index_size; i++)
      {
        col_index[i] = cif.readByte();       
      }
    } catch(Exception se)
      {System.out.println("CXMTableReader : setColIndexArray_error="+se.getMessage());}
  }

 /* public void rowDataAppend(byte[] i_data, int[] i_col_index)
  {
    try
    {
      setByteValue(d_pos);
      setByteValue(i_col_index);
      rdf.seek(d_pos);
      rdf.write(i_data);
      long t_pos = (long) i_data.length;
      d_pos = d_pos + t_pos;
      ri_pos = ri_pos + row_index_size;
      ci_pos = ci_pos + col_index_num*col_index_size;
    } catch (Exception e)
      {
        System.out.println("CXMTableReader : rowDataAppend_error="+e.getMessage());
      }
  } 

  private void setByteValue(long i_pos)
  {
    long temp_value = i_pos;
    for (int i=0; i<row_index_size; i++)
    {
      quot_long = temp_value/128;
      mod_long = temp_value%128;
      a_row_index[i] = (byte) mod_long;
      temp_value = quot_long;
    }
    try
    {
      rif.seek(ri_pos);
      rif.write(a_row_index);
    } catch (Exception rie)
      {System.out.println("CXMTableReader : setByteValue_long_error="+rie.getMessage());}
  }

  private void setByteValue(int[] i_int_arr)
  {
    int int_size_value = 0;
    for (int i=0; i<i_int_arr.length; i++)
    {
      int_size_value = int_size_value+i_int_arr[i];
      temp_int_value = int_size_value;
      //System.out.println("temp_int_value="+temp_int_value);
      for (int j=0; j<col_index_size; j++)
      {
        quot_int = temp_int_value/128;
        mod_int = temp_int_value%128;
        a_col_index[j] = (byte) mod_int;
        temp_int_value = quot_int;
      }
      try
      {
        cif.seek(ci_pos);
        cif.write(a_col_index);
      } catch (Exception cie)
        {System.out.println("CXMTableReader : setByteValue_int_error="+cie.getMessage());}
    }
  } */

}