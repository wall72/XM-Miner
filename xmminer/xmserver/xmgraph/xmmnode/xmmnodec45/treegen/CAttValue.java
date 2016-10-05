
//Title:        XM_TreeGen
//Version:      
//Copyright:    Copyright (c) 1999
//Author:       최 대 우
//Company:      지능정보시스템
//Description:  OneTree

package xmminer.xmserver.xmgraph.xmmnode.xmmnodec45.treegen;
public class CAttValue
{

  int	  discr_val;
	double	cont_val;

  public CAttValue()
  {
      this.discr_val = 0;
      this.cont_val=0.0;
  }

  public void put_cont_val(double cont_value)
  {
      this.cont_val= cont_value;
  }

  public void put_discr_val(int discr_value)
  {
      this.discr_val = discr_value;
  }
  public double get_cont_val()
  {
      return this.cont_val;
  }

  public int get_discr_val()
  {
      return this.discr_val;
  }
}
