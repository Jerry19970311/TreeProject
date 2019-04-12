/**
 * Created by XZL on 2016/9/23.
 */
//单链表类。实现ADT List<T>声明的方法，T表示数据元素的数据类型（带头结点）
public class SinglyList<T> implements LList<T> {
    public Node<T> head;//头指针：指向单链表的头结点。
    //注：头结点一般不存储数据，只做索引用，一般从头节点的后继结点开始存放第一个元素（即第0号元素）。
    public SinglyList(){//构造空单链表。
        this.head=new Node<T>();//创建头节点，数据域和地址域值均为null。
    }
    public SinglyList(T[] values){//创造单链表，由values数组提供元素。
        this();//构造空单链表，只有头结点。
        Node<T> rear=this.head;//rear指向单链表最后一个结点。
        for(int i=0;i<values.length;i++){//若values长度为0则创造空链表。
            rear.next=new Node<T>(values[i],null);//尾插入，创建结点链入rear结点之后。
            rear=rear.next;//rear指向新的链尾结点。
        }
    }
    public SinglyList(SinglyList<T> list){
        this.head=list.head;
    }
    public SinglyList(LList<? extends T> list){
        for(int i=0;list.get(i)!=null;i++) {
            append(list.get(i));
        }
    }

    //判空的方法：数据一般从头结点的下一个结点开始存放数据，如果头节点的next为空，则说明它没有下一个结点，链表为空。
    @Override
    public boolean isEmpty() {
        return this.head.next==null;
    }

    //（该方法为个人操作，正确性尚无法保证，须后续检验）
    @Override
    public int length() {
        Node<T> p=this.head.next;
        int i=0;
        //遍历。
        while(p!=null){
            i++;
            p=p.next;
        }
        return i;
    }

    //取值：返回第index个元素（注意：第0个元素为头结点的后继元素），如index越界，则返回null。
    @Override
    public T get(int index) {
        //设定指针p并将其至于第0个元素所在的结点上。
        Node<T> p=this.head.next;
        //如果要求的元素位置超出链表长度本身，那么p就会因为null值提前退出循环。（参见条件语句p!=null）
        //只有在index不越界的情况下，才能使得指针p因为到达相应位置退出循环。（参见条件语句i<index）
        for(int i=0;p!=null&&i<index;i++){
            p=p.next;
        }
        //利用3目运算符判断结束循环的情况从而决定返回值：
        //如果是由于溢出或者不规范输入而退出循环，则返回null。
        //如果是由于到达相应元素的所在结点而退出循环，则返回该结点的数据域。
        return (index>=0&&p!=null)?p.data:null;
    }

    //置值：将第index个结点的数据更改为x，并返回被改的元素。
    //（该方法为个人操作，正确性尚无法保证，须后续检验）
    @Override
    public T set(int index, T x) {
        T temp;//给被换掉的元素提供空间。
        Node<T> p=this.head.next;//设定指针p，并将其初值定在第0个元素所在的结点上。
        //如果要求的元素位置超出链表长度本身，那么p就会因为null值提前退出循环。（参见条件语句p!=null）
        //只有在index不越界的情况下，才能使得指针p因为到达相应位置退出循环。（参见条件语句i<index）
        for(int i=0;i<index&&p!=null;i++){
            p=p.next;
        }
        //如果是因为溢出而退出循环，指针p必为null，这里设定相关控制语句。
        //如果为null则提前结束方法，避免空值进入下面实际操作部分造成单链表中断。
        if(p==null){
            return null;
        }
        //实际操作部分：将被换掉的元素放入中转变量，然后将x放入该位置。最后返回中转变量temp中的元素。
        temp=p.data;
        p.data=x;
        return temp;
    }

    @Override
    public String toString() {
        String str=this.getClass().getName()+"(";
        for(Node<T> p=this.head.next;p!=null;p=p.next){
            str+=p.data.toString();
            if(p.next!=null){
                str+=",";
            }
        }
        return str+")";
    }

