package xmminer.xmclient.xmgraph.xmxnode.xmxnodefile.filexscr;

import java.lang.Thread;
import java.util.Hashtable;
import java.util.Vector;
import xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.EnumerationType;

public class EnumListTableFiller extends Thread 
{
	EnumerationType[] enumList = null;
	Hashtable table = null;	
	private boolean stopRequested = false;

  	public EnumListTableFiller(EnumerationType[] enumList)
  	{
  			this.enumList = enumList;
  			this.table = new Hashtable();
  	}// end of EnumListTableFiller
  
  	public void run()
  	{
      System.out.println("thread start");
      try {
      		int i=0;
      		while ( (i<enumList.length) && (!stopRequested) ) {
          		table.put(enumList[i].key, strArrayToVector(enumList[i].values));
          		i++;
          		sleep(1000);
      		}
      	} catch (InterruptedException e) {}
      System.out.println("thread end");
  	}// end of run
  	
  	public void requestStop() 
  	{
  			stopRequested = true;
  	}
  	private Vector strArrayToVector(String input[])
  	{
      Vector v = new Vector(3,2);
      int size = input.length;
      for (int i=0; i<size; i++) {
          v.addElement(input[i]);
      }
      return v;
  	}// end of strArrayToVector

  	public Hashtable getTable() 
  	{
  			return table;
  	}// end of getTable
  
}// end of class