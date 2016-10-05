
//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1999
//Author:       최대우
//Company:      전북대학교 산업공학과 지능정보시스템
//Description:  Your description
package xmminer.xmserver.xmgraph.xmmnode.xmmnodec45.rulegen;
import  xmminer.xmserver.xmgraph.xmmnode.xmmnodec45.treegen.*;
import xmminer.xmserver.xmgraph.xmmnode.xmmnodec45.rulegen.typedef.*;
import xmminer.xmlib.xmtable.*;
import xmminer.xmlib.xmcompute.*;

public class XMCMakeRules extends XMCPruneRule//XMCMakeRules
{//
//
//makerules.c
//
//

int     SingleValue[];	// [Attribute]

XMCCondition Stack_rule[];

int	   MaxDisjuncts,
	     MaxDepth;


////////////////////////////////////////////////////////////////////////////////
//
// 생성자
// public XMCMakeRules()
// public XMCMakeRules(String ProjectName, String Modelname)
// 
////////////////////////////////////////////////////////////////////////////////
  public XMCMakeRules()
  {

  }
  
  public XMCMakeRules(String ProjectName, String Modelname)
  {
        m_sProjectName = ProjectName;
        m_sModelName= Modelname;
  }



/////////////////////////////////////////////////////////////////////////
//
//	Form a ruleset from decision tree t
//
/////////////////////////////////////////////////////////////////////////


public void    FormRules(XMCTree t)
//  ----------
//    XMCTree t;
{
    int i;
    m_iPbCount_num++;
    //  Find essential parameters and allocate storage
//    System.out.println("FormRules #1 ");
    MaxDepth = 0;
    MaxDisjuncts = 0;
//    System.out.println("FormRules #2 ");
    TreeParameters(t, 0);
//    System.out.println("FormRules #3 ");
    Actual =new double[MaxDepth+2];//, sizeof(double));
    Total = new int[MaxDepth+2];//(int *) calloc(MaxDepth+2, sizeof(int));
    Errors =new int[MaxDepth+2];//(int *) calloc(MaxDepth+2, sizeof(int));
    Pessimistic =new double[MaxDepth+2];//(double *) calloc(MaxDepth+2, sizeof(double));

    CondSigLevel =new double[MaxDepth+2];//(double *) calloc(MaxDepth+2, sizeof(double));

    TargetClassFreq =new int[2];//(int *) calloc(2, sizeof(int));

    Deleted =new boolean[MaxDepth+2];//(Boolean *) calloc(MaxDepth+2, sizeof(Boolean));

    //CondSatisfiedBy = char[MaxDepth+2][];//(char **) calloc(MaxDepth+2, sizeof(char *));
    String m_sCondSatisfiedBy=new String(m_sModelName+"_CondSatisfiedBy");
    int[]   m_iColIndex;
    m_iColIndex=new int[MaxDepth+2];
    for(i=0; i<=MaxDepth+1;i++)
    {
         m_iColIndex[i]=4;
    }
    m_fCondSatisfiedBy_Saver = new  CXMWeightTableSaver();
    m_fCondSatisfiedBy_Saver.setFileStatus(m_sProjectName,m_sCondSatisfiedBy);
    m_fCondSatisfiedBy_Saver.createWeightTable(m_iColIndex, MaxItem+1);
    m_fCondSatisfiedBy_Saver.close();

    m_fCondSatisfiedBy_Manager = new CXMWeightTableManager();
    //m_cRWTableManager.setFileStatus(m_sProjectName,m_sWeightFileName);
    m_fCondSatisfiedBy_Manager.setFileStatus(m_sProjectName, m_sCondSatisfiedBy, m_iColIndex, MaxItem+1);

    Stack_rule = new XMCCondition[MaxDepth+2];//(XMCCondition *) calloc(MaxDepth+2, sizeof(XMCCondition));
//    System.out.println("  MaxDepth=  "+MaxDepth);
    for(i=0;i<=MaxDepth+1;i++)//ForEach(i, 0, MaxDepth+1)
    {
    	 //CondSatisfiedBy[i] = new char[MaxItem+1]; //(char *) calloc(MaxItem+1, sizeof(char));
       Stack_rule[i] = new XMCCondition();//(XMCCondition) malloc(sizeof(struct CondRec));
    }

    SingleValue = new int[MaxAtt+1];//(int *) calloc(MaxAtt+1, sizeof(int));

    InitialiseRules();

    //  Extract and prune disjuncts
//    System.out.println("FormRules #4 ");
    Scan(t, 0);
//    System.out.println("FormRules #5 ");
    //  Deallocate storage

    //ForEach(i, 0, MaxDepth+1)    {	free(CondSatisfiedBy[i]);	free(Stack[i]);    }
    //free(Deleted);    free(CondSatisfiedBy);    free(Stack);    free(Actual);
    //free(Total);    free(Errors);    free(Pessimistic);    free(CondSigLevel);
    //free(TargetClassFreq);
}



/////////////////////////////////////////////////////////////////////////
//
//  Find the maximum depth and the number of leaves in tree t
//  with initial depth d
//
/////////////////////////////////////////////////////////////////////////


void  TreeParameters(XMCTree t,int d)
{
    int v;

    if ( t.NodeType>0 )
    {
    	for(v=1;v<=t.Forks;v++)//ForEach(v, 1, t->Forks)
      {
      	    TreeParameters((XMCTree)t.Branch.elementAt(v-1), d+1);
      }
    }//if ( t.NodeType>0 )
    else
    {
	       //  This is a leaf
         	if ( d > MaxDepth ) MaxDepth = d;
          MaxDisjuncts++;
    }
}



/////////////////////////////////////////////////////////////////////////
//
//  Extract disjuncts from tree t at depth d, and process them
//
/////////////////////////////////////////////////////////////////////////


public void    Scan(XMCTree t,int d)
{
    int v;
    int i,length,Bytes;
    XMCCondition Term[];
    XMCTest x;//, FindTest();
	m_iPbCount_num++;
    //System.out.println("Scan #1 "+d);
    if ( t.NodeType>0 )
    {
        d++;
		//System.out.println("Scan #2 "+d);
        x = new XMCTest();//(XMCTest) malloc(sizeof(struct TestRec));
	    x.NodeType = t.NodeType;
	    x.Tested = t.Tested;
	    x.Forks = t.Forks;
	    x.Cut = ( t.NodeType == ThreshContin ? t.Cut : 0 );
		//System.out.println("Scan #3 "+d);
	    if ( t.NodeType == BrSubset )
		{
      	    x.Subset = new char[t.Forks + 1][];//(Set *) calloc(t->Forks + 1, sizeof(Set));

            length=t.Subset[1].length;
            //System.out.println("Scan #3 "+"length="+length);
            for(v=1;v<=t.Forks;v++)  //ForEach(v, 1, t->Forks)
	          {
                  x.Subset[v]=new char[length];
                  for(Bytes=0;Bytes<length;Bytes++)
                  {
                      x.Subset[v][Bytes]= t.Subset[v][Bytes];
                  }
            }
         }//if ( t.NodeType == BrSubset )

         Stack_rule[d].CondTest = FindTest(x);
         for(v=1;v<=t.Forks;v++)//ForEach(v, 1, t->Forks)
		 {
            Stack_rule[d].TestValue = v;
			//System.out.println("Scan #4 "+d);
            Scan((XMCTree)t.Branch.elementAt(v-1), d);
			//System.out.println("Scan #5 "+d);
         }//for(v=1;v<=t.Forks;v++)
    }//if ( t.NodeType>0 )
    else
    {
         //  Leaf of decision tree - construct the set of
         //  conditions associated with this leaf and prune
         Term = new XMCCondition[d+1];//(XMCCondition *) calloc(d+1, sizeof(XMCCondition));        
		 for (i=1;i<=d;i++)//ForEach(i, 1, d)
         {
         	  Term[i] = new XMCCondition();//(XMCCondition) malloc(sizeof(struct CondRec));         	  
              Term[i].CondTest.CopyXMCTest(Stack_rule[i].CondTest);// = Stack[i].CondTest;              
              Term[i].TestValue = Stack_rule[i].TestValue;
              
         }		 
         
		 PruneRule(Term, d, t.Leaf);
		 
         //free(Term);
    }
}
/////////////////////////////////////////////////////////////////////////
//
//  End of Class
//
/////////////////////////////////////////////////////////////////////////
}







