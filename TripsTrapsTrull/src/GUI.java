/**
 * Created by Alar on 07/04/2016.
 */

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;

public class GUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public static void play(GridPane gp, Button button,Background item){
        button.setBackground(item);
        Võidukontroll.setCurrentRow(gp.getRowIndex(button));
        Võidukontroll.setCurrentCol(gp.getColumnIndex(button));
        Mänguväli.updateGrid();
        Võidukontroll.checkGameOver();
        Võidukontroll.changeMove();

    }

    private static GridPane ruudustik(){
        GridPane nupud = new GridPane();

        for (int i = 0; i<3; i++){
            for (int j = 0; j<3; j++){


                Button a = new Button();
                a.setPrefSize(135, 135);
                BackgroundImage pilt = new BackgroundImage(new Image("blank.png" ),BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT );
                Background tühi = new Background(pilt);
                BackgroundImage piltrist = new BackgroundImage(new Image("cross.png" ),BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT );
                Background rist = new Background(piltrist);
                BackgroundImage piltring = new BackgroundImage(new Image("circle.png" ),BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT );
                Background ring = new Background(piltring);
                a.setBackground(tühi);
                a.setOnAction(event-> {
                    if (Võidukontroll.isPlayer1Turn() && a.getBackground() == tühi ){
                        play(nupud, a, rist);

                    }
                    else if (!Võidukontroll.isPlayer1Turn() && a.getBackground() == tühi ){
                        play(nupud, a, ring);

                    }
                });
                nupud.add(a, i, j);
                nupud.setMargin(a, new Insets(12,11,12,12));

            }
        }
        BackgroundImage pilt = new BackgroundImage(new Image("mänguväljak2.png" ),BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT );
        Background taust = new Background(pilt);
        nupud.setBackground(taust);
        return nupud;
    }
    private static GridPane valikud(){
        GridPane nupud = new GridPane();
        Label kelleKäik = new Label("");
        nupud.add(kelleKäik, 0,0);
        if (Võidukontroll.isPlayer1Turn()){
            kelleKäik.setText("X kord");
        }
        else if (!Võidukontroll.isPlayer1Turn()){
            kelleKäik.setText("O kord");
        }

        Button uusMäng = new Button("Uus mäng");
        nupud.add(uusMäng, 0,1);
        return nupud;
    }


    @Override
    public void start(Stage primaryStage) throws IOException {
        Võidukontroll.whoStarts();
        int laius = 550;
        int kõrgus = 480;

        Group root = new Group();

        GridPane gp = new GridPane();


        GridPane mänguväli = ruudustik();
        gp.add(mänguväli, 0,0);
        GridPane valikud = valikud();
        gp.add(valikud, 1,0);

        root.getChildren().addAll(gp);
        Scene scene = new Scene(root, laius, kõrgus);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
