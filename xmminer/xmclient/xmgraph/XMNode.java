
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

public class XMNode extends XMGraphElement implements Serializable
{
  public Point m_pos;
  public Dimension m_dim;

  String m_name;

  int m_statEdit;
  int m_statRun;

  transient boolean m_bVisited;

  // Constructors
  public XMNode(int stat)
  {
    super(stat);
  }

  public static XMGraphElement Create(int stat)
  {
    switch (stat & XMGEFLAG_NODE)
    {
    case XMGESTAT_XNODE:
      return(XMXNode.Create(stat));
    case XMGESTAT_DNODE:
      return(XMDNode.Create(stat));
    case XMGESTAT_MNODE:
      return(XMMNode.Create(stat));
    case XMGESTAT_VNODE:
      return(XMVNode.Create(stat));
    default:
      System.out.println("Error: unknown stat for XMNode.Create");
      return(null);
    }
  }

  public boolean FillInitialContent(XMGraph graph)
  {
    if (!super.FillInitialContent(graph))
    {
      return(false);
    }

    m_dim = new Dimension(48, 48);
    m_statEdit = XMGESTAT_EDIT_NOCHANGE;
    m_statRun = XMGESTAT_RUN_NORUN;

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
    return(super.toString() + ".XMNode");
  }

  // Operation
  public String GetText()
  {
    return("XMGraphElement.XMNode");
  }

  public String GetName()
  {
    return(m_name == null ? GetText() : m_name);
  }

  public void SetName(String name)
  {
    m_name = name;
  }

  public int GetStatEdit()
  {
    return(m_statEdit);
  }

  public void SetStatEdit(int statEdit)
  {
    m_statEdit = statEdit;
  }

  public int GetStatRun()
  {
    return(m_statRun);
  }

  public void SetStatRun(int statRun)
  {
    m_statRun = statRun;
  }

  public Color GetColor()
  {
    switch (m_statRun)
    {
    case XMGESTAT_RUN_SUCCESS:
      return(Color.cyan);
    case XMGESTAT_RUN_ERROR:
      return(Color.red);
    case XMGESTAT_RUN_NORUN:
    default:
      return(Color.lightGray);
    }
  }

  public int GetIndex(XMGraph graph)
  {
    int i, f;
    for (i = 0, f = graph.GetNodeSize(); i < f; i++)
    {
      if ((XMNode)graph.GetNodeAt(i) == this)
      {
        return(i);
      }
    }
    return(-1);
  }

  public int GetWidth(int scale)
  {
    return(GetBoundary(scale).width);
  }

  public int GetHeight(int scale)
  {
    return(GetBoundary(scale).height);
  }

  public Rectangle GetBoundary(int scale)
  {
    return(new Rectangle(m_pos.x * scale / 48, m_pos.y * scale / 48, m_dim.width * scale / 48, m_dim.height * scale / 48));
  }

  public int RemoveConnectedArcs(JFrame frame, XMGraph graph)
  {
    int n = graph.GetArcSize(), r;
    XMArc arc;
    while (n-- > 0)
    {
      if ((arc = (XMArc)graph.GetArcAt(n)) != null
	  && (arc.m_fromNode == this || arc.m_toNode == this)
	  && (r = graph.RemoveArc(frame, arc, n)) != 0)
      {
	return(r);
      }
    }
    return(0);
  }

  public boolean IsRootNode(XMGraph graph)
  {
    return(GetInCount(graph) == 0);
  }

  public boolean IsLeafNode(XMGraph graph)
  {
    return(GetOutCount(graph) == 0);
  }

  public int GetInCount(XMGraph graph)
  {
    int i, f, n = 0;
    XMArc arc;
    for (i = 0, f = graph.GetArcSize(); i < f; i++)
    {
      if ((arc = (XMArc)graph.GetArcAt(i)) != null && arc.m_toNode == this)
      {
	n++;
      }
    }
    return(n);
  }

  public int GetOutCount(XMGraph graph)
  {
    int i, f, n = 0;
    XMArc arc;
    for (i = 0, f = graph.GetArcSize(); i < f; i++)
    {
      if ((arc = (XMArc)graph.GetArcAt(i)) != null && arc.m_fromNode == this)
      {
	    n++;
      }
    }
    return(n);
  }

  public XMGraphElement GetInElement(int index, XMGraph graph)
  {
    int i, f, n = 0;
    XMArc arc;
    for (i = 0, f = graph.GetArcSize(); i < f; i++)
    {
      if ((arc = (XMArc)graph.GetArcAt(i)) != null && arc.m_toNode == this)
      {
        if (n == index)
        {
          return(arc.m_fromNode);
        }
        n++;
      }
    }
    return(null);
  }

