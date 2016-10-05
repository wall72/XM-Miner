package xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.filex;
import java.io.*;

class NotAcceptedExt implements FilenameFilter 
{
	String ext[];
	
	public NotAcceptedExt(String e[])
	{
		this.ext = new String[e.length];
		for (int i=0; i<e.length; i++)
			this.ext[i] = "." + e[i];
	}
	
	public boolean accept(File dir, String name)
	{
		for (int i=0; i<ext.length; i++) 
			if (name.endsWith(ext[i]))	return false;	
		return true;
	}
} // end of OnlyExt class
