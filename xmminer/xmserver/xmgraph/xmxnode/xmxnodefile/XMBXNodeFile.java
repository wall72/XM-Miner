/**
 *		XMBXNodeFileImpl.java
 *		programmed by Lee, Hyun-Young
 *		2000. 6. 9
 */
package xmminer.xmserver.xmgraph.xmxnode.xmxnodefile;

import java.util.*;
import java.io.*;
import org.omg.CORBA.*;
import javax.swing.*;

import xmminer.*;
import xmminer.xmlib.*;
import xmminer.xmserver.*;
import xmminer.xmserver.xmgraph.*;
import xmminer.xmlib.xmtable.CXMSetMetaDataColumnProperty;
import xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.*;

import xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.filex.*;
import xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.xmutil.*;
import xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.xmfileio.*;

public class XMBXNodeFile implements Runnable
{
//   final String DIR_BACKUP_FILENAME = "dirbackup.info";
   
  	String _workfilepath = "";
  	String _errorlogfilename = "";
  	String _projectname = "";  
  	XMXNodeFileInfo _info = null;
  	CXMColumnInfoManager _cman = new CXMColumnInfoManager();
  	FileIndex findex = null;
  	int _numOfPreviewLines = 0;
  	FilePath previewFile = null;
  	String dirDelimiter = System.getProperty("file.separator");
  	Properties dirProperties = null;
  	CXMDirectoryManager dirManager[] = null;
	int outputID = 0;
	String datePattern = new String("yyyy-MM-dd");
	
  	public Keeper keeper = null;
  	private Thread thread;
  	
  	public XMBXNodeFile()
  	{
  			super();
//	init();
  	}

  	public void setProjectInfo(FilePath prj){
 			System.out.println("setProjectInfo [XMBXNodeFileImpl] is called... projectname is " + _projectname);	
    		isDisconnect = false;

   			//2002.10.03 C/S 추가
			try{
				dirProperties = new Properties();
   				dirProperties.load(new FileInputStream("environment.properties"));

				_workfilepath = dirProperties.getProperty("DIR1") + "/"; //prj.path[0]; 2002.09.29 C/S 수정
				System.out.println("_workfilepath = " + _workfilepath);
    		} catch (IOException e) {
      			System.out.println(e.getMessage());
    		}

  			_projectname = prj.filename;
  			keeper = null;
  			thread = null;
  			dir_init("environment.properties");
 			System.out.println("setProjectInfo [XMBXNodeFileImpl] is end... projectname is " + _projectname);	
 			_workfilepath += _projectname + "/";
 			File dir = new File(_workfilepath);
 			if (!dir.exists()) {
 					if (dir.mkdir()) {
 							new File(_workfilepath+"meta").mkdir();
 							new File(_workfilepath+"data").mkdir();
 							new File(_workfilepath+"index").mkdir();
 					} else {
 							System.out.println("project directory creating Failure !!!");
 					}
 			}
  	}
  	
  	private boolean isDisconnect = false;
  	public void disconnect()
  	{
  			System.out.println("disconnect is called");
  			isDisconnect = true;		
  	}
  	public boolean isDisconnect()
  	{
  			System.out.println("isDisconnect is called");
  			return isDisconnect;
  	}
  	
  	public void dir_init(String propfilename)
  	{
    	try {
      			dirProperties = new Properties();
      			dirProperties.load(new FileInputStream(propfilename));
      			String dirDelimiter = dirProperties.getProperty("DIRECTORY_DELIMITER");
      			StringUtility u = new StringUtility();
      			int num = u.str2Int(dirProperties.getProperty("NUMBER_OF_DIRECTORIES"));
      			dirManager = new CXMDirectoryManager[num];
      			for (int i=0; i<num; i++) {
      					dirManager[i] = new CXMDirectoryManager("DIR"+i,true,dirProperties.getProperty("DIR"+i),dirDelimiter);
      					System.out.println("DIR"+i+" : " + dirProperties.getProperty("DIR"+i));
      			}// end of for
    	} catch (IOException e) {
      			System.out.println(e.getMessage());
    	}
  	}// end of dir_init
  	
