/*
 *	programmed by Lee Hyun-Young : 2000. 4. 15
 */
package xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.filex;

import java.io.*;
import java.util.Hashtable;
import java.util.TreeSet;
import java.util.Set;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.ColumnInfoType;
import xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.xmutil.CXMMetaDataSaver;

public class ExtractRunner 
{

  	Hashtable enumList = new Hashtable();		// enumeration 타입에 대한 미생성 목록을 가지고 있다. 진행과정에서 생성
  	byte[] NULL_VALUE = "-".getBytes();
  	int rowUnit = 5;												// row index에 대한 byte 사이즈 단위, 최대값이 5로 fix되어 있음  
	final int MAX_ENUM_VALUES = 4096;			// 
	SimpleDateFormat dFormatter = null;
	int dateCount = 0;
	
  	// enumeration type들을 위한 list를 초기화한다.
  	// columnInfo에 이미 리스트가 존재하면 그것을 사용, 존재하지 않을면 새로 생성할 enumList에 추가
  	private void initNewEnumerationList(CXMColumnInfoManager colsInfo)
  	{
      		final String ENUMERATION = "ENUMERATION";
      		for (int i=0; i<colsInfo.numberOfColumns; i++) {
          		// column type이 enumeration 이면
          		if (colsInfo.colsInfoList[i].type.equals(ENUMERATION)) {
              			// column name이 필터링 정보의 키에 존재하지 않으면
              			if (!colsInfo.filterInfo.containsKey(colsInfo.colsInfoList[i].name)) {
                  				// 새로 생성할 enumeration list에 추가
//                  				System.out.println("# key is " + colsInfo.colsInfoList[i].name);
                  				enumList.put(colsInfo.colsInfoList[i].name, new TreeSet());
              			}// end of if
          		} else if (colsInfo.colsInfoList[i].type.equals("DATE")) {
							dateCount++;
          		}// end of if
      		}// end of for
  	}// end of initNewEnumerationList
  
 	// 데이터의 전체 row를 계산한다. 
 	public long calculateNumberOfLines(String filename)
  	{
    		System.out.println("\n calculate number of rows.....");
    		long count = 0;
    		try {
      				FileInputStream fi = new FileInputStream(filename);

      				byte[] b = "\n".getBytes();
      				byte crlf = b[0];
      				byte inbuffer[] = new byte[4096];

      				while (fi.available() > 0) {
          				int n = fi.read(inbuffer);
          				for (int i=0; i<n; i++) {
            						if (inbuffer[i] == crlf) {
              							count ++;
//              							if ( (count%500) == 0 ) System.out.print(count + "-");
            						}
          				}// end of for
      				} //end of while

      				fi.close();
    		} catch (Exception e) {
      				e.printStackTrace();
    		}
    		System.out.println("\n calculation end.......\n");
    		return count;  	
  	}// end of calculateNumberOfLines

