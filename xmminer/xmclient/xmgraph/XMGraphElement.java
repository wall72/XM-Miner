
//Title:        XM-Miner
//Version:
//Copyright:    Copyright (c) 1999
//Author:       Yong Uk Song
//Company:      DIS
//Description:  XM-Miner

package xmminer.xmclient.xmgraph;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

import xmminer.xmlib.*;
import xmminer.xmclient.*;
import xmminer.xmclient.xmgraph.*;

public class XMGraphElement implements Serializable
{
  int m_stat;
  public static final int XMGEFLAG_GRAPHELEMENT               = 0xF000;
  public static final int XMGESTAT_ARC                        = 0x1000;
  public static final int XMGESTAT_NODE                       = 0x2000;

  public static final int XMGEFLAG_ARC                        = 0xFF00;
  public static final int XMGESTAT_SARC                       = 0x1100;

  public static final int XMGEFLAG_SARC                       = 0xFFFF;
  public static final int XMGESTAT_SARC_NORMAL                = 0x1101;

  public static final int XMGEFLAG_NODE                       = 0xFF00;
  public static final int XMGESTAT_XNODE                      = 0x2100;
  public static final int XMGESTAT_DNODE                      = 0x2200;
  public static final int XMGESTAT_MNODE                      = 0x2300;
  public static final int XMGESTAT_VNODE                      = 0x2400;

  public static final int XMGEFLAG_XNODE                      = 0xFFFF;
  public static final int XMGESTAT_XNODE_FILE                 = 0x2101;
  public static final int XMGESTAT_XNODE_DB                   = 0x2102;

  public static final int XMGEFLAG_DNODE                      = 0xFFFF;
  public static final int XMGESTAT_DNODE_SAMPLING             = 0x2201;
  public static final int XMGESTAT_DNODE_PARTITIONING         = 0x2202;
  public static final int XMGESTAT_DNODE_COLUMNCOMPUTE        = 0x2203;
  public static final int XMGESTAT_DNODE_CALCULATE            = 0x2204;
  public static final int XMGESTAT_DNODE_COLUMNSELECT         = 0x2205;
  public static final int XMGESTAT_DNODE_DATAQUERY            = 0x2206;
  public static final int XMGESTAT_DNODE_DATAMERGE            = 0x2207;
  public static final int XMGESTAT_DNODE_DATATRANSFORM        = 0x2208;

  public static final int XMGEFLAG_MNODE                      = 0xFFFF;
  public static final int XMGESTAT_MNODE_REGRESSION           = 0x2301;
  public static final int XMGESTAT_MNODE_ASSOCIATIONRULE      = 0x2302;
  public static final int XMGESTAT_MNODE_SEQUENCERULE         = 0x2303;
  public static final int XMGESTAT_MNODE_EPISODERULE          = 0x2304;
  public static final int XMGESTAT_MNODE_C45                  = 0x2305;
  public static final int XMGESTAT_MNODE_NEURALNETWORK        = 0x2306;
  public static final int XMGESTAT_MNODE_SOFM                 = 0x2307;
  public static final int XMGESTAT_MNODE_CORRELATION          = 0x2308;
  public static final int XMGESTAT_MNODE_PREDICTION           = 0x2309;

  public static final int XMGEFLAG_VNODE                      = 0xFFFF;
  public static final int XMGESTAT_VNODE_NORMAL               = 0x2401;

  int m_uniqueId;

  boolean m_bSelected;

  // Constructors
  // Need Inheritance
  public XMGraphElement(int stat)
  {
    m_stat = stat;
  }

  // Need Implement
  public int OnCreate()
  {
    //System.out.println("XMGraphElement.OnCreate");
    return(0);
  }

  // Need Implement
  public int OnDelete()
  {
    //System.out.println("XMGraphElement.OnDelete");
    return(0);
  }

  // Need Implement
  public static XMGraphElement Create(int stat)
  {
    XMGraphElement element;
    switch (stat & XMGEFLAG_GRAPHELEMENT)
    {
    case XMGESTAT_ARC:
      element = XMArc.Create(stat);
      break;
    case XMGESTAT_NODE:
      element = XMNode.Create(stat);
      break;
    default:
      System.out.println("Error: unknown stat for XMGraphElement.Create");
      element = null;
      break;
    }
    return((element != null && element.OnCreate() == 0) ? element : null);
  }