  	public void setDatePattern(String pattern)
  	{
  			_info.datePattern = pattern;
  	}
  	public String[] getAvailablePatterns()
  	{
  			String[] patterns = { "yyyy.MM.dd G 'at' hh:mm:ss z", 
  												"EEE, MMM d, ''yy", 
  												"yyyyy.MMMMM.dd GGG hh:mm aaa",
  												"h:mm a",
  												"yyyy-MM-dd" };
 			return patterns;
  	}

  	public void createMetafile(int outputID)
  	{
    		System.out.println("createMetafile [ExtractorImpl] is called.");

        	this.outputID = outputID;
    		_cman.setNumberOfColumns(_info.numberOfColumns);
    		_cman.setColumnInfoList(_info.columnInfo);
    		_cman.setEnumerationList(_info.enumList);

        	_cman.saveColumnInfoToMetaFormat(_workfilepath + "meta/arc" + outputID);
        	prnEtcMeta(_workfilepath + "meta/arc" + outputID, _cman.getNumberOfColumns(), _info.delimiter.getBytes(), _info.nullvalue.getBytes(), outputID);    
  	}// end of createMetafile

   private void prnEtcMeta(String newdatafilename, int colNum, byte[] del, byte[] nullValue, int outputID)
   {
   			CXMMetaDataSaver file = new CXMMetaDataSaver();
   			file.setFileStatus(newdatafilename);
   			file.setProfile("PREVIOUS_NODE", "XMXNodeFile");
   			file.setProfile("COLUMN_DELIMITER", new String(del));
   			file.setProfile("NULL_VALUE", new String(nullValue));
			file.setProfile("DATA_FILE", "arc"+outputID);
			file.setProfile("ROW_INDEX", "arc"+outputID);

			ExtractRunner runner = new ExtractRunner();
			
			file.setProfile("NUMBER_OF_ROWS", String.valueOf(runner.calculateNumberOfLines(makeFilePath(_info.srcdata))));
   			file.close();
   	}
  	
  	public void PerformRunning(int outputID)
  	{
  			this.outputID = outputID;
  			start();
  	}// end of PerformRunning
  
  	public void start()
  	{
  			if (thread == null) {
  					thread = new Thread(this);
  					thread.start();
  			}
  	}// end of start
  
  	public void run() 
  	{
  			if (keeper == null) keeper = new Keeper();
  			extract(outputID);
  			keeper.setState(false);	
  	}// end of run

  	public void extract(int outputID)
  	{
    		System.out.println("extract [ExtractorImpl] is called.");
    		_cman.setNumberOfColumns(_info.numberOfColumns);
    		_cman.setColumnInfoList(_info.columnInfo);
    		_cman.setEnumerationList(_info.enumList);
    
    		System.out.println("\n extract information....\n");
    		System.out.println("# of column : " + _info.numberOfColumns + " = " + _info.columnInfo.length);
    		System.out.println("# of enumerationlist : " + _info.enumList.length);
    		System.out.println("datePattern : " + _info.datePattern);
    
    		ExtractRunner runner = new ExtractRunner();
    		long lineCount = runner.extract(makeFilePath(_info.srcdata),   //기존 데이터이름
				_workfilepath,
				"arc" + outputID,
//            _workfilepath + makeNewFilename(_info.srcdata.filename),   //새데이터 이름
            _cman,   //컬럼 정보
            _info.delimiter.getBytes(),         //컬럼 구분자
            _info.nullvalue.getBytes(),        //널값 표시 기호
            keeper,
             new java.text.SimpleDateFormat(_info.datePattern, java.util.Locale.KOREA),
             _info.ignoreLinePointer,  
            _projectname);
    
    		// extracted row count를 .meta에 기록한다.
    		// 단 새로운 CXMMetaData를 이용할 것이므로 추하에 생성)
    		System.out.println(long2Str(lineCount) + " rows is extracted successfully");
  	}// end of extract

  	public long getNumberOfProcessedLines()
  	{
  			return 0;
  	}

  	public int getProcessedRate()
  	{
  			System.out.println("\n@ GetRate messgae reached in " + _projectname);  	
  			if (keeper == null) keeper = new Keeper();
  			return keeper.getRate();
  	}
  	
