import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


/**
 * Created by Alar on 12/03/2016.
 */

public class Suhtlemine_Mängijaga {
    public static Scanner scanner = new Scanner(System.in); // Sisendi küsimise tagamine.
    // Mängijaga suhtlemine ja sisendi kontroll
    public static void askPlayerMove() {
        // Vaikimisi väärtused, et vältida kompileerimisaegset viga.
        int sisend1 = 0;
        int sisend2 = 0;

        // Küsime sisendit senikaua, kuni kasutaja sisestab korrektse sisendi.
        while (true) {
            System.out.println("\nSisestage üks number.");
            System.out.println("Number tohivad olla ainult vahemikust 1-9.");
            System.out.println("Esimene rida on 1-3, teine rida 4-6, kolmas rida 7-9.");

            // Loeme sisse kasutaja antud numbri.
            String intsAsStr = scanner.nextLine();

            // Kui sisestati vähem/rohkem kui 1 märk.
            if (intsAsStr.length()!= 1) {
                System.out.println("Sisestasid liiga vähe/palju andmeid!");
                continue;
            }
            // Teisendame sisestuse arvuks.
            int sisestus = Character.getNumericValue(intsAsStr.charAt(0));

            if (sisestus == 0) {
                System.out.println("Seda välja pole!");
                continue;
            }

            // Määrame mängia sisendile vastava mänguvälja koordinaadi.
            if (sisestus == 1) {
                sisend1 = 0;
                sisend2 = 0;
            }
            else if (sisestus == 2){
                sisend1 = 0;
                sisend2 = 1;
            }
            else if (sisestus == 3){
                sisend1 = 0;
                sisend2 = 2;
            }
            else if (sisestus == 4){
                sisend1 = 1;
                sisend2 = 0;
            }
            else if (sisestus == 5){
                sisend1 = 1;
                sisend2 = 1;
            }
            else if (sisestus == 6){
                sisend1 = 1;
                sisend2 = 2;
            }
            else if (sisestus == 7){
                sisend1 = 2;
                sisend2 = 0;
            }
            else if (sisestus == 8){
                sisend1 = 2;
                sisend2 = 1;
            }
            else if (sisestus == 9){
                sisend1 = 2;
                sisend2 = 2;
            }
            else {
                System.out.println("Vale sisend!");
                continue;
            }


            // Kui sisestatud väljal on juba märk tehtud.
            if (Mänguväli.field[sisend1][sisend2] != 0) {
                System.out.println("See väli on juba hõivatud!");
                continue;
            }


            break;
        }

        // Sisendi lisamine rea ja veeru muutujasse.
        Võidukontroll.currentRow = sisend1;
        Võidukontroll.currentCol = sisend2;

    }
}
