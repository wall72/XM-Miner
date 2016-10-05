package xmminer.xmserver.xmgraph.xmdnode.xmdnodecalculate;

import java.io.*;
import java.util.*;
import java.lang.*;
import java.lang.Math;
import java.util.Vector;
import xmminer.xmlib.xmtable.*;
import xmminer.xmlib.xmcompute.*;
import xmminer.xmlib.xmfileprocess.*;

public class CXMCalculater
{
    //private String init_file = "arc0";

    private Hashtable pre_cal_h = new Hashtable();
    private Hashtable post_cal_h = new Hashtable();
    private Hashtable column_h;
    private Hashtable new_column_h;
    private String project;
    private String str_value;
    private String arc;
    private String unique_file_name;
    private String column_name;
    private String max_freq_value;
    private String[] column_list;
    private String[] column_index;
    private String[] new_column_list;
    private String[] new_column_index;
    private String[] cal_result_list;
    private int h_size;
    private int index_value;
    private int avr_numerator;
    private int miss_cnt;
    private int total_rows;
    private int btree_value;
    private int max_freq_cnt;
    private int[] cal_option;
    private double average;
    private double double_value;
    private double avr_denominator;
    private double max;
    private double min;
    private double miss;
    private boolean column_exist = false;
    private boolean new_column_exist = false;
    private boolean column_set = false;
    private boolean new_column_set = false;
    private boolean cal_max_init;
    private boolean cal_min_init;
    private Vector index_v;
    private CXMMetaDataReader cdr;
    private CXMDataQuerier cdq;
    private CXMNewDataQuerier cnq;
    private CXMFilteredValueCount cfc;
    private CXMFileInfo cfi;
    private CXMTableSaver cts;
    private CXMNullCheck cnc = new CXMNullCheck();
    private Hashtable cal_result_file = new Hashtable();


  public void setPreCalculateStatus(String project_name,String previous_arc, Hashtable i_hashtable)
  {
      project = project_name;
      arc = previous_arc;
      pre_cal_h = i_hashtable;
      getMetaData();
      setColumnHashtable();
      if (column_exist)
      {
      	  cdq = new CXMDataQuerier();
      	  cdq.setFileStatus(project,arc,null,null);
      }
      if (new_column_exist)
      {
      	  cnq = new CXMNewDataQuerier();
      	  cnq.setFileStatus(project,arc,null,null);
      }
      cfi = new CXMFileInfo();
      cfi.setFileStatus(project,arc);
  }

  public void setColumnName(String i_str)
  {
        column_name = i_str;
  	if (column_h.containsKey(column_name))
  	{
  		index_value = Integer.parseInt((String) column_h.get(column_name));
  		cdq.setSelectColumnIndex(index_value);
  		column_set = true;
  		new_column_set = false;
  	}
  	else
  	{
  		if (new_column_h.containsKey(column_name))
  		{
  		       index_value = Integer.parseInt((String) new_column_h.get(column_name));
  		       cnq.setSelectColumnIndex(index_value);
  		       column_set = false;
  		       new_column_set = true;
  		}
  		else
  		{
  			column_set = false;
  			new_column_set = false;
  		}
  	}
  	cal_option = (int[]) pre_cal_h.get(column_name);
  	setCalInitStatus();
  }

  private void setCalInitStatus()
  {
  	cal_result_list = new String[5];
  	avr_numerator = 0;
  	avr_denominator = 0;
  	average = 0;
  	cal_max_init = false;
  	cal_min_init = false;
  	miss_cnt = 0;
  	max_freq_cnt = 0;

  	if (cal_option[3]==1)
     	{
  	    cfc = new CXMFilteredValueCount();
            cfi.setColumnName(column_name);
            unique_file_name = arc+"_"+cfi.getColumnSequence();
            cal_result_file.put(column_name,unique_file_name);
       }
  }

  public void numericCalculate(int i_number)
  {
     	try
     	{
     	    if (column_set)
     	    {
     	        str_value = cdq.getStringInColumn(i_number);
     	    }
     	    else
     	    {
     	    	if (new_column_set)
     	    	{
     	    		str_value = cnq.getStringInColumn(i_number);
     	       }
     	       else
     	       {
     	    	      str_value = "";
     	    	}
     	    }
     	} catch(Exception me)
     	   {
     	   	System.out.println("type match error="+me.getMessage());
     	   }

       if (cnc.nullCheck(str_value))
       {
       	   if (cal_option[4]==1)
     	   {
     	      miss_cnt = miss_cnt + 1;
     	   }
       }
       else
       {
     	   double_value = Double.parseDouble(str_value);

     	   if (cal_option[0]==1)
     	   {
     	       calAverage(double_value);
     	   }

     	   if (cal_option[1]==1)
     	   {
     	       calMax(double_value);
     	   }

     	   if (cal_option[2]==1)
     	   {
     	      calMin(double_value);
     	   }

     	   if (cal_option[3]==1)
     	   {
             btree_value = cfc.getValueCount(str_value);
             if (btree_value>=max_freq_cnt)
             {
            	  max_freq_cnt = btree_value;
            	  max_freq_value = str_value;
             }
             cfc.setValueCount(str_value,btree_value+1);
          }
     	}
  }

