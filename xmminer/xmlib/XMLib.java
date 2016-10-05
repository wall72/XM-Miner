
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

public class XMLib
{

  public static String GetDirectory(String path)
  {
    int i;
    return((((i = path.lastIndexOf('/')) > 0 && i < path.length() - 1)
	    || ((i = path.lastIndexOf('\\')) > 0 && i < path.length() - 1))
	   ? path.substring(0, i + 1)
	   : path);
  }

  public static String GetDirectory(File f)
  {
    return((f == null)
	   ? null
	   : (f.isDirectory()
	      ? f.getAbsolutePath()
	      : GetDirectory(f.getAbsolutePath())));
  }

  public static String GetFilename(String name)
  {
    int i;
    return(((i = name.lastIndexOf('.')) > 0 && i < name.length() - 1)
	    ? name.substring(0, i)
	    : name);
  }

  public static String GetFilename(File f)
  {
    return((f == null) ? null : GetFilename(f.getName()));
  }

  public static String GetExtension(String path)
  {
    int i;
    return(((i = path.lastIndexOf('.')) > 0 && i < path.length() - 1)
	    ? path.substring(i + 1).toLowerCase()
	    : null);
  }

  public static String GetExtension(File f)
  {
    return((f == null) ? null : GetExtension(f.getName()));
  }

  protected static final int CLM_TOP	= (00 | 00);
  protected static final int CLM_BOTTOM	= (01 | 02);
  protected static final int CLM_LEFT	= (01 | 00);
  protected static final int CLM_RIGHT	= (00 | 02);

  static int GetULMode(int x1, int y1, int x2, int y2, int x, int y)
  {
    return((y - y1) * (x1 - x2) < (x - x1) * (y1 - y2) ? 1 : 0);
  }

  static int GetCLMode(Rectangle fromRect, Point toPoint)
  {
    int mode = 0;

    mode |= GetULMode(fromRect.x, fromRect.y, fromRect.x + fromRect.width, fromRect.y + fromRect.height,
		      toPoint.x, toPoint.y);
    mode |= GetULMode(fromRect.x, fromRect.y + fromRect.height, fromRect.x + fromRect.width, fromRect.y,
		      toPoint.x, toPoint.y) << 1;
    return(mode);
  }

  public static Point GetConnectPoint(Rectangle fromRect, Point toPoint)
  {
    switch (GetCLMode(fromRect, toPoint))
    {
    case CLM_TOP:
      return(new Point(fromRect.x + fromRect.width / 2, fromRect.y));
    case CLM_BOTTOM:
      return(new Point(fromRect.x + fromRect.width / 2, fromRect.y + fromRect.height));
    case CLM_LEFT:
      return(new Point(fromRect.x, fromRect.y + fromRect.height / 2));
    case CLM_RIGHT:
      return(new Point(fromRect.x + fromRect.width, fromRect.y + fromRect.height / 2));
    default:
      return(new Point(0, 0));
    }
  }

  public static Point CenterPoint(Rectangle rect)
  {
    return(new Point(rect.x + rect.width / 2, rect.y + rect.height / 2));
  }

  public static XMPoints GetConnectLine(Rectangle fromRect, Rectangle toRect)
  {
    XMPoints points = new XMPoints(2);
    Point toCenterPoint = CenterPoint(toRect);
    switch (GetCLMode(fromRect, toCenterPoint))
    {
    case CLM_TOP:
	points.xPoints[0] = fromRect.x + fromRect.width / 2;
	points.yPoints[0] = fromRect.y;
	points.xPoints[1] = toRect.x + toRect.width / 2;
	points.yPoints[1] = toRect.y + toRect.height;
	break;
    case CLM_BOTTOM:
	points.xPoints[0] = fromRect.x + fromRect.width / 2;
	points.yPoints[0] = fromRect.y + fromRect.height;
	points.xPoints[1] = toRect.x + toRect.width / 2;
	points.yPoints[1] = toRect.y;
	break;
    case CLM_LEFT:
	points.xPoints[0] = fromRect.x;
	points.yPoints[0] = fromRect.y + fromRect.height / 2;
	points.xPoints[1] = toRect.x + toRect.width;
	points.yPoints[1] = toRect.y + toRect.height / 2;
	break;
    case CLM_RIGHT:
	points.xPoints[0] = fromRect.x + fromRect.width;
	points.yPoints[0] = fromRect.y + fromRect.height / 2;
	points.xPoints[1] = toRect.x;
	points.yPoints[1] = toRect.y + toRect.height / 2;
	break;
    default:
	points.xPoints[0] = 0;
	points.yPoints[0] = 0;
	points.xPoints[1] = 0;
	points.yPoints[1] = 0;
	break;
    }
    return(points);
  }

