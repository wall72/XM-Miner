package xmminer.xmclient.xmgraph.xmdnode.xmdnodepartitioning;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import java.util.Vector;
import xmminer.xmclient.xmgraph.*;
import xmminer.xmclient.xmgraph.XMDialogGraphElement;
import xmminer.xmclient.xmgraph.xmdnode.xmdnodepartitioning.*;
import xmminer.xmserver.xmgraph.xmdnode.xmdnodepartitioning.*;
import xmminer.xmserver.xmgraph.xmdnode.xmdnodepartitioning.XMBDNodePartitioning;

public class XMDialogDNodePartitioning extends JDialog {

// Modified by Sun Jee Hun ///////////////////////////////////////////////////////
	private boolean modified;
	public boolean isModified()
	{
		return modified;
	}
//////////////////////////////////////////////////////////////////////////////////

  //*** developer declared class&variable
  XMBDNodePartitioning sdp;
  XMDNodePartitioning cdp;
  XMDNodeTextFieldParsing tfp;
  String previous_arc;
  String training_arc;
  String validation_arc;
  String arc = "arc";
  int selected_index;
  int meta_status;
  int total_rows;
  JFrame m_frame;

// Modified by Sun Jee Hun ///////////////////////////////////////////////////////
	String[] column_list;
//////////////////////////////////////////////////////////////////////////////////

  JOptionPane optionPane;

  //***