  	public void stop()
  	{
  			System.out.println("\n stop messgae reached in " + _projectname);
  			keeper.setState(false);
  	}
  	
  	String long2Str(long i) throws NumberFormatException
  	{
      		Long ii = new Long(i);
      		return ii.toString();
  	}
  
  	String makeNewFilename(String srcfilename)
  	{
    		char[] NEWDATA_END = { '_', 'r', 'e', 's' };

    		char[] name = srcfilename.toCharArray();
    		int i,j;
    		for (i=name.length-1; i>=0; i--)
        			if (name[i] == '.') break;
    		char[] newname = new char[i+3];
    		for (j=0; j<i-1; j++)  newname[j] = name[j+1];
    		for (i=0; i<NEWDATA_END.length; i++) newname[j+i] = NEWDATA_END[i];
    		return new String(newname);
  	}

  	public void parcingHeaderFile(FilePath hdr) 
  	{
 			System.out.println("parcingHeaderFile [XMBXNodeFileImpl] is called...");
  			_cman.removeFilterInfo();
  			if (_info == null) {
  					_info = new XMXNodeFileInfo();
  					_info.nullvalue = "";
  			}
  			_info.hdrdata = hdr;
  			_info.delimiter = "";	

    		try {
      				_cman.readColumnInfo(makeFilePath(hdr));
      				_info.numberOfColumns = _cman.getNumberOfColumns();
      				_info.columnInfo = _cman.getColumnInfoList();
      				_info.enumList = _cman.getEnumerationList();
//      		System.out.println("enum list length is " + _info.enumList.length);
//      		for (int i=0; i<_info.enumList.length; i++) System.out.println(" key is " + _info.enumList[i].key);      		
      				CXMMetaDataReader mman = new CXMMetaDataReader();
          		mman.setFileStatus(makeFilePath(hdr));
      				_info.delimiter = mman.getProfile("COLUMN_DELIMITER");
    		} catch (FaultDataException e) {
    		}
  	}// end of parcingHeaderFile

  	public void parcing(String delimiter, int cnamePointer, int ignoreLinePointer) 
  	{
 			System.out.println("setAllInfo [parcing] is called...");	
  			_cman.removeFilterInfo();
  			if (_info == null) {
  					_info = new XMXNodeFileInfo();
  					_info.hdrdata = new FilePath();
  					_info.nullvalue = "";
  			}	
  			_info.hdrdata.filename = "";
  			_info.delimiter = delimiter;
  			_info.columnNameLinePointer = cnamePointer;
  			_info.ignoreLinePointer = ignoreLinePointer; 
  			
  			try {
  					System.out.println("src data is " + _info.srcdata.path[0] + " , " + _info.srcdata.filename
  							+ "  " + cnamePointer + "  " + ignoreLinePointer);
  					CXMDataFileReader reader = new CXMDataFileReader(makeFilePath(_info.srcdata), delimiter);
  					if (cnamePointer == -1) 	reader.setIgnoreLinePointer(ignoreLinePointer);
  					_info.numberOfColumns = reader.calNumberOfColumns();
  					if (cnamePointer != -1) {
  							String[][] cname = reader.readRows(cnamePointer+1);
  							if (cname[cnamePointer].length == _info.numberOfColumns)
  								_info.columnInfo = makeDefaultColumnInfo(cname[cnamePointer]);
  							else _info.columnInfo = makeDefaultColumnInfo(_info.numberOfColumns);
  					} else {
  							_info.columnInfo = makeDefaultColumnInfo(_info.numberOfColumns);
  					}
  			} catch (Exception e) {
  				e.printStackTrace();
  			}
  	}// end of parcing
  
  	private ColumnInfoType[] makeDefaultColumnInfo(String[] cname)
  	{   	  
    	  	ColumnInfoType[] cInfo = new ColumnInfoType[cname.length];
	  		for (int i=0; i<cname.length; i++) {
		  			cInfo[i] = new ColumnInfoType(cname[i], "STRING", true);
	  		}
	  		return cInfo;
  	}// end of makeDefaultColumnInfo
   	private ColumnInfoType[] makeDefaultColumnInfo(int num)
  	{   	  
    	  	ColumnInfoType[] cInfo = new ColumnInfoType[num];
	  		for (int i=0; i<num; i++) {
		  			String name = "CName_" + i;
		  			cInfo[i] = new ColumnInfoType(name, "STRING", true);
	  		}
	  		return cInfo;
  	}// end of makeDefaultColumnInfo
 
