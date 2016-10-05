
/**
 * Title:        null<p>
 * Description:  null<p>
 * Copyright:    null<p>
 * Company:      <p>
 * @author
 * @version null
 */
package xmminer.xmlib.xmfileprocess;

import java.util.Vector;
import xmminer.xmlib.xmtable.*;

public class CXMFileInfo
{
  private String root_path = System.getProperty("minerdir") + "/xmminer/user/";//"/XM-Miner/xmminer/user"); ¼öÁ¤ 2002.09.22
  private String data_path = "/data/";
  private String index_path = "/index/";
  private String project;
  private String set_data_path;
  private String set_index_path;
  private String column_seq;
  private String f_name;
  private String c_name;
  private String[] column_list;
  private String[] column_index_list;
  private String[] new_column_list;
  private String[] new_column_index_list;

  private CXMMetaDataReader cmr;
  private CXMNullCheck cnc = new CXMNullCheck();
  private Vector column_v;
  private Vector new_column_v;

  public CXMFileInfo()
  {
  }

  public void setFileStatus(String project_name, String arc_name)
  {
    project = project_name;
    set_data_path = root_path + project + data_path;
    set_index_path = root_path + project + index_path;
    f_name = arc_name;

    cmr = new CXMMetaDataReader();
    cmr.setFileStatus(project, f_name);
    column_list = cmr.getProfiles("OLD_COLUMN_LIST");
    if (!cnc.nullCheck(column_list))
    {
        column_v = new Vector();
        for (int i=0; i<column_list.length; i++)
        {
            column_v.addElement(column_list[i]);
        }
        column_index_list = cmr.getProfiles("COLUMN_INDEX");
    }

    new_column_list = cmr.getProfiles("NEW_COLUMN_LIST");
    if (!cnc.nullCheck(new_column_list))
    {
        new_column_v = new Vector();
        for (int j=0; j<new_column_list.length; j++)
        {
            new_column_v.addElement(new_column_list[j]);
        }
        new_column_index_list = cmr.getProfiles("NEW_COLUMN_INDEX");
    }
    cmr.close();
  }

  public void setColumnName(String column_name)
  {
     c_name = column_name;
  }

  public String getColumnSequence()
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
      return column_seq;
  }

  public String getDataPath()
  {
     return set_data_path;
  }

  public String getIndexPath()
  {
     return set_index_path;
  }

}