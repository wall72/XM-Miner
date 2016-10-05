
//Title:        XM-Miner
//Version:      
//Copyright:    Copyright (c) 1999
//Author:       Yong Uk Song
//Company:      DIS
//Description:  XM-Miner

package xmminer.xmlib;

import javax.swing.*;
import java.awt.*;
import java.io.*;

import xmminer.xmlib.*;

public class XMPoints
{
  public int xPoints[];
  public int yPoints[];

  // Constructors
  public XMPoints(int n)
  {
    xPoints = new int[n];
    yPoints = new int[n];
  }

  public Object clone()
  {
    int i;
    int n = xPoints.length;
    XMPoints c = new XMPoints(n);
    for (i = 0; i < n; i++)
    {
      c.xPoints[i] = xPoints[i];
      c.yPoints[i] = yPoints[i];
    }
    return(c);
  }

  public void Normalize()
  {
    boolean changed = true;
    int i, n, temp;
    while (changed)
    {
      changed = false;
      for (i = 0, n = xPoints.length - 1; i < n; i++)
      {
	if (xPoints[i] > xPoints[i + 1])
	{
	  temp = xPoints[i];
	  xPoints[i] = xPoints[i + 1];
	  xPoints[i + 1] = temp;
	  changed = true;
	}
	if (yPoints[i] > yPoints[i + 1])
	{
	  temp = yPoints[i];
	  yPoints[i] = yPoints[i + 1];
	  yPoints[i + 1] = temp;
	  changed = true;
	}
      }
    }
  }

  public boolean PointOnLines(Point point)
  {
    int i, f;
    XMPoints line = new XMPoints(2);
    double distance;
    for (i = 0, f = xPoints.length; i < f; i++)
    {
	if (i == 0)
	{
	    line.xPoints[1] = xPoints[i];
	    line.yPoints[1] = yPoints[i];
	}
	else
	{
	    line.xPoints[0] = line.xPoints[1];
	    line.yPoints[0] = line.yPoints[1];
	    line.xPoints[1] = xPoints[i];
	    line.yPoints[1] = yPoints[i];
	    if ((distance = XMLib.GetDistance(line, point)) >= 0.0
		&& distance <= (double)XMLib.WBITHRESHOLD_MOUSE)
	    {
		return(true);
	    }
	}
    }
    return(false);
  }

}
