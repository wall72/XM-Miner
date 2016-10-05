
//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1999
//Author:       최대우
//Company:      전북대학교 산업공학과 지능정보시스템
//Description:  Your description

package xmminer.xmclient.xmgraph.xmmnode.xmmnodec45;

import java.io.*;
import java.lang.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import javax.swing.table.TableColumn;
import javax.swing.plaf.metal.*;
import java.util.*;
import xmminer.xmclient.xmgraph.*;
//import com.borland.jbcl.layout.*;
//import java.swing.
//import com.borland.jbcl.layout.*;

public class XMMNodeC45DLG extends JDialog
{

  protected static final int XMC45_TREE_BEFORE                   = 0x0000;
  protected static final int XMC45_TREE_LEARN                    = 0x0011;
  protected static final int XMC45_TREE_PRUNE                    = 0x0012;
  protected static final int XMC45_RULE_LEARN                    = 0x0021;
  protected static final int XMC45_RULE_PRUNE                    = 0x0022;
  protected static final String NOINPUTDATA                      = new String("NO INPUT DATA");
  protected static final String NOOUTPUTDATA                     = new String("NO OUTPUT DATA");
  //
  //METHOD SETTING
  //

  String ms_modelname= new String("untitledModel");//MODEL NAME
  String old_ms_modelname= new String("untitledModel");//MODEL NAME
  //TREE OR RULE
  boolean TreeGen = true; // True :TreeGem, False :RuleGen
  boolean old_TreeGen = true; // True :TreeGem, False :RuleGen
   Object temp_object;
  //LEARN AND TEST
  boolean MethodLearn =true; // True :Learn, False :
  boolean MethodTest =true; // True :Test, False :
                  //만약에 Method가 Learning 되어 있지 않으면
                  // MethodLearn=true
  boolean old_MethodLearn =true; // True :Learn, False :
  boolean old_MethodTest =true; // True :Test, False :
  boolean Start_flag=true;

  //LEARNING DATA INOUT SELECTION
  String m_sLearnData[];  //Node의 Input  Arc들의 String
  String m_sLearnDataArc[];
  String m_sLearnOutput[];//Node의 Output Arc들의 String
  int    m_iLearnData=1;  //User Selcted  m_sLearnData num
  int    m_iLearnOutput=1;//User Selcted  m_sLearnResult num

  String m_sSelectedLearnData=null;
  String old_m_sSelectedLearnData=null;
  String temp_m_sSelectedLearnData=null;
  String m_sSelectedLearnOutput=null;
  String old_m_sSelectedLearnOutput=null;


  //TEST DATA INOUT SELECTION
  String m_sTestData[];  //Node의 Input  Arc들의 String
  String m_sTestOutput[];//Node의 Output Arc들의 String
  int    m_iTestData=1;  //User Selcted  m_sTestData num
  int    m_iTestOutput=1;//User Selcted  m_sTestResult num
  String m_sSelectedTestData=null;
  String old_m_sSelectedTestData=null;
  String m_sSelectedTestOutput=null;
  String old_m_sSelectedTestOutput=null;

  String [] m_sColumnList=null;
  int m_iInputNum=0;
  //
  //PARAMETER SETTING(TREE)
  //

  //MinOBS
  int     m_minobs=2;
  int     old_m_minobs=2;
  String  ms_minobs =null;  //=new String();
  Integer mi_minobs= null; //new Integer(m_minobs);
  //CF
  double m_cf=0.25;
  double old_m_cf=0.25;
  String ms_cf= null;
  Double md_cf= null;
  boolean m_GainRatio = false;//Gain and GainRatio
  boolean old_m_GainRatio = false;//Gain and GainRatio
  boolean m_subset = false;////Subset
  boolean old_m_subset = false;////Subset



  //
  //PARAMETER SETTING(RULE)
  //

  //REDUNDANCY
  int m_Redundancy=1;
  int old_m_Redundancy=1;
  String ms_Redundancy =null;  //=new String();
  Integer mi_Redundancy= null; //new Integer(m_minobs);

  //SIGTHRESH
  double m_Sigthresh=0.05;
  double old_m_Sigthresh=0.05;
  String ms_Sigthresh= null;
  Double md_Sigthresh= null;

  boolean m_Sigtest = false;//Sigtest: True일 경우에만 m_Sigthresh가 Editable
  boolean old_m_Sigtest = false;




  //
  //COLUMN SELECTION
  //

  Vector v_sColumn_list = new Vector();//칼럼의 이름
  Vector v_sColumn_type = new Vector();//칼럼의 DataType
  Vector v_iUse = new Vector();
  Vector new_v_iUse;
  int num_instances;  //DB의 row수
  int num_attributes; //DB의 Attributes수
  int num_rows;       //table의 row수(= DB의 Attributes수)
  int num_columns;    //column수(3개)

  //boolean ParameterSetting
  boolean ParameterSetting=false;

  //String ParameterFileName

  String m_sProjectName=System.getProperty("minerdir") + "/xmminer/xmclient/xmgraph/xmmnode/xmmnodec45";
  int m_iMethodStatus;
  XMMNodeC45 NodeC45; // =null;
  C45TableModel C45Table = new C45TableModel();  //table model




  //백그라운드 및 백그라운드의 north,center,south
  JPanel panel_background = new JPanel();
  JPanel jPanel_north = new JPanel();
  JTabbedPane jTabbedPane_Center = new JTabbedPane();
  JPanel jPanel_south = new JPanel();
  BorderLayout borderLayout_background = new BorderLayout();
  FlowLayout flowLayout_south = new FlowLayout();


  //Model Setting
  JPanel jPanel_Status = new JPanel();//jPanel_MethodSetting(Center),jPanel_ModelExplain
  BorderLayout borderLayout_Status = new BorderLayout();

  JPanel jPanel_MethodSetting = new JPanel();//jPanel_MethodNorth,jPanel_MethodCenter,jPanel_ModelExplain
  JPanel jPanel_MethodNorth = new JPanel();
  JPanel jPanel_MethodCenter = new JPanel();//jPanel_LandT, jPanel_RorT
  JPanel jPanel_LandT = new JPanel();
  JPanel jPanel_RorT = new JPanel();
  JPanel jPanel_MethodSouth = new JPanel();//jPanel_TestInOut,jPanel_LearnInOut
  JPanel jPanel_TestInOut = new JPanel();
  JPanel jPanel_LearnInOut = new JPanel();
  BorderLayout borderLayout_MethodSetting = new BorderLayout();
  GridLayout gridLayout_MethodCenter = new GridLayout();
  GridLayout gridLayout_RorT = new GridLayout();
  GridLayout gridLayout_MethodSouth = new GridLayout();
  GridLayout gridLayout_LearnInOut = new GridLayout();
  GridLayout gridLayout_TestInOut = new GridLayout();
  GridLayout gridLayout_MethodNorth = new GridLayout();


  JPanel jPanel_ModelExplain = new JPanel();//현재 모델 상태
  FlowLayout flowLayoutModelExplain = new FlowLayout();

  //Parameter Setting
  JPanel jPanel_parm = new JPanel();//jPanel_Par_Tree(north),jPanel_Par_Rule(south)
  BorderLayout borderLayout_Parm = new BorderLayout();
                //Tree
  JPanel jPanel_Par_Tree = new JPanel();//jPanel_Minobs_CF,jPanel_Subset_Gain
  JPanel jPanel_Minobs_CF = new JPanel();
  JPanel jPanel_CF = new JPanel();
  JPanel jPanel_Minobs = new JPanel();
  JPanel jPanel_Subset_Gain = new JPanel();
  JPanel jPanel_Subset = new JPanel();
  JPanel jPanel_Gain = new JPanel();
  GridLayout gridLayout_Par_Tree = new GridLayout();
  BorderLayout borderLayout_Minobs_CF = new BorderLayout();
  FlowLayout flowLayout_CF = new FlowLayout();
  FlowLayout flowLayout_Minobs = new FlowLayout();
  BorderLayout borderLayout_Subset_Gain = new BorderLayout();
  FlowLayout flowLayout_Subset = new FlowLayout();
  FlowLayout flowLayout_Gain = new FlowLayout();
               //Rule
  JPanel jPanel_Par_Rule = new JPanel();
  JPanel jPanel_Reducdancy = new JPanel();
  JPanel jPanel_Sigtest = new JPanel();
  JPanel jPanel_Sigthresh = new JPanel();
  JPanel jPanel_Sigtest_thresh = new JPanel();
  GridLayout gridLayout_Par_Rule = new GridLayout();
  FlowLayout flowLayout_Redunancy = new FlowLayout();
  FlowLayout flowLayout_Sigtest = new FlowLayout();
  FlowLayout flowLayout_Sigthresh = new FlowLayout();
  BorderLayout borderLayout_Sigtest_thresh = new BorderLayout();


  //Column Selection
  JPanel jPanel_Column = new JPanel();
  BorderLayout borderLayout_Column = new BorderLayout();




  //jPanel_north
  ImageIcon imageBanner = new ImageIcon(xmminer.xmclient.xmgraph.xmmnode.xmmnodec45.XMMNodeC45DLG.class.getResource("../../../images/c45_banner.jpg"));
  JLabel jLabelC45 = new JLabel(imageBanner);

  //jPanel_south OK AND CANCEL
  JButton jButton_caccel = new JButton();
  JButton jButton_ok = new JButton();

  //
  // METHOD SETTING
  //

  //jPanel_MethodNorth
  JLabel jLabel_modelname = new JLabel();
  JTextField jTextField_modelname2 = new JTextField();

  //jPanel_RorT  의사결정 트리 또는 의사 결정RULE
  JRadioButton jRadioButton_Rule = new JRadioButton();
  JRadioButton jRadioButton_Tree = new JRadioButton();
  //jPanel_LandT LEARN AND TEST
  JCheckBox jCheckBox_Test = new JCheckBox();
  JCheckBox jCheckBox_Learn = new JCheckBox();

  //gridLayout_LearnInOut
  JLabel jLabel_LearnInput = new JLabel();
  JComboBox jComboBox_LearnInput = new JComboBox();
  JLabel jLabel_LearnOutput = new JLabel();
  JComboBox jComboBoxLearnOutput = new JComboBox();
  //gridLayout_TestInOut
  JLabel jLabel_TestOutput = new JLabel();
  JComboBox jComboBox_TestInput = new JComboBox();
  JLabel jLabel_TestInput = new JLabel();
  JComboBox jComboBoxTestOutput = new JComboBox();

  //jPanel_ModelExplain : SYSTEM 상태 설명
  JLabel jLabel_modelstatus = new JLabel();
  JLabel jLabel_modelexplain = new JLabel();



  //
  //Tree Parameter Setting
  //

  //minobs
  JLabel jLabel_minobs = new JLabel();
  JTextField jTextField_minobs = new JTextField();
  //Subset
  JComboBox jComboBox_subset = new JComboBox();
  JLabel jLabel_subset = new JLabel();
  //Gain & Gainratio
  JRadioButton jRadioButton_gainratio = new JRadioButton();
  JRadioButton jRadioButton_gain = new JRadioButton();
  //CF
  JLabel jLabel_cf = new JLabel();
  JTextField jTextField_cf = new JTextField();


  //
  //Rule Parameter Setting
  //

  //SIGTEST
  JLabel jLabel_Sigtest = new JLabel();
  JComboBox jComboBox_Sigtest = new JComboBox();
  //SIGTHRESH
  JLabel jLabel_Sigthresh = new JLabel();
  JTextField jTextField_Sigthresh = new JTextField();
  //REDUNDANCY
  JLabel jLabel_Redundancy = new JLabel();
  JTextField jTextField_Redundancy = new JTextField();


