
//Title:        C45_Classifier
//Version:
//Copyright:    Copyright (c) 1999
//Author:       최대우
//Company:      전북대학교 산업공학과 지능정보시스템
//Description:  Your description
package xmminer.xmserver.xmgraph.xmmnode.xmmnodec45.treegen;

import java.util.*;
import java.io.*;
import java.lang.Math.*;

public class XMCPruneTreeGen  extends XMCTreeGen
{
  char   PossibleValues[][] ; //=Nil;
  boolean	Changed,Add_flag;
  double   Val[] = {  0.0,  0.001, 0.005, 0.01, 0.05, 0.10, 0.20, 0.40, 1.00};
  double   Dev[] = {100.0,  3.09,  2.58,  2.33, 1.65, 1.28, 0.84, 0.25, 0.0};
  static double  Coeff=0;
  
////////////////////////////////////////////////////////////////////////////////
//
// 생성자
// public XMCPruneTreeGen()
// public XMCPruneTreeGen(int mode)
// public XMCPruneTreeGen(String DataFileName)
// public XMCPruneTreeGen(String DataFileName, int mode)
// 
////////////////////////////////////////////////////////////////////////////////
  public XMCPruneTreeGen()
  {

  }  
  public XMCPruneTreeGen(String ProjectName, String Modelname)
  {
        m_sProjectName = ProjectName;
        m_sModelName= Modelname;
  }

////////////////////////////////////////////////////////////////////////////////
//
// public boolean Prune(XMCTree T)
//  
// 
// 
// 
////////////////////////////////////////////////////////////////////////////////

