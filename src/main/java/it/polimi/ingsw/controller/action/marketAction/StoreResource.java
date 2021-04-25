package it.polimi.ingsw.controller.action.marketAction;

import it.polimi.ingsw.exceptions.CounterTopOverloadException;
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
            case 2:
                if(player.getStorage().getSecondRow().getContent() == 0) {
                    // If a counterTop is empty, we need to set the new resource type
                    player.getStorage().getSecondRow().setResourceType(marble.getColour().convertToResource());
                }
                if(player.getStorage().getSecondRow().getResourceType() != marble.getColour().convertToResource()) {
                    return false; // User cannot add a resource of a different type
                }
                if(player.getStorage().getSecondRow().getContent() > 1) {
                    return false; // User cannot add a resource into a full counterTop
                }
                player.getStorage().addToSecondRow(1); break;
            case 3:
                if(player.getStorage().getThirdRow().getContent() == 0) {
                    // If a counterTop is empty, we need to set the new resource type
                    player.getStorage().getThirdRow().setResourceType(marble.getColour().convertToResource());
                }
                if(player.getStorage().getThirdRow().getResourceType() != marble.getColour().convertToResource()) {
                    return false; // User cannot add a resource of a different type
                }
                if(player.getStorage().getThirdRow().getContent() > 2) {
                    return false; // User cannot add a resource into a full counterTop
                }
                player.getStorage().addToThirdRow(1); break;
            case 4:
                try {
                    player.addToDeposit(marble.getColour().convertToResource());
                }
                catch (CounterTopOverloadException e) {
                    return false; // User cannot add a resource in an additional deposit if it is full or not present
                }
        }
        return true;
    }
}