  	public long extract(String datafilename, 								// source data filename
  									String outfilepath,						// new filename for extracted data
  									String outputnodename,
    								CXMColumnInfoManager colsInfo, 		// meta information
    								byte[] inColumnDelimiter, 						// column delimiter for source data file
    								byte[] nullvalue, 									// null value sign for source data file
    								Keeper keeper,										// 계속 진행할지 여부를 문의하기 위한 상태 플러그 내장
    								SimpleDateFormat formatter, 
    								int ignoreLinePointer, 
    								String prjname)			
  	{
    		System.out.println("extract is called. project name is " + prjname +  
    			" \n source filename is " + datafilename + " new name is " + outputnodename);
    		NULL_VALUE = nullvalue;
    		dFormatter = formatter;
    		String ERROR_FILE_EXTENTION = ".err";
    		byte[] CRLF = "\n".getBytes();
    		byte[] ERR_DEL = " : ".getBytes();
    		long numberOfLines = -1;
    		
    		try {
        			initNewEnumerationList(colsInfo);
        			numberOfLines = calculateNumberOfLines(datafilename) - ignoreLinePointer + 1;
        			System.out.println("\n number of all lines is " + numberOfLines);
        			keeper.initCalculateRate(numberOfLines);
    		} catch (Exception e) {
       			System.out.print("initialize new enumeration list...");
       			e.printStackTrace();
       			return 0;
    		}
    
    		int lineUnit = 1000;					// 줄 단위 (화면에 진행상태 출력, Keeper에 진행상태 기록, row index file write 단위) 
    		long rowStartPosition = 0;		//
    		String line;								// 읽어들인 문자열을 간진
    		byte inBuffer[];						// 읽어들인 문자열에 대한 byte[]
    		byte outBuffer[];					// out byte arrray
    		byte rowIndexBuffer[] = new byte [rowUnit*lineUnit];	// row index를 가지고 있는 배열
    		int rowIndexBufferIndex = 0;			// 위 배열에 대한 index
    		int sindex, slength;  // index for inBuffer ( point column start, column length )
    		int numberOfColumns = colsInfo.numberOfColumns;
    		int columnLocList[][] = new int[numberOfColumns][2];  //0:시작위치 1:length
    		int columnCount = 0;				// 컬럼 수
    		long lineCount = 0;					// 라인 수
    		long errLineCount = 0;			// 오류 라인 수
    		boolean checkResult = true;		// 현재의 줄이 오류가 있는지 없는 지 (true : 정상, false : 오류 발생)


    		try {
					FileReader freader = new FileReader(datafilename);
        			BufferedReader fin = new  BufferedReader(freader);
					FileOutputStream fout = new FileOutputStream(outfilepath+"data/"+outputnodename+".data");
					FileOutputStream foutCidx = new FileOutputStream(outfilepath+"index/"+outputnodename+".cidx");
					FileOutputStream foutRidx = new FileOutputStream(outfilepath+"index/"+outputnodename+".ridx");
					FileOutputStream fouterr = new FileOutputStream(outfilepath+"index/"+outputnodename+ERROR_FILE_EXTENTION);

        			for (int i=0; i<=ignoreLinePointer; i++) {
        					fin.readLine();
        			}
        		
        			while( 		 ((line = fin.readLine()) != null) 
        						&& (keeper.keepRunning()) 
        					) {  

            				// inBuffer에 한줄을 집어넣는다.
            				inBuffer = line.getBytes();
            				int inLength = inBuffer.length;
            				line = null;
            				sindex = slength = 0;
            				
            				for (int i=0; i<inLength; i++) {
                				if ((inBuffer[i] == inColumnDelimiter[0]) && equals(inBuffer,i,inColumnDelimiter)) { //구분자 포착
                   						if (columnCount >= numberOfColumns)  {
                   								checkResult = false;
                   								break;
                   						}
                   						columnLocList[columnCount][0] = sindex;
          	   							if (slength == 0) {
          	   									// if column is space then pass
                    							columnLocList[columnCount][1] = 0;
          	   							} else if ( 	 (slength == NULL_VALUE.length) 
          	   											&& (inBuffer[sindex] == NULL_VALUE[0]) 
          	   											&& equals(inBuffer,sindex,NULL_VALUE) 			) {
          	   									// if column equals null value then pass          	   		
                    							columnLocList[columnCount][1] = 0;
                						} else if (typeCheck(inBuffer, sindex, sindex+slength, columnCount, colsInfo)) {
                    							// if valid column, then put column and delimiter to out buffer
                    							columnLocList[columnCount][1] = slength;
                						} else {
                    							checkResult = false;
                    							break;
                						}
                   						columnCount++;          	   
                   						sindex = i+1;
                   						slength = 0;
                				} else {
                    					slength++;
                				}// end of else
            				}// end of for

            				if ((checkResult == true) && ((columnCount+1) == numberOfColumns)) {
                				columnLocList[columnCount][0] = sindex;
        							if (slength == 0) {
                						columnLocList[columnCount][1] = 0;
          						} else if (		 (slength == NULL_VALUE.length) 
          	   									&& (inBuffer[sindex] == NULL_VALUE[0]) 
          	   									&& equals(inBuffer,sindex,NULL_VALUE)  ) {
                    					columnLocList[columnCount][1] = 0;
               					} else if (typeCheck(inBuffer, sindex, sindex+slength, columnCount, colsInfo)) {
                    					columnLocList[columnCount][1] = slength;
                				} else {  // mal formed rows
                   						checkResult = false;
                    					errLineCount++;
                    					fouterr.write((int)lineCount);                    					fouterr.write(ERR_DEL);
            	    						fouterr.write(inBuffer);            	    						fouterr.write(CRLF);
                				}
                
                				if (checkResult) {
                    					// make new line to fit format and print to file
                   						outBuffer = makeOutputFormatLine(inBuffer,columnLocList, colsInfo.getColumnInfoList());
                    					fout.write(outBuffer);
										//------------------------
										//------------------------
                    					foutCidx.write(makeOutputColumnIndex(columnLocList));
                    					rowIndexBuffer = makeOutputRowIndex(rowIndexBuffer, rowIndexBufferIndex, rowStartPosition);
                    					rowIndexBufferIndex += rowUnit;
                    					rowStartPosition += outBuffer.length;
                    					lineCount++;
                				}// end of if
                	
            				} else { // mal formed rows
                				fouterr.write((int)lineCount);                				fouterr.write(ERR_DEL);
            						fouterr.write(inBuffer);            		fouterr.write(CRLF);
            				}// end of else
           				
           				checkResult = true;
            				columnCount = 0;
            				if ( ((lineCount+errLineCount)%lineUnit) == 0 ) {
            						keeper.increaseRate(lineCount+errLineCount);
            						foutRidx.write(rowIndexBuffer);
            						rowIndexBufferIndex = 0;
            						System.out.print(" [" + prjname + "]" + lineCount + "  ");
            				}
        			}//end of while

        			foutRidx.write(rowIndexBuffer,0,rowIndexBufferIndex);
        			System.out.println(lineCount);
        			fout.close();
        			foutCidx.close();
        			foutRidx.close();
        			fouterr.close();

					createNumberIndex(outfilepath+"index/"+outputnodename+".nidx", lineCount);
        			unifyColumnInfo(colsInfo);
        			colsInfo.saveColumnInfoToMetaFormat(outfilepath+"meta/"+outputnodename);
        			prnEtcMeta(outfilepath, outputnodename, lineCount, colsInfo.getNumberOfColumns(), inColumnDelimiter, nullvalue);
					
					keeper.setState(false);

        			return lineCount;

    		} catch(Exception e){
	      			e.printStackTrace();
        			return 0;
    		}
  	}// end of extract

