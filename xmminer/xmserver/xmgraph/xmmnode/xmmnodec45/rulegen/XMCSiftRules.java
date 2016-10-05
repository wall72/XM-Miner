
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
import java.lang.Math.*;


public class XMCSiftRules extends XMCRules
{
//
//
//siftrules.c
//
//

private int	 ClassFreq_rule[],	 // ClassFreq[c]	= no. items of class c
             //Covered[],	   // Covered[i]	= no. included rules that cover item i
	           FalsePos[],	 // FalsePos[c]	= no. false positives from rules
					                 //selected for class c
	           NoRule[],	     // NoRule[c]	= no. items covered by no selected rule

	           Right[];		   // Right[r]	= no. correct rule firings
private CXMWeightTableSaver m_fCovered_Saver=null;
private CXMWeightTableManager m_fCovered_Manager=null;

private int	 m_iWrong[];		   // m_iWrong[r]	= no. incorrect rule firings

private double	Value[],		// Value[r]	= advantage attributable to rule r or
					          //realisable if rule r included
	     SubsetValue,	// value of best class subset so far
	     CodeWeight;	// multiplying factor for rule encodings

private boolean	RuleIn[],	   // RuleIn[r]	= true if rule r included
	      Subset_rule[];	// best class subset so far
	      //Match[][];	   // Match[r][i]	= true if rule r fires on item i
private CXMWeightTableSaver m_fMatch_Saver=null;
private CXMWeightTableManager m_fMatch_Manager=null;
private int	    ClassRules[];	  // list of all rules for current target class

////////////////////////////////////////////////////////////////////////////////
//
// 생성자
// public XMCSiftRules()
// public XMCSiftRules(String ProjectName, String Modelname)
// 
////////////////////////////////////////////////////////////////////////////////
  public XMCSiftRules()
  {

  }
  
