
//Title:        XM-Miner
//Version:
//Copyright:    Copyright (c) 1999
//Author:       Yong Uk Song
//Company:      DIS
//Description:  XM-Miner

// Modified by Sun Jee Hun ///////////////////////////////////////////////////////
//																				//
//		Title : XM-Miner 클라이언트 파티셔닝 객체								//
//	 	2001년 4월 14일 수정 시작 (by 선지헌)									//
//		Last update : 2001년 4월 18일 선지헌									//
//																				//
//////////////////////////////////////////////////////////////////////////////////

package xmminer.xmclient.xmgraph.xmdnode.xmdnodepartitioning;

import javax.swing.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import xmminer.xmlib.*;
import xmminer.xmclient.*;
import xmminer.xmclient.xmgraph.*;
import xmminer.xmclient.xmgraph.xmdnode.xmdnodepartitioning.*;

public class XMDNodePartitioning extends XMDNode implements Serializable
{

  public int previous_index;
  public int training_index;
  public int validation_index;
  public String project_name;
  private String previous_arc;
  private String training_arc;
  private String validation_arc;
  private String arc = "arc";

// Modified by Sun Jee Hun ///////////////////////////////////////////////////////
	private int total_data_number;
	private int partitioning_type;
	private int column_count;

	private int partitioning_number_of_training;
	private int partitioning_rate_of_training;
	private int partitioning_number_of_testing;
	private int partitioning_rate_of_testing;

	private String partitioning_range_of_training;
	private String partitioning_range_of_testing;

	private JOptionPane optionPane;	// 에러 메세지 출력을위한 

	public void setColumnCount(int tmp)
	{
		column_count = tmp;
	}
	public int getColumnCount()
	{
		return column_count;
	}

	public void setPartitioningRangeOfTraining(String tmp)
	{
		partitioning_range_of_training = tmp;
	}
	public String getPartitioningRangeOfTraining()
	{
		return partitioning_range_of_training;
	}

	public void setPartitioningRangeOfTesting(String tmp)
	{
		partitioning_range_of_testing = tmp;
	}
	public String getPartitioningRangeOfTesting()
	{
		return partitioning_range_of_testing;
	}

	public void setPartitioningType(int tmp)
	{
		partitioning_type = tmp;
	}
	public int getPartitioningType()
	{
		return partitioning_type;
	}

	public void setTotalDataNumber(int tmp)
	{
		total_data_number = tmp;
	}
	public int getTotalDataNumber()
	{
		return total_data_number;
	}

	public void setPartitioningNumberOfTraining(int tmp)
	{
		partitioning_number_of_training = tmp;
	}
	public int getPartitioningNumberOfTraining()
	{
		return partitioning_number_of_training;
	}

	public void setPartitioningRateOfTraining(int tmp)
	{
		partitioning_rate_of_training = tmp;
	}
	public int getPartitioningRateOfTraining()
	{
		return partitioning_rate_of_training;
	}

	public void setPartitioningNumberOfTesting(int tmp)
	{
		partitioning_number_of_testing = tmp;
	}
	public int getPartitioningNumberOfTesting()
	{
		return partitioning_number_of_testing;
	}

	public void setPartitioningRateOfTesting(int tmp)
	{
		partitioning_rate_of_testing = tmp;
	}
	public int getPartitioningRateOfTesting()
	{
		return partitioning_rate_of_testing;
	}

	private void showWarningDialog(JFrame frame, String msg, String title)
	{
		optionPane.showMessageDialog(frame, msg, title, optionPane.WARNING_MESSAGE);
	}
//////////////////////////////////////////////////////////////////////////////////

  // Constructors
  public XMDNodePartitioning(int stat)
  {
    super(stat);
  }

  public int OnCreate()
  {
    // Do not touch
    int err;
    if ((err = super.OnCreate()) != 0)
    {
      return(err);
    }

    return(0);
  }

  public int OnDelete()
  {
    // Do not touch
    int err;
    if ((err = super.OnDelete()) != 0)
    {
      return(err);
    }
    return(0);
  }

  public static XMGraphElement Create(int stat)
  {
    return(new XMDNodePartitioning(stat));
  }

  public String toString()
  {
    //return(super.toString() + ".XMDNodePartitioning");
    return("XMDNodePartitioning");
  }

  public String GetText()
  {
    //return(super.toString() + ".XMDNodePartitioning");
    return("Partitioning");
  }

  // Operation
  public int OnConnectedFrom(JFrame frame, XMGraph graph, XMGraphElement element)
  {
    // Do not touch
    int err;
    if ((err = super.OnConnectedFrom(frame, graph, element)) != 0)
    {
      return(err);
    }

    // chkeck validity of connectin from the 'element'
    if (GetInCount(graph) >= 1)   // maximumn in = 1
    {
      return(-1);
    }
    return(0);
  }

