package src.Server;
import src.Logger.*;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable{
    private ArrayList<ConnectionHandler> connctions;
    private ArrayList<String> activeUsers;
    private ServerSocket server;
    private boolean done;
    private ExecutorService pool;
    private ServerLogger logger;
    private ClientLogger clLogger;
    private int port;

    public Server(){
        done = false;
        connctions = new ArrayList<>();
        activeUsers= new ArrayList<>();
        logger = new ServerLogger();
        clLogger = new ClientLogger ();
        port = 9999;
    }

    public Server(int port){
        done = false;
        connctions = new ArrayList<>();
        activeUsers= new ArrayList<>();
        logger = new ServerLogger();
        clLogger = new ClientLogger ();
        this.port = port;
    }

    @Override
    public void run() {
        try {
            server = new ServerSocket(port);
            pool = Executors.newCachedThreadPool();
            logger.echo("Starting...", true);
            logger.echo("Server successfully started!", true);

            Thread terminalInputThread = new Thread(() -> {
                BufferedReader terminalReader = new BufferedReader(new InputStreamReader(System.in));
                try {
                    while (true) {
                        String command = terminalReader.readLine();
                        if ("shutdown".equalsIgnoreCase(command.trim())) {
                            logger.echo("Received shutdown command. Shutting down the server...", true);
                            shutdown();
                            break;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            terminalInputThread.start();

            while (!done) {
                if (server.isClosed()) {
                    break;
                }
                try {
                    Socket client = server.accept();
                    ConnectionHandler handler = new ConnectionHandler(client);
                    connctions.add(handler);
                    pool.execute(handler);

                } catch (SocketException se) {
                    if (!server.isClosed()) {
                        se.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void broadcast(String message, String sender){
        for(ConnectionHandler ch: connctions){
            if(!ch.getNickname().equals(sender)) ch.sendMessage(message);
        }
    }
    public void shutdown(){
        try{
            done = true;
            pool.shutdown();
            if(!server.isClosed()){
                logger.echo("Server closed!", true);
                server.close();
            }
            for(ConnectionHandler ch: connctions){
                ch.shutdown();
            }
        }catch (IOException e){
            logger.echo("Shutting down error: " + e.getMessage(), true);
            // ignore
        }
    }
    class ConnectionHandler implements Runnable {
        private Socket client;
        private BufferedReader in;
        private PrintWriter out;
        private String nickname;

        public ConnectionHandler(Socket client) {
            this.client = client;
        }

        @Override
        public void run() {
            try {
                out = new PrintWriter(client.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                nickname = in.readLine();

                activeUsers.add(nickname);
                String activeClientsUpdate = "/active " + String.join(",", activeUsers);
                broadcast(nickname + " joined chat", nickname);
                broadcast(activeClientsUpdate, nickname);
                sendMessage(activeClientsUpdate);

                logger.echo(nickname + " connected", true);
                logger.echo(nickname + " joined the chat!", true);

                String message;
                while ((message = in.readLine()) != null) {
                    if (message.startsWith("/nick ")) {
                        String[] messageSplit = message.split(" ", 2);
                        if (messageSplit.length == 2) {
                            broadcast(nickname + " changed nickname to:  " + messageSplit[1], nickname);
                            logger.echo(nickname + " changed nickname to: " + messageSplit[1], true);
                            nickname = messageSplit[1];
                            logger.echo("Successfuly changes nickname to " + nickname, true);
                        } else {
                            logger.echo("No nickname provided!", true);
                        }
                    } else if (message.startsWith("/quit")) {
                        broadcast(nickname + " left chat", nickname);
                        logger.echo(nickname + " left chat", true);
                        shutdown();
                    } else {
                        //test
                        clLogger.echo(nickname + ": " + message, true);
                        broadcast(nickname + ": " + message, nickname);
                    }
                }
            } catch (IOException e) {
                shutdown();
            }
        }
        public void sendMessage(String message) {
            out.println(message);
        }
        public void shutdown() {
            try {
                activeUsers.remove(nickname);
                String activeClientsUpdate = "/active " + String.join(",", activeUsers);
                broadcast(nickname + " left chat", nickname);
                broadcast(activeClientsUpdate, nickname);

                in.close();
                out.close();
                if (!client.isClosed()) {
                    client.close();
                }
            } catch (IOException e) {
                // ignore
            }
        }
        public String getNickname() {
            return nickname;
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.run();
    }
}