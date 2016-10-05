package xmminer.xmclient.xmgraph.xmdnode.xmdnodetablemodel;

import javax.swing.table.AbstractTableModel;

public class XMDNodeColumnSelectTableModel extends AbstractTableModel
{
  private Object[][] data;
  private String[] table_column;
  private String[] column_list;
  private String[] column_type;
  private String[] average_value;
  private String[] max_value;
  private String[] min_value;
  private String[] max_frequency_value;
  private String[] miss_percent;

  public void setModelValue()
  {      
      data = new Object[column_list.length][8];
      table_column = new String[8];
      for (int i=0; i<column_list.length;i++)
      {
        data[i][0] = new Boolean(false);
        data[i][1] = column_list[i];
        data[i][2] = column_type[i];
        data[i][3] = average_value[i];
        data[i][4] = max_value[i];
        data[i][5] = min_value[i];
        data[i][6] = max_frequency_value[i];
        data[i][7] = miss_percent[i];
      }
      table_column[0] = "선택";
      table_column[1] = "칼럼명";
      table_column[2] = "형태";
      table_column[3] = "평균값";
      table_column[4] = "최대값";
      table_column[5] = "최소값";
      table_column[6] = "최다빈도값";
      table_column[7] = "Missing(%)";
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
  
  public void setAverageValue(String[] i_value)
  {
    average_value = i_value;
  }                         

  public void setMaxValue(String[] i_value)
  {
    max_value = i_value;
  }

  public void setMinValue(String[] i_value)
  {
    min_value = i_value;
  }

  public void setMaxFreqValue(String[] i_value)
  {
    max_frequency_value = i_value;
  }

  public void setMissPercent(String[] i_value)
  {
    miss_percent = i_value;
  }

}
