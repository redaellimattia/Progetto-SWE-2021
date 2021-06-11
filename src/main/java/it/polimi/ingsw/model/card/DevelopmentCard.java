package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.Production;
import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.enumeration.CardColour;

public class DevelopmentCard extends Card {
    private final ResourceCount cost;
    private final Production productionPower;
    private int level;
    private final CardColour colour;

    /**
     *
     * @param victoryPoints victoryPoints value of the card
     * @param cost cost as ResourceCount to buy the card
     * @param productionPower production of the card
     * @param level level value of the card
     * @param colour colour of the card as CardColour
     */
    public DevelopmentCard(int id, int victoryPoints, ResourceCount cost, Production productionPower, int level, CardColour colour) {
        super(id, victoryPoints);
        this.cost = cost;
        this.productionPower = productionPower;
        if(level >= 1 && level <= 3)
            this.level = level;
        this.colour = colour;
    }

    //GETTERS

    /**
     *
     * @return cost value as ResourceCount
     */
    public ResourceCount getCost() {
        return cost;
    }
    /**
     *
     * @return production as Production
     */
    public Production getProductionPower() {
        return productionPower;
    }
    /**
     *
     * @return level value as int
     */
    public int getLevel() {
        return level;
    }
    /**
     *
     * @return colour as CardColour
     */
    public CardColour getColour() {
        return colour;
    }


    /**
     * EQUALS
     *
     * @param o passed Object
     * @return true if o is the same object of this, or it has the same attributes
     */
    @Override
    public boolean equals(Object o){
        if (o == this) { //True if it's this instance
            return true;
        }
        if (!(o instanceof DevelopmentCard)) //Check if o is instanceOf ResourceCount
            return false;

        //Check if same values
        DevelopmentCard c = (DevelopmentCard) o; //Cast to ResourceCount
        return this.getVictoryPoints() == c.getVictoryPoints() && this.getCost().equals(c.getCost()) &&
                this.getProductionPower().equals(c.getProductionPower()) && this.getLevel() == c.getLevel() && this.getColour() == c.getColour();
    }
}
