
//Title:        XM-Miner
//Version:
//Copyright:    Copyright (c) 1999
//Author:       Yong Uk Song
//Company:      DIS
//Description:  XM-Miner

package xmminer.xmclient.xmgraph.xmmnode.xmmnodec45;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.tree.*;

import xmminer.xmlib.xmtable.*;
import xmminer.xmlib.xmcompute.*;
import xmminer.xmlib.*;
import xmminer.xmclient.*;
import xmminer.xmclient.xmlib.*;
import xmminer.xmclient.xmgraph.*;
import xmminer.xmclient.xmgraph.xmmnode.xmmnodec45.*;

public class XMMNodeC45 extends XMMNode implements Serializable
{


  int m_stat;
  protected static final int XMC45_TREE_BEFORE                   = 0x0000;
  protected static final int XMC45_TREE_LEARN                    = 0x0011;
  protected static final int XMC45_TREE_TEST                     = 0x0012;
  protected static final int XMC45_RULE_LEARN                    = 0x0021;
  protected static final int XMC45_RULE_TEST                     = 0x0022;  

  Point	m_pos;
  Dimension m_dim;
  boolean m_bModified;
  
  String m_name;

  Vector m_inArcs;
  Vector m_outArcs;
  
  XMGraph m_cGraph=null;
  int m_iMethodStatus;
  String ms_modelname;
  String m_sProjectName;
  String m_sProjectPass;

  boolean m_bParameterSetting;
  boolean m_bMethodSettingChaneg=true;
  // Constructors
  // Need Inheritance  // qorwrk modify
  public XMMNodeC45(int stat)
  {
    super(stat);
    m_stat = stat;
    m_bModified = false;
    //m_name= new String("untitledTree");
    m_iMethodStatus = XMC45_TREE_BEFORE;
    ms_modelname= new String("untitledModel");
    //
    m_bParameterSetting=false;

  }

  
  public String Getms_modelname()
  {
  	return ms_modelname;
  } 
  
  public void Setms_modelname(String tms_modelname)
  {
  	ms_modelname=new String(tms_modelname);
  } 
  
   
  public void Setm_bMethodSettingChaneg(boolean tm_bModified)
  {
  	m_bMethodSettingChaneg=tm_bModified;
  } 
 
  public int Getm_iMethodStatus()
  {
    if(m_bParameterSetting)
     m_iMethodStatus=m_sXMMNodeC45.Getm_iMethodStatus(m_sProjectName,ms_modelname);
    else
     m_iMethodStatus = XMC45_TREE_BEFORE;
    return  m_iMethodStatus;
  } 
 
  public boolean Getm_bParameterSetting()
  {
  	return m_bParameterSetting;
  } 
  
  public void Setm_bParameterSetting(boolean tm_bParameterSetting)
  {
  	m_bParameterSetting=tm_bParameterSetting;
  }
 
 
 
 
  // Need Implement
  /*public int OnCreate()
  {
    return(0);
  }*/

  // Need Implement
  public int OnDelete()
  {
    return(0);
  }

  // Need Implement // qorwrk modify
  public static XMGraphElement Create(int stat)
  {

    return(new XMMNodeC45(stat));

		    //XMMNodeC45 element;
		    //switch (stat & XMGEFLAG_GRAPHELEMENT)
		    //{
		    //case XMGESTAT_ARC:
		    //  element = XMArc.Create(stat);
		    //  break;
		    //case XMGESTAT_NODE:
		    //  element = XMNode.Create(stat);
		    //  break;
		    //default:
		    //  element = null;
		    //  break;
		    //}
                    //return(element != null && element.OnCreate() == 0 ? element : null);
  }


  // Need Inheritance
  // qorwrk modify
  public String toString()
  {
    return("XMMNodeC45");
  }

  // Operation
  // Need Implement
  



  // Need Implement
  public int OnConnectedFrom(JFrame frame, XMMNodeC45 element)
  {
    return(0);
  }

