package it.polimi.ingsw.network.client;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.card.ProductionAbility;
import it.polimi.ingsw.model.card.Requirement;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.network.messages.clientMessages.*;
import it.polimi.ingsw.network.messages.clientMessages.actionMessages.*;
import it.polimi.ingsw.network.messages.serverMessages.ServerMessage;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.view.cli.Cli;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.gui.GuiManager;

import java.util.*;
import java.util.logging.Level;

public class ClientManager {
    private String nickname;
    private ClientSocket clientSocket;
    private long serverLobbyID = -1;
    private final View view;
    private ClientGameStatus gameStatus;
    private boolean mainActionDone;
    private boolean isMyTurn;
    private boolean gameStarted;
    private boolean gameEnded;
    private boolean productionActionOnGoing;
    private boolean basicProductionDone;
    private int lastProduction,lastIndex,numOfProd;
    private final boolean[] leaderCardProductionDone;
    private final boolean[] devCardProductionDone;
    private final String address;
    private final int socketPort;
    private PlayerDashboard thisPlayerDashboard;
    private Timer keepAliveTimer;

    /**
     * Creates client Object, handles client connection and instantiates view
     *
     * @param address address chosen
     * @param socketPort port chosen
     */
    public ClientManager(String address, int socketPort,String choice) {
        this.nickname = "defaultNickname";
        this.address = address;
        this.socketPort = socketPort;
        if(choice.equals("-cli")) {
            this.view = new Cli(this);
            connection(address,socketPort);
        }
        else
            this.view = new GuiManager(this);
        this.gameStarted = false;
        this.leaderCardProductionDone = new boolean[2];
        this.devCardProductionDone = new boolean[3];
        this.gameEnded=false;
        view.start();
    }

    //GETTERS
    public String getAddress() {
        return address;
    }

    public int getSocketPort() {
        return socketPort;
    }

    public boolean isMyTurn() {
        return isMyTurn;
    }
    public ClientGameStatus getGameStatus() { return gameStatus;}
    public String getNickname() {
        return nickname;
    }
    public View getView(){ return view;}
    public boolean isProductionActionOnGoing() {
        return productionActionOnGoing;
    }
    public int getLastProduction() {
        return lastProduction;
    }
    public boolean[] getLeaderCardProductionDone() {
        return leaderCardProductionDone;
    }
    public boolean[] getDevCardProductionDone() {
        return devCardProductionDone;
    }
    public int getNumOfProd() {
        return numOfProd;
    }
    public boolean isGameEnded() {
        return gameEnded;
    }

