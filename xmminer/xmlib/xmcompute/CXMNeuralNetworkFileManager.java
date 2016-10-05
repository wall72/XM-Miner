package xmminer.xmlib.xmcompute;

import java.io.*;
import xmminer.xmlib.xmtable.*;

public class CXMNeuralNetworkFileManager
{

  private String root_path = System.getProperty("minerdir") + "/xmminer/user/";//"/XM-Miner/xmminer/user"); ¼öÁ¤ 2002.09.22
  private String data_path = "/data/";
  private String index_path = "/index/";
  private String set_data_path;
  private String set_index_path;
  private String project;

  private String f_name;
  private byte[] value_byte;
  private int row_numbers;
  private int int_value;
  private int byte_size;
  private long index_first_pos;
  private long data_first_pos;
  private long data_next_pos;
  private double double_value;
  private File input;
  private File input_index;
  private File output;
  private File output_index;
  private File result;
  private File result_index;
  private RandomAccessFile idf;
  private RandomAccessFile iif;
  private RandomAccessFile odf;
  private RandomAccessFile oif;
  private RandomAccessFile rdf;
  private RandomAccessFile rif;
  private CXMMetaDataReader cmr;

  public CXMNeuralNetworkFileManager()
  {
  }

  public void setFileStatus(String project_name, String arc_name)
  {
     project = project_name;
     set_data_path = root_path + project + data_path;
     set_index_path = root_path + project + index_path;
     f_name = arc_name;
     getArcMetaData();
     fileOpen();
  }

  private void getArcMetaData()
  {
     cmr = new CXMMetaDataReader();
     cmr.setFileStatus(project,f_name);
     row_numbers = Integer.parseInt(cmr.getProfile("NUMBER_OF_ROWS"));
     cmr.close();
  }

  private void fileOpen()
  {
      input = new File(set_data_path+f_name+"_nif.data");
      input_index = new File(set_index_path+f_name+"_nif.cidx");
      output = new File(set_data_path+f_name+"_nof.data");
      output_index = new File(set_index_path+f_name+"_nof.cidx");
      result = new File(set_data_path+f_name+"_rof.data");
      result_index = new File(set_index_path+f_name+"_rof.cidx");

      try
      {
          idf = new RandomAccessFile(input,"r");
          iif = new RandomAccessFile(input_index,"r");
      } catch (Exception ioe)
        {
           System.out.println("input_file_open_error="+ioe.getMessage());
        }
      try
      {
          odf = new RandomAccessFile(output,"r");
          oif = new RandomAccessFile(output_index,"r");
      } catch (Exception ooe)
        {
           System.out.println("output_file_open_error="+ooe.getMessage());
        }
      try
      {
          rdf = new RandomAccessFile(result,"rw");
          rif = new RandomAccessFile(result_index,"r");
      } catch (Exception roe)
        {
           System.out.println("result_file_open_error="+roe.getMessage());
        }
  }

  public void close()
  {
      try
      {
          idf.close();
          iif.close();
      } catch (Exception ice)
        {
           System.out.println("input_file_close_error="+ice.getMessage());
        }
      try
      {
          odf.close();
          oif.close();
      } catch (Exception oce)
        {
           System.out.println("output_file_close_error="+oce.getMessage());
        }
      try
      {
          rdf.close();
          rif.close();
      } catch (Exception rce)
        {
           System.out.println("result_file_close_error="+rce.getMessage());
        }
  }

  public byte[] getInputBytes(int row_number, int column_number)
  {
      try
      {
          index_first_pos = (column_number-1)*row_numbers*8 + (row_number-1)*8;
          iif.seek(index_first_pos);
          data_first_pos = iif.readLong();
          data_next_pos = iif.readLong();
          byte_size = (int) (data_next_pos - data_first_pos);
          value_byte = new byte[byte_size];
          idf.seek(data_first_pos);
          for (int i=0; i<byte_size; i++)
          {
              value_byte[i] = idf.readByte();
          }
      } catch (Exception gie)
        {
            System.out.println("get_input_byte_error="+gie.getMessage());
        }
      return value_byte;
  }

  public byte[] getOutputBytes(int row_number, int column_number)
  {
      try
      {
          index_first_pos = (column_number-1)*row_numbers*8 + (row_number-1)*8;
          oif.seek(index_first_pos);
          data_first_pos = oif.readLong();
          data_next_pos = oif.readLong();
          byte_size = (int) (data_next_pos - data_first_pos);
          value_byte = new byte[byte_size];
          odf.seek(data_first_pos);
          for (int i=0; i<byte_size; i++)
          {
              value_byte[i] = odf.readByte();
          }
      } catch (Exception goe)
        {
            System.out.println("get_output_byte_error="+goe.getMessage());
        }
      return value_byte;
  }

