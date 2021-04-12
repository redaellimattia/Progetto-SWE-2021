package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.CounterTop;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerDashboard;

public class OrganizeStorage extends Action{

    //RETURN TRUE IF THE SHELVES ARE SWAPPED CORRECTLY AND THE STORAGE RESPECTS THE SAME RULES;
    public boolean swapShelves(PlayerDashboard player, int from, int to) {
        CounterTop[] shelves = player.getStorage().getShelvesArray();
        CounterTop temp = shelves[to];
        if(player.getStorage().setThisRow(shelves[from],to) && player.getStorage().setThisRow(temp,from))
            if(super.checkShelves(player.getStorage()))
                return true;

        return false;
    }
    public boolean moveFromLeaderDeposit(PlayerDashboard player, int from, int to){
        return true;
    }
}
