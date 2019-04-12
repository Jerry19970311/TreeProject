/**
 * Created by XZL on 2016/11/27.
 */
public class Matrix {
    private int rows,columns;
    private int[][] element;
    public Matrix(int m,int n){
        this.element=new int[m][n];
        this.rows=m;
        this.columns=n;
    }
    public Matrix(int n){
        this(n,n);
    }
    public Matrix(int m,int n,int[][] value){
        this(m,n);
        for(int i=0;i<value.length&&i<m;i++){
            for(int j=0;j<value.length&&j<n;j++){
                this.element[i][j]=value[i][j];
            }
        }
    }
    public int getRows(){
        return this.rows;
    }
    public int getColumns(){
        return this.columns;
    }
    public int get(int i,int j){
        if(i>=0&&i<this.rows&&j>=0&&j<this.columns){
            return this.element[i][j];
        }
        throw new IndexOutOfBoundsException("i="+i+",j+"+j);
    }
    public void set(int i,int j,int x){
        if(i>=0&&i<this.rows&&j>=0&&j<this.columns){
            this.element[i][j]=x;
        }
        throw new IndexOutOfBoundsException("i="+i+",j="+j+",rows="+this.rows+","+this.element.length+",columns="+this.columns+","+this.element[0].length);
    }
    public void setRowsColumns(int m,int n){
        if(m>0&&n>0){
            if(m>this.element.length||n>this.element[0].length){
                int[][] sourse=this.element;
                this.element=new int[m][n];
                for(int i=0;i<sourse.length;i++){
                    for(int j=0;j<sourse[0].length;j++){
                        this.element[i][j]=sourse[i][j];
                    }
                }
            }
            this.rows=m;
            this.columns=n;
        }
        else throw new IllegalArgumentException("矩阵行列数不能小于等于0,m="+m+",n="+n);
    }
    public String toString(){
        String str=" 矩阵"+this.getClass().getName()+"("+this.rows+"*"+this.columns+"):\n";
        for(int i=0;i<this.rows;i++){
            for(int j=0;j<this.columns;j++){
                str=str+String.format("%6d",this.element[i][j]);
            }
            str=str+"\n";
        }
        return str;
    }
}
