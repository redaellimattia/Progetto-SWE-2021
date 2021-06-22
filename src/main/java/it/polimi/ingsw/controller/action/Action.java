package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.controller.PlayerTurnManager;
import it.polimi.ingsw.controller.action.productionAction.BasicProductionAction;
import it.polimi.ingsw.controller.action.productionAction.DevCardProductionAction;
import it.polimi.ingsw.controller.action.productionAction.LeaderCardProductionAction;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.enumeration.Resource;

import java.util.ArrayList;

public abstract class Action {
    public boolean useAction(PlayerDashboard player, PlayerTurnManager turnManager) {return false;}
    public void endAction(PlayerDashboard player){}
    public boolean addBasicProduction(BasicProductionAction basicProduction,PlayerDashboard player,PlayerTurnManager turnManager) {return false;}
    public boolean addDevCardProduction(DevCardProductionAction devCardProduction,PlayerDashboard player,PlayerTurnManager turnManager) {return false;}
    public boolean addLeaderCardProduction(LeaderCardProductionAction leaderCardProduction,PlayerDashboard player,PlayerTurnManager turnManager) {return false;}

    /**
     * Payment
     * @param storageCount payment from storage
     * @param chestCount payment from chest
     * @param player player that is paying
     * @return true if both storage and chest pay are done correctly
     */
    public boolean deleteRes(ResourceCount storageCount, ResourceCount chestCount, PlayerDashboard player){
        boolean storageDone = false,chestDone = false;
        int storageToInt = ResourceCount.resCountToInt(storageCount);
        int chestToInt = ResourceCount.resCountToInt(chestCount);
        if(storageToInt != 0)
            storageDone = removeFromStorage(storageCount,player.getStorage());
        if(chestToInt != 0)
            chestDone = removeFromChest(chestCount,player);

        if(storageToInt != 0 && chestToInt != 0)
            return storageDone&&chestDone;
        if(storageToInt != 0)
            return storageDone;
        if(chestToInt != 0)
            return chestDone;
        return false;
    }
    /**
     * Removing resources from storage || used 2 array of support to scan the resources in the cost and the shelves in the storage
     *
     * @param storageCost resources the player wants to use from the storage
     * @param storage storage of the player that is paying
     * @return true if paid
     */
    public boolean removeFromStorage(ResourceCount storageCost, Storage storage){
        if(!storage.readStorage().hasMoreOrEqualsResources(storageCost))
            return false;

        Resource[] resources = new Resource[]{ Resource.COIN, Resource.ROCK, Resource.SERVANT, Resource.SHIELD};
        for (Resource res : resources) { //SCAN EVERY RESOURCE IN THE STORAGECOST
            int valueToRemove = res.get(storageCost);
            if(valueToRemove != 0){ //THIS RESOURCE IS ACTUALLY REQUIRED
                ArrayList<CounterTop> shelves = storage.getShelvesArray();
                for (int i=0;i<=2;i++){ //SCAN EVERY SHELF
                    CounterTop counter = shelves.get(i);
                    if(counter.getResourceType().equals(res)&&counter.getContent()>0) {//IF THE SHELF RESOURCETYPE MATCHES THE REQUESTED RESOURCE
                    //counter.removeContent(valueToRemove); //THEN REMOVE FROM THE SHELF CONTENT
                        CounterTop newCounter = new CounterTop(counter.getResourceType(),counter.getContent()-valueToRemove);
                        switch(i){
                            case 0: storage.setFirstRow(newCounter);
                            break;
                            case 1: storage.setSecondRow(newCounter);
                            break;
                            case 2: storage.setThirdRow(newCounter);
                            break;
                        }
                    }
                }
            }
        }
        return true;
    }


    /**
     * Removing resources from chest
     *
     * @param chestCost resources the player wants to use from the chest
     * @param player player that is paying
     * @return true if paid
     */
    public boolean removeFromChest(ResourceCount chestCost,PlayerDashboard player){
        ResourceCount chest = player.getChest();
        if(!chest.hasMoreOrEqualsResources(chestCost))
            return false;
        player.subtractFromChest(chestCost);
        return true;
    }
}
