package it.polimi.ingsw.controller.action.productionAction;

import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.enumeration.Resource;

public class LeaderCardProductionAction extends Action {
    LeaderCard card;
    ResourceCount storageCount;
    ResourceCount chestCount;
    Resource res;

    public LeaderCardProductionAction(LeaderCard card, ResourceCount storageCount, ResourceCount chestCount, Resource res) {
        this.card = card;
        this.storageCount = storageCount;
        this.chestCount = chestCount;
        this.res = res;
    }

    //LEADERCARDS
    //RECEIVING CARD,COUNT OF RESOURCES FROM THE STORAGE,COUNT OF RESOURCES FROM THE CHEST,PLAYER,AND THE RESOURCE AS THE CHOSEN OUTPUT
    @Override
    public boolean useAction(PlayerDashboard player){

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
        player.incrementBufferProduction(output);
        return true;
    }

    //ADD THE RESOURCES OBTAINED FROM THE PRODUCTIONS TO THE PLAYER AND THEN RESET THE BUFFER;
    @Override
    public void endAction(PlayerDashboard player) {
        player.emptyBufferProduction();
    }
}
