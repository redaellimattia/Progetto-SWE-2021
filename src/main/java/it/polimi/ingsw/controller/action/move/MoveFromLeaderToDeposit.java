package it.polimi.ingsw.controller.action.move;

import it.polimi.ingsw.controller.PlayerTurnManager;
import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.exceptions.CounterTopOverloadException;
import it.polimi.ingsw.exceptions.action.WrongResourcesMovedException;
import it.polimi.ingsw.model.CounterTop;
import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.Storage;
import it.polimi.ingsw.model.enumeration.Resource;

public class MoveFromLeaderToDeposit extends Action {
    private final int from_leader;
    private final int to_deposit;
    private final int number;

    /**
     *
     * @param from_leader position of the LeaderDeposit from which the resources will be taken
     * @param to_deposit position of the target CounterTop on which the resources will be placed
     * @param number number of resources switched
     */
    public MoveFromLeaderToDeposit(int from_leader, int to_deposit, int number) {
        this.from_leader = from_leader;
        this.to_deposit = to_deposit;
        this.number = number;
    }

    /**
     * Move given resources from a leader deposit to a shelf, respecting basic storage rules (content and resourcetype)
     * or considering the case the shelf is empty, return false if that deposit doesn't have the required resources to move
     * @param player player that wanted to switch resources
     * @param turnManager turnManager
     */
    @Override
    public boolean useAction(PlayerDashboard player, PlayerTurnManager turnManager){
        CounterTop leaderDeposit;
        if(player.getArrayDeposit().size()>1)
            leaderDeposit = player.getArrayDeposit().get(from_leader-1);
        else
            leaderDeposit = player.getArrayDeposit().get(0);
        Storage storage = player.getStorage();

        if(leaderDeposit.getContent()< number)
            throw new WrongResourcesMovedException(player);

        switch(to_deposit){
            case 1: if(storage.getFirstRow().getContent() == 0 && number == 1 ) {
                if(checkStorageRows(1,storage,leaderDeposit.getResourceType())) {
                    CounterTop substitute = new CounterTop(leaderDeposit.getResourceType(), number);
                    try {
                        storage.setFirstRow(substitute);
                    } catch (CounterTopOverloadException ignored) {
                    }
                    leaderDeposit.removeContent(number);
                    player.updateArrayDeposits();
                    return true;
                }
                else
                    throw new WrongResourcesMovedException(player);
            }
            else
                throw new WrongResourcesMovedException(player);
            case 2: if((storage.getSecondRow().getContent() + number) <= 2 && storage.getSecondRow().getResourceType().equals(leaderDeposit.getResourceType()) || (storage.getSecondRow().getContent() == 0)){
                if(checkStorageRows(2,storage,leaderDeposit.getResourceType())) {
                    CounterTop substitute = new CounterTop(leaderDeposit.getResourceType(), number + storage.getSecondRow().getContent());
                    try {
                        storage.setSecondRow(substitute);
                    } catch (CounterTopOverloadException ignored) {
                    }
                    leaderDeposit.removeContent(number);
                    player.updateArrayDeposits();
                    return true;
                }
                else
                    throw new WrongResourcesMovedException(player);
            }
            else
                throw new WrongResourcesMovedException(player);
            case 3: if(((storage.getThirdRow().getContent() + number) <= 3 && storage.getThirdRow().getResourceType().equals(leaderDeposit.getResourceType())) || (storage.getThirdRow().getContent() == 0)){
                if(checkStorageRows(3,storage,leaderDeposit.getResourceType())) {
                    CounterTop substitute = new CounterTop(leaderDeposit.getResourceType(), number + storage.getThirdRow().getContent());
                    try {
                        storage.setThirdRow(substitute);
                    } catch (CounterTopOverloadException ignored) {
                    }
                    leaderDeposit.removeContent(number);
                    player.updateArrayDeposits();
                    return true;
                }
                else
                    throw new WrongResourcesMovedException(player);
            }
            else
                throw new WrongResourcesMovedException(player);
            default: return false;
        }
    }

    /**
     * Integrity check on the rules of the storage
     * @param deposit target deposit
     * @param storage player's storage
     * @param resourceType type of the resource moved
     * @return true if the chosen position is valid given the condition of the storage
     */
    public boolean checkStorageRows(int deposit, Storage storage, Resource resourceType){
        switch (deposit){
            case 1:
                if((storage.getSecondRow().getResourceType() == resourceType && storage.getSecondRow().getContent() != 0) ||
                        (storage.getThirdRow().getResourceType() == resourceType && storage.getThirdRow().getContent() != 0))
                    return false;
                break;
            case 2:
                if((storage.getFirstRow().getResourceType() == resourceType && storage.getFirstRow().getContent() != 0) ||
                        (storage.getThirdRow().getResourceType() == resourceType && storage.getThirdRow().getContent() != 0))
                    return false;
                break;
            case 3:
                if((storage.getFirstRow().getResourceType() == resourceType && storage.getFirstRow().getContent() != 0) ||
                        (storage.getSecondRow().getResourceType() == resourceType && storage.getSecondRow().getContent() != 0))
                    return false;
                break;
            default:
                return true;
        }
        return true;
    }
}