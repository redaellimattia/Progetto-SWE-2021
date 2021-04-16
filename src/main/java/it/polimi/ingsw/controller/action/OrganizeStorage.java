package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.model.CounterTop;
import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.Storage;

public class OrganizeStorage extends Action {

    //RETURN TRUE IF THE SHELVES ARE SWAPPED CORRECTLY AND THE STORAGE RESPECTS THE RULES FOR RESOURCETYPES;
    public boolean swapShelves(PlayerDashboard player, int from, int to) {
        if(player.getStorage().swapRows(from,to))
            return player.getStorage().checkShelves();
        return false;
    }
    //MOVE GIVEN RESOURCES FROM A LEADER DEPOSIT TO A SHELF, RESPECTING BASIC STORAGE RULES (CONTENT AND RESOURCETYPE) OR CONSIDERING THE CASE THE SHELF IS EMPTY;
    public boolean moveFromLeaderDeposit(PlayerDashboard player, CounterTop leaderDeposit, int to, int number){
        Storage storage = player.getStorage();
        switch(to){
            case 1: if(storage.getFirstRow().getContent() == 0 && number == 1) {
                CounterTop substitute = new CounterTop(leaderDeposit.getResourceType(),number);
                storage.setFirstRow(substitute);
                leaderDeposit.removeContent(number);
                return storage.checkShelves();
            }
            break;
            case 2: if((storage.getSecondRow().getContent() + number) <= 2 && storage.getSecondRow().getResourceType().equals(leaderDeposit.getResourceType()) || (storage.getSecondRow().getContent() == 0)){
                CounterTop substitute = new CounterTop(leaderDeposit.getResourceType(),number+storage.getSecondRow().getContent());
                storage.setSecondRow(substitute);
                leaderDeposit.removeContent(number);
                return storage.checkShelves();
            }
            break;
            case 3: if(((storage.getThirdRow().getContent() + number) <= 3 && storage.getThirdRow().getResourceType().equals(leaderDeposit.getResourceType())) || (storage.getThirdRow().getContent() == 0)){
                CounterTop substitute = new CounterTop(leaderDeposit.getResourceType(),number+storage.getThirdRow().getContent());
                storage.setThirdRow(substitute);
                leaderDeposit.removeContent(number);
                return storage.checkShelves();
            }
            break;
        }
        return false;
    }
    //FOR EACH CASE CHECK IF: THE SHELVES HAS ENOUGH RESOURCES TO MOVE (CONTENT >= NUMBER), THE NUMBER+ CONTENT OF ARRAY IS MAX 2 AND THAT RESOURCES ARE COMPATIBLE;
    public boolean moveFromDeposit(PlayerDashboard player, CounterTop leaderDeposit, int from, int number){
        Storage storage = player.getStorage();
        switch(from){
            case 1: if( storage.getFirstRow().getContent() >= number &&(number+ leaderDeposit.getContent()) <= 2 && storage.getFirstRow().getResourceType().equals(leaderDeposit.getResourceType())) {
                leaderDeposit.addContent(number);
                storage.getFirstRow().removeContent(number);
                return storage.checkShelves();
            }
                break;
            case 2: if( storage.getSecondRow().getContent() >= number &&(number+ leaderDeposit.getContent()) <= 2 && storage.getSecondRow().getResourceType().equals(leaderDeposit.getResourceType())) {
                leaderDeposit.addContent(number);
                storage.getSecondRow().removeContent(number);
                return storage.checkShelves();
            }
                break;
            case 3: if( storage.getThirdRow().getContent() >= number &&(number+ leaderDeposit.getContent()) <= 2 && storage.getThirdRow().getResourceType().equals(leaderDeposit.getResourceType())) {
                leaderDeposit.addContent(number);
                storage.getThirdRow().removeContent(number);
                return storage.checkShelves();
            }
                break;
        }
        return false;
    }
}
