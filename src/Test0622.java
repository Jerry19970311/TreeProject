import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

/**
 * Created by XZL on 2017/6/22.
 */
public class Test0622 {
    public static Statement statement;
    public static Connection connection;
    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
        init();
        Scanner input=new Scanner(System.in);
        String s=input.next();
        List<String> list=maximumMatching("去年奥运会期间，王宝强的离婚声明爆出妻子马蓉出轨经纪人宋喆的消息满世界飞扬，真是刷新了小编的三观！");
        System.out.println(list);
        //start2();
        /*String SQL2="SELECT * FROM words WHERE word='去年'";
        ResultSet n=statement.executeQuery(SQL2);
        if(n.next()) {
            System.out.println(n.getString(1));
        }*/

        /*String SQL="INSERT INTO words VALUES ('你')";
        statement.execute(SQL);*/
    }
    public static void init() throws ClassNotFoundException, SQLException {
        String connectionUrl = "jdbc:sqlserver://localhost:1433;" +
                "databaseName=StudentDB;integratedSecurity=true;";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        connection= DriverManager.getConnection(connectionUrl);
        statement=connection.createStatement();
    }
    //正向最大匹配算法（MM算法），最长词优先匹配
    public static List<String> maximumMatching(String text) throws SQLException {
        //result存储分词后的结果
        List<String> result = new ArrayList<>();
        while(text.length() > 0){
            int len = 13;
            //如果待切分字符串的长度小于词典中的最大词长，则令最大词长等于待切分字符串的长度
            if(text.length() < len){
                len = text.length();
            }
            //取指定的最大长度的文本去词典里面匹配
            String tryWord = text.substring(0, 0+len);
            String SQL2="SELECT * FROM words WHERE word='"+tryWord+"'";
            ResultSet n=statement.executeQuery(SQL2);
            boolean have=n.next();
            while(!have){
                //如果长度已经减为一且在词典中仍未找到匹配，则按长度为一切分
                if(tryWord.length() == 1){
                    break;
                }
                //如果匹配不到，则长度减一继续匹配
                tryWord = tryWord.substring(0, tryWord.length()-1);
                SQL2="SELECT * FROM words WHERE word='"+tryWord+"'";
                n=statement.executeQuery(SQL2);
                have=n.next();
            }
            result.add(tryWord);
            //从待切分字符串中去除已经分词完的部分
            text = text.substring(tryWord.length());
        }
        return result;
    }
    public static void start() throws IOException, SQLException {
        FileReader fr=null;
        try {
            fr=new FileReader("E:\\Jenny2.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader br=new BufferedReader(fr);
        String s;
        do{
            s=br.readLine();
            if(s!=null&&s.contains("】")){
                String word=s.split("】")[0];
                word=word.replaceAll("【","");
                String add="INSERT INTO words VALUES ('"+word+"')";
                try {
                    statement.execute(add);
                }catch (SQLException e){
                }
            }
        }while(s!=null);
    }
    public static void start2() throws IOException {
        FileReader fr=null;
        try {
            fr=new FileReader("E:\\731606673\\FileRecv\\glossary2.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader br=new BufferedReader(fr);
        String s=null;
        do{
            s=br.readLine();
            if(s!=null){
                s=s.replaceAll("\t"," ");
                String word=s.split(" ")[0];
                String add="INSERT INTO words VALUES ('"+word+"')";
                try {
                    statement.execute(add);
                }catch (SQLException e){
                }
            }
        }while (s!=null);
    }
}
