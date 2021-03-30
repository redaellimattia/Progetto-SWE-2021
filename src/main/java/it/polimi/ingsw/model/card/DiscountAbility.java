package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.enumeration.Resource;

public class DiscountAbility implements SpecialAbility {
    private Resource resourceType;

    public DiscountAbility(Resource resourceType) {
        this.resourceType = resourceType;
    }

    @Override
    public boolean useDiscountAbility(ResourceCount cost){ //Decreasing Resource Cost
        if(resourceType.get(cost)>0)
            resourceType.remove(cost,1);
        return true; //True, devCard cost is reduced
    }
    @Override
    public boolean useAbility(){return false;}
    @Override
    public boolean useProductionAbility(ResourceCount count){return false;} //Used in productionAbility
    @Override
    public boolean useDepositAbility(PlayerDashboard player){return false;} //Used in DepositAbility
}
