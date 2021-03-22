package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumeration.CardColour;

public class DiscardToken implements SoloToken{
    private CardColour colour;

    public DiscardToken(CardColour colour){
        this.colour = colour;
    }

    @Override
    public void useToken() {

    }
}
