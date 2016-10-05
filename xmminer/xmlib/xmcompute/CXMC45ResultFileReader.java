package xmminer.xmlib.xmcompute;

import java.io.*;
import xmminer.xmlib.xmtable.*;

public class CXMC45ResultFileReader
{
  private String root_path = System.getProperty("minerdir") + "/xmminer/user/";//"/XM-Miner/xmminer/user"); ¼öÁ¤ 2002.09.22
  private String data_path = "/data/";   
  private String index_path = "/index/";
  private String set_data_path;
  private String set_index_path;
  private String project;

  private byte[] byte_value;
  private String f_name;
  private String str_value;

  private int row_numbers;
  private int int_value;
  private int data_size;

  private long index_first_pos;
  private long index_last_pos;
  private long data_first_pos;
  private long data_last_pos;

  private double double_value;

  private boolean t_flag;

  private File result;
  private File result_index;
  private RandomAccessFile rcf;
  private RandomAccessFile cif;
  private CXMMetaDataReader cmr;
  private String m_sModelName;

  public CXMC45ResultFileReader()
  {
  }

  public void setFileStatus(String project_name, String arc_name, String ModelName,boolean test_flag)
  {
     project = project_name;
     set_data_path = root_path + project + data_path;
     set_index_path = root_path + project + index_path;
     f_name = arc_name;
     m_sModelName=ModelName;
     t_flag = test_flag;
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
      try
      {
          if (t_flag)
          {
              result = new File(set_data_path+f_name+"_"+m_sModelName+"_c45_rtf.data");
              result_index = new File(set_index_path+f_name+"_"+m_sModelName+"_c45_rtf.cidx");
          }
          else
          {
              result = new File(set_data_path+f_name+"_"+m_sModelName+"_c45_rlf.data");
              //System.out.println("result_file_name="+set_index_path+f_name+"_"+m_sModelName+"_c45_rlf.cidx");
              result_index = new File(set_index_path+f_name+"_"+m_sModelName+"_c45_rlf.cidx");

          }
          rcf = new RandomAccessFile(result,"r");
          cif = new RandomAccessFile(result_index,"r");
      } catch(Exception foe)
        {
            System.out.println("result_file_open_error");
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
           System.out.println("result_file_close_error="+fce.getMessage());
        }
  }

  public String getString(int row_number, int column_number)
  {
      try
      {
          index_first_pos = (column_number-1)*row_numbers*8 + (row_number-1)*8;
          //System.out.println("index_first_pos="+index_first_pos);
          index_last_pos = (column_number-1)*row_numbers*8 + row_number*8;
          //System.out.println("index_last_pos="+index_last_pos);
          cif.seek(index_first_pos);
          //System.out.println("kkk");
          //for (int i=0; i<8; i++)
          //{
          //   System.out.println(cif.readByte());
          //}
          data_first_pos = cif.readLong();
          //System.out.println("data_first_pos="+data_first_pos);
          cif.seek(index_last_pos);
          data_last_pos = cif.readLong();
          //System.out.println("data_last_pos="+data_last_pos);
          data_size = (int) (data_last_pos - data_first_pos);
          //System.out.println("data_size="+data_size);
          byte_value = new byte[data_size];
          rcf.seek(data_first_pos);
          for (int i=0; i<data_size; i++)
          {
              byte_value[i] = rcf.readByte();
          }
          str_value = new String(byte_value);
      } catch (Exception sve)
        {
            System.out.println("get_string_error="+sve.getMessage());
        }
      return str_value;
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

}