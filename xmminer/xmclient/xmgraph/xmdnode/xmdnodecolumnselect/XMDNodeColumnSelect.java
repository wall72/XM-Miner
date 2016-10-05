
//Title:        XM-Miner
//Version:      
//Copyright:    Copyright (c) 1999
//Author:       Yong Uk Song
//Company:      DIS
//Description:  XM-Miner

package xmminer.xmclient.xmgraph.xmdnode.xmdnodecolumnselect;

import javax.swing.*;
import java.io.*;

import java.awt.*;
import java.awt.event.*;

import xmminer.xmlib.*;
import xmminer.xmclient.*;
import xmminer.xmclient.xmgraph.*;
import xmminer.xmclient.xmgraph.xmdnode.xmdnodecolumnselect.*;

public class XMDNodeColumnSelect extends XMDNode implements Serializable
{
  
  public int previous_index;
  public int next_index;  
  public String project_name;
  private String previous_arc;
  private String next_arc;
  private String arc = "arc";
  
  // Constructors
  public XMDNodeColumnSelect(int stat)
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
    return(new XMDNodeColumnSelect(stat));
  }
  
  public String toString()
  {    
    return("XMDNodeColumnSelect");
  }
  
  public String GetText()
  {    
    return("Column Select");
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
  //transient xmminer.xmvbj.xmgraph.xmdnode.xmdnodecolumnselect.XMBDNodeColumnSelect m_sDNodeColumnSelect = null;
  transient xmminer.xmserver.xmgraph.xmdnode.xmdnodecolumnselect.XMBDNodeColumnSelect m_sDNodeColumnSelect = null;
  
  private void SetCORBA()
  {  
  	if (m_sDNodeColumnSelect == null)
  	{
  		//m_sDNodeColumnSelect = xmminer.xmvbj.xmgraph.xmdnode.xmdnodecolumnselect.XMBDNodeColumnSelectHelper.bind(XMMiner.m_orb, "/xmminer_poa","XMDNodeColumnSelection".getBytes());	
  		m_sDNodeColumnSelect = new xmminer.xmserver.xmgraph.xmdnode.xmdnodecolumnselect.XMBDNodeColumnSelect();	
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
    next_index = GetOutElement(0, graph).GetUniqueId();
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
  	XMDialogDNodeColumnSelect dds = new XMDialogDNodeColumnSelect(frame,"Column Select V1.0",true,m_sDNodeColumnSelect,this);
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
}