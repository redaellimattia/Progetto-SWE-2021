package it.polimi.ingsw.network.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer extends Thread{
    private final Server server;
    private final int port;

    private ServerSocket serverSocket;

    /**
     *
     * @param server Server Object
     * @param port port listening
     */
    public SocketServer(Server server,int port){
        this.server = server;
        this.port = port;
    }

    /**
     * Starting the Socket Server-Side
     */
    void startSocketServer(){
        try {
            serverSocket = new ServerSocket(port);
            start();
        } catch (IOException e) {}
    }

    /**
     * Running the Server
     */
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Socket client = serverSocket.accept();
                new SocketConnection(this, client);
            } catch (IOException e) {//LOGGER}
            }
        }
    }

    /**
     *
     * @param clientConnection client that is disconnected, passing it to the Server
     */
    public void onDisconnect(SocketConnection clientConnection) {
        //server.onDisconnect(clientConnection);
    }

    /**
     *
     * @param clientConnection client that wrote a message
     */
    public void onMessage(SocketConnection clientConnection) {
        //server.onMessage(clientConnection);
    }

    /**
     *
     * @param clientConnection client trying to log in the lobby
     */
    public void onLogin(SocketConnection clientConnection) {
        //server.onLogin(clientConnection);
    }
}
