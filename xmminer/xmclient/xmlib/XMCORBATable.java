
//Title:        XM-Miner
//Version:
//Copyright:    Copyright (c) 1999
//Author:       Yong Uk Song
//Company:      DIS
//Description:  XM-Miner

package xmminer.xmclient.xmlib;

import xmminer.xmclient.*;

public class XMCORBATable
{
  transient String m_columnNames[];
  transient int m_nColumns, m_nRows;

  transient int m_page;
  transient String m_cColumnNames[];
  transient int m_cRowNumber;
  transient int m_cNColumns, m_cNRows;
  transient String m_cRowData[][];

  public XMCORBATable(String projectPath, String projectName, String metaFilename, int id) throws Exception
  {
    try
    {
      SetCORBA();

	  System.out.println(projectPath + ", " + projectName + ", " + metaFilename + ", " + id);

      m_sCORBATable.Initialize(projectPath, projectName, metaFilename, id);

	  System.out.println("constructor");

      m_columnNames = m_cColumnNames = m_sCORBATable.GetColumnNames();
      m_nColumns = m_cNColumns = (m_columnNames == null) ? 0 : m_columnNames.length;
      m_nRows = m_sCORBATable.GetNumberOfRows();
    }
    catch (Exception e)
    {
      m_columnNames = m_cColumnNames = null;
      m_nColumns = m_cNColumns = m_nRows = 0;
      throw(e);
    }
  }

  // CORBA
  //transient xmminer.xmvbj.xmlib.XMBCORBATable m_sCORBATable = null;
  transient xmminer.xmserver.xmlib.XMBCORBATable m_sCORBATable = null;

  private void SetCORBA() throws org.omg.CORBA.OBJECT_NOT_EXIST
  {
    if (m_sCORBATable == null)
    {
      // Get the manager Id
      // Locate an account manager. Give the full POA name and the servant ID.
      try
      {
        //m_sCORBATable = xmminer.xmvbj.xmlib.XMBCORBATableHelper.bind(XMMiner.m_orb, "/xmminer_poa", "XMCORBATable".getBytes());
        m_sCORBATable = new xmminer.xmserver.xmlib.XMBCORBATable();
      }
      catch (org.omg.CORBA.OBJECT_NOT_EXIST e)
      {
        m_sCORBATable = null;
        System.out.println("CORBA object XMCORBATable does not exist.");
        throw(e);
      }
    }
  }

  public int Initialize(String[] queryColumnNames, int page)
  {
	  System.out.println("initialize");
    SetQueryColumns(queryColumnNames);

    m_page = page;
    m_cRowNumber = 0;
    m_cNRows = (m_page <= 0) ? 128 : m_page;
    m_cRowData = (m_sCORBATable == null) ? null : m_sCORBATable.GetRows(m_cRowNumber, m_cNRows);
    return((m_sCORBATable == null) ? 1 : 0);
  }

  public void Finalize()
  {
    if (m_sCORBATable != null)
    {
      m_sCORBATable.Finalize();
    }
  }

  // No Buffering

  public String GetProfile(String name)
  {
    return((m_sCORBATable == null) ? null : m_sCORBATable.GetProfile(name));
  }

  public String[] GetProfiles(String name)
  {
    return((m_sCORBATable == null) ? null : m_sCORBATable.GetProfiles(name));
  }

  public int SetQueryColumns(String[] columnNames)
  {
    m_cColumnNames = (columnNames == null) ? m_columnNames : columnNames;
    m_cNColumns = m_cColumnNames.length;
    return((m_sCORBATable == null) ? -1 : m_sCORBATable.SetQueryColumns(m_cColumnNames));
  }

  // Buffering

  public int GetRowCount()
  {
    return(m_nRows);
  }

  public int GetColumnCount()
  {
    return(m_cNColumns);
  }

  public String GetColumnName(int col)
  {
    return(m_cColumnNames[col]);
  }

  public Class GetColumnClass(int c)
  {
    return((m_cRowData.length <= 0) ? null : m_cRowData[0][c].getClass());
  }

  public Object GetValueAt(int row, int col)
  {
    if (row < m_cRowNumber)
    {
      int bottom = m_cRowNumber - m_cNRows;
      if (bottom < 0) bottom = 0;
      m_cRowNumber = (bottom >= row) ? row : bottom;
      m_cRowData = m_sCORBATable.GetRows(m_cRowNumber, m_cNRows);
    }
    else if (row >= m_cRowNumber + m_cNRows)
    {
      m_cRowNumber = row;
      m_cRowData = m_sCORBATable.GetRows(m_cRowNumber, m_cNRows);
    }
    return(m_cRowData[row - m_cRowNumber][col]);
  }

  public String[][] GetAllRows(){
    return m_sCORBATable.GetAllRows();
  }

  public String[] GetAllCols(){ return m_columnNames; }

  public void SaveAllRows(){
    m_sCORBATable.SaveAllRows();
  }
}
