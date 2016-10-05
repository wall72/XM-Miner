package xmminer.xmlib.xmcompute;

import java.io.*;
import java.util.Vector;
import xmminer.xmlib.xmtable.*;

public class CXMC45ResultFileSaver
{
    private String root_path = System.getProperty("minerdir") + "/xmminer/user/";//"/XM-Miner/xmminer/user"); ¼öÁ¤ 2002.09.22
    private String data_path = "/data/";   
    private String index_path = "/index/";
    private String set_data_path;
    private String set_index_path;
    private String project;

    private byte[] value_byte;
    private byte[] index_byte;
    private byte[] result_byte;

    private String f_name;
    private String l_name;
    private String c_name;
    private String c_type;
    private String column_seq;
    private String t_column = "";

    private String[] column_list;
    private String[] new_column_list;
    private String[] column_index_list;
    private String[] new_column_index_list;
    private String[] c45_column_list;
    private String[] c45_column_type;
    private String[] non_numeric_column_list;
    private int row_numbers;
    private int index_size;
    private int data_size;
    private int sequence_number;
    private int column_index;
    private int column_number;
    private long index_first_pos;
    private long index_last_pos;
    private long data_first_pos;
    private long data_last_pos;
    private long result_index_pos;
    private long temp_pos;
    private long temp_long;
    private long quot_long;
    private long mod_long;
    private long out_long;
    private long factor_long;
    private double double_value;
    private boolean t_flag;
    private Vector column_v;
    private Vector new_column_v;
    private Vector non_numeric_column_v;
    private Vector non_numeric_index_v;
    private CXMMetaDataReader cmr;
    private CXMMetaDataSaver cms;
    private CXMC45TableManager ctm;
    private File btree;
    private File index;
    private File temp;
    private File result;
    private File result_index;
    private RandomAccessFile rdf;
    private RandomAccessFile rif;
    private RandomAccessFile rtf;
    private RandomAccessFile rcf;
    private RandomAccessFile cif;
    private String m_sModelName;

    public CXMC45ResultFileSaver()
    {
    }

    public void setFileStatus(String project_name, String arc_name, String ModelName ,boolean test_flag)
    {
         project = project_name;
         set_data_path = root_path + project + data_path;
         set_index_path = root_path + project + index_path;

         f_name = arc_name;
         t_flag = test_flag;
		 m_sModelName=ModelName;
         index_size = 5;
         index_byte = new byte[index_size];

         getArcMetaData();
         getC45MetaData();
    }

    public void setTrainingColumn(String training_column)
    {
        t_column = training_column;//"Estimate_Value";//
    }

