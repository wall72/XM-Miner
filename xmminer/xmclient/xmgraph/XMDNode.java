
//Title:        XM-Miner
//Version:
//Copyright:    Copyright (c) 1999
//Author:       Yong Uk Song
//Company:      DIS
//Description:  XM-Miner

package xmminer.xmclient.xmgraph;

import javax.swing.*;
import java.io.*;

import xmminer.xmlib.*;
import xmminer.xmclient.*;
import xmminer.xmclient.xmgraph.*;
import xmminer.xmclient.xmgraph.xmdnode.xmdnodepartitioning.*;
import xmminer.xmclient.xmgraph.xmdnode.xmdnodesampling.*;
import xmminer.xmclient.xmgraph.xmdnode.xmdnodecolumnselect.*;
import xmminer.xmclient.xmgraph.xmdnode.xmdnodecalculate.*;
import xmminer.xmclient.xmgraph.xmdnode.xmdnodedataquery.*;
//import xmminer.xmclient.xmgraph.xmdnode.xmdnodedatamerge.*;
//import xmminer.xmclient.xmgraph.xmdnode.xmdnodedatatransform.*;

public class XMDNode extends XMNode implements Serializable
{

  // Constructors
  public XMDNode(int stat)
  {
    super(stat);
  }

  public static XMGraphElement Create(int stat)
  {
    switch (stat & XMGEFLAG_DNODE)
    {
    case XMGESTAT_DNODE_SAMPLING:
      return(XMDNodeSampling.Create(stat));
    case XMGESTAT_DNODE_PARTITIONING:
      return(XMDNodePartitioning.Create(stat));
    case XMGESTAT_DNODE_CALCULATE:
      return(XMDNodeCalculate.Create(stat));
    case XMGESTAT_DNODE_COLUMNSELECT:
      return(XMDNodeColumnSelect.Create(stat));
    case XMGESTAT_DNODE_DATAQUERY:
      return(XMDNodeDataQuery.Create(stat));
    default:
      System.out.println("Error: unknown stat for XMDNode.Create");
      return(null);
    }
  }

  public boolean FillInitialContent(XMGraph graph)
  {
    if (!super.FillInitialContent(graph))
    {
      return(false);
    }

    return(true);
  }

  public String toString()
  {
    return(super.toString() + ".XMDNode");
  }

  // Operation
  public String GetText()
  {
    return("XMGraphElement.XMNode.XMDNode");
  }

}
