
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

public class XMCTreeGen extends XMCData
{

    public final static int    IGNORE1=1;       //특정한 칼럼을 사용하지 않을 경우
    public final static int    DISCRETE=2;      //칼럼의 Data Type이 숫자인지 문자인지 구별
    public final static int    CONTINUOUS=3;    //칼럼의 Data Type이 숫자인지 문자인지 구별
    public final static double Unknown= -999;   //data값을 모를 경우 -999로 할당
    public final static int    LEARN=101;       //data가 tree을 생성하기 위한 data인 경우
    public final static int    TEST=102;        //data가 tree을 테스트하기 위한 data인 경우
    public final static int    EXCUTION=103;    //실행을 위한 data인 경우

    public final static int    TREE_BEFORE=1000; //Tree를 생성하기 전
    public final static int    TREE_LEARN=1001;  //Tree가 생성되고, 파일에 저장된 상태
    public final static int    TREE_LEARN_WITH_PRUNE=1002;  ////Tree가 생성되고, pruning되어 파일에 저장된 상태
    public final static int    RULE_BEFORE=2000;
    public final static int    RULE_LEARN_MODE=2001;
    public final static int    RULE_LEARN_WITH_PRUNE=2002;

    public final static double Log2= 0.69314718055994530942;
    public final static double Epsilon= 0.0003;
    public final static double None	=		-1;
    public final static int   BrDiscr=	1;	/* node types:	branch */
    public final static int   ThreshContin=	2;	/*		threshold cut */
    public final static int   BrSubset=	3;
    public final static int   TabSize=	4;
    public final static int  	Width	=	80;
    public final static String	Tab	 =	"|";

    //TreeInput parameter들
    public int		   VERBOSITY=6,TRIALS=1,	TRACE4;
    public boolean		GAINRATIO=false,
		                  SUBSET=true,
      		            BATCH=true,
		                  UNSEENS=false,
      		            PROBTHRESH=false;
    public int		    MINOBJS=2,
		          WINDOW,
      		    INCREMENT;
    public double	  CF=0.25;
    public boolean	AllKnown;

    


    public  double[]	LowClassSum;
    //XMCTree   DecisionTree= new XMCTree();
    public  double    Confidence;
    boolean		FirstTime;//=true;
    public  boolean		SIGTEST	 ;
    CXMCRangeDescRec RangeDesc= new CXMCRangeDescRec();

	  //besttree.c
    double Weight;	// Weight[i]  = current fraction of item i
    int[]  TargetClassFreq;
    XMCTree Raw;

    //build.c
    double[][] Freq;		/* Freq[x][c] = no. items of class c with outcome x */
    double[]   ValFreq;	/* ValFreq[x]   = no. items with outcome x */
    double[]   ClassFreq;	/* ClassFreq[c] = no. items of class c */
    double[]   Gain;		/* Gain[a] = info gain by split on att a */
    double[]   Info;		/* Info[a] = potential info of split on att a */
    double[]   Bar;		/* Bar[a]  = best threshold for contin att a */
    double[]   UnknownRate;	/* UnknownRate[a] = current unknown rate for att a */
    boolean[]  Tested;	/* Tested[a] set if att a has already been tested */


    //classfy.c
    double[]	ClassSum;//=Nil;		/* ClassSum[c] = total weight of class c */

     //contin.c
    double	SplitGain;	/* SplitGain[i] = gain with att value of item i as threshold */
    double	SplitInfo;	/* SplitInfo[i] = potential info ditto */


    //st-thresh.c
    boolean[]   LHSErr;	//  Does a misclassification occur with this value of an att
    boolean[]   RHSErr;	//  if the below or above threshold branches are taken
    int[]       ThreshErrs;	//  ThreshErrs[i] is the no. of misclassifications if thresh is i
    double[]	CVals;		//  All values of a continuous attribute
    double[]	Slice1;	// Slice1[c]    = saved values of Freq[x][c] in subset.c
    double[] 	Slice2;	// Slice2[c]    = saved values of Freq[y][c]

    //subset.c
    char[][][]  Subset4;	//Subset[a][s] = subset s for att a
    int[]   	Subsets;	// Subsets[a] = no. subsets for att a

    boolean     exam_flag=false;

    int m_aIndex[];
    CXMWeightTableSaver  m_cRWTableCreator=null;
    CXMWeightTableManager m_cRWTableManager=null;

    XMCData m_cDataTable = null;
    String m_sWeightFileName=null;



////////////////////////////////////////////////////////////////////////////////
//
// 생성자
// public XMCTreeGen()
// public XMCTreeGen(String DataFileName, String MetaFileName, String TestDataFileName,String PrintFileName,String SaveFileName)
////////////////////////////////////////////////////////////////////////////////

  public XMCTreeGen()
  {

  }
 public XMCTreeGen(String ProjectName, String Modelname)
  {
        m_sProjectName = ProjectName;
        m_sModelName= Modelname;
  }

