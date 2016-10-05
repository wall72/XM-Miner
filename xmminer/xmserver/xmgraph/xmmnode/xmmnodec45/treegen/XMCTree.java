
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

public class XMCTree
{

      public final static double Log2= 0.69314718055994530942;
      public final static double Unknown= -999;
      public final static char  IGNORE1=1	;/* special attribute status: do not use */
      public final static char  DISCRETE=2 ;	/* ditto: collect values as data read */
      public final static double Epsilon= 0.0003;
   	  public final static double None	=		-1;
      public final static int   BrDiscr=	1;	/* node types:	branch */
      public final static int   ThreshContin=	2;	/*		threshold cut */
      public final static int   BrSubset=	3;
      public final static int   TabSize=	4;
      public final static int  	Width	=	80;
      public final static String	Tab	 =	"|";

      public int	      NodeType;	  /* 0=leaf 1=branch 2=cut 3=subset */
      public int	      Leaf;		    /* most frequent class at this node */
      public double	      Items;		  /* no of items at this node */
      public double[]     ClassDist;	/* class distribution of items */
      public double       Errors;		/* no of errors at this node */
      public int	      Tested; 	/* attribute referenced in test */
      public int	      Forks;		/* number of branches at this node */
      public double	      Cut,		  /* threshold for continuous attribute */
		  	                  Lower,		/* lower limit of soft threshold */
                		  	  Upper;		/* upper limit ditto */
      public char[][]     Subset;	  /* subsets of discrete values  */
      public Vector       Branch;	  /* Branch[x] = (sub)tree for outcome x */
      //String PrintFileName = new String();

      FileOutputStream Tree_file = null;
      DataOutputStream Tree_data = null;
      String Treefname= new String("print.txt");

      FileOutputStream Save_file = null;
      DataOutputStream Save_data = null;
      //String Savefname= new String("save.txt");


      XMCData CData = new XMCData();


      //tree.c
      int	      Subtree;		// highest subtree to be printed
      XMCTree[] 	Subdef;		// pointers to subtrees



////////////////////////////////////////////////////////////////////////////////
//
// 생성자
// public XMCTree()
// public XMCTree(XMCData CData)
// public XMCTree(double ClassFreq[], int NodeClass,double Cases, double Errors,int MaxClass,XMCData CData)
// public XMCTree(XMCTree CTree)
////////////////////////////////////////////////////////////////////////////////

  public XMCTree()
  {
     Branch =new Vector();

  }

  public XMCTree(XMCData CData)
  {
     Branch =new Vector();
     this.CData =CData;

   }

  public XMCTree(double ClassFreq[], int NodeClass,double Cases, double Errors,int MaxClass,XMCData CData)
  {

    ClassDist = new double[MaxClass+1];
    for(int i=0;i<=MaxClass;i++)
    {
         ClassDist[i]= ClassFreq[i];
    }
    NodeType	= 0;
    Leaf		= NodeClass;
    Items		= Cases;
    Errors	= Errors;
    Branch = new Vector();
    this.CData =CData;
  }

