package xmminer.xmserver.xmgraph.xmdnode.xmredundantcheck;

import java.io.*;
import xmminer.xmlib.xmfileprocess.*;

public class CXMRedundantCheck
{
  
  private String root_path = System.getProperty("minerdir") + "/xmminer/user/";//"/XM-Miner/xmminer/user"); ¼öÁ¤ 2002.09.22
  private String index_path = "/index/";
  private String set_index_path;
  private String project;    
  
  private byte[] byte_value;
  private byte[] read_byte_value;
  private int temp_value = 0;
  private int quot = 0;
  private int mod = 0;
  private int size = 0;
  private int out_value = 0;
  private int factor = 0;
  private long pos = 0;
  private boolean check_value = false;
  private RandomAccessFile r; 

  public CXMRedundantCheck(String project_name, int i_int)
  {
    project = project_name;
    set_index_path = root_path + project + index_path;
    
    byte_value = new byte[i_int+1];
    size = i_int;
    File deleteFile = new File(set_index_path+"rcheck.indx");
    deleteFile.delete();
  }

  public void fileOpen()
  {
    try
    {
      r = new RandomAccessFile(set_index_path+"rcheck.indx","rw");
    } catch(Exception re)
      {}
  }

  public void fileClose()
  { 
    try
    {
      r.close();
    } catch(Exception re)
      {}
  }                             

  public void dataInsert(int i_int)
  {
    byte_value = setByteValue(i_int);
    try
    {
      r.write(byte_value);
    } catch(Exception re)
      {}
  }

  public boolean dataFind(int i_int)
  {
    pos = (long) (i_int-1)*(size+1);
    try
    {
      r.seek(pos);
      if (r.readByte()==1)
      {
        check_value = true;
      }
      else
      {
        for (int i=0; i<size; i++)
        {
          byte_value[i] = r.readByte();
        }
        if (i_int == setIntValue(byte_value))
        {
          r.seek(pos);
          r.writeByte(1);
        }
        check_value = false;
      }
    } catch(Exception re)
      {System.out.println("data_file_error="+re.getMessage());}
    return check_value;
  }

  private byte[] setByteValue(int i_int)
  {
    temp_value = i_int;
    byte_value[0] = 0;
    for (int i=0; i<size; i++)
    {
      quot = temp_value/128;
      mod = temp_value%128;
      byte_value[i+1] = (byte) mod;
      temp_value = quot;
    }
    return byte_value;
  }

  private int setIntValue(byte[] i_byte)
  {
    out_value = 0;
    factor = 1;
    for (int i=0; i<size; i++)
    {
    	temp_value = (int) i_byte[i];
    	out_value = out_value + temp_value*factor;
    	factor = factor*128;
    }
    return out_value;
  }

  private void deleteFile(String d, String e)
  {
    CXMExtensionFilter filter = new CXMExtensionFilter(e);
    File dir = new File(d);
    String[] list = dir.list(filter);
    File file;
    boolean del_fl = false;
    if (list.length == 0)  return;
    for (int i = 0; i < list.length; i++)
    {
      file = new File((list[i]));
      del_fl = file.delete();
    }
  }
}

