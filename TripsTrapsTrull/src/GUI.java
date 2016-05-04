/**
 * Created by Alar on 07/04/2016.
 */

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

    private BackgroundImage pilt = new BackgroundImage(new Image("blank.png" ),BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100,100,true,true,true,false) );
    private Background tühi = new Background(pilt);
    private BackgroundImage piltrist = new BackgroundImage(new Image("cross.png" ),BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100,100,true,true,true,false) );
    private Background rist = new Background(piltrist);
    private BackgroundImage piltring = new BackgroundImage(new Image("circle.png" ),BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100,100,true,true,true,false) );
    private Background ring = new Background(piltring);
    private Label märkKelleKäik = new Label("");

    public static void teatekast(String lühiteade, String pikemteade) {
        Alert teade = new Alert(Alert.AlertType.INFORMATION, pikemteade, ButtonType.OK);
        teade.setHeaderText(lühiteade);
        teade.setTitle("Tulemus");
        teade.showAndWait();
    }

    public void setMärkKelleKäik(){
        if (Võidukontroll.isPlayer1Turn()){
            märkKelleKäik.setText("X kord");
        }
        else if (!Võidukontroll.isPlayer1Turn()){
            märkKelleKäik.setText("O kord");
        }
    }

    public Node getNodeByRowColumnIndex(final int row, final int column, GridPane gridPane) {
        Node result = null;
        ObservableList<Node> childrens = gridPane.getChildren();
        for(Node node : childrens) {
            if(gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    private void buttonBackground(GridPane nupud, Button button, String valik){
        button.setOnAction(event-> {
            if (Võidukontroll.isPlayer1Turn() && button.getBackground() == tühi && !Võidukontroll.isPlayer1Won() && !Võidukontroll.isPlayer2Won() ){
                try {
                    if (valik.equals("Sõber")){
                        gameFriend(nupud, button, rist);
                        nupuvajutused.add(button);
                    }
                    else if (valik.equals("Arvuti")){
                        gameAI(nupud, button, rist, ring);
                    }

                } catch (IOException e) {
                    System.out.println("IOException 1: logi tegemine risti paigutamisel");
                    System.exit(0);
                }
            }
            else if (!Võidukontroll.isPlayer1Turn() && button.getBackground() == tühi && !Võidukontroll.isPlayer1Won() && !Võidukontroll.isPlayer2Won() ){
                try {
                    if (valik.equals("Sõber")) {
                        gameFriend(nupud, button, ring);
                        nupuvajutused.add(button);
                    }
                    else if (valik.equals("Arvuti")){
                        gameAI(nupud, button, ring, rist);
                    }
                } catch (IOException e) {
                    System.out.println("IOException 2: logi tegemine ringi paigutamisel");
                    System.exit(0);
                }
            }
        });
    }

    private void gameFriend(GridPane gp, Button button,Background item) throws IOException{
        button.setBackground(item);
        Võidukontroll.setCurrentRow(gp.getRowIndex(button));
        Võidukontroll.setCurrentCol(gp.getColumnIndex(button));
        Mänguväli.updateGrid(false);
        Võidukontroll.checkGameOver();
        Võidukontroll.changeMove();
        setMärkKelleKäik();
    }

    private void gameAI(GridPane gp, Button button,Background item1, Background item2) throws IOException{
        button.setBackground(item1);
        Võidukontroll.setCurrentRow(gp.getRowIndex(button));
        Võidukontroll.setCurrentCol(gp.getColumnIndex(button));
        Mänguväli.updateGrid(false);
        Võidukontroll.checkGameOver();
        Võidukontroll.changeMove();
        nupuvajutused.add(button);
        if (!Võidukontroll.isPlayer1Won() && !Võidukontroll.isPlayer2Won() && !Võidukontroll.isDraw()){
            SuhtlemineAIga.askAIMove();
            Button nupp = (Button)getNodeByRowColumnIndex(Võidukontroll.getCurrentRow(), Võidukontroll.getCurrentCol(), gp);
            nupp.setBackground(item2);
            Mänguväli.updateGrid(true);
            Võidukontroll.checkGameOver();
            Võidukontroll.changeMove();
            nupuvajutused.add(nupp);
        }
    }

    private GridPane ruudustik(String valik) throws IOException{
        GridPane nupud = new GridPane();
        ColumnConstraints tulp1 = new ColumnConstraints();
        tulp1.setPercentWidth(33.3);
        ColumnConstraints tulp2 = new ColumnConstraints();
        tulp2.setPercentWidth(33.3);
        ColumnConstraints tulp3 = new ColumnConstraints();
        tulp3.setPercentWidth(33.3);
        BackgroundImage pilt = new BackgroundImage(new Image("mänguväljak2.png" ),BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100,100,true,true,true,false) );
        Background taust = new Background(pilt);
        nupud.setBackground(taust);

        for (int i = 0; i<3; i++){
            for (int j = 0; j<3; j++){
                Button a = new Button();
                a.setPrefSize(150, 150);
                a.setBackground(tühi);
                buttonBackground(nupud, a, valik);
                nupud.add(a, i, j);
                nupud.setMargin(a, new Insets(3,3,3,3));


            }
        }
        nupud.setOnMouseClicked(event ->{
            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.01), nupud);
            fadeTransition.setFromValue(1);
            fadeTransition.setToValue(0.5);
            fadeTransition.setAutoReverse(true);
            fadeTransition.setCycleCount(2);
            fadeTransition.play();
        });

        return nupud;
    }
    private GridPane valikud(Stage primaryStage, String valik) throws IOException{
        GridPane nupud = new GridPane();

        setMärkKelleKäik();
        märkKelleKäik.setPrefSize(nupuLaius, nupuKõrgus);
        nupud.setMargin(märkKelleKäik, new Insets(3,3,3,3));
        nupud.add(märkKelleKäik, 0,0);

        Button uusMäng = new Button("Uus mäng");
        uusMäng.setOnAction(event ->{
            primaryStage.close();
            try {

                try(Logipidaja a = new Logipidaja("mängulogi.txt")){
                    if (!Võidukontroll.isPlayer1Won() && !Võidukontroll.isPlayer2Won() && !Võidukontroll.isDraw())
                        a.write("Mäng katkestati: alustati uue mänguga."+ System.lineSeparator());
                    else if (Võidukontroll.isPlayer1Won() || Võidukontroll.isPlayer2Won() || Võidukontroll.isDraw()){
                        a.write("Alustati uue mänguga."+ System.lineSeparator());
                    }
                    if (valik.equals("Arvuti")){
                        a.write((Võidukontroll.isAIstarts()?"Arvuti alustab.":"Mängija alustab.") + System.lineSeparator());
                    }
                };
                resetGame();
                Võidukontroll.whoStarts(valik);
                gameType(laius, kõrgus, primaryStage, valik);
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
            } catch (IOException e) {
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
                if (valik.equals("Sõber") && nupuvajutused.size()>0 && !Võidukontroll.isPlayer1Won() && !Võidukontroll.isPlayer2Won() && !Võidukontroll.isDraw()){
                    List<String> ajad = Mänguväli.getKäiguAjad();
                    int[] c = new Logilugeja("mängulogi.txt", ajad.get(ajad.size()-1)).viimaneKäik();
                    ajad.remove(ajad.size()-1);
                    Mänguväli.setKäiguAjad(ajad);

                    Mänguväli.undoMove(c[0], c[1]);
                    Võidukontroll.setMoveCount(Võidukontroll.getMoveCount()-1);
                    Võidukontroll.setPlayer1Turn(!Võidukontroll.isPlayer1Turn());
                    setMärkKelleKäik();
                    nupuvajutused.get(nupuvajutused.size()-1).setBackground(tühi);
                    nupuvajutused.remove(nupuvajutused.size()-1);

                    try(Logipidaja a = new Logipidaja("mängulogi.txt")){
                        a.write("Käik võeti tagasi. Nüüd on taas " + (Võidukontroll.isPlayer1Turn()?"X":"O")+  " kord." + System.lineSeparator());
                        a.write(System.lineSeparator()+Mänguväli.grid()+System.lineSeparator());
                    };
                }
                else if (valik.equals("Arvuti") && !(Võidukontroll.isAIstarts() && nupuvajutused.size()==1) && nupuvajutused.size()>0 && !Võidukontroll.isPlayer1Won() && !Võidukontroll.isPlayer2Won() && !Võidukontroll.isDraw()){
                    List<String> ajad = Mänguväli.getKäiguAjad();
                    int[] c = new Logilugeja("mängulogi.txt", ajad.get(ajad.size()-1)).viimaneKäik();
                    int[] d = new Logilugeja("mängulogi.txt", ajad.get(ajad.size()-2)).viimaneKäik();
                    ajad.remove(ajad.size()-1);
                    ajad.remove(ajad.size()-1);
                    Mänguväli.setKäiguAjad(ajad);

                    Mänguväli.undoMove(c[0], c[1]);
                    Mänguväli.undoMove(d[0], d[1]);
                    Võidukontroll.setMoveCount(Võidukontroll.getMoveCount()-2);
                    nupuvajutused.get(nupuvajutused.size()-1).setBackground(tühi);
                    nupuvajutused.remove(nupuvajutused.size()-1);
                    nupuvajutused.get(nupuvajutused.size()-1).setBackground(tühi);
                    nupuvajutused.remove(nupuvajutused.size()-1);
                    try(Logipidaja a = new Logipidaja("mängulogi.txt")){
                        a.write("Mängija ja seega ka viimane arvuti käik võeti tagasi. Nüüd taas mängija kord" + System.lineSeparator());
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
                    if (Võidukontroll.isAIstarts() && nupuvajutused.size()==1){
                        teatekast("Käiku ei võetud tagasi.", "Sina pole veel oma käiku teinud!");
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
        nupuvajutused.clear();
        Mänguväli.setKäiguAjad(new ArrayList<>());
        Võidukontroll.setMoveCount(1);
        Mänguväli.resetField();
        Võidukontroll.setPlayer1Won(false);
        Võidukontroll.setPlayer2Won(false);
    }

    public void gameType(int laius, int kõrgus, Stage primaryStage, String valik)throws IOException{
        Group root = new Group();
        GridPane gp = new GridPane();
        GridPane mänguväli = ruudustik(valik);
        mänguväli.setMinSize(150, 150);
        gp.add(mänguväli, 0,0);
        GridPane valikud = valikud(primaryStage, valik);

        gp.add(valikud, 1,0);
        root.getChildren().addAll(gp);
        Scene scene = new Scene(root, laius, kõrgus);

        scene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                //if (mänguväli.getWidth() < primaryStage.getWidth())
                mänguväli.setPrefWidth((double)newSceneWidth-nupuLaius-5);
                //if (((double)newSceneWidth-(double)oldSceneWidth) < 200){
                mänguväli.setPrefHeight((double)newSceneWidth-nupuLaius-5);

                double vahe = primaryStage.getWidth()- (double)newSceneWidth;
                //gp.setPrefHeight((double)newSceneWidth-nupuLaius-5);
                //System.out.println(primaryStage.getHeight()+", "+ gp.getHeight());

                //

                //gp.setPrefHeight((double)newSceneWidth);
                //gp.setPrefWidth((double)newSceneWidth);

                //primaryStage.setHeight(scene.getHeight()+vahe);
            }
        });
        scene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
                //if (mänguväli.getHeight() < primaryStage.getHeight())
                mänguväli.setPrefHeight((double)newSceneHeight);
                //if (((double)newSceneHeight-(double)oldSceneHeight) < 200){
                mänguväli.setPrefWidth((double)newSceneHeight);
                double vahe = primaryStage.getHeight()- (double)newSceneHeight;

                //gp.setPrefWidth((double)newSceneHeight+nupuLaius+5);
                //
                ////+vahe);
                //primaryStage.setHeight();
                //gp.setPrefWidth((double)newSceneHeight);
                //gp.setPrefHeight((double)newSceneHeight);
                //primaryStage.setWidth(scene.getWidth()+vahe);
                //System.out.println(primaryStage.getWidth());
            }
        });

        scene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ESCAPE){
                väljuMängust(primaryStage);
            }
        });
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(event ->{
            väljuMängust(primaryStage);
        });
        if (Võidukontroll.isAIstarts() && Võidukontroll.getMoveCount() == 1){
            SuhtlemineAIga.askAIMove();
            Button nupp = (Button)getNodeByRowColumnIndex(Võidukontroll.getCurrentRow(), Võidukontroll.getCurrentCol(), mänguväli);
            nupp.setBackground(Võidukontroll.isPlayer1Turn()?rist:ring);
            Mänguväli.updateGrid(true);
            Võidukontroll.checkGameOver();
            Võidukontroll.changeMove();
            nupuvajutused.add(nupp);
        }

        primaryStage.setMinHeight(205);
        primaryStage.setMinWidth(280);
        primaryStage.setMaxHeight(kõrgus+8);
        primaryStage.setMaxWidth(laius-12);
        //primaryStage.minWidthProperty().bind(scene.heightProperty().multiply(1.2));
        //primaryStage.minHeightProperty().bind(scene.widthProperty().divide(1.2));
        primaryStage.show();

    }



    public void väljuMängust(Stage primaryStage){
        Dialog väljuMängust = new Dialog();
        väljuMängust.setTitle("Väljumine");
        väljuMängust.setHeaderText("Kas soovite mängust väljuda?");
        väljuMängust.getDialogPane().getButtonTypes().add(ButtonType.YES);
        väljuMängust.getDialogPane().getButtonTypes().add(ButtonType.NO);
        väljuMängust.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                primaryStage.close();
                System.exit(1);
            } else if (response == ButtonType.NO){
                väljuMängust.close();
            }
        });
    }


    @Override
    public void start(Stage primaryStage) throws IOException {
        ChoiceDialog<String> valikukast = new ChoiceDialog<>("Sõber","Arvuti");
        valikukast.setTitle("Vali vastane");
        valikukast.setHeaderText("Kelle vastu mängida soovid?");
        valikukast.setOnCloseRequest(event ->{
            //väljuMängust(primaryStage);
        });
        valikukast.showAndWait();
        String valik = valikukast.getResult();

        Võidukontroll.whoStarts(valik);


        if (valik == null)
            System.exit(0);
        else if (valik.equals("Sõber")){
            try(Logipidaja a = new Logipidaja("mängulogi.txt")){
                a.write("Alustati uue mänguga: vastaseks Sõber"+ System.lineSeparator());
            };
        }
        else if (valik.equals("Arvuti")){
            try(Logipidaja a = new Logipidaja("mängulogi.txt")){
                a.write("Alustati uue mänguga: vastaseks Arvuti"+ System.lineSeparator());
                a.write((Võidukontroll.isAIstarts()?"Arvuti alustab.":"Mängija alustab.") + System.lineSeparator());
            };
        }

        gameType(laius, kõrgus, primaryStage, valik);


    }


}
