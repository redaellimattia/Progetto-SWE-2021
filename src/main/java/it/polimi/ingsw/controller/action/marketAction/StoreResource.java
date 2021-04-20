package it.polimi.ingsw.controller.action.marketAction;

import it.polimi.ingsw.model.MarketMarble;
import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.enumeration.MarbleColour;

public class StoreResource implements AtomicMarketAction {

    private int storageRow;

    public StoreResource(int storageRow) {
        this.storageRow = storageRow;
    }

    @Override
    public boolean useAction(MarketMarble marble, PlayerDashboard player) {
        if (marble.getColour() == MarbleColour.WHITE || marble.getColour() == MarbleColour.RED) {
            return false; // User cannot obtain a resource from red or white marble (without using the WhiteChangeAbility)
        }
        switch(storageRow) {
            case 1:
                if(player.getStorage().getFirstRow().getContent() == 0) {
                    // If a counterTop is empty, we need to set the new resource type
                    player.getStorage().getFirstRow().setResourceType(marble.getColour().convertToResource());
                }
                if(player.getStorage().getFirstRow().getResourceType() != marble.getColour().convertToResource()) {
                    return false; // User cannot add a resource of a different type
                }
                if(player.getStorage().getFirstRow().getContent() > 0) {
                    return false; // User cannot add a resource into a full counterTop
                }
                player.getStorage().addToFirstRow(1); break;
            case 2: player.getStorage().addToSecondRow(1); break;
            case 3: player.getStorage().addToThirdRow(1); break;
        }

        return false;
    }
}
