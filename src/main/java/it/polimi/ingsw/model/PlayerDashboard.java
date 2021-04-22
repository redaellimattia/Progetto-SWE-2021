package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.exceptions.CounterTopOverloadException;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.enumeration.Resource;

import java.util.ArrayList;

public class PlayerDashboard extends Player{
    private int pathPosition;
    private Storage storage;
    private ResourceCount chest;
    private DeckDashboard[] devCards;
    private ArrayList<LeaderCard> leaderCards;
    private ArrayList<CounterTop> arrayDeposit;
    private ResourceCount bufferProduction;

    /**
     *
     * @param storage passed Storage
     * @param chest ResourceCount that simulate the player's chest
     * @param devCards Array of DevelopmentCard bought by the player
     * @param leaderCards ArrayList of LeaderCard that are the leader owned by the player
     * @param position counter for the position on the Faith Path
     * @param nickname nickname of the player
     * @param points total of victory Points
     */
    public PlayerDashboard(Storage storage, ResourceCount chest, DeckDashboard[] devCards, ArrayList<LeaderCard> leaderCards, int position, String nickname, int points) {
        super(position, nickname, points);
        this.pathPosition = 0;
        this.storage = storage;
        this.chest = chest;
        this.devCards = devCards;
        this.devCards[0] = new DeckDashboard();
        this.devCards[1] = new DeckDashboard();
        this.devCards[2] = new DeckDashboard();
        this.leaderCards = leaderCards;
        this.arrayDeposit = new ArrayList<>();
        bufferProduction = new ResourceCount(0,0,0,0,0);
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
     *
     * @param res Resource of a particular DepositAbility
     */
    //INITIALIZE A NEW SHELF WHEN A DEPOSITABILITY LEADER IS PLAYED;
    public void initArrayDeposit(Resource res){
        if(arrayDeposit.size()<2)
            arrayDeposit.add(0,new CounterTop(res,0));
    }

    /**
     *
     * @param resources ResourceCount that needs to be added to the chest
     */
    //ADD THE RESOURCES PASSED IN A RESOURCECOUNT TO THE CHEST;
    public void addToChest(ResourceCount resources){
        chest.addResources(resources.getCoins(),resources.getRocks(),resources.getServants(),resources.getShields(), resources.getFaith());
    }

    /**
     *
     * @param resources ResourceCount that needs to be removed from the chest
     */
    //SUBTRACT THE RESOURCES PASSED IN A RESOURCECOUNT TO THE CHEST;
    public void subtractToChest(ResourceCount resources){
        chest.removeResources(resources.getCoins(),resources.getRocks(),resources.getServants(),resources.getShields(), resources.getFaith());
    }

    /**
     *
     * @param card DevelopmentCard that has been bought
     * @param position specific deck on which the player has chosen to add the bought card
     */
    //ADD A GIVEN DEVCARD TO A GIVEN DEVCARD DECK ON THE PLAYERDASHBOARD;
    public void addDevCards(DevelopmentCard card, int position){ //the controller checks before buying the card if the player can place it, then checks where to put it;
        devCards[position].addCard(card);
    }

    /**
     *
     * @return true if at least one leader is in game
     */
    //RETURN TRUE IF AT LEAST ONE LEADER IS IN GAME;
    public boolean leadersInGame(){
        return (leaderCards.get(0).isInGame() || leaderCards.get(1).isInGame());
    }

    /**
     *
     * @param position position of the leader the player has chosen to discard to gain a faith point
     */
    //METHOD TO DISCARD A LEADER FROM THE HAND TO GAIN A FAITH POINT;
    public void discardLeader(int position){
        leaderCards.remove(position);
        updatePathPosition();
    }

    /**
     * ADVANCE OF 1 ON THE PATH POSITION
     */
    //
    public void updatePathPosition(){
        pathPosition += 1;
        //GameManager.checkFaithPath();// to check Papal influence
    }

    /**
     *
     * @param res specific resource searched
     * @return true if there's a full SpecialDeposit for that resource or if there is none
     */
    //IF A LEADER WITH A DEPOSITABILITY WITH A CERTAIN RESOURCES IS IN GAME AND IS ALREADY FULL RETURN TRUE; IN EVERY OTHER CASE FALSE;
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
     *
     * @param res specific resource searched
     * @return true if the addition is done correctly
     */
    //METHOD TO TRY TO ADD A SINGLE RESOURCE AT A TIME TO A SPECIALABILITY DEPOSIT;
    public void addToDeposit(Resource res) throws CounterTopOverloadException { //Trying to add resource to abilityDeposit if it isn't full
        if(!isFull(res)){
            for (CounterTop c: arrayDeposit) {
                if(c.getResourceType().equals(res)&&c.getContent()<2) {
                    c.addContent(1);
                }
            }
        }
        else
            throw new CounterTopOverloadException();
    }

    /**
     *
     * @return total resources stored on SpecialAbility deposit
     */
    //RETURN IN A RESOURCECOUNT THE RESOURCES STORED ON THE LEADERCARDS; (not the ones in the specialability deposits);
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
    //RETURN ALL THE RESOURCES IN THE CHEST AND IN THE STORAGE;
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
    //CHECK IF THE GIVEN LEADERCARD IS OWNED BY THE PLAYER
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
    //CHECK IF THE GIVEN DEVELOPMENTCARD IS OWNED BY THE PLAYER
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
    //RETURNS POSITION IF EXISTS, -1 IF NOT FOUND
    public int getLeaderPos(LeaderCard card){
        for(int i=0;i<this.getLeaderCards().size();i++) {
            LeaderCard l = this.getLeaderCards().get(i);
            if(l.equals(card))
                return i;
        }
        return -1;
    }

    /**
     *
     * @param position specific position of the leader the player wants to play
     */
    //SET THE LEADER AT THE GIVEN POSITION IN GAME (LEADERACTION)
    public void setLeaderInGame(int position){
        this.getLeaderCards().get(position).setInGame();
    }

    /**
     *  at the end of a ProductionAction, the produced resources are given to the player in his chest
     */
    //EMPTY BUFFERPRODUCTION WHEN PRODUCTIONACTION ENDS
    public void emptyBufferProduction(){
        int faith = bufferProduction.getFaith();
        bufferProduction.removeFaith(faith);
        while(faith!=0) {
            updatePathPosition();
            faith--;
        }
        chest.sumCounts(bufferProduction);
        bufferProduction = new ResourceCount(0,0,0,0,0);
    }

    /**
     *
     * @param count add a specific ResourceCount to bufferProduction
     */
    public void incrementBufferProduction(ResourceCount count){
        bufferProduction.sumCounts(count);
    }
}
