
//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1999
//Author:       최대우
//Company:      전북대학교 산업공학과 지능정보시스템
//Description:  Your description
package xmminer.xmserver.xmgraph.xmmnode.xmmnodec45.rulegen;
import  xmminer.xmserver.xmgraph.xmmnode.xmmnodec45.treegen.*;
import java.util.*;
import java.io.*;
import java.lang.*;
import java.text.DecimalFormat;
import xmminer.xmlib.xmtable.*;
import xmminer.xmlib.xmcompute.*;
import xmminer.xmserver.xmgraph.xmmnode.xmmnodec45.rulegen.typedef.*;
public class XMCRules extends XMCGenLogs
{

public XMCTest[]	TestVec;
private int	NTests = 0;
private int TestSpace=0;
private int old_TestSpace=0;
private int old_RuleSpace=0;
//FILE	*Rf = 0, *fopen();	// rules file

////////////////////////////////////////////////////////////////////////////////
//
// 생성자
// public XMCSiftRules()
// public XMCSiftRules(String ProjectName, String Modelname)
// 
////////////////////////////////////////////////////////////////////////////////
  public XMCRules()
  {

  }
  
  public XMCRules(String ProjectName, String Modelname)
  {
        m_sProjectName = ProjectName;
        m_sModelName= Modelname;
  }

/////////////////////////////////////////////////////////////////////////
//
//  Save the current ruleset in rules file in order of the index
//
/////////////////////////////////////////////////////////////////////////
public void    SaveRules(String RuleFileName)
{
    int ri, d, v, Bytes,i;
    int r;
    XMCTest Tst;

    //if ( Rf ) fclose(Rf);

    //strcpy(Fn, FileName);
    //strcat(Fn, ".rules");
    //if ( ! ( Rf = fopen(Fn, "w") ) ) Error(0, Fn, " for writing");
    m_iPbCount_num++;
    try
    {
        FileOutputStream Save_file = null;
        DataOutputStream Save_Rule = null;
        Save_file = new FileOutputStream(RuleFileName);// throws FileNotFoundException;
        Save_Rule = new DataOutputStream(Save_file);

        Save_Rule.writeInt(NRules);//RStreamOut((char *) &NRules, sizeof(int));
        Save_Rule.writeInt(DefaultClass);//RStreamOut((char *) &DefaultClass, sizeof(int));

        for(ri= 1;ri<= NRules;ri++)
        {
             r = RuleIndex[ri];
             Save_Rule.writeInt(Rule[r].Size); //RStreamOut((char *) &Rule[r].Size, sizeof(short));
             for(d=1;d<=Rule[r].Size;d++)
             {
                 Tst = Rule[r].Lhs[d].CondTest;
                 Save_Rule.writeInt(Tst.NodeType); //RStreamOut((char *) &Tst.NodeType, sizeof(short));
                 Save_Rule.writeInt(Tst.Tested); //RStreamOut((char *) &Tst->Tested, sizeof(int));
                 Save_Rule.writeInt(Tst.Forks); //RStreamOut((char *) &Tst->Forks, sizeof(short));
                 Save_Rule.writeDouble(Tst.Cut); //RStreamOut((char *) &Tst->Cut, sizeof(double));
                 if ( Tst.NodeType == BrSubset )
                 {
                    Bytes = (MaxAttVal[Tst.Tested]>>3) + 1;
                    for(v=1; v<=Tst.Forks;v++)
                    {
                       for(i=0;i<Bytes;i++)
                          Save_Rule.writeChar( Tst.Subset[v][i]);//RStreamOut((char *) Tst->Subset[v], Bytes);
                    }
                 }//if ( Tst.NodeType == BrSubset )
	               Save_Rule.writeInt(Rule[r].Lhs[d].TestValue);//RStreamOut((char *) &Rule[r].Lhs[d]->TestValue, sizeof(short));
	           }//for(d=1;d<=Rule[r].Size;d++)
	           Save_Rule.writeInt(Rule[r].Rhs); //RStreamOut((char *) &Rule[r].Rhs, sizeof(int));
	           Save_Rule.writeDouble(Rule[r].Error); //RStreamOut((char *) &Rule[r].Error, sizeof(double));
        }//for(ri= 1;ri<= NRules;ri++)
     }
     catch(Exception e)
     {
           System.out.println("Error in read for Rule file ");
     }
}
/////////////////////////////////////////////////////////////////////////
//
//	Get a new ruleset from rules file
//
/////////////////////////////////////////////////////////////////////////


public void    GetRules(String RuleFileName)
//  ---------
{
    int nr, r,i;
    int n, d, v, Bytes;
    XMCCondition Cond[];
    XMCTest Tst;
    int c;
    double e;

    try
    {
        FileInputStream Rule_file = null;
        DataInputStream Rule_data = null;

        Rule_file = new FileInputStream(RuleFileName);
        Rule_data = new DataInputStream(Rule_file);
        //if ( Rf ) fclose(Rf);

       //strcpy(Fn, FileName);
       //strcat(Fn, ".rules");

       //if ( ! ( Rf = fopen(Fn, "r") ) ) Error(0, Fn, "");

       nr=Rule_data.readInt();// RStreamIn((char *) &nr, sizeof(int));
       DefaultClass=Rule_data.readInt(); //RStreamIn((char *) &DefaultClass, sizeof(int));
       for(r=1;r<=nr;r++)//ForEach(r, 1, nr)
       {
             n=Rule_data.readInt();//RStreamIn((char *) &n, sizeof(short));
             Cond=new XMCCondition[n+1];//Cond = (XMCCondition *) calloc(n+1, sizeof(XMCCondition));
             for(d=1;d<=n;d++)
             {
                 Tst=new XMCTest();//Tst =  (XMCTest) malloc(sizeof(struct TestRec));
                 Tst.NodeType=Rule_data.readInt();//RStreamIn((char *) &Tst->NodeType, sizeof(short));
                 Tst.Tested=Rule_data.readInt();//RStreamIn((char *) &Tst->Tested, sizeof(int));
                 Tst.Forks=Rule_data.readInt();//RStreamIn((char *) &Tst->Forks, sizeof(short));
                 Tst.Cut=Rule_data.readDouble();//RStreamIn((char *) &Tst->Cut, sizeof(double));
                 if ( Tst.NodeType == BrSubset )
                 {
                     Tst.Subset = new char[Tst.Forks+1][];//(Set *) calloc(Tst->Forks + 1, sizeof(Set));
                     Bytes = (MaxAttVal[Tst.Tested]>>3) + 1;
                     for(v=1;v<=Tst.Forks;v++)//ForEach(v, 1, Tst.Forks)
                     {
                        Tst.Subset[v] = new char[Bytes];//(Set) malloc(Bytes);
                        for(i=0;i<Bytes;i++)
                         Tst.Subset[v][i]=Rule_data.readChar();//RStreamIn((char *) Tst->Subset[v], Bytes);
                     }
                  }
                  Cond[d] = new XMCCondition();//(XMCCondition) malloc(sizeof(struct CondRec));
                  Cond[d].CondTest = FindTest(Tst);
                  Cond[d].TestValue= Rule_data.readInt();//RStreamIn((char *) &Cond[d]->TestValue, sizeof(short));
              }
	            c= Rule_data.readInt();//RStreamIn((char *) &c, sizeof(int));
	            e=Rule_data.readDouble();//RStreamIn((char *) &e, sizeof(double));
	            NewRule(Cond, n, c, e);
         }
		 
     }
     catch(Exception errM)
     {
            System.out.println("Error in read for Rule file ");
     }

}

/////////////////////////////////////////////////////////////////////////
//
//  Find a test in the test vector; if it's not there already, add it
//
/////////////////////////////////////////////////////////////////////////
public XMCTest FindTest(XMCTest Newtest)
{
    int i;

    for(i=1;i<NTests;i++)   // ForEach(i, 1, NTests)
    {
      if ( SameTest(Newtest, TestVec[i]) )
      {
	       //free(Newtest);
	       return TestVec[i];
       }
    }

    NTests++;
    if ( NTests >= TestSpace )
    {
	      old_TestSpace=TestSpace;
		  TestSpace += 1000;
        if ( TestSpace > 1000 )
        {
           XMCTest[]  TestVec_temp = new  XMCTest[old_TestSpace];
           System.arraycopy(TestVec, 0, TestVec_temp, 0, old_TestSpace);

	         TestVec = new XMCTest[TestSpace];//(XMCTest *) realloc(TestVec, TestSpace * sizeof(XMCTest));

           System.arraycopy(TestVec_temp, 0, TestVec, 0, old_TestSpace);
        }
	      else
	      {
          TestVec = new XMCTest[TestSpace]; //) malloc(TestSpace * sizeof(XMCTest));
          old_TestSpace=TestSpace;
        }
    }

    TestVec[NTests] = Newtest;

    return TestVec[NTests];
}



/////////////////////////////////////////////////////////////////////////
//
//	See if test t1 is the same test as test t2
//
/////////////////////////////////////////////////////////////////////////


public boolean SameTest(XMCTest t1,XMCTest t2)
//      ---------
//    XMCTest t1, t2;
{
    int i;

    if ( t1.NodeType != t2.NodeType ||	t1.Tested != t2.Tested )
    {
	      return false;
    }

    switch ( t1.NodeType )
    {
	       case BrDiscr:       return true;
	       case ThreshContin:  return  t1.Cut == t2.Cut;
	       case BrSubset:
               for(i=1;i<=t1.Forks;i++)//(i, 1, t1->Forks)
               {
                  String m_sTest1 = new String(t1.Subset[i]);
                  String m_sTest2 = new String(t2.Subset[i]);
		 		          if (!m_sTest1.equals(m_sTest2))
                  {
				               return false;
				           }
			          }
    }
    return true;
}



/////////////////////////////////////////////////////////////////////////
//
//		Clear for new set of rules
//
/////////////////////////////////////////////////////////////////////////


public void    InitialiseRules()
//  ----------------
{
    NRules = 0;
    Rule = null;//0;
    RuleSpace = 0;
}



/////////////////////////////////////////////////////////////////////////
//
//  Clear the outcome fields for the current item in all tests
//
/////////////////////////////////////////////////////////////////////////


public void    ClearOutcomes()
//  --------------
{
    int i;

    for(i=1;i<=NTests;i++)//ForEach(i, 1, NTests)
    {
	     TestVec[i].Outcome = 0;
    }
}



/////////////////////////////////////////////////////////////////////////
//
//  Add a new rule to the current ruleset, by updating Rule[],
//  NRules and, if necessary, RuleSpace
//
/////////////////////////////////////////////////////////////////////////


public boolean NewRule(XMCCondition[] Cond, int NConds,int TargetClass,double Err)
{

	int d, r,i;
    //  See if rule already exists
//	System.out.println("NewRule #2      NRules="+NRules);
    for(r=1;r<=NRules;r++)//ForEach(r, 1, NRules)
    {
	        if ( SameRule(r, Cond, NConds, TargetClass) )
	        {
             if(VERBOSITY >= 1)  System.out.println("\t duplicates rule " + r);
             //  Keep the most pessimistic error estimate
             if ( Err > Rule[r].Error )
             {
                 Rule[r].Error = Err;
             }
             return false;
          }//if ( SameRule(r, Cond, NConds, TargetClass) )
    }//for(r=1;r<=NRules;r++)//ForEach(r, 1, NRules)
    
    //  Make sure there is enough room for the new rule
    NRules++;
//	System.out.println("NewRule #3      NRules="+NRules);
    if ( NRules >= RuleSpace )
    {
		System.out.println("#1: NRules "+NRules);
	    old_RuleSpace=RuleSpace;
		RuleSpace += 100;
	    if ( RuleSpace > 100 )
      {
		System.out.println("#2: RuleSpace "+RuleSpace);
         XMCPR[]  Rule_temp = new  XMCPR[old_TestSpace];
         System.arraycopy(Rule, 0, Rule_temp, 0, old_RuleSpace);

	       Rule = new XMCPR[RuleSpace];//Rule = (XMCPR *) realloc(Rule, RuleSpace * sizeof(XMCPR));

         System.arraycopy(Rule_temp, 0, Rule, 0, old_RuleSpace);
      }
	    else
      {
		System.out.println("#3");
	       Rule = new XMCPR[RuleSpace];//(XMCPR *) malloc(RuleSpace * sizeof(XMCPR));
           old_RuleSpace=RuleSpace;
		   for(i=0;i<RuleSpace;i++)
		   {
		     Rule[i]= new XMCPR();
		   }
      }
    }//if ( NRules >= RuleSpace )

    //  Form the new rule

	System.out.println("#4: Rule.length "+Rule.length);
    Rule[NRules].Size = NConds;
    Rule[NRules].Lhs = new XMCCondition[NConds+1];//(XMCCondition *) calloc(NConds+1, sizeof(XMCCondition));
    for(d=1;d<=NConds;d++)//ForEach(d, 1, NConds)
    {
       Rule[NRules].Lhs[d] = new XMCCondition();//(XMCCondition) malloc(sizeof(struct CondRec));
       Rule[NRules].Lhs[d].CondTest = Cond[d].CondTest;
	   Rule[NRules].Lhs[d].TestValue = Cond[d].TestValue;
    }
    Rule[NRules].Rhs = TargetClass;
    Rule[NRules].Error = Err;
//    System.out.println("NewRule #4      NRules="+NRules);
    if(VERBOSITY >= 1) PrintRule(NRules);

    return true;
}



/////////////////////////////////////////////////////////////////////////
//
//  Decide whether the given rule duplicates rule r
//
/////////////////////////////////////////////////////////////////////////


public boolean SameRule(int r,XMCCondition[] Cond,int NConds,int TargetClass)
//      --------
//    int r;
//    XMCCondition Cond[];
//    short NConds;
//    int TargetClass;
{
    int d, i;
    XMCTest SubTest1, SubTest2;

    if ( Rule[r].Size != NConds || Rule[r].Rhs != TargetClass )
    {
 	      return false;
    }

    for(d=1;d<=NConds;d++)//ForEach(d, 1, NConds)
    {
	      if ( Rule[r].Lhs[d].CondTest.NodeType != Cond[d].CondTest.NodeType ||
	           Rule[r].Lhs[d].CondTest.Tested   != Cond[d].CondTest.Tested )
        {
	             return false;
        }

	       switch ( Cond[d].CondTest.NodeType )
         {
            case BrDiscr:
		             if ( Rule[r].Lhs[d].TestValue != Cond[d].TestValue )
                 {
                    return false;
                 }
		             break;

	          case ThreshContin:
                 if ( Rule[r].Lhs[d].CondTest.Cut != Cond[d].CondTest.Cut)
                 {
		                  return false;
                 }
                 break;

	          case BrSubset:
                 SubTest1 = Rule[r].Lhs[d].CondTest;
                 SubTest2 = Cond[d].CondTest;
                 for(i=1;i<=SubTest1.Forks;i++)//ForEach(i, 1, SubTest1.Forks)
                 {
                    String m_sTest1 = new String(SubTest1.Subset[i]);
                    String m_sTest2 = new String(SubTest2.Subset[i]);
		 		            if (!m_sTest1.equals(m_sTest2))
                    {
                     	return false;
                    }
		             }
	       }//switch ( Cond[d].CondTest.NodeType )
    }//for(d=1;d<=NConds;d++)

    return true;
}



/////////////////////////////////////////////////////////////////////////
//
//		Print the current indexed ruleset
//
/////////////////////////////////////////////////////////////////////////


public void    PrintIndexedRules()
//  -----------------
{
    int ri;

//    System.out.println("PrintIndexedRules, Rule #= "+NRules);
	for(ri=1;ri<=NRules;ri++)//ForEach(ri, 1, NRules )
    {
	      PrintRule(RuleIndex[ri]);
    }
    System.out.println("\nDefault class: "+GetClassValName(DefaultClass));//+ ClassName[DefaultClass]);
}



/////////////////////////////////////////////////////////////////////////
//
//		Print the rule r
//
/////////////////////////////////////////////////////////////////////////


public void    PrintRule(int r)
//  ---------
//    int r;
{
    int d;

    System.out.println("\nRule "+r);
    for(d=1;d<=Rule[r].Size;d++)//ForEach(d, 1, Rule[r].Size)
    {
        System.out.println("    ");
        PrintCondition(Rule[r].Lhs[d]);
    }
	double error=100 * (1 - Rule[r].Error);
   System.out.println(" : " +GetClassValName(Rule[r].Rhs) + " :   "+ error);
	  //  ClassName[Rule[r].Rhs], 100 * (1 - Rule[r].Error));	 
}



/////////////////////////////////////////////////////////////////////////
//
//	Print a condition c of a production rule
//]
/////////////////////////////////////////////////////////////////////////
public void    PrintCondition(XMCCondition c)
//  --------------
//    XMCCondition c;
{
    XMCTest tp;
    int v, pv;
	String m_sAttValName=null;
    boolean First=true;

    tp = c.CondTest;
    v = c.TestValue;

    System.out.println("\t"+AttName[tp.Tested] );

    if ( c.TestValue < 0 )
    {
	System.out.println(" is unknown\n");
	return;
    }

    switch ( tp.NodeType )
    {
	   case BrDiscr:
	       m_sAttValName=GetAttValName(tp.Tested,v);
	       System.out.println(" = "+m_sAttValName);
	    break;

	case ThreshContin:
	      if(v==1)  System.out.println("  <=" + tp.Cut );// ( v == 1 ? "<=" : ">" ), tp.Cut);
		  else      System.out.println("  >" + tp.Cut );//
	    break;

	case BrSubset:
	    System.out.println(" in ");
	    for (pv=1;pv<=MaxAttVal[tp.Tested];pv++)//ForEach(pv, 1,MaxAttVal[tp.Tested] )
	    {
        //#define	 Bit(b)			(1 << (b))
                 //#define	 In(b,s)		((s[(b) >> 3]) & Bit((b) & 07))

        if ( (tp.Subset[v][pv>>3] &(1<<(pv&07)))!=0)//In(pv, tp.Subset[v]) )
        {
        	    if ( First )
               {
               			System.out.println("{");
                    First = false;
               }
               else
               {
                  	System.out.println(", ");
               }
               System.out.println(AttValName[tp.Tested][pv]);
			   m_sAttValName=GetAttValName(tp.Tested,v);
	           System.out.println(" = "+ m_sAttValName);
        }
	    }
	    System.out.println("}");
    }
}

public void MakeRuleFile(String m_sProjectName, String m_sRuleFilename)      
{
  try
  {
	int i, j;
    CXMTableSaver ruleData = new CXMTableSaver();   //litemset data file    
    ruleData.setFileStatus(m_sProjectName,m_sRuleFilename);
    MakeMetaFile(m_sProjectName, m_sRuleFilename);
	for(i=1; i<=NRules;i++)
	{
	   write_rules(ruleData,RuleIndex[i]);
	}
	write_default_rule(ruleData);
	
	ruleData.close();
  }
  catch (IOException ce)
  {
      System.out.println("파일 에러 입니다. 룰을 파일에 쓸수 없습니다.");      
  }
}
public void MakeMetaFile(String _ProjectName, String m_sRuleFilename) throws IOException
  {
    CXMMetaDataSaver ruleMetaData = new CXMMetaDataSaver();
    ruleMetaData.setFileStatus(_ProjectName,m_sRuleFilename);
    ruleMetaData.setProfile("NUMBER_OF_ROWS", String.valueOf(NRules+1));
    ruleMetaData.setProfile("NUMBER_OF_COLUMNS", "3");
    String colNames = "Premise^Consequent^Error";
    ruleMetaData.setProfile("COLUMN_LIST", colNames);
    ruleMetaData.setProfile("DATA_FILE", m_sRuleFilename);
    ruleMetaData.setProfile("ROW_INDEX", m_sRuleFilename);
    colNames = "1^2^3";
    ruleMetaData.setProfile("COLUMN_INDEX", colNames);
    String property = "STRING^INFINITE^DISCRETE^NOMINAL^not_sorted^not_filtered^not_transformed^not_computed";
	  ruleMetaData.setProfile("premise", property);
    ruleMetaData.setProfile("consequent", property);
    property = "STRING^INFINITE^DISCRETE^CARDINAL^not_sorted^not_filtered^not_transformed^not_computed";
    ruleMetaData.setProfile("Error", property);   
    ruleMetaData.close();
  }

  

public void write_rules(CXMTableSaver ruleData, int m_irule_num) throws IOException
{
    String temp = new String();
    String premise = new String();
    String consequent = new String();
    String m_sError = new String();

    int[] colIndex = new int[3];  //5개의 column
    byte[] row, column;
    int d;



    //cxmtable로 rule 저장
    premise = MakePrimse(m_irule_num);
    consequent = GetClassValName(Rule[m_irule_num].Rhs);
    temp = premise + "=>" + consequent + "; ";
    
	//support와 confidence를 0.000형식으로 출력하기 위해...
	DecimalFormat fourDigits = new DecimalFormat("0.000");
    double error=100 * (Rule[m_irule_num].Error);       
    m_sError = fourDigits.format(error).toString();   //supprt(%)
	m_sError+="%";
    temp += m_sError;

    row = premise.getBytes();   //premise
    colIndex[0] = row.length;
    column = consequent.getBytes();   //consequent
    row = addColAtRow(column, row, colIndex, 1);
    column = m_sError.getBytes();     //Error
    row = addColAtRow(column, row, colIndex, 2);
    System.out.println( "temp = " + temp);
	ruleData.createRowData(row, colIndex);  //하나의 rule을 저장한다.
    
}

public void write_default_rule(CXMTableSaver ruleData) throws IOException
{
    String temp = new String();
    String premise = new String("Default class");
    String consequent = new String(GetClassValName(DefaultClass));
    String m_sError = new String();

    int[] colIndex = new int[3];  //5개의 column
    byte[] row, column;
            
    temp = premise + "=>" + consequent + "; ";
    
	//support와 confidence를 0.000형식으로 출력하기 위해...
	DecimalFormat fourDigits = new DecimalFormat("0.000");
    double error=0.000;       
    m_sError = fourDigits.format(error).toString();   //supprt(%)
	m_sError+="%";
    temp += m_sError;

    row = premise.getBytes();   //premise
    colIndex[0] = row.length;
    column = consequent.getBytes();   //consequent
    row = addColAtRow(column, row, colIndex, 1);
    column = m_sError.getBytes();     //Error
    row = addColAtRow(column, row, colIndex, 2);
    System.out.println( "temp = " + temp);
	ruleData.createRowData(row, colIndex);  //하나의 rule을 저장한다.
    
}



public String MakePrimse(int m_irule_num) 
{
    String m_sCondition = new String();
    String premise = new String();
    String consequent = new String();
    String m_sError = new String();
    
	int d; 
    for(d=1;d<=Rule[m_irule_num].Size;d++)//ForEach(d, 1, Rule[r].Size)
    {
        
        m_sCondition= m_sCondition+MakeCondition(Rule[m_irule_num].Lhs[d]);
		if(d!=Rule[m_irule_num].Size)
		   m_sCondition= m_sCondition+", ";
		  
    }
	return m_sCondition;
}
public String MakeCondition(XMCCondition c)
//  --------------
//    XMCCondition c;
{
    XMCTest tp;
    int v, pv;
	
    boolean First=true;
    String m_sCondtion = new String();
    tp = c.CondTest;
    v = c.TestValue;

    //System.out.println("\t"+AttName[tp.Tested] );
    m_sCondtion=m_sCondtion+AttName[tp.Tested];    

    if ( c.TestValue < 0 )
    {
	  //System.out.println(" is unknown\n");
      m_sCondtion=m_sCondtion+" "+"is unknown";
	  return m_sCondtion;
    }

    switch ( tp.NodeType )
    {
	   case BrDiscr:     
		   m_sCondtion=m_sCondtion+" = "+GetAttValName(tp.Tested,v);
		   break;

	   case ThreshContin:
	      if(v==1)  m_sCondtion=m_sCondtion+" <= " + String.valueOf(tp.Cut);
		  else      m_sCondtion=m_sCondtion+" > " + String.valueOf(tp.Cut);
	      break;

	   case BrSubset:
	      m_sCondtion=m_sCondtion+" in ";
		  for (pv=1;pv<=MaxAttVal[tp.Tested];pv++)
		  {
		     if ( (tp.Subset[v][pv>>3] &(1<<(pv&07)))!=0)
			 {
        	    if ( First )
               {     
					m_sCondtion=m_sCondtion+"{";
                    First = false;
               }
               else
               {
                   m_sCondtion=m_sCondtion+", ";
               }               
			   m_sCondtion=m_sCondtion+GetAttValName(tp.Tested,v);
			}//if ( (tp.Subset[v][pv>>3] &(1<<(pv&07)))!=0)
	    }//for (pv=1;pv<=MaxAttVal[tp.Tested];pv++)
		m_sCondtion=m_sCondtion+"}";
    }//switch ( tp.NodeType )
	return m_sCondtion;
}
     


/////////////////////////////////////////////////////////////////////////
//	End of Class
/////////////////////////////////////////////////////////////////////////
}
