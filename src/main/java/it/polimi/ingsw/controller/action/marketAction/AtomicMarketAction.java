package it.polimi.ingsw.controller.action.marketAction;

import it.polimi.ingsw.model.MarketMarble;
import it.polimi.ingsw.model.PlayerDashboard;

public interface AtomicMarketAction {
    boolean useAction(MarketMarble marble, PlayerDashboard player);
}
