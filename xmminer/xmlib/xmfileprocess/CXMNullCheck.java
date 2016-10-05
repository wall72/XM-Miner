package xmminer.xmlib.xmfileprocess;

public class CXMNullCheck
{
    
    public CXMNullCheck()
    {
    }
    
    public boolean nullCheck(String i_str)
    {
          boolean out_bool = false;
          if (i_str==null)
          {
               out_bool = true;
          }
          else
          {
               if (i_str.equals(""))
               {
                     out_bool = true;
               }
          }
          return out_bool;
    }     
    
    public boolean nullCheck(String[] i_str)
    {
          if (i_str==null)
          {
              return true;
          }
          else
          {
              if (i_str.length==0)
              {
                   return true;
              }
              else
              {
                  return false;
              }
          }    
    }   
}