  public static final int WBITHRESHOLD_MOUSE		= 4;

  public static double GetDistance(XMPoints line, Point point)
  {
    XMPoints normalLine = (XMPoints)line.clone();
    normalLine.Normalize();
    if (normalLine.xPoints[1] - normalLine.xPoints[0] < 2 * WBITHRESHOLD_MOUSE)
    {
	normalLine.xPoints[0] -= WBITHRESHOLD_MOUSE;
	normalLine.xPoints[1] += WBITHRESHOLD_MOUSE;
    }
    if (normalLine.yPoints[1] - normalLine.yPoints[0] < 2 * WBITHRESHOLD_MOUSE)
    {
	normalLine.yPoints[0] -= WBITHRESHOLD_MOUSE;
	normalLine.yPoints[1] += WBITHRESHOLD_MOUSE;
    }
    if (point.x >= normalLine.xPoints[0] && point.x <= normalLine.xPoints[1]
	&& point.y >= normalLine.yPoints[0] && point.y <= normalLine.yPoints[1])
    {
	double a, b, dx, dy;

	a = (double)(line.yPoints[0] - line.yPoints[1]) / (double)(line.xPoints[0] - line.xPoints[1]);
	b = (double)line.yPoints[0] - (double)((line.yPoints[0] - line.yPoints[1]) * line.xPoints[0])
				      / (double)(line.xPoints[0] - line.xPoints[1]);
	dx = ((double)point.y - a * (double)point.x - b) * a / (a * a + 1.0);
	dy = (a * (double)point.x - (double)point.y + b) / (a * a + 1.0);
	return(Math.sqrt(dx * dx + dy * dy));
    }
    else
    {
	return(-1.0);
    }
  }

  protected static final double LASIZE_LENGTH	= 10.0;
  protected static final double LASIZE_ANGLE	= (Math.PI / 4.0);

