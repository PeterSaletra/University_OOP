package src.Client;
import src.Logger.ClientLogger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client implements  Runnable{

    private Socket client;
    private BufferedReader in;
    private PrintWriter out;
    private boolean done;
    private String host;
    private int port;
    private ClientLogger logger = new ClientLogger("client_log.txt");

    Client(){
        this.host = "127.0.0.1";
        this.port = 9999;
    }

    Client(String connection){
        String[] con = connection.split(":");
        this.host = con[0];
        this.port = Integer.parseInt(con[1]);
    }

    @Override
    public void run() {
        try{
            client = new Socket(host, port);
            out = new PrintWriter(client.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));

            InputHandler inHandler = new InputHandler();
            Thread t = new Thread(inHandler);
            t.start();

            String inMessage;
            while((inMessage = in.readLine()) != null){
                System.out.println(inMessage);
            }
        }catch(IOException e){
            shutdown();
        }
    }

    public void shutdown(){
        done = true;
        try{
            in.close();
            out.close();
            if(!client.isClosed()){
                client.close();
                shutdown();
            }
        }catch (IOException e){
            // ignore
        }
    }
    class InputHandler implements Runnable{

        @Override
        public void run(){
            try{
                BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));
                while(!done){
                    String message = inReader.readLine();
                    if(message.equals("/quit")){
                        out.println(message);
                        inReader.close();
                        shutdown();
                    }else{
                        out.println(message);
                    }
                }
            }catch (IOException e){
                shutdown();
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Podaj server:");
        String connection = sc.nextLine();

        Client client = new Client(connection);
        client.run();
    }
}
