
//Title:        XM-Miner
//Version:
//Copyright:    Copyright (c) 1999
//Author:       Yong Uk Song
//Company:      DIS
//Description:  XM-Miner

package xmminer.xmclient.xmgraph;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

import xmminer.xmlib.*;
import xmminer.xmclient.*;
import xmminer.xmclient.xmgraph.*;

public class XMVNode extends XMNode implements Serializable
{
  String m_metaFilename;

  // Constructors
  public XMVNode(int stat)
  {
    super(stat);
  }

  public static XMGraphElement Create(int stat)
  {
    switch (stat & XMGEFLAG_VNODE)
    {
    case XMGESTAT_VNODE_NORMAL:
      return(new XMVNode(stat));
    default:
      System.out.println("Error: unknown stat for XMVNode.Create");
      return(null);
    }
  }

  public boolean FillInitialContent(XMGraph graph)
  {
    if (!super.FillInitialContent(graph))
    {
      return(false);
    }

    return(true);
  }

  // Serialize
  /*
  private void writeObject(ObjectOutputStream out) throws IOException
  {
  }

  private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
  {
  }
  */

  public String toString()
  {
    return(super.toString() + ".XMVNode");
  }

  // Operation
  public String GetText()
  {
    return("Data");
  }

  public String GetMetaFilename()
  {
    if (m_metaFilename == null)
    {
      //m_metaFilename = "arc" + GetUniqueId() + ".meta";
      m_metaFilename = "arc" + GetUniqueId();
    }
    return(m_metaFilename);
  }

  // Popup Menu
  class popupVNodeConnect_actionPerformed implements java.awt.event.ActionListener
  {
    JFrame m_frame;
    XMGraph m_graph;
    XMNode m_node;

    public popupVNodeConnect_actionPerformed(JFrame frame, XMGraph graph, XMNode node)
    {
      m_frame = frame;
      m_graph = graph;
      m_node = node;
    }

    public void actionPerformed(ActionEvent e)
    {
      ((XMMinerFrame)m_frame).SetDrawActionConnect(e);
    }
  }

  public JPopupMenu CreatePopupMenu(JFrame frame, XMGraph graph)
  {
    JPopupMenu popupNode = new JPopupMenu();
    JMenuItem popupNodeProperties = new JMenuItem();
    JMenuItem popupNodeRename = new JMenuItem();
    JMenuItem popupNodeConnect = new JMenuItem();

    popupNodeProperties.setMnemonic('P');
    popupNodeProperties.setText("Properties");
    popupNodeProperties.addActionListener(new popupNodeProperties_actionPerformed(frame, graph, this));
    popupNodeRename.setMnemonic('R');
    popupNodeRename.setText("Rename");
    popupNodeRename.addActionListener(new popupNodeRename_actionPerformed(frame, graph, this));
    popupNodeConnect.setMnemonic('C');
    popupNodeConnect.setText("Connect");
    popupNodeConnect.addActionListener(new popupVNodeConnect_actionPerformed(frame, graph, this));

    popupNode.add(popupNodeProperties);
    popupNode.add(popupNodeRename);
    popupNode.addSeparator();
    popupNode.add(popupNodeConnect);

    return(popupNode);
  }

  // Paint
  /*
  public int Paint(Graphics g, int scale, Rectangle clipBounds)
  {
    Rectangle boundary = GetBoundary(scale);
    if (clipBounds.intersects(boundary))
    {
      g.setColor(GetColor());
      g.fillRect(boundary.x, boundary.y, boundary.width, boundary.height);

      g.setColor(new Color(0, 0, 0));
      g.drawRect(boundary.x, boundary.y, boundary.width, boundary.height);
      if (GetSelected())
      {
	XMLib.PaintSelected(g, scale, boundary);
      }
      String text = GetName();
      g.setFont(new Font("SansSerif", Font.PLAIN, 15));
      FontMetrics fontMetrics = g.getFontMetrics();
      g.drawString(text, m_pos.x + (m_dim.width - fontMetrics.stringWidth(text)) / 2,
			 m_pos.y + (m_dim.height - (fontMetrics.getAscent() + fontMetrics.getDescent())) / 2 + fontMetrics.getAscent());

      return(0);
    }
    else
    {
      return(1);
    }
  }
  */

  public void DrawRoaming(Graphics g, Dimension offset, int scale)
  {
    Rectangle boundary = GetBoundary(scale);
    g.drawRect(boundary.x + offset.width, boundary.y + offset.height,
               boundary.width, boundary.height);
  }

  // Schema
  public int SetSchema(XMGraph graph)
  {
    return(0);
  }

  // Edit
  public int Edit(JFrame frame, XMGraph graph)
  {
    try
    {
      JDialog dialog = new XMDialogVNode(frame, "Data", graph, this, true);
      //dialog.setSize(700, 500);
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      Dimension dialogSize = dialog.getSize();
      if (dialogSize.height > screenSize.height)
        dialogSize.height = screenSize.height;
      if (dialogSize.width > screenSize.width)
        dialogSize.width = screenSize.width;
      dialog.setLocation((screenSize.width - dialogSize.width) / 2, (screenSize.height - dialogSize.height) / 2);
      dialog.show();
    }
    catch (Exception e)
    {
      JOptionPane.showMessageDialog(frame, "Data was not prepared.");
    }
    return(XMGESTAT_EDIT_NOCHANGE);
  }

}
