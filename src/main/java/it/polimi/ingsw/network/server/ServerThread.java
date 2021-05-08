package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.GameLobby;
import it.polimi.ingsw.controller.PlayerTurnManager;
import it.polimi.ingsw.exceptions.network.GameAlreadyStartedException;
import it.polimi.ingsw.exceptions.network.NicknameAlreadyUsedException;
import it.polimi.ingsw.exceptions.network.NotYourTurnException;
import it.polimi.ingsw.exceptions.network.UnrecognisedPlayerException;
import it.polimi.ingsw.model.Shop;
import it.polimi.ingsw.network.messages.clientMessages.ClientMessage;
import it.polimi.ingsw.network.messages.serverMessages.ErrorMessage;
import it.polimi.ingsw.network.messages.serverMessages.JoinedLobbyMessage;
import it.polimi.ingsw.network.messages.serverMessages.PreGameMessage;
import it.polimi.ingsw.network.messages.serverMessages.YourTurnMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;


public class ServerThread extends Thread implements Observer {
    private final Object gameLock = new Object();
    private Map<String, SocketConnection> clients;
    private PingTimer timer;
    private GameLobby gameLobby;

    /**
     * Creating Game Lobby, Clients HashMap and starting the Thread
     */
    public ServerThread(int numberOfPlayers){
        this.clients = new HashMap<>();


        //Server.LOGGER.log(Level.INFO, "ServerThread: "+getThreadId()+" Thread created, waiting for clients...");
        this.gameLobby = new GameLobby(Thread.currentThread().getId(),numberOfPlayers);
        //far partire timer per task preGame
        start(); //Start the thread
        Server.LOGGER.log(Level.INFO, "Server: "+getThreadId()+" Game lobby created with "+numberOfPlayers+" players.");
    }

    public Map<String, SocketConnection> getClients() {
        return new HashMap<>(clients);
    }

    public GameLobby getGameLobby() {
        return gameLobby;
    }
    public PlayerTurnManager getTurnManager(){return gameLobby.getGameManager().getTurnManager();}
    /**
     * Checks it's actually the player's turn and that the player is actually in this game
     *
     * @param socketConnection socketConnection of the client
     * @param deserializedMessage ClientMessage sent by the client
     */
    public void onMessage(SocketConnection socketConnection, ClientMessage deserializedMessage){
        if(gameLobby.isGameStarted()) {
            String actualPlayer = getTurnManager().getPlayer().getNickname();
            String askingPlayer = deserializedMessage.getNickname();

            //If there isn't the askingPlayer or the askingPlayer nickname on the clients map doesn't match the socketConnection
            if (!clients.containsKey(askingPlayer) || !clients.get(askingPlayer).equals(socketConnection))
                throw new UnrecognisedPlayerException();

            if (actualPlayer.equals(askingPlayer)) //If it's the player's turn
                deserializedMessage.useMessage(socketConnection, this);
            else
                throw new NotYourTurnException();
        }
        else
            deserializedMessage.useMessage(socketConnection, this);
    }
    /**
     * Forwarding Round then telling the new player that it's his turn to play
     */
    public void endRound(){
        //gameLobby.getGameManager().nextRound();
        String nickname = getTurnManager().getPlayer().getNickname();
        SocketConnection clientConnection;
        if(gameLobby.getNumberOfPlayers()!=1)
            clientConnection = clients.get(nickname);
        else
            do
                clientConnection = clients.get(nickname);
            while(clientConnection==null);
        /*YourTurnMessage yourTurn = new YourTurnMessage();
        clientConnection.send(yourTurn.serialize());
        timer = new PingTimer(this,clientConnection);
        timer.startPinging();*/
    }
    /**
     * Login of a player that disconnected before
     *
     * @param playerPosition position of the player in the arraylist of players in game
     * @param nickname player nickname
     * @param clientConnection socketConnection of the client
     */
    public void knownPlayerLogin(int playerPosition,String nickname,SocketConnection clientConnection){
        gameLobby.getGameManager().playerComeback(playerPosition,nickname);
        clients.put(nickname,clientConnection);
        Server.LOGGER.log(Level.INFO,nickname+" is back in the lobby #"+getThreadId());
        clientConnection.send(new JoinedLobbyMessage(getThreadId()).serialize());
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
                Server.LOGGER.log(Level.INFO,nickname+" joined the lobby #"+getThreadId()+", "+(gameLobby.getNumberOfPlayers()-clients.size())+" players to go!");
                clientConnection.send(new JoinedLobbyMessage(getThreadId()).serialize());
                if (gameLobby.getNumberOfPlayers() == 1) {
                    createGame(true, clientConnection);
                    clientConnection.send(new JoinedLobbyMessage(getThreadId()).serialize());
                }
                else if (clients.size() == gameLobby.getNumberOfPlayers()) {
                    createGame(false, clientConnection);
                    clientConnection.send(new JoinedLobbyMessage(getThreadId()).serialize());
                }
            } else {
                //clientConnection.disconnect();
                clientConnection.send(new ErrorMessage("This username: [" + nickname +"] is already taken!").serialize());
                throw new NicknameAlreadyUsedException(nickname);
            }
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
     * starting the game initializing the timer and then creating the model
     * @param singlePlayer true if it's a singlePlayer game
     */
    public void createGame(boolean singlePlayer,SocketConnection socketConnection){
        //to be completed
        gameLobby.initGame(singlePlayer);
        /*timer = new PingTimer(this,socketConnection);
        timer.startPinging();*/
        //ASSEGNAZIONE RISORSE E LEADER
        if(gameLobby.getNumberOfPlayers() != 1)
            preGame();
    }

