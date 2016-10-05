package xmminer.xmlib.xmfilecompute;

import java.io.*;
import java.util.*;
import java.util.Vector;
import xmminer.xmlib.xmtable.*;
import xmminer.xmlib.xmfileprocess.*;

public class CXMNeuralNetworkFileSaver
{
    private String root_path = System.getProperty("minerdir") + "/xmminer/user/";//"/XM-Miner/xmminer/user"); ¼öÁ¤ 2002.09.22
    private String data_path = "/data/";
    private String index_path = "/index/";
    private String set_data_path;
    private String set_index_path;
    private String project;

    private byte[] value_byte;
    private byte[] index_byte;
    private byte[] trans_byte;

    private String bool_value;
    private String f_name;
    private String c_name;
    private String column_seq;
    private String column_type;
    private String filter_type;
    private String unique_value_file;

    private String[] bool_arr;
    private String[] column_list;
    private String[] new_column_list;
    private String[] column_index_list;
    private String[] new_column_index_list;
    private String[] input_column_list;
    private String[] output_column_list;
    private String[] column_property;
    private String[] filter_property;
    private String[] column_type_list;
    private String[] cal_result;
    private String[] column_size_arr;
    private String[] max_value_arr;
    private String[] min_value_arr;

    private int row_numbers;
    private int index_size;
    private int column_number;
    private int value_number;
    private int column_size;
    private int pre_index;
    private int method;
    private int byte_size;
    private int temp_int_value;
    private int quot_int;
    private int mod_int;
    private int unique_value_cnt;
    private int[] input_method_list;
    private int[] output_method_list;

    private long index_pos;
    private long data_pos;
    private long temp_long;
    private long quot_long;
    private long mod_long;
    private long out_long;
    private long factor_long;

    private double double_value;
    private double max_value;
    private double min_value;
    private double normalize_value;

    private Vector column_v;
    private Vector new_column_v;

    private boolean reset_init;

    private CXMMetaDataReader cmr;
    private CXMMetaDataSaver cms;
    private CXMTableQuerier ctq;
    private CXMSetFilteredValueNumber sfn;
    private File unique_data_file;
    private File unique_index_file;
    private File temp;
    private File input;
    private File input_index;
    private File output;
    private File output_index;

    private RandomAccessFile rdf;
    private RandomAccessFile rif;
    private RandomAccessFile rtf;
    private RandomAccessFile rcf;
    private RandomAccessFile cif;
    private Hashtable max_h;
    private Hashtable min_h;

    //variable plus
    private boolean t_flag;
    private String l_name;
    private int temp_cnt;
    private byte[] init_value_byte;
    private CXMUniqueValueReader uvr;


    public CXMNeuralNetworkFileSaver()
    {
    }

    public void setFileStatus(String project_name, String arc_name, boolean test_flag)
    {
         project = project_name;
         set_data_path = root_path + project + data_path;
         set_index_path = root_path + project + index_path;

         f_name = arc_name;
         t_flag = test_flag;
         index_size = 5;
         index_byte = new byte[index_size];
         getArcMetaData();
         ctq = new CXMTableQuerier();
         cms = new CXMMetaDataSaver();
         cms.setFileStatus(project,f_name+"_neural_network");
    }

    public void setLearnedArcName(String learned_arc_name)
    {
        l_name = learned_arc_name;
    }

    public void close()
    {
      cms.close();
      cmr.close();
      ctq.close();
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
    }

    public void createNeuralNetworkFile(String[] input_column, String[] output_column, int[] input_method, int[] output_method)
    {
         input_column_list = input_column;
         output_column_list = output_column;
         input_method_list = input_method;
         output_method_list = output_method;
         cms.setProfiles("INPUT_COLUMN_LIST",input_column_list);
         cms.setProfiles("OUTPUT_COLUMN_LIST",output_column_list);
         cms.setProfiles("INPUT_METHOD",resetIntArray(input_method_list));
         cms.setProfiles("OUTPUT_METHOD",resetIntArray(output_method_list));
         max_h = new Hashtable();
         min_h = new Hashtable();
         createInputFile();
         createOutputFile();
         createResultFile();
    }

    private String[] resetIntArray(int[] i_int_arr)
    {
        String[] out_array = new String[i_int_arr.length];
        for (int i=0; i<i_int_arr.length; i++)
        {
            out_array[i] = String.valueOf(i_int_arr[i]);
        }
        return out_array;
    }

