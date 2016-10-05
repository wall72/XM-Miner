
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
import xmminer.xmclient.xmgraph.xmmnode.xmmnoderegression.*;
import xmminer.xmclient.xmgraph.xmmnode.xmmnodeassociationrule.*;
import xmminer.xmclient.xmgraph.xmmnode.xmmnodesequencerule.*;
import xmminer.xmclient.xmgraph.xmmnode.xmmnodec45.*;
import xmminer.xmclient.xmgraph.xmmnode.xmmnodeneuralnetwork.*;

public class XMMNode extends XMNode implements Serializable
{

  // Constructors
  public XMMNode(int stat)
  {
    super(stat);
  }

  public static XMGraphElement Create(int stat)
  {
    switch (stat & XMGEFLAG_MNODE)
    {
    case XMGESTAT_MNODE_REGRESSION:
      return(XMMNodeRegression.Create(stat));
    case XMGESTAT_MNODE_ASSOCIATIONRULE:
      return(XMMNodeAssociationRule.Create(stat));
    case XMGESTAT_MNODE_SEQUENCERULE:
      return(XMMNodeSequenceRule.Create(stat));
    case XMGESTAT_MNODE_C45:
      return(XMMNodeC45.Create(stat));
    case XMGESTAT_MNODE_NEURALNETWORK:
      return(XMMNodeNeuralNetwork.Create(stat));
    default:
      System.out.println("Error: unknown stat for XMMNode.Create");
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
    return(super.toString() + ".XMMNode");
  }

  // Operation
  public String GetText()
  {
    return("XMGraphElement.XMNode.XMMNode");
  }

}