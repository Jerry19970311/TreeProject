import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by XZL on 2017/4/27.
 */
public class MeanElement {
    private int index;
    private String word;
    private int father;
    private LinkedList<Word> declaredWord;
    public MeanElement(int index,String word,int father){
        this.index=index;
        this.word=word;
        this.father=father;
        this.declaredWord=new LinkedList<Word>();
    }

    public int getFather() {
        return father;
    }

    public int getIndex() {
        return index;
    }

    public String getWord() {
        return word;
    }

    public void addDW(Word w){
        this.declaredWord.add(w);
    }

    public LinkedList<Word> getDeclaredWord() {
        return declaredWord;
    }
    @Override
    public String toString() {
        //String str="\n序号："+this.getIndex()+"\n内容："+this.getWord()+"\n父意元序号："+this.father+"\n\n";
        String str=this.getWord();
        return str;
    }
}
