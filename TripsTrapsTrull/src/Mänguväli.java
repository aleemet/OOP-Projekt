/**
 * Created by Alar on 12/03/2016.
 */
public class Mänguväli {
    public static final int ROW_AMT = 3; // Ridade arv väljal
    public static final int COL_AMT = 3; // Tulpade arv väljal
    public static int[][] field = new int[ROW_AMT][COL_AMT];

    public static void updateGrid() {
        field[Võidukontroll.currentRow][Võidukontroll.currentCol] = Võidukontroll.player1Turn?1:2;
        printGrid();
    }
    public static void printGrid() {
		/*

	     O | X | X
	    -----------
	     X |   |
		-----------
	       |   | O
	    siin visandasin v�ljaku
		*/
        for (int i = 0; i < ROW_AMT; i++) {
            for (int j = 0; j < COL_AMT; j++) {
                if (field[i][j] == 0) {
                    System.out.print("   ");
                }
                else if (field[i][j] == 1) {
                    System.out.print(" X ");
                }
                else { // field[i][j] == 2
                    System.out.print(" O ");
                }

                if (j != COL_AMT - 1) {
                    System.out.print("|");
                }
            }
            System.out.println("");

            if (i != ROW_AMT - 1) {
                System.out.println("-----------");
            }
        }
    }
}
