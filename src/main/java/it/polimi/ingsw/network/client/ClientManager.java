package it.polimi.ingsw.network.client;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.card.ProductionAbility;
import it.polimi.ingsw.model.card.Requirement;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.network.messages.clientMessages.*;
import it.polimi.ingsw.network.messages.clientMessages.actionMessages.CardShopMessage;
import it.polimi.ingsw.network.messages.clientMessages.actionMessages.DiscardLeaderMessage;
import it.polimi.ingsw.network.messages.clientMessages.actionMessages.PlayLeaderMessage;
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
    private long serverLobbyID = -1;
    private View view;
    private ClientGameStatus gameStatus;
    private boolean mainActionDone;
    private boolean isMyTurn;
    private boolean gameStarted;

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
        this.mainActionDone = false;
        this.gameStarted = false;
    }
    public ClientGameStatus getGameStatus() { return gameStatus;}
    public String getNickname() {
        return nickname;
    }
    public long getServerLobbyID() {
        return serverLobbyID;
    }
    public View getView(){ return view;}
    public ClientSocket getClientSocket(){return clientSocket;}

    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public void setServerLobbyID(long serverLobbyID) {
        this.serverLobbyID = serverLobbyID;
    }

    /**
     * Creates a first gameStatus instance
     *
     * @param players players connected
     * @param shop shop
     * @param market market
     */
    public void initGameStatus(ArrayList<PlayerDashboard> players, Shop shop, MarketDashboard market,VaticanReport[] vReports){
        gameStatus = new ClientGameStatus(players,shop,market,vReports);
    }

    /**
     * Connection to the server
     * @param address address chosen
     * @param socketPort port chosen
     */
    public void connection(String address, int socketPort ){
        clientSocket = new ClientSocket(address, socketPort,this);
        askLobbies();
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
        clientSocket.send(new CreateGameMessage(this.nickname,-1,numberOfPlayers).serialize());
    }

    /**
     * Sends a message to ask available lobbies
     */
    public void askLobbies(){
        clientSocket.send(new AskLobbyMessage(nickname, serverLobbyID).serialize());
    }

    /**
     * Creates and sends a JoinGameMessage
     *
     * @param serverThreadID chosen serverID for the game to join
     */
    public void joinGame(long serverThreadID){
        clientSocket.send(new JoinGameMessage(this.nickname,serverThreadID).serialize());
    }

    /**
     * Sends preChoice Message with the chosen resources and leaders
     *
     * @param resources amount of chosen resources
     * @param leaders leaderCards chosen
     */
    public void preGameChoice(ArrayList<Resource> resources, ArrayList<LeaderCard> leaders){
        clientSocket.send(new PreGameResponseMessage(this.nickname, serverLobbyID,resources,leaders).serialize());
    }

    public void playLeader(LeaderCard leaderCard){
        clientSocket.send(new PlayLeaderMessage(nickname, serverLobbyID,leaderCard).serialize());
    }

    public void discardLeader(LeaderCard leaderCard){
        clientSocket.send(new DiscardLeaderMessage(nickname,serverLobbyID,leaderCard).serialize());
    }

    public void endTurn(){
        isMyTurn = false;
        clientSocket.send(new EndTurnMessage(nickname,serverLobbyID).serialize());
    }

    /**
     * Checking if the requirement is covered by the player
     *
     * @param req req affected
     * @return true if playable
     */
    public boolean isRequirementPossible(Requirement req){
        return req.isPlayable(getThisClientDashboard());
    }

    public ArrayList<LeaderCard> getNotPlayedLeaders(){
        ArrayList<LeaderCard> inHandLeaders = new ArrayList<>();
        for (LeaderCard l: getThisClientDashboard().getLeaderCards()) {
            if(!l.isInGame())
                inHandLeaders.add(l);
        }
        return inHandLeaders;
    }

    public boolean leadersInHand(){
        for (LeaderCard l: getThisClientDashboard().getLeaderCards()) {
            if(!l.isInGame())
                return true;
        }
        return false;
    }

    /**
     *
     * @return true if there is a playable leader
     */
    public boolean canPlayLeader(){
        ArrayList<LeaderCard> leaderCards = getThisClientDashboard().getLeaderCards();
        for(LeaderCard l:leaderCards)
            if(!l.isInGame()&&isRequirementPossible(l.getRequirement()))
                return true;
        return false;
    }

    /**
     * Checks if the player has more or equals resources than the cost
     * @param cost passed amount of resources that are needed to pay
     * @return true if the payment is possible somewhere
     */
    public boolean hasEnoughResources(ResourceCount cost){
        return getThisClientDashboard().getTotalResources().hasMoreOrEqualsResources(cost);
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

    public boolean canBuyCardFromShop(){
        PlayerDashboard p = getThisClientDashboard();
        Deck[][] shop = gameStatus.getShop().getGrid();
        for(int i=0;i<3;i++){
            for(int j=0;j<4;j++){
                if(canBuySpecificCard(shop[i][j].getFirst().getId()))
                    return true;
            }
        }
        return false;
    }

    public boolean canBuySpecificCard(int ID){
        PlayerDashboard p = getThisClientDashboard();
        DevelopmentCard c = getShopCardByID(ID);
        ResourceCount cost = c.getCost();
        boolean canPlace = false;
        for(int i=0;i<3;i++)
            if(p.getDevCards()[i].getDeck().size()>0)
                if(p.getDevCards()[i].getFirst().getLevel() == c.getLevel()-1)
                    canPlace = true;
        return p.getTotalResources().hasMoreOrEqualsResources(cost) && canPlace;
    }

    public boolean positionPossible(int position, int level){
        PlayerDashboard p = getThisClientDashboard();
        return p.getDevCards()[position].getFirst().getLevel() == level-1;
    }

    public ArrayList<Integer> getAllShopID(){
        ArrayList<Integer> firstCardID = new ArrayList<>();
        Deck[][] shop = gameStatus.getShop().getGrid();
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 4; j++)
                firstCardID.add(shop[i][j].getFirst().getId());
        return firstCardID;
    }

    public void buyCard(ResourceCount storage, ResourceCount chest, int id,int position){
        Deck[][] shop = gameStatus.getShop().getGrid();
        int row = 0,column = 0;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 4; j++)
                if(shop[i][j].getFirst().getId() == id){
                    row = i;
                    column = j;
                    break;
                }
        clientSocket.send(new CardShopMessage(nickname,serverLobbyID,row,column,position,storage,chest).serialize());
    }

    public boolean canDoProduction(){
        PlayerDashboard p = getThisClientDashboard();
        ResourceCount resource = new ResourceCount(0,0,0,0,0);
        //can do devCardProduction
        for (DeckDashboard d: p.getDevCards()) {
            if(d.getDeck().size()>0) {
                if (p.getTotalResources().hasMoreOrEqualsResources(d.getFirst().getCost()))
                    return true;
            }
        }
        //can do leaderCardProduction
        for (LeaderCard l: p.getLeaderCards()) {
            if(l.getSpecialAbility() instanceof ProductionAbility) {
                l.getSpecialAbility().getResourceType().add(resource, 1);
                if (p.getTotalResources().hasMoreOrEqualsResources(resource))
                    return true;
            }
        }
        //can do at least basicProduction
        return (ResourceCount.resCountToInt(p.getTotalResources()) >=2);
    }

    /**
     *
     * @param ID
     * @return
     */
    public DevelopmentCard getShopCardByID(int ID){
        return gameStatus.getShop().getCardByID(ID);
    }

    public boolean isMainActionDone() {
        return mainActionDone;
    }

    public void setMainActionDone(boolean mainActionDone) {
        this.mainActionDone = mainActionDone;
    }

    /**
     *
     * @return this client dashboard
     */
    public PlayerDashboard getThisClientDashboard(){
        return gameStatus.getClientDashboard(nickname);
    }

    public void yourTurn(){
        isMyTurn = true;
        view.yourTurn();
    }

    public void updateViewWithMessage(String msg){
        view.clearView();
        view.printMsg(msg);
        updateView();
    }

    public void updateViewWithClear(){
        view.clearView();
        updateView();
    }

    public void updateView(){
        if(gameStarted) {
            if (isMyTurn)
                view.yourTurn();
            else
                view.waitingForTurn();
        }
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