  //
  //COLUMN SELECTION
  //


  JScrollPane jScrollPane_Column = new JScrollPane();
  JTable jTable_Column = new JTable();


  //TitledBorder
  TitledBorder titledBorder1;
  TitledBorder titledBorder2;
  TitledBorder titledBorder3;
  TitledBorder titledBorder4;
  TitledBorder titledBorder5;
  TitledBorder titledBorder6;
  TitledBorder titledBorder7;
  TitledBorder titledBorder8;
  GridLayout gridLayout1 = new GridLayout();
  BorderLayout borderLayout1 = new BorderLayout();






  ///////////////////////////////////////////////////////////////////////////////////
  //
  // CONSTRUCTOR
  //
  ///////////////////////////////////////////////////////////////////////////////////
  public XMMNodeC45DLG(Frame frame, String title, boolean modal)
  {
    super(frame, title, modal);
    try
    {
      jbInit();
      num_columns = 6;
      MetalLookAndFeel metalLF = new MetalLookAndFeel();
      UIManager.setLookAndFeel(metalLF);
      SwingUtilities.updateComponentTreeUI(this);
      pack();
    }
    catch(Exception ex)
    {
      //ex.printStackTrace();
      System.out.println(ex.toString());
    }
  }

  public XMMNodeC45DLG(Frame frame, String title, boolean modal,XMMNodeC45 Node1)
  {
    super(frame, title, modal);
    NodeC45=Node1;
    num_columns = 6;
    try
    {
      SetParameter();

      Start_flag=true;
      jbInit();
	  Start_flag=false;
	  System.out.println("jbInit finished Start_flag="+Start_flag);

      if(!old_m_sSelectedLearnData.equals(NOINPUTDATA))
      {
		String TableData = new String(old_m_sSelectedLearnData);//DataArcName(true,old_m_sSelectedLearnData));
		System.out.println("TableData="+TableData);
        if(check_in_m_sLearnData(TableData))
        {
          tableSetting(TableData,true);
          //if(ParameterSetting)SetTableColumn_parm(temp_m_sSelectedLearnData);

        }
      }

      // else if(vnode중의 하나를 선택)
      // else (빈캄으로 남겨놓음)
      MetalLookAndFeel metalLF = new MetalLookAndFeel();
      UIManager.setLookAndFeel(metalLF);
      SwingUtilities.updateComponentTreeUI(this);
      pack();
    }
    catch(Exception ex)
    {
      //ex.printStackTrace();
      System.out.println(ex.toString());
    }
  }

  public XMMNodeC45DLG()
  {
    this(null, "", false);
  }





