package xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.xmutil;

import java.io.*;
import java.util.*;

public class CXMMetaDataReader
{
  private String i_name;
  private String input;
  private String key;
  private String value;
  private String outString;
  private String delimiter = "^";
  private String[] outStringArray;
  private int a;
  private int b;
  private boolean file_open;
  private Vector value_v;
  private Hashtable h;
  private File meta_file;
  private FileReader fr;   
  private BufferedReader br;
  private String path = ""; //"c:/XM-Miner/xmminer/xmserver/xmgraph/xmdnode/data/meta/";

  public CXMMetaDataReader()
  {}

  public void setFileStatus(String filename)
  {
    i_name = path+filename;
    fileOpen();
  }

  private void fileOpen()
  {
    try
    {
      meta_file = new File(i_name);
      fr = new FileReader(meta_file);
      br = new BufferedReader(fr);
      file_open=true;
      setInitStatus();
    } catch(Exception foe)
      {System.out.println("file_open_error="+foe.getMessage());}
  }

  public void close()
  {
    try
    {
      fr.close();
      br.close();
    } catch(Exception fce)
      {System.out.println("file_close_error="+fce.getMessage());}
  }

  private void setInitStatus()
  {
    if (file_open)
    {
      h = new Hashtable();
      try
      {
        while((input=br.readLine())!=null)
        {
          a = input.indexOf("[");
          b = input.indexOf("]");
          key = input.substring(a+1,b);
          value = input.substring(b+1,input.length());
          h.put(key,value);
          //key_v.addElement(key);
        }
      } catch(Exception bre)
        {System.out.println("buffer_read_error="+bre.getMessage());}
    }
  }

  public String getProfile(String key)
  {
    if (h.containsKey(key))
    {
      outString = (String) h.get(key);
    }
    else
    {
      outString = null;
    }
    return outString;
  }

  public String[] getProfiles(String key)
  {
    if (h.containsKey(key))
    {
      dataParsing((String) h.get(key));
    }
    else
    {
      outStringArray = null;
    }
    return outStringArray;
  }

  public boolean containsKey(String key)
  {
    if (h.containsKey(key))
    {
      return true;
    }
    else
    {
      return false;
    } 
  }

  private void dataParsing(String i_str)
  {
    value_v = new Vector();
    try
    {
      StringTokenizer st = new StringTokenizer(i_str, delimiter);
      while((value = st.nextToken()) != null )
      {
        value_v.addElement(value);
      }
    } catch(Exception ste)
      {
      }
    vectorToStringArray();
  }

  private void vectorToStringArray() //throws IOException, Exception
  {
    int value_num = value_v.size();
    outStringArray = new String[value_num];
    try
    {
      Enumeration vEum = value_v.elements();
      int i=0;
      while(vEum.hasMoreElements())
      {
        outStringArray[i] = (String) vEum.nextElement();
        i++;
      }
      value_v.removeAllElements();
    } catch(Exception ene)
      { System.out.println("enumeration_error="+ene.getMessage());}
  }

}

