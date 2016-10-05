package xmminer.xmlib.xmcompute;

import java.io.*;
import java.util.Vector;
import xmminer.xmlib.xmtable.*;

public class CXMC45TableSaver
{
    private final static int    IGNORE1=1;       //특정한 칼럼을 사용하지 않을 경우
    private final static int    DISCRETE=2;      //칼럼의 Data Type이 숫자인지 문자인지 구별
    private final static int    CONTINUOUS=3;    //칼럼의 Data Type이 숫자인지 문자인지 구별
    private final static double Unknown= -999;   //data값을 모를 경우 -999로 할당
  
    
    private String root_path = System.getProperty("minerdir") + "/xmminer/user/";//"/XM-Miner/xmminer/user"); 수정 2002.09.22
    private String data_path = "/data/";
    private String index_path = "/index/";
    private String set_data_path;
    private String set_index_path;
    private String project;

    private byte[] value_byte;
    private byte[] index_byte;
    private String f_name;
    private String l_name;
    private String c_name;
    private String column_seq;
    private String column_type;
    private String filter_type;
    private String unique_value_file;
    private String[] column_list;
    private String[] new_column_list;
    private String[] column_index_list;
    private String[] new_column_index_list;
    private String[] c45_column_list;
    private String[] c45_column_type;
    private String[] column_property;
    private String[] filter_property;
    private String[] column_type_list;
    private int row_numbers;
	private int learned_row_numbers;
    private int index_size;
    private int int_value;
    private int column_number;
    private int value_number;
    private int column_size;
    private long result_index_pos;
    private long temp_pos;
    private long data_pos;
    private long temp_long;
    private long quot_long;
    private long mod_long;
    private long out_long;
    private long factor_long;
    private long btree_pos;
    private long index_pos;
    private double double_value;
    private boolean t_flag;
    private Vector column_v;
    private Vector new_column_v;
    private CXMMetaDataReader cmr;
    private CXMMetaDataSaver cms;
    private CXMTableQuerier ctq;
    private CXMSetFilteredValueNumber sfn;
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
    
    private  String m_sModelName;
        
    private  int    MaxAttVal[];      //각 칼럼의 data set의 크기를 저장
    private  int    MaxItem;          //CXMTable의 row number
    private  int    SpecialStatus[];  //각 칼럼의 data type :DISCRETE,CONTINUOUS,IGNORE1
    
    public CXMC45TableSaver()
    {
    }
    
    public int GetMaxItem() {return MaxItem;}
    public int[] GetMaxAttVal(){return MaxAttVal;}
    public int[] GetSpecialStatus(){return SpecialStatus;}
    
    public void setFileStatus(String project_name, String arc_name,String ModelName, boolean test_flag)
    {
         project = project_name;
         set_data_path = root_path + project + data_path;
         set_index_path = root_path + project + index_path;

         f_name = arc_name;
         t_flag = test_flag;
         m_sModelName =ModelName; //qorwkr Edit
         index_size = 5;
         index_byte = new byte[index_size];
     
         getArcMetaData();
    }

    public void setLearnedArcName(String learned_arc_name){
      l_name = learned_arc_name;
      if(t_flag){
        CXMMetaDataReader lmr = new CXMMetaDataReader();
        lmr.setFileStatus(project,l_name);
        learned_row_numbers = Integer.parseInt(lmr.getProfile("NUMBER_OF_ROWS"));
        lmr.close();
      }
    }

    public void close(){
      setC45MetaData();
      cmr.close();
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
         MaxItem=row_numbers;
    }

