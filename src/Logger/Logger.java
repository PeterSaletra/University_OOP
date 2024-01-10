package src.Logger;
import java.io.*;
import java.util.Date;
import java.text.SimpleDateFormat;

public abstract class Logger {
    protected BufferedWriter logWriter;
    protected Writer consoleWriter;

//<<<<<<< HEAD
//    public Logger(){
//        try {
//            logWriter = new BufferedWriter(new FileWriter("DUpa", true));
//            consoleWriter = new OutputStreamWriter(System.out);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//=======
    public Logger(){}
//>>>>>>> 4b092e8a23103caf325c75ecd002fc1bdb4e6593

    public Logger(String logFileName) {
        try {
            logWriter = new BufferedWriter(new FileWriter(logFileName, true));
            consoleWriter = new OutputStreamWriter(System.out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract void echo(String message, Boolean log);

    public void close() {
        try {
            logWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected String getTimestamp(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("[yyyy-MM-dd][HH;mm;ss]");
        return dateFormat.format(new Date());
    }
}
