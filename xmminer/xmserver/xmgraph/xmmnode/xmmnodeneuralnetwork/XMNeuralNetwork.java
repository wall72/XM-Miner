package xmminer.xmserver.xmgraph.xmmnode.xmmnodeneuralnetwork;

import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.util.Random;
import xmminer.xmlib.xmcompute.*;
import xmminer.xmlib.xmtable.*;
import java.util.*;

public class XMNeuralNetwork {

private int			SAVE_EPOCHE	 =	100 ;
private float		MAX_WGT   =    0.9f ;
private int			NUM_INP		;
private int			NUM_OUT	;
private double		WGT_LR		;
private double		MOM_LR		;
private double		BIS_LR		;
private int		    END_EPOCHE	;

private int        NumData;                            /* Number of input data          */
private int        NumLayer;                           /* Number of total layer (input layer 제외   */
private int[]      NumLayerNode ; //= {2,4,1}; //= new int [3];            /* Number of each layer's nodes  */
private double	   Err_limit ;      //= 0.1f;

private int        NumItr;                             /* Number of total iteration     */

private double[][]    Bias		;// = new double[3][300];
private double[][][]  Weight	;//= new double[3][300][300];    /* weight matrix           */
private double[][][]  Momentum	;//= new double[3][300][300];  /* weight matrix           */

private double[][]    Net		;//= new double[3][300];           /* Before activation function    */
private double[][]    Result	;//	= new double[3][300];        /* After activation function     */
private double[][]    Delta		;//= new double [3][300];         /* Delta value                   */

private double[]      Desired	; //= new double[300];        /* Desired data matrix           */
private double[]	  Input		; //= new double[300];          /* Input data matrix             */

private double	      ACCEPT_RATIO = 0.9f;

private XMNNData  NNData ;
private XMNNFile  NNFile ;
private XMBMNodeNeuralNetwork NNImpl;
  //Construct the XMNeuralNetwork

  public XMNeuralNetwork(XMNNData _NNData, XMBMNodeNeuralNetwork _NNImpl) {
	 NNData = _NNData;
	 NNImpl = _NNImpl;
     NNFile = new XMNNFile(NNData);
  }


 public int Learn() {        //ok
    init_net_For_Learn();
    NumData = NNFile.getNumberOfRow(NNData.m_sSelectedLearnData);
	training();
    return 1;
  }

