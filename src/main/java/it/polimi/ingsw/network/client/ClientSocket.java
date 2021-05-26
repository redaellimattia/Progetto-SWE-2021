package it.polimi.ingsw.network.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;

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
            ClientManager.LOGGER.info("Client successfully connected at: "+ address+":" + socketPort);
        } catch (IOException e) { ClientManager.LOGGER.severe("Failed to connect to: "+ address+":" + socketPort + "\n" +e.getMessage());}
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
        ArrayList<String> messageQueue = new ArrayList<>();
        while (!socketListener.isInterrupted()) {
            try {
                    /*while(in.ready()) {
                        String msg = in.readLine();
                        messageQueue.add(in.readLine());
                    }*/
                    String msg = in.readLine();
                    System.out.println(msg);
                    if (msg != null)
                        clientManager.onMessage(msg);
                    else
                        disconnect();
                /*while(messageQueue.size()>0){
                    String message = messageQueue.get(0);
                    if(message != null) {
                        clientManager.onMessage(message);
                        messageQueue.remove(0);
                    }
                    else
                        disconnect();
                }*/
                    //else disconnect();
            } catch (IOException e) {
                ClientManager.LOGGER.severe("Failed to read message from server: "+ e.getMessage());
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
            } catch (IOException e) {
                ClientManager.LOGGER.log(Level.SEVERE,"Error while closing the Socket Connection\n"+ e.getMessage());}

            socketListener.interrupt(); // Interrupts the thread
            isConnected = false;
        }
    }
}
