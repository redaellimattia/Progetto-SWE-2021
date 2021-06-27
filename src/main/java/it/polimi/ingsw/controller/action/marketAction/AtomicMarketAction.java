package it.polimi.ingsw.controller.action.marketAction;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.exceptions.CounterTopOverloadException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.enumeration.Resource;

import java.util.ArrayList;

public abstract class AtomicMarketAction {


    boolean checkAction(MarketMarble marble, PlayerDashboard player, Storage tempStorage, ArrayList<CounterTop> tempArrayDeposit) {return false;}

    /**
     *
     * @param marble the marble involved in the action
     * @param player the player performing the action
     * @return true if ended correctly
     */
    boolean useAction(MarketMarble marble, PlayerDashboard player, GameManager gameManager) {
        return false;
    }

    /**
     * Check if the action is legal, without updating the real user storage
     * @param tempStorage the storage copy updated during checks on different marbles, to perform controls without updating the real storage
     * @param tempArrayDeposit the deposit array copy updated during checks on different marbles, to perform controls without updating the real deposits
     * @param res the resource the user wants to store
     * @param row the number of the storage row in which the resource should be added
     *            1-3: first row, second row, third row
     *            4: special deposit (leader card ability)
     * @return true if the action is legal
     *         false if the action is illegal
     */
    public boolean checkStore(Storage tempStorage, ArrayList<CounterTop> tempArrayDeposit, Resource res, int row) {
        switch(row) {
            case 1:
                if(tempStorage.getFirstRow().getContent() == 0) {
                    if(cannotCreateNewRow(res, tempStorage)){
                        return false; // User cannot have two counterTops with the same Resource type
                    }
                    tempStorage.setFirstRow(new CounterTop(res, 1));
                    return true;
                }
                if(tempStorage.getFirstRow().getResourceType() != res) {
                    return false; // User cannot add a resource of a different type
                }
                if(tempStorage.getFirstRow().getContent() > 0) {
                    return false; // User cannot add a resource into a full counterTop
                }
                tempStorage.addToFirstRow(1);
                return true;
            case 2:
                if(tempStorage.getSecondRow().getContent() == 0) {
                    if(cannotCreateNewRow(res, tempStorage)){
                        return false; // User cannot have two counterTops with the same Resource type
                    }
                    tempStorage.setSecondRow(new CounterTop(res, 1));
                    return true;
                }
                if(tempStorage.getSecondRow().getResourceType() != res) {
                    return false;  // User cannot add a resource of a different type
                }
                if(tempStorage.getSecondRow().getContent() > 1) {
                    return false; // User cannot add a resource into a full counterTop
                }
                tempStorage.addToSecondRow(1);
                return true;
            case 3:
                if(tempStorage.getThirdRow().getContent() == 0) {
                    if(cannotCreateNewRow(res, tempStorage)){
                        return false;  // User cannot have two counterTops with the same Resource type
                    }
                    tempStorage.setThirdRow(new CounterTop(res, 1));
                    return true;
                }
                if(tempStorage.getThirdRow().getResourceType() != res) {
                    return false;  // User cannot add a resource of a different type
                }
                if(tempStorage.getThirdRow().getContent() > 2) {
                    return false; // User cannot add a resource into a full counterTop
                }
                tempStorage.addToThirdRow(1);
                return true;
            case 4:
                for(CounterTop c: tempArrayDeposit) {
                    if(c.getResourceType() == res && c.getContent() < 2) {
                        c.addContent(1);
                        return true;
                    }
                }
                return false;
            default:
                return false;
        }
    }

    /**
     *
     * @param player the player storing the resource
     * @param resource the resource to store
     * @param storageRow the number of the storage row in which the resource should be added
     *                   1-3: first row, second row, third row
     *                   4: special deposit (leader card ability)
     * @return true if ended correctly
     */
    public boolean storeResource(PlayerDashboard player, Resource resource, int storageRow) throws CounterTopOverloadException {
        switch(storageRow) {
            case 1:
                if(player.getStorage().getFirstRow().getContent() == 0) {
                    // If a counterTop is empty, we need to create a new one of the right resourceType
                    CounterTop counterTop = new CounterTop(resource, 1);
                    player.getStorage().setFirstRow(counterTop); return true;
                }
                player.getStorage().addToFirstRow(1); return true;
            case 2:
                if(player.getStorage().getSecondRow().getContent() == 0) {
                    // If a counterTop is empty, we need to create a new one of the right resourceType
                    CounterTop counterTop = new CounterTop(resource, 1);
                    player.getStorage().setSecondRow(counterTop); return true;
                }
                player.getStorage().addToSecondRow(1); return true;
            case 3:
                if(player.getStorage().getThirdRow().getContent() == 0) {
                    // If a counterTop is empty, we need to create a new one of the right resourceType
                    CounterTop counterTop = new CounterTop(resource, 1);
                    player.getStorage().setThirdRow(counterTop); return true;
                }
                player.getStorage().addToThirdRow(1); return true;
            case 4:
                player.addToDeposit(resource);
                return true;
            default:
                return false;
        }
    }

    /**
     * Check if the user already has a non-empty counterTop for this resource type
     * @param res the resource type
     * @param tempStorage the storage of the player
     * @return true if there is another counterTop of the same type
     */
    public boolean cannotCreateNewRow(Resource res, Storage tempStorage) {
        for(CounterTop c: tempStorage.getShelvesArray()) {
            if(c.getContent() != 0 && c.getResourceType().equals(res)) {
                return true;
            }
        }
        return false;
    }
}
