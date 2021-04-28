package it.polimi.ingsw.network.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketConnection implements Runnable{
    private final SocketServer socketServer;
    private final Socket socket;

    private ObjectInputStream in;
    private ObjectOutputStream out;

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
                this.in = new ObjectInputStream(socket.getInputStream());
            }
            synchronized (outLock) {
                this.out = new ObjectOutputStream(socket.getOutputStream());
            }
        } catch (IOException e) {}

        socketListener = new Thread(this);
        socketListener.start();
    }

    /**
     * Running the Thread and keeping listening
     */
    @Override
    public void run() {
        /*while (!Thread.currentThread().isInterrupted()) {
            try {
                synchronized (inLock) {
                   //Lettura messaggi in entrata dal client, login e messaggi
                }
            } catch (IOException e) {
                disconnect();
            } catch (ClassNotFoundException e) {}
        }*/
    }

    /**
     * Sending messages to the client
     */
    public void send() {
        if (isConnected) {
            try {
                synchronized (outLock) {
                    //out.writeObject();
                    out.flush();
                }
            } catch (IOException e) {
                disconnect();
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
            } catch (IOException e) {}

            socketListener.interrupt(); // Interrupts the thread
            isConnected = false;

            socketServer.onDisconnect(this);
        }
    }

}
