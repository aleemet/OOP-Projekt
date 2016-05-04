/**
 * Created by Alar on 12/03/2016.
 */
public class Risti_Võit extends Võidukontroll {

    // Kontrollib rida või veergu, sõltuvalt "row_if_true" tõeväärtusest.
    // Kui row_if_true on tõene, kontrollitakse ridu, vastasel juhul veerge.
    public static void checkCross(boolean row_if_true, int row_col){
        for (int j = 0; j < (row_if_true?Mänguväli.ROW_AMT:Mänguväli.COL_AMT); j++) {
            if (Mänguväli.field[row_if_true?row_col:j][row_if_true?j:row_col] != (player1Turn?1:2)) { // https://en.wikipedia.org/wiki/%3F:
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

    // Meetod ridade ja veergude järjestikuseks kontrollimiseks.
    public static void checkCrossVictory() {

        whosTurn();
        checkCross(true, currentRow);
        whoWon();

        whosTurn();
        checkCross(false, currentCol);
        whoWon();
    }
}
