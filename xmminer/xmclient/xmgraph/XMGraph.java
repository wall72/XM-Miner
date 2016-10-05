
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
import java.util.*;

import xmminer.xmlib.*;
import xmminer.xmclient.*;
import xmminer.xmclient.xmgraph.*;

public class XMGraph implements Serializable
{
  String m_version;

  int m_nextUniqueId;

  public File m_file;

  int m_statDrawing;
  int m_scale;
  int m_gridScale;

  Vector m_allNodes;
  Vector m_allArcs;

  boolean m_bModified;

  // Constructors
  public XMGraph()
  {
    m_version = "XMMiner/1.0";

    m_nextUniqueId = 0;

    m_statDrawing = 0;
    m_scale = 48;
    m_gridScale = 0;

    m_allNodes = new Vector(64, 64);
    m_allArcs = new Vector(64, 64);

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
    return("XMGraph");
  }

  // Operation
  public int GetUniqueId()
  {
    return(m_nextUniqueId++);
  }

  public String GetProjectName()
  {
    return(XMLib.GetFilename(m_file));
  }

  public String GetDirectory()
  {
    return(XMLib.GetDirectory(m_file));
  }

  public int GetScale()
  {
    return(m_scale);
  }

  public void SetScale(int scale)
  {
    m_scale = scale;
  }

  public boolean GetModified()
  {
    return(m_bModified);
  }

  public void SetModified(boolean bModified)
  {
    m_bModified = bModified;
  }

  public int GetNodeSize()
  {
    return(m_allNodes.size());
  }

  public void AddNode(XMGraphElement node)
  {
    m_allNodes.addElement(node);
  }

  public void RemoveNodeAt(int n)
  {
    m_allNodes.removeElementAt(n);
  }

  public XMGraphElement GetNodeAt(int n)
  {
    return((XMGraphElement)m_allNodes.elementAt(n));
  }

  public XMGraphElement GetNodeAt(Point point)
  {
    int i, f;
    XMNode node;
    for (i = 0, f = GetNodeSize(); i < f; i++)
    {
      if ((node = (XMNode)GetNodeAt(i)) != null && node.GetBoundary(m_scale).contains(point))
      {
        return(node);
      }
    }
    return(null);
  }

  public XMGraphElement GetUniqueNode(int uniqueId)
  {
    int i, f;
    XMGraphElement node;
    for (i = 0, f = GetNodeSize(); i < f; i++)
    {
      if ((node = GetNodeAt(i)) != null && node.GetUniqueId() == uniqueId)
      {
        return(node);
      }
    }
    return(null);
  }

  public XMGraphElement GetFrontNode(boolean bSelected)
  {
    int n = GetNodeSize();
    XMGraphElement node;
    while (n-- > 0)
    {
      if ((node = GetNodeAt(n)) != null && (!bSelected || node.GetSelected()))
      {
        return(node);
      }
    }
    return(null);
  }

  public XMGraphElement CreateNodeAt(Point point, int stat, boolean bSelected, boolean bFillInitialContent)
  {
    XMNode node;
    if ((node = (XMNode)XMGraphElement.Create(stat)) != null)
    {
      node.m_pos = new Point(point.x * 48 / m_scale, point.y * 48 / m_scale);
      node.m_bSelected = bSelected;
      if (bFillInitialContent && !node.FillInitialContent(this))
      {
	return(null);
      }
      AddNode(node);
      return(node);
    }
    else
    {
      return(null);
    }
  }

  public int RemoveNode(JFrame frame, XMGraphElement node, int nodeId)
  {
    if (node != null && nodeId >= 0)
    {
      int r;
      if ((r = ((XMNode)node).RemoveConnectedArcs(frame, this)) == 0
          && (r = node.OnDelete()) == 0)
      {
	RemoveNodeAt(nodeId);
	return(0);
      }
      else
      {
	return(r);
      }
    }
    else
    {
      return(1);
    }
  }

  public int RemoveNode(JFrame frame, XMGraphElement node)
  {
    return(RemoveNode(frame, node, node.GetIndex(this)));
  }

  public int RemoveNode(JFrame frame, int nodeId)
  {
    return(RemoveNode(frame, GetNodeAt(nodeId), nodeId));
  }

  public int RemoveNodes(JFrame frame, boolean bSelected)
  {
    int n = GetNodeSize(), r;
    XMGraphElement node;
    while (n-- > 0)
    {
      if ((node = GetNodeAt(n)) != null
          && (!bSelected || node.GetSelected())
	  && (r = RemoveNode(frame, node, n)) != 0)
      {
      	return(r);
      }
    }
    return(0);
  }

