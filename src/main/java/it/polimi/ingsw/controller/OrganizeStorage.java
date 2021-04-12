package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.CounterTop;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerDashboard;

public class OrganizeStorage extends Action{

    /*public boolean swapShelves(PlayerDashboard player, int from, int to){
        CounterTop[] shelves = player.getStorage().getShelvesArray();
        switch (to) {
            case 1:
                if(shelves[from].getContent() <= 1) {
                    player.getStorage().swap(from,to);
                }
                return true;
            case 2:
                if(shelves[from].getContent() <= 2 && )
        }


    }*/
    //RETURN TRUE IF THE SHELVES ARE SWAPPED CORRECTLY AND THE STORAGE RESPECTS THE SAME RULES;
    public boolean swapShelves(PlayerDashboard player, int from, int to) {
        CounterTop[] shelves = player.getStorage().getShelvesArray();
        CounterTop temp = shelves[to];
        if(player.getStorage().setThisRow(shelves[from],to) && player.getStorage().setThisRow(temp,from))
            if(super.checkShelves(player.getStorage()))
                return true;

        return false;
    }
}
