package xmminer.xmlib.xmtable;

import java.util.*;

public class CXMSetMetaDataColumnProperty
{
  private String f_name;
  private String datatype = "datatype";
  private String column_list_key = "COLUMN_LIST";
  private String type_key = "_TYPE";
  private String delimiter = "^";
  private String sort_default = "not_sorted";
  private String filter_default = "not_filtered";
  private String transform_default = "not_transformed";
  private String compute_default = "not_computed";
  private String project;
  private String[] column_list;
  private String[] column_property;
  private String[] column_type_property;
  private String[] column_type_value;
  private int type_property_size = 3;
  private CXMMetaDataReader cmr1;
  private CXMMetaDataReader cmr2;
  private CXMMetaDataSaver cms;
  private Hashtable h;
  private Vector value_v;


  public void setColumnProperty(String project_name, String file_name)
  {
    project = project_name;
    f_name = file_name;
    readColumnProperty();
    transformColumnProperty();
  }

  private void readColumnProperty()
  {
    h = new Hashtable();
    cmr1 = new CXMMetaDataReader();
    cmr1.setFileStatus(project,f_name);
    cmr2 = new CXMMetaDataReader();
    cmr2.setFileStatus(project,datatype);
    column_list = cmr1.getProfiles(column_list_key);

    String[] column_list_value;
    String column_property_list = "";
    String column_type = "";
    String[] data_type_value;
    for (int i=0; i<column_list.length; i++)
    {
      column_list_value = cmr1.getProfiles(column_list[i]);
      column_type = cmr2.getProfile(column_list_value[0].toUpperCase());
      data_type_value = cmr2.getProfiles(column_type+type_key);
      column_property_list = column_type+delimiter+data_type_value[0]+delimiter+
                             data_type_value[1]+delimiter+data_type_value[2]+delimiter+
                             sort_default+delimiter+filter_default+delimiter+
                             transform_default+delimiter+compute_default;
      h.put(column_list[i],column_property_list);
    }
    cmr1.close();
    cmr2.close();
  }

  private void transformColumnProperty()
  {
    cms = new CXMMetaDataSaver();
    cms.setFileStatus(project,f_name);
    for (int i=0; i<column_list.length; i++)
    {
      cms.setProfiles (column_list[i],dataParsing((String)h.get(column_list[i])));
    }
    cms.close();
  }

  private String[] dataParsing(String i_str)
  {
    value_v = new Vector();
    String value = "";
    try
    {
      StringTokenizer st = new StringTokenizer(i_str, delimiter);
      while((value = st.nextToken()) != null )
      {
        value_v.addElement(value);
      }
    } catch(Exception ste)
      {
      }
    return vectorToStringArray();
  }

  private String[] vectorToStringArray() //throws IOException, Exception
  {
    int value_num = value_v.size();
    String[] outStringArray = new String[value_num];
    try
    {
      Enumeration vEum = value_v.elements();
      int i=0;
      while(vEum.hasMoreElements())
      {
        outStringArray[i] = (String) vEum.nextElement();
        i++;
      }
      value_v.removeAllElements();
    } catch(Exception ene)
      { System.out.println("enumeration_error="+ene.getMessage());}
    return outStringArray;
  }

}