  public static XMPoints GetArrowPolygon(Point fromPoint, Point toPoint, int scale, boolean bFromArrow)
  {
    XMPoints polygon = new XMPoints(3);
    double a = (fromPoint.x != toPoint.x)
	       ? ((double)(fromPoint.y - toPoint.y) / (double)(fromPoint.x - toPoint.x))
	       : 0.0;
    if (bFromArrow)
    {
	polygon.xPoints[0] = fromPoint.x;
	polygon.yPoints[0] = fromPoint.y;
	polygon.xPoints[1] = fromPoint.x
		       + (int)((fromPoint.x != toPoint.x)
			 ? (((double)(((fromPoint.x > toPoint.x) ? -LASIZE_LENGTH : LASIZE_LENGTH) * scale) * (Math.cos(LASIZE_ANGLE / 2.0) - a * Math.sin(LASIZE_ANGLE / 2.0)) / (100.0 * Math.sqrt(1 + a * a))))
			 : (((double)(LASIZE_LENGTH * scale) * (-Math.sin(LASIZE_ANGLE / 2.0)) / (100.0))));
	polygon.yPoints[1] = fromPoint.y
		       + (int)((fromPoint.x != toPoint.x)
			 ? (((double)(((fromPoint.x > toPoint.x) ? -LASIZE_LENGTH : LASIZE_LENGTH) * scale) * (a * Math.cos(LASIZE_ANGLE / 2.0) + Math.sin(LASIZE_ANGLE / 2.0)) / (100.0 * Math.sqrt(1 + a * a))))
			 : (((double)(((fromPoint.y > toPoint.y) ? -LASIZE_LENGTH : LASIZE_LENGTH) * scale) * (Math.cos(LASIZE_ANGLE / 2.0)) / (100.0))));
	polygon.xPoints[2] = fromPoint.x
		       + (int)((fromPoint.x != toPoint.x)
			 ? (((double)(((fromPoint.x > toPoint.x) ? -LASIZE_LENGTH : LASIZE_LENGTH) * scale) * (Math.cos(LASIZE_ANGLE / 2.0) + a * Math.sin(LASIZE_ANGLE / 2.0)) / (100.0 * Math.sqrt(1 + a * a))))
			 : (((double)(LASIZE_LENGTH * scale) * (+Math.sin(LASIZE_ANGLE / 2.0)) / (100.0))));
	polygon.yPoints[2] = fromPoint.y
		       + (int)((fromPoint.x != toPoint.x)
			 ? (((double)(((fromPoint.x > toPoint.x) ? -LASIZE_LENGTH : LASIZE_LENGTH) * scale) * (a * Math.cos(LASIZE_ANGLE / 2.0) - Math.sin(LASIZE_ANGLE / 2.0)) / (100.0 * Math.sqrt(1 + a * a))))
			 : (((double)(((fromPoint.y > toPoint.y) ? -LASIZE_LENGTH : LASIZE_LENGTH) * scale) * (Math.cos(LASIZE_ANGLE / 2.0)) / (100.0))));
    }
    else
    {
	polygon.xPoints[0] = toPoint.x;
	polygon.yPoints[0] = toPoint.y;
	polygon.xPoints[1] = toPoint.x
		       + (int)((fromPoint.x != toPoint.x)
			 ? (((double)(((fromPoint.x < toPoint.x) ? -LASIZE_LENGTH : LASIZE_LENGTH) * scale) * (Math.cos(LASIZE_ANGLE / 2.0) - a * Math.sin(LASIZE_ANGLE / 2.0)) / (100.0 * Math.sqrt(1 + a * a))))
			 : (((double)(-LASIZE_LENGTH * scale) * (-Math.sin(LASIZE_ANGLE / 2.0)) / (100.0))));
	polygon.yPoints[1] = toPoint.y
		       + (int)((fromPoint.x != toPoint.x)
			 ? (((double)(((fromPoint.x < toPoint.x) ? -LASIZE_LENGTH : LASIZE_LENGTH) * scale) * (a * Math.cos(LASIZE_ANGLE / 2.0) + Math.sin(LASIZE_ANGLE / 2.0)) / (100.0 * Math.sqrt(1 + a * a))))
			 : (((double)(((fromPoint.y < toPoint.y) ? -LASIZE_LENGTH : LASIZE_LENGTH) * scale) * (Math.cos(LASIZE_ANGLE / 2.0)) / (100.0))));
	polygon.xPoints[2] = toPoint.x
		       + (int)((fromPoint.x != toPoint.x)
			 ? (((double)(((fromPoint.x < toPoint.x) ? -LASIZE_LENGTH : LASIZE_LENGTH) * scale) * (Math.cos(LASIZE_ANGLE / 2.0) + a * Math.sin(LASIZE_ANGLE / 2.0)) / (100.0 * Math.sqrt(1 + a * a))))
			 : (((double)(-LASIZE_LENGTH * scale) * (+Math.sin(LASIZE_ANGLE / 2.0)) / (100.0))));
	polygon.yPoints[2] = toPoint.y
		       + (int)((fromPoint.x != toPoint.x)
			 ? (((double)(((fromPoint.x < toPoint.x) ? -LASIZE_LENGTH : LASIZE_LENGTH) * scale) * (a * Math.cos(LASIZE_ANGLE / 2.0) - Math.sin(LASIZE_ANGLE / 2.0)) / (100.0 * Math.sqrt(1 + a * a))))
			 : (((double)(((fromPoint.y < toPoint.y) ? -LASIZE_LENGTH : LASIZE_LENGTH) * scale) * (Math.cos(LASIZE_ANGLE / 2.0)) / (100.0))));
    }
    return(polygon);
  }

