package src.Server;
import src.ServerLogger;

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

    public Server(){
        done = false;
        connctions = new ArrayList<>();
        logger = new ServerLogger("server_Log.txt");
    }
    @Override
    public void run() {
        try{
            server = new ServerSocket(9999);
            pool = Executors.newCachedThreadPool();
            logger.log("Server started");
            while(!done){
                Socket client = server.accept();
                ConnectionHandler handler = new ConnectionHandler(client);
                connctions.add(handler);
                pool.execute(handler);
            }
        }catch (Exception e){
            logger.log("Connection error: " + e.getMessage());
            shutdown();
        }
    }

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
                logger.log("Server shutting down...");
                server.close();
            }
            for(ConnectionHandler ch: connctions){
                ch.shutdown();
            }
        }catch (IOException e){
            logger.log("Shutting down error: " + e.getMessage());
            // ignore
        }

    }

    class ConnectionHandler implements Runnable{
        private Socket client;
        private BufferedReader in;
        private PrintWriter out;
        private String nickname;

        public ConnectionHandler(Socket client){
            this.client = client;
        }

        @Override
        public void run() {
            try {
                out = new PrintWriter(client.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                out.println("Please enter a nickname: ");
                nickname = in.readLine();
                System.out.println(nickname + " connected");
                logger.log(nickname + " connected");
                broadcast(nickname + " joined the chat!");
                logger.log(nickname + " joined the chat!");

                //tu trzeba dodac logi ale narazie im sie nie chce czytac co to za kongo
                String message;
                while ((message = in.readLine()) != null) {
                    if (message.startsWith("/nick ")) {
                        String[] messageSplit = message.split(" ", 2);
                        if (messageSplit.length == 2) {
                            broadcast(nickname + " renamed themselves to " + messageSplit[1]);
                            System.out.println(nickname + " renamed themselves to " + messageSplit[1]);
                            nickname = messageSplit[1];
                            out.println("Successfuly changes nickname to " + nickname);
                        } else {
                            out.println("No nickname provided!");
                        }
                    } else if (message.startsWith("/quit")) {
                        broadcast(nickname + "left chat");
                        shutdown();
                    } else {
                        broadcast(nickname + ": " + message);
                    }
                }
            } catch (IOException e) {
                shutdown();
            }
        }

        public void sendMessage(String message){
            out.println(message);
        }

        public void shutdown(){
            try{
                in.close();
                out.close();
                if (!client.isClosed()) {
                    client.close();
                }
            }catch (IOException e){
                // ignore
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.run();
    }
}