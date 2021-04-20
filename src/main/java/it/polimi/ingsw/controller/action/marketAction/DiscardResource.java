package it.polimi.ingsw.controller.action.marketAction;

import it.polimi.ingsw.model.MarketMarble;
import it.polimi.ingsw.model.PlayerDashboard;

public class DiscardResource implements AtomicMarketAction {
    @Override
    public boolean useAction(MarketMarble marble, PlayerDashboard player) {
        return false;
    }
}
