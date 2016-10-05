
//Title:        C45_Classifier
//Version:
//Copyright:    Copyright (c) 1999
//Author:       최대우
//Company:      전북대학교 산업공학과 지능정보시스템
//Description:  Your description
package xmminer.xmserver.xmgraph.xmmnode.xmmnodec45.treegen;
import java.util.*;
import java.io.*;
import java.lang.*;
import xmminer.xmlib.xmtable.*;
import xmminer.xmlib.xmcompute.*;
import xmminer.xmserver.xmgraph.xmmnode.xmmnodec45.rulegen.typedef.*;

public class XMCTreeEvaluation extends XMCPruneTreeGen
{
  
////////////////////////////////////////////////////////////////////////////////
//
// 생성자
// public XMCTreeEvaluation()
// public XMCTreeEvaluation(String ProjectName, String Modelname)
// 
////////////////////////////////////////////////////////////////////////////////
  public XMCTreeEvaluation()
  {

  }
  
  public XMCTreeEvaluation(String ProjectName, String Modelname)
  {
        m_sProjectName = ProjectName;
        m_sModelName= Modelname;
  }

  
////////////////////////////////////////////////////////////////////////////////
//
// 생성자
// public XMCTreeEvaluation()
// public XMCTreeEvaluation(int mode)
// public XMCTreeEvaluation(String DataFileName)
// public XMCTreeEvaluation(String DataFileName, int mode)
// 
////////////////////////////////////////////////////////////////////////////////
  
