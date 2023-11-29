package src;


import java.io.IOException;

public class ClientLogger extends Logger {
    public ClientLogger(String logFileName) {
        super(logFileName);
    }

    @Override
    public void log(String message) {
        try {
            logWriter.write("[Client] [" + getTimestamp() + "] " + message);
            logWriter.newLine();
            logWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}