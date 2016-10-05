
//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1999
//Author:       Your Name
//Company:      Your Company
//Description:  Your description

package xmminer.xmclient.xmgraph.xmmnode.xmmnodeneuralnetwork;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import javax.swing.table.TableColumn;
import java.util.*;
import java.lang.*;
import javax.swing.event.*;
import javax.swing.table.*;
import javax.swing.DefaultCellEditor;

import xmminer.xmclient.xmgraph.*;
import xmminer.xmclient.xmgraph.xmmnode.xmmnodeneuralnetwork.*;

public class XMMNodeNeuralDialog extends JDialog {
  JPanel panel1 = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel_bottom = new JPanel();
  JPanel jPanel_center = new JPanel();
  JPanel jPanel_top = new JPanel();
  JTabbedPane jTabbedPane1 = new JTabbedPane();
  JPanel jPanel_Method = new JPanel();
  JPanel jPanel_parameter = new JPanel();
  JPanel jPanel_column_sel = new JPanel();
  JButton jButton1 = new JButton();
  JButton jButton2 = new JButton();
  JPanel jPanel_model = new JPanel();
  JPanel jPanel3 = new JPanel();
  JPanel jPanel4 = new JPanel();
  JLabel jLabel2 = new JLabel();
  JTextField jTextField_Networkname = new JTextField();
  JPanel jPanel2 = new JPanel();
  TitledBorder titledBorder1;
  JCheckBox jCheckBox_Learn = new JCheckBox();
  JCheckBox jCheckBox_Test = new JCheckBox();
  TitledBorder titledBorder2;
  JLabel jLabel3 = new JLabel();
  JLabel jLabel4 = new JLabel();
  JLabel jLabel5 = new JLabel();
  JLabel jLabel6 = new JLabel();
  JComboBox jComboBox_LearnInput = new JComboBox();
  JComboBox jComboBox_TestInput = new JComboBox();
  JComboBox jComboBox_LearnOutput = new JComboBox();
  JComboBox jComboBox_TestOutput = new JComboBox();
  JPanel jPanel_strategy = new JPanel();
  JPanel jPanel_Topology = new JPanel();
  TitledBorder titledBorder3;
  TitledBorder titledBorder4;
  JLabel jLabel7 = new JLabel();
  JTextField jTextField_EndingEpoch = new JTextField();
  JLabel jLabel8 = new JLabel();
  JLabel jLabel9 = new JLabel();
  JLabel jLabel10 = new JLabel();
  JTextField jTextField_ErrorCriteria = new JTextField();
  JTextField jTextField_LearningRate = new JTextField();
  JTextField jTextField_MomentumRate = new JTextField();
  JLabel jLabel11 = new JLabel();
  JComboBox jComboBox_HiddenLayerNum = new JComboBox();
  JPanel jPanel6 = new JPanel();
  JList jList_HiddenNode = new JList();
  JLabel jLabel12 = new JLabel();
  JTextField jTextField_HiddenNodeNum = new JTextField();
  JPanel jPanel7 = new JPanel();
  JPanel jPanel8 = new JPanel();
  JScrollPane jScrollPane1 = new JScrollPane();
  JLabel jLabel13 = new JLabel();
  JPanel jPanel5 = new JPanel();
  JScrollPane jScrollPane2 = new JScrollPane();
  JTable jTable1 = new JTable();



 //*******************************
 //COLUMN SELECTION
 //*******************************

  int num_attributes; //DB의 Attributes수
  int num_rows;       //table의 row수(= DB의 Attributes수)
  int num_columns;    //column수(7개)


//////////========== 변수 세팅 ===========//////////////
  String  project ;
  String  metafile ;
  String  ProjectPath;
  String[]  m_InArcList    ;
  String[]	m_OutArcList   ;

  String[]  m_InArcName    ;
  String[]	m_OutArcName   ;

  String    ms_networkname ;//NETWORK NAME

  boolean   MethodLearn    ; // True :Learn, False :
  boolean   MethodTest     ; // True :Test, False :

  //TEST DATA INOUT SELECTION
  String	m_sSelectedLearnData   ;
  String    m_sSelectedLearnOutput  ;

  String   m_sSelectedTestData  ;
  String   m_sSelectedTestOutput ;

  //*******************************
  //PARAMETER SETTING(전략)
  //*******************************
   double  m_ErrorCriteria  ;
   int     m_EndingEpoch   ;
   double  m_LearningRate   ;
   double  m_MomentumRate   ;

  //*******************************
  //PARAMETER SETTING(토폴로지)
  //*******************************
   String []  m_HiddenNode = new String[3];
   int    m_HiddenLayerNum;

   //*******************************
  //PARAMETER SETTING(컬럼 선택)
  //*******************************

	Vector InputColumnList   = new  Vector();
	Vector OutputColumnList  = new  Vector();
	Vector InputColumnType   = new  Vector();
	Vector OutputColumnType  = new  Vector();
	Vector InputTransMethod  = new  Vector();
    Vector OutputTransMethod = new  Vector();


   Vector attriName = new Vector();
   Vector attriType = new Vector();
//////////========== 변수 세팅 ===========//////////////
///--------리스트 모델 선언-----///
  private XMMNodeNeuralNetwork NodeNeural; // =null;
  private NeuralTableModel NeuralTable = new NeuralTableModel();
  private ColumnData colData;
  private DefaultListModel listModel;
  FlowLayout flowLayout1 = new FlowLayout();
  Border border1;
  Border border2;
  BorderLayout borderLayout2 = new BorderLayout();
  ImageIcon imageBanner = new ImageIcon(xmminer.xmclient.xmgraph.xmmnode.xmmnodeassociationrule.assoInputDialog.class.getResource("../../../images/nn_banner.jpg"));
  JLabel jLabel1 = new JLabel(imageBanner);
  BorderLayout borderLayout3 = new BorderLayout();
  FlowLayout flowLayout2 = new FlowLayout();
  FlowLayout flowLayout3 = new FlowLayout();
  TitledBorder titledBorder5;
  BorderLayout borderLayout4 = new BorderLayout();
  BorderLayout borderLayout5 = new BorderLayout();
//////