  public XMGraphElement GetOutElement(int index, XMGraph graph)
  {
    int i, f, n = 0;
    XMArc arc;
    for (i = 0, f = graph.GetArcSize(); i < f; i++)
    {
      if ((arc = (XMArc)graph.GetArcAt(i)) != null && arc.m_fromNode == this)
      {
        if (n == index)
        {
          return(arc.m_toNode);
        }
        n++;
      }
    }
    return(null);
  }

  // Popup Menu
  public class popupNodeProperties_actionPerformed implements java.awt.event.ActionListener
  {
    JFrame m_frame;
    XMGraph m_graph;
    XMNode m_node;

    public popupNodeProperties_actionPerformed(JFrame frame, XMGraph graph, XMNode node)
    {
      m_frame = frame;
      m_graph = graph;
      m_node = node;
    }

    public void actionPerformed(ActionEvent e)
    {
      int editRet;
      if (m_graph != null && (editRet = Edit(m_frame, m_graph)) != XMGESTAT_EDIT_NOCHANGE)
      {
        m_graph.SetModified(true);
        m_node.SetStatEdit(editRet);
        ((XMMinerFrame)m_frame).Invalidate();
      }
    }
  }

  public class graphPanelRename_actionPerformed implements java.awt.event.ActionListener
  {
    JFrame m_frame;
    XMGraph m_graph;
    XMNode m_node;
    JTextField m_graphPanelRename;

    public graphPanelRename_actionPerformed(JFrame frame, XMGraph graph, XMNode node, JTextField graphPanelRename)
    {
      m_frame = frame;
      m_graph = graph;
      m_node = node;
      m_graphPanelRename = graphPanelRename;
    }

    public void actionPerformed(ActionEvent e)
    {
      if (m_graph != null)
      {
        String newText = m_graphPanelRename.getText();
        if (!newText.equals(m_node.GetName()))
        {
          m_node.SetName(newText);
          m_graph.SetModified(true);
        }
        ((XMMinerFrame)m_frame).RemoveFromGraphPanel(((XMMinerFrame)m_frame).graphPanel, m_graphPanelRename);
        ((XMMinerFrame)m_frame).InvalidateComponents();
      }
    }
  }

  public class popupNodeRename_actionPerformed implements java.awt.event.ActionListener
  {
    JFrame m_frame;
    XMGraph m_graph;
    XMNode m_node;

    public popupNodeRename_actionPerformed(JFrame frame, XMGraph graph, XMNode node)
    {
      m_frame = frame;
      m_graph = graph;
      m_node = node;
    }

    public void actionPerformed(ActionEvent e)
    {
      if (m_graph != null)
      {
	JTextField graphPanelRename = new JTextField();
	graphPanelRename.addActionListener(new graphPanelRename_actionPerformed(m_frame, m_graph, m_node, graphPanelRename));
	graphPanelRename.setText(m_node.GetName());
	graphPanelRename.selectAll();
	Rectangle boundary = GetBoundary(m_graph.GetScale());
	graphPanelRename.setFont(new Font("SansSerif", Font.PLAIN, 12));
	graphPanelRename.setBounds(m_pos.x-26, m_pos.y+boundary.height, 100, 20);
	((XMMinerFrame)m_frame).AddToGraphPanel(((XMMinerFrame)m_frame).graphPanel, graphPanelRename);
	((XMMinerFrame)m_frame).InvalidateComponents();
	graphPanelRename.requestFocus();
      }
    }
  }

  public class popupNodeAddVisual_actionPerformed implements java.awt.event.ActionListener
  {
    JFrame m_frame;
    XMGraph m_graph;
    XMNode m_node;

    public popupNodeAddVisual_actionPerformed(JFrame frame, XMGraph graph, XMNode node)
    {
      m_frame = frame;
      m_graph = graph;
      m_node = node;
    }

    public void actionPerformed(ActionEvent e)
    {
      if (m_graph != null)
      {
        Point point = new Point(m_node.m_pos.x + 2 * m_node.m_dim.width,
                                m_node.m_pos.y + 3 * m_node.GetOutCount(m_graph) * m_node.m_dim.height / 2);
        XMNode toNode;
        if ((toNode = (XMNode)m_graph.CreateNodeAt(point, XMGESTAT_VNODE_NORMAL, true, true)) != null)
        {
          XMArc arc;
          if ((arc = (XMArc)m_graph.CreateArc(m_frame, XMGESTAT_SARC_NORMAL, m_node, toNode, false, true, true)) != null)
          {
    	    m_graph.SetModified(true);
	    ((XMMinerFrame)m_frame).Invalidate();
          }
          else
          {
            m_graph.RemoveNode(m_frame, toNode);
          }
        }
      }
    }
  }

  public class popupNodeRunAlone_actionPerformed implements java.awt.event.ActionListener
  {
    JFrame m_frame;
    XMGraph m_graph;
    XMNode m_node;

