package xmminer.xmclient.xmgraph.xmxnode.xmxnodefile.filextree;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.DefaultMutableTreeNode;
import java.io.*;
import xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.*;

public class ServerDirectoryTreeModel extends DefaultTreeModel
{
        DirectoryType[] dirList = null;

        public ServerDirectoryTreeModel (MutableTreeNode root, DirectoryType[] dList)
        {
                super(root);
                int currentIndex = 0;
                dirList = dList;
 /*
	  System.out.println("create directory list .................");
	  for (int i=0; i<dList.length; i++) {
		  System.out.print("[ " + i + " ] " + dList[i].nodeName);
		  for (int j=0; j<dList[i].childnodeNames.length; j++)
		  	System.out.print(":" + dList[i].childnodeNames[j]);
		  System.out.println("");
	  }
 */
                for (int i=0; i<dirList[0].childnodeNames.length; i++) {
                  currentIndex = addNewNode(root, dirList[0].childnodeNames[i], currentIndex);
                }
        }

        int addNewNode(MutableTreeNode pnode, String next, int currentIndex)
        {
            MutableTreeNode cnode = null;
            int tindex = 0;
            if (next.equals("NONE")) return currentIndex;

            try {
                int index = new Integer(next).intValue();
                if (!dirList[index].nodeType.equals("folder")) return currentIndex;

                cnode = (MutableTreeNode)new DefaultMutableTreeNode(dirList[index].nodeName);
                insertNodeInto(cnode, pnode, currentIndex);
                currentIndex ++;

                if (dirList[index].childnodeNames.length == 0) return currentIndex;

                for (int i=0; i<dirList[index].childnodeNames.length; i++) {
                  tindex = addNewNode(cnode, dirList[index].childnodeNames[i], tindex);
                }

            } catch (NumberFormatException e) {
               System.out.println(e.getMessage());
            }
            return currentIndex;
        } // end of addNewNode

}