 public XMCTree(XMCTree CTree)
  {

    NodeType =CTree.NodeType;
	  Leaf=CTree.Leaf;
	  Items=CTree.Items;
		ClassDist=CTree.ClassDist;
	  Errors=CTree.Errors;
	  Tested=CTree.Tested;
	  Forks=CTree.Forks;
	  Cut=CTree.Cut;
		Lower=CTree.Lower;
    Upper=CTree.Upper;
	  Subset=CTree.Subset;
	  Branch=CTree.Branch;
    CData =CTree.CData;
  }
public void SetCData(XMCData ArgCData)
{
   CData= ArgCData;
}

////////////////////////////////////////////////////////////////////////////////
//
// 트리를 파일에 프린트 하는 부분
// public void PrintTree(XMCTree T,String PrintFileName,boolean flag)  throws IOException
// public void Show(XMCTree T,int Sh)  throws IOException
// public int MaxLine(XMCTree St)
// public void ShowBranch(int Sh,XMCTree T,int v) throws IOException
// public void Indent(int Sh,String Mark) throws IOException
////////////////////////////////////////////////////////////////////////////////


public void PrintTree(String PrintFileName,boolean flag)  throws IOException
{
    int s;
    Tree_file = new FileOutputStream(PrintFileName);// throws FileNotFoundException;
    Tree_data = new DataOutputStream(Tree_file);

    Subtree=0;

    Tree_data.writeBytes("Decision Tree:\r\n");

    Show(this, 0);
    Tree_data.writeBytes("\r\n");

    for(s=1; s<=Subtree ; ++s)
    {
      Tree_data.writeBytes("\r\n\r\nSubtree [S" + s +"]\r\n");
	    Show(Subdef[s],0);
	    Tree_data.writeBytes("\r\n");
    }
    Tree_data.writeBytes("\r\n");

}// End of public void PrintTree(XMCTree T)


public void Show(XMCTree T,int Sh)  throws IOException
{
    int v, MaxV;


    if ( T.NodeType>0 )
    {

	/*     // See whether separate subtree needed
       if ( T != null && Sh * TabSize + MaxLine(T) > Width )
	     {
	        if ( Subtree < 99 )
	        {
		                Subdef[++Subtree] = T;
                    Tree_data.writeBytes("[S"+Subtree+"]");
//					System.out.println("[S"+Subtree+"]");
	        }
	        else
	        {
                  Tree_data.writeBytes("[S??]");
//				  System.out.println("[S??]");
	        }

	     }//if ( T != null && Sh * TabSize + MaxLine(T) > Width )
	else
	{*/
	        MaxV = T.Forks;
                XMCTree Temp_vector_Tree =  new XMCTree();
	        for(v=1 ; v<=MaxV ; ++v)
	        {
                         Temp_vector_Tree = (XMCTree)T.Branch.elementAt(v-1);
		         //System.out.println("else Nodetype of super  : "+T.NodeType+" 하위  NodeType"+v+":" +Temp_vector_Tree.NodeType+"  MaxV"+MaxV);
		          if ( Temp_vector_Tree.NodeType==0 )
		          {
		           //  System.out.println("NodeType=0" );	 
		             ShowBranch(Sh, T, v);
		          }
          }//for(v=1 ; v<=MaxV ; ++v)


	        // Print subtrees
          for(v=1 ; v<=MaxV ; ++v)
	        {
                 Temp_vector_Tree =(XMCTree) T.Branch.elementAt(v-1);
                 //System.out.println("for Nodetype of super"+T.NodeType+" 하위  NodeType"+v+":" +Temp_vector_Tree.NodeType+"  MaxV"+MaxV);
		 if ( Temp_vector_Tree.NodeType>0 )
		 {
                      ShowBranch(Sh, T, v);
		 }
	        }//for(v=1 ; v<=MaxV ; ++v)

	    // }//if ( T != null && Sh * TabSize + MaxLine(T) > Width )_else
    }// if ( T.NodeType>0 )
    else
    {
      //System.out.println("Nodetype of super"+T.NodeType);
      //Tree_data.writeBytes("  "+CData.ClassName[T.Leaf]+"("+T.Items);
      Tree_data.writeBytes(CData.GetClassValName(T.Leaf));//+"("+T.Items); // ClassColName,T.Leaf
      Tree_data.writeBytes("  "+"("+T.Items);
//	  System.out.println(CData.GetClassValName(T.Leaf));
//	  System.out.println("  "+"("+T.Items);

	    if ( T.Errors > 0 ) Tree_data.writeBytes("/"+T.Errors);
	    Tree_data.writeBytes(")");
//		System.out.println(")");
    }//if ( T.NodeType>0 )_else
  }// End of public void Show(XMCTree T,int Sh)

public int MaxLine(XMCTree St)
{
    int a;
    int v, MaxV, Next;
    int Ll, MaxLl=0;
    String m_sAttValName=null;
	 XMCTree Temp_vector_Tree =  new XMCTree();

    a = St.Tested;

    MaxV = St.Forks;

    for(v=1;v<=MaxV;v++)
    {
     	m_sAttValName=CData.GetAttValName(a,v);
		Ll =  (int)(St.NodeType == 2 ? m_sAttValName.length()+1: 5);//CData.AttValName[a][v].length() + 1);
         
		 
	     // Find the appropriate branch

        Next = v;
        Temp_vector_Tree = (XMCTree) St.Branch.elementAt(Next-1);

	      if (  Temp_vector_Tree.NodeType==0 )
        {
        	    Ll += /*CData.ClassName[Temp_vector_Tree.Leaf].length()*/5 + 6;
	       }
	       MaxLl = ((MaxLl)>(Ll) ? MaxLl : Ll);
    }//for(v=1;v<=MaxV;v++)

    return (/*CData.AttName[a].length()*/5 + 4 + MaxLl);
}

public void ShowBranch(int Sh,XMCTree T,int v) throws IOException
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
     	       Indent(Sh, Tab);
               //Tree_data.writeBytes(CData.AttName[Att]+" ="+CData.AttValName[Att][v]+":");
			   Tree_data.writeBytes(CData.AttName[Att]+" ="+m_sAttValName+":");//CData.AttValName[Att][v]
//			   System.out.println(CData.AttName[Att]+" ="+m_sAttValName+":");
	       break;

