/**
 * Created by Alar on 12/03/2016.
 */
public class Diagonaalne_Võit extends Võidukontroll {
    public static void checkDiagonalVictory() {
        boolean onFirstDiagonal = currentRow == currentCol;
        boolean onSecondDiagonal = currentRow == COL_AMT - currentCol - 1;

        if (!onFirstDiagonal && !onSecondDiagonal) {
            return;
        }

        if (player1Turn) {
            player1Won = true;
        }
        else {
            player2Won = true;
        }

        // Kontrollib millise diagonaaliga on tegemist
        if (onFirstDiagonal) {
            for (int i = 0; i < ROW_AMT; i++) {
                if (field[i][i] != (player1Turn?1:2)) {
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
        if (onSecondDiagonal) {
            for (int i = 0; i < ROW_AMT; i++) {
                if (field[i][COL_AMT - i - 1] != (player1Turn?1:2)) {
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
