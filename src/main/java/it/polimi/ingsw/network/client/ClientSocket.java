package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.messages.clientMessages.AskLobbyMessage;
import it.polimi.ingsw.network.messages.clientMessages.CreateGameMessage;
import it.polimi.ingsw.network.server.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
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
     * Sends the first message, ASKLOBBIES to ask available lobbies
     */
    public void startConnection() {
        send(new AskLobbyMessage(clientManager.getNickname(), clientManager.getServerThreadID()).serialize());
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
        while (!Thread.currentThread().isInterrupted()) {
            try {
                String msg = in.readLine();
                //System.out.println(msg);
                if(msg!=null) clientManager.onMessage(msg);
                else disconnect();
            } catch (IOException e) {
                ClientManager.LOGGER.severe("Failed to read message from server: "+ e.getMessage());
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
