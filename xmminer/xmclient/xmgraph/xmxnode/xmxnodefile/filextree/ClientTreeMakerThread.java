package xmminer.xmclient.xmgraph.xmxnode.xmxnodefile.filextree;

import java.io.File;
import java.lang.Thread;
import javax.swing.tree.DefaultMutableTreeNode;

public class ClientTreeMakerThread extends Thread
{
   	final String sep = System.getProperty("file.separator");
	DefaultMutableTreeNode rootNode;
	private boolean stopRequested;
	private int sleepCounter=0;;
	
  	public ClientTreeMakerThread(DefaultMutableTreeNode root)
	{
			rootNode = root;
	}// end of ClientTreeMakerThread

	public void run()
	{
	    	System.out.println("thread start");
          File f[] = File.listRoots();
          String path = f[0].getPath();
          DefaultMutableTreeNode node = new DefaultMutableTreeNode(path);
          rootNode.add(node);
      		for (int i=1; i<f.length; i++) {
           		if (!stopRequested) {
                	path = f[i].getPath();
                	node = new DefaultMutableTreeNode(path);
           			rootNode.add(node);
           			addChild(node);
           		}
      		}
	    	System.out.println("thread end");
	}// end of run
	
	public void requestStop()
	{
			stopRequested = true;
			interrupt();
	}
	
    private void addChild(DefaultMutableTreeNode cnode)
    {
        int childCount;
        DefaultMutableTreeNode nnode;   // next node (child node)

        if (stopRequested) return;
        
        try {
        		sleepCounter++;
        		if (sleepCounter > 30) {
        			sleep(3000);
        			sleepCounter = 0;
            		Object[] pathlist = cnode.getUserObjectPath();
            		StringBuffer fullpath = new StringBuffer(64);
            		fullpath.append((String)pathlist[1]);
            		for (int i=2; i<pathlist.length; i++) fullpath.append(sep).append((String)pathlist[i]);
        			System.out.println("cnode is " + fullpath.toString());
        		}
	    } catch (InterruptedException e) {
	    		if (stopRequested) return;
	    }

   		 if (cnode.isLeaf()) {
            		Object[] pathlist = cnode.getUserObjectPath();
            		StringBuffer fullpath = new StringBuffer(64);
            		fullpath.append((String)pathlist[1]);
            		for (int i=2; i<pathlist.length; i++) fullpath.append(sep).append((String)pathlist[i]);
  	    			System.out.println("add node " + fullpath);
          		File file = new File(fullpath.toString());
          		File tfile;
            		String[] list = file.list();
            		fullpath.append(sep);
            		for (int i=0; i<list.length; i++) {
              			tfile = new File(fullpath.toString()+list[i]);
               			if (tfile.isDirectory()) {
                 					nnode = new DefaultMutableTreeNode(list[i]);
                    				cnode.add(nnode);
                    				addChild(nnode);
                 		}// end of if
            		}// end of for
      		} else {
            		childCount = cnode.getChildCount();
            		for (int i=0; i<childCount; i++) {
                	nnode = (DefaultMutableTreeNode) cnode.getChildAt(i);
                	addChild(nnode);
            		}// end of for
     		}// end of else
    }// end of addChild
	
	public void waitThread()
	{
		try {
			wait();
		} catch (InterruptedException e) {}
	}
	
	public void notifyThread()
	{
		notify();
	}   
}// end of class