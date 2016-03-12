/**
 * Created by Alar on 12/03/2016.
 */
public class Võidukontroll {
    // Konstandid
    public static final int ROW_AMT = 3; // Ridade arv v�ljal
    public static final int COL_AMT = 3; // Tulpade arv v�ljal

    // V�idutingimused
    public static boolean isGameOver = false;
    public static boolean player1Won; //= false;
    public static boolean player2Won; //= false;
    public static boolean isDraw = false;
    // V�idukontroll
    public static int currentRow;
    public static int currentCol;
    public static boolean player1Turn = true;
    public static int moveCount = 0;

    // Mänguväli
    public static int[][] field = new int[ROW_AMT][COL_AMT];


    public static void checkVictory() {
        // Horisontaalkontroll
        Horisontaalne_Võit.checkHorizontalVictory();

        // Vertikaalkontroll
        Vertikaalne_Võit.checkVerticalVictory();

        // Diagonaalkontroll
        Diagonaalne_Võit.checkDiagonalVictory();
    }
    public static void changeMove() {
        player1Turn = !player1Turn;
        moveCount += 1;
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

}