    private void createInputFile()
    {
        inputFileOpen();
        column_number = input_column_list.length;
        column_type_list = new String[column_number];
        column_size_arr = new String[column_number];
        max_value_arr = new String[column_number];
        min_value_arr = new String[column_number];
        try
        {
            index_pos = rcf.getFilePointer();
            cif.writeLong(index_pos);
        } catch (Exception spe)
          {
             System.out.println("set_init_index_position_error="+spe.getMessage());
          }
        for (int i=0; i<column_number; i++)
        {
             c_name = input_column_list[i];
             method = input_method_list[i];
             setColumnStatus();
             column_type_list[i] = column_type;
             ctq.setFileStatus(project,f_name,c_name,null);
             setValueOnFile(i);
        }
        cms.setProfiles("INPUT_COLUMN_TYPE",column_type_list);
        cms.setProfiles("INPUT_COLUMN_SIZE",column_size_arr);
        cms.setProfiles("INPUT_MAX_VALUE",max_value_arr);
        cms.setProfiles("INPUT_MIN_VALUE",min_value_arr);
        fileClose();
    }

    private void createOutputFile()
    {
        outputFileOpen();
        column_number = output_column_list.length;
        column_type_list = new String[column_number];
        column_size_arr = new String[column_number];
        max_value_arr = new String[column_number];
        min_value_arr = new String[column_number];
        try
        {
           index_pos = rcf.getFilePointer();
           cif.writeLong(index_pos);
        } catch (Exception spe)
          {
              System.out.println("set_init_index_position_error="+spe.getMessage());
          }
        for (int i=0; i<column_number; i++)
        {
             c_name = output_column_list[i];
             method = output_method_list[i];
             setColumnStatus();
             column_type_list[i] = column_type;
             ctq.setFileStatus(project,f_name,c_name,null);
             setValueOnFile(i);
        }
        cms.setProfiles("OUTPUT_COLUMN_TYPE",column_type_list);
        cms.setProfiles("OUTPUT_COLUMN_SIZE",column_size_arr);
        cms.setProfiles("OUTPUT_MAX_VALUE",max_value_arr);
        cms.setProfiles("OUTPUT_MIN_VALUE",min_value_arr);
        fileClose();
    }

    private void createResultFile()
    {
        CXMFileCopy cfc = new CXMFileCopy();
        String output_file = set_data_path+f_name+"_nof.data";
        String output_index_file = set_index_path+f_name+"_nof.cidx";
        String result_file = set_data_path+f_name+"_rof.data";
        String result_index_file = set_index_path+f_name+"_rof.cidx";
        cfc.fileCopy(output_file,result_file);
        cfc.fileCopy(output_index_file,result_index_file);
    }

    private void setValueOnFile(int i_index)
    {
        if (column_type.equals("INTEGER")||column_type.equals("REAL"))
        {
            setNumericValue();
            column_size_arr[i_index] = "8";
            max_value_arr[i_index] = (String) max_h.get(c_name);
            min_value_arr[i_index] = (String) min_h.get(c_name);
        }
        else
        {
            setNonNumericValue();
            column_size_arr[i_index] = String.valueOf(column_size);
            max_value_arr[i_index] = " ";
            min_value_arr[i_index] = " ";
        }
    }

    private void setNumericValue()
    {
        setNumericInitStatus();
        max_value = Double.parseDouble((String)max_h.get(c_name));
        min_value = Double.parseDouble((String)min_h.get(c_name));

        for (int i=1; i<row_numbers+1; i++)
        {
            double_value = ctq.getDoubleInColumn(i);
            normalize_value = (double_value-min_value)/(max_value-min_value);
            try
            {
                rcf.writeDouble(normalize_value);
                index_pos = rcf.getFilePointer();
                cif.writeLong(index_pos);
            } catch (Exception we)
              {
                  System.out.println("numeric_value_write_error="+we.getMessage());
              }
        }
    }

    private void setNumericInitStatus()
    {
        if (filter_type.equals("filtered"))
        {
            cal_result = cmr.getProfiles(c_name+"_cal_result");
            if (!max_h.containsKey(c_name))
            {
                max_value = Double.parseDouble(cal_result[1]);
                max_h.put(c_name,cal_result[1]);
            }
            if (!min_h.containsKey(c_name))
            {
                min_value = Double.parseDouble(cal_result[2]);
                min_h.put(c_name,cal_result[2]);
            }
        }
        else
        {
            if (!max_h.containsKey(c_name))
            {
                max_value = ctq.getMaxValue();
                max_h.put(c_name,(String.valueOf(max_value)));
            }
            if (!min_h.containsKey(c_name))
            {
                min_value = ctq.getMinValue();
                min_h.put(c_name,(String.valueOf(min_value)));
            }
        }
    }

