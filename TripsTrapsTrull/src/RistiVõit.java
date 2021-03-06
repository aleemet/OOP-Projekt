import java.io.IOException;

/**
 * Created by Alar on 12/03/2016.
 */

// Klass veergude ja ridade kontrolliks.
public class RistiVõit extends Võidukontroll {

    // Kontrollib rida või veergu, sõltuvalt "row_if_true" tõeväärtusest.
    // Kui row_if_true on tõene, kontrollitakse ridu, vastasel juhul veerge.
    public static void checkCross(boolean row_if_true, int row_col){
        for (int j = 0; j < (row_if_true?Mänguväli.getRowAmt():Mänguväli.getColAmt()); j++) {
            if (Mänguväli.getField()[row_if_true?row_col:j][row_if_true?j:row_col] != (Võidukontroll.isPlayer1Turn()?1:2)) { // https://en.wikipedia.org/wiki/%3F:
                if (Võidukontroll.isPlayer1Turn()) {
                    Võidukontroll.setPlayer1Won(false);
                }
                else {
                    Võidukontroll.setPlayer2Won(false);
                }
                break;
            }
        }
    }

    // Meetod ridade ja veergude järjestikuseks kontrollimiseks.
    public static void checkCrossVictory() throws IOException {

        whosTurn();
        checkCross(true, Võidukontroll.getCurrentRow());
        whoWon();
        if (!Võidukontroll.isPlayer1Won() && !Võidukontroll.isPlayer2Won()) {
            whosTurn();
            checkCross(false, Võidukontroll.getCurrentCol());
            whoWon();
        }
    }
}
