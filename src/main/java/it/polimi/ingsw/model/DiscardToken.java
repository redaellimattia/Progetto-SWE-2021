package it.polimi.ingsw.model;

public class DiscardToken extends SoloToken{
    private CardColour colour;

    public DiscardToken(CardColour colour){
        this.colour = colour;
    }
    public CardColour getColour() {
        return colour;
    }
}
