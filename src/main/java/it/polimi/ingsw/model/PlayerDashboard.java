package it.polimi.ingsw.model;

import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.card.LeaderCard;

public class PlayerDashboard extends Player{
    private int pathPosition;
    private Storage storage;
    private ResourceCount chest;
    private Deck[] devCards = new Deck[3];
    private LeaderCard[] leaderCards = new LeaderCard[2];
    private Production basicProduction;

    public PlayerDashboard(Storage storage, ResourceCount chest, Deck[] devCards, LeaderCard[] leaderCards, Production basicProduction) {
        this.pathPosition = 0;
        this.storage = storage;
        this.chest = chest;
        this.devCards = devCards;
        this.leaderCards = leaderCards;
        this.basicProduction = basicProduction;
    }


    public void updateStorage(){

    }

    public void updateChest(){

    }

    public void updateDevCards(DevelopmentCard card){


    }

    public void updateLeaderCards(){

    }

    public void updatePosition(){
        pathPosition += 1;
    }

    public Production getBasicProduction(){
        return basicProduction;
    }

    public Deck[] getDevCards() {
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
}
