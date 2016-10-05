package xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.xmutil;
import java.io.*;
import java.lang.*;
import java.util.StringTokenizer ;
import java.util.Vector;
import java.util.Enumeration;


public class CXMMetaDataManager{
/************************* CLASS START*************************/

        String                 _Path;                          //저장하거나 입력할 화일명을 포험한 path를 저장한다.
        String                 _Comma = ",";             //파일에 사용할 구분자
        int                      _Index ;                      //구분자로 표신된 value에서의 Index를 말한다.
        static Vector     _Vec ;      
        String[]              _Values;                   

      public CXMMetaDataManager(String fileName){
               _Path = fileName;
               File f = new File(_Path);
               try{
                    if (f.exists() == false) f.createNewFile();
               }catch(IOException e){
                    System.out.println(e.getMessage());
               }
       } 


/******************************************************************
* 환경파일에 값을 넣는 메쏘드로 input로는 key값과 value를 넣어 준다. 
* 성공하면 "success"를 output로 보내준다. 
******************************************************************/
        public void setProfile(String key, String value) //throws IOException
        {
                String dataKey = "["+key+"]"+value+"^";  //라인 스타일을 [key]value형식으로 함
                String text =  isIniFileWrite(key, dataKey, 0);

                String check= fileWriterCreate(text);
        }
        
        public void setProfile(String key, String[] values) //throws IOException
        {
                int  i  = values.length;
                String value = "";

                for(int arrRow = 0 ; arrRow < i ; arrRow++)
                {
                        if(arrRow == i - 1)  value = value  + values[arrRow];
                        else value = value  + values[arrRow] + _Comma;
                }

                String dataKey = "["+key+"]"+value+"^";  //라인 스타일을 [key]value형식으로 함
                String text =  isIniFileWrite(key, dataKey, 0);
                String check= fileWriterCreate(text);
        }

/******************************************************************
* 환경파일에 있는 key값을 넘겨준다. input으로는 key값을 넘겨준다.
* 성공하면 key값에 맞는 value값을 실패하면 
******************************************************************/
        public String getProfile(String key) //throws IOException//, MatchKeyNotFoundException
        {
                String text =  isIniFileWrite(key, "", 1);
                return text;
        }

        public String getProfile(String key, int index) //throws IOException//, MatchKeyNotFoundException
        {
                _Index = index;
                String text =  isIniFileWrite(key, "", 1);
                text = returnIndexOfString(text);
                return text;
        }

       public String[] getProfiles(String key) //throws IOException//, MatchKeyNotFoundException
       {
                try{                
                        String text =  isIniFileWrite(key, "", 1);
                        _Values = valueToStringArray(text);
                } catch(Exception e) {
                        System.out.println(e.getMessage());
                }
                return _Values;
        }
        
/******************************************************************
* 환경파일을 한 라인씩 읽어서 체크한다.
* 1. setProfileString일때는 전체를 체크하고 중복된  값이 들어오면 append시킨다.
* 2. getProfilestring일때는 key값과 일치는 하는 것이 있는 지 유무를체크하고 있으면 output으로 
*   그에 해당하는 value값을 리턴한다. 
******************************************************************/
        private String isIniFileWrite(String key, String dataLine, int chk) //throws IOException//, MatchKeyNotFoundException
        {
               try
               {
                       StringWriter sw = new StringWriter();
                       PrintWriter pw = new PrintWriter(sw);

                       FileReader fr = new FileReader(_Path);
                       BufferedReader br = new BufferedReader(fr);

                       String line, val;

                       int j = 0;
                       
                       while((line = br.readLine()) != null) {
                               val = checkEqualString(key, line);
                               if(val.compareTo("No") != 0 ) {  
                                       if(chk == 1) {
                                               line = val;
                                               break;
                                       }
                                       line = dataLine;
                                       j = 1;
                               }
                               try {
                                       pw.println(line);
                               }catch(Exception e) {
                                          System.out.println(e);
                               }
                       }//end while

                       if(j == 0 && chk != 1) {
                               try {
                                          pw.println(dataLine);        
                               }catch(Exception e) {
                                          System.out.println(e);
                               }
                       }
                       if(chk != 1) line = sw.toString();
                       return line;
                }//end try
                catch(IOException ioe) {
                        System.out.println(ioe.getMessage());
                        return "Error";
                }
        }

/******************************************************************
*key값이 같은 것이 있는 경우  value값을 변환한다. 
******************************************************************/
      private String  checkEqualString(String arg, String arg1) 
       {
                 try{
                         StringTokenizer st = new StringTokenizer(arg1, "]^");
                         String key = st.nextToken();
                         
                         key = key.substring(1);
                         String val = st.nextToken();

                         if(key.compareTo(arg) == 0) return val;
                         else return "No";
                 } catch(Exception e){
//                         System.out.print("checkEqualString : ");
                         return "Error";
                 }
       }

/******************************************************************
*
******************************************************************/
      private String  returnIndexOfString(String arg) //throws ColumnNotFitException
      {
                 try{
                         StringTokenizer st = new StringTokenizer(arg, _Comma);
                         String key = "";
                         for(int i = 0; i < _Index ; i++){
                                 key = st.nextToken();
                         }
                         return key;
                 } catch(Exception e){
//                         System.out.print("returnIndexOfString : ");
                         System.out.println(e.getMessage());
                         return "Error";
                 }
       }

/******************************************************************
*
******************************************************************/
      private String[]  valueToStringArray(String arg)  //throws IOException, Exception //, MatchKeyNotFoundException
      {        
              _Vec = new Vector();
                 try{
                         StringTokenizer st = new StringTokenizer(arg, _Comma);
                         String key = "";
                         
                         while((key = st.nextToken()) != null ){
                                   _Vec.addElement(key);
                         }              

                 } catch(Exception e){
                 }

                 try{
                         vectorToStringArray();
                 } catch(Exception e){
//                         System.out.print("valueToStringArray2 : ");
                         System.out.println(e.getMessage());
                 }                 
         
                 return _Values;
       }
/******************************************************************
*
******************************************************************/
      private void vectorToStringArray() //throws IOException, Exception
      {        
                 int rowCount = _Vec.size();
                 _Values  = new String[rowCount];

                 try{
                         Enumeration vEum = _Vec.elements();

                         String val = "";
                         int i = 0;
                         while(vEum.hasMoreElements())
                         {
                                 _Values[i] = (String)vEum.nextElement();
                                 i++;
                         }

                         _Vec.removeAllElements();             

                 } catch(Exception e){
//                         System.out.print("vectorToStringArray : ");
                         System.out.println(e.getMessage());
                 }
       }

/******************************************************************
*
******************************************************************/
       String  fileWriterCreate(String arg) //throws IOException
        {

                try{
                       FileWriter _FW = new FileWriter(_Path);
                       _FW.write(arg);
                       _FW.close();
                       return "Success";
                } catch(IOException Em_e) {
//                         System.out.print("fileWriterCreate : ");
                         System.out.println(Em_e.getMessage());
                         return "ERROR";
                }
       }
       
/************************* THE END*************************/
}