	    case ThreshContin:
  	         Indent(Sh, Tab);
                 String Temp_String =new String(( v == 1 ? "<=" : ">" ));
                 Tree_data.writeBytes(CData.AttName[Att] + " " + Temp_String+  " "+T.Cut);
//				 System.out.println(CData.AttName[Att] + " " + Temp_String+  " "+T.Cut);
                 if ( T.Lower != T.Upper )
  	              {
				    Tree_data.writeBytes("[ "+ T.Lower+ " , "+T.Upper +" ]");
//                    System.out.println("[ "+ T.Lower+ " , "+T.Upper +" ]");
					}
				 Tree_data.writeBytes(":");
				 
//				 System.out.println(":");
                 break;

	    case BrSubset:
                 // Count values at this branch
                 for(Pv=1 ; Pv<=CData.MaxAttVal[Att] ; ++Pv)
	          {
                     //#define	 Bit(b)			(1 << (b))
                     //#define	 In(b,s)		((s[(b) >> 3]) & Bit((b) & 07))
                   if ( ((T.Subset[v][Pv >> 3]) & (1<<(Pv & 07)))!=0)
		   {
		               Last = Pv;
                               Values++;
                    }//((T->Subset[v][Pv >> 3]) & (1<<(Pv & 07)))
	          }// for(Pv=1 ; Pv<=MaxAttVal[Att] ; ++Pv)
	          if (  Values==0 ) return;

                   Indent(Sh, Tab);

	          if ( Values == 1 )
	          {     
		            //fprintf(fprin,"%s = %s:", AttName[Att], AttValName[Att][Last]);
					m_sAttValName=CData.GetAttValName(Att,Last);
                       Tree_data.writeBytes(CData.AttName[Att]+" = "+m_sAttValName);//CData.AttValName[Att][Last]
//					   System.out.println(CData.AttName[Att]+" = "+m_sAttValName);
		        break;
	          }//if ( Values == 1 )
                   //fprintf(fprin,"%s in {", AttName[Att]);
                  Tree_data.writeBytes(CData.AttName[Att]+" = (");
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
		  	                  Indent(Sh, Tab);
			                    for(i=1 ; i<=Skip ; ++i) Tree_data.writeBytes(" ");
                          TextWidth = Skip;
                          FirstValue = true;
                   }//if ( ! FirstValue && TextWidth + strlen(AttValName[Att][Pv]) + 11 > Width )
                   //fprintf(fprin,"%s%c", AttValName[Att][Pv], Pv == Last ? '}' : ',');
                   if(Pv==Last)
				   {
                       //Tree_data.writeBytes(CData.AttValName[Att][Pv]+" )");
                       m_sAttValName=CData.GetAttValName(Att,Pv);
					   Tree_data.writeBytes(m_sAttValName+" )");
//					   System.out.println(m_sAttValName+" )");
                   }
                   else
				   {
                       //Tree_data.writeBytes(CData.AttValName[Att][Pv]+" , ");
					   m_sAttValName=CData.GetAttValName(Att,Pv);
                       Tree_data.writeBytes(m_sAttValName+" , ");
//					   System.out.println(m_sAttValName+" , ");
					 }
		               TextWidth += m_sAttValName.length()+1;///CData.AttValName[Att][Pv].length()5 + 1;
		               FirstValue = false;
		           }//if ( ((T->Subset[v][Pv >> 3]) & (1<<(Pv & 07)))!=0)
	          }//for(Pv=1 ; Pv<=MaxAttVal[Att] ; ++Pv)
	          //fprintf(fprin, ":");
            Tree_data.writeBytes(":") ;
//			System.out.println(" :");
       }//switch ( T.NodeType )
    Temp_vector_Tree = (XMCTree) T.Branch.elementAt(v-1);
    Show(Temp_vector_Tree,(int)(Sh+1));
  }// End of public void ShowBranch(int Sh,XMCTree T,int v)


  public void Indent(int Sh,String Mark) throws IOException
  {
    Tree_data.writeBytes("\r\n");
    //fprintf(fprin,"\n");
    while ( Sh-- >0) Tree_data.writeBytes( "  "+Mark);
  }// End of public void Indent(int Sh,char * Mark)



