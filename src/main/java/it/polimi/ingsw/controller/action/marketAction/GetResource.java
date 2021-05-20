package it.polimi.ingsw.controller.action.marketAction;

import it.polimi.ingsw.exceptions.action.WrongMarbleException;
import it.polimi.ingsw.model.MarketMarble;
import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.enumeration.MarbleColour;

public class GetResource extends AtomicMarketAction {

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
     * @throws WrongMarbleException if the action was illegal
     */
    @Override
    public boolean useAction(MarketMarble marble, PlayerDashboard player) {
        if(marble.getColour() == MarbleColour.WHITE) {
            // If the player owns at least one Leader Card with WhiteChangeAbility, that card must be used when getting a white marble
            // (so the choice made by the user cannot be GetResource, it should be ConvertWhiteMarble)
            for (LeaderCard c: player.getLeaderCards()) {
                if (c.getSpecialAbility().useWhiteChangeAbility() != null) {
                    throw new WrongMarbleException(marble);
                    // TO-DO: Maybe is better to create a more specific exception
                }
            }
            return true; // GetResource on a white marble does nothing

        }
        if (marble.getColour() == MarbleColour.RED) {
            throw new WrongMarbleException(marble); // User cannot obtain a resource from red or white marble (without using the WhiteChangeAbility)
        }
        return storeResource(player, marble.getColour().convertToResource(), storageRow);
    }
}
