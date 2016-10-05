package xmminer.xmserver.xmgraph.xmmnode.xmmnodeassociationrule;

import java.util.*;
import java.io.*;
import java.lang.*;
import java.text.DecimalFormat;
import xmminer.xmlib.xmtable.*;
import xmminer.xmlib.xmcompute.*;

public class AssoRule{
  static String projectName;
  static String projectPath;
  static String dataFile;

  private String Lkset;
  private int Lkset_sup;
  private int k;

  static float min_conf;
  static String ruleFile;
  static int num_trans;
  static String modelName;

  static Hashtable largeHash;

  Hashtable premises;     //rule의 전제 부분과 confidence를 저장하는 변수

  public AssoRule(String litemset, int num, int sup){
    Lkset = litemset;
    k = num;              //Lkset이 몇개의 item들로 이루어져 있나..
    Lkset_sup = sup;      //support를 저장

    premises = new Hashtable();
  }

  public static void setParam(Hashtable litemHash, float confidence, String model, int transNumber, String project, String data, String path){
    largeHash = litemHash; //모든 litemset의 support를 가지고 있다.
    
	projectName = project;
    projectPath = path;
    dataFile = data;

    min_conf = confidence;
    num_trans = transNumber;

    modelName = model;
    ruleFile = projectPath + projectName + "\\" + modelName + "_rule.txt";
  }

  public static Hashtable read_Litemset(String fname) throws IOException{
    //Litemset 파일에서 Lkset을 하나씩 읽어 처리한다.
    //파일이 존재하는지 체크한다.  또는 내용이 있는지..
    FileInputStream litemset_file = new FileInputStream(fname);
    BufferedReader litemset_data = new BufferedReader(new InputStreamReader(litemset_file));
 
    String line =  new String();
    String litemset = new String();
    int sup;
    Hashtable litem_table = new Hashtable();

    //line별로 읽어 Hashtable에 저장한다.
    while((line = litemset_data.readLine()) != null){
      StringTokenizer tokenized_line = new StringTokenizer(line, ";");
      //int num_itemset = tokenized_line.countTokens();

      litemset = tokenized_line.nextToken();
      sup = Integer.parseInt(tokenized_line.nextToken());
      litem_table.put(litemset, new Integer(sup));
    }

    litemset_file.close();

    return litem_table;
  }

  private Litemset getH1() throws IOException {
    //consequents가 1 item인 premises_tmp를 구해 rule의 consequent를 저장한다.
    Vector subsets = Litemset.subset(Lkset);
    Litemset consequents = new Litemset(1);

    int s = subsets.size();
    for(int i=0; i < s; i++){
      String premise = (String)subsets.elementAt(i);
      if(check_rule(premise, k-1)== true){
        //premise의 consequent를 구해 consequents에 저장한다.
        String consequent = substract_itemset(premise);
        consequents.table.put(consequent, new Integer(0));
      }
    }//end for

    return consequents;
  }

  public void gen_rule(String litemset, int num_items,CXMTableSaver ruleData) throws IOException { //simple용
    //Lkset을 가지고 생성 가능한 rules의 premises들을 구한다.
    Vector subsets = Litemset.subset(litemset);
    int s = subsets.size();

    int m = num_items;  //k
    for(int i = 0; i < s; i++){
      String premise = (String)subsets.elementAt(i);
      if(check_rule(premise, m-1) == true){    //rule이 min_conf를 만족하면,
        if( m-1 > 1){
          gen_rule(premise, m-1,ruleData);
        }
      }//end if
    }//end for

    if(premises.size() > 0 ){
      write_rules(ruleData);
      premises.clear();     //premises Hashtable을 비운다.
    }
  }

  public void gen_rule2(String litemset, int num_items, CXMTableSaver ruleData) throws IOException { //faster용
    //Lkset을 가지고 생성 가능한 rules의 premises들을 구한다.
    Litemset litemH1 = getH1();

    apGenRules(litemH1, 1);    //apriori_gen()을 하기위해 Litemset형으로 선언

    write_rules(ruleData);
  }

  private void apGenRules(Litemset litemHm, int num_items) throws IOException {
    int m = num_items + 1;
    if(k > m){
      Litemset litemHmm = new Litemset(m);
      litemHm.apriori_gen(litemHmm);   //litemHm : Hm, litemHmm : Hm+1

      Enumeration consequents = litemHmm.table.keys();
      while(consequents.hasMoreElements()){
        String consequent = (String)consequents.nextElement();

        //check_rule()에서 성립되는 rule의 premise는 premises에 저장한다.
        if(check_rule(substract_itemset(consequent), k-m) == false){
          litemHmm.table.remove(consequent);
        }
      }//end while
      apGenRules(litemHmm, m);
    }//end if
  }

  private boolean check_rule(String premise, int num_items) throws IOException{
    //prmises로 생성될 수 있는 rule이 min_conf가 되는지 확인한다.
    //성립되는 rule은 confidence와 함께 premises에 저장한다.
    boolean check = false;

    //premise의 support를 읽어온다.
    Integer supInt = (Integer)largeHash.get(premise);
    int sup = supInt.intValue();

    float conf = Lkset_sup/(float)sup ; // /  (premises의 support)
    if(conf >= min_conf){
	  //Hashtable에 confidence와 함께 저장한다.    float -> Float
      premises.put(premise, new Float(conf));
      check = true;
    }

    //Lkfile.close();
    return check;
  }

