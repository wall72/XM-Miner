package xmminer.xmserver.xmgraph.xmmnode.xmmnodeneuralnetwork;

import xmminer.xmserver.xmgraph.xmmnode.xmmnodeneuralnetwork.*;
import xmminer.xmlib.xmtable.*;
import xmminer.xmlib.xmcompute.*;


import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.lang.*;


public class XMNNFile {
 
private String[] input_column_size;
private String[] input_column_type;
private String[] output_column_size;
private String[] output_column_type;

public CXMNeuralNetworkFileManager      NNFileManager;
public CXMNeuralNetworkFileSaver        NNFileSaver;
public CXMNeuralNetworkOutputTableSaver NNOutputTableSaver;
public XMNNData NNData ;

public XMNNFile(XMNNData _NNData) {
 NNData = _NNData;
 NNFileManager		= new CXMNeuralNetworkFileManager();
 NNFileSaver		= new CXMNeuralNetworkFileSaver();
 NNOutputTableSaver = new CXMNeuralNetworkOutputTableSaver();
 }

public void Normalize_Learn()
{
  //////=============create transfer _ & normalize ======/////
   NNFileSaver.setFileStatus(NNData.project, NNData.m_sSelectedLearnData,false);
   NNFileSaver.createNeuralNetworkFile(NNData.InputColumnList   , NNData.OutputColumnList,
                                       NNData.iInputTransMethod , NNData.iOutputTransMethod);
   NNFileSaver.close();
   init_Learn();
  //////=============create transfer _ & normalize ======/////
}

public void Normalize_Test()
{
  //////=============create transfer _ & normalize ======/////
   NNFileSaver.setFileStatus(NNData.project, NNData.m_sSelectedTestData,true);
   NNFileSaver.setLearnedArcName(NNData.m_sSelectedLearnData);

   NNFileSaver.createNeuralNetworkFile(NNData.InputColumnList   , NNData.OutputColumnList,
                                       NNData.iInputTransMethod , NNData.iOutputTransMethod);
   NNFileSaver.close();
   init_Test();
  //////=============create transfer _ & normalize ======/////
}

public void openNNOutputTableSaver(String in_arc , String out_arc)
{
  NNOutputTableSaver.setFileStatus(NNData.project,in_arc,out_arc);

}
public void createResult()
{
  NNOutputTableSaver.createResultFile();
}

public  void closeNNOutputTableSaver()
{
  NNOutputTableSaver.close();
}

private void init_Learn()
{
   CXMMetaDataReader cmr_nn = new CXMMetaDataReader();
   cmr_nn.setFileStatus( NNData.project , NNData.m_sSelectedLearnData+"_neural_network");
   input_column_size = cmr_nn.getProfiles("INPUT_COLUMN_SIZE");
   input_column_type = cmr_nn.getProfiles("INPUT_COLUMN_TYPE");
   output_column_size= cmr_nn.getProfiles("OUTPUT_COLUMN_SIZE");
   output_column_type= cmr_nn.getProfiles("OUTPUT_COLUMN_TYPE");
   cmr_nn.close();
}
private void init_Test()
{
   CXMMetaDataReader cmr_nn = new CXMMetaDataReader();
   cmr_nn.setFileStatus( NNData.project , NNData.m_sSelectedTestData+"_neural_network");
   input_column_size = cmr_nn.getProfiles("INPUT_COLUMN_SIZE");
   input_column_type = cmr_nn.getProfiles("INPUT_COLUMN_TYPE");
   output_column_size= cmr_nn.getProfiles("OUTPUT_COLUMN_SIZE");
   output_column_type= cmr_nn.getProfiles("OUTPUT_COLUMN_TYPE");
   cmr_nn.close();
}
public int getInNodeNum(){
    int node = 0;
             //  iInputTransMethod 
			 //           
			 // "One of N Codes" , "Boolean", "Not Transfer" 
    for (int i = 0; i < NNData.iInputTransMethod.length;i++)
    { if ( NNData.iInputTransMethod[i] == 1)
       node = node + Integer.parseInt(input_column_size[i]);
     else     // " Boolean  "  "Not Transfer"(Integer or Real)
       node ++;
     }
	
  return node;
}

public int getOutNodeNum(){
   int node = 0;
	for (int i = 0; i < NNData.iOutputTransMethod.length;i++)
    { if ( NNData.iOutputTransMethod[i] == 1)
       node = node + Integer.parseInt(output_column_size[i]);
     else   // "Boolean"  "Not Transfer"(Integer or Real)
       node ++;
     }

 return node;
}


public int getNumberOfRow(String _arcName)
{ int rows;
  CXMMetaDataReader cmr = new CXMMetaDataReader();
  cmr.setFileStatus(NNData.project,_arcName);
  rows = str2Int(cmr.getProfile("NUMBER_OF_ROWS"));
  cmr.close();   
  return rows;
}

public void openInputOutput(String _arcName){
 NNFileManager.setFileStatus(NNData.project,_arcName);
 return;
}

public void closeInputOutput(){
 NNFileManager.close();
 return;
}

public void getInputOutput(int row_num,double[] Input,double[] Desired)
{
///======================
  
  String[] unique_values;
  int unique_length = 0;
  CXMMetaDataReader cmr_nn = new CXMMetaDataReader();
  cmr_nn.setFileStatus( NNData.project , NNData.m_sSelectedLearnData+"_neural_network");


   int data = row_num;
   int col_num = 1;      
   int ch;


   for (int i=0;i < NNData.iInputTransMethod.length;i++)
   {
     if(NNData.iInputTransMethod[i] == 5)    //  if InputTransMethod value is Not Transfer(5)
      {
	   double in_double = NNFileManager.getInputDouble(data,i+1);
       Input[col_num] = in_double;
       col_num++;
       }
	 else if (NNData.iInputTransMethod[i] == 4) // if InputTransMethod value is Boolean
     {
	   int in_integer = NNFileManager.getInputInteger(data,i+1);
//	   System.out.println(" int col_num = "+col_num);
       Input[col_num] = (double) in_integer;
       col_num++;
     }
	 else if (NNData.iInputTransMethod[i] ==1) // if InputTransMethod value is OneOfNCodes
	 { 
 	    ch = 0;
	    byte[] in  = NNFileManager.getInputBytes(data,i+1);
        for(int j=0;j < Integer.parseInt(input_column_size[i]);j++)
        { 
          Input[col_num] = (double) in[j];
		  if (in [j] == 1)
		    ch = 1;
          col_num ++;
         }
		 if (ch == 0)
		 { ch = Integer.parseInt(input_column_size[i]);
		   for(int j=0;j < Integer.parseInt(input_column_size[i]);j++)
	        { Input[col_num-ch] = (double)1/Integer.parseInt(input_column_size[i]);
			  int tt = col_num-ch;
 		   	  ch--;
             }
	     }
     }
   }

   col_num=1;
   for (int i=0;i < NNData.iOutputTransMethod.length;i++)
   {
     if(NNData.iOutputTransMethod[i] == 5)
      {
	   double out_double = NNFileManager.getOutputDouble(data,i+1);
       Desired[col_num] = out_double;
       col_num++;
      }
	else if(NNData.iOutputTransMethod[i] == 4) // if InputTransMethod value is Boolean
	 {

       int out_integer = NNFileManager.getOutputInteger(data,i+1);
       Desired[col_num] = (double) out_integer;
       col_num++;
	 }
     else if(NNData.iOutputTransMethod[i] == 1) // if InputTransMethod value is OneofNcode
     {
	    ch = 0;
        byte[] out  = NNFileManager.getOutputBytes(data,i+1);
        for(int j=0;j < Integer.parseInt(output_column_size[i]);j++)
        { 
          Desired[col_num] = (double) out[j];
		  if (out [j] == 1)
		    ch = 1;
          col_num ++;
        }
         if (ch == 0)
		 {ch = Integer.parseInt(output_column_size[i]);
	      for(int j=0;j < Integer.parseInt(output_column_size[i]);j++)
	        { Desired[col_num-ch] = (double)1/Integer.parseInt(output_column_size[i]);
			  int temp = col_num-ch;
 		  	 ch--;
             }
	     }
     }
   }

 cmr_nn.close();
 return;
}


public void save_net(double[][][] Weight,double[][] Bias,int NumLayer,int[] NumLayerNode)  // throws IOExcetion
{
  int 	l,n1,n2;
  try   {
    String weightfilename;
    weightfilename = NNData.projectpath +"/"+ NNData.project+"/data/"+NNData.ms_networkname + "_nn.wqt" ;
    RandomAccessFile weightfile = new RandomAccessFile(weightfilename,"rw");
    int count = 0;
    for(l=1; l<=NumLayer;l++)
    {
		for(n1=1; n1<=NumLayerNode[l];n1++)
		{

          weightfile.writeBytes(String.valueOf(Bias[l][n1]));
          weightfile.writeBytes("\r\n");
		 for(n2=1;n2<=NumLayerNode[l-1];n2++) {
          count++;
          weightfile.writeBytes(String.valueOf(Weight[l][n2][n1]));
          weightfile.writeBytes("\r\n");
          }
		}
    }
    weightfile.close();
    }
  catch(IOException e)
  {
   System.out.println("Write Error");
  }
  return;
}



public void saveResult(double[] _result,int row)
{
   int col_num=1;
   int col =1;
   for (int i=0;i < NNData.iOutputTransMethod.length;i++)
   {
     if(NNData.iOutputTransMethod[i] == 1)     // "One of N Codes",
      { convertOneOfNCodes(_result,col_num,Integer.parseInt(output_column_size[i]),col,row) ;
	    col_num += Integer.parseInt(output_column_size[i]);
		col++;
      }
    else if(NNData.iOutputTransMethod[i] == 4)  //  "Boolean"
      { convertBoolean(_result,col_num,1,col,row) ;
	    col_num ++;
		col++;
      }
    else if(NNData.iOutputTransMethod[i] == 5)  // "Not Transfer" 
      { convertNotTransfer(_result,col_num,1,col,row) ;
	    col_num ++;
		col++;
      }
   }
 return;
 } 

private void convertOneOfNCodes(double[] result,int s_col,int e_col,int _col,int _row)
{ 
  double temp;
  byte[] val = new byte[e_col];
  int index = 0;
  temp = -999.0f;
  for(int j=0;j < e_col;j++)
     {	 if ( result[s_col+j] > temp)
	      {  index = j;
			temp = result[s_col+j];
		  }
	  } 
  for(int j=0;j<e_col;j++)
   {
      if (index == j)
         val[j] = 1;
      else
	     val[j] =0;
   }
 NNFileManager.setResultBytes(_row,_col,val);
 return;
}

private void convertNotTransfer(double[] result,int s_col,int e_col,int _col,int _row)
{ 
  double val ;
  val = result[s_col];
  NNFileManager.setResultDouble(_row,_col,val);  
  return;
}

private void convertBoolean(double[] result,int s_col,int e_col,int _col,int _row)
{ 
  int val ;
  if (result[s_col] < 0.5f)
    val = 0;
  else
    val = 1;

  NNFileManager.setResultInteger(_row,_col,val);
  return;
}


private int str2Int(String str)
    {   Integer i = new Integer(str);
       	return i.intValue();
    }

private float str2Float(String str)
    {   Float f = new Float(str);
       	return f.floatValue();
    }

private double str2Double(String str)
    {
        Double d = new Double(str);
       	return d.doubleValue();
    }
    private long  str2Long(String str)
    {
        Long l = new Long(str);
       	return l.longValue();
    }

}