    private void setNonNumericValue()
    {
        if (filter_type.equals("filtered"))
        {
           filter_property = cmr.getProfiles(c_name+"_filtered_file");
           unique_value_file = filter_property[0];
           //createFilteredTemporaryFile();
        }
        else
        {
           if (method==1)
           {
               setNonNumericInitStatus();
               transOneOfNCodes();
           }
           else if (method==2)
           {
               setNonNumericInitStatus();
               transBinaryCodes();
           }
           else if (method==3)
           {
               //unique_value_cnt = 0;
               transNMapping();
           }
           else if (method==4)
           {
               transBoolean();
           }
        }
    }

    private void setNonNumericInitStatus()
    {
         sfn = new CXMSetFilteredValueNumber();
         uniqueValueFileSaverOpen();
         unique_value_cnt = temp_cnt;
         if (t_flag)
         {
            sfn.setFileStatus(project,f_name, c_name, row_numbers, false);
            sfn.setMaxIndex(unique_value_cnt);
         }
         else
         {
            sfn.setFileStatus(project,f_name, c_name, row_numbers, true);
         }
         try
         {
             for (int i=1; i<row_numbers+1; i++)
             {
                 value_byte = ctq.getByteInColumn(i);
                 value_number = sfn.find(value_byte);
                 if (value_number==0)
                 {
                     unique_value_cnt = unique_value_cnt + 1;
                     sfn.insert(value_byte,unique_value_cnt);
                     data_pos = rdf.getFilePointer();
                     setIndexByte(data_pos);
                     rif.write(index_byte);
                     rdf.write(value_byte);
                     rtf.writeInt(unique_value_cnt);
                 }
                 else
                 {
                     rtf.writeInt(value_number);
                 }
             }
             data_pos = rdf.getFilePointer();
             setIndexByte(data_pos);
             rif.write(index_byte);
         } catch(Exception fwe)
           {
                System.out.println("non_numeric_init_status_error");
           }
         sfn.close();
    }

  /*  private void createFilteredTemporaryFile()
    {
        temp = new File(unique_value_file+".fval");
        try
        {
            rtf = new RandomAccessFile(temp, "r");
            for (int i=0; i<row_numbers; i++)
            {
                rcf.writeInt(rtf.readInt());
                index_pos = rcf.getFilePointer();
                cif.writeLong(index_pos);
            }
            rtf.close();
         } catch (Exception re)
           {
               System.out.println("filtered_file_write_error");
           }
    }        */

    private void transOneOfNCodes()
    {
         if (t_flag)
         {
            testTransOneOfNCodes();
         }
         else
         {
            learnTransOneOfNCodes();
         }
    }

    private void learnTransOneOfNCodes()
    {
         try
         {
             trans_byte = new byte[unique_value_cnt];
             setTransByteInit(1);
             reset_init = false;
             rtf.seek(0);
             for (int i=0; i<row_numbers; i++)
             {
                 value_number = rtf.readInt();
                 resetTransByte(value_number-1);
                 rcf.write(trans_byte);
                 index_pos = rcf.getFilePointer();
                 cif.writeLong(index_pos);
             }
         } catch(Exception fwe)
           {
                System.out.println("trans_one_of_ncodes_error");
           }
         uniqueValueFileSaverClose();
         column_size = unique_value_cnt;
    }

    private void testTransOneOfNCodes()
    {
         try
         {
             trans_byte = new byte[temp_cnt];
             init_value_byte = new byte[temp_cnt];
             setTransByteInit(3);
             setInitValueByte();
             reset_init = false;
             rtf.seek(0);
             for (int i=0; i<row_numbers; i++)
             {
                 value_number = rtf.readInt();
                 if (value_number > temp_cnt)
                 {
                    rcf.write(init_value_byte);
                 }
                 else
                 {
                    resetTransByte(value_number-1);
                    rcf.write(trans_byte);
                 }
                 index_pos = rcf.getFilePointer();
                 cif.writeLong(index_pos);
             }
         } catch(Exception fwe)
           {
                System.out.println("trans_one_of_ncodes_error");
           }
         uniqueValueFileSaverClose();
         column_size = temp_cnt;
    }

    private void transBinaryCodes()
    {
         try
         {
             byte_size = calByteSize(unique_value_cnt);
             trans_byte = new byte[byte_size];
             setTransByteInit(2);
             rtf.seek(0);
             for (int i=0; i<row_numbers; i++)
             {
                 value_number = rtf.readInt();
                 setValueByte(value_number);
                 rcf.write(trans_byte);
                 index_pos = rcf.getFilePointer();
                 cif.writeLong(index_pos);
             }
         } catch(Exception fwe)
           {
                System.out.println("trans_binary_codes_error");
           }
         uniqueValueFileSaverClose();
         column_size = byte_size;
    }