  public int OnConnectedTo(JFrame frame, XMGraph graph, XMGraphElement element)
  {
    int err;
    if ((err = super.OnConnectedTo(frame, graph, element)) != 0)
    {
      return(err);
    }

    // chkeck validity of connectin to the 'element'
    if (GetOutCount(graph) >= 2)  // maximumn out = 1
    {
        return(-1);
    }

    return(0);
  }

  public int OnDisconnectedFrom(JFrame frame, XMGraph graph, XMGraphElement element)
  {
    // Do not touch
    int err;
    if ((err = super.OnDisconnectedFrom(frame, graph, element)) != 0)
    {
      return(err);
    }

    return(0);
  }

  public int OnDisconnectedTo(JFrame frame, XMGraph graph, XMGraphElement element)
  {
    // Do not touch
    int err;
    if ((err = super.OnDisconnectedTo(frame, graph, element)) != 0)
    {
      return(err);
    }

    return(0);
  }

  // Paint

  public int SetSchema(XMGraph graph)
  {
    XMVNode inNode;
    if ((inNode = (XMVNode)GetInElement(0, graph)) == null)
    {
      System.out.println("Input node is not defined.");
      return(XMGraphElement.XMGESTAT_RUN_ERROR);
    }
    XMVNode outNode;
    if ((outNode = (XMVNode)GetOutElement(0, graph)) == null)
    {
      System.out.println("Output node is not defined.");
      return(XMGraphElement.XMGESTAT_RUN_ERROR);
    }
    return(0);
  }

  //CORBA
  //transient xmminer.xmvbj.xmgraph.xmdnode.xmdnodepartitioning.XMBDNodePartitioning m_sDNodePartitioning = null;
  transient xmminer.xmserver.xmgraph.xmdnode.xmdnodepartitioning.XMBDNodePartitioning m_sDNodePartitioning = null;

  private void SetCORBA()
  {
  	if (m_sDNodePartitioning == null)
  	{
  		//m_sDNodePartitioning = xmminer.xmvbj.xmgraph.xmdnode.xmdnodepartitioning.XMBDNodePartitioningHelper.bind(XMMiner.m_orb, "/xmminer_poa","XMDNodePartitioning".getBytes());
  		m_sDNodePartitioning = new xmminer.xmserver.xmgraph.xmdnode.xmdnodepartitioning.XMBDNodePartitioning();
  	}
  }

  // Edit
  public int Edit(JFrame frame, XMGraph graph)
  {
  	try
  	{
  	    previous_index = GetInElement(0, graph).GetUniqueId();
  	}  catch(NullPointerException ne1)
  	    {
  	         showWarningDialog(frame, "입력 Data Node가 없습니다 !", "초기화에러");
  	         return XMGESTAT_EDIT_NOCHANGE;
  	    }
  	try
  	{
  	    training_index = GetOutElement(0, graph).GetUniqueId();
  	    validation_index = GetOutElement(1, graph).GetUniqueId();
  	} catch (NullPointerException ne2)
  	   {
  	         showWarningDialog(frame, "출력 Data Node가 없습니다 !", "초기화에러");
  	         return XMGESTAT_EDIT_NOCHANGE;
  	   }

	XMNode errNode;
    if ((errNode = GetSchema(graph)) != null)
    {
      JOptionPane.showMessageDialog(frame, "Cannot get the schema of " + errNode.GetName() + ".");
      return(XMGESTAT_EDIT_NOCHANGE);
    }

  	project_name = graph.GetProjectName();

	SetCORBA();

	XMDialogDNodePartitioning ddp = new XMDialogDNodePartitioning(frame,"Partitioning V1.0",true,m_sDNodePartitioning,this);
  	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
       Dimension frameSize = ddp.getSize();
       if (frameSize.height > screenSize.height)
           frameSize.height = screenSize.height;
       if (frameSize.width > screenSize.width)
           frameSize.width = screenSize.width;
       ddp.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
       ddp.setVisible(true);

		if (ddp.isModified() == false)
		{
			return XMGESTAT_EDIT_NOCHANGE;
		}
		else
		{
			return XMGESTAT_EDIT_CRITICALCHANGE;
		}

	}

  // Run

  transient Thread m_threadRun = null;
  transient Thread m_threadProgress = null;
  JDialog m_dialog;
  boolean m_bStopped;
  boolean m_bSuccess;
  int m_pbMin;
  int m_pbMax;

