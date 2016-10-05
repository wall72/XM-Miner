
//Title:        XM-Miner
//Version:      
//Copyright:    Copyright (c) 1999
//Author:       Yong Uk Song
//Company:      DIS
//Description:  XM-Miner

package xmminer.xmlib.xmstat;

import xmminer.xmlib.xmmath.*;

public class XMStat
{
    public static double min(XMMatrix d)
    {
	int i, j;
	double r = 0.0, g;
	int row = d.getRow();
	int column = d.getColumn();
	for (i = 0; i < row; i++)
	{
	    for (j = 0; j < column; j++)
	    {
		g = d.get(i, j);
		if ((i == 0 && j == 0) || g < r)
		{
		    r = g;
		}
	    }
	}
	return(r);
    } // min

    public static double max(XMMatrix d)
    {
	int i, j;
	double r = 0.0, g;
	int row = d.getRow();
	int column = d.getColumn();
	for (i = 0; i < row; i++)
	{
	    for (j = 0; j < column; j++)
	    {
		g = d.get(i, j);
		if ((i == 0 && j == 0) || g > r)
		{
		    r = g;
		}
	    }
	}
	return(r);
    } // max

    public static double sum(XMMatrix d)
    {
	int i, j;
	double r = 0.0;
	int row = d.getRow();
	int column = d.getColumn();
	for (i = 0; i < row; i++)
	{
	    for (j = 0; j < column; j++)
	    {
		r += d.get(i, j);
	    }
	}
	return(r);
    } // sum

    public static double average(XMMatrix d)
    {
	return(sum(d) / (double)(d.getRow() * d.getColumn()));
    } // average

    public static double var(XMMatrix d)
    {
	int i, j;
	double r = 0.0, t;
	int row = d.getRow();
	int column = d.getColumn();
	double avg = average(d);
	for (i = 0; i < row; i++)
	{
	    for (j = 0; j < column; j++)
	    {
		t = d.get(i, j) - avg;
		r += t * t;
	    }
	}
	return(r / (row * column - 1));
    } // var

    public static double stddev(XMMatrix d)
    {
	return(Math.sqrt(var(d)));
    } // stddev

	static int ng, nl, ns;
    private static void countComp(XMMatrix d, double a)
    {
	int i, j;
	double g;
	int row = d.getRow();
	int column = d.getColumn();
	ng = nl = ns = 0;
	for (i = 0; i < row; i++)
	{
	    for (j = 0; j < column; j++)
	    {
		g = d.get(i, j);
		if (g > a)
		{
		    ng++;
		}
		else if (g < a)
		{
		    nl++;
		}
		else
		{
		    ns++;
		}
	    }
	}
    } // countComp

    public static double median(XMMatrix d)
    {
	int i, j;
	double g;
	int row = d.getRow();
	int column = d.getColumn();
	int n = row * column / 2;
	for (i = 0; i < row; i++)
	{
	    for (j = 0; j < column; j++)
	    {
		g = d.get(i, j);
		countComp(d, g);
		if (nl <= n && ng <= n)
		{
		    return(g);
		}
	    }
	}
	return(0.0);
    } // median
}  // class XMStat