    public void createC45Table(String[] learning_column_list)
    {
        temporaryFileOpen();
        ctq = new CXMTableQuerier();
        c45_column_list = learning_column_list;
        column_number = c45_column_list.length;
        column_type_list = new String[column_number];
        MaxAttVal= new int[column_number];
        SpecialStatus= new int[column_number];

        try
        {
           result_index_pos = rcf.getFilePointer();
           cif.writeLong(result_index_pos);
        } catch (Exception spe)
          {
              System.out.println("set_init_index_position_error="+spe.getMessage());
          }
        
        for (int i=0; i<column_number; i++)
        {
             setColumnStatus(i);
             column_type_list[i] = column_type;
             ctq.setFileStatus(project,f_name,c_name,null);

             if (column_type.equals("INTEGER"))
             {
                setIntegerValue();
                SpecialStatus[i]=CONTINUOUS;
                MaxAttVal[i]=0;
             }
             else if (column_type.equals("REAL"))
             {
                setDoubleValue();
                SpecialStatus[i]=CONTINUOUS;
                MaxAttVal[i]=0;
             }
             else
             {
                MaxAttVal[i]=setNonNumericValue()-1;
                if(MaxAttVal[i]==0)MaxAttVal[i]=1;
                SpecialStatus[i]=DISCRETE;
             }
        }
        setResultTemplateValue();
        ctq.close();
        temporaryFileClose();
    }

    private void setC45MetaData()
    {
        cms = new CXMMetaDataSaver();
        cms.setFileStatus(project,f_name+"_"+m_sModelName+"_c45");
        cms.setProfiles("COLUMN_LIST",c45_column_list);
        cms.setProfiles("COLUMN_TYPE",column_type_list);
        cms.close();
    }

    private void setIntegerValue()
    {
        for (int i=0; i<row_numbers; i++)
        {
            int_value = ctq.getIntInColumn(i+1);
            try
            {
                //rcf.writeInt(int_value);
                rcf.writeDouble((double)int_value);
                result_index_pos = rcf.getFilePointer();
                cif.writeLong(result_index_pos);
            } catch (Exception we)
              {
                  System.out.println("c45_temporary_int_write_error="+we.getMessage());
              }
        }
    }

    private void setDoubleValue()
    {
        for (int i=0; i<row_numbers; i++)
        {
            double_value = ctq.getDoubleInColumn(i+1);
            try
            {
                rcf.writeDouble(double_value);
                result_index_pos = rcf.getFilePointer();
                cif.writeLong(result_index_pos);
            } catch (Exception we)
              {
                  System.out.println("c45_temporary_double_write_error="+we.getMessage());
              }
        }
    }

    private int setNonNumericValue()
    {
        int m_iMaxColumnVal;
        if (filter_type.equals("filtered"))
        {
           if (t_flag)
           {
               m_iMaxColumnVal=createNotFilteredTemporaryFile();
           }
           else
           {
               filter_property = cmr.getProfiles(c_name+"_filtered_file");
               unique_value_file = filter_property[0];
               m_iMaxColumnVal=createFilteredTemporaryFile();
           }
        }
        else
        {
              m_iMaxColumnVal=createNotFilteredTemporaryFile();
        }
        return m_iMaxColumnVal;
    }

    private void setResultTemplateValue()
    {
        try
        {
            for (int i=0; i<row_numbers; i++)
            {
                rcf.writeInt(0);
                result_index_pos = rcf.getFilePointer();
                cif.writeLong(result_index_pos);
            }
         } catch (Exception re)
           {
               System.out.println("set_result_template_value_error");
           }
    }

    private int createFilteredTemporaryFile()
    {
        int m_iMaxColumnVal=0;
        temp = new File(unique_value_file+".fval");
        try
        {
            rtf = new RandomAccessFile(temp, "r");
            for (int i=0; i<row_numbers; i++)
            {
                rcf.writeInt(rtf.readInt());
                result_index_pos = rcf.getFilePointer();
                cif.writeLong(result_index_pos);
            }
            rtf.close();
         } catch (Exception re)
           {
               System.out.println("filtered_file_write_error");
           }
       return m_iMaxColumnVal;     
    }

