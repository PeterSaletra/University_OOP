package src.Logger;
import src.Logger.Logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class ServerLogger extends Logger {

    public ServerLogger(){
        try {
            logWriter = new BufferedWriter(new FileWriter("[ServerLOGS]" + getTimestamp()+".txt", true));
            consoleWriter = new OutputStreamWriter(System.out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ServerLogger(String logFileName) {
        super(logFileName);
    }

    @Override
    public void echo(String message, Boolean log) {
        try {
            System.out.println("[Server]" + getTimestamp() + " " + message);
            if(log) {
                logWriter.write("[Server]" + getTimestamp() + " " + message);
                logWriter.newLine();
                logWriter.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}