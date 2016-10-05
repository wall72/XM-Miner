
//Title:        XM-Miner
//Version:
//Copyright:    Copyright (c) 1999
//Author:       Yong Uk Song
//Company:      DIS
//Description:  XM-Miner

package xmminer.xmclient.xmlib;

import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.table.*;

import xmminer.xmclient.xmlib.*;
import xmminer.xmclient.xmgraph.*;

public class XMDialogTable extends JDialog
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
  JMenuItem jMenuItem4 = new JMenuItem();
  private XMTableModel tm;
  JFrame m_frame;
  String m_projectpath;
  String m_projectname;
  int m_id;

  public XMDialogTable(Frame frame, String title, AbstractTableModel tableModel, boolean modal, String projectpath, String projectname, int id)
  {
    super(frame, title, modal);

	m_frame = (JFrame)frame;
	m_projectpath = projectpath;
	m_projectname = projectname;
	m_id = id;

    tableCenter = new JTable(tableModel);
	tm = (XMTableModel)tableModel;

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
	jMenuItem4.setText("Save Server");
    jMenuItem4.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
	  {
        jMenuItem4_actionPerformed(e);
	  }
    });
    jMenuBar1.add(jMenu1);
    jMenu1.add(jMenuItem1);
	jMenu1.add(jMenuItem4);
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
    String[] m_AllCols;
	String[][] m_AllRows;
    String c = "\n";
    byte[] enter = c.getBytes();

	try{
	File dir = new File(m_projectpath+m_projectname+"/out");
	if (!dir.exists()) dir.mkdir();
    FileOutputStream FOS = new FileOutputStream(m_projectpath+m_projectname+"/out/client_table"+m_id+".csv");
	
	m_AllCols = tm.GetAllCols();
	for(int k=0; k<m_AllCols.length; k++){
      if(k==0){
	    formatted = m_AllCols[k];
	    continue;
	  }
	  formatted = formatted + "," + m_AllCols[k];
	}
	FOS.write(formatted.getBytes());
	FOS.write(enter);

    m_AllRows = tm.GetAllRows();
	for(int i=0; i<m_AllRows.length; i++){
	  for(int j=0; j<m_AllRows[i].length; j++){
	    if(j==0){
		  formatted = m_AllRows[i][j];
		  continue;
		}
	    formatted = formatted + "," + m_AllRows[i][j];
	  }
      FOS.write(formatted.getBytes());
      FOS.write(enter);
	}
    FOS.close();
	}catch(IOException ie){ ie.printStackTrace(); }
  }

  void jMenuItem2_actionPerformed(ActionEvent e){
    ((JXMScrollPane)scrollPaneCenter).Print();
  }

  void jMenuItem3_actionPerformed(ActionEvent e)
  {
    dispose();
  }

  void jMenuItem4_actionPerformed(ActionEvent e)
  {
    tm.SaveAllRows();
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
      java.awt.PrintJob job = toolkit.getPrintJob(m_frame, "XM-BRENIC/Miner V1.0", null);

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