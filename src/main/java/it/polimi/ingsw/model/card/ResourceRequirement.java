package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.enumeration.CardColour;

public class ResourceRequirement implements Requirement {
    private ResourceCount resources;

    /**
     *
     * @param resources keeps card requirement as ResourceCount
     */
    public ResourceRequirement(ResourceCount resources) {
        this.resources = resources;
    }

    /**
     *
     * @return value of resources
     */
    public ResourceCount getResources() {
        return resources;
    }

    /**
     *
     * @param player player trying to play the card
     * @return true if playing that leader is possible
     */
    @Override
    public boolean isPlayable(PlayerDashboard player) { //True if player has enough resources
        ResourceCount count = player.getTotalResources();
        ResourceCount abilityDepositResources = player.getAbilityDepositResources(); // Fill with abilityDeposit, if there are any
        count.addResources(abilityDepositResources.getCoins(), abilityDepositResources.getRocks(), abilityDepositResources.getServants(), abilityDepositResources.getShields(),abilityDepositResources.getFaith());
        //Now count is equal to all the resources available to the player
        return resources.getCoins() <= count.getCoins() && resources.getRocks() <= count.getRocks() &&
                resources.getServants() <= count.getServants() && resources.getShields() <= count.getShields(); //True if player has enough resources
    }

    /**
     * EQUALS
     *
     * @param o passed Object
     * @return true if o is the same object of this, or it has the same ResourceCount cost
     */
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

    @Override
    public String toString(){
        return "You need these resources: " + resources.toString() + " to play this card;";
    }
}
