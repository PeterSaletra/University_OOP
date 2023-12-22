package src.Server;
import src.Logger.*;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable{
    private ArrayList<ConnectionHandler> connctions;
    private ServerSocket server;
    private boolean done;
    private ExecutorService pool;
    private ServerLogger logger;
    private ClientLogger clLogger;

    public Server(){
        done = false;
        connctions = new ArrayList<>();
        logger = new ServerLogger("server_Log.txt");
        logger = new ServerLogger();
        clLogger = new ClientLogger ();
    }

    @Override
    public void run() {
        try {
            server = new ServerSocket(9999);
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

/*    public void run() {
        try{
            server = new ServerSocket(port);
            pool = Executors.newCachedThreadPool();
            logger.echo("Starting...", true);
            logger.echo("Server succesfully started!", true);
            while(!done){
                Socket client = server.accept();
                ConnectionHandler handler = new ConnectionHandler(client);
                connctions.add(handler);
                pool.execute(handler);
            }
        }catch (Exception e){
            logger.echo("Connection error: " + e.getMessage(), true);
            shutdown();
        }
    }*/

    public void broadcast(String message){
        for(ConnectionHandler ch: connctions){
            ch.sendMessage(message);
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
                out.println("Please enter a nickname: ");
                nickname = in.readLine();
                logger.echo(nickname + " connected", true);
                logger.echo(nickname + " joined the chat!", true);

                String message;
                while ((message = in.readLine()) != null) {
                    if (message.startsWith("/nick ")) {
                        String[] messageSplit = message.split(" ", 2);
                        if (messageSplit.length == 2) {
                            broadcast(nickname + " renamed themselves to " + messageSplit[1]);
                            logger.echo(nickname + " renamed themselves to " + messageSplit[1], true);
                            nickname = messageSplit[1];
                            logger.echo("Successfuly changes nickname to " + nickname, true);
                        } else {
                            logger.echo("No nickname provided!", true);
                        }
                    } else if (message.startsWith("/quit")) {
                        broadcast(nickname + " left chat");
                        logger.echo(nickname + " left chat", true);
                        shutdown();
                    } else {
                        //test
                        clLogger.echo(nickname + ": " + message, true);
                        broadcast(nickname + ": " + message);
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
                in.close();
                out.close();
                if (!client.isClosed()) {
                    client.close();
                }
            } catch (IOException e) {
                // ignore
            }
        }

    }

    public static void main(String[] args) {
        Server server = new Server();
        server.run();
    }
}