package xmminer.xmserver.xmgraph.xmmnode.xmmnodeassociationrule;

import java.util.*;
import java.io.*;

import xmminer.xmserver.xmgraph.xmmnode.xmmnodeassociationrule.*;
import xmminer.xmlib.xmtable.*;
import xmminer.xmlib.xmcompute.*;

public class Litemset{
  private int num_items;
  public Hashtable table = new Hashtable();

  static int min_sup;
  static int num_trans;
  static String dataFile;

  private String projectName;
  private String projectPath;
  private String modelName;

  private int dbType;
  private String[] selAttributes;   //dbType = 1
  private String[] columnList;      //dbType = 2

  private String transIDFld;  //Type 2에서 기준이 되는 필드명(transaction ID)
  private String targetFld;   //Type 2에서 item 필드명

  public Litemset(int num, int support, String model, int transNumber, String project, String data, String path){
    projectName = project;
    projectPath = path;
    dataFile = data;

    num_items = num;
    min_sup = support;
    modelName = model;

    num_trans = transNumber;
  }

  public Litemset(int num){
    num_items = num;
  }

  public void setParam1(String[] attributes){ //dbType = 1
    //선택된 칼럼들을 파싱하여 selAttributes 멤버변수를 초기화한다.
    dbType = 1;
    selAttributes = attributes;
  }

  public void setParam2(String transIDFldName, String targetFldName, String[] cols){ //dbType = 2
    dbType = 2;

    transIDFld = transIDFldName;
    targetFld = targetFldName;

    columnList = cols;
  }

  public int gen_litemset() throws IOException{  //dbType = 1
    int num_litems;

    get_support();
    num_litems = get_large_itemset();      //small itemset은 table에서 삭제한다.

    return num_litems;
  }

  public int gen_litemset2() throws IOException{  //dbType = 2
    int num_litems;

    get_support2();
    num_litems = get_large_itemset();      //small itemset은 table에서 삭제한다.

    return num_litems;
  }

  public void read_allItem() throws IOException{
    FileInputStream allItem_file = null;
    allItem_file = new FileInputStream(projectPath + projectName + "/data/" + dataFile + ".udat");
    BufferedReader allItem_data = new BufferedReader(new InputStreamReader(allItem_file));

    String item = new String();
    String line = allItem_data.readLine();
    StringTokenizer tokenized_line = new StringTokenizer(line, ",");

    while(tokenized_line.hasMoreTokens()){
      item = tokenized_line.nextToken();
      table.put(item, new Integer(0));
    }

    allItem_file.close();
  }

  public int itemsDataSaver(){ //dbType=1인 association에만 사용 가능
    CXMUniqueValueFileSaver itemsData = new CXMUniqueValueFileSaver();
    itemsData.setFileStatus(projectName, dataFile);

    itemsData.makeUniqueValueFileInColumnList(selAttributes, true);
    itemsData.close();

    return num_trans;
  }

  public void itemsDataSaver2(){
    String[] colList = new String[1];
    colList[0] = targetFld;

    CXMUniqueValueFileSaver itemsData = new CXMUniqueValueFileSaver();
    itemsData.setFileStatus(projectName, dataFile);
    itemsData.makeUniqueValueFileInColumnList(colList, false);
    itemsData.close();
  }

  private void get_support() throws IOException{  //dbType = 1
    //transaction을 읽어 Ct를 찾고, Hashtable의 해당 support를 증가시킨다.
    String itemset;    //item;
    Vector transaction, contained_itemset;

    CXMTableQuerier transData = new CXMTableQuerier();  //transaction data file
    transData.setFileStatus(projectName, dataFile, null, selAttributes);

    for(int t = 0; t < num_trans; t++){  //transaction 수
      transaction = transData.getTransaction(t + 1);

      contained_itemset = contained_itemsets(transaction);  //Vector
      int num_Ct = contained_itemset.size();

      for(int i=0; i < num_Ct; i++){

        itemset = (String)contained_itemset.elementAt(i);
        if(table.containsKey(itemset)){   //table에 itemset이 저장되어 있으면
          Integer sup = (Integer)table.get(itemset);

          int sup_tmp = sup.intValue();
          sup_tmp++;                    //해당 item의 support를 증가시킨다.
          sup = new Integer(sup_tmp);
          table.put(itemset, sup);
        }//end if
      }//end for
      contained_itemset.clear();

    }//end for

    transData.close();
  }

