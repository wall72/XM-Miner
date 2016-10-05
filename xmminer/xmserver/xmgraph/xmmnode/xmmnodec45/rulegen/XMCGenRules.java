
//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1999
//Author:       최대우
//Company:      전북대학교 산업공학과 지능정보시스템
//Description:  Your description
package xmminer.xmserver.xmgraph.xmmnode.xmmnodec45.rulegen;
import  xmminer.xmserver.xmgraph.xmmnode.xmmnodec45.treegen.*;
import xmminer.xmlib.xmtable.*;
import xmminer.xmlib.xmcompute.*;
import xmminer.xmserver.xmgraph.xmmnode.xmmnodec45.rulegen.typedef.*;

public class XMCGenRules extends XMCTestRules
{////////////////////////////////////////////////////////////////////////////////
//
//genrules.c
//
//
//
//  For each tree, form a set of rules and process them, then form a
//  composite set of rules from all of these sets.
//  If there is only one tree, then no composite set is formed.
//								  	 
//  Rulesets are stored in  PRSet[0]  to  PRSet[TRIALS], where
//  PRSet[TRIALS] contains the composite ruleset.
//
//  On completion, the current ruleset is the composite ruleset (if one
//  has been made), otherwise the ruleset from the single tree.
//
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
//
// 생성자
// public XMCGenRules()
// public XMCGenRules(String ProjectName, String Modelname)
// 
////////////////////////////////////////////////////////////////////////////////
  public XMCGenRules()
  {

  }
  
