package it.polimi.ingsw.model;

public class ResourceCount {
    private int coins;
    private int rocks;
    private int servants;
    private int shields;
    private int faith;

    public ResourceCount(int coins, int rocks, int servants, int shields, int faith) {
        this.coins = coins;
        this.rocks = rocks;
        this.servants = servants;
        this.shields = shields;
        this.faith = faith;
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

    public int getFaith() { return faith;}

    public void addCoins(int coins){
        this.coins += coins;
    }
    public void removeCoins(int coins){
        this.coins -= coins;
    }
    public void addRocks(int rocks){
        this.rocks += rocks;
    }
    public void removeRocks(int rocks){
        this.rocks -= rocks;
    }
    public void addServants(int servants){
        this.servants += servants;
    }
    public void removeServants(int servants){
        this.servants -= servants;
    }
    public void addShields(int shields){
        this.shields += shields;
    }
    public void removeShields(int shields){
        this.shields -= shields;
    }

    public void addResources(int coins, int rocks, int servants, int shields){
        this.coins += coins;
        this.rocks += rocks;
        this.servants += servants;
        this.shields += shields;
    }

    public void removeResources(int coins, int rocks, int servants, int shields){
        this.coins -= coins;
        this.rocks -= rocks;
        this.servants -= servants;
        this.shields -= shields;

    }
}
