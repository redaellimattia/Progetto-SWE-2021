package it.polimi.ingsw.network.client;

import it.polimi.ingsw.controller.action.marketAction.AtomicMarketAction;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.card.ProductionAbility;
import it.polimi.ingsw.model.card.Requirement;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.network.messages.clientMessages.*;
import it.polimi.ingsw.network.messages.clientMessages.actionMessages.*;
import it.polimi.ingsw.network.messages.serverMessages.ServerMessage;
import it.polimi.ingsw.view.cli.Cli;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.gui.GuiManager;

import java.util.*;

public class ClientManager {
    private String nickname;
    private ClientSocket clientSocket;
    private long serverLobbyID = -1;
    private final View view;
    private ClientGameStatus gameStatus;
    private boolean mainActionDone;
    private boolean isMyTurn;
    private boolean gameStarted;
    private boolean productionActionOnGoing;
    private boolean basicProductionDone;
    private int lastProduction;
    private int lastIndex;
    private boolean[] leaderCardProductionDone;
    private boolean[] devCardProductionDone;

    /**
     * Creates client Object, handles client connection and instantiates view
     * @param address address chosen
     * @param socketPort port chosen
     */
    public ClientManager(String address, int socketPort,String choice) {
        this.nickname = "defaultNickname";
        if(choice.equals("-cli"))
            this.view = new Cli(this);
        else
            this.view = new GuiManager(this);
        view.start();
        connection(address,socketPort);
        this.gameStarted = false;
        this.leaderCardProductionDone = new boolean[2];
        this.devCardProductionDone = new boolean[3];
    }

    //GETTERS
    public ClientGameStatus getGameStatus() { return gameStatus;}
    public String getNickname() {
        return nickname;
    }
    public View getView(){ return view;}
    public ClientSocket getClientSocket(){return clientSocket;}
    public boolean isProductionActionOnGoing() {
        return productionActionOnGoing;
    }
    public int getLastProduction() {
        return lastProduction;
    }
    /**
     *
     * @return true if the mainAction has been already done
     */
    public boolean isMainActionDone() {
        return mainActionDone;
    }


    //SETTERS
    public void setMainActionDone(boolean mainActionDone) {
        this.mainActionDone = mainActionDone;
    }
    public void setProductionActionOnGoing(boolean productionActionOnGoing) {
        this.productionActionOnGoing = productionActionOnGoing;
    }
    public void setBasicProductionDone(boolean basicProductionDone) {
        this.basicProductionDone = basicProductionDone;
    }
    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public void setServerLobbyID(long serverLobbyID) {
        this.serverLobbyID = serverLobbyID;
    }
    public void removeIndexLeaderCardProductionDone() {
        this.leaderCardProductionDone[lastIndex] = false;
    }
    public void removeIndexDevCardProductionDone() {
        this.devCardProductionDone[lastIndex] = false;
    }

