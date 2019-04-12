/**
 * Created by XZL on 2016/9/23.
 */
//单链表节点类
public class Node<T>{
    public T data;
    public Node<T> next;
    public Node(){
        data=null;
        next=null;
    }
    public Node(T data,Node<T> next){
        this.data=data;
        this.next=next;
    }
    public String toString(){
        return this.data.toString();
    }
    public boolean isLeaf(){
        return false;
    }
}
