package xmminer.xmlib.xmtable;

import java.io.*;

public class CXMIndexSaver
{
  private String root_path = System.getProperty("minerdir") + "/xmminer/user/";//"/XM-Miner/xmminer/user"); ¼öÁ¤ 2002.09.22
  private String index_path = "/index/";
  private String set_index_path;
  private String project;    
  
  private byte[] num_index;
  private int byte_size;
  private int temp_int_value;
  private int quot_int;
  private int mod_int;
  private int r_numbers;
  private long pos;
  private File new_num_index_file;
  private File pre_num_index_file;  
  private String new_f_name = "";
  private String pre_f_name = "";
  private FileOutputStream nis;
  private RandomAccessFile raf;


  ///*** declare and set init status ***
  public CXMIndexSaver()
  {}

  public void setFileStatus(String project_name, String new_index_file, String previous_index_file, int row_numbers)
  {
    
    project = project_name;
    set_index_path = root_path + project + index_path;
        
    new_f_name = set_index_path+new_index_file;
    pre_f_name = set_index_path+previous_index_file;  
    r_numbers = row_numbers;  
    indexFileOpen();    
    num_index = new byte[byte_size];   
  }

  private void indexFileOpen()
  {
    try
    {
      new_num_index_file = new File(new_f_name+".nidx");
      pre_num_index_file = new File(pre_f_name+".nidx");      
      raf = new RandomAccessFile(pre_num_index_file,"r");
      nis = new FileOutputStream(new_num_index_file);
      byte_size = (int) raf.length()/r_numbers;    
    } catch(Exception foe)
      {System.out.println("index_file_open_error="+foe.getMessage());}
  }
  
  public void close()
  {
    try
    {
      raf.close();
      nis.close();
    } catch(Exception fce)
      {System.out.println("index_file_close_error="+fce.getMessage());}	
  }

  public void setNumberIndex(int row_number)
  {    
    pos = (long) (row_number-1)*byte_size;
    try
    {
      raf.seek(pos);
      for (int i=0; i<byte_size; i++)
      {
        num_index[i] = raf.readByte();
      }
      nis.write(num_index);     
    } catch (Exception ne)
      {
        System.out.println("number_index_create_error="+ne.getMessage());
      }       
  } 
  
  //+++ end ***
}

