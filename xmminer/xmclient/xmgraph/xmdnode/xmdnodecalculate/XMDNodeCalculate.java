
//Title:        XM-Miner
//Version:
//Copyright:    Copyright (c) 1999
//Author:       Yong Uk Song
//Company:      DIS
//Description:  XM-Miner

package xmminer.xmclient.xmgraph.xmdnode.xmdnodecalculate;

import javax.swing.*;
import java.io.*;

import java.awt.*;
import java.awt.event.*;
import xmminer.xmlib.*;
import xmminer.xmclient.*;
import xmminer.xmclient.xmgraph.*;
import xmminer.xmclient.xmgraph.xmdnode.xmdnodecalculate.*;
import xmminer.xmclient.xmgraph.xmdnode.xmdnodecolumnselect.*;
import xmminer.xmclient.xmgraph.xmdnode.xmdnodedataquery.*;

public class XMDNodeCalculate extends XMDNode implements Serializable
{

  public int previous_index; 

  private XMDialogDNodeColumnSelect dcs;
  private XMDialogDNodeDataQuery ddq;
  private int dialog_status;

  // Constructors
  public XMDNodeCalculate(int stat)
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
    return(new XMDNodeCalculate(stat));
  }

  public String toString()
  {
    //return(super.toString() + ".XMDNodeCalculate");
    return("XMDNodeCalculate");
  }

  public String GetText()
  {
    //return(super.toString() + ".XMDNodeCalculate");
    return("Calculate");
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

  public void setDialog(XMDialogDNodeColumnSelect i_dialog, int i_index, int status)
  {
  	dcs = i_dialog;
  	previous_index = i_index;
  	dialog_status = status;
  }

  public void setDialog(XMDialogDNodeDataQuery i_dialog, int i_index, int status)
  {
  	ddq = i_dialog;
  	previous_index = i_index;
  	dialog_status = status;
  }


  // Paint

  //CORBA
  //xmminer.xmvbj.xmgraph.xmdnode.xmdnodecalculate.XMBDNodeCalculate m_sDNodeCalculate = null;
  xmminer.xmserver.xmgraph.xmdnode.xmdnodecalculate.XMBDNodeCalculate m_sDNodeCalculate = null;

  private void SetCORBA()
  {
	  if(m_sDNodeCalculate == null){
		  //m_sDNodeCalculate = xmminer.xmvbj.xmgraph.xmdnode.xmdnodecalculate.XMBDNodeCalculateHelper.bind(XMMiner.m_orb, "/xmminer_poa","XMDNodeCalculate".getBytes());
		  m_sDNodeCalculate = new xmminer.xmserver.xmgraph.xmdnode.xmdnodecalculate.XMBDNodeCalculate();
	  }
  }

  public int SetSchema(XMGraph graph)
  {
    return(0);
  }

  // Edit
  public int Edit(JFrame frame, XMGraph graph)
  {        
       
  	SetCORBA();
  	 System.out.println("dialog_status="+dialog_status);
  	XMDialogDNodeCalculate ddc;
  	//XMDialogDNodeCalculate ddc = new XMDialogDNodeCalculate(frame,"데이터 계산(Calculate)",true,m_sDNodeCalculate,this,column_list,column_type);
  	if (dialog_status==0)
  	{
  	    ddc = new XMDialogDNodeCalculate(frame,"Calculate V1.0",true,m_sDNodeCalculate,this,dcs);
  	}
  	else 
  	{
  	    ddc = new XMDialogDNodeCalculate(frame,"Calculate V1.0",true,m_sDNodeCalculate,this,ddq);
        }
   			  Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
              Dimension frameSize = ddc.getSize();
              if (frameSize.height > screenSize.height)
                 frameSize.height = screenSize.height;
              if (frameSize.width > screenSize.width)
                 frameSize.width = screenSize.width;
              ddc.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
              ddc.setVisible(true);
              return XMGESTAT_EDIT_CRITICALCHANGE;
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
    return(new DNodeDialogPrgBar(frame, "데이터 계산(Calculate)", true, this));
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
            x = m_sDNodeCalculate.GetPBValue();
            progressBar.setValue(x);
        }
        m_dialog.dispose();
    }
  }

  public class ThreadRun implements Runnable{
    public void run() {
        m_bSuccess = (m_sDNodeCalculate.Run() == 0) && !m_bStopped;
    }
  }

  public boolean IsRunning () {
    return (m_sDNodeCalculate.IsRunning());
  }

  public void StopRunning(){
    m_sDNodeCalculate.StopRunning();
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
    m_pbMin = m_sDNodeCalculate.GetPBMin();
    m_pbMax = m_sDNodeCalculate.GetPBMax();

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
