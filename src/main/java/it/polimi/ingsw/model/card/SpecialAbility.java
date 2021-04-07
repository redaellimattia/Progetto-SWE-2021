package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.enumeration.Resource;

public abstract class SpecialAbility {
    private Resource resourceType;

    public Resource getResourceType() {
        return resourceType;
    }

    public SpecialAbility(Resource resourceType) {
        this.resourceType = resourceType;
    }

    public boolean useDepositAbility(PlayerDashboard player) {return false;} //DepositAbility
    public boolean useProductionAbility(ResourceCount count) {return false;} //ProductionAbility
    public boolean useDiscountAbility(ResourceCount cost){return false;}  //DiscountAbility
    public Resource useWhiteChangeAbility(){return null;}  //Used in WhiteChangeAbility
}
