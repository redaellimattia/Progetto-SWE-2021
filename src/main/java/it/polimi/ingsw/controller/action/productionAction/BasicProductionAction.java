package it.polimi.ingsw.controller.action.productionAction;

import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.enumeration.Resource;

public class BasicProductionAction extends Action {
    private Resource res;
    private ResourceCount storageCount ;
    private ResourceCount chestCount ;

    /**
     *
     * @param res resource chosen by the player as the output
     * @param storageCount amount of resources in the Storage, that the player wants to pay with, can be null
     * @param chestCount amount of resources in the Chest, that the player wants to pay with, can be null
     */
    public BasicProductionAction(Resource res,ResourceCount storageCount,ResourceCount chestCount) { //Will be called in PlayerTurnManager
        this.res = res;
        this.storageCount = storageCount;
        this.chestCount = chestCount;
    }

    /**
     *
     * @param player player that is doing the action
     * @return true if ended correctly
     */
    //BASICPRODUCTION
    //RECEIVING COST CHOSEN BY THE PLAYER, AND COUNT OF RESOURCES FROM THE STORAGE,COUNT OF RESOURCES FROM THE CHEST,PLAYER,AND THE RESOURCE AS THE CHOSEN OUTPUT
    @Override
    public boolean useAction(PlayerDashboard player){
        if(res.equals(Resource.FAITH))
            return false;

        //If Sum of storageCount and ChestCount != 2 OR deleteRes goes wrong then return false
        int total = 0;
        ResourceCount totalCount = ResourceCount.getTotal(storageCount,chestCount);
        Resource[] resources = new Resource[]{ Resource.COIN, Resource.ROCK, Resource.SERVANT, Resource.SHIELD};
        for (Resource r : resources)
            total += r.get(totalCount);
        if(!deleteRes(storageCount,chestCount,player)||total!=2||totalCount.getFaith()!=0)
            return false;

        ResourceCount output = new ResourceCount(0,0,0,0,0); //ResourceCount with 1 Faith
        res.add(output,1);
        player.incrementBufferProduction(output);
        return true;
    }

    /**
     *
     * @param player player that is ending the action
     */
    //ADD THE RESOURCES OBTAINED FROM THE PRODUCTIONS TO THE PLAYER AND THEN RESET THE BUFFER;
    @Override
    public void endAction(PlayerDashboard player) {
        player.emptyBufferProduction();
    }
}
