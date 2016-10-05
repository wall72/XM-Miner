
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

public class CXMSetUVRowNumber
{
  private RandomAccessFile raf;
  private File linked_list_file;
  private String linked_list_file_name;
  private String project;
  private String arc;
  private String column;
  private long file_size;
  private long set_position;
  private CXMTransByteValue tbv = new CXMTransByteValue();
  private CXMFileInfo cfi = new CXMFileInfo();

  public CXMSetUVRowNumber()
  {
  }

  public void setFileStatus(String project_name, String arc_name, String column_name)
  {
     project = project_name;
     arc = arc_name;
     column = column_name;
     file_size = 0;
     set_position = 0;
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
          linked_list_file.delete();
          raf = new RandomAccessFile(linked_list_file,"rw");
      } catch (Exception foe)
        {
            System.out.println("linked_list_file_open_error");
        }
  }

  public byte[] getValuePosition()
  {
      try
      {
          return tbv.getLongToByte(5, raf.getFilePointer());
      } catch (Exception gvpe)
        {
            System.out.println("get_value_position_error");
            return null;
        }
  }

  public void setValuePosition(byte[] byte_value)
  {
      long value_position = tbv.getByteToLong(byte_value);
      try
      {
          raf.seek(value_position);
          if (file_size > value_position)
          {
              int value_number = raf.readInt();
              set_position = raf.getFilePointer();
              long next_position = raf.readLong();
              if (next_position!=0)
              {
                  setNextPosition(next_position);
              }
          }
      } catch (Exception gvpe)
        {
            System.out.println("set_value_position_error");
        }
  }

  private void setNextPosition(long position)
  {
      try
      {
          raf.seek(position);
          if (file_size > position)
          {
              int value_number = raf.readInt();
              set_position = raf.getFilePointer();
              long next_position = raf.readLong();
              if (next_position!=0)
              {
                  setNextPosition(next_position);
              }
          }
      } catch (Exception gvpe)
        {
            System.out.println("set_value_position_error");
        }
  }

  public void setRowNumber(int row_number)
  {
      try
      {
          long new_position = file_size;
          if (set_position < file_size)
          {
              raf.seek(set_position);
              raf.writeLong(new_position);
          }
          raf.seek(new_position);
          raf.writeInt(row_number);
          raf.writeLong(0);
          file_size = raf.getFilePointer();
          set_position = file_size;
      } catch(Exception srne)
        {
           System.out.println("set_row_number_error");
        }
  }
}