    public void setGameEnded(boolean gameEnded) {
        this.gameEnded = gameEnded;
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

    /**
     * Reset the production at the start of the turn
     */
    private void initProductionDone(){
        numOfProd = 0;
        basicProductionDone = false;
        for(int i=0;i<3;i++)
            devCardProductionDone[i] = thisPlayerDashboard.getDevCards()[i].getDeck().size() <= 0;

        ArrayList<LeaderCard> leaderCards = thisPlayerDashboard.getLeaderCards();
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
        thisPlayerDashboard = getThisClientDashboard();
        if(view instanceof GuiManager)
            gameStatus.addObserver(GuiManager.getInstance());
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
            view.failedConnection("Failed to connect to: "+ address+":" + socketPort);
        else {
            keepAliveTimer = new Timer();
            keepAliveTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    clientSocket.send(new KeepAliveMessage("", -1).serialize());
                }
            }, 0, 180000); //3min
        }
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
        ArrayList<LeaderCard> leaderCards = thisPlayerDashboard.getLeaderCards();
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

    public void sendMoveFromLeader(int from, int to, int numberOfResources){
        clientSocket.send(new MoveFromLeaderToDepositMessage(nickname,serverLobbyID,from,to,numberOfResources).serialize());

    }

    public void sendMoveToLeader(int from, int to, int numberOfResources){
        clientSocket.send(new MoveFromDepositToLeaderMessage(nickname,serverLobbyID,from,to,numberOfResources).serialize());

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
        numOfProd++;
        mainActionDone = true;
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
        numOfProd++;
        mainActionDone = true;
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
        numOfProd++;
        mainActionDone = true;
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
        return req.isPlayable(thisPlayerDashboard);
    }

    /**
     * Get List of leader cards in hand
     *
     * @return an ArrayList full of notPlayed leaders
     */
    public ArrayList<LeaderCard> getNotPlayedLeaders(){
        ArrayList<LeaderCard> inHandLeaders = new ArrayList<>();
        for (LeaderCard l: thisPlayerDashboard.getLeaderCards()) {
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
        for (LeaderCard l: thisPlayerDashboard.getLeaderCards()) {
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
        PlayerDashboard thisPlayer = thisPlayerDashboard;
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
        ArrayList<LeaderCard> leaderCards = thisPlayerDashboard.getLeaderCards();
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
       gameStatus.updateDevCards(nickname,devCards);
    }

    /**
     *
     * @param resource Resource chosen by the player
     * @return true if the move from storage to leader's deposit ability is possible
     */
    public boolean canMoveToLeader(Resource resource){
        PlayerDashboard p = thisPlayerDashboard;
        Storage storage = p.getStorage();
        for (CounterTop c : storage.getShelvesArray()) {
            if(c.getResourceType().equals(resource) && c.getContent()>0 && !p.isFull(resource))
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
        PlayerDashboard p = thisPlayerDashboard;
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
     * @return true if it's possible
     */
    public boolean swapOk(int from, int to){
        PlayerDashboard p = thisPlayerDashboard;
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
     * @return true if it's possible
     */
    public boolean canMoveResources(){
        PlayerDashboard p = thisPlayerDashboard;
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
        PlayerDashboard p = thisPlayerDashboard;
        ArrayList<CounterTop> shelves = p.getStorage().getShelvesArray();
        int from= 0,to = 0;
        if(fromLeader) {
            for (int i = 0; i < shelves.size(); i++) {
                if (shelves.get(i).getResourceType().equals(resource) || shelves.get(i).getContent()==0) {
                    if(i==0){
                        if((shelves.get(1).getResourceType()!=resource || shelves.get(1).getContent()==0) && (shelves.get(2).getResourceType()!= resource || shelves.get(2).getContent()== 0)){
                            if(num==1) {
                                to = i + 1;
                                break;
                            }
                        }
                    }
                    else{
                        if(i==1){
                            if(num == 1 && shelves.get(i).getContent()<=1) {
                                if (shelves.get(2).getResourceType() != resource || shelves.get(2).getContent()==0 ) {
                                    to = i + 1;
                                    break;
                                }
                            }
                            else
                                if(num==2 && shelves.get(i).getContent()==0){
                                    if (shelves.get(2).getResourceType() != resource || shelves.get(2).getContent()==0 ) {
                                        to = i + 1;
                                        break;
                                    }
                                }
                        }
                        else {
                            to = i + 1;
                            break;
                        }
                    }

                }
            }
            for (int i = 0; i < p.getArrayDeposit().size(); i++) {
                if (p.getArrayDeposit().get(i).getResourceType().equals(resource)) {
                    from = i+1;
                    break;
                }
            }
            sendMoveFromLeader(from,to,num);
        }
        else{
            for (int i = 0; i < shelves.size(); i++) {
                if (shelves.get(i).getResourceType().equals(resource) && shelves.get(i).getContent() >= num) {
                    to = i+1;
                    break;
                }
            }
            for (int i = 0; i < p.getArrayDeposit().size(); i++) {
                if (p.getArrayDeposit().get(i).getResourceType().equals(resource)) {
                    from = i+1;
                    break;
                }
            }
            sendMoveToLeader(to,from,num);
        }


    }

    /**
     * Check if the player has the possibility to buy at least one card from the shop
     * @return true if it's possible
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
    public ResourceCount discountCardCost(ResourceCount cost){
        ResourceCount discountCost = new ResourceCount(cost.getCoins(),cost.getRocks(),cost.getServants(),cost.getShields(),0);
        ArrayList<LeaderCard> leaders = getThisClientDashboard().getLeaderCards();
        if(leaders.get(0).isInGame())
            leaders.get(0).getSpecialAbility().useDiscountAbility(discountCost);
        if(leaders.get(1).isInGame())
            leaders.get(1).getSpecialAbility().useDiscountAbility(discountCost);

        return discountCost;
    }
    /**
     * Check if the player can buy a specific Development Card
     * @param ID id of the specific Development Card
     * @return true if it's possible
     */
    public boolean canBuySpecificCard(int ID){
        DevelopmentCard c = getShopCardByID(ID);
        ResourceCount cost = c.getCost();
        boolean canPlace = false;
        for(int i=0;i<3 && !canPlace;i++) {
            if (thisPlayerDashboard.getDevCards()[i].getDeck().size() == 0 && c.getLevel() == 1)
                canPlace = true;
            else {
                if(thisPlayerDashboard.getDevCards()[i].getDeck().size()>0)
                    if (thisPlayerDashboard.getDevCards()[i].getFirst().getLevel() == c.getLevel() - 1)
                        canPlace = true;
            }
        }
        return thisPlayerDashboard.getTotalResources().hasMoreOrEqualsResources(discountCardCost(cost)) && canPlace;
    }

    /**
     * Check if the player can place a Development Card in a chosen position
     * @param position chosen position
     * @param level card's level
     * @return true if it's possible
     */
    public boolean positionPossible(int position, int level){
        if(position == -1)
            return false;
        if(thisPlayerDashboard.getDevCards()[position].getDeck().size() == 0 && level == 1)
            return true;
        else
            return thisPlayerDashboard.getDevCards()[position].getFirst().getLevel() == level-1;
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
        ResourceCount resource;
        for(int i=0;i<p.getLeaderCards().size();i++){
            resource = new ResourceCount(0,0,0,0,0);
            LeaderCard l = p.getLeaderCards().get(i);
            l.getSpecialAbility().getResourceType().add(resource, 1);
            if (l.isInGame()&&l.getSpecialAbility() instanceof ProductionAbility&&p.getTotalResources().hasMoreOrEqualsResources(resource)&&!leaderCardProductionDone[i])
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
        return (canDoDevCardProduction(thisPlayerDashboard) || canDoLeaderCardProduction(thisPlayerDashboard) || canDoBasicProduction(thisPlayerDashboard));
    }


    public MarketMarble[] getMarketMarbles(int type, int pos) {
        if(type == 0) {
            return gameStatus.getMarket().getRow(pos);
        }
        else {
            return gameStatus.getMarket().getColumn(pos);
        }
    }


    /**
     * Check if the user already has a non-empty counterTop for this resource type
     * @param res the resource type
     * @return true if there is NOT another counterTop of the same type
     */
    public boolean canCreateNewRow(Resource res) {
        for(CounterTop c: thisPlayerDashboard.getStorage().getShelvesArray()) {
            if(c.getContent() != 0 && c.getResourceType().equals(res)) {
                return false;
            }
        }
        return true;
    }

    public boolean hasWhiteChangeAbility() {
        for (LeaderCard c: thisPlayerDashboard.getLeaderCards())
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
        for (CounterTop c: thisPlayerDashboard.getArrayDeposit()) {
            if(c.getResourceType().equals(res)) {
                return true;
            }
        }
        return false;
    }

    public LeaderCard getWhiteChangeCard(int card) {
        int count;
        count = 0;
        for (LeaderCard c: thisPlayerDashboard.getLeaderCards()) {
            if(c.getSpecialAbility().useWhiteChangeAbility() != null && c.isInGame()) {
                count++;
                if(card == count) {
                    return c;
                }
            }
        }
        return null;
    }

    public void getResource(int row) {
        clientSocket.send(new GetResourceMessage(nickname, serverLobbyID, row).serialize());
    }

    public void discardResource() {
        clientSocket.send(new DiscardResourceMessage(nickname, serverLobbyID).serialize());
    }

    public void convertMarble(LeaderCard leaderCard, int row) {
        int cardId = leaderCard.getId();
        clientSocket.send(new ConvertMarbleMessage(nickname, serverLobbyID, cardId, row).serialize());
    }

    public void endMarketAction(int type, int pos) {
        mainActionDone = true;
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