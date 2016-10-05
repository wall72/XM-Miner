package xmminer.xmserver.xmgraph.xmxnode.xmxnodefile;


public final class EnumerationType implements org.omg.CORBA.portable.IDLEntity {
  public java.lang.String key;
  public java.lang.String[] values;


  public EnumerationType () {
  }


  public EnumerationType (final java.lang.String key, 
                          final java.lang.String[] values) {
    this.key = key;
    this.values = values;
  }


  private transient java.util.Hashtable _printMap = null;
  public java.lang.String toString() {
    final java.lang.StringBuffer _ret = new java.lang.StringBuffer("struct xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.EnumerationType {");
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
    _ret.append("java.lang.String key=");
    _ret.append(key != null?'\"' + key + '\"':null);
    _ret.append(",\n");
    _ret.append("java.lang.String[] values=");
    _ret.append("{");
    if (values == null) {
      _ret.append(values);
    } else {
      for (int $counter26 = 0; $counter26 < values.length; $counter26++) {
        _ret.append(values[$counter26] != null?'\"' + values[$counter26] + '\"':null);
        if ($counter26 < values.length - 1) {
          _ret.append(",");
        }
      }
    }
    _ret.append("}");
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
    if (o instanceof xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.EnumerationType) {
      _cmpMap.put(_currentThread, o);
      final xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.EnumerationType obj = (xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.EnumerationType)o;
      boolean res = true;
      do {
        res = this.key == obj.key ||
         (this.key != null && obj.key != null && this.key.equals(obj.key));
        if (!res) break;
          if (res = (this.values.length == obj.values.length)) {
            for (int $counter27 = 0; res && $counter27 < this.values.length; $counter27++) {
              res = this.values[$counter27] == obj.values[$counter27] ||
               (this.values[$counter27] != null && obj.values[$counter27] != null && this.values[$counter27].equals(obj.values[$counter27]));
            }
          }
      } while (false);
      _cmpMap.remove(_currentThread);
      return res;
    }
    else {
      return false;
    }
  }
}