    public popupNodeRunAlone_actionPerformed(JFrame frame, XMGraph graph, XMNode node)
    {
      m_frame = frame;
      m_graph = graph;
      m_node = node;
    }

    public void actionPerformed(ActionEvent e)
    {
      if (m_graph != null)
      {
        int statRun = m_node.Run(m_frame, m_graph);
	m_node.SetStatRun(statRun);
        switch (statRun)
        {
        case XMGESTAT_RUN_SUCCESS:
	  m_node.SetStatEdit(XMGESTAT_EDIT_NOCHANGE);
          break;
        case XMGESTAT_RUN_ERROR:
        case XMGESTAT_RUN_NORUN:
        default:
          break;
        }
        ((XMMinerFrame)m_frame).Invalidate();
      }
    }
  }

  public class popupNodeRun2Here_actionPerformed implements java.awt.event.ActionListener
  {
    JFrame m_frame;
    XMGraph m_graph;
    XMNode m_node;

    public popupNodeRun2Here_actionPerformed(JFrame frame, XMGraph graph, XMNode node)
    {
      m_frame = frame;
      m_graph = graph;
      m_node = node;
    }

    public void actionPerformed(ActionEvent e)
    {
      if (m_graph != null)
      {
        XMNode errNode;
	m_node.InitRun(m_graph, true);
        if ((errNode = m_node.Run2Here(m_frame, m_graph)) != null)
        {
	  ((XMMinerFrame)m_frame).ShowRunError(errNode);
        }
        ((XMMinerFrame)m_frame).Invalidate();
      }
    }
  }

  public JPopupMenu CreatePopupMenu(JFrame frame, XMGraph graph)
  {
    JPopupMenu popupNode = new JPopupMenu();
    JMenuItem popupNodeProperties = new JMenuItem();
    JMenuItem popupNodeRename = new JMenuItem();
    JMenuItem popupNodeAddVisual = new JMenuItem();
    JMenuItem popupNodeRunAlone = new JMenuItem();
    JMenuItem popupNodeRun2Here = new JMenuItem();

    popupNodeProperties.setMnemonic('P');
    popupNodeProperties.setText("Properties");
    popupNodeProperties.addActionListener(new popupNodeProperties_actionPerformed(frame, graph, this));
    popupNodeRename.setMnemonic('R');
    popupNodeRename.setText("Rename");
    popupNodeRename.addActionListener(new popupNodeRename_actionPerformed(frame, graph, this));
    popupNodeAddVisual.setMnemonic('D');
    popupNodeAddVisual.setText("Add Data");
    popupNodeAddVisual.addActionListener(new popupNodeAddVisual_actionPerformed(frame, graph, this));
    popupNodeRunAlone.setMnemonic('A');
    popupNodeRunAlone.setText("Run alone");
    popupNodeRunAlone.addActionListener(new popupNodeRunAlone_actionPerformed(frame, graph, this));
    popupNodeRun2Here.setMnemonic('H');
    popupNodeRun2Here.setText("Run to here");
    popupNodeRun2Here.addActionListener(new popupNodeRun2Here_actionPerformed(frame, graph, this));

    popupNode.add(popupNodeProperties);
    popupNode.add(popupNodeRename);
    popupNode.addSeparator();
    popupNode.add(popupNodeAddVisual);
    popupNode.addSeparator();
    popupNode.add(popupNodeRunAlone);
    popupNode.add(popupNodeRun2Here);

    return(popupNode);
  }

