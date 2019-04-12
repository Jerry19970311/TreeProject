/**
 * Created by XZL on 2016/9/18.
 */
public class SeqList<E> implements LList<E> {
    protected Object[] element;//对象数组（私有成员）
    protected int n;//顺序表长度

    public SeqList(int length){
        element=new Object[length];
        this.n=0;
    }
    public SeqList(){
        this(64);
    }
    public SeqList(E[] values){
        this(values.length);
        //System.out.println("62651651652154");
        for(int i=0;i<values.length;i++){
            //System.out.println("65312161653125");
            this.element[i]=values[i];
            //System.out.println("48987");
        }
        this.n=values.length;
        //System.out.println("5198561");
    }
    public SeqList(SeqList<E> list){
        this.n=list.n;
        this.element=list.element;
    }
    public SeqList(LList<? extends E> list){
        this.n=list.length();
        for(int i=0;i<list.length();i++){
            this.element[i]=list.get(i);
        }
    }

    @Override
    public boolean isEmpty() {
        return this.n==0;
    }

    @Override
    public int length() {
        return this.n;
    }

    @Override
    public E get(int index) {
        if (index >= 0 && index < n) {
            return (E) element[index];
        }
        return null;
    }

    //（该方法为个人操作，正确性尚无法保证，须后续检验）
    @Override
    public E set(int index, E x) {
        if (index >= 0 && index < n) {
            Object temp = element[index];
            element[index] = x;
            return (E) temp;
        }
        return null;
    }

    @Override
    public boolean insert(int index, E x) {
        //该方法体内囊括了所有插入失败的情况，如果这些情况均未发生，则意味着插入成功。
        if (x == null) {
            return false;//情况1：不能添加null。
        }
        if (n == element.length) {//情况2：如果数组满，则需要扩充顺序表容量。
            Object[] temp = element;
            //System.out.println("561651653165");
            element = new Object[temp.length * 2];
            //System.out.println("651526532165156");
            //把原数组元素复制到空间扩增2倍的新数组里。
            //注：顺序表类中的属性数组容量可以大于等于n，但不能小于n。
            for (int i = 0; i < temp.length; i++) {
                //System.out.println("654856185151856");
                element[i] = temp[i];
            }
        }
        //情况3：容错，如果指示的数字超出数组下标范围将自动调至该数组极限值。
        if (index < 0) {
            index = 0;
        }
        if (index > n) {
            index = n;
        }
        //当这3个误差情况解决以后才能进行正式的插入工作。
        for (int j = n - 1; j >= index; j--) {
            //System.out.println("5132865151653165"+element[j+1]+" "+element[j]+element.length);
            element[j + 1] = element[j];
            //System.out.println("6519651561651651"+element[j+1]+" "+element[j]+element.length);
        }
        element[index] = x;
        n++;
        return true;
    }

    //（该方法为个人操作，正确性尚无法保证，须后续检验）
    @Override
    public boolean append(E x) {
        /*if (x == null) {
            return false;
        }
        if (n == element.length) {//如果数组满，则需要扩充顺序表容量。
            System.out.println("489415");
            Object[] temp = element;
            element = new Object[temp.length * 2];
            //把原数组元素复制到空间扩增2倍的新数组里。
            //注：顺序表类中的属性数组容量可以大于等于n，但不能小于n。
            for (int i = 0; i < temp.length; i++)
                element[i] = temp[i];
            element[element.length] = x;
            n++;
            System.out.println("48awe4f8");
            return true;
        }
        System.out.println("148541851");
        element[n] = x;
        n++;
        return false;*/
        return insert(this.n,x);
    }

    //删除方法：将目标元素删除，并且返回被删除的元素。
    @Override
    public E remove(int index) {
        //该判断语句规定了能寻找目标元素并且将其删除的两个条件，满足以后才能执行：
        //条件1：顺序表不为空（n不为0）
        //条件2：目标元素下标不超出顺序表长度范围。
        if (n != 0 && index >= 0 && index < n) {
            E old = (E) element[index];
            //该循环语句执行将目标元素之后的元素全部向前移动一位的动作。
            //目标元素自动被覆盖，达到删除效果。
            for (int j = index; j < this.n - 1; j++) {
                element[j] = element[j + 1];
            }
            element[n - 1] = null;
            n--;
            return old;
        }
        return null;//如果均不满足上述两个条件，则返回空值。
    }

    //（该方法为个人操作，正确性尚无法保证，须后续检验）
    @Override
    public void clear() {
        n = 0;
        //Q：如果没有下列循环语句，不会造成空间的浪费吗？
        for (int i = 0; i < element.length; i++) {
            element[i] = null;
        }
    }

    @Override
    public int search(E key) {
        for (int i = 0; i < this.n; i++) {
            if (key.equals(this.element[i])) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean contains(E key) {
        return this.search(key) != -1;
    }

    @Override
    public int insertDifferent(E key) {
        if(search(key)<0){
            if(append(key)==true){
                return this.n-1;
            }
        }
        return search(key);
    }

    @Override
    public E remove(E key) {
        return remove(search(key));
    }

    @Override
    public void addAll(LList<E> list) {
        for(int i=0;append(list.get(i))==true;i++){
            System.out.println(list.length());
            System.out.println(i);
            System.out.println("5615165321");
            System.out.println(i);
            System.out.println("56151");
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;//Q:此处的this指什么？
        }
        if (obj instanceof SeqList<?>) {
            SeqList<E> list = (SeqList<E>) obj;
            if (this.n == list.n) {
                for (int i = 0; i < this.n; i++) {
                    if (!(this.get(i).equals(list.get(i)))) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }


}