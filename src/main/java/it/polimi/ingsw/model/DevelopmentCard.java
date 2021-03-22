package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumeration.CardColour;

public class DevelopmentCard extends Card{
    private ResourceCount cost;
    private Production productionPower;
    private int level;
    private CardColour colour;

    public DevelopmentCard(ResourceCount cost, Production productionPower, int level, CardColour colour) {
        this.cost = cost;
        this.productionPower = productionPower;
        this.level = level;
        this.colour = colour;
    }

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
