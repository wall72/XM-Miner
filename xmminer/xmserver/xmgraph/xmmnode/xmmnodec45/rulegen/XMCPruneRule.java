
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

public class XMCPruneRule extends  XMCSiftRules
{//
//
//prunerule.c
//
//
public int	 TargetClassFreq[],	// [Boolean]
	           Errors[],		// [Condition]
	           Total[];			// [Condition]

public double	Pessimistic[],		// [Condition]
	      Actual[],		// [Condition]
	      CondSigLevel[];		// [Condition]

//int	  CondSatisfiedBy[][],	// [Condition][int]
public CXMWeightTableSaver m_fCondSatisfiedBy_Saver=null;
public CXMWeightTableManager m_fCondSatisfiedBy_Manager=null;
public boolean	      Deleted[];		// [Condition]

//#define Before(n1,n2)  (n1->Tested < n2->Tested ||\
//			n1->NodeType < n2->NodeType ||\
//		        n1->Tested == n2->Tested && n1->Cut < n2->Cut)

//#define IsTarget(case)  (Class(case) == TargetClass ? 1 : 0)


////////////////////////////////////////////////////////////////////////////////
//
// 생성자
// public XMCPruneRule()
// public XMCPruneRule(String ProjectName, String Modelname)
// 
////////////////////////////////////////////////////////////////////////////////
  public XMCPruneRule()
  {

  }
  