    //插入：插入x对象，插入后对象序号为index。
    @Override
    public boolean insert(int index, T x) {
        //首先排除x为空的情况。
        if(x==null){
            return false;
        }
        //设置指针p，并将其定为头结点。

        //注：由于单链表中的每个结点内均只有保存后继结点的引用，而没有保存前驱结点的引用。
        //而在单链表中的插入操作的原理是将待插入结点的地址域设为后继结点，然后将待插入结点的引用至于前驱结点的地址域中。
        //如果提前把p移动到第index个结点上（即x所在结点未来的后继结点），由于没有事先预存index-1个结点，则需要重新从头寻找第index-1个结点。
        //这一操作将大大增加该方法的时间复杂度。（如果要另设指针寻找前驱元素的结点，则还会增加该方法的空间复杂度。）
        //而在第index-1个结点上则原先就保存着第index个结点的引用，如果在进行插入操作前先将指针定在该结点上将大大减少该元素的时间复杂度。
        //（上述注释可以间接解释【为什么insertDifferent方法和remove方法不能利用search方法确定操作位置】）
        Node<T> p=this.head;
        //遍历。讨论该算法的容错性：
        //情况1：如果index设定为0到最后一个结点之间，则正常运行，p到达相应位置的前一个结点，通过变换前驱元素的结点的地址域和待插入的结点的地址域进行插入。
        //情况2：如果index设定为负数，则下面的循环语句根本无法进行而直接跳过，由于p预设为头结点，x的结点将直接在头结点和第0个元素的结点之间进行插入。
        //情况3：如果index设定超过该单链表长度（溢出），则下面的循环语句会因p.next==null而退出，导致p将留在最后一个结点。
        //由于最后一个结点的地址域为null，故为x创建的结点地址域也将为null，并且这一新结点的引用将被设定为p所在结点的地址域。最后该新结点成为新的最后一个结点。
        //总结：无论是否index溢出，最终的操作都能进行下去，所以返回值为true，此方法唯一可能导致操作失败的因素只有【待插入元素为空的情况】。
        for(int i=0;i<index&&p.next!=null;i++){
            p=p.next;
        }
        Node<T> n=new Node<T>(x,p.next);
        p.next=n;
        return true;
    }

    //插入：插入x对象，插入最后位置。
    //注：课本的方法体中只有insert(Integer.MAX_VALUE,x)，利用了insert方法的容错性，将index参数设定在一个极少发生的极端情况。
    @Override
    public boolean append(T x) {
        if(x==null){
        return false;
        }
        Node<T> p=this.head;
        while(p.next!=null){
            p=p.next;
        }
        p.next=new Node<T>(x,null);
        return true;
    }

    //删除：移去序号为index的对象，返回被移去对象。
    //（关于该方法的指针使用问题，详见insert方法。）
    @Override
    public T remove(int index) {
        Node<T> front=this.head;
        for(int j=0;front.next!=null&&j<index;j++){
            front=front.next;
        }
        if(index>=0&&front.next!=null){
            T old=front.next.data;
            front.next=front.next.next;
            return old;
        }
        return null;
    }

    //清空：清空线性表。
    //若将头结点的地址域设为空，将导致除头结点外的其他结点没有任何利用可能，最终它们占用的空间将由Java的资源回收机制所释放。
    //而该单链表也将只剩头结点，成为定义上的空链表。
    @Override
    public void clear() {
        this.head.next=null;
    }

    //搜索：利用元素查找元素。
    @Override
    public int search(T key) {
        Node<T> p=this.head.next;
        for(int i=0;p!=null;i++){
            if(p.data.equals(key)){
                return i;
            }
            p=p.next;
        }
        return -1;
    }

    @Override
    public boolean contains(T key) {
        if(search(key)>=0){
            return true;
        }
        return false;
    }

    @Override
    public int insertDifferent(T key) {
        if(search(key)>=0){
            return search(key);
        }
        if(key==null){
            return -1;
        }
        Node<T> p=this.head;
        int i=-1;
        while(p.next!=null){
            p=p.next;
            i++;
        }
        p.next=new Node<T>(key,null);
        i++;
        return i;
    }

    @Override
    public T remove(T key) {
        Node<T> p=this.head;
        for(int i=0;p.next!=null;i++){
            if(p.next.equals(key)){
                p.next=p.next.next;
                return key;
            }
            p=p.next;
        }
        return null;
    }

    @Override
    public void addAll(LList<T> list) {
        for(int i=0;append(list.get(i))==true;i++){
        }
    }
}
