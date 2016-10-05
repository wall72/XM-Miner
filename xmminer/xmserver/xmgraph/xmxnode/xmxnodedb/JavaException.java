package xmminer.xmserver.xmgraph.xmxnode.xmxnodedb;

public class JavaException extends Exception {
        
  public String message;

  JavaException(String msg)    {
    super(msg);
    message = msg;
  }

  JavaException()    {
    super();
  }
}