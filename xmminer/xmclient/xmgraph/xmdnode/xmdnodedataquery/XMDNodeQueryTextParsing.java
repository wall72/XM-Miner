package xmminer.xmclient.xmgraph.xmdnode.xmdnodedataquery;

public class XMDNodeQueryTextParsing
{
    private String symbol1;
    private String symbol2;
    private String symbol3;
    private String symbol4;
    private String selected_column_name;
    private String temp_value;
    private String parse_value;
    private String new_value;
    private String[] area_value;
    private String[] area_opt;
    private String[] area_ser;
    private String[] area_parse;
    private String[] valid_value;
    private String[] valid_parse;
    private String[] valid_opt;
    private String[] valid_ser;
    private int opt_cnt;
    private int column_cnt;
    private int selected_index;
    private int[] area_index;
    private int[] valid_index;
    private boolean or_check;
    private boolean  and_check;

    public XMDNodeQueryTextParsing()
    {

    }

    public void setInitStatus(int table_column_cnt)
    {
        column_cnt = table_column_cnt;
        setArrayInitStatus();
        opt_cnt = 0;
        new_value = "";
        or_check = false;
        and_check = false;
    }

    private void setArrayInitStatus()
    {
        area_value = new String[column_cnt];
        area_parse = new String[column_cnt];
        area_opt = new String[column_cnt];
        area_ser = new String[column_cnt];
        area_index = new int[column_cnt];

        for (int i=0; i<column_cnt; i++)
        {
            area_value[i] = " ";
            area_index[i] = -1;
            area_parse[i] = " ";
            area_opt[i] = " ";
            area_ser[i] = " ";
        }
    }

    public void setSelectedColumnStatus(String column_name, int combo_index)
    {
        selected_column_name = column_name;
        selected_index = combo_index;
    }

    public String getTextArea(String i_str1, String i_str2, String i_str3, String i_gubun)
    {
       String comp_value;
       String area_text;
       int comp_result;
       int check_value = checkTextField(i_str2,i_str3);
       if (check_value == 1)
       {
          comp_result = compareValue(i_str1,i_str2);
          comp_value = i_str2;
          setSymbol(i_gubun+"1");
          setTempValue(i_str1,i_str2,i_str3,comp_value,i_gubun,comp_result);
       }
       else if (check_value == 2)
       {
          comp_result = compareValue(i_str1,i_str3);
          comp_value = i_str3;
          setSymbol(i_gubun+"2");
          setTempValue(i_str1,i_str2,i_str3,comp_value,i_gubun,comp_result);
       }
       else
       {
          setTempValue(i_str1, i_gubun);
       }
       opt_cnt = opt_cnt + 1;
       area_value[selected_index]="("+temp_value+")";
       area_index[selected_index]=selected_index;
       area_parse[selected_index]=parse_value;
       area_ser[selected_index]=String.valueOf(opt_cnt);
       if (or_check&&and_check)
       {
          area_text = new_value+area_value[selected_index]+")";
       }
       else
       {
       //   area_text = new_value+area_value[selected_index]+")";
          area_text = new_value+area_value[selected_index];
       }
       return area_text;
  }

  public void setTextArea()
  {
       String temp_opt="";
       int temp_index = 0;
       for (int i=0; i<opt_cnt; i++)
       {
          temp_index = serCheck(i+1);
          if (temp_index!=-1)
          {
              temp_opt = area_opt[temp_index];
          }
       }
       if (temp_opt.equals("or"))
       {
          if (!and_check)
          {
              new_value = new_value+area_value[temp_index]+temp_opt;
          }
          else
          {
              if (!or_check)
              {
                  new_value = "("+new_value+area_value[temp_index]+")"+temp_opt;
              }
              else
              {
                  new_value = new_value+area_value[temp_index]+")"+temp_opt;
              }
          }
          or_check = true;
          and_check = false;
       }
       else if (temp_opt.equals("and"))
       {
          if (and_check)
          {
              new_value = new_value+area_value[temp_index]+temp_opt;
          }
          else
          {
              if (!or_check)
              {
                   new_value = new_value+area_value[temp_index]+temp_opt;
              }
              else
              {
                   new_value = new_value+"("+area_value[temp_index]+temp_opt;
              }
          }
          and_check = true;
       }
  }

  public String getTextArea(String i_str)
  {
      temp_value = "("+selected_column_name+"="+i_str+")";
      parse_value = "3"+"C"+selected_index+"="+i_str;
      opt_cnt = opt_cnt + 1;
      area_value[selected_index]=temp_value;
      area_index[selected_index]=selected_index;
      area_parse[selected_index]=parse_value;
      area_ser[selected_index]=String.valueOf(opt_cnt);
      if (or_check&&and_check)
      {
         return new_value+temp_value+")";
      }
      else
      {
         return new_value+temp_value;
      }
  }

  public void setSelectOption(String event_item, String cbox_item)
  {
      if (cbox_item.equals(event_item))
      {
          if (!cbox_item.equals(" "))
          {
          area_opt[selected_index] = cbox_item;
          setTextArea();
      }
    }
    temp_value = "";
    parse_value = "";
  }

