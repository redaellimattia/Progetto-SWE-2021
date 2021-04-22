package it.polimi.ingsw.controller.action.move;

import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.exceptions.WrongResourcesMovedException;
import it.polimi.ingsw.model.PlayerDashboard;

public class OrganizeStorage extends Action {
    private int from;
    private int to;

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
     *
     * @param player player that wanted to switch Storage's shelves
     * @return true if the action is done correctly, false otherwise
     */
    //RETURN TRUE IF THE SHELVES ARE SWAPPED CORRECTLY AND THE STORAGE RESPECTS THE RULES FOR RESOURCETYPES;
    public boolean useAction(PlayerDashboard player) {
        if(player.getStorage().swapRows(from,to))
            return player.getStorage().checkShelves();
        throw new WrongResourcesMovedException();
        //return false;
    }


}
