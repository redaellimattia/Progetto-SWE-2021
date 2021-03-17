package it.polimi.ingsw.model;

public class ResourceCount {
    private int coins;
    private int rocks;
    private int servants;
    private int shields;

    public ResourceCount(int coins, int rocks, int servants, int shields) {
        this.coins = coins;
        this.rocks = rocks;
        this.servants = servants;
        this.shields = shields;
    }

    public int getCoins() {
        return coins;
    }

    public int getRocks() {
        return rocks;
    }

    public int getServants() {
        return servants;
    }

    public int getShields() {
        return shields;
    }

    public void addResources(){

    }

    public void removeResources(){

    }
}
