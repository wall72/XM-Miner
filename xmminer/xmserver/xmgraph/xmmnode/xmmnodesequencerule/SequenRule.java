package xmminer.xmserver.xmgraph.xmmnode.xmmnodesequencerule;

import java.util.*;
import java.io.*;
import java.lang.*;
import java.text.DecimalFormat;
import xmminer.xmlib.xmtable.*;
import xmminer.xmlib.xmcompute.*;

public class SequenRule {
  static String projectName;
  static String modelName;
  static String dataFile;
  static String projectPath;

  static float min_conf;
  static int num_trans;

  String LSkset;
  int Lkset_sup;
  int k;

  static Hashtable largeHash;
  Hashtable premises;     //rule의 전제 부분과 confidence를 저장하는 변수

  public SequenRule(String litemset, int num_items, int sup){
    LSkset = litemset;
    k = num_items;        //Lkset이 몇개의 item들로 이루어져 있나..
    Lkset_sup = sup;      //support를 저장

    premises = new Hashtable();
  }


  public static void setParam(Hashtable litemHash, float confidence, String model, int transNumber, String project, String data, String path){
    largeHash = litemHash; //모든 litemset의 support를 가지고 있다.

    int size = largeHash.size();

    projectName = project;
    projectPath = path;
    dataFile = data;

    min_conf = confidence;
    num_trans = transNumber;

    modelName = model;
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

      litemset = tokenized_line.nextToken();
      sup = Integer.parseInt(tokenized_line.nextToken());
      litem_table.put(litemset, new Integer(sup));
    }

