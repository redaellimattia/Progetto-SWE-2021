package it.polimi.ingsw.controller.action.marketAction;

import it.polimi.ingsw.exceptions.CounterTopOverloadException;
import it.polimi.ingsw.exceptions.action.NoAdditionalDepositException;
import it.polimi.ingsw.exceptions.action.WrongCounterTopException;
import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.enumeration.Resource;

public class StoreResource {

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
    // Store a resource from GetResource or ConvertWhiteMarble
    static boolean storeResource(PlayerDashboard player, Resource resource, int storageRow) throws CounterTopOverloadException {
        switch(storageRow) {
            case 1:
                if(player.getStorage().getFirstRow().getContent() == 0) {
                    // If a counterTop is empty, we need to set the new resource type
                    player.getStorage().getFirstRow().setResourceType(resource);
                }
                if(player.getStorage().getFirstRow().getResourceType() != resource) {
                    throw new WrongCounterTopException(resource); // User cannot add a resource of a different type
                }
                if(player.getStorage().getFirstRow().getContent() > 0) {
                    throw new CounterTopOverloadException(); // User cannot add a resource into a full counterTop
                }
                player.getStorage().addToFirstRow(1); return true;
            case 2:
                if(player.getStorage().getSecondRow().getContent() == 0) {
                    // If a counterTop is empty, we need to set the new resource type
                    player.getStorage().getSecondRow().setResourceType(resource);
                }
                if(player.getStorage().getSecondRow().getResourceType() != resource) {
                    throw new WrongCounterTopException(resource); // User cannot add a resource of a different type
                }
                if(player.getStorage().getSecondRow().getContent() > 1) {
                    throw new CounterTopOverloadException(); // User cannot add a resource into a full counterTop
                }
                player.getStorage().addToSecondRow(1); return true;
            case 3:
                if(player.getStorage().getThirdRow().getContent() == 0) {
                    // If a counterTop is empty, we need to set the new resource type
                    player.getStorage().getThirdRow().setResourceType(resource);
                }
                if(player.getStorage().getThirdRow().getResourceType() != resource) {
                    throw new WrongCounterTopException(resource); // User cannot add a resource of a different type
                }
                if(player.getStorage().getThirdRow().getContent() > 2) {
                    throw new CounterTopOverloadException(); // User cannot add a resource into a full counterTop
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
}
