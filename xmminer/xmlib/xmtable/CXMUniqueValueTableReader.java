
/**
 * Title:        null<p>
 * Description:  null<p>
 * Copyright:    null<p>
 * Company:      <p>
 * @author
 * @version null
 */
package xmminer.xmlib.xmtable;

import xmminer.xmlib.xmtable.*;
import java.util.*;

public class CXMUniqueValueTableReader
{
    private CXMTableReader ctr = new CXMTableReader();
    private CXMMetaDataReader cmr = new CXMMetaDataReader();
    private String project;
    private String uvtable;
    private int unique_value_numbers;
    private Hashtable uvh;

    public CXMUniqueValueTableReader()
    {
    }

    public void setFileStatus(String project_name, String file_name)
    {
        project = project_name;
        uvtable = file_name;
        getMetaData();
        fileOpen();
        setUniqueValue();
    }

    public void close()
    {
        uvh.clear();
        ctr.close();
    }

    private void fileOpen()
    {
        ctr.setFileStatus(project,uvtable);
    }

    private void getMetaData()
    {
       cmr.setFileStatus(project,uvtable);
       unique_value_numbers = Integer.parseInt(cmr.getProfile("NUMBER_OF_ROWS"));
       cmr.close();
    }

    private void setUniqueValue()
    {
       String key;
       String count;
       uvh = new Hashtable();
       for (int i=1; i<=unique_value_numbers; i++)
       {
           key = ctr.getStringInColumn(i,1);
           count = ctr.getStringInColumn(i,2);
           uvh.put(key,count);
       }
    }

    public int getUniqueValueNumber()
    {
        return unique_value_numbers;
    }

    public String getCountOfValue(String value)
    {
        return (String) uvh.get(value);
    }

    public String getUniqueValueAt(int row_number)
    {
        return ctr.getStringInColumn(row_number,1);
    }

    public int getValueCountAt(int row_number)
    {
        return ctr.getIntInColumn(row_number,2);
    }

    public Hashtable getUniqueValueTable()
    {
        return uvh;
    }
}