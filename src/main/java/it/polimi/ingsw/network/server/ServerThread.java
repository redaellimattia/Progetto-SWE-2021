package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.GameLobby;
import it.polimi.ingsw.exceptions.network.GameAlreadyStartedException;
import it.polimi.ingsw.exceptions.network.NicknameAlreadyUsedException;
import it.polimi.ingsw.network.messages.clientMessages.CreateGameMessage;
import it.polimi.ingsw.network.messages.serverMessages.YourTurnMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class ServerThread extends Thread{
    private final Object gameLock = new Object();
    private Map<String, SocketConnection> clients;

    private GameLobby gameLobby;


    /**
     * Creating Game Lobby, Clients HashMap and starting the Thread
     */
    public ServerThread(int numberOfPlayers){
        this.clients = new HashMap<>();

        start(); //Start the thread
        Server.LOGGER.log(Level.INFO, "ServerThread: "+Thread.currentThread().getId()+" Thread created, waiting for clients...");
        GameLobby gamelobby = new GameLobby(Thread.currentThread().getId(),numberOfPlayers);
        Server.LOGGER.log(Level.INFO, "Server: "+Thread.currentThread().getId()+" Game lobby created with "+numberOfPlayers+" players.");
    }

    public Map<String, SocketConnection> getClients() {
        return new HashMap<>(clients);
    }

    public GameLobby getGameLobby() {
        return gameLobby;
    }

    /**
     * Forwarding Round then telling the new player that it's his turn to play
     */
    public void endRound(){
        gameLobby.getGameManager().nextRound();
        String nickname = gameLobby.getGameManager().getTurnManager().getPlayer().getNickname();
        SocketConnection clientConnection = clients.get(nickname);
        YourTurnMessage yourTurn = new YourTurnMessage();
        clientConnection.send(yourTurn.serialize());
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
        synchronized (gameLock) {
            if (gameLobby.isGameStarted())
                throw new GameAlreadyStartedException();
            if (Server.checkNickname(nickname)) {
                gameLobby.addPlayer(nickname);
                clients.put(nickname, clientConnection);
                if (gameLobby.getNumberOfPlayers() == 1)
                    gameLobby.startGame(true);
                else if (clients.size() == gameLobby.getNumberOfPlayers())
                    gameLobby.startGame(false);
            } else
                throw new NicknameAlreadyUsedException(nickname);
        }
    }

    /**
     *
     * @param nickname nickname chosen by the client
     * @param clientConnection socketConnection of the client
     */
    public void playerLogin(String nickname, SocketConnection clientConnection){
        int playerPosition = gameLobby.getGameManager().wasPlaying(nickname);
        if(playerPosition!=-1)
            knownPlayerLogin(playerPosition,nickname,clientConnection);
        else
            newPlayerLogin(nickname,clientConnection);
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

    /**
     *
     * @return this thread ID
     */
    public long getThreadId(){
        return Thread.currentThread().getId();
    }
}
