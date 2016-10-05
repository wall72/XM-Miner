package xmminer.xmlib.xmcompute;

import java.io.*;
import java.util.Vector;
import xmminer.xmlib.xmtable.*;

public class CXMC45OutputTableSaver
{
    private String root_path = System.getProperty("minerdir") + "/xmminer/user/";//"/XM-Miner/xmminer/user"); ¼öÁ¤ 2002.09.22
    private String data_path = "/data/";   
    private String index_path = "/index/";
    private String set_data_path;
    private String set_index_path;
    private String project;

    private byte[] value_byte;
    private String f_name;
    private String new_f_name;
    private String t_column;
    private String value;
    private String temp_str;
    private String[] c45_column_list;
    private String[] c45_column_type;
    private String[] non_numeric_column_list;
    private int row_numbers;
    private int column_number;
    private int[] column_type_index;
    private int[] column_size;
    private boolean t_flag;
    private Vector c45_column_v;
    private Vector c45_type_v;
    private CXMMetaDataReader cmr;
    private CXMC45ResultFileReader rfr;  
    private CXMC45TableManager ctm;
    private CXMTableSaver cts;
    private String m_sModelName;

    public CXMC45OutputTableSaver()
    {
    }

    public void setFileStatus(String project_name,String arc_name,String ModelName, String new_arc_name, boolean test_flag)
    {
         project = project_name;
         set_data_path = root_path + project + data_path;
         set_index_path = root_path + project + index_path;

         f_name = arc_name;
         new_f_name = new_arc_name;
         t_flag = test_flag;
         m_sModelName=ModelName;
         getArcMetaData();
         getC45MetaData();
         ctm = new CXMC45TableManager();
         ctm.setFileStatus(project,f_name,m_sModelName,t_flag);
         rfr = new CXMC45ResultFileReader();
         rfr.setFileStatus(project,f_name,m_sModelName,t_flag);
         cts = new CXMTableSaver();
         cts.setFileStatus(project,new_f_name);
         column_size = new int[column_number];
    }

    private void getArcMetaData()
    {
         cmr = new CXMMetaDataReader();
         cmr.setFileStatus(project,f_name);
         row_numbers = Integer.parseInt(cmr.getProfile("NUMBER_OF_ROWS"));
         cmr.close();
    }

    private void getC45MetaData()
    {
         cmr = new CXMMetaDataReader();
         cmr.setFileStatus(project,f_name+"_"+m_sModelName+"_c45");
         c45_column_list = cmr.getProfiles("COLUMN_LIST");
         c45_column_type = cmr.getProfiles("COLUMN_TYPE");
         t_column = cmr.getProfile("TRAINING_COLUMN");
         cmr.close();
         c45_column_v = new Vector();
         c45_type_v = new Vector();

         column_number = c45_column_list.length;
         for (int i=0; i<column_number; i++)
         {
            c45_column_v.addElement(c45_column_list[i]);
            c45_type_v.addElement(c45_column_type[i]);
         }
         if (!nullCheck(t_column))
         {
             column_number = column_number + 1;
             c45_column_v.addElement(t_column);
             c45_type_v.addElement("TRAINING_TYPE");
         }
         column_type_index = new int[column_number];
         for (int j=0; j<column_number; j++)
         {
             column_type_index[j] = transColumnType((String)c45_type_v.elementAt(j));
         }

    }

    private boolean numericTypeCheck(String i_str)
    {
        if (i_str.equals("REAL")||i_str.equals("INTEGER"))
        {
           return true;
        }
        else
        {
           return false;
        }
    }

    private int transColumnType(String i_str)
    {
        if (i_str.equals("INTEGER"))
        {
           return 0;
        }
        else if (i_str.equals("REAL"))
        {
           return 1;
        }
        else
        {
           return 2;
        }
    }

    public void close()
    {
      ctm.close();
      rfr.close();
      cts.close();
    }

    public void createC45OutputTable()
    {   
        for (int i=0; i<row_numbers; i++)
        {
           value = "";
           int non_numeric_column_cnt = 0;
           for (int j=0; j<column_number; j++)
           {
               if (column_type_index[j]==0)
               {
                   temp_str = String.valueOf((int)ctm.getDouble(i+1,j+1));
                   value_byte = temp_str.getBytes();
                   value = value + temp_str;
                   column_size[j] = value_byte.length;
               }
               else if (column_type_index[j]==1)
               {
                   temp_str = String.valueOf(ctm.getDouble(i+1,j+1));
                   value_byte = temp_str.getBytes();
                   value = value + temp_str;
                   column_size[j] = value_byte.length;
               }
               else if (column_type_index[j]==2)
               {
                   non_numeric_column_cnt = non_numeric_column_cnt + 1;
                   System.out.println("row_number="+i);
                   System.out.println("column_number="+j);
                   System.out.println("cnt="+non_numeric_column_cnt);
                   temp_str = rfr.getString(i+1,non_numeric_column_cnt);
                   value_byte = temp_str.getBytes();
                   value = value + temp_str;
                   column_size[j] = value_byte.length;
               }
           }       
           cts.createRowData(value.getBytes(),column_size);
        }
    }

    private boolean nullCheck(String i_str)
    {
       boolean out_bool = false;
       if (i_str==null)
       {
          out_bool = true;
       }
       else
       {
          if (i_str.equals(""))
          {
              out_bool = true;
          }
       }
       return out_bool;
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

    private void pba(String i_title, byte[] i_byte)
    {
        System.out.print(i_title + " : ");
        for (int i=0; i<i_byte.length; i++)
        {
    	      System.out.print(i_byte[i]+",");
        }
        System.out.print("\n");
    }
}
