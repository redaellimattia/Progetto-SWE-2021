package it.polimi.ingsw.network.client;

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
    private ClientManager clientManager;
    private Thread socketListener;


    /**
     * Creates a clientSocket, the connection of the client to the server
     * @param address address chosen
     * @param socketPort port chosen
     */
    public ClientSocket(String address, int socketPort, ClientManager clientManager) {
        try {
            this.clientManager = clientManager;
            this.socket = new Socket(address, socketPort);
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(socket.getOutputStream());
            this.isConnected = true;
            socketListener = new Thread(this);
            socketListener.start();
        } catch (IOException e) { System.out.println("Failed to connect to: "+ address+":" + socketPort + "\n" +e.getMessage());}
    }


    public boolean isConnected() {
        return isConnected;
    }

    /**
     * Send passed String
     * @param msg serialized Json message
     */
    public void send(String msg) {
        if (isConnected) {
            out.println(msg);
            out.flush();
        }
    }

    /**
     * While running, the thread checks the BufferedReader for available messages
     * If some message comes up, it will be sent to the deserializeMessage method on the client Object
     */
    @Override
    public void run() {
        while (!socketListener.isInterrupted()) {
            try {
                String msg = in.readLine();
                if (msg != null) {
                    //System.out.println(msg);
                    clientManager.onMessage(msg);
                }
                else
                    disconnect();
            } catch (IOException e) {
                clientManager.getView().printMsg("Can't reach the Server");
                disconnect();
            }
        }
    }

    /**
     * Server disconnected, closing socket
     */
    public void disconnect() {
        if (isConnected) {
            try {
                if (!socket.isClosed())
                    socket.close();
            } catch (IOException e) {System.out.println(e.getMessage());}
            socketListener.interrupt(); // Interrupts the thread
            isConnected = false;
        }
        if(!clientManager.isGameEnded()){
            clientManager.getView().failedConnection("Unexpectedly the server stopped running!");
        }
    }
}
