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


    public ClientSocket(String address, int socketPort, Client client) {
        try {
            this.client = client;
            this.nickname = client.getNickname();
            this.socket = new Socket(address, socketPort);
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(socket.getOutputStream());
            this.isConnected = true;
            socketListener = new Thread(this);
            socketListener.start();
            Client.LOGGER.info("Client successfully connected at: "+ address+":" + socketPort);
        } catch (IOException e) { Client.LOGGER.severe("Failed to connect to: "+ address+":" + socketPort + "\n" +e.getMessage()); }
    }

    public void startConnection() {
        //on connection request the available lobbies
        send(new AskLobbyMessage(this.nickname, -1).serialize());
    }

    public void send(String msg) {
        if (isConnected) {
            out.println(msg);
            out.flush();
        }
    }

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
