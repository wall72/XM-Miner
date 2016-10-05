package xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.xmfileio;
import java.io.*;

public class CXMDataFileReader
{
	private String filename = null;
	private byte[] delimiter = null;
	private int numberOfColumns = -1;
	BufferedReader in = null;
	private int ignoreLinePointer = -1;
	
	public CXMDataFileReader(String fname, String d)
	{
		filename = fname;
		delimiter = d.getBytes();
	}
	
	public void setIgnoreLinePointer(int i) { ignoreLinePointer = i; }
	public int getIgnoreLinePointer() { return ignoreLinePointer; }
	
	public int calNumberOfColumns()
	{	
	    try {	
					int columnCount = 1;
					FileReader freader = new FileReader(filename);
        	BufferedReader fin = new  BufferedReader(freader);
        	
        	if (ignoreLinePointer >= 0) 
        			for (int i=0; i<= ignoreLinePointer; i++) fin.readLine();
       	
        	byte[] line = fin.readLine().getBytes();
        	for (int i=0; i<line.length; i++) {
        			if ( (line[i] == delimiter[0]) && (equals(line,i)) ) {
        					columnCount++;
        			}
        	}
        	numberOfColumns = columnCount;
        	fin.close();
        	return columnCount;
      } catch (IOException e) {
         	return -1;
      }
        	
	}
	
	public void setNumberOfColumns(int numOfCols)
	{
			numberOfColumns = numOfCols;
	}
	
	public String[][] readRows(int numOfRows) throws IOException
	{
	   	String[][] rows = new String[numOfRows][numberOfColumns];
	   	int rowCount = 0;
	   	int columnCount = 0;
	   	String line = null;
	   	byte[] inBuffer = null;
	   	int sindex, slength;
	
	   	if (in == null) {
 	   			FileReader freader = new FileReader(filename);
          in = new  BufferedReader(freader);
	   	}
	   	
       if (ignoreLinePointer >= 0) 
        			for (int i=0; i<= ignoreLinePointer; i++) in.readLine();	   	
	   
		while ( ((line = in.readLine()) != null) && (rowCount <numOfRows) ) {
	     		inBuffer = line.getBytes();
	     		columnCount = calculateColumnCount(inBuffer);
	     		
	     		if (columnCount == numberOfColumns) {	
             		sindex = slength = 0;
             		columnCount = 0;
             		for (int i=0; i<inBuffer.length; i++) {
                  			if ((inBuffer[i] == delimiter[0]) && equals(inBuffer,i)) {                   
          	   						if (slength == 0) {
          	   								rows[rowCount][columnCount] = new String("");
                					} else {
                							rows[rowCount][columnCount] = line.substring(sindex,sindex+slength);
                					}
                  					columnCount++;          	   
                  					sindex = i+1;
                  					slength = 0;
                 			} else {
                    				slength++;
                  			}
              		}// end of for
     					rows[rowCount][columnCount] = line.substring(sindex,sindex+slength);				
						rowCount++;
	     		}// end of if
	   	}// end of while
	   
	   	for (; rowCount<numOfRows; rowCount++) {
	   			for (int i=0; i<numberOfColumns; i++) {
	   					rows[rowCount][i] = new String("");
	   			}
	   	}
	   	return rows;
	}// end of readRows
	
	public void close() throws IOException 
	{
		if (in != null) in.close();
	}
  	
  private int calculateColumnCount(byte[] inBuffer)
  {
  		int count = 1;
  		for (int i=0; i<inBuffer.length; i++) {
  			if ( (inBuffer[i] == delimiter[0]) && equals(inBuffer, i) )
  				count++;
  		}	
  		return count;
  }
  	
  private boolean equals(byte inBuffer[], int bufferIndex)
  {
      		if (delimiter.length == 1) return true;
      		for (int i=1; i<delimiter.length; i++) {
          		if (inBuffer[bufferIndex+i] != delimiter[i]) return false;
      		}
      		return true;
  }

}