 public  int Test() {
      init_net_For_Test();
      NumData = NNFile.getNumberOfRow(NNData.m_sSelectedTestData);
      testing();
   return 1;
  }

private double str2Double(String str)
    {
        Double d = new Double(str);
       	return d.doubleValue();
    }

////***********Initial network*************////////

protected void init_net_For_common(){
	NUM_INP		= NNFile.getInNodeNum();
    NUM_OUT		= NNFile.getOutNodeNum();
    WGT_LR		= NNData.m_LearningRate;
	MOM_LR		= NNData.m_MomentumRate;
    BIS_LR		= NNData.m_LearningRate;
    END_EPOCHE	= NNData.m_EndingEpoch;
    NumLayer    = NNData.m_HiddenLayerNum+1 ;         /* Number of total layer (input layer 제외)  */

	NumLayerNode = new int[NNData.m_HiddenLayerNum+2];
	NumLayerNode[0] = NUM_INP;
	NumLayerNode[NNData.m_HiddenLayerNum+1] = NUM_OUT;
    for (int i = 0; i < NNData.m_HiddenLayerNum ; i++ )
    	NumLayerNode[i+1] = Integer.parseInt(NNData.m_HiddenNode[i]);

	Err_limit = NNData.m_ErrorCriteria;

    Bias		= new double[NNData.m_HiddenLayerNum+2][300];
    Weight		= new double[NNData.m_HiddenLayerNum+2][300][300];		/* weight matrix           */
    Momentum	= new double[NNData.m_HiddenLayerNum+2][300][300];		/* weight matrix           */

    Net			= new double[NNData.m_HiddenLayerNum+2][300];        /* Before activation function    */
    Result		= new double[NNData.m_HiddenLayerNum+2][300];        /* After activation function     */
    Delta		= new double[NNData.m_HiddenLayerNum+2][300];        /* Delta value                   */
    Desired	    = new double[NUM_OUT+1];					    /* Desired data matrix           */
    Input	    = new double[NUM_INP+1];
}
protected void init_net_For_Learn()
{
   NNFile.Normalize_Learn();
   init_net_For_common();

   int l,n1,n2;
   Random randomNumber = new Random(System.currentTimeMillis());

     for(l=1;l<=NumLayer;l++)
		for(n1=1;n1<=NumLayerNode[l-1];n1++)
			for(n2=1;n2<=NumLayerNode[l];n2++)
			{	Weight[l][n1][n2] = (double) (MAX_WGT*(0.002f*(Math.abs(randomNumber.nextInt())%1000+1)-1.0));
				Momentum[l][n1][n2] = 0.0f;
             }
		for(l=1;l<=NumLayer;l++)
			for(n1=1;n1<=NumLayerNode[l];n1++)
			{	Bias[l][n1] = (double)(MAX_WGT*(0.002f*(Math.abs(randomNumber.nextInt())%1000+1)-1.0));
				Momentum[l][0][n1] = 0.0f;
 			 }
}

protected void init_net_For_Test()
{
   NNFile.Normalize_Test();
   init_net_For_common();

   int l,n1,n2;
  try{
    String weightfilename;
    weightfilename = NNData.projectpath +"/"+ NNData.project+"/data/"+NNData.ms_networkname + "_nn.wqt" ;
	String fname = new String(weightfilename);
    FileInputStream weightfile  = new FileInputStream(fname);
    BufferedReader weightdata = new BufferedReader(new InputStreamReader(weightfile));

    String line = new String();
	 for(l=1;l<=NumLayer;l++)
   		for(n1=1;n1<=NumLayerNode[l];n1++)
		{
       		line = weightdata.readLine();
			Bias[l][n1] = str2Double(line);
       		Momentum[l][0][n1] = 0.0f;

       		for(n2=1;n2<=NumLayerNode[l-1];n2++)
			{
	       		line = weightdata.readLine();
				Weight[l][n2][n1] = str2Double(line);
       			Momentum[l][n1][n2] = 0.0f;
			}
	    }
       }
	   catch (IOException ie)
	   { System.out.println("Network Initializing Error.");
	   }
}


