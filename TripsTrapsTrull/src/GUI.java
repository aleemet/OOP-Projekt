/**
 * Created by Alar on 07/04/2016.
 */

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GUI extends Application {

    private int laius = 585;
    private int kõrgus = 480;
    private double nupuLaius = 100;
    private double nupuKõrgus = 30;
    private static List<Button> nupuvajutused = new ArrayList<>();

    private BackgroundImage pilt = new BackgroundImage(new Image("blank.png" ),BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT );
    private Background tühi = new Background(pilt);
    private BackgroundImage piltrist = new BackgroundImage(new Image("cross.png" ),BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT );
    private Background rist = new Background(piltrist);
    private BackgroundImage piltring = new BackgroundImage(new Image("circle.png" ),BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT );
    private Background ring = new Background(piltring);
    private Label kelleKäik = new Label("");

    public static void teatekast(String lühiteade, String pikemteade) {
        Alert teade = new Alert(Alert.AlertType.INFORMATION, pikemteade, ButtonType.OK);
        teade.setHeaderText(lühiteade);
        teade.setTitle("Tulemus");
        teade.showAndWait();
    }

    public void setKelleKäik(){
        if (Võidukontroll.isPlayer1Turn()){
            kelleKäik.setText("X kord");
        }
        else if (!Võidukontroll.isPlayer1Turn()){
            kelleKäik.setText("O kord");
        }
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    private void game(GridPane gp, Button button,Background item) throws IOException{
        button.setBackground(item);
        Võidukontroll.setCurrentRow(gp.getRowIndex(button));
        Võidukontroll.setCurrentCol(gp.getColumnIndex(button));
        Mänguväli.updateGrid();
        Võidukontroll.checkGameOver();
        Võidukontroll.changeMove();
        setKelleKäik();

    }

    private GridPane ruudustik() throws IOException{
        GridPane nupud = new GridPane();

        for (int i = 0; i<3; i++){
            for (int j = 0; j<3; j++){
                Button a = new Button();
                a.setPrefSize(135, 135);
                a.setBackground(tühi);
                a.setOnAction(event-> {
                    if (Võidukontroll.isPlayer1Turn() && a.getBackground() == tühi && !Võidukontroll.isPlayer1Won() && !Võidukontroll.isPlayer2Won() ){
                        try {
                            game(nupud, a, rist);
                            nupuvajutused.add(a);
                        } catch (IOException e) {
                            System.out.println("IOException 1: logi tegemine risti paigutamisel");
                            System.exit(0);
                        }
                    }
                    else if (!Võidukontroll.isPlayer1Turn() && a.getBackground() == tühi && !Võidukontroll.isPlayer1Won() && !Võidukontroll.isPlayer2Won() ){
                        try {
                            game(nupud, a, ring);
                            nupuvajutused.add(a);
                        } catch (IOException e) {
                            System.out.println("IOException 2: logi tegemine ringi paigutamisel");
                            System.exit(0);
                        }
                    }
                });
                nupud.add(a, i, j);
                nupud.setMargin(a, new Insets(12,11,12,12));
                nupud.setOnMouseClicked(event ->{
                    FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.01), nupud);
                    fadeTransition.setFromValue(1);
                    fadeTransition.setToValue(0.5);
                    fadeTransition.setAutoReverse(true);
                    fadeTransition.setCycleCount(2);
                    fadeTransition.play();
                });

            }
        }
        BackgroundImage pilt = new BackgroundImage(new Image("mänguväljak2.png" ),BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT );
        Background taust = new Background(pilt);
        nupud.setBackground(taust);

        return nupud;
    }
    private GridPane valikud(Stage primaryStage, ChoiceDialog valikukast) throws IOException{
        GridPane nupud = new GridPane();
        setKelleKäik();
        kelleKäik.setPrefSize(nupuLaius, nupuKõrgus);
        nupud.setMargin(kelleKäik, new Insets(3,3,3,3));
        nupud.add(kelleKäik, 0,0);

        Button uusMäng = new Button("Uus mäng");
        uusMäng.setOnAction(event ->{
            primaryStage.close();
            try {

                try(Logipidaja a = new Logipidaja("mängulogi.txt")){
                    if (!Võidukontroll.isPlayer1Won() && !Võidukontroll.isPlayer2Won() && !Võidukontroll.isDraw())
                        a.write("Mäng katkestati: alustati uue mänguga."+ System.lineSeparator());
                    else if (Võidukontroll.isPlayer1Won() || Võidukontroll.isPlayer2Won() || Võidukontroll.isDraw())
                        a.write("Alustati uue mänguga."+ System.lineSeparator());
                };
                resetGame();
                Võidukontroll.whoStarts();
                gameTypeFriend(laius, kõrgus, primaryStage, valikukast);
            } catch (Exception e) {
                System.out.println("IOException uue mängu alustamisel.");
                System.exit(1);
            }
        });
        uusMäng.setPrefSize(nupuLaius, nupuKõrgus);
        nupud.setMargin(uusMäng, new Insets(3,3,3,3));
        nupud.add(uusMäng, 0,1);

        Button vahetaVastast = new Button("Vaheta vastast");
        vahetaVastast.setOnAction(event->{
            try {
                primaryStage.close();

                try(Logipidaja a = new Logipidaja("mängulogi.txt")){
                    if (!Võidukontroll.isPlayer1Won() && !Võidukontroll.isPlayer2Won() && !Võidukontroll.isDraw())
                        a.write("Mäng katkestati: valitakse uus vastane."+ System.lineSeparator());
                    else if (Võidukontroll.isPlayer1Won() || Võidukontroll.isPlayer2Won() || Võidukontroll.isDraw())
                        a.write("Valitakse uus vastane."+ System.lineSeparator());
                };
                resetGame();
                start(primaryStage);
            } catch (Exception e) {
                System.out.println("IOException vastase vahetamisel.");
                System.exit(1);
            }
        });
        vahetaVastast.setPrefSize(nupuLaius, nupuKõrgus);
        nupud.setMargin(vahetaVastast, new Insets(3,3,3,3));
        nupud.add(vahetaVastast, 0,2);

        Button võtaTagasi = new Button("Võta käik tagasi");
        võtaTagasi.setOnAction(event ->{

            try {
                if (nupuvajutused.size()>0 && !Võidukontroll.isPlayer1Won() && !Võidukontroll.isPlayer2Won() && !Võidukontroll.isDraw()){
                    List<String> ajad = Mänguväli.getKäiguAjad();
                    int[] c = new Logilugeja("mängulogi.txt", ajad.get(ajad.size()-1)).viimaneKäik();
                    ajad.remove(ajad.size()-1);
                    Mänguväli.setKäiguAjad(ajad);

                    Mänguväli.undoMove(c[0], c[1]);
                    Võidukontroll.setMoveCount(Võidukontroll.getMoveCount()-1);
                    Võidukontroll.setPlayer1Turn(!Võidukontroll.isPlayer1Turn());
                    setKelleKäik();
                    nupuvajutused.get(nupuvajutused.size()-1).setBackground(tühi);
                    nupuvajutused.remove(nupuvajutused.size()-1);

                    try(Logipidaja a = new Logipidaja("mängulogi.txt")){
                        a.write("Käik võeti tagasi. Nüüd on taas " + (Võidukontroll.isPlayer1Turn()?"X":"O")+  " kord." + System.lineSeparator());
                        a.write(System.lineSeparator()+Mänguväli.grid()+System.lineSeparator());
                    };
                }
                else{
                    if (Võidukontroll.isPlayer1Won() || Võidukontroll.isPlayer2Won() || Võidukontroll.isDraw()){
                        teatekast("Käiku ei võetud tagasi.", "Mäng on juba lõppenud!");
                    }
                    if (nupuvajutused.size() == 0){
                        teatekast("Käiku ei võetud tagasi.", "Mängus pole veel käike sooritatud!");
                    }
                }
            } catch (IOException e) {
                System.out.println("IOException käigu tagasi võtmisel.");
                System.exit(1);
            }
        });
        võtaTagasi.setPrefSize(nupuLaius, nupuKõrgus);
        nupud.setMargin(võtaTagasi, new Insets(3,3,3,3));
        nupud.add(võtaTagasi, 0,3);

        return nupud;
    }

    private void resetGame(){
        Võidukontroll.setMoveCount(1);
        Mänguväli.resetField();
        Võidukontroll.setPlayer1Won(false);
        Võidukontroll.setPlayer2Won(false);
    }

    private void gameTypeFriend(int laius, int kõrgus, Stage primaryStage, ChoiceDialog valikukast)throws IOException{
        Group root = new Group();
        GridPane gp = new GridPane();
        GridPane mänguväli = ruudustik();
        gp.add(mänguväli, 0,0);
        GridPane valikud = valikud(primaryStage, valikukast);
        gp.add(valikud, 1,0);
        root.getChildren().addAll(gp);
        Scene scene = new Scene(root, laius, kõrgus);
        scene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ESCAPE){
                väljuMängust().showAndWait().ifPresent(response -> {
                    if (response == ButtonType.YES) {
                        primaryStage.close();
                        System.exit(1);
                    }else {
                        väljuMängust().close();
                    }
                });
            }
        });
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public Dialog väljuMängust(){
        Dialog väljuMängust = new Dialog();
        väljuMängust.setTitle("Väljumine");
        väljuMängust.setHeaderText("Kas soovite mängust väljuda?");
        väljuMängust.getDialogPane().getButtonTypes().add(ButtonType.YES);
        väljuMängust.getDialogPane().getButtonTypes().add(ButtonType.NO);

        return väljuMängust;
    }


    @Override
    public void start(Stage primaryStage) throws IOException {
        ChoiceDialog<String> valikukast = new ChoiceDialog<>("Sõber","Arvuti");
        valikukast.setTitle("Vali vastane");
        valikukast.setHeaderText("Kelle vastu mängida soovid?");
        valikukast.showAndWait();
        Võidukontroll.whoStarts();
        String valik = valikukast.getResult();

        //primaryStage.addEventHandler(KeyEvent);
        if (valik == null)
            System.exit(0);

        else if (valik.equals("Sõber")){
            try(Logipidaja a = new Logipidaja("mängulogi.txt")){
                a.write("Alustati uue mänguga: vastaseks Sõber"+ System.lineSeparator());
            };
            gameTypeFriend(laius, kõrgus, primaryStage, valikukast);

        }
        else if (valik.equals("Arvuti")){
            System.out.println("See funktsionaalsus puudub.");
            System.exit(0);
        }

    }


}
