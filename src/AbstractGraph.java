/**
 * Created by XZL on 2016/11/26.
 */
//抽象图类：由于图中的边集合描述的是一种二维线性关系，因此边一般用矩阵表示。
//因此矩阵的存储结构有3种：
//1，图的邻接矩阵；2，图的邻接表；3，图的邻接多重表。
//而点集合的存储方式相对简单，只需要利用线性表存储即可。
//故设此抽象类，这一抽象类将由表示前两种存储结构的类继承。
//而前两种结构对于点集合的存储方式是相同的，故在此可先对点的部分操作进行定义，剩余部分交由子类提供实现。
public abstract class AbstractGraph<T> implements Graph<T>{
    protected static final int MAX_WEIGHT=0x0000ffff;//设定最大值常数为0x0000ffff(无穷大)
    protected SeqList<T> vertexlist;//抽象图类的子类均有以顺序表为数据结构存储的点集合作为数据域。
    protected boolean isDirection;//判断是否为有向图。
    //构造方法：这里因为只有点集合为数据域，因此构造方法也只对点进行定义，其它部分由子类进一步实现。如果构造方法无参数，则自动定义线性表长度为10。
    public AbstractGraph(int length,boolean isDirection){
        this.vertexlist=new SeqList<T>(length);
        this.isDirection=isDirection;
    }
    public AbstractGraph(){
        this(10,false);
    }
    public int vertexCount(){
        return this.vertexlist.length();
    }
    public String toString(){
        return "顶点集合"+this.vertexlist.toString()+"\n";
    }
    public T getVertex(int i){
        return this.vertexlist.get(i);
    }
    public void setVertex(int i,T x){
        this.vertexlist.set(i,x);
    }
    public void DFSTraverse(int i) {
        //为点集合创建一个等长度的boolean类型数组专门记录每个结点是否已被访问。
        boolean[] visited=new boolean[this.vertexCount()];
        int j=i;
        //此循环专为非连通图准备，如果为连通图，则只需一次递归调用即可访问全部结点。
        do{
            if(!visited[j]){
                System.out.println("{");
                this.depthfs(j,visited);
                System.out.println("}");
            }
            j=(i+1)%this.vertexCount();
        }while (j!=i);
        System.out.println();
    }

    //深度优先遍历的递归形式，遍历整个连通图（或非连通图的连通子图），访问权限设为private，仅由DFSTraverse方法调用。
    private void depthfs(int i,boolean[] visited){
        //将预设的访问点输出。
        System.out.print(this.getVertex(i)+" ");
        //在visited数组中的对应位置标记为true。
        visited[i]=true;
        //找到访问点的下一个邻接点。
        int j=this.next(i,-1);
        //循环，将i的所有邻接点做到全部访问，直到next方法返回值为-1。
        while(j!=-1){
            //如果该邻接点未被访问，则递归调用。
            if(!visited[i]){
                //递归调用：实现沿着每个点的首个邻接点（如果在调用时未被访问）进行遍历。
                depthfs(j,visited);
            }
            //如果经过递归以后以已经走完一条路径，则返回最后一个结点的上一个结点，重新查找是否有其它邻接点，
            //以此类推，直到回到最初的访问点已无未被访问的邻接点为止。
            j=this.next(i,j);
        }
    }

    public void BFSTraverse(int i) {
        boolean[] visited=new boolean[this.vertexCount()];
        int j=i;
        do{
            if(!visited[j]){
                System.out.print("{");
                breadthfs(j,visited);
                System.out.print("}");
            }
            j=(j+1)%this.vertexCount();
        }while (j!=i);
        System.out.println();
    }

    private void breadthfs(int i,boolean[] visited){
        System.out.println(this.getVertex(i)+" ");
        visited[i]=true;
        LinkedQueue<Integer> q=new LinkedQueue<Integer>();
        q.add(i);
        while (!q.isEmpty()){
            i=q.poll();
            for(int j=next(i,-1);j!=-1;j=next(i,j)){
                if(!visited[j]){
                    System.out.print(this.getVertex(j)+" ");
                    visited[j]=true;
                    q.add(j);
                }
            }
        }
    }

