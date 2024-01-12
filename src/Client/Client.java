package src.Client;
import src.Logger.ClientLogger;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

public class Client implements  Serializable, Runnable  {
    private Socket client;
    public BufferedReader in;
    public PrintWriter out;
    private boolean done;
    transient private ClientLogger logger = new ClientLogger("client_log.txt");
    private String host;
    private int port;
    public String nickname;
    private String[] activeClients;


    Client() {
        this.host = "127.0.0.1";
        this.port = 9999;
        this.nickname = "Anonymous";
    }

    Client(String connection, String nickname) {
        this.nickname = nickname;
        try {
            System.out.println(connection);
            String[] con = connection.split(":");
            this.host = con[0];
            this.port = Integer.parseInt(con[1]);
        } catch (Exception e) {
            App.createPopUpWindow(e.getMessage());
            logger.echo("Incorrect connection data " + e.getMessage(), true);
        }
    }

    @Override
    public void run() {
        try {
            client = new Socket(host, port);
            out = new PrintWriter(client.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));

            out.println(nickname);

            Thread inputThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));
                        while (!done) {
                            String message = inReader.readLine();
                            if (message.equals("/quit")) {
                                out.println(message);
                                inReader.close();
                                shutdown();
                            } else if (message.equals("/active ")) {

                                String clientsList = message.substring("/active ".length());
                                activeClients = clientsList.split(",");
                                logger.echo("Active clients updated: " + Arrays.toString(activeClients), true);


                                App.updateActiveUsersPanel(activeClients);


                            } else {

                                out.println(message);
                            }
                        }
                    } catch (IOException e) {
                        shutdown();
                    }
                }
            });

            inputThread.start();
            String inMessage;
            while ((inMessage = in.readLine()) != null) {
                if (inMessage.startsWith("/active ")) {

                    String clientsList = inMessage.substring("/active ".length());
                    activeClients = clientsList.split(",");
                    logger.echo("Active clients updated: " + Arrays.toString(activeClients), true);


                    App.updateActiveUsersPanel(activeClients);

                } else {
                    App.sendMessage(inMessage, 2);
                }
            }
        } catch (IOException e) {
            shutdown();
        }
    }

    public void shutdown() {
        done = true;
        try {
            in.close();
            out.close();
            if (!client.isClosed()) {
                client.close();
                shutdown();
            }
        } catch (IOException e) {
            // ignore
        }
    }

    public BufferedReader getIn() {
        return in;
    }

    public void serialize(String filename) throws IOException {
        FileOutputStream fileOut = new FileOutputStream(filename);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(this);

        out.close();
        fileOut.close();
        logger.echo("Client info saved to file!", true);
    }

    public void deserialize(String filepath) throws IOException, ClassNotFoundException {
        FileInputStream fileIn = new FileInputStream(filepath);
        ObjectInputStream in = new ObjectInputStream(fileIn);

        Client deserializedClient = (Client) in.readObject();

        this.client = deserializedClient.client;
        this.in = deserializedClient.in;
        this.out = deserializedClient.out;
        this.done = deserializedClient.done;
        this.logger = deserializedClient.logger;
        this.host = deserializedClient.host;
        this.port = deserializedClient.port;
        this.nickname = deserializedClient.nickname;
        this.activeClients = deserializedClient.activeClients;

        in.close();
        fileIn.close();
        logger.echo("Client info read from file!", true);
    }
}

