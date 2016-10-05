
//Title:        XM-Miner
//Version:
//Copyright:    Copyright (c) 1999
//Author:       Yong Uk Song
//Company:      DIS
//Description:  XM-Miner

package xmminer.xmclient.xmgraph.xmmnode.xmmnodec45;

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
    JScrollPane jScrollPane1 = new JScrollPane();
    JTextArea jTextArea1 = new JTextArea();
    JLabel jLabel2 = new JLabel();
  JPanel jPanel7 = new JPanel();
  FlowLayout flowLayout3 = new FlowLayout();
  FlowLayout flowLayout2 = new FlowLayout();

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
            jLabel2.setText(msg);
            this.jTextArea1.setText(detailMsg);
            pack();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public MessageBox(JFrame frame, String title, boolean modal, String msg, String detailMsg)
    {
        super(frame, title, modal);
        try
        {
            jbInit();
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
        jButton1.setFont(new java.awt.Font("Dialog", 0, 12));
    jButton1.setMaximumSize(new Dimension(59, 25));
        jButton1.setMinimumSize(new Dimension(59, 25));
        jButton1.setPreferredSize(new Dimension(60, 25));
        jButton1.setText("»Æ¿Œ");
        jButton1.addActionListener(new java.awt.event.ActionListener()
        {

            public void actionPerformed(ActionEvent e)
            {
                jButton1_actionPerformed(e);
            }
        });
        jPanel1.setLayout(flowLayout1);
        jPanel1.setMinimumSize(new Dimension(89, 30));
        jPanel1.setPreferredSize(new Dimension(89, 35));
        jPanel2.setLayout(flowLayout2);
        jTextArea1.setLineWrap(true);
        jTextArea1.setBorder(null);
        jTextArea1.setText("jTextArea1");
        jScrollPane1.setBorder(BorderFactory.createLoweredBevelBorder());
    jScrollPane1.setPreferredSize(new Dimension(450, 450));
        jLabel2.setText("jLabel2");
        jPanel7.setLayout(flowLayout3);
    panel1.setPreferredSize(new Dimension(500, 500));
    getContentPane().add(panel1);
        panel1.add(jPanel1, BorderLayout.SOUTH);
        jPanel1.add(jButton1, null);
        panel1.add(jPanel2, BorderLayout.CENTER);
    jPanel2.add(jScrollPane1, null);
    jScrollPane1.getViewport().add(jTextArea1, null);
    panel1.add(jPanel7, BorderLayout.NORTH);
    jPanel7.add(jLabel2, null);
    }

    void jButton1_actionPerformed(ActionEvent e)
    {
        this.dispose();
    }
}

