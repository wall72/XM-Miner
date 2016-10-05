package xmminer.xmlib.xmfilecompute;

import java.io.*;
import java.util.Vector;
import xmminer.xmlib.xmtable.*;

public class CXMGetFilteredRuleValue
{

  private String root_path = System.getProperty("minerdir") + "/xmminer/user/";//"/XM-Miner/xmminer/user"); ¼öÁ¤ 2002.09.22
  private String data_path = "/data/";
  private String index_path = "/index/";
  private String set_data_path;
  private String set_index_path;
  private String project;

  private byte[] index_byte;
  private byte[] pos_byte;
  private byte[] value_byte;
  private byte[] data_index_byte;
  private byte[] default_index;
  private byte[] null_index;
  private byte[] root_node;
  private byte[] ins_node;
  private byte[] fnd_node;
  private byte[] new_node;
  private byte[] out_byte;
  private byte[] left_index;
  private byte[] right_index;
  private byte[] new_index;
  private byte[] max_index_byte;
  private String f_name = "";
  private long out_long_value;
  private long temp_long_value;
  private long quot_long;
  private long mod_long;
  private long factor_long;
  private long index_pos;
  private long index_size;
  private long data_size;
  private long r_pos_init;
  private long r_pos_next;
  private int cnt;
  private int index_byte_size;
  private int data_byte_size;
  private int max_index = 1;
  private int out_int_value;
  private int temp_int_value;
  private int quot_int;
  private int mod_int;
  private int factor_int;
  private int row_numbers;
  private int init_int;
  private int last_int;
  private int byte_length;
  private int comp;
  private int comp_1;
  private int comp_2;
  private int comp_3;
  private int new_size;
  private int old_size;
  private int node_size;
  private File btree;
  private File index;
  private RandomAccessFile rdf;
  private RandomAccessFile rif;
  private boolean check_start = false;
  private boolean new_check = false;

  public void setFileStatus(String project_name, String rule_name, boolean new_tree_create)
  {
    project = project_name;
    set_data_path = root_path + project + data_path;
    set_index_path = root_path + project + index_path;

    f_name = rule_name;
    index_byte_size = 5;
    data_byte_size = 5;
    index_byte = new byte[index_byte_size];
    pos_byte = new byte[index_byte_size];
    new_check = new_tree_create;

    openFile();
    default_index = new byte[data_byte_size];
    default_index[0] = 1;
    for (int i=1; i<data_byte_size; i++)
    {
      default_index[i] = 0;
    }
    null_index = new byte[data_byte_size];
    for (int j=0; j<data_byte_size; j++)
    {
      null_index[j] = 0;
    }
    data_index_byte = new byte[data_byte_size];
    if (check_start)
    {
      root_node = readNode(default_index);
    }
  }

  private void openFile()
  {
    try
    {
    	btree = new File(set_data_path+f_name+".rdat");
    	index = new File(set_index_path+f_name+".ridx");
    	if (new_check)
    	{
    		btree.delete();
    		index.delete();
    	}
    	else
    	{
    		check_start = true;
    	}
    	rdf = new RandomAccessFile(btree, "rw");
    	rif = new RandomAccessFile(index, "rw");
    	rif.write(setIndexByteValue(0));
    } catch (Exception e)
      {pm("file_open_error",e.getMessage());}
  }

  public void close()
  {
    try
    {
      rdf.close();
      rif.close();
    } catch(Exception e)
      {pm("file_close_error",e.getMessage());}
  }

  public void insert(byte[] i_value)
  {
  	if (check_start)
  	{
  		dataInsert(i_value);
  	}
  	else
  	{
  		//System.out.println("000");
  		setRootNode(i_value);
  		// System.out.println("111");
  		// pba("root_node",root_node);
  		writeNode(default_index,root_node);
  		// System.out.println("222");
  		check_start = true;
  	}
  }

  public int find(byte[] i_byte)
  {
  	if (check_start)
  	{
  		return dataFind(i_byte);
  	}
  	else
  	{
  		return 0;
  	}
  }

