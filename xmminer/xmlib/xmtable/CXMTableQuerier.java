package xmminer.xmlib.xmtable;

import java.util.*;
import java.util.Vector;
import xmminer.xmlib.xmfileprocess.*;

public class CXMTableQuerier
{

  byte[] out_byte_arr;
  private String f_name;
  private String c_name;
  private String select_column_name;
  private String new_select_column_name;
  private String new_value;
  private String out_str;
  private String project;
  private String[] c_name_list;
  private String[] column_list;
  private String[] new_column_list;
  private String[] column_index;
  private String[] new_column_index;
  private String[] select_column_list;
  private String[] new_select_column_list;
  private String[] trans_value_arr;
  private String[] new_trans_value_arr;
  private String[] out_str_arr;
  private String[] temp_str_arr1;
  private String[] temp_str_arr2;
  private int column_length;
  private int new_column_length;
  private int select_column_length;
  private int new_select_column_length;
  private int row_numbers;
  private int column_index_number;
  private int new_column_index_number;
  private int out_int;
  private int out_status;
  private int array_size;
  private int[] out_int_arr;
  private int[] temp_int_arr1;
  private int[] temp_int_arr2;
  private double max;
  private double min;
  private double out_double;
  private double[] out_double_arr;
  private double[] temp_double_arr1;
  private double[] temp_double_arr2;
  private boolean column_set = false;
  private boolean column_list_set = false;
  private boolean file_set = false;
  private boolean new_file_set = false;
  private boolean new_column_set = false;
  private boolean new_column_list_set = false;
  private Vector column_v;
  private Vector new_column_v;
  private Vector select_column_v;
  private Vector new_select_column_v;
  private Vector trans_v = new Vector();
  private Vector ass_trans_v = new Vector();
  private CXMDataQuerier cdq;
  private CXMNewDataQuerier cnq;
  private CXMMetaDataReader cmr;

  public CXMTableQuerier()
  {}

  public void setFileStatus(String project_name, String arc_name, String column_name, String[] column_name_list)
  {
    project = project_name;
    f_name = arc_name;
    c_name = column_name;
    c_name_list = column_name_list;
    cmr = new CXMMetaDataReader();
    cmr.setFileStatus(project,arc_name);
    column_list = cmr.getProfiles("OLD_COLUMN_LIST");
    column_index = cmr.getProfiles("COLUMN_INDEX");
    new_column_list = cmr.getProfiles("NEW_COLUMN_LIST");
    row_numbers = Integer.parseInt(cmr.getProfile("NUMBER_OF_ROWS"));
    new_column_index = cmr.getProfiles("NEW_COLUMN_INDEX");
    if (!nullCheck(column_list))
    {
        file_set = true;
        column_v = new Vector();
        select_column_v = new Vector();
        column_length = column_list.length;
        for (int i=0; i<column_list.length; i++)
        {
            column_v.addElement(column_list[i]);
        }
        if (c_name != null)
        {
        	if (column_v.contains(c_name))
        	{
        	    select_column_name = c_name;

        	    cdq = new CXMDataQuerier();
        	    cdq.setFileStatus(project,f_name,select_column_name,null);
        	    column_set = true;
        	}
        }
        else
        {
             if (!nullCheck(c_name_list))
             {
                  for (int j=0; j<c_name_list.length; j++)
                  {
                       if (column_v.contains(c_name_list[j]))
                       {
                              select_column_v.addElement(c_name_list[j]);
                       }
                  }
                  select_column_length = select_column_v.size();
                  select_column_list = new String[select_column_length];
                  for (int k=0; k<select_column_length; k++)
                  {
                       select_column_list[k] = (String) select_column_v.elementAt(k);
                  }
                  cdq = new CXMDataQuerier();
                  cdq.setFileStatus(project,f_name,null,select_column_list);
                  column_list_set = true;
             }
        }
    }
    if (!nullCheck(new_column_list))
    {
        new_file_set = true;
        new_column_v = new Vector();
        new_select_column_v = new Vector();
        new_column_length = new_column_list.length;
        for (int i=0; i<new_column_list.length; i++)
        {
            new_column_v.addElement(new_column_list[i]);
        }
        if (c_name != null)
        {
        	if (new_column_v.contains(c_name))
        	{
        	    new_select_column_name = c_name;
        	    cnq = new CXMNewDataQuerier();
        	    cnq.setFileStatus(project,f_name,new_select_column_name,null);
        	    new_column_set = true;
        	}
        }
        else
        {
             if (!nullCheck(c_name_list))
             {
                  for (int j=0; j<c_name_list.length; j++)
                  {
                       if (new_column_v.contains(c_name_list[j]))
                       {
                              new_select_column_v.addElement(c_name_list[j]);
                       }
                  }
                  new_select_column_length = new_select_column_v.size();
                  new_select_column_list = new String[new_select_column_length];
                  for (int k=0; k<new_select_column_length; k++)
                  {
                       new_select_column_list[k] = (String) new_select_column_v.elementAt(k);
                  }
                  cnq = new CXMNewDataQuerier();
                  cnq.setFileStatus(project,f_name,null,new_select_column_list);
                  new_column_list_set = true;
             }
        }
    }
    setInitStatus();
  }

