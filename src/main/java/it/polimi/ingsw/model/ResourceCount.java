package it.polimi.ingsw.model;

public class ResourceCount {
    private int coins;
    private int rocks;
    private int servants;
    private int shields;
    private int faith;

    /**
     *
     * @param coins Resource.COIN counter
     * @param rocks Resource.ROCK counter
     * @param servants Resource.SERVANT counter
     * @param shields Resource.SHIELD counter
     * @param faith Resource.FAITH counter
     */
    public ResourceCount(int coins, int rocks, int servants, int shields, int faith) {
        this.coins = coins;
        this.rocks = rocks;
        this.servants = servants;
        this.shields = shields;
        this.faith = faith;
    }

    //GETTERS

    /**
     *
     * @return Resource.COIN counter
     */
    public int getCoins() {
        return coins;
    }

    /**
     *
     * @return Resource.ROCK counter
     */
    public int getRocks() {
        return rocks;
    }

    /**
     *
     * @return Resource.SERVANT counter
     */
    public int getServants() {
        return servants;
    }

    /**
     *
     * @return Resource.SHIELD counter
     */
    public int getShields() {
        return shields;
    }

    /**
     *
     * @return Resource.FAITH counter
     */
    public int getFaith() { return faith;}


    //ADD ON A SINGLE PARAMETER

    /**
     *
     * @param coins Resource.COIN counter incremented of coins
     */
    public void addCoins(int coins){
        this.coins += coins;
    }

    /**
     *
     * @param rocks Resource.ROCK counter incremented of rocks
     */
    public void addRocks(int rocks){
        this.rocks += rocks;
    }

    /**
     *
     * @param servants Resource.SERVANT counter incremented by servants
     */
    public void addServants(int servants){
        this.servants += servants;
    }

    /**
     *
     * @param shields Resource.SHIELD counter incremented by shields
     */
    public void addShields(int shields){
        this.shields += shields;
    }

    /**
     *
     * @param faith Resource.FAITH counter incremented by faith
     */
    public void addFaith(int faith){
        this.faith += faith;
    }

    //REMOVE ON A SINGLE PARAMETER

    /**
     *
     * @param coins Resource.COIN counter reduced of coins
     */
    public void removeCoins(int coins){
        this.coins -= coins;
    }

    /**
     *
     * @param rocks Resource.ROCK counter reduced of rocks
     */
    public void removeRocks(int rocks){
        this.rocks -= rocks;
    }

    /**
     *
     * @param servants Resource.SERVANT counter reduced of servants
     */
    public void removeServants(int servants){
        this.servants -= servants;
    }

    /**
     *
     * @param shields Resource.SHIELD counter reduced of shields
     */
    public void removeShields(int shields){
        this.shields -= shields;
    }

    /**
     *
     * @param faith Resource.FAITH counter reduced of faith
     */
    public void removeFaith(int faith){
        this.faith -= faith;
    }




    /**
     * ADD RESOURCES, TO MODIFY MORE THAN ONE PARAMETER AT ONCE
     *
     * @param coins  Resource.COIN counter incremented of coins
     * @param rocks Resource.ROCK counter incremented of rocks
     * @param servants Resource.SERVANT counter incremented of servants
     * @param shields Resource.SHIELD counter incremented of shields
     * @param faith Resource.FAITH counter incremented of coins
     */
    public void addResources(int coins, int rocks, int servants, int shields,int faith){
        this.coins += coins;
        this.rocks += rocks;
        this.servants += servants;
        this.shields += shields;
        this.faith += faith;
    }

    /**
     * REMOVE RESOURCES, TO MODIFY MORE THAN ONE PARAMETER AT ONCE
     *
     * @param coins  Resource.COIN counter reduced of coins
     * @param rocks Resource.ROCK counter reduced of rocks
     * @param servants Resource.SERVANT counter reduced of servants
     * @param shields Resource.SHIELD counter reduced of shields
     * @param faith Resource.FAITH counter reduced of coins
     */
    public void removeResources(int coins, int rocks, int servants, int shields,int faith){
        this.coins -= coins;
        this.rocks -= rocks;
        this.servants -= servants;
        this.shields -= shields;
        this.faith -= faith;

    }

    /**
     * RETURN TRUE IF THIS RESOURCECOUNT HAS >= RESOURCES THAN THE PASSED ONE
     *
     * @param count passed ResourceCount
     * @return true if this has more or equal resources compared to count
     */
    public boolean hasMoreOrEqualsResources(ResourceCount count){
        return (this.coins >= count.getCoins() && this.rocks >= count.getRocks() && this.servants >= count.getServants() && this.shields >= count.getShields());
    }

    /**
     * SUM 2 RESOURCECOUNT
     *
     * @param add passed ResourceCount
     */
    public void sumCounts(ResourceCount add){
        if(add!=null)
            this.addResources(add.getCoins(),add.getRocks(), add.getServants(), add.getShields(),add.getFaith());
    }

    /**
     * SUBTRACT 2 RESOURCECOUNT
     *
     * @param sub passed ResourceCount
     */
    public void subCounts(ResourceCount sub){
        if(sub!=null) //Should avoid sub if it has more res than THIS?
            this.removeResources(sub.getCoins(),sub.getRocks(), sub.getServants(), sub.getShields(),sub.getFaith());
    }

    /**
     * RETURNS SUM OF 2 RESOURCECOUNT AS A SINGLE NEW RESOURCECOUNT
     *
     * @param rc1 first ResourceCount passed
     * @param rc2 second ResourceCount passed
     * @return new ResourceCount as Sum of rc1 and rc2
     */
    public static ResourceCount getTotal(ResourceCount rc1, ResourceCount rc2){
        if(rc1!=null&&rc2!=null) {
            ResourceCount total = new ResourceCount(0, 0, 0, 0, 0);
            total.sumCounts(rc1);
            total.sumCounts(rc2);
            return total;
        }
        if(rc1==null)
            return rc2;
        return rc1;
    }

    /**
     * RETURN AS AN INT THE NUMBER OF THE TOTAL RESOURCES
     * @param rc ResourceCount to convert to an int
     * @return the number of resources in rc
     */
    public static int resCountToInt(ResourceCount rc){
        return rc.getCoins()+rc.getRocks()+rc.getServants()+rc.getShields();
    }
    /**
     * EQUALS
     *
     * @param o passed Object
     * @return true if o is the same object of this, or it has the same number of resources
     */
    @Override
    public boolean equals(Object o){
        if (o == this) { //True if it's this instance
            return true;
        }
        if (!(o instanceof ResourceCount)) //Check if o is instanceOf ResourceCount
            return false;

        //Check if same values
        ResourceCount c = (ResourceCount) o; //Cast to ResourceCount
        return this.getCoins() == c.getCoins() && this.getRocks() == c.getRocks() &&
                this.getServants() == c.getServants() && this.getShields() == c.getShields() && this.getFaith() == c.getFaith();
    }

    public String toString(){
        String count = "";
        if(this.getCoins() !=0)
            count += ("COINS: " + this.getCoins()+" ");
        if(this.getRocks() !=0)
            count +=("ROCKS: " + this.getRocks()+" ");
        if(this.getServants() !=0)
            count += ("SERVANTS: " + this.getServants()+" ");
        if(this.getShields() !=0)
            count += ("SHIELDS: " + this.getShields()+" ");
        if(this.getFaith() !=0)
            count += ("FAITHPOINT: " + this.getFaith()+" ");
        return count;
    }
}