  ///////////////////////////////////////////////////////////////////////////////////
  //
  // jbInit()
  //
  ///////////////////////////////////////////////////////////////////////////////////
  void jbInit() throws Exception
  {

    titledBorder1 = new TitledBorder("");
    titledBorder2 = new TitledBorder(new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(142, 142, 142)),"트리 생성 파라미터");
    titledBorder3 = new TitledBorder("수행 작업 선택");
    titledBorder4 = new TitledBorder(new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(142, 142, 142)),"룰 생성 파라미터");
    titledBorder5 = new TitledBorder("데이타 정보");
    titledBorder6 = new TitledBorder(new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(142, 142, 142)),"의사 결정 트리 또는 의사 결정 룰");
    titledBorder7 = new TitledBorder(new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(142, 142, 142)),"학습, 테스트");
    titledBorder8 = new TitledBorder(new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(142, 142, 142)),"입력 및 출력 파일 선택");

    //MINOBS
    mi_minobs = new Integer(old_m_minobs);
    ms_minobs = new String(mi_minobs.toString());

    //CF
    md_cf = new Double(old_m_cf);
    ms_cf=  new String(md_cf.toString());


    //REDUNDANCY
    mi_Redundancy = new Integer(old_m_Redundancy);
    ms_Redundancy = new String(mi_Redundancy.toString());

    //SIGTHRESH
    md_Sigthresh = new Double(old_m_Sigthresh);
    ms_Sigthresh=  new String(md_Sigthresh.toString());


    panel_background.setLayout(borderLayout_background);

    jPanel_north.setLayout(borderLayout1);

    jPanel_south.setLayout(flowLayout_south);
    jPanel_south.setAlignmentX((float) 0.0);
    jPanel_south.setAlignmentY((float) 0.0);
    jPanel_south.setBorder(BorderFactory.createEtchedBorder());
    jPanel_south.setMinimumSize(new Dimension(180, 37));
    jPanel_south.setPreferredSize(new Dimension(500, 53));

    flowLayout_south.setAlignment(FlowLayout.RIGHT);
	flowLayout_south.setHgap(10);
	flowLayout_south.setVgap(10);




    jPanel_Par_Tree.setLayout(gridLayout_Par_Tree);


    jButton_caccel.setFont(new java.awt.Font("Dialog", 0, 12));
    jButton_caccel.setBorder(BorderFactory.createRaisedBevelBorder());
    jButton_caccel.setMaximumSize(new Dimension(83, 29));
    jButton_caccel.setMinimumSize(new Dimension(83, 29));
    jButton_caccel.setPreferredSize(new Dimension(83, 29));
    jButton_caccel.setText("취소");
    jButton_caccel.addMouseListener(new java.awt.event.MouseAdapter() {

      public void mouseClicked(MouseEvent e) {
        jButton_caccel_mouseClicked(e);
      }
    });
    jButton_ok.setFont(new java.awt.Font("Dialog", 0, 12));
    jButton_ok.setBorder(BorderFactory.createRaisedBevelBorder());
    jButton_ok.setMaximumSize(new Dimension(83, 29));
    jButton_ok.setMinimumSize(new Dimension(83, 29));
    jButton_ok.setPreferredSize(new Dimension(83, 29));
    jButton_ok.setMargin(new Insets(2, 0, 2, 0));
    jButton_ok.setSelected(true);
    jButton_ok.setText("확인");
    jButton_ok.addMouseListener(new java.awt.event.MouseAdapter() {

      public void mouseClicked(MouseEvent e) {
        jButton_ok_mouseClicked(e);
      }
    });

    jLabelC45.setFont(new java.awt.Font("Dialog", 0, 18));
    jLabelC45.setAlignmentY((float) 0.0);
	jLabelC45.setBorder(BorderFactory.createEtchedBorder());
    jLabelC45.setMaximumSize(new Dimension(500, 40));
    jLabelC45.setMinimumSize(new Dimension(500, 40));
    jLabelC45.setHorizontalAlignment(SwingConstants.CENTER);
    jLabelC45.setHorizontalTextPosition(SwingConstants.CENTER);
    jLabelC45.setText("");
    jPanel_north.setAlignmentX((float) 0.0);
    jPanel_north.setAlignmentY((float) 0.0);
    jPanel_north.setMinimumSize(new Dimension(78, 40));
    jPanel_north.setPreferredSize(new Dimension(500, 50));
    jPanel_north.setRequestFocusEnabled(false);
    gridLayout_Par_Tree.setRows(2);
    jPanel_Minobs_CF.setLayout(borderLayout_Minobs_CF);
    jPanel_CF.setLayout(flowLayout_CF);
    jPanel_CF.setMinimumSize(new Dimension(200, 30));
    jPanel_Minobs.setLayout(flowLayout_Minobs);

    jPanel_Par_Tree.setBorder(titledBorder2);
    jPanel_Par_Tree.setMaximumSize(new Dimension(412, 170));
    jPanel_Par_Tree.setMinimumSize(new Dimension(412, 150));
    jPanel_Par_Tree.setPreferredSize(new Dimension(412, 130));
    jPanel_Subset.setMinimumSize(new Dimension(200, 30));
    jPanel_Subset.setLayout(flowLayout_Subset);
    jPanel_Subset_Gain.setLayout(borderLayout_Subset_Gain);
    panel_background.setMinimumSize(new Dimension(700, 500));
    panel_background.setPreferredSize(new Dimension(500, 420));
    panel_background.setToolTipText("");
    jPanel_parm.setLayout(borderLayout_Parm);
    jPanel_Status.setLayout(borderLayout_Status);
    jPanel_parm.setPreferredSize(new Dimension(600, 200));
    jPanel_Par_Rule.setLayout(gridLayout_Par_Rule);
    jPanel_Par_Rule.setBorder(titledBorder4);
    jPanel_Par_Rule.setMaximumSize(new Dimension(412, 170));
    jPanel_Par_Rule.setMinimumSize(new Dimension(412, 150));
    jPanel_Par_Rule.setPreferredSize(new Dimension(412, 130));
    gridLayout_Par_Rule.setRows(2);
    jPanel_Reducdancy.setLayout(flowLayout_Redunancy);
    jTabbedPane_Center.setFont(new java.awt.Font("Dialog", 0, 12));
    jTabbedPane_Center.setBorder(BorderFactory.createEtchedBorder());
	jTabbedPane_Center.setPreferredSize(new Dimension(500, 400));

    jPanel_MethodSetting.setBorder(titledBorder1);
    jPanel_MethodSetting.setLayout(borderLayout_MethodSetting);


    jPanel_MethodNorth.setLayout(gridLayout_MethodNorth);
    jTextField_modelname2.setHorizontalAlignment(SwingConstants.CENTER);
    //VALUE SETTING
    jTextField_modelname2.setText(old_ms_modelname);
    jTextField_modelname2.setPreferredSize(new Dimension(120, 30));
    jTextField_modelname2.setBorder(BorderFactory.createLoweredBevelBorder());
    jLabel_modelname.setText("모델 이름");
    jLabel_modelname.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel_modelname.setPreferredSize(new Dimension(100, 30));
    jLabel_modelname.setMinimumSize(new Dimension(100, 30));
    jLabel_modelname.setMaximumSize(new Dimension(100, 30));
    jLabel_modelname.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel_modelname.setBorder(titledBorder1);

    jLabel_modelstatus.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel_modelstatus.setAlignmentX((float) 0.5);
    jLabel_modelstatus.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel_modelstatus.setHorizontalTextPosition(SwingConstants.CENTER);

    String text=null;
    switch(m_iMethodStatus)
    {
      case XMC45_TREE_BEFORE:
           text=new String("트리나 룰이 생성되지 않았습니다.");
           break;
      case XMC45_TREE_LEARN:
           text=new String("트리가 생성되었습니다.");
           break;
      case XMC45_RULE_LEARN:
           text=new String("룰이 생성 되엇습니다.");
           break;

    }


    jLabel_modelstatus.setText(text);
    jLabel_modelexplain.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel_modelexplain.setForeground(new java.awt.Color(102, 102, 153));
    jLabel_modelexplain.setText("현재 모델 상태 :");
    jPanel_ModelExplain.setLayout(flowLayoutModelExplain);
    jPanel_ModelExplain.setBorder(BorderFactory.createEtchedBorder());
    flowLayoutModelExplain.setAlignment(FlowLayout.LEFT);
    jPanel_Column.setLayout(borderLayout_Column);
    jPanel_MethodCenter.setLayout(gridLayout_MethodCenter);
    gridLayout_MethodCenter.setColumns(2);
    jPanel_RorT.setLayout(gridLayout_RorT);

    jRadioButton_Rule.setText("의사 결정 룰");
    jRadioButton_Rule.setForeground(new java.awt.Color(102, 102, 153));
    jRadioButton_Rule.setActionCommand("jRadioButton_Rule");
    jRadioButton_Rule.setFont(new java.awt.Font("Dialog", 0, 12));
    jRadioButton_Rule.setHorizontalAlignment(SwingConstants.CENTER);
    jRadioButton_Rule.addMouseListener(new java.awt.event.MouseAdapter() {

      public void mouseClicked(MouseEvent e) {
        jRadioButton_Rule_mouseClicked(e);
      }
    });

    jRadioButton_Tree.setText("의사 결정 트리");
    jRadioButton_Tree.setForeground(new java.awt.Color(102, 102, 153));
    jRadioButton_Tree.setActionCommand("jRadioButton_Tree");
    jRadioButton_Tree.setFont(new java.awt.Font("Dialog", 0, 12));
    jRadioButton_Tree.setHorizontalAlignment(SwingConstants.CENTER);
    jRadioButton_Tree.addMouseListener(new java.awt.event.MouseAdapter() {

      public void mouseClicked(MouseEvent e) {
        jRadioButton_Tree_mouseClicked(e);
      }
    });

    if(old_TreeGen)
    {
    	jRadioButton_Tree.setSelected(true);
    	jRadioButton_Rule.setSelected(false);
    }
    else
    {
    	jRadioButton_Tree.setSelected(false);
        jRadioButton_Rule.setSelected(true);
    }


    jPanel_RorT.setAlignmentX((float) 1.0);
    jPanel_RorT.setBorder(titledBorder6);


    jPanel_LandT.setBorder(titledBorder7);
    jPanel_LandT.setLayout(gridLayout1);

    //LEARN AND TEST
    jCheckBox_Learn.setText("학습(생성)");
    jCheckBox_Learn.setForeground(new java.awt.Color(102, 102, 153));
    jCheckBox_Learn.setHorizontalAlignment(SwingConstants.CENTER);
    jCheckBox_Learn.setFont(new java.awt.Font("Dialog", 0, 12));
    jCheckBox_Learn.addMouseListener(new java.awt.event.MouseAdapter() {

      public void mouseClicked(MouseEvent e) {
        jCheckBox_Learn_mouseClicked(e);
      }
    });
    if(old_MethodLearn)    jCheckBox_Learn.setSelected(true);
    else jCheckBox_Learn.setSelected(false);

    jCheckBox_Test.setPreferredSize(new Dimension(51, 26));
    jCheckBox_Test.setText("테스트");
    jCheckBox_Test.setForeground(new java.awt.Color(102, 102, 153));
    jCheckBox_Test.setHorizontalAlignment(SwingConstants.CENTER);
    jCheckBox_Test.setFont(new java.awt.Font("Dialog", 0, 12));
    jCheckBox_Test.addMouseListener(new java.awt.event.MouseAdapter() {

      public void mouseClicked(MouseEvent e) {
        jCheckBox_Test_mouseClicked(e);
      }
    });
    if(old_MethodTest)    jCheckBox_Test.setSelected(true);
    else jCheckBox_Test.setSelected(false);

    jPanel_MethodSouth.setLayout(gridLayout_MethodSouth);
    jPanel_LearnInOut.setLayout(gridLayout_LearnInOut);
    jPanel_TestInOut.setLayout(gridLayout_TestInOut);
    gridLayout_LearnInOut.setRows(4);
    gridLayout_TestInOut.setRows(4);
    jLabel_LearnInput.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel_LearnInput.setText("학습 데이타");
    jLabel_LearnOutput.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel_LearnOutput.setText("학습 결과 출력");
    jLabel_TestOutput.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel_TestOutput.setText("테스트 결과 출력");
    jLabel_TestInput.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel_TestInput.setText("테스트 데이타");
    jPanel_MethodSouth.setBorder(titledBorder8);

    jComboBox_LearnInput.addItemListener(new java.awt.event.ItemListener() {

      public void itemStateChanged(ItemEvent e) {
        jComboBox_LearnInput_itemStateChanged(e);
      }
    });
    int i=0,arraylength=0,m_iselcted_num=0;

	//LEARN INPUT
    System.out.println("jbInit #11_1 ");
    arraylength=m_sLearnData.length;
    System.out.println("jbInit #11_2 "+"  arraylength  "+arraylength
                      +"    m_sLearnData[0]"+m_sLearnData[0]);
    String m_sTempm_sLearnData=null;
    for(i=0;i<arraylength;i++)
    {
       //i=0;
       m_sTempm_sLearnData=new String(m_sLearnData[i]);
        //jComboBox_LearnInput.addItem("NO INPUT DATA");//m_sTempm_sLearnData);//(Object)m_sLearnData[i]);
       jComboBox_LearnInput.addItem(m_sTempm_sLearnData);//(Object)m_sLearnData[i]);
	   if(old_m_sSelectedLearnData.equals(m_sLearnDataArc[i]))m_iselcted_num=i;
	   System.out.println("    m_sLearnData["+i+"]=  "+m_sLearnData[i]);
    }

    System.out.println("jbInit #11_2_3 "+"  arraylength  "+arraylength
                      +"    m_sLearnData[0]"+m_sLearnData[0]);
    jComboBox_LearnInput.setSelectedItem(m_sLearnData[m_iselcted_num]);

    //jComboBox_LearnInput.setSelectedIndex(0);//m_iLearnData);

    System.out.println("jbInit #11_3 "+"  arraylength  "+arraylength);

	//LEARN OUTPUT

    arraylength=m_sLearnOutput.length;
    for(i=0;i<arraylength;i++)  //jComboBoxLearnOutput.addItem("NO_INPUT_DATA");//m_sLearnOutput[i]);
    jComboBoxLearnOutput.addItem(m_sLearnOutput[i]);
    //jComboBoxLearnOutput.setSelectedIndex(m_iLearnOutput);
    jComboBoxLearnOutput.setSelectedItem(old_m_sSelectedLearnOutput);
    System.out.println("jbInit #11_4 "+"  arraylength  "+arraylength);
    jComboBox_LearnInput.setEnabled(old_MethodLearn);
    jComboBox_LearnInput.setFont(new java.awt.Font("Dialog", 0, 12));
    jComboBoxLearnOutput.setEnabled(old_MethodLearn);
    jComboBoxLearnOutput.setFont(new java.awt.Font("Dialog", 0, 12));




    //TEST INPUT
    arraylength=m_sTestData.length;
    for(i=0;i<arraylength;i++)  jComboBox_TestInput.addItem(m_sTestData[i]);
    jComboBox_TestInput.setSelectedItem(old_m_sSelectedTestData);
    //jComboBox_TestInput.setSelectedIndex(m_iTestData);
    System.out.println("jbInit #11_5 "+"  arraylength  "+arraylength);
    //TEST OUTPUT
    arraylength=m_sTestOutput.length;
    for(i=0;i<arraylength;i++)  jComboBoxTestOutput.addItem(m_sTestOutput[i]);
    //jComboBoxTestOutput.setSelectedIndex(m_iTestOutput);
    jComboBoxTestOutput.setSelectedItem(this.old_m_sSelectedTestOutput);
    System.out.println("jbInit #12 "+"  arraylength  "+arraylength);
    jComboBox_TestInput.setEnabled(old_MethodTest);
    jComboBox_TestInput.setFont(new java.awt.Font("Dialog", 0, 12));
    jComboBoxTestOutput.setEnabled(old_MethodTest);
    jComboBoxTestOutput.setFont(new java.awt.Font("Dialog", 0, 12));

    jLabel_minobs.setText("MINOBS");
    jLabel_minobs.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel_minobs.setPreferredSize(new Dimension(100, 30));
    jLabel_minobs.setMinimumSize(new Dimension(100, 30));
    jLabel_minobs.setMaximumSize(new Dimension(100, 30));
    jLabel_minobs.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel_minobs.setBorder(titledBorder1);
    jTextField_minobs.setHorizontalAlignment(SwingConstants.CENTER);
    //MINOBS VALUE SETTING
    jTextField_minobs.setText(ms_minobs);
    jTextField_minobs.setBorder(BorderFactory.createLoweredBevelBorder());
    jTextField_minobs.setPreferredSize(new Dimension(120, 30));
    jTextField_minobs.setEnabled(old_MethodLearn);//old_MethodLearn&&old_TreeGen
    jLabel_cf.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel_cf.setAlignmentY((float) 0.0);
    jLabel_cf.setBorder(titledBorder1);
    jLabel_cf.setMaximumSize(new Dimension(100, 30));
    jLabel_cf.setMinimumSize(new Dimension(100, 30));
    jLabel_cf.setPreferredSize(new Dimension(100, 30));
    jLabel_cf.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel_cf.setText("CF");
    jTextField_cf.setBorder(BorderFactory.createLoweredBevelBorder());
    jTextField_cf.setMaximumSize(new Dimension(120, 30));
    jTextField_cf.setMinimumSize(new Dimension(120, 30));
    jTextField_cf.setPreferredSize(new Dimension(120, 30));//old_MethodLearn&&old_TreeGen

    //VALUE SETTING
    jTextField_cf.setText(ms_cf);
    jTextField_cf.setHorizontalAlignment(SwingConstants.CENTER);
    jTextField_cf.setEnabled(old_MethodLearn);

    jLabel_subset.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel_subset.setAlignmentY((float) 0.0);
    jLabel_subset.setBorder(titledBorder1);
    jLabel_subset.setMaximumSize(new Dimension(100, 30));
    jLabel_subset.setMinimumSize(new Dimension(100, 30));
    jLabel_subset.setPreferredSize(new Dimension(100, 30));
    jLabel_subset.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel_subset.setText("SUBSET");
    jComboBox_subset.setMinimumSize(new Dimension(120, 27));
    jComboBox_subset.setPreferredSize(new Dimension(120, 30));
    jComboBox_subset.addItem("TRUE");
    jComboBox_subset.addItem("FALSE");
    if(old_m_subset)jComboBox_subset.setSelectedItem("TRUE");
    else jComboBox_subset.setSelectedItem("FALSE");
    jComboBox_subset.setEnabled(old_MethodLearn);
    jComboBox_subset.setFont(new java.awt.Font("Dialog", 0, 12));//old_MethodLearn&&old_TreeGen

    jRadioButton_gainratio.setPreferredSize(new Dimension(100, 30));
    jRadioButton_gainratio.setMaximumSize(new Dimension(100, 30));
    jRadioButton_gainratio.setBorder(titledBorder1);
    jRadioButton_gainratio.setMinimumSize(new Dimension(100, 30));
    jRadioButton_gainratio.setText("GAINRATIO");
    jRadioButton_gainratio.setForeground(new java.awt.Color(102, 102, 153));
    jRadioButton_gainratio.setHorizontalAlignment(SwingConstants.CENTER);
    jRadioButton_gainratio.setFont(new java.awt.Font("Dialog", 0, 12));
    jRadioButton_gainratio.addMouseListener(new java.awt.event.MouseAdapter() {

      public void mouseClicked(MouseEvent e) {
        jRadioButton_gainratio_mouseClicked(e);
      }
    });
    jRadioButton_gain.setPreferredSize(new Dimension(70, 30));
    jRadioButton_gain.setMaximumSize(new Dimension(100, 20));

    jRadioButton_gain.setBorder(titledBorder1);
    jRadioButton_gain.setMinimumSize(new Dimension(100, 20));
    jRadioButton_gain.setText("GAIN");
    jRadioButton_gain.setForeground(new java.awt.Color(102, 102, 153));
    jRadioButton_gain.setFont(new java.awt.Font("Dialog", 0, 12));
    jRadioButton_gain.addMouseListener(new java.awt.event.MouseAdapter() {

      public void mouseClicked(MouseEvent e) {
        jRadioButton_gain_mouseClicked(e);
      }
    });

    if(old_m_GainRatio)
    {
    	jRadioButton_gainratio.setSelected(true);
        jRadioButton_gain.setSelected(false);
    }
    else
    {
    	jRadioButton_gainratio.setSelected(false);
        jRadioButton_gain.setSelected(true);
    }
    jRadioButton_gainratio.setEnabled(old_MethodLearn); //old_MethodLearn&&old_TreeGen
    jRadioButton_gain.setEnabled(old_MethodLearn); //old_MethodLearn&&old_TreeGen
    jPanel_Gain.setLayout(flowLayout_Gain);
    jPanel_Gain.setPreferredSize(new Dimension(227, 40));
    flowLayout_Gain.setAlignment(FlowLayout.LEFT);
    flowLayout_Sigthresh.setVgap(10);


    jPanel_Sigtest.setLayout(flowLayout_Sigtest);
    jPanel_Sigtest.setMinimumSize(new Dimension(200, 30));
    jPanel_Sigthresh.setLayout(flowLayout_Sigthresh);
    jPanel_Sigthresh.setMinimumSize(new Dimension(200, 30));
    jPanel_Sigthresh.setPreferredSize(new Dimension(235, 30));
    jPanel_Sigtest_thresh.setLayout(borderLayout_Sigtest_thresh);
    flowLayout_Sigtest.setVgap(10);
    jLabel_Sigtest.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel_Sigtest.setAlignmentY((float) 0.0);
    jLabel_Sigtest.setBorder(titledBorder1);
    jLabel_Sigtest.setMaximumSize(new Dimension(100, 30));
    jLabel_Sigtest.setMinimumSize(new Dimension(100, 30));
    jLabel_Sigtest.setPreferredSize(new Dimension(100, 30));
    jLabel_Sigtest.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel_Sigtest.setText("SIGTEST");
    jComboBox_Sigtest.setMinimumSize(new Dimension(120, 27));
    jComboBox_Sigtest.setPreferredSize(new Dimension(120, 30));
    jComboBox_Sigtest.addItemListener(new java.awt.event.ItemListener() {

      public void itemStateChanged(ItemEvent e) {
        jComboBox_Sigtest_itemStateChanged(e);
      }
    });
    jComboBox_Sigtest.addItem("TRUE");
    jComboBox_Sigtest.addItem("FALSE");

    if(old_m_Sigtest)jComboBox_Sigtest.setSelectedItem("TRUE");
    else jComboBox_Sigtest.setSelectedItem("FALSE");
    jComboBox_Sigtest.setEnabled(old_MethodLearn && !old_TreeGen);
    jComboBox_Sigtest.setFont(new java.awt.Font("Dialog", 0, 12));//old_MethodLearn && !old_TreeGen


    jLabel_Sigthresh.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel_Sigthresh.setAlignmentY((float) 0.0);
    jLabel_Sigthresh.setBorder(titledBorder1);
    jLabel_Sigthresh.setMaximumSize(new Dimension(100, 30));
    jLabel_Sigthresh.setMinimumSize(new Dimension(100, 30));
    jLabel_Sigthresh.setPreferredSize(new Dimension(100, 30));
    jLabel_Sigthresh.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel_Sigthresh.setText("SIGTHRESH");
    jTextField_Sigthresh.setBorder(BorderFactory.createLoweredBevelBorder());
    jTextField_Sigthresh.setMaximumSize(new Dimension(120, 30));
    jTextField_Sigthresh.setMinimumSize(new Dimension(120, 30));
    jTextField_Sigthresh.setPreferredSize(new Dimension(120, 30));
    //SIGTHRESH VALUE SETTING
    jTextField_Sigthresh.setText(ms_Sigthresh);
    jTextField_Sigthresh.setHorizontalAlignment(SwingConstants.CENTER);
    jTextField_Sigthresh.setEnabled(old_MethodLearn && !old_TreeGen);  //old_MethodLearn && !old_TreeGen

    jLabel_Redundancy.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel_Redundancy.setBorder(titledBorder1);
    jLabel_Redundancy.setMaximumSize(new Dimension(100, 30));
    jLabel_Redundancy.setMinimumSize(new Dimension(100, 30));
    jLabel_Redundancy.setPreferredSize(new Dimension(100, 30));
    jLabel_Redundancy.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel_Redundancy.setText("REDUNNDANCY");
    jTextField_Redundancy.setBorder(BorderFactory.createLoweredBevelBorder());
    jTextField_Redundancy.setPreferredSize(new Dimension(120, 30));
    jTextField_Redundancy.setText(ms_Redundancy);
    jTextField_Redundancy.setHorizontalAlignment(SwingConstants.CENTER);
    flowLayout_Redunancy.setAlignment(FlowLayout.LEFT);
    jTextField_Redundancy.setEnabled(old_MethodLearn && !old_TreeGen);//old_MethodLearn && !old_TreeGen
    jTable_Column.addMouseListener(new java.awt.event.MouseAdapter() {

      public void mouseClicked(MouseEvent e) {
        jTable_Column_mouseClicked(e);
      }
    });
    jTable_Column.setEnabled(old_MethodLearn);
    this.setResizable(false);
    getContentPane().add(panel_background);
    panel_background.add(jPanel_north, BorderLayout.NORTH);
    jPanel_north.add(jLabelC45, BorderLayout.CENTER);

    panel_background.add(jTabbedPane_Center, BorderLayout.CENTER);
    jTabbedPane_Center.add(jPanel_Status, "방법론 셋팅");
    jPanel_Status.add(jPanel_MethodSetting, BorderLayout.CENTER);
    jPanel_MethodSetting.add(jPanel_MethodCenter, BorderLayout.CENTER);
    jPanel_MethodCenter.add(jPanel_RorT, null);
    jPanel_RorT.add(jRadioButton_Tree, null);
    jPanel_RorT.add(jRadioButton_Rule, null);
    jPanel_MethodCenter.add(jPanel_LandT, null);
    jPanel_LandT.add(jCheckBox_Learn, null);
    jPanel_LandT.add(jCheckBox_Test, null);
    jPanel_MethodSetting.add(jPanel_MethodSouth, BorderLayout.SOUTH);
    jPanel_MethodSouth.add(jPanel_LearnInOut, null);
    jPanel_LearnInOut.add(jLabel_LearnInput, null);
    jPanel_LearnInOut.add(jComboBox_LearnInput, null);
    jPanel_LearnInOut.add(jLabel_LearnOutput, null);
    jPanel_LearnInOut.add(jComboBoxLearnOutput, null);
    jPanel_MethodSouth.add(jPanel_TestInOut, null);
    jPanel_TestInOut.add(jLabel_TestInput, null);
    jPanel_TestInOut.add(jComboBox_TestInput, null);
    jPanel_TestInOut.add(jLabel_TestOutput, null);
    jPanel_TestInOut.add(jComboBoxTestOutput, null);
    jPanel_MethodSetting.add(jPanel_MethodNorth, BorderLayout.NORTH);
    jPanel_MethodNorth.add(jLabel_modelname, null);
    jPanel_MethodNorth.add(jTextField_modelname2, null);
    jPanel_Status.add(jPanel_ModelExplain, BorderLayout.SOUTH);
    jPanel_ModelExplain.add(jLabel_modelexplain, null);
    jPanel_ModelExplain.add(jLabel_modelstatus, null);
    jTabbedPane_Center.add(jPanel_parm, "입력파라미터 셋팅");
    jPanel_parm.add(jPanel_Par_Rule, BorderLayout.SOUTH);
    jPanel_Par_Rule.add(jPanel_Sigtest_thresh, null);
    jPanel_Sigtest_thresh.add(jPanel_Sigthresh, BorderLayout.EAST);
    jPanel_Sigthresh.add(jLabel_Sigthresh, null);
    jPanel_Sigthresh.add(jTextField_Sigthresh, null);
    jPanel_Sigtest_thresh.add(jPanel_Sigtest, BorderLayout.WEST);
    jPanel_Sigtest.add(jLabel_Sigtest, null);
    jPanel_Sigtest.add(jComboBox_Sigtest, null);
    jPanel_Par_Rule.add(jPanel_Reducdancy, null);
    jPanel_Reducdancy.add(jLabel_Redundancy, null);
    jPanel_Reducdancy.add(jTextField_Redundancy, null);
    jPanel_parm.add(jPanel_Par_Tree, BorderLayout.NORTH);
    jPanel_Par_Tree.add(jPanel_Minobs_CF, null);
    jPanel_Minobs_CF.add(jPanel_CF, BorderLayout.EAST);
    jPanel_CF.add(jLabel_cf, null);
    jPanel_CF.add(jTextField_cf, null);
    jPanel_Minobs_CF.add(jPanel_Minobs, BorderLayout.WEST);
    jPanel_Minobs.add(jLabel_minobs, null);
    jPanel_Minobs.add(jTextField_minobs, null);
    jPanel_Par_Tree.add(jPanel_Subset_Gain, null);
    jPanel_Subset_Gain.add(jPanel_Subset, BorderLayout.WEST);
    jPanel_Subset.add(jLabel_subset, null);
    jPanel_Subset.add(jComboBox_subset, null);
    jPanel_Subset_Gain.add(jPanel_Gain, BorderLayout.EAST);
    jPanel_Gain.add(jRadioButton_gain, null);
    jPanel_Gain.add(jRadioButton_gainratio, null);
    jTabbedPane_Center.add(jPanel_Column, "칼럼 선택");
    jPanel_Column.add(jScrollPane_Column, BorderLayout.CENTER);
    jScrollPane_Column.getViewport().add(jTable_Column, null);
    panel_background.add(jPanel_south, BorderLayout.SOUTH);
    jPanel_south.add(jButton_ok, null);
    jPanel_south.add(jButton_caccel, null);

    //this.setResizable(false); //.setResizible(false);

  }


