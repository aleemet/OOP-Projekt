/**
 * Created by Alar on 12/03/2016.
 */
public class Diagonaalne_Võit extends Võidukontroll {

    // Kontrollib esimest või teist diagonaali, sõltuvalt "first_if_true" tõeväärtusest.
    // Kui first_if_true on tõene, siis kontrollitakse peadiagonaali, vastasel juhul kõrvaldiagonaali.
    public static void diagonalCheck(boolean first_if_true){
        for (int i = 0; i < Mänguväli.ROW_AMT; i++) {
            if (Mänguväli.field[i][first_if_true?i:Mänguväli.COL_AMT - i - 1] != (player1Turn?1:2)) {
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

    // Meetod mõlema diagonaali järjestikuseks kontrollimiseks.
    public static void checkDiagonalVictory() {
        boolean onFirstDiagonal = currentRow == currentCol;
        boolean onSecondDiagonal = currentRow == Mänguväli.COL_AMT - currentCol - 1;

        // Kui värskelt lisatud märk ei asu kummagil diagonaalil.
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
