package xmminer.xmlib.xmcompute;

import java.io.*;

public class CXMWeightTableManager
{

  private String root_path = System.getProperty("minerdir") + "/xmminer/user/";//"/XM-Miner/xmminer/user"); ¼öÁ¤ 2002.09.22
  private String data_path = "/data/";   
  private String index_path = "/index/";
  private String set_data_path;
  private String set_index_path;
  private String project;

  private String f_name;
  private int row_numbers;
  private int int_value;
  private int column_number;
  private int temp_int1;
  private int temp_int2;
  private int[] column_size_list;
  private long index_first_pos;
  private long data_first_pos;
  private double double_value;
  private double temp_double1;
  private double temp_double2;
  private double[] row_value1;
  private double[] row_value2;
  private double[] out_double;
  private File result;
  private File result_index;
  private RandomAccessFile rcf;
  private RandomAccessFile cif;

  public CXMWeightTableManager()
  {
  }

  public void setFileStatus(String project_name, String file_name, int[] column_size, int row_number)
  {
     project = project_name;
     set_data_path = root_path + project + data_path;
     set_index_path = root_path + project + index_path;
     f_name = file_name;
     column_number = column_size.length;
     column_size_list = column_size;
     row_numbers = row_number;
     row_value1 = new double[column_number];
     row_value2 = new double[column_number];
     fileOpen();
  }

  private void fileOpen()
  {
      result = new File(set_data_path+f_name+".data");
      result_index = new File(set_index_path+f_name+".cidx");

      try
      {
          rcf = new RandomAccessFile(result,"rw");
          cif = new RandomAccessFile(result_index,"r");
      } catch (Exception foe)
        {
           System.out.println("file_open_error="+foe.getMessage());
        }
  }

  public void close()
  {
      try
      {
          rcf.close();
          cif.close();
      } catch (Exception fce)
        {
           System.out.println("file_close_error="+fce.getMessage());
        }
  }

  public int getInteger(int row_number, int column_number)
  {
      try
      {
          index_first_pos = (column_number-1)*row_numbers*8 + (row_number-1)*8;
          cif.seek(index_first_pos);
          data_first_pos = cif.readLong();
          rcf.seek(data_first_pos);
          int_value = rcf.readInt();
      } catch (Exception ive)
        {
            System.out.println("get_int_error="+ive.getMessage());
        }
      return int_value;
  }

  public double getDouble(int row_number, int column_number)
  {
      try
      {
          index_first_pos = (column_number-1)*row_numbers*8 + (row_number-1)*8;
          cif.seek(index_first_pos);
          data_first_pos = cif.readLong();
          rcf.seek(data_first_pos);
          double_value = rcf.readDouble();
      } catch (Exception dve)
        {
            System.out.println("get_double_error="+dve.getMessage());
        }
      return double_value;
  }

  public void setInteger(int row_number, int column_number, int value)
  {
      try
      {
          index_first_pos = (column_number-1)*row_numbers*8 + (row_number-1)*8;
          cif.seek(index_first_pos);
          data_first_pos = cif.readLong();
          rcf.seek(data_first_pos);
          rcf.writeInt(value);
      } catch (Exception ive)
        {
            System.out.println("set_int_error="+ive.getMessage());
        }
  }

  public void setDouble(int row_number, int column_number, double value)
  {
      try
      {
          index_first_pos = (column_number-1)*row_numbers*8 + (row_number-1)*8;
          cif.seek(index_first_pos);
          data_first_pos = cif.readLong();
          rcf.seek(data_first_pos);
          rcf.writeDouble(value);
      } catch (Exception dve)
        {
            System.out.println("set_double_error="+dve.getMessage());
        }
  }

  public void exchangeRowIntegerInColumn(int row1,int row2, int column_number)
  {
      temp_int1 = getInteger(row1,column_number);
      temp_int2 = getInteger(row2,column_number);
      setInteger(row1,column_number,temp_int2);
      setInteger(row2,column_number,temp_int1);
  }

  public void exchangeRowDoubleInColumn(int row1,int row2, int column_number)
  {
      temp_double1 = getDouble(row1,column_number);
      temp_double2 = getDouble(row2,column_number);
      setDouble(row1,column_number,temp_double2);
      setDouble(row2,column_number,temp_double1);
  }

  public void exchangeRows(int row1,int row2)
  {
      row_value1 = getDoubleValueArray(row1);
      row_value2 = getDoubleValueArray(row2);

      for (int i=0; i<column_number; i++)
      {
          if (column_size_list[i]==4)
          {
              setInteger(row1,i+1,(int)row_value2[i]);
          }
          else if (column_size_list[i]==8)
          {
              setDouble(row1,i+1,row_value2[i]);
          }
      }

      for (int i=0; i<column_number; i++)
      {
          if (column_size_list[i]==4)
          {
              setInteger(row2,i+1,(int)row_value1[i]);
          }
          else if (column_size_list[i]==8)
          {
              setDouble(row2,i+1,row_value1[i]);
          }
      }

  }

  public double[] getDoubleValueArray(int i_int)
  {
      out_double = new double[column_number];
      for (int i=0; i<column_number; i++)
      {
          if (column_size_list[i]==4)
          {
              out_double[i] = (double) getInteger(i_int,i+1);
          }
          else if (column_size_list[i]==8)
          {
              out_double[i] = getDouble(i_int,i+1);
          }
      }
      return out_double;
  }

}