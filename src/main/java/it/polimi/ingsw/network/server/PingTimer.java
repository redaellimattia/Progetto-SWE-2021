package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.messages.serverMessages.PingMessage;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;

public class PingTimer {
    private ServerThread serverThread;
    private SocketConnection socketConnection;
    private Timer waitResponse;
    private Timer pingDuringGame;

    /**
     * Creating PingTimer object that is used to ping clients and check if they are still connected
     * @param serverThread serverThread of the game
     * @param socketConnection socketConnection of the client
     */
    public PingTimer(ServerThread serverThread, SocketConnection socketConnection) {
        this.serverThread = serverThread;
        this.socketConnection = socketConnection;
        pingDuringGame = new Timer();
    }

    /**
     * Send a message every 200ms
     */
    public void startPinging(){
        pingDuringGame.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                send();
            }
        },0,1000);
    }


    /**
     * Send a message, then schedule the timer to run in 100ms
     */
    public void send(){
        socketConnection.send(new PingMessage().serialize());
        waitResponse = new Timer();
        waitResponse.schedule(new TimerTask() {
            @Override
            public void run() {
                Server.LOGGER.log(Level.INFO,"No answer from client, going to disconnect it.");
                serverThread.onDisconnect(socketConnection);
            }
        },500);
    }

    /**
     * This method will be called if the client has answered
     */
    public void cancelTimer(){
        waitResponse.cancel();
    }

    /**
     * Stop pinging the client
     */
    public void endTimer(){
        pingDuringGame.cancel();
    }
}