  public JDialog CreateDialog(JFrame frame)
  {
    return(new DNodeDialogPrgBar(frame, "데이터 분할(Partitioning)", true, this));
  }

  public class ThreadProgress implements Runnable {
    public void run(){
        JProgressBar progressBar = ((DNodeDialogPrgBar)m_dialog).jProgressBar1;

        int x = m_pbMin;
        int prevx = x;
        progressBar.setMinimum(m_pbMin);
        progressBar.setMaximum(m_pbMax);
        progressBar.setValue(x);
        while(IsRunning())
        {
            x = m_sDNodePartitioning.GetPBValue();
            progressBar.setValue(x);
        }
        m_dialog.dispose();
    }
  }

  public class ThreadRun implements Runnable{
    public void run() {
        m_bSuccess = (m_sDNodePartitioning.Run() == 0) && !m_bStopped;
    }
  }

  public boolean IsRunning () {
    return (m_sDNodePartitioning.IsRunning());
  }

  public void StopRunning(){
    m_sDNodePartitioning.StopRunning();
    m_bStopped = true;
  }

// Modified by Sun Jee Hun ///////////////////////////////////////////////////////
// 실행
	public int Run(JFrame frame, XMGraph graph)
	{
		try
		{
			if (total_data_number == 0 || partitioning_number_of_training == 0 || partitioning_number_of_testing == 0)
			{
				throw new Exception();
			}	
		}
		catch (Exception e)
		{
			showWarningDialog(frame, "객체의 정보가 적절하게 초기화 되지 않았습니다 !", "초기화에러");
			return(1);
		}

		try
		{
			previous_index = GetInElement(0, graph).GetUniqueId();
		}  
		catch(NullPointerException ne1)
		{
			showWarningDialog(frame, "입력 Data Node가 없습니다 !", "초기화에러");
			return(1);
		}

		try
		{
			training_index = GetOutElement(0, graph).GetUniqueId();
			validation_index = GetOutElement(1, graph).GetUniqueId();
		} 
		catch (NullPointerException ne2)
		{
			showWarningDialog(frame, "출력 Data Node가 없거나 부족합니다 !", "초기화에러");
			return(1);
		}

		project_name = graph.GetProjectName();
		setPreviousArc();
		setTrainingArc();
		setValidationArc();
		
		m_dialog = CreateDialog(frame);
		m_dialog.setSize(400, 120);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension dialogSize = m_dialog.getSize();
	
		if (dialogSize.height > screenSize.height) dialogSize.height = screenSize.height;
		if (dialogSize.width > screenSize.width) dialogSize.width = screenSize.width;
		
		m_dialog.setLocation((screenSize.width - dialogSize.width) / 2, (screenSize.height - dialogSize.height) / 2);

	// GraphElement Server
		SetCORBA();
		m_bStopped = false;
		m_bSuccess = false;
		int r = m_sDNodePartitioning.setFileStatus(project_name,previous_arc,training_arc,validation_arc,"run");
		if (r!=0) 
		{
			showWarningDialog(frame, "객체의 정보가 적절하게 초기화 되지 않았습니다 !", "초기화에러");
			return XMGESTAT_RUN_ERROR;
		}
		
		String [] column_list = m_sDNodePartitioning.getColumnList();

		try
		{
			if ((total_data_number != m_sDNodePartitioning.getTotalRows()) || (column_list.length != column_count))
			{
				throw new Exception();
			}
		}
		catch (Exception e)
		{
			showWarningDialog(frame, "데이터에 변동이 있거나 데이터가 준비되지 않았습니다 !", "초기화에러");
			return(1);
		}

		m_pbMin = m_sDNodePartitioning.GetPBMin();
		m_pbMax = m_sDNodePartitioning.GetPBMax();

		// Thread
		if (m_threadRun == null || !m_threadRun.isAlive())
		{
			m_threadRun = new Thread(new ThreadRun());
			m_threadRun.start();
		}
		
		if (m_threadProgress == null || !m_threadProgress.isAlive())
		{
			m_threadProgress = new Thread(new ThreadProgress());
			m_threadProgress.start();
		}
		m_dialog.show();
		System.out.println("m_bStopped="+m_bStopped);
		System.out.println("m_bSuccess="+m_bSuccess);
		return(m_bStopped ? XMGESTAT_RUN_NORUN : XMGESTAT_RUN_SUCCESS);
	}
//////////////////////////////////////////////////////////////////////////////////

  private void setPreviousArc()
  {
      previous_arc = arc+previous_index;
  }

  private void setTrainingArc()
  {
      training_arc = arc+training_index;
  }

  private void setValidationArc()
  {
      validation_arc = arc+validation_index;
  }
}
