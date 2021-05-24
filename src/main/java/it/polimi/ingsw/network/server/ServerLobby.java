package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.GameLobby;
import it.polimi.ingsw.controller.PlayerTurnManager;
import it.polimi.ingsw.exceptions.network.GameAlreadyStartedException;
import it.polimi.ingsw.exceptions.network.NotYourTurnException;
import it.polimi.ingsw.exceptions.network.UnrecognisedPlayerException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.network.enumeration.ClientMessageType;
import it.polimi.ingsw.network.enumeration.PlayerUpdateType;
import it.polimi.ingsw.network.messages.clientMessages.ClientMessage;
import it.polimi.ingsw.network.messages.serverMessages.*;
import it.polimi.ingsw.network.messages.serverMessages.updates.*;

import java.util.ArrayList;
import java.util.Collections;
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
        this.gameLobby = new GameLobby(getLobbyId(),numberOfPlayers);
        this.gameLobby.addObserver(this);
        Server.LOGGER.log(Level.INFO, "Server: "+ getLobbyId()+" Game lobby created with "+numberOfPlayers+" players.");
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
            if(gameLobby.getPlayers().contains(deserializedMessage.getNickname())&&deserializedMessage.getType().equals(ClientMessageType.JOINGAME)) {
                for(PlayerDashboard p:gameLobby.getGameManager().getGame().getPlayers())
                    if(p.getNickname().equals(askingPlayer)&&!p.isPlaying())
                        deserializedMessage.useMessage(socketConnection, this);
                    else
                        if(p.getNickname().equals(askingPlayer)) {
                            socketConnection.send(new PrintMessage("You can't join this lobby, " + askingPlayer + " is already in it!").serialize());
                            socketConnection.send(Server.createReturnLobbiesMessage().serialize());
                        }
            }
            else {
                if(!(gameLobby.getPlayers().contains(deserializedMessage.getNickname()))&&deserializedMessage.getType().equals(ClientMessageType.JOINGAME)) {
                    socketConnection.send(new PrintMessage("You can't join this lobby, it's already started without you").serialize());
                    socketConnection.send(Server.createReturnLobbiesMessage().serialize());
                }
                //If there isn't the askingPlayer or the askingPlayer nickname on the clients map doesn't match the socketConnection
                if (!clients.containsKey(askingPlayer) || !clients.get(askingPlayer).equals(socketConnection))
                    throw new UnrecognisedPlayerException();

                if (actualPlayer.equals(askingPlayer)) { //If it's the player's turn
                    deserializedMessage.useMessage(socketConnection, this);
                    sendToAll(new DoneMessage().serialize());
                }
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
     * Login of a player that disconnected before,
     * Sets the known player as playing (isPlaying = true) then, if it's the only one playing, send him the YourTurnMessage
     *
     * @param playerPosition position of the player in the arraylist of players in game
     * @param nickname player nickname
     * @param clientConnection socketConnection of the client
     */
    public void knownPlayerLogin(int playerPosition,String nickname,SocketConnection clientConnection){
        synchronized (gameLock) {
            if(gameLobby.isGameCreated()) {
                clients.put(nickname, clientConnection);
                Server.LOGGER.log(Level.INFO, nickname + " is back in the lobby #" + getLobbyId());
                clientConnection.send(new JoinedLobbyMessage(getLobbyId()).serialize());
                clientConnection.send(new InitGameStatusMessage(gameLobby.getGameManager().getGame().getPlayers(), gameLobby.getGameManager().getGame().getShop(), gameLobby.getGameManager().getGame().getMarket(), gameLobby.getGameManager().getGame().getvReports()).serialize());
                if(gameLobby.isGameStarted())
                    clientConnection.send(new GameStartedMessage().serialize());

                if(gameLobby.isGameStarted()&&!gameLobby.getGameManager().isAnyoneConnected()) {
                    for(PlayerDashboard p:gameLobby.getGameManager().getGame().getPlayers()){
                        if(p.getNickname().equals(nickname)) {
                            gameLobby.getGameManager().getTurnManager().setPlayer(p);
                            clientConnection.send(new YourTurnMessage().serialize());
                        }
                    }
                }
                else {
                    if (gameLobby.getNumberOfPlayers() == 1 && gameLobby.isGameStarted())
                        clientConnection.send(new YourTurnMessage().serialize());
                    else if (gameLobby.getNumberOfPlayers() == 1 && !gameLobby.isGameStarted())
                        gameLobby.addReadyPlayer();
                    else
                        clientConnection.send(new WaitYourTurnMessage().serialize());
                }
                gameLobby.getGameManager().playerComeback(playerPosition);
            }
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
            Server.LOGGER.log(Level.INFO,nickname+" joined the lobby #"+ getLobbyId()+", "+(gameLobby.getNumberOfPlayers()-clients.size())+" players to go!");
            clientConnection.send(new JoinedLobbyMessage(getLobbyId()).serialize());
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
        if(gameLobby.isGameCreated())
            playerPosition = gameLobby.getGameManager().wasPlaying(nickname);
        if(playerPosition!=-1)
            knownPlayerLogin(playerPosition,nickname,clientConnection);
        else
            if(Server.checkNickname(nickname))
                newPlayerLogin(nickname,clientConnection);
            else {
                clientConnection.send(new PrintMessage("You can't join this lobby, " + nickname + " is already in it!").serialize());
                clientConnection.send(Server.createReturnLobbiesMessage().serialize());
            }
    }

    /**
     * starting the game initializing the timer and then creating the model
     * @param singlePlayer true if it's a singlePlayer game
     */
    public void createGame(boolean singlePlayer){
        gameLobby.initGame(singlePlayer,this);
        sendToAll(new InitGameStatusMessage(gameLobby.getGameManager().getGame().getPlayers(), gameLobby.getGameManager().getGame().getShop(), gameLobby.getGameManager().getGame().getMarket(),gameLobby.getGameManager().getGame().getvReports()).serialize());
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
     * 3 Different disconnections:
     * 1. Player disconnected while the game is not even started, empty space for new players.
     * 2. Player disconnected while the game is started, sets the player as not playing (isPlaying = false)
     * 3. Player disconnected during the preGame, just assign him random resources and set him as not playing (isPlaying = false)
     *
     * @param socketConnection Client that is disconnecting
     */
    public void onDisconnect(SocketConnection socketConnection){
        Server.LOGGER.log(Level.INFO,"No answer from client, going to disconnect it.");
        String disconnectedPlayerNickname = getPlayerNickname(socketConnection);
        Server.LOGGER.log(Level.INFO, "Disconnecting client: " + disconnectedPlayerNickname);
        synchronized (gameLock) {
            if (!gameLobby.isGameCreated()) {
                gameLobby.removePlayer(disconnectedPlayerNickname);
                clients.remove(disconnectedPlayerNickname);
                Server.LOGGER.log(Level.INFO, "Client disconnected, waiting for players to join the lobby...");
            } else {
                if(gameLobby.isGameStarted()){
                    for(PlayerDashboard p: gameLobby.getGameManager().getGame().getPlayers())
                        if(p.getNickname().equals(disconnectedPlayerNickname))
                            p.setPlaying(false);
                    clients.remove(disconnectedPlayerNickname);
                    Server.LOGGER.log(Level.INFO, "Client disconnected, going to next round...");
                    if(disconnectedPlayerNickname.equals(getTurnManager().getPlayer().getNickname()))
                        endRound();
                }
                else{
                    ArrayList<Resource> resources = new ArrayList<>();
                    resources.add(Resource.COIN);
                    resources.add(Resource.ROCK);
                    resources.add(Resource.SERVANT);
                    resources.add(Resource.SHIELD);
                    Collections.shuffle(resources);
                    ArrayList<LeaderCard> fourLeaders = gameLobby.getFourLeaders(disconnectedPlayerNickname);
                    ArrayList<LeaderCard> chosenLeaders = new ArrayList<>();
                    Collections.shuffle(fourLeaders);
                    chosenLeaders.add(fourLeaders.get(0));
                    chosenLeaders.add(fourLeaders.get(1));
                    ArrayList<PlayerDashboard> players = gameLobby.getGameManager().getGame().getPlayers();
                    for(int i=0;i<players.size();i++){
                        PlayerDashboard p = players.get(i);
                        if(p.getNickname().equals(disconnectedPlayerNickname)){
                            switch(i){
                                case 0: p.setLeaderCards(chosenLeaders);
                                        break;
                                case 1:
                                case 2:
                                        Collections.shuffle(resources);
                                        ArrayList<Resource> chosenResources = new ArrayList<>();
                                        chosenResources.add(resources.get(0));
                                        gameLobby.preGame(disconnectedPlayerNickname,chosenResources,chosenLeaders);
                                        break;
                                case 3: Collections.shuffle(resources);
                                        chosenResources = new ArrayList<>();
                                        chosenResources.add(resources.get(0));
                                        chosenResources.add(resources.get(1));
                                        gameLobby.preGame(disconnectedPlayerNickname,chosenResources,chosenLeaders);
                                        break;
                            }
                            p.setPlaying(false);
                            break;
                        }
                    }
                    clients.remove(disconnectedPlayerNickname);
                    Server.LOGGER.log(Level.INFO, "Client disconnected during preGame, setPlaying to false...");
                    if(gameLobby.getNumberOfPlayers()>1)
                        gameLobby.addReadyPlayer();
                }

            }
        }
    }

    /**
     * Starts the game
     */
    public void startGame(){
        gameLobby.setGameStarted(true);
        SocketConnection socketConnection;
        String firstPlayerNickname = gameLobby.getGameManager().getTurnManager().getPlayer().getNickname();
        sendToAll(new GameStartedMessage().serialize());
        sendToAll(new WaitYourTurnMessage().serialize());
        for(int i=0;i<gameLobby.getGameManager().getGame().getPlayers().size();i++){
            PlayerDashboard p = gameLobby.getGameManager().getGame().getPlayers().get(i);
            if(p.isPlaying()){
                gameLobby.getGameManager().getTurnManager().setPlayer(p);
                socketConnection = clients.get(p.getNickname());
                socketConnection.send(new YourTurnMessage().serialize());
                break;
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
     * @return this Server ID
     */
    public long getLobbyId(){
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
    public void updateDevCards(String nickname, DeckDashboard[] devCards) {
        sendToAll(new DevCardsUpdateMessage(nickname,devCards).serialize());
    }

    @Override
    public void updateRemoveLeader(String nickname, ArrayList<LeaderCard> leaderCards) {
        sendToAll(new LeaderUpdateMessage(PlayerUpdateType.DISCARDLEADER,nickname,leaderCards).serialize());
    }

    @Override
    public void updatePathPosition(PlayerDashboard player, int position) {
        if(gameLobby.getGameManager()!=null)
            gameLobby.getGameManager().getGame().checkFaithPath(player);
        sendToAll(new PathPositionUpdateMessage(player.getNickname(),position).serialize());
    }

    @Override
    public void updateInGameLeader(String nickname, ArrayList<LeaderCard> leaderCards) {
        sendToAll(new LeaderUpdateMessage(PlayerUpdateType.INGAMELEADER,nickname,leaderCards).serialize());
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
    public void updateVaticanReport(int victoryPoints,ArrayList<String> nicknames){
        sendToAll(new VaticanReportActivatedMessage(victoryPoints,nicknames).serialize());
    }

    @Override
    public void updateStartGame(){
        startGame();
    }

    @Override
    public void updateEndGame(){
        if(gameLobby.getNumberOfPlayers()==1)
            sendToAll(new EndSinglePlayerGameMessage(gameLobby.getGameManager().getGame().isLorenzoWin(),gameLobby.getGameManager().getTurnManager().getPlayer().getPoints()).serialize());
        else
            sendToAll(new EndMultiPlayerGameMessage(gameLobby.getGameManager().getGame().getPlayers()).serialize());
    }

    @Override
    public void updateLeaders(String nickname, ArrayList<LeaderCard> leaderCards){
        sendToAll(new LeaderChoiceMessage(nickname,leaderCards).serialize());

    }
    @Override
    public void setGameMustEnd(){
        gameLobby.getGameManager().setGameMustEnd();
    }
}