  JPanel panel1 = new JPanel();
  JPanel panel2 = new JPanel();
  JButton button_ok = new JButton();
  JButton button_cancel = new JButton();
  Border border1;
  JPanel jPanel1 = new JPanel();
  TitledBorder titledBorder1;
  BorderLayout borderLayout1 = new BorderLayout();
  JTabbedPane jTabbedPane1 = new JTabbedPane();
  JPanel jPanel2 = new JPanel();
  JPanel jPanel3 = new JPanel();
  BorderLayout borderLayout2 = new BorderLayout();
  JPanel jPanel7 = new JPanel();
  JPanel jPanel21 = new JPanel();
  BorderLayout borderLayout3 = new BorderLayout();
  JPanel jPanel22 = new JPanel();
  JLabel jLabel3 = new JLabel();
  JTextField jTextField3 = new JTextField();
  JTextField jTextField1 = new JTextField();
  JTextField jTextField2 = new JTextField();
  JPanel jPanel13 = new JPanel();
  JLabel jLabel2 = new JLabel();
  JTextField jTextField4 = new JTextField();
  JTextField jTextField5 = new JTextField();
  BorderLayout borderLayout5 = new BorderLayout();
  JPanel jPanel16 = new JPanel();
  JPanel jPanel20 = new JPanel();
  JPanel jPanel24 = new JPanel();
  JLabel jLabel6 = new JLabel();
  JTextField jTextField6 = new JTextField();
  JLabel jLabel7 = new JLabel();
  JTextField jTextField7 = new JTextField();
  JLabel jLabel8 = new JLabel();
  JTextField jTextField8 = new JTextField();
  JLabel jLabel9 = new JLabel();
  JTextField jTextField9 = new JTextField();
  FlowLayout flowLayout1 = new FlowLayout();
  BorderLayout borderLayout6 = new BorderLayout();
  Border border2;
  FlowLayout flowLayout8 = new FlowLayout();
  JPanel jPanel4 = new JPanel();
  Border border3;
  TitledBorder titledBorder2;
  JLabel jLabel4 = new JLabel();
  JLabel jLabel5 = new JLabel();
  Border border4;
  TitledBorder titledBorder3;
  JLabel jLabel1 = new JLabel();
  FlowLayout flowLayout3 = new FlowLayout();
  GridLayout gridLayout1 = new GridLayout();
  FlowLayout flowLayout2 = new FlowLayout();
  Border border5;
  Border border6;
  TitledBorder titledBorder4;
  Border border7;
  Border border8;
  TitledBorder titledBorder5;
  Border border9;
  Border border10;
  FlowLayout flowLayout4 = new FlowLayout();
  GridLayout gridLayout2 = new GridLayout();
  FlowLayout flowLayout5 = new FlowLayout();

// Modified by Sun Jee Hun ///////////////////////////////////////////////////////
  // 생성자
  public XMDialogDNodePartitioning(JFrame frame, String title,
								   boolean modal,
								   XMBDNodePartitioning server_module,
								   XMDNodePartitioning client_module)
  {
    super(frame, title, modal);
    try {
      jbInit();
      m_frame = frame;
      cdp = client_module;
      setPreviousArc();
      setTrainingArc();
      setValidationArc();
      sdp = server_module;
	  sdp.setFileStatus(cdp.project_name,previous_arc,training_arc,validation_arc,"edit");
      meta_status = sdp.setFileStatus(cdp.project_name,previous_arc,training_arc,validation_arc,"edit");
	  setInitStatus();
	  getPartitioningObjectStatus();	// 객체의 상태정보 가져오기
	  save_action();	// 파티셔닝 객체의 현재 상태를 서버에 저장한다.
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    pack();
  }
//////////////////////////////////////////////////////////////////////////////////

  private void jbInit() throws Exception
  {
	  border1 = BorderFactory.createRaisedBevelBorder();
	  titledBorder1 = new TitledBorder("");
	  border2 = BorderFactory.createEmptyBorder(5,5,0,5);
	  border3 = BorderFactory.createEtchedBorder(Color.white,new Color(173, 172, 161));
	  titledBorder2 = new TitledBorder(border3,"TRAINING");
	  border4 = BorderFactory.createEtchedBorder(Color.white,new Color(173, 172, 161));
	  titledBorder3 = new TitledBorder(border4,"TESTING");
	  border5 = BorderFactory.createLineBorder(Color.gray,1);
	  border6 = BorderFactory.createLineBorder(Color.white,1);
	  titledBorder4 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(173, 172, 161)),"TRAINING");
	  border7 = BorderFactory.createEtchedBorder(Color.white,new Color(173, 172, 161));
	  border8 = BorderFactory.createEtchedBorder(Color.white,new Color(173, 172, 161));
	  titledBorder5 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(173, 172, 161)),"TESTING");
	  border9 = BorderFactory.createLineBorder(Color.gray,1);
	  border10 = BorderFactory.createLineBorder(Color.gray,1);
	  jPanel1.setLayout(flowLayout1);
	  button_ok.setFont(new java.awt.Font("Dialog", 0, 12));
	  button_ok.setBorder(border1);
	  button_ok.setMinimumSize(new Dimension(90, 30));
	  button_ok.setPreferredSize(new Dimension(90, 30));
	  button_ok.setText("확인");
	  button_ok.addActionListener(new XMDialogDNodePartitioning_button_ok_actionAdapter(this));
	  button_cancel.setFont(new java.awt.Font("Dialog", 0, 12));
	  button_cancel.setBorder(border1);
	  button_cancel.setMaximumSize(new Dimension(90, 30));
	  button_cancel.setMinimumSize(new Dimension(90, 30));
	  button_cancel.setPreferredSize(new Dimension(90, 30));
	  button_cancel.setText("취소");
	  button_cancel.addActionListener(new XMDialogDNodePartitioning_button_cancel_actionAdapter(this));
	  this.addWindowListener(new XMDialogDNodePartitioning_this_windowAdapter(this));
	  panel1.setLayout(borderLayout6);
	  panel1.setPreferredSize(new Dimension(430, 350));
	  jPanel1.setBorder(BorderFactory.createEtchedBorder());
	  jPanel1.setPreferredSize(new Dimension(500, 55));
	  panel2.setLayout(borderLayout1);
	  jTabbedPane1.setFont(new java.awt.Font("Dialog", 0, 12));

	  jTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {
	  public void stateChanged(ChangeEvent e) {
        jTabbedPane1_stateChanged(e);
      }
    });

    jPanel2.setLayout(borderLayout2);
    borderLayout2.setHgap(10);
    borderLayout2.setVgap(10);
    jPanel7.setLayout(gridLayout1);
    jPanel21.setBorder(BorderFactory.createEtchedBorder());
	jPanel21.setPreferredSize(new Dimension(10, 40));
    jPanel21.setLayout(borderLayout3);
    jLabel3.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel3.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel3.setText("데이터 분할 파일 전체 행수");
    jTextField3.setBorder(border5);
    jTextField3.setPreferredSize(new Dimension(150, 22));
    jTextField3.setEditable(false);
    jTextField3.setHorizontalAlignment(SwingConstants.RIGHT);
    jPanel22.setLayout(flowLayout8);
    jPanel22.setPreferredSize(new Dimension(400, 20));
    jPanel13.setLayout(flowLayout2);
    jLabel2.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel2.setPreferredSize(new Dimension(90, 18));
    jLabel2.setHorizontalAlignment(SwingConstants.LEFT);
    jLabel2.setText("분할행 갯수");
    jTextField2.setPreferredSize(new Dimension(210, 22));
    jTextField2.setHorizontalAlignment(SwingConstants.RIGHT);