  // Need Implement
  public int OnConnectedTo(JFrame frame, XMMNodeC45 element)
  {
    return(0);
  }



  // Need Implement
  public int OnDisconnectedFrom(JFrame frame, XMGraphElement element)
  {
    return(0);
  }

  // Need Implement
  public int OnDisconnectedTo(JFrame frame, XMGraphElement element)
  {
    return(0);
  }

  // Paint
  // May Need Implement
  public int Paint(Graphics g)
  {
    return(0);
  }

  // CORBA
  // qorwrk modify
  //transient public xmminer.xmvbj.xmgraph.xmmnode.xmmnodec45.XMBMNodeC45 m_sXMMNodeC45= null;
  transient public xmminer.xmserver.xmgraph.xmmnode.xmmnodec45.XMBMNodeC45 m_sXMMNodeC45= null;


  // Need Implement
  // qorwrk modify
  private void SetCORBA()
  {    
	if (m_sXMMNodeC45 == null)
    {
      // Get the manager Id
      // Locate an account manager. Give the full POA name and the servant ID.
      // m_sXMMNodeC45 = xmminer.xmvbj.xmgraph.xmmnode.xmmnodec45.XMBMNodeC45Helper.bind(XMMiner.m_orb, "/xmminer_poa", "XMMNodeC45".getBytes());
         m_sXMMNodeC45 = new xmminer.xmserver.xmgraph.xmmnode.xmmnodec45.XMBMNodeC45();
         
    }
	
  }

  public String GetText() //qorwrk modify
  {
    /*SetCORBA();
    Getm_iMethodStatus();
    String text=null;
    switch(m_iMethodStatus)
    {
      case XMC45_TREE_BEFORE:
           text=new String("Tree is Not Generated");
           break;
      case XMC45_TREE_LEARN:
           text=new String("Tree is Generated");
           break;
      case XMC45_TREE_PRUNE:
           text=new String("Pruned Tree is Generated");
           break;
      case XMC45_RULE_LEARN:
           text=new String("Rule is Generated");
           break;
      case XMC45_RULE_PRUNE:
           text=new String("Pruned Rule is Generated");
           break;
    }*/
    String text=null;
	text=new String("C45");

    return(text);
  }
  public   String GetProjectNameC45()
  {      
      System.out.println("GetProjectNameC45 m_sProjectName ="+m_sProjectName);
      return m_sProjectName;
  }


  public int SetSchema(XMGraph graph, XMVNode node)
  {
    return(0);
  }


  //int m_iMethodStatus;       //학습 상태를 나타내기위한 변
  
  
  // Edit
  // Need Implement

  public XMGraph GetXMGraph()
  {
    return m_cGraph;
  }

  public int Edit(JFrame frame, XMGraph graph)
  {
    
    XMNode errNode;
    if ((errNode = GetSchema(graph)) != null)
    {
      JOptionPane.showMessageDialog(frame, "Cannot get the schema of " + errNode.GetName() + ".");
      return(XMGESTAT_EDIT_NOCHANGE);
    }
    m_bMethodSettingChaneg=false;
    m_sProjectName =new String(graph.GetProjectName());
	m_sProjectPass =new String(graph.GetDirectory());
    SetCORBA();
    System.out.println("XMGraphElement.Edit_ParameterSetting   " + m_bParameterSetting);
    m_cGraph=graph;
    
    XMMNodeC45DLG m_pardlg =new XMMNodeC45DLG(frame,"Decision Tree - C45 V1.0",false,this);
    m_pardlg.setSize(500,420);
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension dialogSize = m_pardlg.getSize();
    if (dialogSize.height > screenSize.height)
      dialogSize.height = screenSize.height;
    if (dialogSize.width > screenSize.width)
      dialogSize.width = screenSize.width;
    m_pardlg.setLocation((screenSize.width - dialogSize.width) / 2, (screenSize.height - dialogSize.height) / 2);
    m_pardlg.setModal(true);
    
    m_pardlg.show();

	//System.out.println("XMGraphElement.Edit");
    //if(ParameterSetting)
    //{
    //  System.out.println("ParameterSetting");
    //
    //}
    if(m_bMethodSettingChaneg) return XMGESTAT_EDIT_CRITICALCHANGE;
    else  return XMGESTAT_EDIT_NOCHANGE;
  }


