package it.polimi.ingsw.network.server;

import java.io.*;
import java.net.Socket;
import java.util.logging.Level;

public class SocketConnection implements Runnable{
    private final SocketServer socketServer;
    private final Socket socket;

    private BufferedReader  in;
    private PrintWriter out;

    private final Object outLock = new Object();
    private final Object inLock = new Object();

    private boolean isConnected;

    private Thread socketListener;

    /**
     *
     * @return true if there's a connection ongoing
     */
    public boolean isConnected() {
        return isConnected;
    }

    /**
     *
     * @param socketServer ServerSocket Object Server-Side
     * @param socket Socket Object Client-Side
     */
    public SocketConnection(SocketServer socketServer, Socket socket) {
        this.socketServer = socketServer;
        this.socket = socket;

        this.isConnected = true;

        try {
            synchronized (inLock) {
                this.in = new BufferedReader (new InputStreamReader(socket.getInputStream()));
            }
            synchronized (outLock) {
                this.out = new PrintWriter(socket.getOutputStream());
            }
        } catch (IOException e) {Server.LOGGER.log(Level.SEVERE,"Error while creating the Socket Connection.\n"+ e.getMessage());}

        socketListener = new Thread(this);
        socketListener.start();
    }

    /**
     * Running the Thread and keeping listening
     */
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                synchronized (inLock) {
                    String msg = in.readLine();
                    if(msg!=null) socketServer.onMessage(this,msg);
                }
                //Lettura messaggi in entrata dal client, login e messaggi
            } catch (IOException e) { Server.LOGGER.log(Level.SEVERE,"Error while reading.\n"+ e.getMessage());}
        }
    }

    /**
     * Sending messages to the client
     */
    public void send(String json) {
        if (isConnected) {
            synchronized (outLock) {
                out.println(json);
                out.flush();
            }
        }
    }

    /**
     * Client is disconnecting
     */
    public void disconnect() {
        if (isConnected) {
            try {
                if (!socket.isClosed())
                    socket.close();
            } catch (IOException e) {Server.LOGGER.log(Level.SEVERE,"Error while closing the Socket Connection\n"+ e.getMessage());}

            socketListener.interrupt(); // Interrupts the thread
            isConnected = false;

            socketServer.onDisconnect(this);
        }
    }

}