  private int dataFind(byte[] fnd_byte)
  {
  	value_byte = getValueByte(root_node);
  	comp_1 = compareByte(fnd_byte,value_byte);
  	if (comp_1==-1)
  	{
  		fnd_node = checkLeftNode(fnd_byte, root_node);
  		value_byte = getValueByte(fnd_node);
  		comp_2 = compareByte(fnd_byte,value_byte);
  		if (comp_2 == 0)
  		{
  			return setIntValue(getCntByte(fnd_node));
  		}
  		else
  		{
  			return 0;
  		}
      }
      else if(comp_1==1)
      {
      	      fnd_node = checkRightNode(fnd_byte, root_node);
      	      value_byte = getValueByte(fnd_node);
      	      comp_3 = compareByte(fnd_byte,value_byte);
      	      if (comp_3 == 0)
      	      {
      	      	       return setIntValue(getCntByte(fnd_node));
      	      }
      	      else
      	      {
      	      	      return 0;
      	      }
      }
      else
      {
      	     return setIntValue(getCntByte(root_node));
      }
  }

  private void dataInsert(byte[] ins_byte)
  {
  	value_byte = getValueByte(root_node);
  	comp_1 = compareByte(ins_byte,value_byte);
  	if (comp_1==0)
  	{
  		root_node = increaseCntByte(root_node);
  		writeNode(default_index,root_node);
  	}
  	if (comp_1==-1)
  	{
  		ins_node = checkLeftNode(ins_byte, root_node);
  		new_index = getIndexByte(ins_node);
  		value_byte = getValueByte(ins_node);
  		comp_2 = compareByte(ins_byte, value_byte);
  		if (comp_2==0)
  		{
  			ins_node = increaseCntByte(ins_node);
  			writeNode(new_index,ins_node);
  		}
  		else
  		{
  			max_index = max_index + 1;
  			if (comp_2==-1)
  			{
  				ins_node = setLeftIndexByte(ins_node,setDataByteValue(max_index));
  			}
  			else if (comp_2==1)
  			{
  				ins_node = setRightIndexByte(ins_node,setDataByteValue(max_index));
  			}
  			setNodeData(new_index,ins_byte,ins_node);
  		}
  	}
  	else if(comp_1==1)
  	{
  		ins_node = checkRightNode(ins_byte, root_node);
  		new_index = getIndexByte(ins_node);
  		value_byte = getValueByte(ins_node);
  		comp_3 = compareByte(ins_byte, value_byte);
  		if (comp_3==0)
  		{
  			ins_node = increaseCntByte(ins_node);
  			writeNode(new_index,ins_node);
  		}
  		else
  		{
  			max_index = max_index + 1;
  			if (comp_3==-1)
  			{
  				ins_node = setLeftIndexByte(ins_node,setDataByteValue(max_index));
  			}
  			else if (comp_3==1)
  			{
  				ins_node = setRightIndexByte(ins_node,setDataByteValue(max_index));
  			}
  			setNodeData(new_index,ins_byte,ins_node);
  		}
  	}
  }

  private byte[] checkLeftNode(byte[] i_byte, byte[] l_node)
  {
  	left_index = getLeftIndexByte(l_node);
  	comp_1 = compareByte(left_index,null_index);
  	if (comp_1!=0)
  	{
  		l_node = readNode(left_index);
  		value_byte = getValueByte(l_node);
  		comp_2 = compareByte(i_byte,value_byte);
  		if (comp_2==-1)
  		{
  			l_node = checkLeftNode(i_byte, l_node);
  		}
  		else if (comp_2==1)
  		{
  			l_node = checkRightNode(i_byte, l_node);
  		}
  	}
  	else
  	{}
  	return l_node;
  }

  private byte[] checkRightNode(byte[] i_byte, byte[] r_node)
  {
  	right_index = getRightIndexByte(r_node);
  	comp_1 = compareByte(right_index,null_index);
  	if (comp_1!=0)
  	{
  		r_node = readNode(right_index);
  		value_byte = getValueByte(r_node);
  		comp_2 = compareByte(i_byte,value_byte);
  		if (comp_2==-1)
  		{
  			r_node = checkLeftNode(i_byte, r_node);
  		}
  		else if (comp_2==1)
  		{
  			r_node = checkRightNode(i_byte, r_node);
  		}
  	}
  	else
  	{}
  	return r_node;
  }

  private byte[] increaseCntByte(byte[] i_byte)
  {
  	init_int = i_byte.length - 3*data_byte_size;
  	out_byte = new byte[data_byte_size];
  	for (int i=0; i<data_byte_size; i++)
  	{
  		out_byte[i] = i_byte[i+init_int];
  	}
  	cnt = setIntValue(out_byte)+1;
  	out_byte = setDataByteValue(cnt);
  	for (int i=0; i<data_byte_size; i++)
  	{
  		i_byte[i+init_int] = out_byte[i];
  	}
  	return i_byte;
  }