  // Paint
  public int Paint(Frame frame, Graphics g, int scale, Rectangle clipBounds)
  {
/*
    Rectangle boundary = GetBoundary(scale);
    if (clipBounds.intersects(boundary))
    {
      g.setColor(GetColor());
      g.fillOval(boundary.x, boundary.y, boundary.width, boundary.height);

      g.setColor(new Color(0, 0, 0));
      g.drawOval(boundary.x, boundary.y, boundary.width, boundary.height);
      if (GetSelected())
      {
	XMLib.PaintSelected(g, scale, boundary);
      }
      String text = GetName();
      g.setFont(new Font("SansSerif", Font.PLAIN, 12));
      FontMetrics fontMetrics = g.getFontMetrics();
      g.drawString(text, m_pos.x + (m_dim.width - fontMetrics.stringWidth(text)) / 2,
			 m_pos.y + (m_dim.height - (fontMetrics.getAscent() + fontMetrics.getDescent())) / 2 + fontMetrics.getAscent());

      return(0);
    }
    else
    {
      return(1);
    }
*/

    Rectangle boundary = GetBoundary(scale);
    String text = GetName();
    FontMetrics fontMetrics;
    Image image;
    if (clipBounds.intersects(boundary))
    {
      switch (m_stat & XMGEFLAG_NODE)
      {
      case XMGESTAT_XNODE:
        switch (m_stat & XMGEFLAG_XNODE)
	{
        case XMGESTAT_XNODE_FILE:
	  if (GetColor().equals(Color.cyan))
	  {
	    image = Toolkit.getDefaultToolkit().getImage(xmminer.xmclient.xmgraph.XMNode.class.getResource("../images/file_l.gif"));
	  }
	  else if (GetColor().equals(Color.red))
	  {
	    image = Toolkit.getDefaultToolkit().getImage(xmminer.xmclient.xmgraph.XMNode.class.getResource("../images/file_lw.gif"));
	  }
	  else
	  {
	    image = Toolkit.getDefaultToolkit().getImage(xmminer.xmclient.xmgraph.XMNode.class.getResource("../images/file_lb.gif"));
	  }
	  g.drawImage(image, boundary.x, boundary.y, frame);
	  break;
        case XMGESTAT_XNODE_DB:
	  if (GetColor().equals(Color.cyan))
	  {
	    image = Toolkit.getDefaultToolkit().getImage(xmminer.xmclient.xmgraph.XMNode.class.getResource("../images/DB_l.gif"));
	  }
	  else if (GetColor().equals(Color.red))
	  {
	    image = Toolkit.getDefaultToolkit().getImage(xmminer.xmclient.xmgraph.XMNode.class.getResource("../images/DB_lw.gif"));
	  }
	  else
	  {
	    image = Toolkit.getDefaultToolkit().getImage(xmminer.xmclient.xmgraph.XMNode.class.getResource("../images/DB_lb.gif"));
	  }
	  g.drawImage(image, boundary.x, boundary.y, frame);
	  break;
        default:
          System.out.println("Error: unknown stat for Paint");
          g.setColor(GetColor());
          g.fillOval(boundary.x, boundary.y, boundary.width, boundary.height);
          g.setColor(new Color(0, 0, 0));
          g.drawOval(boundary.x, boundary.y, boundary.width, boundary.height);
	  break;
        }
	if (GetSelected())
	{
	  XMLib.PaintSelected(g, 70, boundary);
	}
	g.setFont(new Font("SansSerif", Font.PLAIN, 12));
	fontMetrics = g.getFontMetrics();
	g.drawString(text, m_pos.x + (m_dim.width - fontMetrics.stringWidth(text)) / 2, m_pos.y + m_dim.height + fontMetrics.getAscent() + fontMetrics.getDescent());
	break;
      case XMGESTAT_DNODE:
        switch (m_stat & XMGEFLAG_DNODE)
	{
        case XMGESTAT_DNODE_SAMPLING:
	  if (GetColor().equals(Color.cyan))
	  {
	    image = Toolkit.getDefaultToolkit().getImage(xmminer.xmclient.xmgraph.XMNode.class.getResource("../images/samp_l.gif"));
	  }
	  else if (GetColor().equals(Color.red))
	  {
	    image = Toolkit.getDefaultToolkit().getImage(xmminer.xmclient.xmgraph.XMNode.class.getResource("../images/samp_lw.gif"));
	  }
	  else
	  {
	    image = Toolkit.getDefaultToolkit().getImage(xmminer.xmclient.xmgraph.XMNode.class.getResource("../images/samp_lb.gif"));
	  }
	  g.drawImage(image, boundary.x, boundary.y, frame);
	  break;
        case XMGESTAT_DNODE_PARTITIONING:
	  if (GetColor().equals(Color.cyan))
	  {
	    image = Toolkit.getDefaultToolkit().getImage(xmminer.xmclient.xmgraph.XMNode.class.getResource("../images/part_l.gif"));
	  }
	  else if (GetColor().equals(Color.red))
	  {
	    image = Toolkit.getDefaultToolkit().getImage(xmminer.xmclient.xmgraph.XMNode.class.getResource("../images/part_lw.gif"));
	  }
	  else
	  {
	    image = Toolkit.getDefaultToolkit().getImage(xmminer.xmclient.xmgraph.XMNode.class.getResource("../images/part_lb.gif"));
	  }
	  g.drawImage(image, boundary.x, boundary.y, frame);
	  break;
        case XMGESTAT_DNODE_COLUMNCOMPUTE:
	  if (GetColor().equals(Color.cyan))
	  {
	    image = Toolkit.getDefaultToolkit().getImage(xmminer.xmclient.xmgraph.XMNode.class.getResource("../images/comp_l.gif"));
	  }
	  else if (GetColor().equals(Color.red))
	  {
	    image = Toolkit.getDefaultToolkit().getImage(xmminer.xmclient.xmgraph.XMNode.class.getResource("../images/comp_lw.gif"));
	  }
	  else
	  {
	    image = Toolkit.getDefaultToolkit().getImage(xmminer.xmclient.xmgraph.XMNode.class.getResource("../images/comp_lb.gif"));
	  }
	  g.drawImage(image, boundary.x, boundary.y, frame);
	  break;
        case XMGESTAT_DNODE_COLUMNSELECT:
	  if (GetColor().equals(Color.cyan))
	  {
	    image = Toolkit.getDefaultToolkit().getImage(xmminer.xmclient.xmgraph.XMNode.class.getResource("../images/sel_l.gif"));
	  }
	  else if (GetColor().equals(Color.red))
	  {
	    image = Toolkit.getDefaultToolkit().getImage(xmminer.xmclient.xmgraph.XMNode.class.getResource("../images/sel_lw.gif"));
	  }
	  else
	  {
	    image = Toolkit.getDefaultToolkit().getImage(xmminer.xmclient.xmgraph.XMNode.class.getResource("../images/sel_lb.gif"));
	  }
	  g.drawImage(image, boundary.x, boundary.y, frame);
	  break;
        case XMGESTAT_DNODE_DATAQUERY:
	  if (GetColor().equals(Color.cyan))
	  {
	    image = Toolkit.getDefaultToolkit().getImage(xmminer.xmclient.xmgraph.XMNode.class.getResource("../images/query_l.gif"));
	  }
	  else if (GetColor().equals(Color.red))
	  {
	    image = Toolkit.getDefaultToolkit().getImage(xmminer.xmclient.xmgraph.XMNode.class.getResource("../images/query_lw.gif"));
	  }
	  else
	  {
	    image = Toolkit.getDefaultToolkit().getImage(xmminer.xmclient.xmgraph.XMNode.class.getResource("../images/query_lb.gif"));
	  }
	  g.drawImage(image, boundary.x, boundary.y, frame);
	  break;
        default:
          System.out.println("Error: unknown stat for Paint");
          g.setColor(GetColor());
          g.fillOval(boundary.x, boundary.y, boundary.width, boundary.height);
          g.setColor(new Color(0, 0, 0));
          g.drawOval(boundary.x, boundary.y, boundary.width, boundary.height);
	  break;
        }
	if (GetSelected())
	{
	  XMLib.PaintSelected(g, 70, boundary);
	}
	g.setFont(new Font("SansSerif", Font.PLAIN, 12));
	fontMetrics = g.getFontMetrics();
	g.drawString(text, m_pos.x + (m_dim.width - fontMetrics.stringWidth(text)) / 2, m_pos.y + m_dim.height + fontMetrics.getAscent() + fontMetrics.getDescent());
	break;
      case XMGESTAT_MNODE:
        switch (m_stat & XMGEFLAG_MNODE)
	{
        case XMGESTAT_MNODE_REGRESSION:
	  if (GetColor().equals(Color.cyan))
	  {
	    image = Toolkit.getDefaultToolkit().getImage(xmminer.xmclient.xmgraph.XMNode.class.getResource("../images/reg_l.gif"));
	  }
	  else if (GetColor().equals(Color.red))
	  {
	    image = Toolkit.getDefaultToolkit().getImage(xmminer.xmclient.xmgraph.XMNode.class.getResource("../images/reg_lw.gif"));
	  }
	  else
	  {
	    image = Toolkit.getDefaultToolkit().getImage(xmminer.xmclient.xmgraph.XMNode.class.getResource("../images/reg_lb.gif"));
	  }
	  g.drawImage(image, boundary.x, boundary.y, frame);
	  break;
        case XMGESTAT_MNODE_CORRELATION:
	  if (GetColor().equals(Color.cyan))
	  {
	    image = Toolkit.getDefaultToolkit().getImage(xmminer.xmclient.xmgraph.XMNode.class.getResource("../images/cor_l.gif"));
	  }
	  else if (GetColor().equals(Color.red))
	  {
	    image = Toolkit.getDefaultToolkit().getImage(xmminer.xmclient.xmgraph.XMNode.class.getResource("../images/cor_lw.gif"));
	  }
	  else
	  {
	    image = Toolkit.getDefaultToolkit().getImage(xmminer.xmclient.xmgraph.XMNode.class.getResource("../images/cor_lb.gif"));
	  }
	  g.drawImage(image, boundary.x, boundary.y, frame);
	  break;
        case XMGESTAT_MNODE_ASSOCIATIONRULE:
	  if (GetColor().equals(Color.cyan))
	  {
	    image = Toolkit.getDefaultToolkit().getImage(xmminer.xmclient.xmgraph.XMNode.class.getResource("../images/asso_l.gif"));
	  }
	  else if (GetColor().equals(Color.red))
	  {
	    image = Toolkit.getDefaultToolkit().getImage(xmminer.xmclient.xmgraph.XMNode.class.getResource("../images/asso_lw.gif"));
	  }
	  else
	  {
	    image = Toolkit.getDefaultToolkit().getImage(xmminer.xmclient.xmgraph.XMNode.class.getResource("../images/asso_lb.gif"));
	  }
	  g.drawImage(image, boundary.x, boundary.y, frame);
	  break;
        case XMGESTAT_MNODE_SEQUENCERULE:
	  if (GetColor().equals(Color.cyan))
	  {
	    image = Toolkit.getDefaultToolkit().getImage(xmminer.xmclient.xmgraph.XMNode.class.getResource("../images/seq_l.gif"));
	  }
	  else if (GetColor().equals(Color.red))
	  {
	    image = Toolkit.getDefaultToolkit().getImage(xmminer.xmclient.xmgraph.XMNode.class.getResource("../images/seq_lw.gif"));
	  }
	  else
	  {
	    image = Toolkit.getDefaultToolkit().getImage(xmminer.xmclient.xmgraph.XMNode.class.getResource("../images/seq_lb.gif"));
	  }
	  g.drawImage(image, boundary.x, boundary.y, frame);
	  break;
        case XMGESTAT_MNODE_EPISODERULE:
	  if (GetColor().equals(Color.cyan))
	  {
	    image = Toolkit.getDefaultToolkit().getImage(xmminer.xmclient.xmgraph.XMNode.class.getResource("../images/epi_l.gif"));
	  }
	  else if (GetColor().equals(Color.red))
	  {
	    image = Toolkit.getDefaultToolkit().getImage(xmminer.xmclient.xmgraph.XMNode.class.getResource("../images/epi_lw.gif"));
	  }
	  else
	  {
	    image = Toolkit.getDefaultToolkit().getImage(xmminer.xmclient.xmgraph.XMNode.class.getResource("../images/epi_lb.gif"));
	  }
	  g.drawImage(image, boundary.x, boundary.y, frame);
	  break;
        case XMGESTAT_MNODE_C45:
	  if (GetColor().equals(Color.cyan))
	  {
	    image = Toolkit.getDefaultToolkit().getImage(xmminer.xmclient.xmgraph.XMNode.class.getResource("../images/c45_l.gif"));
	  }
	  else if (GetColor().equals(Color.red))
	  {
	    image = Toolkit.getDefaultToolkit().getImage(xmminer.xmclient.xmgraph.XMNode.class.getResource("../images/c45_lw.gif"));
	  }
	  else
	  {
	    image = Toolkit.getDefaultToolkit().getImage(xmminer.xmclient.xmgraph.XMNode.class.getResource("../images/c45_lb.gif"));
	  }
	  g.drawImage(image, boundary.x, boundary.y, frame);
	  break;
        case XMGESTAT_MNODE_NEURALNETWORK:
	  if (GetColor().equals(Color.cyan))
	  {
	    image = Toolkit.getDefaultToolkit().getImage(xmminer.xmclient.xmgraph.XMNode.class.getResource("../images/nn_l.gif"));
	  }
	  else if (GetColor().equals(Color.red))
	  {
	    image = Toolkit.getDefaultToolkit().getImage(xmminer.xmclient.xmgraph.XMNode.class.getResource("../images/nn_lw.gif"));
	  }
	  else
	  {
	    image = Toolkit.getDefaultToolkit().getImage(xmminer.xmclient.xmgraph.XMNode.class.getResource("../images/nn_lb.gif"));
	  }
	  g.drawImage(image, boundary.x, boundary.y, frame);
	  break;
        case XMGESTAT_MNODE_PREDICTION:
	  if (GetColor().equals(Color.cyan))
	  {
	    image = Toolkit.getDefaultToolkit().getImage(xmminer.xmclient.xmgraph.XMNode.class.getResource("../images/pre_l.gif"));
	  }
	  else if (GetColor().equals(Color.red))
	  {
	    image = Toolkit.getDefaultToolkit().getImage(xmminer.xmclient.xmgraph.XMNode.class.getResource("../images/pre_lw.gif"));
	  }
	  else
	  {
	    image = Toolkit.getDefaultToolkit().getImage(xmminer.xmclient.xmgraph.XMNode.class.getResource("../images/pre_lb.gif"));
	  }
	  g.drawImage(image, boundary.x, boundary.y, frame);
	  break;
        default:
          System.out.println("Error: unknown stat for Paint");
          g.setColor(GetColor());
          g.fillOval(boundary.x, boundary.y, boundary.width, boundary.height);
          g.setColor(new Color(0, 0, 0));
          g.drawOval(boundary.x, boundary.y, boundary.width, boundary.height);
	  break;
        }
	if (GetSelected())
	{
	  XMLib.PaintSelected(g, 70, boundary);
	}
	g.setFont(new Font("SansSerif", Font.PLAIN, 12));
	fontMetrics = g.getFontMetrics();
	g.drawString(text, m_pos.x + (m_dim.width - fontMetrics.stringWidth(text)) / 2, m_pos.y + m_dim.height + fontMetrics.getAscent() + fontMetrics.getDescent());
	break;
      case XMGESTAT_VNODE:
	if (GetColor().equals(Color.cyan))
	{
	  image = Toolkit.getDefaultToolkit().getImage(xmminer.xmclient.xmgraph.XMNode.class.getResource("../images/data_l.gif"));
	}
	else if (GetColor().equals(Color.red))
	{
	  image = Toolkit.getDefaultToolkit().getImage(xmminer.xmclient.xmgraph.XMNode.class.getResource("../images/data_lw.gif"));
	}
	else
	{
	  image = Toolkit.getDefaultToolkit().getImage(xmminer.xmclient.xmgraph.XMNode.class.getResource("../images/data_lb.gif"));
	}
	g.drawImage(image, boundary.x, boundary.y, frame);
	if (GetSelected())
	{
	  XMLib.PaintSelected(g, 70, boundary);
	}
	g.setFont(new Font("SansSerif", Font.PLAIN, 12));
	fontMetrics = g.getFontMetrics();
	g.drawString(text, m_pos.x + (m_dim.width - fontMetrics.stringWidth(text)) / 2, m_pos.y + m_dim.height + fontMetrics.getAscent() + fontMetrics.getDescent());
	break;
      default:
        System.out.println("Error: unknown stat for XMNode");
        return(0);
      }
      return(0);
    }
    else
    {
      return(1);
    }
  }