    public void preGame(){
        int c=0;
        for (String p: gameLobby.getPlayers()) {
            String message = new PreGameMessage(gameLobby.getFourLeaders(),c).serialize();
            clients.get(p).send(message);
        }
        gameLobby.addInitialFaith();
    }

    /**
     *
     * @param socketConnection Client that is disconnecting
     */
    public void onDisconnect(SocketConnection socketConnection){
        timer.endTimer();
        if(!gameLobby.isGameStarted()){
            String disconnectedPlayerNickname = getPlayerNickname(socketConnection);
            Server.LOGGER.log(Level.INFO,"Disconnecting client: "+disconnectedPlayerNickname);
            gameLobby.removePlayer(disconnectedPlayerNickname);
            socketConnection.disconnect();
            clients.remove(disconnectedPlayerNickname);
            Server.LOGGER.log(Level.INFO,"Client disconnected, waiting for players to join the lobby...");
        }
        else {
            String currPlayerNickname = getTurnManager().getPlayer().getNickname();
            Server.LOGGER.log(Level.INFO,"Disconnecting client: "+currPlayerNickname);
            getTurnManager().getPlayer().setPlaying(false);
            socketConnection.disconnect();
            clients.remove(currPlayerNickname);
            Server.LOGGER.log(Level.INFO,"Client disconnected, going to next round...");
            endRound();
        }
    }




    /**
     * method called upon receiving a PingResponse, it reset the timer because the client is still connected
     */
    public void resetTimer(){
        timer.cancelTimer();
    }

    /**
     * Thread pinging clients to check if they are still playing
     */
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            synchronized (gameLock) {
                if (!gameLobby.isGameStarted() && !gameLobby.readyToStartGame()) {
                    synchronized (this) {
                        for (String key : clients.keySet()) {
                            timer = new PingTimer(this, clients.get(key));
                            timer.send();
                            try {
                                wait(1000);
                            } catch (InterruptedException e) {
                                Server.LOGGER.log(Level.SEVERE, e.getMessage());
                            }
                        }
                    }
                } else {
                    if (!gameLobby.isGameStarted()) {
                        //startGame()
                    } else {
                        //OBSERVER}
                    }
                }

            }
        }
    }

    /**
     *
     * @param clientConnection socketConnection of the client
     * @return player nickname from socketconnection
     */
    public String getPlayerNickname(SocketConnection clientConnection){
        for (String key : clients.keySet()) {
            if(clients.get(key).equals(clientConnection))
                return key;
        }
        return null;
    }
    /**
     *
     * @return this thread ID
     */
    public long getThreadId(){
        return Thread.currentThread().getId();
    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof Shop){
            //shopMessage
        }
    }
}

