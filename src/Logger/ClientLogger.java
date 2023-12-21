package src.Logger;
import src.Logger.Logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class ClientLogger extends Logger {

    public ClientLogger(){
        try {
            logWriter = new BufferedWriter(new FileWriter("[ClientLOGS]"+ getTimestamp() +".txt", true));
            consoleWriter = new OutputStreamWriter(System.out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ClientLogger(String logFileName){
        super(logFileName);
    }

    @Override
    public void echo(String message, Boolean log) {
        try {
            System.out.println("[Client]"+ getTimestamp() + " " + message);
            if(log) {
                logWriter.write("[Client]"+ getTimestamp() + " " + message);
                logWriter.newLine();
                logWriter.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}