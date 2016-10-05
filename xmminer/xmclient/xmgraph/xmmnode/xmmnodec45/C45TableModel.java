package xmminer.xmclient.xmgraph.xmmnode.xmmnodec45;

import javax.swing.table.AbstractTableModel;
import javax.swing.JCheckBox;
import java.util.Vector;


class C45TableModel extends AbstractTableModel
{

  Object[][] i_data = new Object[630][6];  //(frolle)


  String[] cn = {"번호","칼럼 이름","칼럼 데이타 타입","입력변수","출력변수","사용않음"};
  int num_attributes = 0;   //DB의 Attributes 수(=talbe의 row 수)
  boolean m_bEnable=true;

  public int getColumnCount() { //4
    return cn.length;
  }

  public int getRowCount() {  //DB의 Attributes 수(=talbe의 row 수)
    //return i_data.length;
    return num_attributes;
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

  public void SetEnable(boolean m_aEnable)
  {
   m_bEnable=m_aEnable;
  }

  public boolean isCellEditable(int row, int col)
  {
    /*if (col >= 3)
    {
      System.out.println( "isCellEditable row="+ row+"   col"+col+"  m_bEnable"+m_bEnable);
      return m_bEnable; //checkbox
    }
    else*/
      return false;
  }

  public void setValueAt(Object value, int row, int col) {
          if(m_bEnable)
          {
                    i_data[row][col] = value;
                    fireTableCellUpdated(row, col);
           }         
  }
  public void setValueBool(int row, int col,int num_rows) {
          int i;
          for(i=3;i<=5;i++)
          {
            System.out.println("row= "+row+"   col="+col+"  i ="+i);
			if(i==col)
			{
			    i_data[row][i] = new Boolean(true);
				System.out.println("row= "+row+"   col="+col+"  i ="+i+"  true");
			}
            else 
			{
			    i_data[row][i] = new Boolean(false);
				System.out.println("row= "+row+"   col="+col+"  i ="+i+"  false");
            }
			if(col==3 || col==4)
            fireTableCellUpdated(row, i);
          }
          
          
          if(col==4)
          {
            for(i=0;i<num_rows;i++)
            {
               
			  if(row!=i)
			  {
                boolean check=((Boolean)i_data[i][4]).booleanValue();
				if(check)
				     i_data[i][5] =new Boolean(true);
				i_data[i][4] = new Boolean(false);
				
			  }
				System.out.println("row= "+row+"   col="+col+"  i ="+i+"  false");
			fireTableCellUpdated(i, col);
            }	
          }	  
          
  }

  public void setDataList(Object[][] value) {
            i_data = value;
  }

  public void setHeaderList(String[] val, Object[][] value) {
            cn = val;
            i_data = value;
  }

  //assoInputDialog의 생성자에서 불려지는 함수
  public void setData(Vector attriName, Vector attriType)
  {
    num_attributes = attriName.size(); //table의 row 수
    System.out.println("setData   #1");
	System.out.println("num_attributes="+num_attributes);
	int i=0;
    for( i = 0; i < num_attributes-1;i ++)
    {
      //No. 넣기
      i_data[i][0] = new Integer( i + 1 );

      //attribute name cell 넣기
      i_data[i][1] = (String)attriName.elementAt(i);

      //attribute type cell 넣기
      i_data[i][2] = (String)attriType.elementAt(i);
      i_data[i][3] = new Boolean(true);
      i_data[i][4] = new Boolean(false);
      i_data[i][5] = new Boolean(false);
    }
    i_data[i][0] = new Integer( i + 1 );

      //attribute name cell 넣기
    i_data[i][1] = (String)attriName.elementAt(i);

      //attribute type cell 넣기
    i_data[i][2] = (String)attriType.elementAt(i);
    i_data[i][3] = new Boolean(false);
    i_data[i][4] = new Boolean(true);
    i_data[i][5] = new Boolean(false);

  }

//
//End of Class
//


}
