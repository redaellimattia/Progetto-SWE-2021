package it.polimi.ingsw.controller.action.marketAction;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.model.MarketMarble;
import it.polimi.ingsw.model.PlayerDashboard;

public class DiscardResource extends AtomicMarketAction {

    public DiscardResource() {
    }

    /**
     *
     * @param marble the marble to discard
     * @param player the player performing the action
     * @return true if ended correctly
     *         false if the action was illegal
     */
    @Override
    public boolean useAction(MarketMarble marble, PlayerDashboard player, GameManager gameManager) {
        gameManager.updateOpponentsPathPosition(player);
        return true;
    }
}
