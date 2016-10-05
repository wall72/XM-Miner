//Title:        C45_Classifier
//Version:      
//Copyright:    Copyright (c) 2000
//Author:       최대우
//Company:      전북대학교 산업공학과 지능정보시스템
//Description:  Tree의 정보를 저장 하고, 파일에 출력하는 부분
package xmminer.xmserver.xmgraph.xmmnode.xmmnodec45.treegen;
import java.util.*;
import java.io.*;
import java.lang.*;
import xmminer.xmlib.xmtable.*;
import xmminer.xmlib.xmcompute.*;

public class XMCTreeView
{

      public final static double Log2= 0.69314718055994530942;
      public final static double Unknown= -999;
      public final static char  IGNORE1=1	;/* special attribute status: do not use */
      public final static char  DISCRETE=2 ;	/* ditto: collect values as data read */
      public final static double Epsilon= 0.0003;
   	  public final static double None	=		-1;
      public final static int   BrDiscr=	1;	/* node types:	branch */
      public final static int   ThreshContin=	2;	/* threshold cut */
      public final static int   BrSubset=	3;
      public final static int   TabSize=	4;
      public final static int  	Width	=	80;
      public final static String	Tab	 =	"|";

      XMCData CData = new XMCData();
	  XMCTree CTree = new XMCTree();

	  String [] m_sTreeString;
	  int[]     m_iIndentNum;
	  int       m_iIndex=-1;
	  int       m_iTreeSize=0;

////////////////////////////////////////////////////////////////////////////////
//
// 생성자
//
// public XMCTree(XMCData CData, XMCTree CTree)
//
//
////////////////////////////////////////////////////////////////////////////////

  public XMCTreeView(XMCData CData, XMCTree CTree)
  {
     int i=0;
     this.CData =CData;
	   this.CTree =CTree;
	   m_iTreeSize=CTree.TreeSize(CTree);
     m_sTreeString= new String[m_iTreeSize+10];
     m_iIndentNum= new int[m_iTreeSize+10];
     for(i=0;i<m_iTreeSize+10;i++)
     {
          m_sTreeString[i]= new String("");
          m_iIndentNum[i]=0;
     }
     ViewTree();
   }
////////////////////////////////////////////////////////////////////////////////
//
// 생성된 값을 읽어 주는 함수
//
//
//
//
//
////////////////////////////////////////////////////////////////////////////////
public int GetStringSize()
{
   return m_iIndex-1;
}
public int GetIndentNum(int index)
{
   //int m_iReturn=0;
   if(index<= m_iIndex-1)
     return m_iIndentNum[index];
   else return 0;
}

public String GetTreeString(int index)
{
   //int m_iReturn=0;
   if(index<= m_iIndex-1)
     return m_sTreeString[index];
   else return new String("null");
}



////////////////////////////////////////////////////////////////////////////////
//
// 트리를 파일에 프린트 하는 부분
// private void ViewTree(XMCTree T,String PrintFileName,boolean flag)  throws IOException
// private void Show(XMCTree T,int Sh)  throws IOException
// private void ShowBranch(int Sh,XMCTree T,int v) throws IOException
//
////////////////////////////////////////////////////////////////////////////////


private void ViewTree()  
{
    int s;
    Show(CTree, 0);
}// End of public void PrintTree(XMCTree T)


private void Show(XMCTree T,int Sh)  
{
    int v, MaxV;


    if ( T.NodeType>0 )
    {
       m_iIndex++;

	     MaxV = T.Forks;
       XMCTree Temp_vector_Tree =  new XMCTree();
	     for(v=1 ; v<=MaxV ; ++v)
	     {
          Temp_vector_Tree = (XMCTree)T.Branch.elementAt(v-1);
          if ( Temp_vector_Tree.NodeType==0 )
          {
		             ShowBranch(Sh, T, v);
		      }
       }//for(v=1 ; v<=MaxV ; ++v)

       // Print subtrees
       for(v=1 ; v<=MaxV ; ++v)
	     {
           Temp_vector_Tree =(XMCTree) T.Branch.elementAt(v-1);
           if ( Temp_vector_Tree.NodeType>0 )
           {
                      ShowBranch(Sh, T, v);
		       }
	     }//for(v=1 ; v<=MaxV ; ++v)
    }// if ( T.NodeType>0 )
    else
    {
      //Tree_data.writeBytes(CData.GetClassValName(T.Leaf));//+"("+T.Items); // ClassColName,T.Leaf
      //Tree_data.writeBytes("  "+"("+T.Items);
      String m_sTempString = new String("");
      int m_iTempInt = (int)(T.Items*100);
      m_sTempString = Double.toString((double)m_iTempInt/100.0);
      m_sTreeString[m_iIndex]=m_sTreeString[m_iIndex]+"   "+
                              CData.GetClassValName(T.Leaf) +"  "+"("+m_sTempString;
	    if ( T.Errors > 0 )
      {
          //Tree_data.writeBytes("/"+T.Errors);
          m_iTempInt = (int)(T.Errors*100);
          m_sTempString = Double.toString((double)m_iTempInt/100.0);
          m_sTreeString[m_iIndex]=m_sTreeString[m_iIndex]+"/" +m_sTempString;
      }
	    //Tree_data.writeBytes(")");
      m_sTreeString[m_iIndex]=m_sTreeString[m_iIndex]+")";
      m_iIndex++;
    }//if ( T.NodeType>0 )_else
  }// End of public void Show(XMCTree T,int Sh)

