package xmminer.xmserver.xmgraph.xmmnode.xmmnodesequencerule;

import java.util.*;
import java.io.*;
import java.lang.*;
import java.awt.*;
import xmminer.xmlib.xmtable.*;
import xmminer.xmlib.xmcompute.*;
import xmminer.xmserver.xmgraph.xmmnode.xmmnodesequencerule.*;

public class LSitemset {
  private int num_items;
  Hashtable table = new Hashtable();

  static int min_sup;
  static int num_L1Sequence;    //총 litem(L1Sequence)수
  static int num_trans;  //customer-transaction 수

  private String dataFile;
  private String projectName;
  private String modelName;
  private String projectPath;

  private String transIDFld;  //기준이 되는 필드명(transaction ID)
  private String targetFld;   //item 필드명
  private String timeFld;     //time 필드명

  public LSitemset(int num, int support, String model, int transNumber, String project, String data, String path){
    projectName = project;
    projectPath = path;
    dataFile = data;
	  
    num_items = num;
    min_sup = support;
    modelName = model;

    num_trans = transNumber;
  }

  public LSitemset(int num){
    num_items = num;
  }

  public void setParam(String transIDFldName, String targetFldName, String timeFldName){
    transIDFld = transIDFldName;
    targetFld = targetFldName;
    timeFld = timeFldName;
  }

