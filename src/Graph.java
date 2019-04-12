/**
 * Created by XZL on 2016/11/25.
 */
//图接口。以下方法为对图的可操作方法，T指定顶点元素类型。
public interface Graph<T> {
    int vertexCount();//取值：返回图中的顶点数。
    T getVertex(int i);//取值：返回顶点vi元素（第i个元素）。
    void setVertex(int i,T x);//置值：设置顶点vi元素（第i个元素）为x。
    int insertVertex(T x);//插入：插入元素为x的顶点，返回顶点序号。
    boolean removeVertex(int i);//删除：删除顶点vi（第i个元素）及其关联的边。
    int getFirstNeighbor(int i);//取值：返回邻接顶点序号。
    int next(int i,int j);//取值：返回vi在vj后的后继邻接顶点序号。
    boolean insertEdge(int i,int j,int weight);//插入：插入边<vi,vj>，权值为weight。
    boolean removeEdge(int i,int j);//删除：删除边<vi,vj>。
    int weight(int i,int j);//取值：返回<vi,vj>边的权值。
    void DFSTraverse(int i);//遍历：非连通图的一次深度优先搜索遍历，从顶点vi出发。
    void BFSTraverse(int i);//遍历：非连通图的一次广度优先搜索遍历，从顶点vi出发。
    void minSpanTreePrim();//构造：构造带权无向图的最小生成树，Prim算法。
    void shortestPath(int i);//构造：求带权图顶点vi的单源最短路径，Dijkstra算法。
    void shortestPath();//取值：求带权图每对顶点间的最短路径和长度，Floyd算法。
}
