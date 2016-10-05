
//Title:        XM-Miner
//Version:
//Copyright:    Copyright (c) 1999
//Author:       Yong Uk Song
//Company:      DIS
//Description:  XM-Miner

package xmminer.xmclient.xmgraph;

import java.io.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.*;

public class XMDialogVNodeStatistics extends JDialog
{
  JPanel panelMain = new JPanel();
  BorderLayout borderLayoutMain = new BorderLayout();
  JScrollPane scrollPaneCenter;
  JTable tableCenter;
  JMenuBar jMenuBar1 = new JMenuBar();
  JMenu jMenu1 = new JMenu();
  JMenuItem jMenuItem1 = new JMenuItem();
  JMenuItem jMenuItem2 = new JMenuItem();
  JMenuItem jMenuItem3 = new JMenuItem();

  JFrame m_frame;
  String m_projectpath;
  String m_projectname;
  int m_id;
  TableModelStatistics tm;

  public XMDialogVNodeStatistics(Frame frame, String title, String data[][], boolean modal, String projectpath, String projectname, int id)
  {
    super(frame, title, modal);
	m_frame = (JFrame)frame;
	m_projectpath = projectpath;
	m_projectname = projectname;
	m_id = id;
	tm = new TableModelStatistics(data);
    tableCenter = new JTable(tm);
    try
    {
      jbInit();
      pack();
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
    }
  }

  void jbInit() throws Exception
  {
    scrollPaneCenter = new JXMScrollPane(m_frame);
    panelMain.setLayout(borderLayoutMain);
    jMenu1.setText("File");
    jMenuItem1.setText("Save Client");
    jMenuItem1.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        jMenuItem1_actionPerformed(e);
      }
    });
    jMenuItem2.setText("Print...");
    jMenuItem2.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        jMenuItem2_actionPerformed(e);
      }
    });
    jMenuItem3.setText("Close");
    jMenuItem3.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        jMenuItem3_actionPerformed(e);
      }
    });
	jMenuBar1.add(jMenu1);
    jMenu1.add(jMenuItem1);
    jMenu1.add(jMenuItem2);
    jMenu1.addSeparator();
    jMenu1.add(jMenuItem3);
    panelMain.add(jMenuBar1, BorderLayout.NORTH);
    scrollPaneCenter.getViewport().add(tableCenter, null);
    panelMain.add(scrollPaneCenter, BorderLayout.CENTER);
    getContentPane().add(panelMain);
  }

  void jMenuItem1_actionPerformed(ActionEvent e){
	String formatted = null;
    String c = "\n";
    byte[] enter = c.getBytes();

	try{
	File dir = new File(m_projectpath+m_projectname+"/out");
	if (!dir.exists()) dir.mkdir();
    FileOutputStream FOS = new FileOutputStream(m_projectpath+m_projectname+"/out/client_stat"+m_id+".csv");

	for(int k=0; k<tm.m_columnNames.length; k++){
      if(k==0){
	    formatted = tm.m_columnNames[k];
	    continue;
	  }
	  formatted = formatted + "," + tm.m_columnNames[k];
	}
	FOS.write(formatted.getBytes());
	FOS.write(enter);

	for(int i=0; i<tm.m_data.length; i++){
	  for(int j=0; j<tm.m_data[i].length; j++){
	    if(j==0){
		  formatted = (String)tm.m_data[i][j];
		  continue;
		}
	    formatted = formatted + "," + (String)tm.m_data[i][j];
	  }
      FOS.write(formatted.getBytes());
      FOS.write(enter);
	}
    FOS.close();
	}catch(IOException ie){ ie.printStackTrace(); }
  }

  void jMenuItem2_actionPerformed(ActionEvent e)
  {
    ((JXMScrollPane)scrollPaneCenter).Print();
  }

  void jMenuItem3_actionPerformed(ActionEvent e)
  {
    dispose();
  }

  class JXMScrollPane extends JScrollPane{
    Frame m_frame;

    public JXMScrollPane(Frame frame)
    {
      super();
      m_frame = frame;
    }

	public void Print(){
      java.awt.Toolkit toolkit = this.getToolkit();
      java.awt.PrintJob job = toolkit.getPrintJob(m_frame, "XM-Miner V1.5", null);

      if(job == null) return;
      java.awt.Graphics g = job.getGraphics();
      g.translate(20, 20);
      java.awt.Dimension size = this.getSize();
      g.setClip(0, 0, size.width, size.height);

      this.print(g);

      g.dispose();
      job.end();
	}
  }
}

class TableModelStatistics extends AbstractTableModel
{
  final String m_columnNames[] =
  {
    "Column",
    "Average",
    "Variance",
    "Minimum",
    "Maximum"
  };

  Object m_data[][];

  public TableModelStatistics(String data[][])
  {
    super();
    m_data = data;
  }

  public int getColumnCount()
  {
    return(m_columnNames.length);
  }

  public int getRowCount()
  {
    return(m_data.length);
  }

  public String getColumnName(int col)
  {
    return(m_columnNames[col]);
  }

  public Object getValueAt(int row, int col)
  {
    return(m_data[row][col]);
  }

  public Class getColumnClass(int c)
  {
    return(getValueAt(0, c).getClass());
  }

  public boolean isCellEditable(int row, int column)
  {
    return(false);
  }

  public void setValueAt(Object value, int row, int col){
  }
}