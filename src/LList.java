/**
 * Created by XZL on 2016/9/18.
 */
public interface LList<E> {//线性表接口
    //以下为对线性表数据所能进行的操作。
    boolean isEmpty();//判空：判断线性表是否为空。
    int length();//获取表长度：返回线性表长度。
    E get(int index);//取值：返回序号为index的对象
    E set(int index,E x);//置值：设置序号为index的对象为x。
    boolean insert(int index,E x);//插入：插入x对象，插入后对象序号为index。
    boolean append(E x);//插入：插入x对象，插入最后位置。
    E remove(int index);//删除：移去序号为index的对象，返回被移去对象。
    void clear();//清空：清空线性表。

    int search(E key);
    boolean contains(E key);
    int insertDifferent(E key);
    E remove(E key);
    boolean equals(Object obj);
    void addAll(LList<E> list);
}
