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
        return StoreResource.storeResource(player, resource, storageRow);
    }
}
