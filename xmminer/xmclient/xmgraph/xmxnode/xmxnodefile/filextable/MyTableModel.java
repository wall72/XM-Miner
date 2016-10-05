package xmminer.xmclient.xmgraph.xmxnode.xmxnodefile.filextable;

import javax.swing.table.*;

public class MyTableModel extends AbstractTableModel
{
    String[][] data = null;
    String[] columnNames = null;

    public MyTableModel(String[][] c, String[] names)
    {
        data = c;
        columnNames = names;
    }

    public String getColumnName(int col) { return columnNames[col]; }
    public int getColumnCount() {   return columnNames.length;   }
    public int getRowCount() {    return data.length;   }

    public Object getValueAt(int row, int col)
    {
        return data[row][col];
    }
}