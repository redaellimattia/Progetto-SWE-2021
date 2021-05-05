package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.messages.clientMessages.AskLobbyMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientSocket implements Runnable{
    private BufferedReader in;
    private PrintWriter out;
    private Socket socket;
    private boolean isConnected;
    private String nickname;
    private String address;
    private int socketPort;
    private Thread socketListener;


    public ClientSocket(String nickname,String address, int socketPort){
        this.nickname = nickname;
        try{
            this.socket = new Socket(address,socketPort);
            this.in = new BufferedReader (new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(socket.getOutputStream());
        } catch(IOException e){
                //logger
        }
        this.isConnected = true;
        socketListener = new Thread(this);
        socketListener.start();
    }

    public void startConnection(){
        send(new AskLobbyMessage(this.nickname,-1).serialize());
    }

    public void send(String msg){
        if (isConnected) {
            out.println(msg);
            out.flush();
        }
    }
    @Override
    public void run() {

    }
}
