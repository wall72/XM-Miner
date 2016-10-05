package xmminer.xmserver.xmgraph.xmxnode.xmxnodefile;


public final class FilePath implements org.omg.CORBA.portable.IDLEntity {
  public java.lang.String[] path;
  public java.lang.String filename;


  public FilePath () {
  }


  public FilePath (final java.lang.String[] path, 
                   final java.lang.String filename) {
    this.path = path;
    this.filename = filename;
  }


  private transient java.util.Hashtable _printMap = null;
  public java.lang.String toString() {
    final java.lang.StringBuffer _ret = new java.lang.StringBuffer("struct xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.FilePath {");
    final java.lang.Thread _currentThread = java.lang.Thread.currentThread();
    if (_printMap == null) {
      _printMap = new java.util.Hashtable();
    } else {
      if (_printMap.get(_currentThread) != null) {
        _ret.append("...}");
        return _ret.toString();
      }
    }
    _printMap.put(_currentThread, this);
    _ret.append("\n");
    _ret.append("java.lang.String[] path=");
    _ret.append("{");
    if (path == null) {
      _ret.append(path);
    } else {
      for (int $counter24 = 0; $counter24 < path.length; $counter24++) {
        _ret.append(path[$counter24] != null?'\"' + path[$counter24] + '\"':null);
        if ($counter24 < path.length - 1) {
          _ret.append(",");
        }
      }
    }
    _ret.append("}");
    _ret.append(",\n");
    _ret.append("java.lang.String filename=");
    _ret.append(filename != null?'\"' + filename + '\"':null);
    _ret.append("\n");
    _printMap.remove(_currentThread);
    _ret.append("}");
    return _ret.toString();
  }

  private transient java.util.Hashtable _cmpMap = null;
  public boolean equals (java.lang.Object o) {
    if (this == o) return true;
    if (o == null) return false;

    final java.lang.Thread _currentThread = java.lang.Thread.currentThread();
    if (_cmpMap == null) {
      _cmpMap = new java.util.Hashtable();
    } else {
      final java.lang.Object _cmpObj;
      _cmpObj= _cmpMap.get(_currentThread);
      if (_cmpObj != null) return o == _cmpObj;
    }
    if (o instanceof xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.FilePath) {
      _cmpMap.put(_currentThread, o);
      final xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.FilePath obj = (xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.FilePath)o;
      boolean res = true;
      do {
          if (res = (this.path.length == obj.path.length)) {
            for (int $counter25 = 0; res && $counter25 < this.path.length; $counter25++) {
              res = this.path[$counter25] == obj.path[$counter25] ||
               (this.path[$counter25] != null && obj.path[$counter25] != null && this.path[$counter25].equals(obj.path[$counter25]));
            }
          }
        if (!res) break;
        res = this.filename == obj.filename ||
         (this.filename != null && obj.filename != null && this.filename.equals(obj.filename));
      } while (false);
      _cmpMap.remove(_currentThread);
      return res;
    }
    else {
      return false;
    }
  }
}