  public int ReadParameter_tree()
  {
    try
    {
      int i;
	  m_iPbCount_num++;
      CXMMetaDataReader Rcmdr= new CXMMetaDataReader();
      Rcmdr.setFileStatus(m_sProjectName,m_sModelName+"_par");
      //PARAMETER SETTING(TREE)
      MINOBJS= Integer.parseInt(Rcmdr.getProfile("MINOBS"));
      CF= Double.parseDouble(Rcmdr.getProfile("CF"));

	  //SUBSET =Boolean.getBoolean(Rcmdr.getProfile("SUBSET"));
	  if(Rcmdr.getProfile("SUBSET").equals("true"))SUBSET=true;
	  else SUBSET=false;

      //GAINRATIO =Boolean.getBoolean(Rcmdr.getProfile("GAINRATIO"));
	  if(Rcmdr.getProfile("GAINRATIO").equals("true"))GAINRATIO=true;
	  else GAINRATIO=false;
      Rcmdr.close();
//      System.out.println(" MINOBJS=   "+ MINOBJS+"   CF="+CF+"    SUBSET= "+ SUBSET+ "   GAINRATIO"+GAINRATIO);
      return(0);
    }
      catch (Exception e) {
    	e.printStackTrace();
    	return(1);
    }
  }
////////////////////////////////////////////////////////////////////////////////
// public double GetWeight(int m_iRowNumber)
////////////////////////////////////////////////////////////////////////////////
public double GetWeight(int m_iRowNumber)
{
   //return m_cRWTableManager.getDouble(m_iRowNumber+1,2);
   return m_cRWTableManager.getDouble(m_aIndex[m_iRowNumber],2);
}

////////////////////////////////////////////////////////////////////////////////
// public double GetSplitGain(int m_iRowNumber)
////////////////////////////////////////////////////////////////////////////////
public double GetSplitGain(int m_iRowNumber)
{
   //return m_cRWTableManager.getDouble(m_iRowNumber+1,3);
   return m_cRWTableManager.getDouble(m_aIndex[m_iRowNumber],3);
}
////////////////////////////////////////////////////////////////////////////////
// public double GetSplitGain(int m_iRowNumber)
////////////////////////////////////////////////////////////////////////////////
public double GetSplitInfo(int m_iRowNumber)
{
   return m_cRWTableManager.getDouble(m_aIndex[m_iRowNumber],4);
}
////////////////////////////////////////////////////////////////////////////////
// public void SetIndex(int m_iRowNumber,int m_iIndex)
////////////////////////////////////////////////////////////////////////////////
public void SetIndex(int m_iRowNumber,int m_iIndex)
{
    m_aIndex[m_iRowNumber]=m_iIndex;
	//m_cRWTableManager.setInteger(m_iRowNumber+1,1,m_iIndex);
}
////////////////////////////////////////////////////////////////////////////////
// public void SetWeight(int m_iRowNumber,double m_dWeight)
////////////////////////////////////////////////////////////////////////////////
public void SetWeight(int m_iRowNumber,double m_dWeight)
{
    //m_cRWTableManager.setDouble(m_iRowNumber+1,2,m_dWeight);
	m_cRWTableManager.setDouble(m_aIndex[m_iRowNumber],2,m_dWeight);
}

////////////////////////////////////////////////////////////////////////////////
// public void SetSplitGain(int m_iRowNumber,double m_dSplitGain)
////////////////////////////////////////////////////////////////////////////////
public void SetSplitGain(int m_iRowNumber,double m_dSplitGain)
{
   //m_cRWTableManager.setDouble(m_iRowNumber+1,3,m_dSplitGain);
   m_cRWTableManager.setDouble(m_aIndex[m_iRowNumber],3,m_dSplitGain);
}

////////////////////////////////////////////////////////////////////////////////
// public void SetSplitInfo(int m_iRowNumber,double m_dSplitInfo)
////////////////////////////////////////////////////////////////////////////////
public void SetSplitInfo(int m_iRowNumber,double m_dSplitInfo)
{
   //m_cRWTableManager.setDouble(m_iRowNumber+1,4,m_dSplitInfo);
   m_cRWTableManager.setDouble(m_aIndex[m_iRowNumber],4,m_dSplitInfo);
}

////////////////////////////////////////////////////////////////////////////////
//
// public int GetIntegerValue(int m_iRow, int m_Column)
//
// Data의 수를 세는 함수
//
//
////////////////////////////////////////////////////////////////////////////////
public  int GetIntegerValue(int m_iRow, int m_iColumn)
{
    int m_iTableRow=0, m_iReturn=0;
    if(!m_bTestFlag)
    {
       //m_iTableRow=m_cRWTableManager.getInteger(m_iRow+1,1);//Data Table의 row를 찾음
       //m_iReturn = m_cC45DataTableManager.getInteger(m_iTableRow,m_iColumn+1);
	   m_iReturn = m_cC45DataTableManager.getInteger(m_aIndex[m_iRow],m_iColumn+1);
    }
    else
    {
      m_iReturn = m_cC45DataTableManager.getInteger(m_iRow+1,m_iColumn+1);
    }

    return m_iReturn;
}
////////////////////////////////////////////////////////////////////////////////
//
// public int GetClassValue(int m_iRow)
//
// Data의 수를 세는 함수
//
//
////////////////////////////////////////////////////////////////////////////////
public  int GetClassValue(int m_iRow)
{
    int m_iTableRow=0, m_iReturn=0;
    if(!m_bTestFlag)
    {
        //m_iTableRow=m_cRWTableManager.getInteger(m_iRow+1,1);//Data Table의 row를 찾음
        //m_iReturn = m_cC45DataTableManager.getInteger(m_iTableRow,m_iClassColumn);
		m_iReturn = m_cC45DataTableManager.getInteger(m_aIndex[m_iRow],m_iClassColumn);
    }
    else
    {
      m_iReturn = m_cC45DataTableManager.getInteger(m_iRow+1,m_iClassColumn);
    }

    return m_iReturn;
}
////////////////////////////////////////////////////////////////////////////////
//
// public  void SetEstimateValue(int m_iRow,int m_iEstimateValue)
//
// Data의 수를 세는 함수
//
//
////////////////////////////////////////////////////////////////////////////////
public  void SetEstimateValue(int m_iRow,int m_iEstimateValue)
{
    int m_iTableRow=0, m_iReturn=0;

    if(!m_bTestFlag)
    {
       //m_iTableRow=m_cRWTableManager.getInteger(m_iRow+1,1);//Data Table의 row를 찾음
       //m_cC45DataTableManager.setInteger(m_iTableRow,m_iClassColumn+1,m_iEstimateValue);
	   m_cC45DataTableManager.setInteger(m_aIndex[m_iRow],m_iClassColumn+1,m_iEstimateValue);
    }
    else
    {
        m_cC45DataTableManager.setInteger(m_iRow+1,m_iClassColumn+1,m_iEstimateValue);
    }

}
////////////////////////////////////////////////////////////////////////////////
//
// public int GetDoubleValue(int m_iRow, int m_Column)
//
// Data의 수를 세는 함수
//
//
////////////////////////////////////////////////////////////////////////////////
public  double GetDoubleValue(int m_iRow, int m_iColumn)
{
    int m_iTableRow=0;
    double m_dReturn=0;
    if(!m_bTestFlag)
    {
       //m_iTableRow=m_cRWTableManager.getInteger(m_iRow+1,1);//Data Table의 row를 찾음
       //m_dReturn = m_cC45DataTableManager.getDouble(m_iTableRow,m_iColumn+1);
	   m_dReturn = m_cC45DataTableManager.getDouble(m_aIndex[m_iRow],m_iColumn+1);
    }
    else
    {
      m_dReturn = m_cC45DataTableManager.getDouble(m_iRow+1,m_iColumn+1);
    }

    return m_dReturn;
}



////////////////////////////////////////////////////////////////////////////////
//
// public void  InitialiseTreeData()
//
// 메모리 할당
//
//
////////////////////////////////////////////////////////////////////////////////

public void  InitialiseTreeData()

{
    int v;
    int a;

    Tested	= new boolean[MaxAtt+1];
    for(int x=0; x< MaxAtt+1;x++)
    {
            Tested[x]=false;
    }

    Gain	= new double[MaxAtt+1];
    Info	= new double[MaxAtt+1];
    Bar		= new double[MaxAtt+1];
    Subsets = new int[MaxAtt+1];

    MaxDiscrVal=2;
    for(int i=0;i<=MaxAtt;i++)
    {
      if(MaxDiscrVal<MaxAttVal[i]) MaxDiscrVal= MaxAttVal[i];
    }


    Subset4= new char[MaxAtt+1][][];
    for( a=0;a<=MaxAtt;a++)
    {
     	if ( MaxAttVal[a]>0 )
      {
      		Subset4[a]  =  new char[MaxDiscrVal+1][];//calloc(MaxDiscrVal+1, sizeof(Set));
		for(v=0 ;v<= MaxAttVal[a];v++)//ForEach(v, 0, MaxAttVal[a])
          {
		Subset4[a][v] = new char[(MaxAttVal[a]>>3) + 1];//(Set) malloc((MaxAttVal[a]>>3) + 1);
          }
      }
    }




    //Subset4 = (Set **) calloc(MaxAtt+1, sizeof(Set *));
    // Weight를 저장하는 부분
    // 파일 처리 필요
    //SplitGain = new double[(int)MaxItem+1];
    //SplitInfo = new double[(int)MaxItem+1];
    //Weight = new double[(int)MaxItem+1];




    Freq  = new double[MaxDiscrVal+1][];
    for (v=0;v<= MaxDiscrVal;v++)
    {
    		Freq[v]  = new double[MaxClass+1];
    }

    ValFreq =  new double[MaxDiscrVal+1];
    ClassFreq = new double[MaxClass+1];

    Slice1 = new double[MaxClass+2];
    Slice2 = new double[MaxClass+2];

    UnknownRate = new double[MaxAtt+1];
}


////////////////////////////////////////////////////////////////////////////////
//
// public void  InitialiseWeights()
// 
// SplitGain,SplitInfo,Weight에 값을 할당
// 
// 
////////////////////////////////////////////////////////////////////////////////
public void  InitialiseWeights()
{
    int i, m_iRowIndex=0;
    double m_dWeight=1.0;
    double m_dSplitGain=0.0;
    double m_dSplitInfo=0.0;
    int[] m_iColIndex = new int[4];  //5개의 column
    m_iColIndex[0]=4;
    m_iColIndex[1]=8;
    m_iColIndex[2]=8;
    m_iColIndex[3]=8;
    m_iPbCount_num++;
    m_sWeightFileName=new String(m_sModelName+"Weight");
    m_cRWTableCreator = new CXMWeightTableSaver();
    m_cRWTableCreator.setFileStatus(m_sProjectName,m_sWeightFileName);
    m_cRWTableCreator.createWeightTable(m_iColIndex, MaxItem+1);
    m_cRWTableCreator.close();
    
    m_cRWTableManager = new CXMWeightTableManager();
    //m_cRWTableManager.setFileStatus(m_sProjectName,m_sWeightFileName);
    m_cRWTableManager.setFileStatus(m_sProjectName, m_sWeightFileName, m_iColIndex, MaxItem+1);
    m_aIndex= new int[MaxItem+2];
    for(i=0; i<=MaxItem;i++)
    {
    	m_iRowIndex=i+1;
        SetIndex(i,m_iRowIndex);
        SetWeight(i,m_dWeight);
        SetSplitGain(i,m_dSplitGain);
        SetSplitInfo(i,m_dSplitInfo);
    }

}

////////////////////////////////////////////////////////////////////////////////
//
// public XMCTree FormTree (int Fp, int Lp)  throws IOException
// 
// Tree를 생성하는 함수
// 
//
////////////////////////////////////////////////////////////////////////////////
public XMCTree FormTree (int Fp, int Lp)  throws IOException
{
    int i, Kp, Ep;
    double Cases, NoBestClass, KnownCases;
    double Factor, BestVal, Val, AvGain=0;
    int Att, BestAtt, Possible=0;
    int c, BestClass;
    XMCTree Node;
    int v;
    boolean PrevAllKnown;

    m_iPbCount_num++;
    Cases = CountItems(Fp,Lp);
//    System.out.println("here is FormTree  : Fp  " +Fp+"  Lp "+Lp);

    m_iPbCount_num++;

    //  Generate the class frequency distribution
    //System.out.println("here is FormTree  : maxClass  " +MaxClass+"  MaxItem "+MaxItem);

    for(c=0 ; c<=MaxClass ; ++c) //#define	 ForEach(v,f,l)		for(v=f ; v<=l ; ++v)
    {
	    ClassFreq[c] = 0;
    }
    m_iPbCount_num++;
    /*for(i=Fp; i<=Lp;++i)
    {
       for(c=0;c<=MaxAtt;c++)
       {
            if(SpecialStatus[c] == DISCRETE)//------------------------------------>>Read!
            {
                System.out.println("("+i+","+c+")="+GetIntegerValue(i,c));
            }
            else
            {
                System.out.println("("+i+","+c+")="+GetDoubleValue(i,c));
            }
       }
       System.out.println("("+i+",class)="+this.GetClassValue(i));
    }*/
    m_iPbCount_num++;
    int m_iClass=0;
    for(i=Fp ; i<=Lp ; ++i)
    {
      m_iClass =GetClassValue(i);
      ClassFreq[m_iClass] += GetWeight(i);
    }

    m_iPbCount_num++;
    //  Find the most frequent class

    BestClass = 0;
    for(c=0 ; c<=MaxClass ; ++c)
    {
	    if ( ClassFreq[c] > ClassFreq[BestClass] )
	    {
	      BestClass = c;
	    }
    }
	m_iPbCount_num++;
    NoBestClass = ClassFreq[BestClass];
    //System.out.println("BestClass  " +BestClass +"   NoBestClass  "+NoBestClass   );
    Node = new XMCTree(ClassFreq, BestClass, Cases, Cases - NoBestClass,MaxClass, (XMCData)this);

    /*  If all cases are of the same class or there are not enough
	cases to divide, the tree is a leaf  */

    if ( NoBestClass == Cases  || Cases < 2 * MINOBJS )
    {
        double aa=  Cases - NoBestClass;
//	      System.out.println("return BestClass :"+BestClass+ "NodeType = 0"+"error=Cases-NoBestClass = "+Node.Errors);
	      return Node;
    }
    m_iPbCount_num++;
    if(VERBOSITY >= 1)   //#define	 Verbosity(d)		if(VERBOSITY >= d)
       System.out.println("\n"+(Lp-Fp+1)+" items"+" total weight "+Cases );

    //  For each available attribute, find the information and gain
    
    for(Att=0 ; Att<=MaxAtt ; ++Att)  //#define	 ForEach(v,f,l)		for(v=f ; v<=l ; ++v)
    {
	   Gain[Att] = (double)-Epsilon;
       m_iPbCount_num++;
              //if ( SpecialStatus[Att] == IGNORE1 ) continue;

           if ( MaxAttVal[Att] >0)      //  discrete valued attribute
           {
               if ( SUBSET && MaxAttVal[Att] > 2 )
               {
//                    System.out.println("SUBSET ");
		                EvalSubset(Att, Fp, Lp, Cases);
                }
                else if ( !Tested[Att] )
                {
                    EvalDiscreteAtt(Att, Fp, Lp, Cases);
	              }
           }
           else                       //   continuous attribute
	         {
                   EvalContinuousAtt(Att, Fp, Lp);
           }

           //  Update average gain, excluding attributes with very many values
           if ( Gain[Att] >= 0 && ( SUBSET || MaxAttVal[Att] < 0.3 * MaxItem ) )
           {
	            Possible++;
	            AvGain += Gain[Att];
           }

    } //for(Att=0 ; Att<=MaxAtt ; ++Att)  //#define	 ForEach(v,f,l)		for(v=f ; v<=l ; ++v)
    m_iPbCount_num++;
    //  Find the best attribute according to the given criterion

    BestVal = (double)-Epsilon;
    BestAtt = (int)None;
    AvGain  = (double)( Possible>0 ? AvGain / Possible : 1E6 );
    ////////////////////////
    if(VERBOSITY >= 2) System.out.println("\taverage gain "+AvGain);
    for(Att=0 ; Att<=MaxAtt; ++Att) //#define	 ForEach(v,f,l)		for(v=f ; v<=l ; ++v)
    {
//      System.out.println("FormTree    "+Att);
	    if ( Gain[Att] >= 0 )
	    {
	      Val = Worth(Info[Att], Gain[Att], AvGain);
	      if ( Val > BestVal )
	      {
	        BestAtt  = Att;
	        BestVal = Val;
	      }
	    }
    }
	m_iPbCount_num++;
//    System.out.println(" BestAtt  :   " +BestAtt);
    //  Decide whether to branch or not
    
    if ( BestAtt != None )
    {
//         System.out.println(" BestAtt  :   " +BestAtt+"   attname  :   " +AttName[BestAtt]);
		 if ( MaxAttVal[BestAtt] >0)
         {
            if ( SUBSET && MaxAttVal[BestAtt] > 2 )
	    {
                 SubsetTest(Node, BestAtt);
            }
            else
            {
                 DiscreteTest(Node, BestAtt);
            }
          }//if ( MaxAttVal[BestAtt] >0)
          else
	          ContinTest(Node, BestAtt);

	  PrevAllKnown = AllKnown;

	  Kp = Group((int)0, Fp, Lp, Node) + 1;

	  if ( Kp != Fp ) AllKnown = false;

//          System.out.println("Fp="+Fp+" Kp-1="+Kp );
          if(Kp>0)  KnownCases = Cases - CountItems(Fp, Kp-1);
          else    KnownCases = Cases;

          UnknownRate[BestAtt] = (double)((Cases - KnownCases) / (Cases + 0.001));

          Tested[BestAtt]=true;

//          System.out.println("BestAtt :"+AttName[BestAtt]+"  NodeType : "+Node.NodeType+"   Node.Forks"+Node.Forks);
          Ep = Kp - 1;
          Node.Errors = 0;

          String m_sAttValName=null;
          double m_dWeight=0.0;
          for(v=1 ; v<=Node.Forks ; ++v)   //#define	 ForEach(v,f,l)		for(v=f ; v<=l ; ++v)
          {
             Ep = Group(v, Kp, Lp, Node);

             if ( Kp <= Ep )
             {
			    //System.out.println("BestAtt :"+AttName[BestAtt]);
				// m_sAttValName=GetAttValName(BestAtt,v);
                //System.out.println("BestAtt :"+AttName[BestAtt]+"  v  :"+m_sAttValName);
                Factor = CountItems(Kp, Ep) / KnownCases;
                for(i=Fp ; i<=Kp-1 ; ++i)
                {
	                 m_dWeight=GetWeight(i);
	                 m_dWeight *= Factor;
	                 SetWeight(i,m_dWeight);
	              }
                XMCTree tempTree= new XMCTree();
                tempTree= FormTree(Fp, Ep);
//                System.out.println("tempTree.error= :"+tempTree.Errors);
                Node.Errors = Node.Errors + tempTree.Errors;
                Node.Branch.add(tempTree);

                //XMCTree Temp_Branch =  (XMCTree)Node.Branch.elementAt(v-1);
                Group((int)0, Fp, Ep, Node);
                for(i=Fp ; i<=Kp-1 ; ++i)
                {
		         //Weight[i] /= Factor;
		         m_dWeight=GetWeight(i);
	                 m_dWeight /= Factor;
	                 SetWeight(i,m_dWeight);
	        }
	      } //if ( Kp <= Ep )
	      else
	      {
                 XMCTree Temp_Branch1 = new XMCTree(Node.ClassDist, BestClass, 0.0, 0.0,MaxClass,(XMCData)this);
                 //System.out.println("BestAtt :"+AttName[BestAtt]+"  v  :"+AttValName[BestAtt][v]+"  NodeType=0");
                 Node.Branch.add(Temp_Branch1);
              } //if ( Kp <= Ep ).else
          } //end of for(v=1 ; v<=Node.Forks ; ++v)

	  Tested[BestAtt]=false;
          AllKnown = PrevAllKnown;

	  // See whether we would have been no worse off with a leaf
//          System.out.println(" Node.Errors "+Node.Errors+"    Cases"+Cases+"   NoBestClass"+NoBestClass);
       	  if ( Node.Errors >= Cases - NoBestClass - Epsilon )
	  {
              if(VERBOSITY >= 1)
               //System.out.println("Collapse tree for "+(Lp - Fp + 1)+" items to leaf "+ClassName[BestClass]);

              Node.NodeType = 0;

          }//if ( Node.Errors >= Cases - NoBestClass - Epsilon )
    }//if ( BestAtt != None )
    else
    {
      if(VERBOSITY >= 1)
        System.out.println("\tno sensible splits  "+Cases+(Cases - NoBestClass) );
    }
    m_iPbCount_num++;
    return Node;
  }

////////////////////////////////////////////////////////////////////////////////
//
// public double CountItems (int Fp, int Lp)
//
// Data의 수를 세는 함수
// 
// 
////////////////////////////////////////////////////////////////////////////////
public  double  CountItems(int Fp,int Lp)
{
    double Sum=0.0, Wt, LWt;

    if ( AllKnown ) return (double)(Lp - Fp + 1);


    for (int i=Fp; i<=Lp;i++)
          Sum=Sum+GetWeight(i); 
    
    return Sum;
}



////////////////////////////////////////////////////////////////////////////////
//
// public void EvalSubset(int Att, int Fp,int Lp,double  Items)
// 
// SubSet
// 
//
////////////////////////////////////////////////////////////////////////////////
public void  EvalSubset(int Att, int Fp,int Lp,double  Items) throws IOException
{
    int V1, V2, BestV1, BestV2=0, Barred;
    double KnownItems;
    int c;
    double BaseInfo, MinGain, ThisGain, ThisInfo,
	         Val, BestVal, BestGain, BestInfo=0.0,
	         PrevVal, PrevGain, PrevInfo;

    int Blocks=0, MissingValues=0, ReasonableSubsets, Bytes, b;
    boolean MergedSubsets = false;
    int   SaveMINOBJS,j;

    SaveMINOBJS = MINOBJS;
    MINOBJS = 1;
    m_iPbCount_num++;
    //  First compute Freq[][], ValFreq[], base info, and the gain
	  //  and total info of a split on discrete attribute Att

    ComputeFrequencies(Att, Fp, Lp, Items);

    KnownItems = Items - ValFreq[0];

    BaseInfo = DiscrKnownBaseInfo(KnownItems, MaxAttVal[Att]);
    
    PrevGain = ComputeGain(BaseInfo, UnknownRate[Att], MaxAttVal[Att],(int) KnownItems);
    PrevInfo = TotalInfo(ValFreq, (int)0, MaxAttVal[Att]) / Items;
    PrevVal = Worth(PrevInfo, PrevGain, Epsilon);

    //  Eliminate unrepresented attribute values from Freq[] and ValFreq[]
    //  and form a separate subset for each represented attribute value

    Bytes = (int)((MaxAttVal[Att]>>3) + 1);

    for(j=0;j<Bytes;j++)
    {
       Subset4[Att][0][j]= 0;
    }

    for(V1=1; V1<=MaxAttVal[Att];V1++) // ForEach(V1, 1, MaxAttVal[Att])
    {
      	if ( ValFreq[V1] > 0.5 )
        {
            if ( ++Blocks < V1 )
            {
            	ValFreq[Blocks] = ValFreq[V1];
		for(c=0;c<=MaxClass;c++) //ForEach(c, 0, MaxClass)
		        Freq[Blocks][c] = Freq[V1][c];
             }
	          //ClearBits(Bytes, Subset4[Att][Blocks]);
            for(j=0;j<Bytes;j++)
            {
               Subset4[Att][Blocks][j]=0;
            }
	    //SetBit(V1, Subset4[Att][Blocks]);
            //SetBit(i, PossibleValues[a]);//#define SetBit(b,s)	(s[(b) >> 3] |= Bit((b) & 07))
             Subset4[Att][Blocks][(V1) >> 3] |=  (1 << ((V1) & 07)); //


	 }//if ( ValFreq[V1] > 0.5 )
	 else
	 {
	                       //SetBit(V1, Subset4[Att][0]);
            Subset4[Att][0][(V1) >> 3] |=  (1 << ((V1) & 07));
	    MissingValues++;
	 }//if ( ValFreq[V1] > 0.5 ).else
     }//for(V1=1; V1=MaxAttVal[Att];V1++)

    //  Merge any single-class subsets with others of the same class
    //  Note: have ValFreq[V] > 0 for all V

   for(V1=1;V1<=Blocks-1;V1++)  // ForEach(V1, 1, Blocks-1)
   {
      for ( c = 0 ; Freq[V1][c] < 0.1 ; c++ );
      if ( Freq[V1][c] < ValFreq[V1] - 0.1 ) continue;

      //  Now have a single class -- look for others

      for ( V2 = (int)(V1+1) ; V2 <= Blocks ; )
      {
     	    if ( Freq[V2][c] < ValFreq[V2] - 0.1 )
            {
                V2++;
            }//if ( Freq[V2][c] < ValFreq[V2] - 0.1 )
            else
            {
                  //  Merge these subsets
                  Combine(V1, V2, Blocks);
                  for(b=0;b<=Bytes-1;b++)//ForEach(b, 0, Bytes-1)
                  {
                         Subset4[Att][V1][b] |= Subset4[Att][V2][b];
                         Subset4[Att][V2][b] = Subset4[Att][Blocks][b];
                  }
                  Blocks--;
	          MergedSubsets = true;
	     }//if ( Freq[V2][c] < ValFreq[V2] - 0.1 ).else
      }//for ( V2 = V1+1 ; V2 <= Blocks ; )
    }//for(V1=1;V1<=Blocks-1;V1++)

    if ( MergedSubsets )
    {
//        System.out.println("SUBSET_MergedSubsets");
        PrevGain = ComputeGain(BaseInfo, UnknownRate[Att], Blocks, (int)KnownItems);
        PrevInfo = TotalInfo(ValFreq, (int)0, Blocks) / Items;
        PrevVal = Worth(PrevInfo, PrevGain, Epsilon);
    }//if ( MergedSubsets )

    //  Examine possible pair mergers and hill-climb

    MinGain = PrevGain / 2;

    while ( Blocks > 2 )
    {
         BestVal = BestV1 = 0;
         BestGain = -Epsilon;
         //Check reasonable subsets; if less than 3, bar mergers
         //involving the largest block

         ReasonableSubsets = 0;
         Barred = 1;

         for(V1=1;V1<=Blocks;V1++)//ForEach(V1, 1, Blocks)
         {
                if ( ValFreq[V1] >= SaveMINOBJS ) ReasonableSubsets++;
                if ( ValFreq[V1] > ValFreq[Barred] ) Barred = V1;
         }

         if ( ReasonableSubsets >= 3 ) Barred = 0;
         //  For each possible pair of values, calculate the gain and
         //  total info of a split in which they are treated as one.
         //  Keep track of the pair with the best gain.

         for(V1=1;V1<=Blocks-1;V1++)//ForEach(V1, 1, Blocks-1)
         {
             for(V2=(int)(V1+(int)1);V2<=Blocks;V2++)//ForEach(V2, V1+1, Blocks)
             {
               	  if ( V1 == Barred || V2 == Barred ) continue;
                  Combine(V1, V2, Blocks);
//                  System.out.println("SUBSET_v1="+V1+"  V2="+V2);
                  ThisGain = ComputeGain(BaseInfo, UnknownRate[Att],(int)(Blocks-1),(int) KnownItems);
              	  ThisInfo = TotalInfo(ValFreq, (int)0, (int)(Blocks-1)) / Items;
                  Val      = Worth(ThisInfo, ThisGain, Epsilon);

                  //  Force a split if
		  //  less than two reasonable subsets, or
		  //  using GAIN criterion
		  //  Prefer this split to the previous one if
		  //  gain >= MinGain (and previous < MinGain), or
		  //  val >= previous best val
                  if ( ThisGain >= MinGain && BestGain < MinGain ||  Val >= BestVal ||
		                   ! (BestV1!=0) && ( ! GAINRATIO || ReasonableSubsets < 2 ) )
                  {
                         BestVal  = Val;
                         BestGain = ThisGain;
                         BestInfo = ThisInfo;
                         BestV1   = V1;
                         BestV2   = V2;
                  }

		  Uncombine(V1, V2);
	       }//for(V2=V1+1;V2=Blocks;V2++)
	     }//for(V1=1;V1<Blocks-1;V1++)

	    if ( GAINRATIO && ReasonableSubsets >= 2 &&
                 ( ! (BestV1!=0) ||  BestVal < PrevVal + 1E-5 ||
                 BestVal == PrevVal && BestGain < PrevGain ) ) break;

    	  PrevGain = BestGain;
          PrevInfo = BestInfo;
          PrevVal = BestVal;

          Combine(BestV1, BestV2, Blocks);
          for(b=0;b<=Bytes-1;b++)//ForEach(b, 0, Bytes-1)
          {
	        Subset4[Att][BestV1][b] |= Subset4[Att][BestV2][b];
	        Subset4[Att][BestV2][b] = Subset4[Att][Blocks][b];
	  }

	  Blocks--;	  
    }// while ( Blocks > 2 )

    MINOBJS = SaveMINOBJS;

    if ( PrevVal <= 0 )
    {
      	Gain[Att] = -Epsilon;
        Info[Att] = 0;
    }
    else
    {
      	Gain[Att] = ComputeGain(BaseInfo, UnknownRate[Att], Blocks, (int)KnownItems);
        Info[Att] = PrevInfo;
        if ( MissingValues>0 )
        {
            Blocks++;
            //CopyBits(Bytes, Subset4[Att][0], Subset4[Att][Blocks]);

            for(j=0;j<Bytes;j++)
            {
                Subset4[Att][Blocks][j]=Subset4[Att][0][j];
            }
         }

	 Subsets[Att] = Blocks;
         
    }
}//End of EvalSubset


////////////////////////////////////////////////////////////////////////////////
//
// public void ComputeFrequencies(int Att,int  Fp,int Lp,double Items)
// 
// 각 클래스 별로 frequency를 구하는 함수
// 
// 
////////////////////////////////////////////////////////////////////////////////
public  void  ComputeFrequencies(int Att,int  Fp,int Lp,double Items)
{

    int c;
    int v;

    int p;
    //System.out.println(":::::ComputeFrequencies:::::::\n Att : "
    //                   +Att +",AttName = "+ AttName[Att]+", MaxAttVal[Att]=  "+ MaxAttVal[Att] );
    ResetFreq(MaxAttVal[Att]);

    //  Determine the frequency of each class amongst cases
    //  with each possible value for the given attribute
    int x,y,kkkk=0;

     
    //System.out.println("Items<=READLINENUM"  );
    for(p=Fp;p<= Lp;p++) //Case = Item[p];
    {
         x=GetIntegerValue(p,Att);
         y=GetClassValue(p);
         Freq[x][y] += GetWeight(p);
    } //for(p=Fp;p<= Lp;p++)
    

    //  Determine the frequency of each possible value for the
	  //  given attribute

    for(v=0;v<= MaxAttVal[Att];v++)
    {
	    for(c=0;c<= MaxClass;c++)
	    {
	        ValFreq[v] += Freq[v][c];
          //System.out.println("Freq["+v+"]["+c+"] : "+Freq[v][c] +"  "+kkkk);
	    }
      //System.out.println("ValFreq["+v+"] : "+ValFreq[v] );
    }

    //  Set the rate of unknown values of the attribute  */

    UnknownRate[Att] = ValFreq[0] / CountItems(Fp, Lp);
}

////////////////////////////////////////////////////////////////////////////////
//
// public double DiscrKnownBaseInfo(double KnownItems, int MaxVal)
// 
// 각 클래스 별로 frequency를 구하는 함수
//
// 
////////////////////////////////////////////////////////////////////////////////
public double DiscrKnownBaseInfo(double KnownItems, int MaxVal)
  {
    int c;
    double ClassCount;
    double Sum=0,m_dTemp;
    int v;
   
    for(c=0; c<=MaxClass;c++)
    {
       ClassCount = 0;
       for(v=1;v<=MaxVal;v++)
       {
	        ClassCount += Freq[v][c];
       }
      // System.out.println("class "+c+"  ClassCount =" +ClassCount);
       
       m_dTemp=java.lang.Math.log(ClassCount)/java.lang.Math.log(java.lang.Math.E);
       Sum += ClassCount * ((ClassCount) <= 0 ? 0.0 : m_dTemp / Log2);
       //System.out.println("class "+c+"  ClassCount =" +ClassCount +"  sum ="+ Sum);
    }
    m_dTemp=java.lang.Math.log(KnownItems)/java.lang.Math.log(java.lang.Math.E);
    return (KnownItems * ((KnownItems) <= 0 ? 0.0 : m_dTemp / Log2) - Sum) / KnownItems;
}

////////////////////////////////////////////////////////////////////////////////
//
// public double ComputeGain(double BaseInfo,double UnknFrac,int MaxVal,int TotalItems)
// 
// Att의 Gain을 구함
//
// 
////////////////////////////////////////////////////////////////////////////////
public double ComputeGain(double BaseInfo,double UnknFrac,int MaxVal,int TotalItems)
  {
    int v;
    double ThisInfo=0.0, ThisGain;
    int ReasonableSubsets=0;

    //  Check whether all values are unknown or the same  */
    //System.out.println(":::::::::ComputeGain:::::::::\n ");
    if (  TotalItems<=0 ) return -Epsilon;

    //  There must be at least two subsets with MINOBJS items  */

    for(v=1; v<=MaxVal;v++)
    {
      //System.out.println ("ValFreq[v]"+v+"  "+ValFreq[v]);
     	if ( ValFreq[v] >= MINOBJS ) ReasonableSubsets++;
    }
    //System.out.println("ReasonableSubsets="+ReasonableSubsets);
    if ( ReasonableSubsets < 2 ) return -Epsilon;

    //  Compute total info after split, by summing the
	  //    info of each of the subsets formed by the test

    for(v=1; v<=MaxVal;v++)
    {
      	ThisInfo += TotalInfo(Freq[v], (int) 0, MaxClass);
        //System.out.println("ThisInfo["+v+"]="+ThisInfo);
    }

    /*  Set the gain in information for all items, adjusted for unknowns  */

    ThisGain = (1 - UnknFrac) * (BaseInfo - ThisInfo / TotalItems);

    /*Verbosity(5)
        fprintf(fprin,"ComputeThisGain: items %.1f info %.3f base %.3f unkn %.3f result %.3f\n",
    		TotalItems + ValFreq[0], ThisInfo, BaseInfo, UnknFrac, ThisGain);
      */
    if(VERBOSITY >= 5)
    {System.out.println("ComputeThisGain: items "+(TotalItems + ValFreq[0])+" info "+ThisInfo+" base "+BaseInfo+" unkn "+UnknFrac+"result"+ThisGain+"\n"); }

    return ThisGain;
}

////////////////////////////////////////////////////////////////////////////////
//
// public public double TotalInfo(double V[], int MinVal,int MaxVal)
// 
// Att의 Gain을 구함
// 
// 
////////////////////////////////////////////////////////////////////////////////
public double TotalInfo(double V[], int MinVal,int MaxVal)
{
    int v;
    double Sum=0.0,result=0.0,m_dTemp;
    int N, TotalItems=0;
    //System.out.println(":::::::::TotalInfo:::::::::\n ");
    for(v=MinVal; v<=MaxVal;v++)
    {
       N = (int)V[v];
       m_dTemp=java.lang.Math.log(N)/java.lang.Math.log(java.lang.Math.E);
       Sum += N * ((N) <= 0 ? 0.0 : java.lang.Math.log(N) / Log2);
       TotalItems += N;
       //System.out.println("N = "+N+"  Sum = "+Sum+"  TotalItems = "+TotalItems);
    }
    
    m_dTemp=java.lang.Math.log(TotalItems)/java.lang.Math.log(java.lang.Math.E);
    result = TotalItems * ((TotalItems) <= 0 ? 0.0 : m_dTemp / Log2) - Sum;
    //System.out.println("result ="+result);
    return result;
}

////////////////////////////////////////////////////////////////////////////////
//
// public double  Worth(double ThisInfo,double ThisGain,double MinGain)
// 
// Att의 Gain을 구함
// 
// 
////////////////////////////////////////////////////////////////////////////////
public double  Worth(double ThisInfo,double ThisGain,double MinGain)
 {
  if ( GAINRATIO )
  {
	 if ( ThisGain >= MinGain - Epsilon && ThisInfo > Epsilon )
	 {
	    return ThisGain / ThisInfo;
	 }
	 else
	 {
	    return -Epsilon;
	 }
  }
  else
  {
	  return ThisInfo > 0 && ThisGain > 0 ? ThisGain : - Epsilon;
  }
 }//End of double public Worth
////////////////////////////////////////////////////////////////////////////////
//
// public  void Combine(int x,int y,int Last)
// 
//  Combine the distribution figures of discrete attribute values
//  x and y, putting the combined figures in Freq[x][] and
//  ValFreq[x][], and saving old values in Slice1 and Slice2
////////////////////////////////////////////////////////////////////////////////
public  void Combine(int x,int y,int Last)
{
    int c;

    for(c=0;c<=MaxClass;c++)//ForEach(c, 0, MaxClass)
    {
	Slice1[c] = Freq[x][c];
	Slice2[c] = Freq[y][c];

	Freq[x][c] += Freq[y][c];
	Freq[y][c]  = Freq[Last][c];
    }

    Slice1[MaxClass+1] = ValFreq[x];
    Slice2[MaxClass+1] = ValFreq[y];

    ValFreq[x] += ValFreq[y];
    ValFreq[y]  = ValFreq[Last];
}

////////////////////////////////////////////////////////////////////////////////
//
// public  void Combine(int x,int y,int Last)
// 
//  Restore old class distribution figures of discrete attribute
//  values x and y from Slice1 and Slice2
//  
////////////////////////////////////////////////////////////////////////////////
public void Uncombine(int x,int y)
{
    int c;

    for(c=0;c<=MaxClass;c++)//ForEach(c, 0, MaxClass)
    {
       Freq[x][c] = Slice1[c];
       Freq[y][c] = Slice2[c];
    }

    ValFreq[x] = Slice1[MaxClass+1];
    ValFreq[y] = Slice2[MaxClass+1];
}
////////////////////////////////////////////////////////////////////////////////
//
// public void  EvalDiscreteAtt(int Att,int Fp,int Lp,double Items)
// 
//  Restore old class distribution figures of discrete attribute
//  values x and y from Slice1 and Slice2
//  
////////////////////////////////////////////////////////////////////////////////
public void  EvalDiscreteAtt(int Att,int Fp,int Lp,double Items)  throws IOException
  {
    double KnownItems;
      
    ComputeFrequencies(Att, Fp, Lp,Items);

    KnownItems = Items - ValFreq[0];

    //  Special case when no known values of the attribute

    if ( Items <= ValFreq[0] )
    {
      if(VERBOSITY >= 2)
      {
           System.out.println("EvalDiscreteAtt::::\n Items"+Items+"ValFreq[0]"+ValFreq[0]);
           System.out.println("\tAtt "+AttName[Att]+": no known values\n");
      }
      Gain[Att] = (double)-Epsilon;
      Info[Att] = 0.0;
      return;
    }
    double m_dDiscrKnownBaseInfo= DiscrKnownBaseInfo(KnownItems, MaxAttVal[Att]);
    Gain[Att] = ComputeGain(m_dDiscrKnownBaseInfo, UnknownRate[Att], MaxAttVal[Att], (int)KnownItems);
    Info[Att] = TotalInfo(ValFreq, (int)0, MaxAttVal[Att]) / Items;

    if(VERBOSITY >= 2)
    {
      System.out.println("\tAtt "+AttName[Att]+": \n");
      System.out.println("\tinf "+Info[Att]+" gain "+Gain[Att]+"\n");
    }
  }//public void  EvalDiscreteAtt(int Att,int Fp,int Lp,double Items) 
  
////////////////////////////////////////////////////////////////////////////////
//
// public void  EvalDiscreteAtt(int Att,int Fp,int Lp,double Items)
// 
//  
//
//  
////////////////////////////////////////////////////////////////////////////////  
public void EvalContinuousAtt(int Att, int Fp, int Lp) throws IOException
{
    int i, BestI, Xp, Tries=0,p,m_iClass;
    int Items, KnownItems, LowItems, MinSplit;
    int c;
    double AvGain=0, Val, BestVal, BaseInfo,m_dValue=0;
     
    if(VERBOSITY >= 2){System.out.println("EvalContinuousAtt\nAtt "+AttName[Att] ); }
    if(VERBOSITY >= 3){System.out.println("\n"); }
    ResetFreq((int)2);

    //  Omit and count unknown values 

    Items = (int)CountItems(Fp, Lp);

    Xp = Fp+1;
    for(p=Fp;p<= Lp;p++) //Case = Item[p];
    {

       m_dValue=GetDoubleValue(p,Att);
       m_iClass=GetClassValue(p);
       if ( m_dValue == Unknown )
       {
	   Freq[0][m_iClass] += GetWeight(p);
	   Swap(Xp, p);
	   Xp++;
//           System.out.println("Unknown value in"+m_dValue);
       }
    } //for(p=Fp;p<= Lp;p++)
     //write_itemset(Datafilename,Fp,Lp);
     
    
    ValFreq[0] = 0;
    for(c=0;c<=MaxClass;c++)
    {
	   ValFreq[0] += Freq[0][c];
          //System.out.println("Freq[0]["+c+"]=" +Freq[0][c]);
    }

    KnownItems = (int)(Items - ValFreq[0]);
    if(Items!=0)
      UnknownRate[Att] = 1.0 - KnownItems / Items;
    else
      UnknownRate[Att] = 0.0;
    //  Special case when very few known values

    if ( KnownItems < 2 * MINOBJS )
    {
	     
       if(VERBOSITY >= 2){System.out.println("\tinsufficient cases with known values\n"); }
       	Gain[Att] = -Epsilon;
        Info[Att] = 0.0;
       	return;
    }
    m_iPbCount_num++;
    Quicksort(--Xp, Lp, Att, true);//Swap);


    // Count base values and determine base information
    for(i=Xp;i<=Lp;i++)
    {
        m_iClass=GetClassValue(i);
    	Freq[ 2 ][ m_iClass] += GetWeight(i);
        SetSplitGain(i,-Epsilon);//SplitGain[i] = -Epsilon;
        SetSplitInfo(i,0.0);//SplitInfo[i] = 0;
    }

    BaseInfo = TotalInfo(Freq[2], (int)0, MaxClass) / (KnownItems!=0?KnownItems:1000000);


    //  Try possible cuts between items i and i+1, and determine the
    //	information and gain of the split in each case.  We have to be wary
    //	of splitting a small number of items off one end, as we can always
    //	split off a single item, but this has little predictive power.  

    MinSplit = (int)(0.10 * KnownItems / (MaxClass + 1));
    if ( MinSplit <= MINOBJS ) MinSplit = (int)MINOBJS;
    else
    if ( MinSplit > 25 ) MinSplit = 25;
    //System.out.println("Freq[2][0]=" +Freq[2][0]+"    Freq[2][1]=" +Freq[2][1]+
    //                    "BaseInfo="+BaseInfo+"  MinSplit="+MinSplit);

    LowItems = 0;
    
    double m_dWeight=0.0;
    double m_dSplitGain=0.0;
    double m_dSplitInfo=0.0;
    for(i=Xp;i<=Lp-1;i++)
    {             
       c = GetClassValue(i);
       m_dWeight=GetWeight(i);
       LowItems   += m_dWeight;
       Freq[1][c] += m_dWeight;
       Freq[2][c] -= m_dWeight;

       if ( LowItems < MinSplit ) continue;
       else if ( LowItems > KnownItems - MinSplit ) break;
       //System.out.println("Freq[1][0]=" +Freq[1][0]+"    Freq[1][1]=" +Freq[1][1]+
       //                   "Freq[2][0]=" +Freq[2][0]+"    Freq[2][1]=" +Freq[2][1]+
       //                   "Weight["+i+"]"+GetWeight(i));

       if (GetDoubleValue(i,Att) < GetDoubleValue(i+1,Att) - 0.000005 )
       {
       	    ValFreq[1] = LowItems;
            ValFreq[2] = KnownItems - LowItems;
            //System.out.println("ValFreq[1]=" +ValFreq[1]+"    ValFreq[2]=" +ValFreq[2]) ;
            //System.out.println( "BaseInfo"+BaseInfo +"UnknownRate "  +  UnknownRate[Att]+ " KnownItems" +KnownItems);
            SplitGain = ComputeGain(BaseInfo, UnknownRate[Att], (int)2, KnownItems);
            SplitInfo = TotalInfo(ValFreq, (int)0, (int)2) / ( Items!=0 ? Items  : 0.01 );
            SetSplitGain(i,SplitGain);
            SetSplitInfo(i,SplitInfo);
            AvGain += GetSplitGain(i);
	    Tries++;
            //System.out.println("ValFreq[1]=" +ValFreq[1]+"    ValFreq[2]=" +ValFreq[2]+
            //                   "SplitGain["+i+"]="+SplitGain[i]+"  SplitInfo["+i+"]="+SplitInfo[i]) ;
	      
	}//if (GetIntegerValue(i,Att+1) < GetIntegerValue(i+1,Att+1) - 0.000005)
    }//for(i=Xp+1;i<=Lp;i++)

    
    //  Find the best attribute according to the given criterion  
    BestVal = -Epsilon;
    BestI   = (int)None;
    AvGain  = ( Tries>0 ? AvGain / Tries : 1E6 );
    for(i=Xp;i<=Lp-1;i++)
    {
   	
        m_dSplitGain=GetSplitGain(i);
        m_dSplitInfo=GetSplitInfo(i);
   	Val = Worth(m_dSplitInfo, m_dSplitGain, AvGain);
	if ( m_dSplitGain >= 0 && Val >= BestVal )
	{
	      BestI   = i;
	      BestVal = Val;
        }
    }//for(i=Xp+1;i<=Lp-1+1;i++)

    /*  If a test on the attribute is able to make a gain,
	set the best break point, gain and information  */




    if ( BestI == None )
    {
	       Gain[Att] = -Epsilon;
	       Info[Att] = 0.0;

	       //Verbosity(2) fprintf(fprin,"\tno gain\n");
         if(VERBOSITY >= 2){System.out.println("\tno gain\n"); }
    }
    else
    {
    	Bar[Att]  = (GetDoubleValue(BestI,Att)  + GetDoubleValue(BestI+1,Att))  / 2;
	Gain[Att] = GetSplitGain(BestI);
	Info[Att] = GetSplitInfo(BestI);
         //System.out.println(" \n\n\nBestI ="+BestI+" "+Temp_CAttValue_Att4.get_cont_val()+" " +Temp_CAttValue_Att5.get_cont_val()+"   Bar[Att]="+Bar[Att]+"\n\n\n");
	      //Verbosity(2)
	    //fprintf(fprin,"\tcut=%.3f, inf %.3f, gain %.3f\n",
		   //Bar[Att], Info[Att], Gain[Att]);
       if(VERBOSITY >= 2){System.out.println("\tcut="+Bar[Att]+" inf "+Info[Att]+" gain "+Gain[Att]); }
    }
}
////////////////////////////////////////////////////////////////////////////////
//
// public void ResetFreq(int MaxVal)
// 
//  
//  
//  
////////////////////////////////////////////////////////////////////////////////    
public void ResetFreq(int MaxVal)
{
    int v;
    int c;

    for(v=0;v<= MaxVal;v++)
    {
    	for(c=0;c<= MaxClass;c++)
	{
          Freq[v][c] = 0;
	}
	ValFreq[v] = 0;
    }
}

////////////////////////////////////////////////////////////////////////////////
//
// public void Quicksort(int Fp,int Lp,int Att,boolean swap_flag)//void (*Exchange)() )
// 
//  
//  
//  
////////////////////////////////////////////////////////////////////////////////    
public void Quicksort(int Fp,int Lp,int Att,boolean swap_flag)//void (*Exchange)() )
{
    int Lower, Middle;
    double Thresh;
    int i;

    boolean flag = swap_flag;
    m_iPbCount_num++;
    if ( Fp < Lp )
    {
        
	Thresh = GetDoubleValue(Lp,Att);
	
	//  Isolate all items with values <= threshold

	Middle = Fp;

        for ( i = Fp ; i < Lp ; i++ )
        {
            if ( GetDoubleValue(i,Att) <= Thresh )
			{
			  if ( i != Middle )
	      	    {
              	   if(flag) Swap(Middle, i);
               	   else	SwapUnweighted(Middle, i);
                }
                  Middle++;
             }
       	}

        // Extract all values equal to the threshold

      	Lower = Middle-1 ;
        for ( i = Lower ; i >= Fp ; i-- )
        {
	     if ( GetDoubleValue(i,Att) == Thresh )
             {
                 if ( i != Lower )
                 {
                    if(flag)
                        Swap(Lower, i);
                    else
              		SwapUnweighted(Lower, i);
                  }
                  Lower--;
              }
	 } //for ( i = Lower ; i >= Fp ; i-- )

	 //  Sort the lower values

	 Quicksort(Fp, Lower, Att, flag);

         //  Position the middle element

	 if(flag)
	     Swap(Middle, Lp);
	 else
             SwapUnweighted(Middle, Lp);
 
	 Quicksort(Middle+1, Lp, Att, flag);
    } //if ( Fp < Lp )
} //public Quicksort(int Fp,int Lp,int Att,bool swap_flag)//void (*Exchange)() )  

////////////////////////////////////////////////////////////////////////////////
// public void SwapUnweighted(int a,int b)
////////////////////////////////////////////////////////////////////////////////    
public void SwapUnweighted(int a,int b)
  {
    double Hold_a = GetWeight(a);
    double Hold_b = GetWeight(b);

    Swap(a,b);
    SetWeight(a, Hold_a);
    SetWeight(b, Hold_b);
    
    
  }
////////////////////////////////////////////////////////////////////////////////
// public void SwapUnweighted(int a,int b)
////////////////////////////////////////////////////////////////////////////////    
public void Swap(int a,int b)
  {
    //m_cRWTableManager.exchangeRows(a+1,b+1);
     int temp=m_aIndex[a];
	 m_aIndex[a]=m_aIndex[b]; 
     m_aIndex[b]=temp; 
  }     
  
////////////////////////////////////////////////////////////////////////////////
//
// public void SubsetTest(XMCTree Node,int Att)
// 
//  
//  
//  
////////////////////////////////////////////////////////////////////////////////    
public void SubsetTest(XMCTree Node,int Att)
{
    int S, Bytes,j;

    Sprout(Node, Subsets[Att]);

    Node.NodeType = BrSubset;
    Node.Tested	= Att;
    Node.Errors	= 0;

    Bytes = (MaxAttVal[Att]>>3) + 1;
    Node.Subset = new char[Subsets[Att] + 1][];
    for(S=1;S<=Node.Forks;S++) // ForEach(S, 1, Node->Forks)
    {
	 Node.Subset[S] = new char[Bytes];
	 //CopyBits(Bytes, Subset4[Att][S], Node->Subset[S]);
         for(j=0;j<Bytes;j++)
         {
           Node.Subset[S][j]=Subset4[Att][S][j];
         }
    }
} //SubsetTest(XMCTree Node,int Att)

////////////////////////////////////////////////////////////////////////////////
//
// public void DiscreteTest(XMCTree Node,int Att)
//
//  
//  
//  
////////////////////////////////////////////////////////////////////////////////    
public void  DiscreteTest(XMCTree Node,int Att)
{
    Sprout(Node, MaxAttVal[Att]);

    Node.NodeType	= BrDiscr;
    Node.Tested	= Att;
    Node.Errors	= 0;
 }//End of void public DiscreteTest(XMCTree Node,int Att)
////////////////////////////////////////////////////////////////////////////////
//
// public void ContinTest(XMCTree Node,int Att)
// 
//  
//  
//  
////////////////////////////////////////////////////////////////////////////////    
public void  ContinTest(XMCTree Node,int Att)

{
    double Thresh;


    Sprout(Node, (int)2);

    Thresh = GreatestValueBelow(Att, Bar[Att]);

    Node.NodeType	= ThreshContin;
    Node.Tested	= Att;
    Node.Cut		=
    Node.Lower		=
    Node.Upper		= Thresh;
    Node.Errors        = 0;
 }//End of void public ContinTest(CXMXMCTreeForm Node,int Att)

////////////////////////////////////////////////////////////////////////////////
//
// public double GreatestValueBelow(int Att,double t)
//
//  
//  
//  
////////////////////////////////////////////////////////////////////////////////    
public double GreatestValueBelow(int Att,double t)
{
    int i;
    double v, Best;

    Best = -1E20;

    for(i=0 ; i<=MaxItem ; ++i)
    {
      v =  GetDoubleValue(i,Att);
      if ( v != Unknown && v <= t && v > Best ) Best = v;
    }

    return Best;
}//End of double public GreatestValueBelow(int Att,double t)
////////////////////////////////////////////////////////////////////////////////
//
// public void Sprout(XMCTree Node,int Branches)
// 
//  
//  
//  
////////////////////////////////////////////////////////////////////////////////    
public void Sprout(XMCTree Node,int Branches)
{
    Node.Forks = Branches;
    //Node.Branch = (Tree *) calloc(Branches+1, sizeof(Tree));
}

////////////////////////////////////////////////////////////////////////////////
//
// public int Group(int V, int Fp, int Lp, XMCTree TestNode)
// 
//  
//  
//  
////////////////////////////////////////////////////////////////////////////////    
public int Group(int V, int Fp, int Lp, XMCTree TestNode)
{
    int i;
    int Att;
    double Thresh;
    char[] SS;
  

    Att = TestNode.Tested;


   
   m_iPbCount_num++;

    if ( V>0 )
    {
	//  Group items on the value of attribute Att, and depending
	//    on the type of branch  

	  switch ( TestNode.NodeType )
	  {
	    case BrDiscr:

		  for(i=Fp ; i<=Lp ; ++i)
		  { //#define  DVal(Case,Attribute)   Case[Attribute]._discr_val
                      if ( GetIntegerValue(i,Att) == V ) 
                      {
                      	Swap(Fp++, i);
                      }

		  }
		  break;

	    case ThreshContin:

		  Thresh = TestNode.Cut;
		  for(i=Fp ; i<=Lp ; ++i)
		  {         
		    if ( (GetDoubleValue(i,Att) <= Thresh) == (V == 1) )
		    	   {Swap(Fp++,i); }
		  }
		  break;

	    case BrSubset:

		  SS = TestNode.Subset[V];
                  int jj=0,jj1,jj2;
                  char xxx;
		  for(i=Fp;i<=Lp;i++)//ForEach(i, Fp, Lp)
		  {
		  	//#define In(b,s) ((s[(b) >> 3]) & Bit((b) & 07))
                        
                        jj= GetIntegerValue(i,Att);
                        jj2 =jj >> 3;
                        xxx= SS[jj2];

		    if ( ((SS[jj >> 3]) & (1 << (jj & 07)))!=0)  
                           { Swap(Fp++,i); }
                   //System.out.println("here is group   Fp :"    + Fp+ "   jj " +jj+"   jj2   "+ jj2 +"   xxxx "+xxx);
		  }
		  break;
	  }
  }
  else
  {
	/*  Group together unknown values  */

     switch ( TestNode.NodeType )
	   {
	     case BrDiscr:
	     case BrSubset:

		   for(i=Fp ; i<=Lp ; ++i)
		   {           
                     if ( GetIntegerValue(i,Att)==0 ) {Swap(Fp++, i);}
                    //System.out.println("here is group   Fp :"    + Fp);
		    //if ( ! Item[i][Att]._discr_val ) Swap(Fp++, i);
		   }
		   break;

	     case ThreshContin:

		   for(i=Fp ; i<=Lp ; ++i)
		   {        
		        if (GetDoubleValue(i,Att) == Unknown)
		        { Swap(Fp++, i);}
                   }
                   break;
	   }
  }
  //System.out.println("here is group   Fp :"    + Fp);
  return (int)Fp - 1;
 }// End of int public Group(int V, int Fp, int Lp, XMCTree TestNode)




////////////////////////////////////////////////////////////////////////////////
//
// End of Class
// 
////////////////////////////////////////////////////////////////////////////////
}//End of Class
















