
/**
 * Title:        XM-Miner<p>
 * Description:  XM-Miner<p>
 * Copyright:    Copyright (c) 1999<p>
 * Company:      DIS<p>
 * @author Seunghee Lee
 * @version
 */
package xmminer.xmclient.xmgraph.xmdnode.xmdnodepartitioning;

import java.util.Vector;

public class XMDNodeTextFieldParsing
{
  private Vector result_v;

  public String[][] getPartitionNumberArray(String text_value)
  {
      textFieldParsing(text_value);
      int array_size = result_v.size();
      String[][] number_array = new String[array_size][2];
      String[] index_array = new String[2];
      for (int i = 0; i<array_size; i++)
      {
        index_array = (String[]) result_v.elementAt(i);
        number_array[i][0] = index_array[0];
        number_array[i][1] = index_array[1];
      }
      return number_array;
  }

  public int textFieldParsing(String text_value)
  {
      Vector temp_v = new Vector();
      Vector number_v = new Vector();
      Integer temp_value;
      boolean digit_check = false;
      boolean new_check = true;
      int init_number = 0;
      int end_number = 0;
      int cal_number = 0;
      int result_number = 0;

      for (int i = 0; i<text_value.length(); i++)
      {
        Character t_ch = new Character(text_value.charAt(i));

        switch (t_ch.charValue()){
          case ',':
            digit_check = false;
            new_check = true;
            init_number = Integer.parseInt(String.valueOf(temp_v.elementAt(0)));
            end_number = Integer.parseInt(String.valueOf(temp_v.elementAt(1)));
            cal_number = end_number-init_number+1;
            result_number = result_number + cal_number;
            String[] index_array_1 = new String[2];
            index_array_1[0] = String.valueOf(init_number);
            index_array_1[1] = String.valueOf(end_number);
            number_v.addElement(index_array_1);
            temp_v.clear();
            break;
          case '-':            
            digit_check = false;
            new_check = false;
            break;
          case ' ':
            break;
          default:            
            if (!digit_check)
            {
              if (new_check)
              {
                temp_value = new Integer(Character.digit((t_ch.charValue()),10));
                temp_v.addElement(temp_value);
              }
              else
              {
                temp_value = new Integer(Character.digit((t_ch.charValue()),10));
                temp_v.addElement(temp_value);
              }
            }
            else
            {
              if (new_check)
              {
                temp_value = new Integer((((Integer)(temp_v.elementAt(0))).intValue() *10+Character.digit((t_ch.charValue()),10)));
                temp_v.setElementAt(temp_value,0);
              }
              else
              {
                temp_value = new Integer((((Integer)(temp_v.elementAt(1))).intValue() *10+Character.digit((t_ch.charValue()),10)));
                temp_v.setElementAt(temp_value,1);
              }
            }
            digit_check = true;
        }
      }
      init_number = Integer.parseInt(String.valueOf(temp_v.elementAt(0)));
      end_number = Integer.parseInt(String.valueOf(temp_v.elementAt(1)));
      cal_number = end_number-init_number+1;
      result_number = result_number + cal_number;
      String[] index_array_2 = new String[2];
      index_array_2[0] = String.valueOf(init_number);
      index_array_2[1] = String.valueOf(end_number);
      number_v.addElement(index_array_2);
      result_v = number_v;
      return result_number;
  }
}