 private void training()
 {   int 	    l,n1,n2,data;
  	 double 	err,dt,variation, Learn_Ratio;
	 int		simulated = 1 ;

     NNFile.openInputOutput( NNData.m_sSelectedLearnData);

	 Random random = new Random(System.currentTimeMillis());

	 NumItr = 0;

      err = 0.0f;

    for(data = 1; data <= NumData; data++)
    {
		if (NNImpl.KeepRunning()){
              NNImpl.setPBvalue(1);
		  }
		 else   // KeepRunning 이 false
		 { System.out.println("사용자에 의한 Neural network 생성 작업 종료");
		   return;
		 }
       NNFile.getInputOutput(data,Input,Desired);
       err += foward_propagate(data,Input,Desired);
    }

	  err /= NumData;
    variation = err;

	int  data1 = 0;

	while(err > Err_limit)
	{

          data = Math.abs(random.nextInt())%NumData+1;

		 if (NNImpl.KeepRunning()){
              NNImpl.setPBvalue(1);
		  }
		 else   // KeepRunning 이 false
		 {
            System.out.println("사용자에 의한 Neural network 생성 작업 종료");
		   return;
		 }


        NNFile.getInputOutput(data,Input,Desired);
        err = foward_propagate(data,Input,Desired);

		if (simulated == 1) Learn_Ratio = 0.9f ;
		else 	Learn_Ratio = WGT_LR ;

        /* Calculate Delta */
        l = NumLayer;
        for(n1=1;n1<=NUM_OUT;n1++)
	   	{
          dt = (double)Desired[n1] - Result[l][n1];
          Delta[l][n1] = diff_fn(Result[l][n1])*dt;
        }

        for(l=NumLayer-1;l>=1;l--)
			for(n1=1;n1<=NumLayerNode[l];n1++)
			{
				dt = 0.0f;
				for(n2=1;n2<=NumLayerNode[l+1];n2++)
					dt += Delta[l+1][n2]*Weight[l+1][n1][n2];

				Delta[l][n1] = diff_fn(Result[l][n1])*dt;
			}

        /* Adjust Network */
        for(l=NumLayer;l>0;l--)
			for(n1=1;n1<=NumLayerNode[l];n1++)
			{

				/* Adjust Bias */
				dt = BIS_LR*Delta[l][n1]*1.0f+MOM_LR*Momentum[l][0][n1];
				Bias[l][n1] += dt;
				Momentum[l][0][n1] = dt;

				/* Adjust Weight */
				for(n2=1;n2<=NumLayerNode[l-1];n2++)
				{
					dt = Learn_Ratio*Delta[l][n1]*Result[l-1][n2]+MOM_LR*Momentum[l][n2][n1];
					Weight[l][n2][n1] += dt;
					Momentum[l][n2][n1] = dt;
				}
			}

        NumItr++;
    		simulated = 0 ;

        if(NumItr%NumData==0)
		{
            err = 0.0f;

			for(data=1;data<=NumData;data++)
	         {
				NNFile.getInputOutput(data,Input,Desired);
				err += foward_propagate(data,Input,Desired);
			  }
			err /= NumData;
            variation = err - variation;

        NNImpl.setTSSValue(err);

        variation = err;

       if(NumItr%(NumData*END_EPOCHE) == 0)  break;
	   if(NumItr%(NumData*SAVE_EPOCHE) == 0) {
          NNFile.save_net(Weight,Bias,NumLayer,NumLayerNode);}
        }
		else
			err = 1000.0f;
	}


	SaveResult(1);
	NNFile.save_net(Weight,Bias,NumLayer,NumLayerNode);
	NNFile.closeInputOutput();
 return;
 }


private void SaveResult(int Training)
{
  int row;
  int data;
  int n1;
  double[] result = new double[NUM_OUT+1];

  if (Training == 1)
    NNFile.openNNOutputTableSaver(NNData.m_sSelectedLearnData,NNData.m_sSelectedLearnOutput);
  else
    NNFile.openNNOutputTableSaver(NNData.m_sSelectedTestData,NNData.m_sSelectedTestOutput);


  for(data = 1; data <= NumData; data++)
    {
       NNFile.getInputOutput(data,Input,Desired);
       foward_propagate(data,Input,Desired);
       for(n1=1;n1<=NUM_OUT;n1++)
		  result[n1] = Result[NumLayer][n1];
       NNFile.saveResult(result,data);
    }

  NNFile.createResult();
  NNFile.closeNNOutputTableSaver();
return;
}

 private void testing()
  {
    NNFile.openInputOutput(NNData.m_sSelectedTestData);
    SaveResult(2);
    NNFile.closeInputOutput();
  return;
  }


////***********Activation Function*************////////
 protected double activation_fn(double value)
 {
    return (double) (1.0f/(1.0f + Math.exp((double)(-value))));
 }

 protected double diff_fn(double value)
 {
   return (double)(value*(1.0f-value));
 }

////////*************Calculate Network************////////
 public double foward_propagate(int row, double [] Input,double [] Desired )
 {
  int l;
  int n1;
  int n2;
  double err;

  for(n1= 1; n1 <= NUM_INP;n1++)
     Result[0][n1] = (double) Input[n1];

  for(l=1;l<=NumLayer;l++)
  for(n2=1;n2<=NumLayerNode[l];n2++){
      Net[l][n2]=0.0f;
      for(n1=1;n1<=NumLayerNode[l-1];n1++)
           Net[l][n2] +=  Weight[l][n1][n2]*Result[l-1][n1];
      Net[l][n2] += Bias[l][n2];
      Result[l][n2] = activation_fn(Net[l][n2]);
   }
   err = 0.0f;
   for(n1=1;n1<=NUM_OUT;n1++)
      err+=(Desired[n1]-Result[NumLayer][n1])* (Desired[n1]-Result[NumLayer][n1]);

   return err/NUM_OUT;
 }

}
