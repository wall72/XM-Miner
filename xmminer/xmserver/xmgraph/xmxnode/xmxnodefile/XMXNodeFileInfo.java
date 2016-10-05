package xmminer.xmserver.xmgraph.xmxnode.xmxnodefile;


public final class XMXNodeFileInfo implements org.omg.CORBA.portable.IDLEntity {
  public xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.FilePath srcdata;
  public int columnNameLinePointer;
  public int ignoreLinePointer;
  public xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.FilePath hdrdata;
  public java.lang.String delimiter;
  public java.lang.String datePattern;
  public int numberOfColumns;
  public xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.ColumnInfoType[] columnInfo;
  public xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.EnumerationType[] enumList;
  public java.lang.String nullvalue;


  public XMXNodeFileInfo () {
  }


  public XMXNodeFileInfo (final xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.FilePath srcdata, 
                          final int columnNameLinePointer, 
                          final int ignoreLinePointer, 
                          final xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.FilePath hdrdata, 
                          final java.lang.String delimiter, 
                          final java.lang.String datePattern, 
                          final int numberOfColumns, 
                          final xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.ColumnInfoType[] columnInfo, 
                          final xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.EnumerationType[] enumList, 
                          final java.lang.String nullvalue) {
    this.srcdata = srcdata;
    this.columnNameLinePointer = columnNameLinePointer;
    this.ignoreLinePointer = ignoreLinePointer;
    this.hdrdata = hdrdata;
    this.delimiter = delimiter;
    this.datePattern = datePattern;
    this.numberOfColumns = numberOfColumns;
    this.columnInfo = columnInfo;
    this.enumList = enumList;
    this.nullvalue = nullvalue;
  }


  private transient java.util.Hashtable _printMap = null;
  public java.lang.String toString() {
    final java.lang.StringBuffer _ret = new java.lang.StringBuffer("struct xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.XMXNodeFileInfo {");
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
    _ret.append("xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.FilePath srcdata=");
    _ret.append(srcdata);
    _ret.append(",\n");
    _ret.append("int columnNameLinePointer=");
    _ret.append(columnNameLinePointer);
    _ret.append(",\n");
    _ret.append("int ignoreLinePointer=");
    _ret.append(ignoreLinePointer);
    _ret.append(",\n");
    _ret.append("xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.FilePath hdrdata=");
    _ret.append(hdrdata);
    _ret.append(",\n");
    _ret.append("java.lang.String delimiter=");
    _ret.append(delimiter != null?'\"' + delimiter + '\"':null);
    _ret.append(",\n");
    _ret.append("java.lang.String datePattern=");
    _ret.append(datePattern != null?'\"' + datePattern + '\"':null);
    _ret.append(",\n");
    _ret.append("int numberOfColumns=");
    _ret.append(numberOfColumns);
    _ret.append(",\n");
    _ret.append("xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.ColumnInfoType[] columnInfo=");
    _ret.append("{");
    if (columnInfo == null) {
      _ret.append(columnInfo);
    } else {
      for (int $counter42 = 0; $counter42 < columnInfo.length; $counter42++) {
        _ret.append(columnInfo[$counter42]);
        if ($counter42 < columnInfo.length - 1) {
          _ret.append(",");
        }
      }
    }
    _ret.append("}");
    _ret.append(",\n");
    _ret.append("xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.EnumerationType[] enumList=");
    _ret.append("{");
    if (enumList == null) {
      _ret.append(enumList);
    } else {
      for (int $counter43 = 0; $counter43 < enumList.length; $counter43++) {
        _ret.append(enumList[$counter43]);
        if ($counter43 < enumList.length - 1) {
          _ret.append(",");
        }
      }
    }
    _ret.append("}");
    _ret.append(",\n");
    _ret.append("java.lang.String nullvalue=");
    _ret.append(nullvalue != null?'\"' + nullvalue + '\"':null);
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
    if (o instanceof xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.XMXNodeFileInfo) {
      _cmpMap.put(_currentThread, o);
      final xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.XMXNodeFileInfo obj = (xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.XMXNodeFileInfo)o;
      boolean res = true;
      do {
        res = this.srcdata == obj.srcdata ||
         (this.srcdata != null && obj.srcdata != null && this.srcdata.equals(obj.srcdata));
        if (!res) break;
        res = this.columnNameLinePointer == obj.columnNameLinePointer;
        if (!res) break;
        res = this.ignoreLinePointer == obj.ignoreLinePointer;
        if (!res) break;
        res = this.hdrdata == obj.hdrdata ||
         (this.hdrdata != null && obj.hdrdata != null && this.hdrdata.equals(obj.hdrdata));
        if (!res) break;
        res = this.delimiter == obj.delimiter ||
         (this.delimiter != null && obj.delimiter != null && this.delimiter.equals(obj.delimiter));
        if (!res) break;
        res = this.datePattern == obj.datePattern ||
         (this.datePattern != null && obj.datePattern != null && this.datePattern.equals(obj.datePattern));
        if (!res) break;
        res = this.numberOfColumns == obj.numberOfColumns;
        if (!res) break;
          if (res = (this.columnInfo.length == obj.columnInfo.length)) {
            for (int $counter44 = 0; res && $counter44 < this.columnInfo.length; $counter44++) {
              res = this.columnInfo[$counter44] == obj.columnInfo[$counter44] ||
               (this.columnInfo[$counter44] != null && obj.columnInfo[$counter44] != null && this.columnInfo[$counter44].equals(obj.columnInfo[$counter44]));
            }
          }
        if (!res) break;
          if (res = (this.enumList.length == obj.enumList.length)) {
            for (int $counter45 = 0; res && $counter45 < this.enumList.length; $counter45++) {
              res = this.enumList[$counter45] == obj.enumList[$counter45] ||
               (this.enumList[$counter45] != null && obj.enumList[$counter45] != null && this.enumList[$counter45].equals(obj.enumList[$counter45]));
            }
          }
        if (!res) break;
        res = this.nullvalue == obj.nullvalue ||
         (this.nullvalue != null && obj.nullvalue != null && this.nullvalue.equals(obj.nullvalue));
      } while (false);
      _cmpMap.remove(_currentThread);
      return res;
    }
    else {
      return false;
    }
  }
}