 	public void setAllInfo(XMXNodeFileInfo info) 
 	{	
 				System.out.println("setAllInfo [XMBXNodeFileImpl] is called...");	
 				_info = info;	
 	}
 
  	public XMXNodeFileInfo getAllInfo() 
  	{ 	
 			System.out.println("getAllInfo [XMBXNodeFileImpl] is called...");	
  			return _info; 
  	}

  	public void fileUpload (String filename, byte[] buf)
  	{
    		try {
    				System.out.println("fileUpload [ExtractorImpl] is called. file is " + filename + " saved path is " + _workfilepath);
    				OutputStream outStream = new FileOutputStream(_workfilepath+filename);
    				outStream.write(buf);
    				outStream.close();
    		} catch (Exception e) {
    				e.printStackTrace();
    		}
  	}
  	
  	private DirectoryType[] readDirectoryInfo(File file) throws Exception
  	{
  			ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
  			int count = in.readInt();
  			DirectoryType[] dirList = new DirectoryType[count];
  			for (int i=0; i<count; i++) {
  					dirList[i] = (DirectoryType)in.readObject();
  			}
  			return dirList;
   }// end of readDirectoryInfo
  	private void writeDirectoryInfo(File file, DirectoryType[] dirList) throws Exception
  	{
  			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
  			out.writeInt(dirList.length);
  			for (int i=0; i<dirList.length; i++) {
  					out.writeObject(dirList[i]);
  			}
   }// end of readDirectoryInfo
  
  	public DirectoryType[] getInitDirectoryInfo()
	{
      		System.out.println("getInitDirectoryInfo is called... ");
      		// 모든 Directory 정보를 담기위한 구조체를 생성한다.
      		int num = 1;
      		DirectoryType[] dirList = new DirectoryType[dirManager.length*2+1];
      		// Directory 정보를 하나로 병합한다.
      		dirList[0] = new DirectoryType();
      		dirList[0].nodeName = "ROOT";
      		dirList[0].nodeType = "folder";
      		dirList[0].nodeDate = " ";
      		dirList[0].nodeSize = " ";
      		dirList[0].childnodeNames = new String[dirManager.length];
      		dirList[0].childnodeNames[0] = "1";
      		dirList[0].childnodeNames[1] = "3";

      				dirList[1] = new DirectoryType();
      				dirList[1].nodeName = dirManager[0].name;	
      				dirList[1].nodeType = "folder";
      				dirList[1].nodeDate = " ";
      				dirList[1].nodeSize = " ";
      				String[] n1 = { "2" };
      				dirList[1].childnodeNames = n1;
      				dirList[2] = new DirectoryType();
      				dirList[2].nodeName = "temp";	
      				dirList[2].nodeType = "folder";
      				dirList[2].nodeDate = " ";
      				dirList[2].nodeSize = " ";
      				String[] n = { "NONE" };
      				dirList[2].childnodeNames = n;

      				dirList[3] = new DirectoryType();
      				dirList[3].nodeName = dirManager[1].name;	
      				dirList[3].nodeType = "folder";
      				dirList[3].nodeDate = " ";
      				dirList[3].nodeSize = " ";
      				String[] nn = { "4" };
      				dirList[3].childnodeNames = nn;
      				dirList[4] = new DirectoryType();
      				dirList[4].nodeName = "temp";	
      				dirList[4].nodeType = "folder";
      				dirList[4].nodeDate = " ";
      				dirList[4].nodeSize = " ";
      				dirList[4].childnodeNames = n;
/*
      		for (int i=0; i<dirManager.length; i++) {
      				dirList[i+1] = new DirectoryType();
      				dirList[i+1].nodeName = dirManager[i].name;	
      				dirList[i+1].nodeType = "folder";
      				dirList[i+1].nodeDate = " ";
      				dirList[i+1].nodeSize = " ";
      				String[] n = { "NONE" };
      				dirList[i+1].childnodeNames = n;
      		}
   */   		return dirList;
	}// end of getInitDirectoryInfo

