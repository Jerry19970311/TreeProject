import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

/**
 * Created by XZL on 2017/6/3.
 */
public class Test05222 {
    public static void main(String[] args) throws IOException {
        FileReader fr=null;
        try {
            fr=new FileReader("E:\\Jenny2.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader br=new BufferedReader(fr);
        String s;
        HashMap<Integer,HashSet<String>> wordtable= new HashMap<>();
        do{
            s=br.readLine();
            if(s!=null&&s.contains("】")){
                String word=s.split("】")[0];
                word=word.replaceAll("【","");
                if(!wordtable.containsKey(word.length())){
                    //System.out.println("iqwehf9oqwejf9qwojef9qwejf9owqef");
                    HashSet<String> wordss=new HashSet<String>();
                    wordss.add(word);
                    //System.out.println(wordss);
                    wordtable.put(word.length(),wordss);
                }else{
                    //System.out.println(wordtable.get(word.length()));
                    wordtable.get(word.length()).add(word);
                }
            }
        }while(s!=null);
        Integer[] a=new Integer[wordtable.keySet().size()];
        a=wordtable.keySet().toArray(a);
        System.out.println(wordtable.keySet());
        for(int i=0;i<a.length;i++){
            System.out.println(wordtable.get(a[i]));
        }
        Scanner input=new Scanner(System.in);
        String test=input.next();
        StringBuffer sb=new StringBuffer();
        int tlength=test.length();
        for(int i=0;i<tlength;){
            int samelength=1;
            int samemax=1;
            String cword;
            for(int j=0;j<a.length;j++){
                //System.out.println("i="+i+";a[j]="+a[j]);
                try {
                    cword=test.substring(i,i+a[j]);
                }catch(StringIndexOutOfBoundsException e){
                    cword=test.substring(i);
                    /*sb.append(cword);
                    break;*/
                }
                //System.out.println(cword);
                if(wordtable.get(a[j]).contains(cword)){
                    samemax=a[j];
                }
                /*if(wordtable.get(a[j]).contains(cword)==false){
                    try{
                        samelength=a[j-1];
                        cword=cword.substring(0,cword.length()-1);
                        sb.append(cword+" ");
                    }catch (IndexOutOfBoundsException e){
                        System.out.println("hqwierfi9q3whr9q83r98rqw");
                        samelength=1;
                    }
                    finally {
                        break;
                    }
                }*/
            }
            cword=test.substring(i,i+samemax);
            sb.append(cword+" ");
            i=i+samemax;
        }
        System.out.println(sb.toString());
    }
}
