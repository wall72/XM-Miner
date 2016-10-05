package xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.filex;

import java.io.Serializable;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.TreeSet;

import xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.*;
import xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.xmutil.*;

public class CXMColumnInfoManager implements Serializable {

  ColumnInfoType[] colsInfoList = null;
  int numberOfColumns = -1;
  Hashtable filterInfo = new Hashtable(3);

  public Hashtable getFilterInfo() { return filterInfo; };
  public void setFilterInfo(Hashtable f) { filterInfo = f; }
  public void removeFilterInfo() { 
  		System.out.println("remove filter info");
  		filterInfo = new Hashtable(3); }
  public void setNumberOfColumns(int n) { numberOfColumns = n; }
  public int getNumberOfColumns() { return numberOfColumns; }
  public void setColumnInfoList(ColumnInfoType[] cInfoList) { colsInfoList = cInfoList; }
  public ColumnInfoType[] getColumnInfoList() { return colsInfoList; }
  public static CXMColumnInfoManager readObjectColumnInfo(String filename)
  {
      try {
          ObjectInputStream inStream = new ObjectInputStream(new FileInputStream(filename));
          CXMColumnInfoManager ci = (CXMColumnInfoManager)inStream.readObject();
          inStream.close();
          return ci;
      } catch (Exception e) {
          e.printStackTrace();  return null;
      }
  }
  public void writeObjectColumnInfo(String filename)
  {
      try {
          ObjectOutputStream outStream = new ObjectOutputStream(new FileOutputStream(filename));
          outStream.writeObject(this);
          outStream.close();
      } catch (Exception e) {
          e.printStackTrace();
      }
  }
  
  public void setEnumerationList(EnumerationType[] enumList)
  {
  		for (int i=0; i<enumList.length; i++) {
  				TreeSet vList = new TreeSet();
  				for (int j=0; j<enumList[i].values.length; j++) {
  						vList.add(enumList[i].values[j]);
  				}
  				filterInfo.put(enumList[i].key,vList);
  		}
  }
  
  public EnumerationType[] getEnumerationList()
  {
      Object keys[] = filterInfo.keySet().toArray();  		
  		EnumerationType[] enumList = new EnumerationType[keys.length];

  		for (int i=0; i<enumList.length; i++) {
					enumList[i] = new EnumerationType();
					enumList[i].key = (String)keys[i];
     	    TreeSet vList = (TreeSet)filterInfo.get(enumList[i].key);
					enumList[i].values = new String[vList.size()];
					Iterator valueList = vList.iterator();
					int j=0;
					while (valueList.hasNext()) {
							enumList[i].values[j++] = (String)valueList.next();
					}
  		}
  		return enumList;		
  }

  public void readColumnInfo(String filename) throws FaultDataException
  {
      final String NUMBER_OF_COLUMNS = "NUMBER_OF_COLUMNS";
      final String COLUMN_LIST = "COLUMN_LIST";
      final String ENUMERATION_FILTERING_VALUE = "_FILTERING_VALUE";

      try {
          CXMMetaDataReader file = new CXMMetaDataReader();
          file.setFileStatus(filename);
          // 컬럼 수를 읽어들인다.
          String str = file.getProfile(NUMBER_OF_COLUMNS);
          numberOfColumns = str2Int(str);
          colsInfoList = new ColumnInfoType[numberOfColumns];
          // 컬럼 이름의 리스트를 읽어들인다.
          String list[] = file.getProfiles(COLUMN_LIST);
//          if (list == null) throw new FaultDataException("Fault Header File");
          if (numberOfColumns != list.length) throw new FaultDataException("Number of column list is not matched.");

          // 컬럼 정보를 읽어들인다.
          for (int i=0; i<numberOfColumns; i++) {
              	String clist[] = file.getProfiles(list[i]);
					if (clist[0].equals("INTEGER")) { 
							colsInfoList[i] = new ColumnInfoType(list[i], "INTEGER", true);
					} else if (clist[0].equals("REAL")) {
							colsInfoList[i] = new ColumnInfoType(list[i], "FLOAT", true);
					} else if (clist[0].equals("DATE")) {
							colsInfoList[i] = new ColumnInfoType(list[i], "DATE", true);
					} else if (clist[0].equals("STRING")) {
							if (clist[5].equals("not_filtered")) {
									colsInfoList[i] = new ColumnInfoType(list[i], "STRING", true);
							} else {
									String flist[] = file.getProfiles(list[i]+ENUMERATION_FILTERING_VALUE);
									if (flist == null) colsInfoList[i] = new ColumnInfoType(list[i], "ENUMERATION", true);
									else {
            								colsInfoList[i] = new ColumnInfoType(list[i], "ENUMERATION", true);
            								TreeSet enum = new TreeSet();
            								for (int j=0; j<flist.length; j++)  enum.add(flist[j]);
            								filterInfo.put(list[i], enum);											
									}
							}// end of else
					} else {
							colsInfoList[i] = new ColumnInfoType(list[i], "STRING", true);
					}// end of else
          } // end of for
      } catch (NumberFormatException e) {
          throw new FaultDataException("Fault Header File");
      	} catch (Exception e) {
          throw new FaultDataException("Header File Parcing Fault");
      }
  }// end of readColumnInfo

