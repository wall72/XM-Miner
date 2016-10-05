package xmminer.xmclient.xmgraph.xmmnode.xmmnodeassociationrule;

import javax.swing.table.AbstractTableModel;
import javax.swing.JCheckBox;
import java.util.Vector;

class assoTableModel extends AbstractTableModel{
  private Object[][] i_data; // = new Object[100][4];  //(frolle)

  String[] cn = {"번호", "선택", "컬럼 명", "타입"};
  int num_attributes = 0;   //DB의 Attributes 수(=talbe의 row 수)

  public int getColumnCount(){ //4
    return cn.length;
  }

  public int getRowCount(){  //DB의 Attributes 수(=talbe의 row 수)
    return num_attributes;
  }

  public String getColumnName(int col){
    return cn[col];
  }

  public Object getValueAt(int row, int col){
    return i_data[row][col];
  }

  public Class getColumnClass(int c){
    return getValueAt(0, c).getClass();
  }

  public boolean isCellEditable(int row, int col){
    if (col == 1) return true;
    else return false;
  }

  public void setValueAt(Object value, int row, int col){
    i_data[row][col] = value;
    fireTableCellUpdated(row, col);
  }

  public void setDataList(Object[][] value){
    i_data = value;
  }

  public void setHeaderList(String[] val, Object[][] value){
    cn = val;
    i_data = value;
  }

  //"All" button이 눌려질때 불려질 함수
  public void includeAll(){
    for (int i = 0; i < num_attributes; i++) //table의 row 수 만큼
	    i_data[i][1] = new Boolean(true);
    fireTableRowsUpdated(0, num_attributes);
  }

  //"None" button이 눌려질때 불려질 함수
  public void removeAll() {
    for (int i = 0; i < num_attributes; i++) //table의 row 수 만큼
      i_data[i][1] = new Boolean(false);
    fireTableRowsUpdated(0, num_attributes);
  }

  //"Invert" button이 눌려질때 불려질 함수
  public void invert(){
    for(int i = 0; i < num_attributes; i++){ //table의 row 수 만큼
      if(((Boolean)i_data[i][1]).booleanValue() == true) i_data[i][1] = new Boolean(false);
      else i_data[i][1] = new Boolean(true);
    }
    fireTableRowsUpdated(0, num_attributes);
  }

  //assoInputDialog의 생성자에서 불려지는 함수
  public void setData(Vector attriName, Vector attriType){
    num_attributes = attriName.size(); //table의 row 수
    i_data = new Object[num_attributes][4];

    for(int i = 0; i < num_attributes;i ++){
      //No. 넣기
      i_data[i][0] = new Integer( i + 1 );

      //Boolean(check) 넣기
      i_data[i][1] = new Boolean(true);

	  //attribute name cell 넣기
      i_data[i][2] = (String)attriName.elementAt(i);

      //attribute type cell 넣기
      i_data[i][3] = (String)attriType.elementAt(i);
    }
  }

}