  private void get_support2() throws IOException{  //dbType = 2
    //transaction을 읽어 Ct를 찾고, Hashtable의 해당 support를 증가시킨다.
    String itemset, item;      //, colUniqueValue;
    Vector transaction, contained_itemset;

    String transLine;

    CXMTableReader transData2 = new CXMTableReader();  //transaction data file
    transData2.setFileStatus(projectName, modelName + "_asso_trans");

    for(int t = 0; t < num_trans; t++){  //transaction 수
      transaction = new Vector();

      //cxmtable에서 하나의 transaction을 읽어 transaction Vector에 저장한다.
      transLine = transData2.getString(t + 1);
      StringTokenizer tokened_trans = new StringTokenizer(transLine, ",");
      while(tokened_trans.hasMoreTokens()){
        item = tokened_trans.nextToken();
        transaction.add(item);
      }

      contained_itemset = contained_itemsets(transaction);  //Vector
      int num_Ct = contained_itemset.size();

      for(int i=0; i < num_Ct; i++){
        itemset = (String)contained_itemset.elementAt(i);
        if(table.containsKey(itemset)){   //table에 itemset이 저장되어 있으면
          Integer sup = (Integer)table.get(itemset);
          int sup_tmp = sup.intValue();
          sup_tmp++;                    //해당 item의 support를 증가시킨다.
          sup = new Integer(sup_tmp);
          table.put(itemset, sup);
        }//end if
      }//end for
      contained_itemset.clear();
    }//end for
    transData2.close();
  }

  public Vector contained_itemsets(Vector transaction){
    //table의 itemset들 중 해당 trans의 subset(Ct)인 것을 찾아 Vector에 저장한다.
    String item = new String();
    String itemset = new String();
    Vector contained_itemset = new Vector();

    Enumeration itemsets = table.keys();

    outsideLoop:   //---------------------
    while(itemsets.hasMoreElements()){
      itemset = (String)itemsets.nextElement();

      StringTokenizer tokenized_itemset = new StringTokenizer(itemset, ",");
      while(tokenized_itemset.hasMoreTokens()){
        item = tokenized_itemset.nextToken();

        //모든 item이 trans에 substring으로 포함되어 있는지 확인한다...
        if( transaction.contains(item) == false ){
          continue outsideLoop;   //포함되지 않으면 다음 itemset으로 넘어간다.
        }
      }//end inside while
      contained_itemset.addElement(itemset);
    }//end outside while
    return contained_itemset;
  }

  public int apriori_gen(Litemset Candiset){
  //candidate itemsets을 구한다음 구해진 itemset의 수를 리턴한다.
    String pitemset = new String();
    String qitemset = new String();
    String temp = new String();

    if(num_items == 1){   // 1개의 item으로 이루어진 itemset에서..
      int num_keys = table.size();
      String[] items = new String[num_keys];

      //items을 소팅한다.
      int j = 0;
      Enumeration itemsets = table.keys();
      while(itemsets.hasMoreElements()){
        items[j++] = (String)itemsets.nextElement();
      }
      Arrays.sort(items);

      //String items[]을 가지고 join작업을 실행한다.
      for(int i = 0; i < num_keys; i++){
        for(int k=i+1; k < num_keys; k++){
          pitemset = items[i];
          qitemset = items[k];
          temp = pitemset + "," + qitemset;
          Candiset.table.put(temp, new Integer(0));
        }
      }//end for
    }else{     // num_items가 2이상일 때
      Vector candiset;

      if (dbType == 1 ){ //dbType = 1
        candiset = join();
      }else{    //dbType = 2
        candiset = join2();
      }
      candiset = prune(candiset);

      //Litemset형 candidate itemset에 저장한다.
      int num_candisets = candiset.size();
      for(int i=0; i < num_candisets; i++){
        temp = (String)candiset.elementAt(i);
        Candiset.table.put(temp, new Integer(0));
      }
    }//end else

    return Candiset.table.size();
  }