   private void prnEtcMeta(String outfilepath, String outputnodename, long rowNum, int colNum, byte[] del, byte[] nullValue)
   {
   			System.out.println(" new file name is " + outfilepath+outputnodename + "row # " + rowNum + " col # " + colNum);
   			CXMMetaDataSaver file = new CXMMetaDataSaver();
   			file.setFileStatus(outfilepath+"meta/"+outputnodename);
   			file.setProfile("PREVIOUS_NODE", "XMXNodeFile");
   			file.setProfile("DATA_FILE", outputnodename);
   			file.setProfile("ROW_INDEX", outputnodename);
   			file.setProfile("NUMBER_OF_ROWS", new Long(rowNum).toString());
   			file.setProfile("COLUMN_DELIMITER", new String(del));
   			file.setProfile("NULL_VALUE", new String(nullValue));
   			file.close();
   	}
  	private void createNumberIndex(String filename, long lineCount) throws Exception
  	{
  			int unit=1;
  			int n;
  			long tmpCount = lineCount;
  			byte[] number;
  			FileOutputStream out = new FileOutputStream(filename);

  			while ( tmpCount >= 128 ) {
  					tmpCount = tmpCount >> 7;
  					unit++;
  			}
  			
//			System.out.println("unit size is " + unit);
  			
  			for (int i=0; i<lineCount; i++) {
  					number = new byte[unit];
					n = i+1;
  					for (int j=0; j<unit; j++) {
  							number[j] = (byte)(n&127);
  							n = n >> 7;
  					}
  					out.write(number);
  			}
  		
  			out.close();
  	}// end of createNumberIndex
  	
