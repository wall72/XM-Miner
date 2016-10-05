
/**
 * Title:        null<p>
 * Description:  null<p>
 * Copyright:    null<p>
 * Company:      <p>
 * @author
 * @version null
 */
package xmminer.xmlib.xmcompute;

import xmminer.xmlib.xmtable.*;
import xmminer.xmlib.xmfileprocess.*;
import java.util.*;

public class CXMGetUniqueValueCount
{

  private String project;
  private String arc;
  private String column;
  private String unique_file_name;
  private CXMUniqueValueTableReader uvr;
  private Hashtable uvt;
  private boolean set_hash_table = false;

  public CXMGetUniqueValueCount()
  {
  }

  public void setFileStatus(String project_name, String arc_name, String column_name)
  {
      project = project_name;
      arc = arc_name;
      column = column_name;
      CXMFileInfo cfi = new CXMFileInfo();
      cfi.setFileStatus(project,arc);
      cfi.setColumnName(column);
      unique_file_name = arc+"_"+cfi.getColumnSequence();
      fileOpen();
  }

  private void fileOpen()
  {
      uvr = new CXMUniqueValueTableReader();
      uvr.setFileStatus(project, unique_file_name);
  }

  public void close()
  {
      uvr.close();
      if (set_hash_table)
      {
         uvt.clear();
      }
  }

  public String getUniqueValueAt(int row_number)
  {
      return uvr.getUniqueValueAt(row_number);
  }

  public int getUniqueValueNumber()
  {
      return uvr.getUniqueValueNumber();
  }

  public int getValueCountAt(int row_number)
  {
      return uvr.getValueCountAt(row_number);
  }

  public String getCountOfValue(String value)
  {
      return uvr.getCountOfValue(value);
  }

  public String[][] getUniqueValueCount()
  {
      uvt = uvr.getUniqueValueTable();
      String[][] uv_array = new String[uvt.size()][2];
      String key;
      String count;
      try
      {
          int index = 0;
          Enumeration hEum = uvt.keys();
          while(hEum.hasMoreElements())
          {
              key = (String) hEum.nextElement();
              count = (String) uvt.get(key);
              uv_array[index][0] = key;
              uv_array[index][1] = count;
              index = index + 1;
          }
          set_hash_table = true;          
      } catch(Exception cte)
        { System.out.println("CXMGetUniqueValueCount : getUniqueValueCount_error="+cte.getMessage());}        
      return uv_array;
  }
}
