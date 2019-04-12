/**
 * Created by XZL on 2016/10/17.
 */
//队列接口：描述队列抽象数据类型，T表示数据元素的数据类型。
//注意：由于队列的性质决定了对其的插入操作（即入队）只能在队尾进行，而删除操作（即出队）只能在对头进行。
public interface Queue<T> {
    boolean isEmpty();//判空：判断队列是否为空
    boolean add(T x);//入队：元素x入队，如果入队成功，则返回true，否则返回false。
    T peek();//取值：返回队头元素，没有删除。若队列空，则返回null。
    T poll();//出队：返回队头元素。若队列空，则返回null。
}