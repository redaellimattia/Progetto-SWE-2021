package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.Storage;
import it.polimi.ingsw.model.enumeration.Resource;

public class ResourceRequirement implements Requirement {
    private ResourceCount resources;

    public ResourceRequirement(ResourceCount resources) {
        this.resources = resources;
    }

    @Override
    public boolean isPlayable(PlayerDashboard player) {
        ResourceCount count = new ResourceCount(0,0,0,0);
        Storage playerStorage = player.getStorage();
        readStorage(playerStorage,count); //Reading all storage
        ResourceCount chest = player.getChest();
        count.addResources(chest.getCoins(), chest.getRocks(), chest.getServants(), chest.getShields());
        //ORA COUNT é tutte le risorse del player
        if(resources.getCoins()<=count.getCoins()&&resources.getRocks()<=count.getRocks()&&resources.getServants()<=count.getServants()&&resources.getShields()<=count.getServants())
            return true;
        else
            return false;

    }
    private void readStorage(Storage storage,ResourceCount count){
        Resource res = storage.getFirstRow();
        readResource(res,1,count); //Reading storage first row
        Resource resVet[] = storage.getSecondRow();
        readResource(resVet[0],calcAddNum(resVet),count); //Reading storage second row
        resVet = storage.getThirdRow();
        readResource(resVet[0],calcAddNum(resVet),count);//Reading storage third row
    }
    private void readResource(Resource res,int addNum,ResourceCount count){
        switch (res){
            case COIN: count.addResources(addNum,0,0,0); //If res is COIN then add (addNum) values to count
                       break;
            case ROCK: count.addResources(0,addNum,0,0); //If res is ROCK then add (addNum) values to count
                       break;
            case SERVANT: count.addResources(0,0,addNum,0); //If res is SERVANT then add (addNum) values to count
                          break;
            case SHIELD: count.addResources(0,0,0,addNum); //If res is SHIELD then add (addNum) values to count
                         break;
        }
    }
    private int calcAddNum(Resource resVet[]){
        int addNum = 0;
        for(int i=0;i<resVet.length;i++)
            if(resVet[i]!=null)
                addNum++;
        return addNum;
    }
}
