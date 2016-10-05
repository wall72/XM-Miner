
//Title:        XM-Miner
//Version:
//Copyright:    Copyright (c) 1999
//Author:       Yong Uk Song
//Company:      DIS
//Description:  XM-Miner

package xmminer.xmclient.xmgraph.xmdnode.xmdnodedataquery;

import javax.swing.*;
import java.io.*;

import java.awt.*;
import java.awt.event.*;

import xmminer.xmlib.*;
import xmminer.xmclient.*;
import xmminer.xmclient.xmgraph.*;
import xmminer.xmclient.xmgraph.xmdnode.xmdnodedataquery.*;

public class XMDNodeDataQuery extends XMDNode implements Serializable
{

  public int previous_index;
  public int next_index;
  public String project_name;
  private int trans_status;
  private String previous_arc;
  private String next_arc;
  private String arc = "arc";

  // Constructors
  public XMDNodeDataQuery(int stat)
  {
    super(stat);
  }

  public int OnCreate()
  {
    // Do not touch
    int err;
    if ((err = super.OnCreate()) != 0)
    {
      return(err);
    }

    return(0);
  }

  public int OnDelete()
  {
    // Do not touch
    int err;
    if ((err = super.OnDelete()) != 0)
    {
      return(err);
    }
    return(0);
  }

  public static XMGraphElement Create(int stat)
  {
    return(new XMDNodeDataQuery(stat));
  }

  public String toString()
  {
    //return(super.toString() + ".XMDNodeDataQuery");
    return("XMDNodeDataQuery");
  }

  public String GetText()
  {
    //return(super.toString() + ".XMDNodeDataQuery");
    return("Data Query");
  }

  // Operation
  public int OnConnectedFrom(JFrame frame, XMGraph graph, XMGraphElement element)
  {
    // Do not touch
    int err;
    if ((err = super.OnConnectedFrom(frame, graph, element)) != 0)
    {
      return(err);
    }

    // chkeck validity of connectin from the 'element'
    if (GetInCount(graph) >= 1)   // maximumn in = 1
    {
      return(-1);
    }
    return(0);
  }

  public int OnConnectedTo(JFrame frame, XMGraph graph, XMGraphElement element)
  {
    int err;
    if ((err = super.OnConnectedTo(frame, graph, element)) != 0)
    {
      return(err);
    }

    // chkeck validity of connectin to the 'element'
    if (GetOutCount(graph) >= 1)  // maximumn out = 1
    {
      return(-1);
    }

    return(0);
  }

  public int OnDisconnectedFrom(JFrame frame, XMGraph graph, XMGraphElement element)
  {
    // Do not touch
    int err;
    if ((err = super.OnDisconnectedFrom(frame, graph, element)) != 0)
    {
      return(err);
    }

    return(0);
  }

  public int OnDisconnectedTo(JFrame frame, XMGraph graph, XMGraphElement element)
  {
    // Do not touch
    int err;
    if ((err = super.OnDisconnectedTo(frame, graph, element)) != 0)
    {
      return(err);
    }

    return(0);
  }

  // Paint

  //CORBA
  //transient xmminer.xmvbj.xmgraph.xmdnode.xmdnodedataquery.XMBDNodeDataQuery m_sDNodeDataQuery = null;
  transient xmminer.xmserver.xmgraph.xmdnode.xmdnodedataquery.XMBDNodeDataQuery m_sDNodeDataQuery = null;

  private void SetCORBA()
  {
  	if(m_sDNodeDataQuery == null)
  	{
  		//m_sDNodeDataQuery = xmminer.xmvbj.xmgraph.xmdnode.xmdnodedataquery.XMBDNodeDataQueryHelper.bind(XMMiner.m_orb, "/xmminer_poa","XMDNodeDataQuery".getBytes());
  		m_sDNodeDataQuery = new xmminer.xmserver.xmgraph.xmdnode.xmdnodedataquery.XMBDNodeDataQuery();
  	}
  }

