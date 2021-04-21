package it.polimi.ingsw.controller.action.marketAction;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.MarketMarble;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerDashboard;

public class DiscardResource implements AtomicMarketAction {
    @Override
    public boolean useAction(MarketMarble marble, PlayerDashboard player) {
        /* for(Player i: Game.getPlayers()) {
            // Update path position if the player selected is not the current player
        } */
        return true;
    }
}
