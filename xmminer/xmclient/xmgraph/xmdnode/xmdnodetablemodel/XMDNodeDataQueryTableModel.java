package xmminer.xmclient.xmgraph.xmdnode.xmdnodetablemodel;

import javax.swing.table.AbstractTableModel;

public class XMDNodeDataQueryTableModel extends AbstractTableModel
{
  private Object[][] data;
  private String[] table_column;
  private String[] column_list;
  //private String[][] data;
  private int row_size = 100;
  private int column_size;

  public void setModelValue(String[][] i_data)
  {
      if (i_data.length<100)
      {
          row_size = i_data.length;
          System.out.println("new_size="+row_size);
      }
      data = new Object[row_size][column_size];
      //data = new String[row_size][column_size];
      table_column = new String[column_size];
      for (int k=0; k<column_size; k++)
      {
          table_column[k] = column_list[k];
      }
      for (int i=0; i<row_size; i++)
      {
        for (int j=0; j<column_size; j++)
        {
           data[i][j] = i_data[i][j];
        }
      }
  }

  public int getColumnCount()
  {
    return table_column.length;
  }

  public int getRowCount()
  {
    return data.length;
  }

  public String getColumnName(int col)
  {
    return table_column[col];
  }

  public Object getValueAt(int row, int col)
  {
    return data[row][col];
  }

  public Class getColumnClass(int c)
  {
    return getValueAt(0, c).getClass();
  }

  public boolean isCellEditable(int row, int col)
  {
    boolean r_value = false;
    if (col==0)
    {
      r_value = true;
    }
    else
    {
      r_value = false;
    }
    return r_value;
  }

  public void setValueAt(Object value, int row, int col)
  {
    data[row][col] = value;
    fireTableCellUpdated(row, col);
  }

  public void setColumnList(String[] i_list)
  {
    column_list = i_list;
    column_size = column_list.length;
  }
}
