package xmminer.xmserver.xmgraph.xmxnode.xmxnodedb;

import java.io.*;

public class CXMDBExtractRunner {

  int rowUnit = 5;

  /* Input Data의 구분자의 길이가 1인 경우 */
  public long extract(String datafilename, String newdatafilename, int numOfCols, byte[] inColumnDelimiter, String path){
  	int lineUnit = 500;
   	long rowStartPosition = 0;
   	String line;
   	byte inBuffer[];
   	byte outBuffer[];
   	byte rowIndexBuffer[] = new byte [rowUnit*lineUnit];
   	int rowIndexBufferIndex = 0;
   	int sindex, slength;  // index for inBuffer ( point column start, column length )
   	int numberOfColumns =numOfCols;
   	int columnLocList[][] = new int[numberOfColumns][2];  //0:시작위치 1:length
   	int columnCount = 0;
   	long lineCount = 0;
   	boolean checkResult = true;

	File dir = new File(path+"meta");
	if (!dir.exists()) dir.mkdir();
	dir = new File(path+"data");
	if (!dir.exists()) dir.mkdir();
	dir = new File(path+"index");
	if (!dir.exists()) dir.mkdir();

	try{
	  FileReader freader = new FileReader(path+"data/"+datafilename);
	  BufferedReader fin = new  BufferedReader(freader);
      FileOutputStream fout = new FileOutputStream(path+"data/"+newdatafilename+".data");
 	  FileOutputStream foutCidx = new FileOutputStream(path+"index/"+newdatafilename+".cidx");
	  FileOutputStream foutRidx = new FileOutputStream(path+"index/"+newdatafilename+".ridx");

      while((line = fin.readLine()) != null) { //한줄을 읽어들인다.

        // inBuffer에 한줄을 집어넣는다.
        inBuffer = line.getBytes();
        int inLength = inBuffer.length;
        line = null;
        sindex = slength = 0;

        for(int i=0; i<inLength; i++) {

          if((inBuffer[i] == inColumnDelimiter[0]) && equals(inBuffer,i,inColumnDelimiter)) { //구분자 포착
			columnLocList[columnCount][0] = sindex;
 		    columnLocList[columnCount][1] = slength;
            columnCount++;
            sindex = i+1;
            slength = 0;
		  }else{
			slength++;
		  }// end of else
		}// end of for

		if((checkResult == true) && ((columnCount+1) == numberOfColumns)) {
		  columnLocList[columnCount][0] = sindex;
          columnLocList[columnCount][1] = slength;

          // make new line to fit format and print to file
          outBuffer = makeOutputFormatLine(inBuffer,columnLocList);
          fout.write(outBuffer);
          foutCidx.write(makeOutputColumnIndex(columnLocList));
          rowIndexBuffer = makeOutputRowIndex(rowIndexBuffer, rowIndexBufferIndex, rowStartPosition);
          rowIndexBufferIndex += rowUnit;
          rowStartPosition += outBuffer.length;
          lineCount++;

        }// end of if

		checkResult = true;
        columnCount = 0;

        if((lineCount%lineUnit) == 0){
		  foutRidx.write(rowIndexBuffer);
          rowIndexBufferIndex = 0;
        }// end of if
      }//end of while
       
 	  foutRidx.write(rowIndexBuffer,0,rowIndexBufferIndex);
      fout.close();
      foutCidx.close();
      foutRidx.close();
      freader.close();

  	  createNumberIndex(path+"index/"+newdatafilename+".nidx", lineCount);

	  return lineCount;

    }catch(Exception e){
	   e.printStackTrace();
       return 0;
    }// end of catch
  }// end of extract

  private void createNumberIndex(String filename, long lineCount) throws Exception{
	int unit=1;
	int n;
  	long tmpCount = lineCount;
  	byte[] number;
  	FileOutputStream out = new FileOutputStream(filename);

  	while(tmpCount >= 128){
  	  tmpCount = tmpCount >> 7;
  	  unit++;
  	}

  	for(int i=0; i<lineCount; i++){
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

  byte[] makeOutputFormatLine(byte inBuffer[], int columnLocList[][]){
    // calculate outputLine length
    int outLength = 0;
    int numberOfColumns = columnLocList.length;
    
	for(int i=0; i<numberOfColumns; i++) {
      outLength = outLength + columnLocList[i][1];
    }

    byte[] outBuffer = new byte[outLength];
    int outBufferIndex = 0;
    
	for(int i=0; i<numberOfColumns; i++){
      for(int j=0; j<columnLocList[i][1]; j++, outBufferIndex++)
        outBuffer[outBufferIndex] = inBuffer[columnLocList[i][0]+j];
    }// end of for
    return outBuffer;
  }

  byte[] makeOutputColumnIndex(int columnLocList[][]){
  	byte[] out = new byte[2*columnLocList.length];
  	int cIndex =0;
  	for(int i=0; i<columnLocList.length; i++){
	  cIndex += columnLocList[i][1];
	  out[i*2] = (byte)(cIndex&127);
	  out[i*2+1] = (byte)(cIndex >> 7);
	}
	return out;
  }

  byte[] makeOutputRowIndex(byte[] buffer, int index, long rowLength){
  	for(int i=0; i<rowUnit; i++){
	  buffer[index+i] = (byte)(rowLength&127);
  	  rowLength = rowLength >> 7;
  	}
  	return buffer;
  }

  int str2Int(String str) throws NumberFormatException{
	Integer i = new Integer(str);
	return i.intValue();
  }

  float str2Float(String str) throws NumberFormatException{
   	Float f = new Float(str);
   	return f.floatValue();
  }

  String int2Str(int i) throws NumberFormatException{
    Integer ii = new Integer(i);
    return ii.toString();
  }

  boolean equals(byte inBuffer[], int bufferIndex, byte delimiter[]){
    if(delimiter.length == 1) return true;
    for(int i=1; i<delimiter.length; i++){
      if (inBuffer[bufferIndex+i] != delimiter[i]) return false;
    }
    return true;
  }

}