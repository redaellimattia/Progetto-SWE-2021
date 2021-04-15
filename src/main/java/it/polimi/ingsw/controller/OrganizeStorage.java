package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.CounterTop;
import it.polimi.ingsw.model.PlayerDashboard;

public class OrganizeStorage extends Action{

    //RETURN TRUE IF THE SHELVES ARE SWAPPED CORRECTLY AND THE STORAGE RESPECTS THE RULES FOR RESOURCETYPES;
    public boolean swapShelves(PlayerDashboard player, int from, int to) {
        if(player.getStorage().swapRows(from,to))
            return player.getStorage().checkShelves();
        return false;
    }

    public boolean moveFromLeaderDeposit(PlayerDashboard player, int from, int to){
        return true;
    }
}
