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

    // Võitja määramine
    public static void whoWon(){
        if (player1Won) {
            System.out.println("Mängija 1 võitis.");
            //System.exit(0);
        }
        else if (player2Won) {
            System.out.println("Mängija 2 võitis.");
            //System.exit(0);
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
    public static void checkVictory() {
        // Horisontaal- ja vertikaalkontroll
        Risti_Võit.checkCrossVictory();

        // Diagonaalkontroll
        Diagonaalne_Võit.checkDiagonalVictory();
    }

    // Kontrollime, kas tegemist on viigiga
    public static void checkDraw() {
        isDraw = moveCount == Mänguväli.ROW_AMT * Mänguväli.COL_AMT;
        if (isDraw) {
            System.out.println("Viik");
            //System.exit(0);
        }
    }

    // Kontrollime, kas on mäng on läbi.
    public static void checkGameOver() {
        checkVictory();
        checkDraw();
    }

    // Lidame iga käigu järel käikude arvule ühe juurde.
    public static void changeMove() {
        player1Turn = !player1Turn;
        moveCount++;
    }

    public static void whoStarts(){
        Random generaator = new Random();
        if (generaator.nextInt(101)%2 == 0) {
            player1Turn = false;
            System.out.println("O alustab.");
        }
        else{
            player1Turn = true;
            System.out.println("X alustab.");
        }
    }


}