  public XMMNodeNeuralDialog(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try  {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

public XMMNodeNeuralDialog(Frame frame, String title, boolean modal, XMMNodeNeuralNetwork _node)
  {
    super(frame, title, modal);
    NodeNeural = _node;
    num_columns = 7;
	m_InArcList  =  NodeNeural.m_InArcList;
	m_OutArcList =  NodeNeural.m_OutArcList;
	m_InArcName  =  NodeNeural.m_InArcName;
	m_OutArcName =  NodeNeural.m_OutArcName;

    try
    {
	  jbInit();
	  SetParameter();
      tablesetting();
	  setTableValue();  //기존에  컬럼 선택 테이블에서 선택했던 check 값을 세팅해줌
      pack();
    }
    catch(Exception ex)
    {
      System.out.println(ex.toString());
    }
  }


  public XMMNodeNeuralDialog() {
    this(null, "", false);
  }

  void jbInit() throws Exception {

   	int arraylength ;
 	arraylength=m_InArcList.length;
	for(int i=0;i<arraylength;i++)
    {
      jComboBox_LearnInput.addItem(m_InArcList[i]);
      jComboBox_TestInput.addItem(m_InArcList[i]);
    }

   arraylength=m_OutArcList.length;
	for(int i=0;i<arraylength;i++)
    {
      jComboBox_LearnOutput.addItem(m_OutArcList[i]);
      jComboBox_TestOutput.addItem(m_OutArcList[i]);
    }
   border1 = BorderFactory.createLineBorder(new Color(153, 153, 153),1);
    border2 = BorderFactory.createEmptyBorder();
    titledBorder5 = new TitledBorder("");
    jComboBox_LearnInput.setSelectedIndex(0);
    jComboBox_LearnInput.addItemListener(new java.awt.event.ItemListener() {

      public void itemStateChanged(ItemEvent e) {
        jComboBox_LearnInput_itemStateChanged(e);
      }
    });
   jComboBox_TestInput.setSelectedIndex(0);
   jComboBox_LearnOutput.setSelectedIndex(0);
   jComboBox_TestOutput.setSelectedIndex(0);

    jComboBox_HiddenLayerNum.addItem("1");
    jComboBox_HiddenLayerNum.addItem("2");
    jComboBox_HiddenLayerNum.addItem("3");

    jComboBox_HiddenLayerNum.setSelectedItem("2");
    jComboBox_HiddenLayerNum.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        jComboBox_HiddenLayerNum_actionPerformed(e);
      }
    });

