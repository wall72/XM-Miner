package xmminer.xmlib.xmcompute;

import java.io.*;
import java.util.*;
import java.util.Vector;
import xmminer.xmlib.xmtable.*;

public class CXMNeuralNetworkOutputTableSaver
{
    private String root_path = System.getProperty("minerdir") + "/xmminer/user/";//"/XM-Miner/xmminer/user"); ¼öÁ¤ 2002.09.22
    private String data_path = "/data/";
    private String index_path = "/index/";
    private String set_data_path;
    private String set_index_path;
    private String project;

    private byte[] index_byte;
    private byte[] result_byte;
    private byte[] trans_byte;
    private byte[] binary_byte;
    private byte[] byte_value;

    private String f_name;
    private String new_f_name;
    private String column_seq;
    private String result_value;
    private String trans_value;
    private String binary_value;
    private String nmap_value;
    private String numeric_value;
    private String bool_index;

    private String[] column_list;
    private String[] new_column_list;
    private String[] column_index_list;
    private String[] new_column_index_list;
    private String[] input_column_list;
    private String[] output_column_list;
    private String[] output_max_value;
    private String[] output_min_value;
    private String[] output_method;
    private String[] input_value;
    private String[] bool_value;

    private int row_numbers;
    private int index_size;
    private int data_size;
    private int out_int_value;
    private int temp_int_value;
    private int factor_int;
    private int input_column_size;
    private int output_column_size;
    private int sequence_number;
    private int method;
    private int file_index;

    private int[] method_array;
    private int[] column_size;
    private int[] file_index_array;

    private long index_first_pos;
    private long index_last_pos;
    private long data_first_pos;
    private long data_last_pos;
    private long temp_long;
    private long quot_long;
    private long mod_long;
    private long out_long;
    private long factor_long;

    private double reverse_double;
    private double max_value;
    private double min_value;
    private double[] max_array;
    private double[] min_array;

    private Vector column_v;
    private Vector new_column_v;

    private Hashtable bool_first_value_h = new Hashtable();
    private Hashtable bool_last_value_h = new Hashtable();
    private Hashtable bool_first_length_h = new Hashtable();
    private Hashtable bool_last_length_h = new Hashtable();

    private CXMMetaDataReader cmr;
    private CXMMetaDataSaver cms;
    private CXMNeuralNetworkFileManager nnm;
    private CXMTableQuerier ctq;
    private CXMTableSaver cts;

 //   private File[] btree;
 //   private File[] index;
 //   private File[] temp;
    private RandomAccessFile[] rdf;
    private RandomAccessFile[] rif;
    private RandomAccessFile[] rtf;

    private String data_file;
    private String index_file;
    private String temp_file;

    public CXMNeuralNetworkOutputTableSaver()
    {
    }

    public void setFileStatus(String project_name, String arc_name, String new_arc_name)
    {
         project = project_name;
         set_data_path = root_path + project + data_path;
         set_index_path = root_path + project + index_path;

         f_name = arc_name;
         new_f_name = new_arc_name;
         index_size = 5;
         index_byte = new byte[index_size];

         getArcMetaData();
         getNeuralNetworkMetaData();
         setColumnLength();
         setMethodArray();
         setMaxValueArray();
         setMinValueArray();
    }

    private void getArcMetaData()
    {
         cmr = new CXMMetaDataReader();
         cmr.setFileStatus(project,f_name);
         column_list = cmr.getProfiles("OLD_COLUMN_LIST");
         if (!nullCheck(column_list))
         {
             column_index_list = cmr.getProfiles("COLUMN_INDEX");
             column_v = new Vector();
             for (int i=0; i<column_list.length; i++)
             {
                column_v.addElement(column_list[i]);
             }
         }
         new_column_list = cmr.getProfiles("NEW_COLUMN_LIST");
         if (!nullCheck(new_column_list))
         {
             new_column_index_list = cmr.getProfiles("NEW_COLUMN_INDEX");
             new_column_v = new Vector();
             for (int j=0; j<new_column_list.length; j++)
             {
                new_column_v.addElement(new_column_list[j]);
             }
         }
         row_numbers = Integer.parseInt(cmr.getProfile("NUMBER_OF_ROWS"));
         cmr.close();
    }

    private void getNeuralNetworkMetaData()
    {
         cmr = new CXMMetaDataReader();
         cmr.setFileStatus(project,f_name+"_neural_network");
         input_column_list = cmr.getProfiles("INPUT_COLUMN_LIST");
         output_column_list = cmr.getProfiles("OUTPUT_COLUMN_LIST");
         output_max_value = cmr.getProfiles("OUTPUT_MAX_VALUE");
         output_min_value = cmr.getProfiles("OUTPUT_MIN_VALUE");
         output_method = cmr.getProfiles("OUTPUT_METHOD");
         for (int i=0; i<output_method.length; i++)
         {
             if (output_method[i].equals("4"))
             {
                bool_index = String.valueOf(i);
                bool_value = cmr.getProfiles(output_column_list[i]+"_BOOL_VALUE");
                bool_first_value_h.put(bool_index,bool_value[0]);
                bool_last_value_h.put(bool_index,bool_value[1]);
                bool_first_length_h.put(bool_index,new Integer(bool_value[0].getBytes().length));
                bool_last_length_h.put(bool_index,new Integer(bool_value[1].getBytes().length));
             }
         }
         cmr.close();
    }

