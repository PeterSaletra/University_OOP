package src.Client;

import java.io.*;
import java.net.Socket;
public class Client implements  Serializable, Runnable  {

    transient public Socket client;
    transient public BufferedReader in;
    transient public PrintWriter out;
    boolean done;
    public String host;
    public int port;
    public String nickname;
    transient private String[] activeClients;
    Client(String connection, String nickname) {
        this.nickname = nickname;
        try {
            System.out.println(connection);
            String[] con = connection.split(":");
            this.host = con[0];
            this.port = Integer.parseInt(con[1]);
        } catch (Exception e) {
            App.createPopUpWindow("Failed connection to server");
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

    public static void serialize(String filename, Object object) throws IOException {
        FileOutputStream fileOut = new FileOutputStream(filename);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(object);

        out.close();
        fileOut.close();
    }
    public static Client deserialize(String filepath) throws IOException, ClassNotFoundException {
        FileInputStream fileIn = new FileInputStream(filepath);
        ObjectInputStream in = new ObjectInputStream(fileIn);

        Client deserializedClient = (Client) in.readObject();
        in.close();
        fileIn.close();
        return deserializedClient;
    }
}

