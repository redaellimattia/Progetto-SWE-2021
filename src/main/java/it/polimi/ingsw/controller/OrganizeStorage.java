package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.CounterTop;
import it.polimi.ingsw.model.PlayerDashboard;

public class OrganizeStorage extends Action{

    //RETURN TRUE IF THE SHELVES ARE SWAPPED CORRECTLY AND THE STORAGE RESPECTS THE SAME RULES;
    public boolean swapShelves(PlayerDashboard player, int from, int to) {
        CounterTop[] shelves = player.getStorage().getShelvesArray();
        CounterTop temp = shelves[to];
        if(player.getStorage().setThisRow(shelves[from],to) && player.getStorage().setThisRow(temp,from)) //contemporaneamente forse non funziona;
            if(player.getStorage().checkShelves())
                return true;
            else{//IF FOR SOME ABSURD REASON THE RULES ARE NOT FOLLOWED, NEED TO GO BACK TO NORMAL;
                player.getStorage().setThisRow(shelves[to],from);
                player.getStorage().setThisRow(temp,to);
                return false;
            }
        return false;
    }
    public boolean moveFromLeaderDeposit(PlayerDashboard player, int from, int to){
        return true;
    }
}
