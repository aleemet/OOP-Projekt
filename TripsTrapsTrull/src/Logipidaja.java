import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Alar on 24/04/2016.
 */

// Klass mängu logi pidamiseks.
public class Logipidaja extends Writer{

    private DataOutputStream dos; // Voog logi väljastamiseks.
    private String timeStamp; // Ajahetk, mil logisse kirjutati.

    public String getTimeStamp() {
        return timeStamp;
    }

    // Loome logi, lubame lõppu kirjutamise.
    public Logipidaja(String failinimi) throws FileNotFoundException {
        this.dos = new DataOutputStream(new FileOutputStream(failinimi,true));
    }

    // Tingimused klassi Writer tingimustele vastamiseks.
    public void close() throws IOException{
        dos.close();
    }
    public void flush() throws IOException{
        dos.flush();
    }
    public void write(char[] cbuf, int off, int len) throws IOException {
        for (int i = off; i<len; i++){
            dos.writeChar((int) cbuf[i]);
        }
    }

    // Meetod logi sissekande tegemiseks.
    public void write(String sisu)throws IOException{

        // Määrame ajahetke, kirjutame selle logi sissekande ette ja seejärel kirjutame logisse vastava sisu.
        // Logi ajahetk määratakse millisekundi täpsusega, et kiiresti üksteise järel sooritatud käigud oleksid eristatavad
        timeStamp = new SimpleDateFormat("HH.mm.ss.SSS dd.MM.yyyy").format(new Date()).replaceAll("\\s", " ").trim();
        dos.writeUTF(timeStamp + "\t");
        dos.writeUTF(sisu);

    }
}