    titledBorder1 = new TitledBorder(new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(142, 142, 142)),"학습,테스트");
    titledBorder2 = new TitledBorder(new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(142, 142, 142)),"입력 및 출력 파일 선택");
    titledBorder3 = new TitledBorder(new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(142, 142, 142)),"전략");
    titledBorder4 = new TitledBorder(new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(142, 142, 142)),"토폴로지");
    panel1.setLayout(borderLayout1);
    jTabbedPane1.setFont(new java.awt.Font("Dialog", 0, 12));
    jTabbedPane1.setPreferredSize(new Dimension(450, 340));
    jButton1.setFont(new java.awt.Font("Dialog", 0, 12));
    jButton1.setBorder(BorderFactory.createRaisedBevelBorder());
    jButton1.setPreferredSize(new Dimension(83, 29));
    jButton1.setText("확인");
    jButton1.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        jButton1_actionPerformed(e);
      }
    });
    jButton2.setFont(new java.awt.Font("Dialog", 0, 12));
    jButton2.setBorder(BorderFactory.createRaisedBevelBorder());
    jButton2.setPreferredSize(new Dimension(83, 29));
    jButton2.setText("취소");
    jButton2.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        jButton2_actionPerformed(e);
      }
    });
    jPanel_model.setPreferredSize(new Dimension(430, 35));
    jPanel3.setBorder(titledBorder1);
    jPanel3.setPreferredSize(new Dimension(480, 65));
    jPanel4.setBorder(titledBorder2);
    jPanel4.setPreferredSize(new Dimension(480, 170));
    jLabel2.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel2.setPreferredSize(new Dimension(220, 27));
    jLabel2.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel2.setText("네트워크이름");
    jTextField_Networkname.setPreferredSize(new Dimension(200, 25));
    jTextField_Networkname.setText("Untitiled_network");
    jPanel2.setBackground(new java.awt.Color(204, 204, 204));
    jPanel2.setBorder(BorderFactory.createEtchedBorder());
    jPanel2.setPreferredSize(new Dimension(480, 50));
    jCheckBox_Learn.setPreferredSize(new Dimension(200, 25));
    jCheckBox_Learn.setSelected(true);
    jCheckBox_Learn.setText("학습");
    jCheckBox_Learn.setHorizontalAlignment(SwingConstants.CENTER);
    jCheckBox_Learn.setFont(new java.awt.Font("Dialog", 0, 12));
    jCheckBox_Learn.addMouseListener(new java.awt.event.MouseAdapter() {

      public void mouseClicked(MouseEvent e) {
        jCheckBox_Learn_mouseClicked(e);
      }
    });
    jCheckBox_Test.setPreferredSize(new Dimension(200, 25));
    jCheckBox_Test.setText("테스트");
    jCheckBox_Test.setHorizontalAlignment(SwingConstants.CENTER);
    jCheckBox_Test.setFont(new java.awt.Font("Dialog", 0, 12));
    jCheckBox_Test.addMouseListener(new java.awt.event.MouseAdapter() {

      public void mouseClicked(MouseEvent e) {
        jCheckBox_Test_mouseClicked(e);
      }
    });
    jLabel3.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel3.setPreferredSize(new Dimension(200, 25));
    jLabel3.setText("학습 데이타");
    jLabel4.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel4.setPreferredSize(new Dimension(200, 25));
    jLabel4.setText("테스트 데이타");
    jLabel5.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel5.setPreferredSize(new Dimension(200, 25));
    jLabel5.setText("학습 결과 출력");
    jLabel6.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel6.setPreferredSize(new Dimension(200, 25));
    jLabel6.setText("테스트 결과 출력");
    jComboBox_LearnInput.setFont(new java.awt.Font("Dialog", 0, 12));
    jComboBox_LearnInput.setPreferredSize(new Dimension(200, 25));
    jComboBox_TestInput.setFont(new java.awt.Font("Dialog", 0, 12));
    jComboBox_TestInput.setPreferredSize(new Dimension(200, 25));
    jComboBox_LearnOutput.setFont(new java.awt.Font("Dialog", 0, 12));
    jComboBox_LearnOutput.setPreferredSize(new Dimension(200, 25));
    jComboBox_TestOutput.setFont(new java.awt.Font("Dialog", 0, 12));
    jComboBox_TestOutput.setPreferredSize(new Dimension(200, 25));
    jPanel_strategy.setBorder(titledBorder3);
    jPanel_strategy.setPreferredSize(new Dimension(480, 150));
    jPanel_Topology.setBorder(titledBorder4);
    jPanel_Topology.setPreferredSize(new Dimension(480, 140));
    jLabel7.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel7.setPreferredSize(new Dimension(200, 25));
    jLabel7.setText("종료 주기");
    jTextField_EndingEpoch.setPreferredSize(new Dimension(200, 25));
    jTextField_EndingEpoch.setText("100");
    jLabel8.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel8.setPreferredSize(new Dimension(200, 25));
    jLabel8.setText("에러 범위");
    jLabel9.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel9.setPreferredSize(new Dimension(200, 25));
    jLabel9.setText("학습률 가중치");
    jLabel10.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel10.setPreferredSize(new Dimension(200, 25));
    jLabel10.setText("모멘텀 가중치");
    jTextField_ErrorCriteria.setPreferredSize(new Dimension(200, 25));
    jTextField_ErrorCriteria.setText("0.1");
    jTextField_LearningRate.setPreferredSize(new Dimension(200, 25));
    jTextField_LearningRate.setText("0.5");
    jTextField_MomentumRate.setPreferredSize(new Dimension(200, 25));
    jTextField_MomentumRate.setText("0.9");
    jLabel11.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel11.setPreferredSize(new Dimension(200, 25));
    jLabel11.setText("은닉층의 수");
    jComboBox_HiddenLayerNum.setFont(new java.awt.Font("Dialog", 0, 12));
    jComboBox_HiddenLayerNum.setPreferredSize(new Dimension(200, 25));
    jPanel6.setPreferredSize(new Dimension(200, 105));
    jList_HiddenNode.setPreferredSize(new Dimension(180, 64));
    jList_HiddenNode.addMouseListener(new java.awt.event.MouseAdapter() {

      public void mouseClicked(MouseEvent e) {
        jList_HiddenNode_mouseClicked(e);
      }
    });
    jList_HiddenNode.addInputMethodListener(new java.awt.event.InputMethodListener() {

      public void caretPositionChanged(InputMethodEvent e) {
        jList_HiddenNode_caretPositionChanged(e);
      }

      public void inputMethodTextChanged(InputMethodEvent e) {
      }
    });
    jLabel12.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel12.setPreferredSize(new Dimension(90, 20));
    jLabel12.setText("노드의 수");
    jTextField_HiddenNodeNum.setPreferredSize(new Dimension(90, 22));
    jTextField_HiddenNodeNum.setText("2");
    jTextField_HiddenNodeNum.addFocusListener(new java.awt.event.FocusAdapter() {

      public void focusLost(FocusEvent e) {
        jTextField_HiddenNodeNum_focusLost(e);
      }
    });
    jTextField_HiddenNodeNum.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        jTextField_HiddenNodeNum_actionPerformed(e);
      }
    });
    jPanel7.setBackground(new java.awt.Color(204, 204, 204));
    jPanel7.setPreferredSize(new Dimension(200, 100));
    jPanel8.setBackground(new java.awt.Color(204, 204, 204));
    jPanel8.setPreferredSize(new Dimension(200, 100));
    jScrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    jScrollPane1.setPreferredSize(new Dimension(180, 64));
    jLabel13.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel13.setPreferredSize(new Dimension(180, 30));
    jPanel5.setPreferredSize(new Dimension(440, 300));
    jPanel5.setLayout(borderLayout5);
    jScrollPane2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    jScrollPane2.setPreferredSize(new Dimension(430, 290));
    jTable1.addMouseListener(new java.awt.event.MouseAdapter() {

      public void mouseClicked(MouseEvent e) {
        jTable1_mouseClicked(e);
      }
    });
    jPanel_bottom.setLayout(flowLayout1);
    flowLayout1.setAlignment(FlowLayout.RIGHT);
    flowLayout1.setHgap(10);
    flowLayout1.setVgap(10);
    jPanel_center.setLayout(borderLayout2);
    jLabel1.setFont(new java.awt.Font("Dialog", 0, 18));
    jLabel1.setBorder(BorderFactory.createEtchedBorder());
    jLabel1.setPreferredSize(new Dimension(500, 50));
    jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel1.setHorizontalTextPosition(SwingConstants.CENTER);
    jPanel_top.setLayout(borderLayout3);
    jPanel_top.setPreferredSize(new Dimension(500, 50));
    jPanel_Method.setLayout(flowLayout2);
    flowLayout2.setHgap(0);
    jPanel_parameter.setLayout(flowLayout3);
    flowLayout3.setHgap(0);
    jPanel_column_sel.setLayout(borderLayout4);
    jPanel_bottom.setBorder(BorderFactory.createEtchedBorder());
    getContentPane().add(panel1);
    panel1.add(jPanel_bottom, BorderLayout.SOUTH);
    jPanel_bottom.add(jButton1, null);
    jPanel_bottom.add(jButton2, null);
    panel1.add(jPanel_center, BorderLayout.CENTER);
    jPanel_center.add(jTabbedPane1, BorderLayout.CENTER);
    jTabbedPane1.add(jPanel_Method, "방법론 세팅");
    jPanel_Method.add(jPanel2, null);
    jPanel2.add(jPanel_model, null);
    jPanel_model.add(jLabel2, null);
    jPanel_model.add(jTextField_Networkname, null);
    jPanel_Method.add(jPanel3, null);
    jPanel3.add(jCheckBox_Learn, null);
    jPanel3.add(jCheckBox_Test, null);
    jPanel_Method.add(jPanel4, null);
    jPanel4.add(jLabel3, null);
    jPanel4.add(jLabel4, null);
    jPanel4.add(jComboBox_LearnInput, null);
    jPanel4.add(jComboBox_TestInput, null);
    jPanel4.add(jLabel5, null);
    jPanel4.add(jLabel6, null);
    jPanel4.add(jComboBox_LearnOutput, null);
    jPanel4.add(jComboBox_TestOutput, null);
    jTabbedPane1.add(jPanel_parameter, "입력파라미터 세팅");
    jPanel_parameter.add(jPanel_strategy, null);
    jPanel_strategy.add(jLabel8, null);
    jPanel_strategy.add(jLabel7, null);
    jPanel_strategy.add(jTextField_ErrorCriteria, null);
    jPanel_strategy.add(jTextField_EndingEpoch, null);
    jPanel_strategy.add(jLabel9, null);
    jPanel_strategy.add(jLabel10, null);
    jPanel_strategy.add(jTextField_LearningRate, null);
    jPanel_strategy.add(jTextField_MomentumRate, null);
    jPanel_parameter.add(jPanel_Topology, null);
    jPanel_Topology.add(jPanel7, null);
    jPanel7.add(jLabel11, null);
    jPanel7.add(jComboBox_HiddenLayerNum, null);
    jPanel7.add(jLabel13, null);
    jPanel_Topology.add(jPanel8, null);
    jPanel8.add(jPanel6, null);
    jPanel6.add(jLabel12, null);
    jPanel6.add(jTextField_HiddenNodeNum, null);
    jPanel6.add(jScrollPane1, null);
    jScrollPane1.getViewport().add(jList_HiddenNode, null);
    jTabbedPane1.add(jPanel_column_sel, "컬럼 선택");
    jPanel_column_sel.add(jPanel5, BorderLayout.CENTER);
    jPanel5.add(jScrollPane2, BorderLayout.CENTER);
    jScrollPane2.getViewport().add(jTable1, null);
    panel1.add(jPanel_top, BorderLayout.NORTH);
    jPanel_top.add(jLabel1, BorderLayout.CENTER);

    this.setResizable(false);
  }

