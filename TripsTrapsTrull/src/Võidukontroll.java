/**
 * Created by Alar on 12/03/2016.
 */
public class Võidukontroll {

    // Võidutingimuste isendid
    public static boolean player1Won = false;
    public static boolean player2Won = false;
    public static boolean isDraw = false;
    // Võidukontrolli isendid
    public static int currentRow;
    public static int currentCol;
    public static boolean player1Turn = true;
    public static int moveCount = 1;

    // Võitja määramine
    public static void whoWon(){
        if (player1Won) {
            System.out.println("Mängija 1 võitis.");
            System.exit(0);
        }
        else if (player2Won) {
            System.out.println("Mängija 2 võitis.");
            System.exit(0);
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
            System.exit(0);
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

}
