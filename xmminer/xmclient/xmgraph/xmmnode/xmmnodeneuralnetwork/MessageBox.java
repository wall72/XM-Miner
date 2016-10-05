
//Title:        XM-Miner
//Version:      
//Copyright:    Copyright (c) 1999
//Author:       Yong Uk Song
//Company:      DIS
//Description:  XM-Miner

package xmminer.xmclient.xmgraph.xmmnode.xmmnodeneuralnetwork;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class MessageBox extends JDialog {
    JPanel panel1 = new JPanel();
    BorderLayout borderLayout1 = new BorderLayout();
    JPanel jPanel1 = new JPanel();
    JButton jButton1 = new JButton();
    FlowLayout flowLayout1 = new FlowLayout();
    JPanel jPanel2 = new JPanel();
    GridBagLayout gridBagLayout1 = new GridBagLayout();
    JPanel jPanel3 = new JPanel();
    JPanel jPanel4 = new JPanel();
    JPanel jPanel5 = new JPanel();
    JPanel jPanel6 = new JPanel();
    BorderLayout borderLayout2 = new BorderLayout();
    FlowLayout flowLayout2 = new FlowLayout();
    JScrollPane jScrollPane1 = new JScrollPane();
    JTextArea jTextArea1 = new JTextArea();
    JLabel jLabel1 = new JLabel();
    BorderLayout borderLayout3 = new BorderLayout();
    JLabel jLabel2 = new JLabel();
    BorderLayout borderLayout4 = new BorderLayout();

    public MessageBox(Frame frame, String title, boolean modal) {
        super(frame, title, modal);
        try  {
            jbInit();
            pack();
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public MessageBox(JDialog frame, String title, boolean modal, String msg, String detailMsg)
    {
        super(frame, title, modal);
        try
        {
            jbInit();
//            ImageIcon icon = null;//new ImageIcon("orange.gif");
            ImageIcon icon = new ImageIcon("angel1.gif");
            jLabel1.setIcon(icon);
            jLabel2.setText(msg);
            this.jTextArea1.setText(detailMsg);
            pack();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    void jbInit() throws Exception {
        panel1.setLayout(borderLayout1);
        jButton1.setMaximumSize(new Dimension(59, 25));
        jButton1.setMinimumSize(new Dimension(59, 25));
        jButton1.setPreferredSize(new Dimension(59, 25));
        jButton1.setText("»Æ¿Œ");
        jButton1.addActionListener(new java.awt.event.ActionListener()
        {

            public void actionPerformed(ActionEvent e)
            {
                jButton1_actionPerformed(e);
            }
        });
        jPanel1.setLayout(flowLayout1);
        jPanel1.setMinimumSize(new Dimension(89, 50));
        jPanel1.setPreferredSize(new Dimension(89, 50));
        jPanel2.setLayout(gridBagLayout1);
        jPanel4.setMaximumSize(new Dimension(40, 40));
        jPanel4.setMinimumSize(new Dimension(40, 40));
        jPanel4.setPreferredSize(new Dimension(40, 40));
        jPanel4.setLayout(borderLayout3);
        jPanel3.setMaximumSize(new Dimension(10, 10));
        jPanel3.setMinimumSize(new Dimension(10, 10));
        jPanel3.setPreferredSize(new Dimension(10, 10));
        jPanel3.setLayout(flowLayout2);
        jPanel5.setPreferredSize(new Dimension(230, 40));
        jPanel5.setLayout(borderLayout4);
        jPanel6.setPreferredSize(new Dimension(290, 90));
        jPanel6.setLayout(borderLayout2);
        jTextArea1.setLineWrap(true);
        jTextArea1.setBorder(null);
        jTextArea1.setText("jTextArea1");
        jScrollPane1.setBorder(BorderFactory.createLoweredBevelBorder());
        jLabel1.setText("jLabel1");
        jLabel2.setText("jLabel2");
        getContentPane().add(panel1);
        panel1.add(jPanel1, BorderLayout.SOUTH);
        jPanel1.add(jButton1, null);
        panel1.add(jPanel2, BorderLayout.CENTER);
        jPanel2.add(jPanel3, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(20, 0, 0, 0), 290, 140));
        jPanel3.add(jPanel4, null);
        jPanel4.add(jLabel1, BorderLayout.CENTER);
        jPanel3.add(jPanel5, null);
        jPanel5.add(jLabel2, BorderLayout.CENTER);
        jPanel3.add(jPanel6, null);
        jPanel6.add(jScrollPane1, BorderLayout.CENTER);
        jScrollPane1.getViewport().add(jTextArea1, null);
    }

    void jButton1_actionPerformed(ActionEvent e)
    {
        this.dispose();
    }
}