  private Vector join() throws IndexOutOfBoundsException{
    //table의 itemset들을 join하여 임시 candidate itemset을 생성한다.
    Vector candiset = new Vector();
    String temp;
    String cmp_itemset = new String();
    int capacityIncrement = 0;   //String itemsets[]변수의 증가량
    //k-1개까지의 items이 동일한 itemset들을 sort하기 위해 모아둘 변수
    //동적으로 할당하는 거 생각해보기..!(system.arraycopy)
    int i;        //동일한  block에 속하는 itemset 수
    int index;    //k-1개까지의 items을 구분하기 위한 변수

    //table의 itemsets을 Vector형 변수로 복사한다.
    Vector table_tmp = new Vector();
    Enumeration itemsets_tmp = table.keys();
    while(itemsets_tmp.hasMoreElements()){
      temp = (String)itemsets_tmp.nextElement();
      table_tmp.addElement(temp);
    }

    //복사된 Vector형 변수 table_tmp를 가지고 하나씩 꺼내온다.
    itemsets_tmp = table_tmp.elements();
    while(table_tmp.isEmpty() == false){ //empty가 아닐 동안...
      String itemsets[] = new String[10];   //k-1까지의 item이 동일한 itemset을 모아둘 변수
      i = 0;
      capacityIncrement = 0;
      itemsets_tmp = table_tmp.elements();
      temp =(String)itemsets_tmp.nextElement();
      itemsets[i++] = temp;
      capacityIncrement = capacityIncrement + 1;

      index = temp.lastIndexOf(',');
      cmp_itemset = temp.substring(0, index + 1);   //','까지 포함된 cmp_itemset

      //cmp_itemset 부분이 동일한 itemset들을 구하여 itemsets[]에 저장한다.
      while(itemsets_tmp.hasMoreElements()){
        temp = (String)itemsets_tmp.nextElement();

        //itemset_tmp가 qitemset이 될 수 있는지 확인한다.
        if(temp.indexOf(cmp_itemset) == 0){   //k-1개까지의 items이 동일하다면,
          itemsets[i++] = temp;

          //capacityIncrement가 10이 되면 메모리를 늘려준다...!!
          capacityIncrement = capacityIncrement + 1;
          if(capacityIncrement == 10){
            capacityIncrement = 0;            //다시 0으로 초기화

            int oldCapacity = itemsets.length;
            String oldData[] = itemsets;
            int newCapacity = oldCapacity + 10;
            itemsets = new String[newCapacity];
            System.arraycopy(oldData, 0, itemsets, 0, oldCapacity);
          }
        }
      }//end while
      Arrays.sort(itemsets, 0, i);     //String itemsets[]을 소팅한다.(i-1)???

      //String itemsets[]을 가지고, 실제 join작업을 실행한다.
      String pitemset, qitemset;

      for(int j=0; j < i; j++){
        for(int k=j+1; k < i; k++){

          pitemset = itemsets[j];
          qitemset = itemsets[k];

          index = pitemset.lastIndexOf('_');
          temp = pitemset.substring(0, index);
          index = qitemset.lastIndexOf('_');

          if( ! temp.equals( qitemset.substring(0, index) )){ //pitemset과 qitemset이 동일 칼럼이 아니면,

            temp = cmp_itemset;
            index = pitemset.lastIndexOf(',');
            temp += pitemset.substring(index+1) + ","; //pitemset의 마지막 item 추가

            index = qitemset.lastIndexOf(',');
            temp += qitemset.substring(index+1);  //qitemset의 마지막 item 추가

            candiset.addElement(temp);     //Vector 변수에 저장
          }
        }
      }//end for
      for(int j=0; j < i; j++){
        temp = itemsets[j];
        table_tmp.removeElement(temp);
      }
    }//end while

    return candiset;
  }

  private Vector join2() throws IndexOutOfBoundsException{
    //table의 itemset들을 join하여 임시 candidate itemset을 생성한다.
    Vector candiset = new Vector();
    String temp;
    String cmp_itemset = new String();
    int capacityIncrement = 0;   //String itemsets[]변수의 증가량
    //k-1개까지의 items이 동일한 itemset들을 sort하기 위해 모아둘 변수
    //동적으로 할당하는 거 생각해보기..!(system.arraycopy)
    int i;        //동일한  block에 속하는 itemset 수
    int index;    //k-1개까지의 items을 구분하기 위한 변수

    //table의 itemsets을 Vector형 변수로 복사한다.
    Vector table_tmp = new Vector();
    Enumeration itemsets_tmp = table.keys();
    while(itemsets_tmp.hasMoreElements()){
      temp = (String)itemsets_tmp.nextElement();
      table_tmp.addElement(temp);
    }

    //복사된 Vector형 변수 table_tmp를 가지고 하나씩 꺼내온다.
    itemsets_tmp = table_tmp.elements();
    while(table_tmp.isEmpty() == false){ //empty가 아닐 동안...
      String itemsets[] = new String[10];   //k-1까지의 item이 동일한 itemset을 모아둘 변수
      i = 0;
      capacityIncrement = 0;
      itemsets_tmp = table_tmp.elements();
      temp =(String)itemsets_tmp.nextElement();
      itemsets[i++] = temp;
      capacityIncrement = capacityIncrement + 1;

      index = temp.lastIndexOf(',');
      cmp_itemset = temp.substring(0, index + 1);   //','까지 포함된 cmp_itemset

      while(itemsets_tmp.hasMoreElements()){
        temp = (String)itemsets_tmp.nextElement();

        //itemset_tmp가 qitemset이 될 수 있는지 확인한다.
        if(temp.indexOf(cmp_itemset) == 0){   //k-1개까지의 items이 동일하다면,
          itemsets[i++] = temp;

          //capacityIncrement가 10이 되면 메모리를 늘려준다...!!
          capacityIncrement = capacityIncrement + 1;
          if(capacityIncrement == 10){
            capacityIncrement = 0;            //다시 0으로 초기화

            int oldCapacity = itemsets.length;
            String oldData[] = itemsets;
            int newCapacity = oldCapacity + 10;
            itemsets = new String[newCapacity];
            System.arraycopy(oldData, 0, itemsets, 0, oldCapacity);
          }
        }
      }//end while
      Arrays.sort(itemsets, 0, i);     //String itemsets[]을 소팅한다.(i-1)???

      //String itemsets[]을 가지고, 실제 join작업을 실행한다.
      String pitemset, qitemset;

      for(int j=0; j < i; j++){
        for(int k=j+1; k < i; k++){
          temp = cmp_itemset;

          pitemset = itemsets[j];
          qitemset = itemsets[k];

          index = pitemset.lastIndexOf(',');
          temp += pitemset.substring(index+1) + ","; //pitemset의 마지막 item 추가

          index = qitemset.lastIndexOf(',');
          temp += qitemset.substring(index+1);  //qitemset의 마지막 item 추가

          candiset.addElement(temp);     //Vector 변수에 저장
        }
      }//end for

      for(int j=0; j < i; j++){
        temp = itemsets[j];
        table_tmp.removeElement(temp);
      }
    }//end while
    return candiset;
  }

