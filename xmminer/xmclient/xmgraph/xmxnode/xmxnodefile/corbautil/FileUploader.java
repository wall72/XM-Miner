package xmminer.xmclient.xmgraph.xmxnode.xmxnodefile.corbautil;

import java.io.*;
import javax.swing.JPanel;

import xmminer.xmclient.xmgraph.xmxnode.xmxnodefile.xmscrutil.*;
import xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.*;

public class FileUploader {

  XMBXNodeFile bxnodefile = null;
  	
  public FileUploader(XMBXNodeFile extractor)
  {
  	bxnodefile = extractor;
  }
  public String questionDialog(String oldfilename)
  {
  	return CXMDialog.getInputStringDialog(new JPanel(), oldfilename + "\n"
          + "이 파일을 서버로 Upload 하시겠습니까? \n 그러시다면 서버에서의 파일 이름을 입력해주십시오.");
  }
  public boolean upload(String oldfilename, String name)
  {
    try {
      InputStream inStream = new FileInputStream(oldfilename);
      int size = inStream.available();
      byte b[] = new byte[size];
      int res = inStream.read(b);
      inStream.close();
      bxnodefile.fileUpload(name, b);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return true;
  }
}