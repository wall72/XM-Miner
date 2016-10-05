package xmminer.xmclient.xmgraph.xmmnode.xmmnodeneuralnetwork;

import java.lang.*;

class ColumnData
{
  public Integer m_number;
  public String  m_col_name;  
  public String  m_col_type;  
  public Integer m_category;
  public Boolean m_input_check;
  public Boolean m_output_check;
  public Boolean m_not_use_check;


  public ColumnData() {
    m_number		= new Integer(0);
	m_col_name		= new String();
	m_col_type		= new String();
	m_category      = new Integer(3);
	m_input_check   = new Boolean(false);
	m_output_check  = new Boolean(false);
	m_not_use_check = new Boolean(true);
  }

  public ColumnData(int number, String col_name, String col_type,
  int category, boolean input_check, boolean output_check, boolean not_use_check)
   {
    m_number		= new Integer(number) ;
	m_col_name		= col_name ;
	m_col_type		= col_type ;
	m_category      = new Integer(category);
	m_input_check   = new Boolean(input_check) ;
	m_output_check  = new Boolean(output_check) ;
	m_not_use_check = new Boolean(not_use_check);
  }
}
