package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.messages.serverMessages.PingMessage;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;

public class PingTimer {
    private ServerThread serverThread;
    private SocketConnection socketConnection;
    private Timer waitResponse;

    /**
     * TimerTask that ends the connection if the client has not answered in 100ms
     */
    private TimerTask waitResponseTask = new TimerTask() {
        @Override
        public void run() {
            Server.LOGGER.log(Level.INFO,"No answer from client, going to disconnect it.");
            serverThread.onDisconnect(socketConnection);
        }
    };

    /**
     * Creating PingTimer object that is used to ping clients and check if they are still connected
     * @param serverThread serverThread of the game
     * @param socketConnection socketConnection of the client
     */
    public PingTimer(ServerThread serverThread, SocketConnection socketConnection) {
        this.serverThread = serverThread;
        this.socketConnection = socketConnection;
        waitResponse = new Timer();
    }

    /**
     * Send a message, then schedule the timer to run in 100ms
     */
    public void send(){
        socketConnection.send(new PingMessage().serialize());
        waitResponse.schedule(waitResponseTask,100);
    }

    /**
     * This method will be called if the client has answered
     */
    public void cancelTimer(){
        waitResponse.cancel();
    }
}

