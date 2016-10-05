package xmminer.xmlib.xmcompute;

import java.io.*;
import java.util.Vector;
import xmminer.xmlib.xmtable.*;
import xmminer.xmlib.xmfileprocess.*;

public class CXMColumnSort
{
  private byte[] left_byte;
  private byte[] right_byte;
  private String project;
  private String arc;
  private String column;
  private String file_path;
  private String type;
  private String sort_property;
  private String left_str = "";
  private String right_str = "";
  private String center_str = "";
  private String sort_index_file_name;
  private String[] column_property;
  private int row_numbers;
  private int byte_size;
  private int pivot;
  private int out_int_value;
  private int factor_int;
  private int temp_int_value;
  private long left_pos;
  private long right_pos;
  private double left_double;
  private double right_double;
  private double center_double;
  private File data_file;
  private File row_index_file;
  private File col_index_file;
  private File sort_index_file;
  private RandomAccessFile sif;  
  private CXMTableQuerier ctq;
  private CXMFileInfo cfi;

  public void setFileStatus(String project_name, String arc_name, String column_name)
  {
      project = project_name;
      System.out.println("project="+project);
      arc = arc_name;
      System.out.println("arc="+arc);
      column = column_name;
      System.out.println("column="+column);
      cfi = new CXMFileInfo();
      System.out.println("ss1");
      cfi.setFileStatus(project,arc);
      System.out.println("ss2");
      cfi.setColumnName(column);
      System.out.println("ss3");
      setInitStatus();
      System.out.println("ss4");
  }

  private void setInitStatus()
  {
      file_path = cfi.getIndexPath();
      System.out.println("file_path="+file_path);
      CXMMetaDataReader mdr = new CXMMetaDataReader();
       System.out.println("n1");
      mdr.setFileStatus(project,arc);
      System.out.println("n2");
      row_numbers = Integer.parseInt(mdr.getProfile("NUMBER_OF_ROWS"));
      System.out.println("row_numbers="+row_numbers);
      column_property = mdr.getProfiles(column);      
      type = column_property[0];
      System.out.println("type="+type);
      mdr.close();
      System.out.println("n3");;
  }

  public void sort()
  {
      fileOpen();
      setPivot();
      int r;
      if (type.equals("REAL")||type.equals("INTEGER"))
      {
          r = doubleSort(1,row_numbers);
      }
      else
      {
          r = stringSort(1,row_numbers);
      }

      if (r==0)
      {
         CXMMetaDataSaver cms = new CXMMetaDataSaver();
         cms.setFileStatus(project,arc);
         column_property[4] = "sorted";
         cms.setProfiles(column,column_property);
         cms.close();
      }
  }

  private void fileOpen()
  {
      try
      {
          row_index_file = new File(file_path+arc+".nidx");
          byte_size = (int) row_index_file.length()/row_numbers;
          sort_index_file_name = file_path+arc+"_"+cfi.getColumnSequence()+".sidx";
    	  sort_index_file = new File(sort_index_file_name);
          sort_index_file.delete();
    	  sif = new RandomAccessFile(sort_index_file,"rw");
    	  setNumberIndex();
    	  ctq = new CXMTableQuerier();
    	  ctq.setFileStatus(project,arc,column,null);
      } catch(Exception foe)
        {
           pm("file_open_error=",foe.getMessage());
        }
  }

  public int getByteSize()
  {
      return byte_size;
  }

  public String getSortIndexFileName()
  {
      return sort_index_file_name;
  }

  private void setNumberIndex()
  {
      int quot_int = 0;
      int mod_int = 0;
      int temp_int_value = 0;
      byte[] num_index = new byte[byte_size];
      for (int i=1; i<row_numbers+1; i++)
      {
          temp_int_value = i;
          for (int j=0; j<byte_size; j++)
          {
               quot_int = temp_int_value/128;
               mod_int = temp_int_value%128;
               num_index[j] = (byte) mod_int;
               temp_int_value = quot_int;
          }
          try
          {
               sif.write(num_index);
          } catch (Exception nie)
             {
                  System.out.println("sort_index_create_error="+nie.getMessage());
             }
      }
  }

  public void close()
  {
      try
      {
    	  ctq.close();
    	  sif.close();
      } catch(Exception fce)
        {
            pm("file_close_error=",fce.getMessage());
        }
  }

