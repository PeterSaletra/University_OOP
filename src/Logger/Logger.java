package src.Logger;
import java.io.*;
import java.util.Date;
import java.text.SimpleDateFormat;

public abstract class Logger {
    protected BufferedWriter logWriter;
    protected Writer consoleWriter;

//    public Logger(){
//        try {
//            logWriter = new BufferedWriter(new FileWriter("DUpa", true));
//            consoleWriter = new OutputStreamWriter(System.out);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public Logger(String logFileName) {
        try {
            logWriter = new BufferedWriter(new FileWriter(logFileName, true));
            consoleWriter = new OutputStreamWriter(System.out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract void log(String message);

    public void echo(String message) {
        try {
            consoleWriter.write(message);
            consoleWriter.write(System.lineSeparator());
            consoleWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected String getTimestamp(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(new Date());
    }

    public void close() {
        try {
            logWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
