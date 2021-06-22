package it.polimi.ingsw.controller.action.move;

import it.polimi.ingsw.controller.PlayerTurnManager;
import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.exceptions.action.WrongResourcesMovedException;
import it.polimi.ingsw.model.PlayerDashboard;

public class OrganizeStorage extends Action {
    private final int from;
    private final int to;

    /**
     *
     * @param from number of the CounterTop from which the swap start
     * @param to number of target CounterTop
     */
    public OrganizeStorage(int from, int to) {
        this.from = from;
        this.to = to;
    }

    /**
     * Return true if the shelves are swapped correctly and the storage respects the rules for resourcetypes
     * @param player player that wanted to switch Storage's shelves
     */
    @Override
    public boolean useAction(PlayerDashboard player, PlayerTurnManager turnManager) {
        if(player.getStorage().swapRows(from,to)) {
            return player.getStorage().checkShelves();
        }
        else
            throw new WrongResourcesMovedException(player);
    }


}
