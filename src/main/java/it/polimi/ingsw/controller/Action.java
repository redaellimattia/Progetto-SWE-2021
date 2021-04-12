package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.enumeration.Resource;

public abstract class Action {
    //TRUE IF BOTH STORAGE AND CHEST PAY ARE DONE CORRECTLY
    public boolean deleteRes(ResourceCount storageCount, ResourceCount chestCount, PlayerDashboard player){
        boolean storageDone = false,chestDone = false;
        if(storageCount != null)
            storageDone = removeFromStorage(storageCount, player.getStorage());
        if(chestCount != null)
            chestDone = removeFromChest(chestCount,player.getChest());

        return storageDone&&chestDone;
    }
    //REMOVING RESOURCES FROM STORAGE || USED 2 ARRAY OF SUPPORT TO SCAN THE RESOURCES IN THE COST AND THE SHELVES IN THE STORAGE;
    public boolean removeFromStorage(ResourceCount storageCount, Storage storage){
        if(!storage.readStorage().hasMoreOrEqualsResources(storageCount))
            return false;

        Resource[] resources = new Resource[]{ Resource.COIN, Resource.ROCK, Resource.SERVANT, Resource.SHIELD};
        for (Resource res : resources) { //SCAN EVERY RESOURCE IN THE STORAGECOST
            int valueToRemove = res.get(storageCount);
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
    public boolean removeFromChest(ResourceCount chestCount,ResourceCount chest){
        if(!chest.hasMoreOrEqualsResources(chestCount))
            return false;
        chest.subCounts(chestCount);
        return true;
    }
    //RETURN THE TOTAL OF RESOURCES THAT THE PLAYER IS USING TO PAY;
    public ResourceCount getTotal(ResourceCount storage, ResourceCount chest){
        ResourceCount total = new ResourceCount(0,0,0,0,0);
        total.sumCounts(storage);
        total.sumCounts(chest);
        return total;
    }

    //CHECK IF EVERY COUNTERTOP OF THE STORAGE HAS A DIFFERENT RESOURCETYPE
    public boolean checkShelves(Storage storage){
        if(!storage.getFirstRow().getResourceType().equals(storage.getSecondRow().getResourceType()) &&
                !storage.getFirstRow().getResourceType().equals(storage.getThirdRow().getResourceType()) &&
                    !storage.getSecondRow().getResourceType().equals(storage.getThirdRow().getResourceType()))
                        return true;
        return false;
    }
    public boolean endAction(){
        return false;
    }
}
