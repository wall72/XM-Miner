
/**
 * Title:        null<p>
 * Description:  null<p>
 * Copyright:    null<p>
 * Company:      <p>
 * @author
 * @version null
 */
package xmminer.xmlib.xmtable;

import java.util.*;

public class CXMUniqueValueTableSaver
{
  private CXMTableSaver cts;
  private CXMMetaDataSaver cms;
  private Hashtable uvh;
  private String project;
  private String uvtable;

  public CXMUniqueValueTableSaver()
  {
  }

  public void createUniqueValueFile(String project_name, String file_name, Hashtable h)
  {
      project = project_name;
      uvtable = file_name;  
      //System.out.println("uvtable="+uvtable);
      fileOpen();  
      uvh = h;   
      create();   
      fileClose();
  }

  private void fileOpen()
  {
      cts = new CXMTableSaver();
      cts.setFileStatus(project, uvtable);     
      cms = new CXMMetaDataSaver();
      cms.setFileStatus(project, uvtable);  
  }

  private void fileClose()
  {
      cts.close();
      cms.close();
  }

  private void create()
  {
      createUVTable();
      createUVTableMeta();
  }

  private void createUVTable()
  {
      String key;
      String count;
      String value;
      int[] value_size = new int[2];
      try
      {
          Enumeration hEum = uvh.keys();
          while(hEum.hasMoreElements())
          {
              key = (String) hEum.nextElement();
              //System.out.println("key="+key);
              count = (String) uvh.get(key);
              //System.out.println("count="+count);
              value_size[0] = (key.getBytes()).length;              
              value_size[1] = (count.getBytes()).length;             
              value = key+count;
              //System.out.println("value="+value);
              cts.createRowData(value.getBytes(),value_size);
              //System.out.println("save");
          }
      } catch(Exception cte)
        { System.out.println("create_uvtable_error="+cte.getMessage());}
  }

  private void createUVTableMeta()
  {
      String[] column_list = {"KEY","COUNT"};
      String[] column_index = {"1","2"};
      cms.deleteAllProfile();
      cms.setProfile("DATA_FILE",uvtable);
      cms.setProfile("ROW_INDEX",uvtable);
      cms.setProfile("NUMBER_OF_ROWS",String.valueOf(uvh.size()));
      cms.setProfile("NUMBER_OF_COLUMNS","2");
      cms.setProfiles("COLUMN_LIST",column_list);
      cms.setProfiles("COLUMN_INDEX",column_index);
      cms.setProfile("KEY","STRING");
      cms.setProfile("COUNT","INTEGER");
      cms.close();
  }
}