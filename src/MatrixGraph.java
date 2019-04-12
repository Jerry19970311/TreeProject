/**
 * Created by XZL on 2016/11/27.
 */
public class MatrixGraph<T> extends AbstractGraph<T>{
    protected Matrix matrix;
    public MatrixGraph(int length,boolean isDirected){
        super(length,isDirected);
        matrix=new Matrix(length);
    }
    public MatrixGraph(boolean isDirected){
        this(10,isDirected);
    }
    public MatrixGraph(){
        this(10,false);
    }
    public MatrixGraph(T[] vertices,boolean isDirected){
        this(vertices.length,isDirected);
        for(int i=0;i<vertices.length;i++){
            this.insertVertex(vertices[i]);
        }
    }
    public MatrixGraph(T[] vertices,Triple[] edges,boolean isDirection){
        this(vertices,isDirection);
        for(int j=0;j<edges.length;j++){
            this.insertEdge(edges[j]);
        }
    }
    public String toString(){
        String str=super.toString()+"邻接矩阵: \n";
        int n=this.vertexCount();
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if(this.matrix.get(i,j)==MAX_WEIGHT){
                    str+="    ∞";
                }
                else {
                    str+=String.format("%6d",this.matrix.get(i,j));
                }
            }
            str+="\n";
        }
        return str;
    }

    @Override
    public int insertVertex(T x) {
        this.vertexlist.append(x);
        int i=this.vertexlist.length()-1;
        if(i>=this.matrix.getRows()){
            this.matrix.setRowsColumns(i+1,i+1);
        }
        for(int j=0;j<i;j++){
            this.matrix.set(i,j,MAX_WEIGHT);
            this.matrix.set(j,i,MAX_WEIGHT);
        }
        return i;
    }

    @Override
    public boolean removeVertex(int i) {
        int n=this.vertexCount();
        if(i>=0&&i<n){
            this.vertexlist.remove(i);
            for(int j=i+1;j<n;j++){
                for(int k=0;k<n;k++){
                    this.matrix.set(j-1,k,this.matrix.get(j,k));
                }
            }
            for(int j=0;j<n;j++){
                for(int k=i+1;k<n;k++){
                    this.matrix.set(j,k+1,this.matrix.get(j,k));
                }
            }
            this.matrix.setRowsColumns(n-1,n-1);
            return true;
        }
        return false;
    }

    @Override
    public int getFirstNeighbor(int i) {
        return this.next(i,-1);
    }

    @Override
    public int next(int i, int j) {
        int n=this.vertexCount();
        if(i>=0&&j<n&&j>=-1&&i<n&&i!=j){
            for(int k=j+1;k<n;k++){
                if(this.matrix.get(i,k)>0&&this.matrix.get(i,k)<MAX_WEIGHT){
                    return k;
                }
            }
        }
        return 0;
    }

    @Override
    public boolean insertEdge(int i, int j, int weight) {
        if(i!=j){
            if(weight<=0||weight>MAX_WEIGHT){
                weight=MAX_WEIGHT;
            }
            this.matrix.set(i,j,weight);
            if(isDirection==false){
                this.matrix.set(j,i,weight);
            }
            return true;
        }
        return false;
    }
    public void insertEdge(Triple edge){
        this.insertEdge(edge.row,edge.column,edge.value);
    }

    @Override
    public boolean removeEdge(int i, int j) {
        if(i!=j){
            this.matrix.set(i,j,MAX_WEIGHT);
            if(isDirection==false){
                this.matrix.set(j,i,MAX_WEIGHT);
            }
            return true;
        }
        return false;
    }

    @Override
    public int weight(int i, int j) {
        return this.matrix.get(i,j);
    }

    @Override
    public void minSpanTreePrim() {
        if(this.isDirection==false) {
            super.minSpanTreePrim();
        }else{
            System.out.println("您构建的是有向树，不能创建最小生成树！");
        }
    }

    @Override
    public void shortestPath() {
    }

}