  private byte[] getCntByte(byte[] i_byte)
  {
  	init_int = i_byte.length - 3*data_byte_size;
  	out_byte = new byte[data_byte_size];
  	for (int i=0; i<data_byte_size; i++)
  	{
  		out_byte[i] = i_byte[i+init_int];
  	}
  	return out_byte;
  }

  private int compareByte(byte[] new_byte, byte[] old_byte)
  {
  	new_size = new_byte.length;
  	old_size = old_byte.length;
  	if (new_size>old_size)
  	{
  		return 1;
  	}
  	else if (new_size<old_size)
  	{
  		return -1;
  	}
       else
       {
  	       for (int i=0; i<new_size;)
              {
       	            if (new_byte[i]>old_byte[i])
       	            {
       	      	           return 1;
       	            }
       	            else if (new_byte[i]<old_byte[i])
       	            {
       	      	           return -1;
       	            }
       	            else
       	            {
       	      	           i++;
       	            }
       	       }
       }
       return 0;
  }

  private byte[] getIndexByte(byte[] i_byte)
  {
  	out_byte = new byte[data_byte_size];
  	for (int i=0; i<data_byte_size; i++)
  	{
  		out_byte[i] = i_byte[i];
  	}
  	return out_byte;
  }

  private byte[] getLeftIndexByte(byte[] i_byte)
  {
  	init_int = i_byte.length - 2*data_byte_size;
  	out_byte = new byte[data_byte_size];
  	for (int i=0; i<data_byte_size; i++)
  	{
  		out_byte[i] = i_byte[i+init_int];
  	}
  	return out_byte;
  }

  private byte[] getRightIndexByte(byte[] i_byte)
  {
  	init_int = i_byte.length - data_byte_size;
  	out_byte = new byte[data_byte_size];
  	for (int i=0; i<data_byte_size; i++)
  	{
  		out_byte[i] = i_byte[i+init_int];
  	}
  	return out_byte;
  }

  private byte[] getValueByte(byte[] i_byte)
  {
  	init_int = data_byte_size;
  	last_int = i_byte.length - 3*data_byte_size;
  	byte_length = last_int - init_int;
  	out_byte = new byte[byte_length];
  	for (int i=0; i<byte_length; i++)
  	{
  		out_byte[i] = i_byte[i+init_int];
  	}
  	return out_byte;
  }

  private byte[] setLeftIndexByte(byte[] i_node, byte[] i_byte)
  {
  	init_int = i_node.length - 2*data_byte_size;
  	for (int i=0; i<data_byte_size; i++)
  	{
  		i_node[i+init_int] = i_byte[i];
  	}
  	return i_node;
  }

  private byte[] setRightIndexByte(byte[] i_node, byte[] i_byte)
  {
  	init_int = i_node.length - data_byte_size;
  	for (int i=0; i<data_byte_size; i++)
  	{
  		i_node[i+init_int] = i_byte[i];
  	}
  	 return i_node;
  }

  private void setNodeData(byte[] set_index, byte[] set_data, byte[] set_node)
  {
  	comp = compareByte(set_index,default_index);
  	if (comp!=0)
  	{
  		writeNode(set_index,set_node);
  	}
  	else
  	{
  		root_node = set_node;
  		writeNode(set_index,set_node);
  	}
  	max_index_byte = setDataByteValue(max_index);
  	new_node = setNewNode(max_index_byte, set_data);
  	writeNode(max_index_byte,new_node);
  }

  private byte[] setNewNode(byte[] new_index_byte, byte[] value_byte)
  {
  	//pba("i_index",new_index_byte);
  	byte_length = value_byte.length;
  	node_size = byte_length + 4*data_byte_size;
  	out_byte = new byte[node_size];
  	for (int i=0; i<data_byte_size; i++)
  	{
  		out_byte[i] = new_index_byte[i];
  	}
  	//pba("o_index",new_index_byte);
  	for (int j=0; j<byte_length; j++)
  	{
  		out_byte[data_byte_size+j] = value_byte[j];
  	}
  	out_byte[data_byte_size+byte_length] = 1;
  	for (int k=1; k<3*data_byte_size; k++)
  	{
  		out_byte[k+data_byte_size+byte_length] = 0;
  	}
  	return out_byte;
  }

