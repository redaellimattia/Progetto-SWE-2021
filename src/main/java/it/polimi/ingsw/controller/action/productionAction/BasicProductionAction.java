package it.polimi.ingsw.controller.action.productionAction;

import it.polimi.ingsw.controller.Parameter;
import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.enumeration.Resource;

public class BasicProductionAction extends Action {
    private ResourceCount bufferOutput;
    private Resource res;
    private ResourceCount storageCount ;
    private ResourceCount chestCount ;

    public BasicProductionAction(Resource res,ResourceCount storageCount,ResourceCount chestCount) { //Will be called in PlayerTurnManager
        this.bufferOutput = new ResourceCount(0,0,0,0,0);
        this.res = res;
        this.storageCount = storageCount;
        this.chestCount = chestCount;
    }

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
        bufferOutput.sumCounts(output);
        return true;
    }

    //ADD THE RESOURCES OBTAINED FROM THE PRODUCTIONS TO THE PLAYER AND THEN RESET THE BUFFER;
    @Override
    public void endAction(PlayerDashboard player) {
        int faith = bufferOutput.getFaith();
        if(faith!=0)
            player.updatePathPosition(faith);
        bufferOutput.removeFaith(faith);
        player.getChest().sumCounts(bufferOutput);
        bufferOutput = new ResourceCount(0,0,0,0,0);
    }
}
