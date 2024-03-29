package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.messages.serverMessages.PingMessage;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;

public class PingTimer {
    private final ServerLobby serverLobby;
    private final SocketConnection socketConnection;
    private Timer waitResponse;
    private final Timer pingDuringGame;

    /**
     * Creating PingTimer object that is used to ping clients and check if they are still connected
     * @param serverLobby serverLobby of the game
     * @param socketConnection socketConnection of the client
     */
    public PingTimer(ServerLobby serverLobby, SocketConnection socketConnection) {
        this.serverLobby = serverLobby;
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
        },0,200000);
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
                pingDuringGame.cancel();
                Server.LOGGER.log(Level.INFO,"No ping response...");
                serverLobby.onDisconnect(socketConnection);
            }
        },180000);
    }

    /**
     * This method will be called if the client has answered
     */
    public void hasResponded(){
        if(waitResponse!=null)
            waitResponse.cancel();
    }

    /**
     * Stop pinging the client
     */
    public void deleteTimer(){
        if(waitResponse!=null)
            waitResponse.cancel();
        pingDuringGame.cancel();
    }
}
