/**
 * Created by Alar on 12/03/2016.
 */
public class Diagonaalne_Võit extends Võidukontroll {

    // Kontrollib esimest või teist diagonaali, sõltuvalt "first_if_true" tõeväärtusest.
    public static void diagonalCheck(boolean first_if_true){
        for (int i = 0; i < ROW_AMT; i++) {
            if (Mänguväli.field[i][first_if_true?i:COL_AMT - i - 1] != (player1Turn?1:2)) {
                if (player1Turn) {
                    player1Won = false;
                }
                else {
                    player2Won = false;
                }
                break;
            }
        }
    }

    public static void checkDiagonalVictory() {
        boolean onFirstDiagonal = currentRow == currentCol;
        boolean onSecondDiagonal = currentRow == COL_AMT - currentCol - 1;

        if (!onFirstDiagonal && !onSecondDiagonal) {
            return;
        }


        // Kontrollib millise diagonaaliga on tegemist
        if (onFirstDiagonal) {
            whosTurn();
            diagonalCheck(true);
        }
        whoWon();

        if (onSecondDiagonal) {
            whosTurn();
            diagonalCheck(false);
        }

        whoWon();
    }
}
