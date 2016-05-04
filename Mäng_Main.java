/**
 * Created by Alar on 12/03/2016.
 */
public class Mäng_Main {
    public static void main(String[] args) {
        int ctr = 0;
        while (ctr < 11) {
        	if (ctr % 2 == 0) {
                Suhtlemine_Mängijaga.askPlayerMove();
        	}
        	else { // ctr % 2 == 1
        		SuhtlemineAIga.askAIMove();
        	}
            Mänguväli.updateGrid();
            Võidukontroll.checkGameOver();
            Võidukontroll.changeMove();

            ctr++;
        }
    }
}
