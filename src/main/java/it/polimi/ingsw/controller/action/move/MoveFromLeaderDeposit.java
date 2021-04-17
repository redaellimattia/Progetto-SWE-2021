package it.polimi.ingsw.controller.action.move;

import it.polimi.ingsw.controller.Parameter;
import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.model.CounterTop;
import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.Storage;

public class MoveFromLeaderDeposit extends Action {
    private CounterTop leaderDeposit;
    private int to;
    private int number;

    public MoveFromLeaderDeposit(CounterTop leaderDeposit, int to, int number) {
        this.leaderDeposit = leaderDeposit;
        this.to = to;
        this.number = number;
    }

    //MOVE GIVEN RESOURCES FROM A LEADER DEPOSIT TO A SHELF, RESPECTING BASIC STORAGE RULES (CONTENT AND RESOURCETYPE) OR CONSIDERING THE CASE THE SHELF IS EMPTY;
    @Override
    public boolean useAction(PlayerDashboard player){

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
}