////////////////////////////////////////////////////////////////////////////////
//
// 트리를 파일에 저장 하는 부분
// public void SaveTree(XMCTree T,String SaveTreeFileName)throws FileNotFoundException ,IOException
// public void OutTree(XMCTree T) throws IOException
////////////////////////////////////////////////////////////////////////////////
public void SaveTree(String SaveTreeFileName)   throws  FileNotFoundException ,IOException
  {
	  if ( !(SaveTreeFileName.length()==0) ) //close(SaveTreeFileName);
      {
         Save_file = new FileOutputStream(SaveTreeFileName);// throws FileNotFoundException;
         Save_data = new DataOutputStream(Save_file);
         OutTree(this);
      }
  }// End of public void SaveTree(XMCTree T,String Extension)

public void OutTree(XMCTree T)      throws IOException
  {
    int v,i;
    int Bytes;
//      System.out.println("OutTree(XMCTree T)");
      Save_data.writeInt(T.NodeType);
      Save_data.writeInt(T.Leaf);
      Save_data.writeDouble(T.Items);
      Save_data.writeDouble(T.Errors);
      for(i=0; i<=CData.MaxClass;i++)
         Save_data.writeDouble(T.ClassDist[i]);
//     System.out.println("T.NodeTypr :"+T.NodeType +"  T.Leaf  :"
//                         +T.Leaf+"   T.Items :"+T.Items+"  CData.MaxClass : "+CData.MaxClass);
    if ( T.NodeType>0 )
    {
      Save_data.writeInt(T.Tested);
      Save_data.writeInt(T.Forks);
	    switch ( T.NodeType )
	    {
	      case BrDiscr:
		    break;

	      case ThreshContin:
                 Save_data.writeDouble(T.Cut);
                 Save_data.writeDouble(T.Lower);
                 Save_data.writeDouble(T.Upper);
//                 System.out.println("T.Cut :"+ T.Cut+"  T.Lower"+T.Lower+"  T.Upper :"+T.Upper);
		    break;

	      case BrSubset:
		            Bytes = (CData.MaxAttVal[T.Tested]>>3) + 1;
		            for(v=1 ; v<=T.Forks ; ++v)
		            {
                     for(i=0;i<Bytes;i++)
                          Save_data.writeChar( T.Subset[v][i]);
//                     System.out.println("T.Subset[v] :"+ new String(T.Subset[v]));
		            }
		    break;
	    }

	    for(v=1 ; v<=T.Forks ; ++v)
	    {
        XMCTree Temp_vector_Tree =  new XMCTree();
        Temp_vector_Tree= (XMCTree)T.Branch.elementAt(v-1);
	      OutTree(Temp_vector_Tree);
      }
    }
  }// End of public void OutTree(CXMCTreeForm T)



////////////////////////////////////////////////////////////////////////////////
//
// Retrieve entire decision tree with extension Extension
// public void SaveTree(XMCTree T,String SaveTreeFileName)throws FileNotFoundException ,IOException
// public void OutTree(XMCTree T) throws IOException
////////////////////////////////////////////////////////////////////////////////

public void GetTree(String OpenTreeFileName) throws FileNotFoundException, IOException
//public XMCTree GetTree(String OpenTreeFileName) throws FileNotFoundException, IOException
 {
    //Tree InTree();

	         FileInputStream Tree_file = null;
           DataInputStream Tree_data = null;
           Tree_file = new FileInputStream(OpenTreeFileName);
           Tree_data = new DataInputStream(Tree_file);//Data

           //return InTree( Tree_data);
           InTree1( Tree_data);
 }

