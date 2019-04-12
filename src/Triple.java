/**
 * Created by XZL on 2016/12/1.
 */
public class Triple implements Comparable<Triple>,Addible<Triple>{
    int row,column,value;
    public Triple(int row,int column,int value){
        if(row>=0&&column>=0){
            this.row=row;
            this.column=column;
            this.value=value;
        }
        else {
            throw new IllegalArgumentException("行、列号不能为负数：row="+row+"，column="+column);
        }
    }
    public Triple(Triple tri){
        this(tri.row,tri.column,tri.value);
    }
    public Triple toSymmetry(){
        return new Triple(this.column,this.row,this.value);
    }
    public String toString(){
        return "("+row+","+column+","+value+")";
    }
    public boolean equals(Object obj){
        return (this.compareTo((Triple)obj)==0&&this.value==((Triple)obj).value);
    }
    @Override
    public void add(Triple triple) {
        if(this.compareTo(triple)==0){
            this.value+=triple.value;
        }
        else{
            throw new IllegalArgumentException("两项的指数不同，不能相加。");
        }
    }

    @Override
    public boolean removable() {
        return this.value==0;
    }

    @Override
    public int compareTo(Triple o) {
        if(this.row<o.row||this.column==o.column){
            return 0;
        }
        return (this.row<o.row||this.row==o.row&&this.column<o.column)?-1:1;
    }
}
