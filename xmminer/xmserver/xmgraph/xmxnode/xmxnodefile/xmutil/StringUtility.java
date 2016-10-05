package xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.xmutil;

import java.util.*;

public class StringUtility
{
  public Vector strArray2Vector(String input[])
  {
      Vector v = new Vector(3,2);
      int size = input.length;
      for (int i=0; i<size; i++) {
          v.addElement(input[i]);
      }
      return v;
  }

  public String[] vector2StrArray(Vector input)
  {
      int size = input.size();
      String s[] = new String[size];
      for (int i=0; i<size; i++) {
          s[i] = (String)input.elementAt(i);
      }
      return s;
  }

  public String long2Str(long i) throws NumberFormatException
  {
      Long ii = new Long(i);
      return ii.toString();
  }
  
  public String int2Str(int i) throws NumberFormatException
  {
      Integer ii = new Integer(i);
      return ii.toString();
  }

  public String float2Str(float f) throws NumberFormatException

  {
      Float ff = new Float(f);
      return ff.toString();
  }

       
    public int str2Int(String str) 
    {
       		Integer i = new Integer(str);
       		return i.intValue();
    }
    public float str2Float(String str) 
    {
       	Float f = new Float(str);
       	return f.floatValue();
    }

} // end of Utility Class