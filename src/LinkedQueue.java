/**
 * Created by XZL on 2016/10/17.
 */
//链式队列类：最终类（不可继承），实现队列接口，T表示数据元素的数据类型。
//注意：由于此处如果使用单链表作为数据域，进行入队操作的时候会在遍历上大大增加时间复杂度，降低效率，
//故此处单独设置单链表，并设头尾结点，这样进行入队和出队操作时时间复杂度均为O(1)。
public final class LinkedQueue<T> implements Queue<T>{
    private Node<T> front,rear;//front和rear分别指向队头和队尾结点。
    //构造方法：设置空的链式队列，头结点和尾结点均为null。
    public LinkedQueue(){
        this.front=this.rear=null;
    }
    //判空：如果头结点和尾结点均为空，则返回true。
    @Override
    public boolean isEmpty() {
        return (this.front==null)&&(this.rear==null);
    }

    //入队：和之前的插入操作一样，必须防止空元素进入。而一般情况下，入队操作只修改尾结点，
    //如果是空队列，则另外处理。
    @Override
    public boolean add(T x) {
        if(x==null){
            return false;
        }
        if(isEmpty()==true){
            this.front.data=this.rear.data=x;
            return true;
        }
        this.rear.next=new Node<>(x,null);
        this.rear=this.rear.next;
        return true;
    }

    @Override
    public T peek() {
        return this.isEmpty()?null:this.front.data;
    }

    //出队：首先排除空队列情况，对头结点操作，将头结点移向其所指结点的下一结点，原结点所占的空间在无法被使用的情况下将自动被Java回收机制释放。
    @Override
    public T poll() {
        if(isEmpty()==true){
            return null;
        }
        Node<T> delete=this.front;
        this.front=this.front.next;
        //如果该链式队列只有一个结点，对头结点进行操作以后，会出现尾结点仍为该结点，而头结点已为null的情况。
        //下列判断语句可解决这一情况。
        if(this.front==null){
            this.rear=null;
        }
        return delete.data;
    }
}