  public XMCPruneRule(String ProjectName, String Modelname)
  {
        m_sProjectName = ProjectName;
        m_sModelName= Modelname;
  }
/////////////////////////////////////////////////////////////////////////
//
//  Prune the rule given by the conditions Cond, and the number of
//  conditions NCond, and add the resulting rule to the current
//  ruleset if it is sufficiently accurate
//
/////////////////////////////////////////////////////////////////////////

void    PruneRule(XMCCondition Cond[], int NCond,int TargetClass)
{
    int d, dd, id, Bestd, Bestid=0, Remaining=NCond;
    double DefaultError=0, Extra=0;//, AddErrs(), TableProb();
    boolean Alter=true;//, Satisfies(), NewRule(), Redundant();
    XMCCondition Hold;
    int i;
    //System.out.println("pruning rule #1");
    if(VERBOSITY >=1) System.out.println("\n  pruning rule   " + "Remaining  =  "+Remaining);

    for(d=0;d<=NCond;d++)//ForEach(d, 0, NCond)
    {
      	Deleted[d] = false;
		if(d!=0)PrintCondition(Cond[d]);
    }

    //  Evaluate the satisfaction matrix
    TargetClassFreq[0] = TargetClassFreq[1] = 0;
    //System.out.println("pruning rule #2");
    for(i=0;i<=MaxItem;i++)//ForEach(i, 0, MaxItem)
    {
      	ClearOutcomes();
        for(d=1;d<=NCond;d++)//ForEach(d, 1, NCond)
        {
              //need Edit
        	  if(Satisfies(i, Cond[d]))//CondSatisfiedBy[d][i] = Satisfies(i, Cond[d]);
			  {
                   m_fCondSatisfiedBy_Manager.setInteger(i+1,d+1,1);
				   //System.out.println("CondSatisfiedBy["+d+"]["+i+"]=1");
              }
              else 
			  {
			       m_fCondSatisfiedBy_Manager.setInteger(i+1,d+1,0);
				   //System.out.println("CondSatisfiedBy["+d+"]["+i+"]=0");
              }
        }
        if(GetClassValue(i)== TargetClass)      TargetClassFreq[1]++;
        else TargetClassFreq[0]++;

    }
//	System.out.println("TargetClassFreq[1]="+TargetClassFreq[1]+"   TargetClassFreq[0]="+TargetClassFreq[0]);
    DefaultError = 1.0 - (TargetClassFreq[1] + 1.0) / (MaxItem + 3.0);
//    System.out.println("pruning rule #3        DefaultError="+DefaultError);
    //  Find conditions to delete

    do
    {
      //System.out.println("pruning rule #4");
	  Alter = false;
      FindTables(NCond, TargetClass);
      
	  
	  //  Find the condition, deleting which would most improve
      //  the accuracy of the rule.
      //  Notes: The pessimistic error rate, and not the actual
      //	error rate, is currently used.
      //  When d is 0, we are dealing with all conditions.
      
	  //System.out.println("pruning rule #5");
      Bestd = id = 0;
      if(VERBOSITY >=1)  System.out.println("Err   Used   Pess   Absent condition");
      m_iPbCount_num++;
      for(d=0;d<=NCond;d++)//ForEach(d, 0, NCond)
      {
      	    if ( Deleted[d] ) continue;
            if ( Total[d]!=0)
            {
            	Actual[d] = Errors[d] / (double) Total[d];
                Extra = AddErrs((double) Total[d], (double) Errors[d]);
                Pessimistic[d] = (Errors[d] + Extra) / Total[d];
            }
            else
            {
             	 Actual[d] = 0;
                 Pessimistic[d] = DefaultError;
            }

            if(VERBOSITY >=1)
		         System.out.println(" Errors ="+  Errors[d]+"   Total="+Total[d]+  "     Pessimistic="+ Pessimistic[d]);

            if ( d==0 )
            {
               if(VERBOSITY >=1) System.out.println("\t<base rule>\n");
            }
            else
            {
            	  id++;
                //  If significance testing option used, invoke Fisher's
                //  exact test here to assess probability that division
                //  by d arises from chance.
                if ( SIGTEST )
                {
                   CondSigLevel[d] = TableProb(Errors[0],
                                               Errors[d]-Errors[0],
                                               Total[0]-Errors[0],
                                               Total[d]-Total[0]-Errors[d]+Errors[0]);
                   if(VERBOSITY >=1) System.out.println("  Sig="+CondSigLevel[d]);
                }//if ( SIGTEST )

	              if(VERBOSITY >=1) PrintCondition(Cond[d]);

	              //  Bestd identifies the condition with lowest pessimistic
	              //  error  estimate
	              if ( Bestd==0 || Pessimistic[d] <= Pessimistic[Bestd] )
	              {
	                 Bestd = d;
	                 Bestid = id;
	              }
//				  System.out.println("Bestd="+Bestd+"  Bestid="+Bestid);

			          //  Alter is set true if we are going to drop a condition
		        	  //    (either because we get lower pessimistic est, or
			          //    because one of the conditions fails a significance test)

		            if ( Pessimistic[d] <= Pessimistic[0] ||
	                   Actual[d] <= Actual[0]  ||
	                   SIGTEST && CondSigLevel[d] > SIGTHRESH )
	              {
	                   Alter = true;
	              }
		         }//if ( d==0 ).else
	    }//for(d=0;d<=NCond;d++)
//		System.out.println("pruning rule #6");
      if ( Alter )
      {
          if(VERBOSITY >=1) System.out.println("\teliminate test "+Bestid);
          Deleted[Bestd] = true;
          Remaining--;
       }
        
    } while ( Alter && Remaining>=0 );
//    System.out.println("pruning rule #8   Alter="+Alter+"      Remaining="+Remaining); 
    if (  Remaining==0 ||  Total[0]==0 )
    {
	       System.out.println("pruning rule #9   Total[0]="+Total[0]+"      Remaining="+Remaining);
		   return;
    }

    //if ( Pessimistic[0] >= DefaultError )
    //{
    // 	if(VERBOSITY >=1) System.out.println("\ttoo inaccurate\n");
    //  return;
    //}

    //  Sort the conditions
//    System.out.println("pruning rule #11"); 
    for(d=1;d<=Remaining;d++)//ForEach(d, 1, Remaining)
    {
      	dd =  0;
        for(id=d;id<=NCond;id++)//ForEach(id, d, NCond)
        {
        	    if ( ! Deleted[id] &&
                 (  dd==0 ||
                    Cond[id].CondTest.Tested < Cond[dd].CondTest.Tested ||//\
                    Cond[id].CondTest.NodeType < Cond[dd].CondTest.NodeType ||//\
                    Cond[id].CondTest.Tested == Cond[dd].CondTest.Tested &&
                    Cond[id].CondTest.Cut < Cond[dd].CondTest.Cut))
                    //Before(Cond[id].CondTest, Cond[dd].CondTest) ) )
              {
                	dd = id;
              }
	      }
        if ( dd != d )
        {
        	    Hold    = Cond[d];
              Cond[d] = Cond[dd];
              Cond[dd] = Hold;
              Deleted[dd] = Deleted[d];
        }
        Deleted[d] = true;
    }
//    System.out.println("pruning rule #12    NewRule   Remaining="+Remaining
//	                    +"  TargetClass="+TargetClass+"    Pessimistic[0]="+Pessimistic[0]); 
    NewRule(Cond, Remaining, TargetClass, Pessimistic[0]);
//	System.out.println("pruning rule #13"); 
}



/////////////////////////////////////////////////////////////////////////
//
//  See whether condition R is redundant
//
/////////////////////////////////////////////////////////////////////////


boolean Redundant(int R, XMCCondition Cond[],int NCond)
//      ---------
//    XMCCondition Cond[];
//    short R, NCond;
{
    int d, v, vv;
    XMCTest t, Rt;
    //Boolean IsSubset();

    Rt = Cond[R].CondTest;
    v =  Cond[R].TestValue;

    for(d=1;d<=NCond;d++)//ForEach(d, 1, NCond)
    {
	      if ( Deleted[d] || d == R ) continue;
        t = Cond[d].CondTest;
        vv = Cond[d].TestValue;

       	if ( t.Tested != Rt.Tested ) continue;

        switch ( t.NodeType )
        {
            case BrDiscr:  // test of discrete attribute
                 return false;

	          case ThreshContin:  // test of continuous attribute

               if ( vv == v &&
		               ( v == 1 ? t.Cut < Rt.Cut : t.Cut > Rt.Cut ) )
                   return true;

               break;

	           case BrSubset:  // subset test on discrete attribute
                if ( IsSubset(t.Subset[vv], Rt.Subset[v], Rt.Tested) )
                      return true;
	      }
    }

    return false;
}



/////////////////////////////////////////////////////////////////////////
//
//  Decide whether subset S1 of values is contained in subset S2
//
/////////////////////////////////////////////////////////////////////////


boolean IsSubset(char[] S1,char[] S2,int Att)
//      --------
//    Set S1, S2;
//    int Att;
{
    int v;

    for(v=1;v<=MaxAttVal[Att];v++)//ForEach(v, 1, MaxAttVal[Att])
    {
         //#define	 Bit(b)			(1 << (b))
         //#define	 In(b,s)		((s[(b) >> 3]) & Bit((b) & 07))

	       if ( ((S1[(v) >> 3]) &(1 << (v & 07)))>0
               && ((S2[(v) >> 3]) &(1 << (v & 07)))==0 ) return false;
    }
    return true;
}



/////////////////////////////////////////////////////////////////////////
//
//  Find the frequency distribution tables for the current conditions:
//
//	Total[0] = items matching all conditions
//	Total[d] = items matching all except condition d
//
//	Errors[0] = wrong-class items matching all conditions
//	Errors[d] = wrong-class items matching all but cond d
//
//  This routine is critical to the efficiency of rule pruning. It
//  computes the information above in one pass through the data,
//  looking at cases that fail to satisfy at most one of the
//  non-deleted conditions
//
/////////////////////////////////////////////////////////////////////////


void    FindTables(int NCond,int TargetClass)
//  -----------
//    short NCond;
//    int TargetClass;
{
    int i,Misses,d;
    int  Missed[] ;
    Missed=new int[2];
    boolean CorrectClass, First=true;
    int Att;
	int m_iCondSatisfiedBy=0;

    //  Clear distributions
//    System.out.println("FindTables   NCond="+NCond+"    TargetClass");
    for(d=0;d<=NCond;d++)//ForEach(d, 0, NCond)
    {
	        Total[d] = Errors[d] = 0;
    }
//    System.out.println("\n\n\n\n");
 //   for(d=0;d<=NCond;d++)//ForEach(d, 1, NCond)
//    	System.out.println("Total["+d+"]="+Total[d]+"  Errors["+d+"]="+Errors[d]);
    //  Set distributions

    for(i=0;i<MaxItem;i++)//ForEach(i, 0, MaxItem)
    {
          Misses = 0;
          //IsTarget(case)  (Class(case) == TargetClass ? 1 : 0
          if(GetClassValue(i)== TargetClass)      CorrectClass = true;
          else CorrectClass = false;
          //System.out.println("GetClassValue("+i+")="+GetClassValue(i)+"   TargetClass="+TargetClass+"    CorrectClass="+CorrectClass);
          for ( d = 1 ; d <= NCond && Misses <= 1 ; d++ )
          {
		     m_iCondSatisfiedBy=m_fCondSatisfiedBy_Manager.getInteger(i+1,d+1);
             if ( ! Deleted[d] && m_iCondSatisfiedBy==0)//CondSatisfiedBy[d][i]==0 )
             {
                 //System.out.println("i="+i+"   Deleted["+d+"]="+Deleted[d]+"    m_fCondSatisfiedBy_Manager.getInteger(i+1,d+1)=0");
				 //System.out.println("CondSatisfiedBy["+d+"]["+i+"]="+m_iCondSatisfiedBy);
				 Missed[Misses++] = d;
             }
	      }//for ( d = 1 ; d <= NCond && Misses <= 1 ; d++ )

          if (  Misses==0 )
          {
              //System.out.println("Misses==0");
			  UpdateCount(Total, Errors, 0, CorrectClass);
          }
          else if ( Misses == 1 )
          {      
          	    //System.out.println("Misses==1");
				UpdateCount(Total, Errors, Missed[0], CorrectClass);
          }
          //System.out.println("Total["+Misses+"]="+Total[Misses]);
   }//for(i=0;i<MaxItem;i++)

    // Adjust counts to reflect cases that met all conditions
   
    
	for(d=1;d<=NCond;d++)//ForEach(d, 1, NCond)
    {
	     if ( ! Deleted[d] )
       {
           Total[d] += Total[0];
           Errors[d] += Errors[0];
		   //System.out.println("Total["+d+"]="+Total[d]+"  Errors["+d+"]="+Errors[d]);
		   
       }
    }

}



/////////////////////////////////////////////////////////////////////////
//
//  Increment the counts Total[d] and Errors[d]
//
/////////////////////////////////////////////////////////////////////////


void    UpdateCount(int[] T,int[] E,int d, boolean OK)
//  -----------
//    int T[], E[];
//    short d;
//    Boolean OK;
{
    T[d]++;
    if ( ! OK ) E[d]++;
}


/* 
/////////////////////////////////////////////////////////////////////////
//
//  Determine whether the given case description satisfies the given
//  condition. To save time, the outcome of every test on every item
//  is set the first time it is needed and retained for later use
//
/////////////////////////////////////////////////////////////////////////


boolean Satisfies(int CaseDesc,XMCCondition OneCond)
//    Description CaseDesc;
//    XMCCondition OneCond;
{
    int v;
    double cv;
    XMCTest t;
    int s;

    t = OneCond.CondTest;

    if (  t.Outcome!=0 )
    {
      	//  Determine the outcome of this test on this item

        switch ( t.NodeType )
        {
            case BrDiscr:  // test of discrete attribute
               v = GetIntegerValue(CaseDesc, t.Tested);
		           
				   t.Outcome = v;//( v == 0 ? -1 : v );
               break;

	          case ThreshContin:  // test of continuous attribute

		           cv = GetDoubleValue(CaseDesc, t.Tested);
		           //t.Outcome = ( cv == Unknown ? -1 : cv <= t->Cut ? 1 : 2 );
				   if(cv <= t.Cut)t.Outcome=1;
				   else t.Outcome=2;
		           break;

	           case BrSubset:  // subset test on discrete attribute

		           v = GetIntegerValue(CaseDesc, t.Tested);
		           t.Outcome = -1;
		           for(s=1;s<=t.Forks;s++)//ForEach(s, 1, t->Forks)
				   {
		                  if ( ((t.Subset[s][v >> 3]) &(1 << (v & 07)))>0 )//In(v, t->Subset[s]) )
                          {
                      		   t.Outcome = s;
                               break;
		                  }
		            }
	       }//switch ( t.NodeType )
    }//    if (  t.Outcome!=0 )

    return ( t.Outcome == OneCond.TestValue );
} 


*/
/////////////////////////////////////////////////////////////////////////
//									 
//  Hypergeometric distribution	(uses tabulated log factorials)		 
//									 
/////////////////////////////////////////////////////////////////////////


double Hypergeom(int a,int r,int A,int B)
//               ---------
//    int a, r, A, B;
{
    return java.lang.Math.exp(
                             m_fLogItemNo_LogFact_Manager.getDouble(A+1,C_LOGFACT)//LogFact[A]
                             + m_fLogItemNo_LogFact_Manager.getDouble(B+1,C_LOGFACT)//LogFact[B]
                             + m_fLogItemNo_LogFact_Manager.getDouble(r+1,C_LOGFACT)//LogFact[r]
                             + m_fLogItemNo_LogFact_Manager.getDouble(A+B-r+1,C_LOGFACT)//LogFact[A+B-r] -
	                           - ( m_fLogItemNo_LogFact_Manager.getDouble(a+1,C_LOGFACT)//LogFact[a]
                                 + m_fLogItemNo_LogFact_Manager.getDouble(r-a+1,C_LOGFACT)//LogFact[r-a]
                                 + m_fLogItemNo_LogFact_Manager.getDouble(A-a+1,C_LOGFACT)//LogFact[A-a]
		                             + m_fLogItemNo_LogFact_Manager.getDouble(B-(r-a)+1,C_LOGFACT)//LogFact[B-(r-a)]
                                 + m_fLogItemNo_LogFact_Manager.getDouble(A+B+1,C_LOGFACT)
                                )
                              );   //LogFact[A+B]) );
}



/////////////////////////////////////////////////////////////////////////
//
//  TableProb examines the 2x2 contingency table t and computes the
//  probability that a random division could have produced a split at
//  least as extreme as this.  Also known as "Fisher's Exact Test",
//  after its inventor, R.A. Fisher.
//
/////////////////////////////////////////////////////////////////////////


double TableProb(int t11,int t12,int t21,int t22)
{
    double Sum=0.0;
    int A, B, r, a, k, a0;

    //  First, rearrange the rows and columns of the table to get it into
	  //  canonical form

    if ( t11 + t12 > t21 + t22 )
    {
      	A = t11 + t12;
        B = t21 + t22;

        if ( t11 * (t21 + t22) > t21 * (t11 + t12) )
        {
        	    a0 = t11;
              r  = t11 + t21;
        }
        else
        {
              a0 = t12;
              r  = t12 + t22;
        }
    }
    else
    {
       	A = t21 + t22;
	      B = t11 + t12;

      	if ( t21 * (t11 + t12) > t11 * (t21 + t22) )
        {
        	    a0 = t21;
              r  = t21 + t11;
	      }
	      else
	      {
	          a0 = t22;
	          r  = t22 + t12;
	      }
    }

    //  Now compute the probability

    k =( r<A ? r : A);// Min(r, A);
    for (a=a0;a<=k;a++)//ForEach(a, a0, k)
    {
	      Sum += Hypergeom(a, r, A, B);
    }

    return Sum;
}
/////////////////////////////////////////////////////////////////////////
//
//  End of Class
//
/////////////////////////////////////////////////////////////////////////
}


