/**
 * Created by Alar on 07/04/2016.
 */

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;

public class GUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }



    private static GridPane ruudustik(){
        GridPane nupud = new GridPane();

        for (int i = 0; i<3; i++){
            for (int j = 0; j<3; j++){


                Button a = new Button();
                a.setPrefSize(83, 83);
                nupud.add(a, i, j);
                //nupud.setMargin(a, new Insets(x, y, z, w));
            }
        }
        BackgroundImage pilt = new BackgroundImage(new Image("mänguväljak2.png" ),BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT );
        Background taust = new Background(pilt);
        nupud.setBackground(taust);
        return nupud;
    }
    private static GridPane valikud(){
        GridPane nupud = new GridPane();

        return nupud;
    }


    @Override
    public void start(Stage primaryStage) throws IOException {
        int laius = 450;
        int kõrgus = 400;

        Group root = new Group();

        GridPane gp = new GridPane();


        GridPane nupud = ruudustik();

        gp.getChildren().addAll(nupud);
        root.getChildren().addAll(gp);
        Scene scene = new Scene(root, laius, kõrgus);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