    private void getArcMetaData()
    {
         cmr = new CXMMetaDataReader();
         cmr.setFileStatus(project,f_name);
         column_list = cmr.getProfiles("COLUMN_LIST");
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

    private void getC45MetaData()
    {
         cmr = new CXMMetaDataReader();
         cmr.setFileStatus(project,f_name+"_"+m_sModelName+"_c45");
         c45_column_list = cmr.getProfiles("COLUMN_LIST");
         c45_column_type = cmr.getProfiles("COLUMN_TYPE");
         cmr.close();
         non_numeric_column_v = new Vector();
         non_numeric_index_v = new Vector();
         for (int i=0; i<c45_column_type.length; i++)
         {
             if (!numericTypeCheck(c45_column_type[i]))
             {
                 non_numeric_column_v.addElement(c45_column_list[i]);
                 non_numeric_index_v.addElement(String.valueOf(i));
             }
         }
         column_number = non_numeric_column_v.size();
    }

    private boolean numericTypeCheck(String i_str)
    {
        if (i_str.equals("REAL")||i_str.equals("INTEGER"))
        {
           return true;
        }
        else
        {
           return false;
        }
    }

    public void setLearnedArcName(String learned_arc_name)
    {
        l_name = learned_arc_name;
    }

    public void close()
    {
        ctm.close();
        setC45MetaData();
    }

    private void setC45MetaData()
    {
        cms = new CXMMetaDataSaver();
        cms.setFileStatus(project,f_name+"_"+m_sModelName+"_c45");
        cms.setProfile("TRAINING_COLUMN",t_column);
        cms.close();
    }


    public void createResultFile()
    {
        resultFileOpen();
        ctm = new CXMC45TableManager();
        ctm.setFileStatus(project,f_name,m_sModelName,t_flag);
        try
        {
           result_index_pos = rcf.getFilePointer();
           System.out.println("result_index_pos="+result_index_pos);
           cif.writeLong(result_index_pos);
        } catch (Exception spe)
          {
              System.out.println("set_init_index_position_error="+spe.getMessage());
          }
        for (int i=0; i<column_number; i++)
        {
             setColumnStatus(i);
             setNonNumericValue(i);
        }
        if (!t_column.equals(""))
        {
           setTrainingColumnValue();
        }
        resultFileClose();
    }

    private void setColumnStatus(int i_int)
    {
        c_name = (String) non_numeric_column_v.elementAt(i_int);
        System.out.println("c_name="+c_name);
        if (column_v.contains(c_name))
        {
            column_seq = column_index_list[column_v.indexOf(c_name)];
        }
        else
        {
            if (new_column_v.contains(c_name))
            {
                column_seq = new_column_index_list[new_column_v.indexOf(c_name)];
            }
        }
    }

    private void setNonNumericValue(int i_int)
    {
        column_index = Integer.parseInt((String) non_numeric_index_v.elementAt(i_int));		
		System.out.println("column_index="+column_index);
        templateFileOpen();
        for (int i=0; i<row_numbers; i++)
        {
             sequence_number = ctm.getInteger(i+1,column_index+1);
             setIndexPosition();
             setDataPosition();
             writeResultData();
        }
        templateFileClose();
    }

	private void setNonNumericValue_esti(int i_int)
    {
        column_index = Integer.parseInt((String) non_numeric_index_v.elementAt(i_int));		
		System.out.println("column_index="+column_index);
        templateFileOpen();
        for (int i=0; i<row_numbers; i++)
        {
             sequence_number = ctm.getInteger(i+1,column_index+2);
             setIndexPosition();
             setDataPosition();
             writeResultData();
        }
        templateFileClose();
    }

    private void setTrainingColumnValue()
    {
        for (int i=0; i<column_number; i++)
        {
            if (t_column.equals((String)non_numeric_column_v.elementAt(i)))
            {
				System.out.println("(String)non_numeric_column_v.elementAt(i) = " + (String)non_numeric_column_v.elementAt(i));
                setColumnStatus(i);
                //setNonNumericValue(i);
				setNonNumericValue_esti(i);
                break;
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

    private void setDataPosition()
    {
        try
        {
            rif.seek(index_first_pos);

            for (int i=0; i<index_size; i++)
            {
                index_byte[i] = rif.readByte();
            }

            data_first_pos = setLongValue(index_byte);

            rif.seek(index_last_pos);
            for (int j=0; j<index_size; j++)
            {
                index_byte[j] = rif.readByte();
            }

            data_last_pos = setLongValue(index_byte);

        } catch (Exception die)
          {
             System.out.println("set_data_pos_error="+die.getMessage());
          }
    }

    private void writeResultData()
    {
        try
        {
            data_size = (int) (data_last_pos - data_first_pos);
            result_byte = new byte[data_size];
            rdf.seek(data_first_pos);
            for (int i=0; i<data_size; i++)
            {
                result_byte[i] = rdf.readByte();
            }
            rcf.write(result_byte);
            String tempx = new String(result_byte);
            System.out.println("result_value="+tempx);
            result_index_pos = rcf.getFilePointer();
            //System.out.println("result_index_pos2="+result_index_pos);
            cif.writeLong(result_index_pos);
        } catch (Exception wie)
          {
             System.out.println("write_result_error="+wie.getMessage());
          }
    }

    private void resultFileOpen()
    {
        try
        {
            if (t_flag)
            {
                result = new File(set_data_path+f_name+"_"+m_sModelName+"_c45_rtf.data");
                result.delete();
                rcf = new RandomAccessFile(result,"rw");
                result_index = new File(set_index_path+f_name+"_"+m_sModelName+"_c45_rtf.cidx");
                result_index.delete();
                cif = new RandomAccessFile(result_index,"rw");
            }
            else
            {
                result = new File(set_data_path+f_name+"_"+m_sModelName+"_c45_rlf.data");
                result.delete();
                rcf = new RandomAccessFile(result,"rw");
                result_index = new File(set_index_path+f_name+"_"+m_sModelName+"_c45_rlf.cidx");
                result_index.delete();
                cif = new RandomAccessFile(result_index,"rw");
            }
        } catch(Exception foe)
          {
            System.out.println("result_file_open_error");
          }
    }

    private void resultFileClose()
    {
        try
        {
            rcf.close();
            cif.close();
        } catch(Exception foe)
          {
             System.out.println("result_file_close_error");
          }
    }

    private void templateFileOpen()
    {
        try
        {
            if (t_flag)
            {
               btree = new File(set_data_path+l_name+"_"+m_sModelName+"_c45_"+column_seq+"_uvf.fdat");
    	         index = new File(set_index_path+l_name+"_"+m_sModelName+"_c45_"+column_seq+"_uvf.fidx");
               temp = new File(set_index_path+l_name+"_"+m_sModelName+"_c45_"+column_seq+"_uvf.fval");
            }
            else
            {
               //System.out.println("btree="+set_data_path+f_name+"_"+m_sModelName+"_c45_"+column_seq+"_uvf.fdat");
               btree = new File(set_data_path+f_name+"_"+m_sModelName+"_c45_"+column_seq+"_uvf.fdat");
               //System.out.println("s111");
    	         index = new File(set_index_path+f_name+"_"+m_sModelName+"_c45_"+column_seq+"_uvf.fidx");
               //System.out.println("s112");
               temp = new File(set_index_path+f_name+"_"+m_sModelName+"_c45_"+column_seq+"_uvf.fval");
               //System.out.println("s113");
            }
          	rdf = new RandomAccessFile(btree, "r");
            //System.out.println("s114");
    	      rif = new RandomAccessFile(index, "r");
            //System.out.println("s115");
            rtf = new RandomAccessFile(temp, "r");
            //System.out.println("s116");
        } catch(Exception foe)
          {
             System.out.println("template_file_open_error");
          }
    }

    private void templateFileClose()
    {
        try
        {
          	rdf.close();
    	      rif.close();
            rtf.close();
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
