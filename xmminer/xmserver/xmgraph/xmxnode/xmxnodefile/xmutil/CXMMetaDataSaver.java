package xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.xmutil;

import java.io.*;
import java.util.*;

public class CXMMetaDataSaver
{
  private String i_name="";
  private String input;
  private String key;
  private String value;
  private String outString;
  private String delimiter = "^";
  private int a;
  private int b;
  private boolean file_open=false;
//  private Vector key_v = new Vector();
  private Hashtable h;
  private File meta_file;
  private BufferedReader br;
  private FileReader fr;
  private FileWriter fw;
  private String path = "";

  public CXMMetaDataSaver()
  {}

  public void setFileStatus(String filename)
  {
    i_name = path+filename;
    setInitStatus();
    fileOpen();
  }

  private void initFileOpen()
  {
    try
    {
      meta_file = new File(i_name+".meta");
      if (meta_file.exists())
      {
        fr = new FileReader(meta_file);
        br = new BufferedReader(fr);
        file_open=true;
      }
      else
      {
        meta_file.createNewFile();
      }
    } catch(Exception re)
      {System.out.println(re.getMessage());}
  }

  private void initFileClose()
  {
    try
    {
      br.close();
      fr.close();
    } catch(Exception ce)
      {System.out.println(ce.getMessage());}
  }

  private void fileOpen()
  {
    try
    {
      fw = new FileWriter(meta_file);
    } catch(Exception foe)
      {System.out.println("file_open_error="+foe.getMessage());}
  }

  public void close()
  {
    setFile();
    try
    {
      fw.close();
    } catch(Exception foe)
      {System.out.println("file_close_error="+foe.getMessage());}
  }

  private void setInitStatus()
  {
    initFileOpen();
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
//          key_v.addElement(key);
        }
      } catch(Exception bre)
        {System.out.println("buffer_read_error="+bre.getMessage());}
    }
    else
    {
      h = new Hashtable();
    }
    initFileClose();
    fileOpen();
  }

  public void setProfile(String key, String i_value)
  {
    h.put(key,i_value);
/*    if (key_v.isEmpty())
    {
      key_v.addElement(key);
    }
    else
    {
      if (!key_v.contains(key))
      {
        key_v.addElement(key);
      }
    }*/
  }

  public void setProfiles(String key, String[] i_value_arr)
  {
    h.put(key,dataParsing(i_value_arr));
/*    if (key_v.isEmpty())
    {
      key_v.addElement(key);
    }
    else
    {
      if (!key_v.contains(key))
      {
        key_v.addElement(key);
      }
    }*/
  }

  public void deleteProfile(String key)
  {
  		
/*    if (!key_v.isEmpty())
    {
      if (key_v.contains(key))
      {
        key_v.removeElement(key);
      }
    }*/
  }

  private String dataParsing(String[] values)
  {
    StringBuffer outBuffer = new StringBuffer(1024);
    int i=0;
    for (; i<values.length-1; i++)
    {
      outBuffer.append(values[i]).append(delimiter);
    }
    outBuffer.append(values[i]);
    return outBuffer.toString();
  }

  private void setFile() //throws IOException, Exception
  {
  		try {
  		Object[] keys = h.keySet().toArray();
  		String values = null; 
  		for (int i=0; i<keys.length; i++) {
  			values = (String)h.get(keys[i]);
  			fw.write("["+(String)keys[i]+"]"+values+"\n");
  		}
  	} catch (IOException e) {
  			System.out.println(e.getMessage());
  		}
  		
/*    try
    {
      Enumeration vEum = key_v.elements();
      while(vEum.hasMoreElements())
      {
        key = (String) vEum.nextElement();
        value = (String) h.get(key);
        outString = "["+key+"]"+value;
        fw.write(outString+"\n");
      }
      key_v.removeAllElements();
    } catch(Exception ene)
      { System.out.println("set_file_error="+ene.getMessage());}*/
  }
}