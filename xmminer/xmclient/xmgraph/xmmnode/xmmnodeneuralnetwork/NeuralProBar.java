
//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1999
//Author:       Your Name
//Company:      Your Company
//Description:  Your description

package xmminer.xmclient.xmgraph.xmmnode.xmmnodeneuralnetwork;

import java.awt.*;
import javax.swing.*;
import javax.swing.Icon;
import java.awt.event.*;

public class NeuralProBar extends JDialog {
  //start member variables declaraion
  XMMNodeNeuralNetwork NN;    //수정할것!!
  //end of member variables declaration

  JPanel panel1 = new JPanel();
  JProgressBar jProgressBar1 = new JProgressBar();
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel1 = new JPanel();
  JPanel jPanel6 = new JPanel();
  JLabel jLabel_TSS = new JLabel();
  JPanel jPanel2 = new JPanel();

   DefaultListModel listModel = new DefaultListModel();
  JButton jButton_Stop = new JButton();
  BorderLayout borderLayout2 = new BorderLayout();
  JList jList_TSS = new JList();
  JScrollPane jScrollPane1 = new JScrollPane(jList_TSS);
  BorderLayout borderLayout3 = new BorderLayout();
  BorderLayout borderLayout4 = new BorderLayout();

  public NeuralProBar(Frame frame,String title, boolean modal) {
    super(frame, title, modal);
    try  {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public NeuralProBar(Frame frame, XMMNodeNeuralNetwork _NN,String title, boolean modal) {
    super(frame, title, modal);
    NN = _NN;
    try  {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public NeuralProBar() {
    this(null, "", false);
  }

  void jbInit() throws Exception {


    panel1.setLayout(borderLayout1);
    jProgressBar1.setPreferredSize(new Dimension(280, 30));
    jProgressBar1.setStringPainted(true);
    panel1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    panel1.setDoubleBuffered(false);
    panel1.setMinimumSize(new Dimension(400, 80));
    panel1.setOpaque(false);
    panel1.setPreferredSize(new Dimension(400, 120));
    jPanel6.setLayout(borderLayout2);
    jLabel_TSS.setMaximumSize(new Dimension(190, 29));
    jLabel_TSS.setPreferredSize(new Dimension(176, 29));
    jLabel_TSS.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel_TSS.setHorizontalTextPosition(SwingConstants.CENTER);
    jLabel_TSS.setText("총 제곱오차 합");
    jPanel6.setPreferredSize(new Dimension(382, 80));
    jPanel1.setLayout(borderLayout4);


    jPanel2.setMaximumSize(new Dimension(186, 39));
    jPanel2.setPreferredSize(new Dimension(186, 39));
    jPanel2.setLayout(borderLayout3);


    jButton_Stop.setText("취소");
    jButton_Stop.addActionListener(new java.awt.event.ActionListener(){
      public void actionPerformed(ActionEvent e){
        jButton_Stop_actionPerformed(e);
      }
    });
    jList_TSS.setModel(listModel);
    jList_TSS.setMaximumSize(new Dimension(176, 290));
    jList_TSS.setMinimumSize(new Dimension(176, 29));
    jList_TSS.setPreferredSize(new Dimension(176, 58));
    jList_TSS.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    jList_TSS.setVisibleRowCount(2);
    jScrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    jScrollPane1.setPreferredSize(new Dimension(176, 58));
    borderLayout2.setHgap(5);
    borderLayout1.setVgap(5);
    this.getContentPane().setBackground(new Color(204, 204, 204));
    getContentPane().add(panel1);
    panel1.add(jPanel1, BorderLayout.NORTH);
    jPanel1.add(jProgressBar1, BorderLayout.CENTER);
    panel1.add(jPanel6, BorderLayout.CENTER);
    jPanel6.add(jButton_Stop, BorderLayout.EAST);
    jPanel6.add(jPanel2, BorderLayout.CENTER);
    jPanel2.add(jScrollPane1, BorderLayout.CENTER);
    jScrollPane1.getViewport().add(jList_TSS, null);
    jPanel2.add(jLabel_TSS, BorderLayout.NORTH);
  }

  public void DialogClose(){
    this.dispose();
  }

  //event listener Stop Button: stopp running...
  void jButton_Stop_actionPerformed(ActionEvent e){
     NN.m_sMNodeNeuralNetwork.StopRunning();  //수정할것!!

  }

  public void setList(float _TSS)
  {
    listModel.addElement(" "+_TSS);
    int size = listModel.getSize();
	if (size == 4)
	 listModel.remove(0);

	jList_TSS.setSelectedIndex(size-1);
//	jList_TSS.ensureIndexIsVisible(size-1);
	jScrollPane1.validate();
    return;
  }
}

