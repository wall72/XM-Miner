
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

public class CXMGetAssociationTransaction
{
  private CXMTableQuerier ctq = new CXMTableQuerier();
  private CXMGetUVRowNumber grn = new CXMGetUVRowNumber();
  private String project;
  private String arc;
  private Vector ass_trans_v;
  private Vector row_index_v;

  public CXMGetAssociationTransaction()
  {
  }

  public void setFileStatus(String project_name, String arc_name, String trans_field, String target_field)
  {
      project = project_name;
      arc = arc_name;
      ass_trans_v = new Vector();
      row_index_v = new Vector();
      ctq.setFileStatus(project, arc, target_field, null);
      grn.setFileStatus(project,arc,trans_field);
  }

  public void close()
  {
      ctq.close();
  }

  public Vector getAssociationTransaction(byte[] position_array)
  {
      int[] row_index_position = getRowIndexPosition(position_array);
      ass_trans_v.clear();
      for (int i=0; i<row_index_position.length; i++)
      {
          int row_number = row_index_position[i];
          ass_trans_v.addElement(ctq .getStringInColumn(row_index_position[i]));
      }
      return ass_trans_v;
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

}