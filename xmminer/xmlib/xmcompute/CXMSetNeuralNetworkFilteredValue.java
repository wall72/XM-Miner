
/**
 * Title:        null<p>
 * Description:  null<p>
 * Copyright:    null<p>
 * Company:      <p>
 * @author
 * @version null
 */
package xmminer.xmlib.xmcompute;

import java.util.*;
import xmminer.xmlib.xmtable.*;
import xmminer.xmlib.xmfileprocess.*;

public class CXMSetNeuralNetworkFilteredValue
{
  private String project;
  private String arc;
  private String column;
  private String learning_arc;
  private String filtered_file_name;
  private boolean test = false;
  private Hashtable fvh;
  private CXMUniqueValueTableReader uvr;
  private CXMUniqueValueTableSaver uvs;

  public CXMSetNeuralNetworkFilteredValue()
  {
  }

  public void setFileStatus(String project_name, String arc_name, String column_name, String learning_arc_name, boolean test_check)
  {
      project = project_name;
      arc = arc_name;
      column = column_name;
      test = test_check;
      fvh = new Hashtable();
      CXMFileInfo cfi = new CXMFileInfo();
      cfi.setFileStatus(project,arc);
      cfi.setColumnName(column);
      filtered_file_name = arc + "_" + String.valueOf(cfi.getColumnSequence()) + "_nnfv";
      if (test)
      {
         learning_arc = learning_arc_name + "_" + String.valueOf(cfi.getColumnSequence()) + "_nnfv";
         fvh = getHashtable();
      }
  }

  public int find(String value)
  {
      if (fvh.containsKey(value))
      {
          return Integer.parseInt((String)fvh.get(value));
      }
      else
      {
          return 0;
      }
  }

  public void insert(String value, int value_number)
  {
      fvh.put(value, String.valueOf(value_number));
  }

  public void close()
  {    
      uvs = new CXMUniqueValueTableSaver();    
      uvs.createUniqueValueFile(project,filtered_file_name,fvh);     
      fvh.clear();   
      if (test)
      {
         uvr.close();
      }
  }

  private Hashtable getHashtable()
  {
      uvr = new CXMUniqueValueTableReader();
      uvr.setFileStatus(project,learning_arc);
      return uvr.getUniqueValueTable();
  }

}