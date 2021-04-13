package it.polimi.ingsw.model;

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
    private Production basicProduction;



    private ArrayList<CounterTop> arrayDeposit;

    public PlayerDashboard(Storage storage, ResourceCount chest, DeckDashboard[] devCards, ArrayList<LeaderCard> leaderCards, Production basicProduction, int position, String nickname, int points) {
        super(position, nickname, points);
        this.pathPosition = 0;
        this.storage = storage;
        this.chest = chest;
        this.devCards = devCards;
        this.devCards[0] = new DeckDashboard();
        this.devCards[1] = new DeckDashboard();
        this.devCards[2] = new DeckDashboard();
        this.leaderCards = leaderCards;
        this.basicProduction = basicProduction;
        this.arrayDeposit = new ArrayList<CounterTop>();
    }

    //GETTERS
    public int getPathPosition(){ return pathPosition;}
    public Production getBasicProduction(){ return basicProduction; }
    public DeckDashboard[] getDevCards() { return devCards; }
    public ArrayList<LeaderCard> getLeaderCards() { return new ArrayList<>(leaderCards); } //Return copy of leaderCards
    public Storage getStorage() { return storage; }
    public ResourceCount getChest() { return chest; }
    public ArrayList<CounterTop> getArrayDeposit() { return arrayDeposit; }

    //INITIALIZE A NEW SHELF WHEN A DEPOSITABILITY LEADER IS PLAYED;
    public void initArrayDeposit(Resource res){
        if(arrayDeposit.size()<2)
            arrayDeposit.add(0,new CounterTop(res,0));
    }

    //public void updateStorage(){} DA FARE NEL CONTROLLER LO SWAP

    //ADD THE RESOURCES PASSED IN A RESOURCECOUNT TO THE CHEST;
    public void addToChest(ResourceCount resources){
        chest.addResources(resources.getCoins(),resources.getRocks(),resources.getServants(),resources.getShields(), resources.getFaith());
    }

    //SUBTRACT THE RESOURCES PASSED IN A RESOURCECOUNT TO THE CHEST;
    public void subtractToChest(ResourceCount resources){
        chest.removeResources(resources.getCoins(),resources.getRocks(),resources.getServants(),resources.getShields(), resources.getFaith());
    }

    //ADD A GIVEN DEVCARD TO A GIVEN DEVCARD DECK ON THE PLAYERDASHBOARD;
    public void addDevCards(DevelopmentCard card, int position){ //the controller checks before buying the card if the player can place it, then checks where to put it;
        devCards[position].addCard(card);
    }

    //RETURN TRUE IF AT LEAST ONE LEADER IS IN GAME;
    public boolean leadersInGame(){
        return (leaderCards.get(0).isInGame() == true || leaderCards.get(1).isInGame() == true);
    }

    //METHOD TO DISCARD A LEADER FROM THE HAND TO GAIN A FAITH POINT;
    public void discardLeader(int position){
        leaderCards.remove(position);
    }


    //ADVANCE OF A GIVEN NUMBER OF STEPS ON THE PATH POSITION;
    public void updatePathPosition(int number){
        pathPosition += number;
    }

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

    //METHOD TO TRY TO ADD A SINGLE RESOURCE AT A TIME TO A SPECIALABILITY DEPOSIT;
    public boolean addToDeposit(Resource res){ //Trying to add resource to abilityDeposit if it isn't full
        if(!isFull(res)){
            for (CounterTop c: arrayDeposit) {
                if(c.getResourceType().equals(res)&&c.getContent()<2) {
                    c.addContent(1);
                    return true; //True if added correctly
                }
            }
        }
        return false; //False if couldn't add
    }

    //RETURN IN A RESOURCECOUNT THE RESOURCES STORED ON THE LEADERCARDS; (not the ones in the specialability deposits);
    public ResourceCount getAbilityDepositResources(){
        ResourceCount count = new ResourceCount(0,0,0,0,0);
        for (CounterTop c: arrayDeposit)
            c.getResourceType().add(count,c.getContent());
        return count;
    }

    //RETURN ALL THE RESOURCES IN THE CHEST AND IN THE STORAGE;
    public ResourceCount getTotalResources(){
        ResourceCount count = storage.readStorage(); //initialize count to the content of storage
        count.addResources(chest.getCoins(), chest.getRocks(), chest.getServants(), chest.getShields(),chest.getFaith()); //add chest resources to count
        return count;
    }
}
