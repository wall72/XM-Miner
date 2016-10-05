
//Title:        XM-Miner
//Version:      
//Copyright:    Copyright (c) 1999
//Author:       Yong Uk Song
//Company:      DIS
//Description:  XM-Miner

package xmminer.xmlib.xmmath;

import java.io.*;

import xmminer.xmlib.xmtable.*;

public class XMTableMatrixQuerier
{
    CXMTableQuerier m_tableQuerier;
    int m_nRow, m_page, m_startColumn, m_nColumn;
    boolean m_bIntercept;

    public XMTableMatrixQuerier(CXMTableQuerier tableQuerier,
				int nRow, int page, int startColumn, int nColumn, boolean bIntercept)
    {
	m_tableQuerier = tableQuerier;

	m_nRow = nRow;
	m_page = (page <= 0) ? 128 : page;

	m_startColumn = startColumn;
	m_nColumn = nColumn + (bIntercept ? 1 : 0);

	m_bIntercept = bIntercept;
    }

    public int GetRow()
    {
	return(m_nRow);
    }

    public int GetColumn()
    {
	return(m_nColumn);
    }

    private String[][] GetRows(int nStart, int nRows)
    {
	int i;
	if (nStart >= m_nRow)
	{
	    nRows = 0;
	}
	else if (nStart + nRows > m_nRow)
	{
	    nRows = m_nRow - nStart;
	}

	if (nRows <= 0)
	{
	    return(null);
	}
	else
	{
	    String rows[][] = new String[nRows][];
	    for (i = 0; i < nRows; i++)
	    {
		rows[i] = m_tableQuerier.getStringArrayInColArray(nStart + i + 1);
	    }
	    return(rows);
	}
    }

    int m_cRowNumber = 0;
    String m_cRowData[][] = null;

    public double GetValueAt(int row, int col)
    {
	if (m_bIntercept && col == 0)
	{
	    return(1.0);
	}
        if (m_cRowData == null)
	{
	    m_cRowNumber = 0;
	    m_cRowData = this.GetRows(m_cRowNumber, m_page);
	}
	else if (row < m_cRowNumber)
	{
	    int bottom = m_cRowNumber - m_page;
	    if (bottom < 0) bottom = 0;
	    m_cRowNumber = (bottom >= row) ? row : bottom;
	    m_cRowData = this.GetRows(m_cRowNumber, m_page);
	}
	else if (row >= m_cRowNumber + m_page)
	{
	    m_cRowNumber = row;
	    m_cRowData = this.GetRows(m_cRowNumber, m_page);
	}
	return(Double.parseDouble(m_cRowData[row - m_cRowNumber][m_startColumn + col - (m_bIntercept ? 1 : 0)]));
    }

}
