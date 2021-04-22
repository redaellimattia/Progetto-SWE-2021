package it.polimi.ingsw.controller.action.move;

import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.exceptions.action.WrongResourcesMovedException;
import it.polimi.ingsw.model.CounterTop;
import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.Storage;

public class MoveFromDepositToLeader extends Action {
    private int to_leader;
    private int from_deposit;
    private int number;

    /**
     *
     * @param to_leader position of the target LeaderDeposit on which the resources will be placed
     * @param from_deposit position of the CounterTop from which the resources will be taken
     * @param number resources interested in the swap
     */
    public MoveFromDepositToLeader(int to_leader, int from_deposit, int number) {
        this.to_leader = to_leader;
        this.from_deposit = from_deposit;
        this.number = number;
    }

    /**
     *
     * @param player player that wanted to switch resources
     * @return true if the action is done correctly, false otherwise
     */
    //FOR EACH CASE CHECK IF: THE SHELVES HAS ENOUGH RESOURCES TO MOVE (CONTENT >= NUMBER), THE NUMBER+ CONTENT OF ARRAY IS MAX 2 AND THAT RESOURCES ARE COMPATIBLE;
    @Override
    public boolean useAction(PlayerDashboard player){
        Storage storage = player.getStorage();
        CounterTop leaderDeposit = player.getArrayDeposit().get(to_leader);
        switch(from_deposit){
            case 1: if( storage.getFirstRow().getContent() >= number &&(number+ leaderDeposit.getContent()) <= 2 && storage.getFirstRow().getResourceType().equals(leaderDeposit.getResourceType())) {
                leaderDeposit.addContent(number);
                storage.getFirstRow().removeContent(number);
                //return storage.checkShelves();
            }
            else
                throw new WrongResourcesMovedException();
                break;
            case 2: if( storage.getSecondRow().getContent() >= number &&(number+ leaderDeposit.getContent()) <= 2 && storage.getSecondRow().getResourceType().equals(leaderDeposit.getResourceType())) {
                leaderDeposit.addContent(number);
                storage.getSecondRow().removeContent(number);
                //return storage.checkShelves();
            }
            else
                throw new WrongResourcesMovedException();
                break;
            case 3: if( storage.getThirdRow().getContent() >= number &&(number+ leaderDeposit.getContent()) <= 2 && storage.getThirdRow().getResourceType().equals(leaderDeposit.getResourceType())) {
                leaderDeposit.addContent(number);
                storage.getThirdRow().removeContent(number);
                //return storage.checkShelves();
            }
            else
                throw new WrongResourcesMovedException();
                break;
        }
        return false;
    }

}
