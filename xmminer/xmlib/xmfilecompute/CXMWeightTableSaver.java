package xmminer.xmlib.xmfilecompute;

import java.io.*;
import java.util.Vector;
import xmminer.xmlib.xmtable.*;

public class CXMWeightTableSaver
{
    private String root_path = System.getProperty("minerdir") + "/xmminer/user/";//"/XM-Miner/xmminer/user"); ¼öÁ¤ 2002.09.22
    private String data_path = "/data/";
    private String index_path = "/index/";
    private String set_data_path;
    private String set_index_path;
    private String project;

    private byte[] index_byte;
    private String f_name;
    private String c_name;
    private String column_seq;
    private String column_type;
    private String[] column_list;
    private String[] new_column_list;
    private String[] column_index_list;
    private String[] new_column_index_list;
    private String[] c45_column_list;
    private String[] column_property;
    private String[] column_type_list;
    private String[] column_size_list;
    private int row_numbers;
    private int index_size;
    private int int_value;
    private int column_number;
    private long result_index_pos;
    private double double_value;
    private Vector column_v;
    private Vector new_column_v;
    private CXMMetaDataReader cmr;
    private CXMMetaDataSaver cms;
    private CXMTableQuerier ctq;
    private File result;
    private File result_index;
    private RandomAccessFile rcf;
    private RandomAccessFile cif;

    public CXMWeightTableSaver()
    {
    }

    public void setFileStatus(String project_name, String arc_name)
    {
         project = project_name;
         set_data_path = root_path + project + data_path;
         set_index_path = root_path + project + index_path;

         f_name = arc_name;

         index_size = 5;
         index_byte = new byte[index_size];

         getArcMetaData();
    }


    public void close()
    {
      setWeightMetaData();
    }

    private void getArcMetaData()
    {
         cmr = new CXMMetaDataReader();
         cmr.setFileStatus(project,f_name);
         column_list = cmr.getProfiles("OLD_COLUMN_LIST");
         if (!nullCheck(column_list))
         {
             column_index_list = cmr.getProfiles("COLUMN_INDEX");
             column_v = new Vector();
             for (int i=0; i<column_list.length; i++)
             {
                column_v.addElement(column_list[i]);
             }
         }
         new_column_list = cmr.getProfiles("NEW_COLUMN_LIST");
         if (!nullCheck(new_column_list))
         {
             new_column_index_list = cmr.getProfiles("NEW_COLUMN_INDEX");
             new_column_v = new Vector();
             for (int j=0; j<new_column_list.length; j++)
             {
                new_column_v.addElement(new_column_list[j]);
             }
         }
         row_numbers = Integer.parseInt(cmr.getProfile("NUMBER_OF_ROWS"));
         cmr.close();
    }

    public void createWeightTable(String[] column_list)
    {
        weightFileOpen();
        ctq = new CXMTableQuerier();
        c45_column_list = column_list;
        column_number = c45_column_list.length;
        column_type_list = new String[column_number];
        column_size_list = new String[column_number];

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
             setColumnStatus(i);
             column_type_list[i] = column_type;
             ctq.setFileStatus(project,f_name,c_name,null);
             if (column_type.equals("INTEGER"))
             {
                column_size_list[i] = "4";
                setIntegerValue();
             }
             else if (column_type.equals("REAL"))
             {
                column_size_list[i] = "8";
                setDoubleValue();
             }
        }
        setLastWeightValue();
        ctq.close();
        weightFileClose();
    }

    private void setWeightMetaData()
    {
        cms = new CXMMetaDataSaver();
        cms.setFileStatus(project,f_name+"_weight");
        cms.setProfiles("COLUMN_LIST",c45_column_list);
        cms.setProfiles("COLUMN_TYPE",column_type_list);
        cms.setProfiles("COLUMN_SIZE",column_size_list);
        cms.close();
    }

    private void setIntegerValue()
    {
        for (int i=0; i<row_numbers; i++)
        {
            int_value = ctq.getIntInColumn(i+1);
            try
            {
                rcf.writeInt(int_value);
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
            double_value = ctq.getDoubleInColumn(i+1);
            try
            {
                rcf.writeDouble(double_value);
                result_index_pos = rcf.getFilePointer();
                cif.writeLong(result_index_pos);
            } catch (Exception we)
              {
                  System.out.println("c45_temporary_double_write_error="+we.getMessage());
              }
        }
    }

    private void setLastWeightValue()
    {
        try
        {
            for (int i=0; i<row_numbers; i++)
            {
                rcf.writeInt(0);
                result_index_pos = rcf.getFilePointer();
                cif.writeLong(result_index_pos);
            }
         } catch (Exception re)
           {
               System.out.println("set_result_template_value_error");
           }
    }

    private void weightFileOpen()
    {
        try
        {
           result = new File(set_data_path+f_name+"_cwf.data");
           result.delete();
           rcf = new RandomAccessFile(result,"rw");
           result_index = new File(set_index_path+f_name+"_cwf.cidx");
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

    private void setColumnStatus(int i_int)
    {
        c_name = c45_column_list[i_int];
        if (column_v.contains(c_name))
        {
            column_seq = column_index_list[column_v.indexOf(c_name)];
            column_property = cmr.getProfiles(c_name);
            column_type = column_property[0];
        }
        else
        {
            if (new_column_v.contains(c_name))
            {
                column_seq = new_column_index_list[new_column_v.indexOf(c_name)];
                column_type = "REAL";
            }
        }
    }

    private boolean nullCheck(String[] i_str)
    {
        if (i_str==null)
        {
            return true;
        }
        else
        {
            if (i_str.length==0)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
    }

}
