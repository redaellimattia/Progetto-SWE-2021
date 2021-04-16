package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.model.CounterTop;
import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.Storage;

public class OrganizeStorage extends Action {

    //RETURN TRUE IF THE SHELVES ARE SWAPPED CORRECTLY AND THE STORAGE RESPECTS THE RULES FOR RESOURCETYPES;
    public boolean swapShelves(PlayerDashboard player,Parameter param) {
        int from = param.getFrom_row();
        int to = param.getTo_column();
        if(player.getStorage().swapRows(from,to))
            return player.getStorage().checkShelves();
        return false;
    }


}