    private void setColumnLength()
    {
         input_column_size = input_column_list.length;
         output_column_size = output_column_list.length;
    }

    private void setMethodArray()
    {
         method_array = new int[output_column_size];
         for (int i=0; i<output_column_size; i++)
         {
             method_array[i] = Integer.parseInt(output_method[i]);
         }
    }

    private void setMaxValueArray()
    {
         if (!nullCheck(output_max_value))
         {
             max_array = new double[output_column_size];
             for (int i=0; i<output_column_size; i++)
             {
                 if (output_max_value[i].equals(" "))
                 {
                    max_array[i] = 0;
                 }
                 else
                 {
                    max_array[i] = Double.parseDouble(output_max_value[i]);
                 }
             }
         }
    }

    private void setMinValueArray()
    {
         if (!nullCheck(output_min_value))
         {
             min_array = new double[output_column_size];
             for (int j=0; j<output_column_size; j++)
             {
                 if (output_min_value[j].equals(" "))
                 {
                    min_array[j] = 0;
                 }
                 else
                 {
                    min_array[j] = Double.parseDouble(output_min_value[j]);
                 }
             }
         }
    }

    public void close()
    {
        ctq.close();
        nnm.close();
        cts.close();
    }

    public void createResultFile()
    {
        setCreateInitStatus();
        templateFileOpen();
        for (int i=1; i<row_numbers+1; i++)
        {
            result_value = "";
            setInputColumnValue(i);
            for (int j=0; j<output_column_size; j++)
            {
                method = method_array[j];
                if (method == 1)
                {
                   setReverseOneOfNCodesValue(i,j);
                }
                else if (method == 2)
                {
                   setReverseBinaryCodesValue(i,j);
                }
                else if (method == 3)
                {
                   setReverseNMappingValue(i,j);
                }
                else if (method == 4)
                {
                   setReverseBooleanValue(i,j);
                }
                else
                {
                   setReverseNumericValue(i,j);
                }
            }
            cts.createRowData(result_value.getBytes(),column_size);
        }
        templateFileClose();
    }

    private void setCreateInitStatus()
    {
        ctq = new CXMTableQuerier();
        ctq.setFileStatus(project,f_name,null,input_column_list);
        nnm = new CXMNeuralNetworkFileManager();
        nnm.setFileStatus(project,f_name);
        cts = new CXMTableSaver();
        cts.setFileStatus(project,new_f_name);
        column_size = new int[input_column_size+output_column_size];
    }

    private void setInputColumnValue(int i_int)
    {
        input_value = ctq.getStringArrayInColArray(i_int);
        for (int i=0; i<input_column_size; i++)
        {
            result_value = result_value + input_value[i];
            byte_value = input_value[i].getBytes();
            column_size[i] = byte_value.length;
        }
    }

    private void setReverseOneOfNCodesValue(int row_number, int column_number)
    {
        trans_byte = nnm.getResultBytes(row_number,column_number+1);
        sequence_number = reverseTransByte();
        setIndexPosition();
        setDataPosition(column_number);
        setResultByte(column_number);
        trans_value = new String(result_byte);
        result_value = result_value + trans_value;
        column_size[input_column_size+column_number] = result_byte.length;
    }

    private void setReverseBinaryCodesValue(int row_number, int column_number)
    {
         binary_byte = nnm.getResultBytes(row_number,column_number+1);
         sequence_number = reverseBinaryByte();
         setIndexPosition();
         setDataPosition(column_number);
         setResultByte(column_number);
         binary_value = new String(result_byte);
         result_value = result_value + binary_value;
         column_size[input_column_size+column_number] = result_byte.length;
    }

    private void setReverseNMappingValue(int row_number, int column_number)
    {
         sequence_number = nnm.getResultInteger(row_number,column_number+1);
         setIndexPosition();
         setDataPosition(column_number);
         setResultByte(column_number);
         nmap_value = new String(result_byte);
         result_value = result_value + nmap_value;
         column_size[input_column_size+column_number] = result_byte.length;
    }

    private void setReverseBooleanValue(int row_number, int column_number)
    {
         sequence_number = nnm.getResultInteger(row_number,column_number+1);
         bool_index = String.valueOf(column_number);
         if (sequence_number==0)
         {
              result_value = result_value + (String) bool_first_value_h.get(bool_index);
              column_size[input_column_size+column_number] = ((Integer) bool_first_length_h.get(bool_index)).intValue();
         }
         else if (sequence_number==1)
         {
              result_value = result_value + (String) bool_last_value_h.get(bool_index);
              column_size[input_column_size+column_number] = ((Integer) bool_last_length_h.get(bool_index)).intValue();
         }
    }