  public int SetSchema(XMGraph graph, XMVNode node)
  {      
    XMVNode inNode;
    if ((inNode = (XMVNode)GetInElement(0, graph)) == null)
    {
      System.out.println("Input node is not defined.");
      return(XMGraphElement.XMGESTAT_RUN_ERROR);
    }
    XMVNode outNode;
    if ((outNode = (XMVNode)GetOutElement(0, graph)) == null)
    {
      System.out.println("Output node is not defined.");
      return(XMGraphElement.XMGESTAT_RUN_ERROR);
    }
    return(0);      
  }   

  // Edit
  public int Edit(JFrame frame, XMGraph graph)
  {
    XMNode errNode;
    if ((errNode = GetSchema(graph)) != null)
    {
      JOptionPane.showMessageDialog(frame, "Cannot get the schema of " + errNode.GetName() + ".");
      return(XMGESTAT_EDIT_NOCHANGE);
    }
    previous_index = GetInElement(0, graph).GetUniqueId();
    next_index = GetOutElement(0, graph).GetUniqueId();
  	project_name = graph.GetProjectName();
  	SetCORBA();
  	XMDialogDNodeDataQuery dds = new XMDialogDNodeDataQuery(frame,"Data Query V1.0",true,m_sDNodeDataQuery,this);
  	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
       Dimension frameSize = dds.getSize();
       if (frameSize.height > screenSize.height)
           frameSize.height = screenSize.height;
       if (frameSize.width > screenSize.width)
           frameSize.width = screenSize.width;
       dds.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
       dds.setVisible(true);
       return XMGESTAT_EDIT_CRITICALCHANGE;
  }

   public void setTransStatus(int status)
  {
      trans_status = status;
  }

  // Run

  transient Thread m_threadRun = null;
  transient Thread m_threadProgress = null;
  JDialog m_dialog;
  boolean m_bStopped;
  boolean m_bSuccess;
  int m_pbMin;
  int m_pbMax;

  public JDialog CreateDialog(JFrame frame)
  {
    if (trans_status==0)
    {
      return(new DNodeDialogPrgBar(frame, "데이터 삭제(Data Delete)", true, this));
    }
    else
    {
      return(new DNodeDialogPrgBar(frame, "데이터 저장(Data Save)", true, this));
    }
  }

  public class ThreadProgress implements Runnable {
    public void run(){
        JProgressBar progressBar = ((DNodeDialogPrgBar)m_dialog).jProgressBar1;

        int x = m_pbMin;
        int prevx = x;
        progressBar.setMinimum(m_pbMin);
        progressBar.setMaximum(m_pbMax);
        progressBar.setValue(x);
        while(IsRunning())
        {
            x = m_sDNodeDataQuery.GetPBValue();
            progressBar.setValue(x);
        }
        m_dialog.dispose();
    }
  }

  public class ThreadRun implements Runnable{
    public void run() {
        m_bSuccess = (m_sDNodeDataQuery.Run() == 0) && !m_bStopped;
    }
  }

  public boolean IsRunning () {
    return (m_sDNodeDataQuery.IsRunning());
  }

  public void StopRunning(){
    m_sDNodeDataQuery.StopRunning();
    m_bStopped = true;
  }

  public int Run(JFrame frame, XMGraph graph)
  {
    m_dialog = CreateDialog(frame);
    m_dialog.setSize(400, 120);
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension dialogSize = m_dialog.getSize();
    if (dialogSize.height > screenSize.height)
      dialogSize.height = screenSize.height;
    if (dialogSize.width > screenSize.width)
      dialogSize.width = screenSize.width;
    m_dialog.setLocation((screenSize.width - dialogSize.width) / 2, (screenSize.height - dialogSize.height) / 2);
    // GraphElement Server
    SetCORBA();
    m_bStopped = false;
    m_bSuccess = false;
    m_pbMin = m_sDNodeDataQuery.GetPBMin();
    m_pbMax = m_sDNodeDataQuery.GetPBMax();

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
    System.out.println("m_bStopped="+m_bStopped);
    System.out.println("m_bSuccess="+m_bSuccess);

    return(m_bStopped ? XMGESTAT_RUN_NORUN : XMGESTAT_RUN_SUCCESS);
  }

}