  public void SelectNodes(boolean bSelected)
  {
    int i, f;
    XMGraphElement node;
    for (i = 0, f = GetNodeSize(); i < f; i++)
    {
      if ((node = GetNodeAt(i)) != null)
      {
	node.SetSelected(bSelected);
      }
    }
  }

  public void SelectNodes(Rectangle boundary, boolean bSelected)
  {
    int i, f;
    XMGraphElement node;
    Rectangle nodeBoundary, intersectBoundary;
    for (i = 0, f = GetNodeSize(); i < f; i++)
    {
      if ((node = GetNodeAt(i)) != null)
      {
	nodeBoundary = node.GetBoundary(m_scale);
        intersectBoundary = nodeBoundary.intersection(boundary);
	if (intersectBoundary.equals(nodeBoundary))
	{
	  node.m_bSelected = bSelected;
	}
	else
	{
	  node.m_bSelected = !bSelected;
	}
      }
    }
  }

  public void MoveNodes(Dimension offset, boolean bSelected)
  {
    int i, f;
    XMNode node;
    for (i = 0, f = GetNodeSize(); i < f; i++)
    {
      if ((node = (XMNode)GetNodeAt(i)) != null && (!bSelected || node.m_bSelected))
      {
	node.m_pos.x += offset.width * 48 / m_scale;
	node.m_pos.y += offset.height * 48 / m_scale;
      }
    }
  }

  public int GetArcSize()
  {
    return(m_allArcs.size());
  }

  public void AddArc(XMGraphElement arc)
  {
    m_allArcs.addElement(arc);
  }

  public void RemoveArcAt(int n)
  {
    m_allArcs.removeElementAt(n);
  }

  public XMGraphElement GetArcAt(int n)
  {
    return((XMGraphElement)m_allArcs.elementAt(n));
  }

  public XMGraphElement GetArcAt(Point point)
  {
    int i, f;
    XMArc arc;
    XMPoints connectPoints;
    for (i = 0, f = GetArcSize(); i < f; i++)
    {
      if ((arc = (XMArc)GetArcAt(i)) != null
          && (connectPoints = arc.GetConnectPoints(m_scale)) != null
          && connectPoints.PointOnLines(point))
      {
        return(arc);
      }
    }
    return(null);
  }

  public XMGraphElement GetUniqueArc(int uniqueId)
  {
    int i, f;
    XMGraphElement arc;
    for (i = 0, f = GetArcSize(); i < f; i++)
    {
      if ((arc = GetArcAt(i)) != null && arc.GetUniqueId() == uniqueId)
      {
        return(arc);
      }
    }
    return(null);
  }

  public XMGraphElement GetFrontArc(boolean bSelected)
  {
    int n = GetArcSize();
    XMGraphElement arc;
    while (n-- > 0)
    {
      if ((arc = GetArcAt(n)) != null && (!bSelected || arc.GetSelected()))
      {
        return(arc);
      }
    }
    return(null);
  }

  public boolean IsValidArc(XMGraphElement fromNode, XMGraphElement toNode)
  {
    switch (fromNode.GetStat() & XMGraphElement.XMGEFLAG_NODE)
    {
    case XMGraphElement.XMGESTAT_DNODE:
    case XMGraphElement.XMGESTAT_XNODE:
    case XMGraphElement.XMGESTAT_MNODE:
      switch (toNode.GetStat() & XMGraphElement.XMGEFLAG_NODE)
      {
      case XMGraphElement.XMGESTAT_DNODE:
      case XMGraphElement.XMGESTAT_XNODE:
      case XMGraphElement.XMGESTAT_MNODE:
        return(false);
      case XMGraphElement.XMGESTAT_VNODE:
        return(true);
      default:
        return(false);
      }
    case XMGraphElement.XMGESTAT_VNODE:
      switch (toNode.GetStat() & XMGraphElement.XMGEFLAG_NODE)
      {
      case XMGraphElement.XMGESTAT_DNODE:
      case XMGraphElement.XMGESTAT_XNODE:
      case XMGraphElement.XMGESTAT_MNODE:
        return(true);
      case XMGraphElement.XMGESTAT_VNODE:
        return(false);
      default:
        return(false);
      }
    default:
      return(false);
    }
  }

  public boolean ExistArc(XMGraphElement fromNode, XMGraphElement toNode)
  {
    int n = GetArcSize();
    XMArc arc;
    while (n-- > 0)
    {
	if ((arc = (XMArc)GetArcAt(n)) != null
	    && arc.m_fromNode == fromNode
	    && arc.m_toNode == toNode)
	{
	    return(true);
	}
    }
    return(false);
  }

  public boolean ExistArc(XMGraphElement fromNode)
  {
    int n = GetArcSize();
    XMArc arc;
    while (n-- > 0)
    {
	if ((arc = (XMArc)GetArcAt(n)) != null && arc.m_fromNode == fromNode)
	{
	    return(true);
	}
    }
    return(false);
  }

