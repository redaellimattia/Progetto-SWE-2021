package it.polimi.ingsw.controller.action.productionAction;

import it.polimi.ingsw.controller.Parameter;
import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.enumeration.Resource;

public class DevCardProductionAction extends Action {
    private ResourceCount bufferOutput;
    DevelopmentCard card;
    ResourceCount storageCount;
    ResourceCount chestCount;

    public DevCardProductionAction(DevelopmentCard card, ResourceCount storageCount, ResourceCount chestCount) {
        this.bufferOutput = new ResourceCount(0,0,0,0,0);
        this.card = card;
        this.storageCount = storageCount;
        this.chestCount = chestCount;
    }

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
