public class SuhtlemineAIga extends Võidukontroll {
	public static final int[][] priorityMatrix = new int[][]{
    	{2, 3, 2},
    	{3, 1, 3},
    	{2, 3, 2}
    };
	
	private static boolean moveDetermined = true;
	
	public static boolean getMoveDetermined() {
		return moveDetermined;
	}
	
	public static void setMoveDetermined(boolean newMoveDetermined) {
		moveDetermined = newMoveDetermined;
	}
	
	public static void determineMove(boolean victoryPossible, int[][] field) {
		
		// Horizontal cases
        for (int i = 0; i < Mänguväli.ROW_AMT; i++) {
        	// Case 1: X | X | O
        	if (field[i][0] == (player1Turn == victoryPossible?1:2) && field[i][1] == (player1Turn == victoryPossible?1:2) && field[i][2] == 0) {
        		currentRow = i;
        		currentCol = 2;
        		return;
        	}
        	// Case 2: O | X | X
        	if (field[i][1] == (player1Turn == victoryPossible?1:2) && field[i][2] == (player1Turn == victoryPossible?1:2) && field[i][0] == 0) {
        		currentRow = i;
        		currentCol = 0;
        		return;
        	}
        	// Case 3: X | O | X
        	if (field[i][0] == (player1Turn == victoryPossible?1:2) && field[i][2] == (player1Turn == victoryPossible?1:2) && field[i][1] == 0) {
        		currentRow = i;
        		currentCol = 1;
        		return;
        	}
        }
        
        // Vertical cases
        for (int j = 0; j < Mänguväli.COL_AMT; j++) {
        	// Case 1: X |
        	//         ---
        	//         X |
        	//         ---
        	//         O |
        	if (field[0][j] == (player1Turn == victoryPossible?1:2) && field[1][j] == (player1Turn == victoryPossible?1:2) && field[2][j] == 0) {
        		currentRow = 2;
        		currentCol = j;
        		return;
        	}
        	// Case 2: O |
        	//         ---
        	//         X |
        	//         ---
        	//         X |
        	if (field[1][j] == (player1Turn == victoryPossible?1:2) && field[2][j] == (player1Turn == victoryPossible?1:2) && field[0][j] == 0) {
        		currentRow = 0;
        		currentCol = j;
        		return;
        	}
        	// Case 3: X |
        	//         ---
        	//         O |
        	//         ---
        	//         X |
        	if (field[0][j] == (player1Turn == victoryPossible?1:2) && field[2][j] == (player1Turn == victoryPossible?1:2) && field[1][j] == 0) {
        		currentRow = 1;
        		currentCol = j;
        		return;
        	}
        }
        
        // Top-left to bottom-right diagonal cases
        // Case 1: X |   |  
	    //        -----------
	    //           | X |  
		//        -----------
	    //           |   | O
        if (field[0][0]  == (player1Turn == victoryPossible?1:2) && field[1][1] == (player1Turn == victoryPossible?1:2) && field[2][2] == 0) {
        	currentRow = 2;
    		currentCol = 2;
    		return;
        }
        
        // Case 2: O |   |  
	    //        -----------
	    //           | X |  
		//        -----------
	    //           |   | X
        if (field[1][1] == (player1Turn == victoryPossible?1:2) && field[2][2] == (player1Turn == victoryPossible?1:2) && field[0][0] == 0) {
        	currentRow = 0;
    		currentCol = 0;
    		return;
        }
        
        // Case 3: X |   |  
	    //        -----------
	    //           | O |  
		//        -----------
	    //           |   | X
        if (field[0][0] == (player1Turn == victoryPossible?1:2) && field[2][2] == (player1Turn == victoryPossible?1:2) && field[1][1] == 0) {
        	currentRow = 1;
    		currentCol = 1;
    		return;
        }
        
        // Bottom-left to top-right diagonal cases
        // Case 1:   |   | O
	    //        -----------
	    //           | X |  
		//        -----------
	    //         X |   |  
        if (field[2][0]  == (player1Turn == victoryPossible?1:2) && field[1][1] == (player1Turn == victoryPossible?1:2) && field[0][2] == 0) {
        	currentRow = 0;
    		currentCol = 2;
    		return;
        }
        // Case 2:   |   | X
	    //        -----------
	    //           | X |  
		//        -----------
	    //         O |   |  
        if (field[1][1] == (player1Turn == victoryPossible?1:2) && field[0][2] == (player1Turn == victoryPossible?1:2) && field[2][0] == 0) {
        	currentRow = 2;
    		currentCol = 0;
    		return;
        }
        
        // Case 3:   |   | X
	    //        -----------
	    //           | O |  
		//        -----------
	    //         X |   |  
        if (field[2][0] == (player1Turn == victoryPossible?1:2) && field[0][2] == (player1Turn == victoryPossible?1:2) && field[1][1] == 0) {
        	currentRow = 1;
    		currentCol = 1;
    		return;
        }
        
        // Move has never been determined
        setMoveDetermined(false);
	}
	
	public static void determinePriorityNumber(int[][] field) {
		for (int k = 1; k <= 3; k++) {
			for (int i = 0; i < Mänguväli.ROW_AMT; i++) {
	            for (int j = 0; j < Mänguväli.COL_AMT; j++) {
	            	if (priorityMatrix[i][j] == k && field[i][j] == 0) {
	            		currentRow = i;
	            		currentCol = j;
	            		return;
	            	}
	            }
	        }
		}
	}
	
    public static void askAIMove() {
    	/*
        Key
        1: central slot
        2: corner slot
        3: edge slot
        
        Diagram
	     2 | 3 | 2
	    -----------
	     3 | 1 | 3
		-----------
	     2 | 3 | 2
	     
       */
    	
        final int[][] field = Mänguväli.field;
        boolean victoryPossible = true;
        setMoveDetermined(true);
        
        // Check for winning condition
        determineMove(victoryPossible, field);
        System.out.println("moveDetermined: " + getMoveDetermined());
        
        if (!getMoveDetermined()) {
            setMoveDetermined(true);
        	victoryPossible = false;
        	// Mitigate opponent winning condition
            determineMove(victoryPossible, field);
        }
        
        if (!getMoveDetermined()) {
            setMoveDetermined(true);
	        // Choose a move based on the diagram above
	        determinePriorityNumber(field);
        }
    }
}