  public byte[] getResultBytes(int row_number, int column_number)
  {
      try
      {
          index_first_pos = (column_number-1)*row_numbers*8 + (row_number-1)*8;
          rif.seek(index_first_pos);
          data_first_pos = rif.readLong();
          data_next_pos = rif.readLong();
          byte_size = (int) (data_next_pos - data_first_pos);
          value_byte = new byte[byte_size];
          rdf.seek(data_first_pos);
          for (int i=0; i<byte_size; i++)
          {
              value_byte[i] = rdf.readByte();
          }
      } catch (Exception gre)
        {
            System.out.println("get_result_byte_error="+gre.getMessage());
        }
      return value_byte;
  }

  public int getInputInteger(int row_number, int column_number)
  {
      try
      {
          index_first_pos = (column_number-1)*row_numbers*8 + (row_number-1)*8;
          iif.seek(index_first_pos);
          data_first_pos = iif.readLong();
          idf.seek(data_first_pos);
          int_value = idf.readInt();
      } catch (Exception gie)
        {
            System.out.println("get_input_int_error="+gie.getMessage());
        }
      return int_value;
  }

  public int getOutputInteger(int row_number, int column_number)
  {
      try
      {
          index_first_pos = (column_number-1)*row_numbers*8 + (row_number-1)*8;
          oif.seek(index_first_pos);
          data_first_pos = oif.readLong();
          odf.seek(data_first_pos);
          int_value = odf.readInt();
      } catch (Exception goe)
        {
            System.out.println("get_output_int_error="+goe.getMessage());
        }
      return int_value;
  }

  public int getResultInteger(int row_number, int column_number)
  {
      try
      {
          index_first_pos = (column_number-1)*row_numbers*8 + (row_number-1)*8;
          rif.seek(index_first_pos);
          data_first_pos = rif.readLong();
          rdf.seek(data_first_pos);
          int_value = rdf.readInt();
      } catch (Exception gre)
        {
            System.out.println("get_result_int_error="+gre.getMessage());
        }
      return int_value;
  }

  public double getInputDouble(int row_number, int column_number)
  {
      try
      {
          index_first_pos = (column_number-1)*row_numbers*8 + (row_number-1)*8;
          iif.seek(index_first_pos);
          data_first_pos = iif.readLong();
          idf.seek(data_first_pos);
          double_value = idf.readDouble();
      } catch (Exception gie)
        {
            System.out.println("get_input_double_error="+gie.getMessage());
        }
      return double_value;
  }

  public double getOutputDouble(int row_number, int column_number)
  {
      try
      {
          index_first_pos = (column_number-1)*row_numbers*8 + (row_number-1)*8;
          oif.seek(index_first_pos);
          data_first_pos = oif.readLong();
          odf.seek(data_first_pos);
          double_value = odf.readDouble();
      } catch (Exception goe)
        {
            System.out.println("get_output_double_error="+goe.getMessage());
        }
      return double_value;
  }

  public double getResultDouble(int row_number, int column_number)
  {
      try
      {
          index_first_pos = (column_number-1)*row_numbers*8 + (row_number-1)*8;
          rif.seek(index_first_pos);
          data_first_pos = rif.readLong();
          rdf.seek(data_first_pos);
          double_value = rdf.readDouble();
      } catch (Exception gre)
        {
            System.out.println("get_result_double_error="+gre.getMessage());
        }
      return double_value;
  }

  public void setResultBytes(int row_number, int column_number, byte[] value)
  {
      try
      {
          index_first_pos = (column_number-1)*row_numbers*8 + (row_number-1)*8;
          rif.seek(index_first_pos);
          data_first_pos = rif.readLong();
          rdf.seek(data_first_pos);
          rdf.write(value);
      } catch (Exception sre)
        {
            System.out.println("set_result_byte_error="+sre.getMessage());
        }
  }

  public void setResultInteger(int row_number, int column_number, int value)
  {
      try
      {
          index_first_pos = (column_number-1)*row_numbers*8 + (row_number-1)*8;
          rif.seek(index_first_pos);
          data_first_pos = rif.readLong();
          rdf.seek(data_first_pos);
          rdf.writeInt(value);
      } catch (Exception sre)
        {
            System.out.println("set_result_int_error="+sre.getMessage());
        }
  }

  public void setResultDouble(int row_number, int column_number, double value)
  {
      try
      {
          index_first_pos = (column_number-1)*row_numbers*8 + (row_number-1)*8;
          rif.seek(index_first_pos);
          data_first_pos = rif.readLong();
          rdf.seek(data_first_pos);
          rdf.writeDouble(value);
      } catch (Exception sre)
        {
            System.out.println("set_result_double_error="+sre.getMessage());
        }
  }
}