    private void transNMapping()
    {
         sfn = new CXMSetFilteredValueNumber();
         uniqueValueFileSaverOpen();
         unique_value_cnt = temp_cnt;
         if (t_flag)
         {
            sfn.setFileStatus(project,f_name, c_name, row_numbers, false);
            sfn.setMaxIndex(unique_value_cnt);
         }
         else
         {
            sfn.setFileStatus(project,f_name, c_name, row_numbers, true);
         }
         try
         {
             for (int i=1; i<row_numbers+1; i++)
             {
                 value_byte = ctq.getByteInColumn(i);
                 value_number = sfn.find(value_byte);
                 if (value_number==0)
                 {
                     unique_value_cnt = unique_value_cnt + 1;
                     sfn.insert(value_byte,unique_value_cnt);
                     data_pos = rdf.getFilePointer();
                     setIndexByte(data_pos);
                     rif.write(index_byte);
                     rdf.write(value_byte);
                     rcf.writeInt(unique_value_cnt);
                 }
                 else
                 {
                     rcf.writeInt(value_number);
                 }
                 index_pos = rcf.getFilePointer();
                 cif.writeLong(index_pos);
             }
             data_pos = rdf.getFilePointer();
             setIndexByte(data_pos);
             rif.write(index_byte);
         } catch(Exception fwe)
           {
                System.out.println("trans_nmapping_error");
           }
         uniqueValueFileSaverClose();
         sfn.close();
         column_size = 4;
    }

    private void transBoolean()
    {
         bool_arr = new String[2];
         bool_arr[0] = "";
         bool_arr[1] = "";
         bool_value = "";
         try
         {
             for (int i=1; i<row_numbers+1; i++)
             {
                 bool_value = ctq.getStringInColumn(i);
                 if (!nullCheck(bool_value))
                 {
                     if (bool_value.equals(bool_arr[0]))
                     {
                         value_number = 0;
                     }
                     else if (bool_value.equals(bool_arr[1]))
                     {
                         value_number = 1;
                     }
                     else
                     {
                         if ((!nullCheck(bool_arr[0]))&&(!nullCheck(bool_arr[1])))
                         {
                            System.out.println("set_boolean_value_error");
                            break;
                         }
                         else
                         {
                            if (nullCheck(bool_arr[0]))
                            {
                                bool_arr[0] = bool_value;
                                value_number = 0;
                            }
                            else
                            {
                                if (nullCheck(bool_arr[1]))
                                {
                                   bool_arr[1] = bool_value;
                                   value_number = 1;
                                }
                            }
                         }
                     }
                 }
                 else
                 {
                     System.out.println("set_boolean_value_error");
                     break;
                 }
                 rcf.writeInt(value_number);
                 index_pos = rcf.getFilePointer();
                 cif.writeLong(index_pos);
             }
         } catch(Exception fwe)
           {
                System.out.println("trans_boolean_error");
           }
           column_size = 4;
           cms.setProfiles(c_name+"_BOOL_VALUE",bool_arr);
    }

    private void uniqueValueFileSaverOpen()
    {
        long unique_data_pos;
        long unique_index_pos;
        try
        {
            String unique_data = set_data_path+f_name+"_"+column_seq+"_uvf.fdat";
            String unique_index = set_index_path+f_name+"_"+column_seq+"_uvf.fidx";
            unique_data_file = new File(unique_data);
 	          unique_index_file = new File(unique_index);
            temp = new File(set_index_path+f_name+"_"+column_seq+"_uvf.fval");
            unique_data_file.delete();
            unique_index_file.delete();
            temp.delete();
            if (t_flag)
            {
                String learned_btree_data = set_data_path+l_name+"_"+column_seq+".tdat";
              	String learned_btree_index = set_index_path+l_name+"_"+column_seq+".tidx";
                String btree_data = set_data_path+f_name+"_"+column_seq+".tdat";
               	String btree_index = set_index_path+f_name+"_"+column_seq+".tidx";
                String learned_unique_data = set_data_path+l_name+"_"+column_seq+"_uvf.fdat";
    	          String learned_unique_index = set_index_path+l_name+"_"+column_seq+"_uvf.fidx";
                CXMFileCopy cfc = new CXMFileCopy();
                cfc.fileCopy(learned_btree_data,btree_data);
                cfc.fileCopy(learned_btree_index,btree_index);
                cfc.fileCopy(learned_unique_data,unique_data);
                cfc.fileCopy(learned_unique_index,unique_index);
                unique_data_pos = unique_data_file.length();
                unique_index_pos = unique_index_file.length();
                temp_cnt = (int) (unique_index_pos/index_size) - 1;
                cms.setProfile(c_name+"_unique_value_file",l_name+"_"+column_seq);
            }
            else
            {
                unique_data_pos = 0;
                unique_index_pos = 0;
                temp_cnt = 0;
                cms.setProfile(c_name+"_unique_value_file",f_name+"_"+column_seq);
            }
          	rdf = new RandomAccessFile(unique_data_file, "rw");
    	      rif = new RandomAccessFile(unique_index_file, "rw");
            rtf = new RandomAccessFile(temp, "rw");
            rdf.seek(unique_data_pos);
            rif.seek(unique_index_pos);

        } catch(Exception foe)
          {
             System.out.println("unique_value_file_saver_open_error");
          }
    }