// Modified by Sun Jee Hun ///////////////////////////////////////////////////////
	jTextField1.addKeyListener(new XMDialogDNodePartitioning_jTextField1_keyAdapter(this));
	jTextField2.addKeyListener(new XMDialogDNodePartitioning_jTextField2_keyAdapter(this));
	jTextField4.addKeyListener(new XMDialogDNodePartitioning_jTextField4_keyAdapter(this));
	jTextField5.addKeyListener(new XMDialogDNodePartitioning_jTextField5_keyAdapter(this));

	jTextField7.addKeyListener(new XMDialogDNodePartitioning_jTextField7_keyAdapter(this));
	jTextField9.addKeyListener(new XMDialogDNodePartitioning_jTextField9_keyAdapter(this));
//////////////////////////////////////////////////////////////////////////////////

    jTextField1.setPreferredSize(new Dimension(210, 22));
    jTextField1.setHorizontalAlignment(SwingConstants.RIGHT);

    jTextField5.setPreferredSize(new Dimension(210, 22));
    jTextField5.setHorizontalAlignment(SwingConstants.RIGHT);

    jTextField4.setBorder(BorderFactory.createLoweredBevelBorder());
    jTextField4.setPreferredSize(new Dimension(210, 22));
    jTextField4.setHorizontalAlignment(SwingConstants.RIGHT);

	jPanel3.setLayout(borderLayout5);
    jPanel16.setLayout(gridLayout2);
    jPanel20.setLayout(flowLayout4);
    jLabel7.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel7.setPreferredSize(new Dimension(90, 18));
    jLabel7.setHorizontalAlignment(SwingConstants.LEFT);
    jLabel7.setText("분할범위");
    jPanel24.setLayout(flowLayout5);
    jLabel8.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel8.setMaximumSize(new Dimension(150, 18));
    jLabel8.setPreferredSize(new Dimension(90, 18));
    jLabel8.setHorizontalAlignment(SwingConstants.LEFT);
    jLabel8.setText("분할범위");
    jLabel9.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel9.setMaximumSize(new Dimension(150, 18));
    jLabel9.setPreferredSize(new Dimension(90, 18));
    jLabel9.setHorizontalAlignment(SwingConstants.LEFT);
    jLabel9.setText("분할비율(%)");
    jLabel6.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel6.setPreferredSize(new Dimension(90, 18));
    jLabel6.setToolTipText("");
    jLabel6.setHorizontalAlignment(SwingConstants.LEFT);
    jLabel6.setText("분할비율(%)");

    borderLayout5.setHgap(10);
    borderLayout5.setVgap(10);
    flowLayout1.setAlignment(FlowLayout.RIGHT);
	flowLayout1.setVgap(10);
	titledBorder4.setTitleFont(new java.awt.Font("Dialog", 0, 12));
	titledBorder5.setTitleFont(new java.awt.Font("Dialog", 0, 12));
    jPanel20.setBorder(titledBorder4);

	jPanel20.setPreferredSize(new Dimension(400, 59));
    jPanel24.setBorder(titledBorder5);

	jPanel24.setPreferredSize(new Dimension(400, 59));
    borderLayout6.setVgap(5);
	this.setResizable(false);

	titledBorder3.setTitleFont(new java.awt.Font("Dialog", 0, 12));
	jPanel13.setBorder(titledBorder3);

	titledBorder2.setTitleFont(new java.awt.Font("Dialog", 0, 12));
	jPanel4.setBorder(titledBorder2);
	jPanel4.setLayout(flowLayout3);
	jLabel4.setMaximumSize(new Dimension(150, 18));
	jLabel4.setPreferredSize(new Dimension(90, 18));
	jLabel4.setHorizontalAlignment(SwingConstants.LEFT);
	jLabel4.setFont(new java.awt.Font("Dialog", 0, 12));
	jLabel4.setText("분할행 갯수");
	jLabel5.setMaximumSize(new Dimension(150, 18));
	jLabel5.setPreferredSize(new Dimension(90, 18));
    jLabel5.setHorizontalAlignment(SwingConstants.LEFT);
	jLabel5.setFont(new java.awt.Font("Dialog", 0, 12));
	jLabel5.setText("분할비율(%)");
	jLabel1.setMaximumSize(new Dimension(150, 18));
	jLabel1.setPreferredSize(new Dimension(90, 18));
	jLabel1.setFont(new java.awt.Font("Dialog", 0, 12));
	jLabel1.setText("분할비율(%)");
	gridLayout1.setRows(2);
	flowLayout3.setHgap(10);
	flowLayout3.setVgap(10);
	flowLayout2.setHgap(10);
    flowLayout2.setVgap(10);
    jTextField6.setBorder(border9);
    jTextField6.setPreferredSize(new Dimension(210, 22));
    jTextField6.setEditable(false);
    jTextField6.setHorizontalAlignment(SwingConstants.RIGHT);
    jTextField8.setBorder(border10);
    jTextField8.setPreferredSize(new Dimension(210, 22));
    jTextField8.setEditable(false);
    jTextField8.setHorizontalAlignment(SwingConstants.RIGHT);
    jTextField7.setPreferredSize(new Dimension(210, 22));
    jTextField7.setHorizontalAlignment(SwingConstants.RIGHT);
    jTextField9.setPreferredSize(new Dimension(210, 22));
    jTextField9.setHorizontalAlignment(SwingConstants.RIGHT);
    gridLayout2.setRows(2);
    flowLayout4.setHgap(10);
    flowLayout4.setVgap(10);
    flowLayout5.setHgap(10);
    flowLayout5.setVgap(10);
    panel1.add(panel2, BorderLayout.CENTER);
    panel2.add(jTabbedPane1, BorderLayout.CENTER);
    jTabbedPane1.add(jPanel2, "         무작위 분할         ");
    jPanel2.add(jPanel7, BorderLayout.CENTER);
    jPanel7.add(jPanel4, null);
    jPanel4.add(jLabel4, null);
    jPanel4.add(jTextField2, null);
    jPanel4.add(jLabel5, null);
    jPanel4.add(jTextField1, null);
    jPanel7.add(jPanel13, null);
    jPanel13.add(jLabel2, null);
    jPanel13.add(jTextField5, null);
    jPanel13.add(jLabel1, null);
    jPanel13.add(jTextField4, null);
    jTabbedPane1.add(jPanel3, "          순차적 분할          ");
    jPanel3.add(jPanel16, BorderLayout.CENTER);
    jPanel16.add(jPanel20, null);
    jPanel20.add(jLabel7, null);
    jPanel20.add(jTextField7, null);
    jPanel20.add(jLabel6, null);
    jPanel20.add(jTextField6, null);
    jPanel16.add(jPanel24, null);
    jPanel24.add(jLabel8, null);
    jPanel24.add(jTextField9, null);
    jPanel24.add(jLabel9, null);
    jPanel24.add(jTextField8, null);
    panel2.add(jPanel21, BorderLayout.NORTH);
    jPanel21.add(jPanel22, BorderLayout.CENTER);
    jPanel22.add(jLabel3, null);
    jPanel22.add(jTextField3, null);
    panel1.add(jPanel1, BorderLayout.SOUTH);
    jPanel1.add(button_ok, null);
    jPanel1.add(button_cancel, null);
    getContentPane().add(panel1);
}

