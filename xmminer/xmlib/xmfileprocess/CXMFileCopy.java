package xmminer.xmlib.xmfileprocess;

import java.io.*;

public class CXMFileCopy
{
    private String s_file;
    private String n_file;
    public CXMFileCopy()
    {
    }
    
    public void fileCopy(String source_file, String new_file)
    {
        s_file = source_file;       
        n_file = new_file;        
        try
        {
            FileInputStream fis = new FileInputStream(s_file);
            FileOutputStream fos = new FileOutputStream(n_file);
            byte[] buf = new byte[1024];
            int i = 0;
            while((i=fis.read(buf))!=-1)
            {
               fos.write(buf,0,i);
            }
            fis.close();
            fos.close();
        }  catch (Exception fce)
           {
               System.out.println("file_copy_error="+fce.getMessage());
           }
    }    
}