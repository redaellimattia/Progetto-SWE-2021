package it.polimi.ingsw.controller.action.productionAction;

import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.exceptions.CardNotExistsException;
import it.polimi.ingsw.exceptions.PaymentFailedException;
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
     * @return true if ended correctly
     */
    @Override
    public boolean useAction(PlayerDashboard player) {

        Resource abilityRes = card.getSpecialAbility().getResourceType();
        ResourceCount cost = new ResourceCount(0,0,0,0,0);
        abilityRes.add(cost,1);

        //If leaderCard doesnt exist in the model then throw Exception
        if(!(player.leaderCardExists(card)&&card.isInGame()))
            throw new CardNotExistsException("Leader Card");
        //Player wants to use the Storage to pay || Return false if there aren't enough resources in StorageCount, or deleteRes goes wrong
        if(storageCount!=null&&(!card.getSpecialAbility().useProductionAbility(storageCount)||!deleteRes(storageCount,chestCount,player)))
            throw new PaymentFailedException("Storage");
        //Player wants to use the Chest to pay || Return false if there aren't enough resources in StorageCount, or deleteRes goes wrong
        if(chestCount!=null&&(!card.getSpecialAbility().useProductionAbility(chestCount)||!deleteRes(storageCount,chestCount,player)))
            throw new PaymentFailedException("Chest");

        //res is the chosen resource as output
        ResourceCount output = new ResourceCount(0,0,0,0,1); //ResourceCount with 1 Faith
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
