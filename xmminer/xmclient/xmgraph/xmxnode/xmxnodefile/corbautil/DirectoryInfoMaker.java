package xmminer.xmclient.xmgraph.xmxnode.xmxnodefile.corbautil;

import xmminer.xmclient.xmgraph.xmxnode.xmxnodefile.filextree.*;
import xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.*;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.JTree;
import java.io.*;

public class DirectoryInfoMaker {

  public JTree makeServerDirectoryInfo(DirectoryType[] dirList)
  {
      DefaultMutableTreeNode top = new DefaultMutableTreeNode(dirList[0].nodeName);
      ServerDirectoryTreeModel treeModel = new ServerDirectoryTreeModel(top, dirList);
      return new JTree((TreeModel)treeModel);
  }
   
  public JTree makeClientDirectoryInfo(ClientTreeMakerThread thread)
  {
      DefaultMutableTreeNode top = new DefaultMutableTreeNode("내 컴퓨터");
      ClientDirectoryTreeModel treeModel = new ClientDirectoryTreeModel(thread, top);
      return new JTree((TreeModel)treeModel);
  }
}