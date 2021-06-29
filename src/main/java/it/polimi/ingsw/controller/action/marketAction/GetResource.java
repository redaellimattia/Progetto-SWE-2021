package it.polimi.ingsw.controller.action.marketAction;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.model.CounterTop;
import it.polimi.ingsw.model.MarketMarble;
import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.Storage;
import it.polimi.ingsw.model.enumeration.MarbleColour;

import java.util.ArrayList;

public class GetResource extends AtomicMarketAction {

    private final int storageRow;

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
     * Checks if the action is legal
     * @param marble the marble from witch take a resource
     * @param player the player performing the action
     * @param tempStorage the storage copy updated during checks on different marbles, to perform controls without updating the real storage
     * @param tempArrayDeposit the deposit array copy updated during checks on different marbles, to perform controls without updating the real deposits
     * @return true if the action is legal
     *         false if the action is illegal
     */
    @Override
    public boolean checkAction(MarketMarble marble, PlayerDashboard player, Storage tempStorage, ArrayList<CounterTop> tempArrayDeposit){
        if(marble.getColour() == MarbleColour.WHITE) {
            return true; // GetResource on a white marble does nothing (and is always possible)
        }
        if (marble.getColour() == MarbleColour.RED) {
            return false; // User cannot obtain a resource from red marble
        }
        return checkStore(tempStorage, tempArrayDeposit, marble.getColour().convertToResource(), storageRow);
    }

    /**
     *
     * @param marble the marble to convert
     * @param player the player performing the action
     * @param gameManager gameManager
     * @return true if ended correctly
     */
    @Override
    public boolean useAction(MarketMarble marble, PlayerDashboard player, GameManager gameManager) {
        if(marble.getColour() == MarbleColour.WHITE) {
            return true; // GetResource on a white marble does nothing (and is always possible)
        }
        return storeResource(player, marble.getColour().convertToResource(), storageRow);
    }
}
