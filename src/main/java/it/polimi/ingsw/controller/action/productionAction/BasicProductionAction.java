package it.polimi.ingsw.controller.action.productionAction;

import it.polimi.ingsw.controller.PlayerTurnManager;
import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.exceptions.action.PaymentFailedException;
import it.polimi.ingsw.exceptions.action.WrongResourceException;
import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.enumeration.Resource;

public class BasicProductionAction extends Action {
    private final Resource res;
    private final ResourceCount storageCount ;
    private final ResourceCount chestCount ;

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
     * Receiving cost chosen by the player, and count of resources from the storage,count of resources from the chest,player,and the resource as the chosen output
     *
     * @param player player that is doing the action
     * @param turnManager turnManager
     */
    @Override
    public boolean useAction(PlayerDashboard player, PlayerTurnManager turnManager) {
        if(res.equals(Resource.FAITH))
            throw new WrongResourceException("Faith",player,turnManager);

        //If Sum of storageCount and ChestCount != 2 OR deleteRes goes wrong then return false
        int total = 0;
        ResourceCount totalCount = ResourceCount.getTotal(storageCount,chestCount);
        Resource[] resources = new Resource[]{ Resource.COIN, Resource.ROCK, Resource.SERVANT, Resource.SHIELD};
        for (Resource r : resources)
            total += r.get(totalCount);
        if(total!=2||!deleteRes(storageCount,chestCount,player)||totalCount.getFaith()!=0)
            throw new PaymentFailedException(player,turnManager);

        ResourceCount output = new ResourceCount(0,0,0,0,0);
        res.add(output,1);
        player.incrementBufferProduction(output);
        return true;
    }

}
