package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.messages.clientMessages.AskLobbyMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientSocket implements Runnable {
    private BufferedReader in;
    private PrintWriter out;
    private Socket socket;
    private boolean isConnected;
    private String nickname;
    private Client client;
    private Thread socketListener;


    /**
     * Creating ClientSocket
     *
     * @param address address chosen
     * @param socketPort socketPort chosen
     * @param client client that is trying to connect
     */
    public ClientSocket(String address, int socketPort, Client client) {
        this.client = client;
        this.nickname = client.getNickname();
        try {
            this.socket = new Socket(address, socketPort);
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(socket.getOutputStream());
            this.isConnected = true;
            socketListener = new Thread(this);
            socketListener.start();
            Client.LOGGER.info("Client successfully connected at: "+ address+":" + socketPort);
        } catch (IOException e) { Client.LOGGER.severe("Failed to connect to: "+ address+":" + socketPort + "\n" +e.getMessage()); }
    }

    /**
     * On connection request the available lobbies
     */
    public void startConnection() {
        send(new AskLobbyMessage(this.nickname, -1).serialize());
    }

    /**
     * if connected, send the messages
     * @param msg serialized json message
     */
    public void send(String msg) {
        if (isConnected) {
            out.println(msg);
            out.flush();
        }
    }

    /**
     * While the tread is running, wait for responses from the Server
     */
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                String msg = in.readLine();
                //System.out.println(msg);
                if(msg!=null) client.onMessage(msg);
            } catch (IOException e) { Client.LOGGER.severe("Failed to read message from server: "+ e.getMessage());}
        }
    }
}