    private void initProductionDone(){
        basicProductionDone = false;
        PlayerDashboard p = getThisClientDashboard();
        for(int i=0;i<3;i++)
            devCardProductionDone[i] = p.getDevCards()[i].getDeck().size() <= 0;

        ArrayList<LeaderCard> leaderCards = p.getLeaderCards();
        for(int i=0;i<leaderCards.size();i++)
            leaderCardProductionDone[i] = !leaderCards.get(i).isInGame();
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

    /**
     * Sends a PlayLeaderMessage to the server
     *
     * @param leaderCard Leader Card that the user wants to play
     */
    public void playLeader(LeaderCard leaderCard){
        ArrayList<LeaderCard> leaderCards = getThisClientDashboard().getLeaderCards();
        for(int i=0;i<leaderCards.size();i++)
            if(leaderCards.get(i).equals(leaderCard))
            leaderCardProductionDone[i] = false;
        clientSocket.send(new PlayLeaderMessage(nickname, serverLobbyID,leaderCard).serialize());
    }

    /**
     * Sends a DiscardLeaderMessage to the server
     * @param leaderCard Leader Card that the user wants to discard
     */
    public void discardLeader(LeaderCard leaderCard){
        clientSocket.send(new DiscardLeaderMessage(nickname,serverLobbyID,leaderCard).serialize());
    }

    /**
     * Ends Action, sending a EndActionMessage to the server
     */
    public void endAction(){
        if(isProductionActionOnGoing())
            productionActionOnGoing = false;
        clientSocket.send(new EndActionMessage(nickname,serverLobbyID).serialize());
    }

    /**
     * Basic production on the dashboard
     * Sends a BasicProductionMessage to the server
     *
     * @param storagePayment amount of resources from the storage that the user wants to use in order to pay
     * @param chestPayment amount of resources from the chest that the user wants to use in order to pay
     * @param outputResource Resource chosen by the user as output
     */
    public void basicProduction(ResourceCount storagePayment,ResourceCount chestPayment, Resource outputResource){
        basicProductionDone = true;
        lastProduction = 1;
        clientSocket.send(new BasicProductionMessage(nickname,serverLobbyID,outputResource,storagePayment,chestPayment).serialize());
    }

    /**
     * Production in a Leader Card
     * Sends a LeaderProductionMessage to the server
     *
     * @param card Leader Card chosen
     * @param storageCount amount of resources from the storage that the user wants to use in order to pay
     * @param chestCount amount of resources from the chest that the user wants to use in order to pay
     * @param res Resource chosen by the user as output
     */
    public void leaderProduction(int index,LeaderCard card,ResourceCount storageCount, ResourceCount chestCount, Resource res){
        leaderCardProductionDone[index] = true;
        lastProduction = 2;
        lastIndex = index;
        clientSocket.send(new LeaderProductionMessage(nickname,serverLobbyID,card,storageCount,chestCount,res).serialize());
    }

    /**
     * Production in a Development Card
     * Sends a DevCardProductionMessage to the server
     *
     * @param card Development Card chosen
     * @param storageCount amount of resources from the storage that the user wants to use in order to pay
     * @param chestCount amount of resources from the chest that the user wants to use in order to pay
     */
    public void devCardProduction(int index,DevelopmentCard card,ResourceCount storageCount, ResourceCount chestCount){
        devCardProductionDone[index] = true;
        lastProduction = 3;
        lastIndex = index;
        clientSocket.send(new DevCardProductionMessage(nickname,serverLobbyID,card,storageCount,chestCount).serialize());
    }

    /**
     * Sets isMyTurn to false, then sens a EndTurnMessage to the server
     */
    public void endTurn(){
        isMyTurn = false;
        clientSocket.send(new EndTurnMessage(nickname,serverLobbyID).serialize());
        view.waitingForTurn();
    }

    public void pingResponse(){
        clientSocket.send(new PingResponseMessage(nickname,serverLobbyID).serialize());
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

    /**
     * Get List of leader cards in hand
     *
     * @return an ArrayList full of notPlayed leaders
     */
    public ArrayList<LeaderCard> getNotPlayedLeaders(){
        ArrayList<LeaderCard> inHandLeaders = new ArrayList<>();
        for (LeaderCard l: getThisClientDashboard().getLeaderCards()) {
            if(!l.isInGame())
                inHandLeaders.add(l);
        }
        return inHandLeaders;
    }

    /**
     *
     * @return true if the player has some leader cards that are not in game yet
     */
    public boolean leadersInHand(){
        for (LeaderCard l: getThisClientDashboard().getLeaderCards()) {
            if(!l.isInGame())
                return true;
        }
        return false;
    }

    /**
     * Get a list with only leader cards that have a production ability
     *
     * @return an ArrayList full of leader cards with production ability
     */
    public ArrayList<LeaderCard> getProductionLeaders(){
        PlayerDashboard thisPlayer = getThisClientDashboard();
        ArrayList<LeaderCard> productionLeaders = new ArrayList<>();
        for(LeaderCard l:thisPlayer.getLeaderCards())
            if(l.getSpecialAbility() instanceof ProductionAbility && l.isInGame())
                productionLeaders.add(l);
        return productionLeaders;
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
     * Updates player devCards
     *
     * @param nickname nickname of the player
     * @param devCards updated devCards
     */
    public void updateDevCards(String nickname,DeckDashboard[] devCards){
        int contDeck = 0;
       gameStatus.updateDevCards(nickname,devCards);
    }

    /**
     *
     * @param resource Resource chosen by the player
     * @return true if the move from storage to leader's deposit ability is possible
     */
    public boolean canMoveToLeader(Resource resource){
        PlayerDashboard p = getThisClientDashboard();
        Storage storage = p.getStorage();
        for (CounterTop c : storage.getShelvesArray()) {
            if(c.getResourceType().equals(resource) && !p.isFull(resource))
                return true;
        }
        return false;
    }

    /**
     *
     * @param resource Resource chosen by the player
     * @return true if the move from leader's deposit ability to storage is possible
     */
    public boolean canMoveFromLeader(Resource resource){
        PlayerDashboard p = getThisClientDashboard();
        Storage storage = p.getStorage();
        if(storage.getFirstRow().getContent() == 0)
            return true;
        if(storage.getSecondRow().getContent() == 0 || (storage.getSecondRow().getContent() == 1 && storage.getSecondRow().getResourceType().equals(resource)))
            return true;
        return storage.getThirdRow().getContent() == 0 || (storage.getThirdRow().getContent() <= 2 && storage.getThirdRow().getResourceType().equals(resource));
    }

    /**
     * Check if it's is possible to swap two selected countertops
     * @param from the starting position
     * @param to the arriving position
     * @return true if it's doable
     */
    public boolean swapOk(int from, int to){
        PlayerDashboard p = getThisClientDashboard();
        ArrayList<CounterTop> supportShelves = p.getStorage().getShelvesArray();
        return supportShelves.get(to - 1).getContent() <= from;
    }

    /**
     * Send an OrganizeStorageMessage to the server
     * @param from start of the swap
     * @param to arrive of the swap
     */
    public void organizeStorage(int from, int to){
        clientSocket.send(new OrganizeStorageMessage(nickname, serverLobbyID,from,to).serialize());
    }

    /**
     * Check if the player has resources to enable an organize action
     * @return true if it's doable
     */
    public boolean canMoveResources(){
        PlayerDashboard p = getThisClientDashboard();
        ResourceCount total = p.getTotalResources();
        total.sumCounts(p.getAbilityDepositResources());
        return ResourceCount.resCountToInt(total) > 0;
    }

    /**
     * Send a MoveFromLeaderToDeposit/MoveFromDepositToLeader - Message depending on the choice done on the cli
     * @param resource type of resource to move to find the indexes of the countertop and special ability deposit
     * @param num number of resources to move
     * @param fromLeader true if MoveFromLeaderToDeposit, false if MoveFromDepositToLeader
     */
    public void moveLeaderResources(Resource resource, int num, boolean fromLeader){
        PlayerDashboard p = getThisClientDashboard();
        ArrayList<CounterTop> shelves = p.getStorage().getShelvesArray();
        int from= 0,to = 0;
        for(int i=0; i<shelves.size();i++) {
            if (shelves.get(i).getResourceType().equals(resource)) {
                to = i;
                break;
            }
        }
        for (int i=0;i<p.getArrayDeposit().size();i++){
            if(p.getArrayDeposit().get(i).getResourceType().equals(resource)) {
                from = i;
                break;
            }
        }
        if(fromLeader)
            clientSocket.send(new MoveFromLeaderToDepositMessage(nickname,serverLobbyID,from,to,num).serialize());
        else
            clientSocket.send(new MoveFromDepositToLeaderMessage(nickname,serverLobbyID,to,from,num).serialize());
    }

    /**
     * Check if the player has the possibility to buy at least one card from the shop
     * @return true if it's doable
     */
    public boolean canBuyCardFromShop(){
        Deck[][] shop = gameStatus.getShop().getGrid();
        for(int i=0;i<3;i++){
            for(int j=0;j<4;j++){
                if(shop[i][j].getDeck().size()>0)
                    if(canBuySpecificCard(shop[i][j].getFirst().getId()))
                        return true;
            }
        }
        return false;
    }

    /**
     * Check if the player can buy a specific Development Card
     * @param ID id of the specific Development Card
     * @return true if it's doable
     */
    public boolean canBuySpecificCard(int ID){
        PlayerDashboard p = getThisClientDashboard();
        DevelopmentCard c = getShopCardByID(ID);
        ResourceCount cost = c.getCost();
        boolean canPlace = false;
        for(int i=0;i<3 && !canPlace;i++) {
            if (p.getDevCards()[i].getDeck().size() == 0 && c.getLevel() == 1)
                canPlace = true;
            else {
                if(p.getDevCards()[i].getDeck().size()>0)
                    if (p.getDevCards()[i].getFirst().getLevel() == c.getLevel() - 1)
                        canPlace = true;
            }
        }
        return p.getTotalResources().hasMoreOrEqualsResources(cost) && canPlace;
    }

    /**
     * Check if the player can place a Development Card in a chosen position
     * @param position chosen position
     * @param level card's level
     * @return true if it's doable
     */
    public boolean positionPossible(int position, int level){
        PlayerDashboard p = getThisClientDashboard();
        if(position == -1)
            return false;
        if(p.getDevCards()[position].getDeck().size() == 0 && level == 1)
            return true;
        else
            return p.getDevCards()[position].getFirst().getLevel() == level-1;
    }

    /**
     * Get all the IDs of the first cards on the shop's decks
     * @return an arraylist containing all of the IDs
     */
    public ArrayList<Integer> getAllShopID(){
        ArrayList<Integer> firstCardID = new ArrayList<>();
        Deck[][] shop = gameStatus.getShop().getGrid();
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 4; j++)
                if(shop[i][j].getDeck().size() >0)
                    firstCardID.add(shop[i][j].getFirst().getId());
        return firstCardID;
    }

    /**
     * Send a CardShopMessage with the chosen parameters
     * @param storage resources taken from the storage to pay
     * @param chest resources taken from the chest to pay
     * @param id of the chosen card
     * @param position chosen to place the card on the dashboard
     */
    public void buyCard(ResourceCount storage, ResourceCount chest, int id,int position){
        Deck[][] shop = gameStatus.getShop().getGrid();
        int row = 0,column = 0;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 4; j++)
                if(shop[i][j].getDeck().size() > 0 && shop[i][j].getFirst().getId() == id){
                    row = i;
                    column = j;
                    break;
                }
        clientSocket.send(new CardShopMessage(nickname,serverLobbyID,row,column,position,storage,chest).serialize());
    }

    /**
     *
     * @param p this player
     * @return true if a devCard production is available
     */
    public boolean canDoDevCardProduction(PlayerDashboard p){
        for (int i=0;i<3;i++) {
            DeckDashboard d = p.getDevCards()[i];
            if (d.getDeck().size() > 0)
                if (p.getTotalResources().hasMoreOrEqualsResources(d.getFirst().getProductionPower().getInput()) && !devCardProductionDone[i])
                    return true;
        }
        return false;
    }

    /**
     *
     * @param p this player
     * @return true if a leaderCard production is available
     */
    public boolean canDoLeaderCardProduction(PlayerDashboard p){
        ResourceCount resource = new ResourceCount(0,0,0,0,0);
        ArrayList<LeaderCard> productionLeaders = getProductionLeaders();
        for(int i=0;i<productionLeaders.size();i++){
            LeaderCard l = productionLeaders.get(i);
            l.getSpecialAbility().getResourceType().add(resource, 1);
            if (p.getTotalResources().hasMoreOrEqualsResources(resource)&&!leaderCardProductionDone[i])
                return true;
        }
        return false;
    }

    /**
     *
     * @param p this player
     * @return true if the basic production is available
     */
    public boolean canDoBasicProduction(PlayerDashboard p){
        if(basicProductionDone)
            return false;
        return (ResourceCount.resCountToInt(p.getTotalResources()) >=2);
    }

    /**
     *
     * @return true if atleast 1 production is available.
     */
    public boolean canDoProduction(){
        PlayerDashboard p = getThisClientDashboard();
        return (canDoDevCardProduction(p) || canDoLeaderCardProduction(p) || canDoBasicProduction(p));
    }

    /*/**
     * Creates a copy of the user storage at the beginning of the MarketAction
     * that will be updated when user makes choices in order to perform client-side validity controls.
     * It won't be sent to the server (the server will re-execute choices)
     */
    /*
    public void initTempStorage() {
        ArrayList<CounterTop> tempStorageRows = new ArrayList<CounterTop>();
        // Create a COPY of each "regular" counterTop
        for(CounterTop c: getThisClientDashboard().getStorage().getShelvesArray()) {
            tempStorageRows.add(new CounterTop(c.getResourceType(), c.getContent()));
        }
        // Create a COPY of each additional counterTop
        for(CounterTop c: getThisClientDashboard().getArrayDeposit()) {
            tempArrayDeposit.add(new CounterTop(c.getResourceType(), c.getContent()));
        }
        tempStorage = new Storage(tempStorageRows.get(0), tempStorageRows.get(1), tempStorageRows.get(2));
    }*/

    public MarketMarble[] getMarketMarbles(int type, int pos) {
        if(type == 0) {
            return gameStatus.getMarket().getRow(pos);
        }
        else {
            return gameStatus.getMarket().getColumn(pos);
        }
    }

    /*
    public boolean checkAddToStorage(int row, Resource res) {
        switch(row) {
            case 1:
                if(getThisClientDashboard().getStorage().getFirstRow().getContent() == 0) {
                    if(!canCreateNewRow(res)){
                        return false; // User cannot have two counterTops with the same Resource type
                        //throw new WrongCounterTopException(res);
                    }
                    return true;
                }
                if(getThisClientDashboard().getStorage().getFirstRow().getResourceType() != res) {
                    return false; // User cannot add a resource of a different type
                    //throw new WrongCounterTopException(res);
                }
                if(getThisClientDashboard().getStorage().getFirstRow().getContent() > 0) {
                    return false; // User cannot add a resource into a full counterTop
                    //throw new CounterTopOverloadException();
                }
                return true;
            case 2:
                if(getThisClientDashboard().getStorage().getSecondRow().getContent() == 0) {
                    if(!canCreateNewRow(res)){
                        return false; // User cannot have two counterTops with the same Resource type
                        //throw new WrongCounterTopException(res);
                    }
                    return true;
                }
                if(getThisClientDashboard().getStorage().getSecondRow().getResourceType() != res) {
                    return false;  // User cannot add a resource of a different type
                    //throw new WrongCounterTopException(res);
                }
                if(getThisClientDashboard().getStorage().getSecondRow().getContent() > 1) {
                    return false; // User cannot add a resource into a full counterTop
                    //throw new CounterTopOverloadException();
                }
                return true;
            case 3:
                if(getThisClientDashboard().getStorage().getThirdRow().getContent() == 0) {
                    if(!canCreateNewRow(res)){
                        return false;  // User cannot have two counterTops with the same Resource type
                        //throw new WrongCounterTopException(res);
                    }
                    return true;
                }
                if(getThisClientDashboard().getStorage().getThirdRow().getResourceType() != res) {
                    return false;  // User cannot add a resource of a different type
                    //throw new WrongCounterTopException(res);
                }
                if(getThisClientDashboard().getStorage().getThirdRow().getContent() > 2) {
                    return false; // User cannot add a resource into a full counterTop
                    //throw new CounterTopOverloadException();
                }
                return true;
            case 4:
                if(getThisClientDashboard().isFull(res)) {
                    return false;
                    //throw new NoAdditionalDepositException(res);
                }
                else {
                    return true;
                }
            default:
                return false;
                //throw new IllegalArgumentException();
        }
    } */

    /**
     * Check if the user already has a non-empty counterTop for this resource type
     * @param res the resource type
     * @return true if there is NOT another counterTop of the same type
     */
    public boolean canCreateNewRow(Resource res) {
        for(CounterTop c: getThisClientDashboard().getStorage().getShelvesArray()) {
            if(c.getContent() != 0 && c.getResourceType().equals(res)) {
                return false;
            }
        }
        return true;
    }

    public boolean hasWhiteChangeAbility() {
        for (LeaderCard c: getThisClientDashboard().getLeaderCards())
            if(c.getSpecialAbility().useWhiteChangeAbility() != null && c.isInGame())
                return true;
        return false;
    }

    /**
     *
     * @param res the resource type of the deposit
     * @return true if the user has an additional deposit of that type
     */
    public boolean hasAdditionalDeposit(Resource res) {
        for (CounterTop c: getThisClientDashboard().getArrayDeposit()) {
            if(c.getResourceType().equals(res)) {
                return true;
            }
        }
        return false;
    }

    public LeaderCard getWhiteChangeCard(int card) {
        int count;
        count = 0;
        for (LeaderCard c: getThisClientDashboard().getLeaderCards()) {
            if(c.getSpecialAbility().useWhiteChangeAbility() != null && c.isInGame()) {
                count++;
                if(card == count) {
                    return c;
                }
            }
        }
        return null;
    }

    public void takeResourcesFromMarket(int type, int pos, ArrayList<AtomicMarketAction> choices) {
        // System.out.println(new MarketActionMessage(nickname, serverLobbyID, type, pos, choices).serialize());
        // clientSocket.send(new MarketActionMessage(nickname, serverLobbyID, type, pos, choices).serialize());
    }

    public void getResource(int row) {
        // System.out.println(new GetResourceMessage(nickname, serverLobbyID, row).serialize());
        clientSocket.send(new GetResourceMessage(nickname, serverLobbyID, row).serialize());
    }

    public void discardResource() {
        // System.out.println(new DiscardResourceMessage(nickname, serverLobbyID).serialize());
        clientSocket.send(new DiscardResourceMessage(nickname, serverLobbyID).serialize());
    }

    public void convertMarble(LeaderCard leaderCard, int row) {
        int cardId = leaderCard.getId();
        // System.out.println(new ConvertMarbleMessage(nickname, serverLobbyID, cardId, row).serialize());
        clientSocket.send(new ConvertMarbleMessage(nickname, serverLobbyID, cardId, row).serialize());
    }

    public void endMarketAction(int type, int pos) {
        setMainActionDone(true);
        clientSocket.send(new EndMarketActionMessage(nickname, serverLobbyID, type, pos).serialize());
    }

    /**
     *
     * @param ID ID of the card
     * @return the Development card associated to the passed ID
     */
    public DevelopmentCard getShopCardByID(int ID){
        return gameStatus.getShop().getCardByID(ID);
    }

    /**
     *
     * @return this client dashboard
     */
    public PlayerDashboard getThisClientDashboard(){
        return gameStatus.getClientDashboard(nickname);
    }

    /**
     * When a YourTurnMessage is received, set boolean values, then update the view
     */
    public void yourTurn(){
        isMyTurn = true;
        mainActionDone = false;
        initProductionDone();
        view.yourTurn();
    }

    /**
     * Updates the view
     */
    public void updateViewWithClear(){
        view.clearView();
        updateView();
    }

    /**
     * Updates the view
     */
    public void updateView(){
        if(gameStarted) {
            if (isMyTurn)
                view.yourTurn();
            else
                view.waitingForTurn();
        }
    }
}