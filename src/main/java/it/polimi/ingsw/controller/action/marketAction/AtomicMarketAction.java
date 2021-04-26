package it.polimi.ingsw.controller.action.marketAction;

import it.polimi.ingsw.model.MarketMarble;
import it.polimi.ingsw.model.PlayerDashboard;

public interface AtomicMarketAction {

    /**
     *
     * @param marble the marble involved in the action
     * @param player the player performing the action
     * @return true if ended correctly
     *         false if the action was illegal
     */
    boolean useAction(MarketMarble marble, PlayerDashboard player);
}