  static boolean IntersectLineX(Point fromPoint, Point toPoint, int x, int right, Point point)
  {
    if (point.x < x)
    {
	if (toPoint.x == fromPoint.x)
	{
	    return(true);
	}
	else
	{
	    point.x = x;
	    point.y = ((toPoint.y - fromPoint.y) * x + (toPoint.x * fromPoint.y - fromPoint.x * toPoint.y))
		      / (toPoint.x - fromPoint.x);
	    return(false);
	}
    }
    else if (point.x > right)
    {
	if (toPoint.x == fromPoint.x)
	{
	    return(true);
	}
	else
	{
	    point.x = right;
	    point.y = ((toPoint.y - fromPoint.y) * right + (toPoint.x * fromPoint.y - fromPoint.x * toPoint.y))
		      / (toPoint.x - fromPoint.x);
	    return(false);
	}
    }
    else
    {
	return(false);
    }
  }

  static boolean IntersectLineY(Point fromPoint, Point toPoint, int y, int bottom, Point point)
  {
    if (point.y < y)
    {
	if (toPoint.y == fromPoint.y)
	{
	    return(true);
	}
	else
	{
	    point.y = y;
	    point.x = ((toPoint.x - fromPoint.x) * y + (toPoint.y * fromPoint.x - fromPoint.y * toPoint.x))
		      / (toPoint.y - fromPoint.y);
	    return(false);
	}
    }
    else if (point.y > bottom)
    {
	if (toPoint.y == fromPoint.y)
	{
	    return(true);
	}
	else
	{
	    point.y = bottom;
	    point.x = ((toPoint.x - fromPoint.x) * bottom + (toPoint.y * fromPoint.x - fromPoint.y * toPoint.x))
		      / (toPoint.y - fromPoint.y);
	    return(false);
	}
    }
    else
    {
	return(false);
    }
  }

  public static boolean IntersectLine(Point fromPoint1, Point toPoint1, Point fromPoint2, Point toPoint2,
				      Point fromPoint, Point toPoint)
  {
    if (fromPoint1.x < toPoint1.x || fromPoint2.x < toPoint2.x)
    {
	if ((toPoint1.x < fromPoint2.x) || (toPoint2.x < fromPoint1.x))
	{
	    return(true);
	}
	fromPoint.x = Math.max(fromPoint1.x, fromPoint2.x);
	toPoint.x = Math.min(toPoint1.x, toPoint2.x);
    }
    else
    {
	if ((toPoint1.x > fromPoint2.x) || (toPoint2.x > fromPoint1.x))
	{
	    return(true);
	}
	fromPoint.x = Math.min(fromPoint1.x, fromPoint2.x);
	toPoint.x = Math.max(toPoint1.x, toPoint2.x);
    }

    if (fromPoint1.y < toPoint1.y || fromPoint2.y < toPoint2.y)
    {
	if ((toPoint1.y < fromPoint2.y) || (toPoint2.y < fromPoint1.y))
	{
	    return(true);
	}
	fromPoint.y = Math.max(fromPoint1.y, fromPoint2.y);
	toPoint.y = Math.min(toPoint1.y, toPoint2.y);
    }
    else
    {
	if ((toPoint1.y > fromPoint2.y) || (toPoint2.y > fromPoint1.y))
	{
	    return(true);
	}
	fromPoint.y = Math.min(fromPoint1.y, fromPoint2.y);
	toPoint.y = Math.max(toPoint1.y, toPoint2.y);
    }

    return(false);
  }

  public static boolean IntersectLineRect(Point fromPoint, Point toPoint, Rectangle rect,
					  Point adjustedFromPoint, Point adjustedToPoint)
  {
    Point xAdjustedFromPoint = new Point(fromPoint);
    if (IntersectLineX(fromPoint, toPoint, rect.x, rect.x + rect.width, xAdjustedFromPoint))
    {
	return(true);
    }
    Point xAdjustedToPoint = new Point(toPoint);
    if (IntersectLineX(fromPoint, toPoint, rect.x, rect.x + rect.width, xAdjustedToPoint))
    {
	return(true);
    }

    Point yAdjustedFromPoint = new Point(fromPoint);
    if (IntersectLineY(fromPoint, toPoint, rect.y, rect.y + rect.height, yAdjustedFromPoint))
    {
	return(true);
    }
    Point yAdjustedToPoint = new Point(toPoint);
    if (IntersectLineY(fromPoint, toPoint, rect.y, rect.y + rect.height, yAdjustedToPoint))
    {
	return(true);
    }

    return(IntersectLine(xAdjustedFromPoint, xAdjustedToPoint, yAdjustedFromPoint, yAdjustedToPoint,
			 adjustedFromPoint, adjustedToPoint));
  }

