import java.util.Scanner;

/**
 * Created by Alar on 12/03/2016.
 */

public class Suhtlemine_Mängijaga {
    public static Scanner scanner = new Scanner(System.in);
    public static void askPlayerMove() {
        System.out.println("Sisestage kaks tühikuga eraldatud numbrit.");
        System.out.println("Esimene number tähistab rida ja teine number tähistab tulpa.");

        // Kui sisestatud vähem kui 3 märki.
        String[] intsAsStr = scanner.nextLine().split(" ");
        if (intsAsStr.length < 2) {
            System.out.println("Sisestasid vigased andmed!");
            askPlayerMove();
        }
        // Kui sisestatud väljal on juba märk tehtud.
        if (Mänguväli.field[Integer.parseInt(intsAsStr[0])][Integer.parseInt(intsAsStr[1])] != 0){
            System.out.println("See koht on juba hõivatud!!");
            askPlayerMove();
        }

        else{
            Võidukontroll.currentRow = Integer.parseInt(intsAsStr[0]);
            Võidukontroll.currentCol = Integer.parseInt(intsAsStr[1]);
        }
    }
}
