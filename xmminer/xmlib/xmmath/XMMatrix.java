
//Title:        XM-Miner
//Version:      
//Copyright:    Copyright (c) 1999
//Author:       Yong Uk Song
//Company:      DIS
//Description:  XM-Miner

package xmminer.xmlib.xmmath;

import java.io.*;

public class XMMatrix
{
    protected int	row;
    protected int	column;

    XMMatrix(int nRow, int nColumn)
    {
	row = nRow;
	column = nColumn;
    }

    public int getRow()
    {
	return(row);
    } // getRow

    public int getRow(boolean transpose)
    {
	return(transpose ? column : row);
    } // getRow

    public int getColumn()
    {
	return(column);
    } // getColumn

    public int getColumn(boolean transpose)
    {
	return(transpose ? row : column);
    } // getColumn

    public XMMatrix copy()				// virtual
    {
	return(null);
    } // copy

    public double get(int i, int j)			// virtual
    {
	return(0.0);
    } // get

    public double get(int i, int j, boolean transpose)	// virtual
    {
	return(0.0);
    } // get

    public double set(int i, int j, double element)	// virtual
    {
	return(0.0);
    } // set

    public double set(int i, int j, boolean transpose, double element)	// virtual
    {
	return(0.0);
    } // set

    public double[] toVector()
    {
	int i, n;
	double r[];
	if (getRow() == 1)
	{
	    n = getColumn();
	    r = new double[n];
	    for (i = 0; i < n; i++)
	    {
		r[i] = get(0, i);
	    }
	}
	else if (getColumn() == 1)
	{
	    n = getRow();
	    r = new double[n];
	    for (i = 0; i < n; i++)
	    {
		r[i] = get(i, 0);
	    }
	}
	else
	{
	    r = null;
	}
	return(r);
    }

    public boolean swapRow(int r1, int r2)
    {
	if (r1 >= row || r2 >= row)
	    return(false);
	else
	{
	    int j;
	    double tmp;
	    for (j = 0; j < column; j++)
	    {
		tmp = get(r1, j);
		set(r1, j, get(r2, j));
		set(r2, j, tmp);
	    }
	    return(true);
	}
    } // swapRow

    public void print()
    {
	int i, j;
	for (i = 0; i < row; i++)
	{
	    for (j = 0; j < column; j++)
	    {
		System.out.print(get(i, j));
		System.out.print(' ');
	    }
	    System.out.println();
	}
	System.out.println();
    } // print

    public static int identity(XMMatrix r)
    {
	int i, j;
	for (i = 0; i < r.row; i++)
	{
	    for (j = 0; j < r.column; j++)
	    {
		r.set(i, j, (i == j) ? 1.0 : 0.0);
	    }
	}
	return(0);
    } // identity

    public static int add(XMMatrix a, XMMatrix b, XMMatrix r)
    {
	if (a.row != b.row || a.column != b.column
	    || a.row != r.row || a.column != r.column)
	{
	    return(1);
	}
	else
	{
	    int i, j;
	    for (i = 0; i < a.row; i++)
	    {
		for (j = 0; j < a.column; j++)
		{
		    r.set(i, j, a.get(i, j) + b.get(i, j));
		}
	    }
	    return(0);
	}
    } // add

    public static int subtract(XMMatrix a, XMMatrix b, XMMatrix r)
    {
	if (a.row != b.row || a.column != b.column
	    || a.row != r.row || a.column != r.column)
	{
	    return(1);
	}
	else
	{
	    int i, j;
	    for (i = 0; i < a.row; i++)
	    {
		for (j = 0; j < a.column; j++)
		{
		    r.set(i, j, a.get(i, j) - b.get(i, j));
		}
	    }
	    return(0);
	}
    } // subtract

    public double multiplyAt(XMMatrix b, int aRow, int bColumn)
    {
        double r = 0.0;
        if (column == b.row)
        {
	    for (int k = 0; k < column; k++)
	    {
                r += get(aRow, k) * b.get(k, bColumn);
            }
        }
        return(r);
    }

    public static int multiply(double a, XMMatrix b, XMMatrix r)
    {
	if (b.row != r.row || b.column != r.column)
	{
	    return(1);
	}
	else
	{
	    int i, j;
	    for (i = 0; i < b.row; i++)
	    {
		for (j = 0; j < b.column; j++)
		{
		    r.set(i, j, a * b.get(i, j));
		}
	    }
	    return(0);
	}
    } // multiply

