package it.polimi.ingsw.controller.action.marketAction;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.MarketMarble;
import it.polimi.ingsw.model.PlayerDashboard;

public class DiscardResource implements AtomicMarketAction {

    /**
     *
     * @param marble the marble to discard
     * @param player the player performing the action
     * @return true if ended correctly
     *         false if the action was illegal
     */
    @Override
    public boolean useAction(MarketMarble marble, PlayerDashboard player) {
        for(PlayerDashboard i: Game.getPlayers()) {
            if(i.getNickName() != player.getNickName()) {
                i.updatePathPosition();
                // Check path position
            }
        }
        return true;
    }
}
