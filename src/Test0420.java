import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by XZL on 2017/4/20.
 */
import javafx.application.Application;
import javafx.geometry.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Test0420 extends Application{
    public static void main(String[] args) {
        launch();
        //测试构建意元表的方法是否可行。
        //ArrayList<MeanElement> a=TreeProcess.buildMeanElementTable("E:\\731606673\\FileRecv\\WHOLE.txt");
        /*for(int i=0;i<a.size();i++){
            System.out.println(a.get(i).getIndex());
            System.out.println(a.get(i).getWord());
            System.out.println(a.get(i).getFather());
            System.out.println();
        }*/
        //ArrayList<TreeNode<MeanElement>> b=TreeProcess.buildMeanElementTree(a);
        //System.out.println(b.size()+"\n"+b);
        //测试查找结点的方法是否可行。
        //TreeNode<MeanElement> result=TreeProcess.search("Afghanistan",b);
        //System.out.println(result);
        //测试计算相似度的方法是否可行。
        //System.out.println(TreeProcess.countSimilarly("mean","BeNot",b));
        //ArrayList<Word> c=GraphProcess.buildWordTable("E:\\731606673\\FileRecv\\glossary2.txt",b);
        //System.out.println(c.size());
        /*for (int i=0;i<a.size();i++){
            System.out.println(c.get(i));
        }*/
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ArrayList<MeanElement> a=TreeProcess.buildMeanElementTable("E:\\731606673\\FileRecv\\WHOLE.txt");
        ArrayList<TreeNode<MeanElement>> b=TreeProcess.buildMeanElementTree(a);
        Label l1=new Label("第1个单词：");
        Label l2=new Label("第2个单词：");
        Label l3=new Label("相似度：");
        TextField word1=new TextField();
        TextField word2=new TextField();
        TextField result=new TextField();
        word1.setEditable(true);
        word2.setEditable(true);
        result.setEditable(false);
        Button count=new Button("66455");
        count.setOnAction(event -> {
            String w1=word1.getText();
            String w2=word2.getText();
            if((w1.length()==0)||(w2.length()==0)){
                result.setText("请完全输入后再进行运算");
                return;
            }
            double r=TreeProcess.countSimilarly(w1,w2,b);
            if(r==0){
                result.setText("单词无关联");
            }else{
                result.setText(String.valueOf(r));
            }
        });
        GridPane vBox=new GridPane();
        vBox.add(l1,0,0);
        vBox.add(word1,1,0);
        vBox.add(l2,0,1);
        vBox.add(word2,1,1);
        vBox.add(l3,0,2);
        vBox.add(result,1,2);
        vBox.add(count,1,3);
        vBox.setHgap(5);
        vBox.setVgap(5);
        vBox.setPadding(new Insets(10,10,10,10));
        Scene scene=new Scene(vBox);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
