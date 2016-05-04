import javafx.scene.control.ChoiceDialog;

import java.io.IOException;
import java.util.Random;

/**
 * Created by Alar on 12/03/2016.
 */
public class Võidukontroll {

    // Võidutingimuste isendid
    private static boolean player1Won = false;
    private static boolean player2Won = false;
    private static boolean isDraw = false;
    // Võidukontrolli isendid
    private static int currentRow;
    private static int currentCol;
    private static boolean player1Turn = false;
    private static int moveCount = 1;
    private static boolean AIstarts = false;


    public static boolean isAIstarts() {
        return AIstarts;
    }

    public static boolean isPlayer1Won() {
        return player1Won;
    }

    public static boolean isPlayer2Won() {
        return player2Won;
    }

    public static boolean isDraw() {
        return isDraw;
    }

    public static int getCurrentRow() {
        return currentRow;
    }

    public static int getCurrentCol() {
        return currentCol;
    }

    public static boolean isPlayer1Turn() {
        return player1Turn;
    }

    public static int getMoveCount() {
        return moveCount;
    }

    public static void setMoveCount(int moveCount) {
        Võidukontroll.moveCount = moveCount;
    }

    public static void setCurrentRow(int currentRow) {
        Võidukontroll.currentRow = currentRow;
    }

    public static void setCurrentCol(int currentCol) {
        Võidukontroll.currentCol = currentCol;
    }

    public static void setPlayer1Won(boolean player1Won) {
        Võidukontroll.player1Won = player1Won;
    }

    public static void setPlayer2Won(boolean player2Won) {
        Võidukontroll.player2Won = player2Won;
    }

    public static void setPlayer1Turn(boolean player1Turn) {
        Võidukontroll.player1Turn = player1Turn;
    }

    // Võitja määramine
    public static void whoWon() throws IOException{
        if (player1Won) {
            GUI.teatekast("X võitis", "X võitis! Võid alustada uut mängu.");
            try(Logipidaja a = new Logipidaja("mängulogi.txt")){
                a.write("Käigu järel oli võitja mängija X." + System.lineSeparator()+ System.lineSeparator());

            };

            //System.out.println("Mängija 1 võitis.");
            //System.exit(0);
        }
        else if (player2Won) {
            GUI.teatekast("O võitis!", "O võitis! Võid alustada uut mängu.");
            try(Logipidaja a = new Logipidaja("mängulogi.txt")){
                a.write("Käigu järel oli võitja mängija O." + System.lineSeparator()+ System.lineSeparator());
            };
        }
    }

    // Määrame, kelle käik on ja temal on võimalus olla võitja pärast võidukontrolli läbimist.
    public static void whosTurn(){
        if (player1Turn) {
            player1Won = true;
        }
        else {
            player2Won = true;
        }
    }
    // Kontrollime, kas keegi võitis.
    public static void checkVictory() throws IOException{
        // Horisontaal- ja vertikaalkontroll
        Risti_Võit.checkCrossVictory();

        // Diagonaalkontroll
        if (!player1Won && !player2Won)
            Diagonaalne_Võit.checkDiagonalVictory();
    }

    // Kontrollime, kas tegemist on viigiga
    public static void checkDraw() throws IOException{
        isDraw = moveCount == Mänguväli.getRowAmt() * Mänguväli.getColAmt();
        if (isDraw) {
            GUI.teatekast("Viik", "Mäng jäi viiki! Võid alustada uut mängu.");
            try(Logipidaja a = new Logipidaja("mängulogi.txt")){
                a.write("Mäng jäi viiki." + System.lineSeparator()+ System.lineSeparator());
            };
        }
    }

    // Kontrollime, kas on mäng on läbi.
    public static void checkGameOver() throws IOException{
        checkVictory();
        if(!player1Won && !player2Won) {
            checkDraw();
        }
        //if(!player1Won && !player2Won && !isDraw){
            //try(Logipidaja a = new Logipidaja("mängulogi.txt")){
            //    a.write("Mäng jätkub."+ System.lineSeparator()+""+ System.lineSeparator());
            //};
        //}
    }

    // Lidame iga käigu järel käikude arvule ühe juurde.
    public static void changeMove() {
        player1Turn = !player1Turn;
        moveCount++;
    }

    public static void whoStarts(String valik){
        Random generaator = new Random();
        if (generaator.nextInt(101)%2 == 0) {
            player1Turn = false;
            //System.out.println("O alustab.");
        }
        else{
            player1Turn = true;
            //System.out.println("X alustab.");
        }
        if (valik.equals("Arvuti") && generaator.nextInt(101)%2 == 0){
                AIstarts = true;
        }
        else AIstarts = false;
        //System.out.println(AIstarts);
    }
}
