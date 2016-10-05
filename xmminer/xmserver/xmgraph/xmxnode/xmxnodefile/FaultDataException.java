package xmminer.xmserver.xmgraph.xmxnode.xmxnodefile;


public final class FaultDataException extends org.omg.CORBA.UserException {
  public java.lang.String message;

  public FaultDataException () {
    super();
  }


  public FaultDataException (java.lang.String message) {
    this();
    this.message = message;
  }


  public FaultDataException (java.lang.String _reason, java.lang.String message) {
    super(_reason);
    this.message = message;
  }


  public java.lang.String toString () {
    final java.lang.StringBuffer _ret = new java.lang.StringBuffer("exception xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.FaultDataException {");
    _ret.append("\n");
    _ret.append("java.lang.String message=");
    _ret.append(message != null?'\"' + message + '\"':null);
    _ret.append("\n");
    _ret.append("}");
    return _ret.toString();
  }

  public boolean equals (java.lang.Object o) {
    if (this == o) return true;
    if (o == null) return false;
    if (o instanceof xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.FaultDataException) {
      final xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.FaultDataException obj = (xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.FaultDataException)o;
      boolean res = true;
      do {
        res = this.message == obj.message ||
         (this.message != null && obj.message != null && this.message.equals(obj.message));
      } while (false);
      return res;
    }
    else {
      return false;
    }
  }
}