////////////////////////////////////////////////////////////////////////////////
//
// SetParameter을 생성하는 함수
//
/////////////////////////////////////////////////////////////////////////////////
  private void SetParameter()
  {
    ms_networkname = NodeNeural.ms_networkname  ;//NETWORK NAME

	project   =  NodeNeural.ProjectName;
	metafile     = NodeNeural.MetaFileName;
	ProjectPath  = NodeNeural.m_Projectpath;
//    metafile  = NodeNeural.m_InArcList[0];  //MetaFileName;


    MethodLearn =  NodeNeural.MethodLearn  ; // True :Learn, False :
    MethodTest =   NodeNeural.MethodTest ; // True :Test, False :

  //TEST DATA INOUT SELECTION
	m_sSelectedLearnData   = NodeNeural.m_sSelectedLearnData;
    m_sSelectedLearnOutput = NodeNeural.m_sSelectedLearnOutput;

    m_sSelectedTestData   =  NodeNeural.m_sSelectedTestData;
    m_sSelectedTestOutput = NodeNeural.m_sSelectedTestOutput;

  //*******************************
  //PARAMETER SETTING(전략)
  //*******************************
     m_ErrorCriteria  =   NodeNeural.m_ErrorCriteria;
     m_EndingEpoch    =   NodeNeural.m_EndingEpoch;
     m_LearningRate   =   NodeNeural.m_LearningRate;
     m_MomentumRate   =   NodeNeural.m_MomentumRate;

  //*******************************
  //PARAMETER SETTING(토폴로지)
  //*******************************
     m_HiddenNode =  NodeNeural.m_HiddenNode;
     m_HiddenLayerNum = NodeNeural.m_HiddenLayerNum;
  //*******************************
  //COLUMN SELECTION
  //*******************************
	InputColumnList		=	NodeNeural.InputColumnList;
	OutputColumnList	=	NodeNeural.OutputColumnList;
	InputColumnType		=	NodeNeural.InputColumnType	;
 	OutputColumnType	=	NodeNeural.OutputColumnType	;
 	InputTransMethod	=	NodeNeural.InputTransMethod	;
   	OutputTransMethod	=	NodeNeural.OutputTransMethod;

//////////////////////////////////////////////

   jTextField_Networkname.setText(ms_networkname);


   jCheckBox_Learn.setSelected(MethodLearn);
   jCheckBox_Test.setSelected(MethodTest);

	int arraylength,LearnArcPos = -1,TestArcPos = -1;
 	arraylength=m_InArcList.length;
	for(int i=0;i<arraylength;i++)
    {
	  if (m_InArcList[i].equals(m_sSelectedLearnData))
	      LearnArcPos = i;
	  if (m_InArcList[i].equals(m_sSelectedTestData))
	      TestArcPos = i;
     }

    if (LearnArcPos < 0)
          jComboBox_LearnInput.setSelectedIndex(0);
    else
	      jComboBox_LearnInput.setSelectedIndex(LearnArcPos);

    if (TestArcPos < 0)
          jComboBox_TestInput.setSelectedIndex(0);
    else
	      jComboBox_TestInput.setSelectedIndex(TestArcPos);


 	arraylength=m_OutArcList.length;
    LearnArcPos = -1;
    TestArcPos  = -1;

	for(int i=0;i<arraylength;i++)
    {
	  if (m_OutArcList[i].equals(m_sSelectedLearnOutput))
	      LearnArcPos = i;
	  if (m_OutArcList[i].equals(m_sSelectedTestOutput))
	      TestArcPos = i;
     }

    if (LearnArcPos < 0)
          jComboBox_LearnOutput.setSelectedIndex(0);
    else
	      jComboBox_LearnOutput.setSelectedIndex(LearnArcPos);

    if (TestArcPos < 0)
          jComboBox_TestOutput.setSelectedIndex(0);
    else
	      jComboBox_TestOutput.setSelectedIndex(TestArcPos);


   jComboBox_LearnInput.setEnabled(MethodLearn);
   jComboBox_LearnOutput.setEnabled(MethodLearn);
   jTextField_ErrorCriteria.setEnabled(MethodLearn);
   jTextField_EndingEpoch.setEnabled(MethodLearn);
   jTextField_LearningRate.setEnabled(MethodLearn);
   jTextField_MomentumRate.setEnabled(MethodLearn);
   jComboBox_HiddenLayerNum.setEnabled(MethodLearn);
   jTextField_HiddenNodeNum.setEnabled(MethodLearn);
   jList_HiddenNode.setEnabled(MethodLearn);
   jTable1.setEnabled(MethodLearn);


   jComboBox_TestInput.setEnabled(MethodTest);
   jComboBox_TestOutput.setEnabled(MethodTest);


	Double  md_ErrorCriteria = new Double(m_ErrorCriteria);
	String  ms_ErrorCriteria = new String(md_ErrorCriteria.toString());

	Integer mi_EndingEpoch  = new Integer(m_EndingEpoch);
    String  ms_EndingEpoch = new String(mi_EndingEpoch.toString());

	Double  md_LearningRate = new Double(m_LearningRate);
    String  ms_LearningRate = new String(md_LearningRate.toString());

	Double  md_MomentumRate = new Double(m_MomentumRate);
    String  ms_MomentumRate= new String(md_MomentumRate.toString());

   jTextField_ErrorCriteria.setText(ms_ErrorCriteria);
   jTextField_EndingEpoch.setText(ms_EndingEpoch);
   jTextField_LearningRate.setText(ms_LearningRate);
   jTextField_MomentumRate.setText(ms_MomentumRate);

   jComboBox_HiddenLayerNum.setSelectedIndex(m_HiddenLayerNum-1);

   setList();

   jTextField_HiddenNodeNum.setText(m_HiddenNode[0]);

   return;
  }
    ////----------리스트  값 세팅 ---------/////
 private void setList()
 {
   listModel = new DefaultListModel();

  for(int i=1;i<=m_HiddenLayerNum;i++)
      listModel.addElement("HiddenLayer #"+i);

   jList_HiddenNode.setModel(listModel);   //     = new JList(listModel);

   jList_HiddenNode.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
   jList_HiddenNode.setSelectedIndex(0);

  return;
 }

 private void setTableValue()
  {
  int inputNum,outputNum;
   	inputNum = InputColumnList.size() ;
	outputNum = OutputColumnList.size();

    if (inputNum <= 0 || outputNum <= 0)
    	return;
	String temp;
	int val;
	int index ;
    for(int i =0 ;i < attriName.size() ; i++)
	{
	  temp = (String) attriName.elementAt(i);
	  index = InputColumnList.indexOf(temp);
	  if (index != -1)
	   { NeuralTable.setValueAt(new Boolean(true),i,4);
 		 NeuralTable.setValueAt(new Boolean(false),i,6);
		 NeuralTable.setValueAt((String)InputTransMethod.elementAt(index),i,3);
		}
	  index = OutputColumnList.indexOf(temp);
	  if (index != -1)
	   { NeuralTable.setValueAt(new Boolean(true),i,5);
 		 NeuralTable.setValueAt(new Boolean(false),i,6);
		 NeuralTable.setValueAt((String)OutputTransMethod.elementAt(index),i,3);
		}
	}

 }

