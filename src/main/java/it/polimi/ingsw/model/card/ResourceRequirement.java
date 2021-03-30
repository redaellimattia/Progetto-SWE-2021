package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.ResourceCount;

public class ResourceRequirement implements Requirement {
    private ResourceCount resources;

    public ResourceRequirement(ResourceCount resources) {
        this.resources = resources;
    }

    @Override
    public boolean isPlayable(PlayerDashboard player) { //True if player can actually play that card
        ResourceCount count = player.getTotalResources();
        ResourceCount abilityDepositResources = player.getAbilityDepositResources();
        count.addResources(abilityDepositResources.getCoins(), abilityDepositResources.getRocks(), abilityDepositResources.getServants(), abilityDepositResources.getShields());
        //Now count is equal to all the resources available to the player
        if(resources.getCoins()<=count.getCoins()&&resources.getRocks()<=count.getRocks()&&
           resources.getServants()<=count.getServants()&&resources.getShields()<=count.getShields())
            return true;
        else
            return false;
    }
}
