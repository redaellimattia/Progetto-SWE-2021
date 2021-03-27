package it.polimi.ingsw.model;

import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.card.LeaderCard;

public class PlayerDashboard extends Player{
    private int pathPosition;
    private Storage storage;
    private ResourceCount chest;
    private DeckDashboard[] devCards = new DeckDashboard[3];
    private LeaderCard[] leaderCards = new LeaderCard[2];
    private Production basicProduction;

    public PlayerDashboard(Storage storage, ResourceCount chest, DeckDashboard[] devCards, LeaderCard[] leaderCards, Production basicProduction) {
        this.pathPosition = 0;
        this.storage = storage;
        this.chest = chest;
        this.devCards = devCards;
        this.leaderCards = leaderCards;
        this.basicProduction = basicProduction;
    }


    //public void updateStorage(){} DA FARE NEL CONTROLLER LO SWAP

    public void addToChest(ResourceCount resources){
        chest.addResources(resources.getCoins(),resources.getRocks(),resources.getServants(),resources.getShields());
    }

    public void subtractToChest(ResourceCount resources){
        chest.removeResources(resources.getCoins(),resources.getRocks(),resources.getServants(),resources.getShields());
    }

    public void updateDevCards(DevelopmentCard card){


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

    public LeaderCard[] getLeaderCards() {
        return leaderCards;
    }

    public Storage getStorage() {
        return storage;
    }

    public ResourceCount getChest() {
        return chest;
    }

    public ResourceCount getTotalResources(){
        ResourceCount count = storage.readStorage(); //initialize count to the content of storage
        count.addResources(chest.getCoins(), chest.getRocks(), chest.getServants(), chest.getShields()); //add chest resources to count
        return count;
    }
}
