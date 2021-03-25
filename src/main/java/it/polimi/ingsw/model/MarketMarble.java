package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumeration.MarbleColour;

public class MarketMarble {
    private final MarbleColour colour;

    MarketMarble(MarbleColour colour) {
        this.colour = colour;
    }

    public MarbleColour getColour() {
        return colour;
    }
}
