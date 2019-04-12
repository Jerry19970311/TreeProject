import java.util.LinkedList;

/**
 * Created by XZL on 2017/4/20.
 */
public class TreeNode<E> {
    private TreeNode<E> father;
    private E data;
    private LinkedList<TreeNode<E>> next;
    public TreeNode(){
        this.father=null;
        this.data=null;
        this.next=new LinkedList<TreeNode<E>>();
    }
    public TreeNode(E data){
        this.father=null;
        this.data=data;
        this.next=new LinkedList<TreeNode<E>>();
    }
    public TreeNode(TreeNode<E> father,E data){
        this.father=father;
        this.data=data;
        this.next=new LinkedList<TreeNode<E>>();
    }
    public void setData(E data){
        this.data=data;
    }
    public E getData(){
        return this.data;
    }
    public boolean isLeaf(){
        return next.isEmpty();
    }
    public void addNext(TreeNode<E> n){
        next.add(n);
    }
    public boolean remove(E n){
        return next.remove(n);
    }
    public LinkedList<TreeNode<E>> getNext(){
        return this.next;
    }
    public TreeNode<E> getFather(){
        return this.father;
    }

    /*
        public void setFather(TreeNode<E> father) {
            this.father = father;
        }
    */
    @Override
    public String toString() {
        String str=this.data.toString();
            if(!this.isLeaf()) {
                str = str + "\n\n直接子节点：\n";
                int length = this.next.size();
                for (int i = 0; i < length; i++) {
                    str = str + this.next.get(i).data.toString() + "\n";
                }
            }else{
            str=str+"\n\n无子结点！\n";
        }
        return str;
    }
}
