package xmminer.xmlib.xmcompute;

import java.util.*;

/* import java.io.*;
import java.util.TreeSet;
import java.util.Vector;
import xmminer.xmlib.xmtable.*; */

public class CXMFilteredValueCount
{
  private Hashtable h;
  private Integer count;
  private String new_count;

  public CXMFilteredValueCount()
  {
      h = new Hashtable();
  }

  public void close()
  {
      h.clear();
  }

  public void setValueCount(String i_value, int i_count)
  {
        new_count = String.valueOf(i_count);
        h.put(i_value,new_count);
        //System.out.println("set_value="+i_value);
        //System.out.println("new_count="+new_count);
  }

  public int getValueCount(String i_value)
  {
  	//System.out.println("find_value="+i_value);
  	if (h.containsKey(i_value))
  	{
  	    int find_count = Integer.parseInt((String) h.get(i_value));
  	    //System.out.println("find_count="+find_count);
            return find_count;
  	}
  	else
  	{
            return 0;
  	}
  }

  public Hashtable getValueTable()
  {
      return h;
  }

}