    public void minSpanTreePrim() {
        //由于最小生成树的特性，其边数必定为该图的点数减1，因此设定一个专门存储边的数组，并且它的长度也为边数减1。
        Triple[] mst=new Triple[vertexCount()-1];
        //将点集合中的第一个点与其他所有点的边存进该数组。（如果无边权值则为无穷大）
        for(int i=0;i<mst.length;i++){
            mst[i]=new Triple(0,i+1,this.weight(0,i+1));
        }
        for(int i=0;i<mst.length;i++){
            //将保存最小权值的边及其权值的变量初始化。
            int minweight=MAX_WEIGHT,min=i;
            //将i位置之后的所有边的权值中搜寻权值最小的，然后与i位置的边进行交换。
            for(int j=i+1;j<mst.length;j++){
                if(mst[j].value<minweight){
                    minweight=mst[j].value;
                    min=j;
                }
            }
            Triple edge=mst[min];
            mst[min]=mst[i];
            mst[i]=edge;
            //将比较出来的最小权值的边的另一端点存入tv，将tv与尚未加入最小生成树的点的权值
            //与集合中的点与尚未加入最小生成树的点的权值进行比较，如果先前有的权值较大，则进行替换，反之则保留。
            //（注意：尚未加入最小生成树的点与该数任何一个点相连均不可能出现回路）
            int tv=edge.column;
            for(int j=i+1;j<mst.length;j++){
                int v=mst[j].column;
                int weight=this.weight(tv,v);
                if(weight<mst[j].value){
                    mst[j]=new Triple(tv,v,weight);
                }
            }
            //结束一次循环后对剩下的边进行相同的操作
            //（重点在于：每次循环中，随着新结点的加入，每一个尚未加入最小生成树的点都要与已经加入的点进行比较，并更新他们与该树所有点的最小权值）
        }
        //循环结束后进行输出。
        System.out.println("\n最小生成树的集合：");
        //设定保存最小权值的变量并在之后的循环里进行累加。
        int mincost=0;
        for(int i=0;i<mst.length;i++){
            System.out.print(mst[i]+" ");
            mincost+=mst[i].value;
        }
        System.out.println("，最小代价为"+mincost);
    }

    public void shortestPath(int i){
        //专门设定一个存储点集合长度（即点个数）的变量，因为它在整个运行过程中会多次被用到。
        int n=this.vertexCount();
        //定义一个boolean类型数组专门存放对应位置的点是否已经进入最短路径，长度为点个数。
        boolean[] vset=new boolean[n];
        //参数的点作为加入最短路径的第一个点，首先被标记为true。
        vset[i]=true;
        //定义一个int类型的数组专门存放对应位置的点在最短路径中的上一个访问点，长度为点个数。
        int[] dist=new int[n];
        //定义一个int类型的数组专门存放i点到每个点的最短路径。
        int[] path=new int[n];
        //初始化dist数组和path数组：（这时进入最短路径的只有初始点i）
        //将dist数组中的每一个值部署为对应位置的点到i点的最短路径，
        //将path数组中对应的i的邻接点值设为i，如果其对应的点不与i邻接则设为-1。
        for(int j=0;j<n;j++){
            dist[j]=this.weight(i,j);
            path[j]=(j!=i&&dist[j]<MAX_WEIGHT)?i:-1;
        }
        //开始进行循环的工作，对每一个点均要进行插入，因此循环次数为n。
        for(int j=(i+1)%n;j!=i;j=(i+1)%n){
            //每次循环，将最小权值和所对应的点初始化。
            int mindist=MAX_WEIGHT,min=0;
            //遍历所有点确定最小权值及其所对应的点。
            for(int k=0;k<n;k++){
                if(vset[k]==false&&dist[k]<mindist){
                    mindist=dist[k];
                    min=k;
                }
            }
            //此选择语句专门为非连通图设计：
            //如果经历了之前的循环对比被初始化的最小权值仍没有被改变，则说明剩下的所有没有加入最短路径的点与i点完全不连通，
            //即i点与剩下的点处于不同的两个连通子图上，此图属于非连通图。
            //那么就应该提前结束这个循环，提高运行效率。
            if(mindist==MAX_WEIGHT){
                break;
            }
            //将比较出来的与最短路径中的点权值最小的点加入最短路径。
            vset[min]=true;
            //将新加入的点（序号为min）与尚未加入最短路径的min邻接点（vset[k]==false&&this.weight(min,k)<MAX_WEIGHT）进行比较，
            //如果其中的点先前对应在dist数组相应位置的值与min的对应值加min和该点边的权值大，
            //则说明该点与最短路径中的点的最小权值由于min点的加入发生变化，
            //因此对该点在diat和path数组的对应值进行更新，然后在下一次循环中进行对比。
            for(int k=0;k<n;k++){
                if(vset[k]==false&&this.weight(min,k)<MAX_WEIGHT&&dist[min]+this.weight(min,k)<dist[k]){
                    dist[k]=dist[min]+this.weight(min,k);
                    path[k]=min;
                }
            }
        }
        System.out.print(this.getVertex(i)+"顶点的单源最短路径");
        for(int j=0;j<0;j++){
            if(j!=i){
                SinglyList<T> pathlink=new SinglyList<T>();
                pathlink.insert(0,this.getVertex(j));
                for(int k=path[j];k!=i&&k!=j&&k!=-1;k=path[k]){
                    pathlink.insert(0,this.getVertex(k));
                }
                pathlink.insert(0,this.getVertex(i));
                System.out.print(pathlink.toString()+"长度"+(dist[j]==MAX_WEIGHT?"∞":dist[j])+"，");
            }
        }
        System.out.println();
    }
}