  public boolean ExistPath(XMGraphElement fromNode, XMGraphElement toNode)
  {
    int i, f;
    XMArc arc;
    for (i = 0, f = GetArcSize(); i < f; i++)
    {
      if ((arc = (XMArc)GetArcAt(i)) != null
	  && arc.m_fromNode == fromNode
	  && (arc.m_toNode == toNode || ExistPath(arc.m_toNode, toNode)))
      {
	return(true);
      }
    }
    return(false);
  }

  public boolean IsValidConnection(XMGraphElement fromNode, XMGraphElement toNode)
  {
    return(IsValidArc(fromNode, toNode) && !ExistPath(toNode, fromNode));
  }

  public XMGraphElement CreateArc(JFrame frame, int stat, XMGraphElement fromNode, XMGraphElement toNode,
				  boolean bSelected, boolean bCheckValidity, boolean bFillInitialContent)
  {
    XMArc arc;
    if ((!bCheckValidity || IsValidConnection(fromNode, toNode))
	&& (arc = (XMArc)XMGraphElement.Create(stat)) != null
	&& fromNode.OnConnectedTo(frame, this, toNode) == 0
	&& toNode.OnConnectedFrom(frame, this, fromNode) == 0)
    {
      arc.m_fromNode = fromNode;
      arc.m_toNode = toNode;
      arc.m_bSelected = bSelected;
      if (bFillInitialContent && !arc.FillInitialContent(this))
      {
	    return(null);
      }
      AddArc(arc);
      return(arc);
    }
    else
    {
      return(null);
    }
  }

  public int RemoveArc(JFrame frame, XMGraphElement arc, int arcId)
  {
    if (arc != null && arcId >= 0)
    {
      int r;
      if ((r = ((XMArc)arc).m_fromNode.OnDisconnectedTo(frame, this, ((XMArc)arc).m_toNode)) == 0
	  && (r = ((XMArc)arc).m_toNode.OnDisconnectedFrom(frame, this, ((XMArc)arc).m_fromNode)) == 0
	  && (r = arc.OnDelete()) == 0)
      {
	RemoveArcAt(arcId);
	((XMNode)(((XMArc)arc).m_toNode)).SetStatEdit(XMGraphElement.XMGESTAT_EDIT_CRITICALCHANGE);
	return(0);
      }
      else
      {
	return(r);
      }
    }
    else
    {
      return(1);
    }
  }

  public int RemoveArc(JFrame frame, XMGraphElement arc)
  {
    return(RemoveArc(frame, arc, arc.GetIndex(this)));
  }

  public int RemoveArc(JFrame frame, int arcId)
  {
    return(RemoveArc(frame, GetArcAt(arcId), arcId));
  }

  public int RemoveArcs(JFrame frame, boolean bSelected)
  {
    int n = GetArcSize(), r;
    XMGraphElement arc;
    while (n-- > 0)
    {
      if ((arc = GetArcAt(n)) != null)
      {
        if ((!bSelected || arc.GetSelected())
	    && (r = RemoveArc(frame, arc, n)) != 0)
        {
      	  return(r);
        }
      }
    }
    return(0);
  }

  public void SelectArcs(boolean bSelected)
  {
    int i, f;
    XMGraphElement arc;
    for (i = 0, f = GetArcSize(); i < f; i++)
    {
      if ((arc = GetArcAt(i)) != null)
      {
	arc.SetSelected(bSelected);
      }
    }
  }

  public void SelectArcs(Rectangle boundary, boolean bSelected)
  {
    int i, f;
    XMGraphElement arc;
    Rectangle arcBoundary, intersectBoundary;
    for (i = 0, f = GetArcSize(); i < f; i++)
    {
      if ((arc = GetArcAt(i)) != null)
      {
	arcBoundary = arc.GetBoundary(m_scale);
        intersectBoundary = arcBoundary.intersection(boundary);
	if (intersectBoundary.equals(arcBoundary))
	{
	  arc.m_bSelected = bSelected;
	}
	else
	{
	  arc.m_bSelected = !bSelected;
	}
      }
    }
  }

  public XMGraphElement GetElementAt(Point point)
  {
    XMGraphElement element;
    if ((element = GetNodeAt(point)) != null)
    {
      return(element);
    }
    else if ((element = GetArcAt(point)) != null)
    {
      return(element);
    }
    else
    {
      return(null);
    }
  }

  public XMGraphElement GetUniqueElement(int uniqueId)
  {
    XMGraphElement element;
    if ((element = GetUniqueNode(uniqueId)) != null)
    {
      return(element);
    }
    else if ((element = GetUniqueArc(uniqueId)) != null)
    {
      return(element);
    }
    else
    {
      return(null);
    }
  }

