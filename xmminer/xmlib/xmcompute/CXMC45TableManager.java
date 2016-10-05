package xmminer.xmlib.xmcompute;

import java.io.*;
import xmminer.xmlib.xmtable.*;

public class CXMC45TableManager
{                                         

  private String root_path = System.getProperty("minerdir") + "/xmminer/user/";//"/XM-Miner/xmminer/user"); ¼öÁ¤ 2002.09.22
  private String data_path = "/data/";
  private String index_path = "/index/";
  private String set_data_path;
  private String set_index_path;
  private String project;
  private String m_sModelName;

  private String f_name;
  private int row_numbers;
  private int int_value;
  private long index_first_pos;
  private long data_first_pos;
  private double double_value;
  private boolean t_flag;
  private File result;
  private File result_index;
  private RandomAccessFile rcf;
  private RandomAccessFile cif;
  private CXMMetaDataReader cmr;

  public CXMC45TableManager()
  {
  }

  public void setFileStatus(String project_name, String arc_name,String ModelName, boolean test_flag)
  {
     project = project_name;
     set_data_path = root_path + project + data_path;
     set_index_path = root_path + project + index_path;
     f_name = arc_name;
     m_sModelName=ModelName;
     t_flag = test_flag;
     m_sModelName=ModelName;
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
      if (t_flag)
      {
         result = new File(set_data_path+f_name+"_"+m_sModelName+"_c45"+"_ctf.data");
         result_index = new File(set_index_path+f_name+"_"+m_sModelName+"_c45"+"_ctf.cidx");
		 System.out.println("result="+set_data_path+f_name+"_"+m_sModelName+"_c45"+"_ctf.data");
		 System.out.println("result_index="+set_index_path+f_name+"_"+m_sModelName+"_c45"+"_ctf.cidx");
      }
      else
      {
         result = new File(set_data_path+f_name+"_"+m_sModelName+"_c45"+"_clf.data");
         result_index = new File(set_index_path+f_name+"_"+m_sModelName+"_c45"+"_clf.cidx");
      }

      try
      {
          System.out.println("e111");
		  rcf = new RandomAccessFile(result,"rw");
		  System.out.println("e222");
          cif = new RandomAccessFile(result_index,"r");
		  System.out.println("e333");
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

}
