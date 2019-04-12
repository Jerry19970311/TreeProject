import java.io.*;
import java.util.*;

/**
 * Created by XZL on 2017/5/7.
 */
public class GraphProcess{
    public static ArrayList<Word> buildWordTable(String filePath,ArrayList<TreeNode<MeanElement>> root){
        //对文件的操作，详见前面实验。
        FileReader fr=null;
        try {
            fr=new FileReader(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader br=new BufferedReader(fr);
        //构建词表。
        ArrayList<Word> words=new ArrayList<Word>();
        //声明存放文件时每行读取的内容。
        String s=null;
        //定义存放单词序号的整形变量。
        int index=0;
        do{
            try {
                //读取行。
                s=br.readLine();
                //System.out.println(s);
                //定义存放词类和词的变量。
                String kind = null;
                String data=null;
                //限定条件：所读取的行不为空。
                if(s!=null){
                    //处理可能干扰分词的字符：（42~43行，74~79行）
                    //将不必要而又会干扰分词的字符去除，由于整个文件所涉及的符号过多，而单行中不可能存在这么多的无关字符，如果全部列举出来会严重影响效率。
                    //因此这些无关字符可分为两种情况：1，在义原前面；2，在义原后面。
                    //第1种情况所涉及的字符过多，但是在接下来的过程中相对容易被处理，因此这里不予理睬。（详见74~79行）
                    //第2种情况发现之后被处理的难度要高于前者，但是涉及字符无非两种：右小括号、右中括号，因此可以在此处提前处理。
                    s=s.replace(")","");
                    s=s.replace("}","");
                    //除去制表符。
                    s=s.replace("\t","");
                    //将每一行按空格进行切分，会产生很多的空字符串。
                    String str[]=s.split(" ");
                    //整形变量i：用于遍历前一步所得到的字符串数组。
                    //整形变量j：用于对每一行中出现的3种数据（词、词性、定义词的义原）对应情况进行区分。
                    for(int i=0,j=0;j<3;i++){
                        //如果i在读取过程中意外发生数组越界则强制终止循环。
                        if(i>=str.length){
                            break;
                        }
                        //对字符串数组进行判断，长度为0则为空字符串，不予处理。
                        //经过处理和切分后形成的字符串数组内必然只有3个非空的字符串，先后顺序为：词、词性、定义词的义原。
                        //因此我们利用j变量进行区分：
                        //j=0时，说明读到词，将该词存入相应变量；（64行）
                        //j=1时，说明读到词性，存入相应变量；（65行）
                        //j=2时，说明读到义原，进行处理。（66~102行）
                        if(str[i].length()!=0){
                            //System.out.println(index+"\t"+data+"\t"+kind);
                            switch (j){
                                case 0:data=str[i];break;
                                case 1:kind=str[i];break;
                                case 2:
                                    //将之前存放的词、词性、定义词的义原用来创建一个词类。
                                    Word dal=new Word(index,kind,data);
                                    //对表示义原的字符串进行切割。
                                    String mses[]=str[i].split(",");
                                    //处理义原的字符串数组。
                                    for(int k=0;k<mses.length;k++){
                                        //System.out.println((int)mses[k].charAt(0));
                                        //对第1种干扰字符情况的处理：如果这一字符串开头不是大小写字母，说明存在干扰字符，将其除去.
                                        if((mses[k].charAt(0)<65)||(mses[k].charAt(0)>90&&mses[k].charAt(0)<97)||(mses[k].charAt(0)>122)){
                                            //System.out.println("9wejf90w4fjk90oqw4efqw4fe");
                                            mses[k]=mses[k].substring(1);
                                            //System.out.println(mses[k]);
                                        }
                                        //System.out.println(mses[k]);
                                        //这里进行的是对各个义原添加其所对定义的词的工作，实现词和对应的义原间相联系。
                                        MeanElement me=null;
                                        String beSearch=mses[k];
                                        TreeNode<MeanElement> ms=TreeProcess.search(beSearch,root);
                                        //ms是存储搜索结果的义原的变量，如果为空，说明原来的义原树根本没有这一相关义原，也就无需在义原表上添加了。
                                        if(ms==null){
                                            continue;
                                        }
                                        //System.out.println("-----------------------------\n"+ms.getData().getWord()+"\n-------------------------------");
                                        //如果ms非空，则将该词存入这一义原所定义词的词表中。
                                        me=ms.getData();
                                        me.addDW(dal);
                                        dal.addMS(me);
                                        ms.setData(me);
                                    }
                                    //将处理好的词类对象放进词表。
                                    words.add(dal);
                                    //将序号加1，准备处理下一个词。
                                    index++;
                                    break;
                                default:
                            }
                            //每次处理后j变量加1，等待下次处理。
                            j++;
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }while (s!=null);
        return words;
    }
    public static ABGraph<MeanElement,Word> buildGraph(ArrayList<MeanElement> mees,ArrayList<Word> words){
        ABGraph<MeanElement,Word> result=new ABGraph<MeanElement,Word>(mees,words);
        int aLength=mees.size();
        for(int i=0;i<aLength;i++){
            MeanElement test=mees.get(i);
            LinkedList<Word> word=test.getDeclaredWord();
            int bLength=word.size();
            for(int j=0;j<bLength;j++){
                int wordIndex=word.get(j).getIndex();
                if(result.weight(i,wordIndex)!=1){
                    result.insertEdge(i,wordIndex,1);
                }
            }
        }
        return result;
    }
    public static ABGraph2<Word,MeanElement> buildGraph2(ArrayList<Word> words,ArrayList<MeanElement> mses){
        //ABGraph2<Word,MeanElement> result=new ABGraph2<Word,MeanElement>(words);
        ArrayList<LinkedList<MeanElement>> result1=new ArrayList<LinkedList<MeanElement>>();
        int length=words.size();
        for(int i=0;i<length;i++){
            LinkedList<MeanElement> temp=words.get(i).getMsList();
            result1.add(temp);
        }
        ABGraph2<Word,MeanElement> result=new ABGraph2<Word,MeanElement>(words,result1);
        return result;
    }
    public static ArrayList<Word> getSameWords(String word,ABGraph2<Word,MeanElement> g){
        ArrayList<Word> words=g.getsVertexList();
        ArrayList<Word> result=new ArrayList<Word>();
        int length=words.size();
        for(int i=0;i<length;i++){
            if(words.get(i).getData().equalsIgnoreCase(word)){
                result.add(words.get(i));
            }
        }
        return result;
    }
    public static ArrayList<Word> getSameWord(String word,ABGraph2<Word,MeanElement> g){
        ArrayList<Word> result=new ArrayList<Word>();
        ArrayList<Word> wordlist=g.getsVertexList();
        int length=wordlist.size();
        for(int i=0;i<length;i++){
            if(word.equals(wordlist.get(i).getData())==true){
                result.add(wordlist.get(i));
            }
        }
        return result;
    }
    //利用待处理单词及其所在的图构建以json数组形式表达的关系。
    public static String getLinked(Word word,ABGraph2<Word,MeanElement> g){
        //由于题目要求，我们必须在意词和义原在即将构建的图中的序号，并且词不可以重复（义原没有重复的可能），因此选用有序而不重复的数据结构进行存放构图的词（LinkedHashSet）。
        LinkedHashSet<Word> isRead=new LinkedHashSet<Word>();
        //存放连接多个义原的词，由于这些词与义原的关系是一对多，而别的词是多对一，因此分开处理。
        LinkedHashSet<Word> isRelated=new LinkedHashSet<Word>();
        //讲待处理的词第一个插入第一个链式哈希集。
        isRead.add(word);
        //存放哈希表，
        LinkedList<MeanElement> neigh=g.sGetNeighborhoods(word);
        int nlength=neigh.size();
        int[] ad=new int[nlength];
        for(int i=0;i<nlength;i++){
            ad[i]=isRead.size();
            LinkedList<Word> read=new LinkedList<Word>(neigh.get(i).getDeclaredWord());
            read.removeAll(isRelated);
            System.out.println(read);
            int l1=read.size();
            for(int j=0;j<l1;j++){
                Word w=read.get(j);
                int k=i+1;
                for(;k<nlength;k++){
                    if(w.getMsList().contains(neigh.get(k))){
                        isRelated.add(w);
                        break;
                    }
                }
                if(k!=nlength){
                    continue;
                }
                isRead.add(w);
            }
            ad[i]=isRead.size()-ad[i];
        }
        isRelated.remove(word);
        System.out.println(isRelated+"\n"+isRead+"\n"+isRead.size());
        int ilength=isRead.size();
        int length=nlength+ilength;
        String[] points=new String[length];
        Iterator<Word> iterator=isRead.iterator();
        points[0]=iterator.next().getData();
        for(int i=0;i<nlength;i++){
            points[i+1]=neigh.get(i).getWord();
        }
        for(int i=1;iterator.hasNext();i++){
            points[nlength+i]=iterator.next().getData();
        }
        /*for(int i=0;i<points.length;i++){
            System.out.println(points[i]);
        }*/
        String nodestart="{  \"nodes\":[\n";
        String nodes="{\"name\":\""+points[0]+"\",\"group\":0}";
        for(int i=0;i<nlength;i++){
            nodes=nodes+",\n{\"name\":\""+points[i+1]+"\",\"group\":1}";
        }
        for(int i=0;i<nlength;i++){
            for(int j=0;j<ad[i]&&(nlength+1+j<points.length);j++){
                nodes=nodes+",\n{\"name\":\""+points[nlength+1+j]+"\",\"group\":"+Integer.toString(2+i)+"}";
            }
        }
        int diedai=0;
        for (Iterator<Word> i=isRelated.iterator();i.hasNext();){
            nodes=nodes+",\n{\"name\":\""+i.next().getData()+"\",\"group\":"+Integer.toString(2+nlength+diedai)+"}";
            diedai++;
        }
        diedai=0;
        String nodend="]";
        String result1=nodestart+nodes+nodend;
        //System.out.println(result1);
        String linkstart="\"links\":[\n";
        String links="";
        for(int i=0;i<nlength;i++){
            if(i!=0){
                links=links+",\n";
            }
            links=links+"{\"source\":0,\"target\":"+Integer.toString(i+1)+",\"value\":1}";
        }
        for(int i=0,havebeen=0;i<nlength;i++){
            for(int j=0;j<ad[i];j++){
                links=links+",\n{\"source\":"+Integer.toString(i+1)+",\"target\":"+Integer.toString(havebeen+nlength+1+j)+",\"value\":1}";
            }
            havebeen=havebeen+ad[i];
        }
        Iterator<Word> dd=isRelated.iterator();
        int il=isRead.size();
        for(int i=0;i<isRelated.size();i++){
            LinkedList<MeanElement> mses2=new LinkedList<MeanElement>(dd.next().getMsList());
            mses2.retainAll(word.getMsList());
            int mlength=mses2.size();
            for(int j=0;j<mlength;j++){
                MeanElement search=mses2.get(j);
                for(int k=1;k<nlength+1;k++){
                    if(search.getWord()==points[k]){
                        links=links+",\n{\"source\":"+Integer.toString(il+nlength+i)+",\"target\":"+Integer.toString(k)+",\"value\":1}";
                    }
                }
            }
        }
        String linkend="\n]}";
        String result2=linkstart+links+linkend;
        //System.out.println(points.length+"-------------------------------\n"+result2);
        String result=result1+"\n,"+result2;
        System.out.println(result);
        return result;
    }
    public static void writeStringContentToFile(String filePath,String fileContent){
        File f=new File(filePath);
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileWriter fw;
        try{
            fw=new FileWriter(f);
            fw.write(fileContent);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*public static ArrayList<String> getLinked(String word,ABGraph<MeanElement,Word> g){
        ArrayList<String> result=new ArrayList<String>();
        if(word.indexOf('|')<0){
            ArrayList<Word> words=g.getvVertexList();
            int length=words.size();
            for (int i=0;i<length;i++){
                if(words.get(i).getData().equalsIgnoreCase(word)){
                    ArrayList<MeanElement> temp=new ArrayList<MeanElement>();
                }
            }
        }
        return result;
    }*/
}
