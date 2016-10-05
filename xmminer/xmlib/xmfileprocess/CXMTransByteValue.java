
/**
 * Title:        null<p>
 * Description:  null<p>
 * Copyright:    null<p>
 * Company:      <p>
 * @author
 * @version null
 */
package xmminer.xmlib.xmfileprocess;

public class CXMTransByteValue
{

  public CXMTransByteValue()
  {
  }

  public byte[] getLongToByte(int byte_size, long value)
  {
     byte[] byte_array = new byte[byte_size];
     long long_value = value;
     long quot_long;
     long mod_long;
     for (int i=0; i<byte_size; i++)
     {
        quot_long = long_value/128;
        mod_long = long_value%128;
        byte_array[i] = (byte) mod_long;
        long_value = quot_long;
     }
     return byte_array;
  }

  public long getByteToLong(byte[] value)
  {
     long result_value = 0;
     long factor = 1;
     long long_value = 0;
     for (int i=0; i<value.length; i++)
     {
         long_value = (long) value[i];
         result_value = result_value + long_value*factor;
         factor = factor*128;
     }
     return result_value;
  }


}