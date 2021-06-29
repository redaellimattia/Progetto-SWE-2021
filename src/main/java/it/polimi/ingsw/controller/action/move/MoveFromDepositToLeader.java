package it.polimi.ingsw.controller.action.move;

import it.polimi.ingsw.controller.PlayerTurnManager;
import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.exceptions.action.WrongResourcesMovedException;
import it.polimi.ingsw.model.CounterTop;
import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.Storage;

public class MoveFromDepositToLeader extends Action {
    private final int from_deposit;
    private final int to_leader;
    private final int number;

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
     * For each case check if: the shelves has enough resources to move (content >= number), the number + content of array is max 2 and that resources are compatible
     * @param player player that wanted to switch resources
     * @param turnManager turnManager
     */
    @Override
    public boolean useAction(PlayerDashboard player, PlayerTurnManager turnManager){
        Storage storage = player.getStorage();
        CounterTop leaderDeposit;
        if(player.getArrayDeposit().size()>1)
            leaderDeposit = player.getArrayDeposit().get(to_leader-1);
        else
            leaderDeposit = player.getArrayDeposit().get(0);
        switch(from_deposit){
            case 1: if(storage.getFirstRow().getContent() >= number &&(number+ leaderDeposit.getContent()) <= 2 && storage.getFirstRow().getResourceType().equals(leaderDeposit.getResourceType())) {
                CounterTop substitute = new CounterTop(leaderDeposit.getResourceType(),0);
                leaderDeposit.addContent(number);
                storage.setFirstRow(substitute);
                player.updateArrayDeposits();
                return true;
            }
            else
                throw new WrongResourcesMovedException(player);
            case 2: if( storage.getSecondRow().getContent() >= number &&(number+ leaderDeposit.getContent()) <= 2 && storage.getSecondRow().getResourceType().equals(leaderDeposit.getResourceType())) {
                CounterTop substitute = new CounterTop(storage.getSecondRow().getResourceType(),storage.getSecondRow().getContent() - number);
                leaderDeposit.addContent(number);
                storage.setSecondRow(substitute);
                player.updateArrayDeposits();
                return true;
            }
            else
                throw new WrongResourcesMovedException(player);
            case 3: if( storage.getThirdRow().getContent() >= number &&(number+ leaderDeposit.getContent()) <= 2 && storage.getThirdRow().getResourceType().equals(leaderDeposit.getResourceType())) {
                CounterTop substitute = new CounterTop(storage.getThirdRow().getResourceType(),storage.getThirdRow().getContent() - number);
                leaderDeposit.addContent(number);
                storage.setThirdRow(substitute);
                player.updateArrayDeposits();
                return true;
            }
            else
                throw new WrongResourcesMovedException(player);
            default: return false;
        }
    }

}
