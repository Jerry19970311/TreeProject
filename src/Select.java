import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by XZL on 2017/5/24.
 */
public class Select extends Test0507{
    public void start(Stage s){
        RadioButton[] rbs=new RadioButton[super.select.size()];
        final ToggleGroup group=new ToggleGroup();
        for(int i=0;i<rbs.length;i++){
            rbs[i]=new RadioButton(select.get(i).toString());
            rbs[i].setToggleGroup(group);
        }
        Button b=new Button("构造文件");
        b.setOnAction(event -> {
            for(int i=0;i<rbs.length;i++){
                if(rbs[i].isSelected()){
                    String json=GraphProcess.getLinked(select.get(i),graph);
                    GraphProcess.writeStringContentToFile("test.json",json);
                    try {
                        Runtime.getRuntime().exec("cmd  /c  start  E:\\IdeaProjects\\TreeProject\\template.htm");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        VBox vBox=new VBox();
        for(int i=0;i<rbs.length;i++){
            vBox.getChildren().add(rbs[i]);
        }
        vBox.getChildren().add(b);
        Scene scene=new Scene(vBox);
        s.setScene(scene);
        s.show();
    }
}
