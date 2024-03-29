package it.polimi.ingsw.controller.action.marketAction;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.model.CounterTop;
import it.polimi.ingsw.model.MarketMarble;
import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.Storage;

import java.util.ArrayList;

public class DiscardResource extends AtomicMarketAction {

    public DiscardResource() {
    }

    /**
     * Checks if the action is legal
     * @param marble the marble to discard
     * @param player the player performing the action
     * @param tempStorage the storage copy updated during checks on different marbles, to perform controls without updating the real storage
     *
     * @return true if the action is legal
     *         false if the action is illegal
     */
    @Override
    public boolean checkAction(MarketMarble marble, PlayerDashboard player, Storage tempStorage, ArrayList<CounterTop> tempArrayDeposit) {
        return true; // Discard is always possible
    }

    /**
     *
     * @param marble the marble to discard
     * @param player the player performing the action
     * @param gameManager gameManager
     * @return true if ended correctly
     */
    @Override
    public boolean useAction(MarketMarble marble, PlayerDashboard player, GameManager gameManager) {
        gameManager.updateOpponentsPathPosition(player);
        return true;
    }
}
