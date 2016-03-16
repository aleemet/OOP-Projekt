/**
 * Created by Alar on 12/03/2016.
 */
public class Võidukontroll {
    // Konstandid
    public static final int ROW_AMT = 3; // Ridade arv väljal
    public static final int COL_AMT = 3; // Tulpade arv väljal

    // Võidutingimused
    public static boolean isGameOver = false;
    public static boolean player1Won = false;
    public static boolean player2Won = false;
    public static boolean isDraw = false;
    // Võidukontroll
    public static int currentRow;
    public static int currentCol;
    public static boolean player1Turn = true;
    public static int moveCount = 1;

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

    public static void whosTurn(){
        if (player1Turn) {
            player1Won = true;
        }
        else {
            player2Won = true;
        }
    }

    public static void checkVictory() {
        // Horisontaalkontroll
        Risti_Võit.checkCrossVictory();

        // Vertikaalkontroll
        //Vertikaalne_Võit.checkVerticalVictory();

        // Diagonaalkontroll
        Diagonaalne_Võit.checkDiagonalVictory();
    }


    public static void checkDraw() {
        isDraw = moveCount == ROW_AMT * COL_AMT;
        if (isDraw) {
            System.out.println("Viik");
            System.exit(0);
        }
    }

    public static void checkGameOver() {
        checkVictory();
        checkDraw();
    }

    public static void changeMove() {
        player1Turn = !player1Turn;
        moveCount += 1;
    }

}
