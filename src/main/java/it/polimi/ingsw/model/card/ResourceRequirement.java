package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.ResourceCount;

public class ResourceRequirement implements Requirement {
    private ResourceCount resources;

    public ResourceRequirement(ResourceCount resources) {
        this.resources = resources;
    }

    public ResourceCount getResources() {
        return resources;
    }

    @Override
    //TRUE IF THE PLAYER CAN ACTUALLY PLAY THE CARD
    public boolean isPlayable(PlayerDashboard player) { //True if player has enough resources
        ResourceCount count = player.getTotalResources();
        ResourceCount abilityDepositResources = player.getAbilityDepositResources(); // Fill with abilityDeposit, if there are any
        count.addResources(abilityDepositResources.getCoins(), abilityDepositResources.getRocks(), abilityDepositResources.getServants(), abilityDepositResources.getShields(),abilityDepositResources.getFaith());
        //Now count is equal to all the resources available to the player
        if(resources.getCoins()<=count.getCoins()&&resources.getRocks()<=count.getRocks()&&
           resources.getServants()<=count.getServants()&&resources.getShields()<=count.getShields())
            return true; //True if player has enough resources
        else
            return false;
    }
    @Override
    public boolean equals(Object o){
        if (o == this) { //True if it's this instance
            return true;
        }
        if (!(o instanceof ResourceRequirement))
            return false;

        //Check if same values
        ResourceRequirement c = (ResourceRequirement) o;
        return this.resources.equals(c.getResources()); // true if same resourceCount
    }
}
