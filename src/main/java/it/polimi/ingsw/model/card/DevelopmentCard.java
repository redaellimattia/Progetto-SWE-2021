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
}