  private Vector prune(Vector candiset){
    //join으로 생성된 itemset들을 table의 itemset과 비교하여
    //table의 itemset의 subset이 아니면 pruning한다.
    //candiset : join으로 생성된 itemsets

	int num_candisets = candiset.size();

    for(int i = 0; i < num_candisets; i++){
      String itemset = (String)candiset.elementAt(i);
      Vector subsets = subset(itemset);

      int num_subsets = subsets.size();
      //num_subsets개의 subset 모두 table에 포함되어 있어야
      //candiset의 itemset을 pruning시키지 않는다.
      for(int j=0; j < num_subsets; j++){

        String subset = (String)subsets.elementAt(j);
        if(table.containsKey(subset) == false){ //subset이 table에 포함되지 않으면,
          candiset.remove(i);          //해당 candidate itemset을 삭제한다.
          i--;
          num_candisets--;
          break;
        }
      }//end for
    }//end outside for

    return candiset;
  }

  public static Vector subset(String itemset){
    //모든 가능한 k-1개의 items로 이루어진 subset을 구한다.
    Vector items = new Vector();
    Vector subsets = new Vector();
    String temp;

    StringTokenizer tokenized_itemset = new StringTokenizer(itemset, ",");
    while(tokenized_itemset.hasMoreTokens()){
      temp = tokenized_itemset.nextToken();
      items.addElement(temp);
    }
    int s = items.size();
    //item_tmp을 마지막꺼부터 제거해가며 subset을 생성한다.
    Vector items_tmp = (Vector)items.clone();
    for(int i = s-1; i > -1; i--){
      items_tmp.remove(i);
      
      temp = new String();
      int d = s -1;
      for(int j = 0; j < d; j++){
        temp += (String)items_tmp.elementAt(j) + ",";
      }
      temp = temp.substring(0, temp.length() - 1);

      if(temp.length() > 0){
        subsets.addElement(temp);   //하나의 subset을 추가한다.
      }
      items_tmp = (Vector)items.clone();
    }
    return subsets;
  }

  private int get_large_itemset(){
    //현재 itemset 중 large인 것만을 골라낸다.
    String itemset = new String();
    Integer sup;
    int sup_tmp;

    Enumeration itemsets = table.keys();
    while(itemsets.hasMoreElements()){
      itemset = (String)itemsets.nextElement();
      sup = (Integer)table.get(itemset);       //support
      sup_tmp = sup.intValue();

      if(sup_tmp < min_sup){
        table.remove(itemset);   //small itemset은 Hashtable에서 삭제한다.
      }
    }//end while

    return table.size();     //large itemset의 개수를 리턴한다.
  }

  private void write_itemset(String fname) throws IOException{
    //파일에 itemset과 support를 저장한다.

    FileOutputStream itemset_file = null;
    DataOutputStream itemset_data = null;

    itemset_file = new FileOutputStream(fname);
    itemset_data = new DataOutputStream(itemset_file);

    //temp를 StringBuffer로 바꿔보자....!
    //sup를 int형으로 변환않고 바로 파일에 쓴다.(append function)
    String temp = new String();    //line을 저장할 임시 변수
    String itemset = new String();
    Integer sup;
    int sup_tmp;

    Enumeration itemsets = table.keys();
    while(itemsets.hasMoreElements()){
      itemset = ((String)itemsets.nextElement());
      sup = (Integer)table.get(itemset);
      sup_tmp = sup.intValue();
      temp = itemset + ";" + new Integer(sup_tmp).toString() + "\r\n";
	  itemset_data.writeBytes(temp);
    }

    itemset_file.close();
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

}