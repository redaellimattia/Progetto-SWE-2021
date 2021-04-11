package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.Production;
import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.enumeration.Resource;

public class ProductionAction extends Action{

    private ResourceCount bufferOutput;

    public ProductionAction(ResourceCount bufferOutput) { //Will be called in PlayerTurnManager
        this.bufferOutput = bufferOutput;
    }

    //DEVCARDS || RETURN TRUE IF EVERYTHING IS DONE CORRECTLY (PAYMENT FROM STORAGE AND/OR CHEST) AND PLAYER HAS ENOUGH RESOURCES
    //I GET FROM THE VIEW THE CARD, THE CHOSEN AMOUNT OF RESOURCES FROM STORAGE AND CHEST (EVENTUALLY NULL) AND THE PLAYER
    public boolean useProductionAction(DevelopmentCard card, ResourceCount storageCount, ResourceCount chestCount, PlayerDashboard player){
        ResourceCount cost = card.getProductionPower().getInput();
        ResourceCount output = card.getProductionPower().useProduction(getTotal(storageCount,chestCount));

        if(output==null || !deleteRes(storageCount,chestCount,player)) //NOT ENOUGH RESOURCES OR PAYMENT FAILED
            return false;

        bufferOutput.sumCounts(output);
        return true;
    }

    //LEADERCARDS
    public boolean useProductionAction(LeaderCard card, ResourceCount playerCount, Resource res){ //Receiving LeaderCard from the view
        if(!card.getSpecialAbility().useProductionAbility(playerCount))
            return false;
        //res is the chosen resource as output
        ResourceCount output = new ResourceCount(0,0,0,0,1); //ResourceCount with 1 Faith
        res.add(output,1);
        bufferOutput.sumCounts(output);
        return true;
    }

    //BASICPRODUCTION
    public boolean useProductionAction(ResourceCount cost,Resource res,ResourceCount playerCount){ //Receiving dashBoard production from the view, then using it
        if(cost.getCoins()>playerCount.getCoins()||cost.getRocks()>playerCount.getRocks()||
                cost.getServants()>playerCount.getServants()||cost.getShields()>playerCount.getShields())
            return false;
        ResourceCount output = new ResourceCount(0,0,0,0,0); //ResourceCount with 1 Faith
        res.add(output,1);
        bufferOutput.sumCounts(output);
        return true;
    }
    //ADD THE RESOURCES OBTAINED FROM THE PRODUCTIONS TO THE PLAYER AND THEN RESET THE BUFFER;
    public void endProductionAction(ResourceCount playerChest) {
        playerChest.sumCounts(bufferOutput);
        bufferOutput = new ResourceCount(0,0,0,0,0);
    }
}