  public XMCSiftRules(String ProjectName, String Modelname)
  {
        m_sProjectName = ProjectName;
        m_sModelName= Modelname;
  }


/////////////////////////////////////////////////////////////////////////
//
//  Construct an ordered subset (indexed by RuleIndex) of the current
//  set of rules
//
/////////////////////////////////////////////////////////////////////////
public void    ConstructRuleset()
//  ----------------
{
    int r, OldNRules = NRules;
    int i;
    int c;

    //  Allocate tables
    //System.out.println("ConstructRuleset #1");
    Right = new int[NRules+1];//Right = (int *) calloc(NRules+1, sizeof(int));
    m_iWrong =new int[NRules+1];//m_iWrong = (int *) calloc(NRules+1, sizeof(int));

    Value =new  double [NRules+1];

    RuleIn = new boolean[NRules+1];//RuleIn = (Boolean *) calloc(NRules+1, sizeof(Boolean));
    Subset_rule = new boolean[NRules+1];//Subset_rule = (Boolean *) malloc((NRules+1) * sizeof(Boolean));

    ClassRules = new int[NRules+1];//ClassRules = (int *) malloc((NRules+1) * sizeof(int));

    ClassFreq_rule = new int[MaxClass+1];//ClassFreq = (int *) calloc(MaxClass+1, sizeof(int));

    //Covered= new int[MaxItem+1];//Covered = (int *) calloc(MaxItem+1, sizeof(int));
	//System.out.println("ConstructRuleset #2");
    String m_sCovered=new String(m_sModelName+"_Covered");
    int[]   m_iColIndex_covered;
    m_iColIndex_covered=new int[1];
    m_iColIndex_covered[0]=4;
    //System.out.println("ConstructRuleset #3");
   	m_fCovered_Saver=new CXMWeightTableSaver();
    m_fCovered_Saver.setFileStatus(m_sProjectName,m_sCovered);
    m_fCovered_Saver.createWeightTable(m_iColIndex_covered, MaxItem+1);
    m_fCovered_Saver.close();
 
    m_fCovered_Manager = new CXMWeightTableManager();
    m_fCovered_Manager.setFileStatus(m_sProjectName, m_sCovered, m_iColIndex_covered, MaxItem+1);
    //System.out.println("ConstructRuleset #4");





    // Match = new boolean[NRules+1];//Match = (Boolean **) calloc(NRules+1, sizeof(Boolean *));
    String m_sMatch=new String(m_sModelName+"_Match");
    int[]   m_iColIndex;
    m_iColIndex=new int[NRules+1];
    for(i=0; i<NRules+1;i++)
    {
         m_iColIndex[i]=4;
    }
	//System.out.println("ConstructRuleset #5");
	m_fMatch_Saver=new CXMWeightTableSaver();
    m_fMatch_Saver.setFileStatus(m_sProjectName,m_sMatch);
    m_fMatch_Saver.createWeightTable(m_iColIndex, MaxItem+1);
    m_fMatch_Saver.close();
    m_iPbCount_num++;
    m_fMatch_Manager = new CXMWeightTableManager();
    m_fMatch_Manager.setFileStatus(m_sProjectName, m_sMatch, m_iColIndex, MaxItem+1);
    //System.out.println("ConstructRuleset #6");
 
  
    FalsePos= new int[MaxClass+1];//FalsePos = (int *) calloc(MaxClass+1, sizeof(int));

    NoRule= new int[MaxClass+1];//NoRule = (int *) calloc(MaxClass+1, sizeof(int));
    //System.out.println("ConstructRuleset #7");
    //for(r=1;r<=NRules;r++)//ForEach(r, 1, NRules)
    //{
	//     Match[r] =new boolean[MaxItem+1];//Match[r] = (Boolean *) calloc(MaxItem+1, sizeof(Boolean));
    //}

    //  Cover each class, then order the classes to give an index of rules
    //System.out.println("ConstructRuleset #8");
    InitialiseTables();
    
    FindRuleCodes();
	
    CodeWeight = 0.5;
    
    //for(c=1;c<=MaxClass;c++)//ForEach(c, 0, MaxClass)
    //{
	//     CoverClass(c);
    //}
//    System.out.println("ConstructRuleset #12");
    MakeIndex();
//	System.out.println("ConstructRuleset #13");
    FindDefault();
//    System.out.println("ConstructRuleset #14");
    //  Clear

   // free(Value);    free(RuleIn);    free(ClassRules);    free(Subset_rule;    free(Covered);
   // free(FalsePos);    free(NoRule);
   // ForEach(r, 1, OldNRules)	free(Match[r]);    free(Match);
}



/////////////////////////////////////////////////////////////////////////
//
//		Initialise all tables used in sifting
//
/////////////////////////////////////////////////////////////////////////


public void    InitialiseTables()
//  ----------------
{
    int i;
    int r;
    int c;
    //double Strength();
//    System.out.println("InitialiseTables#1  NRules="+NRules);
    for (r=1;r<=NRules;r++)//ForEach(r, 1, NRules)
    {
	     RuleIn[r] = true;//true;//false;
	     Rule[r].Used = Rule[r].Incorrect = 0;
    }
//    System.out.println("InitialiseTables#2");
    for (c=1;c<=MaxClass;c++)//ForEach(c, 0, MaxClass)
    {
      	ClassFreq_rule[c] = 0;
    }
//	System.out.println("InitialiseTables#3");
    int temp_match=0;
    for (i=0;i<=MaxItem;i++)//ForEach(i, 0, MaxItem)
    {
     	  ClassFreq_rule[GetClassValue(i)]++; //GetClassValue(int m_iRow)

	      ClearOutcomes();

  	    for (r=1;r<=NRules;r++)//ForEach(r, 1, NRules)
	      {
	         if(Strength(Rule[r], i) > 0.3) temp_match=1; //Match[r][i] = Strength(Rule[r], i) > 0.3;
           else temp_match=0;
           m_fMatch_Manager.setInteger(i+1,r+1,temp_match);
	          if ( temp_match > 0)
	          {
		          Rule[r].Used++;
		          if ( Rule[r].Rhs!=GetClassValue(i) ) Rule[r].Incorrect++;
	          }
	      }//for (r=1;r<=NRules;r++)
    }//for (i=0;i<=MaxItem;i++)
//	System.out.println("InitialiseTables#4");
}



/////////////////////////////////////////////////////////////////////////
//
//	Select a subset of the rules for class FocusClass
//
/////////////////////////////////////////////////////////////////////////


public void    CoverClass(int FocusClass)
//  ----------
//    int FocusClass;
{
    int r, RuleCount=0;
    int i;
//    System.out.println("CoverClass#1  NRules="+NRules);
    if(VERBOSITY>=1)
//	   System.out.println("\nClass"+GetClassValName(FocusClass));//ClassName[FocusClass]+" \n-----\nAction  Change  Value");

    for(i=0;i<=MaxItem;i++)//ForEach(i, 0, MaxItem)
    {
       m_fCovered_Manager.setInteger(i+1,1,0);//[i] = 0;
    }

    for(r=1;r<=NRules;r++)//ForEach(r, 1, NRules)
    {
	     if ( Rule[r].Rhs == FocusClass )
       {
       	    RuleCount++;
            ClassRules[RuleCount] = r;
       }
    }
//    System.out.println(" RuleCount="+RuleCount+"   NRules="+NRules);
    if ( RuleCount==0 )//if ( ! RuleCount )
    {
	       return;
    }
    m_iPbCount_num++;
    SubsetValue = 10000000;

    if ( RuleCount <= 10 )
    {
	       AllCombinations(RuleCount, FocusClass);
    }
    else
    {
	       SimAnneal(RuleCount, FocusClass);
    }

    //memcpy(RuleIn, Subset_rule, NRules+1);
    System.arraycopy(Subset_rule,0, RuleIn,0, NRules+1);
//	for(i=0;i<=NRules;i++)
//			  System.out.println("RuleIn["+i+"]="+RuleIn[i]+"  Subset_rule["+i+"]="+Subset_rule[i]);
    if(VERBOSITY>=1) System.out.println("\n\tBest value "+ SubsetValue);
}


 
/////////////////////////////////////////////////////////////////////////
//									 
//    Try all combinations of rules to find best value			 
//									 
/////////////////////////////////////////////////////////////////////////


public  void    AllCombinations(int NR,int FocusClass)
//  ---------------  
//    int NR;
//    int FocusClass;
{
    double ThisValue;//, CalculateValue();
    int r;
	int i=0;
    
//	System.out.println("AllCombinations#1   NR="+NR);
    if (  NR==0 )//if (  !NR )
    {
	    ThisValue = CalculateValue(FocusClass);
//		System.out.println("ThisValue="+ThisValue+"    SubsetValue="+SubsetValue);
      	if ( ThisValue < SubsetValue )
        {
	        SubsetValue = ThisValue;
	        System.arraycopy( RuleIn,0, Subset_rule,0,NRules+1);
//			for(i=0;i<=NRules;i++)
//			  System.out.println("RuleIn["+i+"]="+RuleIn[i]+"  Subset_rule["+i+"]="+Subset_rule[i]);
        }
    }//if (  NR==0 )
    else
    {
	    r = ClassRules[NR];
        AllCombinations(NR-1, FocusClass);
        AddRule(r);
//		for(i=0;i<=NRules;i++)
//			  System.out.println("RuleIn["+i+"]="+RuleIn[i]+"  Subset_rule["+i+"]="+Subset_rule[i]);
        AllCombinations(NR-1, FocusClass);
		
        DeleteRule(r);
//		for(i=0;i<=NRules;i++)
//			  System.out.println("RuleIn["+i+"]="+RuleIn[i]+"  Subset_rule["+i+"]="+Subset_rule[i]);
		//AddRule(r);
//        if(VERBOSITY>=1) System.out.println("\n");
    }
}



/////////////////////////////////////////////////////////////////////////
//
//  Find a good subset by simulated annealing
//
/////////////////////////////////////////////////////////////////////////


public void    SimAnneal(int RuleCount, int FocusClass)
//  ---------
//    int RuleCount;
//    int FocusClass;
{
    int r, OutCount;
    int i;
    int ri, Tries;
    double Temp, Delta, ThisValue;//, CalculateValue();
    boolean m_bChanged;

    //  Keep dropping and adding rules until can't improve

    for ( Temp = 1000 ; Temp > 0.001 ; Temp *= 0.95 )
    {
       ThisValue = CalculateValue(FocusClass);
       if ( ThisValue < SubsetValue )
       {
       	    SubsetValue = ThisValue;
            System.arraycopy(RuleIn,0,Subset_rule,0, NRules+1);
       }
       if(VERBOSITY>2)
       {
	         OutCount = 0;
           for(ri=1;ri<=RuleCount;ri++)//ForEach(ri, 1, RuleCount)
           {
           	  r = ClassRules[ri];
              if ( !RuleIn[r])//if ( ! RuleIn[r] )
              {
                 if ( (OutCount++ % 3)==0 ) System.out.println("\n\t\t");
                 System.out.println(r+"<"+Right[r]+"|"+m_iWrong[r]+"="+Value[r]+">");
             	}
            }

	         System.out.println("\n\n");
	      }//if(VERBOSITY>2)

	      m_bChanged = false;
        double Random=0.0;
	      for ( Tries = 100 ; ! m_bChanged && Tries > 0 ; Tries-- )
	      {
	          //  Choose a rule to add or delete
            Random=((int)java.lang.Math.random()&2147483647) / 2147483648.0;
	          ri = (int) (RuleCount * Random + 1);

	          r = ClassRules[ri];

	          Delta = ( RuleIn[r] ? -Value[r] : Value[r] );

            Random=((int)java.lang.Math.random()&2147483647) / 2147483648.0;
	          if ( Delta > 0 ||   Random < java.lang.Math.exp(Delta / Temp) )
            {
                if ( RuleIn[r] )
                {
                    DeleteRule(r);
                }
                else
                {
                   AddRule(r);
                }
                m_bChanged = true;
            }//if ( Delta > 0 ||   Random < java.lang.Math.exp(Delta / Temp) )
          }//for ( Tries = 100 ; ! m_bChanged && Tries > 0 ; Tries-- )

	      if ( ! m_bChanged ) break;
    }//for ( Temp = 1000 ; Temp > 0.001 ; Temp *= 0.95 )
}



/////////////////////////////////////////////////////////////////////////
//
//  Find the number of correct and incorrect rule firings for rules
//  for class FocusClass and hence determine the Value of the rules
//
/////////////////////////////////////////////////////////////////////////


public double CalculateValue(int FocusClass)
//    --------------
//    int FocusClass;
{
    int r, Selected=0, InCount=0;
    int i, Times, FPos=0, FNeg=0, SumUsed=0, SumCover=0;
    double SumErrors=0.0, BaseBits, RuleBits=0, NewBits;//, ExceptionBits();
    int ThisClass;

    //  Initialise Right and m_iWrong
    for(r=1;r<=NRules;r++)//ForEach(r, 1, NRules)
    {
      	if ( Rule[r].Rhs == FocusClass )
        {
            Right[r] = m_iWrong[r] = 0;
            if ( RuleIn[r] )
            {
            	SumUsed += Rule[r].Used;
                SumErrors += Rule[r].Error * Rule[r].Used;
                RuleBits += Rule[r].Bits;
                Selected++;
             }
         }//if ( Rule[r].Rhs == FocusClass )
    }//for(r=1;r<=NRules;r++)//ForEach(r, 1, NRules)
//    System.out.println("SumUsed="+SumUsed+"   SumErrors="+SumErrors+"   RuleBits="+RuleBits+" Selected="  +Selected);
    RuleBits -= m_fLogItemNo_LogFact_Manager.getDouble(Selected+1,C_LOGFACT);//LogFact[Selected];	// allow for reordering of rules
//    System.out.println("   RuleBits="+RuleBits);
    //  For each rule for the FocusClass, set Right to the number
	  //  of items that correctly fire the rule and aren't covered by
	  //  any other included rules, and m_iWrong to the number of items
	  //  that incorrectly do so.

    for(i=0;i<=MaxItem;i++)//ForEach(i, 0, MaxItem)
    {
      	ThisClass = GetClassValue(i);

        Times = m_fCovered_Manager.getInteger(i+1,1);
		//System.out.println("   Times="+Times+"   i="+i);
        if (Times>0)//Covered[i]
        {
            SumCover++;
            if( ThisClass != FocusClass ) FPos++;
        }
        else if ( ThisClass == FocusClass )
        {
            FNeg++;
        }
        for (r=1;r<=NRules;r++)//ForEach(r, 1, NRules)
        {
        	   if ( Rule[r].Rhs == FocusClass &&	m_fMatch_Manager.getInteger(i+1,r+1)>0 &&// Match[r][i]
              		 (  Times==0 || Times == 1 && RuleIn[r] ) )
              {
                	if ( ThisClass == FocusClass )
                  {
                  	    Right[r]++;
                  }
                  else
                  {
                  	    m_iWrong[r]++;
                  }
               }
         }//for (r=1;r<=NRules;r++)
    }//for(i=0;i<=MaxItem;i++)
//    System.out.println("   RuleBits="+RuleBits+"   FPos="+FPos+"   FNeg="+FNeg+"   SumCover="+SumCover);
    BaseBits = CodeWeight * RuleBits + ExceptionBits(SumCover, FPos, FNeg);
//    System.out.println("   RuleBits="+RuleBits+"   BaseBits="+BaseBits);
    //  From the Right and m_iWrong of each rule, calculate its Value

    if (VERBOSITY>=1)
    {
        System.out.println("\t");
    	  InCount = -1;
    }

    for(r=1;r<=NRules;r++)//ForEach(r, 1, NRules)
    {
       if ( Rule[r].Rhs == FocusClass )
       {
          if ( RuleIn[r] )
          {
            	NewBits = ExceptionBits(SumCover-Right[r]-m_iWrong[r],
                            	        FPos-m_iWrong[r],
                                      FNeg+Right[r]) + CodeWeight *
                                      (RuleBits - Rule[r].Bits +
                                      m_fLogItemNo_LogFact_Manager.getDouble(Selected+1,C_LOGITEMNO));//LogItemNo[Selected]);
		          Value[r] = NewBits - BaseBits;
          }
          else
          {
            	NewBits = ExceptionBits(SumCover+Right[r]+m_iWrong[r],
                                      FPos+m_iWrong[r],
                                      FNeg-Right[r]) + CodeWeight *
                                      (RuleBits + Rule[r].Bits -
                                      m_fLogItemNo_LogFact_Manager.getDouble(Selected+2,C_LOGITEMNO));//LogItemNo[Selected+1]);
              Value[r] = BaseBits - NewBits;
           }
           if(VERBOSITY>=1)
           {
                  if ( RuleIn[r] )
                  {
                      if ( ++InCount>0 && (InCount % 3)==0 ) System.out.println("\n\t\t");
                      System.out.println(r+"["+Right[r]+"|"+m_iWrong[r]+"="+Value[r]+"]  ");
                   }
            }
       }//if ( Rule[r].Rhs == FocusClass )
    }//for(r=1,r<=NRules,r++)

    if(VERBOSITY>=1)
    {
    	System.out.println("\t\t"+Selected+" rules,"+ SumCover +" firings: F+="
                       +FPos+", F-="+FNeg+", "+BaseBits+" bits (rules="+RuleBits+")\n");
    }

    return BaseBits;
}



/////////////////////////////////////////////////////////////////////////
//
//  Add rule r to the set of included rules and increase the number of
//  rules covering each of the items that fire the rule
//
/////////////////////////////////////////////////////////////////////////
void    AddRule(int r)
//  -------
//    int r;
{
    int i,temp_Coverd;

    RuleIn[r] = true;
    for(i=0;i<=MaxItem;i++)//ForEach(i, 0, MaxItem)
    {

        if ( m_fMatch_Manager.getInteger(i+1,r+1)>0 )//Match[r][i]
        {
          temp_Coverd = m_fCovered_Manager.getInteger(i+1,1)+1;  //[i]++;
          m_fCovered_Manager.setInteger(i+1,1,temp_Coverd);
        }
    }

    if(VERBOSITY>=1) System.out.println(r +" "+  Value[r]);
}

/////////////////////////////////////////////////////////////////////////
//
//  Delete rule r from the included rules and decrease the number of
//  rules covering each of the items covered by the rule
//
/////////////////////////////////////////////////////////////////////////

public void    DeleteRule(int r)
//  ----------
//    int r;
{
    int i,temp_Coverd;

    RuleIn[r] = false;

    for(i=0;i<=MaxItem;i++)//ForEach(i, 0, MaxItem)
    {
	     if ( m_fMatch_Manager.getInteger(i+1,r+1)>0 )//Match[r][i]
       {
           temp_Coverd = m_fCovered_Manager.getInteger(i+1,1)-1;  //[i]++;
           m_fCovered_Manager.setInteger(i+1,1,temp_Coverd);//         Covered[i]--;
       }
    }

    if(VERBOSITY>=1) System.out.println(r +" "+  Value[r]);
}

/////////////////////////////////////////////////////////////////////////
//
//  Make an index of included rules in RuleIndex.  Select first those
//  classes whose rules have the fewest false positives.  Within a
//  class, put rules with higher accuracy ahead.
//
/////////////////////////////////////////////////////////////////////////


public void    MakeIndex()
//  ---------
{
    int c, BestC, Pass,temp_Coverd;
    int r, BestR, NewNRules = 0;
    int i;
    boolean Included[];

    Included = new boolean [MaxClass+1];
    RuleIndex = new int[NRules+1]; //calloc(NRules+1, sizeof(int));
    for(i=1;i<=MaxClass;i++) Included[i]=false;
    if(VERBOSITY>=1) System.out.println("\nFalsePos  Class\n");
    m_iPbCount_num++;
    //System.out.println("MakeIndex#1");
    for(i=0;i<=MaxItem;i++)//ForEach(i, 0, MaxItem)
    {
	       m_fCovered_Manager.setInteger(i+1,1,0);//Covered[i] = 0;
    }
    //System.out.println("MakeIndex#2");
    //  Select the best class to put next

    for(Pass=1;Pass<=MaxClass;Pass++)//ForEach(Pass, 0, MaxClass)
    {
      for(c=1;c<=MaxClass;c++)//ForEach(c, 0, MaxClass)
      {
         if ( Included[c] ) continue;
         FalsePos[c] = 0;
         for(i=0;i<=MaxItem;i++)//ForEach(i, 0, MaxItem)
         {
            if ( m_fCovered_Manager.getInteger(i+1,1)>0 || c ==GetClassValue(i) ) continue;
            for(r=1;r<=NRules;r++)//ForEach(r, 1, NRules)
            {
                if ( Rule[r].Rhs == c && RuleIn[r] && m_fMatch_Manager.getInteger(i+1,r+1)>0)//Match[r][i]
                {
                     FalsePos[c]++;
                     break;
                }
            }//for(r=1;r<=NRules;r++)
         }//for(i=0;i<=MaxItem;i++)
      }//for(c=0;c<=MaxClass;c++)
      //System.out.println("MakeIndex#3");
      BestC = -1;
      for(c=1;c<=MaxClass;c++)// ForEach(c, 0, MaxClass)
      {
      	    if ( ! Included[c] &&
	                 ( BestC < 0 || FalsePos[c] < FalsePos[BestC] ) )
            {
               BestC = c;
            }
       }
       Included[BestC] = true;
       //System.out.println("BestC="+BestC+"    "+" MaxClass    "+MaxClass);//ClassName[BestC]);
       if(VERBOSITY >=1  && BestC>=0 && BestC<=MaxClass)
           System.out.println("BestC="+BestC+"    "+FalsePos[BestC]+"     "+GetClassValName(BestC));//ClassName[BestC]);

	     //  Now grab the rules for this class
//       System.out.println("MakeIndex#4");
       do
       {
	         BestR = 0;
           //  Find the best rule to put next
           for(r=1;r<=NRules;r++)//ForEach(r, 1, NRules)
           {
//                System.out.println("RuleIn["+r+"]="+RuleIn[r]+"     Rule["+r+"].Rhs="+Rule[r].Rhs+"BestC"+BestC);
//				System.out.println("Rule["+r+"].Error="+Rule[r].Error+"     Rule["+BestR+"].Error="+Rule[BestR].Error);
				if ( RuleIn[r] && Rule[r].Rhs == BestC &&
                   ( BestR==0 || Rule[r].Error < Rule[BestR].Error ) )
                {
                    BestR = r;
                }
//                System.out.println("BestR="+BestR+"    "+" MaxClass    "+MaxClass);//ClassName[BestC]);
           }//for(r=1;r<=NRules;r++)
           if ( BestR >0)
           {
              	RuleIndex[++NewNRules] = BestR;
                RuleIn[BestR] = false;
                for(i=0;i<=MaxItem;i++)//ForEach(i, 0, MaxItem)
                {
                        temp_Coverd= m_fCovered_Manager.getInteger(i+1,1);
                        temp_Coverd |= m_fMatch_Manager.getInteger(i+1,BestR+1);//Match[BestR][i];
                		m_fCovered_Manager.setInteger(i+1,1,temp_Coverd);//[i]
                }
            }
         } while ( BestR!=0 );
//		 System.out.println("MakeIndex#5");
    }//for(Pass=0;Pass<=MaxClass;Pass++)
    NRules = NewNRules;
	m_iPbCount_num++;
//	System.out.println("NRules="+NRules);
    //free(Included);
}

/////////////////////////////////////////////////////////////////////////
//
//  Find the default class as the one with most items not covered by
//  any rule.  Resolve ties in favour of more frequent classes.
//  (Note: Covered has been set by MakeIndex.)
//
/////////////////////////////////////////////////////////////////////////
public  void    FindDefault()
//  -----------
{
    int c;
    int i;
    int r;

    //  Determine uncovered items

    for(c=0;c<MaxClass;c++)//ForEach(c, 0, MaxClass)
    {
 	       NoRule[c] = 0;
    }

    for(i=0;i<=MaxItem;i++)//ForEach(i, 0, MaxItem)
    {
       if (  m_fCovered_Manager.getInteger(i+1,1)==0 )
       {
           NoRule[GetClassValue(i)]++;
       }
    }

    if(VERBOSITY>=1)
    {
         System.out.println("\nItems: Uncovered   Class\n");
         for(c=1;c<=MaxClass;c++)
         {
             System.out.println(ClassFreq_rule[c] + "  "+ NoRule[c]+"     "+GetClassValName(c));//+ClassName[c]);
         }
         System.out.println("\n");
    }
    DefaultClass = 0;
    for(c=1;c<=MaxClass;c++)//ForEach(c, 1, MaxClass)
    {
       if ( NoRule[c] > NoRule[DefaultClass] ||  NoRule[c] == NoRule[DefaultClass] &&
	          ClassFreq_rule[c] > ClassFreq_rule[DefaultClass] )
       {
          DefaultClass = c;
       }
    }
}



/////////////////////////////////////////////////////////////////////////
//
//  Given a rule and a case (ClearOutcomes performed), determine
//  the strength with which we can conclude that the case belongs
//  to the class specified by the rule's right-hand side.
//
//  If the case doesn't satisfy all the conditions of the rule,
//  then this is 0.
//
/////////////////////////////////////////////////////////////////////////


public  double Strength(XMCPR ThisRule,int Case)
//    --------
//    XMCPR ThisRule;
//    Description Case;
{
    int d;
    

    if ( ThisRule.Error > 0.7 ) return 0.0;

    for (d=1; d<=ThisRule.Size;d++)// ForEach(d, 1, ThisRule.Size)
    {
           if ( ! Satisfies(Case, ThisRule.Lhs[d]) )
           {
               return 0.0;
           }
    }

    return ( 1 - ThisRule.Error );
}

/////////////////////////////////////////////////////////////////////////
//
//  Determine whether the given case description satisfies the given
//  condition. To save time, the outcome of every test on every item
//  is set the first time it is needed and retained for later use
//
/////////////////////////////////////////////////////////////////////////


public boolean Satisfies(int CaseDesc,XMCCondition OneCond)
{
    int v;
    double cv;
    XMCTest t;
    int s;

    t = OneCond.CondTest;

    //if ( t.Outcome == 0)
    {
 	      //Determine the outcome of this test on this item

        switch ( t.NodeType )
        {
            case BrDiscr:  // test of discrete attribute
             		v = GetIntegerValue(CaseDesc,t.Tested);//DVal(CaseDesc, t->Tested);
                    //System.out.println("Data["+CaseDesc+"]["+t.Tested+"]="+v);
					t.Outcome = ( v == 0 ? -1 : v );
                break;
            case ThreshContin:  // test of continuous attribute
                cv = GetDoubleValue(CaseDesc,t.Tested);//CVal(CaseDesc, t->Tested);
				//System.out.println("Data["+CaseDesc+"]["+t.Tested+"]="+cv);
                t.Outcome = ( cv == Unknown ? -1 : cv <= t.Cut ? 1 : 2 );
                break;
            case BrSubset:  // subset test on discrete attribute
                 v = GetIntegerValue(CaseDesc,t.Tested);//DVal(CaseDesc, t->Tested);
                 t.Outcome = -1;
                 for(s=1;s<=t.Forks;s++)//ForEach(s, 1, t->Forks)
                 {
                 //((SS[jj >> 3]) & (1 << (jj & 07)))!=0
                 //#define	 Bit(b)			(1 << (b))
                 //#define	 In(b,s)		((s[(b) >> 3]) & Bit((b) & 07))

                    if ( (t.Subset[s][v>>3] &(1<<(v&07)))!=0)//In(v, t->Subset[s]) )
                    {
                    		t.Outcome = s;
                        break;
                    }
                 }
         }
    }
    //System.out.println("t.Outcome="+t.Outcome+" OneCond.TestValue="+OneCond.TestValue);  
    return ( t.Outcome == OneCond.TestValue );
}


/////////////////////////////////////////////////////////////////////////
//
//  Determine the number of bits to encode exceptions: FP false +ves
//  out of Fires firings, and FN false negatives in the remaining items
//
/////////////////////////////////////////////////////////////////////////


//#define nCrBits(E,N)  (LogFact[N] - (LogFact[E] + LogFact[N-(E)]))


public double ExceptionBits(int Fires,int FP,int FN)
//    -------------
//    int Fires, FP, FN;
{
     double m_dA,m_dB;
     m_dA= m_fLogItemNo_LogFact_Manager.getDouble(Fires+1,C_LOGFACT)//LogFact[Fires]
           -( m_fLogItemNo_LogFact_Manager.getDouble(FP+1,C_LOGFACT) //LogFact[FP]
             + m_fLogItemNo_LogFact_Manager.getDouble(Fires-FP+1,C_LOGFACT)//LogFact[Fires-FP]
            ) ;
     m_dB = m_fLogItemNo_LogFact_Manager.getDouble(MaxItem+1-Fires+1,C_LOGFACT) //LogFact[MaxItem+1-Fires]
             - ( m_fLogItemNo_LogFact_Manager.getDouble(FN+1,C_LOGFACT)//LogFact[FN]
                + m_fLogItemNo_LogFact_Manager.getDouble(MaxItem+1-Fires-FN+1,C_LOGFACT));//LogFact[MaxItem+1-Fires-FN]

    return m_dA+m_dB; //nCrBits(FP, Fires) +  nCrBits(FN, MaxItem+1-Fires);
}



/////////////////////////////////////////////////////////////////////////
//
//  Find encoding lengths for all rules
//
/////////////////////////////////////////////////////////////////////////


public void    FindRuleCodes()
//  -------------
{
    int r;
    int d, NCond;
    double Bits;//, CondBits();
//     System.out.println("FindRuleCodes#1  NRules="+NRules);
    for(r=1;r<=NRules;r++)//ForEach(r, 1, NRules)
    {
	      NCond = Rule[r].Size;
        Bits = 0;
        for(d=1;d<=NCond;d++)//ForEach(d, 1, NCond)
        {
          Bits += CondBits(Rule[r].Lhs[d]);
        }

        //  Must encode the number of conditions, but credit the total
        //  encoding by the ways conditions can be reordered
        Rule[r].Bits = Bits +
                       m_fLogItemNo_LogFact_Manager.getDouble(NCond+1,C_LOGITEMNO)
                       - m_fLogItemNo_LogFact_Manager.getDouble(NCond+1,C_LOGFACT)
        ;//LogItemNo[NCond] - LogFact[NCond];
    }//for(r=1;r<=NRules;r++)
}



/////////////////////////////////////////////////////////////////////////
//
//  Determine the number of bits required to encode a condition
//
/////////////////////////////////////////////////////////////////////////


public double CondBits(XMCCondition C)
{
    XMCTest t;
    int a;

    t = C.CondTest;
    a = t.Tested;

    switch ( t.NodeType )
    {
         case BrDiscr:		// test of discrete attribute
         case ThreshContin:	// test of continuous attribute
                return AttTestBits/REDUNDANCY + BranchBits[a];
         case BrSubset:		// subset test on discrete attribute
              return AttTestBits/REDUNDANCY + MaxAttVal[a];
    }
    return 0.0;
}
/////////////////////////////////////////////////////////////////////////
//
//  End of Class
//
/////////////////////////////////////////////////////////////////////////
}








              




