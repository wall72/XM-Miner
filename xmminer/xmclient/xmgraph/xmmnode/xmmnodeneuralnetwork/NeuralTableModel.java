package xmminer.xmclient.xmgraph.xmmnode.xmmnodeneuralnetwork;

import javax.swing.table.*;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import java.util.Vector;
import javax.swing.*;


class NeuralTableModel extends AbstractTableModel 
{
  public static final ColumnNameFormatData m_columns[] = {
    new ColumnNameFormatData( "번호", 40, JLabel.CENTER ),
    new ColumnNameFormatData( "칼럼이름", 120, JLabel.CENTER),
    new ColumnNameFormatData( "데이타 타입", 120, JLabel.CENTER),
    new ColumnNameFormatData( "변환함수", 150, JLabel.CENTER),
    new ColumnNameFormatData( "입력", 60, JLabel.CENTER ),
    new ColumnNameFormatData( "출력", 60, JLabel.CENTER),
    new ColumnNameFormatData( "사용(X)", 60, JLabel.CENTER )
  };

  public static final int COL_NUMBER = 0;
  public static final int COL_COL_NAME = 1;
  public static final int COL_COL_TYPE = 2;
  public static final int COL_TRANS_FUNC = 3;
  public static final int COL_INPUT_NODE = 4;
  public static final int COL_OUTPUT_NODE = 5;
  public static final int COL_NOT_USE = 6;

  public static final String[] CATEGORIES = {
  "One of N Codes","Boolean", "Normalize" 
  };

  protected Vector m_vector = new Vector();;

  public int getRowCount() {
    return m_vector==null ? 0 : m_vector.size(); 
  }

  public int getColumnCount() { 
    return m_columns.length; 
  } 

  public String getColumnName(int column) { 
    return m_columns[column].m_title; 
  }
 
  
  public boolean isCellEditable(int row, int col)
  {
    if (col >= 3)
    {
      return true; 
    }
    else
      return false;
  }


  public Object getValueAt(int nRow, int nCol) {
    if (nRow < 0 || nRow>=getRowCount())
      return "";
    ColumnData row = (ColumnData)m_vector.elementAt(nRow);
    switch (nCol) {
      case COL_NUMBER: return row.m_number;
      case COL_COL_NAME: return row.m_col_name;
      case COL_COL_TYPE: return row.m_col_type ;
      case COL_TRANS_FUNC: return CATEGORIES[row.m_category.intValue()];
      case COL_INPUT_NODE: return row.m_input_check;
	  case COL_OUTPUT_NODE: return row.m_output_check;
	  case COL_NOT_USE: return row.m_not_use_check;
	 }
    fireTableCellUpdated(nRow,nCol);
    return "";
  }
 

  public void setValueAt(Object value, int nRow, int nCol) {
    if (nRow < 0 || nRow>=getRowCount())
      return;
    ColumnData row = (ColumnData)m_vector.elementAt(nRow);
    String svalue = value.toString();

    switch (nCol) {
      case COL_NUMBER: 
        row.m_number = new Integer(svalue); 
        break;
      case COL_COL_NAME:
        row.m_col_name = svalue; 
        break;
      case COL_COL_TYPE:
	    row.m_col_type = svalue;
		break;
	  case COL_TRANS_FUNC:
	    for (int k=0; k<CATEGORIES.length; k++)
          if (svalue.equals(CATEGORIES[k])) {
            row.m_category = new Integer(k);
            break;
          }
        break;
      case COL_INPUT_NODE:
        row.m_input_check = (Boolean)value; 
        break;
	  case COL_OUTPUT_NODE:
        row.m_output_check = (Boolean)value; 
        break;
	 case COL_NOT_USE:
        row.m_not_use_check = (Boolean)value; 
        break;
    }
   fireTableCellUpdated(nRow,nCol);
  }

 public void setData_1(ColumnData colData,int row) {
    if (row < 0)
      row = 0;
    if (row > m_vector.size())
      row = m_vector.size();
    m_vector.insertElementAt(colData, row);
  }

// 수정 2002.09.15 by 이창호
public void setData(Vector attriName, Vector attriType) {
    m_vector.removeAllElements();
    int  num_attributes = attriName.size(); //table의 row 수
 	for (int i=0;i < num_attributes; i++ )
	{
	  ColumnData colData = new ColumnData();
	  colData.m_number = new Integer(i+1);
	  colData.m_col_name = (String)attriName.elementAt(i);

      colData.m_col_type = (String)attriType.elementAt(i);

	  if(colData.m_col_type.equals("INTEGER")) colData.m_category = new Integer(2);
	  else if(colData.m_col_type.equals("STRING")) colData.m_category = new Integer(0);
	  else colData.m_category = new Integer(2);
      
	  if(i == (num_attributes - 1)){
	      colData.m_input_check = new Boolean(false); 
          colData.m_output_check = new Boolean(true); 
	      colData.m_not_use_check = new Boolean(false); 
	  }else{
	      colData.m_input_check = new Boolean(true); 
          colData.m_output_check = new Boolean(false); 
	      colData.m_not_use_check = new Boolean(false); 
	  }

	  m_vector.addElement(colData);
  	}

 }

  public void insert(int row) {
    if (row < 0)
      row = 0;
    if (row > m_vector.size())
      row = m_vector.size();
    m_vector.insertElementAt(new ColumnData(), row);
  }


  public boolean delete(int row) {
    if (row < 0 || row >= m_vector.size())
      return false;
    m_vector.remove(row);
      return true;
  }
}  

class ColumnNameFormatData  // 컬럼 명에 대한 포맷
{
  public String  m_title;
  int m_width;
  int m_alignment;

  public ColumnNameFormatData(String title, int width, int alignment) {
    m_title = title;
    m_width = width;
    m_alignment = alignment;
  }
}
