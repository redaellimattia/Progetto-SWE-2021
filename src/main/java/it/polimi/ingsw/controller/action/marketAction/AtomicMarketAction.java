package it.polimi.ingsw.controller.action.marketAction;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.exceptions.CounterTopOverloadException;
import it.polimi.ingsw.exceptions.action.NoAdditionalDepositException;
import it.polimi.ingsw.exceptions.action.WrongCounterTopException;
import it.polimi.ingsw.model.CounterTop;
import it.polimi.ingsw.model.MarketMarble;
import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.enumeration.Resource;

public abstract class AtomicMarketAction {

    /**
     *
     * @param marble the marble involved in the action
     * @param player the player performing the action
     * @return true if ended correctly
     *         false if the action was illegal
     */
    boolean useAction(MarketMarble marble, PlayerDashboard player, GameManager gameManager) {
        return false;
    }

    /**
     *
     * @param player the player storing the resource
     * @param resource the resource to store
     * @param storageRow the number of the storage row in which the resource should be added
     *                   1-3: first row, second row, third row
     *                   4: special deposit (leader card ability)
     * @return true if ended correctly
     *         false if the action was illegal
     */
    public boolean storeResource(PlayerDashboard player, Resource resource, int storageRow) throws CounterTopOverloadException {
        switch(storageRow) {
            case 1:
                if(player.getStorage().getFirstRow().getContent() == 0) {
                    // We need to check if there isn't another counterTop already in use for this Resource type
                    if(!canCreateNewRow(resource, player)){
                        throw new WrongCounterTopException(resource, player); // User cannot have two counterTops with the same Resource type
                    }
                    // If a counterTop is empty, we need to create a new one of the right resourceType
                    CounterTop counterTop = new CounterTop(resource, 1);
                    player.getStorage().setFirstRow(counterTop); return true;
                    //player.getStorage().getFirstRow().setResourceType(resource);
                }
                if(player.getStorage().getFirstRow().getResourceType() != resource) {
                    throw new WrongCounterTopException(resource, player); // User cannot add a resource of a different type
                }
                if(player.getStorage().getFirstRow().getContent() > 0) {
                    throw new CounterTopOverloadException(player); // User cannot add a resource into a full counterTop
                }
                player.getStorage().addToFirstRow(1); return true;
            case 2:
                if(player.getStorage().getSecondRow().getContent() == 0) {
                    // We need to check if there isn't another counterTop already in use for this Resource type
                    if(!canCreateNewRow(resource, player)){
                        throw new WrongCounterTopException(resource, player); // User cannot have two counterTops with the same Resource type
                    }
                    // If a counterTop is empty, we need to create a new one of the right resourceType
                    CounterTop counterTop = new CounterTop(resource, 1);
                    player.getStorage().setSecondRow(counterTop); return true;
                    //player.getStorage().getSecondRow().setResourceType(resource);
                }
                if(player.getStorage().getSecondRow().getResourceType() != resource) {
                    throw new WrongCounterTopException(resource, player); // User cannot add a resource of a different type
                }
                if(player.getStorage().getSecondRow().getContent() > 1) {
                    throw new CounterTopOverloadException(player); // User cannot add a resource into a full counterTop
                }
                player.getStorage().addToSecondRow(1); return true;
            case 3:
                if(player.getStorage().getThirdRow().getContent() == 0) {
                    // We need to check if there isn't another counterTop already in use for this Resource type
                    if(!canCreateNewRow(resource, player)){
                        throw new WrongCounterTopException(resource, player); // User cannot have two counterTops with the same Resource type
                    }
                    // If a counterTop is empty, we need to create a new one of the right resourceType
                    CounterTop counterTop = new CounterTop(resource, 1);
                    player.getStorage().setThirdRow(counterTop); return true;
                    //player.getStorage().getThirdRow().setResourceType(resource);
                }
                if(player.getStorage().getThirdRow().getResourceType() != resource) {
                    throw new WrongCounterTopException(resource, player); // User cannot add a resource of a different type
                }
                if(player.getStorage().getThirdRow().getContent() > 2) {
                    throw new CounterTopOverloadException(player); // User cannot add a resource into a full counterTop
                }
                player.getStorage().addToThirdRow(1); return true;
            case 4:
                try {
                    player.addToDeposit(resource);
                    return true;
                }
                catch (CounterTopOverloadException e) {
                    throw new NoAdditionalDepositException(resource); // User cannot add a resource in an additional deposit if it is full or not present
                }
            default:
                throw new IllegalArgumentException();
        }
    }

    /**
     * Check if the user already has a non-empty counterTop for this resource type
     * @param res the resource type
     * @param player the player that owns the storage
     * @return true if there is NOT another counterTop of the same type
     */
    public boolean canCreateNewRow(Resource res, PlayerDashboard player) {
        for(CounterTop c: player.getStorage().getShelvesArray()) {
            if(c.getContent() != 0 && c.getResourceType().equals(res)) {
                return false;
            }
        }
        return true;
    }
}