private int category(String category){
// val = category((String)InputTransMethod.elementAt(index));
String[] categories = {
	  "One of N Codes","Boolean", "Normalize"
	  };
  int i;
  for( i=0;i < 3 ;i ++)
	 if(categories[i].equals(category))
		  break;
  return i;
}


 private void tablesetting(){
	getAttributesList();
    jTable1.setAutoCreateColumnsFromModel(false);
    NeuralTable.setData(attriName, attriType);

    jTable1.setModel(NeuralTable);
    jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    for (int k = 0; k < NeuralTableModel.m_columns.length; k++) {
	 TableCellRenderer renderer = new DefaultTableCellRenderer();

      if (k==NeuralTableModel.COL_INPUT_NODE || k== NeuralTableModel.COL_OUTPUT_NODE || k== NeuralTableModel.COL_NOT_USE )
        renderer = new CheckCellRenderer();
      else {
        DefaultTableCellRenderer textRenderer =
          new DefaultTableCellRenderer();
        textRenderer.setHorizontalAlignment(
          NeuralTableModel.m_columns[k].m_alignment);
        renderer = textRenderer;
      }

      TableCellEditor editor;

      if (k==NeuralTableModel.COL_TRANS_FUNC)
        editor = new DefaultCellEditor(new JComboBox(NeuralTableModel.CATEGORIES));
      else if (k==NeuralTableModel.COL_INPUT_NODE || k== NeuralTableModel.COL_OUTPUT_NODE ||k== NeuralTableModel.COL_NOT_USE)
        editor = new DefaultCellEditor(new JCheckBox());
      else
        editor = new DefaultCellEditor(new JTextField());

      TableColumn column = new TableColumn(k,NeuralTableModel.m_columns[k].m_width,renderer, editor);
      jTable1.addColumn(column);
     }


 return;
 }

