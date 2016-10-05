
//Title:        XM-Miner
//Version:      
//Copyright:    Copyright (c) 1999
//Author:       Yong Uk Song
//Company:      DIS
//Description:  XM-Miner

package xmminer.xmlib.xmmath;

import java.io.*;

public class XMMatrixM extends XMMatrix
{
    private double	a[][];

    private static void copyArray(double t[][], double s[][], int r, int c)
    {
	int i, j;
	for (i = 0; i < r; i++)
	{
	    for (j = 0; j < c; j++)
	    {
		t[i][j] = s[i][j];
	    }
	}
    } // copyArray

    public XMMatrixM(int nRow, int nColumn)
    {
	super(nRow, nColumn);
	a = new double[row][column];
    } // XMMatrixM

    public XMMatrixM(XMMatrixM aMatrix)
    {
	this(aMatrix.row, aMatrix.column);
	copyArray(a, aMatrix.a, row, column);
    } // XMMatrixM

    public XMMatrix copy()				// virtual
    {
	XMMatrixM c = new XMMatrixM(this);
	return(c);
    } // copy

    public double get(int i, int j)			// virtual
    {
	return(a[i][j]);
    } // get

    public double get(int i, int j, boolean transpose)	// virtual
    {
	return(transpose ? a[j][i] : a[i][j]);
    } // get

    public double set(int i, int j, double element)	// virtual
    {
	return(a[i][j] = element);
    } // set

    public double set(int i, int j, boolean transpose, double element)	// virtual
    {
	return(transpose ? (a[j][i] = element) : (a[i][j] = element));
    } // set
} // XMMatrixM
