package xmminer.xmclient.xmgraph.xmmnode.xmmnodesequencerule;

import javax.swing.*;
import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.*;

import xmminer.*;
import xmminer.xmlib.*;
import xmminer.xmclient.*;
import xmminer.xmclient.xmlib.*;
import xmminer.xmclient.xmgraph.*;
import xmminer.xmclient.xmgraph.xmmnode.*;
import xmminer.xmclient.xmgraph.xmmnode.xmmnodesequencerule.*;

public class XMMNodeSequenceRule extends XMMNode implements Serializable{
  boolean IsExistFile;   //이전에 파라미터 세팅을 했는지에 대한 정보
  public String IsExistRule = "false";
  public String next_node;
  public String ParameterFileName="" ;
  public boolean bEdit;

  public String ProjectName = "";
  public String ProjectPath = "";
  public String MetaFileName;
  public String model = "model01";
  public int transNumber = -1;    //transaction 수

  public float min_sup = 20f;
  public float min_conf = 60f;
  public int min_sup_type = 0;   //min_conf_type

  public String transIDFld = new String("");
  public String targetFld = new String("");
  public String timeFld = new String("");

  public XMMNodeSequenceRule(int stat){
    super(stat);
    IsExistFile = false;
	bEdit = false;
  }

  public int OnDelete(){
    return (0);
  }

  public static XMGraphElement Create(int stat){
    return(new XMMNodeSequenceRule(stat));
  }

  public String toString(){
    return("XMMNodeSequenceRule");
  }

  public String GetText(){
    return("Sequence");
  }

  // Operation
  public int OnConnectedFrom(JFrame frame,XMGraph graph, XMGraphElement element){
    return (0);
  }

  public int OnConnectedTo(JFrame frame, XMGraph graph,XMGraphElement element){
    return (0);
  }

  // Need Implement
  public int OnDisconnectedFrom(JFrame frame, XMGraph graph, XMGraphElement element){
    return(0);
  }

  // Need Implement
  public int OnDisconnectedTo(JFrame frame, XMGraph graph, XMGraphElement element){
    return(0);
  }

  // Paint

  public int SetSchema(XMGraph graph){
    try{
	  next_node =  "arc"+GetOutElement(0, graph).GetUniqueId();
	  ProjectName = graph.GetProjectName();
      return(0);
    }catch(NullPointerException e){
      return(1);
   	}
  }


  public void setParameterName(String filename,boolean bCheck,String modelName){
    ParameterFileName = filename;
    bEdit = bCheck;
	model = modelName;
	return;
  }

  //CORBA
  //transient xmminer.xmvbj.xmgraph.xmmnode.xmmnodesequencerule.XMBMNodeSequenceRule m_sMNodeSequence = null;
  transient xmminer.xmserver.xmgraph.xmmnode.xmmnodesequencerule.XMBMNodeSequenceRule m_sMNodeSequence = null;

  private void SetCORBA(){
    if(m_sMNodeSequence == null) {
      //Get the manager Id
      //Locate an account manager. Give the full POA name and the servant ID.
      //m_sMNodeSequence = xmminer.xmvbj.xmgraph.xmmnode.xmmnodesequencerule.XMBMNodeSequenceRuleHelper.bind(XMMiner.m_orb,"/xmminer_poa","XMMNodeSequence".getBytes());
      m_sMNodeSequence = new xmminer.xmserver.xmgraph.xmmnode.xmmnodesequencerule.XMBMNodeSequenceRule();
    }
  }