  public XMGraphElement GetFrontElement(boolean bSelected)
  {
    XMGraphElement element;
    if ((element = GetFrontNode(bSelected)) != null)
    {
      return(element);
    }
    else if ((element = GetFrontArc(bSelected)) != null)
    {
      return(element);
    }
    else
    {
      return(null);
    }
  }

  public int RemoveElements(JFrame frame, boolean bSelected)
  {
    int r;
    return(((r = RemoveNodes(frame, bSelected)) == 0
	    && (r = RemoveArcs(frame, bSelected)) == 0) ? 0 : r);
  }

  public void SelectElements(boolean bSelected)
  {
    SelectNodes(bSelected);
    SelectArcs(bSelected);
  }

  public void SelectElements(Rectangle boundary, boolean bSelected)
  {
    SelectNodes(boundary, bSelected);
    SelectArcs(boundary, bSelected);
  }

  public int GetWidth(boolean bSelected)
  {
    return(GetBoundary(bSelected).width);
  }

  public int GetHeight(boolean bSelected)
  {
    return(GetBoundary(bSelected).height);
  }

  public Rectangle GetBoundary(boolean bSelected)
  {
    boolean bFirst;
    int i, f, r, b;
    XMGraphElement node;
    int left = 0;
    int top = 0;
    int right = 0;
    int bottom = 0;
    Rectangle nodeBoundary;
    for (i = 0, f = GetNodeSize(), bFirst = true; i < f; i++)
    {
      if ((node = GetNodeAt(i)) != null && (!bSelected || node.m_bSelected))
      {
	nodeBoundary = node.GetBoundary(m_scale);
	if (bFirst)
	{
	  left = nodeBoundary.x;
	  top = nodeBoundary.y;
	  right = left + nodeBoundary.width;
	  bottom = top + nodeBoundary.height;
	  bFirst = false;
	}
	else
	{
	  if (nodeBoundary.x < left)
          {
            left = nodeBoundary.x;
          }
	  if (nodeBoundary.y < top)
          {
            top = nodeBoundary.y;
          }
	  if ((r = nodeBoundary.x + nodeBoundary.width) > right)
          {
            right = r;
          }
	  if ((b = nodeBoundary.y + nodeBoundary.height) > bottom)
          {
            bottom = b;
          }
	}
      }
    }
    return(new Rectangle(left, top, right - left, bottom - top));
  }

  // Paint
  public int Paint(Frame frame, Graphics g)
  {
    Rectangle clipBounds = g.getClipBounds();
    int i, f;
    XMGraphElement element;
    for (i = 0, f = m_allNodes.size(); i < f; i++)
    {
      element = (XMGraphElement)m_allNodes.elementAt(i);
      element.Paint(frame, g, m_scale, clipBounds);
    }
    for (i = 0, f = m_allArcs.size(); i < f; i++)
    {
      element = (XMGraphElement)m_allArcs.elementAt(i);
      element.Paint(frame, g, m_scale, clipBounds);
    }
    return(0);
  }

  public void DrawRoamingElements(Graphics g, Point cPoint, Point sPoint, boolean bSelected)
  {
    Dimension offset = new Dimension(cPoint.x - sPoint.x, cPoint.y - sPoint.y);
    int i, f;
    XMGraphElement element;
    for (i = 0, f = GetNodeSize(); i < f; i++)
    {
      if ((element = GetNodeAt(i)) != null && (!bSelected || element.m_bSelected))
      {
	  element.DrawRoaming(g, offset, m_scale);
      }
    }
  }

  // Edit
  public void InitEdit(int statEdit)
  {
    int i, f;
    XMNode node;
    for (i = 0, f = GetNodeSize(); i < f; i++)
    {
      if ((node = (XMNode)GetNodeAt(i)) != null)
      {
	node.SetStatEdit(statEdit);
      }
    }
  }

  public int Edit(JFrame frame)
  {
    return(0);
  }

  // Run
  public XMNode Run(JFrame frame, boolean bRunAll)
  {
    int i, f;
    XMNode node, errNode;
    for (i = 0, f = GetNodeSize(); i < f; i++)
    {
      if ((node = (XMNode)GetNodeAt(i)) != null
	  && node.IsLeafNode(this))
      {
	node.InitRun(this, bRunAll);
      }
    }
    for (i = 0, f = GetNodeSize(); i < f; i++)
    {
      if ((node = (XMNode)GetNodeAt(i)) != null
	  && node.IsLeafNode(this)
	  && (errNode = node.Run2Here(frame, this)) != null)
      {
	return(errNode);
      }
    }
    return(null);
  }

}
