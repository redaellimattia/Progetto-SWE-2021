package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.Production;
import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.enumeration.CardColour;

public class DevelopmentCard extends Card {
    private ResourceCount cost;
    private Production productionPower;
    private int level;
    private CardColour colour;

    public DevelopmentCard(int victoryPoints,ResourceCount cost, Production productionPower, int level, CardColour colour) {
        super(victoryPoints);
        this.cost = cost;
        this.productionPower = productionPower;
        if(level >= 1 && level <= 3)
            this.level = level;
        this.colour = colour;
    }

    //GETTERS
    public ResourceCount getCost() {
        return cost;
    }
    public Production getProductionPower() {
        return productionPower;
    }
    public int getLevel() {
        return level;
    }
    public CardColour getColour() {
        return colour;
    }


    @Override
    public boolean equals(Object o){
        if (o == this) { //True if it's this instance
            return true;
        }
        if (!(o instanceof DevelopmentCard)) //Check if o is instanceOf ResourceCount
            return false;

        //Check if same values
        DevelopmentCard c = (DevelopmentCard) o; //Cast to ResourceCount
        if(this.getVictoryPoints()==c.getVictoryPoints()&&this.getCost().equals(c.getCost())&&
                this.getProductionPower().equals(c.getProductionPower())&&this.getLevel()==c.getLevel()&&this.getColour()==c.getColour())
            return true;
        return false;
    }
}
