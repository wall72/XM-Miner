package xmminer.xmclient.xmgraph.xmdnode.xmdnodetablemodel;

import javax.swing.table.AbstractTableModel;

public class XMDNodeFilterValueQueryTableModel extends AbstractTableModel
{
  private Object[][] data;
  private String[] table_column;
  private String[] filtering_value;
  private String[] frequency_value;
  private String[] percent_value;
  private int row_size = 100;
  private int column_size = 3;

  public void setModelValue()
  {
      if (filtering_value.length<100)
      {
          row_size = filtering_value.length;
          System.out.println("new_size="+row_size);
      }
      data = new Object[row_size][column_size];
      table_column = new String[column_size];
      //filtering_value = new String[row_size];
      //frequency_value = new String[row_size];
      //percent_value = new String[row_size];
      for (int i=0; i<row_size;i++)
      {
         data[i][0] = filtering_value[i];
         System.out.println("f_value="+data[i][0]);
         data[i][1] = frequency_value[i];
         System.out.println("fr_value="+data[i][1]);
         data[i][2] = percent_value[i];
         System.out.println("p_value="+data[i][2]);
      }
      table_column[0] = "필터링된 값";
      table_column[1] = "빈도";
      table_column[2] = "비율";
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

  public void setFilteringValue(String[] i_value)
  {
     filtering_value = i_value;
  }

  public void setFrequencyValue(String[] i_frequency)
  {
     frequency_value = i_frequency;
  }

  public void setPercentValue(String[] i_percent)
  {
     percent_value = i_percent;
  }
}
