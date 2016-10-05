package xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.xmfileio;
import java.util.Vector;
import java.lang.Long;
import java.io.Serializable;

public class FileIndex implements Serializable {

	Vector index = new Vector();
	int current = -1;

  public FileIndex(long numOfBytes)
  {
		Long number = new Long(numOfBytes);
		index.addElement(number);
  }

	public void addNewIndex(long numOfBytes)
	{
		Long number = new Long(numOfBytes);
		index.addElement(number);
		current ++;
	}

	public long getPreviousIndex()
	{
		current --;
    if (current<0) return 0;
		Long number = (Long)index.get(current);
		return number.longValue();
	}
	public long getNextIndex()
	{
		Long number = (Long)index.get(current);
		current ++;
		return number.longValue();
	}
	public boolean hasNextIndex()
	{
    if (current == -1) return false;
		if ((current+2) < index.size()) return true;
		return false;
	}
  public long getLastIndex()
  {
      if (index.size() <= 0) return 0;
      Long number = (Long)index.get(index.size()-1);
      return number.longValue();
  }

}
