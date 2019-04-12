import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by XZL on 2017/4/20.
 */
public class TreeProcess {
    public static ArrayList<MeanElement> buildMeanElementTable(String path){
        FileReader fr=null;
        try {
            fr=new FileReader(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader br=new BufferedReader(fr);
        ArrayList<MeanElement> a=new ArrayList<>();
        String s=null;
        do{
            try {
                s=br.readLine();
                //System.out.println(s);
                if(s!=null){
                    String[] st=s.split(" ");
                    int index=0;
                    String w=null;
                    int f=0;
                    for(int i=0,k=0;i<3;k++){
                        if(k>=st.length){
                            break;
                        }
                        if(st[k].length()!=0){
                            //System.out.println("k="+k+"----------------------"+st[k]);
                            switch (i){
                                case 0:index=Integer.parseInt(st[k]);break;
                                case 1:w=st[k];break;
                                case 2:
                                    f=Integer.parseInt(st[k]);
                                    a.add(new MeanElement(index,w,f));
                                    break;
                                default:
                            }
                            i++;
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }while (s!=null);
        return a;
    }
    public static ArrayList<TreeNode<MeanElement>> buildMeanElementTree(ArrayList meanElementTable){
        int length=meanElementTable.size();
        ArrayList<TreeNode<MeanElement>> a=new ArrayList<TreeNode<MeanElement>>();
        ArrayList<TreeNode<MeanElement>> roots=new ArrayList<TreeNode<MeanElement>>();
        for(int i=0;i<length;i++){
            MeanElement m=(MeanElement)meanElementTable.get(i);
            TreeNode<MeanElement> n;
            if(m.getIndex()!=m.getFather()) {
                int fa = m.getFather();
                //System.out.println(i+" "+fa+"***************************\n"+m);
                TreeNode<MeanElement> temp = a.get(fa);
                n = new TreeNode<MeanElement>(temp, m);
                temp.addNext(n);
                a.set(fa, temp);
            }else {
               // System.out.println(i+"---------------------------\n"+m);
                n=new TreeNode<>(m);
                roots.add(n);
            }
            a.add(n);
        }
        //System.out.println(a);
        /*
        for(int i=0;i<length;i++){
            System.out.println("\n\n"+a.get(i).getData().getIndex()+"\n"+a.get(i).getData().getWord()+"\n"+a.get(i).getData().getFather()+"\n--------------------------\n");
        }*/
        //System.out.println(a.get(1614));
        return roots;
        /*FileReader fr=null;
        try {
            fr=new FileReader(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader br=new BufferedReader(fr);
        ArrayList<TreeNode<String>> a=new ArrayList<TreeNode<String>>();
        do{
            try {
                s=br.readLine();
                if(s!=null){
                    String[] st=s.split(" ");
                    int index = 0;
                    //System.out.println(st.length);
                    for(int i=0,k=0;i<3;k++){
                        if(st[k].length()!=0){
                            //System.out.println("k="+k+"----------------------"+st[k]);
                            switch (i){
                                case 0:index=Integer.parseInt(st[k]);break;
                                case 1:
                                    a.add(new TreeNode<String>(st[k]));
                                    break;
                                case 2:
                                    TreeNode<String> t=a.get(Integer.parseInt(st[k]));
                                    t.addNext(a.get(index));
                                    a.set(Integer.parseInt(st[k]),t);
                                    break;
                                default:
                            }
                            i++;
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }while (s!=null);
        System.out.println(a.size()+"\n"+a);
        return a.get(0);*/
    }
    public static TreeNode<MeanElement> search(String word,ArrayList<TreeNode<MeanElement>> roots){
        //System.out.println("1\t"+word+"\n2\t"+roots);
        LinkedBlockingQueue<TreeNode<MeanElement>> path=new LinkedBlockingQueue<TreeNode<MeanElement>>();
        int lengths=roots.size();
        TreeNode<MeanElement> root;
        for(int j=0;j<lengths;j++) {
            root=roots.get(j);
            path.offer(root);
            while (!path.isEmpty()) {
                TreeNode<MeanElement> node = path.poll();
                if (node.getData().getWord().indexOf(word) != -1) {
                    return node;
                }
                LinkedList<TreeNode<MeanElement>> children = node.getNext();
                int length = children.size();
                for (int i = 0; i < length; i++) {
                    path.offer(children.get(i));
                }
            }
        }
        //System.out.println("222222222222222222222222222222222222222222222222");
        return null;
    }
    public static double countSimilarly(String word1,String word2,ArrayList<TreeNode<MeanElement>> roots){
        Stack<TreeNode<MeanElement>> path1=new Stack<TreeNode<MeanElement>>();
        Stack<TreeNode<MeanElement>> path2=new Stack<TreeNode<MeanElement>>();
        TreeNode<MeanElement> w1=search(word1,roots);
        TreeNode<MeanElement> w2=search(word2,roots);
        double result=0;
        if((w1==null)||(w2==null)){
            return 0;
        }
        while (w1.getFather()!=null){
            path1.push(w1);
            w1=w1.getFather();
        }
        while (w2.getFather()!=null){
            path2.push(w2);
            w2=w2.getFather();
        }
        if(path1.peek()!=path2.peek()){
            return 0;
        }
        while (path1.peek()==path2.peek()){
            path1.pop();
            path2.pop();
        }
        result=(double)path1.size()+(double)path2.size();
        result=((double)1)/result;
        return result;
    }
}
