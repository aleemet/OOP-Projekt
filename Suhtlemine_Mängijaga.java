import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


/**
 * Created by Alar on 12/03/2016.
 */

public class Suhtlemine_Mängijaga {
    public static Scanner scanner = new Scanner(System.in); // Sisendi küsimise tagamine.
    public static ArrayList<Integer> lubatud_arvud = new ArrayList<Integer>(Arrays.asList(0,1,2)); // Sobilike arvude lisamine listi.

    // Mängijaga suhtlemine ja sisendi kontroll
    public static void askPlayerMove() {
        int sisend1;
        int sisend2;
        while (true) {
            System.out.println("\nSisestage kaks numbrit.");
            System.out.println("Numbrid tohivad olla ainult 0, 1 ja 2.");
            System.out.println("Esimene number tähistab rida ja teine number tähistab tulpa.");

            // Loeme sisse kasutaja antud numbrid.
            String intsAsStr = scanner.nextLine();
            sisend1 = Character.getNumericValue(intsAsStr.charAt(0));
            sisend2 = Character.getNumericValue(intsAsStr.charAt(1));


            // Kui sisestati arve, mis ei ole  hulgas {0,1,2}
            if (!lubatud_arvud.contains(sisend1) || !lubatud_arvud.contains(sisend2)) {
                System.out.println("Seda välja pole!");
                continue;
            }
            // Kui sisestati vähem/rohkem kui 2 märki.
            if (intsAsStr.length()!= 2) {
                System.out.println("Sisestasid liiga vähe/palju andmeid!");
                continue;
            }
            // Kui sisestatud väljal on juba märk tehtud.
            if (Mänguväli.field[sisend1][sisend2] != 0) {
                System.out.println("See koht on juba hõivatud!!");
                continue;
            }
            break;
        }

        // Sisendi lisamine rea ja veeru muutujasse.
        Võidukontroll.currentRow = sisend1;
        Võidukontroll.currentCol = sisend2;

    }
}
