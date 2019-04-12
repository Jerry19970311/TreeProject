import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by XZL on 2017/5/22.
 */
public class Test05221 {
    public static void main(String[] args) throws IOException {
        FileReader fr=null;
        try {
            fr=new FileReader("E:\\731606673\\FileRecv\\glossary2.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader br=new BufferedReader(fr);
        HashSet<String> words=new HashSet<String>();
        String s=null;
        HashMap<Integer,HashSet<String>> wordtable= new HashMap<>();
        int maxlength=0;
        do{
            s=br.readLine();
            //String news="查尔戈加戈格曼乔加戈格舍巴纳甘加马湖\tN";
            //news=news.replaceAll("\t"," ");
            //System.out.println(news);
            /*方法1：简单的HashSet构建，暂时被冻结。*/
            /*
            if(s!=null){
                s=s.replaceAll("\t"," ");
                String word=s.split(" ")[0];
                if(word.length()>1) {
                    words.add(word);
                    if (maxlength < word.length()) {
                        maxlength = word.length();
                        System.out.println(word);
                    }
                }
            }
            */
            if(s!=null){
                s=s.replaceAll("\t"," ");
                //System.out.println(s);
                String word=s.split(" ")[0];
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
                //HashSet<String> news=wordtable.get(Integer.valueOf(word.length()));
                //news.add(word);
                //System.out.println(news);
            }
        }while (s!=null);
        Integer[] a=new Integer[wordtable.keySet().size()];
        a=wordtable.keySet().toArray(a);
        /*System.out.println(wordtable.keySet());
        Collection<HashSet<String>> result=wordtable.values();
        for(Iterator<HashSet<String>> iterator=result.iterator();iterator.hasNext();){
            System.out.println(iterator.next().size());
        }*/
        //System.out.println(wordtable);
        //System.out.println(maxlength);
        Scanner input=new Scanner(System.in);
        String test=input.next();
        //String test="去年奥运会期间，王宝强的离婚声明爆出妻子马蓉出轨经纪人宋喆的消息满世界飞扬，真是刷新了小编的三观！一时间马蓉和宋喆被推向了舆论的风口浪尖，一片倒的骂声铺天盖地冲着马蓉飞奔而来，祖宗十八代早已被骂个遍了。。。";
        StringBuffer sb=new StringBuffer();
        /*for(int i=a.length-1;i>=0;i--){
            HashSet<String> compare=wordtable.get(a[i]);
            System.out.println(compare);
        }*/
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
        /*int last=-1;
        for(int i=0;i<50;){
            System.out.println("sb="+sb);
            System.out.println("last="+last);
            int next=i+1;
            System.out.println("Bnext="+next);
            for(int k=1;k<maxlength;k++){
                String compare;
                if(i+k>=50){
                    compare=test.substring(i,49);
                    sb=sb.append(compare);
                    last=-2;
                    break;
                }
                compare=test.substring(i,i+k);
                if(words.contains(compare)){
                    System.out.println("yes");
                    if(i!=0&&last==-1){
                        System.out.println("afwnioeijqwoefjq9wioejfjqwoefij");
                        sb=sb.append(compare.substring(0,i));
                    }
                    if(last!=next&&last!=-1){
                        System.out.println("i啊我好的覅我就愤怒文翻译");
                        sb=sb.append(compare.substring(last,i));
                    }
                    sb=sb.append(compare+" ");
                    next=i+k;
                    last=next;
                    break;
                }
                System.out.println(compare);
            }
            System.out.println("Lnext="+next);
            if(last==-2){
                break;
            }
            i=next;
        }*/
        System.out.println(sb.toString());
    }
}