  private void setInitStatus()
  {
      if (column_list_set)
      {
          if (new_column_list_set)
          {
               array_size = select_column_length + new_select_column_length;
               out_status = 0;
          }
          else
          {
               array_size = select_column_length;
               out_status = 1;
          }
          setOutArray();
      }
      else
      {
          if (new_column_list_set)
          {
               array_size = new_select_column_length;
               setOutArray();
               out_status = 2;
          }
          else
          {
               out_status = 3;
          }
      }

  }

  private void setOutArray()
  {
      out_int_arr = new int[array_size];
      out_str_arr = new String[array_size];
      out_double_arr = new double[array_size];
  }

  public Vector getTransaction(int row_number)
  {
       trans_v.clear();
       if (file_set)
       {
       	    trans_value_arr = cdq.getTransactionValue(row_number);
           for (int i=0; i<column_length; i++)
           {
               new_value = column_list[i]+"_"+trans_value_arr[i];
               trans_v.addElement(new_value);
           }
       }
       if (new_file_set)
       {
       	    new_trans_value_arr = cnq.getTransactionValue(row_number);
           for (int i=0; i<new_column_length; i++)
           {
               new_value = new_column_list[i]+"_"+new_trans_value_arr[i];
               trans_v.addElement(new_value);
           }
       }
       return trans_v;
  }

  public Vector getTransactionValueInColumnList(int row_number)
  {
       trans_v.clear();
       if (column_list_set)
       {
       	    trans_value_arr = cdq.getTransactionValueInColumnList(row_number);
           for (int i=0; i<select_column_length; i++)
           {
               new_value = select_column_list[i]+"_"+trans_value_arr[i];
               trans_v.addElement(new_value);
           }
       }
       if (new_column_list_set)
       {
       	    new_trans_value_arr = cnq.getTransactionValueInColumnList(row_number);
           for (int i=0; i<new_select_column_length; i++)
           {
               new_value = new_select_column_list[i]+"_"+new_trans_value_arr[i];
               trans_v.addElement(new_value);
           }
       }
       return trans_v;
 }

/* public Vector getAssociationTransaction(String trans_field, String trans_field_val, String target_field)
 {
    ass_trans_v.clear();
    String[] ass_trans_arr = new String[2];
    for (int i=1; i<row_numbers+1; i++)
    {
        if (column_v.contains(trans_field))
        {
            column_index_number = Integer.parseInt(column_index[column_v.indexOf(trans_field)]);
            ass_trans_arr[0] = cdq.getValueInColumn(i,column_index_number);
        }
        else
        {
            if (new_column_v.contains(trans_field))
            {
                 new_column_index_number = Integer.parseInt(new_column_index[new_column_v.indexOf(trans_field)]);
                 ass_trans_arr[0] = cnq.getValueInColumn(i,new_column_index_number);
            }
        }
        if (column_v.contains(target_field))
        {
            column_index_number = Integer.parseInt(column_index[column_v.indexOf(target_field)]);
            ass_trans_arr[1] = cdq.getValueInColumn(i,column_index_number);
        }
        else
        {
            if (new_column_v.contains(target_field))
            {
                new_column_index_number = Integer.parseInt(new_column_index[new_column_v.indexOf(target_field)]);
                ass_trans_arr[1] = cnq.getValueInColumn(i,new_column_index_number);
            }
        }

        if (trans_field_val.equals(ass_trans_arr[0]))
        {
            if (!ass_trans_v.contains(ass_trans_arr[1]))
            {
                ass_trans_v.addElement(ass_trans_arr[1]);
            }
        }

    }
    return ass_trans_v;
  } */

  public double getMaxValue()
  {
      if (column_set)
      {
          max = cdq.getMaxValue();
      }
      else
      {
      	  if (new_column_set)
      	  {
      	  	max = cnq.getMaxValue();
      	  }
      }
      return max;
  }

  public double getMinValue()
  {
      if (column_set)
      {
          min = cdq.getMinValue();
      }
      else
      {
      	  if (new_column_set)
      	  {
      	  	min = cnq.getMinValue();
      	  }
      }
      return min;
  }

  public byte[] getByteInColumn(int row_number)
  {
      if (column_set)
      {
           out_byte_arr =  cdq.getByteInColumn(row_number);
      }
      else
      {
          if (new_column_set)
          {
          	out_byte_arr = cnq.getByteInColumn(row_number);
          }
      }
      return out_byte_arr;
  }

