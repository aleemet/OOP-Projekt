/**
 * Created by Alar on 12/03/2016.
 */
public class Horisontaalne_Võit extends Võidukontroll {
    public static void checkHorizontalVictory() {
        if (player1Turn) {
            player1Won = true;
        }
        else {
            player2Won = true;
        }

        for (int j = 0; j < COL_AMT; j++) {
            if (field[currentRow][j] != (player1Turn?1:2)) { // https://en.wikipedia.org/wiki/%3F:
                if (player1Turn) {
                    player1Won = false;
                }
                else {
                    player2Won = false;
                }
                break;
            }
        }

        if (player1Won) {
            System.out.println("M�ngija 1 v�itis.");
            System.exit(0);
        }
        else if (player2Won) {
            System.out.println("M�ngija 2 v�itis.");
            System.exit(0);
        }
    }
}
