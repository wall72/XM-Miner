package xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.xmutil;

public class CXMFaultColumnException extends Exception 
{
	public String[] row = null;
	public CXMFaultColumnException(String[] r) { row = r; }
	
	public CXMFaultColumnException(String msg, String[] r)
	{	 
			super(msg);
			row = r;
	}
} // end of EndOfFileException Class

