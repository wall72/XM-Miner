package xmminer.xmclient.xmgraph.xmxnode.xmxnodefile;

import javax.swing.*;
import java.io.*;
import java.awt.*;
import java.awt.Point;

import xmminer.xmclient.*;
import xmminer.xmclient.xmgraph.*;
import xmminer.xmclient.xmgraph.xmxnode.xmxnodefile.*;

import xmminer.xmclient.xmgraph.xmxnode.xmxnodefile.corbautil.*;
import xmminer.xmclient.xmgraph.xmxnode.xmxnodefile.xmscrutil.*;
import xmminer.xmclient.xmgraph.xmxnode.xmxnodefile.filexscr.*;
import xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.*;

public class XMXNodeFile extends XMXNode implements Serializable, Runnable
{
  CorbaAgent agent = null;
  XMXNodeFileInfo info = null;

  // Constructors
  public XMXNodeFile(int stat)
  {
    super(stat);
    System.out.println("create XMXNodeFile .....");
    init();
  }
  private void init()
  {
    info = new XMXNodeFileInfo();
    String[] s = { "" };
    info.srcdata = new FilePath(s, "");
    info.ignoreLinePointer = -1;
    info.columnNameLinePointer = -1;
    info.hdrdata = new FilePath(s, "");
    info.delimiter = ",";
    info.nullvalue = "";
    info.datePattern = "yyyy-MM-dd";
    info.numberOfColumns = -1;
    info.columnInfo = new ColumnInfoType[1];
    info.columnInfo[0] = new ColumnInfoType("", "STRING", false);
    info.enumList = new EnumerationType[1];
    info.enumList[0] = new EnumerationType();
    info.enumList[0].key = "";
    info.enumList[0].values = s;
  }

  public static XMGraphElement Create(int stat)
  {
    System.out.println("Create[XMXNodeFile] is called .....");
    return(new XMXNodeFile(stat));
  }

  // Serialize
  private void writeObject(ObjectOutputStream out) throws IOException
  {
    	out.writeObject(info);
  }

  private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
  {
    	info = (XMXNodeFileInfo)in.readObject();
  }

  public String toString()
  {
    return(super.toString() + ".XMXNodeFile");
  }

  public String GetText()
  {
  	return("File Extract");
  }

  // Operation
  public int OnConnectedFrom(XMGraphElement element)
  {
  	return -1;
  }

  public int OnConnectedTo(XMGraphElement element)
  {
  	return 0;
  }

  // Paint

  //xmminer.xmvbj.xmgraph.xmxnode.xmxnodefile.XMBXNodeFile m_sXNodeFile = null;
  transient xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.XMBXNodeFile m_sXNodeFile = null;

  // CORBA
  private void SetCORBA() throws org.omg.CORBA.OBJECT_NOT_EXIST
  {
  	 	try {
  	 		if (m_sXNodeFile == null) {
   				//m_sXNodeFile = XMBXNodeFileHelper.bind(XMMiner.m_orb, "/xmminer_poa", "XMXNodeFile".getBytes());
   				m_sXNodeFile = new xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.XMBXNodeFile();
			}
  		} catch (org.omg.CORBA.OBJECT_NOT_EXIST e) {
  				throw e;
  		}
  }// end of SetCorba

  private void save(String filename)
  {
      try {
          FileOutputStream fout = new FileOutputStream(filename);
          ObjectOutputStream out = new ObjectOutputStream(fout);
          out.writeObject(info);
          out.close();
      } catch (Exception e) {}
  }
  private void restore(String filename)
  {
      try {
          FileInputStream fin = new FileInputStream(filename);
          ObjectInputStream in = new ObjectInputStream(fin);
          info = (XMXNodeFileInfo)in.readObject();
          in.close();
      } catch (Exception e) {}
  }
  private void delete(String filename)
  {
      try {
          File file = new File(filename);
          file.delete();
      } catch (Exception e) {  }
  }//// end of delete

  // Set Schema
  public int SetSchema(XMGraph graph)
  {
    XMVNode outNode;
    if((outNode = (XMVNode)GetOutElement(0, graph)) == null) return XMGESTAT_RUN_ERROR;
   		try {
   				int outputID = GetOutElement(0, graph).GetUniqueId();
   				m_sXNodeFile.createMetafile(outputID);
		} catch (NullPointerException ne) {
				CXMDialog.errorDialog(new JPanel(), "출력할 노드가 없습니다.");
				return(1);
		}
    	return(0);
  }

