package xmminer.xmclient.xmgraph.xmxnode.xmxnodedb;

import javax.swing.table.AbstractTableModel;

class TableModel extends AbstractTableModel{
  Object[][] i_data = {{"","",new Integer(4),"",""},{"","",new Integer(3),"",""},{"","",new Integer(2),"",""},{"","",new Integer(1),"",""}};

  String[] cn = {"","Column Name","Type","Length","isNull"};

  public int getColumnCount() {
   return cn.length;
  }

  public int getRowCount() {
   return i_data.length;
  }

  public String getColumnName(int col) {
    return cn[col];
  }

  public Object getValueAt(int row, int col) {
    return i_data[row][col];
  }

  public Class getColumnClass(int c) {
    return getValueAt(0, c).getClass();
  }

  public boolean isCellEditable(int row, int col) {
    return false;
  }

  public void setValueAt(Object value, int row, int col) {
    i_data[row][col] = value;
    fireTableCellUpdated(row, col);
  }

  public void setDataList(Object[][] value) {
    i_data = value;
  }

  public void setHeaderList(String[] val, Object[][] value) {
    cn = val;
    i_data = value;
  }
}