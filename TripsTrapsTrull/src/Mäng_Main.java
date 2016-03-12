/**
 * Created by Alar on 12/03/2016.
 */
public class Mäng_Main {
    public static void main(String[] args) {
        int ctr = 0;
        while (ctr < 11) {
            Suhtlemine_Mängijaga.askPlayerMove();
            Väljasta_Mänguväli.updateGrid();
            Võidukontroll.checkGameOver();
            Võidukontroll.changeMove();

            ctr++;
        }
    }
}