  private void setPivot()
  {
      left_byte = new byte[byte_size];
      right_byte = new byte[byte_size];
      int center_number = java.lang.Math.round((1+row_numbers)/2);
      if (type.equals("STRING"))
      {
          left_str = ctq.getStringInColumn(1);
          right_str = ctq.getStringInColumn(row_numbers);
          center_str = ctq.getStringInColumn(center_number);
          if ((left_str.compareTo(center_str)) > 0)
          {
              swap(1,center_number);
          }
          if ((left_str.compareTo(right_str)) > 0)
          {
              swap(1,row_numbers);
          }
          if ((center_str.compareTo(right_str)) > 0)
          {
              swap(center_number,row_numbers);
          }
          swap(center_number,row_numbers-1);
      }
      else
      {
          left_double = ctq.getDoubleInColumn(1);
          right_double = ctq.getDoubleInColumn(row_numbers);
          center_double = ctq.getDoubleInColumn(center_number);
          if (left_double > center_double)
          {
              swap(1,center_number);
          }
          if (left_double > right_double)
          {
              swap(1,row_numbers);
          }
          if (center_double > right_double)
          {
              swap(center_number,row_numbers);
          }
          swap(center_number,row_numbers-1);
       }
  }

 private int stringSort(int left_number, int right_number)
 {
     pivot = right_number;

     if(right_number > left_number)
     {
         int i = left_number;
         int j = right_number;
         while(true)
         {
             for (;i<right_number;)
             {
                 if (compareStringMoreThanOrEqual(i,pivot))
                 {
                      break;
                 }
                 else
                 {
                      i++;
                 }
             }
             for (;j>left_number;)
             {
                 j--;
                 if (compareStringLessThanOrEqual(j,pivot))
                 {
                      break;
                 }
             }
             if(i >= j)
             {
                 break;
             }
             swap(i, j);
         }

         if (compareStringMoreThanOrEqual(i,pivot))
         {
            swap(i , right_number);
         }
         stringSort(left_number, i-1);
         stringSort(i+1, right_number);
     }
     return (0);
  }

  private int doubleSort(int left_number, int right_number)
  {
      pivot = right_number;

      if(right_number > left_number)
      {
          int i = left_number;
          int j = right_number;
          while(true)
          {
              for (;i<right_number;)
              {
                  if (compareDoubleMoreThanOrEqual(i,pivot))
                  {
                      break;
                  }
                  else
                  {
                      i++;
                  }
             }
             for (;j>left_number;)
             {
                  j--;
                  if (compareDoubleLessThanOrEqual(j,pivot))
                  {
                      break;
                  }
             }
             if(i >= j)
             {
                 break;
             }
             swap(i, j);
          }

          if (compareDoubleMoreThanOrEqual(i,pivot))
          {
             swap(i , right_number);
          }
          doubleSort(left_number, i-1);
          doubleSort(i+1, right_number);
      }
      return (0);
  }

  private boolean compareStringMoreThanOrEqual(int left_number, int right_number)
  {
      left_str = ctq.getStringInColumn(left_number);
      right_str = ctq.getStringInColumn(right_number);
      return (left_str.compareTo(right_str)) >= 0;
  }

  private boolean compareStringLessThanOrEqual(int left_number, int right_number)
  {
      left_str = ctq.getStringInColumn(left_number);
      right_str = ctq.getStringInColumn(right_number);
      return (left_str.compareTo(right_str)) <= 0;
  }

  private boolean compareDoubleMoreThanOrEqual(int left_number, int right_number)
  {
      left_double = ctq.getDoubleInColumn(left_number);
      right_double = ctq.getDoubleInColumn(right_number);
      return (left_double >= right_double);
  }

  private boolean compareDoubleLessThanOrEqual(int left_number, int right_number)
  {
      left_double = ctq.getDoubleInColumn(left_number);
      right_double = ctq.getDoubleInColumn(right_number);
      return (left_double <= right_double);
  }

  private void swap(int left_number, int right_number)
  {
      left_pos = (left_number-1)*byte_size;
      right_pos = (right_number-1)*byte_size;
      try
      {
         sif.seek(left_pos);
         for (int i=0; i<byte_size; i++)
         {
             left_byte[i] = sif.readByte();
         }
         sif.seek(right_pos);
         for (int j=0; j<byte_size; j++)
         {
            right_byte[j] = sif.readByte();
         }
         sif.seek(left_pos);
         sif.write(right_byte);
         sif.seek(right_pos);
         sif.write(left_byte);
      } catch (Exception se)
        {
            pm("swap_error=",se.getMessage());
        }
  }

  private int setIntValue(byte[] i_byte)
  {
       out_int_value = 0;
       factor_int = 1;
       for (int i=0; i<byte_size; i++)
       {
    	   temp_int_value = (int) i_byte[i];
    	   out_int_value = out_int_value + temp_int_value*factor_int;
    	   factor_int = factor_int*128;
       }
       return out_int_value;
  }

  private void pm(String i_title, String i_msg)
  {
       System.out.println(i_title+"="+i_msg);
  }

  private void pba(String i_title, byte[] i_byte)
  {
       System.out.print(i_title + " : ");
       for (int i=0; i<i_byte.length; i++)
       {
    	   System.out.print(i_byte[i]+",");
       }
       System.out.print("\n");
  }

}