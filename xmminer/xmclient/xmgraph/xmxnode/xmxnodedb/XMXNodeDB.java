package xmminer.xmclient.xmgraph.xmxnode.xmxnodedb;

import javax.swing.*;
import java.io.*;

import java.awt.*;
import java.awt.event.*;

import xmminer.*;
import xmminer.xmlib.*;
import xmminer.xmclient.*;
import xmminer.xmclient.xmgraph.*;
import xmminer.xmclient.xmgraph.xmxnode.xmxnodedb.*;
import xmminer.xmserver.xmgraph.xmxnode.xmxnodedb.*;

public class XMXNodeDB extends XMXNode implements Serializable{

  //start member variables declaration
  JFrame frame;
  XMDialogXNodeDB m_XMDialogXNodeDB;
  transient Thread m_threadRun = null;
  transient Thread m_threadProgress = null;
  JDialog m_dialog;
  int m_pbMin;
  int m_pbMax;
  int m_index;
  boolean m_Changed = false;
  int m_RunCheck = 1;
  String m_projectname;
  String m_projectpath;

  //end of member variables declaration

  // Constructor
  public XMXNodeDB(int stat){
    super(stat);
  }

  public int OnCreate(){
    return (0);
  }

  public int OnDelete(){
    return (0);
  }

  public static XMGraphElement Create(int stat){
    return(new XMXNodeDB(stat));
  }

  public String toString(){
    return(super.toString() + ".XMXNodeDB");
  }

  public String GetText(){
    return("DB Extract");
  }

  // Operation
  public int OnConnectedFrom(JFrame frame, XMGraph graph, XMGraphElement element){
    return(-1);
  }

  public int OnConnectedTo(JFrame frame, XMGraph graph, XMGraphElement element){
    return(0);
  }

  // Need Implement
  public int OnDisconnectedFrom(JFrame frame, XMGraph graph, XMGraphElement element){
    return(0);
  }

  // Need Implement
  public int OnDisconnectedTo(JFrame frame, XMGraph graph, XMGraphElement element){
    return(0);
  }

  // Serialize
  private void writeObject(ObjectOutputStream out) throws IOException{}

  private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException{}

  //CORBA
  //transient xmminer.xmvbj.xmgraph.xmxnode.xmxnodedb.XMBXNodeDB m_sXNodeDB = null;
  transient xmminer.xmserver.xmgraph.xmxnode.xmxnodedb.XMBXNodeDB m_sXNodeDB = null;

  private void SetCORBA(){
    if(m_sXNodeDB == null) {
      //Get the manager Id
      //Locate an account manager. Give the full POA name and the servant ID.
      //m_sXNodeDB = xmminer.xmvbj.xmgraph.xmxnode.xmxnodedb.XMBXNodeDBHelper.bind(XMMiner.m_orb,"/xmminer_poa","XMXNodeDB".getBytes());
      m_sXNodeDB = new xmminer.xmserver.xmgraph.xmxnode.xmxnodedb.XMBXNodeDB();
    }
  }

  public boolean GetChanged(){
    return m_Changed;
  }

  public void SetChanged(boolean b){
    m_Changed = b;
  }

  public int Edit(JFrame frame, XMGraph graph){
    SetCORBA();

	frame = frame;

    try{
      m_index = GetOutElement(0, graph).GetUniqueId();
      m_projectname = graph.GetProjectName();
      m_projectpath = graph.GetDirectory();
      DBExt_Client_Module dcm = new DBExt_Client_Module(frame, m_sXNodeDB);
      dcm.startDialog(m_projectname);
      if((dcm.check()) == false ) return(1);
      m_XMDialogXNodeDB = new XMDialogXNodeDB(frame, "DB Extractor V0.9", true, dcm, this, graph);
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      Dimension frameSize = m_XMDialogXNodeDB.getSize();
      if (frameSize.height > screenSize.height) frameSize.height = screenSize.height;
      if (frameSize.width > screenSize.width) frameSize.width = screenSize.width;
      m_XMDialogXNodeDB.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
      m_XMDialogXNodeDB.setVisible(true);
	  if(GetChanged() == true) return XMGESTAT_EDIT_CRITICALCHANGE;
	  else return XMGESTAT_EDIT_NOCHANGE;
    }catch(NullPointerException ne){
      JOptionPane.showMessageDialog(frame,"출력할 Data Node가 없습니다!");
      return XMGESTAT_EDIT_NOCHANGE;
    }
  }

