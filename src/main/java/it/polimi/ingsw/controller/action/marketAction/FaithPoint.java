package it.polimi.ingsw.controller.action.marketAction;

import it.polimi.ingsw.model.MarketMarble;
import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.enumeration.Resource;

public class FaithPoint implements AtomicMarketAction {

    public FaithPoint() {
    }

    @Override
    public boolean useAction(MarketMarble marble, PlayerDashboard player) {
        if(marble.getColour().convertToResource() != Resource.FAITH) {
            return false; // This choice requires a red marble
        }
        player.updatePathPosition();
        return true;
    }
}
