package it.polimi.ingsw.controller.action.move;

import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.model.CounterTop;
import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.Storage;

public class MoveFromLeaderToDeposit extends Action {
    private int from_leader;
    private int to_deposit;
    private int number;

    public MoveFromLeaderToDeposit(int from_leader, int to_deposit, int number) {
        this.from_leader = from_leader;
        this.to_deposit = to_deposit;
        this.number = number;
    }

    //MOVE GIVEN RESOURCES FROM A LEADER DEPOSIT TO A SHELF, RESPECTING BASIC STORAGE RULES (CONTENT AND RESOURCETYPE) OR CONSIDERING THE CASE THE SHELF IS EMPTY;
    //RETURN FALSE IF THAT DEPOSIT DOESN'T HAVE THE REQUIRED RESOURCES TO MOVE;
    @Override
    public boolean useAction(PlayerDashboard player){
        CounterTop leaderDeposit = player.getArrayDeposit().get(from_leader);
        Storage storage = player.getStorage();

        if(leaderDeposit.getContent()<= number)
            return false;

        switch(to_deposit){
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