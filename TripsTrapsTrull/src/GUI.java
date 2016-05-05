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

// Klass graafilise liidese loomiseks ja opereerimiseks.
public class GUI extends Application {

    // Suurused
    private int laius = 585;
    private int kõrgus = 480;
    private double nupuLaius = 100;
    private double nupuKõrgus = 30;

    // List, kuhu jäetakse nupuvajutused meelde, et oleks lihtsam käiku tagasi võtta.
    private static List<Button> nupuvajutused = new ArrayList<>();

    // Taustapildid nuppudele
    // Pildid seadistatud täielikult katma nuppu vastavalt nupu suurusele.
    private BackgroundImage pilt = new BackgroundImage(new Image("blank.png"),BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100,100,true,true,true,false));
    private Background tühi = new Background(pilt);
    private BackgroundImage piltrist = new BackgroundImage(new Image("cross.png"),BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100,100,true,true,true,false));
    private Background rist = new Background(piltrist);
    private BackgroundImage piltring = new BackgroundImage(new Image("circle.png"),BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100,100,true,true,true,false));
    private Background ring = new Background(piltring);

    // Silt, mis näitab kelle käik on.
    private Label märkKelleKäik = new Label("");

    // http://stackoverflow.com/questions/20825935/javafx-get-node-by-row-and-column
    // Abimeetod ruudustiku liikme leidmiseks ruudustiku koordinaatide abil (vajalik AI käigu sooritamise jaoks).
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

    // Meetod teatekasti kuvamiseks. Kasutatakse võidu, kaotuse, illegaalse nupuvajutuse vms puhul.
    public static void teatekast(String lühiteade, String pikemteade) {
        Alert teade = new Alert(Alert.AlertType.INFORMATION, pikemteade, ButtonType.OK);
        teade.setHeaderText(lühiteade);
        teade.setTitle("Teade");
        teade.showAndWait();
    }

    // Meetod küsimaks üle, kas soovitakse kindlalt mängust väljuda.
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

    // Meetod, mis uuendab silti, mis kuvab kelle käik parajasti käsil on.
    // Kui mäng on läbi, kuvab vastavat lõppseisu.
    public void setMärkKelleKäik(){
        if (Võidukontroll.isPlayer1Turn() && !Võidukontroll.isDraw() && !Võidukontroll.isPlayer1Won() && !Võidukontroll.isPlayer2Won() )
            märkKelleKäik.setText("X kord");
        else if (!Võidukontroll.isPlayer1Turn() && !Võidukontroll.isDraw() && !Võidukontroll.isPlayer1Won() && !Võidukontroll.isPlayer2Won() )
            märkKelleKäik.setText("O kord");
        else if (Võidukontroll.isPlayer1Won())
            märkKelleKäik.setText("X võit");
        else if (Võidukontroll.isPlayer2Won())
            märkKelleKäik.setText("O võit");
        else if (Võidukontroll.isDraw())
            märkKelleKäik.setText("Viik");
    }

    // Meetod käigu registreerimiseks ja mänguseisu kontrollimiseks (kui mängitakse sõbraga).
    private void mängSõber(GridPane gp, Button button,Background item) throws IOException{
        // Määrame nupule vastava tausta.
        button.setBackground(item);

        // Lisame mänguseisu vajutatud nupu koordinaadid ja ajakohastame mänguvälja back-endis.
        Võidukontroll.setCurrentRow(gp.getRowIndex(button));
        Võidukontroll.setCurrentCol(gp.getColumnIndex(button));
        Mänguväli.updateGrid(false);

        // Kontrollime, kas mäng on nüüd läbi. Kui mitte, vahetame käigu sooritajat,
        // jätame nupuvajutuse meelde ja uuendame sildi märget.
        Võidukontroll.checkGameOver();
        Võidukontroll.changeMove();
        nupuvajutused.add(button);
        setMärkKelleKäik();
    }

    // Meetod käigu registreerimiseks ja mänguseisu kontrollimiseks (kui mängitakse arvutiga).
    private void mängArvuti(GridPane gp, Button button,Background item1, Background item2) throws IOException{

        // Mängija poolt sooritatud käigu fikseerimine on sarnane sõbraga mängides.
        mängSõber(gp,button,item1);

        // Küsime käigu arvutilt, kui mäng läbi ei saanud.
        if (!Võidukontroll.isPlayer1Won() && !Võidukontroll.isPlayer2Won() && !Võidukontroll.isDraw()){

            // Määrame arvuti poolt sooritatava käigu, uuendame mängu seisu ja märgistame vastavalt arvuti käigule
            // ühe nupu mänguväljalt.
            SuhtlemineAIga.askAIMove();
            Button nupp = (Button)getNodeByRowColumnIndex(Võidukontroll.getCurrentRow(), Võidukontroll.getCurrentCol(), gp);
            nupp.setBackground(item2);
            Mänguväli.updateGrid(true);

            // Kontrollime omakorda, kas mäng on läbi.
            // Kui ei, siis lisame arvuti käigu samuti nupuvajutuste hulka.
            // Seejärel uuendame silti.
            Võidukontroll.checkGameOver();
            Võidukontroll.changeMove();
            nupuvajutused.add(nupp);
            setMärkKelleKäik();
        }
    }
    // Meetod nupu tausta muutmiseks vajutuse ja mänguseisu põhjal.
    private void buttonBackground(GridPane nupud, Button button, String valik){
        button.setOnAction(event-> {

            // Kui on X kord ja mäng ei ole läbi.
            if (Võidukontroll.isPlayer1Turn() && button.getBackground() == tühi && !Võidukontroll.isPlayer1Won() && !Võidukontroll.isPlayer2Won() ){
                try {

                    // Kui mängitakse sõbra vastu.
                    if (valik.equals("Sõber")){
                        mängSõber(nupud, button, rist);
                    }

                    // Kui mängitakse arvuti vastu.
                    else if (valik.equals("Arvuti")){
                        mängArvuti(nupud, button, rist, ring);
                    }

                } catch (IOException e) {
                    System.out.println("IOException 1: logi tegemine risti paigutamisel");
                    System.exit(0);
                }
            }

            // Kui on O kord ja mäng ei ole läbi.
            else if (!Võidukontroll.isPlayer1Turn() && button.getBackground() == tühi && !Võidukontroll.isPlayer1Won() && !Võidukontroll.isPlayer2Won() ){
                try {

                    // Kui mängitakse sõbra vastu.
                    if (valik.equals("Sõber")) {
                        mängSõber(nupud, button, ring);
                    }

                    // Kui mängitakse arvuti vastu.
                    else if (valik.equals("Arvuti")){
                        mängArvuti(nupud, button, ring, rist);
                    }
                } catch (IOException e) {
                    System.out.println("IOException 2: logi tegemine ringi paigutamisel");
                    System.exit(0);
                }
            }
        });
    }

    // Meetod mänguvälja loomiseks, kus on ühtlasi ka nupud.
    private GridPane ruudustik(String valik) throws IOException{
        GridPane nupud = new GridPane();

        // Määrame tulpadele võrdsed suurused (vajalik akna suuruse muutmisele reageerimiseks).
        ColumnConstraints tulp1 = new ColumnConstraints();
        tulp1.setPercentWidth(50);
        ColumnConstraints tulp2 = new ColumnConstraints();
        tulp2.setPercentWidth(50);
        ColumnConstraints tulp3 = new ColumnConstraints();
        tulp3.setPercentWidth(50);

        // Paneme mänguväljale tausta. Taust seatud suurust muutma vastavalt mänguvälja suuruse muutumisele.
        BackgroundImage pilt = new BackgroundImage(new Image("mänguväljak2.png" ),BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100,100,true,true,true,false) );
        Background taust = new Background(pilt);
        nupud.setBackground(taust);

        // Loome nupud, paneme nad mänguvälja ja määrame neile sündmusele reageerimised.
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

        // Määrame mänguvälja taustale vilkumise efekti näitamaks, et ei vajutatud käigu määramiseks piisavalt täpselt.
        nupud.setOnMouseClicked(event ->{
            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.01), nupud);
            fadeTransition.setFromValue(1);
            fadeTransition.setToValue(0.5);
            fadeTransition.setAutoReverse(true);
            fadeTransition.setCycleCount(2);
            fadeTransition.play();
        });

        // Tagastame terve mänguvälja, et seda hiljem mängu stseeni paigutada saaks.
        return nupud;
    }

    // Meetod mänguseisu lähtestamiseks, et saaks puhtalt lehelt alustada.
    // Tühistame kõik nuppude vajutused, käikude sooritamise ajad, lähtestame käikude arvu,
    // mänguvälja ja tühistame võitja.
    private void lähtestaMäng(){
        nupuvajutused.clear();
        Mänguväli.setKäiguAjad(new ArrayList<>());
        Võidukontroll.setMoveCount(1);
        Mänguväli.resetField();
        Võidukontroll.setPlayer1Won(false);
        Võidukontroll.setPlayer2Won(false);
    }

    // Meetod lisafunktsioonide nuppude loomiseks ja nendele funktsioonide omistamine.
    private GridPane valikud(Stage primaryStage, String valik) throws IOException{
        GridPane nupud = new GridPane();

        // Kuvame sildile alustava mängija. Määrame sildile asukoha ja paigutuse.
        setMärkKelleKäik();
        märkKelleKäik.setPrefSize(nupuLaius, nupuKõrgus);
        nupud.setMargin(märkKelleKäik, new Insets(3,3,3,3));
        nupud.add(märkKelleKäik, 0,0);

        // Loome nupu uue mängu alustamiseks.
        Button uusMäng = new Button("Uus mäng");

        // Määrame uue mängu alustamise nupule funktsiooni.
        uusMäng.setOnAction(event ->{

            // Sulgeme vana mängu akna.
            primaryStage.close();

            // Try-Catch vajalik erindi konsumeerimise vältimiseks.
            try {

                // Kirjutame logisse, et alustati uue mänguga.
                // Eraldi sissekanded, kui mäng on pooleli või lõppenud.
                try(Logipidaja a = new Logipidaja("mängulogi.txt")){
                    if (!Võidukontroll.isPlayer1Won() && !Võidukontroll.isPlayer2Won() && !Võidukontroll.isDraw())
                        a.write("Mäng katkestati: alustati uue mänguga."+ System.lineSeparator());
                    else if (Võidukontroll.isPlayer1Won() || Võidukontroll.isPlayer2Won() || Võidukontroll.isDraw()){
                        a.write("Alustati uue mänguga."+ System.lineSeparator());
                    }
                    // Kui vastaseks on arvuti, kuvame eraldi, kes alustab.
                    if (valik.equals("Arvuti")){
                        a.write((Võidukontroll.isAIstarts()?"Arvuti alustab.":"Mängija alustab.") + System.lineSeparator());
                    }
                };

                // Lähtestame mängu, määrame uue alustaja ja avame uuesti mängu akna.
                lähtestaMäng();
                Võidukontroll.whoStarts(valik);
                mäng(laius, kõrgus, primaryStage, valik);
            } catch (IOException e) {
                System.out.println("IOException uue mängu alustamisel.");
                System.exit(1);
            }
        });

        // Määrame uue mängu alustamise nupule suurused ja asukoha.
        uusMäng.setPrefSize(nupuLaius, nupuKõrgus);
        nupud.setMargin(uusMäng, new Insets(3,3,3,3));
        nupud.add(uusMäng, 0,1);

        // Loome nupu mängu vastase vahetamiseks.
        Button vahetaVastast = new Button("Vaheta vastast");

        // Määrame mängu vastase vahetamise nupule funktsiooni.
        vahetaVastast.setOnAction(event->{

            // Try-Catch vajalik erindi konsumeerimise vältimiseks.
            try {

                // Sulgeme mängu akna ja kirjutame logisse, et vahetatakse vastast.
                // Eraldi sissekanded, kui mäng on pooleli või lõppenud.
                primaryStage.close();
                try(Logipidaja a = new Logipidaja("mängulogi.txt")){
                    if (!Võidukontroll.isPlayer1Won() && !Võidukontroll.isPlayer2Won() && !Võidukontroll.isDraw())
                        a.write("Mäng katkestati: valitakse uus vastane."+ System.lineSeparator());
                    else if (Võidukontroll.isPlayer1Won() || Võidukontroll.isPlayer2Won() || Võidukontroll.isDraw())
                        a.write("Valitakse uus vastane."+ System.lineSeparator());
                };

                // Lähtestame mängu ja taaskäivitame mängu akna.
                lähtestaMäng();
                start(primaryStage);
            } catch (IOException e) {
                System.out.println("IOException vastase vahetamisel.");
                System.exit(1);
            }
        });

        // Määrame vastase vahetamise nupule mõõtmed ja asukoha.
        vahetaVastast.setPrefSize(nupuLaius, nupuKõrgus);
        nupud.setMargin(vahetaVastast, new Insets(3,3,3,3));
        nupud.add(vahetaVastast, 0,2);

        // Loome nupu käigu tagasivõtmiseks.
        Button võtaTagasi = new Button("Võta käik tagasi");

        // Määrame käigu tagasivõtmise nupule funktsiooni.
        // Lubame käiku tagasi võtta ainult siis, kui mängija poolt on sooritatud vähemalt üks käik ja mäng pole läbi.
        võtaTagasi.setOnAction(event ->{

            // Try-Catch vajalik erindi konsumeerimise vältimiseks
            try {

                // Kui mängitakse sõbra vastu, võtame ainult viimase käigu tagasi.
                if (valik.equals("Sõber") && nupuvajutused.size()>0 && !Võidukontroll.isPlayer1Won() && !Võidukontroll.isPlayer2Won() && !Võidukontroll.isDraw()){

                    // Vaatame, millal käimasoleva sessiooni käigud sooritati.
                    // Leiame logist viimase käigu ja selle koordinaadid, kuhu see sooritati.
                    // Seejärel kustutame vastava aja aegade listist.
                    List<String> ajad = Mänguväli.getKäiguAjad();
                    int[] c = new Logilugeja("mängulogi.txt", ajad.get(ajad.size()-1)).otsitavKäik();
                    ajad.remove(ajad.size()-1);
                    Mänguväli.setKäiguAjad(ajad);

                    // Leitud koordinaatide abil kustutame tehtud käigu back-endist, vähendame tehtud käikude arvu,
                    // vahetame käigu sooritaja, uuendame silti, lähtestame viimasena vajutatud käigu nupu tausta
                    // ja kustutame viimase nupuvajutuse.
                    Mänguväli.undoMove(c[0], c[1]);
                    Võidukontroll.setMoveCount(Võidukontroll.getMoveCount()-1);
                    Võidukontroll.setPlayer1Turn(!Võidukontroll.isPlayer1Turn());
                    setMärkKelleKäik();
                    nupuvajutused.get(nupuvajutused.size()-1).setBackground(tühi);
                    nupuvajutused.remove(nupuvajutused.size()-1);

                    // Kirjutame logisse, et käik võeti tagasi ja kirjutame veel sellejärgse mänguseisu.
                    try(Logipidaja a = new Logipidaja("mängulogi.txt")){
                        a.write("Käik võeti tagasi. Nüüd on taas " + (Võidukontroll.isPlayer1Turn()?"X":"O")+  " kord." + System.lineSeparator());
                        a.write(System.lineSeparator()+Mänguväli.grid()+System.lineSeparator());
                    };
                }

                // Kui mängitakse arvuti vastu, võtame lisaks mängija viimase käigule ka arvuti viimase käigu tagasi.
                // Seda seepärast, et arvuti sooritab oma käigu koheselt pärast mängijat.
                else if (valik.equals("Arvuti") && !(Võidukontroll.isAIstarts() && nupuvajutused.size()==1) && nupuvajutused.size()>0 && !Võidukontroll.isPlayer1Won() && !Võidukontroll.isPlayer2Won() && !Võidukontroll.isDraw()){
                    // Sarnaselt sõbraga mängides määrame viimased kaks käiku, kustutame nad sooritatud käikude hulgast.
                    List<String> ajad = Mänguväli.getKäiguAjad();
                    int[] c = new Logilugeja("mängulogi.txt", ajad.get(ajad.size()-1)).otsitavKäik();
                    int[] d = new Logilugeja("mängulogi.txt", ajad.get(ajad.size()-2)).otsitavKäik();
                    ajad.remove(ajad.size()-1);
                    ajad.remove(ajad.size()-1);
                    Mänguväli.setKäiguAjad(ajad);

                    // Sooritatud käikude määramise järel kustutame need käigud back-endist, vähendame sooritatud käikude
                    // arvu ja lähtestame nupud.
                    Mänguväli.undoMove(c[0], c[1]);
                    Mänguväli.undoMove(d[0], d[1]);
                    Võidukontroll.setMoveCount(Võidukontroll.getMoveCount()-2);
                    nupuvajutused.get(nupuvajutused.size()-1).setBackground(tühi);
                    nupuvajutused.remove(nupuvajutused.size()-1);
                    nupuvajutused.get(nupuvajutused.size()-1).setBackground(tühi);
                    nupuvajutused.remove(nupuvajutused.size()-1);

                    // Kirjutame logisse, et käik sai tagasi võetud.
                    try(Logipidaja a = new Logipidaja("mängulogi.txt")){
                        a.write("Mängija ja seega ka viimane arvuti käik võeti tagasi. Nüüd taas mängija kord" + System.lineSeparator());
                        a.write(System.lineSeparator()+Mänguväli.grid()+System.lineSeparator());
                    };

                }
                // Kui käiku ei saanud tagasi võtta, kuvame vastava teate.
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

        // Määrame käigu tagasivõtmise nupule mõõtmed ja asukoha.
        võtaTagasi.setPrefSize(nupuLaius, nupuKõrgus);
        nupud.setMargin(võtaTagasi, new Insets(3,3,3,3));
        nupud.add(võtaTagasi, 0,3);

        // Tagastame lisafunktsioonide nupud, et see hiljem stseeni lisada.
        return nupud;
    }

    // Paneme mängu kokku.
    public void mäng(int laius, int kõrgus, Stage primaryStage, String valik)throws IOException{

        // Loome juurele ruudustiku, millesse lisame mänguvälja vasakusse tulpa ja valikunupud paremasse tulpa.
        Group root = new Group();
        GridPane gp = new GridPane();
        GridPane mänguväli = ruudustik(valik);
        mänguväli.setMinSize(150, 150);
        gp.add(mänguväli, 0,0);
        GridPane valikud = valikud(primaryStage, valik);
        gp.add(valikud, 1,0);
        root.getChildren().addAll(gp);
        Scene scene = new Scene(root, laius, kõrgus);

        // Määrame mänguväljale reageerimise suuruse muutmisele.
        scene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                mänguväli.setPrefWidth((double)newSceneWidth-nupuLaius-5);
                mänguväli.setPrefHeight((double)newSceneWidth-nupuLaius-5);
            }
        });
        scene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
                mänguväli.setPrefHeight((double)newSceneHeight);
                mänguväli.setPrefWidth((double)newSceneHeight);
            }
        });

        // Kui vajutatakse ESCAPE nuppu, küsime üle, kas tahetakse mängust väljuda. Küsime sama, kui mängu ristist sulgeda tahetakse.
        scene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ESCAPE){
                väljuMängust(primaryStage);
            }
        });
        primaryStage.setOnCloseRequest(event ->{
            väljuMängust(primaryStage);
        });

        // Juhul kui mängitakse arvuti vastu ja loositi, et arvuti alustab, laseme arvutil käigu sooritada.
        if (Võidukontroll.isAIstarts() && Võidukontroll.getMoveCount() == 1){
            SuhtlemineAIga.askAIMove();
            Button nupp = (Button)getNodeByRowColumnIndex(Võidukontroll.getCurrentRow(), Võidukontroll.getCurrentCol(), mänguväli);
            nupp.setBackground(Võidukontroll.isPlayer1Turn()?rist:ring);
            Mänguväli.updateGrid(true);
            Võidukontroll.checkGameOver();
            Võidukontroll.changeMove();
            nupuvajutused.add(nupp);
        }

        // Määrame mängu aknale miinimum- ja maksimumsuurused, stseeni ja seejärel kuvame akna.
        primaryStage.setScene(scene);
        primaryStage.setMinHeight(205);
        primaryStage.setMinWidth(280);
        primaryStage.setMaxHeight(kõrgus+8);
        primaryStage.setMaxWidth(laius-12);
        primaryStage.show();
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        // Kui programm käivitatakse, küsime kõigepealt, kellega mängida soovitakse.
        // Selleks loome uue valikukasti ja määrame valikud ja kuvame selle.
        ChoiceDialog<String> valikukast = new ChoiceDialog<>("Sõber","Sõber","Arvuti");
        valikukast.setTitle("Vali vastane");
        valikukast.setHeaderText("Kelle vastu mängida soovid?");
        valikukast.showAndWait();

        // Fikseerime valiku.
        String valik = valikukast.getResult();

        // Loosime alustaja märgi. Kui vastaseks valiti arvuti, määrame ühtlasi, kas alustab arvuti või mängija.
        Võidukontroll.whoStarts(valik);

        // Valiku põhjal määrame mängu.
        // Kui valikukast suleti, sulgeme programmi
        if (valik == null)
            System.exit(0);
            // Kui valiti sõber, kanname selle logisse
        else if (valik.equals("Sõber")){
            try(Logipidaja a = new Logipidaja("mängulogi.txt")){
                a.write("Alustati uue mänguga: vastaseks Sõber"+ System.lineSeparator());
            };
        }
        // Kui valiti arvuti, kanname selle logisse.
        else if (valik.equals("Arvuti")){
            try(Logipidaja a = new Logipidaja("mängulogi.txt")){
                a.write("Alustati uue mänguga: vastaseks Arvuti"+ System.lineSeparator());
                a.write((Võidukontroll.isAIstarts()?"Arvuti alustab.":"Mängija alustab.") + System.lineSeparator());
            };
        }

        // Käivitame mängu vastavalt valikule.
        mäng(laius, kõrgus, primaryStage, valik);
    }
}
