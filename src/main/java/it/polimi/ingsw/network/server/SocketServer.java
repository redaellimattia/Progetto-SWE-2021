package it.polimi.ingsw.network.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;

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
        } catch (IOException e) {Server.LOGGER.log(Level.SEVERE,"Error while creating ServerSocket\n"+ e.getMessage());}
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
                Server.LOGGER.log(Level.INFO,"Client Trying to connect..");
            } catch (IOException e) {Server.LOGGER.log(Level.SEVERE,"Error while accepting client.\n"+ e.getMessage());}
        }
    }

    /**
     *
     * @param clientConnection client that is disconnected, passing it to the Server
     */
    public void onDisconnect(SocketConnection clientConnection) {
        server.onDisconnect(clientConnection);
    }

    /**
     *
     * @param clientConnection SocketConnetion of the client who wrote the msg
     * @param msg String msg wrote by the client
     */
    public void onMessage(SocketConnection clientConnection,String msg) {
        server.onMessage(clientConnection,msg);
    }
}
