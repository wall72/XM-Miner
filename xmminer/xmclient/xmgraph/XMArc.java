
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

public class XMArc extends XMGraphElement implements Serializable
{
  XMGraphElement m_fromNode;
  XMGraphElement m_toNode;

  // Constructors
  public XMArc(int stat)
  {
    super(stat);
  }

  public static XMGraphElement Create(int stat)
  {
    switch (stat & XMGEFLAG_ARC)
    {
    case XMGESTAT_SARC:
      return(XMSArc.Create(stat));
    default:
      System.out.println("Error: unknown stat for XMArc.Create");
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
    return(super.toString() + ".XMArc");
  }

  // Operation
  public String GetText()
  {
    return("XMGraphElement.XMArc");
  }

  public int GetIndex(XMGraph graph)
  {
    int i, f;
    for (i = 0, f = graph.GetArcSize(); i < f; i++)
    {
      if ((XMArc)graph.GetArcAt(i) == this)
      {
        return(i);
      }
    }
    return(-1);
  }

  public int GetWidth(int scale)
  {
    return(GetBoundary(scale).width);
  }

  public int GetHeight(int scale)
  {
    return(GetBoundary(scale).height);
  }

  public Rectangle GetBoundary(int scale)
  {
    int left = 0, top = 0, right = 0, bottom = 0;
    int i, f;
    XMPoints connectPoints = GetConnectPoints(scale);
    for (i = 0, f = connectPoints.xPoints.length; i < f; i++)
    {
	if (i == 0 || connectPoints.xPoints[i] < left)	 left = connectPoints.xPoints[i];
	if (i == 0 || connectPoints.yPoints[i] < top)	 top = connectPoints.yPoints[i];
	if (i == 0 || connectPoints.xPoints[i] > right)  right = connectPoints.xPoints[i];
	if (i == 0 || connectPoints.yPoints[i] > bottom) bottom = connectPoints.yPoints[i];
    }
    return(new Rectangle(left, top, right - left, bottom - top));
  }

  public XMPoints GetConnectPoints(int scale)
  {
    System.out.println("XMArc.GetConnectPoints");
    return(null);
  }

  // Paint
  public int Paint(Frame frame, Graphics g, int scale, Rectangle clipBounds)
  {
    Rectangle boundary = GetBoundary(scale);
    if (clipBounds.intersects(boundary))
    {
      g.setColor(new Color(0, 0, 0));

      // Draw edge
      int i, f;
      XMPoints connectPoints = GetConnectPoints(scale);
      Point fromPoint = new Point(0, 0);
      Point toPoint = new Point(0, 0);
      Point adjustedFromPoint = new Point(0, 0);
      Point adjustedToPoint = new Point(0, 0);
      for (i = 0, f = connectPoints.xPoints.length; i < f; i++)
      {
	  fromPoint.x = toPoint.x;
	  fromPoint.y = toPoint.y;
	  toPoint.x = connectPoints.xPoints[i];
	  toPoint.y = connectPoints.yPoints[i];
	  if (i > 0 && !XMLib.IntersectLineRect(fromPoint, toPoint, clipBounds, adjustedFromPoint, adjustedToPoint))
	  {
	      g.drawLine(adjustedFromPoint.x, adjustedFromPoint.y,
			 adjustedToPoint.x, adjustedToPoint.y);
	  }
      }

      // Arrow
      XMPoints arrowPolygon;
      if (f >= 2
	  && (arrowPolygon = XMLib.GetArrowPolygon(fromPoint, toPoint, scale+100, false)) != null
	  && clipBounds.contains(toPoint))
      {
	  // Draw polygon ( = arrow)
	  g.fillPolygon(arrowPolygon.xPoints, arrowPolygon.yPoints, arrowPolygon.xPoints.length);
      }

      // Paint Selected
      if (GetSelected())
      {
	XMLib.PaintSelected(g, scale, connectPoints);
      }
      return(0);
    }
    else
    {
      return(1);
    }
  }

  // Edit
  public int Edit(JFrame frame, XMGraph graph)
  {
    System.out.println("XMArc.Edit");
    return(XMGraphElement.XMGESTAT_EDIT_NOCHANGE);
  }

}
