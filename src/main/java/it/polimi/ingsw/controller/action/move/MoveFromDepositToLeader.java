package it.polimi.ingsw.controller.action.move;

import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.model.CounterTop;
import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.Storage;

public class MoveFromDepositToLeader extends Action {
    private CounterTop leaderDeposit;
    private int from;
    private int number;

    public MoveFromDepositToLeader(CounterTop leaderDeposit, int from, int number) {
        this.leaderDeposit = leaderDeposit;
        this.from = from;
        this.number = number;
    }

    //FOR EACH CASE CHECK IF: THE SHELVES HAS ENOUGH RESOURCES TO MOVE (CONTENT >= NUMBER), THE NUMBER+ CONTENT OF ARRAY IS MAX 2 AND THAT RESOURCES ARE COMPATIBLE;
    @Override
    public boolean useAction(PlayerDashboard player){
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