private void  getAttributesList(){
	Vector temp = new Vector();
    attriName.clear();
	attriType.clear();

    String colNumStr = NodeNeural.m_sMNodeNeuralNetwork.getProfile(project,metafile,"NUMBER_OF_COLUMNS"); //attributes 수
    String colList =  NodeNeural.m_sMNodeNeuralNetwork.getProfile(project,metafile,"COLUMN_LIST");
    String newColList = NodeNeural.m_sMNodeNeuralNetwork.getProfile(project,metafile,"NEW_COLUMN_LIST");
    String colIndex = NodeNeural.m_sMNodeNeuralNetwork.getProfile(project,metafile,"COLUMN_INDEX");
    String newColIndex=null;

	if(!newColList.equals("null")) {
	    newColIndex = NodeNeural.m_sMNodeNeuralNetwork.getProfile(project,metafile,"NEW_COLUMN_INDEX");
  		}

     //table의 row수를 setting한다.(table의 column 수는 4개로 일정)
    StringTokenizer tokend_colIndex = new StringTokenizer(colIndex, "^");
    num_rows = tokend_colIndex.countTokens();

	StringTokenizer tokend_newColIndex =null;
	if(!newColList.equals("null")) {
	   tokend_newColIndex = new StringTokenizer(newColIndex, "^");
       num_rows += tokend_newColIndex.countTokens();
	}

    //DB의 Attributes 수를 set한다.
    num_attributes = num_rows;    //table의 row수(= DB의 Attributes수)
  //  System.out.println("getAttributesList # 6 ");
    //colList와 colIndex를 가지고 attribute name을 구한다.

    StringTokenizer tokend_colList = new StringTokenizer(colList, "^");
    while(tokend_colList.hasMoreElements()){
      temp.add( (String)tokend_colList.nextElement() ); //column list를 temp 벡터에 넣는다.
    }
    while(tokend_colIndex.hasMoreElements()){ //column index에 존재하는 column name만을 가져온다.
      int index = Integer.parseInt( (String)tokend_colIndex.nextElement());
      attriName.add( (String)temp.elementAt(index-1) );
    }
    //newColList와 newColIndex를 가지고 나머지 attribute name을 구한다.
    temp.clear();

	if(!newColList.equals("null"))
	{
		StringTokenizer tokend_newColList = new StringTokenizer(newColList, "^");
		while(tokend_newColList.hasMoreElements()){
		  temp.add( (String)tokend_newColList.nextElement() ); //column list를 temp 벡터에 넣는다.
		}
		while(tokend_newColIndex.hasMoreElements()){ //column index에 존재하는 column name만을 가져온다.
		  int index = Integer.parseInt( (String)tokend_newColIndex.nextElement());
		  attriName.add( (String)temp.elementAt(index-1) );
		}
	}

    //column의 type을 구해 attriType Vector에 저장한다.
    String colName, colType, colProperty;
    int size = attriName.size();

    for(int i = 0; i < size; i++){
      colName = (String)attriName.elementAt(i);
      colProperty = NodeNeural.m_sMNodeNeuralNetwork.getProfile(project,metafile,colName); //해당 column 속성을 읽는다.
      StringTokenizer tokend_colProperty = new StringTokenizer(colProperty, "^");
      colType = (String)tokend_colProperty.nextElement(); //column 속성에서 column type을 얻는다.
      attriType.add(colType);
    }


return;
}


  void jButton1_actionPerformed(ActionEvent e) {        //ok 버튼
    //NETWORK NAME SETTING
    ms_networkname=jTextField_Networkname.getText();
    if ( (ms_networkname.length() == 0)) {
        MessageBox  err_box = new MessageBox(this,"에러메시지",true,
              "입력 에러"," 네트워크 이름을 입력하여 주십시오 ");
        java.awt.Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        java.awt.Dimension frameSize = err_box.getSize();
        if (frameSize.height > screenSize.height)
            frameSize.height = screenSize.height;
        if (frameSize.width > screenSize.width)
            frameSize.width = screenSize.width;
        err_box.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
        err_box.setVisible(true);
        return;
      }

    //METHODLEARN SETTING
    if(jCheckBox_Learn.isSelected())  MethodLearn =true;
    else MethodLearn =false;
    //MOTHODTEST SETTING
    if(jCheckBox_Test.isSelected())  MethodTest =true;
    else MethodTest =false;
    //LEARN INPUT SETTING
	int iLearnData=jComboBox_LearnInput.getSelectedIndex();
    m_sSelectedLearnData=(String)jComboBox_LearnInput.getItemAt(iLearnData);
    //LEARN OUTPUT SETTING
    int iLearnOutput=jComboBox_LearnOutput.getSelectedIndex();
    m_sSelectedLearnOutput=(String)jComboBox_LearnOutput.getItemAt(iLearnOutput);
    //TEST INPUT SETTING
    int iTestData=jComboBox_TestInput.getSelectedIndex();
    m_sSelectedTestData=(String)jComboBox_TestInput.getItemAt(iTestData);
    //TEST OUTPUT SETTING
    int iTestOutput=jComboBox_TestOutput.getSelectedIndex();
    m_sSelectedTestOutput=(String)jComboBox_TestOutput.getItemAt(iTestOutput);

	String sSelectedLearnData   = m_InArcName[iLearnData];
	String sSelectedLearnOutput = m_OutArcName[iLearnOutput];
	String sSelectedTestData    = m_InArcName[iTestData];
	String sSelectedTestOutput  = m_OutArcName[iTestOutput];
    //
    //PARAMETER SETTING
    //
    //m_ErrorCriteria
    String ms_ErrorCriteria=jTextField_ErrorCriteria.getText();
	Double  md_ErrorCriteria=new Double(ms_ErrorCriteria);
	  m_ErrorCriteria=md_ErrorCriteria.doubleValue();
    //m_EndingEpoches
    String ms_EndingEpoch=jTextField_EndingEpoch.getText();
	Integer  mi_EndingEpoch=new Integer(ms_EndingEpoch);
	  m_EndingEpoch=mi_EndingEpoch.intValue();
    //m_LearningRate
    String ms_LearningRate=jTextField_LearningRate.getText();
	Double  md_LearningRate=new Double(ms_LearningRate);
	  m_LearningRate=md_LearningRate.doubleValue();
    //m_MomentumRate
    String ms_MomentumRate=jTextField_MomentumRate.getText();
	Double  md_MomentumRate=new Double(ms_MomentumRate);
	  m_MomentumRate=md_MomentumRate.doubleValue();

    String Learn_rowNum = NodeNeural.m_sMNodeNeuralNetwork.getProfile(project,sSelectedLearnData,"NUMBER_OF_ROWS");
    String Test_rowNum = NodeNeural.m_sMNodeNeuralNetwork.getProfile(project,sSelectedTestData,"NUMBER_OF_ROWS");
    String parameterFile = ms_networkname+"_par";
    NodeNeural.m_sMNodeNeuralNetwork.setProfile(project,parameterFile,"PROJECT_NAME",project);
    NodeNeural.m_sMNodeNeuralNetwork.setProfile(project,parameterFile,"PROJECT_PATH",ProjectPath);
	NodeNeural.m_sMNodeNeuralNetwork.setProfile(project,parameterFile,"NETWORK_NAME",ms_networkname);
    if(MethodLearn)	NodeNeural.m_sMNodeNeuralNetwork.setProfile(project,parameterFile,"METHODLEARN","true");
    else NodeNeural.m_sMNodeNeuralNetwork.setProfile(project,parameterFile,"METHODLEARN","false");
	if(MethodTest)	NodeNeural.m_sMNodeNeuralNetwork.setProfile(project,parameterFile,"METHODTEST","true");
	else	NodeNeural.m_sMNodeNeuralNetwork.setProfile(project,parameterFile,"METHODTEST","false");
	NodeNeural.m_sMNodeNeuralNetwork.setProfile(project,parameterFile,"LEARNDATA",sSelectedLearnData);
	NodeNeural.m_sMNodeNeuralNetwork.setProfile(project,parameterFile,"TESTDATA",sSelectedTestData);
	NodeNeural.m_sMNodeNeuralNetwork.setProfile(project,parameterFile,"LEARNRESULT",sSelectedLearnOutput);
	NodeNeural.m_sMNodeNeuralNetwork.setProfile(project,parameterFile,"TESTRESULT",sSelectedTestOutput);
	NodeNeural.m_sMNodeNeuralNetwork.setProfile(project,parameterFile,"ERRORCRITERIA",String.valueOf(m_ErrorCriteria));
	NodeNeural.m_sMNodeNeuralNetwork.setProfile(project,parameterFile,"ENDINGEPOCH",String.valueOf(m_EndingEpoch));
	NodeNeural.m_sMNodeNeuralNetwork.setProfile(project,parameterFile,"LEARNINGRATE",String.valueOf(m_LearningRate));
	NodeNeural.m_sMNodeNeuralNetwork.setProfile(project,parameterFile,"MOMENTUMRATE",String.valueOf(m_MomentumRate));
	NodeNeural.m_sMNodeNeuralNetwork.setProfile(project,parameterFile,"HIDDENLAYERNUM",String.valueOf(m_HiddenLayerNum));
	NodeNeural.m_sMNodeNeuralNetwork.setProfiles(project,parameterFile,"HIDDENNODE",m_HiddenNode);
	NodeNeural.m_sMNodeNeuralNetwork.setProfile(project,parameterFile,"NUMBER_OF_LEARN_DATA_SET",Learn_rowNum);
	NodeNeural.m_sMNodeNeuralNetwork.setProfile(project,parameterFile,"NUMBER_OF_TEST_DATA_SET",Test_rowNum);
	int attributeNum = attriName.size();

	InputColumnList.removeAllElements();
    OutputColumnList.removeAllElements();
	InputColumnType.removeAllElements();
	OutputColumnType.removeAllElements();
	InputTransMethod.removeAllElements();
    OutputTransMethod.removeAllElements();

	int inputNum = 0;
	int outputNum = 0;
	Boolean booleanVal ;
	for (int nRow=0; nRow < attributeNum ; nRow++)
     {
	        booleanVal = (Boolean) NeuralTable.getValueAt(nRow,4);
		    if (booleanVal.booleanValue())
	           {
		         InputColumnList.addElement((String)NeuralTable.getValueAt(nRow,1));
				 InputColumnType.addElement((String)NeuralTable.getValueAt(nRow,2));
				 InputTransMethod.addElement((String)NeuralTable.getValueAt(nRow,3));
				 inputNum++;
			   }
	        booleanVal = (Boolean) NeuralTable.getValueAt(nRow,5);
		     if (booleanVal.booleanValue())
			 {
		         OutputColumnList.addElement((String)NeuralTable.getValueAt(nRow,1));
				 OutputColumnType.addElement((String)NeuralTable.getValueAt(nRow,2));
				 OutputTransMethod.addElement((String)NeuralTable.getValueAt(nRow,3));
				 outputNum++;
			}
     }

	String[] sInputColumnList   = new String[inputNum];
	String[] sOutputColumnList  = new String[outputNum];
	String[] sInputColumnType   = new String[inputNum];
	String[] sOutputColumnType  = new String[outputNum];
	String[] sInputTransMethod  = new String[inputNum];
	String[] sOutputTransMethod = new String[outputNum];
    for(int i=0;i<inputNum;i++)
	{   sInputColumnList[i]  = (String) InputColumnList.elementAt(i);
		sInputColumnType[i]  = (String)InputColumnType.elementAt(i);
		sInputTransMethod[i] = (String)InputTransMethod.elementAt(i);
	}
	for(int i=0;i<outputNum;i++)
	{   sOutputColumnList[i]  = (String)OutputColumnList.elementAt(i);
		sOutputColumnType[i]  = (String)OutputColumnType.elementAt(i);
		sOutputTransMethod[i] = (String)OutputTransMethod.elementAt(i);
	}

    if (inputNum > 0 || outputNum > 0)
    {
		NodeNeural.m_sMNodeNeuralNetwork.setProfiles(project,parameterFile,"INPUT_COLUMN_LIST",sInputColumnList);
		NodeNeural.m_sMNodeNeuralNetwork.setProfiles(project,parameterFile,"OUTPUT_COLUMN_LIST",sOutputColumnList);
		NodeNeural.m_sMNodeNeuralNetwork.setProfiles(project,parameterFile,"INPUT_COLUMN_TYPE",sInputColumnType);
		NodeNeural.m_sMNodeNeuralNetwork.setProfiles(project,parameterFile,"OUTPUT_COLUMN_TYPE",sOutputColumnType);
		NodeNeural.m_sMNodeNeuralNetwork.setProfiles(project,parameterFile,"INPUT_TRANS_METHOD",sInputTransMethod);
		NodeNeural.m_sMNodeNeuralNetwork.setProfiles(project,parameterFile,"OUTPUT_TRANS_METHOD",sOutputTransMethod);
	}
	else
	{    MessageBox  err_box = new MessageBox(this,"에러메시지",true,"입력 에러","  반드시 입력노드와 출력노드는 선택하여야 합니다.");
         java.awt.Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
         java.awt.Dimension frameSize = err_box.getSize();
        if (frameSize.height > screenSize.height)
            frameSize.height = screenSize.height;
        if (frameSize.width > screenSize.width)
            frameSize.width = screenSize.width;
        err_box.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
        err_box.setVisible(true);
        return;
    }

    NodeNeural.ms_networkname   = ms_networkname  ;//NETWORK NAME
    NodeNeural.MethodLearn   = MethodLearn ; // True :Learn, False :
    NodeNeural.MethodTest    =  MethodTest; // True :Test, False :

  //TEST DATA INOUT SELECTION
	NodeNeural.m_sSelectedLearnData  = m_sSelectedLearnData;
    NodeNeural.m_sSelectedLearnOutput = m_sSelectedLearnOutput;

    NodeNeural.m_sSelectedTestData  = m_sSelectedTestData;
    NodeNeural.m_sSelectedTestOutput = m_sSelectedTestOutput;


  //*******************************
  //PARAMETER SETTING(전략)
  //*******************************
    NodeNeural.m_ErrorCriteria =  m_ErrorCriteria;
    NodeNeural.m_EndingEpoch   =  m_EndingEpoch;
    NodeNeural.m_LearningRate  =  m_LearningRate;
    NodeNeural.m_MomentumRate  =  m_MomentumRate;

  //*******************************
  //PARAMETER SETTING(토폴로지)
  //*******************************
    NodeNeural.m_HiddenNode = m_HiddenNode;
	NodeNeural.m_HiddenLayerNum = m_HiddenLayerNum;
  //*******************************
  //COLUMN SELECTION
  //*******************************
  	NodeNeural.InputColumnList		=   InputColumnList ;
    NodeNeural.OutputColumnList		=	OutputColumnList;
	NodeNeural.InputColumnType		=	InputColumnType;
	NodeNeural.OutputColumnType		=	OutputColumnType;
	NodeNeural.InputTransMethod		=	InputTransMethod;
    NodeNeural.OutputTransMethod	=	OutputTransMethod;
    NodeNeural.bSaved               = true;
    NodeNeural.setParameterName(parameterFile,true,ms_networkname);
    NodeNeural.m_sMNodeNeuralNetwork.setSchema(NodeNeural.ProjectName,parameterFile);
    this.dispose();

  }

  void jButton2_actionPerformed(ActionEvent e) {    //cancel
    this.dispose();

  }



  void jList_HiddenNode_caretPositionChanged(InputMethodEvent e) {
      jTextField_HiddenNodeNum.setText(m_HiddenNode[jList_HiddenNode.getSelectedIndex()]);
	  return;
  }

  void jCheckBox_Test_mouseClicked(MouseEvent e) {
    if(jCheckBox_Test.isSelected())
    {
      jComboBox_TestInput.setEnabled(true);
      jComboBox_TestOutput.setEnabled(true);

    }
    else
    {
      jComboBox_TestInput.setEnabled(false);
      jComboBox_TestOutput.setEnabled(false);
    }
  }

  void jCheckBox_Learn_mouseClicked(MouseEvent e) {
   if(jCheckBox_Learn.isSelected())
    {
      jComboBox_LearnInput.setEnabled(true);
      jComboBox_LearnOutput.setEnabled(true);

      jTextField_ErrorCriteria.setEnabled(true);
      jTextField_EndingEpoch.setEnabled(true);
      jTextField_LearningRate.setEnabled(true);
      jTextField_MomentumRate.setEnabled(true);
      jComboBox_HiddenLayerNum.setEnabled(true);
      jTextField_HiddenNodeNum.setEnabled(true);
      jList_HiddenNode.setEnabled(true);
	  jTable1.setEnabled(true);
    }
    else
    {
      jComboBox_LearnInput.setEnabled(false);
      jComboBox_LearnOutput.setEnabled(false);

      jTextField_ErrorCriteria.setEnabled(false);
      jTextField_EndingEpoch.setEnabled(false);
      jTextField_LearningRate.setEnabled(false);
      jTextField_MomentumRate.setEnabled(false);
      jComboBox_HiddenLayerNum.setEnabled(false);
      jTextField_HiddenNodeNum.setEnabled(false);
      jList_HiddenNode.setEnabled(false);
	  jTable1.setEnabled(false);
    }
	return;

  }

  void jTextField_HiddenNodeNum_actionPerformed(ActionEvent e) {

    if (Integer.parseInt(jTextField_HiddenNodeNum.getText())<0)
      {
        MessageBox  err_box = new MessageBox(this,"에러메시지",true,"입력 값 에러","  정수를 입력 하세요");
         java.awt.Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
         java.awt.Dimension frameSize = err_box.getSize();
        if (frameSize.height > screenSize.height)
            frameSize.height = screenSize.height;
        if (frameSize.width > screenSize.width)
            frameSize.width = screenSize.width;
        err_box.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
        err_box.setVisible(true);
        return;
       }

	 m_HiddenNode[jList_HiddenNode.getSelectedIndex()] = jTextField_HiddenNodeNum.getText();
  }


  void jTextField_HiddenNodeNum_focusLost(FocusEvent e) {
     m_HiddenNode[jList_HiddenNode.getSelectedIndex()] = jTextField_HiddenNodeNum.getText();
  }


  void jComboBox_HiddenLayerNum_actionPerformed(ActionEvent e) {
    listModel = new DefaultListModel();
  	if(jComboBox_HiddenLayerNum.getSelectedIndex()==0) m_HiddenLayerNum=1;
    else if(jComboBox_HiddenLayerNum.getSelectedIndex()==1) m_HiddenLayerNum=2;
    else m_HiddenLayerNum=3;

	  for(int i=1;i<=m_HiddenLayerNum;i++)
		  listModel.addElement("HiddenLayer #"+i);
     jList_HiddenNode.setModel(listModel);   //     = new JList(listModel);
     jList_HiddenNode.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	 jList_HiddenNode.setSelectedIndex(0);

     jTextField_HiddenNodeNum.setText(m_HiddenNode[0]);

  }

  void jList_HiddenNode_mouseClicked(MouseEvent e) {
      jTextField_HiddenNodeNum.setText(m_HiddenNode[jList_HiddenNode.getSelectedIndex()]);
	  return;
  }

  void jTable1_mouseClicked(MouseEvent e) {
     if(MethodLearn)
    {    int x=0,y=0;
	     Boolean booleanVal;
          x=jTable1.getSelectedRow();
          y=jTable1.getSelectedColumn();
          if(y==4&&x>=0)
          {  booleanVal = (Boolean) NeuralTable.getValueAt(x,y);
		     if (booleanVal.booleanValue())
	           {
 			     NeuralTable.setValueAt(new Boolean(false),x,5);
		         NeuralTable.setValueAt(new Boolean(false),x,6);
			   }
	       }
          else if(y==5&&x>=0)
          {  booleanVal = (Boolean) NeuralTable.getValueAt(x,y);
		     if (booleanVal.booleanValue())
			 {
 			   NeuralTable.setValueAt(new Boolean(false),x,4);
		       NeuralTable.setValueAt(new Boolean(false),x,6);
			  }
	       }
          else if(y==6 && x >=0)
		    { booleanVal = (Boolean) NeuralTable.getValueAt(x,y);
			  if(booleanVal.booleanValue())
			  {
			    NeuralTable.setValueAt(new Boolean(false),x,4);
  		        NeuralTable.setValueAt(new Boolean(false),x,5);
			  }
			}
     }
	 jTable1.repaint();
  }

  void jComboBox_LearnInput_itemStateChanged(ItemEvent e) {
       m_sSelectedLearnData = m_InArcList[jComboBox_LearnInput.getSelectedIndex()];
       metafile      =  m_sSelectedLearnData;
       tablesetting();
  }

}  // class 마지막 statement


class CheckCellRenderer extends JCheckBox implements TableCellRenderer
{
  protected static Border m_noFocusBorder;

  public CheckCellRenderer() {
    super();
    m_noFocusBorder = new EmptyBorder(1, 2, 1, 2);
    setOpaque(true);
    setBorder(m_noFocusBorder);
  }

  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
  {
    if (value instanceof Boolean) {
      Boolean b = (Boolean)value;
      setSelected(b.booleanValue());
    }

    setBackground(isSelected && !hasFocus ? table.getSelectionBackground() : table.getBackground());
    setForeground(isSelected && !hasFocus ? table.getSelectionForeground() : table.getForeground());

    setFont(table.getFont());
    setBorder(hasFocus ? UIManager.getBorder("Table.focusCellHighlightBorder") : m_noFocusBorder);

    return this;
  }
}