package xmminer.xmserver.xmgraph.xmmnode.xmmnodesequencerule;

import java.util.*;          
import java.io.*;
import xmminer.xmlib.xmtable.*;
import xmminer.xmlib.xmcompute.*;

public class SeqPattern {
  private int numLitemsets;

  static int min_sup;
  private String dataFile;
  private String projectName;
  private String projectPath;

  static int num_trans;  //customer-transaction 수
  private String modelName;

  private String transIDFld;  //기준이 되는 필드명(transaction ID)
  private String targetFld;   //item 필드명
  private String timeFld;     //time 필드명

  static Vector LS1set;  //large 1-sequence의 mapping정보를 가지고 있는 Vector

  public SeqPattern(int num, int support, String model, int transNumber, String project, String data, String path){
    projectName = project;
    projectPath = path;

    min_sup = support;
    numLitemsets = num;

    dataFile = data;
    modelName = model;
    num_trans = transNumber;
  }

  public SeqPattern(){
  }

  private Hashtable getL1Sequence(String transIDFldName, String targetFldName, String timeFldName) throws IOException{
    //custom transaction File에서 Large 1-sequences을 구한다.
    Hashtable L1sequence = new Hashtable();

    int k = 1;
    int num_litems;            //구해진 large sequence의 개수
    LSitemset litemsetTmp = new LSitemset(k);

    //Apriori
    do{
        LSitemset litemset = new LSitemset(k, min_sup, modelName, num_trans, projectName, dataFile, projectPath);  //num_items, min_sup
        litemset.setParam(transIDFldName, targetFldName, timeFldName); //k가 증가할때마다 Litemset이 다시 만들어지므로..

        if(k == 1){
          litemset.itemsDataSaver();
          XMBMNodeSequenceRule.m_pbValue = 42;
          litemset.readAllItem();    //Candidate 1-Itemset을 구한다.
          XMBMNodeSequenceRule.m_pbValue = 44;
        }else{
          num_litems = litemsetTmp.aprioriGen(litemset);  //Candidate itemset을 생성한다.
          if(num_litems == 0) break;
        }

        num_litems = litemset.genLitemset(L1sequence);  //Hashtable

        //각 pass에서 생성된 Large 1-sequence를 Vector L1sequence에 저장한다.
        //=> genLitemset()안에서 writeItemset()이 수행..

        litemsetTmp = litemset;
        k++;
    }while(num_litems != 0);

    XMBMNodeSequenceRule.m_pbValue = 46;

    return L1sequence;
  }

  public Hashtable genL1Sequence(String transIDFldName, String targetFldName, String timeFldName) throws IOException{
    transIDFld = transIDFldName;
    targetFld = targetFldName;
    timeFld = timeFldName;

    Hashtable L1sequence = getL1Sequence(transIDFld, targetFld, timeFld);

    LS1set = new Vector();
    Hashtable map_L1sequence = new Hashtable();
    String itemset = new String();
    Integer sup;

    //large 1-sequence에 대한 mapping정보를 Vector LS1set에 넣는다.
    int i = 0;
    Enumeration keySet = L1sequence.keys();
    while(keySet.hasMoreElements()){
      itemset = (String)keySet.nextElement();
      sup = (Integer)L1sequence.get(itemset);

      //mapping된 수로 itemset을 대치하여 map_L1sequence Hashtable에 넣는다.
      Integer ii = new Integer(i);
      map_L1sequence.put(ii.toString(), sup);

      LS1set.add(i, itemset);
      i++;
    }
    //L1sequence => (Gin; 2) (Beer, Cider; 4) (Water; 2) (Cider; 5) ... => getL1Sequence()의 결과
    //LS1set => 0(Gin) 1(Cider) 2(Water) 3(Beer, Cider) ... => transform(), reTransform()할 때 필요
    //map_L1sequence => (0; 2) (3; 4) (2; 2) (1; 5) ... => rule을 생성할 때(confidence계산) 필요

    transform();  //멤버변수 Vector LS1set를 이용하여 nctrans.txt생성한다. //46-52

    return map_L1sequence;
  }


