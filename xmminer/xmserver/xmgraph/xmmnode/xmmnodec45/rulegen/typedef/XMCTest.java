//Copyright:    Copyright (c) 1999
//Author:       최대우
//Company:      전북대학교 산업공학과 지능정보시스템
//Description:  Your description
package xmminer.xmserver.xmgraph.xmmnode.xmmnodec45.rulegen.typedef;

public class XMCTest
{
    public final static double None	=		-1;
    public final static int   BrDiscr=	1;	/* node types:	branch */
    public final static int   ThreshContin=	2;	/*		threshold cut */
    public final static int   BrSubset=	3;
	
	public int	NodeType;	//test type (see tree nodes)
    public int	Tested;		// attribute tested
    public int	Forks;		// possible branches
    public double	Cut;	// threshold (if relevant)
    public char[][]	Subset;	// subset (if relevant)
    public int		Outcome;	// result of test on current item

    public    XMCTest()
    {

    }
    public void CopyXMCTest(XMCTest Copy)
    {
//       System.out.println("CopyXMCTest  ");
	   int i=0,Bytes=0,length=0;
       NodeType=Copy.NodeType;
	   //System.out.println("NodeType  "+NodeType);
       Tested=Copy.Tested;
	   //System.out.println("Tested  "+Tested);
       Forks=Copy.Forks;
	   //System.out.println("Forks  "+Forks);
       Cut=Copy.Cut;
	   //System.out.println("Cut  "+Cut);
       Subset = new char[Forks+1][];//(Set *) calloc(Tst->Forks + 1, sizeof(Set));
       if ( NodeType == BrSubset )
	   {
	      //System.out.println("length  "+length);
          length=Copy.Subset[1].length;
	      //System.out.println("length  "+length);
          if(length!=0)
		  {
	        for(i=1;i<=Forks;i++)
			  {
				 Subset[i]=new char[length];
				 for(Bytes=0;Bytes<length;Bytes++)
				 {
					Subset[i][Bytes]= Copy.Subset[i][Bytes];
				   //System.out.println("i=  "+i+"    Bytes=  "+i);
				 }
			 }
          }
       }
       //this.Subset=Copy.Subset;
       Outcome=Copy.Outcome;
	   //System.out.println("Outcome  "+Outcome);
    }
}
    