  public void DrawRoaming(Graphics g, Dimension offset, int scale)
  {
    Rectangle boundary = GetBoundary(scale);
    g.drawOval(boundary.x + offset.width, boundary.y + offset.height,
               boundary.width, boundary.height);
  }

  public void DrawRoamingLine(Graphics g, XMNode node, Point toPoint, int scale)
  {
    Rectangle boundary = GetBoundary(scale);
    Point fromPoint = XMLib.GetConnectPoint(boundary, toPoint);
    g.drawLine(fromPoint.x, fromPoint.y, toPoint.x, toPoint.y);
  }

  // Initialize
  private void InitVisit1(XMGraph graph, boolean bVisited)
  {
    int i, f;
    XMArc arc;
    for (i = 0, f = graph.GetArcSize(); i < f; i++)
    {
      if ((arc = (XMArc)graph.GetArcAt(i)) != null
	  && arc.m_toNode == this
	  && arc.m_fromNode != null)
      {
	((XMNode)arc.m_fromNode).InitVisit1(graph, bVisited);
      }
    }
    m_bVisited = bVisited;
  }

  private void InitVisit(XMGraph graph)
  {
    InitVisit1(graph, false);
  }

  static final int XMNODEFLAG_TRAVERSE_NONE	= 0;
  static final int XMNODEFLAG_TRAVERSE_DO	= 1;
  static final int XMNODEFLAG_TRAVERSE_ERROR	= -1;

