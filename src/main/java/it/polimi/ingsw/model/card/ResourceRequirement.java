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
        ResourceCount count = player.getStorage().readStorage(); //initialize count to the content of storage
        ResourceCount chest = player.getChest();
        count.addResources(chest.getCoins(), chest.getRocks(), chest.getServants(), chest.getShields()); //add chest resources to count
        //now count is all the resources available to the player
        if(resources.getCoins()<=count.getCoins()&&resources.getRocks()<=count.getRocks()&&resources.getServants()<=count.getServants()&&resources.getShields()<=count.getShields())
            return true;
        else
            return false;

    }
}
