package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.enumeration.Resource;

public class LeaderCardProductionAction extends Action{
    private ResourceCount bufferOutput;

    public LeaderCardProductionAction() {
        this.bufferOutput = new ResourceCount(0,0,0,0,0);
    }

    public boolean useAction(PlayerDashboard player, Parameter param){
        LeaderCard card = param.getLeaderCard();
        ResourceCount storageCount = param.getStorageCount();
        ResourceCount chestCount = param.getStorageCount();
        Resource res = param.getRes();
        Resource abilityRes = card.getSpecialAbility().getResourceType();
        ResourceCount cost = new ResourceCount(0,0,0,0,0);
        abilityRes.add(cost,1);

        //If leaderCard doesnt exist in the model then return false
        if(!(player.leaderCardExists(card)&&card.isInGame()))
            return false;
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
}
