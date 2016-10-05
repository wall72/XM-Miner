package xmminer.xmclient.xmgraph.xmxnode.xmxnodefile.xmscrutil;

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

      if (!findex.hasNextIndex()) {
          try {
              long pos = findex.getLastIndex();
              System.out.println("next pos : " + pos);
              datafile.seek(pos);
		          for (int i=0; i<numberOfLines; i++)   lines[i] = datafile.readLine();
              findex.addNewIndex(datafile.getFilePointer());
          } catch (IOException e) {}
      } else {
          try {
              long pos = findex.getNextIndex();
              System.out.println("next pos : " + pos);
              datafile.seek(pos);
		          for (int i=0; i<numberOfLines; i++)   lines[i] = datafile.readLine();
          } catch (IOException e) {}
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