  public void nonNumericCalculate(int i_number)
  {
  	try
     	{
     	    if (column_set)
     	    {
     	        str_value = cdq.getStringInColumn(i_number);
     	    }
     	    else
     	    {
     	    	if (new_column_set)
     	    	{
     	    		str_value = cnq.getStringInColumn(i_number);
     	       }
     	       else
     	       {
     	    	      str_value = "";
     	    	}
     	    }
     	} catch(Exception me)
     	   {
     	   	System.out.println("type match error="+me.getMessage());
     	   }

     	if  (cnc.nullCheck(str_value))
     	{
  	     if (cal_option[4]==1)
     	     {
       	   	miss_cnt = miss_cnt + 1;
            }
     	}
     	else
     	{

     	   if (cal_option[3]==1)
     	   {
             btree_value = cfc.getValueCount(str_value);
             if (btree_value>=max_freq_cnt)
             {
            	  max_freq_cnt = btree_value;
            	  max_freq_value = str_value;
             }
             cfc.setValueCount(str_value,btree_value+1);
          }
       }
  }

  public void numericSetCalResult()
  {
  	calMissingPercentage();
  	cal_result_list[0] = String.valueOf(average);
  	cal_result_list[1] = String.valueOf(max);
  	cal_result_list[2] = String.valueOf(min);
  	cal_result_list[3] = " ";
  	cal_result_list[4] = String.valueOf(miss);
  	post_cal_h.put(column_name,cal_result_list);

  }

  public void nonNumericSetCalResult()
  {
        if (cal_option[3]==1)
        {
            CXMUniqueValueTableSaver uts = new CXMUniqueValueTableSaver();

            //debug init
            System.out.println("project="+project);
            System.out.println("unique_file_name="+unique_file_name);
            Hashtable temp = new Hashtable();
            temp = cfc.getValueTable();
            System.out.println("temp_size="+temp.size());
            try
            {
               System.out.println("111");
               Enumeration hkey = temp.keys();
               System.out.println("222");
               while(hkey.hasMoreElements())
               {                  
                   String a = (String) hkey.nextElement();
                   System.out.println("key ="+ a);
                   System.out.println("count ="+ ((Integer) temp.get(a)).toString());

               }
            } catch(Exception cte)
               { System.out.println("htable_error="+cte.getMessage());}
            //debug end

            uts.createUniqueValueFile(project, unique_file_name, cfc.getValueTable());
            cfc.close();
        }
  	calMissingPercentage();
  	cal_result_list[0] = " ";
  	cal_result_list[1] = " ";
  	cal_result_list[2] = " ";
  	cal_result_list[3] = max_freq_value;
  	cal_result_list[4] = String.valueOf(miss);
  	post_cal_h.put(column_name,cal_result_list);
  }

  public Hashtable setPostCalculateStatus()
  {

      if (column_exist)
      {
      	  cdq.close();
      }
      if (new_column_exist)
      {
      	  cnq.close();
      }

      return post_cal_h;
  }

  public Hashtable getCalResultFile()
  {
      return cal_result_file;
  }

  public void setColumnHashtable()
  {
      if (!cnc.nullCheck(column_list))
      {
      	  column_h = new Hashtable();
      	  for (int i=0; i<column_list.length; i++)
      	  {
      	  	if (pre_cal_h.containsKey(column_list[i]))
      	  	{
      	  		column_h.put(column_list[i],column_index[i]);
      	  	}
      	  }
      	  column_exist = true;
      }
      if (!cnc.nullCheck(new_column_list))
      {
      	  new_column_h = new Hashtable();
      	  for (int i=0; i<new_column_list.length; i++)
      	  {
      	  	if (pre_cal_h.containsKey(new_column_list[i]))
      	  	{
      	  		new_column_h.put(new_column_list[i],new_column_index[i]);
      	  	}
      	  }
      	  new_column_exist = true;
      }
  }

  private void getMetaData()
  {
  	cdr = new CXMMetaDataReader();
  	cdr.setFileStatus(project,arc);
  	column_list = cdr.getProfiles("OLD_COLUMN_LIST");
  	column_index = cdr.getProfiles("COLUMN_INDEX");
  	new_column_list = cdr.getProfiles("NEW_COLUMN_LIST");
  	new_column_index = cdr.getProfiles("NEW_COLUMN_INDEX");
  	total_rows = Integer.parseInt(cdr.getProfile("NUMBER_OF_ROWS"));
  	cdr.close();
  	cdr = new CXMMetaDataReader();
  	cdr.setFileStatus(project,arc);
  	cdr.close();
  }

  public void calAverage(double i_value)
  {
    avr_denominator = avr_denominator + i_value;
    avr_numerator = avr_numerator + 1;
    average = avr_denominator/avr_numerator;
  }

  public void calMax(double i_value)
  {
      if (!cal_max_init)
      {
          max = i_value;
          cal_max_init = true;
      }
      else
      {
          if (max<=i_value)
          {
              max = i_value;
          }
      }
  }

  public void calMin(double i_value)
  {
      if (!cal_min_init)
      {
          min = i_value;
          cal_min_init = true;
      }
      else
      {
          if (min>=i_value)
          {
             min = i_value;
          }
      }
  }

  public void calMissingPercentage()
  {
      miss = java.lang.Math.round((miss_cnt*100)/total_rows);
  }

}