    public static int multiply(XMMatrix a, XMMatrix b, XMMatrix r)
    {
	if (a.column != b.row || a.row != r.row || b.column != r.column)
	{
	    return(1);
	}
	else
	{
	    int i, j, k;
	    for (i = 0; i < r.row; i++)
	    {
		for (j = 0; j < r.column; j++)
		{
		    r.set(i, j, 0.0);
		}
	    }
	    for (k = 0; k < a.column; k++)
	    {
		for (i = 0; i < r.row; i++)
		{
		    for (j = 0; j < r.column; j++)
		    {
			r.set(i, j, r.get(i, j) + a.get(i, k) * b.get(k, j));
		    }
		}
	    }
	    return(0);
	}
    } // multiply

    public static int	TRANSPOSE_ARG1	= 001;
    public static int	TRANSPOSE_ARG2	= 002;

    public static int multiply(XMMatrix a, XMMatrix b, int mode, XMMatrix r)
    {
	boolean ta = (mode & TRANSPOSE_ARG1) > 0;
	boolean tb = (mode & TRANSPOSE_ARG2) > 0;
	int ar = a.getRow(ta);
	int ac = a.getColumn(ta);
	int br = b.getRow(tb);
	int bc = b.getColumn(tb);
	int rr = r.getRow();
	int rc = r.getColumn();
	if (ac != br || ar != rr || bc != rc)
	{
	    return(1);
	}
	else
	{
	    int i, j, k;
	    for (i = 0; i < rr; i++)
	    {
		for (j = 0; j < rc; j++)
		{
		    r.set(i, j, 0.0);
		}
	    }
	    for (k = 0; k < ac; k++)
	    {
		for (i = 0; i < rr; i++)
		{
		    for (j = 0; j < rc; j++)
		    {
			r.set(i, j, r.get(i, j) + a.get(i, k, ta) * b.get(k, j, tb));
		    }
		}
	    }
	    return(0);
	}
    } // multiply

    public static int transpose(XMMatrix a, XMMatrix r)
    {
	if (a.row != r.column || a.column != r.row)
	{
	    return(1);
	}
	else
	{
	    int i, j, k;
	    for (i = 0; i < a.row; i++)
	    {
		for (j = 0; j < a.column; j++)
		{
		    r.set(j, i, a.get(i, j));
		}
	    }
	    return(0);
	}
    } // transpose

    private static double NEARZERO =	0.0000000001;

    private static boolean isNearZero(double n)
    {
	return(n < NEARZERO && n > -NEARZERO);
    } // isNearZero

    private static boolean searchSwap(XMMatrix org, XMMatrix inv, int i)
    {
	int j;
	for (j = 0; j < org.column; j++)
	{
	    if (!isNearZero(org.get(i, j)) && (!isNearZero(org.get(j, i)) || j > i))
	    {
		org.swapRow(i, j);
		inv.swapRow(i, j);
		return(true);
	    }
	}
	return(false);
    } // searchSwap

    private static boolean adjustDiagonal(XMMatrix org, XMMatrix inv)
    {
	int i;
	for (i = 0; i < org.row; i++)
	{
	    if (isNearZero(org.get(i, i)) && !searchSwap(org, inv, i))
	    {
		return(false);
	    }
	}
	return(true);
    } // adjustDiagonal

    private static boolean gaussElimination(XMMatrix org, XMMatrix inv)
    {
	int i, j, r;
	double orr, oir;
	for (r = 0; r < org.row; r++)
	{
	    orr = org.get(r, r);
	    if (isNearZero(orr))
	    {
		return(false);
	    }
	    else
	    {
		for (i = 0; i < org.row; i++)
		{
		    if (i != r)
		    {
			oir = org.get(i, r);
			for (j = 0; j < org.column; j++)
			{
			    org.set(i, j, org.get(i, j) - oir * org.get(r, j) / orr);
			    inv.set(i, j, inv.get(i, j) - oir * inv.get(r, j) / orr);
			}
		    }
		}
	    }
	}
	for (r = 0; r < org.row; r++)
	{
	    orr = org.get(r, r);
	    if (isNearZero(orr))
	    {
		return(false);
	    }
	    else
	    {
		for (j = 0; j < org.column; j++)
		{
		    inv.set(r, j, inv.get(r, j) / orr);
		}
	    }
	}
	return(true);
    } // gaussElimination

    public static int inverse(XMMatrix a, XMMatrix r)
    {
	XMMatrix c = a.copy();
	if (c.row != c.column || c.row != r.row || c.column != r.column)
	{
	    return(1);
	}
	else
	{
	    identity(r);
	    return(adjustDiagonal(c, r) && gaussElimination(c, r) ? 0 : 1);
	}
    } // inverse

} // XMMatrix
