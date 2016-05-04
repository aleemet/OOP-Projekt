import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alar on 12/03/2016.
 */
public class Mänguväli {
    // Väljak ja väljaku mõõtmete isendid
    private static final int ROW_AMT = 3; // Ridade arv väljal
    private static final int COL_AMT = 3; // Tulpade arv väljal
    private static int[][] field = new int[ROW_AMT][COL_AMT];
    private static List<String> käiguAjad = new ArrayList<>();

    // Meetod väljaku värskendamiseks ja väljastamiseks
    public static void updateGrid(boolean AIifTrue) throws IOException{
        field[Võidukontroll.getCurrentRow()][Võidukontroll.getCurrentCol()] = Võidukontroll.isPlayer1Turn()?1:2;
        try(Logipidaja a = new Logipidaja("mängulogi.txt")){
            a.write((Võidukontroll.isPlayer1Turn()?"X":"O")+ (AIifTrue?" ehk arvuti":"") + " sooritas käigu koordinaatidele " + Võidukontroll.getCurrentRow()+", " +  Võidukontroll.getCurrentCol() + ": "+System.lineSeparator());
            käiguAjad.add(a.getTimeStamp());
            a.write(System.lineSeparator()+grid()+System.lineSeparator());

        };
    }

    public static List<String> getKäiguAjad() {
        return käiguAjad;
    }

    public static void setKäiguAjad(List<String> käiguAjad) {
        Mänguväli.käiguAjad = käiguAjad;
    }

    public static int getRowAmt() {
        return ROW_AMT;
    }

    public static int getColAmt() {
        return COL_AMT;
    }

    public static int[][] getField() {
        return field;
    }

    public static void resetField() {
        field = new int[ROW_AMT][COL_AMT];
        käiguAjad.clear();

    }

    public static void undoMove(int koordinaat1, int koordinaat2){
        field[koordinaat1][koordinaat2] = 0;
    }

    /*
            Väljaku väljastamine.
            Väljak näeb välja selline:

             O | X | X
            -----------
             X |   |
            -----------
               |   | O

            */
    public static String  grid() {
        String vastus = "";
        for (int i = 0; i < ROW_AMT; i++) {
            for (int j = 0; j < COL_AMT; j++) {
                if (field[i][j] == 0) {
                    vastus += "   ";
                }
                else if (field[i][j] == 1) {
                    vastus += " X ";
                }
                else if (field[i][j] == 2){
                    vastus += " O ";
                }

                if (j != COL_AMT - 1) {
                    vastus += "|";
                }
            }
            vastus += System.lineSeparator();

            if (i != ROW_AMT - 1) {
                vastus += "-----------"+System.lineSeparator();
            }
        }
        return vastus;
    }
}
