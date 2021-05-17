package it.polimi.ingsw.network.client;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.card.Requirement;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.network.messages.clientMessages.CreateGameMessage;
import it.polimi.ingsw.network.messages.clientMessages.JoinGameMessage;
import it.polimi.ingsw.network.messages.clientMessages.PreGameResponseMessage;
import it.polimi.ingsw.network.messages.serverMessages.ServerMessage;
import it.polimi.ingsw.view.Cli;
import it.polimi.ingsw.view.View;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class ClientManager {
    protected static final Logger LOGGER = Logger.getLogger("Client");

    private String nickname;
    private ClientSocket clientSocket;
    private long serverThreadID = -1;
    private View view;
    private ClientGameStatus gameStatus;

    /**
     * Creates client Object, handles client connection and instantiates view
     * @param address address chosen
     * @param socketPort port chosen
     */
    public ClientManager(String address, int socketPort,String choice) {
        initLogger();
        this.nickname = "defaultNickname";
        if(choice.equals("-cli"))
            this.view = new Cli(this);
        //else
            //this.view = new Gui();
        view.start();
        connection(address,socketPort);
    }
    public ClientGameStatus getGameStatus() { return gameStatus;}
    public String getNickname() {
        return nickname;
    }
    public long getServerThreadID() {
        return serverThreadID;
    }
    public View getView(){ return view;}
    public ClientSocket getClientSocket(){return clientSocket;}

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public void setServerThreadID(long serverThreadID) {
        this.serverThreadID = serverThreadID;

    }

    /**
     * Creates a first gameStatus instance
     *
     * @param players players connected
     * @param shop shop
     * @param market market
     */
    public void initGameStatus(ArrayList<PlayerDashboard> players, Shop shop, MarketDashboard market, VaticanReport[] vReports){
        gameStatus = new ClientGameStatus(players,shop,market,vReports);
    }

    /**
     * Connection to the server
     * @param address address chosen
     * @param socketPort port chosen
     */
    public void connection(String address, int socketPort ){
        clientSocket = new ClientSocket(address, socketPort,this);
        clientSocket.startConnection();
        if(!clientSocket.isConnected())
            view.printMsg("Failed to connect to: "+ address+":" + socketPort);
    }

    /**
     * Handle a new message arrived from the server
     * @param msg serialized Json message
     */
    public void onMessage(String msg){
        ServerMessage deserializedMessage = ServerMessage.deserializeMessage(msg);
        deserializedMessage.useMessage(this);
    }

    /**
     *
     * @param numberOfPlayers for the game the player wants to create
     */
    public void createGame(int numberOfPlayers){
        String message = new CreateGameMessage(this.nickname,-1,numberOfPlayers).serialize();
        clientSocket.send(message);
    }

    /**
     * Creates and sends a JoinGameMessage
     *
     * @param serverThreadID chosen serverID for the game to join
     */
    public void joinGame(long serverThreadID){
        String message = new JoinGameMessage(this.nickname,serverThreadID).serialize();
        clientSocket.send(message);
    }

    public void preGameChoice(ArrayList<Resource> resources, ArrayList<LeaderCard> leaders){
        String message= new PreGameResponseMessage(this.nickname,serverThreadID,resources,leaders).serialize();
        clientSocket.send(message);
    }

    /**
     * Checking if the requirement is covered by the player
     *
     * @param req req affected
     * @return true if playable
     */
    public boolean isRequirementPossible(Requirement req){
        return req.isPlayable(gameStatus.getClientDashboard(nickname));
    }

    /**
     * Checks if the player has more or equals resources than the cost
     * @param cost passed amount of resources that are needed to pay
     * @return true if the payment is possible somewhere
     */
    public boolean hasEnoughResources(ResourceCount cost){
        return gameStatus.getClientDashboard(nickname).getTotalResources().hasMoreOrEqualsResources(cost);
    }

    /**
     * Check if the chosen amount of resources is actually available in the storage
     *
     * @param storage passed amount of resources from the storage
     * @return true if the chest has enough or more resources that the passed storage ResourceCount
     */
    public boolean storageCheck(ResourceCount storage){
        return gameStatus.getClientDashboard(nickname).getStorage().readStorage().hasMoreOrEqualsResources(storage);
    }

    /**
     * Check if the chosen amount of resources is actually available in the chest
     *
     * @param chest passed amount of resources from the chest
     * @return true if the chest has enough or more resources that the passed chest ResourceCount
     */
    public boolean chestCheck(ResourceCount chest){
        return gameStatus.getClientDashboard(nickname).getChest().hasMoreOrEqualsResources(chest);
    }

    /**
     *
     * @param ID
     * @return
     */
    public DevelopmentCard getShopCardByID(int ID){
        return gameStatus.getShop().getCardByID(ID);
    }

    /**
     * Creating logger file handler
     */
    private void initLogger() {
        Date date = GregorianCalendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM_HH.mm.ss");

        try {
            FileHandler fh = new FileHandler("utilities/client_log/client-" + dateFormat.format(date) + ".log");
            fh.setFormatter(new SimpleFormatter());

            LOGGER.addHandler(fh);
        } catch (IOException e) { LOGGER.severe(e.getMessage()); }
    }
}