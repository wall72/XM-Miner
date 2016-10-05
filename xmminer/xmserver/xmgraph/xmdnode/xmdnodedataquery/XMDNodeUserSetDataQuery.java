package xmminer.xmserver.xmgraph.xmdnode.xmdnodedataquery;

public class XMDNodeUserSetDataQuery
{
  private String[] valid_value;
  private int[] valid_index;
  private String[] valid_opt;
  private String[] valid_ser;
  private String filename ="";			// file name
  private String item = "";

  private int s_rows=0;
  private int m_rows = 0; //meta file 열수
  private int dm = -1;			// 파일 구분자
  private int comp_col_cnt = 0;
  private int obj_size = 0;
  private int array_size = 0;
  private int file_size = 0;

  public XMDNodeUserSetDataQuery()
  {

  }

  public void setQueryArray(String[] i_arr1, int[] i_arr2, String[] i_arr3, String[] i_arr4)
  { 
    valid_value = i_arr1;    
    valid_index = i_arr2;   
    valid_opt = i_arr3;  
    valid_ser = i_arr4;    
  }

  public boolean queryColumnCheck(String[] i_arr)
  {
    Object[] postFix;  
    array_size = valid_index.length;   
    int temp_index = 0;
    String temp_opt = "";
    String q_value = "";
    String last_opt = "";
    boolean temp_bln = true;
    boolean orFlag = false;
    boolean pre_bln = false;
    boolean[] valid_chk = new boolean[array_size];    
    for (int i=0; i<array_size; i++)
    {
      q_value = valid_value[i];
      int i_arr_index = valid_index[i];
      String i_arr_value = i_arr[i_arr_index];    
      if (!(i_arr_value.equals(" ")))
      {
        postFix = translateInfix(i_arr[i_arr_index],q_value);    
        valid_chk[i] = valueCheck(postFix);
      }
    }
    for (int j=0; j<array_size; j++)
    {
      temp_index = serCheck(j+1);     
      if (temp_index!=-1)
      {
        temp_opt = valid_opt[temp_index];
      }
      if (temp_opt.equals("and"))
      {
        temp_bln = temp_bln&&valid_chk[temp_index];
        last_opt = "and";
      }
      else if (temp_opt.equals("or"))
      {
        temp_bln = temp_bln&&valid_chk[temp_index];
        if (orFlag)
        {
          pre_bln = pre_bln||temp_bln;
        }
        else
        {
          pre_bln = temp_bln;
        }
        temp_bln = true;
        orFlag = true;
        last_opt = "or";
      }
      else if (temp_opt.equals(" "))
      {
        if (last_opt.equals("and"))
        {
          temp_bln = temp_bln&&valid_chk[temp_index];
          if (orFlag)
          {
            pre_bln = pre_bln||temp_bln;
          }
          else
          {
            pre_bln = temp_bln;
          }
        }
        else if (last_opt.equals("or"))
        {
          temp_bln = valid_chk[temp_index];
          pre_bln = pre_bln||temp_bln;
        }
        else
        {
          pre_bln = valid_chk[temp_index];
        }
      }
    }
    return pre_bln;
  }

  private Object[] translateInfix(String i_str1, String i_str2)
  {
    Double tmpValue;
    Double str1_d;
    double p_i = 0;
    int count = 0;
    int str2_size = i_str2.length();
    boolean digitFlag = false;
    boolean colFlag = false;
    boolean pointFlag = true;
    boolean minusFlag = false;
    boolean symFlag = false;
    boolean orFlag = false;
    boolean equalFlag = false;
    boolean charFlag = false;
    boolean numberFlag;
    String f_char =  String.valueOf(i_str2.charAt(0));
    obj_size = Integer.parseInt(f_char);
    Object[] temp_obj = new Object[obj_size];
    try
    {
      str1_d = new Double(i_str1);
      numberFlag = true;
    } catch(Exception de)
      {
        str1_d = new Double(0);
        numberFlag = false;
      }

    for(int i=1; i<str2_size; i++)
    {
	Character ch = new Character(i_str2.charAt(i));
    	switch (ch.charValue())
        {
	    case '|':
              if (!orFlag)
              {
                 temp_obj[count] = ch;
                 orFlag = true;
                 count++;
              }
              else
              {
                 temp_obj[count-1] = String.valueOf(temp_obj[count-1])+ch;
                 orFlag = false;
              }
              colFlag = false;
              digitFlag = false;
              minusFlag = false;
              symFlag = false;
              equalFlag = false;
              break;
	    case '<':
              temp_obj[count] = ch;
              count++;
              symFlag = true;
              colFlag = false;
              digitFlag = false;
              minusFlag = false;
              equalFlag = false;
	      break;
	    case '>':
              temp_obj[count] = ch;
              count++;
              symFlag = true;
              colFlag = false;
              digitFlag = false;
              minusFlag = false;
              equalFlag = false;
	      break;
	    case '=':
              if(!symFlag)
              {
                  temp_obj[count] = ch;
                  count++;
                  equalFlag = true;
              }
              else
              {
                  temp_obj[count-1] = String.valueOf(temp_obj[count-1])+ch;
                  equalFlag = false;
              }
              colFlag = false;
              digitFlag = false;
              minusFlag = false;
              symFlag = false;
              charFlag = false;
	      break;
           case 'C':
             if (!equalFlag)
             {
                if (numberFlag)
                {
                     temp_obj[count] = str1_d;
                }
                else
                {
                    temp_obj[count] = i_str1;
                }
                count++;
                colFlag = true;
             }
             else
             {
                if (!charFlag)
                {
                   temp_obj[count] = ch;
                   count++;
                   charFlag = true;
                }
                else if (charFlag)
                {
                   temp_obj[count-1] = String.valueOf(temp_obj[count-1])+ch;
                }
             }
             digitFlag = false;
             minusFlag = false;
             symFlag = false;
             break;
           case '-':
             minusFlag = true;
             colFlag = false;
             symFlag = false;
             equalFlag = false;
             break;
           case '.':
             pointFlag = true;
             equalFlag = false;
             p_i = 0;
             break;
	   default:
             if (!numberFlag)
             {           
                if (!colFlag)
                {
                   if (charFlag)
                   {
                      temp_obj[count-1] = String.valueOf(temp_obj[count-1])+ch;
                   }
                   else
                   {              
                      temp_obj[count] = ch;
                      count++;
                      charFlag = true;
                   }
                }
             }
             else
             {
                if(!colFlag)
                {
         	   if(!digitFlag)
                   {
                       if (minusFlag)
                       {
             	            tmpValue = new Double(-(Character.digit((ch.charValue()),10)));
                            temp_obj[count] = tmpValue;
                       }
                       else
                       {
             	            tmpValue = new Double(Character.digit((ch.charValue()),10));
                            temp_obj[count] = tmpValue;
                       }
                       pointFlag = false;
          	       count ++;
      	           }
		   else
                   {
                       if (pointFlag)
                       {
                           p_i = p_i + 1;
                           if (minusFlag)
                           {
                                tmpValue = new Double((((Double)temp_obj[count-1]).doubleValue())-(Character.digit((ch.charValue()),10))*(java.lang.Math.pow(0.1,p_i)));
                                temp_obj[count-1] = tmpValue;
                           }
                           else
                           {
                                tmpValue = new Double((((Double)temp_obj[count-1]).doubleValue())+(Character.digit((ch.charValue()),10))*(java.lang.Math.pow(0.1,p_i)));
                                temp_obj[count-1] = tmpValue;
                           }
                       }
                       else
		       {
                           if (minusFlag)
                           {
                                tmpValue = new Double((((Double)temp_obj[count-1]).doubleValue())*10-(Character.digit((ch.charValue()),10)));
                                temp_obj[count-1] = tmpValue;
                           }
                           else
                           {
                                tmpValue = new Double((((Double)temp_obj[count-1]).doubleValue())*10+(Character.digit((ch.charValue()),10)));
                                temp_obj[count-1] = tmpValue;
                           }
                       }
	           }
		   digitFlag = true;
                   symFlag = false;
               }
            }
	}
    }    
    return temp_obj;
  }

