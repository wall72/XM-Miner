
//Title:        Data Manager
//Version:      
//Copyright:    Copyright (c) 1999
//Author:       이승희
//Company:      대우정보시스템
//Description:  Your description
package xmminer.xmserver.xmgraph.xmdnode.xmdnodepartitioning;

import java.io.*;
import java.lang.Math;
import java.lang.Integer;
import java.util.Random;
import java.math.BigInteger;
import xmminer.xmlib.xmtable.*;
import xmminer.xmserver.xmgraph.xmdnode.xmredundantcheck.*;


public class CXMPartitioning
{ 
  private String t_arc = "";
  private String v_arc = "";
  private String p_arc = "";
  private int total_rows=0; 
  private int p_index = 0;
  private CXMIndexSaver training_cis;
  private CXMIndexSaver validation_cis;
  private CXMRedundantCheck crc;

  public void setTotalRows(int i_num)
  {
    total_rows = i_num;
  }

  public void setArc(String previous_arc, String training_arc, String validation_arc)
  {
    p_arc = previous_arc;
    t_arc = training_arc;
    v_arc = validation_arc;    
  }

  public int getTotalRows()
  {
    return total_rows;
  }

  public int calNumToPer(int i_num) //사용자가 입력한 행수를 백분율로 바꾸어 줌
  {
    float partitioning_percent = ((i_num*100)/total_rows);
    int rounded_partitioning_percent = java.lang.Math.round(partitioning_percent); //백분율을 반올림
    return rounded_partitioning_percent;
  }

  public int calPerToNum(int i_percent) //사용자가 입력한 백분율을 행수로 바꾸어 줌
  {
    float partitioning_number = (total_rows*i_percent)/100;
    int rounded_partitioning_number = java.lang.Math.round(partitioning_number); //열수를 반올림
    return rounded_partitioning_number;
  }
  
  public void setPrePartitioningStatus(String project_name, int partition_index)
  {    
      p_index = partition_index;
      if (p_index==0)
      {
          crc = new CXMRedundantCheck(project_name,getByteSize()); //total number를 setting한 후 선언
          crc.fileOpen();
      }
      training_cis = new CXMIndexSaver();
      training_cis.setFileStatus(project_name, t_arc,p_arc,total_rows);
      validation_cis = new CXMIndexSaver();
      validation_cis.setFileStatus(project_name, v_arc,p_arc,total_rows);   
  }
  
  public void indexDataCreate(int i_int)
  {
     crc.dataInsert(i_int);
  }
  
  public void setIndexFile()
  {
     crc.fileClose();
     crc.fileOpen();     
  }
  
  public void setPostPartitioningStatus()
  {
     if (p_index==0)
     {
         crc.fileClose();     
     }
     training_cis.close(); 
     validation_cis.close();  	     
  }
  
  public boolean dataFind(int i_int)
  {
  	return crc.dataFind(i_int);
  }
  
  public void setTrainingNumberIndex(int i_int)
  {
     training_cis.setNumberIndex(i_int);
  }
  
  public void setValidationNumberIndex(int i_int)
  {
     validation_cis.setNumberIndex(i_int);
  }

  public int getByteSize()
  {
    int temp_value = total_rows;
    int quot;
    int mod;
    int cnt = 0;
    do
    {
      quot = temp_value/128;
      mod = temp_value%128;
      temp_value = quot;
      cnt++;
    } while(quot!=0);
    return cnt;
  }  
 

}