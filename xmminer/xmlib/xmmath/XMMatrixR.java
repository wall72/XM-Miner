
//Title:        XM-Miner
//Version:      
//Copyright:    Copyright (c) 1999
//Author:       Yong Uk Song
//Company:      DIS
//Description:  XM-Miner

package xmminer.xmlib.xmmath;

import java.io.*;

public class XMMatrixR extends XMMatrix
{
    XMTableMatrixQuerier m_tableMatrixQuerier;

    public XMMatrixR(XMTableMatrixQuerier tableMatrixQuerier)
    {
	super(tableMatrixQuerier.GetRow(), tableMatrixQuerier.GetColumn());
	m_tableMatrixQuerier = tableMatrixQuerier;
    }

    public XMMatrix copy()				// virtual
    {
	System.out.println("Error: Copy of XMMatrixR is not allowed");
	return(null);
    }

    public double get(int i, int j)			// virtual
    {
	return(m_tableMatrixQuerier.GetValueAt(i, j));
    }

    public double get(int i, int j, boolean transpose)	// virtual
    {
	return(transpose ? this.get(j, i) : this.get(i, j));
    }

    public double set(int i, int j, double element)	// virtual
    {
	System.out.println("Error: Writing to XMMatrixR is not allowed");
	return(0.0);
    }

    public double set(int i, int j, boolean transpose, double element)	// virtual
    {
	System.out.println("Error: Writing to XMMatrixR is not allowed");
	return(0.0);
    }
}