  public boolean Prune(XMCTree T)
  {
    int i,j;
    int a;

    InitialiseWeights();
    AllKnown = true;

    //Verbosity(1) fprintf(fprin,"\n");

    Changed = false;
    m_iPbCount_num++;
    EstimateErrors(T, (int)0, MaxItem, (int)0, true);
    //System.out.println("Prune #1  : ");//fprintf(fprin,"\nSimplified ");
    if ( SUBSET )
    {
	      if ( PossibleValues==null )
	      {
	          PossibleValues = new char[MaxAtt+1][];//(Set *) calloc(MaxAtt+1, sizeof(Set));
            //System.out.println("Prune #2  : ");//fprintf(fprin,"\nSimplified ");
	      }
 
     	      for(a=0; a<=MaxAtt;a++) //ForEach(a, 0, MaxAtt)
	      {
	         if ( MaxAttVal[a]>0 )
	         {  m_iPbCount_num++;
		    PossibleValues[a] = new char[(MaxAttVal[a]>>3) + 1]; // malloc();
                    // System.out.println("Prune #3  : ");//fprintf(fprin,"\nSimplified ");

		    //ClearBits((MaxAttVal[a]>>3) + 1, PossibleValues[a]);//#define ClearBits(n,s) memset(s,0,n)
                    for(j=0;j<(MaxAttVal[a]>>3);j++)
                    {
                        PossibleValues[a][j]='0';
                        //  System.out.println("Prune #4  : ");//fprintf(fprin,"\nSimplified ");
                    }

		    for(i=1;i<=MaxAttVal[a];i++)//ForEach(i, 1, MaxAttVal[a])
                    {
                        //SetBit(i, PossibleValues[a]);//#define SetBit(b,s)	(s[(b) >> 3] |= Bit((b) & 07))
                        PossibleValues[a][(i) >> 3] |=  (1 << ((i) & 07)); //         Bit((i) & 07));
                        // System.out.println("Prune #5  : ");//fprintf(fprin,"\nSimplified ");
                    }
	         }//if ( MaxAttVal[a] )
	       }//for(a=0; a<=MaxAtt;a++)

	       CheckPossibleValues(T);
              // System.out.println("Prune #6  : ");//fprintf(fprin,"\nSimplified ");
    }//    if ( SUBSET )*/
    //System.out.println("Prune #7  : ");//fprintf(fprin,"\nSimplified ");
    return Changed;
}
////////////////////////////////////////////////////////////////////////////////
//
// public double EstimateErrors(XMCTree T,int Fp,int Lp,int Sh,boolean UpdateTree)
//  
// 
// Estimate the errors in a given subtree
// 
////////////////////////////////////////////////////////////////////////////////

public double EstimateErrors(XMCTree T,int Fp,int Lp,int Sh,boolean UpdateTree)
{
    int i, Kp, Ep;
    double Cases, KnownCases, LocalClassDist[], TreeErrors, LeafErrors,
	   ExtraLeafErrors, BranchErrors, Factor, MaxFactor;
    int v, MaxBr=1;
    int c, BestClass;
    boolean PrevAllKnown;

    //  Generate the class frequency distribution

    Cases = CountItems(Fp, Lp);
    LocalClassDist = new double[MaxClass+1];
 
    //System.out.println("EstimateErrors #1  : ");
    
    for(i=Fp;i<=Lp;i++)//ForEach(i, Fp, Lp)
    {
        LocalClassDist[ GetClassValue(i) ] += GetWeight(i);

    }

    //  Find the most frequent class and update the Tree

    BestClass = T.Leaf;
    for(c=0;c<=MaxClass;c++)//ForEach(c, 0, MaxClass)
    {
       if ( LocalClassDist[c] > LocalClassDist[BestClass] )
       {
           BestClass = c;
       }
    }//for(c=0;c<=MaxClass;c++)//ForEach(c, 0, MaxClass)

    LeafErrors = Cases - LocalClassDist[BestClass];

    ExtraLeafErrors = AddErrs(Cases, LeafErrors);
    //System.out.println("ExtraLeafErrors"+ExtraLeafErrors);
    if ( UpdateTree )
    {
       T.Items = Cases;
       T.Leaf  = BestClass;
       //memcpy(T.ClassDist, LocalClassDist, (MaxClass + 1) * sizeof(double));
       for(i=0;i <=MaxClass;i++)
       {
         T.ClassDist[i]= LocalClassDist[i];
       }

    }

    if (  T.NodeType == 0 )	/*  leaf  */
    {
       TreeErrors = LeafErrors + ExtraLeafErrors;

       if ( UpdateTree )
       {
           T.Errors = TreeErrors;

           //LocalVerbosity(1)
           //{
           //    Intab(Sh);
           //    fprintf(fprin,"%s (%.2f:%.2f/%.2f)\n", ClassName[T.Leaf],
           //                    T.Items, LeafErrors, T.Errors);
           //}
        }

	     //free(LocalClassDist);
        //System.out.println("T.NodeType == 0 "+"  TreeErrors"+TreeErrors+"     LeafErrors"+LeafErrors+"      ExtraLeafErrors"+ExtraLeafErrors);
        //System.out.println(ClassName[T.Leaf]+"("+T.Items +": "+ LeafErrors +"/"+ T.Errors);

	     return TreeErrors;
    }//if (  T.NodeType =! 0 )	/*  leaf  */

    //  Estimate errors for each branch

    Kp = Group((int)0, Fp, Lp, T) + 1;
    KnownCases = CountItems(Kp, Lp);

    PrevAllKnown = AllKnown;
    if ( Kp != Fp ) AllKnown = false;

    TreeErrors = MaxFactor = 0;
    
    
    double m_dWeight=0.0;
    for(v=1;v<=T.Forks;v++) //(v, 1, T.Forks)
    {
        
      	Ep = Group(v, Kp, Lp, T);

        if ( Kp <= Ep )
        {
           Factor = CountItems(Kp, Ep) / KnownCases;
            if ( Factor >= MaxFactor )
            {
            	MaxBr = v;
                MaxFactor = Factor;
            }//if ( Factor >= MaxFactor )

	    for(i=Fp;i<=Kp-1;i++)//ForEach(i, Fp, Kp-1)
            {
              	m_dWeight=GetWeight(i);
	        m_dWeight *= Factor;
	        SetWeight(i,m_dWeight);
              	
            }//for(i=Fp;i<=Kp-1;i++)//ForEach(i, Fp, Kp-1)

	    //System.out.println("EstimateErrors  v:"+v);
	    //Showxxx(T, v);

	    TreeErrors += EstimateErrors((XMCTree)T.Branch.elementAt(v-1), Fp, Ep, (int)(Sh+1), UpdateTree);

	    Group((int)0, Fp, Ep, T);
	    for(i=(int)Fp;i<=Kp-1;i++)//ForEach(i, Fp, Kp-1)
            {
            	m_dWeight=GetWeight(i);
	        m_dWeight /= Factor;
	        SetWeight(i,m_dWeight);
            }//for(i=Fp;i<=Kp-1;i++)//ForEach(i, Fp, Kp-1)
	 }//if ( Kp <= Ep )
    } //for(v=1;v<=T.Forks;v++) //(v, 1, T.Forks)

    AllKnown = PrevAllKnown;

    if (  !UpdateTree )
    {
	     //free(LocalClassDist);
        //System.out.println("!UpdateTree == 0 "+"  TreeErrors"+TreeErrors);
	return TreeErrors;
    }

    //  See how the largest branch would fare
    //System.out.println("EstimateErrors  BranchErrors v:"+MaxBr);
    //Showxxx(T, MaxBr);
    BranchErrors = EstimateErrors((XMCTree)T.Branch.elementAt(MaxBr-1), Fp, Lp, (int)-1000, false);//T.Branch[MaxBr]

    //LocalVerbosity(1)
    //{
    //    Intab(Sh);
    //    fprintf(fprin,"%s:  [%d%%  N=%.2f  XMCTree=%.2f  leaf=%.2f+%.2f  br[%d]=%.2f]\n",
		//A           ttName[T.Tested],
		//            (int) ((TreeErrors * 100) / (T.Items + 0.001)),
		//            T.Items, TreeErrors, LeafErrors, ExtraLeafErrors,
		//            MaxBr, BranchErrors);
    //}
    int aaa= (int) ((TreeErrors * 100) / (T.Items + 0.001));
    //System.out.println(AttName[T.Tested] + " :    ["+aaa+ "    N="+T.Items+"  Tree="
    //                   + TreeErrors+"   leaf="+ LeafErrors+"+"+ExtraLeafErrors+
    //                   "      br["+ MaxBr+"]="+ BranchErrors);
    //  See whether XMCTree should be replaced with leaf or largest branch

    if ( LeafErrors + ExtraLeafErrors <= BranchErrors + 0.1 &&
	       LeafErrors + ExtraLeafErrors <= TreeErrors + 0.1 )
    {
      	//LocalVerbosity(1)
        //{
        //    Intab(Sh);
        //    fprintf(fprin,"Replaced with leaf %s\n", ClassName[T.Leaf]);
        //}

	      //System.out.println("Replaced with leaf"+"LeafErrors   "+LeafErrors +" ExtraLeafErrors  "+ ExtraLeafErrors +"  BranchErrors "+ BranchErrors+" TreeErrors  "+  TreeErrors );
	      T.NodeType = 0;
	      T.Errors = LeafErrors + ExtraLeafErrors;
	      Changed = true;
    }// if ( LeafErrors + ExtraLeafErrors <= BranchErrors + 0.1 &&
    else if ( BranchErrors <= TreeErrors + 0.1 )
    {
        //LocalVerbosity(1)
        //{
        //   Intab(Sh);
        //   fprintf(fprin,"Replaced with branch %d\n", MaxBr);
        //}   
              
               //System.out.println("BranchErrors <= TreeErrors + 0.1"+"  TreeErrors"+TreeErrors); 
	      AllKnown = PrevAllKnown;
              //System.out.println("EstimateErrors  v:"+MaxBr); 
              //Showxxx(T, MaxBr);
              EstimateErrors((XMCTree)T.Branch.elementAt(MaxBr-1), Fp, Lp, Sh, true);
              
              XMCTree T1 = new XMCTree();
              T1 = (XMCTree)T.Branch.elementAt(MaxBr-1);
              T=T1;
              //memcpy((char *) T, (char *) (XMCTree)T.Branch.elementAt(MaxBr-1), sizeof(TreeRec));
              Changed = true;
    }//else if ( BranchErrors <= TreeErrors + 0.1 )
    else
    {
	      T.Errors = TreeErrors;
    }// if ( LeafErrors + ExtraLeafErrors <= BranchErrors + 0.1 &&   ==> else

    AllKnown = PrevAllKnown;
    //free(LocalClassDist);

    return T.Errors;
}

////////////////////////////////////////////////////////////////////////////////
//
// public double AddErrs(double N,double e)
//  
// 
// Add the errors in a given subtree
// 
////////////////////////////////////////////////////////////////////////////////
public double AddErrs(double N,double e)
{
    
    double Val0, Pr;	
	m_iPbCount_num++;
    //System.out.println("Coeff :"+Coeff);
    if ( Coeff==0.0 )
    {
   	 //  Compute and retain the coefficient value, interpolating from
	 //  the values in Val and Dev

	 int i;
         i = 0;
          
         while ( CF > Val[i] ) i++;
         Coeff = Dev[i-1] + (Dev[i] - Dev[i-1]) * (CF - Val[i-1]) /(Val[i] - Val[i-1]);
	 Coeff = Coeff * Coeff;
         //System.out.println("if ( Coeff!=0 )");
    }//if ( ! Coeff )

    if ( e < 1E-6 )
    {
      double aa =java.lang.Math.log(CF)/java.lang.Math.log(java.lang.Math.E);
      double bb = N * (1 - java.lang.Math.exp(aa/N));
      //System.out.println(" if ( e < 1E-6 )   bb : " + bb+"  N:"+N+"  Cf="+CF+"   aa:"+aa);
      return bb;//N * (1 - java.lang.Math.exp((java.lang.Math.ln(CF)) / N)); //N * (1 - exp(log(CF) / N));
    }
    else if ( e < 0.9999 )
    {   //System.out.println("else if ( e < 0.9999 )");
	    double aa =java.lang.Math.log(CF)/java.lang.Math.log(java.lang.Math.E);
	    Val0 = N * (1 - java.lang.Math.exp(aa / N));
	    return Val0 + e * (AddErrs(N, 1.0) - Val0);
    }
    else if ( e + 0.5 >= N )
    {
      //System.out.println("else if ( e + 0.5 >= N )");
	    return 0.67 * (N - e);
    }
    else
    {
       //System.out.println("else if ( e + 0.5 >= N ).else  Coeff; "+Coeff);
	     Pr = (e + 0.5 + Coeff/2  + java.lang.Math.sqrt(Coeff * ((e + 0.5) * (1 - (e + 0.5)/N)
               + Coeff/4)) ) / (N + Coeff);
	     return (N * Pr - e);
    }
}

////////////////////////////////////////////////////////////////////////////////
//
// public double AddErrs(double N,double e)
//  
// 
// Remove unnecessary subset tests on missing values
// 
////////////////////////////////////////////////////////////////////////////////

public void CheckPossibleValues(XMCTree T)

{
    char [] HoldValues;
    int v, Bytes, b,i;
    int A;
    char Any='0';

//    System.out.println("CheckPossibleValues ");
    if ( T.NodeType == BrSubset )
    {
	      A = T.Tested;

        Bytes = (MaxAttVal[A]>>3) + 1;
        HoldValues = new char[Bytes];
//        System.out.println("T.NodeType == BrSubset  : ");
        //  See if last (default) branch can be simplified or omitted
        
//        System.out.println("Any  : " +Any);
        for(b=0;b<=Bytes-1;b++)//       ForEach(b, 0, Bytes-1)
        {
            T.Subset[T.Forks][b] &= PossibleValues[A][b];
            Any |= T.Subset[T.Forks][b];
        }//for(b=0;b<=Bytes-1;b++)
        
        if (  Any=='0' )
        {
//            System.out.println("Any  : " +Any);
            T.Forks--;
        }

	//  Process each subtree, leaving only values in branch subset  //

	      //CopyBits(Bytes, PossibleValues[A], HoldValues);  //#define CopyBits(n,f,t) memcpy(t,f,n)
         //memcpy(HoldValues, PossibleValues[A],Bytes)
        for(i=0;i<Bytes;i++)
        {
            HoldValues[i]=PossibleValues[A][i];
        }


        for(v=1;v<=T.Forks;v++)//(v, 1, T.Forks)
        {

      	  //CopyBits(Bytes, T.Subset[v], PossibleValues[A]); //#define	 CopyBits(n,f,t) memcpy(t,f,n)
          for(i=0;i<Bytes;i++)
          {
                PossibleValues[A][i]=T.Subset[v][i];
          }
          CheckPossibleValues((XMCTree)T.Branch.elementAt(v-1));
        }

	      //CopyBits(Bytes, HoldValues, PossibleValues[A]); //#define CopyBits(n,f,t) memcpy(t,f,n)
        for(i=0;i<Bytes;i++)
        {
            PossibleValues[A][i]=HoldValues[i];
        }
	      //free(HoldValues);
    }//if ( T.NodeType == BrSubset )
    else if ( T.NodeType >0)
    {
       for(v=1;v<=T.Forks;v++)// ForEach(v, 1, T.Forks)
       {
       	    CheckPossibleValues((XMCTree)T.Branch.elementAt(v-1));//T.Branch[v]);
       }
    }//else if ( T.NodeType )
}//public void CheckPossibleValues(XMCTree T)



////////////////////////////////////////////////////////////////////////////////
//
// End of Class
//  
// 
// 
// 
////////////////////////////////////////////////////////////////////////////////

}//End of Class