import java.io.*;
import java.nio.file.Files;

/**
 * Created by Alar on 24/04/2016.
 */

// Klass mängu logist käikude lugemiseks.
public class Logilugeja{
    private String logi; // Logi sisu.
    private String käiguAeg; // Otsitava käigu sooritamise aeg.

    // Määrame konstruktoris logi ja otsitava käigu
    public Logilugeja(String failinimi, String käiguAeg) throws IOException{
        this.logi = new String(Files.readAllBytes(new File(failinimi).toPath()), "UTF-8");
        this.käiguAeg = käiguAeg;
    }

    // Meetod otsitava käigu leidmiseks logist ja selle käigu koordinaatide tagastamiseks.
    public int[] otsitavKäik(){

        // Lööme logi eraldi sissekanneteks, otsime käigu (alustades viimasest sissekandest), mis sooritati vastaval ajal.
        String[] tükid = logi.split(System.lineSeparator()+System.lineSeparator());
        String viimaneKäik = "";
        for (int i = tükid.length-1; i>=0; i--){
            if (tükid[i].length() > 15 && tükid[i].contains(käiguAeg)){
                viimaneKäik = tükid[i];
                break;
            }
        }

        // Kui leiti vastav käik, määrame sissekandesse kirjutatu põhjal otsitava käigu koordinaadid.
        if (!viimaneKäik.equals("")){
            String koordinaadid = viimaneKäik.split("koordinaatidele ")[1].substring(0, 4);
            return new int[]{Character.getNumericValue(koordinaadid.charAt(0)), Character.getNumericValue(koordinaadid.charAt(3))};
        }
        return null;
    }
}