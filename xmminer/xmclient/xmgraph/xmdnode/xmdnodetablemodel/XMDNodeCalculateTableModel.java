package xmminer.xmclient.xmgraph.xmdnode.xmdnodetablemodel;

import javax.swing.table.AbstractTableModel;

public class XMDNodeCalculateTableModel extends AbstractTableModel
{
  private Object[][] data;
  private String[] table_column;
  private String[] column_list;
  private String[] column_type;

  public void setSelectedModelValue()
  {
      data = new Object[column_list.length][3];
      table_column = new String[3];
      for (int i=0; i<column_list.length;i++)
      {
        data[i][0] = new Boolean(true);
        data[i][1] = column_list[i];
        data[i][2] = column_type[i];
      }
      table_column[0] = "선택";
      table_column[1] = "칼럼명";
      table_column[2] = "형태";
  }

  public void setNotSelectedModelValue()
  {
      data = new Object[column_list.length][3];
      table_column = new String[3];
      for (int i=0; i<column_list.length;i++)
      {
        data[i][0] = new Boolean(false);
        data[i][1] = column_list[i];
        data[i][2] = column_type[i];
      }
      table_column[0] = "선택";
      table_column[1] = "칼럼명";
      table_column[2] = "형태";
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
  }

  public void setColumnType(String[] i_type)
  {
    column_type = i_type;
  } 

}