// Modified by Sun Jee Hun ///////////////////////////////////////////////////////
	// 파티셔닝 객체에서 상태정보 가져오기
	void getPartitioningObjectStatus()
	{
		int total_data_number = cdp.getTotalDataNumber();
		int partitioning_number_of_training = cdp.getPartitioningNumberOfTraining();
		int partitioning_rate_of_training = cdp.getPartitioningRateOfTraining();
		int partitioning_number_of_testing = cdp.getPartitioningNumberOfTesting();
		int partitioning_rate_of_testing = cdp.getPartitioningRateOfTesting();
		int partitioning_type = cdp.getPartitioningType();
		String partitioning_range_of_training = cdp.getPartitioningRangeOfTraining();
		String partitioning_range_of_testing = cdp.getPartitioningRangeOfTesting();

		jTabbedPane1.setSelectedIndex(partitioning_type);
		selected_index = partitioning_type;

		if (partitioning_type == 0)
		{
			jTextField2.setText(partitioning_number_of_training + "");
			jTextField1.setText(partitioning_rate_of_training + "");
			jTextField5.setText(partitioning_number_of_testing + "");
			jTextField4.setText(partitioning_rate_of_testing + "");
		}
		else
		{
			jTextField7.setText(partitioning_range_of_training);
			jTextField6.setText(partitioning_rate_of_training + "");
			jTextField9.setText(partitioning_range_of_testing);
			jTextField8.setText(partitioning_rate_of_testing + "");
		}
	}

	// 파티셔닝 객체의 상태정보 셋팅하기 (무작위 분할)
	void setPartitioningObjectStatus(int selected_index, int partitioning_number_of_training,
									 int partitioning_rate_of_training, int partitioning_number_of_testing,
									 int partitioning_rate_of_teting)
	{
		int total_rows = sdp.getTotalRows();
		int column_count = column_list.length;

		cdp.setPartitioningType(selected_index);
		cdp.setTotalDataNumber(total_rows);
		cdp.setPartitioningNumberOfTraining(partitioning_number_of_training);
		cdp.setPartitioningRateOfTraining(partitioning_rate_of_training);
		cdp.setPartitioningNumberOfTesting(partitioning_number_of_testing);
		cdp.setPartitioningRateOfTesting(partitioning_rate_of_teting);
		cdp.setColumnCount(column_count);
	}

	// 파티셔닝 객체의 상태정보 셋팅하기 (순차적 분할)
	void setPartitioningObjectStatus(int selected_index, int partitioning_number_of_training,
									 int partitioning_rate_of_training, int partitioning_number_of_testing,
									 int partitioning_rate_of_teting, String partitioning_range_of_training,
									 String partitioning_range_of_testing)
	{
		int total_rows = sdp.getTotalRows();
		int column_count = column_list.length;

		cdp.setPartitioningType(selected_index);
		cdp.setTotalDataNumber(total_rows);
		cdp.setPartitioningNumberOfTraining(partitioning_number_of_training);
		cdp.setPartitioningRateOfTraining(partitioning_rate_of_training);
		cdp.setPartitioningNumberOfTesting(partitioning_number_of_testing);
		cdp.setPartitioningRateOfTesting(partitioning_rate_of_teting);
		cdp.setPartitioningRangeOfTraining(partitioning_range_of_training);
		cdp.setPartitioningRangeOfTesting(partitioning_range_of_testing);
		cdp.setColumnCount(column_count);
	}

  // OK버튼
  void button_ok_actionPerformed(ActionEvent e)
  {
    int training_number = 0;
    int validation_number = 0;

    if (selected_index==0)
    {
		try
		{
			if (jTextField2.getText().equals("") || Integer.parseInt(jTextField2.getText()) <= 0 ||
				jTextField1.getText().equals("") || Integer.parseInt(jTextField1.getText()) < 0 ||
				jTextField5.getText().equals("") || Integer.parseInt(jTextField5.getText()) <= 0 ||
				jTextField4.getText().equals("") || Integer.parseInt(jTextField4.getText()) < 0 ||
				Integer.parseInt(jTextField2.getText()) > Integer.parseInt(jTextField3.getText()) ||
				Integer.parseInt(jTextField5.getText()) > Integer.parseInt(jTextField3.getText())
				)
			{
				showWarningDialog("입력값이 잘못되었습니다 ! 1", "입력에러");
			}
			else
			{
				training_number = Integer.parseInt(jTextField2.getText());
				validation_number = total_rows - training_number;
				this.rightOK(training_number, validation_number);
			}
		}
		catch (Exception ae)
		{
			showWarningDialog("입력값이 잘못되었습니다 ! 2", "입력에러");
			System.out.println(ae);
		}
    }
    else if (selected_index==1)
    {
		try
		{
			if (jTextField7.getText().equals("") ||
				jTextField6.getText().equals("") || Integer.parseInt(jTextField6.getText()) < 0 ||
				jTextField9.getText().equals("") ||
				jTextField8.getText().equals("") || Integer.parseInt(jTextField8.getText()) < 0)
			{
				showWarningDialog("입력값이 잘못되었습니다 ! 3", "입력에러");
			}
			else
			{
				training_number = tfp.textFieldParsing(jTextField7.getText());
				validation_number = tfp.textFieldParsing(jTextField9.getText());
				String[][] training_value = tfp.getPartitionNumberArray(jTextField7.getText());
				String[][] validation_value = tfp.getPartitionNumberArray(jTextField9.getText());
				sdp.setTrainingValue(training_value);
				sdp.setValidationValue(validation_value);
				this.rightOK(training_number, validation_number);
			}
		}
		catch (Exception ae)
		{
			showWarningDialog("입력값이 잘못되었습니다 ! 4", "입력에러");
		}
    }
  }

	void rightOK(int training_number, int validation_number)
	{
		int pnotr, protr, pnote, prote;
		String prangeotr, prangeote;

		sdp.setSelectedIndex(selected_index);
		sdp.setTrainingNumber(training_number);
		sdp.setValidationNumber(validation_number);
		sdp.close();
		dispose();
		modified = true;

		// 무작위 분할
		if (selected_index == 0)
		{
			pnotr = Integer.parseInt(jTextField2.getText());
			protr = Integer.parseInt(jTextField1.getText());
			pnote = Integer.parseInt(jTextField5.getText());
			prote = Integer.parseInt(jTextField4.getText());
			setPartitioningObjectStatus(selected_index, training_number, protr, validation_number, prote);

		}

		// 순차적 분할
		else
		{
			prangeotr = jTextField7.getText();
			prangeote = jTextField9.getText();
			protr = Integer.parseInt(jTextField6.getText());
			prote = Integer.parseInt(jTextField8.getText());
			setPartitioningObjectStatus(selected_index, training_number, protr, validation_number, prote, prangeotr, prangeote);
		}
	}