    private void uniqueValueFileSaverClose()
    {
        try
        {
            rdf.close();
            rif.close();
            rtf.close();
        } catch(Exception foe)
          {
             System.out.println("unique_value_file_saver_close_error");
          }
    }

    private void inputFileOpen()
    {
        try
        {
            input = new File(set_data_path+f_name+"_nif.data");
            input.delete();
            rcf = new RandomAccessFile(input,"rw");
            input_index = new File(set_index_path+f_name+"_nif.cidx");
            input_index.delete();
            cif = new RandomAccessFile(input_index,"rw");
        } catch(Exception foe)
          {
             System.out.println("input_file_open_error");
          }
    }

    private void outputFileOpen()
    {
        try
        {
           output = new File(set_data_path+f_name+"_nof.data");
           output.delete();
           rcf = new RandomAccessFile(output,"rw");
           output_index = new File(set_index_path+f_name+"_nof.cidx");
           output_index.delete();
           cif = new RandomAccessFile(output_index,"rw");
        } catch(Exception foe)
          {
             System.out.println("output_file_open_error");
          }
    }

    private void fileClose()
    {
        try
        {
            rcf.close();
            cif.close();
        } catch(Exception foe)
          {
             System.out.println("file_close_error");
          }
    }

    private void setTransByteInit(int i_int)
    {
         if (i_int==1)
         {
             for (int i=0; i<unique_value_cnt; i++)
             {
                 trans_byte[i] = 0;
             }
         }
         else if (i_int==2)
         {
             for (int j=0; j<byte_size; j++)
             {
                 trans_byte[j] = 0;
             }
         }
         else if (i_int==3)
         {
             for (int i=0; i<temp_cnt; i++)
             {
                 trans_byte[i] = 0;
             }
         }
    }

    private void setInitValueByte()
    {
        for (int i=0; i<temp_cnt; i++)
        {
            init_value_byte[i] = 0;
        }
    }

    private void resetTransByte(int i_num)
    {
         if (!reset_init)
         {
            trans_byte[i_num] = 1;
            pre_index = i_num;
            reset_init = true;
         }
         else
         {
            trans_byte[pre_index] = 0;
            trans_byte[i_num] = 1;
            pre_index = i_num;
         }
    }

    private void setColumnStatus()
    {
        if (column_v.contains(c_name))
        {
            column_seq = column_index_list[column_v.indexOf(c_name)];
            column_property = cmr.getProfiles(c_name);
            column_type = column_property[0];
            filter_type = column_property[5];
        }
        else
        {
            if (new_column_v.contains(c_name))
            {
                column_seq = "new_"+new_column_index_list[new_column_v.indexOf(c_name)];
                column_type = "REAL";
            }
        }
    }

    private void setIndexByte(long i_pos)
    {
        temp_long = i_pos;
        for (int i=0; i<index_size; i++)
        {
            quot_long = temp_long/128;
            mod_long = temp_long%128;
            index_byte[i] = (byte) mod_long;
            temp_long = quot_long;
        }
    }

    private void setValueByte(int i_value)
    {
        temp_int_value = i_value;
        for (int i=0; i<byte_size; i++)
        {
            quot_int = temp_int_value/2;
            mod_long = temp_int_value%2;
            trans_byte[i] = (byte) mod_long;
            temp_int_value = quot_int;
        }
    }

    public int calByteSize(int i_value)
    {
       temp_int_value = i_value;
       quot_int = 0;
       mod_int = 0;
       int cnt = 0;
       do
       {
          quot_int = temp_int_value/2;
          mod_int = temp_int_value%2;
          temp_int_value = quot_int;
          cnt++;
       }
       while(quot_int!=0);
       return cnt;
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
