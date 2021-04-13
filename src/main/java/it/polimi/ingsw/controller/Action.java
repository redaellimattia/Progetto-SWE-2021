package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.enumeration.Resource;

public abstract class Action {
    //TRUE IF BOTH STORAGE AND CHEST PAY ARE DONE CORRECTLY
    public boolean deleteRes(ResourceCount storageCount, ResourceCount chestCount, PlayerDashboard player){
        boolean storageDone = false,chestDone = false;
        if(storageCount != null)
            storageDone = removeFromStorage(storageCount,player.getStorage());
        if(chestCount != null)
            chestDone = removeFromChest(chestCount,player.getChest());

        if(storageCount!=null&&chestCount!=null)
            return storageDone&&chestDone;
        if(storageCount!=null)
            return storageDone;
        if(chestCount!=null)
            return chestDone;
        return false;
    }
    //REMOVING RESOURCES FROM STORAGE || USED 2 ARRAY OF SUPPORT TO SCAN THE RESOURCES IN THE COST AND THE SHELVES IN THE STORAGE;
    public boolean removeFromStorage(ResourceCount storageCost, Storage storage){
        if(!storage.readStorage().hasMoreOrEqualsResources(storageCost))
            return false;

        Resource[] resources = new Resource[]{ Resource.COIN, Resource.ROCK, Resource.SERVANT, Resource.SHIELD};
        for (Resource res : resources) { //SCAN EVERY RESOURCE IN THE STORAGECOST
            int valueToRemove = res.get(storageCost);
            if(valueToRemove != 0){ //THIS RESOURCE IS ACTUALLY REQUIRED
                for (CounterTop counter : storage.getShelvesArray()) { //SCAN EVERY SHELVES
                    if(counter.getResourceType().equals(res)) //IF THE SHELF RESOURCETYPE MATCHES THE REQUESTED RESOURCE
                        counter.removeContent(valueToRemove); //THEN REMOVE FROM THE SHELF CONTENT
                }
            }
        }
        return true;
    }
    //REMOVING RESOURCES FROM CHEST
    public boolean removeFromChest(ResourceCount chestCost,ResourceCount chest){
        if(!chest.hasMoreOrEqualsResources(chestCost))
            return false;
        chest.subCounts(chestCost);
        return true;
    }


    public boolean endAction(){
        return false;
    }
}
