package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.GameLobby;
import it.polimi.ingsw.exceptions.network.nicknameAlreadyUsedException;
import it.polimi.ingsw.network.messages.ConnectionMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class ServerThread extends Thread{

    private Map<String, SocketConnection> clients;

    private GameLobby gameLobby;


    /**
     * Creating Game Lobby, Clients HashMap and starting the Thread
     */
    protected ServerThread(){

        this.gameLobby = new GameLobby();

        this.clients = new HashMap<>();

        start(); //Start the thread
        Server.LOGGER.log(Level.INFO, "ServerThread: "+Thread.currentThread().getId()+" Thread created, waiting for clients...");
        Server.LOGGER.log(Level.INFO, "Server: "+Thread.currentThread().getId()+" Game lobby created.");
    }

    public Map<String, SocketConnection> getClients() {
        return new HashMap<>(clients);
    }

    public GameLobby getGameLobby() {
        return gameLobby;
    }

    /**
     *
     * @param playerPosition position of the player in the arraylist of players in game
     * @param nickname player nickname
     * @param clientConnection socketConnection of the client
     */
    public void knownPlayerLogin(int playerPosition,String nickname,SocketConnection clientConnection){
        gameLobby.getGameManager().playerComeback(playerPosition,nickname);
        clients.put(nickname,clientConnection);
    }

    /**
     *
     * @param nickname nickname chosen by the client
     * @param clientConnection socketConnection of the client
     */
    public void newPlayerLogin(String nickname,SocketConnection clientConnection){
        //QUANTI PLAYER NELLA LOBBY?
        if(gameLobby.checkNickname(nickname)) {
            gameLobby.addPlayer(nickname);
            clients.put(nickname, clientConnection);
        }
        else
            throw new nicknameAlreadyUsedException(nickname);
    }

    /**
     *
     * @param msg ConnectionMessage received from the client
     */
    public void playerLogin(ConnectionMessage msg,SocketConnection clientConnection){
        int playerPosition = gameLobby.getGameManager().wasPlaying(msg.getNickname());
        if(playerPosition!=-1)
            knownPlayerLogin(playerPosition,msg.getNickname(),clientConnection);
        else
            newPlayerLogin(msg.getNickname(),clientConnection);
    }


    /**
     *
     * @param sockConnection Client that is disconnecting
     */
    public void onDisconnect(SocketConnection sockConnection){
        //Gestito per resilienza
    }


    /**
     * Thread pinging clients to check if they are still playing
     */
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
