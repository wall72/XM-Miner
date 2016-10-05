package xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.filex;

public class Keeper
{
	boolean state;
	long numberOfAllLines = 1;
	long precessedLines = 0;
	public Keeper () {
		state = true;
	}
	
	public void setState(boolean b) {
		state = b;
	}
	
	public boolean keepRunning() {
		return state;
	}
	
	public void initCalculateRate(long n) {
		numberOfAllLines = n;
	}
	
	public void increaseRate(long lines) {
		precessedLines = lines;
	}
	
	public int getRate() {
		if (!state) return 100;
		double p = (double)precessedLines;
		double n = (double)numberOfAllLines;
		return (int)(p/n*100);
	}		
} 