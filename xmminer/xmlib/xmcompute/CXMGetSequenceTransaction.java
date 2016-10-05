
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
import java.util.Vector;
import java.io.*;

public class CXMGetSequenceTransaction
{
  private CXMTableQuerier ctq = new CXMTableQuerier();
  private CXMGetUVRowNumber grn = new CXMGetUVRowNumber();
  private String project;
  private String arc;
  private String time_value;
  private String new_time_value;
  private String transaction_value;
  private String sort_index_file_name;
  private int byte_size;
  private int out_int_value;
  private int temp_int_value;
  private int factor_int;
  private int number_index;
  private long pos;
  private String[] seq_value_array;
  private String[] temp_value_array;
  private File sort_index_file;
  private Vector seq_trans_v;
  private Vector row_index_v;
  private byte[] index_byte;
  private RandomAccessFile sif;

  public CXMGetSequenceTransaction()
  {
  }

  public void setFileStatus(String project_name, String arc_name, String time_field, String trans_field, String target_field)
  {
      project = project_name;
      arc = arc_name;
      seq_trans_v = new Vector();
      row_index_v = new Vector();
      seq_value_array = new String[2];
      seq_value_array[0] = time_field;
      seq_value_array[1] = target_field;
      sort(time_field);
      sortIndexfileOpen();
      ctq.setFileStatus(project, arc, null, seq_value_array);
      grn.setFileStatus(project,arc,trans_field);
      index_byte = new byte[byte_size];
  }

  public void close()
  {
      ctq.close();
  }

  private void sort(String sort_column)
  {
      CXMColumnSort ccs = new CXMColumnSort();
      ccs.setFileStatus(project, arc, sort_column);
      ccs.sort();
      sort_index_file_name = ccs.getSortIndexFileName();
      byte_size = ccs.getByteSize();
      ccs.close();
  }

  private void sortIndexfileOpen()
  {
      try
      {
         sort_index_file = new File(sort_index_file_name);
         sif = new RandomAccessFile(sort_index_file,"r");
      } catch(Exception re)
        {
            System.out.println("file_open_error="+re.getMessage());
        }
  }

  public Vector getSequenceTransaction(byte[] position_array)
  {
      int[] row_index_position = getRowIndexPosition(position_array);
      seq_trans_v.clear();
      time_value = "";
      transaction_value = "";
      for (int i=0; i<row_index_position.length; i++)
      {
          int row_number = row_index_position[i];
          temp_value_array = getValueArrayAt(row_index_position[i]);
          new_time_value = temp_value_array[0];
          if (new_time_value.equals(time_value))
          {
              transaction_value = transaction_value+","+temp_value_array[1];
          }
          else
          {
              if (!transaction_value.equals(""))
              {
                 seq_trans_v.addElement(transaction_value);
              }
              transaction_value = temp_value_array[1];
              time_value = new_time_value;
          }
      }
      if (!transaction_value.equals(""))
      {
          seq_trans_v.addElement(transaction_value);
      }
      return seq_trans_v;
  }

  private String[] getValueArrayAt(int i_num)
  {
    number_index = getSortedRowNumber(i_num);
    return ctq.getStringArrayInColArray(number_index);
  }

  private int[] getRowIndexPosition(byte[] position_array)
  {
     row_index_v.clear();
     int a = grn.getRowNumber(position_array);
     row_index_v.add(new Integer(a));
     int next_row_index;
     do
     {
         next_row_index = grn.getNextRowNumber();
         if (next_row_index > 0)
         {
            row_index_v.add(new Integer(next_row_index));
         }
     } while (next_row_index > 0);
     return getIntArray();
  }

  private int[] getIntArray()
  {
      int array_size = row_index_v.size();
      int[] int_value_array = new int[array_size];
      for (int i=0; i<array_size; i++)
      {
          int_value_array[i] = ((Integer) row_index_v.elementAt(i)).intValue();
      }
      return int_value_array;
  }

  private int getSortedRowNumber(int i_int)
  {
    pos = (long) (i_int-1)*byte_size;
    try
    {
      sif.seek(pos);
      for (int i=0; i<byte_size; i++)
      {
        index_byte[i] = sif.readByte();
      }
    } catch (Exception se)
      {
        System.out.println("sorted_number_read_error="+se.getMessage());
      }
    return setIntValue(index_byte);
  }

  private int setIntValue(byte[] i_byte)
  {
    out_int_value = 0;
    factor_int = 1;
    for (int i=0; i<byte_size; i++)
    {
    	temp_int_value = (int) i_byte[i];
    	out_int_value = out_int_value + temp_int_value*factor_int;
    	factor_int = factor_int*128;
    }
    return out_int_value;
  }

}