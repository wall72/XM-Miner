
//Title:        XM-Miner
//Version:
//Copyright:    Copyright (c) 1999
//Author:       Yong Uk Song
//Company:      DIS
//Description:  XM-Miner

package xmminer.xmclient.xmlib;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import xmminer.xmclient.xmgraph.*;

public class XMCORBARunModel
{
  public boolean m_bStopped;
  public int m_retRun = XMGraphElement.XMGESTAT_RUN_NORUN;

  // Override
  public int InitializeRunning()
  {
    return(0);
  }

  // Override
  public boolean IsRunning()
  {
    return(false);
  }

  // Override
  public void OnStopRunning()
  {
  }

  // Do not override
  public void StopRunning()
  {
    OnStopRunning();
    m_bStopped = true;
  }

  // Override
  public int OnRun()
  {
    return(XMGraphElement.XMGESTAT_RUN_SUCCESS);
  }

  // Do not override
  public void Run()
  {
    m_retRun = OnRun();
  }

  // Override
  public XMDialogProgress CreateDialog(JFrame frame)
  {
    return(new XMDialogProgress(frame, this, "Run", true));
  }

  // Override
  public int GetPBMin()
  {
    return(0);
  }

  // Override
  public int GetPBMax()
  {
    return(0);
  }

  // Override
  public int GetPBValue()
  {
    return(0);
  }

  // Override
  public String GetPBDescription()
  {
    return("");
  }

  // Override
  public void FinalizeRunning()
  {
  }

  // Override
  public int OnInitProgress(XMDialogProgress m_dialogProgress)
  {
    int pbMin = GetPBMin();
    int pbMax = GetPBMax();
    m_dialogProgress.initProgress(pbMin, pbMax);
    m_dialogProgress.setProgress(pbMin, "Starting ...");
    return(pbMin);
  }

  // Override
  public int OnShowProgress(XMDialogProgress m_dialogProgress)
  {
    int x = GetPBValue();
    String description = GetPBDescription();
    m_dialogProgress.setProgress(x, description);
    return(x);
  }
}