  private void writeItemset(Hashtable map_L1sequence) throws IOException{
    //파일에 mapping_number와 itemset과 support를 저장한다.
    //getL1Sequence()의 pass만큼 호출되어 LS1set.txt를 저장할때만 쓰인다.
    String fname = projectPath + projectName + "\\" + modelName + "_LS1set.txt";
    RandomAccessFile litemFile = new RandomAccessFile(fname, "rw");

    String temp = new String();    //line을 저장할 임시 변수
    String itemset = new String();
    Integer sup;
    int sup_tmp;

    //LS1set => 0(Gin) 1(Cider) 2(Water) 3(Beer, Cider) ...
    //map_L1sequence => (0; 2) (3; 4) (2; 2) (1; 5) ...
    int size = LS1set.size();
    for(int i = 0; i < size; i++){
      Integer ii = new Integer(i);
      sup = (Integer)map_L1sequence.get(ii.toString());
      sup_tmp = sup.intValue();
      itemset = (String)LS1set.elementAt(i);

      temp = i + ";" + sup_tmp + ";(" + itemset + ")" +  "\r\n";
      litemFile.writeBytes(temp);
    }

    litemFile.close();
  }

  public String reTransform(String project, String itemset, String model) throws IOException {
    //itemset을 실제 item명으로 재변환하여 넘겨준다.
    String result = new String();     //result : transformed_itemset
    String temp = new String();
    Integer i;

    StringTokenizer tokenizedItemset = new StringTokenizer(itemset, ",");
    while(tokenizedItemset.hasMoreTokens()){
      String item = tokenizedItemset.nextToken();

      i = new Integer(item);
      temp = (String)LS1set.elementAt(i.intValue());

      if(result.length() > 0){
        result += ';' + temp;
      }
      else{
        result = temp;
      }
    }//end while

    return result;
  }


  private void transform() throws IOException {
    //custom transaction File을 L1sequence으로 transform&mapping 한다.
    //nctrans.txt 파일 생성

    String newDBFile = projectPath + projectName + "\\" + modelName + "_nctrans.txt";
    RandomAccessFile newDb_File = new RandomAccessFile(newDBFile, "rw");

    String itemset, trans, transLine;
    String lineTmp = new String();

    int m = 1;

    CXMTableReader transData2 = new CXMTableReader();  //transaction data file
    transData2.setFileStatus(projectName, modelName + "_seq_trans");

    for(int t = 0; t < num_trans; t++){  //transaction 수
      //cxmtable에서 하나의 customer-transaction을 읽어 transaction Vector에 저장한다.
      transLine = transData2.getString(t + 1);  //ex)"Juice;Cider,Water;Beer,Coke"
      StringTokenizer tokened_trans = new StringTokenizer(transLine, ";");
      while(tokened_trans.hasMoreTokens()){
        trans = tokened_trans.nextToken();  //customer-transaction에서 하나의 transaction 분리

		Enumeration litemsets = LS1set.elements();
        while(litemsets.hasMoreElements()){
          itemset = (String)litemsets.nextElement();  //large 1-sequence를 하나씩 꺼낸다.

          //trans에 itemset이 포함되어 있으면...
          if(checkContains(trans, itemset) == true){
            lineTmp += LS1set.indexOf((String)itemset) + ",";   //mapping된 수를 넣는다.
          }
        }//end while    --transaction 하나 끝남...
        if(lineTmp.length() != 0){
          lineTmp = lineTmp.substring(0, lineTmp.length()-1) + ";";
        }
      }//end while    --customer sequence 하나 끝남...

	  if(lineTmp.length() != 0){
        lineTmp = lineTmp.substring(0, lineTmp.length()-1) + "\r\n";
        newDb_File.writeBytes(lineTmp);
        lineTmp = "";
      }

      if((t == 500*m) && (t <= 2500)){ //46-51
        XMBMNodeSequenceRule.m_pbValue += 1;
        m++;
      }//end if

    }//end for

	XMBMNodeSequenceRule.m_pbValue = 52;

    newDb_File.close();
    transData2.close();
  }


