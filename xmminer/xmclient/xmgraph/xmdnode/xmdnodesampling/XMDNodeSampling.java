
//Title:        XM-Miner
//Version:
//Copyright:    Copyright (c) 1999
//Author:       Yong Uk Song
//Company:      DIS
//Description:  XM-Miner

// Modified by Sun Jee Hun ///////////////////////////////////////////////////////
//																				//
//		Title : XM-Miner 클라이언트 샘플링 객체									//
//	 	2001년 3월 21일 수정 시작 (by 선지헌)									//
//		2001년 3월 22일 현재													//
//			   랜덤 샘플링과 클러스터 샘플링, 체계적 표본추출이 구현되어있음	//
//		Last update : 2001년 3월 28일 선지헌									//
//			- 체계적 표본추출 완료 (3월 22일)									//
//			- 객체에 추가할 정보(샘플링 방법, 추출행 갯수 etc)에 관한 수정		//
//			- Run 메서드에서의 에러처리											//
//																				//
//////////////////////////////////////////////////////////////////////////////////

package xmminer.xmclient.xmgraph.xmdnode.xmdnodesampling;

import javax.swing.*;
import java.io.*;

import java.awt.*;
import java.awt.event.*;

import xmminer.xmlib.*;
import xmminer.xmclient.*;
import xmminer.xmclient.xmgraph.*;
import xmminer.xmclient.xmgraph.xmdnode.xmdnodesampling.*;

public class XMDNodeSampling extends XMDNode implements Serializable
{

  //XMDialogDNodeSampling xds;
  public int previous_index;
  public int next_index;
  
  private int total_data_number = 0;	// 전체 데이터 수
  private int sampling_type = 0;	// 생성된 객체가 어떤 샘플링 방식이 선택되어져 있는가를 위한 변수
  private int sampling_number = 0;
  private int cluster_number = 1;
  private int cbox_idx = 0;
  private int sampling_rate = 0;
  private int K = 0;
  private int standard_column = 0;
  private int column_count = 0;

  public String project_name;
  private String previous_arc;
  private String next_arc;
  private String arc = "arc";

  private JOptionPane optionPane;	// 에러 메세지 출력을 위한것.

// Modified by Sun Jee Hun ///////////////////////////////////////////////////////
  // Constructors (생성자)
  public XMDNodeSampling(int stat)
  {
    super(stat);
  }

  // 입력 에러 발생시 경고박스 표시
  private void showWarningInputError(JFrame frame, String msg)
  {
	  optionPane.showMessageDialog(frame, msg, "초기화 에러", optionPane.WARNING_MESSAGE);
  }  
  
  // 전체 데이터 수 저장
  public void setTotalDataNumber(int tmp)
  {
	  total_data_number = tmp;
  }
  
  // 전체 데이터 수 얻어오기
  public int getTotalDataNumber()
  {
	  return total_data_number;
  }
  
  // 샘플링 방법 결정에 관한 정보 저장
  public void setSamplingType(int tmp)
  {
	sampling_type = tmp;
  }

  // 샘플링 방법 결정에 관한 정보 얻어오기
  public int getSamplingType()
  {
	return sampling_type;
  }

  // 추출행 갯수에 관한 정보 셋팅
  public void setSamplingNumber(int tmp)
  {
	sampling_number = tmp;
  }

  // 추출행 갯수에 관한 정보 얻어오기
  public int getSamplingNumber()
  {
	return sampling_number;
  }

  // 추출 비율에 관한 정보 저장
  public void setSamplingRate(int tmp)
  {
	sampling_rate = tmp;
  }

  // 추출 비율에 관한 정보 얻어오기
  public int getSamplingRate()
  {
	return sampling_rate;
  }

  // K에 관한 정보 저장
  public void setKValue(int tmp)
  {
	K = tmp;
  }

  // K에 관한 정보 얻어오기
  public int getKValue()
  {
	return K;
  }

  // 군집 갯수에 관한 정보 저장 (이 메서드는 층화 추출과 군집 추출에서 공유되어 사용된다.)
  public void setClusterNumber(int tmp)
  {
	cluster_number = tmp;
  }

  // 군집 갯수에 관한 정보 얻어오기 (이 메서드는 층화 추출과 군집 추출에서 공유되어 사용된다.)
  public int getClusterNumber()
  {
	return cluster_number;
  }

  // 복원추출인가 아닌가에 대한 정보 저장
  public void setCboxValue(int tmp)
  {
	cbox_idx = tmp;
  }

  // 복원추출인가 아닌가에 대한 정보 얻어오기
  public int getCboxValue()
  {
	return cbox_idx;
  }

  // 기준 컬럼 지정
  public void setStandardColumn(int tmp)
  {
	  standard_column = tmp;
  }
  
  // 기준 컬럼 정보 얻어오기
  public int getStandardColumn()
  {
	  return standard_column;
  }

  // 층화추출에서 선택된 데이터의 컬럼 갯수 저장
  public void setColumnCount(int tmp)
  {
	  column_count = tmp;
  }