  private int InitRun1(XMGraph graph, boolean bRunAll, int statRun)
  {
    boolean bDo;
    int i, f;
    XMArc arc;
    for (bDo = false, i = 0, f = graph.GetArcSize(); i < f; i++)
    {
      if ((arc = (XMArc)graph.GetArcAt(i)) != null
	  && arc.m_toNode == this
	  && arc.m_fromNode != null)
      {
        switch (((XMNode)arc.m_fromNode).InitRun1(graph, bRunAll, statRun))
	{
	case XMNODEFLAG_TRAVERSE_NONE:
	  break;
	case XMNODEFLAG_TRAVERSE_DO:
	  bDo = true;
	  break;
	case XMNODEFLAG_TRAVERSE_ERROR:
	default:
	  return(XMNODEFLAG_TRAVERSE_ERROR);
	}
      }
    }
    if (bDo || bRunAll || m_statEdit != XMGESTAT_EDIT_NOCHANGE)
    {
      m_statRun = statRun;
      return(XMNODEFLAG_TRAVERSE_DO);
    }
    else
    {
      return(XMNODEFLAG_TRAVERSE_NONE);
    }
  }

  public void InitRun(XMGraph graph, boolean bRunAll)
  {
    InitRun1(graph, bRunAll, XMGESTAT_RUN_NORUN);
  }