  private byte[] readNode(byte[] i_index)
  {
      index_pos = setDataLongValue(i_index)*index_byte_size;
      try
      {
    	  rif.seek(index_pos-index_byte_size);
    	  for (int i=0; i<index_byte_size; i++)
    	  {
    		pos_byte[i] = rif.readByte();
    	  }
    	  r_pos_init = setIndexLongValue(pos_byte);
    	 //System.out.println("r_pos_init="+r_pos_init);
    	  for (int j=0; j<index_byte_size; j++)
    	  {
    		pos_byte[j] = rif.readByte();
    	  }
    	  r_pos_next = setIndexLongValue(pos_byte);
    	//System.out.println("r_pos_next="+r_pos_next);
    	  node_size = (int) (r_pos_next - r_pos_init);
    	  out_byte = new byte[node_size];
    	  rdf.seek(r_pos_init);
    	  for (int k=0; k<node_size; k++)
    	  {
    		out_byte[k] = rdf.readByte();
    	  }
      }  catch(Exception re)
         {
      	     out_byte = setDataByteValue(0);
      	     pm("read_error",re.getMessage());
      	  }
      return out_byte;
  }

  private void writeNode(byte[] i_index, byte[] i_node)
  {
    //pba("i_node",i_node);
    index_pos = setDataLongValue(i_index)*index_byte_size;
    try
    {
      index_size = rif.length();
      data_size = rdf.length();
      if (index_size>index_pos)
      {
        rif.seek(index_pos-index_byte_size);
        for (int i=0; i<index_byte_size; i++)
        {
          pos_byte[i] = rif.readByte();
        }
        rdf.seek(setIndexLongValue(pos_byte));
        rdf.write(i_node);
        //pba("update",i_node);
      }
      else
      {
        rdf.seek(data_size);
        rdf.write(i_node);
        //pba("new",i_node);
        pos_byte = setIndexByteValue(rdf.getFilePointer());
        rif.seek(index_size);
        rif.write(pos_byte);
      }
    } catch (Exception we)
      {
        pm("write_error",we.getMessage());
      }
  }

  private void setRootNode(byte[] i_value)
  {
    node_size = i_value.length + 4*data_byte_size;
    byte_length = i_value.length;
    root_node = new byte[node_size];
    root_node[0] = 1;
    for (int i=1; i<data_byte_size; i++)
    {
    	 root_node[i] = 0;
    }
    for (int j=0; j<byte_length; j++)
    {
    	 root_node[data_byte_size+j] = i_value[j];
    }
    root_node[data_byte_size+byte_length] = 1;
    for (int k=1; k<3*data_byte_size; k++)
    {
    	root_node[k+data_byte_size+byte_length] = 0;
    }
  }

  private byte[] setIndexByteValue(long i_long)
  {
    temp_long_value = i_long;
    for (int i=0; i<index_byte_size; i++)
    {
      quot_long = temp_long_value/128;
      mod_long = temp_long_value%128;
      index_byte[i] = (byte) mod_long;
      temp_long_value = quot_long;
    }
    return index_byte;
  }

  private byte[] setDataByteValue(int i_int)
  {
    temp_int_value = i_int;
    for (int i=0; i<data_byte_size; i++)
    {
      quot_int = temp_int_value/128;
      mod_int = temp_int_value%128;
      data_index_byte[i] = (byte) mod_int;
      temp_int_value = quot_int;
    }
    return data_index_byte;
  }

  private int setIntValue(byte[] i_byte)
  {
    out_int_value = 0;
    factor_int = 1;
    for (int i=0; i<data_byte_size; i++)
    {
    	temp_int_value = (int) i_byte[i];
    	out_int_value = out_int_value + temp_int_value*factor_int;
    	factor_int = factor_int*128;
    }
    return out_int_value;
  }

  private long setDataLongValue(byte[] i_byte)
  {
    out_long_value = 0;
    factor_long = 1;
    temp_long_value = 0;
    for (int i=0; i<data_byte_size; i++)
    {
    	temp_long_value = (long) i_byte[i];
    	out_long_value = out_long_value + temp_long_value*factor_long;
    	factor_long = factor_long*128;
    }
    return out_long_value;
  }

  private long setIndexLongValue(byte[] i_byte)
  {
    out_long_value = 0;
    factor_long = 1;
    temp_long_value = 0;
    for (int i=0; i<index_byte_size; i++)
    {
    	temp_long_value = (long) i_byte[i];
    	out_long_value = out_long_value + temp_long_value*factor_long;
    	factor_long = factor_long*128;
    }
    return out_long_value;
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