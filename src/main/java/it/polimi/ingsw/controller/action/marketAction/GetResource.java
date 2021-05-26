package it.polimi.ingsw.controller.action.marketAction;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.exceptions.action.WrongMarbleException;
import it.polimi.ingsw.model.CounterTop;
import it.polimi.ingsw.model.MarketMarble;
import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.Storage;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.enumeration.MarbleColour;

import java.util.ArrayList;

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


    @Override
    public boolean checkAction(MarketMarble marble, PlayerDashboard player, Storage tempStorage, ArrayList<CounterTop> tempArrayDeposit){
        if(marble.getColour() == MarbleColour.WHITE) {
            return true; // GetResource on a white marble does nothing (and is always possible)
        }
        if (marble.getColour() == MarbleColour.RED) {
            return false;
            //throw new WrongMarbleException(marble, player); // User cannot obtain a resource from red marble
        }
        return checkStore(tempStorage, tempArrayDeposit, marble.getColour().convertToResource(), storageRow);
    }

    /**
     *
     * @param marble the marble to convert
     * @param player the player performing the action
     * @throws WrongMarbleException if the action was illegal
     */
    @Override
    public boolean useAction(MarketMarble marble, PlayerDashboard player, GameManager gameManager) {
        if(marble.getColour() == MarbleColour.WHITE) {
            return true; // GetResource on a white marble does nothing (and is always possible)
        }
        return storeResource(player, marble.getColour().convertToResource(), storageRow);
    }
}