  // Schema
  // Do not override
  transient XMNode m_errNode;

  private int GetSchema1(XMGraph graph)
  {
    boolean bDo;
    int i, f;
    XMArc arc;
    for (bDo = false, i = 0, f = graph.GetArcSize(); i < f; i++)
    {
      if ((arc = (XMArc)graph.GetArcAt(i)) != null
	  && arc.m_toNode == this
	  && arc.m_fromNode != null
	  && !m_bVisited)
      {
        switch (((XMNode)arc.m_fromNode).GetSchema1(graph))
	{
	case XMNODEFLAG_TRAVERSE_NONE:
	  break;
	case XMNODEFLAG_TRAVERSE_DO:
	  bDo = true;
	  break;
	case XMNODEFLAG_TRAVERSE_ERROR:
	default:
	  return(XMNODEFLAG_TRAVERSE_ERROR);
	}
      }
    }
    if ((bDo || m_statEdit == XMGESTAT_EDIT_CRITICALCHANGE) && !m_bVisited)
    {
      if (SetSchema(graph) == 0)
      {
	m_bVisited = true;
	m_statEdit = XMGESTAT_EDIT_CHANGE;
	return(XMNODEFLAG_TRAVERSE_DO);
      }
      else
      {
        m_errNode = this;
	return(XMNODEFLAG_TRAVERSE_ERROR);
      }
    }
    else
    {
      return(bDo ? XMNODEFLAG_TRAVERSE_DO : XMNODEFLAG_TRAVERSE_NONE);
    }
  }

