package xmminer.xmserver.xmgraph.xmdnode.xmdnodedataquery;

import java.io.*;
import xmminer.xmlib.xmfileprocess.*;

public class XMDNodeNewIndexCreate
{
  private String root_path = System.getProperty("minerdir") + "/xmminer/user/";//"/XM-Miner/xmminer/user"); ¼öÁ¤ 2002.09.22
  private String index_path = "/index/";
  private String set_index_path;
  private String project;
  private String n_arc;
  private int byte_size;
  private long pos;
  private boolean second_table_check = false;
  private RandomAccessFile nif;
  private RandomAccessFile new_nif;
  private RandomAccessFile nif_second;
  private RandomAccessFile new_nif_second;
  private byte[] index_byte;
  private byte[] index_byte_second;
  private File index_file;
  private File new_index_file;
  private File index_file_second;
  private File new_index_file_second;
  private CXMNullCheck cnc = new CXMNullCheck();

  public XMDNodeNewIndexCreate()
  {

  }

  public void setFileStatus(String project_name, String next_arc, String row_index, String new_row_index, int row_numbers)
  {
      project = project_name;
      n_arc = next_arc;
      set_index_path = root_path + project + index_path;
      index_file = new File(set_index_path+row_index+".nidx");    
      byte_size = (int) index_file.length()/row_numbers;  
      new_index_file = new File(set_index_path+n_arc+".nidx");
      new_index_file.delete();    
      if (!cnc.nullCheck(new_row_index))
      {
           index_file_second = new File(set_index_path+new_row_index+".nidx");    
           new_index_file_second = new File(set_index_path+"new_"+n_arc+".nidx");
           new_index_file_second.delete();          
           second_table_check = true;
      }
      templateFileOpen();
  }

  public void close()
  {
      try
      {
          nif.close();
          new_nif.close();
          if (second_table_check)
          {
                nif_second.close();
                new_nif_second.close();    
          }
      } catch(Exception fce)
        {
           System.out.println("file_close_error="+fce.getMessage());
        }
  }

  public void setNewRowIndex(int row_number)
  {
      getNumberIndex(row_number);
      setNewNumberIndex();
  }

  private void templateFileOpen()
  {
      try
      {
           nif =  new RandomAccessFile(index_file,"r");
           new_nif = new RandomAccessFile(new_index_file,"rw");
           index_byte = new byte[byte_size];
           if (second_table_check)
           {
                   nif_second =  new RandomAccessFile(index_file_second,"r");
                   new_nif_second = new RandomAccessFile(new_index_file_second,"rw");    
                   index_byte_second = new byte[byte_size];
           }
           
       } catch(Exception foe)
         {System.out.println("template_file_open_error="+foe.getMessage());}
  }

  private void getNumberIndex(int i_int)
  {
    pos = (long) (i_int-1)*byte_size;
    try
    {
      nif.seek(pos);
      for (int i=0; i<byte_size; i++)
      {
        index_byte[i] = nif.readByte();
      }
      if (second_table_check)
      {
          nif_second.seek(pos);
          for (int i=0; i<byte_size; i++)
          {
              index_byte_second[i] = nif_second.readByte();
          }            
      }
    } catch (Exception gie)
      {
        System.out.println("get_number_index_error="+gie.getMessage());
      }
  }

  private void setNewNumberIndex()
  {
    try
    {
      new_nif.write(index_byte);
      if (second_table_check)
      {
          new_nif_second.write(index_byte_second);
      }
    } catch (Exception sie)
      {
        System.out.println("set_new_number_index_error="+sie.getMessage());
      }
  }

}