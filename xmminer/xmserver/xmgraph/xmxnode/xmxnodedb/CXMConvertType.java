package xmminer.xmserver.xmgraph.xmxnode.xmxnodedb;

import java.util.*;

public class CXMConvertType{
  public char[] byte2char(byte input[]){
    String data = new String(input) + ",";
    return data.toCharArray();
  }

  public char[] str2Char(String input){
    return input.toCharArray();
  }

  public Vector str2Vec(String input[]){
    Vector v = new Vector(3,2);
    int size = input.length;
    for (int i=0; i<size; i++) v.addElement(input[i]);
    return v;
  }

  public String[] vec2Str(Vector input){
    int size = input.size();
    String s[] = new String[size];
    for (int i=0; i<size; i++) s[i] = (String)input.elementAt(i);
    return s;
  }

  public String long2Str(long i){
    Long ii = new Long(i);
    return ii.toString();
  }

  public String int2Str(int i){
    Integer ii = new Integer(i);
    return ii.toString();
  }

  public String float2Str(float f){
    Float ff = new Float(f);
    return ff.toString();
  }

  public int str2Int(String str){
    Integer i = new Integer(str);
   	return i.intValue();
  }

  public float str2Float(String str){
    Float f = new Float(str);
   	return f.floatValue();
  }

  public String strCut(String str){
    int i = str.indexOf(".");
    str  = str.substring(i+1,str.length());
    return str;
  }

  public String ascii2ksc(String str, String charSet){
    String result = null;

    if(charSet.compareTo("KSC5601") == 0) {
      result = str;
    }else{
      try{
        result = new String(str.getBytes("8859_1"), "KSC5601");
      }catch(java.io.UnsupportedEncodingException e){
        System.err.println(e.toString());
      }
    }
    return  result;
  }

  public char[] ascii2ksc(char[] cha, String charSet){
    String result = null;
    String str = cha.toString();

    if(charSet.compareTo("KSC5601") == 0) result = str;
    else{
      try{
        result = new String(str.getBytes("8859_1"), "KSC5601");
      }catch(java.io.UnsupportedEncodingException e){
        System.err.println(e.toString());
      }
    }
    return  result.toCharArray();
  }

  public char[] Int2CharArray(int n){
    int nLength = calLengthLength(n);
    char s[] = new char[nLength];
    for(int i=1; i<= nLength; i++){
      s[nLength - i] = (char)(n%10 + 48);
      n = (int)(n/10);
    }
    return s;
  }

  private int calLengthLength(int length){
    int lengthlength = 1;
    while(true){
      if (length < 10) return lengthlength;
      length = (int)(length/10);
      lengthlength++;
    }
  }

}