    private int createNotFilteredTemporaryFile()
    {
         sfn = new CXMSetFilteredValueNumber();
         int m_iMaxColumnVal=1;
         if (t_flag)
         {
            sfn.setFileStatus(project,l_name, c_name, learned_row_numbers, false);
         }
         else
         {
            sfn.setFileStatus(project,f_name, c_name, row_numbers, true);
         }
         uniqueValueFileSaverOpen();
         try
         {
             for (int i=1; i<row_numbers+1; i++)
             {
                 value_byte = ctq.getByteInColumn(i);
                 String k = new String(value_byte);
                 value_number = sfn.find(value_byte);
                 if (value_number==0)
                 {
                     column_size = column_size + 1;
                     sfn.insert(value_byte,column_size);
                     data_pos = rdf.getFilePointer();
                     setIndexByteValue(data_pos);
                     rif.write(index_byte);
                     rdf.write(value_byte);
                     rcf.writeInt(column_size);
                 }           
                 else
                 {
                     rcf.writeInt(value_number);
                 }
                 result_index_pos = rcf.getFilePointer();
                 cif.writeLong(result_index_pos);
             }
             data_pos = rdf.getFilePointer();
             if (data_pos>btree_pos)
             {
                 setIndexByteValue(data_pos);
                 rif.write(index_byte);
             }
             m_iMaxColumnVal= (int)rif.length()/index_size;
         } catch(Exception fwe)
           {
                
				fwe.printStackTrace();
				System.out.println("not_filtered_file_write_error");
           }

         uniqueValueFileSaverClose();
         sfn.close();
         return m_iMaxColumnVal;
    }

    private void uniqueValueFileSaverOpen()
    {
        try
        {
            if (t_flag)
            {
                btree = new File(set_data_path+l_name+"_"+m_sModelName+"_c45"+"_"+column_seq+"_uvf.fdat");
    	        index = new File(set_index_path+l_name+"_"+m_sModelName+"_c45"+"_"+column_seq+"_uvf.fidx");
                temp = new File(set_index_path+l_name+"_"+m_sModelName+"_c45"+"_"+column_seq+"_uvf.fval");
                btree_pos = btree.length();
                index_pos = index.length();
                temp_pos = temp.length();
                column_size = (int) (index_pos/index_size);
            }
            else
            {
                btree = new File(set_data_path+f_name+"_"+m_sModelName+"_c45"+"_"+column_seq+"_uvf.fdat");
    	          index = new File(set_index_path+f_name+"_"+m_sModelName+"_c45"+"_"+column_seq+"_uvf.fidx");
                temp = new File(set_index_path+f_name+"_"+m_sModelName+"_c45"+"_"+column_seq+"_uvf.fval");
                btree.delete();
                index.delete();
                temp.delete();
                btree_pos = 0;
                index_pos = 0;
                temp_pos = 0;
                column_size = 0;
            }
          	rdf = new RandomAccessFile(btree, "rw");
    	      rif = new RandomAccessFile(index, "rw");
            rtf = new RandomAccessFile(temp, "rw");
            rdf.seek(btree_pos);
            rif.seek(index_pos);
            rtf.seek(temp_pos);
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

    private void temporaryFileOpen()
    {
        try
        {
            if (t_flag)
            {
               result = new File(set_data_path+f_name+"_"+m_sModelName+"_c45"+"_ctf.data");
               result.delete();
               rcf = new RandomAccessFile(result,"rw");
               result_index = new File(set_index_path+f_name+"_"+m_sModelName+"_c45"+"_ctf.cidx");
               result_index.delete();
               cif = new RandomAccessFile(result_index,"rw");
            }
            else
            {
               result = new File(set_data_path+f_name+"_"+m_sModelName+"_c45"+"_clf.data");
               result.delete();
               rcf = new RandomAccessFile(result,"rw");
               result_index = new File(set_index_path+f_name+"_"+m_sModelName+"_c45"+"_clf.cidx");
               result_index.delete();
               cif = new RandomAccessFile(result_index,"rw");
            }
        } catch(Exception foe)
          {
             System.out.println("c45_temporary_file_open_error");
          }
    }

    private void temporaryFileClose()
    {
        try
        {
            rcf.close();
            cif.close();
        } catch(Exception foe)
          {
             System.out.println("c45_temporary_file_close_error");
          }
    }

    private void setColumnStatus(int i_int)
    {
        c_name = c45_column_list[i_int];
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
                column_seq = new_column_index_list[new_column_v.indexOf(c_name)];
                column_type = "REAL";
            }
        }
    }

    private void setIndexByteValue(long i_pos)
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