    litemset_file.close();
    return litem_table;
  }

  public void gen_rule(int num_items, CXMTableSaver ruleData) throws IOException{
    //Lkset을 가지고 생성 가능한 rules의 premises들을 구한다.
    String item = new String();
    StringTokenizer tokenized_LSkset = new StringTokenizer(LSkset, ",");

    for(int i = 0; i < num_items-1; i++){
      if( i == 0 ){   //LSkset="a,b,c"일 때  i=0 => "a",  i=1 => "a,b"
        item = tokenized_LSkset.nextToken();    //item : premise
      }else item += ',' + tokenized_LSkset.nextToken();

      //rule이 성립되면 premises에 저장한다.
      check_rule(item, i+1);
    }

    if(premises.size() > 0 ){
      write_rules(ruleData);   //premises에 들어있는 rule을 찾아 파일에 저장한다.
      premises.clear();
    }
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

    return check;
  }

  public String substract_itemset(String itemset){
    //LSkset에서 인자로 넘어온 itemset을 뺀 나머지 itemset을 리턴한다.
    //LSkset-itemset;
    String remain = new String();
    String item  = new String();

    Vector imsi = new Vector();
    StringTokenizer tokenized_itemset  = new StringTokenizer(itemset,",");
    while(tokenized_itemset.hasMoreTokens()){
      item = tokenized_itemset.nextToken();
      imsi.addElement(item);
    }

    StringTokenizer tokenized_LSkset  = new StringTokenizer(LSkset,",");

    while(tokenized_LSkset.hasMoreTokens()){
      item = tokenized_LSkset.nextToken();
      if(imsi.contains(item) == false){
        remain += item + ",";
      }
    }//end while

	if(remain.length() > 0) remain = remain.substring(0, remain.length()-1);
    
	return remain;
  }

  private void write_rules(CXMTableSaver ruleData) throws IOException{
    //"premise => consequent; sup(), conf" 형식으로 rule을 파일에 저장한다.
    //파일이 존재하면 내용을 모두 삭제한다.

    String temp = new String();
    String premise = new String();
    String antecedent = new String();
    String consequent = new String();
    Integer sup;

    //제란새로추가
    int[] colIndex = new int[5];  //5개의 column
    byte[] row, column;

    String str_sup1, str_sup2, str_conf;
    //Lkset_sup를 string 타입으로 변환
    Integer ii = new Integer(Lkset_sup);
    str_sup1 = ii.toString();

    Enumeration itemsets = premises.keys();
    while(itemsets.hasMoreElements()){
      premise = (String)itemsets.nextElement();     //mpping된 premise

      SeqPattern patten = new SeqPattern();
      antecedent = patten.reTransform(projectName, premise, modelName); //reTransform된 premise
      consequent = patten.reTransform(projectName, substract_itemset(premise), modelName);

      temp = antecedent + "=>" + consequent + ", ";

      //support와 confidence를 0.000형식으로 출력하기 위해...
      DecimalFormat fourDigits = new DecimalFormat("0.000");
      float f = (float)Lkset_sup/num_trans;   //support
      str_sup2 = fourDigits.format(f).toString();   //supprt(%)
      temp += Lkset_sup + "(" + str_sup2 + "), ";

      f = ((Float)premises.get(premise)).floatValue();  // Float -> float
      str_conf = fourDigits.format(f).toString();  //confidence(%)
      temp += str_conf;        //confidence

      row = antecedent.getBytes();   //premise
      colIndex[0] = row.length;
      column = consequent.getBytes();   //consequent
      row = addColAtRow(column, row, colIndex, 1);
      column = str_sup1.getBytes();     //support(개수)
      row = addColAtRow(column, row, colIndex, 2);
      column = str_sup2.getBytes();     //support(%)
      row = addColAtRow(column, row, colIndex, 3);
      column = str_conf.getBytes();    //confidence(%)
      row = addColAtRow(column, row, colIndex, 4);

      System.out.println( "row = " + new String(row));
      ruleData.createRowData(row, colIndex);  //하나의 rule을 저장한다.
      XMBMNodeSequenceRule.num_rows++;
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
    //"premise => consequent; sup(), conf" 형식으로 rule을 파일에 저장한다.
    //파일이 존재하면 내용을 모두 삭제한다.

    //RandomAccessFile를 이용해서 덧붙인다.
    RandomAccessFile rule_data = new RandomAccessFile(projectPath + projectName + "\\" + modelName + "_rules.txt", "rw");
    long fp ;

    String temp = new String();
    String premise = new String();
    String antecedent = new String();
    String consequent = new String();
    Integer sup;

    String rule_line = new String();
    int bExistRule  = 0;         //rule이 이미 저장되어 있나..

    //제란새로추가
    int[] colIndex = new int[5];  //5개의 column
    byte[] row, column;

    String str_sup1, str_sup2, str_conf;
    //Lkset_sup를 string 타입으로 변환
    Integer ii = new Integer(Lkset_sup);
    str_sup1 = ii.toString();

    Enumeration itemsets = premises.keys();
    while(itemsets.hasMoreElements()){
      premise = (String)itemsets.nextElement();     //mpping된 premise
      SeqPattern patten = new SeqPattern();
      antecedent = patten.reTransform(projectName, premise, modelName); //reTransform된 premise
      consequent = patten.reTransform(projectName, substract_itemset(premise), modelName);

      temp = antecedent + "=>" + consequent + ", ";

      //support와 confidence를 0.000형식으로 출력하기 위해...
      DecimalFormat fourDigits = new DecimalFormat("0.000");
      float f = (float)Lkset_sup/num_trans;   //support
      str_sup2 = fourDigits.format(f).toString();   //supprt(%)
      temp += Lkset_sup + "(" + str_sup2 + "), ";

      f = ((Float)premises.get(premise)).floatValue();  // Float -> float
      str_conf = fourDigits.format(f).toString();  //confidence(%)
      temp += str_conf;        //confidence

      //rule이 존재하는지 검색하지 않아도 된다...?????
      //maximal sequence가 아닌 rule은 저장하지 않는다...
      rule_data.seek(0);        // 파일의 처음 위치로 감..
      while( (rule_line=rule_data.readLine()) != null) {
        if (rule_line.compareTo(temp.toString()) == 0){
          bExistRule = 1;       // rule이 이미 존재..
          break;
        }
      }

      if(bExistRule == 0){
        temp += "\r\n";  //rule을 저장한 후 엔터
        rule_data.writeBytes(temp.toString());
      }

	  bExistRule = 0;

 	}//end while  

   rule_data.close();
  }

}