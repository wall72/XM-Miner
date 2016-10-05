package xmminer.xmserver.xmgraph.xmxnode.xmxnodedb;

public class CXMCharOrBytePlus {
  String chr = "\002";
  char cr = '\002';
  final byte[] by = chr.getBytes();

  String orgchr = "\n";
  char orgcr = '\n';
  final byte[] orgby = orgchr.getBytes();

  String org2chr = "\r";
  char org2cr = '\r';
  final byte[] org2by = org2chr.getBytes();

  public char[] char2PlusSpace(char[] a, char[] b){
    char[] c = new char[a.length + b.length+1];
    for(int i = 0 ; i < a.length ; i++) c[i] = a[i];
    c[a.length] = ' ';
    for(int i = 0 ; i < b.length ; i++) c[a.length + 1 + i] = b[i];
    return c;
  }

  public byte[] byte2Plus(byte[] a, byte[] b){
    byte[] c = new byte[a.length + b.length];
    for(int i = 0 ; i < a.length ; i++) c[i] = a[i];
    for(int i = 0 ; i < b.length ; i++) c[a.length + i] = b[i];
    return c;
  }

  public char[] char2PlusSpace(char[] a, char b){
    char[] c = new char[a.length + 1];
    for(int i = 0 ; i < a.length ; i++) c[i] = a[i];
    c[a.length] = b;
    return c;
  }

  public char[] char2Plus(char[] a, char b){
    char[] c = new char[a.length + 1];
    for(int i = 0 ; i < a.length ; i++) c[i] = a[i];
    c[a.length] = b;
    return c;
  }

  public byte[] byte2Plus(byte[] a, byte b){
    byte[] c = new byte[a.length + 1];
    for(int i = 0 ; i < a.length ; i++) c[i] = a[i];
    c[a.length] = b;
    return c;
  }

  public char[] char3PlusSpace(char[] a, char[] b, char[] c){
    char[] d = new char[a.length + b.length + c.length + 2];
    for(int i = 0 ; i < a.length ; i++) d[i] = a[i];
    d[a.length] = ' ';
    for(int i = 0 ; i < b.length ; i++) d[a.length + i + 1] = b[i];
    d[a.length+b.length+1] = ' ';
    for(int i = 0 ; i < c.length ; i++) d[a.length + b.length + i + 2] = c[i];
    return d;
  }

  public char[] char3Plus(char[] a, char[] b, char[] c){
    char[] d = new char[a.length + b.length + c.length];
    for(int i = 0 ; i < a.length ; i++) d[i] = a[i];
    for(int i = 0 ; i < b.length ; i++) d[a.length + i] = b[i];
    for(int i = 0 ; i < c.length ; i++) d[a.length + b.length + i] = c[i];
    return d;
  }

  public byte[] byte3Plus(byte[] a, byte[] b, byte[] c){
    byte[] d = new byte[a.length + b.length + c.length];
    for(int i = 0 ; i < a.length ; i++) d[i] = a[i];
    for(int i = 0 ; i < b.length ; i++) {
      if(b[i] == orgby[0] || b[i] == org2by[0] ) {
        b[i] = by[0];
        if ((i-1) >= 0) d[a.length + i - 1] = by[0];
      }
      d[a.length + i] = b[i];
    }
    for(int i = 0 ; i < c.length ; i++) d[a.length + b.length + i] = c[i];
    return d;
  }

}