//////////////////////////////////////////////////////////////////////////////////

// Modified by Sun Jee Hun ///////////////////////////////////////////////////////
	void save_action()
	{
		if (cdp.getPartitioningType() == 0)
		{
			sdp.setSelectedIndex(cdp.getPartitioningType());
			sdp.setTrainingNumber(cdp.getPartitioningNumberOfTraining());
			sdp.setValidationNumber(cdp.getPartitioningNumberOfTesting());
		}
		else
		{
			String[][] training_value = tfp.getPartitionNumberArray(cdp.getPartitioningRangeOfTraining());
			String[][] validation_value = tfp.getPartitionNumberArray(cdp.getPartitioningRangeOfTesting());
			sdp.setTrainingValue(training_value);
			sdp.setValidationValue(validation_value);

			sdp.setSelectedIndex(cdp.getPartitioningType());
			sdp.setTrainingNumber(cdp.getPartitioningNumberOfTraining());
			sdp.setValidationNumber(cdp.getPartitioningNumberOfTesting());
		}
		sdp.close();
	}

  // Cancel 버튼
	void button_cancel_actionPerformed(ActionEvent e)
	{
		save_action();
		dispose();
		modified = false;
	}

	void this_windowClosing(WindowEvent e)
	{
		save_action();
	    dispose();
		modified = false;
	}
