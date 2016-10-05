package xmminer.xmclient.xmgraph.xmxnode.xmxnodefile.filextable;

import javax.swing.table.*;
import javax.swing.JComboBox;

public class ColumnNameTableModel extends AbstractTableModel
{
    Object[][] data = null;
    String[] columnNames = { "컬럼명", "컬럼타입", "사용여부" };

    public ColumnNameTableModel(Object[][] c)
    {
        data = c;
    }

    public void set2ndColumnValue(Boolean b)
    {
        for (int i=0; i<data.length; i++)   data[i][2] = b;
    }

    public String getColumnName(int col) { return columnNames[col]; }
    public int getColumnCount() {   return columnNames.length;   }
    public int getRowCount() {    return data.length;   }

    public Object getValueAt(int row, int col)
    {
        return data[row][col];
    }

    public Class getColumnClass(int c)
    {
        return getValueAt(0,c).getClass();
    }

    public boolean isCellEditable(int row, int col)
    {
        return true;
    }

    public void setValueAt(Object value, int row, int col)
    {
        data[row][col] = value;
        fireTableCellUpdated(row, col);
    }

    public Object[][] getData() { return data; }
}