  public void Evaluate(boolean CMInfo,XMCTree ETree) throws IOException
  {
    int RealClass, PrunedClass,EstimateClass;//, Category()
    int t;
    int ConfusionMat[], i,j, RawErrors, PrunedErrors;
    m_iPbCount_num++;
    exam_flag=true;
    
    //if ( CMInfo == true)
    //{
	   ConfusionMat = new int[(MaxClass+1)*(MaxClass+1)];
    
        for(i=0;i<(MaxClass+1)*(MaxClass+1);i++)
	    {
	       ConfusionMat[i]=0;
		}
    //}
    RawErrors = PrunedErrors = 0;

    int m_iRowNum,m_iColumnNum;

    for(i=0 ; i<=MaxItem ; ++i)
    {
        m_iPbCount_num++;
        RealClass          = GetClassValue(i);//Item[i][MaxAtt+1]
        EstimateClass=(int) Category(i, ETree); //추정된 클래스의 값
        SetEstimateValue(i,EstimateClass);

        SetEstimateValue(i,EstimateClass);
        //System.out.println(":::::::::Evaluate:::::::::\n ");
	    if ( EstimateClass != RealClass )
        {
            RawErrors++; //------------>Write!!
        
        }
        

//	 if ( CMInfo==true )
//	 {
		  ConfusionMat[RealClass*(MaxClass+1)+EstimateClass]++;
//	 }
    }//for(i=0 ; i<=MaxItem ; ++i)

    if ( TRIALS > 1 )
   {
			  //fprintf(fprin,"%4d", t);
			  //ETree.Tree_data.writeInt(t);
   }

    
    exam_flag=false;
    if ( CMInfo)
    if(m_bTestFlag)
  	  PrintConfusionMatrix(ConfusionMat,m_sProjectName,m_sModelName+"_TTestC");
	else
	  PrintConfusionMatrix(ConfusionMat,m_sProjectName,m_sModelName+"_TLearnC");
	 
    
    //System.out.println("Evaluate11");
  }// End of public void Evaluate(boolean ean CMInfo,int Saved)
////////////////////////////////////////////////////////////////////////////////
//
// public int Category(int row,XMCTree DecisionTree)  throws IOException
//
//
//
//
//
////////////////////////////////////////////////////////////////////////////////

public int Category(int m_iRowNum,XMCTree DecisionTree)  throws IOException
{
    int c, BestClass;
	m_iPbCount_num++;
    //System.out.println("Category1");
    if (  ClassSum==null )//.length
    {
	    ClassSum = new double [MaxClass+1];
    }
    //System.out.println("Category2");
    for(c=0 ; c<=MaxClass ; ++c)
    {
	    ClassSum[c] = 0;
    }
    //System.out.println("Category3");
    Classify(m_iRowNum, DecisionTree, 1.0);
    //System.out.println("Category4");
    BestClass = 0;
    for(c=0; c<=MaxClass ; ++c)
    {
	    //if (VERBOSITY >= 5) //fprintf(fprin,"class %s weight %.2f\n", ClassName[c], ClassSum[c]);
      //DecisionTree.Tree_data.writeBytes("class "+ClassName[c]+" weight "+ClassSum[c]+"\r\n");
	    if ( ClassSum[c] > ClassSum[BestClass] ) BestClass = c;
    }
    //System.out.println("BestClass  :"+BestClass);
    return BestClass;
 }// End of public int Category(Vector CaseDesc,XMCTree DecisionTree)
////////////////////////////////////////////////////////////////////////////////
//
// public void Classify(int m_iRowNum,XMCTree T,double Weight) throws IOException
// 
// 
// 
// 
// 
////////////////////////////////////////////////////////////////////////////////

//public void Classify(Vector CaseDesc,XMCTree T,double Weight) throws IOException
  public void Classify(int m_iRowNum,XMCTree T,double Weight) throws IOException
  {
    int v, dv;
    double Cv;
    int m_iColumnNum;
    int c;
    m_iPbCount_num++;
//    XMCTree testBranch= new XMCTree();
    XMCTree tempTree= new XMCTree();
    //CAttValue CAttValue_Att= new CAttValue();
    int m_iColumnValue;
    double m_dColumnValue;
    //System.out.println("Classify1");
    switch ( T.NodeType )
    {
        case 0:  // leaf
              
//              System.out.println("here is leaf +\n");          
              if ( T.Items > 0 )
              {
               		//  Update from ALL classes
                  
                  for(c=0 ; c<=MaxClass ; ++c)
                  {
                       if ( T.ClassDist[c] >0 )
                       {
                          ClassSum[c] += (double)(Weight * T.ClassDist[c] / T.Items);
                          //if(exam_flag)T.Tree_data.writeBytes(" ClassSum["+c+"]:" +"\t"+ ClassSum[c]);
//                          System.out.println(" ClassSum["+c+"]:" +"\t"+ ClassSum[c]);
                       }
                  }
                   
              }//if ( T.Items > 0 )

	       else
              {
		   ClassSum[T.Leaf] += (double)Weight;
		   
//		   System.out.println(" ClassSum["+T.Leaf+"]:" +"\t"+ ClassSum[T.Leaf]);
	      }
              return;

	case BrDiscr:  // test of discrete attribute

	     
//	     System.out.println("here is BrDiscr+\n");
	     m_iColumnNum = T.Tested;
                          
             v =GetIntegerValue(m_iRowNum, m_iColumnNum);   
                  
	     if ( v>0 && v <= T.Forks )	//  Make sure not new discrete value
	      {
                  XMCTree testBranch = (XMCTree)T.Branch.elementAt(v-1);
                  Classify(m_iRowNum, testBranch,Weight);
             }
             else
             {
                for(v=1 ; v<=T.Forks ; ++v)
                {
		              //Classify(CaseDesc, T->Branch[v],(Weight * T->Branch[v]->Items) / T.Items);

                   XMCTree testBranch = (XMCTree)T.Branch.elementAt(v-1);
                   Classify(m_iRowNum, testBranch,(Weight * testBranch.Items)/ T.Items);
		}
	     }
             return;

	case ThreshContin:  // test of continuous attribute
             
//	     System.out.println("here is ThreshContin +\n");
	     m_iColumnNum = T.Tested;
	           //Cv = CVal(CaseDesc, a); //CVal(Case,Attribute)   Case[Attribute]._cont_val
             //CAttValue_Att = (CAttValue)CaseDesc.elementAt(a);
             //Cv  = CAttValue_Att.get_cont_val();
             Cv=GetDoubleValue(m_iRowNum, m_iColumnNum);
             // Tree_data.writeBytes("case contin="+Cv);

	     if ( Cv == Unknown )
             {
                  for(v=1; v<=2 ; ++v)
                  {
                    //Classify(CaseDesc, T->Branch[v],(Weight * T->Branch[v]->Items) / T.Items);
                    XMCTree testBranch = (XMCTree)T.Branch.elementAt(v-1);
                    //Classify(CaseDesc, tempTree,(Weight * tempBranch.Items)/ T.Items);
                    Classify(m_iRowNum, testBranch,(Weight * testBranch.Items)/ T.Items);
	          }
             }
	     else
	     {
                 
                 v = (int)( Cv <= T.Cut ? 1 : 2 );
//                 System.out.println("m_iColumnNum: "+m_iColumnNum +"  AttName"+AttName[m_iColumnNum]+"  Cv :"+ Cv +"  T.Cut :"+T.Cut + "  v:"+v);
		             //Classify(CaseDesc, T->Branch[v], Weight);
                 XMCTree testBranch = (XMCTree)T.Branch.elementAt(v-1);
                 //Classify(CaseDesc, tempTree,Weight);
                 Classify(m_iRowNum, testBranch,Weight);
             }
             return;

	case BrSubset:  // subset test on discrete attribute
              
  	      
//  	      System.out.println("here is BrSubset +\n");
  	      m_iColumnNum = T.Tested;
	      //dv = DVal(CaseDesc, a); //DVal(Case,Attribute)   Case[Attribute]._discr_val
              //CAttValue_Att = (CAttValue)CaseDesc.elementAt(a);
              //dv  = CAttValue_Att.get_discr_val();
              dv=GetIntegerValue(m_iRowNum, m_iColumnNum);
	      if ( dv > 0 )
              {
                  for(v=1 ; v<=T.Forks ; ++v)
                  {                   
                  	
                  	
                  	//In(dv, T->Subset[v])
                  	
                  	//#define	 Bit(b)			(1 << (b))
                        //#define	 In(b,s)		(s[(b) >> 3]) & Bit((b) & 07)		
                  //if ( In(dv, T.Subset[v]) )   //( In(dv, T.Subset[v]) ) In(b,s)		((s[(b) >> 3]) & Bit((b) & 07))

                      //In(b,s)             Bit()
                      //System.out.println("dv  "+ dv+"T.Forks  "+T.Forks+"  " +AttValName[m_iColumnNum][dv]);
                      if (((T.Subset[v][dv >> 3])&(1 << (dv & 07)))!=0)//   Bit((dv) & 07) )
                         //(((T.Subset[v][Pv >> 3])&(1 << (Pv & 07)))!=0)
                      {
			                    //Classify(CaseDesc, T.Branch[v], Weight);
//                         System.out.println("v   "+v);
                         XMCTree tempBranch = (XMCTree)T.Branch.elementAt(v-1);
                         Classify(m_iRowNum, tempBranch,Weight);
                         return;
                      }
                  } //for(v=1 ; v<=T.Forks ; ++v)
              } //if ( dv > 0 )

	            //  Value unknown or not found in any of the subsets

	      for(v=1 ; v<=T.Forks ; ++v)
	      {
                      //Classify(CaseDesc, T->Branch[v],(Weight * T->Branch[v]->Items) / T.Items);
                      XMCTree testBranch = (XMCTree)T.Branch.elementAt(v-1);
                      //Classify(CaseDesc, tempTree,(Weight * tempBranch.Items)/ T.Items);
                      Classify(m_iRowNum, testBranch,(Weight * testBranch.Items)/ T.Items);
	      }
	      return;
    }//switch ( T.NodeType )
  }//End of public void Classify(Description CaseDesc,XMCTree T,double Weight)
public static byte[] addColAtRow(byte col[], byte row[], int[] colIndex, int j)
{
  //현재 row에다 col을 덧붙이고, colIndex[j]에 col의 length를 추가하는 루틴이다.

    int colLength = col.length;
    int rowLength = row.length;
    int newCapacity = colLength + rowLength;

    byte[] row_tmp = new byte[newCapacity];

    System.arraycopy(row, 0, row_tmp, 0, rowLength);
    System.arraycopy(col, 0, row_tmp, rowLength, colLength);

    colIndex[j] = colLength;

    return row_tmp;
}

void    PrintConfusionMatrix(int[] ConfusionMat,String m_sProjectName, String m_sConfusionFileName)
{
  try
  {
	int row, col;
	
    CXMTableSaver ConfusionData = new CXMTableSaver();   //litemset data file    
    ConfusionData.setFileStatus(m_sProjectName,m_sConfusionFileName);
    MakeConfusionMetaFile(m_sProjectName, m_sConfusionFileName);
	
	for(row=1;row<=MaxClass;row++)
	{
	   byte[] m_byteRow;
	   String temp=new String();
	   int[] colIndex = new int[MaxClass+1];
	   temp=GetClassValName(row);
	   m_byteRow=GetClassValName(row).getBytes();   
	   colIndex[0] = m_byteRow.length;
	   for(col=1;col<=MaxClass;col++)
	   {
	       byte[] m_byteCol;
		   String m_sCol=new String(Integer.toString(ConfusionMat[row*(MaxClass+1) + col]));
		   m_byteCol=m_sCol.getBytes();
		   temp=temp+" "+m_sCol;
           System.out.println("row="+row+"   Col="+col+"Value="+m_sCol);
		   m_byteRow=addColAtRow(m_byteCol, m_byteRow, colIndex, col);		   
	   }
	   //m_byteRow=addColAtRow(ClassName[Row].getBytes(), m_byteRow, colIndex, col);
	   ConfusionData.createRowData(m_byteRow, colIndex);
	   System.out.println("row="+row+"   temp="+temp);
	   System.out.println("row="+row+"   temp="+temp);

	}	
	ConfusionData.close();
  }
  catch (IOException ce)
  {
      System.out.println("파일 에러 입니다. ConfusionData을 파일에 쓸수 없습니다.");      
  }
}


public void MakeConfusionMetaFile(String m_ProjectName, String m_sConfusionFileName) throws IOException
  {
    int col, row;
	//GetClassValName(1);
	System.out.println("MaxClass[]="+MaxClass);
	CXMMetaDataSaver ConfusionMetaData = new CXMMetaDataSaver();
    ConfusionMetaData.setFileStatus(m_ProjectName,m_sConfusionFileName);
    ConfusionMetaData.setProfile("NUMBER_OF_ROWS", String.valueOf(MaxClass));
    ConfusionMetaData.setProfile("NUMBER_OF_COLUMNS", String.valueOf(MaxClass+1));
    String colNames = new String("CLASS NAME^");
	String colIndex = new String("1^");
	for(col=1;col<MaxClass;col++)
	{
	  colNames+=(GetClassValName(col)+"^");
	  System.out.println("ClassName["+col+"]="+GetClassValName(col));
	  colIndex+=(String.valueOf(col+1)+"^");
	}
	System.out.println("ClassName["+MaxClass+"]="+GetClassValName(MaxClass));
	colNames+=(GetClassValName(MaxClass));
    
	colIndex+=String.valueOf(MaxClass+1);
	
    ConfusionMetaData.setProfile("COLUMN_LIST", colNames);
    ConfusionMetaData.setProfile("DATA_FILE", m_sConfusionFileName);
    ConfusionMetaData.setProfile("ROW_INDEX", m_sConfusionFileName);
    ConfusionMetaData.setProfile("COLUMN_INDEX", colIndex);
    String property = "STRING^INFINITE^DISCRETE^CARDINAL^not_sorted^not_filtered^not_transformed^not_computed";
	
	for(col=1;col<=MaxClass;col++)
	{
	  ConfusionMetaData.setProfile(GetClassValName(col), property);
	}
	//ruleMetaData.setProfile("premise", property);
    //ruleMetaData.setProfile("consequent", property);
    property = "STRING^INFINITE^DISCRETE^NOMINAL^not_sorted^not_filtered^not_transformed^not_computed";
    ConfusionMetaData.setProfile("CLASS NAME", property);   
    ConfusionMetaData.close();
  }

////////////////////////////////////////////////////////////////////////////////
//
// End of Class
//  
// 
// 
// 
////////////////////////////////////////////////////////////////////////////////
}//End of Class