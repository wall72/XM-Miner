package xmminer.xmlib.xmtable;

import java.io.*;
import java.util.*;
import xmminer.xmlib.xmfileprocess.*;

public class CXMMetaDataReader
{
  private String root_path = System.getProperty("minerdir") + "/xmminer/user/";//"/XM-Miner/xmminer/user"); 수정 2002.09.22
  private String meta_path = "/meta/";
  private String lib_path = System.getProperty("minerdir") + "/xmminer/xmlib/xmlibmeta/";//"/XM-Miner/xmminer/xmlib/xmlibmeta/"); 수정 2002.09.22
  private String set_meta_path;


  private String project;
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
  private CXMNullCheck cnc = new CXMNullCheck();

  public CXMMetaDataReader()
  {}

  public void setFileStatus(String project_name, String file_name)
  {
    project = project_name;
    set_meta_path = root_path + project + meta_path;
    if (file_name.equals("datatype")||file_name.equals("function"))
    {
       i_name = lib_path + file_name;
    }
    else
    {
       i_name = set_meta_path+file_name;
    }
    fileOpen();
  }

  private void fileOpen()
  {
    try
    {
      meta_file = new File(i_name+".meta");
      fr = new FileReader(meta_file);
      br = new BufferedReader(fr);
      file_open=true;
      setInitStatus();
    } catch(Exception foe)
      {System.out.println("CXMMetaDataReader : fileOpen_error="+foe.getMessage());
	  return;}
  }

  public void close()
  {
    try
    {
      h.clear();
      fr.close();
      br.close();
    } catch(Exception fce)
      {System.out.println("CXMMetaDataReader : close_error="+fce.getMessage());}
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
        {System.out.println("CXMMetaDataReader : setInitStatus_error="+bre.getMessage());}
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
    if (key.equals("COLUMN_LIST"))
    {
        return getAllColumnList();
    }
    else if (key.equals("OLD_COLUMN_LIST"))
    {
        return getOldColumnList();
    }
    else if (key.equals("NEW_COLUMN_LIST"))
    {
        return getNewColumnList();
    }
    else
    {
        return getProfileArray(key);
    }
  }

  private String[] getAllColumnList()
  {
      String[] temp1;
      String[] temp2;
      temp1 = getOldColumnList();
      temp2 = getNewColumnList();
      if (!cnc.nullCheck(temp1))
      {
          if (!cnc.nullCheck(temp2))
          {
             return resetListArray(temp1, temp2);
          }
          else
          {
            return temp1;
          }
      }
      else
      {
          if (!cnc.nullCheck(temp2))
          {
             return temp2;
          }
          else
          {
             return null;
          }
      }
  }

  private String[] resetListArray(String[] arr1, String[] arr2)
  {
      int a = arr1.length;
      int b = arr2.length;
      String[] list = new String[a+b];
      for (int i=0; i<a; i++)
      {
          list[i] = arr1[i];
      }
      for (int j=0; j<b; j++)
      {
         list[a+j] = arr2[j];
      }
      return list;
  }

  private String[] getOldColumnList()
  {
      return getProfileArray("COLUMN_LIST");
  }

  private String[] getNewColumnList()
  {
      return getProfileArray("NEW_COLUMN_LIST");
  }

  private String[] getProfileArray(String key)
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
      value_v.clear();
    } catch(Exception ene)
      { System.out.println("CXMMetaDataReader : vectorToStringArray_error="+ene.getMessage());}
  }

}