  	// 새로 생성된 enumeration list를 기존의 filter info와 통합한다.
  	private void unifyColumnInfo(CXMColumnInfoManager colsInfo)
  	{
      		Object keys[] = enumList.keySet().toArray();
      		for (int i=0; i<enumList.size(); i++) {
          		colsInfo.filterInfo.put((String)keys[i], enumList.get((String)keys[i]));
      		}
  	}// end of unifyColumnInfo
/*
  	private byte[] makeOutputFormatLine(byte inBuffer[], int columnLocList[][], ColumnInfoType[] cTypeList)
  	{
      		// calculate outputLine length
      		int outLength = 0;
     		int numberOfColumns = columnLocList.length;
      		for (int i=0; i<numberOfColumns; i++) {
          		if (cTypeList[i].use) {
              					outLength = outLength + columnLocList[i][1];
              	}// end of if
      		}// end of for

      		byte[] outBuffer = new byte[outLength];
      		int outBufferIndex = 0;
      		for (int i=0; i<numberOfColumns; i++) {
        			if (cTypeList[i].use) {
              					for (int j=0; j<columnLocList[i][1]; j++, outBufferIndex++)
                  						outBuffer[outBufferIndex] = inBuffer[columnLocList[i][0]+j];
        			}// end of if
      		}// end of for
      		return outBuffer;
  	}// end of makeOutputFormatLine

*/
  	private byte[] makeOutputFormatLine(byte inBuffer[], int columnLocList[][], ColumnInfoType[] cTypeList)
  	{
      		// calculate outputLine length
      		int outLength = 0;
     		int numberOfColumns = columnLocList.length;
      		String date[] = new String[dateCount];
      		long d = 0;
      		int dcount = 0;

			//=======================
			int tmpsize;
			boolean datechecked = false;
			//=======================
      		for (int i=0; i<numberOfColumns; i++) {
          		if (cTypeList[i].use) {
              			if (cTypeList[i].type.equals("DATE")) {
              				if ( columnLocList[i][1]>0) {
              					if ( (columnLocList[i][1] == NULL_VALUE.length) 
              							&& (inBuffer[columnLocList[i][0]] == NULL_VALUE[0]) 
              							&& equals(inBuffer,columnLocList[i][0],NULL_VALUE) ) {
 										date[dcount++] = null;
 									} else {             				
              						try {
              							d = (dFormatter.parse(new String(inBuffer, columnLocList[i][0], columnLocList[i][1])).getTime()) / 1000;
              							date[dcount] = Long.toString(d);
										//=================================
										if (datechecked == false)
										{
										  tmpsize = columnLocList[i][1];
										  columnLocList[i][1] = date[dcount].length();
                                          for (int j=i+1; j<numberOfColumns; j++) columnLocList[i][0] = columnLocList[i][0] - tmpsize - date[dcount].length();
										  datechecked = true;
										}
										//=================================
              							outLength += date[dcount++].length();
              						} catch (ParseException e) {}
              					}// end of else
              				} else {
              					date[dcount++] = null;
              				}
              			} else { 
              					outLength = outLength + columnLocList[i][1];
              			}
              	}// end of if
      		}// end of for

			dcount = 0;
      		byte[] outBuffer = new byte[outLength];
      		int outBufferIndex = 0;
      		for (int i=0; i<numberOfColumns; i++) {
        			if (cTypeList[i].use) {
        					if (cTypeList[i].type.equals("DATE")) {
        							if (date[dcount] != null) {
        									for (int j=0; j<date[dcount].length(); j++, outBufferIndex++) 
        											outBuffer[outBufferIndex] = (byte)date[dcount].charAt(j);
        							}	
        							dcount ++;
        					} else {
              					for (int j=0; j<columnLocList[i][1]; j++, outBufferIndex++)
                  						outBuffer[outBufferIndex] = inBuffer[columnLocList[i][0]+j];
                  		}
        			}// end of if
      		}// end of for
      		date = null;
      		return outBuffer;
  	}// end of makeOutputFormatLine
  
  	private byte[] makeOutputColumnIndex(int columnLocList[][])
  	{
  			byte[] out = new byte[2*columnLocList.length];
  			int cIndex =0;
  			for (int i=0; i<columnLocList.length; i++) {
  					cIndex += columnLocList[i][1];
					out[i*2] = (byte)(cIndex&127);
					out[i*2+1] = (byte)(cIndex >> 7);
			}
			return out;	
  	}// end of makeOutputFormatLine
  
  	private byte[] makeOutputRowIndex(byte[] buffer, int index, long rowLength) 
  	{
  			for (int i=0; i<rowUnit; i++) {
  					buffer[index+i] = (byte)(rowLength&127);
  					rowLength = rowLength >> 7;
  			}
  			return buffer;
  	}// end of makeOutputRowIndex