  // Run
  transient Thread m_threadRun = null;
  transient Thread m_threadProgress = null;
  JDialog m_dialog;
  int m_pbMin=1;
  int m_pbMax;

  // Need Implement
  public JDialog CreateDialog(JFrame frame)
  {
    System.out.println("Prograss Dialog생성");
    return(new XMDialogGraphElement(frame, this, "C45 진행상황", true));//new XMDialogGraphElement(frame, this, "Graph Element", true)//);
  }

  // Need Implement
  public class ThreadProgress implements Runnable
  {
    public void run()
    {
      JProgressBar progressBar = ((XMDialogGraphElement)m_dialog).jProgressBar1;

      int x = m_pbMin;
      int prevx = x;
      int gap = 100;
	  try
	  {
	     Thread.sleep(1000);
	  }
       catch (InterruptedException e)
	   {
	     System.out.println("프로그래스 바 중 에러 발생");
	   }
      progressBar.setMinimum(m_pbMin);
      progressBar.setMaximum(m_pbMax);
      progressBar.setValue(x);
	  System.out.println("Prograss Dialog생성");
      while (IsRunning())
      {
	    x = m_sXMMNodeC45.GetPBValue();
		System.out.println("X="+x);
		if(x>50) x=50;
		progressBar.setValue(x);
		try
		{
		  if (x == prevx && gap < 5000)
		  {
		     gap += 100;
	   	  }
		  else if (x != prevx && gap > 100)
		  {
		     gap -= 100;
		  }
	      Thread.sleep(gap);
		  System.out.println("System is Running");
	   }//try
	   catch (InterruptedException e)
	   {
	     System.out.println("프로그래스 바 중 에러 발생");
	   }
	   prevx = x;
    }
	  System.out.println("System is Stop");
      m_dialog.dispose();
    }
  }

  // Need Implement
  public class ThreadRun implements Runnable
  {
    public void run()
    {
      m_sXMMNodeC45.Setm_sProjectName(m_sProjectName);
      m_sXMMNodeC45.Setms_modelname(ms_modelname);
      m_sXMMNodeC45.Run();
    }
  }

  // Need Implement
  public boolean IsRunning ()
  {
    boolean mm= m_sXMMNodeC45.IsRunning();
	System.out.println("IsRunning="+mm);
	return(m_sXMMNodeC45.IsRunning());
  }

  // Need Implement
  public void StopRunning ()
  {
    m_sXMMNodeC45.StopRunning();
  }

  // Need Implement
  public int Run(JFrame frame, XMGraph graph)
  {
    
    //File Upload
    System.out.println("FileUp");
    //FileUp(ms_modelname);
    
    
	m_dialog = CreateDialog(frame);//, m_sXNodeDB);
    m_dialog.setSize(400, 120);
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension dialogSize = m_dialog.getSize();
    if (dialogSize.height > screenSize.height)
      dialogSize.height = screenSize.height;
    if (dialogSize.width > screenSize.width)
      dialogSize.width = screenSize.width;
    m_dialog.setLocation((screenSize.width - dialogSize.width) / 2, (screenSize.height - dialogSize.height) / 2);

    // GraphElement Server
    SetCORBA();
    m_pbMin = m_sXMMNodeC45.GetPBMin();
    m_pbMax = m_sXMMNodeC45.GetPBMax();
    System.out.println("m_pbMin="+m_pbMin+" m_pbMax="+m_pbMax);
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

	
    return(0);
  }
	 
  



  public class popupNodeShowTree_actionPerformed implements java.awt.event.ActionListener
  {
    JFrame m_frame;
    XMGraph m_graph;
    XMNode m_node;
    ViewTreeNode[] nodes;
	int counter = 0;

