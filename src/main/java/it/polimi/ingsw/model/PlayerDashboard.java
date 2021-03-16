package it.polimi.ingsw.model;

public class PlayerDashboard extends Player{
    private int pathPosition;
    private Storage storage;
    private ResourceCount chest;
    private Deck[] devCards = new Deck[3];
    private LeaderCard[] leaderCards = new LeaderCard[2];

    public PlayerDashboard(Storage storage, ResourceCount chest, Deck[] devCards, LeaderCard[] leaderCards) {
        this.pathPosition = 0;
        this.storage = storage;
        this.chest = chest;
        this.devCards = devCards;
        this.leaderCards = leaderCards;
    }
}
