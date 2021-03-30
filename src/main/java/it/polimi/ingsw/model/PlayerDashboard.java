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

    public PlayerDashboard(Storage storage, ResourceCount chest, DeckDashboard[] devCards, ArrayList<LeaderCard> leaderCards, Production basicProduction) {
        this.pathPosition = 0;
        this.storage = storage;
        this.chest = chest;
        this.devCards = devCards;
        this.leaderCards = leaderCards;
        this.basicProduction = basicProduction;
        this.arrayDeposit = new ArrayList<>();
    }

    public void initArrayDeposit(Resource res){
        if(arrayDeposit.size()<2)
            arrayDeposit.add(new CounterTop(res,0));
    }

    //public void updateStorage(){} DA FARE NEL CONTROLLER LO SWAP

    public void addToChest(ResourceCount resources){
        chest.addResources(resources.getCoins(),resources.getRocks(),resources.getServants(),resources.getShields());
    }

    public void subtractToChest(ResourceCount resources){
        chest.removeResources(resources.getCoins(),resources.getRocks(),resources.getServants(),resources.getShields());
    }

    public void addDevCards(DevelopmentCard card, int position){ //the controller checks before buying the card if the player can place it, then checks where to put it;
        devCards[position].getDeck().add(0,card);
    }

    public boolean leadersInGame(){ //at least one leader is in game right now (true)
        return (leaderCards.get(0).isInGame() == true || leaderCards.get(1).isInGame() == true);
    }

    public void discardLeader(int position){ //method to discard a LeaderCard, the controller will add a faith point;
        leaderCards.remove(position);
    }

    public void updatePosition(){
        pathPosition += 1;
    }

    public Production getBasicProduction(){
        return basicProduction;
    }

    public DeckDashboard[] getDevCards() {
        return devCards;
    }

    public ArrayList<LeaderCard> getLeaderCards() {
        return new ArrayList<>(leaderCards);
    } //Return copy of leaderCards

    public Storage getStorage() {
        return storage;
    }

    public ResourceCount getChest() {
        return chest;
    }

    public boolean isFull(Resource res){ //True if abilityDeposit with that res is full (Content=2)
        for (CounterTop c: arrayDeposit) {
            if(c.getResourceType().equals(res)&&c.getContent()<2)
                return false;
        }
        return true;
    }

    public boolean addToDeposit(Resource res){ //Trying to add resource to abilityDeposit if it isn't full
        if(!isFull(res)){
            for (CounterTop c: arrayDeposit) {
                if(c.getResourceType().equals(res)&&c.getContent()<2) {
                    c.addContent();
                    return true; //True if added correctly
                }
            }
        }
        return false; //False if couldn't add
    }
    public ResourceCount getAbilityDepositResources(){
        ResourceCount count = new ResourceCount(0,0,0,0,0);
        int addNum;
        for (CounterTop c: arrayDeposit)
            c.getResourceType().add(count,c.getContent());
        return count;
    }

    public ResourceCount getTotalResources(){
        ResourceCount count = storage.readStorage(); //initialize count to the content of storage
        count.addResources(chest.getCoins(), chest.getRocks(), chest.getServants(), chest.getShields()); //add chest resources to count
        return count;
    }
}
