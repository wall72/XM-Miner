package xmminer.xmserver.xmgraph.xmxnode.xmxnodefile;


public final class ColumnInfoType implements org.omg.CORBA.portable.IDLEntity {
  public java.lang.String name;
  public java.lang.String type;
  public boolean use;


  public ColumnInfoType () {
  }


  public ColumnInfoType (final java.lang.String name, 
                         final java.lang.String type, 
                         final boolean use) {
    this.name = name;
    this.type = type;
    this.use = use;
  }


  public java.lang.String toString() {
    final java.lang.StringBuffer _ret = new java.lang.StringBuffer("struct xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.ColumnInfoType {");
    _ret.append("\n");
    _ret.append("java.lang.String name=");
    _ret.append(name != null?'\"' + name + '\"':null);
    _ret.append(",\n");
    _ret.append("java.lang.String type=");
    _ret.append(type != null?'\"' + type + '\"':null);
    _ret.append(",\n");
    _ret.append("boolean use=");
    _ret.append(use);
    _ret.append("\n");
    _ret.append("}");
    return _ret.toString();
  }

  public boolean equals (java.lang.Object o) {
    if (this == o) return true;
    if (o == null) return false;

    if (o instanceof xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.ColumnInfoType) {
      final xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.ColumnInfoType obj = (xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.ColumnInfoType)o;
      boolean res = true;
      do {
        res = this.name == obj.name ||
         (this.name != null && obj.name != null && this.name.equals(obj.name));
        if (!res) break;
        res = this.type == obj.type ||
         (this.type != null && obj.type != null && this.type.equals(obj.type));
        if (!res) break;
        res = this.use == obj.use;
      } while (false);
      return res;
    }
    else {
      return false;
    }
  }
}