  // 층화추출에서 선택된 데이터의 컬럼 갯수 얻어오기
  public int getColumnCount()
  {
	  return column_count;
  }
//////////////////////////////////////////////////////////////////////////////////

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
    return(new XMDNodeSampling(stat));
  }

  public String toString()
  {
    //return(super.toString() + ".XMDNodeSampling");
    return("XMDNodeSampling");
  }

  public String GetText()
  {
    //return(super.toString() + ".XMDNodeSampling");
    return("Sampling");
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
    if (GetOutCount(graph) >= 1)  // maximumn out = 1
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
    next_index = GetInElement(0, graph).GetUniqueId();
    return(0);
  }

  // Paint

  //CORBA
  //transient xmminer.xmvbj.xmgraph.xmdnode.xmdnodesampling.XMBDNodeSampling m_sDNodeSampling = null;
  transient xmminer.xmserver.xmgraph.xmdnode.xmdnodesampling.XMBDNodeSampling m_sDNodeSampling = null;

  private void SetCORBA()
  {
  	if (m_sDNodeSampling == null)
  	{
  		m_sDNodeSampling = new xmminer.xmserver.xmgraph.xmdnode.xmdnodesampling.XMBDNodeSampling();
  	}
  }

  // Edit
  public int Edit(JFrame frame, XMGraph graph)
  {
	SetCORBA();
	XMNode errNode;
    if ((errNode = GetSchema(graph)) != null)
    {
      JOptionPane.showMessageDialog(frame, "Cannot get the schema of " + errNode.GetName() + ".");
      return(XMGESTAT_EDIT_NOCHANGE);
    }

	previous_index = GetInElement(0, graph).GetUniqueId();
    next_index = GetOutElement(0, graph).GetUniqueId();
	project_name = graph.GetProjectName();

  	XMDialogDNodeSampling dds = new XMDialogDNodeSampling(frame, "데이터 추출(Sampling)",true,m_sDNodeSampling,this);
  	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = dds.getSize();
    if (frameSize.height > screenSize.height)
        frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
        frameSize.width = screenSize.width;
    dds.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    dds.setVisible(true);

// Modified by Sun Jee Hun ------------------------------------------------------//
	if (dds.getisModified() == true)	// 에디트 박스에서 확인을 누르면 변화가 있다.
	{
		return XMGESTAT_EDIT_CRITICALCHANGE;
	}
	else	// 그렇지 않고 취소를 누르면 변화 없다.
	{
		return XMGESTAT_EDIT_NOCHANGE;
	}
// ------------------------------------------------------------------------------//
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
    return(new DNodeDialogPrgBar(frame, "데이터 추출(Sampling)", true, this));
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
            x = m_sDNodeSampling.GetPBValue();
            progressBar.setValue(x);
        }
        m_dialog.dispose();
    }
  }

  public class ThreadRun implements Runnable{
    public void run() {
        m_bSuccess = (m_sDNodeSampling.Run() == 0) && !m_bStopped;
    }
  }

  public boolean IsRunning () {
    return (m_sDNodeSampling.IsRunning());
  }

  public void StopRunning(){
    m_sDNodeSampling.StopRunning();
    m_bStopped = true;
  }

// RUN (실행)
// Modified by Sun Jee Hun ------------------------------------------------------//
  public int Run(JFrame frame, XMGraph graph)
  {
	try{

		SetCORBA();
		try
		{
			previous_index = GetInElement(0, graph).GetUniqueId();
		}  catch(NullPointerException ne1)
			{
				 showWarningInputError(frame, "입력 Data Node가 없습니다!");
				 return(1);
			}
		try
		{
			next_index = GetOutElement(0, graph).GetUniqueId();
		} catch (NullPointerException ne2)
		   {
				 showWarningInputError(frame, "출력 Data Node가 없습니다!");
				 return(1);
		   }

		project_name = graph.GetProjectName();
		setPreviousArc();
		setNextArc();

		m_dialog = CreateDialog(frame);
		m_dialog.setSize(400, 120);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension dialogSize = m_dialog.getSize();
		if (dialogSize.height > screenSize.height)
		  dialogSize.height = screenSize.height;
		if (dialogSize.width > screenSize.width)
		  dialogSize.width = screenSize.width;
		m_dialog.setLocation((screenSize.width - dialogSize.width) / 2, (screenSize.height - dialogSize.height) / 2);
		// GraphElement Server

		m_bStopped = false;
		m_bSuccess = false;   

		int r = m_sDNodeSampling.setFileStatus(project_name,previous_arc, next_arc,"run");
		
		if (r!=0)
		{
			showWarningInputError(frame, "객체의 정보가 적절하게 초기화 되지 않았습니다 !");
			return XMGESTAT_RUN_ERROR;
		}  

		String [] column_list = m_sDNodeSampling.getColumnList();

		//(column_list는 서버에서 읽은 컬럼수 정보, column_count는 객체가 원래 가지고 있던 컬럼수 정보이다.
		if (column_count == 0)
		{
			showWarningInputError(frame, "객체의 정보가 적절하게 초기화 되지 않았습니다 !");
			return(1);
		}

		if (column_list.length != column_count) 
		{
			showWarningInputError(frame, "데이터에 변동이 있거나 데이터가 준비되지 않았습니다 !");
			return (1);
		}
		
		// m_sDNodeSampling.getTotalRows()는 서버에서 읽은 전체 데이터 수 정보, total_data_number는
		// 객체가 가지고 있던 전체 데이터 수 정보이다.
		if (m_sDNodeSampling.getTotalRows() != total_data_number)
		{
			showWarningInputError(frame, "데이터에 변동이 있거나 데이터가 준비되지 않았습니다 !");
			return (1);
		}

		m_pbMin = m_sDNodeSampling.GetPBMin();
		m_pbMax = m_sDNodeSampling.GetPBMax();

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
	catch (Exception e)
	{
		showWarningInputError(frame, "데이터에 변동이 있거나 데이터가 준비되지 않았습니다 !");
		return (1);
	}
	finally 
    {
		m_sDNodeSampling.close();
    }

  }
// ------------------------------------------------------------------------------//

  private void setPreviousArc()
  {
      previous_arc = arc+previous_index;
  }

  private void setNextArc()
  {
      next_arc = arc+next_index;
  }
}