  protected static final int XMSIZE_RECTSELECTED	= 6;

  static void DrawSelectedRect(Graphics g, int scale, int x, int y)
  {
    g.fillRect(x - XMSIZE_RECTSELECTED * scale / 200, y - XMSIZE_RECTSELECTED * scale / 200,
	       XMSIZE_RECTSELECTED * scale / 100, XMSIZE_RECTSELECTED * scale / 100);
  }

  public static void PaintSelected(Graphics g, int scale, Rectangle boundary)
  {
    DrawSelectedRect(g, scale, boundary.x, boundary.y);
    DrawSelectedRect(g, scale, boundary.x + boundary.width / 2, boundary.y);
    DrawSelectedRect(g, scale, boundary.x + boundary.width, boundary.y);
    DrawSelectedRect(g, scale, boundary.x, boundary.y + boundary.height / 2);
    DrawSelectedRect(g, scale, boundary.x + boundary.width, boundary.y + boundary.height / 2);
    DrawSelectedRect(g, scale, boundary.x, boundary.y + boundary.height);
    DrawSelectedRect(g, scale, boundary.x + boundary.width / 2, boundary.y + boundary.height);
    DrawSelectedRect(g, scale, boundary.x + boundary.width, boundary.y + boundary.height);
  }

  public static void PaintSelected(Graphics g, int scale, XMPoints points)
  {
    int i, f;
    for (i = 0, f = points.xPoints.length; i < f; i++)
    {
      DrawSelectedRect(g, scale, points.xPoints[i], points.yPoints[i]);
    }
  }

  public static void DrawRoamingRectangle(Graphics g, Point p1, Point p2)
  {
    g.drawRect(Math.min(p1.x, p2.x), Math.min(p1.y, p2.y),
	       Math.abs(p1.x - p2.x), Math.abs(p1.y - p2.y));
  }

  public static final int XMDBTYPE_UNKNOWN              = 0xF000;
  public static final int XMDBTYPE_INTEGER              = 0xF001;
  public static final int XMDBTYPE_REAL                 = 0xF002;
  public static final int XMDBTYPE_STRING               = 0xF003;
  public static final int XMDBTYPE_ENUMERATION          = 0xF004;
  public static final int XMDBTYPE_DATE                 = 0xF005;

  public static int GetColumnType(String columnInfo[])
  {
    if (columnInfo == null)
    {
      return(XMDBTYPE_UNKNOWN);
    }
    else if (columnInfo[0].equalsIgnoreCase("INTEGER"))
    {
      return(XMDBTYPE_INTEGER);
    }
    else if (columnInfo[0].equalsIgnoreCase("REAL"))
    {
      return(XMDBTYPE_REAL);
    }
    else if (columnInfo[0].equalsIgnoreCase("STRING"))
    {
      return(XMDBTYPE_STRING);
    }
    else if (columnInfo[0].equalsIgnoreCase("ENUMERATION"))
    {
      return(XMDBTYPE_ENUMERATION);
    }
    else if (columnInfo[0].equalsIgnoreCase("DATE"))
    {
      return(XMDBTYPE_DATE);
    }
    else
    {
      return(XMDBTYPE_UNKNOWN);
    }
  }

  public static String[] SqueezeArray(String[] a, int n)
  {
    int i;
    String r[] = new String[n];

    for (i = 0; i < n; i++)
    {
      r[i] = a[i];
    }
    return(r);
  }

  public static boolean IsMemberIgnoreCase(String[] a, String b)
  {
    int i;
    for (i = 0; i < a.length; i++)
    {
      if (b.equalsIgnoreCase(a[i]))
      {
        return(true);
      }
    }
    return(false);
  }

}
