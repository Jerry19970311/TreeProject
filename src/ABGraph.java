import java.util.ArrayList;

/**
 * Created by XZL on 2017/5/8.
 */
public class ABGraph<S,V> {
    public static final int MAX_WEIGHT=0x0000ffff;
    private ArrayList<S> sVertexList;
    private ArrayList<V> vVertexList;
    private Matrix matrix;
    public ABGraph(int sLength,int vLength){
        this.sVertexList=new ArrayList<S>();
        this.vVertexList=new ArrayList<V>();
        this.matrix=new Matrix(sLength,vLength);
    }
    public ABGraph(){this(10,10);}
    public ABGraph(ArrayList<S> a,ArrayList<V> b){
        this.sVertexList=a;
        this.vVertexList=b;
        this.matrix=new Matrix(a.size(),b.size());
    }
    public int getSVertexCount(){
        return this.sVertexList.size();
    }
    public int getVVertexCount(){
        return this.vVertexList.size();
    }
    public S getSVertex(int i){
        return this.sVertexList.get(i);
    }
    public V getVVertex(int i){
        return this.vVertexList.get(i);
    }
    public int insertSVertex(S x){
        this.sVertexList.add(x);
        int i=this.sVertexList.size()-1;
        int j=this.matrix.getColumns();
        if(i>=this.matrix.getRows()){
            this.matrix.setRowsColumns(i*2,j);
        }
        for(int k=0;k<j;k++){
            this.matrix.set(i,k,MAX_WEIGHT);
        }
        return i;
    }
    public int insertVVertex(V x){
        this.vVertexList.add(x);
        int i=this.matrix.getRows();
        int j=this.vVertexList.size()-1;
        if(j>=this.matrix.getColumns()){
            this.matrix.setRowsColumns(i,j*2);
        }
        for(int k=0;k<i;k++){
            this.matrix.set(k,j,MAX_WEIGHT);
        }
        return j;
    }
    public boolean removeSVertex(int i){
        int n=this.sVertexList.size();
        int m=this.vVertexList.size();
        S deleted=this.sVertexList.remove(i);
        if(deleted==null){
            return false;
        }
        for(int j=i+1;j<n;j++){
            for(int k=0;k<m;k++){
                this.matrix.set(j-1,k,this.matrix.get(j,k));
            }
        }
        return true;
    }
    public boolean removeVVertex(int i){
        int n=this.sVertexList.size();
        int m=this.vVertexList.size();
        V deleted=this.vVertexList.remove(i);
        if(deleted==null){
            return false;
        }
        for(int j=i+1;j<m;j++){
            for(int k=0;k<n;k++){
                this.matrix.set(k,j-1,this.matrix.get(k,j));
            }
        }
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
    public int vSearch(V x,int start){
        int length=this.vVertexList.size();
        for(int i=start;i<length;i++){
            if(x.equals(this.vVertexList.get(i))){
                return i;
            }
        }
        return -1;
    }
    public int vSearch(V x){
        return vSearch(x,0);
    }
    public ArrayList<V> sGetNeighborhoods(S x){
        int index=sSearch(x);
        if(index==-1){
            return null;
        }
        int length=this.vVertexList.size();
        ArrayList<V> result=new ArrayList<V>();
        for(int i=0;i<length;i++){
            if(this.matrix.get(index,i)==1){
                result.add(this.vVertexList.get(i));
            }
        }
        return result;
    }
    public ArrayList<S> vGetNeighborhoods(V x){
        int index=vSearch(x);
        if(index==-1){
            return null;
        }
        int length=this.sVertexList.size();
        ArrayList<S> result=new ArrayList<S>();
        for(int i=0;i<length;i++){
            if(this.matrix.get(index,i)==1){
                result.add(this.sVertexList.get(i));
            }
        }
        return result;
    }
    public boolean insertEdge(int i, int j, int weight) {
        if(i!=j){
            if(weight<=0||weight>MAX_WEIGHT){
                weight=MAX_WEIGHT;
            }
            this.matrix.set(i,j,weight);
            return true;
        }
        return false;
    }
    public int weight(int i, int j) {
        return this.matrix.get(i,j);
    }

    public ArrayList<S> getsVertexList() {
        return sVertexList;
    }

    public ArrayList<V> getvVertexList() {
        return vVertexList;
    }
}