  public boolean checkContains(String trans, String itemset){
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
        check = false;
        break;
      }
    }//end while

    return check;
  }


  public boolean checkOrderContains(String maxSequen, String sequen){
    //maxSequen에 sequen이 포함되면 true를 리턴한다.
    //sequen의 각 item간의 순서를 고려하여 포함되어 있는가를 확인한다.
    String item = new String();
    boolean check = true;
    int index = -1;
    int index2;

    Vector imsi = new Vector();
    StringTokenizer tokenized_maxSequen  = new StringTokenizer(maxSequen, ",");
    while(tokenized_maxSequen.hasMoreTokens()){
	    item = tokenized_maxSequen.nextToken();
      imsi.addElement(item);
    }

    StringTokenizer tokenizedSequen = new StringTokenizer(sequen, ",");
    while(tokenizedSequen.hasMoreTokens()){
      item = tokenizedSequen.nextToken();

      //모든 item이 maxSequen에 substring으로 포함되어 있는지 확인한다...
      if(imsi.contains(item) == false){
        check = false;
        break;
      }
      else{ //포함되어 있으면...
        index2 = imsi.indexOf(item);
        if(index2 > index){
          index = index2;
        }else{  //순서가 맞지 않으면 break
          check = false;
          break;
        }
      }//end else
    }//end while

    return check;
  }

  public int getNumItems(String litemset){
    //litemset이 몇개의 item으로 이루어졌는지 개수를 리턴한다.
    StringTokenizer tokend_litem = new StringTokenizer(litemset, ",");
    int num = tokend_litem.countTokens();

    return num;
  }


  public Vector maximalSequence(int k, Hashtable largeHash) throws IOException{
    //Large k-sequence까지의 모든 sequence에서 maximal인 것만 찾아 파일에 저장한다.

    //출력할 파일..
    String ofname = projectPath + projectName + "\\" + modelName + "_maxLS.txt";
    RandomAccessFile litemFile = new RandomAccessFile(ofname, "rw");

    String sequen = new String();
    String line = new String();
    String maxSequen = new String();

    Vector maxSequence = new Vector();
    boolean maximal = false;
    int num;

    //large-sequence 하나씩 꺼내 구성 item수를 구해 largeHashClone에 넣는다.
    Hashtable largeHashClone = (Hashtable)largeHash.clone();
    Enumeration keySet = largeHashClone.keys();
    while(keySet.hasMoreElements()){
      sequen = (String)keySet.nextElement();
      largeHashClone.remove(sequen);

      num = getNumItems(sequen); //몇개의 item으로 이루어져있나
      largeHashClone.put(sequen, new Integer(num));
    }//end while

    int m = 1;

    for(int i = k; i > 1; i--){
      keySet = largeHashClone.keys();

      while(keySet.hasMoreElements()){
        sequen = (String)keySet.nextElement();
        Integer numInt = (Integer)largeHashClone.get(sequen);
        num = numInt.intValue();  //구성 item수

        if(num == i){
          if(num == k) maximal = true;
          else{
            //sequen가 maximal인지 확인한다.
            Enumeration maxSequens = maxSequence.elements();
            while(maxSequens.hasMoreElements()){
              maxSequen = (String)maxSequens.nextElement();  //maxSequen

              //sequen이 maximal sequence가 아니면,
              if(checkOrderContains(maxSequen, sequen) == true){
                maximal = false;
                break;
              }
            }//end while
          }//END ELSE
          largeHashClone.remove(sequen);
        }//end if

        if(maximal == true){
          maxSequence.addElement((String)sequen);

          //maximal sequence로 확인된 sequen의 support를 가져와서 저장한다.
          Integer support = (Integer)largeHash.get(sequen);
          line = sequen + ";" + support + "\r\n";
          litemFile.writeBytes(line);
        }

		maximal = false;    //초기화

      }//end while   다음 sequence로 넘어간다.


      if(m <= 5){  //68-73
	    XMBMNodeSequenceRule.m_pbValue += 1;
	  }else{   // KeepRunning 이 false
	    System.out.println("사용자에 의한 Association Rule 생성 작업 종료");
	    //return;
	  }
      m++;

    }//end for

	XMBMNodeSequenceRule.m_pbValue = 74;

    litemFile.close();

    return maxSequence;
  }

}