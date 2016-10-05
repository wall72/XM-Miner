
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

public class CXMSetUniqueValueCount
{
  private CXMTableQuerier ctq;
  private String project;
  private String arc;
  private String column;
  private int row_numbers;
  private String value;
  private String unique_file_name;

  public CXMSetUniqueValueCount()
  {
  }

  public void setFileStatus(String project_name, String arc_name, String column_name)
  {
      ctq = new CXMTableQuerier();
      project = project_name;
      arc = arc_name;
      column = column_name;
      ctq.setFileStatus(project,arc,column,null);
      row_numbers = getRowNumbers();
      CXMFileInfo cfi = new CXMFileInfo();
      cfi.setFileStatus(project,arc);
      cfi.setColumnName(column);
      unique_file_name = arc+"_"+cfi.getColumnSequence();
  }

  public void close()
  {
      ctq.close();
  }

  private int getRowNumbers()
  {
      CXMMetaDataReader cmr = new CXMMetaDataReader();
      cmr.setFileStatus(project, arc);
      int out_int = Integer.parseInt(cmr.getProfile("NUMBER_OF_ROWS"));
      cmr.close();
      return out_int;
  }

  public void createUniqueValueTable()
  {
      CXMFilteredValueCount fvc = new CXMFilteredValueCount();
      for (int i=1; i<=row_numbers; i++)
      {
          value = ctq.getStringInColumn(i);
          int count = fvc.getValueCount(value);
          fvc.setValueCount(value,count+1);
      }
      CXMUniqueValueTableSaver uts = new CXMUniqueValueTableSaver();
      uts.createUniqueValueFile(project, unique_file_name, fvc.getValueTable());
      fvc.close();
  }

}
