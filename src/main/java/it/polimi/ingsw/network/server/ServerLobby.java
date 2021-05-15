package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.GameLobby;
import it.polimi.ingsw.controller.PlayerTurnManager;
import it.polimi.ingsw.exceptions.network.GameAlreadyStartedException;
import it.polimi.ingsw.exceptions.network.NotYourTurnException;
import it.polimi.ingsw.exceptions.network.UnrecognisedPlayerException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.network.enumeration.ClientMessageType;
import it.polimi.ingsw.network.enumeration.PlayerUpdateType;
import it.polimi.ingsw.network.messages.clientMessages.ClientMessage;
import it.polimi.ingsw.network.messages.serverMessages.*;
import it.polimi.ingsw.network.messages.serverMessages.updates.*;
import it.polimi.ingsw.view.Cli;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;


public class ServerLobby extends Thread implements Observer {
    private final Object gameLock = new Object();
    private Map<String, SocketConnection> clients;
    private GameLobby gameLobby;
    private final long serverID;

    /**
     * Creating Game Lobby, Clients HashMap and starting the Thread
     */
    public ServerLobby(int numberOfPlayers, long serverID){
        this.clients = new HashMap<>();
        this.serverID = serverID;
        this.gameLobby = new GameLobby(getThreadId(),numberOfPlayers);
        this.gameLobby.addObserver(this);
        Server.LOGGER.log(Level.INFO, "Server: "+getThreadId()+" Game lobby created with "+numberOfPlayers+" players.");
    }

    public Map<String, SocketConnection> getClients() {
        return new HashMap<>(clients);
    }

    /**
     *
     * @return this gameLobby
     */
    public GameLobby getGameLobby() {
        return gameLobby;
    }

