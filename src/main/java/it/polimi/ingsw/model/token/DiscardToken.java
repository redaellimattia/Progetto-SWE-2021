package it.polimi.ingsw.model.token;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.enumeration.CardColour;
import it.polimi.ingsw.network.server.Observer;

public class DiscardToken implements SoloToken {
    private final CardColour colour;

    public DiscardToken(CardColour colour){
        this.colour = colour;
    }

    /**
     * Executes the action represented by the token
     * @param player Lorenzo's dashboard
     * @param game the current Game object
     * @param observer serverLobby observer
     */
    @Override
    public void useToken(PlayerDashboard player, Game game, Observer observer) {
        game.getShop().discardFromToken(colour);
        observer.lorenzoAction("Lorenzo discarded 2: "+colour+" cards from the shop!");
    }
}
