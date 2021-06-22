package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.CounterTopOverloadException;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.network.server.Observer;

import java.util.ArrayList;

public class PlayerDashboard extends Player implements StorageObserver{
    private int pathPosition;
    private final Storage storage;
    private ResourceCount chest;
    private DeckDashboard[] devCards;
    private ArrayList<LeaderCard> leaderCards;
    private ArrayList<CounterTop> arrayDeposit;
    private ResourceCount bufferProduction;
    private transient Observer observer;

    /**
     * Adds reference to the observer
     * @param observer ServerLobby that is observing the Player
     */
    public void addObserver(Observer observer) {
        this.observer = observer;
    }

    /**
     *
     * @param storage passed Storage
     * @param chest ResourceCount that simulate the player's chest
     * @param devCards Array of DevelopmentCard bought by the player
     * @param leaderCards ArrayList of LeaderCard that are the leader owned by the player
     * @param nickname nickname of the player
     * @param points total of victory Points
     */
    public PlayerDashboard(Storage storage, ResourceCount chest, DeckDashboard[] devCards, ArrayList<LeaderCard> leaderCards, String nickname, int points, boolean isLorenzo) {
        super(nickname, points,isLorenzo);
        this.pathPosition = 0;
        this.storage = storage;
        this.storage.addObserver(this);
        this.chest = chest;
        this.devCards = devCards;
        this.devCards[0] = new DeckDashboard();
        this.devCards[1] = new DeckDashboard();
        this.devCards[2] = new DeckDashboard();
        this.leaderCards = leaderCards;
        this.arrayDeposit = new ArrayList<>();
        bufferProduction = new ResourceCount(0,0,0,0,0);
        storage.setPlayer(this);
    }

    /**
     * Call to the observer responsible for an update on the leaders deposit
     */
    public void updateArrayDeposits(){
        this.observer.updateArrayDeposit(getNickname(),arrayDeposit);
    }
    //GETTERS

    /**
     *
     * @return position on the Faith Path
     */
    public int getPathPosition(){ return pathPosition;}

    /**
     *
     * @return array of the Development Cards
     */
    public DeckDashboard[] getDevCards() { return devCards; }

    /**
     *
     * @return ArrayList of the Leader Cards
     */
    public ArrayList<LeaderCard> getLeaderCards() { return leaderCards; }

    /**
     *
     * @return player.storage
     */
    public Storage getStorage() { return storage; }

    /**
     *
     * @return player.chest
     */
    public ResourceCount getChest() { return chest; }

    /**
     *
     * @return the SpecialAbility Deposit that might be owned by the player
     */
    public ArrayList<CounterTop> getArrayDeposit() { return arrayDeposit; }

    /**
     *
     * @return total of resources produced during productionAction
     */
    public ResourceCount getBufferProduction() {
        return bufferProduction;
    }

    /**
     * Sets new PathPosition
     * @param pathPosition updated PathPosition
     */
    public void setPathPosition(int pathPosition) {
        this.pathPosition = pathPosition;
    }

    /**
     * Sets new ArrayDeposit
     * @param arrayDeposit updated ArrayDeposit
     */
    public void setArrayDeposit(ArrayList<CounterTop> arrayDeposit) {
        this.arrayDeposit = arrayDeposit;
    }

    /**
     * Sets new Chest
     * @param chest updated Chest
     */
    public void setChest(ResourceCount chest) {
        this.chest = chest;
    }

    public void setDevCards(DeckDashboard[] devCards) {
        this.devCards = devCards;
    }

    /**
     * Sets new BufferProduction
     * @param bufferProduction updated bufferProduction
     */
    public void setBufferProduction(ResourceCount bufferProduction) {
        this.bufferProduction = bufferProduction;
    }

    public void setLeaderCards(ArrayList<LeaderCard> leaderCards) {
        this.leaderCards = leaderCards;
        if(observer!=null)
            observer.updateLeaders(getNickname(),leaderCards);
    }

    /**
     * Set the Main Action Error message in player and calls the updates of the observer
     * @param error message
     */
    public void setMainActionError(String error){
        super.setMainActionError(error);
        observer.updateMainActionException(getNickname(),getMainActionError());
    }

    /**
     * Set the Side Action Error message in player and calls the updates of the observer
     * @param error message
     */
    public void setSideActionError(String error){
        super.setSideActionError(error);
        observer.updateSideActionException(getNickname(),getSideActionError());
    }

    /**
     * Initialize a new shelf when a depositability leader is played
     *
     * @param res Resource of a particular DepositAbility
     */
    public void initArrayDeposit(Resource res){
        if(arrayDeposit.size()<2) {
            arrayDeposit.add(0, new CounterTop(res, 0));
            if(observer!=null)
                observer.updateInitArrayDeposit(getNickname(),res);
        }

    }

    /**
     * Add a given devcard to a given devcard deck on the playerdashboard
     *
     * @param card DevelopmentCard that has been bought
     * @param position specific deck on which the player has chosen to add the bought card
     */
    public void addDevCards(DevelopmentCard card, int position){ //the controller checks before buying the card if the player can place it, then checks where to put it;
        devCards[position].addCard(card);
        addPoints(card.getVictoryPoints());
        observer.updateDevCards(getNickname(),devCards);
    }

    /**
     * Return true if at least one leader is in game
     *
     * @return true if at least one leader is in game
     */
    public boolean leadersInGame(){
        return (leaderCards.get(0).isInGame() || leaderCards.get(1).isInGame());
    }

    /**
     * Method to discard a leader from the hand to gain a faith point
     *
     * @param position position of the leader the player has chosen to discard to gain a faith point
     */
    public void discardLeader(int position){
        leaderCards.remove(position);
        observer.updateRemoveLeader(getNickname(),leaderCards);
        updatePathPosition();
    }

