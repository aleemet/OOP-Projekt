/**
 * Created by Alar on 07/04/2016.
 */

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;

public class GUI extends Application {

    private int laius = 550;
    private int kõrgus = 480;

    public static void teatekast(String lühiteade, String pikemteade){
        Alert teade = new Alert(Alert.AlertType.INFORMATION, pikemteade, ButtonType.OK);
        teade.setHeaderText(lühiteade);
        teade.setTitle("Tulemus");
        teade.showAndWait();
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    private static void game(GridPane gp, Button button,Background item){
        button.setBackground(item);
        Võidukontroll.setCurrentRow(gp.getRowIndex(button));
        Võidukontroll.setCurrentCol(gp.getColumnIndex(button));
        Mänguväli.updateGrid();
        Võidukontroll.checkGameOver();
        Võidukontroll.changeMove();

    }

    private static GridPane ruudustik() throws Exception{
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
                    if (Võidukontroll.isPlayer1Turn() && a.getBackground() == tühi && !Võidukontroll.isPlayer1Won() && !Võidukontroll.isPlayer2Won() ){
                        game(nupud, a, rist);

                    }
                    else if (!Võidukontroll.isPlayer1Turn() && a.getBackground() == tühi && !Võidukontroll.isPlayer1Won() && !Võidukontroll.isPlayer2Won() ){
                        game(nupud, a, ring);

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
    private GridPane valikud(Stage primaryStage, ChoiceDialog valikukast) throws Exception{
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
        uusMäng.setOnAction(event ->{
            primaryStage.close();
            try {
                resetGame();
                Võidukontroll.whoStarts();
                gameTypeFriend(laius, kõrgus, primaryStage, valikukast);
            } catch (Exception e) {
                System.out.println("Mingi tõrge");
                System.exit(1);
            }
        });
        nupud.add(uusMäng, 0,1);

        Button vahetaVastast = new Button("Vaheta vastast");
        vahetaVastast.setOnAction(event->{
            try {
                primaryStage.close();
                resetGame();
                start(primaryStage);
            } catch (Exception e) {
                System.out.println("Mingi IO tõrge");
                System.exit(1);
            }
        });
        nupud.add(vahetaVastast, 0,2);

        return nupud;
    }

    private void resetGame(){
        Võidukontroll.setMoveCount(1);
        Mänguväli.resetField();
        Võidukontroll.setPlayer1Won(false);
        Võidukontroll.setPlayer2Won(false);
    }

    private void gameTypeFriend(int laius, int kõrgus, Stage primaryStage, ChoiceDialog valikukast)throws Exception{
        Group root = new Group();
        GridPane gp = new GridPane();
        GridPane mänguväli = ruudustik();
        gp.add(mänguväli, 0,0);
        GridPane valikud = valikud(primaryStage, valikukast);
        gp.add(valikud, 1,0);
        root.getChildren().addAll(gp);
        Scene scene = new Scene(root, laius, kõrgus);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        ChoiceDialog<String> valikukast = new ChoiceDialog<>("Sõber","Arvuti");
        valikukast.setTitle("Vali vastane");
        valikukast.setHeaderText("Kelle vastu mängida soovid?");
        valikukast.showAndWait();
        Võidukontroll.whoStarts();

        if (valikukast.getResult().equals("Sõber")){
            gameTypeFriend(laius, kõrgus, primaryStage, valikukast);
        }
        if (valikukast.getResult().equals("Arvuti")){
            System.out.println("See funktsionaalsus puudub.");
            System.exit(0);
        }

    }
}