  public int Edit(JFrame frame, XMGraph graph){
    XMNode errNode;
    if ((errNode = GetSchema(graph)) != null){
      JOptionPane.showMessageDialog(frame, "Cannot get the schema of " + errNode.GetName() + ".");
      return(XMGESTAT_EDIT_NOCHANGE);
    }

    MetaFileName =  "arc"+GetInElement(0, graph).GetUniqueId();
    ProjectName = graph.GetProjectName();
    ProjectPath = graph.GetDirectory();

	boolean bSaved = false;
	if(IsExistFile) bSaved = true;

    SetCORBA();
    seqInputDialog inputdlg = new seqInputDialog(frame,"Sequence Rule V1.0",true,this);
    inputdlg.setSize(500,420);
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension dialogSize = inputdlg.getSize();

    if (dialogSize.height > screenSize.height) dialogSize.height = screenSize.height;
    if (dialogSize.width > screenSize.width) dialogSize.width = screenSize.width;
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

  public JDialog CreateDialog(JFrame frame){
    return(new XMDialogGraphElement(frame, this, "Sequence Rule Learning", true));
  }

  public class ThreadProgress implements Runnable{
    public void run(){
      JProgressBar progressBar = ((XMDialogGraphElement)m_dialog).jProgressBar1;

      int x = m_pbMin;
      int prevx = x;
      int gap = 100;
      progressBar.setMinimum(m_pbMin);
      progressBar.setMaximum(m_pbMax);
      progressBar.setValue(x);
      while(IsRunning()){
        x = m_sMNodeSequence.GetPBValue();
        progressBar.setValue(x);
        try{
          if(x == prevx && gap < 5000) gap += 100;
          else if (x != prevx && gap > 100) gap -= 100;
          Thread.sleep(gap);
        }catch(InterruptedException e){
        }
        prevx = x;
      }
      m_dialog.dispose();
    }
  }

  //Need Implement
  public class ThreadRun implements Runnable{
    public void run(){
      m_sMNodeSequence.Run();
    }
  }

  public boolean IsRunning () {
    return(m_sMNodeSequence.IsRunning());
  }

  public void StopRunning(){
    m_sMNodeSequence.StopRunning();
  }

  // Need Implement
  public int Run(JFrame frame, XMGraph graph){
    if(!bEdit) return(1);

    SetCORBA();

    m_sMNodeSequence.setParameterFileName(ParameterFileName);
 	m_sMNodeSequence.setProjectName(ProjectName, model+"_seq_rule");

    m_dialog = CreateDialog(frame);
    m_dialog.setSize(400, 120);
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension dialogSize = m_dialog.getSize();
    if(dialogSize.height > screenSize.height) dialogSize.height = screenSize.height;
    if(dialogSize.width > screenSize.width) dialogSize.width = screenSize.width;
    m_dialog.setLocation((screenSize.width - dialogSize.width) / 2, (screenSize.height - dialogSize.height) / 2);

    // GraphElement Server
    SetCORBA();
    m_pbMin = m_sMNodeSequence.GetPBMin();
    m_pbMax = m_sMNodeSequence.GetPBMax();

    // Thread
    if(m_threadRun == null || !m_threadRun.isAlive()){
      m_threadRun = new Thread(new ThreadRun());
      m_threadRun.start();
    }
    if (m_threadProgress == null || !m_threadProgress.isAlive()){
      m_threadProgress = new Thread(new ThreadProgress());
      m_threadProgress.start();
    }

    m_dialog.show();

    transNumber = m_sMNodeSequence.getTransNumber(); //frolle

    return(0);
  }

  public void getIsRuleComplete(String _project ,String _parameterName){
    IsExistRule = m_sMNodeSequence.getIsRuleComplete(_project, _parameterName);
    return;
  }
  
  public class popupNodeShowRule_actionPerformed implements java.awt.event.ActionListener{
    JFrame m_frame;
    XMGraph m_graph;
    XMNode m_node;

    public popupNodeShowRule_actionPerformed(JFrame frame, XMGraph graph, XMNode node){
      m_frame = frame;
      m_graph = graph;
      m_node = node;
    }

    public void actionPerformed(ActionEvent e){
 	  String list= "premise^consequent^support_num^support_per^confidence";

	  //선택된 attributes를 "^"로 파싱한다.
      StringTokenizer tokened_attributes = new StringTokenizer(list, "^");
      int num_attributes = tokened_attributes.countTokens();
      String[] Attributes = new String[num_attributes];
      int i = 0;
      while(tokened_attributes.hasMoreElements()){
        Attributes[i] = (String)tokened_attributes.nextElement();
        i++;
      }

      XMCORBATable corbaTable;

 	  try{
 	    corbaTable = new XMCORBATable(ProjectPath, ProjectName, model+"_seq_rule", GetUniqueId());
      }catch(Exception exception){
        corbaTable = null;
      }
      corbaTable.Initialize(Attributes,50);

      XMDialogTable dialog = new XMDialogTable(m_frame,"Data", new XMTableModel(corbaTable),true,ProjectPath,ProjectName,GetUniqueId());
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      Dimension dialogSize = dialog.getSize();

      if (dialogSize.height > screenSize.height) dialogSize.height = screenSize.height;
      if (dialogSize.width > screenSize.width) dialogSize.width = screenSize.width;
      dialog.setLocation((screenSize.width - dialogSize.width) / 2, (screenSize.height - dialogSize.height) / 2);
      dialog.show();
      corbaTable.Finalize();
      ((XMMinerFrame)m_frame).Invalidate();
    }
  }//end class
 
  public JPopupMenu CreatePopupMenu(JFrame frame, XMGraph graph){
    JPopupMenu popupNode = new JPopupMenu();
    JMenuItem popupNodeProperties = new JMenuItem();
    JMenuItem popupNodeRename = new JMenuItem();
    JMenuItem popupNodeAddVisual = new JMenuItem();
    JMenuItem popupNodeRunAlone = new JMenuItem();
    JMenuItem popupNodeRun2Here = new JMenuItem();
    JMenuItem popupNodeShowResult = new JMenuItem();

    popupNodeProperties.setMnemonic('P');
    popupNodeProperties.setText("Properties");
    popupNodeProperties.addActionListener(new popupNodeProperties_actionPerformed(frame, graph, this));

    popupNodeRename.setMnemonic('R');
    popupNodeRename.setText("Rename");
    popupNodeRename.addActionListener(new popupNodeRename_actionPerformed(frame, graph, this));

    popupNodeRunAlone.setMnemonic('A');
    popupNodeRunAlone.setText("Run alone");
    popupNodeRunAlone.addActionListener(new popupNodeRunAlone_actionPerformed(frame, graph, this));
    popupNodeRun2Here.setMnemonic('H');
    popupNodeRun2Here.setText("Run to here");
    popupNodeRun2Here.addActionListener(new popupNodeRun2Here_actionPerformed(frame, graph, this));

    SetCORBA();
    getIsRuleComplete(ProjectName,ParameterFileName);
    if(IsExistRule.equals("true")){
      popupNodeShowResult.setMnemonic('S');
      popupNodeShowResult.setText("Show Rule");
	    popupNodeShowResult.addActionListener(new popupNodeShowRule_actionPerformed(frame, graph, this));
    }
    popupNode.add(popupNodeProperties);
    popupNode.add(popupNodeRename);
    popupNode.addSeparator();
    popupNode.add(popupNodeRunAlone);
    popupNode.add(popupNodeRun2Here);
    if(IsExistRule.equals("true")){
      popupNode.addSeparator();
      popupNode.add(popupNodeShowResult);
    }
    return(popupNode);
  }

}