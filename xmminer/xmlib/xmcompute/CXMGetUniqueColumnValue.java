package xmminer.xmlib.xmcompute;

import java.io.*;
import java.util.*;
import java.util.Vector;
import java.util.TreeSet;
import xmminer.xmlib.xmtable.*;

public class CXMGetUniqueColumnValue
{

  private String project;
  private String f_name = "";
  private String c_name = "";
  private String unique_value;
  private int row_numbers;
  private CXMTableQuerier ctq;
  private CXMSetUVRowNumber srn;
  private Hashtable uvh;
  private TreeSet ts;
  //private TreeSet ts;

  public void setFileStatus(String project_name, String file_name, String column_name)
  {
    project = project_name;
    f_name = file_name;  
    c_name = column_name;    
    row_numbers = getRowNumbers();
    fileOpen();
  }

  private void fileOpen()
  {
    try
    {
      ctq = new CXMTableQuerier();
      ctq.setFileStatus(project,f_name,c_name,null);
      srn = new CXMSetUVRowNumber();
      srn.setFileStatus(project,f_name,c_name);
      uvh = new Hashtable();
      ts = new TreeSet();
    } catch (Exception foe)
      {
        System.out.println("file_open_error="+foe.getMessage());
      }
  }

  public void close()
  {
    try
    {     
      ctq.close();     
      srn.close();     
      ts.clear();     
     
    } catch (Exception fce)
      {
        System.out.println("file_close_error="+fce.getMessage());
      }
  }

  private int getRowNumbers()
  {
      CXMMetaDataReader cmr = new CXMMetaDataReader();
      cmr.setFileStatus(project,f_name);
      int out_int = Integer.parseInt(cmr.getProfile("NUMBER_OF_ROWS"));
      cmr.close();
      return out_int;
  }

  public Hashtable getUniqueValueSet()
  {
      String str_value;
      for (int i=1; i<=row_numbers; i++)
      {
          str_value = ctq.getStringInColumn(i);       
          if (!uvh.containsKey(str_value))
          {
              uvh.put(str_value,srn.getValuePosition());
              srn.setRowNumber(i);            
          }
          else
          {
             srn.setValuePosition((byte[]) uvh.get(str_value));
             srn.setRowNumber(i);
          }
      }
      return uvh;
  }

  public TreeSet getUniqueValueTree()
  {
     
      String str_value;
      for (int i=1; i<=row_numbers; i++)
      {
          str_value = ctq.getStringInColumn(i);
          if (!ts.contains(str_value))
          {
              ts.add(str_value);
          }
      }
      return ts;
  }

}