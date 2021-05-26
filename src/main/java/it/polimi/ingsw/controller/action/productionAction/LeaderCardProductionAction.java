package it.polimi.ingsw.controller.action.productionAction;

import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.exceptions.action.CardNotExistsException;
import it.polimi.ingsw.exceptions.action.PaymentFailedException;
import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.enumeration.Resource;

public class LeaderCardProductionAction extends Action {
    LeaderCard card;
    ResourceCount storageCount;
    ResourceCount chestCount;
    Resource res;

    /**
     *
     * @param card LeaderCard chosen by the player to use its production
     * @param storageCount amount of resources in the Storage, that the player wants to pay with, can be null
     * @param chestCount amount of resources in the Chest, that the player wants to pay with, can be null
     * @param res resource chosen as output
     */
    public LeaderCardProductionAction(LeaderCard card, ResourceCount storageCount, ResourceCount chestCount, Resource res) {
        this.card = card;
        this.storageCount = storageCount;
        this.chestCount = chestCount;
        this.res = res;
    }

    /**
     * RECEIVING LEADER CARD,COUNT OF RESOURCES FROM THE STORAGE,COUNT OF RESOURCES FROM THE CHEST,PLAYER,AND THE RESOURCE AS THE CHOSEN OUTPUT
     *
     * @param player player that is doing the action
     */
    @Override
    public void useAction(PlayerDashboard player) {
        int storageToInt = ResourceCount.resCountToInt(storageCount);
        int chestToInt = ResourceCount.resCountToInt(chestCount);
        Resource abilityRes = card.getSpecialAbility().getResourceType();
        ResourceCount cost = new ResourceCount(0,0,0,0,0);
        abilityRes.add(cost,1);

        if(chestToInt==0&&storageToInt==0)
            throw new PaymentFailedException("Chest and Player",player);
        //If leaderCard doesnt exist in the model then throw Exception
        if(!(player.leaderCardExists(card)&&card.isInGame()))
            throw new CardNotExistsException("Leader Card",player,true);
        //Player wants to use the Storage to pay || throw exception if there aren't enough resources in StorageCount, or deleteRes goes wrong
        if(storageToInt!=0&&(!card.getSpecialAbility().useProductionAbility(storageCount)||!deleteRes(storageCount,chestCount,player)))
            throw new PaymentFailedException("Storage",player);
        //Player wants to use the Chest to pay || throw exception if there aren't enough resources in StorageCount, or deleteRes goes wrong
        if(chestToInt!=0&&(!card.getSpecialAbility().useProductionAbility(chestCount)||!deleteRes(storageCount,chestCount,player)))
            throw new PaymentFailedException("Chest",player);

        //res is the chosen resource as output
        ResourceCount output = new ResourceCount(0,0,0,0,1); //ResourceCount with 1 Faith
        res.add(output,1);
        player.incrementBufferProduction(output);
    }
}