  	private boolean typeCheck(byte buffer[], int sindex, int findex, int columnCount, CXMColumnInfoManager colsInfo)
  	{
      		if (colsInfo.colsInfoList[columnCount].use) {
      
          		if (colsInfo.colsInfoList[columnCount].type.equals("INTEGER"))
            				return checkInteger(buffer,sindex,findex);
          		if (colsInfo.colsInfoList[columnCount].type.equals("FLOAT"))
            				return checkFloat(buffer,sindex,findex);
          		if (colsInfo.colsInfoList[columnCount].type.equals("DATE"))
            				return checkDate(buffer,sindex,findex);
          		if (colsInfo.colsInfoList[columnCount].type.equals("ENUMERATION")) {
      	    				TreeSet enum = (TreeSet)colsInfo.getFilterInfo().get(colsInfo.colsInfoList[columnCount].name);
            				// 기존 enumeration list에 포함되어 있는지 확인한다.
            				if (enum != null) return enum.contains(new String(buffer, sindex,findex-sindex));
            				else {
                  				// 새로운 enumeration list에 추가한다.
                  				Object obj = enumList.get(colsInfo.colsInfoList[columnCount].name);
                  				if (obj instanceof TreeSet) {
              							TreeSet newEnum = (TreeSet)enumList.get(colsInfo.colsInfoList[columnCount].name);
                						if (newEnum.size() < MAX_ENUM_VALUES) {
                								newEnum.add(new String(buffer, sindex,findex-sindex));
                								return true;
                						} else {
//                  								System.out.println("put key is " + colsInfo.colsInfoList[columnCount].name);
                  								enumList.put(colsInfo.colsInfoList[columnCount].name, new String("filename"));
                  								// makeTreeSetToFile(filename, newEnum);
                  								return true;
                  						}
                  				} else if (obj instanceof String) {
                  						// putFile();
                  						return true;
                  				}
            				}// end of else
          		}// end of if 
          		return true;
      		}// end of if
      		return true;
  	}// end of typeCheck

  	/*  ASCII Code
   	 *  48 : 0,  57 : 9,  45 : -,  43 : +,  46 : .
   	 */
  	private boolean checkInteger(byte buffer[], int sindex, int findex)
   {
      		// 첫 글자를 체크한다. (+, -가 올수 있다.)
      		byte b = buffer[sindex];
      		if ((b < 48) || (b > 57)) {
        			if (b != 45)
          				if (b != 43) return false;
      		}
      		for (int i=sindex+1; i<findex; i++) {
              	if ((buffer[i] < 48) || (buffer[i] > 57)) return false;
      		}// end of for
      		return true;
  	}// end of checkInteger
  	private boolean checkFloat(byte buffer[], int sindex, int findex)
  	{
      		boolean dotFound = false;
      		// 첫 글자를 체크한다. (+, -가 올수 있다.)
      		byte b = buffer[sindex];
      		if ((b < 48) || (b > 57)) {
        			if (b != 45)
        					if (b != 43) return false;
      		}
      		for (int i=sindex+1; i<findex; i++) {
           		if ((buffer[i] < 48) || (buffer[i] > 57))  {
              			if ((dotFound == false) && (buffer[i] == 46)) 		dotFound = true;
              			else return false;
           		}
      		}// end of for
      		return true;
  	}
  	private boolean checkDate(byte buffer[], int sindex, int findex)
  	{
   			try {
    		 		dFormatter.parse(new String(buffer, sindex, findex-sindex));
    				return true;
    		} catch (ParseException e) {
    				return false;
    		}
  	}

  	private boolean checkString(byte buffer[], int sindex, int findex, int columnCount)
  	{
    		return true;
  	}

    private int str2Int(String str) throws NumberFormatException
    {
       		Integer i = new Integer(str);
       		return i.intValue();
    }
    private float str2Float(String str) throws NumberFormatException 
    {
       	Float f = new Float(str);
       	return f.floatValue();
    }
  	private String int2Str(int i) throws NumberFormatException
  	{
      		Integer ii = new Integer(i);
     		 return ii.toString();
  	}

  	private boolean equals(byte inBuffer[], int bufferIndex, byte delimiter[])
  	{
      	if (delimiter.length == 1) return true;
      	for (int i=1; i<delimiter.length; i++) 
          	if (inBuffer[bufferIndex+i] != delimiter[i]) return false;     			
      return true;
  	}// end of equals

}// end of class