  public void readAllItem() throws IOException{
    FileInputStream allItem_file = new FileInputStream(projectPath + projectName + "/data/" + dataFile + ".udat");
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


  public void itemsDataSaver(){
    //"items.txt(dataFile.udat)를 생성한다.
    num_L1Sequence = 0;   //총 litem(L1Sequence)수 (한번만 불러지므로)

    String[] columnList = new String[1];
    columnList[0] = targetFld;

    CXMUniqueValueFileSaver itemsData = new CXMUniqueValueFileSaver();
    itemsData.setFileStatus(projectName, dataFile);
    itemsData.makeUniqueValueFileInColumnList(columnList, false);
    itemsData.close();
  }


  public int readItemList() throws IOException{
    //metaData file에서 총 transcation 수를 알아내고
    //data file을 읽어 모든 item들을 items.txt에 저장한다.
    int num_trans;
    CXMGetUniqueColumnValue transData = new CXMGetUniqueColumnValue();
    transData.setFileStatus(projectName, dataFile, transIDFld);
    num_trans = (transData.getUniqueValueTree()).size();
    transData.close(); 

    return num_trans;
  }

  public void putLitem(int num_litems){
    //Large 1 Sequence를 table에 넣는다...
    for(int i = 0; i < num_litems ; i++){
      String item = Integer.toString(i);    //Integer(i).toString();,Integer.toString(i);
      table.put(item, new Integer(0));
    }
  }

  private boolean checkContains(String trans, String itemset){
    //trans에 itemset이 포함되면 true를 리턴한다.
    //itemset의 각 item간의 순서없이 모든 item이 포함되 있는가만을 확인한다.
    String item = new String();
    boolean check = true;

    Vector imsi = new Vector();
    StringTokenizer tokenized_trans  = new StringTokenizer(trans,",");
    while(tokenized_trans.hasMoreTokens()){
      item = tokenized_trans.nextToken();
      imsi.addElement(item);
    }

    StringTokenizer tokenizedItemset = new StringTokenizer(itemset, ",");
    while(tokenizedItemset.hasMoreTokens()){
      item = tokenizedItemset.nextToken();

      //모든 item이 trans에 substring으로 포함되어 있는지 확인한다...
      if(imsi.contains(item) == false){
      //if(trans.indexOf(item) == -1){     //포함되어 있지 않으면...
        check = false;
        break;
      }
    }//end while

    return check;
  }

  private Hashtable addHashtable(Hashtable child, Hashtable parent){
    //child의 item을 parent에 추가한다.

    String itemset = new String();
    Integer support;

    Enumeration keySet = child.keys();

    while(keySet.hasMoreElements()){
      itemset = (String)keySet.nextElement();
      support = (Integer)child.get(itemset);
      parent.put(itemset, support);
    }

    return parent;
  }

  public int genLitemset(Hashtable L1sequence) throws IOException{
    //large 1-sequence(ex)40,70)를 구하기위해 호출되는 함수
    int num_litems;

    getSupport();
    num_litems = getLargeItemset();     //small itemset은 table에서 삭제한다.

    //이번 pass에서 구해진 large 1-sequence를 Hashtable L1sequence에 추가한다.
    addHashtable(table, L1sequence);

    return num_litems;  //구해진 L1Sequence 수를 리턴
  }

  public int genLitemset2() throws IOException{
    //large 1-sequence로 transform&mapping된 nctrans.txt파일을 가지고
    //large k-sequence(k>=2)를 구하기위해 호출되는 함수
    int num_litems;

    getSupport2();
    num_litems = getLargeItemset();      //small itemset은 table에서 삭제한다.

    return num_litems;
  }

  private void getSupport() throws IOException{
    //transaction을 읽어 Ct를 찾고, Hashtable의 해당 support를 증가시킨다.
    //large 1-sequence를 찾기위해 호출되는 함수
    String itemset, trans;
    Vector cusSequence, containedItemset;
    String transLine;

    CXMTableReader transData2 = new CXMTableReader();  //transaction data file
    transData2.setFileStatus(projectName, modelName + "_seq_trans");

    for(int t = 0; t < num_trans; t++){  //transaction 수
      cusSequence = new Vector();

      //cxmtable에서 하나의 customer-transaction을 읽어 transaction Vector에 저장한다.
      transLine = transData2.getString(t + 1);  //ex)"Juice;Cider,Water;Beer,Coke"
      StringTokenizer tokened_trans = new StringTokenizer(transLine, ";");
      while(tokened_trans.hasMoreTokens()){
        trans = tokened_trans.nextToken();
        cusSequence.add(trans);
      }

      containedItemset = containedItemsets(cusSequence);  //a customer transaction
      int numCt = containedItemset.size();
      for(int i=0; i < numCt; i++){
        itemset = (String)containedItemset.elementAt(i);
        if(table.containsKey(itemset)){   //table에 itemset이 저장되어 있으면
          Integer sup = (Integer)table.get(itemset);
          int sup_tmp = sup.intValue();
          sup_tmp++;                    //해당 item의 support를 증가시킨다.
          sup = new Integer(sup_tmp);
          table.put(itemset, sup);
        }//end if
      }//end for
      containedItemset.clear();

    }//end for

    transData2.close();
  }

  public void getSupport2() throws IOException{
    //nctrans.txt파일에서 transaction을 읽어 Ct를 찾고, Hashtable의 해당 support를 증가시킨다.
    //large k-sequence(k>=2)를 찾기위해 호출되는 함수(nctrans.txt)
    FileInputStream db_file = new FileInputStream(projectPath + projectName + "/" + modelName + "_nctrans.txt");
	BufferedReader db_data = new BufferedReader(new InputStreamReader(db_file));

    String line = new String();

    // num_items가 2이상일 때
    Vector containedItemset; // = new Vector();
    String itemset = new String();

    while((line = db_data.readLine()) != null){   //c:\test\nctrans.txt
      containedItemset = containedItemsets2(line);    //Ct를 구한다.
      int numCt = containedItemset.size();

      for(int i=0; i < numCt; i++){
        itemset = (String)containedItemset.elementAt(i);
        if(table.containsKey(itemset)){   //table에 itemset이 저장되어 있으면
          Integer sup = (Integer)table.get(itemset);
          int sup_tmp = sup.intValue();
          sup_tmp++;                    //해당 item의 support를 증가시킨다.
          sup = new Integer(sup_tmp);
          table.put(itemset, sup);
        }//end if
      }//end for
      containedItemset.clear();
    }//end while

    db_file.close();
  }

  private Vector containedItemsets(Vector cusSequence){
    //table의 itemset들 중 해당 cusSequence의 subset인 것을 찾아 Vector에 저장한다.
    String trans = new String();
    String item = new String();
    String itemset = new String();

    Vector containedItemset = new Vector();

    int size = cusSequence.size();
    for(int i = 0; i < size; i++){
      trans = (String)cusSequence.elementAt(i);
      Enumeration itemsets = table.keys();

      outsideLoop:   //---------------------outsideLoop:
      while(itemsets.hasMoreElements()){
        itemset = (String)itemsets.nextElement();

        StringTokenizer tokenizedItemset = new StringTokenizer(itemset, ",");
        while(tokenizedItemset.hasMoreTokens()){
          item = tokenizedItemset.nextToken();

          //모든 item이 cusSequence에 substring으로 포함되어 있는지 확인한다...
          if( checkContains(trans, item) == false){
            continue outsideLoop;   //포함되지 않으면 다음 itemset으로 넘어간다.
          }
        }//end while
        //item이 trans에 포함되어 있으면 이미 저장되어 있는지 확인한다.
        if(containedItemset.contains(itemset) == false){
          containedItemset.addElement(itemset);
        }
      }//end outside while
    }

    return containedItemset;
  }

  private Vector containedItemsets2(String cusSequence){
    //table의 itemset들 중 해당 cusSequence의 subset(Ct)인 것을 찾아 Vector에 저장한다.
    //large k-sequence(k>=2)를 찾기위해 호출되는 함수
    String trans = new String();
    String item = new String();
    String itemset = new String();

    boolean itemCheck = false;    //item을 check했나?
    boolean itemExist = false;    //item이 trans에 존재하나?

    Vector containedItemset = new Vector();

    Enumeration itemsets = table.keys();
    while(itemsets.hasMoreElements()){
      //각 itemset을 test하기 위해 맨처음 transaction으로 이동한다.
      //itemsets에는 ';'가 없다.
      StringTokenizer tokenizedCusSequence = new StringTokenizer(cusSequence, ";");

      itemset = (String)itemsets.nextElement();
      StringTokenizer tokenizedItemset = new StringTokenizer(itemset, ",");

      outsideLoop:   //---------------------outsideLoop:
      while(tokenizedItemset.hasMoreTokens()){
        item = tokenizedItemset.nextToken();
        itemCheck = false;

        while(tokenizedCusSequence.hasMoreTokens()){
          trans = tokenizedCusSequence.nextToken();
          //item이 cusSequence에 포함되어 있지 않으면,
          //next trans에 현재 item이 포함되어 있는지 확인한다..
          if( checkContains(trans, item) == false){
            itemExist = false;
            itemCheck = true;
          }
          //item이 cusSequence에 포함되어 있으면,
          //next item이 next trans에 포함되어 있는지 확인한다..
          else{
            itemExist = true;
            itemCheck = true;
            continue outsideLoop;
          }
        }//end while(trans)
      }//end while(item)

      //마지막 item까지 포함되어 있으면 저장한다.
      if(itemExist && itemCheck){
        containedItemset.addElement(itemset);
        itemExist = false;      //초기화
      }
    }//end while(itemset)

    return containedItemset;
  }


  public int aprioriGen(LSitemset Candiset){
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
      Vector candiset = join();
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

  public int aprioriGen2(LSitemset Candiset){
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

          //순서바꾼 candidate sequence도 저장한다.
          temp = qitemset + "," + pitemset;
          Candiset.table.put(temp, new Integer(0));
        }
      }//end for
    }else{     // num_items가 2이상일 때
      Vector candiset = join();
      candiset = prune(candiset);

      //Litemset형 candidate itemset에 저장한다.
      int num_candisets = candiset.size();
      for(int i=0; i < num_candisets; i++){
        temp = (String)candiset.elementAt(i);
        Candiset.table.put(temp, new Integer(0));
      }
      //candiset.removeAllElements();
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
    int i;        //k-1개까지의 items이 동일한 itemset들이 몇개인지 체크할 변수
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

      subsets.addElement(temp);   //하나의 subset을 추가한다.
      items_tmp = (Vector)items.clone();
    }
    return subsets;
  }

  public int getLargeItemset(){
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


  public static int getNumItem(String itemset){
    //몇개의 item으로 이루어졌는지 그 수를 리턴한다.
    StringTokenizer tokenized_line = new StringTokenizer(itemset, ",");
    int num = tokenized_line.countTokens();

    return num;
  }


  private void writeItemset_imsi(String fname) throws IOException{
    //파일에 mapping_number와 itemset과 support를 저장한다.
    //getL1Sequence()의 pass만큼 호출되어 L1set.txt C1set.txt 등을를 저장할때 쓰인다.
    RandomAccessFile litemFile = new RandomAccessFile(fname, "rw");
    long fp;

    fp = litemFile.length();
    litemFile.seek(fp);       //file의 끝으로 이동

    String temp = new String();    //line을 저장할 임시 변수
    String itemset = new String();
    Integer sup;
    int sup_tmp;

    Enumeration itemsets = table.keys();
    while(itemsets.hasMoreElements()){
      itemset = ((String)itemsets.nextElement());
      sup = (Integer)table.get(itemset);
      sup_tmp = sup.intValue();

      temp = sup_tmp + ";(" + itemset + ")" +  "\r\n";
	  litemFile.writeBytes(temp);
    }

    litemFile.close();
  }


  public void writeItemset(String fname) throws IOException{
    //파일에 mapping_number와 itemset과 support를 저장한다.
    //getL1Sequence()의 pass만큼 호출되어 LS1set.txt를 저장할때만 쓰인다.
    RandomAccessFile litemFile = new RandomAccessFile(fname, "rw");
    long fp;

    fp = litemFile.length();
    litemFile.seek(fp);       //file의 끝으로 이동

    String temp = new String();    //line을 저장할 임시 변수
    String itemset = new String();
    Integer sup;
    int sup_tmp;

    Enumeration itemsets = table.keys();
    while(itemsets.hasMoreElements()){
      itemset = ((String)itemsets.nextElement());
      sup = (Integer)table.get(itemset);
      sup_tmp = sup.intValue();

      //생성된 Large 1-sequence를 Vector L1sequence에 저장한다.
      temp = num_L1Sequence + ";" + sup_tmp + ";(" + itemset + ")" +  "\r\n";
      num_L1Sequence++;     //mapping number를 증가시킨다.
      litemFile.writeBytes(temp);
    }
    
    litemFile.close();
  }


  public void writeItemset2(String fname) throws IOException{
    //파일에 itemset과 support를 저장한다.
    RandomAccessFile litemFile = new RandomAccessFile(fname, "rw");
    long fp;

    fp = litemFile.length();
    litemFile.seek(fp);       //file의 끝으로 이동

    String temp = new String();    //line을 저장할 임시 변수
    String itemset = new String();
    Integer sup;
    int sup_tmp;

    Enumeration itemsets = table.keys();
    while(itemsets.hasMoreElements()){
      itemset = ((String)itemsets.nextElement());
      sup = (Integer)table.get(itemset);
      sup_tmp = sup.intValue();

      temp = itemset + ";" + sup_tmp + "\r\n";
	    litemFile.writeBytes(temp);
    }
   
    litemFile.close();
  }

}