  private boolean valueCheck(Object[] i_obj)
  {
    boolean o_bln = false;
    Double d_1;
    Double d_2;
    Double d_3;
    Double d_4;
    double value1=0;
    double value2=0;
    double value3=0;
    double value4=0;
    String sym1 = "";
    String sym2 = "";    

    if (obj_size==3)
    {
      String str1;
      String str2;
      try
      {
        d_1 = new Double(String.valueOf(i_obj[0]));
        d_2 = new Double(String.valueOf(i_obj[2]));
        value1 = d_1.doubleValue();
        sym1 = String.valueOf(i_obj[1]);
        value2 = d_2.doubleValue();        
        o_bln = comparePostFix(value1,value2,sym1);
      } catch(Exception de1)
        {
          str1 = String.valueOf(i_obj[0]);
          str2 = String.valueOf(i_obj[2]);
          o_bln = str1.equals(str2);
        }
    }
    else if (obj_size==5)
    {
      d_1 = new Double(String.valueOf(i_obj[0]));
      d_2 = new Double(String.valueOf(i_obj[2]));
      d_3 = new Double(String.valueOf(i_obj[4]));
      value1 = d_1.doubleValue();
      sym1 = String.valueOf(i_obj[1]);
      value2 = d_2.doubleValue();
      sym2 = String.valueOf(i_obj[3]);
      value3 = d_3.doubleValue();      
      boolean bln1 = comparePostFix(value1,value2,sym1);
      boolean bln2 = comparePostFix(value2,value3,sym2);    
      o_bln = (bln1&&bln2);      
    }
    else if (obj_size==7)
    {
      d_1 = new Double(String.valueOf(i_obj[0]));
      d_2 = new Double(String.valueOf(i_obj[2]));
      d_3 = new Double(String.valueOf(i_obj[4]));
      d_4 = new Double(String.valueOf(i_obj[6]));
      value1 = d_1.doubleValue();
      sym1 = String.valueOf(i_obj[1]);
      value2 = d_2.doubleValue();
      value3 = d_3.doubleValue();
      sym2 = String.valueOf(i_obj[5]);
      value4 = d_4.doubleValue();
      boolean bln1 = comparePostFix(value1,value2,sym1);
      boolean bln2 = comparePostFix(value3,value4,sym2);
      o_bln = ((bln1)||(bln2));
    }
    return o_bln;
  }

  private int serCheck(int i_num)
  {
    int o_num = -1;
    String i_string = String.valueOf(i_num);
    for (int i=0; i<array_size; i++)
    {
      String temp_ser = valid_ser[i];
      if (i_string.equals(temp_ser))
      {
        o_num = i;
      }
    }
    return o_num;
  }

  private boolean comparePostFix(double d1, double d2, String symbol)
  {
    boolean o_bool = false;
    if (symbol.equals("<"))
    {
      o_bool = (d1 < d2);
    }
    else if (symbol.equals("<="))
    {
      o_bool = (d1 <= d2);
    }
    else if (symbol.equals(">"))
    {
      o_bool = (d1 > d2);
    }
    else if (symbol.equals(">="))
    {
      o_bool = (d1 >= d2);
    }
    else if (symbol.equals("="))
    {
      o_bool = (d1==d2);
    }
    return o_bool;
  }



}