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

    //GETTERS
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


    //ADD ON A SINGLE PARAMETER
    public void addCoins(int coins){
        this.coins += coins;
    }
    public void addRocks(int rocks){
        this.rocks += rocks;
    }
    public void addServants(int servants){
        this.servants += servants;
    }
    public void addShields(int shields){
        this.shields += shields;
    }

    //REMOVE ON A SINGLE PARAMETER
    public void removeCoins(int coins){
        this.coins -= coins;
    }
    public void removeRocks(int rocks){
        this.rocks -= rocks;
    }
    public void removeServants(int servants){
        this.servants -= servants;
    }
    public void removeShields(int shields){
        this.shields -= shields;
    }


    //ADD RESOURCES, TO MODIFY MORE THAN ONE PARAMETER AT ONCE
    public void addResources(int coins, int rocks, int servants, int shields){
        this.coins += coins;
        this.rocks += rocks;
        this.servants += servants;
        this.shields += shields;
    }

    //REMOVE RESOURCES, TO MODIFY MORE THAN ONE PARAMETER AT ONCE
    public void removeResources(int coins, int rocks, int servants, int shields){
        this.coins -= coins;
        this.rocks -= rocks;
        this.servants -= servants;
        this.shields -= shields;

    }

    //EQUALS
    @Override
    public boolean equals(Object o){
        if (o == this) { //True if it's this instance
            return true;
        }
        if (!(o instanceof ResourceCount)) //Check if o is instanceOf ResourceCount
            return false;

        //Check if same values
        ResourceCount c = (ResourceCount) o; //Cast to ResourceCount
        if(this.getCoins()==c.getCoins()&&this.getRocks()==c.getRocks()&&
           this.getServants()==c.getServants()&&this.getShields()==c.getShields()&&this.getFaith()==c.getFaith())
            return true;
        return false;
    }
}