  public double getDoubleInColumn(int row_number)
  {
      if (column_set)
      {
           out_double = cdq.getDoubleInColumn(row_number);
      }
      else
      {
          if (new_column_set)
          {
          	out_double = cnq.getDoubleInColumn(row_number);
          }
      }
      return out_double;
  }

  public int getIntInColumn(int row_number)
  {
      if (column_set)
      {
           out_int = cdq.getIntInColumn(row_number);
      }
      else
      {
          if (new_column_set)
          {
          	out_int = cnq.getIntInColumn(row_number);
          }
      }
      return out_int;
  }

  public String getStringInColumn(int row_number)
  {
      if (column_set)
      {
           out_str = cdq.getStringInColumn(row_number);
      }
      else
      {
          if (new_column_set)
          {
          	out_str = cnq.getStringInColumn(row_number);
          }
      }
      return out_str;
  }

/*  public byte[] getByteInColArray(int row_number)
  {
      if (column_list_set)
      {
           out_byte_arr = cdq.getByteInColArray(row_number);
      }
      else
      {
          if (new_column_list_set)
          {
          	out_byte_arr = cnq.getByteInColArray(row_number);
          }
      }
      return out_byte_arr;
  } */

  public double[] getDoubleArrayInColArray(int row_number)
  {
      if (out_status==0)
      {
          temp_double_arr1 = cdq.getDoubleArrayInColArray(row_number);
          temp_double_arr2 = cnq.getDoubleArrayInColArray(row_number);
          for (int i=0; i<select_column_length; i++)
          {
              out_double_arr[i] = temp_double_arr1[i];
          }
          for (int j=0; j<new_select_column_length; j++)
          {
              out_double_arr[select_column_length+j] = temp_double_arr2[j];
          }
      }
      else if (out_status==1)
      {
          out_double_arr = cdq.getDoubleArrayInColArray(row_number);
      }
      else if (out_status==2)
      {
          out_double_arr = cnq.getDoubleArrayInColArray(row_number);
      }
      else
      {
          out_double_arr = null;
      }
      return out_double_arr;
  }

  public int[] getIntArrayInColArray(int row_number)
  {
      if (out_status==0)
      {
          temp_int_arr1 = cdq.getIntArrayInColArray(row_number);
          temp_int_arr2 = cnq.getIntArrayInColArray(row_number);
          for (int i=0; i<select_column_length; i++)
          {
              out_int_arr[i] = temp_int_arr1[i];
          }
          for (int j=0; j<new_select_column_length; j++)
          {
              out_int_arr[select_column_length+j] = temp_int_arr2[j];
          }
      }
      else if (out_status==1)
      {
          out_int_arr = cdq.getIntArrayInColArray(row_number);
      }
      else if (out_status==2)
      {
          out_int_arr = cnq.getIntArrayInColArray(row_number);
      }
      else
      {
          out_int_arr = null;
      }
      return out_int_arr;
  }

  public String[] getStringArrayInColArray(int row_number)
  {
      if (out_status==0)
      {
          temp_str_arr1 = cdq.getStringArrayInColArray(row_number);
          temp_str_arr2 = cnq.getStringArrayInColArray(row_number);

           printOut(temp_str_arr1);
          printOut(temp_str_arr2);


          for (int i=0; i<select_column_length; i++)
          {
              out_str_arr[i] = temp_str_arr1[i];
          }
          for (int j=0; j<new_select_column_length; j++)
          {
              out_str_arr[select_column_length+j] = temp_str_arr2[j];
          }
          printOut(out_str_arr);
      }
      else if (out_status==1)
      {
          out_str_arr = cdq.getStringArrayInColArray(row_number);
      }
      else if (out_status==2)
      {
          out_str_arr = cnq.getStringArrayInColArray(row_number);
      }
      else
      {
          out_str_arr = null;
      }
      return out_str_arr;
  }

  public void close()
  {
  	if (file_set)
  	{
  	   cdq.close();
  	}

  	if (new_file_set)
  	{
  	   cnq.close();
  	}
  }

  private boolean nullCheck(String i_str)
  {
    boolean out_bool = false;
    if (i_str==null)
    {
      out_bool = true;
    }
    else
    {
      if (i_str.equals(""))
      {
        out_bool = true;
      }
    }
    return out_bool;
  }

  private boolean nullCheck(String[] i_str)
  {
    if (i_str==null)
    {
      return true;
    }
    else
    {
      if (i_str.length==0)
      {
        return true;
      }
      else
      {
        return false;
      }
    }
  }

  void printOut(String[] i_array)
  {
      String k = "";
      for (int i=0; i<i_array.length; i++)
      {
          if (k.equals(""))
          {
              k = i_array[i];
          }
          else
          {
              k = k + "," + i_array[i];
          }
      }
      System.out.println("value="+k);
  }

}

