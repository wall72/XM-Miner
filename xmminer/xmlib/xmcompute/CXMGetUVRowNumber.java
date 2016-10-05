
/**
 * Title:        <p>
 * Description:  <p>
 * Copyright:    Copyright (c) <p>
 * Company:      <p>
 * @author
 * @version 1.0
 */
package xmminer.xmlib.xmcompute;

import java.io.*;
import xmminer.xmlib.xmfileprocess.*;

public class CXMGetUVRowNumber
{
  private RandomAccessFile raf;
  private File linked_list_file;
  private String linked_list_file_name;
  private String project;
  private String arc;
  private String column;
  private long next_position;
  private CXMTransByteValue tbv = new CXMTransByteValue();
  private CXMFileInfo cfi = new CXMFileInfo();

  public CXMGetUVRowNumber()
  {
  }

  public void setFileStatus(String project_name, String arc_name, String column_name)
  {
     project = project_name;
     arc = arc_name;
     column = column_name;
     cfi.setFileStatus(project,arc);
     cfi.setColumnName(column);
     fileOpen();
  }

  public void close()
  {
      try
      {
         raf.close();
      } catch (Exception foe)
        {
            System.out.println("linked_list_file_close_error");
        }
  }

  private void fileOpen()
  {
      try
      {
          linked_list_file_name = cfi.getIndexPath() + arc + "_" + cfi.getColumnSequence() + "_uvrn.idx";
          linked_list_file = new File(linked_list_file_name);
          raf = new RandomAccessFile(linked_list_file,"r");
      } catch (Exception foe)
        {
            System.out.println("linked_list_file_open_error");
        }
  }

  public int getRowNumber(byte[] byte_value)
  {
      long value_position = tbv.getByteToLong(byte_value);
      try
      {
          raf.seek(value_position);
          int value_number = raf.readInt();
          next_position = raf.readLong();
          return value_number;
      } catch (Exception grne)
        {
            System.out.println("get_row_number_error");
            return -1;
        }
  }

  public int getNextRowNumber()
  {
      if (next_position == 0)
      {
          return 0;
      }
      else
      {
          try
          {
              raf.seek(next_position);
              int value_number = raf.readInt();
              next_position = raf.readLong();
              return value_number;
          } catch (Exception gnrne)
            {
                System.out.println("get_next_row_number_error");
                return -1;
             }
      }
  }
}