    /**
     *
     * @return this game's turnManager
     */
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
            //Player trying to reconnect
            if(gameLobby.getPlayers().contains(deserializedMessage.getNickname())&&deserializedMessage.getType().equals(ClientMessageType.JOINGAME))
                deserializedMessage.useMessage(socketConnection,this);
            else {
                if(!(gameLobby.getPlayers().contains(deserializedMessage.getNickname()))&&deserializedMessage.getType().equals(ClientMessageType.JOINGAME)) {
                    socketConnection.send(new PrintMessage("You can't join this lobby, it's already started without you").serialize());
                    socketConnection.send(Server.createReturnLobbiesMessage().serialize());
                }
                //If there isn't the askingPlayer or the askingPlayer nickname on the clients map doesn't match the socketConnection
                if (!clients.containsKey(askingPlayer) || !clients.get(askingPlayer).equals(socketConnection))
                    throw new UnrecognisedPlayerException();

                if (actualPlayer.equals(askingPlayer)) //If it's the player's turn
                    deserializedMessage.useMessage(socketConnection, this);
                else
                    throw new NotYourTurnException();
            }
        }
        else
            deserializedMessage.useMessage(socketConnection,this);
    }


    /**
     * Forwarding Round then telling the new player that it's his turn to play
     */
    public void endRound(){
        gameLobby.getGameManager().nextRound(false);
        String nextPlayerNickname = getTurnManager().getPlayer().getNickname();
        SocketConnection socketConnection = clients.get(nextPlayerNickname);
        if(socketConnection!=null)
            socketConnection.send(new YourTurnMessage().serialize());
    }

    /**
     * Login of a player that disconnected before
     *
     * @param playerPosition position of the player in the arraylist of players in game
     * @param nickname player nickname
     * @param clientConnection socketConnection of the client
     */
    public void knownPlayerLogin(int playerPosition,String nickname,SocketConnection clientConnection){
        synchronized (gameLock) {
            gameLobby.getGameManager().playerComeback(playerPosition);
            clients.put(nickname, clientConnection);
            Server.LOGGER.log(Level.INFO, nickname + " is back in the lobby #" + getThreadId());
            clientConnection.send(new JoinedLobbyMessage(getThreadId()).serialize());
            clientConnection.send(new InitGameStatusMessage(gameLobby.getGameManager().getGame().getPlayers(), gameLobby.getGameManager().getGame().getShop(),gameLobby.getGameManager().getGame().getMarket()).serialize());
        }
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
            gameLobby.addPlayer(nickname);
            clients.put(nickname, clientConnection);
            Server.LOGGER.log(Level.INFO,nickname+" joined the lobby #"+getThreadId()+", "+(gameLobby.getNumberOfPlayers()-clients.size())+" players to go!");
            clientConnection.send(new JoinedLobbyMessage(getThreadId()).serialize());
            //Send message to the clients that are waiting
            for(String key : clients.keySet())
                if(!key.equals(nickname))
                    clients.get(key).send(new PrintMessage(nickname+" joined the lobby, "+(gameLobby.getNumberOfPlayers()-clients.size())+" players to go!").serialize());
            if (gameLobby.getNumberOfPlayers() == 1)
                createGame(true);
            else if (clients.size() == gameLobby.getNumberOfPlayers())
                createGame(false);
        }
    }

    /**
     *
     * @param nickname nickname chosen by the client
     * @param clientConnection socketConnection of the client
     */
    public void playerLogin(String nickname, SocketConnection clientConnection){
        int playerPosition = -1;
        if(gameLobby.isGameStarted())
            playerPosition = gameLobby.getGameManager().wasPlaying(nickname);
        if(playerPosition!=-1)
            knownPlayerLogin(playerPosition,nickname,clientConnection);
        else
            newPlayerLogin(nickname,clientConnection);
    }

    /**
     * starting the game initializing the timer and then creating the model
     * @param singlePlayer true if it's a singlePlayer game
     */
    public void createGame(boolean singlePlayer){
        gameLobby.initGame(singlePlayer,this);
        sendToAll(new InitGameStatusMessage(gameLobby.getGameManager().getGame().getPlayers(), gameLobby.getGameManager().getGame().getShop(), gameLobby.getGameManager().getGame().getMarket()).serialize());
        preGame();
    }

    /**
     * PreGame Set-Up choose 2 LeaderCards, and resources
     */
    public void preGame(){
        synchronized (gameLock) {
            for (int i = 0; i < gameLobby.getPlayers().size(); i++) {
                String p = gameLobby.getPlayers().get(i);
                String message = null;
                switch (i) {
                    case 0:
                        message = new PreGameMessage(gameLobby.getFourLeaders(p), 0).serialize();
                        break;
                    case 1:
                    case 2:
                        message = new PreGameMessage(gameLobby.getFourLeaders(p), 1).serialize();
                        break;
                    case 3:
                        message = new PreGameMessage(gameLobby.getFourLeaders(p), 2).serialize();
                        break;
                }
                clients.get(p).send(message);
            }
            gameLobby.addInitialFaith();
        }
    }

    /**
     *
     * @param socketConnection Client that is disconnecting
     */
    public void onDisconnect(SocketConnection socketConnection){
        //timer.endTimer();
        Server.LOGGER.log(Level.INFO,"No answer from client, going to disconnect it.");
        synchronized (gameLock) {
            if (!gameLobby.isGameStarted()) {
                String disconnectedPlayerNickname = getPlayerNickname(socketConnection);
                Server.LOGGER.log(Level.INFO, "Disconnecting client: " + disconnectedPlayerNickname);
                gameLobby.removePlayer(disconnectedPlayerNickname);
                clients.remove(disconnectedPlayerNickname);
                Server.LOGGER.log(Level.INFO, "Client disconnected, waiting for players to join the lobby...");
            } else {
                String disconnectedPlayerNickname = getPlayerNickname(socketConnection);
                Server.LOGGER.log(Level.INFO, "Disconnecting client: " + disconnectedPlayerNickname);
                for(PlayerDashboard p: gameLobby.getGameManager().getGame().getPlayers())
                    if(p.getNickname().equals(disconnectedPlayerNickname))
                        p.setPlaying(false);
                clients.remove(disconnectedPlayerNickname);
                Server.LOGGER.log(Level.INFO, "Client disconnected, going to next round...");
                if(disconnectedPlayerNickname.equals(getTurnManager().getPlayer().getNickname()))
                    endRound();
            }
        }
    }

    /**
     * Starts the game
     */
    public void startGame(){
        gameLobby.setGameStarted(true);
        sendToAll(new WaitYourTurnMessage().serialize());
        gameLobby.getGameManager().nextRound(clients.size()==1);
        String firstPlayerNickname = gameLobby.getGameManager().getTurnManager().getPlayer().getNickname();
        SocketConnection socketConnection = clients.get(firstPlayerNickname);
        socketConnection.send(new YourTurnMessage().serialize());
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
     * @return this Server ID
     */
    public long getThreadId(){
        return this.serverID;
    }

    /**
     * Broadcasting a message
     *
     * @param msg serialized message
     */
    private void sendToAll(String msg){
        synchronized (gameLock) {
            for (String key : clients.keySet())
                clients.get(key).send(msg);
        }
    }

    @Override
    public void updateShop(DeckShop[][] shopGrid) {
        sendToAll(new ShopUpdateMessage(shopGrid).serialize());
    }

    @Override
    public void updateMarket(MarketMarble[][] structure, MarketMarble freeMarble) {
        sendToAll(new MarketUpdateMessage(structure,freeMarble).serialize());
    }

    @Override
    public void updateChest(String nickname, ResourceCount chest) {
        sendToAll(new ResourceCountUpdateMessage(PlayerUpdateType.CHEST,nickname,chest).serialize());
    }

    @Override
    public void updateBufferProduction(String nickname, ResourceCount chest) {
        sendToAll(new ResourceCountUpdateMessage(PlayerUpdateType.BUFFERPRODUCTION,nickname,chest).serialize());
    }

    @Override
    public void updateArrayDeposit(String nickname, ArrayList<CounterTop> arrayDeposit) {
        sendToAll(new ArrayDepositUpdateMessage(nickname,arrayDeposit).serialize());
    }

    @Override
    public void updateInitArrayDeposit(String nickname, Resource res) {
        sendToAll(new InitArrayDepositUpdateMessage(nickname, res).serialize());
    }

    @Override
    public void updateDevCards(String nickname, DevelopmentCard card, int position) {
        sendToAll(new DevCardsUpdateMessage(nickname,card,position).serialize());
    }

    @Override
    public void updateRemoveLeader(String nickname, int position) {
        sendToAll(new LeaderUpdateMessage(PlayerUpdateType.DISCARDLEADER,nickname,position).serialize());
    }

    @Override
    public void updatePathPosition(PlayerDashboard player, int position) {
        if(gameLobby.getGameManager()!=null)
            gameLobby.getGameManager().checkFaithPath(player);
        sendToAll(new PathPositionUpdateMessage(player.getNickname(),position).serialize());
    }

    @Override
    public void updateInGameLeader(String nickname, int position) {
        sendToAll(new LeaderUpdateMessage(PlayerUpdateType.INGAMELEADER,nickname,position).serialize());
    }

    @Override
    public void updateFirstRow(String nickname, CounterTop firstRow) {
        sendToAll(new StorageUpdateMessage(PlayerUpdateType.FIRSTROW,nickname,firstRow).serialize());
    }

    @Override
    public void updateSecondRow(String nickname, CounterTop secondRow) {
        sendToAll(new StorageUpdateMessage(PlayerUpdateType.SECONDROW,nickname,secondRow).serialize());
    }

    @Override
    public void updateThirdRow(String nickname, CounterTop thirdRow) {
        sendToAll(new StorageUpdateMessage(PlayerUpdateType.THIRDROW,nickname,thirdRow).serialize());
    }

    @Override
    public void updateVictoryPoints(String nickname, int points) {
        sendToAll(new VictoryPointsUpdateMessage(nickname,points).serialize());
    }

    @Override
    public void updateVaticanReport(String nickname, int victoryPoints, boolean gotIt){
        if(gotIt)
            clients.get(nickname).send(new PrintMessage("A vatican report has been activated, you earned: "+victoryPoints+" points!").serialize());
        else
            clients.get(nickname).send(new PrintMessage("A vatican report has been activated, unfortunately you didn't benefit from that!").serialize());
    }

    @Override
    public void updateStartGame(){
        startGame();
    }

    @Override
    public void updateEndGame(){
        if(gameLobby.getNumberOfPlayers()==1)
            sendToAll(new EndSinglePlayerGameMessage(gameLobby.getGameManager().getGame().isLorenzoWin(),gameLobby.getGameManager().getTurnManager().getPlayer()).serialize());
        else
            sendToAll(new EndMultiPlayerGameMessage(gameLobby.getGameManager().getGame().getPlayers()).serialize());
    }

}

