
//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1999
//Author:       최대우
//Company:      전북대학교 산업공학과 지능정보시스템
//Description:  Your description
package xmminer.xmserver.xmgraph.xmmnode.xmmnodec45.rulegen;
import  xmminer.xmserver.xmgraph.xmmnode.xmmnodec45.treegen.*;
import  xmminer.xmlib.xmtable.*;
import  xmminer.xmlib.xmcompute.*;
import java.util.*;
import java.io.*;
import java.lang.*;
import  xmminer.xmserver.xmgraph.xmmnode.xmmnodec45.rulegen.typedef.*;
public class XMCTestRules extends XMCMakeRules
{
//
//
//testrules.c
//
//
////////////////////////////////////////////////////////////////////////////////
//
// 생성자
// public XMCGenRules()
// public XMCGenRules(String ProjectName, String Modelname)
// 
////////////////////////////////////////////////////////////////////////////////
  public XMCTestRules()
  {

  }
  
  public XMCTestRules(String ProjectName, String Modelname)
  {
        m_sProjectName = ProjectName;
        m_sModelName= Modelname;
  }
/////////////////////////////////////////////////////////////////////////
//
//	Evaluate all rulesets
//
/////////////////////////////////////////////////////////////////////////


public void    EvaluateRulesets(boolean  testonly)
//  ----------------
//    Boolean DeleteRules;
{
    int t;
    int i, Errors[];//, Interpret();
    double AvSize=0, AvErrs=0;
    boolean Final;

    if ( TRIALS == 1 )
    {
	      //  Evaluate current ruleset as there is no composite ruleset
        Interpret(0, MaxItem, testonly, true, true);
        return;
    }
    /*
    Errors = (int *) malloc((TRIALS+1) * sizeof(int));

    ForEach(t, 0, TRIALS)
    {
	NRules    = PRSet[t].SNRules;
	Rule      = PRSet[t].SRule;
	RuleIndex = PRSet[t].SRuleIndex;
	DefaultClass = PRSet[t].SDefaultClass;

	if ( t < TRIALS )
	{
	    System.out.println("\nRuleset %d:\n", t);
	}
	else
	{
	    System.out.println("\nComposite ruleset:\n");
	}

	Final = (t == TRIALS);
	Errors[t] = Interpret(0, MaxItem, DeleteRules, Final, Final);

	AvSize += NRules;
	AvErrs += Errors[t];

	if ( DeleteRules )
	{
	    PRSet[t].SNRules = NRules;
	}
    }

    //  Print report

    System.out.println("\n");
    System.out.println("Trial   Size      Errors\n");
    System.out.println("-----   ----      ------\n");

    ForEach(t, 0, TRIALS)
    {
	if ( t < TRIALS )
	{
	    System.out.println("%4d", t);
	}
	else
	{
	    System.out.println("  **");
	}
	System.out.println("    %4d  %3d(%4.1f%%)\n",
	      PRSet[t].SNRules, Errors[t], 100 * Errors[t] / (MaxItem+1.0));
    }

    AvSize /= TRIALS + 1;
    AvErrs /= TRIALS + 1;
    System.out.println("\t\t\t\tAv size = %.1f,  av errors = %.1f (%.1f%%)\n",
	   AvSize, AvErrs, 100 * AvErrs / (MaxItem+1.0));*/
}



/////////////////////////////////////////////////////////////////////////
//
//	Evaluate current ruleset
//
/////////////////////////////////////////////////////////////////////////


double	Confidence;		// certainty factor of fired rule
				// (set by BestRuleIndex)


public int Interpret(int Fp,int Lp,boolean testonly,boolean CMInfo,boolean Arrow)
{
    int i,j, Tested=0, Errors=0, Better[], Worse[], ConfusionMat[];
    boolean FoundRule;
    int AssignedClass, AltClass;
    int Att,m_iValue;
    int p, Bestr, ri, ri2, riDrop=0;
    double ErrorRate, BestRuleConfidence=0;
	String temp=null;
    m_iPbCount_num++;
    i= (MaxClass+1)*(MaxClass+1);
    ConfusionMat = new int[i];//(int *) calloc((MaxClass+1)*(MaxClass+1), sizeof(int));

    for(i=0;i<(MaxClass+1)*(MaxClass+1);i++)
         ConfusionMat[i]=0;
    if(testonly) 
	{
	   RuleIndex = new int[NRules+1];
	   for(ri=1;ri<=NRules;ri++)//ForEach(ri, 1, NRules)
	   {
	       RuleIndex[ri]=ri;  
		   m_iPbCount_num++;
       }
	}
	
    for(ri=1;ri<=NRules;ri++)//ForEach(ri, 1, NRules)
    {
	      p = RuleIndex[ri];
	      Rule[p].Used = Rule[p].Incorrect = 0;
		  m_iPbCount_num++;
    }

    Better = new int[NRules+1];//(int *) calloc(NRules+1, sizeof(int));
    Worse  = new int[NRules+1];//(int *) calloc(NRules+1, sizeof(int));

    for(i=Fp;i<=Lp;i++)//ForEach(i, Fp, Lp)
    {
	      ClearOutcomes();
        //  Find first choice for rule for this item

        ri = BestRuleIndex(i, 1);
        Bestr = ( ri>0 ? RuleIndex[ri] : 0 );
        FoundRule = (Bestr > 0?true:false);
        m_iPbCount_num++;
        if ( FoundRule )
        {
              Rule[Bestr].Used++;
              AssignedClass =  Rule[Bestr].Rhs;
              BestRuleConfidence = Confidence;
              m_iPbCount_num++;
              //  Now find second choice
              ri2 = BestRuleIndex(i, ri+1);
              AltClass = ( ri2 >0 ? Rule[RuleIndex[ri2]].Rhs : DefaultClass );
              if ( AltClass != AssignedClass )
              {
                	if ( AssignedClass == GetClassValue(i) )
                  {
                  	    Better[ri]++;
                  }
		              else if ( AltClass == GetClassValue(i) )
                  {
                      Worse[ri]++;
                  }
              }
	       }
         else
         {
         	    AssignedClass = DefaultClass;
         }

         if ( CMInfo )
         {
         	    ConfusionMat[GetClassValue(i)*(MaxClass+1)+AssignedClass]++;
	       }
         SetEstimateValue(i,AssignedClass);
		 
         Tested++;
         
  	     
         for(j=0;j<=MaxAtt;j++)
         {
		    //System.out.println("Evaluate #1    j="+j);
            //m_iColumnNum=j+1;
            //Temp_CAttValue_Att = (CAttValue)vector_item.elementAt(j);
            if(SpecialStatus[j] == DISCRETE)//------------------------------------>>Read!
            {
                System.out.println(GetAttValName(j,GetIntegerValue(i,j)));
                //AttValName[j][GetIntegerValue(i,j)]+"\t");
                
            }
            else
            {
                System.out.println("AssignedClass="+AssignedClass);
				System.out.println(GetDoubleValue(i,j));

            }

        }

         System.out.println("realclass="+GetClassValue(i));  
         System.out.println("AssignedClass="+AssignedClass);
        /* if ( AssignedClass != GetClassValue(i) )
         {
         	    Errors++;
              if ( FoundRule ) Rule[Bestr].Incorrect++;

              if(true)//VERBOSITY >=3)
              {
                  	System.out.println("\n");
                    for(Att=0;Att<=MaxAtt;Att++)//ForEach(Att, 0, MaxAtt)
                    {
                     	    System.out.println( AttName[Att] + " : " );
                          if ( MaxAttVal[Att]>0 )
                          {
						        m_iValue=GetIntegerValue(i,Att);   
                            	if ( m_iValue>0)
								{
									  temp= GetAttValName(Att,m_iValue);
                                      System.out.println(temp);//System.out.println("%s\n", AttValName[Att][GetIntegerValue(i,Att)]);
								}
                                else  System.out.println("?");
                           }
                           else
                           {
                             		if ( GetDoubleValue(i,Att) != Unknown )
                                     System.out.println(GetDoubleValue(i,Att));
                                else System.out.println("?");
                            }
	    	             }//for(Att=0;Att<=MaxAtt;Att+)
                       //System.out.println("\t"+i+":\tGiven class "+ ClassName[GetClassValue(i)]);

	    	             if ( FoundRule )
						 {
						    System.out.println(" rule "+Bestr+" ["+BestRuleConfidence+"%] gives class ");
						    SetEstimateValue(i,EstimateClass);
						 }
                     else
					 {
                        System.out.println(" default class ");
	    	            System.out.println(GetClassValName(AssignedClass));//ClassName[AssignedClass]);
					}
	             }//if(VERBOSITY >=3)
	        }//if ( AssignedClass != GetClassValue(i) )*/
    }//for(i=Fp;i<=Lp;i++)

   /* System.out.println("\nRule  Size  Error  Used  Wrong\t          Advantage\n");
    System.out.println(  "----  ----  -----  ----  -----\t          ---------\n");
    for(ri=1;ri<=NRules;ri++)//ForEach(ri, 1, NRules)
    {
       p = RuleIndex[ri];
       if ( Rule[p].Used > 0 )
       {
           ErrorRate = Rule[p].Incorrect / (double) Rule[p].Used;
	         System.out.println(p+"\t"+ Rule[p].Size+"\t"+
		                         Rule[p].Error+"\t"+ Rule[p].Used+"\t"+
                             Rule[p].Incorrect +"\t"+ErrorRate +"\t"+
                             Better[ri]+"-"+Worse[ri]+"\t"+
                             Better[ri]+"\t"+ Worse[ri]+"\t"+
                             GetClassValName(Rule[p].Rhs));//ClassName[Rule[p].Rhs]);

	         //  See whether this rule should be dropped.  Note: can only drop
		       //  one rule at a time, because Better and Worse are affected
           if ( DeleteRules &&  riDrop==0 && Worse[ri] > Better[ri] )
                   	riDrop = ri;
       	}
    }

    if ( riDrop >0)
    {
	     System.out.println("\nDrop rule"+RuleIndex[riDrop]);

       for(ri=riDrop+1;ri<=NRules;ri++)//ForEach(ri, riDrop+1, NRules)
	     {
	         RuleIndex[ri-1] = RuleIndex[ri];
       }
	     NRules--;
       Errors = Interpret(Fp, Lp, DeleteRules, false, Arrow);
    }
    else
    {
	    if(Arrow)
               System.out.println("\nTested"+Tested +", errors "+Errors+ " ("+Errors +
                                  (double) Tested+"%)"+ "   <<" );
	    else
	       System.out.println("\nTested"+Tested +", errors "+Errors+ " ("+Errors +
	                          (double) Tested+"%)"+   " : ");
    }

    if ( CMInfo )
    {
	     //PrintConfusionMatrix(ConfusionMat);
    }*/
    if(m_bTestFlag) 
	  PrintConfusionMatrix(ConfusionMat,m_sProjectName,m_sModelName+"_RTestC");
	else
	  PrintConfusionMatrix(ConfusionMat,m_sProjectName,m_sModelName+"_RLearnC");

    return Errors;
}



/////////////////////////////////////////////////////////////////////////
//
//	Find the best rule for the given case.
//	Assume ClearOutcomes called; leave probability in Confidence
//
/////////////////////////////////////////////////////////////////////////


int BestRuleIndex(int m_iRowNum ,int Start)
{
    int r, ri;
    m_iPbCount_num++;
    for(ri=Start;ri<=NRules;ri++)//ForEach(ri, Start, NRules)
    {
	     r = RuleIndex[ri];
	     Confidence = Strength(Rule[r], m_iRowNum);

      	if ( Confidence > 0.1 )
        {
           return ri;
        }
    }

    Confidence = 0.0;
    return 0;
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


/////////////////////////////////////////////////////////////////////////
//
//  End of Class
//
/////////////////////////////////////////////////////////////////////////
}
