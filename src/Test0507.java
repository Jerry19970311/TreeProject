import com.sun.xml.internal.fastinfoset.stax.StAXDocumentSerializer;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONArray;
import org.w3c.dom.Element;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

/**
 * Created by XZL on 2017/5/7.
 */
public class Test0507 extends Application{
    protected ABGraph2<Word,MeanElement> graph;
    protected ArrayList<Word> select;
    public static void main(String[] args){
        //launch();
        ArrayList<MeanElement> a=TreeProcess.buildMeanElementTable("E:\\731606673\\FileRecv\\WHOLE.txt");
        ArrayList<TreeNode<MeanElement>> b=TreeProcess.buildMeanElementTree(a);
        ArrayList<Word> c=GraphProcess.buildWordTable("E:\\731606673\\FileRecv\\glossary2.txt",b);
        /*int al=a.size();
        for(int i=0;i<al;i++){
            System.out.println(a.get(i).getDeclaredWord());
        }*/
        /*int cl=c.size();
        for (int i=0;i<cl;i++){
            System.out.println(c.get(i));
        }*/
        //ABGraph<MeanElement,Word> d=GraphProcess.buildGraph(a,c);
        //System.out.println(d.getsVertexList());
        ABGraph2<Word,MeanElement> e=GraphProcess.buildGraph2(c,a);
        //System.out.println(e);
        System.out.println("请输入要构图的单词：");
        Scanner input=new Scanner(System.in);
        String tword=input.next();
        ArrayList<Word> result2=GraphProcess.getSameWords(tword,e);
        if(result2.isEmpty()){
            System.out.println("该词表没有这个单词！");
        }else {
            if (result2.size() == 1) {
                String json = GraphProcess.getLinked(result2.get(0), e);
                GraphProcess.writeStringContentToFile("test.json", json);
                try {
                    Runtime.getRuntime().exec("cmd  /c  start  E:/IdeaProjects/TreeProject/template.htm");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } else {
                System.out.print("该单词有若干个相同单词：" + result2 + "\n请输入该单词的序号：");
                int select = input.nextInt();
                String json = GraphProcess.getLinked(e.getSVertex(select), e);
                GraphProcess.writeStringContentToFile("test.json", json);
                try {
                    Runtime.getRuntime().exec("cmd  /c  start  E:/IdeaProjects/TreeProject/template.htm");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        //System.out.println(result2);
    }
    public void writeToJson(String filePath,JSONArray object) throws IOException
    {
        File file = new File(filePath);
        char [] stack = new char[1024];
        int top=-1;

        String string = object.toString();

        StringBuffer sb = new StringBuffer();
        char [] charArray = string.toCharArray();
        for(int i=0;i<charArray.length;i++){
            char c= charArray[i];
            if ('{' == c || '[' == c) {
                stack[++top] = c;
                sb.append("\n"+charArray[i] + "\n");
                for (int j = 0; j <= top; j++) {
                    sb.append("\t");
                }
                continue;
            }
            if ((i + 1) <= (charArray.length - 1)) {
                char d = charArray[i+1];
                if ('}' == d || ']' == d) {
                    top--;
                    sb.append(charArray[i] + "\n");
                    for (int j = 0; j <= top; j++) {
                        sb.append("\t");
                    }
                    continue;
                }
            }
            if (',' == c) {
                sb.append(charArray[i] + "");
                for (int j = 0; j <= top; j++) {
                    sb.append("");
                }
                continue;
            }
            sb.append(c);
        }

        Writer write = new FileWriter(file);
        write.write(sb.toString());
        write.flush();
        write.close();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ArrayList<MeanElement> a=TreeProcess.buildMeanElementTable("E:\\731606673\\FileRecv\\WHOLE.txt");
        ArrayList<TreeNode<MeanElement>> b=TreeProcess.buildMeanElementTree(a);
        ArrayList<Word> c=GraphProcess.buildWordTable("E:\\731606673\\FileRecv\\glossary2.txt",b);
        ABGraph2<Word,MeanElement> e=GraphProcess.buildGraph2(c,a);
        this.graph=e;
        Label label=new Label("请输入要构图的单词：");
        TextField word=new TextField();
        word.setEditable(true);
        Button button=new Button("构造文件");
        button.setOnAction(event -> {
            String te=word.getText();
            ArrayList<Word> result2=GraphProcess.getSameWords(te,e);
            this.select=result2;
            if(result2.isEmpty()){
                try {
                    new False().start(new Stage());
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }else{
                if(result2.size()==1){
                    String json=GraphProcess.getLinked(result2.get(0),e);
                    GraphProcess.writeStringContentToFile("test.json",json);
                    try {
                        Runtime.getRuntime().exec("cmd  /c  start  E:/IdeaProjects/TreeProject/template.htm");
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }else{
                    new Select().start(new Stage());
                }
            }
        });
        VBox vBox=new VBox(label,word,button);
        Scene scene=new Scene(vBox);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    /*List<Employee> employees = Arrays.<Employee>asList(
            new Employee("Jacob Smith", "Accounts Department"),
            new Employee("Judy Mayer", "IT Support"),
            new Employee("Gregory Smith", "IT Support"));
    TreeItem<String> rootNode =
            new TreeItem<>("MyCompany Human Resources");

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
        rootNode.setExpanded(true);
        for (Employee employee : employees) {
            TreeItem<String> empLeaf = new TreeItem<>(employee.getName());
            boolean found = false;
            for (TreeItem<String> depNode : rootNode.getChildren()) {
                if (depNode.getValue().contentEquals(employee.getDepartment())){
                    depNode.getChildren().add(empLeaf);
                    found = true;
                    break;
                }
            }
            if (!found) {
                TreeItem depNode = new TreeItem(employee.getDepartment());
                rootNode.getChildren().add(depNode);
                depNode.getChildren().add(empLeaf);
            }
        }

        stage.setTitle("Tree View Sample");
        VBox box = new VBox();
        final Scene scene = new Scene(box, 400, 300);
        scene.setFill(Color.LIGHTGRAY);

        TreeView<String> treeView = new TreeView<>(rootNode);
        treeView.setEditable(true);
        treeView.setCellFactory((TreeView<String> p) ->
                new TextFieldTreeCellImpl());

        box.getChildren().add(treeView);
        stage.setScene(scene);
        stage.show();
    }

    private final class TextFieldTreeCellImpl extends TreeCell<String> {

        private TextField textField;
        private final ContextMenu addMenu = new ContextMenu();

        public TextFieldTreeCellImpl() {
            MenuItem addMenuItem = new MenuItem("Add Employee");
            addMenu.getItems().add(addMenuItem);*/
            /*addMenuItem.setOnAction((ActionEvent t) -> {
                TreeItem newEmployee =
                        new TreeItem<>("New Employee");
                getTreeItem().getChildren().add(newEmployee);
            });*/
        /*}

        @Override
        public void startEdit() {
            super.startEdit();

            if (textField == null) {
                createTextField();
            }
            setText(null);
            setGraphic(textField);
            textField.selectAll();
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();

            setText((String) getItem());
            setGraphic(getTreeItem().getGraphic());
        }

        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(getString());
                    }
                    setText(null);
                    setGraphic(textField);
                } else {
                    setText(getString());
                    setGraphic(getTreeItem().getGraphic());
                    if (
                            !getTreeItem().isLeaf()&&getTreeItem().getParent()!= null
                            ){
                        setContextMenu(addMenu);
                    }
                }
            }
        }

        private void createTextField() {
            textField = new TextField(getString());
            textField.setOnKeyReleased((KeyEvent t) -> {
                if (t.getCode() == KeyCode.ENTER) {
                    commitEdit(textField.getText());
                } else if (t.getCode() == KeyCode.ESCAPE) {
                    cancelEdit();
                }
            });

        }

        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }
    }

    public static class Employee {

        private final SimpleStringProperty name;
        private final SimpleStringProperty department;

        private Employee(String name, String department) {
            this.name = new SimpleStringProperty(name);
            this.department = new SimpleStringProperty(department);
        }

        public String getName() {
            return name.get();
        }

        public void setName(String fName) {
            name.set(fName);
        }

        public String getDepartment() {
            return department.get();
        }

        public void setDepartment(String fName) {
            department.set(fName);
        }
    }*/
}