  public XMCGenRules(String ProjectName, String Modelname)
  {
        m_sProjectName = ProjectName;
        m_sModelName= Modelname;
  }

////////////////////////////////////////////////////////////////////////////////
//
// public void   GenerateRules()
//
//
//
//
////////////////////////////////////////////////////////////////////////////////
public void   GenerateRules(XMCTree DecisionTree)
{
    
    int t=0, RuleSetSpace=0, r,old_RuleSetSpace=0;

    //  Find bits to encode attributes and branches
 try
 {
      FindTestCodes();

    //  Now process each decision tree
    //DecisionTree =new  XMCTree((XMCData) this);
    //while ( DecisionTree.GetTree(m_sModelName+".tree"))//; = GetTree(".unpruned") )
    //DecisionTree.GetTree(m_sModelName+".tree");

//	     System.out.println("\n------------------\n");
//		 System.out.println("Processing XMCTree "+ t);

	       //  Form a set of rules from the next tree
//         System.out.println("before FormRules ");
	     FormRules(DecisionTree);
		 
         m_iPbCount_num++;
         //int x;
		 //for(x=1;x<=NRules;x++)//ForEach(ri, 1, NRules )
		 //{
	     //   PrintRule(x);
		 //}
//          System.out.println("after FormRules");
	       //  Process the set of rules for this trial
//         System.out.println("before ConstructRuleset ");
	     ConstructRuleset();
	     m_iPbCount_num++;
//		 System.out.println("after ConstructRuleset");
         
//	     System.out.println("\nFinal rules from tree "+t+":");
	     PrintIndexedRules();

	     //Make sure there is enough room for the new ruleset

	    if ( t + 1 >= RuleSetSpace )
	    {
	       old_RuleSetSpace=RuleSetSpace;
		   RuleSetSpace += 10;

	       if ( RuleSetSpace > 10 )
	       {
		           XMCRuleSet[]  PRSet_temp = new  XMCRuleSet[old_RuleSetSpace];
				   System.arraycopy(PRSet, 0, PRSet_temp, 0, old_RuleSetSpace);
				   PRSet = new XMCRuleSet[RuleSetSpace];//(XMCTest *) realloc(TestVec, TestSpace * sizeof(XMCTest));
				   System.arraycopy(PRSet_temp, 0, PRSet, 0, old_RuleSetSpace);
				   
				   //PRSet = (XMCRuleSet *) realloc(PRSet, RuleSetSpace * sizeof(XMCRuleSet));
	       }
	       else
	       {
		           PRSet = new XMCRuleSet[RuleSetSpace];
				   for (int i=0;i< RuleSetSpace;i++ )
				   {
        			   PRSet[i]= new XMCRuleSet();
				   }
				   old_RuleSetSpace=RuleSetSpace;
//				   System.out.println("RuleSetSpace :"+RuleSetSpace+"       t:"+t);
				   //PRSet = (XMCRuleSet *) malloc(RuleSetSpace * sizeof(XMCRuleSet));
	       }
	    }
		    
			

	        PRSet[t].SNRules = NRules;
	        PRSet[t].SRule = Rule;
	        PRSet[t].SRuleIndex = RuleIndex;
	        PRSet[t].SDefaultClass = DefaultClass;
           ++t;

    if ( t==0 )
    {
//	     System.out.println("\nERROR in :  GenerateRules(XMCTree DecisionTree)\n");
	     return;//exit(1);
    }

    //TRIALS = t;

    //  If there is more than one tree in the trees file,
	  //  make a composite ruleset of the rules from all trees

    //if ( TRIALS > 1 )
    //{
	  //  CompositeRuleset();
    //}
   }
   catch (Exception e) 
   {
//        System.out.println("\nERROR:  can't find any decision trees\n");
		e.printStackTrace();
   } 
}

////////////////////////////////////////////////////////////////////////////////
//
// public void  FindTestCodes()
//
//
// Determine code lengths for attributes and branches
//
////////////////////////////////////////////////////////////////////////////////
public void  FindTestCodes()
{
    int    Att;
    int    v, V,i;
    int[]     ValFreq;
    int    PossibleCuts;
    double Sum, SumBranches=0, p;


    BranchBits  = new double[MaxAtt+1];//(double *) malloc((MaxAtt+1) * sizeof(double));
     m_iPbCount_num++;
    for(Att=0;Att<=MaxAtt;Att++)//ForEach(Att, 0, MaxAtt)
    {
      V = MaxAttVal[Att];
    	if (V>0 )
      {
        ValFreq = new int[V+1];//(int *) calloc(V+1, sizeof(int));

        for(i=0;i<=MaxItem;i++)//ForEach(i, 0, MaxItem)
        {
            ValFreq[GetIntegerValue(i,Att)]++;
        }
        Sum = 0;
        for(v=1;v<=V;v++)//ForEach(v, 1, V)
        {
		       if ( ValFreq[v]>0)
           {
               Sum += (ValFreq[v] / (MaxItem+1.0)) *
                      ( m_fLogItemNo_LogFact_Manager.getDouble(MaxItem+2,C_LOGITEMNO)
                      - m_fLogItemNo_LogFact_Manager.getDouble(ValFreq[v]+1,C_LOGITEMNO));//LogItemNo[ValFreq[v]]);//C_LOGITEMNO
            }
	      }
	      BranchBits[Att] = Sum;
	    }//if (V>0 )
	    else
      {
         Quicksort(0, MaxItem, Att, false);
	       PossibleCuts = 1;
         for(i=1;i<=MaxItem;i++)
         {
             if ( GetDoubleValue(i,Att) > GetDoubleValue(i-1,Att) )
                  PossibleCuts++;
         }
         BranchBits[Att] = PossibleCuts > 1 ?
			      1 + m_fLogItemNo_LogFact_Manager.getDouble(PossibleCuts+1,C_LOGITEMNO)  / 2 : 0 ; //LogItemNo[PossibleCuts]
	    }
       SumBranches += BranchBits[Att];
   }//for(Att=0;Att<=MaxAtt;Att++)

    AttTestBits = 0;
    for(Att=0;Att<=MaxAtt;Att++)//ForEach(Att, 0, MaxAtt)
    {
      p = BranchBits[Att] / SumBranches;
      if ( p > 0 )
      {
	       AttTestBits -= p * java.lang.Math.log(p) / java.lang.Math.log(2.0);
      }
    }
}
/*
/////////////////////////////////////////////////////////////////////////
//
//	Form composite ruleset for all trials
//
/////////////////////////////////////////////////////////////////////////


public void   CompositeRuleset()
{
    int r;
    int t, ri;
    
    InitialiseRules();

    //  Lump together all the rules from each ruleset

    ForEach(t, 0, TRIALS-1)
    {
	ForEach(ri, 1, PRSet[t].SNRules)
	{
	    r = PRSet[t].SRuleIndex[ri];
	    NewRule(PRSet[t].SRule[r].Lhs, PRSet[t].SRule[r].Size,
		     PRSet[t].SRule[r].Rhs, PRSet[t].SRule[r].Error);
	}
    }

    //  ... and select a subset in the usual way

    ConstructRuleset();

//    System.out.println("\nComposite ruleset:\n");
    PrintIndexedRules();

    PRSet[TRIALS].SNRules    = NRules;
    PRSet[TRIALS].SRule      = Rule;
    PRSet[TRIALS].SRuleIndex = RuleIndex;
    PRSet[TRIALS].SDefaultClass = DefaultClass;
} */
/////////////////////////////////////////////////////////////////////////
//
//  End of Class
//
/////////////////////////////////////////////////////////////////////////
}