  private String substract_itemset(String itemset){
    //Lkset에서 인자로 넘어온 itemset을 뺀 나머지 itemset을 리턴한다.
    //Lkset-itemset;
    String remain = new String();
    String item = new String();

    Vector imsi = new Vector();
    StringTokenizer tokenized_itemset  = new StringTokenizer(itemset,",");
    while(tokenized_itemset.hasMoreTokens()){
	    item = tokenized_itemset.nextToken();
      imsi.addElement(item);
    }

    StringTokenizer tokenized_Lkset  = new StringTokenizer(Lkset,",");
    while(tokenized_Lkset.hasMoreTokens()){
	    item = tokenized_Lkset.nextToken();

      //itemset의 모든 아이템에 대하여 item이 존재하지않으면 remain에 item을 더한다.
      if( imsi.contains(item) == false){
        remain += item + ",";
      }
      /*if(itemset.indexOf(item)== -1){
	      remain += item + ",";
      } */
    }//end while
    if ( remain.length() > 0 ){
      remain = remain.substring(0, remain.length()-1);
    }
    return remain;
  }

  private void write_rules(CXMTableSaver ruleData) throws IOException{
    //"premise;consequent;sup,conf" 형식으로 rule을 파일에 저장한다.
    //파일이 존재하면 내용을 모두 삭제한다.

    //temp를 StringBuffer로 바꿔보자....!
    //sup를 int형으로 변환않고 바로 파일에 쓴다.(append function)
    String premise = new String();
    String consequent = new String();
    Integer sup;

    int[] colIndex = new int[5];  //5개의 column
    byte[] row, column;

    String str_sup1, str_sup2, str_conf;
    //Lkset_sup를 string 타입으로 변환
    Integer ii = new Integer(Lkset_sup);
    str_sup1 = ii.toString();

    //cxmtable로 rule 저장
    Enumeration itemsets = premises.keys();
    while(itemsets.hasMoreElements()){
      premise = ((String)itemsets.nextElement());
      consequent = substract_itemset(premise);

      //support와 confidence를 0.000형식으로 출력하기 위해...
      DecimalFormat fourDigits = new DecimalFormat("0.000");
      float f = (float)Lkset_sup/num_trans;
      str_sup2 = fourDigits.format(f).toString();   //supprt(%)

      f = ((Float)premises.get(premise)).floatValue();    // Float -> float
      str_conf = fourDigits.format(f).toString();  //confidence(%)

      row = premise.getBytes();   //premise
      colIndex[0] = row.length;
      column = consequent.getBytes();   //consequent
      row = addColAtRow(column, row, colIndex, 1);
      column = str_sup1.getBytes();     //support(개수)
      row = addColAtRow(column, row, colIndex, 2);
      column = str_sup2.getBytes();     //support(%)
      row = addColAtRow(column, row, colIndex, 3);
      column = str_conf.getBytes();    //confidence(%)
      row = addColAtRow(column, row, colIndex, 4);

	  ruleData.createRowData(row, colIndex);  //하나의 rule을 저장한다.
      //XMBMNodeAssociationRuleImpl.num_rows++;
      XMBMNodeAssociationRule.num_rows++;
    }//end while
  }
 
  public static byte[] addColAtRow(byte col[], byte row[], int[] colIndex, int j){
    //현재 row에다 col을 덧붙이고, colIndex[j]에 col의 length를 추가하는 루틴이다.
    int colLength = col.length;
    int rowLength = row.length;
    int newCapacity = colLength + rowLength;

    byte[] row_tmp = new byte[newCapacity];

    System.arraycopy(row, 0, row_tmp, 0, rowLength);
    System.arraycopy(col, 0, row_tmp, rowLength, colLength);

    colIndex[j] = colLength;

    return row_tmp;
  }
 
  private void write_rules_file() throws IOException{
    //"premise;consequent;sup,conf" 형식으로 rule을 파일에 저장한다.
    //파일이 존재하면 내용을 모두 삭제한다.

    //RandomAccessFile를 이용해서 덧붙인다.
    RandomAccessFile rule_data = new RandomAccessFile(ruleFile, "rw");
    long fp ;


    //temp를 StringBuffer로 바꿔보자....!
    //sup를 int형으로 변환않고 바로 파일에 쓴다.(append function)
    String temp = new String();
    String premise = new String();
    String consequent = new String();
    Integer sup;

    String rule_line = new String();
    int bExistRule  = 0;         //rule이 이미 저장되어 있나..

    int[] colIndex = new int[5];  //5개의 column
    byte[] row, column;

    String str_sup1, str_sup2, str_conf;
    //Lkset_sup를 string 타입으로 변환
    Integer ii = new Integer(Lkset_sup);
    str_sup1 = ii.toString();

 
    Enumeration itemsets = premises.keys();
    while(itemsets.hasMoreElements()){

      premise = ((String)itemsets.nextElement());
      consequent = substract_itemset(premise);
      temp = premise + "=>" + consequent + "; ";

      //support와 confidence를 0.000형식으로 출력하기 위해...
      DecimalFormat fourDigits = new DecimalFormat("0.000");
      float f = (float)Lkset_sup/num_trans;
      str_sup2 = fourDigits.format(f).toString();   //supprt(%)
      temp += Lkset_sup + "(" + str_sup2 + "), ";

      f = ((Float)premises.get(premise)).floatValue();    // Float -> float
      str_conf = fourDigits.format(f).toString();  //confidence(%)
      temp += str_conf + "\r\n";

      long leng = rule_data.length();
      rule_data.seek(leng);
    }//end while
    rule_data.close();
  }

}