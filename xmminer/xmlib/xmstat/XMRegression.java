
//Title:        XM-Miner
//Version:      
//Copyright:    Copyright (c) 1999
//Author:       Yong Uk Song
//Company:      DIS
//Description:  XM-Miner

package xmminer.xmlib.xmstat;

import xmminer.xmlib.*;
import xmminer.xmlib.xmmath.*;
import xmminer.xmserver.xmlib.*;

public class XMRegression
{
    private boolean	success;
    private XMMatrix	x;
    private XMMatrix	y;
    private XMMatrix	b;
    private XMMatrix	yy;
    private XMMatrix	bxy;

    public XMRegression(XMMatrix xData, XMMatrix yData)
    {
	success = false;
	if (xData.getRow() == yData.getRow())
	{
	    x = xData;
	    y = yData;
	}
    } // XMRegression

    public boolean run(boolean useFile, XMProgress progress)
    {
	if (x != null && y != null && x.getRow() != y.getRow())
	{
	    return(success = false);
	}
	else
	{
	    if (!progress.setProgress(10, "Calculating ... X'X"))
	    {
		return(success = false);
	    }
	    XMMatrix xx = new XMMatrixM(x.getColumn(), x.getColumn());
	    if (XMMatrix.multiply(x, x, XMMatrix.TRANSPOSE_ARG1, xx) != 0)
	    {
		return(success = false);
	    }

	    if (!progress.setProgress(20, "Calculating ... inv(X'X)"))
	    {
		return(success = false);
	    }
	    XMMatrix xxi = new XMMatrixM(x.getColumn(), x.getColumn());
	    if (XMMatrix.inverse(xx, xxi) != 0)
	    {
		return(success = false);
	    }

	    if (!progress.setProgress(30, "Calculating ... X'Y"))
	    {
		return(success = false);
	    }
	    XMMatrix xy = new XMMatrixM(x.getColumn(), y.getColumn());
	    if (XMMatrix.multiply(x, y, XMMatrix.TRANSPOSE_ARG1, xy) != 0)
	    {
		return(success = false);
	    }

	    if (!progress.setProgress(40, "Calculating ... inv(X'X)X'Y"))
	    {
		return(success = false);
	    }
	    b = new XMMatrixM(x.getColumn(), y.getColumn());
	    if (XMMatrix.multiply(xxi, xy, b) != 0)
	    {
		return(success = false);
	    }

	    if (!progress.setProgress(50, "Calculating ... Y'Y"))
	    {
		return(success = false);
	    }
	    yy = new XMMatrixM(y.getColumn(), y.getColumn());
	    if (XMMatrix.multiply(y, y, XMMatrix.TRANSPOSE_ARG1, yy) != 0)
	    {
		return(success = false);
	    }

	    if (!progress.setProgress(60, "Calculating ... bX'Y"))
	    {
		return(success = false);
	    }
	    bxy = new XMMatrixM(y.getColumn(), xy.getColumn());
	    if (XMMatrix.multiply(b, xy, XMMatrix.TRANSPOSE_ARG1, bxy) != 0)
	    {
		return(success = false);
	    }

	    return(success = true);
	}
    } // run

    public XMMatrix getB()
    {
	return(success ? b : null);
    } // getB

    public XMMatrix getYY()
    {
	return(success ? yy : null);
    } // getB

    public XMMatrix getBXY()
    {
	return(success ? bxy : null);
    } // getB

    public int getDF()
    {
	return(x.getRow());
    }

}  // class XMRegression
