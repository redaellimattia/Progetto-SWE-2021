package it.polimi.ingsw.controller.action.marketAction;

import it.polimi.ingsw.exceptions.CounterTopOverloadException;
import it.polimi.ingsw.model.MarketMarble;
import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.enumeration.MarbleColour;
import it.polimi.ingsw.model.enumeration.Resource;

public class ConvertWhiteMarble implements AtomicMarketAction {

    private LeaderCard leaderCard;
    private int storageRow;

    public ConvertWhiteMarble(LeaderCard leaderCard, int storageRow) {
        this.leaderCard = leaderCard;
        this.storageRow = storageRow;
    }

    @Override
    public boolean useAction(MarketMarble marble, PlayerDashboard player) {
        if(marble.getColour() != MarbleColour.WHITE) {
            return false; // User cannot convert a marble that isn't white
        }
        if(!player.leaderCardExists(leaderCard)) {
            return false; // User must own the leaderCard
        }
        Resource resource = leaderCard.getSpecialAbility().useWhiteChangeAbility();
        switch(storageRow) {
            case 1:
                if(player.getStorage().getFirstRow().getContent() == 0) {
                    // If a counterTop is empty, we need to set the new resource type
                    player.getStorage().getFirstRow().setResourceType(resource);
                }
                if(player.getStorage().getFirstRow().getResourceType() != resource) {
                    return false; // User cannot add a resource of a different type
                }
                if(player.getStorage().getFirstRow().getContent() > 0) {
                    return false; // User cannot add a resource into a full counterTop
                }
                player.getStorage().addToFirstRow(1); break;
            case 2:
                if(player.getStorage().getSecondRow().getContent() == 0) {
                    // If a counterTop is empty, we need to set the new resource type
                    player.getStorage().getSecondRow().setResourceType(resource);
                }
                if(player.getStorage().getSecondRow().getResourceType() != resource) {
                    return false; // User cannot add a resource of a different type
                }
                if(player.getStorage().getSecondRow().getContent() > 1) {
                    return false; // User cannot add a resource into a full counterTop
                }
                player.getStorage().addToSecondRow(1); break;
            case 3:
                if(player.getStorage().getThirdRow().getContent() == 0) {
                    // If a counterTop is empty, we need to set the new resource type
                    player.getStorage().getThirdRow().setResourceType(resource);
                }
                if(player.getStorage().getThirdRow().getResourceType() != resource) {
                    return false; // User cannot add a resource of a different type
                }
                if(player.getStorage().getThirdRow().getContent() > 2) {
                    return false; // User cannot add a resource into a full counterTop
                }
                player.getStorage().addToThirdRow(1); break;
            case 4:
                try {
                    player.addToDeposit(resource);
                }
                catch (CounterTopOverloadException e) {
                    return false; // User cannot add a resource in an additional deposit if it is full or not present
                }
        }
        return true;
    }
}
