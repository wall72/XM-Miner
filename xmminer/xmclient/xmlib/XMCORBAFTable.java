
//Title:        XM-Miner
//Version:
//Copyright:    Copyright (c) 1999
//Author:       Yong Uk Song
//Company:      DIS
//Description:  XM-Miner

package xmminer.xmclient.xmlib;

import xmminer.xmclient.*;

public class XMCORBAFTable
{

  public XMCORBAFTable(String projectName, String metaFilename) throws Exception
  {
    try
    {
      SetCORBA();

      m_sCORBAFTable.Initialize(projectName, metaFilename);
    }
    catch (Exception e)
    {
      throw(e);
    }
  }

  // CORBA
  //transient xmminer.xmvbj.xmlib.XMBCORBAFTable m_sCORBAFTable = null;
  transient xmminer.xmserver.xmlib.XMBCORBAFTable m_sCORBAFTable = null;

  private void SetCORBA() throws org.omg.CORBA.OBJECT_NOT_EXIST
  {
    if (m_sCORBAFTable == null)
    {
      // Get the manager Id
      // Locate an account manager. Give the full POA name and the servant ID.
      try
      {
        //m_sCORBAFTable = xmminer.xmvbj.xmlib.XMBCORBAFTableHelper.bind(XMMiner.m_orb, "/xmminer_poa", "XMCORBAFTable".getBytes());
        m_sCORBAFTable = new xmminer.xmserver.xmlib.XMBCORBAFTable();
      }
      catch (org.omg.CORBA.OBJECT_NOT_EXIST e)
      {
        m_sCORBAFTable = null;
        System.out.println("CORBA object XMCORBAFTable does not exist.");
        throw(e);
      }
    }
  }

  public int Initialize()
  {
    return((m_sCORBAFTable == null) ? 1 : 0);
  }

  public void Finalize()
  {
    if (m_sCORBAFTable != null)
    {
      m_sCORBAFTable.Finalize();
    }
  }

  public int InitializeRunning()
  {
    return(m_sCORBAFTable.InitializeRunning());
  }

  public int Run(String columnName)
  {
    return(m_sCORBAFTable.Run(columnName));
  }

  public boolean IsRunning()
  {
    return(m_sCORBAFTable.IsRunning());
  }

  public void StopRunning()
  {
    m_sCORBAFTable.StopRunning();
  }

  public int GetPBMin()
  {
    return(m_sCORBAFTable.GetPBMin());
  }

  public int GetPBMax()
  {
    return(m_sCORBAFTable.GetPBMax());
  }

  public int GetPBValue()
  {
    return(m_sCORBAFTable.GetPBValue());
  }

  public String GetPBDescription()
  {
    return(m_sCORBAFTable.GetPBDescription());
  }

  public void FinalizeRunning()
  {
    m_sCORBAFTable.FinalizeRunning();
  }

  public String[][] GetUniqueValueCount(String columnName)
  {
    return(m_sCORBAFTable.GetUniqueValueCount(columnName));
  }

}