//////////////////////////////////////////////////////////////////////////////////

  void setInitStatus()
  {
    if (meta_status!=0)
    {
       showWarningDialog();
       dispose();
    }
    total_rows = sdp.getTotalRows();

	// Modified by Sun Jee Hun ///////////////////////////////////////////////////////
	column_list = sdp.getColumnList();
	//////////////////////////////////////////////////////////////////////////////////

    jTextField3.setText(String.valueOf(total_rows));
    tfp = new XMDNodeTextFieldParsing();
  }

  private void showWarningDialog()
  {
     optionPane.showMessageDialog(m_frame,"메타 정보가 존재하지 않습니다.","메타 정보 처리 확인", optionPane.WARNING_MESSAGE);
  }

// Modified by Sun Jee Hun ///////////////////////////////////////////////////////
	private void showWarinigDialog(String msg, String title)
	{
		optionPane.showMessageDialog(m_frame, msg, title, optionPane.WARNING_MESSAGE);
	}
//////////////////////////////////////////////////////////////////////////////////

  private void showWarningDialog(String msg, String title)
  {
	  optionPane.showMessageDialog(m_frame, msg, title, optionPane.WARNING_MESSAGE);
  }

  void jTabbedPane1_stateChanged(ChangeEvent e)
  {
      selected_index = jTabbedPane1.getSelectedIndex();
  }

  private void setPreviousArc()
  {
      previous_arc = arc+cdp.previous_index;
  }

  private void setTrainingArc()
  {
      training_arc = arc+cdp.training_index;
  }

  private void setValidationArc()
  {
      validation_arc = arc+cdp.validation_index;
  }