    public popupNodeShowTree_actionPerformed(JFrame frame, XMGraph graph, XMNode node)
    {
      m_frame = frame;
      m_graph = graph;
      m_node = node;
    }

    public void actionPerformed(ActionEvent e){
	  DefaultMutableTreeNode tree = new DefaultMutableTreeNode(m_sProjectName + " : " + ms_modelname);

      Tree_String();
	  Tree_JTree(tree, 0, 0);

      TreeDLG  tree_box = new TreeDLG(m_frame,"Decision Tree : "+m_sProjectName,true, tree);

      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      tree_box.setSize(500,500);
      Dimension frameSize = tree_box.getSize();
      if (frameSize.height > screenSize.height) frameSize.height = screenSize.height;
      if (frameSize.width > screenSize.width) frameSize.width = screenSize.width;
      tree_box.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
      tree_box.setVisible(true);
    }

    private void Tree_String(){
      int m_iTreeSize=0,i=0;
      m_iTreeSize = m_sXMMNodeC45.GetTreeSize();
      nodes = new ViewTreeNode[m_iTreeSize];

      for(i=0; i<m_iTreeSize;i++){
	    nodes[i] = new ViewTreeNode(m_sXMMNodeC45.GetIndentNum(i), m_sXMMNodeC45.GetTreeString(i));
		System.out.println(nodes[i].i+nodes[i].desc);
      }
    }

	private void Tree_JTree(DefaultMutableTreeNode tnode, int recent, int indent){
      ViewTreeNode imsi;
	  for(counter = recent; counter<nodes.length; counter++){
	    DefaultMutableTreeNode rcnode = new DefaultMutableTreeNode(nodes[counter].desc);
		imsi = nodes[counter];
//		System.out.println("\n[loop] counter: " + counter + ", recent: " + recent);
//		System.out.println("[Traverse] indent: " + nodes[counter].i + ", desc: " + nodes[counter].desc);
        if(counter<nodes.length-1&&nodes[counter+1].i > indent){
          Tree_JTree(rcnode, counter+1, nodes[counter+1].i);
        }
        if(counter<nodes.length-1&&nodes[counter+1].i < indent){
          tnode.add(rcnode);
//          System.out.println("[Adding] indent: " + imsi.i + ", desc: " + imsi.desc + "\n");
          return;
        }
        if(counter==nodes.length-1){
          tnode.add(rcnode);
//		  System.out.println("[Adding] indent: " + imsi.i + ", desc: " + imsi.desc + "\n");
		  return;
		}
        tnode.add(rcnode);
//		System.out.println("[Adding] indent: " + imsi.i + ", desc: " + imsi.desc + "\n");
	  }
	}
  }


  public class popupNodeShowRule_actionPerformed implements java.awt.event.ActionListener
  {
    JFrame m_frame;
    XMGraph m_graph;
    XMNode m_node;

    public popupNodeShowRule_actionPerformed(JFrame frame, XMGraph graph, XMNode node)
    {
      m_frame = frame;
      m_graph = graph;
      m_node = node;
    }

