package it.polimi.ingsw.network.server;

import java.io.*;
import java.net.Socket;
import java.util.logging.Level;

public class SocketConnection implements Runnable{
    private final SocketServer socketServer;
    private final Socket socket;

    private BufferedReader  in;
    private PrintWriter out;

    private boolean isConnected;

    private final Thread socketListener;

    /**
     *
     * @return true if there's a connection ongoing
     */
    public boolean isConnected() {
        return isConnected;
    }

    /**
     * Creating socketConnection
     *
     * @param socketServer ServerSocket Object ServerLobby-Side
     * @param socket Socket Object Client-Side
     */
    public SocketConnection(SocketServer socketServer, Socket socket) {
        this.socketServer = socketServer;
        this.socket = socket;

        this.isConnected = true;

        try {
            this.in = new BufferedReader (new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            Server.LOGGER.log(Level.SEVERE,"Error while creating the Socket Connection.\n"+ e.getMessage());}

        socketListener = new Thread(this);
        socketListener.start();
    }

    /**
     * Running the Thread and keeping listening for messages
     */
    @Override
    public void run() {
        while (!socketListener.isInterrupted()) {
            try {
                String msg = in.readLine();
                if (msg != null) socketServer.onMessage(this, msg);
                else
                    disconnect();
            } catch (IOException e) {
                Server.LOGGER.log(Level.SEVERE, "Error while reading.\n" + e.getMessage());
                disconnect();
            }
        }
    }

    /**
     * Sending messages to the client
     */
    public void send(String json) {
        if (isConnected) {
            out.println(json);
            out.flush();
        }
    }

    /**
     * Client is disconnecting during game
     */
    public void disconnect() {
        if (isConnected) {
            try {
                if (!socket.isClosed()) {
                    ServerLobby serverLobby = Server.getServerLobby(this);
                    if(serverLobby !=null)
                        serverLobby.onDisconnect(this);
                    socket.close();
                }
            } catch (IOException e) {
                Server.LOGGER.log(Level.SEVERE,"Error while closing the Socket Connection\n"+ e.getMessage());}
            socketListener.interrupt(); // Interrupts the thread
            isConnected = false;
        }
    }

    /**
     * Close connection to the client
     */
    public void closeConnection(){
        if (isConnected) {
            try {
                if (!socket.isClosed()) {
                    socket.close();
                }
            } catch (IOException e) {
                Server.LOGGER.log(Level.SEVERE,"Error while closing the Socket Connection\n"+ e.getMessage());}
            socketListener.interrupt(); // Interrupts the thread
            isConnected = false;
        }
    }
}