  // Need Implement
  public boolean FillInitialContent(XMGraph graph)
  {
    m_uniqueId = graph.GetUniqueId();
    m_bSelected = false;
    return(true);
  }

  // Serialize
  /*
  private void writeObject(ObjectOutputStream out) throws IOException
  {
  }

  private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
  {
  }
  */

  // Need Implement
  public String toString()
  {
    return("XMGraphElement");
  }

  // Operation
  // Do not touch
  public int GetStat()
  {
    return(m_stat);
  }

  // Do not touch
  public int GetUniqueId()
  {
    return(m_uniqueId);
  }

  // Do not touch
  public boolean GetSelected()
  {
    return(m_bSelected);
  }

  // Do not touch
  public void SetSelected(boolean bSelected)
  {
    m_bSelected = bSelected;
  }

  // Need Implement
  public String GetText()
  {
    return("XMGraphElement");
  }

  // Do not touch
  public Color GetColor()
  {
    return(Color.black);
  }

  // Do not touch
  public int GetIndex(XMGraph graph)
  {
    System.out.println("XMGraphElement.GetIndex");
    return(-1);
  }

  // Do not touch
  public int GetWidth(int scale)
  {
    System.out.println("XMGraphElement.GetWidth");
    return(0);
  }

  // Do not touch
  public int GetHeight(int scale)
  {
    System.out.println("XMGraphElement.GetHeight");
    return(0);
  }

  // Do not touch
  public Rectangle GetBoundary(int scale)
  {
    System.out.println("XMGraphElement.GetBoundary");
    return(null);
  }

  // Do not touch
  public int GetInCount(XMGraph graph)
  {
    System.out.println("XMGraphElement.GetInCount");
    return(0);
  }

  // Do not touch
  public int GetOutCount(XMGraph graph)
  {
    System.out.println("XMGraphElement.GetOutCount");
    return(0);
  }

  // Do not touch
  public XMGraphElement GetInElement(int index, XMGraph graph)
  {
    System.out.println("XMGraphElement.GetInElement");
    return(null);
  }

  // Do not touch
  public XMGraphElement GetOutElement(int index, XMGraph graph)
  {
    System.out.println("XMGraphElement.GetOutElement");
    return(null);
  }

  // Need Implement
  public int OnConnectedFrom(JFrame frame, XMGraph graph, XMGraphElement element)
  {
    //System.out.println("XMGraphElement.OnConnectedFrom");
    return(0);
  }

  // Need Implement
  public int OnConnectedTo(JFrame frame, XMGraph graph, XMGraphElement element)
  {
    //System.out.println("XMGraphElement.OnConnectedTo");
    return(0);
  }

  // Need Implement
  public int OnDisconnectedFrom(JFrame frame, XMGraph graph, XMGraphElement element)
  {
    //System.out.println("XMGraphElement.OnDisconnectedFrom");
    return(0);
  }

  // Need Implement
  public int OnDisconnectedTo(JFrame frame, XMGraph graph, XMGraphElement element)
  {
    //System.out.println("XMGraphElement.OnDisconnectedTo");
    return(0);
  }

  // Popup Menu
  // Need Implement
  public JPopupMenu CreatePopupMenu(JFrame frame, XMGraph graph)
  {
    System.out.println("XMGraphElement.CreatePopupMenu");
    return(null);
  }

  // Paint
  // May Need Implement
  public int Paint(Frame frame, Graphics g, int scale, Rectangle clipBounds)
  {
    System.out.println("XMGraphElement.Paint");
    return(0);
  }

  public void DrawRoaming(Graphics g, Dimension offset, int m_scale)
  {
    System.out.println("XMGraphElement.DrawRoaming");
  }

  // CORBA
  //transient xmminer.xmvbj.xmgraph.XMBGraphElement m_sGraphElement = null;
  transient xmminer.xmserver.xmgraph.XMBGraphElement m_sGraphElement = null;

