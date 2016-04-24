import java.io.*;
import java.nio.file.Files;

/**
 * Created by Alar on 24/04/2016.
 */
public class Logilugeja{
    private String logi;
    private String käiguAeg;

    public Logilugeja(String failinimi, String käiguAeg) throws IOException{
        this.logi = new String(Files.readAllBytes(new File(failinimi).toPath()), "UTF-8");
        this.käiguAeg = käiguAeg;
    }

    public int[] viimaneKäik(){
        String[] tükid = logi.split(System.lineSeparator()+System.lineSeparator());
        String viimaneKäik = "";
        for (int i = tükid.length-1; i>=0; i--){
            if (tükid[i].length() > 15 && tükid[i].contains(käiguAeg)){
                viimaneKäik = tükid[i];
                break;
            }
        }
        if (!viimaneKäik.equals("")){
            String koordinaadid = viimaneKäik.split("koordinaatidele ")[1].substring(0, 4);
            return new int[]{Character.getNumericValue(koordinaadid.charAt(0)), Character.getNumericValue(koordinaadid.charAt(3))};
        }
        return null;
    }

}