  public XMNode GetSchema(XMGraph graph)
  {
    InitVisit(graph);
    int i, n;
    XMNode node;
    for (i = 0, n = GetInCount(graph); i < n; i++)
    {
      if ((node = (XMNode)GetInElement(i, graph)) != null
	  && node.GetSchema1(graph) == XMNODEFLAG_TRAVERSE_ERROR)
      {
        return(m_errNode);
      }
    }
    return(null);
  }

  // Override
  public int SetSchema(XMGraph graph)
  {
    System.out.println("XMNode.SetSchema");
    return(0);
  }

  // Edit
  public int Edit(JFrame frame, XMGraph graph)
  {
    XMNode errNode;
    if ((errNode = GetSchema(graph)) != null)
    {
      JOptionPane.showMessageDialog(frame, "Cannot get the schema of " + errNode.GetName() + ".");
      return(XMGESTAT_EDIT_NOCHANGE);
    }

    return(XMGESTAT_EDIT_CRITICALCHANGE);
  }

  // Run
  public int Run(JFrame frame, XMGraph graph)
  {
    //System.out.println("Running ... " + GetIndex(graph));
    return(XMGESTAT_RUN_SUCCESS);
  }

  private void ModifyOutNodes(XMGraph graph, int statEdit)
  {
    int i, f;
    XMArc arc;
    for (i = 0, f = graph.GetArcSize(); i < f; i++)
    {
      if ((arc = (XMArc)graph.GetArcAt(i)) != null
	  && arc.m_fromNode == this
	  && arc.m_toNode != null)
      {
	((XMNode)arc.m_toNode).m_statEdit = statEdit;
      }
    }
  }

  private XMNode GetCriticalChangedNode1(XMGraph graph, int depth)
  {
    int i, f;
    XMArc arc;
    XMNode errNode;
    for (i = 0, f = graph.GetArcSize(); i < f; i++)
    {
      if ((arc = (XMArc)graph.GetArcAt(i)) != null
	  && arc.m_toNode == this
	  && arc.m_fromNode != null
	  && !m_bVisited
	  && (errNode = ((XMNode)arc.m_fromNode).GetCriticalChangedNode1(graph, depth + 1)) != null)
      {
	return(errNode);
      }
    }
    if (m_bVisited)
    {
      return(null);
    }
    else if (depth > 1 && m_statEdit == XMGESTAT_EDIT_CRITICALCHANGE)
    {
      return(this);
    }
    else
    {
      m_bVisited = true;
      return(null);
    }
  }

  private XMNode GetCriticalChangedNode(XMGraph graph)
  {
    InitVisit(graph);
    return(GetCriticalChangedNode1(graph, 0));
  }

  private XMNode Run2Here1(JFrame frame, XMGraph graph)
  {
    int i, f;
    XMArc arc;
    XMNode errNode;
    for (i = 0, f = graph.GetArcSize(); i < f; i++)
    {
      if ((arc = (XMArc)graph.GetArcAt(i)) != null
	  && arc.m_toNode == this
	  && arc.m_fromNode != null
	  && !m_bVisited
	  && (errNode = ((XMNode)arc.m_fromNode).Run2Here1(frame, graph)) != null)
      {
	return(errNode);
      }
    }
    if (m_bVisited || m_statRun != XMGESTAT_RUN_NORUN)
    {
      return(null);
    }
    else
    {
      m_statRun = Run(frame, graph);
      m_bVisited = true;
      switch (m_statRun)
      {
      case XMGESTAT_RUN_SUCCESS:
	m_statEdit = XMGESTAT_EDIT_NOCHANGE;
	ModifyOutNodes(graph, XMGESTAT_EDIT_CHANGE);
	return(null);
      case XMGESTAT_RUN_ERROR:
      case XMGESTAT_RUN_NORUN:
      default:
	return(this);
      }
    }
  }

  public XMNode Run2Here(JFrame frame, XMGraph graph)
  {
    XMNode errNode;
    if ((errNode = GetCriticalChangedNode(graph)) != null)
    {
      return(errNode);
    }
    else
    {
      InitVisit(graph);
      Run2Here1(frame, graph);
      return(null);
    }
  }

}