  // Edit
  public int Edit(JFrame frame, XMGraph graph)
  {
  		try {
    		System.out.println("Edit is called....");
    		SetCORBA();

    		String str[] = {System.getProperty("minerdir") + "/xmminer/user/"};//"/XM-Miner/xmminer/user"); 수정 2002.09.22
    		m_sXNodeFile.setProjectInfo(new FilePath(str, graph.GetProjectName()));
    		agent = new CorbaAgent(m_sXNodeFile, graph.GetProjectName());
    		System.out.println("corbaAgent create is OK");

    		save("xmxnodefile.tmp");
    		agent.setAllInfo(info);

    		InputFrame frame1 = new InputFrame();
    		ModifyDialog edit = new ModifyDialog(frame, frame1, "File Extractor V1.0", true, agent, info, new Point(50,50));
   
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Dimension dialogSize = edit.getSize();
            if (dialogSize.height > screenSize.height) dialogSize.height = screenSize.height;
            if (dialogSize.width > screenSize.width) dialogSize.width = screenSize.width;
            edit.setLocation((screenSize.width - dialogSize.width) / 2, (screenSize.height - dialogSize.height) / 2);
            edit.setVisible(true);

    		String s = (String)frame1.inputValues.get(0);
    		if (s.equals("OK")) {
        			info = (XMXNodeFileInfo)frame1.inputValues.get(1);
        			delete("xmxnodefile.tmp");
    		                SetSchema(graph);
    		                m_sXNodeFile.disconnect();
    		                return XMGESTAT_EDIT_CRITICALCHANGE;
    		} else if (s.equals("CANCEL")) {
    				restore("xmxnodefile.tmp");
//    		                SetSchema(graph);
    		                m_sXNodeFile.disconnect();
                            return XMGESTAT_EDIT_NOCHANGE;
    		}
            return XMGESTAT_EDIT_NOCHANGE;
    	} catch (org.omg.CORBA.OBJECT_NOT_EXIST e) {
    		CXMDialog.errorDialog(new JPanel(), "서버객체를 찾을 수 없습니다.\n접근 환경을 확인해주십시오.");
    		return XMGESTAT_EDIT_NOCHANGE;
    	}
  }

  public int Run(JFrame frame, XMGraph graph)
  {
  		try {
    			SetCORBA();
    		String str[] = {System.getProperty("minerdir") + "/xmminer/user/"};//"/XM-Miner/xmminer/user"); 수정 2002.09.22
    		m_sXNodeFile.setProjectInfo(new FilePath(str, graph.GetProjectName()));

    			agent = new CorbaAgent(m_sXNodeFile, graph.GetProjectName());
    			//System.out.println("corbaAgent create is OK");

    			agent.setAllInfo(info);

    			System.out.println("Perform Running ....");
    			dialog = (XMDialogXNodeFile)CreateDialog(frame);
    			try {
    				int outputID = GetOutElement(0, graph).GetUniqueId();
    				agent.extract(outputID);
    				System.out.println("extract ....");
			//    if (thread == null) thread = new Thread(this);
    				thread = new Thread(this);
    				thread.start();

                    dialog.setSize(400, 120);
                    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                    Dimension dialogSize = dialog.getSize();
                    if (dialogSize.height > screenSize.height) dialogSize.height = screenSize.height;
                    if (dialogSize.width > screenSize.width) dialogSize.width = screenSize.width;
                    dialog.setLocation((screenSize.width - dialogSize.width) / 2, (screenSize.height - dialogSize.height) / 2);
                    dialog.setVisible(true);
				} catch (NullPointerException ne) {
					CXMDialog.errorDialog(new JPanel(), "출력할 노드가 없습니다.");
					return 1;
				}
    			m_sXNodeFile.disconnect();

    			return(0);
    	} catch (org.omg.CORBA.OBJECT_NOT_EXIST e) {
    		CXMDialog.errorDialog(new JPanel(), "서버객체를 찾을 수 없습니다.\n접근 환경을 확인해주십시오.");
    		return 1;
    	}

  }// end of Run



  // Run
  public JDialog CreateDialog(JFrame frame)
  {
    return (JDialog)new XMDialogXNodeFile(frame, this, "File Extracting...", true);
    //return(null);
  }

  Thread thread = null;
  XMDialogXNodeFile dialog = null;
  public void start()
  {
      if (thread == null) {
          thread = new Thread(this);
          thread.start();
      }
  }

  public void run()
  {
    JProgressBar progressBar = dialog.jProgressBar1;

    int min = 0;
    int max = 100;
    progressBar.setMinimum(min);
    progressBar.setMaximum(max);
    progressBar.setValue(min);

    try {
      System.out.println("Processed Rate ....");
      int rate = 0;
      stopRequseted = false;
      while ( 	((rate = agent.getRate()) < 95) && (!stopRequseted) ) {
          progressBar.setValue(rate);
//          System.out.print(rate + " ");
          thread.sleep(2000);
      }
      dialog.quit();
      System.out.println("Running end....");
    } catch (InterruptedException e) {
        System.out.println(e.getMessage());
    }
  }

  private boolean stopRequseted=false;

  public void requestStop()
  {
  		stopRequseted = true;
  }

  public void StopRunning()
  {
    System.out.println("Stop Running ...");
    requestStop();
    thread.interrupt();
    agent.stop();
  }// end of StopRunning


}// end of Class
