
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

import xmminer.xmclient.xmlib.*;
import xmminer.xmclient.xmgraph.*;

public class XMCORBARun
{
  XMCORBARunModel m_corbaRunModel;

  public XMCORBARun(XMCORBARunModel corbaRunModel)
  {
    m_corbaRunModel = corbaRunModel;
  }

  // Run
  transient Thread m_threadProgress = null;
  transient Thread m_threadRun = null;
  transient XMDialogProgress m_dialogProgress;

  public class ThreadProgress implements Runnable
  {
    public void run()
    {
      int x, prevx, gap;

      for (prevx = m_corbaRunModel.OnInitProgress(m_dialogProgress), gap = 100; m_corbaRunModel.IsRunning(); prevx = x)
      {
        x = m_corbaRunModel.OnShowProgress(m_dialogProgress);
        if (x == prevx && gap < 5000)
        {
          gap += 100;
        }
        else if (x != prevx && gap > 100)
        {
          gap -= 100;
        }
        try
        {
          Thread.sleep(gap);
        }
        catch (InterruptedException e)
        {
        }
      }
      m_dialogProgress.dispose();
    }
  }

  public class ThreadRun implements Runnable
  {
    public void run()
    {
      m_corbaRunModel.Run();
    }
  }

  public int Run(JFrame frame)
  {
    // Global Parameters
    m_corbaRunModel.m_bStopped = false;
    m_corbaRunModel.m_retRun = XMGraphElement.XMGESTAT_RUN_NORUN;

    // Dialog
    m_dialogProgress = m_corbaRunModel.CreateDialog(frame);
    //m_dialogProgress.setSize(700, 500);
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension dialogSize = m_dialogProgress.getSize();
    if (dialogSize.height > screenSize.height)
      dialogSize.height = screenSize.height;
    if (dialogSize.width > screenSize.width)
      dialogSize.width = screenSize.width;
    m_dialogProgress.setLocation((screenSize.width - dialogSize.width) / 2, (screenSize.height - dialogSize.height) / 2);

    if (m_corbaRunModel.InitializeRunning() != 0)
    {
      return(XMGraphElement.XMGESTAT_RUN_ERROR);
    }

    // Thread
    if (m_threadProgress == null || !m_threadProgress.isAlive())
    {
      m_threadProgress = new Thread(new ThreadProgress());
      m_threadProgress.start();
    }
    if (m_threadRun == null || !m_threadRun.isAlive())
    {
      m_threadRun = new Thread(new ThreadRun());
      m_threadRun.start();
    }

    m_corbaRunModel.FinalizeRunning();

    m_dialogProgress.show();

    return(m_corbaRunModel.m_retRun);
  }
}
