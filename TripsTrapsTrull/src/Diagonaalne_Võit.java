import java.io.IOException;

/**
 * Created by Alar on 12/03/2016.
 */
public class Diagonaalne_Võit extends Võidukontroll {

    // Kontrollib esimest või teist diagonaali, sõltuvalt "first_if_true" tõeväärtusest.
    // Kui first_if_true on tõene, siis kontrollitakse peadiagonaali, vastasel juhul kõrvaldiagonaali.
    public static void diagonalCheck(boolean first_if_true){
        for (int i = 0; i < Mänguväli.getRowAmt(); i++) {
            if (Mänguväli.getField()[i][first_if_true?i:Mänguväli.getColAmt() - i - 1] != (Võidukontroll.isPlayer1Turn()?1:2)) {
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

    // Meetod mõlema diagonaali järjestikuseks kontrollimiseks.
    public static void checkDiagonalVictory() throws IOException{
        boolean onFirstDiagonal = Võidukontroll.getCurrentRow() == Võidukontroll.getCurrentCol();
        boolean onSecondDiagonal = Võidukontroll.getCurrentRow() == Mänguväli.getColAmt() - Võidukontroll.getCurrentCol() - 1;

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
            whoWon();
        }


    }
}
