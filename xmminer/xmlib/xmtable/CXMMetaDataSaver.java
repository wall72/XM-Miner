package xmminer.xmlib.xmtable;

import java.io.*;
import java.util.*;

public class CXMMetaDataSaver
{

  private String root_path = System.getProperty("minerdir") + "/xmminer/user/";//"/XM-Miner/xmminer/user");  2002.09.22
  private String meta_path = "/meta/";
  private String set_meta_path;
  private String project;

  private String i_name="";
  private String input;
  private String key;
  private String value;
  private String outString;
  private String delimiter = "^";
  private int a;
  private int b;
  private boolean file_open=false;
  private Vector key_v = new Vector();
  private Hashtable h;
  private File meta_file;
  private BufferedReader br;
  private FileReader fr;
  private FileWriter fw;

  public CXMMetaDataSaver()
  {}

  public void setFileStatus(String project_name, String filename)
  {
    project = project_name;
    set_meta_path = root_path + project + meta_path;

    i_name = set_meta_path+filename;
    setInitStatus();
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
      {System.out.println("CXMMetaDataSaver : initFileOpen_error =" +re.getMessage());}
  }

  private void initFileClose()
  {
    try
    {
      if (file_open)
      {
         br.close();
         fr.close();
      }
    } catch(Exception ce)
      {System.out.println("CXMMetaDataSaver : initFileClose_error ="+ce.getMessage());}
  }

  private void fileOpen()
  {
    try
    {
      File meta_folder = new File(set_meta_path);
      if (!meta_folder.exists())
      {
          meta_folder.mkdirs();
      }
      fw = new FileWriter(meta_file);
    } catch(Exception foe)
      {System.out.println("CXMMetaDataSaver : fileOpen_error ="+foe.getMessage());}
  }

  public void close()
  {
    setFile();
    try
    {
      h.clear();
      fw.close();
    } catch(Exception foe)
      {System.out.println("CXMMetaDataSaver : close_error ="+foe.getMessage());}
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
          key_v.addElement(key);
        }
      } catch(Exception bre)
        {System.out.println("CXMMetaDataSaver : setInitStatus_error ="+bre.getMessage());}
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
    if (key_v.isEmpty())
    {
      key_v.addElement(key);
    }
    else
    {
      if (!key_v.contains(key))
      {
        key_v.addElement(key);
      }
    }
  }

  public void setProfiles(String key, String[] i_value_arr)
  {
    h.put(key,dataParsing(i_value_arr));
    if (key_v.isEmpty())
    {
      key_v.addElement(key);
    }
    else
    {
      if (!key_v.contains(key))
      {
        key_v.addElement(key);
      }
    }
  }

  public void deleteProfile(String key)
  {
    if (!key_v.isEmpty())
    {
      if (key_v.contains(key))
      {
        key_v.removeElement(key);
      }
    }
  }

  public void deleteAllProfile()
  {
    if (!key_v.isEmpty())
    {
       key_v.removeAllElements();
    }
  }

  private String dataParsing(String[] i_str)
  {
    outString="";
    int i=0;
    for (i=0; i<i_str.length-1; i++)
    {
      outString = outString+i_str[i]+delimiter;
    }
    outString = outString+i_str[i];
    return outString;
  }

  private void setFile() //throws IOException, Exception
  {
    try
    {
      Enumeration vEum = key_v.elements();
      while(vEum.hasMoreElements())
      {
        key = (String) vEum.nextElement();
        value = (String) h.get(key);
        outString = "["+key+"]"+value;
        fw.write(outString+"\n");
      }
      key_v.clear();
    } catch(Exception ene)
      {}
  }
}