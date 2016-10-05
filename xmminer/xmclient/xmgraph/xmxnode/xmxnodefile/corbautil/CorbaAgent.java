package xmminer.xmclient.xmgraph.xmxnode.xmxnodefile.corbautil;

import xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.*;
import xmminer.xmclient.xmgraph.xmxnode.xmxnodefile.xmscrutil.*;

import javax.swing.JPanel;

public class CorbaAgent {

  public XMBXNodeFile bxnodefile = null;
  public String prjname = null;

  public CorbaAgent(XMBXNodeFile xnodefile, String name)
  {
    bxnodefile = xnodefile;
    prjname = name;
  }

  public void extract(int id)
  {
      System.out.println("extract [ coabaagent ] is called");
      bxnodefile.PerformRunning(id);
  }

  public void stop()
  {
      bxnodefile.stop();
  }
  public int getRate()
  {
      return bxnodefile.getProcessedRate();
  }

  public XMBXNodeFile getInterface() { return bxnodefile; }

  public void parcing(FilePath hdrdata) {
      try {
          bxnodefile.parcingHeaderFile(hdrdata);
      } catch (Exception e) {
          System.out.println(e.getMessage());
      }
  }
  public void parcing(String delimiter, int i, int j) {
      try {
          bxnodefile.parcing(delimiter, i, j);
      } catch (Exception e) {
          System.out.println(e.getMessage());
      }
  }
  public void setAllInfo(XMXNodeFileInfo info) {
      try {
          bxnodefile.setAllInfo(info);
      } catch (Exception e) {
          //System.out.println(e.getMessage());
          e.printStackTrace();
      }
  }
  public XMXNodeFileInfo getAllInfo() {
      return bxnodefile.getAllInfo();
  }
}