  	//directory 정보를 Depth First Search 순서의 String 배열로 리턴
  	public DirectoryType[] getDirectoryInfo()
  	{
      		System.out.println("getDirectoryInfo is called... ");
      		// Directory 정보를 읽어들인다.
      		DirectoryType[][] dir = new DirectoryType[dirManager.length][];
      		File dirFile = null;
      		try {
      			for (int i=0; i<dirManager.length; i++) {
//      				dirFile =  new File(_workfilepath+dirDelimiter+dirManager[i].name+".dir");
//      				if (dirFile.exists()) {
//      						dir[i] = readDirectoryInfo(dirFile);
//      				} else {
      						dir[i] = dirManager[i].getDirectoryInfo();
//      						writeDirectoryInfo(dirFile, dir[i]);
//      				}
      			}// end of for
      		} catch (Exception e) {
      			e.printStackTrace();
      		}

      		// 모든 Directory 정보를 담기위한 구조체를 생성한다.
      		int num = 1;
      		for (int i=0; i<dirManager.length; i++) num = num + dir[i].length;
      		DirectoryType[] dirList = new DirectoryType[num];

      		// Directory 정보를 하나로 병합한다.
      		dirList[0] = new DirectoryType();
      		dirList[0].nodeName = "ROOT";
      		dirList[0].nodeType = "folder";
      		dirList[0].nodeDate = " ";
      		dirList[0].nodeSize = " ";
      
      		for (int i=0; i<dirManager.length; i++)
        			dir[i][0].nodeName = dirManager[i].name;

      		dirList[0].childnodeNames = new String[dirManager.length];
      		dirList[0].childnodeNames[0] = "1";
      		dirList[0].childnodeNames[1] = "14";
      
      		int addCount = dir[0].length + 1;
      		try {
      				dirList[0].childnodeNames[0] = "1";
       			dirList[0].childnodeNames[1] = new Integer(addCount).toString();
      		} catch (NumberFormatException e) {
      				System.out.println(e.getMessage());
      		}

      		try {
       			for (int i=0; i<dir[0].length; i++) {
     						dirList[i+1] = new DirectoryType();
      						dirList[i+1] = dir[0][i];
      	
   							if ( 		 (dirList[i+1].childnodeNames.length > 0) 
   									&& (!dirList[i+1].childnodeNames[0].equals("NONE")) 
   								) { 
      	   							for (int n=0; n<dirList[i+1].childnodeNames.length; n++) {
      	   									int count = new Integer(dirList[i+1].childnodeNames[n]).intValue();
      										dirList[i+1].childnodeNames[n] = new Integer(count + 1).toString();
      	   							}
      						}// end of if
       			}// end of for
      		} catch (NumberFormatException e) {
      		}
      
      		try {
       			for (int i=dir[0].length+1; i<dir[0].length+1+dir[1].length; i++) {
     						dirList[i] = new DirectoryType();
      						dirList[i] = dir[1][i-dir[0].length-1];
   	
   							if ( 		 (dirList[i].childnodeNames.length > 0) 
   									&& (!dirList[i].childnodeNames[0].equals("NONE")) 
   								) { 
      	   							for (int n=0; n<dirList[i].childnodeNames.length; n++) {
      	   									int count = new Integer(dirList[i].childnodeNames[n]).intValue();
      										dirList[i].childnodeNames[n] = new Integer(count + addCount).toString();
      	   							}
      						}// end of if
      				}// end of for
      		} catch (NumberFormatException e) {
      		}
      		return dirList;
  	}  // end of getDirectoryInfo



/* *****************************************************
 *	getRows, getLines 관련
 * ***************************************************** */
  public java.lang.String[][] getRows(FilePath file, int numberOfRows) throws FaultDataException
  {
    	System.out.println("getRows [XMBXNodeFileImpl] is called... filename is " + makeFilePath(file)+ "   delimtier is " + _info.delimiter);
    	String rows[][] = null;
    	try {
    			CXMDataFileReader data = new CXMDataFileReader(makeFilePath(file),_info.delimiter);
    			data.setNumberOfColumns(_info.numberOfColumns);
    			rows = data.readRows(numberOfRows);
        	data.close();
    	} catch (IOException e) {
    			System.out.println(e.getMessage());
      }
    	return rows;
  }