    public void actionPerformed(ActionEvent e)
    {

	  String list= "Premise^Consequent^Error";

	  //선택된 attributes를 "^"로 파싱한다.
        StringTokenizer tokened_attributes = new StringTokenizer(list, "^");
        int num_attributes = tokened_attributes.countTokens();
        String[] Attributes = new String[num_attributes];
        int i = 0;
        while(tokened_attributes.hasMoreElements()){
          Attributes[i] = (String)tokened_attributes.nextElement();
          i++;
        }

	 for (i=0; i<Attributes.length;i++ )
         System.out.println("list  = "+Attributes[i]);

	System.out.println(" Project = "+m_sProjectName);
    System.out.println("  model_rule= "+ms_modelname+"_rule");

         XMCORBATable corbaTable;
         try
         {
	        corbaTable = new XMCORBATable(m_sProjectPass, m_sProjectName, ms_modelname+"_rule", GetUniqueId());
         }
         catch (Exception exception)
         {
           corbaTable = null;
         }
    corbaTable.Initialize(Attributes,50);

    XMDialogTable dialog = new XMDialogTable(m_frame,"Data", new XMTableModel(corbaTable),true,m_sProjectPass,m_sProjectName,GetUniqueId());
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      Dimension dialogSize = dialog.getSize();

      if (dialogSize.height > screenSize.height) dialogSize.height = screenSize.height;
      if (dialogSize.width > screenSize.width) dialogSize.width = screenSize.width;
      dialog.setLocation((screenSize.width - dialogSize.width) / 2, (screenSize.height - dialogSize.height) / 2);
	dialog.show();
	corbaTable.Finalize();

    }

  }//end class

public class popupNodeShowConfusion_actionPerformed implements java.awt.event.ActionListener
  {
    JFrame m_frame;
    XMGraph m_graph;
    XMNode m_node;
	int m_iStatus;

    public popupNodeShowConfusion_actionPerformed(JFrame frame, XMGraph graph, XMNode node,int MethodStatus)
    {
      m_frame = frame;
      m_graph = graph;
      m_node = node;
	  m_iStatus=MethodStatus;
    }

    public void actionPerformed(ActionEvent e)
    {

	  SetCORBA();
      switch(m_iStatus)
	  {
      
      case XMC45_TREE_LEARN:
	       m_sXMMNodeC45.Read_setFileStatus(m_sProjectName,ms_modelname+"_TLearnC");
           break;
      case XMC45_TREE_TEST:
           m_sXMMNodeC45.Read_setFileStatus(m_sProjectName,ms_modelname+"_TTestC");
		   break;
         
      case XMC45_RULE_LEARN:
           m_sXMMNodeC45.Read_setFileStatus(m_sProjectName,ms_modelname+"_RLearnC");
		   break;

      case XMC45_RULE_TEST:
           m_sXMMNodeC45.Read_setFileStatus(m_sProjectName,ms_modelname+"_RTestC");
		   break;

    }
	   
	  String list=m_sXMMNodeC45.Read_getProfile("COLUMN_LIST");
	  System.out.println("list="+list);
	  //String list= "Premise^Consequent^Error";

	  //선택된 attributes를 "^"로 파싱한다.
        StringTokenizer tokened_attributes = new StringTokenizer(list, "^");
        int num_attributes = tokened_attributes.countTokens();
        String[] Attributes = new String[num_attributes];
        int i = 0;
        while(tokened_attributes.hasMoreElements()){
          Attributes[i] = (String)tokened_attributes.nextElement();
          i++;
        }

	 for (i=0; i<Attributes.length;i++ )
         System.out.println("Attributes["+i+"]  = "+Attributes[i]);

	    System.out.println(" Project = "+m_sProjectName);
        System.out.println("  model_rule= "+ms_modelname+"_rule");

         XMCORBATable corbaTable;
         try
         {
	        //corbaTable = new XMCORBATable(m_sProjectPass, m_sProjectName, ms_modelname+"_rule", GetUniqueId());
			switch(m_iStatus)
			{
			   case XMC45_TREE_LEARN:
			       corbaTable = new XMCORBATable(m_sProjectPass, m_sProjectName, ms_modelname+"_TLearnC", GetUniqueId());				   
                   break;
			   case XMC45_TREE_TEST:
			       corbaTable = new XMCORBATable(m_sProjectPass, m_sProjectName, ms_modelname+"_TTestC", GetUniqueId());			       
				   break;
			   case XMC45_RULE_LEARN:
			       corbaTable = new XMCORBATable(m_sProjectPass, m_sProjectName, ms_modelname+"_RLearnC", GetUniqueId());				   
				   break;
			   case XMC45_RULE_TEST:
                   corbaTable = new XMCORBATable(m_sProjectPass, m_sProjectName, ms_modelname+"_RTestC", GetUniqueId());				   
		           break;
               default:
			       corbaTable = new XMCORBATable(m_sProjectPass, m_sProjectName, ms_modelname+"_TLearnC", GetUniqueId());				   
                   break;
			       
			}
         }
         catch (Exception exception)
         {
           corbaTable = null;
         }
    corbaTable.Initialize(Attributes,50);

    XMDialogTable dialog = new XMDialogTable(m_frame,"Data", new XMTableModel(corbaTable),true,m_sProjectPass,m_sProjectName,GetUniqueId());
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      Dimension dialogSize = dialog.getSize();

      if (dialogSize.height > screenSize.height) dialogSize.height = screenSize.height;
      if (dialogSize.width > screenSize.width) dialogSize.width = screenSize.width;
      dialog.setLocation((screenSize.width - dialogSize.width) / 2, (screenSize.height - dialogSize.height) / 2);
	dialog.show();
	corbaTable.Finalize();

    }

  }//end class


