package xmminer.xmclient.xmgraph.xmxnode.xmxnodefile.filextree;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import java.io.*;
import java.util.Properties;
import xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.*;

public class ClientDirectoryTreeModel extends DefaultTreeModel
{
        final String sep = System.getProperty("file.separator");
        DirectoryType[] dirList = null;
	    DefaultMutableTreeNode node = null;
        ClientTreeMakerThread ctreeThread = null;

        public ClientDirectoryTreeModel (DefaultMutableTreeNode root)
        {
           super(root);
           DefaultMutableTreeNode node = null;
           System.out.println("ClientDirectoryTreeModel created...");
           File f[] = File.listRoots();
           String path = f[0].getPath();
           node = new DefaultMutableTreeNode(path);
           root.add(node);
		   addChild(node); // updated by cliff w. lee in 2002.09.15
           for (int i=1; i<f.length; i++) {
                path = f[i].getPath();
        			node = new DefaultMutableTreeNode(path);
        			root.add(node);
        			addChild(node);
      		 } 
        }
		
		public ClientDirectoryTreeModel (ClientTreeMakerThread thread, DefaultMutableTreeNode node)
		{
				super(node);
				ctreeThread = thread;
		}

    private void addChild(DefaultMutableTreeNode cnode)
	 {
          if (cnode.isLeaf()) {
                String fullpath = getFullpath(cnode);
                File[] filelist = new File(fullpath).listFiles();
                if (filelist == null) return;
                for (int i=0; i<filelist.length; i++) {
                  		if (filelist[i].isDirectory()) {
                      			cnode.add(new DefaultMutableTreeNode(filelist[i].getName()));
                  		}
                }// end of for
          } // end of if
    }// end of addChild


    private String getFullpath(DefaultMutableTreeNode cnode)
    {
          TreeNode[] tpath = cnode.getPath();
          StringBuffer fullpath = new StringBuffer(64);
          fullpath.append(tpath[1].toString());
          for (int i=2; i<tpath.length; i++) fullpath.append(sep).append(tpath[i]);
          return fullpath.toString();
    }

}
