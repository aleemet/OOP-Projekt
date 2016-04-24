import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Alar on 24/04/2016.
 */
public class Logipidaja extends Writer{

    private DataOutputStream dos;
    private String timeStamp;

    public String getTimeStamp() {
        return timeStamp;
    }

    public Logipidaja(String failinimi) throws FileNotFoundException {
        this.dos = new DataOutputStream(new FileOutputStream(failinimi,true));
    }

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

    public void write(String sisu)throws IOException{
        timeStamp = new SimpleDateFormat("HH.mm.ss.SSS dd.MM.yyyy").format(new Date()).replaceAll("\\s", " ").trim();
        dos.writeUTF(timeStamp + "\t");
        dos.writeUTF(sisu);

    }

}
