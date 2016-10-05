/** *******************************************************************
 *      CXMDirectoryManager.java
 *
 *      2000.  1. 13. : first programmed by Lee Hyun-Young
 *      2000.  1. 13. : last modified by Lee Hyun-Young
 * 			---------------------------------------------------------------
 *     디렉토리 관리자 (생성, 삭젝, 리스트)
 *   ******************************************************************* */
package xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.filex;
import java.io.*;
import java.util.Vector;
import java.util.Date;
import java.lang.StringBuffer;
import java.util.GregorianCalendar;
import java.util.Calendar;
import xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.DirectoryType;

public class CXMDirectoryManager implements Serializable 
{
  final static boolean READ_ONLY = false;
  final static boolean READ_WRITE = true;
  boolean accMode = READ_ONLY;
  public String name = null;
  public String rootPath = null;
//  String workPath = null;
  String dirDelimiter = null;
  String notAcceptedExtList[] = {"envi", "work", "BAK", "list"};
 
  	private String makeDate(long lastModified, GregorianCalendar calendar)
 	{
				Date makeDate = new Date(lastModified);			
				calendar.setTime(makeDate);
								
				StringBuffer whenMake = new StringBuffer(new Integer(calendar.get(Calendar.YEAR)).toString());
				whenMake.append('-');
				int m = calendar.get(Calendar.MONTH)+1;
				if (m <10) whenMake.append('0');
				whenMake.append(m);
				whenMake.append('-');
				int d = calendar.get(Calendar.DATE);
				if (d < 10) whenMake.append('0');
				whenMake.append(d);
				m = calendar.get(Calendar.AM_PM);
				if (m == 0) 	whenMake.append(" 오전 ");
				else 	whenMake.append(" 오후 ");
				m = calendar.get(Calendar.HOUR);
				whenMake.append(new Integer(m).toString());
				m = calendar.get(Calendar.MINUTE);
				whenMake.append(":");
				whenMake.append(new Integer(m).toString());
				return whenMake.toString();
  	}
  	private String makeSize(long size)
  	{
  			size = size >> 10;
  			if (size > 999) {
  					StringBuffer s = new StringBuffer(new Long(size).toString());
  					int i = s.length();
  					s.insert(i-3, ',');
  					s.append("KB ");
  					return s.toString(); 
  			} else 	if (size <= 0){
  					return "1KB ";
  			} else {
  					String s = new Long(size).toString() + "KB ";
  					return s;
  			}
  	}

	
  public CXMDirectoryManager(String n, boolean mode, String root, String del)
  {
      name = n;
      accMode = mode;
      rootPath = root;
      dirDelimiter = del;
  }

  public CXMDirectoryManager(String mode, String root, String del)
  {
      System.out.println("CXMDirectoryManager is created. mode is " + mode + " root is " + root + " del is " + del);
      if (mode.equals("READ_WRITE")) accMode = READ_WRITE;
      rootPath = root;
      dirDelimiter = del;
  }

  public DirectoryType[] getDirectoryInfo()
  {
 	  System.out.println("getDirectoryInfo [CXMDirectoryManager] is called");
    
 	  int index = -1;
	  int cindex = -1;
	  Vector dirList = new Vector(5,3);
	  DirectoryType branch = new DirectoryType();
	  File f1 = new File (rootPath);
	  if ( f1.isDirectory() ) {
		  branch.nodeName = " ";
		  branch.nodeType = "folder";
		  branch.nodeSize = " ";
		  branch.nodeDate = " ";
//		  FilenameFilter filter = new NotAcceptedExt(notAcceptedExtList);
		  File[] filelist = f1.listFiles(); //f1.list(filter);
		  String[] s= f1.list();
		  branch.childnodeNames = s;
		  cindex = dirList.size();
		  dirList.addElement(branch);
		  for (int i=0; i<s.length; i++) {
			  index = addDirectoryList(filelist[i], dirList);
			  branch.childnodeNames[i] = new Integer(index).toString();
		  } // end of for
		  dirList.setElementAt(branch,cindex);
	  } else {
		  System.out.println(f1.toString() + "root directory is not valid");
	  }

	  int size = dirList.size();
	  DirectoryType[] dList = new DirectoryType[size];
//	  System.out.println("create directory list .................");
	  for (int i=0; i<size; i++) {
		  dList[i] = (DirectoryType)dirList.elementAt(i);
//		  System.out.print("[ " + i + " ] " + dList[i].nodeName);
//		  for (int j=0; j<dList[i].childnodeNames.length; j++) 
//		  	System.out.print(":" + dList[i].childnodeNames[j]);
//		  System.out.println("");	
	  }
	  return dList;
  }  // end of getDirectoryInfo

  int addDirectoryList(File file, Vector dirList)
  {
			GregorianCalendar calendar = new GregorianCalendar();
 			int cindex = dirList.size();
 			int index = -1;
 			DirectoryType branch = new DirectoryType();

//  		String dir = new String(filepath + dirDelimiter + filename);
//  		File f = new File(dir);
  		branch.nodeName = file.getName();;
  		branch.nodeDate = makeDate(file.lastModified(), calendar);

  		if (file.isDirectory()) {
  				branch.nodeSize = "";
  				branch.nodeType = "folder";
//					FilenameFilter filter = new NotAcceptedExt(notAcceptedExtList);
  				File[] filelist = file.listFiles(); //list(filter);
  				String[] s = file.list();
  				if (filelist != null) branch.childnodeNames = s;
  				else {
  						String[] ss = { "NONE" };
  						branch.childnodeNames = ss;
  				}
					dirList.addElement(branch);
  				for (int i=0; i<s.length; i++) {
  						index = addDirectoryList(filelist[i], dirList);
							branch.childnodeNames[i] = new Integer(index).toString();
  				}
  				dirList.setElementAt(branch, cindex);
  		} else {
  				branch.nodeSize = makeSize(file.length());
  				String[] s = { "NONE" };
  				branch.nodeType = "file";
  				branch.childnodeNames = s;
  				dirList.addElement(branch);  		
  		}
  		return cindex; 
  }//end of addDirectoryList

  public boolean makeDirectory(String dirname)
  {
      if (accMode == READ_WRITE) {
          File file = new File(rootPath + dirname);
          if (!file.isDirectory()) {
            if (!file.isFile()) {
                return file.mkdir();
            }
          }
      }
      return false;
  }
  public boolean delDirectory(String dirname)
  {
     if (accMode == READ_WRITE) {
        File file = new File(rootPath + dirname);
        if (file.isDirectory()) return file.delete();
     }
     return false;
  }
  public boolean delFile(String filename)
  {
     if (accMode == READ_WRITE) {
        File file = new File(rootPath + filename);
        if (!file.isDirectory()) return file.delete();
     }
     return false;
  }
  public String getDirectoryDelimiter() {    return dirDelimiter;   }
  public String getWorkPath() {   return rootPath;    }
  

   public void copyFile(String in, String out) throws Exception 
   {
     	System.out.println("file " + in + " is copied to " + rootPath + out); 
	FileInputStream fis  = new FileInputStream(new File(in));
     	FileOutputStream fos = new FileOutputStream(new File(rootPath+out));
     	byte[] buf = new byte[1024];
     	int i = 0;
     	while((i=fis.read(buf))!=-1) {
       		fos.write(buf, 0, i);
       		System.out.println("file copy : " + buf);
     	}
     	fis.close();
     	fos.close();
   }

} // end of class
