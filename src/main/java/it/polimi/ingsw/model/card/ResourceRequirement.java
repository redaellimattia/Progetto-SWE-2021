package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.ResourceCount;

public class ResourceRequirement implements Requirement {
    private ResourceCount resources;

    public ResourceRequirement(ResourceCount resources) {
        this.resources = resources;
    }

    @Override
    public boolean isPlayable(PlayerDashboard player) {
        ResourceCount count = player.getTotalResources(); //Now count is equal to all the resources available to the player
        //Controllare se ci sono carteLeader in gioco, poi controllare se ci sono depositAbility in gioco e guardare quello scaffale
        //Cosi da poterlo contare.
        if(resources.getCoins()<=count.getCoins()&&resources.getRocks()<=count.getRocks()&&
           resources.getServants()<=count.getServants()&&resources.getShields()<=count.getShields())
            return true;
        else
            return false;
    }
}
