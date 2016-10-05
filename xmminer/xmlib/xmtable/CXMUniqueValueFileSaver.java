package xmminer.xmlib.xmtable;

import java.io.*;
import java.util.TreeSet;
import xmminer.xmlib.xmcompute.*;

public class CXMUniqueValueFileSaver
{

  private String root_path = System.getProperty("minerdir") + "/xmminer/user/";//"/XM-Miner/xmminer/user"); ¼öÁ¤ 2002.09.22
  private String data_path = "/data/";
  private String set_data_path;
  private String project;

  private String f_name;
  private String out_value;
  private String unique_value;
  private String column_name;
  private String[] column_list;
  private int row_numbers;
  private int index_value;
  private boolean file_set;
  private boolean set_column_check;
  private File unique_value_file;
  private CXMTableQuerier ctq;
  private CXMMetaDataReader cdr;
  private FileWriter fw;
  private TreeSet ts;

  public CXMUniqueValueFileSaver()
  {
  }

  public void setFileStatus(String project_name, String file_name)
  {

    project = project_name;
    set_data_path = root_path + project + data_path;

    f_name = file_name;
    cdr = new CXMMetaDataReader();
    cdr.setFileStatus(project,file_name);
    column_list = cdr.getProfiles("COLUMN_LIST");
    if (!nullCheck(column_list))
    {
        file_set = true;
    }
    row_numbers = Integer.parseInt(cdr.getProfile("NUMBER_OF_ROWS"));
    cdr.close();
    ctq = new CXMTableQuerier();
    fileOpen();
  }

  private void fileOpen()
  {
    try
    {
      unique_value_file = new File(set_data_path + f_name + ".udat");
      fw = new FileWriter(unique_value_file);
    } catch(Exception foe)
      {
        System.out.println("file_open_error="+foe.getMessage());
      }
  }

  public void makeUniqueValueFile(boolean set_column)
  {
      set_column_check = set_column;
      if (file_set)
      {
    	  for (int i=0; i<column_list.length; i++)
          {
    	       makeUniqueFile(column_list[i],i);
    	  }
      }
  }

  private void makeUniqueFile(String i_str, int i_num)
  {
       column_name = i_str;
       ts = new TreeSet();
       ctq.setFileStatus(project,f_name,column_name,null);
       unique_value = ctq.getStringInColumn(1);
       if (!ts.contains(unique_value))
       {
            ts.add(unique_value);
            if (set_column_check)
            {
                 out_value = column_name + "_" +unique_value;
            }
            else
            {
                out_value = unique_value;
            }
            try
            {
                 if (i_num==0)
                 {
                      fw.write(out_value);
                 }
                 else
                 {
                      fw.write(","+out_value);
                 }
             } catch (Exception fwe)
                 {
                      System.out.println("file_write_error_1="+fwe.getMessage());
                 }
       }
       for (int j=2; j<=row_numbers; j++)
       {
             unique_value = ctq.getStringInColumn(j);
             if (!ts.contains(unique_value))
             {
                  ts.add(unique_value);
                  if (set_column_check)
                  {
                       out_value = column_name + "_" +unique_value;
                  }
                  else
                  {
                       out_value = unique_value;
                  }
                  try
                  {
                         fw.write(","+out_value);
                  } catch (Exception fwe)
                     {
                          System.out.println("file_write_error_2="+fwe.getMessage());
                     }
            }
      }
      ts.clear();
  }

  public void makeUniqueValueFileInColumnList(String[] list, boolean set_column)
  {
        set_column_check = set_column;
        for (int i=0; i<list.length; i++)
        {
       	    makeUniqueFile(list[i],i);
        }
  }

  public void close()
  {
    try
    {
      fw.close();
      ctq.close();
    } catch (Exception fce)
      {
        System.out.println("file_close_error="+fce.getMessage());
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