import java.util.LinkedList;
import java.util.List;

/**
 * Created by XZL on 2017/5/7.
 */
public class Word {
    private int index;//确定该词在词表中的序号。
    private String data;//存放词。
    private String kind;//存放词性。
    private LinkedList<MeanElement> msList;//存放定义该词的义原。
    //构造方法。
    public Word(int index,String kind,String data){
        this.index=index;
        this.kind=kind;
        this.data=data;
        this.msList=new LinkedList<MeanElement>();
    }
    public Word(int index,String kind, String data, List<MeanElement> list){
        this(index,kind,data);
        int length=list.size();
        for(int i=0;i<length;i++){
            this.msList.add(list.get(i));
        }
    }
    //添加所定义的义原。
    public void addMS(MeanElement ms){
        this.msList.add(ms);
    }
    //得到定义该词的所有义原。
    public LinkedList<MeanElement> getMsList() {
        return msList;
    }
    //返回该词的序号。
    public int getIndex() {
        return index;
    }
    //返回该词。
    public String getData() {
        return data;
    }
    //返回该词的词性。
    public String getKind() {
        return kind;
    }
    public boolean isRelated(Word other){
        LinkedList<MeanElement> test=new LinkedList<MeanElement>(other.getMsList());
        return test.removeAll(this.msList);
    }

    @Override
    public String toString() {
        String str="序号："+this.index+"\n单词："+this.data+"\n定义的义原："+this.msList+"\n\n";
        //String str=this.index+":"+this.data;
        return str;
    }
}
