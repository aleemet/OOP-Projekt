/**
 * Created by Alar on 02/05/2016.
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class katse extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Button btn = new Button();
        btn.setText("Play by resizing the window");
        VBox root = new VBox();
        root.getChildren().add(btn);
        root.setStyle("-fx-background-color: gray");

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.minWidthProperty().bind(scene.heightProperty().multiply(2));
        primaryStage.minHeightProperty().bind(scene.widthProperty().divide(2));
        primaryStage.show();
    }
}