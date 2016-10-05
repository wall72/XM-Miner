
//Title:        XM-Miner
//Version:      
//Copyright:    Copyright (c) 1999
//Author:       Yong Uk Song
//Company:      DIS
//Description:  XM-Miner

package xmminer.xmclient.xmgraph;

import javax.swing.*;
import java.awt.*;
import java.io.*;

import xmminer.xmlib.*;
import xmminer.xmclient.*;
import xmminer.xmclient.xmgraph.*;

public class XMSArc extends XMArc implements Serializable
{

  // Constructors
  public XMSArc(int stat)
  {
    super(stat);
  }

  public static XMGraphElement Create(int stat)
  {
    switch (stat & XMGEFLAG_SARC)
    {
    case XMGESTAT_SARC_NORMAL:
      return(new XMSArc(stat));
    default:
      System.out.println("Error: unknown stat for XMSArc.Create");
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
    return(super.toString() + ".XMSArc");
  }

  // Operation
  public String GetText()
  {
    return("XMGraphElement.XMArc.XMSArc");
  }

  public XMPoints GetConnectPoints(int scale)
  {
    if (m_fromNode == null || m_toNode == null)
    {
      return(null);
    }
    else
    {
      Rectangle fromBoundary = m_fromNode.GetBoundary(scale);
      Rectangle toBoundary = m_toNode.GetBoundary(scale);
      return(XMLib.GetConnectLine(fromBoundary, toBoundary));
    }
  }

  // Edit
  public int Edit(JFrame frame, XMGraph graph)
  {
    System.out.println("XMSArc.Edit");
    return(XMGESTAT_EDIT_NOCHANGE);
  }

}
