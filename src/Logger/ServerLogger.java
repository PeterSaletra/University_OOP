package src.Logger;

import src.Logger.Logger;

import java.io.IOException;

public class ServerLogger extends Logger {
    public ServerLogger(String logFileName) {
        super(logFileName);
    }

    @Override
    public void log(String message) {
        try {
            logWriter.write("[Server] [" + getTimestamp() + "] " + message);
            logWriter.newLine();
            logWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}