import java.util.*;

/**
 * Created by XZL on 2017/5/18.
 */
public class ABGraph2<S,V> {
    private ArrayList<S> sVertexList;
    private ArrayList<LinkedList<V>> link;
    public ABGraph2(){
        this.sVertexList=new ArrayList<S>();
        this.link=new ArrayList<LinkedList<V>>();
    }
    public ABGraph2(ArrayList<S> s,ArrayList<LinkedList<V>> v){
        this.sVertexList=s;
        this.link=v;
    }
    public ArrayList<S> getsVertexList() {
        return sVertexList;
    }
    public HashSet<V> getvVertexList(){
        HashSet<V> result=new HashSet<V>();
        int length1=this.sVertexList.size();
        for(int i=0;i<length1;i++){
            LinkedList<V> test=this.link.get(i);
            int length2=test.size();
            for(int j=0;j<length2;j++){
                result.add(test.get(j));
            }
        }
        return result;
    }
    public int getSVertexCount(){
        return this.sVertexList.size();
    }
    public int getVVertexCount(){
        HashSet<V> result=this.getvVertexList();
        return result.size();
    }
    public S getSVertex(int i){
        return this.sVertexList.get(i);
    }
    public int insertSVertex(S x){
        if(this.sVertexList.add(x)==false){
            return -1;
        }
        int i=this.sVertexList.size()-1;
        return i;
    }
    public boolean removeSVertex(int i){
        S deleted=this.sVertexList.remove(i);
        if(deleted==null){
            return false;
        }
        this.link.remove(i);
        return true;
    }
    public int sSearch(S x,int start){
        int length=this.sVertexList.size();
        for(int i=start;i<length;i++){
            if(x.equals(this.sVertexList.get(i))){
                return i;
            }
        }
        return -1;
    }
    public int sSearch(S x){
        return sSearch(x,0);
    }
    public boolean vSearch(V x){
        int length1=this.sVertexList.size();
        for(int i=0;i<length1;i++){
            LinkedList<V> test=this.link.get(i);
            if(test.contains(x)){
                return true;
            }
            /*
            int length2=test.size();
            for(int j=0;j<length2;j++){
                if(x==test.get(j)){
                    return true;
                }
            }*/
        }
        return false;
    }
    public LinkedList<V> sGetNeighborhoods(S x){
        int index=sSearch(x);
        if(index==-1){
            return null;
        }
        return this.link.get(index);
    }
    public ArrayList<S> vGetNeighborhoods(V x){
        if(vSearch(x)==false){
            return null;
        }
        ArrayList<S> result=new ArrayList<S>();
        int length2=this.link.size();
        for(int i=0;i<length2;i++){
            if(this.link.get(i).contains(x)==true){
                result.add(this.getSVertex(i));
            }
        }
        return result;
    }
    public boolean insertEdge(int si,V x){
        return this.link.get(si).add(x);
    }
    public boolean insertEdge(int si, Collection<V> list){
        Iterator<V> iterator=list.iterator();
        while (iterator.hasNext()){
            insertEdge(si,iterator.next());
        }
        return true;
    }
    public boolean removeVVertex(V x){
        if(vSearch(x)==false){
            return false;
        }
        int length=this.link.size();
        for(int i=0;i<length;i++){
            this.link.get(i).remove(x);
        }
        return true;
    }
    public String toString(){
        String str="";
        int length=this.sVertexList.size();
        for(int i=0;i<length;i++){
            str=str+this.sVertexList.get(i)+","+this.link.get(i)+"\n";
        }
        return str;
    }
}
