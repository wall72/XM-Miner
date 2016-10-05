package xmminer.xmlib.xmcompute;

import java.io.*;

public class CXMWeightTableSaver
{
    private String root_path = System.getProperty("minerdir") + "/xmminer/user/";//"/XM-Miner/xmminer/user"); ¼öÁ¤ 2002.09.22
    private String data_path = "/data/";   
    private String index_path = "/index/";
    private String set_data_path;
    private String set_index_path;
    private String project;
  
    private String f_name;
    private String c_name;
    private int row_numbers;
    private int column_number;
    private long result_index_pos;   

    private File result;
    private File result_index;
    private RandomAccessFile rcf;
    private RandomAccessFile cif;

    public CXMWeightTableSaver()
    {
    }

    public void setFileStatus(String project_name, String file_name)
    {
         project = project_name;
         set_data_path = root_path + project + data_path;
         set_index_path = root_path + project + index_path;

         f_name = file_name;
    }


    public void close()
    {
    }

    public void createWeightTable(int[] column_size, int row_number)
    {
        weightFileOpen();
        column_number = column_size.length;
        row_numbers = row_number;
        try
        {
           result_index_pos = rcf.getFilePointer();
           cif.writeLong(result_index_pos);
        } catch (Exception spe)
          {
              System.out.println("set_init_index_position_error="+spe.getMessage());
          }
        for (int i=0; i<column_number; i++)
        {
             if (column_size[i]==4)
             {
                setIntegerValue();
             }
             else if (column_size[i]==8)
             {
                setDoubleValue();
             }
        }
        weightFileClose();
    }

    private void setIntegerValue()
    {
        for (int i=0; i<row_numbers; i++)
        {
            try
            {
                rcf.writeInt(0);
                result_index_pos = rcf.getFilePointer();
                cif.writeLong(result_index_pos);
            } catch (Exception we)
              {
                  System.out.println("c45_temporary_int_write_error="+we.getMessage());
              }
        }
    }

    private void setDoubleValue()
    {
        for (int i=0; i<row_numbers; i++)
        {
            try
            {
                rcf.writeDouble(0);
                result_index_pos = rcf.getFilePointer();
                cif.writeLong(result_index_pos);
            } catch (Exception we)
              {
                  System.out.println("c45_temporary_double_write_error="+we.getMessage());
              }
        }
    }

    private void weightFileOpen()
    {
        try
        {
           result = new File(set_data_path+f_name+".data");
           result.delete();
           rcf = new RandomAccessFile(result,"rw");
           result_index = new File(set_index_path+f_name+".cidx");
           result_index.delete();
           cif = new RandomAccessFile(result_index,"rw");
        } catch(Exception foe)
          {
             System.out.println("c45_weight_file_open_error");
          }
    }

    private void weightFileClose()
    {
        try
        {
            rcf.close();
            cif.close();
        } catch(Exception foe)
          {
             System.out.println("c45_weight_file_close_error");
          }
    }
}