  public java.lang.String[] getLines(FilePath file, int numOfLines) throws FaultDataException
  {
      previewFile = file;
      _numOfPreviewLines = numOfLines;

    	String lines[] = null;
      PointAccessFileReader freader;
//    	try {
      freader = new PointAccessFileReader(makeFilePath(previewFile),_numOfPreviewLines);

      lines = freader.readNextLines();
      findex = freader.close();
//    	} catch (IOException e) {

    	return lines;
  } //end of getLines

  public java.lang.String[] getDefaultNextLines() throws FaultDataException
  {
		System.out.println("getDefaultNextLines"); 
    	String lines[] = null;
      PointAccessFileReader freader;
//    	try {
        if (findex == null) {
            throw new FaultDataException("작동 순서를 제대로 따라 주세요.");
        } else {
            freader = new PointAccessFileReader(makeFilePath(previewFile), _numOfPreviewLines,findex);
        }

        lines = freader.readNextLines();
        findex = freader.close();
		System.out.println("getDefaultNextLines end"); 
//    	} catch (IOException e) {

    	return lines;
  } //end of getLines

  public java.lang.String[] getDefaultPreviousLines() throws FaultDataException
  {
		System.out.println("getDefaultPreviousLines"); 
     	String lines[] = null;
      PointAccessFileReader freader;
//    	try {
        if (findex == null) {
            throw new FaultDataException("작동 순서를 제대로 따라 주세요.");
        } else {
            freader = new PointAccessFileReader(makeFilePath(previewFile),  _numOfPreviewLines,findex);
        }

        lines = freader.readPreviousLines();
        findex = freader.close();
//    	} catch (IOException e) {

    	return lines;
  } //end of getLines

  public String[][] getNotExtractedRow()
  {
/*	System.out.println("getNotExtractedRow [ExtractorImpl] is called");
	CXMUtility util = new CXMUtility();

    	CXMMetaDataManager meta = new CXMMetaDataManager(makeFilePath(_errorlogpath, _errorlogfilename));
    	int num = util.str2Int(meta.getProfile("NUMBER_OF_ERRORS"));
    	ErrorRowType[] errRows = new ErrorRowType[num];

    	for (int i=0; i<num; i++)
    	{
    		errRows[i] = new ErrorRowType();
    		errRows[i].row = meta.getProfiles(util.int2Str(i));
    		errRows[i].errorColumn = 0;
    		errRows[i].message = "Error Row";
    	}                  */

    	return null;
  }
  public String[][] getNextNotExtractedRow()
  {
      return null;
  }

  	private String makeFilePath(FilePath file)
  	{
	  		for (int i=0; i<dirManager.length; i++) {
		  			if (file.path[0].equals(dirManager[i].name)) { 
		  					String filepath = new String(dirManager[i].rootPath);
		  					for (int j=1; j<file.path.length; j++) filepath = filepath + dirDelimiter + file.path[j];
		  					filepath = filepath + dirDelimiter + file.filename;
        					return filepath;
        			}
	  		}
    		System.out.println("Falut file name");
    		return null;
  	}

//  public static ServerMonitorPage monitor = null;
//  private void init() {
//    java.util.ResourceBundle res = java.util.ResourceBundle.getBundle("xmminer.xmserver.ServerResources");
//    String name = "XMBXNodeFile";
//    if (monitor == null) {
////    monitor = ServerMonitor.addPage(this, name);
////    monitor.showObjectCounter(true);
////	  monitor.showReadCounter(true);
////	  monitor.showWriteCounter(true);
//      ServerMonitor.register(name);
//    }
////    monitor.updateObjectCounter(1);
//    ServerMonitor.log(ServerResources.format(res.getString("created"), name));
//  }
}// end of XMXNodeFile CLASS