////////////////////////////////////////////////////////////////////////////////
//
// Ok,Cancel을 생성하는 함수
//
//
// jButton_ok_mouseClicked(MouseEvent e),
// jButton_caccel_mouseClicked(MouseEvent e)
////////////////////////////////////////////////////////////////////////////////
void jButton_ok_mouseClicked(MouseEvent e)
  {
    //
    //METHOD SETTING
    //

    //MODEL NAME SETTING
    ms_modelname=jTextField_modelname2.getText();
    if ( (ms_modelname.length() == 0))
    {
        MessageBox  err_box = new MessageBox(this,"에러메시지",true,
              "입력 에러"," 모델 이름을 입력하여 주십시오 ");
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

    //TREEGEN SETTING
    if(jRadioButton_Tree.isSelected()) TreeGen=true;
    else TreeGen=false;
    //METHODLEARN SETTING
    if(jCheckBox_Learn.isSelected())  MethodLearn =true;
    else MethodLearn =false;
    //MOTHODTEST SETTING
    if(jCheckBox_Test.isSelected())  MethodTest =true;
    else MethodTest =false;

	String TempData =null;
	//LEARN INPUT SETTING
    m_iLearnData=jComboBox_LearnInput.getSelectedIndex();
	 TempData = new String((String)jComboBox_LearnInput.getItemAt(m_iLearnData));
     m_sSelectedLearnData = DataArcName(true,TempData);
	 System.out.println("	m_sSelectedLearnData ="+m_sSelectedLearnData);
    //m_sSelectedLearnData=(String)jComboBox_LearnInput.getItemAt(m_iLearnData);

	TempData =null;
	//LEARN OUTPUT SETTING
    m_iLearnOutput=jComboBoxLearnOutput.getSelectedIndex();
    TempData = new String((String)jComboBoxLearnOutput.getItemAt(m_iLearnOutput));
    m_sSelectedLearnOutput = DataArcName(false,TempData);
    System.out.println("	m_sSelectedLearnOutput ="+m_sSelectedLearnOutput);
   //m_sSelectedLearnOutput=(String)jComboBoxLearnOutput.getItemAt(m_iLearnOutput);

    TempData =null;
    //TEST INPUT SETTING
    m_iTestData=jComboBox_TestInput.getSelectedIndex();
	TempData = new String((String)jComboBox_TestInput.getItemAt(m_iTestData));
    m_sSelectedTestData = DataArcName(true,TempData);
    System.out.println("	m_sSelectedTestData ="+m_sSelectedTestData);
	//m_sSelectedTestData=(String)jComboBox_TestInput.getItemAt(m_iTestData);

	TempData =null;
	//TEST OUTPUT SETTING
    m_iTestOutput=jComboBoxTestOutput.getSelectedIndex();
	TempData = new String((String)jComboBoxTestOutput.getItemAt(m_iTestOutput));
    m_sSelectedTestOutput = DataArcName(false,TempData);
    System.out.println("	m_sSelectedTestOutput ="+m_sSelectedTestOutput);
    //m_sSelectedTestOutput=(String)jComboBoxTestOutput.getItemAt(m_iTestOutput);

    //
    //PARAMETER SETTING(TREE)
    //

    //m_cf
    ms_cf=jTextField_cf.getText();
    md_cf=new Double(ms_cf);
    m_cf=md_cf.doubleValue();
    //m_minobs
    ms_minobs=jTextField_minobs.getText();
    mi_minobs=new Integer(ms_minobs);
    m_minobs=mi_minobs.intValue();
    //subset
    if(jComboBox_subset.getSelectedIndex()==0) m_subset=true;
    else if(jComboBox_subset.getSelectedIndex()==1) m_subset=false;
    else   m_subset=true;
    //gainratio
    if(jRadioButton_gainratio.isSelected())  m_GainRatio=true;
    else m_GainRatio=false;


    //REDUNDANCY
    ms_Redundancy=jTextField_Redundancy.getText();
    mi_Redundancy=new Integer(ms_Redundancy);
    m_Redundancy=mi_Redundancy.intValue();
    //SIGTHRESH
    ms_Sigthresh=jTextField_Sigthresh.getText();
    md_Sigthresh=new Double(ms_Sigthresh);
    m_Sigthresh=md_Sigthresh.doubleValue();
    //SIGTEST
    if(jComboBox_Sigtest.getSelectedIndex()==0) m_Sigtest=true;
    else if(jComboBox_Sigtest.getSelectedIndex()==1) m_Sigtest=false;
    else   m_subset=true;


   //
   //
   //COLUMN INFORMATION
   //
   //
   Boolean check = null;
   new_v_iUse = new Vector();
   int m_iSeletedUsage=2,i,j,m_iCountOutput=0;

   for(i = 0; i < num_attributes; i++)
   {
        m_iSeletedUsage=2;
		for(j = 3; j<=5 ; j++)
        {

		   System.out.println("OK #5_1   i="+i+" j=  "+j);
           check = (Boolean)C45Table.getValueAt(i , j);
		   System.out.println("check="+check.booleanValue());
           if(check.booleanValue())
               m_iSeletedUsage=j-3;
        }
		System.out.println("OK #5_1   m_iSeletedUsagei="+m_iSeletedUsage);
        new_v_iUse.addElement(Integer.toString(m_iSeletedUsage));
		if(m_iSeletedUsage==1)m_iCountOutput++;
   }//end for


    if( new_v_iUse.size()>2 && m_iCountOutput==1)
	{
	   //System.out.println("ok1");
	   WriteParameter(m_sProjectName,ms_modelname);
	   NodeC45.Setm_bMethodSettingChaneg(CheckPrameterChanege());
	   MakeMetaFile();
	   //System.out.println("ok2");
	   this.dispose();
	 }
	 else
     {
	    MessageBox  err_box = new MessageBox(this,"에러메시지",true,
              "칼럼 선택 에러"," 하나의 output 칼럼과 하나 이상의 input 칼럼을 선택해야 합니다. ");
        java.awt.Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        java.awt.Dimension frameSize = err_box.getSize();
        if (frameSize.height > screenSize.height/5)
            frameSize.height = screenSize.height/5;
        if (frameSize.width > screenSize.width)
            frameSize.width = screenSize.width;
        err_box.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
        err_box.setVisible(true);
        return;
     }
  }

 void jButton_caccel_mouseClicked(MouseEvent e)
  {
   this.dispose();
  }

////////////////////////////////////////////////////////////////////////////////
//
// Gain and Gain Ratio을 생성하는 함수
//
// tableSetting(),getAttributesList(Vector attriName, Vector attriType)
// jTable_Column_mouseClicked(MouseEvent e)
////////////////////////////////////////////////////////////////////////////////

  void jRadioButton_gain_mouseClicked(MouseEvent e)
  {
      jRadioButton_gain.setSelected(true);
      jRadioButton_gainratio.setSelected(false);
  }


  void jRadioButton_gainratio_mouseClicked(MouseEvent e)
  {
       jRadioButton_gainratio.setSelected(true);
       jRadioButton_gain.setSelected(false);
  }

////////////////////////////////////////////////////////////////////////////////
//
// Table column을 생성하는 함수
//
// tableSetting(),getAttributesList(Vector attriName, Vector attriType)
// jTable_Column_mouseClicked(MouseEvent e)
////////////////////////////////////////////////////////////////////////////////


  //int num_rows=0;

  private void tableSetting(String MetaFileName,boolean m_bsetting)
  {

    //한번에 하나의 row만 선택되도록 한다.(default는 멀티 row 선택 가능)
    jTable_Column.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    //jTable_Column.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    //jTable_Column.setSelectionBackground(SystemColor.activeCaption);

    //Vector attriName = new Vector();  //attribute name을 저장할 Vector
    //Vector attriType = new Vector();  //attribute type을 저장할 Vector
    //System.out.println("tableSetting #1 ");
    if(m_bsetting)
	{
	   v_sColumn_list=new Vector();
       v_sColumn_type=new Vector();

	   getAttributesList(v_sColumn_list, v_sColumn_type,MetaFileName);
    }
	//System.out.println("tableSetting #2 ");
    num_rows = v_sColumn_list.size();
    //System.out.println("tableSetting #3 ");
	C45Table.setData(v_sColumn_list, v_sColumn_type);  //tableModel의 data값을 setting
    //System.out.println("tableSetting #4 ");
    jTable_Column.setModel(C45Table);
    //System.out.println("tableSetting #5 ");
    //2번째 칼럼(checkbox)은 eidt 가능하게(frolle)

    //column width 조절하기
    TableColumn column = null;
    for (int i = 0; i < num_columns; i++) {
      column = jTable_Column.getColumnModel().getColumn(i); //return : TableColumnModel, TableColumn
	  if (i==0)
  	    column.setPreferredWidth(20);
	  else
	    column.setPreferredWidth(60);
    }//end for
	System.out.println("tableSetting #6 ");

//    jTable_Column.setEditingColumn(0);
      //jTable_Column..setSelectRow(0);
  }

 private void getAttributesList(Vector attriName,Vector attriType,String MetaFileName)
  {

	try
	{
	  NodeC45.m_sXMMNodeC45.Read_setFileStatus(m_sProjectName,MetaFileName);
	}
	catch (org.omg.CORBA.BAD_OPERATION e)
	{
		e.printStackTrace();
	}
      String rowNumStr = NodeC45.m_sXMMNodeC45.Read_getProfile("NUMBER_OF_ROWS");
	    String colNumStr = NodeC45.m_sXMMNodeC45.Read_getProfile("NUMBER_OF_COLUMNS"); //attributes 수
	    String colList =  NodeC45.m_sXMMNodeC45.Read_getProfile("COLUMN_LIST");
	    String newColList = NodeC45.m_sXMMNodeC45.Read_getProfile("NEW_COLUMN_LIST");
	    String colIndex = NodeC45.m_sXMMNodeC45.Read_getProfile("COLUMN_INDEX");
	    String newColIndex=null;
	    if(!newColList.equals("null")) {
	    newColIndex = NodeC45.m_sXMMNodeC45.Read_getProfile("NEW_COLUMN_INDEX");
      }
    //DB의 instances 수를 set한다.
    num_instances = Integer.parseInt(rowNumStr);

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

    //colList와 colIndex를 가지고 attribute name을 구한다.
    StringTokenizer tokend_colList = new StringTokenizer(colList, "^");
    while(tokend_colList.hasMoreElements()){
      attriName.add( (String)tokend_colList.nextElement() ); //column list를 temp 벡터에 넣는다.
    }

	//newColList와 newColIndex를 가지고 나머지 attribute name을 구한다.
    if(!newColList.equals("null"))
	{
		StringTokenizer tokend_newColList = new StringTokenizer(newColList, "^");
		while(tokend_newColList.hasMoreElements()){
		  attriName.add( (String)tokend_newColList.nextElement() ); //column list를 temp 벡터에 넣는다.
		}
	}
    //column의 type을 구해 attriType Vector에 저장한다.
    String colName, colType, colProperty;
    int size = attriName.size();

    for(int i = 0; i < size; i++){
      colName = (String)attriName.elementAt(i);
      System.out.println("colName= "+colName);
      colProperty = NodeC45.m_sXMMNodeC45.Read_getProfile(colName); //해당 column 속성을 읽는다.
      StringTokenizer tokend_colProperty = new StringTokenizer(colProperty, "^");
      colType = (String)tokend_colProperty.nextElement(); //column 속성에서 column type을 얻는다.
      attriType.add(colType);
    }
    NodeC45.m_sXMMNodeC45.Read_close();
  }


  void jTable_Column_mouseClicked(MouseEvent e)
  {
    if(old_MethodLearn)
    {
          int x=0,y=0;
          x=jTable_Column.getSelectedRow();
          y=jTable_Column.getSelectedColumn();
          if(y>=3&&x>=0)
          {
             C45Table.setValueBool( x, y,num_rows);
          }
          System.out.println("jTable_Column_mouseClicked  old_MethodLearn="+old_MethodLearn);
     }
     else
         System.out.println("jTable_Column_mouseClicked  old_MethodLearn="+old_MethodLearn);

  }


////////////////////////////////////////////////////////////////////////////////
//
// SetParameter을 생성하는 함수
//
// SetParameter(),ReadParameter()
//
////////////////////////////////////////////////////////////////////////////////
  private int SetParameter()
  {

    XMGraph m_cGraph=NodeC45.GetXMGraph();
    m_sProjectName =new String(NodeC45.GetProjectNameC45());
    ParameterSetting=NodeC45.Getm_bParameterSetting();
    ms_modelname= new String(NodeC45.Getms_modelname());
    if(ParameterSetting)
    {
       if (ReadParameter(m_sProjectName,ms_modelname)==0) return (int)0;
       else return (int)1;
    }
    else
    {
      //MethodSetting
      old_ms_modelname=new String(ms_modelname);
      old_TreeGen =true;
      old_MethodLearn =true;
      old_MethodTest =true;
      old_m_sSelectedLearnData = new String(NOINPUTDATA);
      old_m_sSelectedTestData= new String(NOINPUTDATA);
      old_m_sSelectedLearnOutput = new String(NOOUTPUTDATA);
      old_m_sSelectedTestOutput = new String(NOOUTPUTDATA);

      Setm_sLearnData(ParameterSetting);
      Setm_sLearnResult(ParameterSetting);
      Setm_sTestData(ParameterSetting);
      Setm_sTestResult(ParameterSetting);

      //parameterSetting
      old_m_minobs=2;
      old_m_cf=0.25;
      old_m_subset=false;
      old_m_GainRatio=false;

      old_m_Sigtest=false;
      old_m_Redundancy=1;
      old_m_Sigthresh=0.05;

      return (int)0;
      //Column Selection
      // 추가 필요

    }

  }



  private int ReadParameter(String m_sProjectName,String ms_modelname)
  {
    try
    {
      int i;

      NodeC45.m_sXMMNodeC45.Read_setFileStatus(m_sProjectName,ms_modelname+"_par");

      //METHOD SETTING

      old_ms_modelname=new String(ms_modelname);
      if(NodeC45.m_sXMMNodeC45.Read_getProfile("TREEGEN").equals("true"))old_TreeGen=true;
	    else old_TreeGen=false;
	    if(NodeC45.m_sXMMNodeC45.Read_getProfile("METHODLEARN").equals("true"))old_MethodLearn=true;
	    else old_MethodLearn=false;
	    if(NodeC45.m_sXMMNodeC45.Read_getProfile("METHODTEST").equals("true"))old_MethodTest=true;
	    else old_MethodTest=false;

      old_m_sSelectedLearnData = new String(NodeC45.m_sXMMNodeC45.Read_getProfile("LEARNDATA"));
      old_m_sSelectedTestData= new String(NodeC45.m_sXMMNodeC45.Read_getProfile("TESTDATA"));
      old_m_sSelectedLearnOutput = new String(NodeC45.m_sXMMNodeC45.Read_getProfile("LEARNRESULT"));
      old_m_sSelectedTestOutput = new String(NodeC45.m_sXMMNodeC45.Read_getProfile("TESTRESULT"));
      //System.out.println("old_ms_modelname : "+old_ms_modelname+"  old_TreeGen : "+old_TreeGen+"  old_MethodLearn : "+ old_MethodLearn +
      //                    "  old_MethodTest : "+old_MethodTest+"  old_m_sSelectedLearnData : " + old_m_sSelectedLearnData
			//			  + "  old_m_sSelectedTestData   "+old_m_sSelectedTestData +"  old_m_sSelectedLearnOutput:"+old_m_sSelectedLearnOutput
			//			  + "   old_m_sSelectedTestOutput  "+ old_m_sSelectedTestOutput);

	    Setm_sLearnData(ParameterSetting);
      Setm_sLearnResult(ParameterSetting);
      Setm_sTestData(ParameterSetting);
      Setm_sTestResult(ParameterSetting);

      //PARAMETER SETTING(TREE)
      old_m_minobs= Integer.parseInt(NodeC45.m_sXMMNodeC45.Read_getProfile("MINOBS"));
      old_m_cf= Double.parseDouble(NodeC45.m_sXMMNodeC45.Read_getProfile("CF"));
      if(NodeC45.m_sXMMNodeC45.Read_getProfile("SUBSET").equals("true"))old_m_subset=true;
	    else old_m_subset=false;
	    if(NodeC45.m_sXMMNodeC45.Read_getProfile("GAINRATIO").equals("true"))old_m_GainRatio=true;
	    else old_m_GainRatio=false;
	    //old_m_subset =Boolean.getBoolean(NodeC45.m_sXMMNodeC45.Read_getProfile("SUBSET"));
      //old_m_GainRatio =Boolean.getBoolean(NodeC45.m_sXMMNodeC45.Read_getProfile("GAINRATIO"));

      //PARAMETER SETTING(RULE)
      old_m_Redundancy= Integer.parseInt(NodeC45.m_sXMMNodeC45.Read_getProfile("REDUNDANCY"));
      old_m_Sigthresh= Double.parseDouble(NodeC45.m_sXMMNodeC45.Read_getProfile("SIGTHRESH"));
      //old_m_Sigtest = Boolean.getBoolean(NodeC45.m_sXMMNodeC45.Read_getProfile("SIGTEST"));
	    if(NodeC45.m_sXMMNodeC45.Read_getProfile("SIGTEST").equals("true"))old_m_Sigtest=true;
	    else old_m_Sigtest=false;
      m_iMethodStatus = NodeC45.Getm_iMethodStatus();



      //ColumSelection
      String[] temp_column_list=NodeC45.m_sXMMNodeC45.Read_getProfiles("COLUMN_LIST");
      String[] temp_column_type=NodeC45.m_sXMMNodeC45.Read_getProfiles("COLUMN_TYPE");
      //String[] temp_m_iUse=NodeC45.m_sXMMNodeC45.Read_getProfiles("TOTALCOLUMNUSAGE");
      int num_col = temp_column_list.length;

      for(i=0;i<num_col;i++)
      {
          v_sColumn_list.addElement(temp_column_list[i]);
          v_sColumn_type.addElement(temp_column_type[i]);
          //v_iUse.addElement(temp_m_iUse);

      }
      NodeC45.m_sXMMNodeC45.Read_close();
      //System.out.println("old_ms_modelname : "+old_ms_modelname+"  old_TreeGen : "+old_TreeGen+"  old_MethodLearn : "+ old_MethodLearn +
      //                    "  old_MethodTest : "+old_MethodTest+"  old_m_sSelectedLearnData : " + old_m_sSelectedLearnData + "  old_m_sSelectedTestData   "+old_m_sSelectedTestData +
      //                    "  old_m_minobs : "+old_m_minobs+"  old_m_cf : " + old_m_cf + "  old_m_subset   "+old_m_subset +
      //                    "  old_m_GainRatio : "+old_m_GainRatio+"  old_m_Redundancy : " + m_Sigthresh + "  old_m_Sigtest   "+old_m_Sigtest);
      return(0);
    }
      catch (Exception e) {
    	 e.printStackTrace();
         System.out.println(e.toString());
    	return(1);
    }
  }

  private void Setm_sLearnData(boolean ParameterSetting)
  {
      int i = 0,InputArcNum=0,m_iUniqueId;
      XMVNode m_cVNode = null;
      XMGraph m_cGraph=NodeC45.GetXMGraph();
      InputArcNum=NodeC45.GetInCount(m_cGraph);
       System.out.println( "Setm_sLearnData     InputArcNum="+InputArcNum);
      if(InputArcNum<=0)
      {
          m_sLearnData= new String[1];
          m_sLearnDataArc=new String[1];
          m_sLearnData[0]= new String(NOINPUTDATA);
		  m_sLearnDataArc[0]=new String(NOINPUTDATA);
          m_iLearnData=1;
      }
      else if(ParameterSetting)
      {
         m_sLearnData= new String[InputArcNum];
		 m_sLearnDataArc= new String[InputArcNum];
         m_iLearnData= -1;
         for(i=0;i<InputArcNum;i++)
         {
             m_cVNode = (XMVNode)NodeC45.GetInElement(i,m_cGraph);
             m_iUniqueId=m_cVNode.GetUniqueId();
             String tempString = new String(m_cVNode.GetName());
             String tempString2 = "arc"+m_iUniqueId;
             //String tempString = new String("arc"+m_iUniqueId);
             m_sLearnData[i]=new String(tempString);
             m_sLearnDataArc[i]=new String(tempString2);
             if(old_m_sSelectedLearnData.equals(m_sLearnDataArc[i]))m_iLearnData=i;

        }
        if(m_iLearnData<0)
        {
          //old_m_sSelectedLearnData = new String(m_sLearnData[0]);
          m_iLearnData=1;
        }
      }
      else
      {
         m_sLearnData= new String[InputArcNum];
		 m_sLearnDataArc= new String[InputArcNum];
         m_iLearnData= -1;
         for(i=0;i<InputArcNum;i++)
         {
             m_cVNode = (XMVNode)NodeC45.GetInElement(i,m_cGraph);
             m_iUniqueId=m_cVNode.GetUniqueId();
             String tempString = new String(m_cVNode.GetName());
             String tempString2 = "arc"+m_iUniqueId;
             //String tempString = new String("arc"+m_iUniqueId);
             m_sLearnData[i]=new String(tempString);
             m_sLearnDataArc[i]=new String(tempString2);
             //if(old_m_sSelectedLearnData.equals(m_sLearnDataArc[i]))m_iLearnData=i;
         }

         old_m_sSelectedLearnData = new String(m_sLearnDataArc[0]);
         m_iLearnData=1;
      }


  }

  private String DataArcName(boolean m_bInputFlag, String m_sArcName)
  {
      int i = 0,InputArcNum=0,m_iUniqueId;
      XMVNode m_cVNode = null;
      XMGraph m_cGraph=NodeC45.GetXMGraph();

	  if(m_bInputFlag)
	        InputArcNum=NodeC45.GetInCount(m_cGraph);
      else
            InputArcNum=NodeC45.GetOutCount(m_cGraph);
      System.out.println("m_sArcName="+m_sArcName+" InputArcNum"+InputArcNum);

      if(InputArcNum<0)
      {
          return new String(NOINPUTDATA);
      }
      else
      {
         //m_sLearnData= new String[InputArcNum];
         //m_iLearnData= -1;
         for(i=0;i<InputArcNum;i++)
         {
             if(m_bInputFlag) m_cVNode = (XMVNode)NodeC45.GetInElement(i,m_cGraph);
             else    m_cVNode = (XMVNode)NodeC45.GetOutElement(i,m_cGraph);
             if(m_sArcName.equals(m_cVNode.GetName()))
			 {
			      String tempString = new String();
                  m_iUniqueId=m_cVNode.GetUniqueId();
				  tempString = "arc"+m_iUniqueId;
				  return tempString;
			  }

        }

      }
	  if(m_bInputFlag)	  return new String(NOINPUTDATA);
	  else  return new String(NOOUTPUTDATA);

  }


  private void Setm_sLearnResult(boolean ParameterSetting)
  {
      int i = 0,OutputArcNum=0,m_iUniqueId;
      XMVNode m_cVNode = null;
      XMGraph m_cGraph=NodeC45.GetXMGraph();
      OutputArcNum=NodeC45.GetOutCount(m_cGraph);
      if(OutputArcNum<=0)
      {
          m_sLearnOutput= new String[1];
          m_sLearnOutput[0]= new String(NOOUTPUTDATA);
          old_m_sSelectedLearnOutput=new String(m_sLearnOutput[0]);
          m_iLearnOutput=0;
      }
      else if(ParameterSetting)
      {
         m_sLearnOutput= new String[OutputArcNum];
         m_iLearnOutput= -1;
         for(i=0;i<OutputArcNum;i++)
         {
             m_cVNode = (XMVNode)NodeC45.GetOutElement(i,m_cGraph);
             m_iUniqueId=m_cVNode.GetUniqueId();
             String tempString = new String(m_cVNode.GetName());
             //tempString = "arc"+m_iUniqueId;
             //String tempString = new String("arc"+m_iUniqueId);
             m_sLearnOutput[i]=new String(tempString);
             if(old_m_sSelectedLearnOutput.equals(m_sLearnOutput[i]))m_iLearnOutput=i;

        }
        if(m_iLearnOutput<0)
        {
          old_m_sSelectedLearnOutput = new String(m_sLearnOutput[0]);
          m_iLearnOutput=0;
        }
      }
      else
      {
         m_sLearnOutput= new String[OutputArcNum];
         m_iLearnOutput= -1;
         for(i=0;i<OutputArcNum;i++)
         {
             m_cVNode = (XMVNode)NodeC45.GetOutElement(i,m_cGraph);
             //m_iUniqueId=m_cVNode.GetUniqueId();
             String tempString = new String(m_cVNode.GetName());
             //tempString = "arc"+m_iUniqueId;
             //String tempString = new String("arc"+m_iUniqueId);
             m_sLearnOutput[i]=new String(tempString);
         }

         old_m_sSelectedLearnOutput = new String(m_sLearnOutput[0]);
         m_iLearnOutput=1;
      }

  }


  private void Setm_sTestData(boolean ParameterSetting)
  {
      int i = 0,InputArcNum=0,m_iUniqueId;
      XMVNode m_cVNode = null;
      XMGraph m_cGraph=NodeC45.GetXMGraph();
      InputArcNum=NodeC45.GetInCount(m_cGraph);

      if(InputArcNum<=0)
      {
          m_sTestData= new String[1];
          m_sTestData[0]= new String(NOINPUTDATA);
          m_iTestData=1;
      }
      else if(ParameterSetting)
      {
         m_sTestData= new String[InputArcNum];
         m_iTestData= -1;
         for(i=0;i<InputArcNum;i++)
         {
             m_cVNode = (XMVNode)NodeC45.GetInElement(i,m_cGraph);
             //m_iUniqueId=m_cVNode.GetUniqueId();
             String tempString = new String(m_cVNode.GetName());
             //tempString = "arc"+m_iUniqueId;

             //String tempString = new String("arc"+m_iUniqueId);
             m_sTestData[i]=new String(tempString);
             if(old_m_sSelectedTestData.equals(m_sTestData[i]))m_iTestData=i;

        }
        if(m_iTestData<0)
        {
          old_m_sSelectedTestData = new String(m_sTestData[0]);
          m_iTestData=1;
        }
      }
      else
      {
         m_sTestData= new String[InputArcNum];
         m_iTestData= -1;
         for(i=0;i<InputArcNum;i++)
         {
             m_cVNode = (XMVNode)NodeC45.GetInElement(i,m_cGraph);
             //m_iUniqueId=m_cVNode.GetUniqueId();
             String tempString = new String(m_cVNode.GetName());
             //tempString = "arc"+m_iUniqueId;

             m_sTestData[i]=new String(tempString);
         }

         old_m_sSelectedTestData = new String(m_sTestData[0]);
         m_iTestData=1;
      }
  }


  private void Setm_sTestResult(boolean ParameterSetting)
  {
      int i = 0,OutputArcNum=0,m_iUniqueId;
      XMVNode m_cVNode = null;
      XMGraph m_cGraph=NodeC45.GetXMGraph();
      OutputArcNum=NodeC45.GetOutCount(m_cGraph);

      if(OutputArcNum<=0)
      {
          m_sTestOutput= new String[1];
          m_sTestOutput[0]= new String(NOOUTPUTDATA);
          m_iTestOutput=1;
      }
      else if(ParameterSetting)
      {
         m_sTestOutput= new String[OutputArcNum];
         m_iTestOutput= -1;
         for(i=0;i<OutputArcNum;i++)
         {
             m_cVNode = (XMVNode)NodeC45.GetOutElement(i,m_cGraph);
             //m_iUniqueId=m_cVNode.GetUniqueId();
             String tempString = new String(m_cVNode.GetName());
             //tempString = "arc"+m_iUniqueId;
             m_sTestOutput[i]=new String(tempString);
             if(old_m_sSelectedTestOutput.equals(m_sTestOutput[i]))m_iTestOutput=i;

        }
        if(m_iTestOutput<0)
        {
          old_m_sSelectedTestOutput = new String(m_sTestOutput[0]);
          m_iTestOutput=1;
        }
      }
      else
      {
         m_sTestOutput= new String[OutputArcNum];
         for(i=0;i<OutputArcNum;i++)
         {
             m_cVNode = (XMVNode)NodeC45.GetOutElement(i,m_cGraph);
             m_iUniqueId=m_cVNode.GetUniqueId();
             String tempString = new String(m_cVNode.GetName());
             //tempString = "arc"+m_iUniqueId;
             m_sTestOutput[i]=new String(tempString);
         }

         old_m_sSelectedTestOutput = new String(m_sTestOutput[0]);
         m_iTestOutput=1;
      }
  }

////////////////////////////////////////////////////////////////////////////////
//
// Write Prameter을 생성하는 함수
//
//
////////////////////////////////////////////////////////////////////////////////
  private int WriteParameter(String m_sProjectName, String ms_modelname)
  {
    try
    {
      int i;

      //NodeC45.SetCORBA();
      NodeC45.m_sXMMNodeC45.Write_setFileStatus(m_sProjectName,ms_modelname+"_par");

	  NodeC45.m_sXMMNodeC45.Write_setProfile("PROJECT_PASS",NodeC45.m_sProjectPass);
      //METHOD SETTING
       if(TreeGen)NodeC45.m_sXMMNodeC45.Write_setProfile("TREEGEN","true");
       else NodeC45.m_sXMMNodeC45.Write_setProfile("TREEGEN","false");

       if(MethodLearn)NodeC45.m_sXMMNodeC45.Write_setProfile("METHODLEARN","true");
       else NodeC45.m_sXMMNodeC45.Write_setProfile("METHODLEARN","false");

       if(MethodTest)NodeC45.m_sXMMNodeC45.Write_setProfile("METHODTEST","true");
       else NodeC45.m_sXMMNodeC45.Write_setProfile("METHODTEST","false");

       NodeC45.m_sXMMNodeC45.Write_setProfile("LEARNDATA",m_sSelectedLearnData);
       NodeC45.m_sXMMNodeC45.Write_setProfile("TESTDATA",m_sSelectedTestData);
       NodeC45.m_sXMMNodeC45.Write_setProfile("LEARNRESULT",m_sSelectedLearnOutput);
       NodeC45.m_sXMMNodeC45.Write_setProfile("TESTRESULT",m_sSelectedTestOutput);
       NodeC45.m_sXMMNodeC45.Write_setProfile("METHODSTATUS",String.valueOf(m_iMethodStatus));
       //PARAMETER SETTING(TREE)
       NodeC45.m_sXMMNodeC45.Write_setProfile("MINOBS",String.valueOf(m_minobs));
       NodeC45.m_sXMMNodeC45.Write_setProfile("CF",String.valueOf(m_cf));


       if(m_subset)NodeC45.m_sXMMNodeC45.Write_setProfile("SUBSET","true");
       else NodeC45.m_sXMMNodeC45.Write_setProfile("SUBSET","false");


       if(m_GainRatio)NodeC45.m_sXMMNodeC45.Write_setProfile("GAINRATIO","true");
       else NodeC45.m_sXMMNodeC45.Write_setProfile("GAINRATIO","false");



      //PARAMETER SETTING(RULE)
      NodeC45.m_sXMMNodeC45.Write_setProfile("REDUNDANCY",String.valueOf(m_Redundancy));
      NodeC45.m_sXMMNodeC45.Write_setProfile("SIGTHRESH",String.valueOf(m_Sigthresh));

      if(m_Sigtest)NodeC45.m_sXMMNodeC45.Write_setProfile("SIGTEST","true");
      else NodeC45.m_sXMMNodeC45.Write_setProfile("SIGTEST","false");


      //ColumSelection
      int size=v_sColumn_list.size();

      int m_iTempUse=0;
      String m_sTempUse=null;
      m_iInputNum=0;
      String m_sOutputColumnName=null;
      String [] m_sInputColumnList = new String[size];
      String [] m_sInputColumn_type = new String[size];
      String   m_sOutputColumnList =null;
      String   m_sOutputColumn_type = null;
      for(i=0;i<size;i++)
      {
         m_sTempUse=(String)new_v_iUse.elementAt(i);
         m_iTempUse=Integer.parseInt(m_sTempUse);
         if(m_iTempUse==0)
         {
           m_sInputColumnList[m_iInputNum]=(String)v_sColumn_list.elementAt(i);
           m_sInputColumn_type[m_iInputNum]=(String)v_sColumn_type.elementAt(i);

           System.out.println("m_sInputColumnList["+m_iInputNum+"]="+m_sInputColumnList[m_iInputNum]);
           System.out.println("m_sInputColumn_type["+m_iInputNum+"]="+m_sInputColumn_type[m_iInputNum]);
           m_iInputNum++;
         }
         if(m_iTempUse==1)
         {
            m_sOutputColumnList=(String)v_sColumn_list.elementAt(i);
            m_sOutputColumn_type=(String)v_sColumn_type.elementAt(i);
         }
      }
      m_sColumnList = new String[m_iInputNum+1];
      String [] m_sColumn_type = new String[m_iInputNum+1];
      for(i=0;i<m_iInputNum;i++)
      {
         m_sColumnList[i]=m_sInputColumnList[i];
         m_sColumn_type[i]=m_sInputColumn_type[i];
      }
      m_sColumnList[m_iInputNum]=m_sOutputColumnList;
      m_sColumn_type[m_iInputNum]=m_sOutputColumn_type;


      // m_sColumnList[i]=(String)v_sColumn_list.elementAt(i);
      // System.out.println("m_sColumnList["+i+"]="+m_sColumnList[i]);
      // m_sColumn_type[i]=(String)v_sColumn_type.elementAt(i);
      // m_sUse[i]=(String)new_v_iUse.elementAt(i);

      NodeC45.m_sXMMNodeC45.Write_setProfiles("COLUMN_LIST",m_sColumnList);
      NodeC45.m_sXMMNodeC45.Write_setProfiles("COLUMN_TYPE",m_sColumn_type);
     // NodeC45.m_sXMMNodeC45.Write_setProfiles("COLUMN_USAGE",m_sUse);

      NodeC45.m_sXMMNodeC45.Write_close();

      NodeC45.Setms_modelname(ms_modelname);
      NodeC45.Setm_bParameterSetting(true);
      //System.out.println("ms_modelname : "+ms_modelname+"  TreeGen : "+TreeGen+" MethodLearn : "+ MethodLearn +
      //                    "  MethodTest : "+MethodTest+"  m_sSelectedLearnData : " + m_sSelectedLearnData + "  m_sSelectedTestData   "+m_sSelectedTestData +
      //                    "  m_minobs : "+m_minobs+"  m_cf : " + m_cf + "  m_subset   "+m_subset +
      //                    "  m_GainRatio : "+m_GainRatio+"  m_Redundancy : " + m_Sigthresh + "  m_Sigtest   "+m_Sigtest);
      return(0);
    }
      catch (Exception e) {
    	e.printStackTrace();
      System.out.println(e.toString());
    	return(1);
    }
  }

 boolean CheckPrameterChanege()
 {

    //
    //METHOD SETTING
    //
    //MODEL NAME SETTING
    if(!ms_modelname.equals(old_ms_modelname))return true;
    //TREEGEN SETTING
    if(TreeGen!=old_TreeGen ) return true;
    //METHODLEARN SETTING
    if(MethodLearn!=old_MethodLearn) return true;
    //MOTHODTEST SETTING
    if(MethodTest!=old_MethodTest)return true;
    //LEARN INPUT SETTING
    if(!m_sSelectedLearnData.equals(old_m_sSelectedLearnData))return true;
    //LEARN OUTPUT SETTING
    if(!m_sSelectedLearnOutput.equals(old_m_sSelectedLearnOutput))return true;
    //TEST INPUT SETTING
    if(!m_sSelectedTestData.equals(old_m_sSelectedTestData))return true;
    //TEST OUTPUT SETTING
    if(!m_sSelectedTestOutput.equals(old_m_sSelectedTestOutput))return true;

    //
    //PARAMETER SETTING(TREE)
    //

    //m_cf
    if(m_cf!=old_m_cf)return true;
    //m_minobs
    if(m_minobs!=old_m_minobs)return true;
    //subset
    if(m_subset!=old_m_subset)return true;
    //gainratio
    if(m_GainRatio!=old_m_GainRatio)return true;
    //REDUNDANCY
    if(m_Redundancy!=old_m_Redundancy)return true;
    //SIGTHRESH
    if(m_Sigthresh!=old_m_Sigthresh)return true;
    //SIGTEST
    if(m_Sigtest!=old_m_Sigtest)return true;
    //
    //
    //COLUMN INFORMATION
    //
    //
    int length1 = new_v_iUse.size();
    int length2 = v_iUse.size();
    int i,j;
    if(length1!= length2) return true;
    else
      {
        for(i=0;i<length1;i++)
        {
          if(!v_iUse.elementAt(i).equals(new_v_iUse.elementAt(i))) return true;
        }
      }
    return false;
 }



  void jRadioButton_Tree_mouseClicked(MouseEvent e)
  {

       jRadioButton_Tree.setSelected(true);
       jRadioButton_Rule.setSelected(false);
       jComboBox_Sigtest.setEnabled(false);
       jTextField_Sigthresh.setEnabled(false);
       jTextField_Redundancy.setEnabled(false);
  }



  void jRadioButton_Rule_mouseClicked(MouseEvent e)
  {
       jRadioButton_Tree.setSelected(false);
    	 jRadioButton_Rule.setSelected(true);
       jComboBox_Sigtest.setEnabled(true);
       jTextField_Sigthresh.setEnabled(true);
       jTextField_Redundancy.setEnabled(true);
  }

  void jCheckBox_Learn_mouseClicked(MouseEvent e)
  {
    if(jCheckBox_Learn.isSelected())
    {
      jComboBox_LearnInput.setEnabled(true);
      jComboBoxLearnOutput.setEnabled(true);

      jTextField_minobs.setEnabled(true);
      jComboBox_subset.setEnabled(true);
      jRadioButton_gainratio.setEnabled(true);
      jRadioButton_gain.setEnabled(true);
      jTextField_cf.setEnabled(true);
      jComboBox_Sigtest.setEnabled(true);
      jTextField_Sigthresh.setEnabled(true);
      jTextField_Redundancy.setEnabled(true);
      jTable_Column.setEnabled(true);
      System.out.println("C45Table.SetEnable(true);");
      old_MethodLearn=true;
      C45Table.SetEnable(true);

    }
    else
    {
      jComboBox_LearnInput.setEnabled(false);
      jComboBoxLearnOutput.setEnabled(false);
      jTextField_minobs.setEnabled(false);
      jComboBox_subset.setEnabled(false);
      jRadioButton_gainratio.setEnabled(false);
      jRadioButton_gain.setEnabled(false);
      jTextField_cf.setEnabled(false);
      jComboBox_Sigtest.setEnabled(false);
      jTextField_Sigthresh.setEnabled(false);
      jTextField_Redundancy.setEnabled(false);
      jTable_Column.setEnabled(false);
      System.out.println("C45Table.SetEnable(false);");
      old_MethodLearn=false;
      C45Table.SetEnable(false);
    }

  }

  void jCheckBox_Test_mouseClicked(MouseEvent e)
  {
    if(jCheckBox_Test.isSelected())
    {
      jComboBox_TestInput.setEnabled(true);
      jComboBoxTestOutput.setEnabled(true);

    }
    else
    {
      jComboBox_TestInput.setEnabled(false);
      jComboBoxTestOutput.setEnabled(false);
    }
  }

  void jComboBox_Sigtest_itemStateChanged(ItemEvent e)
  {
         if(jComboBox_Sigtest.getSelectedIndex()==0)
         {
             jTextField_Sigthresh.setEnabled(true);
         }
         else if(jComboBox_Sigtest.getSelectedIndex()==1)
         {
              jTextField_Sigthresh.setEnabled(false);
         }
         else
         {
             jTextField_Sigthresh.setEnabled(true);
         }
  }







  //수정 필요
  void jComboBox_LearnInput_itemStateChanged(ItemEvent e)
  {
     String TempData= new String("");
	 System.out.println("jComboBox_LearnInput_itemStateChanged #1");
	 if(Start_flag)
	 {
	        System.out.println("jComboBox_LearnInput_itemStateChanged #2");
			return;
     }
     m_iLearnData=jComboBox_LearnInput.getSelectedIndex();
     TempData = (String)jComboBox_LearnInput.getItemAt(m_iLearnData);
     System.out.println("TempData="+TempData);
	 temp_m_sSelectedLearnData = DataArcName(true,TempData);
	 System.out.println("temp_m_sSelectedLearnData="+temp_m_sSelectedLearnData);

	 //temp_m_sSelectedLearnData=(String)jComboBox_LearnInput.getItemAt(m_iLearnData);

     if( temp_m_sSelectedLearnData.equals(this.NOINPUTDATA))
     {
       System.out.println("jComboBox_LearnInput_itemStateChanged #3");
	   return;
     }
     if(temp_m_sSelectedLearnData.equals(old_m_sSelectedLearnData))
     {
         System.out.println("jComboBox_LearnInput_itemStateChanged #4");
		 tableSetting(temp_m_sSelectedLearnData,true);
         //SetTableColumn_parm(temp_m_sSelectedLearnData);
     }
     else
     {
        System.out.println("jComboBox_LearnInput_itemStateChanged #5");
		tableSetting(temp_m_sSelectedLearnData,true);
     }
  }

  void SetTableColumn_parm(String m_SelectedLearnData)
  {
    int i=0,j=0,m_iUsage=0;
    for(i=0;i<num_rows;i++)
    {
       m_iUsage=Integer.parseInt((String)v_iUse.elementAt(i))+3;
       C45Table.setValueBool(i,m_iUsage, num_rows);
    }
  }

  boolean check_in_m_sLearnData(String m_SelectedLearnData)
  {
    int i=0,arraysize;
    arraysize=m_sLearnData.length;
    for(i=0;i<arraysize;i++)
        if(m_SelectedLearnData.equals(m_sLearnDataArc[i])) return true;
    return false;
  }

////////////////////////////////////////////////////////////////////////////////
//
// MetaFile을 생성하는 함수
//
// MakeMetaFile()
// MakeMetaFileForLearnData()
// MakeMetaFileForTestData()
////////////////////////////////////////////////////////////////////////////////
 private void MakeMetaFile()
 {
   if( MethodLearn &&
       !m_sSelectedLearnData.equals(NOINPUTDATA) &&
       !m_sSelectedLearnOutput.equals(NOOUTPUTDATA))
        MakeMetaFileForLearnData();


    //MOTHODTEST SETTING
   if( MethodTest &&
       !m_sSelectedTestData.equals(NOINPUTDATA) &&
       !m_sSelectedTestOutput.equals(NOOUTPUTDATA))
        MakeMetaFileForTestData();
 }
void MakeMetaFileForLearnData()
{
  int i=0;
  try
	{
	    NodeC45.m_sXMMNodeC45.Read_setFileStatus(m_sProjectName,m_sSelectedLearnData);
      NodeC45.m_sXMMNodeC45.Write_setFileStatus(m_sProjectName,m_sSelectedLearnOutput);

      NodeC45.m_sXMMNodeC45.Write_setProfile("DATA_FILE",m_sSelectedLearnOutput);
      String rowNumStr = NodeC45.m_sXMMNodeC45.Read_getProfile("NUMBER_OF_ROWS");
      NodeC45.m_sXMMNodeC45.Write_setProfile("NUMBER_OF_ROWS",rowNumStr);
      NodeC45.m_sXMMNodeC45.Write_setProfile("NUMBER_OF_COLUMNS",Integer.toString(m_iInputNum+2));

      String[] m_sColumnList_in=new String[m_iInputNum+2];
      String[] m_sColumnIndex=new String[m_iInputNum+2];
      for(i=0;i<m_iInputNum+1;i++)
      {
           m_sColumnIndex[i]= new String(Integer.toString(i+1));
           m_sColumnList_in[i]= new String(m_sColumnList[i]);
      }
      m_sColumnList_in[m_iInputNum+1]=new String("Estimate_Value");
      m_sColumnIndex[m_iInputNum+1]= new String(Integer.toString(m_iInputNum+2));

      NodeC45.m_sXMMNodeC45.Write_setProfiles("COLUMN_LIST",m_sColumnList_in);
      NodeC45.m_sXMMNodeC45.Write_setProfiles("COLUMN_INDEX",m_sColumnIndex);
      NodeC45.m_sXMMNodeC45.Write_setProfile("ROW_INDEX",m_sSelectedLearnOutput);
      NodeC45.m_sXMMNodeC45.Write_setProfile("PREVIOUS_NODE","XMMNODEC45");
      for(i=0;i<=m_iInputNum;i++)
      {
         String m_sColumnPropety = NodeC45.m_sXMMNodeC45.Read_getProfile(m_sColumnList[i]);
         if( m_sColumnPropety!=null)
            NodeC45.m_sXMMNodeC45.Write_setProfile(m_sColumnList[i],m_sColumnPropety);
      }
      String m_sColumnPropety = NodeC45.m_sXMMNodeC45.Read_getProfile(m_sColumnList[m_iInputNum]);
      if( m_sColumnPropety!=null)
            NodeC45.m_sXMMNodeC45.Write_setProfile(m_sColumnList_in[m_iInputNum+1],m_sColumnPropety);
      NodeC45.m_sXMMNodeC45.Write_close();
      NodeC45.m_sXMMNodeC45.Read_close();

  }
	catch (org.omg.CORBA.BAD_OPERATION e)
	{
		 e.printStackTrace();
	}
}
void MakeMetaFileForTestData()
{
  int i=0;
  try
	{

	    NodeC45.m_sXMMNodeC45.Read_setFileStatus(m_sProjectName,m_sSelectedTestData);
      NodeC45.m_sXMMNodeC45.Write_setFileStatus(m_sProjectName,m_sSelectedTestOutput);

      NodeC45.m_sXMMNodeC45.Write_setProfile("DATA_FILE",m_sSelectedTestOutput);
      String rowNumStr = NodeC45.m_sXMMNodeC45.Read_getProfile("NUMBER_OF_ROWS");
      NodeC45.m_sXMMNodeC45.Write_setProfile("NUMBER_OF_ROWS",rowNumStr);
      NodeC45.m_sXMMNodeC45.Write_setProfile("NUMBER_OF_COLUMNS",Integer.toString(m_iInputNum+2));
      String[] m_sColumnList_in=new String[m_iInputNum+2];
      String[] m_sColumnIndex=new String[m_iInputNum+2];
      for(i=0;i<m_iInputNum+1;i++)
      {
           m_sColumnIndex[i]= new String(Integer.toString(i+1));
           m_sColumnList_in[i]= new String(m_sColumnList[i]);
      }
      m_sColumnList_in[m_iInputNum+1]=new String("Estimate_Value");
      m_sColumnIndex[m_iInputNum+1]= new String(Integer.toString(m_iInputNum+2));

      NodeC45.m_sXMMNodeC45.Write_setProfiles("COLUMN_LIST",m_sColumnList_in);
      NodeC45.m_sXMMNodeC45.Write_setProfiles("COLUMN_INDEX",m_sColumnIndex);
      NodeC45.m_sXMMNodeC45.Write_setProfile("ROW_INDEX",m_sSelectedTestOutput);
      NodeC45.m_sXMMNodeC45.Write_setProfile("PREVIOUS_NODE","XMMNODEC45");
      for(i=0;i<=m_iInputNum;i++)
      {
         String m_sColumnPropety = NodeC45.m_sXMMNodeC45.Read_getProfile(m_sColumnList[i]);
         if( m_sColumnPropety!=null)
            NodeC45.m_sXMMNodeC45.Write_setProfile(m_sColumnList[i],m_sColumnPropety);
      }
      String m_sColumnPropety = NodeC45.m_sXMMNodeC45.Read_getProfile(m_sColumnList[m_iInputNum]);
      if( m_sColumnPropety!=null)
            NodeC45.m_sXMMNodeC45.Write_setProfile(m_sColumnList_in[m_iInputNum+1],m_sColumnPropety);
      NodeC45.m_sXMMNodeC45.Write_close();
      NodeC45.m_sXMMNodeC45.Read_close();
  }
	catch (org.omg.CORBA.BAD_OPERATION e)
	{
		 e.printStackTrace();
	}


}




}//end of class
