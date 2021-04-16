package it.polimi.ingsw.controller.action.move;

import it.polimi.ingsw.controller.Parameter;
import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.model.PlayerDashboard;

public class OrganizeStorage extends Action {

    //RETURN TRUE IF THE SHELVES ARE SWAPPED CORRECTLY AND THE STORAGE RESPECTS THE RULES FOR RESOURCETYPES;
    public boolean useAction(PlayerDashboard player, Parameter param) {
        int from = param.getFrom_row();
        int to = param.getTo_column();
        if(player.getStorage().swapRows(from,to))
            return player.getStorage().checkShelves();
        return false;
    }


}