  public void saveColumnInfo(String filename)
  {
      final String NUMBER_OF_COLUMNS = "NUMBER_OF_COLUMNS";
      final String COLUMN_LIST = "COLUMN_LIST";
      final String NUMBER_OF_FILTERING_INFO = "NUMBER_OF_FILTERING_INFO";
      final String ENUMERATION_FILTERING_LIST = "ENUMERATION_FILTERING_LIST";

      CXMMetaDataManager file = new CXMMetaDataManager(filename);
      file.setProfile(NUMBER_OF_COLUMNS, int2Str(numberOfColumns));
      String columnNameList[] = new String[numberOfColumns];
      for (int i=0; i<numberOfColumns; i++) {
        	columnNameList[i] = colsInfoList[i].name;
        	String[] s = new String[2];
        	s[0] = colsInfoList[i].type;
        	if (colsInfoList[i].use) s[1] = "use";
        	else  s[1] = "not_use";
        	file.setProfile(colsInfoList[i].name, s);
      }
      file.setProfile(COLUMN_LIST, columnNameList);

      int size = filterInfo.size();
      file.setProfile(NUMBER_OF_FILTERING_INFO, int2Str(size));
      String list[] = new String[size];
      Object keys[] = filterInfo.keySet().toArray();
      String vlist[];
      Object tlist[];
      for (int i=0; i<size; i++) {
        	list[i] = "f" + int2Str(i);
        	TreeSet elist = (TreeSet)filterInfo.get((String)keys[i]);
        	tlist = elist.toArray();
        	vlist = new String[elist.size()+1];
        	vlist[0] =(String)keys[i];
        	for (int j=0; j<elist.size(); j++) vlist[j+1] = (String)tlist[j];
        	file.setProfile(list[i],vlist);
      }
      file.setProfile(ENUMERATION_FILTERING_LIST,list);
  }
  
  public void saveColumnInfoToMetaFormat(String filename)
  {
      final String NUMBER_OF_COLUMNS = "NUMBER_OF_COLUMNS";
      final String COLUMN_LIST = "COLUMN_LIST";
      final String NUMBER_OF_FILTERING_INFO = "NUMBER_OF_FILTERING_INFO";
      final String ENUMERATION_FILTERING_VALUE = "_FILTERING_VALUE";
      final String ENUMERATION_FILTERING_FILE = "_FILTERING_FILE";

      int useColumnCount = 0;
      for (int i=0; i<numberOfColumns; i++) if (colsInfoList[i].use) useColumnCount ++;

      CXMMetaDataSaver file = new CXMMetaDataSaver();
      file.setFileStatus(filename);
      
      // print [NUMBER_OF_COLUMNS]
      file.setProfile(NUMBER_OF_COLUMNS, int2Str(useColumnCount));
      
      String[] strType = {"STRING", "FINITE", "DISCRETE", "NOMINAL", "not_sorted", "not_filtered", "not_transformed", "not_computed" };
      String[] enumType = {"STRING", "FINITE", "DISCRETE", "NOMINAL", "not_sorted", "filtered", "not_transformed", "not_computed" };
      String[] intType = {"INTEGER", "INFINITE", "DISCRETE", "CARDINAL", "not_sorted", "not_filtered", "not_transformed", "not_computed" };
      String[] realType = { "REAL", "INFINITE", "CONTINUOUS", "CARDINAL", "not_sorted", "not_filtered", "not_transformed", "not_computed" };
      String[] dateType = { "DATE", "INFINITE", "DISCRETE", "CARDINAL", "not_sorted", "not_filtered", "not_transformed", "not_computed" };

      String columnNameList[] = new String[useColumnCount];
      useColumnCount = 0;
//System.out.println("use column count is  " + useColumnCount); 
      for (int i=0; i<numberOfColumns; i++) {
//System.out.print("column name is ");
//System.out.println(colsInfoList[i].name);
          if (colsInfoList[i].use) {
              columnNameList[useColumnCount++] = colsInfoList[i].name;
              // print [column name]
              if (colsInfoList[i].type.equals("INTEGER"))  file.setProfiles(colsInfoList[i].name, intType);
              else if (colsInfoList[i].type.equals("FLOAT"))  file.setProfiles(colsInfoList[i].name, realType);
              else if (colsInfoList[i].type.equals("DATE")) file.setProfiles(colsInfoList[i].name, intType); 
              else if (colsInfoList[i].type.equals("ENUMERATION"))   file.setProfiles(colsInfoList[i].name, enumType);
              else file.setProfiles(colsInfoList[i].name, strType);
          }
      }
      			

      // pirnt [COLUMN_LIST]
      file.setProfiles(COLUMN_LIST, columnNameList);
   		String colIndex[] = new String[columnNameList.length];
   		for (int i=0; i<columnNameList.length; i++) colIndex[i] = new Integer(i+1).toString();
   		file.setProfiles("COLUMN_INDEX", colIndex);

      int size = filterInfo.size();
      Object keys[] = filterInfo.keySet().toArray();
      String vlist[];
      Object tlist[];
      for (int i=0; i<size; i++) {
//      			System.out.println("@ keys is " + (String)keys[i]); 
      			Object obj = filterInfo.get((String)keys[i]);
        		if (obj instanceof TreeSet)   {
        				TreeSet elist = (TreeSet)obj;
        				tlist = elist.toArray();
        				vlist = new String[elist.size()];
        				for (int j=0; j<elist.size(); j++) vlist[j] = (String)tlist[j];
        					// print [column name_FILTERING_VALUE]
        				file.setProfiles((String)keys[i]+ENUMERATION_FILTERING_VALUE, vlist);
        		} else {
        		} 
      }

     	file.close();
  }
    int str2Int(String str) throws NumberFormatException
    {
       		Integer i = new Integer(str);
       		return i.intValue();
    }
    float str2Float(String str) throws NumberFormatException
    {
       	Float f = new Float(str);
       	return f.floatValue();
    }
  String int2Str(int i) throws NumberFormatException
  {
      Integer ii = new Integer(i);
      return ii.toString();
  }

}