import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Created by XZL on 2017/5/24.
 */
public class False extends Stage{
    public void start(Stage primaryStage) throws Exception {
        Label label=new Label("该单词不在词表中！");
        Button button=new Button("确定");
        button.setOnAction(event -> {
            primaryStage.close();
        });
        VBox vBox=new VBox(label,button);
        vBox.setAlignment(Pos.CENTER);
        Scene scene=new Scene(vBox);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
