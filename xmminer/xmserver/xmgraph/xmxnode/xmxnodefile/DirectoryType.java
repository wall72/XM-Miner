package xmminer.xmserver.xmgraph.xmxnode.xmxnodefile;


public final class DirectoryType implements org.omg.CORBA.portable.IDLEntity {
  public java.lang.String nodeName;
  public java.lang.String nodeType;
  public java.lang.String nodeSize;
  public java.lang.String nodeDate;
  public java.lang.String[] childnodeNames;


  public DirectoryType () {
  }


  public DirectoryType (final java.lang.String nodeName, 
                        final java.lang.String nodeType, 
                        final java.lang.String nodeSize, 
                        final java.lang.String nodeDate, 
                        final java.lang.String[] childnodeNames) {
    this.nodeName = nodeName;
    this.nodeType = nodeType;
    this.nodeSize = nodeSize;
    this.nodeDate = nodeDate;
    this.childnodeNames = childnodeNames;
  }


  private transient java.util.Hashtable _printMap = null;
  public java.lang.String toString() {
    final java.lang.StringBuffer _ret = new java.lang.StringBuffer("struct xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.DirectoryType {");
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
    _ret.append("java.lang.String nodeName=");
    _ret.append(nodeName != null?'\"' + nodeName + '\"':null);
    _ret.append(",\n");
    _ret.append("java.lang.String nodeType=");
    _ret.append(nodeType != null?'\"' + nodeType + '\"':null);
    _ret.append(",\n");
    _ret.append("java.lang.String nodeSize=");
    _ret.append(nodeSize != null?'\"' + nodeSize + '\"':null);
    _ret.append(",\n");
    _ret.append("java.lang.String nodeDate=");
    _ret.append(nodeDate != null?'\"' + nodeDate + '\"':null);
    _ret.append(",\n");
    _ret.append("java.lang.String[] childnodeNames=");
    _ret.append("{");
    if (childnodeNames == null) {
      _ret.append(childnodeNames);
    } else {
      for (int $counter34 = 0; $counter34 < childnodeNames.length; $counter34++) {
        _ret.append(childnodeNames[$counter34] != null?'\"' + childnodeNames[$counter34] + '\"':null);
        if ($counter34 < childnodeNames.length - 1) {
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
    if (o instanceof xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.DirectoryType) {
      _cmpMap.put(_currentThread, o);
      final xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.DirectoryType obj = (xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.DirectoryType)o;
      boolean res = true;
      do {
        res = this.nodeName == obj.nodeName ||
         (this.nodeName != null && obj.nodeName != null && this.nodeName.equals(obj.nodeName));
        if (!res) break;
        res = this.nodeType == obj.nodeType ||
         (this.nodeType != null && obj.nodeType != null && this.nodeType.equals(obj.nodeType));
        if (!res) break;
        res = this.nodeSize == obj.nodeSize ||
         (this.nodeSize != null && obj.nodeSize != null && this.nodeSize.equals(obj.nodeSize));
        if (!res) break;
        res = this.nodeDate == obj.nodeDate ||
         (this.nodeDate != null && obj.nodeDate != null && this.nodeDate.equals(obj.nodeDate));
        if (!res) break;
          if (res = (this.childnodeNames.length == obj.childnodeNames.length)) {
            for (int $counter35 = 0; res && $counter35 < this.childnodeNames.length; $counter35++) {
              res = this.childnodeNames[$counter35] == obj.childnodeNames[$counter35] ||
               (this.childnodeNames[$counter35] != null && obj.childnodeNames[$counter35] != null && this.childnodeNames[$counter35].equals(obj.childnodeNames[$counter35]));
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