    private void setReverseNumericValue(int row_number, int column_number)
    {
         max_value = max_array[column_number];
         min_value = min_array[column_number];
         numeric_value = reverseDoubleValue(nnm.getResultDouble(row_number,column_number+1));
         result_value = result_value + numeric_value;
         column_size[input_column_size+column_number] = numeric_value.getBytes().length;
    }

    private int reverseTransByte()
    {
        for (int i=0; i<trans_byte.length; i++)
        {
            if (trans_byte[i]==1)
            {
                return i+1;
            }
        }
        return 0;
    }

    private int reverseBinaryByte()
    {
        out_int_value = 0;
        factor_int = 1;
        for (int i=0; i<binary_byte.length; i++)
        {
           temp_int_value = (int) binary_byte[i];
           out_int_value = out_int_value + temp_int_value*factor_int;
           factor_int = factor_int*2;
        }
        return out_int_value;
    }

    private String reverseDoubleValue(double i_double)
    {
        reverse_double = i_double*(max_value-min_value) + min_value;
        return String.valueOf(reverse_double);
    }


    private void setColumnSeq(String c_name)
    {
        if (column_v.contains(c_name))
        {
            column_seq = column_index_list[column_v.indexOf(c_name)];
        }
        else
        {
            if (new_column_v.contains(c_name))
            {
                column_seq = "new_"+new_column_index_list[new_column_v.indexOf(c_name)];
            }
        }
    }

    private void setIndexPosition()
    {
        try
        {
            index_first_pos = (sequence_number-1)*index_size;
            index_last_pos = (sequence_number)*index_size;
        } catch (Exception sie)
          {
             System.out.println("set_index_pos_error="+sie.getMessage());
          }
    }

    private void setDataPosition(int f_index)
    {
        try
        {
            rif[file_index_array[f_index]].seek(index_first_pos);

            for (int i=0; i<index_size; i++)
            {
                index_byte[i] = rif[file_index_array[f_index]].readByte();
            }

            data_first_pos = setLongValue(index_byte);

            rif[file_index_array[f_index]].seek(index_last_pos);
            for (int j=0; j<index_size; j++)
            {
                index_byte[j] = rif[file_index_array[f_index]].readByte();
            }

            data_last_pos = setLongValue(index_byte);

        } catch (Exception die)
          {
             System.out.println("set_data_pos_error="+die.getMessage());
          }
    }

    private void setResultByte(int f_index)
    {
        try
        {
            data_size = (int) (data_last_pos - data_first_pos);
            result_byte = new byte[data_size];
            rdf[file_index_array[f_index]].seek(data_first_pos);
            for (int i=0; i<data_size; i++)
            {
                result_byte[i] = rdf[file_index_array[f_index]].readByte();
            }
        } catch (Exception wie)
          {
             System.out.println("set_result_byte_error="+wie.getMessage());
          }
    }

    private void templateFileOpen()
    {

        setFileIndex();
        rdf = new RandomAccessFile[file_index];
        rif = new RandomAccessFile[file_index];
        rtf = new RandomAccessFile[file_index];
        try
        {
            for (int i=0; i<output_column_size; i++)
            {
               if (method_array[i]==1||method_array[i]==2||method_array[i]==3)
               {
                  setColumnSeq(output_column_list[i]);
                  data_file = set_data_path+f_name+"_"+column_seq+"_uvf.fdat";
                  index_file = set_index_path+f_name+"_"+column_seq+"_uvf.fidx";
                  temp_file = set_index_path+f_name+"_"+column_seq+"_uvf.fval";
                  rdf[file_index_array[i]] = new RandomAccessFile(data_file, "r");
                  rif[file_index_array[i]] = new RandomAccessFile(index_file, "r");
                  rtf[file_index_array[i]] = new RandomAccessFile(temp_file, "r");
               }
            }
        } catch(Exception foe)
          {
               System.out.println("template_file_open_error");
          }
    }

    private void setFileIndex()
    {
        file_index_array = new int[output_column_size];
        file_index = 0;
        for (int i=0; i<output_column_size; i++)
        {
            if (method_array[i]==1||method_array[i]==2||method_array[i]==3)
            {
                file_index_array[i] = file_index;
                file_index = file_index + 1;
            }
            else
            {
                file_index_array[i] = -1;
            }
        }
    }

    private void templateFileClose()
    {
        try
        {
            for (int i=0; i<output_column_size; i++)
            {
               if (method_array[i]==1||method_array[i]==2||method_array[i]==3)
               {
                   rdf[file_index_array[i]].close();
    	             rif[file_index_array[i]].close();
                   rtf[file_index_array[i]].close();
               }
            }
        } catch(Exception foe)
          {
             System.out.println("template_file_close_error");
          }
    }

    private long setLongValue(byte[] i_byte)
    {
        out_long = 0;
        factor_long = 1;
        for (int i=0; i<index_size; i++)
        {
            temp_long = (long) i_byte[i];
            out_long = out_long + temp_long*factor_long;
            factor_long = factor_long*128;
        }
        return out_long;
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