  private void ShowBranch(int Sh,XMCTree T,int v) 
  {
    int Pv, Last=0;
    int Att;
    boolean FirstValue;
    int TextWidth, Skip, Values=0, i;
    XMCTree Temp_vector_Tree =  new XMCTree();
    String m_sAttValName=null;
    Att = T.Tested;

    switch ( T.NodeType )
    {
	    case BrDiscr:
		       m_sAttValName=CData.GetAttValName(Att,v);
     	     //Indent(Sh, Tab);
           m_iIndentNum[m_iIndex]=Sh;
           //Tree_data.writeBytes(CData.AttName[Att]+" ="+CData.AttValName[Att][v]+":");
			     //Tree_data.writeBytes(CData.AttName[Att]+" ="+m_sAttValName+":");//CData.AttValName[Att][v]
           m_sTreeString[m_iIndex]=m_sTreeString[m_iIndex]+CData.AttName[Att]+" = "+m_sAttValName+" : ";
	    break;

	    case ThreshContin:
  	         //Indent(Sh, Tab);
             m_iIndentNum[m_iIndex]=Sh;
             String Temp_String =new String(( v == 1 ? "<=" : ">" ));
             //Tree_data.writeBytes(CData.AttName[Att] + " " + Temp_String+  " "+T.Cut);
             m_sTreeString[m_iIndex]=m_sTreeString[m_iIndex]+CData.AttName[Att]
                                      + " " + Temp_String+  " "+T.Cut;
             if ( T.Lower != T.Upper )
             {
	               //Tree_data.writeBytes(" [ "+ T.Lower+ " , "+T.Upper +" ] ");
                 m_sTreeString[m_iIndex]=m_sTreeString[m_iIndex]+" [ "+
                                         T.Lower+ " , "+T.Upper +" ] ";
             }

             //Tree_data.writeBytes(":");
             m_sTreeString[m_iIndex]=m_sTreeString[m_iIndex]+":";
             break;

	    case BrSubset:
                 // Count values at this branch
            for(Pv=1 ; Pv<=CData.MaxAttVal[Att] ; ++Pv)
	          {
                if ( ((T.Subset[v][Pv >> 3]) & (1<<(Pv & 07)))!=0)
                {
		               Last = Pv;
                   Values++;
                 }//((T->Subset[v][Pv >> 3]) & (1<<(Pv & 07)))
	          }// for(Pv=1 ; Pv<=MaxAttVal[Att] ; ++Pv)
	          if (  Values==0 ) return;

            //Indent(Sh, Tab);
            m_iIndentNum[m_iIndex]=Sh;

	          if ( Values == 1 )
	          {
                //Tree_data.writeBytes(CData.AttName[Att]+" = "+m_sAttValName);//CData.AttValName[Att][Last]
					      m_sAttValName=CData.GetAttValName(Att,Last);
                m_sTreeString[m_iIndex]=m_sTreeString[m_iIndex]+CData.AttName[Att]+" = "+m_sAttValName;
		            break;
	          }//if ( Values == 1 )

            //Tree_data.writeBytes(CData.AttName[Att]+" = (");
            m_sTreeString[m_iIndex]=m_sTreeString[m_iIndex]+CData.AttName[Att]+" = (";
	          FirstValue = true;
	          Skip = TextWidth = CData.AttName[Att].length() + 5;

	          for(Pv=1 ; Pv<=CData.MaxAttVal[Att] ; ++Pv)
	          {
		           if ( ((T.Subset[v][Pv >> 3]) & (1<<(Pv & 07)))!=0)
		           {
		               m_sAttValName=CData.GetAttValName(Att,Pv);
					         if ( !FirstValue &&
                        (TextWidth + m_sAttValName.length() + 11 > Width) )
		               {
		  	                  //Indent(Sh, Tab);
			                    for(i=1 ; i<=Skip ; ++i)
                          {
                             //Tree_data.writeBytes(" ");
                             m_sTreeString[m_iIndex]=m_sTreeString[m_iIndex]+"   ";
                          }
                          TextWidth = Skip;
                          FirstValue = true;
                   }//if ( ! FirstValue && TextWidth + strlen(AttValName[Att][Pv]) + 11 > Width )

                   if(Pv==Last)
				           {
                       //Tree_data.writeBytes(CData.AttValName[Att][Pv]+" )");
                       m_sAttValName=CData.GetAttValName(Att,Pv);
					             //Tree_data.writeBytes(m_sAttValName+" )");
                       m_sTreeString[m_iIndex]=m_sTreeString[m_iIndex]+m_sAttValName+" )";
                   }
                   else
                   {
                       //Tree_data.writeBytes(CData.AttValName[Att][Pv]+" , ");
					             m_sAttValName=CData.GetAttValName(Att,Pv);
                       //Tree_data.writeBytes(m_sAttValName+" , ");
                       m_sTreeString[m_iIndex]=m_sTreeString[m_iIndex]+m_sAttValName+" , ";
					         }
		               TextWidth += m_sAttValName.length()+1;///CData.AttValName[Att][Pv].length()5 + 1;
		               FirstValue = false;
		           }//if ( ((T->Subset[v][Pv >> 3]) & (1<<(Pv & 07)))!=0)
	          }//for(Pv=1 ; Pv<=MaxAttVal[Att] ; ++Pv)

            //Tree_data.writeBytes(":") ;
            m_sTreeString[m_iIndex]=m_sTreeString[m_iIndex]+ " : ";
       }//switch ( T.NodeType )
    Temp_vector_Tree = (XMCTree) T.Branch.elementAt(v-1);
    Show(Temp_vector_Tree,(int)(Sh+1));
  }// End of public void ShowBranch(int Sh,XMCTree T,int v)
////////////////////////////////////////////////////////////////////////////////
//
// End of Class
// 
////////////////////////////////////////////////////////////////////////////////
}//end of class