// Modified by Sun Jee Hun ///////////////////////////////////////////////////////
// 트레이닝 분할행 갯수
	void jTextField2_keyReleased(KeyEvent e)
	{
		int training_number = 0; //training 열수를 저장할 integer형 변수 선언
		int training_percent = 0; //백분율 처리 변수 선언
		int tmp = e.getKeyCode();

		try
		{
			if( (tmp >= 48 && tmp <= 57) || (tmp >= 96 && tmp <= 105) || tmp == 8 || tmp == 127)
			{
				if  (jTextField2.getText().equals("") || Integer.parseInt(jTextField2.getText()) < 0)
				{
					jTextField1.setText("");
					jTextField4.setText("");
					jTextField5.setText("");
				}
				else if (Integer.parseInt(jTextField2.getText()) >= 0)
				{
					training_number = Integer.parseInt(jTextField2.getText()); //training 열수를 읽는다.
					training_percent = sdp.calNumToPer(training_number); //training 변수를 백분율로 변환
					jTextField1.setText(String.valueOf(training_percent)); //training 비율을 보여줌
					jTextField5.setText(String.valueOf(total_rows-training_number)); // validation 열수 보여줌
					jTextField4.setText(String.valueOf(100-training_percent)); //validation 비율 보여줌
				}
			}
		}
		catch (Exception ae)
		{}
	}

	// 트레이닝 분할 비율
	void jTextField1_keyReleased(KeyEvent e)
	{
		int training_percent = 0; //training 비율을 저장할 integer형 변수 선언
		int training_number = 0; //변환된 열수 처리 변수 선언
		int tmp = e.getKeyCode();

		try
		{
			if( (tmp >= 48 && tmp <= 57) || (tmp >= 96 && tmp <= 105) || tmp == 8 || tmp == 127)
			{
				if  (jTextField1.getText().equals("") || Integer.parseInt(jTextField1.getText()) < 0)
				{
					jTextField2.setText("");
					jTextField4.setText("");
					jTextField5.setText("");
				}

				else if (Integer.parseInt(jTextField1.getText()) >= 0)
				{
					training_percent = Integer.parseInt(jTextField1.getText()); //training 열수를 읽는다.
					training_number = sdp.calPerToNum(training_percent); //training 비율을 열수로 변환
					jTextField2.setText(String.valueOf(training_number)); //training 열수를 보여줌
					jTextField5.setText(String.valueOf(total_rows-training_number)); // validation 열수 보여줌
					jTextField4.setText(String.valueOf(100-training_percent)); //validation 비율 보여줌
				}
			}
		}
		catch (Exception ae)
		{}
	}

	// 테스팅 분할 비율
	void jTextField4_keyReleased(KeyEvent e)
	{
		int validation_percent = 0;
		int validation_number = 0;
		int tmp = e.getKeyCode();

		try
		{
			if( (tmp >= 48 && tmp <= 57) || (tmp >= 96 && tmp <= 105) || tmp == 8 || tmp == 127)
			{
				if  (jTextField4.getText().equals("") || Integer.parseInt(jTextField4.getText()) < 0)
				{
					jTextField2.setText("");
					jTextField1.setText("");
					jTextField5.setText("");
				}

				else if (Integer.parseInt(jTextField4.getText()) >= 0)
				{
					validation_percent = Integer.parseInt(jTextField4.getText());
					validation_number = sdp.calPerToNum(validation_percent); //validation 비율을 열수로 변환
					jTextField5.setText(String.valueOf(validation_number)); //validation 열수를 보여줌
					jTextField2.setText(String.valueOf(total_rows-validation_number)); // training 열수 보여줌
					jTextField1.setText(String.valueOf(100-validation_percent)); //training 비율 보여줌
				}
			}
		}
		catch (Exception ae)
		{}
	}

	// 테스팅 분할행 갯수
	void jTextField5_keyReleased(KeyEvent e)
	{
		int validation_number = 0; //validation 열수를 저장할 integer형 변수 선언
		int validation_percent = 0; //백분율 처리 변수 선언
		int tmp = e.getKeyCode();

		try
		{
			if( (tmp >= 48 && tmp <= 57) || (tmp >= 96 && tmp <= 105) || tmp == 8 || tmp == 127)
			{
				if  (jTextField5.getText().equals("") || Integer.parseInt(jTextField5.getText()) < 0)
				{
					jTextField2.setText("");
					jTextField4.setText("");
					jTextField1.setText("");
				}

				else if ( validation_number >= 0 )
				{
					validation_number = Integer.parseInt(jTextField5.getText()); //validation 열수를 읽는다.
					validation_percent = sdp.calNumToPer(validation_number);
					jTextField4.setText(String.valueOf(validation_percent));
					jTextField2.setText(String.valueOf(total_rows-validation_number));
					jTextField1.setText(String.valueOf(100-validation_percent));
				}
			}
		}
		catch (Exception ae)
		{}
	}

	// 순차적 분할 트레이닝 분할 범위
	void jTextField7_keyReleased(KeyEvent e)
	{
		try
		{
			if  (jTextField7.getText().equals(""))
			{
				jTextField6.setText("");
			}
			else
			{
				String training_str_value = jTextField7.getText();
				int training_str_number = tfp.textFieldParsing(training_str_value);
				jTextField6.setText(String.valueOf(sdp.calNumToPer(training_str_number)));
			}
		}
		catch (Exception ae)
		{}
	}

	// 순차적 분할 테스팅 분할범위
	void jTextField9_keyReleased(KeyEvent e)
	{
		try
		{
			if  (jTextField9.getText().equals(""))
			{
				jTextField8.setText("");
			}
			else
			{
				String validation_str_value = jTextField9.getText();
				int validation_str_number = tfp.textFieldParsing(validation_str_value);
				jTextField8.setText(String.valueOf(sdp.calNumToPer(validation_str_number)));
			}
		}
		catch (Exception ae)
		{}
	}