  // Need Implement
  private void SetCORBA() throws org.omg.CORBA.OBJECT_NOT_EXIST
  {
    if (m_sGraphElement == null)
    {
      // Get the manager Id
      // Locate an account manager. Give the full POA name and the servant ID.
      //m_sGraphElement = xmminer.xmvbj.xmgraph.XMBGraphElementHelper.bind(XMMiner.m_orb, "/xmminer_poa", "XMGraphElement".getBytes());
      m_sGraphElement = new xmminer.xmserver.xmgraph.XMBGraphElement();
    }
  }

  // Edit
  public static final int XMGESTAT_EDIT_NOCHANGE	= 0;
  public static final int XMGESTAT_EDIT_CHANGE		= 1;
  public static final int XMGESTAT_EDIT_CRITICALCHANGE	= 2;

  // Need Implement
  public int Edit(JFrame frame, XMGraph graph)
  {
    // GraphElement Server
    //SetCORBA();
    System.out.println("XMGraphElement.Edit");
    return(XMGESTAT_EDIT_NOCHANGE);
  }

  // Run
  public static final int XMGESTAT_RUN_NORUN		= 1;
  public static final int XMGESTAT_RUN_SUCCESS		= 0;
  public static final int XMGESTAT_RUN_ERROR		= -1;
  transient Thread m_threadRun = null;
  transient Thread m_threadProgress = null;
  transient JDialog m_dialog;
  transient boolean m_bStopped;
  int m_retRun;

  // Need Implement
  public JDialog CreateDialog(JFrame frame)
  {
    System.out.println("XMGraphElement.CreateDialog");
    return(new XMDialogGraphElement(frame, this, "Graph Element", true));
  }

  // Need Implement
  public class ThreadProgress implements Runnable
  {
    public void run()
    {
      JProgressBar progressBar = ((XMDialogGraphElement)m_dialog).jProgressBar1;

      int x, prevx, gap, pbMin, pbMax;

      pbMin = m_sGraphElement.GetPBMin();
      pbMax = m_sGraphElement.GetPBMax();
      progressBar.setMinimum(pbMin);
      progressBar.setMaximum(pbMax);
      progressBar.setValue(pbMin);
      for (x = prevx = pbMin, gap = 100; IsRunning(); prevx = x)
      {
	x = m_sGraphElement.GetPBValue();
	progressBar.setValue(x);
	if (x == prevx && gap < 5000)
	{
	  gap += 100;
	}
	else if (x != prevx && gap > 100)
	{
	  gap -= 100;
	}
	try
	{
	  Thread.sleep(gap);
        }
	catch (InterruptedException e)
	{
	}
      }
      m_dialog.dispose();
    }
  }

  // Need Implement
  public class ThreadRun implements Runnable
  {
    public void run()
    {
      m_retRun = m_sGraphElement.Run();
    }
  }

  // Need Implement
  public boolean IsRunning ()
  {
    return(m_sGraphElement.IsRunning());
  }

  // Need Implement
  public void StopRunning ()
  {
    m_sGraphElement.StopRunning();
    m_bStopped = true;
  }

  // Need Implement
  public int Run(JFrame frame, XMGraph graph)
  {
    // Dialog
    m_dialog = CreateDialog(frame);
    //m_dialog.setSize(700, 500);
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension dialogSize = m_dialog.getSize();
    if (dialogSize.height > screenSize.height)
      dialogSize.height = screenSize.height;
    if (dialogSize.width > screenSize.width)
      dialogSize.width = screenSize.width;
    m_dialog.setLocation((screenSize.width - dialogSize.width) / 2, (screenSize.height - dialogSize.height) / 2);

    // GraphElement Server
    SetCORBA();

    // Global Parameters
    m_bStopped = false;
    m_retRun = 0;

    m_sGraphElement.Initialize("Arg");
    m_sGraphElement.InitializeRunning();

    // Thread
    if (m_threadRun == null || !m_threadRun.isAlive())
    {
      m_threadRun = new Thread(new ThreadRun());
      m_threadRun.start();
    }
    if (m_threadProgress == null || !m_threadProgress.isAlive())
    {
      m_threadProgress = new Thread(new ThreadProgress());
      m_threadProgress.start();
    }

    m_dialog.show();

    // You can recognize the user's cancel action and the run result
    // by m_bStopped and m_retRun
    System.out.println("Client r = " + m_retRun + ", " + m_bStopped);

    m_sGraphElement.FinalizeRunning();
    m_sGraphElement.Finalize();

    return(m_retRun);
  }

}
