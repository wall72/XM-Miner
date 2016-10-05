package xmminer.xmclient.xmgraph.xmxnode.xmxnodefile.filextable;

import javax.swing.table.*;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.Dimension;

import xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.ColumnInfoType;

public class ColumnInfoTableModel extends AbstractTableModel
{
    Object[][] data = null;
    String[] columnNames = { "컬럼명", "컬럼타입", "사용여부" };

    public ColumnInfoTableModel(ColumnInfoType[] cList)
    {        
//        columnNames = makeColumnNames(numOfCols, cList);
//        data = makeData(numOfCols, cList, rows);
			 data = makeData(cList);
    }
/*    
    private String[] makeColumnNames(int numOfCols, ColumnInfoType[] cList)
    {
    		String[] names = new String[numOfCols];
    		for (int i=0; i<numOfCols; i++)		names[i] = cList[i].name;
    		return names;
    }*/
    private String[] makeColumnNames(int numOfCols, ColumnInfoType[] cList)
    {
    		String[] contents = new String[numOfCols];
    		
    		for (int i=0; i<numOfCols; i++) 
    				contents[i] = new String("use");//new JCheckBox("use", cList[i].use);
	 		return contents;
	 }
    
    private Object[][] makeData(int numOfCols, ColumnInfoType[] cList, String[][] rows)
    {
    		System.out.println("column # : " + numOfCols);
    		System.out.println("row row # : " + rows.length + "  row col # : " + rows[0].length);  
    		Object[][] contents = new Object[2+rows.length][numOfCols];
    		
    		for (int i=0; i<numOfCols; i++) {
    				
    				// make Column Type;
        			JComboBox comboBox = new JComboBox(new String[] {"INTEGER", "FLOAT", "ENUMERATION", "STRING" });
    				comboBox.setSelectedItem(cList[i].type);
//    				comboBox.setSize(new Dimension(60,22));
    				contents[0][i] = comboBox;
    				
    				// make Column Use
    				contents[1][i] = new JCheckBox("use", cList[i].use);
    				
    				// make Sample Data		
    				for (int j=0; j<rows.length; j++) {
    						JLabel label = new JLabel(rows[j][i]);
//    						label.setSize(new Dimension(60,22));
    						contents[2+j][i] = label;
    				}
    		}
    		return contents;
    }
    private Object[][] makeData(ColumnInfoType[] cList)
    {
    		Object[][] contents = new Object[cList.length][3];
//    		System.out.println("in ColumnInfoTableMode... cList length is " + cList.length);
    		for (int i=0; i<cList.length; i++) {
//    		System.out.println("name is " + cList[i].name);
					contents[i][0] = (Object)cList[i].name;					    				
					contents[i][1] = (Object)cList[i].type;					    				
					contents[i][2] = (Object)(new Boolean(cList[i].use));					    				
    		}
    		return contents;
    }

    public void setUseRowValue(Boolean b)
    {
        for (int i=0; i<columnNames.length; i++)   data[1][i] = b;
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