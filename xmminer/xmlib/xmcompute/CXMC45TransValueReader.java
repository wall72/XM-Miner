package xmminer.xmlib.xmcompute;

import java.io.*;
import java.util.Vector;
import xmminer.xmlib.xmtable.*;

public class CXMC45TransValueReader
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
    private String column_seq;  
    private String trans_value;

    private String[] column_list;
    private String[] new_column_list;
    private String[] column_index_list;
    private String[] new_column_index_list;

    private int index_size;
    private int data_size;

    private long index_first_pos;
    private long index_last_pos;
    private long data_first_pos;
    private long data_last_pos;
    private long temp_long;
    private long quot_long;
    private long mod_long;
    private long out_long;
    private long factor_long;
    private boolean t_flag;
    private Vector column_v;
    private Vector new_column_v;
    private CXMMetaDataReader cmr;
    private File btree;
    private File index;
    private File temp;
    private RandomAccessFile rdf;
    private RandomAccessFile rif;
    private RandomAccessFile rtf;
    private String m_sModelName;


    public CXMC45TransValueReader()
    {
    }

    public void setFileStatus(String project_name, String arc_name,String ModelName, String column_name, boolean test_flag)
    {
         project = project_name;
         set_data_path = root_path + project + data_path;
         set_index_path = root_path + project + index_path;

         c_name = column_name;
         f_name = arc_name;
		 m_sModelName=ModelName;
         t_flag = test_flag;
         index_size = 5;
         index_byte = new byte[index_size];

         getArcMetaData();
         setColumnStatus();
         fileOpen();
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
         cmr.close();
    }

    public void setLearnedArcName(String learned_arc_name)
    {
        l_name = learned_arc_name;
    }

    public String getTransValue(int numeric_value)
    {
         setIndexPosition(numeric_value);
         setValuePosition();
         setTransValue();
         return trans_value;
    }

    private void setColumnStatus()
    {
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

    private void setIndexPosition(int i_num)
    {
        try
        {
            index_first_pos = (i_num-1)*index_size;
            index_last_pos = i_num*index_size;
        } catch (Exception sie)
          {
             System.out.println("set_index_pos_error="+sie.getMessage());
          }
    }

    private void setValuePosition()
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

        } catch (Exception sve)
          {
             System.out.println("set_value_pos_error="+sve.getMessage());
          }
    }

    private void setTransValue()
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
            trans_value = new String(result_byte);
        } catch (Exception sve)
          {
             System.out.println("set_trans_value_error="+sve.getMessage());
          }
    }

    private void fileOpen()
    {
        try
        {
            if (t_flag)
            {
               btree = new File(set_data_path+l_name+"_"+m_sModelName+"_c45"+"_"+column_seq+"_uvf.fdat");
    	         index = new File(set_index_path+l_name+"_"+m_sModelName+"_c45"+"_"+column_seq+"_uvf.fidx");
               temp = new File(set_index_path+l_name+"_"+m_sModelName+"_c45"+"_"+column_seq+"_uvf.fval");
            }
            else
            {
               btree = new File(set_data_path+f_name+"_"+m_sModelName+"_c45"+"_"+column_seq+"_uvf.fdat");
    	         index = new File(set_index_path+f_name+"_"+m_sModelName+"_c45"+"_"+column_seq+"_uvf.fidx");
               temp = new File(set_index_path+f_name+"_"+m_sModelName+"_c45"+"_"+column_seq+"_uvf.fval");
               //System.out.println(set_data_path+f_name+"_"+m_sModelName+"_c45"+"_"+column_seq+"_uvf.fdat");
			}

          	rdf = new RandomAccessFile(btree, "r");
    	    rif = new RandomAccessFile(index, "r");
            rtf = new RandomAccessFile(temp, "r");
        } catch(Exception foe)
          {
             System.out.println("file_open_error");
          }
    }

    public void close()
    {
        try
        {
          	rdf.close();
    	      rif.close();
            rtf.close();
        } catch(Exception foe)
          {
             System.out.println("file_close_error");
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
