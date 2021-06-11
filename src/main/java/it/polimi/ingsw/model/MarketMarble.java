package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumeration.MarbleColour;

public class MarketMarble {
    private final MarbleColour colour;

    /**
     *
     * @param colour the color of the marble
     */
    public MarketMarble(MarbleColour colour) {
        this.colour = colour;
    }

    /**
     *
     * @return the color of the marble
     */
    public MarbleColour getColour() {
        return colour;
    }
}
