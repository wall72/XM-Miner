package xmminer.xmclient.xmgraph.xmxnode.xmxnodefile.filextable;

import javax.swing.table.*;
import javax.swing.*;
import java.io.*;
import java.util.Date;
import java.lang.StringBuffer;
import java.util.GregorianCalendar;
import java.util.Calendar;

import xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.*;

public class DirectoryTableModel extends AbstractTableModel
{
    Object[][] data = null;
    String[] columnNames = { "이름", "크기", "형식", "수정한 날짜" };
  	 ImageIcon textIcon = new ImageIcon("./xmminer/xmclient/xmgraph/xmxnode/xmxnodefile/filexscr/document.gif"); 
  	 ImageIcon folderIcon = new ImageIcon("./xmminer/xmclient/xmgraph/xmxnode/xmxnodefile/filexscr/cf.gif"); 

    // case for client directory
    public DirectoryTableModel(String selectedFilename)
    {
        GregorianCalendar calendar = new GregorianCalendar();
        File[] flist = new File(selectedFilename).listFiles();
		  JLabel label = null;
        data = new Object[flist.length][4];
        for (int i=0; i<flist.length; i++) {
            data[i][3] = new JLabel(makeDate(flist[i].lastModified(), calendar));
            if (flist[i].isDirectory()) {
            		data[i][0] = new JLabel(flist[i].getName(), folderIcon, JLabel.LEFT);
            		data[i][2] = new JLabel("파일 폴더");
            		data[i][1] = new JLabel(" ", JLabel.RIGHT);
            } else {
            		data[i][0] = new JLabel(flist[i].getName(), textIcon, JLabel.LEFT);
            		data[i][2] = new JLabel("file");
            		data[i][1] = new JLabel(makeSize(flist[i].length()), JLabel.RIGHT);
            }
        }
    }

    // case for server directory
    public DirectoryTableModel(DirectoryType[] dirList, int selectedIndex) throws NumberFormatException
    {
        if (dirList[selectedIndex].childnodeNames[0].equals("NONE")) return;
        int size = dirList[selectedIndex].childnodeNames.length;
        data = new Object[size][4];

        for (int i=0; i<size; i++) {
            int index = Integer.parseInt( dirList[selectedIndex].childnodeNames[i] );
            if (dirList[index].nodeType.equals("folder")) {
            		data[i][0] = new JLabel(dirList[index].nodeName, folderIcon, JLabel.LEFT);
            		data[i][1] = new JLabel(dirList[index].nodeSize, JLabel.RIGHT);
            		data[i][2] = new JLabel("파일 폴더");
            		data[i][3] = new JLabel(dirList[index].nodeDate);
            } else {
            		data[i][0] = new JLabel(dirList[index].nodeName, textIcon, JLabel.LEFT);
            		data[i][1] = new JLabel(dirList[index].nodeSize, JLabel.RIGHT);
            		data[i][2] = new JLabel("file");
            		data[i][3] = new JLabel(dirList[index].nodeDate);
            }// end of else		 
        }// end of for
    }// end of constructor

    public String getColumnName(int col) { return columnNames[col]; }
    public int getColumnCount() {   return columnNames.length;   }
    public int getRowCount() {    return data.length;   }

    public Object getValueAt(int row, int col)
    {
        return data[row][col];
    }

  	private String makeDate(long lastModified, GregorianCalendar calendar)
 	{
				Date makeDate = new Date(lastModified);			
				calendar.setTime(makeDate);
								
				StringBuffer whenMake = new StringBuffer(new Integer(calendar.get(Calendar.YEAR)).toString());
				whenMake.append('-');
				int m = calendar.get(Calendar.MONTH)+1;
				if (m <10) whenMake.append('0');
				whenMake.append(m);
				whenMake.append('-');
				int d = calendar.get(Calendar.DATE);
				if (d < 10) whenMake.append('0');
				whenMake.append(d);
				m = calendar.get(Calendar.AM_PM);
				if (m == 0) 	whenMake.append(" 오전 ");
				else 	whenMake.append(" 오후 ");
				m = calendar.get(Calendar.HOUR);
				whenMake.append(new Integer(m).toString());
				m = calendar.get(Calendar.MINUTE);
				whenMake.append(":");
				whenMake.append(new Integer(m).toString());
				return whenMake.toString();
  	}// end of makeDate
  	private String makeSize(long size)
  	{
  			size = size >> 10;
  			if (size > 999) {
  					StringBuffer s = new StringBuffer(new Long(size).toString());
  					int i = s.length();
  					s.insert(i-3, ',');
  					s.append("KB ");
  					return s.toString(); 
  			} else 	if (size <= 0){
  					return "1KB ";
  			} else 	{
  					String s = new Long(size).toString() + "KB ";
  					return s;
  			}
  	}// end of makeSize


}// end of class
