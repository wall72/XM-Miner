
//Title:        XM-Miner
//Version:
//Copyright:    Copyright (c) 1999
//Author:       Yong Uk Song
//Company:      DIS
//Description:  XM-Miner

package xmminer.xmclient.xmlib;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import jclass.chart.*;

import xmminer.xmclient.xmlib.*;

public class XMDialogChart extends JDialog
{
  JPanel panelMain = new JPanel();
  BorderLayout borderLayoutMain = new BorderLayout();
  JScrollPane scrollPaneCenter;
  XMChartModel m_chartModel;
  MultiChart multiChart = new MultiChart();
  JFrame m_frame;
  JMenuBar jMenuBar1 = new JMenuBar();
  JMenu jMenu1 = new JMenu();
  JMenuItem jMenuItem1 = new JMenuItem();
  JMenuItem jMenuItem2 = new JMenuItem();

  public XMDialogChart(Frame frame, String title, XMChartModel chartModel, boolean modal)
  {
    super(frame, title, modal);

    m_frame = (JFrame)frame;
    m_chartModel = chartModel;

    try
    {
      jbInit();
      pack();
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
    }
  }

  void jbInit() throws Exception
  {
    String text;

    panelMain.setLayout(borderLayoutMain);
    scrollPaneCenter = new JXMScrollPane(m_frame);

    multiChart.getDataView(0).setDataSource(m_chartModel.GetChartDataModel(0));
    multiChart.getDataView(1).setDataSource(m_chartModel.GetChartDataModel(1));
    multiChart.setTriggerList(new jclass.chart.TriggerWrapper("Zoom","None","None","None","None",false));
    multiChart.setAxisAnnotations(new jclass.chart.AxisAnnoWrapper(m_chartModel.GetAxisAnnotations(),"(x1 None)(y1 None)(x2 None)(y2 None)","(x1 Dialog-PLAIN-12)(y1 Dialog-PLAIN-12)(x2 Dialog-PLAIN-12)(y2 Dialog-PLAIN-12)","(x1 3)(y1 3)(x2 3)(y2 3)"));
    multiChart.setAxisGrid(new jclass.chart.AxisGridWrapper(m_chartModel.GetBAxisGrid(),"","(x1 Solid)(y1 Solid)(x2 Solid)(y2 Solid)","(x1 1)(y1 1)(x2 1)(y2 1)","(x1 black)(y1 black)(x2 black)(y2 black)"));
    multiChart.setAxisTitle(new jclass.chart.AxisTitleWrapper(m_chartModel.GetAxisTitle(),"(x1 0)(y1 0)(x2 0)(y2 0)","","(x1 Dialog-PLAIN-12)(y1 Dialog-PLAIN-12)(x2 Dialog-PLAIN-12)(y2 Dialog-PLAIN-12)"));
    multiChart.setChartAppearance(new jclass.chart.AppearanceWrapper("lightGray","black",false,"In",0,false));
    multiChart.setChartAreaAppearance(new jclass.chart.AppearanceWrapper("lightGray","black",true,"Frame_In",0,false));
    multiChart.setDataChart(new jclass.chart.MultiDataChartWrapper(m_chartModel.GetDataChart(),"(data1 x1)(data2 x1)","(data1 y1)(data2 y1)"));
    multiChart.setFooterAppearance(new jclass.chart.AppearanceWrapper("lightGray","black",true,"Frame_In",0,false));
    if ((text = m_chartModel.GetFooterText()) != null)
    {
      multiChart.setFooterText(new jclass.chart.TitleWrapper("Dialog-PLAIN-12",text,"Center"));
    }
    multiChart.setHeaderAppearance(new jclass.chart.AppearanceWrapper("lightGray","black",true,"Frame_In",0,false));
    if ((text = m_chartModel.GetHeaderText()) != null)
    {
      multiChart.setHeaderText(new jclass.chart.TitleWrapper("Dialog-PLAIN-12",text,"Center"));
    }
    multiChart.setLegendAppearance(new jclass.chart.AppearanceWrapper("lightGray","black",true,"Frame_In",0,false));
    multiChart.setLegendLayout(new jclass.chart.LegendWrapper("Vertical","East"));
    multiChart.setPlotAreaAppearance(new jclass.chart.AppearanceWrapper("lightGray","black",true,"None",0,true));

    jMenu1.setText("File");
    jMenuItem1.setText("Print");
    jMenuItem1.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        jMenuItem1_actionPerformed(e);
      }
    });
    jMenuItem2.setText("Close");
    jMenuItem2.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        jMenuItem2_actionPerformed(e);
      }
    });
    jMenuBar1.add(jMenu1);
    jMenu1.add(jMenuItem1);
    jMenu1.addSeparator();
    jMenu1.add(jMenuItem2);
    panelMain.add(scrollPaneCenter, BorderLayout.CENTER);
    panelMain.add(jMenuBar1, BorderLayout.NORTH);
    scrollPaneCenter.getViewport().add(multiChart, null);
    getContentPane().add(panelMain);
  }

  void jMenuItem1_actionPerformed(ActionEvent e)
  {
    ((JXMScrollPane)scrollPaneCenter).Print();
  }

  void jMenuItem2_actionPerformed(ActionEvent e)
  {
    dispose();
  }

  class JXMScrollPane extends JScrollPane{
    Frame m_frame;

    public JXMScrollPane(Frame frame)
    {
      super();
      m_frame = frame;
    }

	public void Print(){
      java.awt.Toolkit toolkit = this.getToolkit();
      java.awt.PrintJob job = toolkit.getPrintJob(m_frame, "XM-BRENIC/Miner V1.0", null);

      if(job == null) return;
      java.awt.Graphics g = job.getGraphics();
      g.translate(20, 20);
      java.awt.Dimension size = this.getSize();
      g.setClip(0, 0, size.width, size.height);

      this.print(g);

      g.dispose();
      job.end();
	}
  }
}