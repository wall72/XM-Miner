/////////****************8수정*********///////
package xmminer.xmclient.xmgraph.xmmnode.xmmnodeneuralnetwork;
////////**************수정***************////

import javax.swing.*;
import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

////////***********삽입************//
import xmminer.*;
import xmminer.xmlib.*;
import xmminer.xmclient.*;
import xmminer.xmclient.xmgraph.*;
import xmminer.xmclient.xmgraph.xmmnode.*;
import xmminer.xmclient.xmgraph.xmmnode.xmmnodeneuralnetwork.*;
///////////********삽입***************///
import xmminer.xmlib.*;

///***********수정**********************************///
public class XMMNodeNeuralNetwork extends XMMNode implements Serializable
////*****************수정************************///
{
  public  String m_Projectpath;
  public  String ProjectName ; 
  public  String MetaFileName ;
  public  String next_node;
  public  String ParameterFileName ; 
  public  boolean bEdit;

/////////////
 public boolean bSaved = false;
 
 String m_InArcList[];
 String m_OutArcList[];
 String m_InArcName[];
 String m_OutArcName[];

 public String ms_networkname= new String("untitledNetwork");//NETWORK NAME

 public boolean MethodLearn =true; // True :Learn, False :
 public boolean MethodTest =false; // True :Test, False :

 public String m_sSelectedLearnData=null;
 public String m_sSelectedLearnOutput=null;

  //TEST DATA INOUT SELECTION
  String m_sSelectedTestData=null;
  String m_sSelectedTestOutput=null;
  
  //*******************************
  //PARAMETER SETTING(전략)
  //*******************************
  double m_ErrorCriteria=0.100000;
  int m_EndingEpoch=1000;

  double m_LearningRate=0.500000;
  double m_MomentumRate=0.900000;

  //*******************************
  //PARAMETER SETTING(토폴로지)
  //*******************************
  String [] m_HiddenNode = {"2","2","2"};
  int   m_HiddenNodeNum = 2;
  int   m_HiddenLayerNum = 1;
  //*******************************
  //COLUMN SELECTION
  //*******************************
	Vector InputColumnList   = new  Vector();
	Vector OutputColumnList  = new  Vector();
	Vector InputColumnType   = new  Vector();
	Vector OutputColumnType  = new  Vector();
	Vector InputTransMethod  = new  Vector();
    Vector OutputTransMethod = new  Vector();
//////////

 // Constructors
  public XMMNodeNeuralNetwork(int stat)   ///***********수정
  {
    super(stat);
	bEdit = false;
  }

  public int OnDelete(){
    return (0);
  }

  public int OnCreate(){
    return (0);
  }

  public static XMGraphElement Create(int stat)
  {
    return(new XMMNodeNeuralNetwork(stat));   ///***********수정
  }

  
  public String toString()
  {
    return("Neural network");
  }

  public String GetText()
  {
    return("NN");
  }

  // Operation
  public int OnConnectedFrom(JFrame frame,XMGraph graph, XMGraphElement element)
  { 
    int err;
    if ((err = super.OnConnectedFrom(frame, graph, element)) != 0)
    {
      return(err);
    }

    // chkeck validity of connectin from the 'element'
    if (GetInCount(graph) >= 2)   // maximumn in = 1
    {
      return(-1);
    }
        return (0);
  }

  public int OnConnectedTo(JFrame frame, XMGraph graph,XMGraphElement element)
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

  // Need Implement
  public int OnDisconnectedFrom(JFrame frame, XMGraph graph, XMGraphElement element)
  {
      return(0);
  }

  // Need Implement
  public int OnDisconnectedTo(JFrame frame, XMGraph graph, XMGraphElement element)
  {
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

    return(0);
 }

public void setParameterName(String filename,boolean bCheck,String modelName)
{
    ParameterFileName = filename;
	bEdit = bCheck;
	ms_networkname = modelName;
	return;
}


  //CORBA       ///***************수정**************///////
    //transient xmminer.xmvbj.xmgraph.xmmnode.xmmnodeneuralnetwork.XMBMNodeNeuralNetwork m_sMNodeNeuralNetwork = null;
    transient xmminer.xmserver.xmgraph.xmmnode.xmmnodeneuralnetwork.XMBMNodeNeuralNetwork m_sMNodeNeuralNetwork = null;

    private void SetCORBA(){
      if (m_sMNodeNeuralNetwork == null)
      {
        // Get the manager Id
        // Locate an account manager. Give the full POA name and the servant ID.
        //m_sMNodeNeuralNetwork = xmminer.xmvbj.xmgraph.xmmnode.xmmnodeneuralnetwork.XMBMNodeNeuralNetworkHelper.bind(XMMiner.m_orb,"/xmminer_poa","XMMNodeNeuralNetwork".getBytes());
        m_sMNodeNeuralNetwork = new xmminer.xmserver.xmgraph.xmmnode.xmmnodeneuralnetwork.XMBMNodeNeuralNetwork();
	  }
    }


 public int getInArcList(XMGraph graph)
 { 
   int InputArcNum = 0;
   InputArcNum = GetInCount(graph);
   XMNode xmnode;
   String temp = new String();

   if(InputArcNum<=0)
   {  System.out.println(" 입력 노드가 없습니다.");
	   return -1;
   }
   else
   {
     m_InArcList = new String[InputArcNum];
	 m_InArcName = new String[InputArcNum];
	 for(int i=0;i<InputArcNum;i++)
	 { 
	   temp = "arc"+GetInElement(i,graph).GetUniqueId();
       m_InArcName[i] = new String(temp);  
	   
	   xmnode = (XMNode)GetInElement(i,graph); 
 	   m_InArcList[i] = xmnode.GetName();
	 }
    }      

  return (1);
 }

public int getOutArcList(XMGraph graph)
{  int OutArcNum=0;
   OutArcNum = GetOutCount(graph);
   XMNode xmnode;
   String temp = new String();
   if (OutArcNum<=0)
   {  System.out.println(" 출력 노드가 없습니다.");
     return -1;
   }
    else
   {
     m_OutArcList = new String[OutArcNum];
     m_OutArcName = new String[OutArcNum];
	 for(int i=0;i<OutArcNum;i++)
	 { 
	   temp = "arc"+GetOutElement(i,graph).GetUniqueId();
       m_OutArcName[i] = new String(temp);   
	   xmnode = (XMNode)GetOutElement(i,graph); 
	   m_OutArcList[i] = xmnode.GetName();
	 }
    }
  return (1);
}

 public int Edit(JFrame frame, XMGraph graph)
 {

    XMNode errNode;
    if ((errNode = GetSchema(graph)) != null)
    {
      JOptionPane.showMessageDialog(frame, "Cannot get the schema of " + errNode.GetName() + ".");
      return(XMGESTAT_EDIT_NOCHANGE);
    }

	MetaFileName =  "arc"+GetInElement(0, graph).GetUniqueId();
    ProjectName = graph.GetProjectName();
	m_Projectpath = graph.GetDirectory();
    next_node =  "arc"+GetOutElement(0, graph).GetUniqueId();

	getInArcList(graph);
	getOutArcList(graph);

		SetCORBA();
		XMMNodeNeuralDialog inputdlg = new XMMNodeNeuralDialog(frame,"Neural Network V1.0",true,this);
        inputdlg.setSize(500,460); 
	    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dialogSize = inputdlg.getSize();
		
        if (dialogSize.height > screenSize.height)
           dialogSize.height = screenSize.height;
       if (dialogSize.width > screenSize.width)
          dialogSize.width = screenSize.width;
          inputdlg.setLocation((screenSize.width - dialogSize.width) / 2, (screenSize.height - dialogSize.height) / 2);

  	   inputdlg.show();
       return XMGESTAT_EDIT_CRITICALCHANGE;
  }

  // Run


  transient Thread m_threadRun = null;
  transient Thread m_threadProgress = null;
  JDialog m_dialog;
  int m_pbMin;
  int m_pbMax;

  public JDialog CreateDialog(JFrame frame)
  {
   return(new NeuralProBar(frame, this, "Neural Network Learning...", true));
  }


  public class ThreadProgress implements Runnable {
    public void run(){
    JProgressBar progressBar = ((NeuralProBar)m_dialog).jProgressBar1;

        int x = m_pbMin;
        int prevx = x;
        int gap = 100;
		float preTSS = 0.0f;
		float TSS = 0.0f;

        progressBar.setMinimum(m_pbMin);
        progressBar.setMaximum(m_pbMax);
        progressBar.setValue(x);
        while(IsRunning()){
            x = m_sMNodeNeuralNetwork.GetPBValue();
            progressBar.setValue(x);
			TSS = m_sMNodeNeuralNetwork.GetTSSValue();
			System.out.println("TSS = " + TSS);
			if(TSS != preTSS)
			{ 
			  ((NeuralProBar)m_dialog).setList(TSS);
			   preTSS = TSS;
			}
            try {
                if (x == prevx && gap < 5000) {
                    gap += 100;
                }
                else if (x != prevx && gap > 100) {
                    gap -= 100;
                }
                Thread.sleep(gap);
            }
            catch (InterruptedException e){
            }
            prevx = x;
        }
        m_dialog.dispose();
    }
  }

  //Need Implement
  public class ThreadRun implements Runnable{
    public void run() {
        m_sMNodeNeuralNetwork.Run();
    }
  }

  public boolean IsRunning () {
    return (m_sMNodeNeuralNetwork.IsRunning());
  }

  public void StopRunning(){
    m_sMNodeNeuralNetwork.StopRunning();
  }

  // Need Implement
  public int Run(JFrame frame, XMGraph graph)
  {
	    if (!bEdit)
	    {  System.out.println("파라미터 세팅을 하지 않았습니다.!");
           return 1;  		  
	    }
	    
	    SetCORBA();
        m_sMNodeNeuralNetwork.setParameterFileName(ParameterFileName);
		m_sMNodeNeuralNetwork.setProjectName(ProjectName,next_node);
////////////////////////////파일 전송 끝/////////////////////

	// Dialog

	m_dialog = CreateDialog(frame);//, m_sXNodeDB);
    m_dialog.setSize(400, 150);
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension dialogSize = m_dialog.getSize();
    if (dialogSize.height > screenSize.height)
      dialogSize.height = screenSize.height;
    if (dialogSize.width > screenSize.width)
      dialogSize.width = screenSize.width;
    m_dialog.setLocation((screenSize.width - dialogSize.width) / 2, (screenSize.height - dialogSize.height) / 2);

    // GraphElement Server
    SetCORBA();
    m_pbMin = m_sMNodeNeuralNetwork.GetPBMin();
    m_pbMax = m_sMNodeNeuralNetwork.GetPBMax();

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

	return 0;
  }

}