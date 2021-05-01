package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.GameLobby;
import it.polimi.ingsw.network.messages.ConnectionMessage;
import it.polimi.ingsw.network.messages.Message;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class ServerThread implements Runnable{

    private Map<String, SocketConnection> clients;

    private GameLobby gameLobby;

    private Thread serverThread;


    protected ServerThread(){

        this.gameLobby = new GameLobby();

        this.clients = new HashMap<>();

        serverThread = new Thread(this);
        Server.LOGGER.log(Level.INFO, "ServerThread: "+Thread.currentThread().getId()+" Thread created.");
        Server.LOGGER.log(Level.INFO, "Server: "+Thread.currentThread().getId()+" Game lobby created.");
    }


    private void knownPlayerLogin(){

    }
    private void newPlayerLogin(){

    }
    private void playerLogin(ConnectionMessage msg){
        /*if(msg.getNickname().wasPlaying()){
            knownPlayerLogin();
        }
        else
            newPlayerLogin();*/
    }


    /**
     *
     * @param sockConnection Client that is disconnecting
     */
    public void onDisconnect(SocketConnection sockConnection){
        //Gestito per resilienza
    }




    @Override
    public void run(){
        while (!Thread.currentThread().isInterrupted()) {
                for(Map.Entry<String, SocketConnection> c: clients.entrySet())
                    if (c.getValue() == null || !c.getValue().isConnected()){
                        //Resilienza Disconnessioni
                    }
        }
    }

    public long getThreadId(){
        return Thread.currentThread().getId();
    }
}
