
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
import xmminer.xmclient.xmgraph.xmxnode.xmxnodedb.*;
import xmminer.xmclient.xmgraph.xmxnode.xmxnodefile.*;

public class XMXNode extends XMNode implements Serializable
{

  // Constructors
  public XMXNode(int stat)
  {
    super(stat);
  }

  public static XMGraphElement Create(int stat)
  {
    switch (stat & XMGEFLAG_XNODE)
    {
    case XMGESTAT_XNODE_FILE:
      return(XMXNodeFile.Create(stat));
    case XMGESTAT_XNODE_DB:
      return(XMXNodeDB.Create(stat));
    default:
      System.out.println("Error: unknown stat for XMXNode.Create");
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
    return(super.toString() + ".XMXNode");
  }

  // Operation
  public String GetText()
  {
    return("XMGraphElement.XMNode.XMVNode");
  }

}
