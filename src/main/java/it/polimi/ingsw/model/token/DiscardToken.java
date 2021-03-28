package it.polimi.ingsw.model.token;

import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.Shop;
import it.polimi.ingsw.model.enumeration.CardColour;

public class DiscardToken implements SoloToken {
    private CardColour colour;

    public DiscardToken(CardColour colour){
        this.colour = colour;
    }

    @Override
    public void useToken(PlayerDashboard player) {
    }
}