  // C4.5
  public JPopupMenu CreatePopupMenu(JFrame frame, XMGraph graph)
  {
    JPopupMenu popupNode = new JPopupMenu();
    JMenuItem popupNodeProperties = new JMenuItem();
    JMenuItem popupNodeRename = new JMenuItem();
    JMenuItem popupNodeAddVisual = new JMenuItem();
    JMenuItem popupNodeRunAlone = new JMenuItem();
    JMenuItem popupNodeRun2Here = new JMenuItem();
    JMenuItem popupNodeShowResult = new JMenuItem();
    JMenuItem popupNodeShowLearnConfusionMatrix = new JMenuItem();
	JMenuItem popupNodeShowTestConfusionMatrix = new JMenuItem();

    popupNodeProperties.setMnemonic('P');
    popupNodeProperties.setText("Properties");
    popupNodeProperties.addActionListener(new popupNodeProperties_actionPerformed(frame, graph, this));
    popupNodeRename.setMnemonic('R');
    popupNodeRename.setText("Rename");
    popupNodeRename.addActionListener(new popupNodeRename_actionPerformed(frame, graph, this));
    popupNodeAddVisual.setMnemonic('D');
    popupNodeAddVisual.setText("Add Data");
    popupNodeAddVisual.addActionListener(new popupNodeAddVisual_actionPerformed(frame, graph, this));
    popupNodeRunAlone.setMnemonic('A');
    popupNodeRunAlone.setText("Run alone");
    popupNodeRunAlone.addActionListener(new popupNodeRunAlone_actionPerformed(frame, graph, this));
    popupNodeRun2Here.setMnemonic('H');
    popupNodeRun2Here.setText("Run to here");
    popupNodeRun2Here.addActionListener(new popupNodeRun2Here_actionPerformed(frame, graph, this));
    
    SetCORBA();
    Getm_iMethodStatus();
	
	switch(m_iMethodStatus)
    {
      case XMC45_TREE_BEFORE:
           break;
      case XMC45_TREE_LEARN:
           popupNodeShowResult.setMnemonic('S');
           popupNodeShowResult.setText("Show Tree");
		   popupNodeShowResult.addActionListener(new popupNodeShowTree_actionPerformed(frame, graph, this));
		   
		   popupNodeShowLearnConfusionMatrix.setMnemonic('L');
           popupNodeShowLearnConfusionMatrix.setText("Result Learn");
		   popupNodeShowLearnConfusionMatrix.addActionListener(new popupNodeShowConfusion_actionPerformed(frame, graph, this,XMC45_TREE_LEARN));		   
           break;
      case XMC45_TREE_TEST:
           popupNodeShowResult.setMnemonic('S');
           popupNodeShowResult.setText("Show Tree");
		   popupNodeShowResult.addActionListener(new popupNodeShowTree_actionPerformed(frame, graph, this));
		   
		   popupNodeShowLearnConfusionMatrix.setMnemonic('L');
           popupNodeShowLearnConfusionMatrix.setText("Result Learn");
		   popupNodeShowLearnConfusionMatrix.addActionListener(new popupNodeShowConfusion_actionPerformed(frame, graph, this,XMC45_TREE_LEARN));		   
		  
		   popupNodeShowTestConfusionMatrix.setMnemonic('L');
           popupNodeShowTestConfusionMatrix.setText("Result Test");
		   popupNodeShowTestConfusionMatrix.addActionListener(new popupNodeShowConfusion_actionPerformed(frame, graph, this,XMC45_TREE_TEST));		   
           break;
         
      case XMC45_RULE_LEARN:
           popupNodeShowResult.setMnemonic('S');
           popupNodeShowResult.setText("Show Rule");
		   popupNodeShowResult.addActionListener(new popupNodeShowRule_actionPerformed(frame, graph, this));
		   
		   popupNodeShowLearnConfusionMatrix.setMnemonic('L');
           popupNodeShowLearnConfusionMatrix.setText("Result Learn");
		   popupNodeShowLearnConfusionMatrix.addActionListener(new popupNodeShowConfusion_actionPerformed(frame, graph, this,XMC45_RULE_LEARN));		   
           break;

      case XMC45_RULE_TEST:
           popupNodeShowResult.setMnemonic('S');
           popupNodeShowResult.setText("Show Rule");
		   popupNodeShowResult.addActionListener(new popupNodeShowRule_actionPerformed(frame, graph, this));
		   
		   popupNodeShowLearnConfusionMatrix.setMnemonic('L');
           popupNodeShowLearnConfusionMatrix.setText("Result Learn");
		   popupNodeShowLearnConfusionMatrix.addActionListener(new popupNodeShowConfusion_actionPerformed(frame, graph, this,XMC45_RULE_LEARN));		   
		  
		   popupNodeShowTestConfusionMatrix.setMnemonic('L');
           popupNodeShowTestConfusionMatrix.setText("Result Test");
		   popupNodeShowTestConfusionMatrix.addActionListener(new popupNodeShowConfusion_actionPerformed(frame, graph, this,XMC45_RULE_TEST));		   
           break;

    }
    popupNode.add(popupNodeProperties);
    popupNode.add(popupNodeRename);
    popupNode.addSeparator();
    popupNode.add(popupNodeAddVisual);
    popupNode.addSeparator();
    popupNode.add(popupNodeRunAlone);
    popupNode.add(popupNodeRun2Here);
    
	switch(m_iMethodStatus)
    {
      case XMC45_TREE_BEFORE:
           break;
      case XMC45_TREE_LEARN:
           popupNode.addSeparator();
           popupNode.add(popupNodeShowResult);
		   popupNode.add(popupNodeShowLearnConfusionMatrix);

           break;
      case XMC45_TREE_TEST:
	       popupNode.addSeparator();
           popupNode.add(popupNodeShowResult);
		   popupNode.add(popupNodeShowLearnConfusionMatrix);
		   popupNode.add(popupNodeShowTestConfusionMatrix);
           break;

      case XMC45_RULE_LEARN:
           popupNode.addSeparator();
           popupNode.add(popupNodeShowResult);
		   popupNode.add(popupNodeShowLearnConfusionMatrix);
           break;
  	  case XMC45_RULE_TEST:
	       popupNode.addSeparator();
           popupNode.add(popupNodeShowResult);
		   popupNode.add(popupNodeShowLearnConfusionMatrix);
		   popupNode.add(popupNodeShowTestConfusionMatrix);
           break;
    }
    return(popupNode);
  }
  
  private class ViewTreeNode{
    public int i;
	public String desc;
	public ViewTreeNode(int ri, String rdesc){
	  i = ri;
	  desc = rdesc;
	}
  }

////////////////////////////////////////////////////////////////////////////////////////////////
//End of class
////////////////////////////////////////////////////////////////////////////////////////////////
}