//////////////////////////////////////////////////////////////////////////////////
}

class XMDialogDNodePartitioning_button_ok_actionAdapter implements ActionListener {
  XMDialogDNodePartitioning adaptee;

  XMDialogDNodePartitioning_button_ok_actionAdapter(XMDialogDNodePartitioning adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.button_ok_actionPerformed(e);
  }
}

class XMDialogDNodePartitioning_button_cancel_actionAdapter implements ActionListener {
  XMDialogDNodePartitioning adaptee;

  XMDialogDNodePartitioning_button_cancel_actionAdapter(XMDialogDNodePartitioning adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.button_cancel_actionPerformed(e);
  }
}

class XMDialogDNodePartitioning_this_windowAdapter extends WindowAdapter {
  XMDialogDNodePartitioning adaptee;

  XMDialogDNodePartitioning_this_windowAdapter(XMDialogDNodePartitioning adaptee) {
    this.adaptee = adaptee;
  }

  public void windowClosing(WindowEvent e) {
    adaptee.this_windowClosing(e);
  }
}

// Modified by Sun Jee Hun ///////////////////////////////////////////////////////
class XMDialogDNodePartitioning_jTextField2_keyAdapter extends java.awt.event.KeyAdapter
{
	XMDialogDNodePartitioning adaptee;

	XMDialogDNodePartitioning_jTextField2_keyAdapter(XMDialogDNodePartitioning adaptee)
	{
		this.adaptee = adaptee;
	}

	public void keyReleased(KeyEvent e)
	{
		adaptee.jTextField2_keyReleased(e);
	}
}

class XMDialogDNodePartitioning_jTextField1_keyAdapter extends java.awt.event.KeyAdapter
{
	XMDialogDNodePartitioning adaptee;

	XMDialogDNodePartitioning_jTextField1_keyAdapter(XMDialogDNodePartitioning adaptee)
	{
		this.adaptee = adaptee;
	}

	public void keyReleased(KeyEvent e)
	{
		adaptee.jTextField1_keyReleased(e);
	}
}

class XMDialogDNodePartitioning_jTextField4_keyAdapter extends java.awt.event.KeyAdapter
{
	XMDialogDNodePartitioning adaptee;

	XMDialogDNodePartitioning_jTextField4_keyAdapter(XMDialogDNodePartitioning adaptee)
	{
		this.adaptee = adaptee;
	}

	public void keyReleased(KeyEvent e)
	{
		adaptee.jTextField4_keyReleased(e);
	}
}

class XMDialogDNodePartitioning_jTextField5_keyAdapter extends java.awt.event.KeyAdapter
{
	XMDialogDNodePartitioning adaptee;

	XMDialogDNodePartitioning_jTextField5_keyAdapter(XMDialogDNodePartitioning adaptee)
	{
		this.adaptee = adaptee;
	}

	public void keyReleased(KeyEvent e)
	{
		adaptee.jTextField5_keyReleased(e);
	}
}

class XMDialogDNodePartitioning_jTextField7_keyAdapter extends java.awt.event.KeyAdapter
{
	XMDialogDNodePartitioning adaptee;

	XMDialogDNodePartitioning_jTextField7_keyAdapter(XMDialogDNodePartitioning adaptee)
	{
		this.adaptee = adaptee;
	}

	public void keyReleased(KeyEvent e)
	{
		adaptee.jTextField7_keyReleased(e);
	}
}

class XMDialogDNodePartitioning_jTextField9_keyAdapter extends java.awt.event.KeyAdapter
{
	XMDialogDNodePartitioning adaptee;

	XMDialogDNodePartitioning_jTextField9_keyAdapter(XMDialogDNodePartitioning adaptee)
	{
		this.adaptee = adaptee;
	}

	public void keyReleased(KeyEvent e)
	{
		adaptee.jTextField9_keyReleased(e);
	}
}
//////////////////////////////////////////////////////////////////////////////////