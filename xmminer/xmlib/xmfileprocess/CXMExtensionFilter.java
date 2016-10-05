package xmminer.xmlib.xmfileprocess;

import java.io.*;
import java.io.FileFilter;

public class CXMExtensionFilter implements FilenameFilter
{
  private String extension;

  public CXMExtensionFilter( String extension )
  {
    this.extension = extension;
  }
  public boolean accept(File dir, String name)
  {
    return (name.endsWith(extension));
  }
}
