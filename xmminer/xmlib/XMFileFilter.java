package xmminer.xmlib;

import java.io.*;
import javax.swing.filechooser.*;
import javax.swing.*;

public class XMFileFilter extends javax.swing.filechooser.FileFilter {
  String extension;
  String description;

  public XMFileFilter(String extension, String description){
    if(extension.indexOf('.')==-1){
	  extension = "." + extension;
    }
	this.extension = extension;
	this.description = description;
  }

  public boolean accept(File f){
    if(f.getName().endsWith(extension)){
	  return true;
    }else if(f.isDirectory()){
	  return true;
    }
	return false;
  }

  public String getDescription(){
    return this.description + "(*" + extension + ")";
  }
}