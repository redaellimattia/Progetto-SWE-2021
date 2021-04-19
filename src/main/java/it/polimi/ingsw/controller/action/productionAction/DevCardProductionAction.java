package it.polimi.ingsw.controller.action.productionAction;

import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.card.DevelopmentCard;

public class DevCardProductionAction extends Action {
    DevelopmentCard card;
    ResourceCount storageCount;
    ResourceCount chestCount;

    /**
     *
     * @param card DevCard chosen by the player to use its production
     * @param storageCount amount of resources in the Storage, that the player wants to pay with, can be null
     * @param chestCount amount of resources in the Chest, that the player wants to pay with, can be null
     */
    public DevCardProductionAction(DevelopmentCard card, ResourceCount storageCount, ResourceCount chestCount) {
        this.card = card;
        this.storageCount = storageCount;
        this.chestCount = chestCount;
    }

    /**
     *
     * @param player player that is doing the action
     * @return true if ended correctly
     */
    //DEVCARDS || RETURN TRUE IF EVERYTHING IS DONE CORRECTLY (PAYMENT FROM STORAGE AND/OR CHEST) AND PLAYER HAS ENOUGH RESOURCES
    //I GET FROM THE VIEW THE CARD, THE CHOSEN AMOUNT OF RESOURCES FROM STORAGE AND CHEST (EVENTUALLY NULL) AND THE PLAYER
    @Override
    public boolean useAction(PlayerDashboard player){
        ResourceCount cost = card.getProductionPower().getInput();
        ResourceCount output = card.getProductionPower().useProduction(ResourceCount.getTotal(storageCount,chestCount));

        //If devCard doesnt exist in the model then return false
        if(!player.devCardExists(card))
            return false;

        if(output==null || !deleteRes(storageCount,chestCount,player)) //NOT ENOUGH RESOURCES OR PAYMENT FAILED
            return false;

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