  private int checkTextField(String i_str1, String i_str2)
  {
       if (i_str1.equals(""))
       {
          if (!i_str2.equals(""))
          {
              return 2;
          }
          else
          {
              return -1;
          }
       }
       else
       {
          if (i_str2.equals(""))
          {
              return 1;
          }
          else
          {
              return -1;
          }
       }
  }

  private int compareValue(String i_str1, String i_str2)
  {
       int int1 = Integer.parseInt(i_str1);
       int int2 = Integer.parseInt(i_str2);
       if (int1<int2)
       {
          return 1;
       }
       else if (int1>int2)
       {
          return 2;
       }
       else
       {
          return -1;
       }
  }

  void setSymbol(String i_string)
  {
       if ((i_string.equals("A1"))||(i_string.equals("C1")))
       {
          symbol1 = "<";
          symbol2 = "<";
          symbol3 = "<";
          symbol4 = ">";
       }
       else if ((i_string.equals("A2"))||(i_string.equals("D1")))
       {
          symbol1 = "<";
          symbol2 = "<=";
          symbol3 = "<=";
          symbol4 = ">";
       }
    else if ((i_string.equals("B1"))||(i_string.equals("C2")))
    {
      symbol1 = "<=";
      symbol2 = "<";
      symbol3 = "<";
      symbol4 = ">=";
    }
    else if ((i_string.equals("B2"))||(i_string.equals("D2")))
    {
      symbol1 = "<=";
      symbol2 = "<=";
      symbol3 = "<=";
      symbol4 = ">=";
    }
  }

  void setTempValue(String i_str1, String i_str2, String i_str3, String comp_value, String i_gubun, int comp_result)
  {
    if ((i_gubun.equals("A"))||(i_gubun.equals("B")))
    {
      if (comp_result==1)
      {
        temp_value = i_str1+symbol1+selected_column_name+symbol2+comp_value;
        parse_value = "5"+i_str1+symbol1+"C"+selected_index+symbol2+comp_value;
      }
      else if (comp_result==2)
      {
        temp_value = selected_column_name+symbol3+comp_value+"||"+selected_column_name+symbol4+i_str1;
        parse_value = "7"+"C"+selected_index+symbol3+comp_value+"||"+"C"+selected_index+symbol4+i_str1;
      }
      else
      {
        System.out.println("Compare Error");
      }
    }
    else
    {
      if (comp_result==1)
      {
        temp_value = selected_column_name+symbol3+i_str1+"||"+selected_column_name+symbol4+comp_value;
        parse_value = "7"+"C"+selected_index+symbol3+i_str1+"||"+"C"+selected_index+symbol4+comp_value;
      }
      else if (comp_result==2)
      {
        temp_value = comp_value+symbol1+selected_column_name+symbol2+i_str1;
        parse_value = "5"+comp_value+symbol1+"C"+selected_index+symbol2+i_str1;
      }
      else
      {
        System.out.println("Compare Error");
      }
    }
  }

  void setTempValue(String i_str, String i_gubun)
  {
    if (i_gubun.equals("A"))
    {
      temp_value = i_str+"<"+selected_column_name;
      parse_value = "3"+i_str+"<"+"C"+selected_index;
    }
    else if (i_gubun.equals("B"))
    {
      temp_value = i_str+"<="+selected_column_name;
      parse_value = "3"+i_str+"<="+"C"+selected_index;
    }
    else if (i_gubun.equals("C"))
    {
      temp_value = selected_column_name+"<"+i_str;
      parse_value = "3"+"C"+selected_index+"<"+i_str;
    }
    else if (i_gubun.equals("D"))
    {
      temp_value = selected_column_name+"<="+i_str;
      parse_value = "3"+"C"+selected_index+"<="+i_str;
    }
  }

  public String getChangeComboBoxValue(String event_item, String cbox_item)
  {
      if (cbox_item.equals(event_item))
      {
         if (!cbox_item.equals(" "))
         {
            area_opt[selected_index] = cbox_item;
            setTextArea();
         }
      }
      temp_value = "";
      parse_value = "";
      return new_value;
  }

  private int serCheck(int i_num)
  {
    int o_num = -1;
    String str_value = String.valueOf(i_num);
    for (int i=0; i<column_cnt; i++)
    {
      String temp_ser = area_ser[i];
      if (str_value.equals(temp_ser))
      {
        o_num = i;
      }
    }
    return o_num;
  }

  public void setValidArray()
  {
      valid_value = new String[opt_cnt];
      valid_index = new int[opt_cnt];
      valid_parse = new String[opt_cnt];
      valid_opt = new String[opt_cnt];
      valid_ser = new String[opt_cnt];
      int count = 0;
      for (int i=0; i<column_cnt; i++)
      {
        if (area_value[i].equals(" "))
        {
          System.out.println("space_bar");
        }
        else
        {
          valid_value[count] = area_value[i];
          valid_index[count] = area_index[i];
          valid_parse[count] = area_parse[i];
          valid_opt[count] = area_opt[i];
          valid_ser[count] = area_ser[i];
          count++;
        }
    }
  }

  public String[] getValidParse()
  {
      return valid_parse;
  }

  public int[] getValidIndex()
  {
      return valid_index;
  }

  public String[] getValidOpt()
  {
      return valid_opt;
  }

  public String[] getValidSer()
  {
      return valid_ser;
  }

}