  public int SetSchema(XMGraph graph){
    XMVNode outNode;
    if((outNode = (XMVNode)GetOutElement(0, graph)) == null) return(XMGraphElement.XMGESTAT_RUN_ERROR);
    try{
      if(GetChanged()==true){
        m_index = GetOutElement(0, graph).GetUniqueId();
        m_projectname = graph.GetProjectName();
        m_projectpath = graph.GetDirectory();
        m_XMDialogXNodeDB.SetSchema();
        return(0);
      }else{
        return(1);
      }
    }catch(NullPointerException ne){
      return(1);
    }
  }

  public JDialog CreateDialog(JFrame frame){
    return(new XNodeDBDialogPrgBar(frame, this, "DB Extracting...", true));
  }


  public class ThreadProgress implements Runnable {
    public void run(){
      JProgressBar progressBar = ((XNodeDBDialogPrgBar)m_dialog).jProgressBar1;
      JLabel lab = ((XNodeDBDialogPrgBar)m_dialog).jLabel2;

      int x = m_pbMin;
      int prevx = x;
      int gap = 100;
      progressBar.setMinimum(m_pbMin);
      progressBar.setMaximum(m_pbMax);
      progressBar.setValue(x);
      String Max = int2Str(m_pbMax);
      String xStr = "0";
      while(IsRunning()){
        x = m_sXNodeDB.GetPBValue();
        progressBar.setValue(x);
        xStr = int2Str(x);
        lab.setText("("+xStr+"/"+Max+")")  ;

        try{
          if(x == prevx && gap < 5000) gap += 100;
          else if (x != prevx && gap > 100) gap -= 100;
          Thread.sleep(gap);
        }catch (InterruptedException e){}

        prevx = x;
      }
      m_dialog.dispose();
    }

    private String int2Str(int i){
      Integer ii = new Integer(i);
      return ii.toString();
    }
  }

  //Need Implement
  public class ThreadRun implements Runnable{
    public void run() {
      m_RunCheck = m_sXNodeDB.Run(m_index, m_projectpath, m_projectname);
    }
  }

  public boolean IsRunning () {
    return (m_sXNodeDB.IsRunning());
  }

  public void StopRunning(){
    m_sXNodeDB.StopRunning();
  }

  // Need Implement
  public int Run(JFrame frame, XMGraph graph){
    SetCORBA();

    try{
      m_index = GetOutElement(0, graph).GetUniqueId();
      m_projectname = graph.GetProjectName();
      m_projectpath = graph.GetDirectory();
      DBExt_Client_Module dcm = new DBExt_Client_Module(frame, m_sXNodeDB);
      String[] val = dcm.XnodeDBMetaDataReads("Login_info", m_projectname);
      dcm.dbConnect(val[0],val[1],val[2],val[3]);
      m_dialog = CreateDialog(frame);
      m_dialog.setSize(400, 120);
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      Dimension dialogSize = m_dialog.getSize();
      if (dialogSize.height > screenSize.height) dialogSize.height = screenSize.height;
      if (dialogSize.width > screenSize.width) dialogSize.width = screenSize.width;
      m_dialog.setLocation((screenSize.width - dialogSize.width) / 2, (screenSize.height - dialogSize.height) / 2);

      // GraphElement Server
      m_pbMin = m_sXNodeDB.GetPBMin();
      m_pbMax = m_sXNodeDB.GetPBMax(m_index, m_projectname);

      // Thread
      if (m_threadRun == null || !m_threadRun.isAlive()){
        m_threadRun = new Thread(new ThreadRun());
        m_threadRun.start();
      }

      if (m_threadProgress == null || !m_threadProgress.isAlive()){
        m_threadProgress = new Thread(new ThreadProgress());
        m_threadProgress.start();
      }

      m_dialog.show();
      SetChanged(false);
    }catch(NullPointerException ne){
      JOptionPane.showMessageDialog(frame,"출력할 Data Node가 없습니다!");
      return(0);
    }catch(Exception e333){
	  e333.printStackTrace();
      JOptionPane.showMessageDialog(frame,"Edit를 먼저 하세요!");
      return(-1);
    }

    return(m_RunCheck);
  }
}