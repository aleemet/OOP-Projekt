import java.util.Scanner;

/**
 * Created by Alar on 12/03/2016.
 */


public class Suhtlemine_Mängijaga {
    public static Scanner scanner = new Scanner(System.in);
    public static void askPlayerMove() {
        System.out.println("Sisestage kaks t�hikuga eraldatud numbrit.");
        System.out.println("Esimene number t�histab rida ja teine number t�histab tulpa.");
        String[] intsAsStr = scanner.nextLine().split(" ");
        Võidukontroll.currentRow = Integer.parseInt(intsAsStr[0]);
        Võidukontroll.currentCol = Integer.parseInt(intsAsStr[1]);
    }
}
