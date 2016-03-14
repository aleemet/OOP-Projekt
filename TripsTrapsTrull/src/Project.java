/*
// Packages
//package tictactoe;

// Impordid
import java.util.Scanner;

class Project {
	// Inputi jaoks
	public static Scanner scanner = new Scanner(System.in);
	
	// Konstandid
	public static final int ROW_AMT = 3; // Ridade arv v�ljal
	public static final int COL_AMT = 3; // Tulpade arv v�ljal
    
	// V�idutingimused
	public static boolean isGameOver = false;
	public static boolean player1Won = false;
	public static boolean player2Won = false;
	public static boolean isDraw = false;
	// V�idukontroll
	public static int currentRow;
	public static int currentCol;
	public static boolean player1Turn = true;
	public static int moveCount = 0;


	public static int[][] field = new int[ROW_AMT][COL_AMT];
	
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
	
	public static void checkVerticalVictory() {
		if (player1Turn) {
			player1Won = true;
		}
		else {
			player2Won = true;
		}
		
		for (int i = 0; i < ROW_AMT; i++) {
			if (field[i][currentCol] != (player1Turn?1:2)) {
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
	
	public static void checkVictory() {
	    // Horisontaalkontroll
		checkHorizontalVictory();
		
		// Vertikaalkontroll
		checkVerticalVictory();
		
		// Diagonaalkontroll
		checkDiagonalVictory();
	}
	
	public static void checkDraw() {
		isDraw = moveCount == ROW_AMT * COL_AMT;
		

		
		if (isDraw) {
			System.out.println("Viik");
			System.exit(0);
		}
	}
	
	public static void checkGameOver() {
		checkVictory();
		checkDraw();
	}
	
	public static void changeMove() {
		player1Turn = !player1Turn;
		moveCount += 1;
	}
	
	public static void printGrid() {
		*/
/*
		
	     O | X | X 
	    -----------
	     X |   |   
		-----------
	       |   | O 
	    siin visandasin v�ljaku
		*//*

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
	
	public static void updateGrid() {
		field[currentRow][currentCol] = player1Turn?1:2;
	}
	
	public static void askPlayerMove() {
		System.out.println("Sisestage kaks t�hikuga eraldatud numbrit.");
		System.out.println("Esimene number t�histab rida ja teine number t�histab tulpa.");
		String[] intsAsStr = scanner.nextLine().split(" ");
		currentRow = Integer.parseInt(intsAsStr[0]);
		currentCol = Integer.parseInt(intsAsStr[1]);
	}
	
	

	public static void main(String[] args) {
		int ctr = 0;
        while (ctr < 11) {
        	askPlayerMove();
            updateGrid();
            printGrid();
            checkGameOver();
            changeMove();
            
            ctr++;
        }
	}
}
*/