public XMCTree InTree(DataInputStream Tree_data) throws IOException
 {
    XMCTree T;
    int v,i;
    int Bytes;

    T = new XMCTree(CData);

    //StreamIn((char *) &T->NodeType, sizeof(int));
    //StreamIn((char *) &T->Leaf, sizeof(ClassNo));
    //StreamIn((char *) &T->Items, sizeof(ItemCount));
    //StreamIn((char *) &T->Errors, sizeof(ItemCount));

    //T.NodeType =Tree_data.readint();
    //T.Leaf =Tree_data.readint();
    //T.Items =Tree_data.readDouble();
    //T.Errors =Tree_data.readDouble();
    //T.ClassDist= new double[CData.MaxClass+1];

    T.NodeType =Tree_data.readInt();
    T.Leaf =Tree_data.readInt();
    T.Items =Tree_data.readDouble();
    T.Errors =Tree_data.readDouble();
    T.ClassDist= new double[CData.MaxClass+1];

    for( i=0; i<=CData.MaxClass;i++)
             T.ClassDist[i] =Tree_data.readDouble();

    //T->ClassDist = (ItemCount *) calloc(MaxClass+1, sizeof(ItemCount));
    //StreamIn((char *) T->ClassDist, (MaxClass + 1) * sizeof(ItemCount));

    if ( T.NodeType>0 )
    {

	     //StreamIn((char *) &T->Tested, sizeof(Attribute));
	     //StreamIn((char *) &T->Forks, sizeof(int));
       T.Tested =Tree_data.readInt();
       T.Forks =Tree_data.readInt();

	     switch ( T.NodeType )
	     {
	            case BrDiscr:
                   		break;

              case ThreshContin:
                   //StreamIn((char *) &T->Cut, sizeof(double));
                   //StreamIn((char *) &T->Lower, sizeof(double));
                   //StreamIn((char *) &T->Upper, sizeof(double));
                   T.Cut =Tree_data.readDouble();
                   T.Lower =Tree_data.readDouble();
                   T.Upper =Tree_data.readDouble();
                   break;

	            case BrSubset:
                   Bytes = (CData.MaxAttVal[T.Tested]>>3) + 1;
                   T.Subset = new char[T.Forks + 1][Bytes];
                   //Bytes = (CData.MaxAttVal[T.Tested]>>3) + 1;
                   for(v=1; v<=T.Forks;v++)
                   {
                         //StreamIn((char *) T->Subset[v], Bytes);
                         for(i=0;i<Bytes;i++)
                           T.Subset[v][i]=Tree_data.readChar();
                   }
       }//switch ( T->NodeType )

	     //T->Branch = (Tree *) calloc(T->Forks + 1, sizeof(Tree));
	     for(v=1;v<= T.Forks;v++)
	     {
             XMCTree TempTree= new XMCTree();
             TempTree=InTree(Tree_data);
	           T.Branch.add(TempTree);
	     }
    } //if ( T.NodeType>0 )
    return T;
 }//public CXMCTreeForm InTree(DataInputStream Tree_data)

public void InTree1(DataInputStream Tree_data) throws IOException
 {
    int v,i;
    int Bytes;
    //System.out.println("InTree1()");
//    System.out.println("InTree1(XMCTree T)");
    NodeType =Tree_data.readInt();
    Leaf =Tree_data.readInt();
    Items =Tree_data.readDouble();
    Errors =Tree_data.readDouble();
    ClassDist= new double[CData.MaxClass+1];

    for( i=0; i<=CData.MaxClass;i++)
             ClassDist[i] =Tree_data.readDouble();
//    System.out.println("NodeTypr :"+NodeType +"  Leaf  :"
//                         +Leaf+"   Items :"+Items+"  CData.MaxClass : "+CData.MaxClass);
    if ( NodeType>0 )
    {
       Tested =Tree_data.readInt();
       Forks =Tree_data.readInt();
	     switch ( NodeType )
	     {
	            case BrDiscr:
                   		break;

              case ThreshContin:
                   Cut =Tree_data.readDouble();
                   Lower =Tree_data.readDouble();
                   Upper =Tree_data.readDouble();
//                   System.out.println("Cut :"+ Cut+"  Lower"+Lower+"  Upper:"+Upper);
                   break;

	            case BrSubset:
                   Bytes = (CData.MaxAttVal[Tested]>>3) + 1;
                   Subset = new char[Forks + 1][Bytes];
                   for(v=1; v<=Forks;v++)
                   {
                         for(i=0;i<Bytes;i++)
                           Subset[v][i]=Tree_data.readChar();
//                         System.out.println("Subset[v] :"+ new String(Subset[v]));
                   }
       }//switch ( T->NodeType )

	     for(v=1;v<= Forks;v++)
	     {
             XMCTree TempTree= new XMCTree(CData);
             TempTree.InTree1(Tree_data);
	           Branch.add(TempTree);
	     }
    } //if ( T.NodeType>0 )

}//public CXMCTreeForm InTree(DataInputStream Tree_data)

public int TreeSize(XMCTree Node)
  {
    int Sum=0;
    int v;

    if ( Node.NodeType > 0)
    {
	    for(v=1 ; v<=Node.Forks ; ++v)
	    {
        XMCTree tempTree = new XMCTree();
        tempTree = (XMCTree)Node.Branch.elementAt(v-1);
        Sum = (Sum + TreeSize(tempTree));
	    }
    }
    return (Sum + 1);
}

////////////////////////////////////////////////////////////////////////////////
//
// End of Class
//  
// 
// 
// 
////////////////////////////////////////////////////////////////////////////////



}//end of class