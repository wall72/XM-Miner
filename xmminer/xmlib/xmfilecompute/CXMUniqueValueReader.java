package xmminer.xmlib.xmfilecompute;

import java.io.*;
import xmminer.xmlib.xmtable.*;
import xmminer.xmlib.xmfileprocess.*;


public class CXMUniqueValueReader
{
  private String root_path = System.getProperty("minerdir") + "/xmminer/user/";//"/XM-Miner/xmminer/user"); ¼öÁ¤ 2002.09.22
  private String data_path = "/data/";
  private String index_path = "/index/";
  private String set_data_path;
  private String set_index_path;

  private byte[] index_byte;
  private byte[] value_byte;
  private String project;
  private String f_name;
  private String column_name;
  private String unique_file_name;
  private String[] unique_columns;
  private int index_size;
  private int data_size;
  private int unique_value_number;
  private int unique_columns_size;
  private long out_long;
  private long factor_long;
  private long temp_long;
  private long first_pos;
  private long next_pos;
  private RandomAccessFile rdf;
  private RandomAccessFile rif;
  private CXMMetaDataReader cdr;

  private File data;
  private File index;

  public CXMUniqueValueReader()
  {

  }

  public void setFileStatus(String project_name, String arc_name, String i_name)
  {
      project = project_name;
      set_data_path = root_path + project + data_path;
      set_index_path = root_path + project + index_path;
      project = project_name;
      f_name = arc_name;
      column_name = i_name;
      index_size = 5;
      index_byte = new byte[index_size];
      setInitStatus();
  }

  public void close()
  {
      templateFileClose();
  }

  private void setInitStatus()
  {
      getNeuralNetworkMeta();
      templateFileOpen();
  }

  private void getNeuralNetworkMeta()
  {
      cdr = new CXMMetaDataReader();
      cdr.setFileStatus(project,f_name+"_neural_network");
      unique_file_name = cdr.getProfile(column_name+"_unique_value_file");
      cdr.close();
  }

  private void templateFileOpen()
  {
      try
      {
          data = new File(set_data_path+unique_file_name+"_uvf.fdat");
          index = new File(set_index_path+unique_file_name+"_uvf.fidx");
          rdf = new RandomAccessFile(data,"r");
          rif = new RandomAccessFile(index,"r");
      } catch(Exception foe)
        {
           System.out.println("template_file_open_error");
        }
  }

  private void templateFileClose()
  {
      try
      {
          rdf.close();
          rif.close();
      } catch(Exception foe)
        {
           System.out.println("template_file_close_error");
        }
  }

  public int getUniqueValueNumber()
  {
      try
      {
          unique_value_number = ((int) (rif.length()/index_size))-1;
      } catch (Exception use)
        {
           unique_value_number = -1;
           System.out.println("get_unique_value_number_error");
        }
      return unique_value_number;
  }

  public String[] getUniqueColumns()
  {
      unique_columns_size = getUniqueValueNumber();
      unique_columns = new String[unique_columns_size];
      setUniqueColumns();
      return unique_columns;
  }

  private void setUniqueColumns()
  {
     try
     {
          rif.seek(0);
          first_pos = getDataPosition();
     } catch (Exception spe)
       {
           System.out.println("seek_first_position_error");
       }
     for (int i=0; i<unique_columns_size; i++)
     {
         next_pos = getDataPosition();
         data_size = (int)(next_pos-first_pos);
         setValueByte();
         unique_columns[i] = new String(value_byte);
         first_pos = next_pos;
     }
  }

  private long getDataPosition()
  {
      try
      {
          for (int i=0; i<index_size; i++)
          {
              index_byte[i] = rif.readByte();
          }
      } catch (Exception dpe)
        {
            System.out.println("get_data_position_error");
        }
      return setLongValue(index_byte);
  }

  private void setValueByte()
  {
      try
      {
         data_size = (int)(next_pos-first_pos);
         value_byte = new byte[data_size];
         for (int i=0; i<data_size; i++)
         {
             value_byte[i] = rdf.readByte();
         }
      } catch (Exception vbe)
        {
            System.out.println("set_value_byte_error");
        }
  }

  private long setLongValue(byte[] i_byte)
  {
    out_long = 0;
    factor_long = 1;
    for (int i=0; i<index_size; i++)
    {
        temp_long = (long) i_byte[i];
        out_long = out_long + temp_long*factor_long;
        factor_long = factor_long*128;
    }
    return out_long;
  }

}