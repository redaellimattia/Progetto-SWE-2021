package it.polimi.ingsw.controller.action.move;

import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.model.PlayerDashboard;

public class OrganizeStorage extends Action {
    private int from;
    private int to;

    public OrganizeStorage(int from, int to) {
        this.from = from;
        this.to = to;
    }

    //RETURN TRUE IF THE SHELVES ARE SWAPPED CORRECTLY AND THE STORAGE RESPECTS THE RULES FOR RESOURCETYPES;
    public boolean useAction(PlayerDashboard player) {
        if(player.getStorage().swapRows(from,to))
            return player.getStorage().checkShelves();
        return false;
    }


}
