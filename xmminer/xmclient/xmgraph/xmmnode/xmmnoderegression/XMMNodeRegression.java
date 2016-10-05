
//Title:        XM-Miner
//Version:
//Copyright:    Copyright (c) 1999
//Author:       Yong Uk Song
//Company:      DIS
//Description:  XM-Miner

package xmminer.xmclient.xmgraph.xmmnode.xmmnoderegression;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

import xmminer.xmlib.*;
import xmminer.xmclient.*;
import xmminer.xmclient.xmgraph.*;
import xmminer.xmclient.xmgraph.xmmnode.xmmnoderegression.*;

public class XMMNodeRegression extends XMMNode implements Serializable
{

  // Constructors
  public XMMNodeRegression(int stat)
  {
    super(stat);
  }

  public int OnCreate()
  {
    //System.out.println("XMMNodeRegression.OnCreate");
    return(0);
  }

  public int OnDelete()
  {
    //System.out.println("XMMNodeRegression.OnDelete");
    return(0);
  }

  public static XMGraphElement Create(int stat)
  {
    return(new XMMNodeRegression(stat));
  }

  public boolean FillInitialContent(XMGraph graph)
  {
    if (!super.FillInitialContent(graph))
    {
      return(false);
    }

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

  public String toString()
  {
    return("XMMNodeRegression");
  }

  // Operation
  public String GetText()
  {
    return("Regression");
  }

  public int OnConnectedFrom(JFrame frame, XMGraph graph, XMGraphElement element)
  {
    //System.out.println("XMMNodeRegression.OnConnectedFrom");
    if (GetInCount(graph) <= 1)
    {
      return(0);
    }
    else
    {
      JOptionPane.showMessageDialog(frame, "1개의 입력노드만 가능합니다.", "오류", JOptionPane.WARNING_MESSAGE);
      return(-1);
    }
  }

  public int OnConnectedTo(JFrame frame, XMGraph graph, XMGraphElement element)
  {
    //System.out.println("XMMNodeRegression.OnConnectedTo");
    if (GetOutCount(graph) <= 1)
    {
      return(0);
    }
    else
    {
      JOptionPane.showMessageDialog(frame, "1개의 출력노드만 가능합니다.", "오류", JOptionPane.WARNING_MESSAGE);
      return(-1);
    }
  }

  public int OnDisconnectedFrom(JFrame frame, XMGraph graph, XMGraphElement element)
  {
    //System.out.println("XMMNodeRegression.OnDisconnectedFrom");
    return(0);
  }

  public int OnDisconnectedTo(JFrame frame, XMGraph graph, XMGraphElement element)
  {
    //System.out.println("XMMNodeRegression.OnDisconnectedTo");
    return(0);
  }

  // Popup Menu
  class popupNodeShowResult_actionPerformed implements java.awt.event.ActionListener
  {
    JFrame m_frame;
    XMGraph m_graph;
    XMMNodeRegression m_node;

    public popupNodeShowResult_actionPerformed(JFrame frame, XMGraph graph, XMMNodeRegression node)
    {
      m_frame = frame;
      m_graph = graph;
      m_node = node;
    }

    public void actionPerformed(ActionEvent e)
    {
      if (m_graph != null && m_node != null && m_node.m_retRun == XMGraphElement.XMGESTAT_RUN_SUCCESS)
      {
	JDialog dialog = new XMDialogShowMNodeRegression(m_frame, "Regression Result", m_graph, m_node, true);
	//dialog.setSize(700, 500);
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	Dimension dialogSize = dialog.getSize();
	if (dialogSize.height > screenSize.height)
	dialogSize.height = screenSize.height;
	if (dialogSize.width > screenSize.width)
	dialogSize.width = screenSize.width;
	dialog.setLocation((screenSize.width - dialogSize.width) / 2, (screenSize.height - dialogSize.height) / 2);
	dialog.show();
        ((XMMinerFrame)m_frame).Invalidate();
      }
    }
  }

  public JPopupMenu CreatePopupMenu(JFrame frame, XMGraph graph)
  {
    JPopupMenu popupNode = new JPopupMenu();
    JMenuItem popupNodeProperties = new JMenuItem();
    JMenuItem popupNodeRename = new JMenuItem();
    JMenuItem popupNodeAddVisual = new JMenuItem();
    JMenuItem popupNodeRunAlone = new JMenuItem();
    JMenuItem popupNodeRun2Here = new JMenuItem();
    JMenuItem popupNodeShowResult = new JMenuItem();

    popupNodeProperties.setMnemonic('P');
    popupNodeProperties.setText("Properties");
    popupNodeProperties.addActionListener(new popupNodeProperties_actionPerformed(frame, graph, this));
    popupNodeRename.setMnemonic('R');
    popupNodeRename.setText("Rename");
    popupNodeRename.addActionListener(new popupNodeRename_actionPerformed(frame, graph, this));
    popupNodeAddVisual.setMnemonic('D');
    popupNodeAddVisual.setText("Add Data");
    popupNodeAddVisual.addActionListener(new popupNodeAddVisual_actionPerformed(frame, graph, this));
    popupNodeRunAlone.setMnemonic('A');
    popupNodeRunAlone.setText("Run alone");
    popupNodeRunAlone.addActionListener(new popupNodeRunAlone_actionPerformed(frame, graph, this));
    popupNodeRun2Here.setMnemonic('H');
    popupNodeRun2Here.setText("Run to here");
    popupNodeRun2Here.addActionListener(new popupNodeRun2Here_actionPerformed(frame, graph, this));
    popupNodeShowResult.setMnemonic('S');
    popupNodeShowResult.setText("Show Result");
    popupNodeShowResult.addActionListener(new popupNodeShowResult_actionPerformed(frame, graph, this));

    popupNode.add(popupNodeProperties);
    popupNode.add(popupNodeRename);
    popupNode.addSeparator();
    popupNode.add(popupNodeAddVisual);
    popupNode.addSeparator();
    popupNode.add(popupNodeRunAlone);
    popupNode.add(popupNodeRun2Here);
    popupNode.addSeparator();
    popupNode.add(popupNodeShowResult);

    return(popupNode);
  }

  // CORBA
  //transient xmminer.xmvbj.xmgraph.xmmnode.xmmnoderegression.XMBMNodeRegression m_sMNodeRegression = null;
  transient xmminer.xmserver.xmgraph.xmmnode.xmmnoderegression.XMBMNodeRegression m_sMNodeRegression = null;

  private void SetCORBA() throws org.omg.CORBA.OBJECT_NOT_EXIST
  {
    if (m_sMNodeRegression == null)
    {
      // Get the manager Id
      // Locate an account manager. Give the full POA name and the servant ID.
      try
      {
        //m_sMNodeRegression = xmminer.xmvbj.xmgraph.xmmnode.xmmnoderegression.XMBMNodeRegressionHelper.bind(XMMiner.m_orb, "/xmminer_poa", "XMMNodeRegression".getBytes());
        m_sMNodeRegression = new xmminer.xmserver.xmgraph.xmmnode.xmmnoderegression.XMBMNodeRegression();
      }
      catch (org.omg.CORBA.OBJECT_NOT_EXIST e)
      {
        m_sMNodeRegression = null;
        System.out.println("CORBA object XMMNodeRegression does not exist.");
        throw(e);
      }
    }
  }

  // Schema
  public int SetSchema(XMGraph graph)
  {
    if (m_y == null || m_x == null)
    {
      return(1);
    }
    try
    {
      // DialogVNode CORBA Server
      SetCORBA();
    }
    catch (Exception exception)
    {
      return(1);
    }

    String projectName = graph.GetProjectName();
    XMVNode inNode;
    if ((inNode = (XMVNode)GetInElement(0, graph)) == null)
    {
      System.out.println("Input node is not defined.");
      return(XMGraphElement.XMGESTAT_RUN_ERROR);
    }
    String metaFilename = inNode.GetMetaFilename();
    XMVNode outNode;
    if ((outNode = (XMVNode)GetOutElement(0, graph)) == null)
    {
      System.out.println("Output node is not defined.");
      return(XMGraphElement.XMGESTAT_RUN_ERROR);
    }
    String nextMetaFilename = outNode.GetMetaFilename();

    m_sMNodeRegression.Initialize(projectName, metaFilename);
    int ret = m_sMNodeRegression.SetSchema(nextMetaFilename, m_y, m_x, m_bIntercept);
    m_sMNodeRegression.Finalize();

    return(ret);
  }

  // Edit
  public String m_y = null;
  public String m_x[] = null;
  public boolean m_bIntercept = true;

  transient public boolean m_bEditOK = false;

  public int Edit(JFrame frame, XMGraph graph)
  {
    XMNode errNode;
    if ((errNode = GetSchema(graph)) != null)
    {
      JOptionPane.showMessageDialog(frame, "Cannot get the schema of " + errNode.GetName() + ".");
      return(XMGESTAT_EDIT_NOCHANGE);
    }
    // MNodeRegression Server
    try
    {
      JDialog dialog = new XMDialogEditMNodeRegression(frame, "Regression V1.0", graph, this, true);
      dialog.setSize(500, 400);
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      Dimension dialogSize = dialog.getSize();
      if (dialogSize.height > screenSize.height)
        dialogSize.height = screenSize.height;
      if (dialogSize.width > screenSize.width)
        dialogSize.width = screenSize.width;
      dialog.setLocation((screenSize.width - dialogSize.width) / 2, (screenSize.height - dialogSize.height) / 2);
      dialog.show();
    }
    catch (Exception e)
    {
      JOptionPane.showMessageDialog(frame, "Data was not prepared.");
    }
    return(m_bEditOK ? XMGESTAT_EDIT_CRITICALCHANGE : XMGESTAT_EDIT_NOCHANGE);
  }

  // Run
  transient Thread m_threadRun = null;
  transient Thread m_threadProgress = null;
  transient JDialog m_dialog;
  transient boolean m_bStopped;
  public int m_retRun = XMGraphElement.XMGESTAT_RUN_NORUN;

  public String m_nextMetaFilename;

  public double m_b[];
  public double m_yy;
  public double m_bxy;
  public int m_df;

  public JDialog CreateDialog(JFrame frame)
  {
    //System.out.println("XMMNodeRegression.CreateDialog");
    return(new XMDialogMNodeRegression(frame, this, "Regression", true));
  }

  public class ThreadProgress implements Runnable
  {
    public void run()
    {
      int x, prevx, gap, pbMin, pbMax;
      String description;

      pbMin = m_sMNodeRegression.GetPBMin();
      pbMax = m_sMNodeRegression.GetPBMax();
      ((XMDialogMNodeRegression)m_dialog).initProgress(pbMin, pbMax);
      ((XMDialogMNodeRegression)m_dialog).setProgress(pbMin, "Starting ...");
      for (x = prevx = pbMin, gap = 100; IsRunning(); prevx = x)
      {
        x = m_sMNodeRegression.GetPBValue();
        description = m_sMNodeRegression.GetPBDescription();
        ((XMDialogMNodeRegression)m_dialog).setProgress(x, description);
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

  public class ThreadRun implements Runnable
  {
    public void run()
    {
      m_retRun = m_sMNodeRegression.Run(m_nextMetaFilename, m_y, m_x, m_bIntercept);
    }
  }

  public boolean IsRunning ()
  {
    return(m_sMNodeRegression.IsRunning());
  }

  public void StopRunning ()
  {
    m_sMNodeRegression.StopRunning();
    m_bStopped = true;
  }

  public int Run(JFrame frame, XMGraph graph)
  {
    if (m_y == null || m_x == null)
    {
      JOptionPane.showMessageDialog(frame, "Variables Y and X are not specified.");
      return(XMGraphElement.XMGESTAT_RUN_ERROR);
    }

    // Global Parameters
    m_bStopped = false;
    m_retRun = XMGraphElement.XMGESTAT_RUN_NORUN;

    try
    {
      // DialogVNode CORBA Server
      SetCORBA();
    }
    catch (Exception exception)
    {
      JOptionPane.showMessageDialog(frame, "Data was not prepared.");
      return(XMGraphElement.XMGESTAT_RUN_ERROR);
    }

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

    String projectName = graph.GetProjectName();
    XMVNode inNode;
    if ((inNode = (XMVNode)GetInElement(0, graph)) == null)
    {
      System.out.println("Input node is not defined.");
      return(XMGraphElement.XMGESTAT_RUN_ERROR);
    }
    String metaFilename = inNode.GetMetaFilename();
    XMVNode outNode;
    if ((outNode = (XMVNode)GetOutElement(0, graph)) == null)
    {
      JOptionPane.showMessageDialog(frame, "Output node is not defined.");
      return(XMGraphElement.XMGESTAT_RUN_ERROR);
    }
    m_nextMetaFilename = outNode.GetMetaFilename();

    m_sMNodeRegression.Initialize(projectName, metaFilename);
    m_sMNodeRegression.InitializeRunning();

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

    switch (m_retRun)
    {
      case XMGraphElement.XMGESTAT_RUN_SUCCESS:
        m_b = m_sMNodeRegression.GetResultB();
	double m_ss[] = m_sMNodeRegression.GetResultSS();
        m_yy = m_ss[0];
        m_bxy = m_ss[1];
        m_df = (int)m_ss[2];
	break;
      case XMGraphElement.XMGESTAT_RUN_ERROR:
        JOptionPane.showMessageDialog(frame, "Error: while performing Regression");
	break;
      case XMGraphElement.XMGESTAT_RUN_NORUN:
      default:
        break;
    }

    m_sMNodeRegression.FinalizeRunning();
    m_sMNodeRegression.Finalize();

    return(m_retRun);
  }

}
