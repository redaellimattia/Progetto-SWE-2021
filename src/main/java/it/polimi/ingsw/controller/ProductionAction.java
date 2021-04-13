package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.enumeration.Resource;

public class ProductionAction extends Action{

    private ResourceCount bufferOutput;

    public ProductionAction() { //Will be called in PlayerTurnManager
        this.bufferOutput = new ResourceCount(0,0,0,0,0);
    }

    public ResourceCount getBufferOutput() {
        return bufferOutput;
    }

    //DEVCARDS || RETURN TRUE IF EVERYTHING IS DONE CORRECTLY (PAYMENT FROM STORAGE AND/OR CHEST) AND PLAYER HAS ENOUGH RESOURCES
    //I GET FROM THE VIEW THE CARD, THE CHOSEN AMOUNT OF RESOURCES FROM STORAGE AND CHEST (EVENTUALLY NULL) AND THE PLAYER
    public boolean useProductionAction(DevelopmentCard card, ResourceCount storageCount, ResourceCount chestCount, PlayerDashboard player){
        ResourceCount cost = card.getProductionPower().getInput();
        ResourceCount output = card.getProductionPower().useProduction(ResourceCount.getTotal(storageCount,chestCount));

        if(output==null || !deleteRes(storageCount,chestCount,player)) //NOT ENOUGH RESOURCES OR PAYMENT FAILED
            return false;

        bufferOutput.sumCounts(output);
        return true;
    }

    //LEADERCARDS
    //RECEIVING CARD,COUNT OF RESOURCES FROM THE STORAGE,COUNT OF RESOURCES FROM THE CHEST,PLAYER,AND THE RESOURCE AS THE CHOSEN OUTPUT
    public boolean useProductionAction(LeaderCard card, ResourceCount storageCount, ResourceCount chestCount, PlayerDashboard player, Resource res){
        Resource abilityRes = card.getSpecialAbility().getResourceType();
        ResourceCount cost = new ResourceCount(0,0,0,0,0);
        abilityRes.add(cost,1);

        //Player wants to use the Storage to pay || Return false if there aren't enough resources in StorageCount, or deleteRes goes wrong
        if(storageCount!=null&&(!card.getSpecialAbility().useProductionAbility(storageCount)||!deleteRes(storageCount,chestCount,player)))
            return false;
        //Player wants to use the Chest to pay || Return false if there aren't enough resources in StorageCount, or deleteRes goes wrong
        if(chestCount!=null&&(!card.getSpecialAbility().useProductionAbility(chestCount)||!deleteRes(storageCount,chestCount,player)))
            return false;

        //res is the chosen resource as output
        ResourceCount output = new ResourceCount(0,0,0,0,1); //ResourceCount with 1 Faith
        res.add(output,1);
        bufferOutput.sumCounts(output);
        return true;
    }

    //BASICPRODUCTION
    //RECEIVING COST CHOSEN BY THE PLAYER, AND COUNT OF RESOURCES FROM THE STORAGE,COUNT OF RESOURCES FROM THE CHEST,PLAYER,AND THE RESOURCE AS THE CHOSEN OUTPUT
    public boolean useProductionAction(ResourceCount cost, Resource res, ResourceCount storageCount, ResourceCount chestCount, PlayerDashboard player){
        //If Sum of storageCount and ChestCount != cost OR deleteRes goes wrong then return false
        if(!ResourceCount.getTotal(storageCount,chestCount).equals(cost)||!deleteRes(storageCount,chestCount,player))
            return false;

        ResourceCount output = new ResourceCount(0,0,0,0,1); //ResourceCount with 1 Faith
        res.add(output,1);
        bufferOutput.sumCounts(output);
        return true;
    }
    //ADD THE RESOURCES OBTAINED FROM THE PRODUCTIONS TO THE PLAYER AND THEN RESET THE BUFFER;
    public void endProductionAction(PlayerDashboard player) {
        int faith = bufferOutput.getFaith();
        if(faith!=0)
                player.updatePathPosition(faith);
        bufferOutput.removeFaith(faith);
        player.getChest().sumCounts(bufferOutput);
        bufferOutput = new ResourceCount(0,0,0,0,0);
    }
}
