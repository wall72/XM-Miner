package xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.xmfileio;
import java.io.*;

public class PointAccessFileReader {

  RandomAccessFile datafile;
	FileIndex findex;
	int numberOfLines;

	public PointAccessFileReader(String datafilename, int numOfLines)
	{
    try {
		  datafile = new RandomAccessFile(datafilename, "r");
      findex = new FileIndex(datafile.getFilePointer());
		  numberOfLines = numOfLines;
    } catch (FileNotFoundException e) {}
    catch (IOException e) {}
	}

	public PointAccessFileReader(String datafilename, int numOfLines, FileIndex fi)
	{
    try {
		  datafile = new RandomAccessFile(datafilename, "r");
		  findex = fi;
		  numberOfLines = numOfLines;

//      datafile.seek(fi.current);
    } catch (FileNotFoundException e) {}
	}

	public String[] readNextLines()
  {
		  String[] lines = new String[numberOfLines];
         int i = 0;

      if (!findex.hasNextIndex()) {
          try {
              long pos = findex.getLastIndex();
//              System.out.println("next pos : " + pos);
              datafile.seek(pos);
		        for (i=0; i<numberOfLines; i++) { 
                  lines[i] = new String(datafile.readLine().getBytes("8859_1"), "KSC5601");
              }
              findex.addNewIndex(datafile.getFilePointer());
          } catch (IOException e) {
          		System.out.println(e.getMessage());
          } catch (NullPointerException e) {
          		String[] tlines = new String[i];
          		for (int j=0; j<i; j++) tlines[j] = lines[j];
          		return tlines;
          } 
      } else {
          try {
              long pos = findex.getNextIndex();
//              System.out.println("next pos : " + pos);
              datafile.seek(pos);
		        for (i=0; i<numberOfLines; i++)
                  lines[i] = new String(datafile.readLine().getBytes("8859_1"), "KSC5601");
          } catch (IOException e) {
          		System.out.println(e.getMessage());
          } catch (NullPointerException e) {
          		String[] tlines = new String[i];
          		for (int j=0; j<i; j++) tlines[j] = lines[j];
          		return tlines;
          } 
      }

      return lines;
	}
  public String[] readPreviousLines()
  {
		  String[] lines = new String[numberOfLines];


          try {
              long pos = findex.getPreviousIndex();
              System.out.println("prev pos : " + pos);
              datafile.seek(pos);
		          for (int i=0; i<numberOfLines; i++)   lines[i] = datafile.readLine();
          } catch (IOException e) {}


      return lines;
  }

  public FileIndex close()
  {
    try {
      datafile.close();
    } catch (IOException e) {}
    return findex;
  }
}
