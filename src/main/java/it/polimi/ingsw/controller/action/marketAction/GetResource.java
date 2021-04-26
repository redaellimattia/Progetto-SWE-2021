package it.polimi.ingsw.controller.action.marketAction;

import it.polimi.ingsw.model.MarketMarble;
import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.enumeration.MarbleColour;

public class GetResource implements AtomicMarketAction {

    private int storageRow;

    /**
     *
     * @param storageRow the number of the storage row in which the resource should be added
     *                   1-3: first row, second row, third row
     *                   4: special deposit (leader card ability)
     */
    public GetResource(int storageRow) {
        this.storageRow = storageRow;
    }

    /**
     *
     * @param marble the marble to convert
     * @param player the player performing the action
     * @return true if ended correctly
     *         false if the action was illegal
     */
    @Override
    public boolean useAction(MarketMarble marble, PlayerDashboard player) {
        if (marble.getColour() == MarbleColour.WHITE || marble.getColour() == MarbleColour.RED) {
            return false; // User cannot obtain a resource from red or white marble (without using the WhiteChangeAbility)
        }
        return StoreResource.storeResource(player, marble.getColour().convertToResource(), storageRow);
    }
}
