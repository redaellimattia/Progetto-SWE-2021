package it.polimi.ingsw.controller.action.marketAction;

import it.polimi.ingsw.model.MarketMarble;
import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.enumeration.MarbleColour;

public class GetResource implements AtomicMarketAction {

    private int storageRow;

    public GetResource(int storageRow) {
        this.storageRow = storageRow;
    }

    @Override
    public boolean useAction(MarketMarble marble, PlayerDashboard player) {
        if (marble.getColour() == MarbleColour.WHITE || marble.getColour() == MarbleColour.RED) {
            return false; // User cannot obtain a resource from red or white marble (without using the WhiteChangeAbility)
        }
        return StoreResource.storeResource(player, marble.getColour().convertToResource(), storageRow);
    }
}