    /**
     * Advance of 1 on the path position
     */
    public void updatePathPosition(){
        if(pathPosition<24)
            pathPosition += 1;
        if(observer!=null)
            observer.updatePathPosition(this,pathPosition);
    }

    /**
     * If a leader with a depositability with a certain resources is in game and is already full return true; in every other case false
     * @param res specific resource searched
     * @return true if there's a full SpecialDeposit for that resource or if there is none
     */
    public boolean isFull(Resource res){ //True if abilityDeposit with that res is full (Content=2) || there's no deposit for that resource
        boolean notFound = false;

        if(arrayDeposit.size() == 0)
            return true;

        for (CounterTop c: arrayDeposit) {
            if(c.getResourceType().equals(res) && c.getContent()<2)
                    return false;
            else
                if(c.getResourceType().equals(res) && c.getContent() == 2)
                    return true;
                else
                    if(!c.getResourceType().equals(res))
                        notFound = true;
        }
        return notFound;
    }

    /**
     * Method to try to add a single resource at a time to a specialability deposit
     *
     * @param res specific resource searched
     */
    public void addToDeposit(Resource res) throws CounterTopOverloadException { //Trying to add resource to abilityDeposit if it isn't full
        if(!isFull(res)) {
            for (CounterTop c : arrayDeposit)
                if (c.getResourceType().equals(res) && c.getContent() < 2)
                    c.addContent(1);
        }
        else
            throw new CounterTopOverloadException(this);
        observer.updateArrayDeposit(getNickname(),arrayDeposit);
    }

    /**
     * Return in a resourcecount the resources stored on the leadercards
     *
     * @return total resources stored on SpecialAbility deposit
     */
    public ResourceCount getAbilityDepositResources(){
        ResourceCount count = new ResourceCount(0,0,0,0,0);
        for (CounterTop c: arrayDeposit)
            c.getResourceType().add(count,c.getContent());
        return count;
    }

    /**
     *
     * @return total of resources from storage and chest
     */
    public ResourceCount getTotalResources(){
        ResourceCount count = storage.readStorage(); //initialize count to the content of storage
        count.addResources(chest.getCoins(), chest.getRocks(), chest.getServants(), chest.getShields(),chest.getFaith()); //add chest resources to count
        return count;
    }

    /**
     *
     * @param card LeaderCard that needs to be searched in player.getLeaderCards()
     * @return true if it's owned, false if not
     */
    public boolean leaderCardExists(LeaderCard card){
        for(LeaderCard l:this.getLeaderCards())
            if (l.equals(card))
                return true;
        return false;
    }

    /**
     *
     * @param card DevelopmentCard that needs to be searched in player.getDevCards() (and its visible)
     * @return true if it's owned, false if not
     */
    public boolean devCardExists(DevelopmentCard card){
        for(int i=0;i<this.getDevCards().length;i++)
            if(this.getDevCards()[i].getDeck().size()!=0) {
                DevelopmentCard d = this.getDevCards()[i].getFirst();
                if (d.equals(card))
                    return true;
            }
        return false;
    }

    /**
     *
     * @param card LeaderCard that needs to be searched in player.getLeaderCards()
     * @return the position if it's found, -1 if not
     */
    public int getLeaderPos(LeaderCard card){
        for(int i=0;i<this.getLeaderCards().size();i++) {
            LeaderCard l = this.getLeaderCards().get(i);
            if(l.equals(card))
                return i;
        }
        return -1;
    }


    /**
     * Set the leader at the given position in game (leaderaction)
     * @param position specific position of the leader the player wants to play
     */
    public void setLeaderInGame(int position){
        LeaderCard leaderCard = this.getLeaderCards().get(position);
        leaderCard.setInGame();
        addVictoryPoints(leaderCard.getVictoryPoints());
        observer.updateInGameLeader(getNickname(),leaderCards);
    }

    /**
     *  At the end of a ProductionAction, the produced resources are given to the player in his chest
     */
    public void emptyBufferProduction(){
        int faith = bufferProduction.getFaith();
        bufferProduction.removeFaith(faith);
        while(faith!=0) {
            updatePathPosition();
            faith--;
        }
        chest.sumCounts(bufferProduction);
        bufferProduction = new ResourceCount(0,0,0,0,0);
        observer.updateChest(getNickname(),chest);
        observer.updateBufferProduction(getNickname(),bufferProduction);
    }

    /**
     *
     * @return true if this player has 7 Development Cards
     */
    public boolean hasSevenDevCards(){
        return devCards[0].getDeck().size() + devCards[1].getDeck().size() + devCards[2].getDeck().size() == 7;
    }

    /**
     *
     * @param count add a specific ResourceCount to bufferProduction
     */
    public void incrementBufferProduction(ResourceCount count){
        bufferProduction.sumCounts(count);
        observer.updateBufferProduction(getNickname(),bufferProduction);
    }

    public void addVictoryPoints(int points){
        addPoints(points);
        observer.updateVictoryPoints(getNickname(),getPoints());
    }
    public void subtractFromChest(ResourceCount toSubtract){
        chest.subCounts(toSubtract);
        observer.updateChest(getNickname(),chest);
    }
    @Override
    public void updateFirstRow(CounterTop firstRow) {
        observer.updateFirstRow(getNickname(),firstRow);
    }

    @Override
    public void updateSecondRow(CounterTop secondRow) {
        observer.updateSecondRow(getNickname(),secondRow);
    }

    @Override
    public void updateThirdRow(CounterTop thirdRow) {
        observer.updateThirdRow(getNickname(),thirdRow);
    }
}
