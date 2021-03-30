package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.enumeration.Resource;

public class ProductionAbility implements SpecialAbility {
    private Resource resourceType;

    public ProductionAbility(Resource resourceType) {
        this.resourceType = resourceType;
    }

    @Override
    public boolean useProductionAbility(ResourceCount count){
        if(resourceType.get(count)>0){ //If there are enough resource of this.resourceType
            resourceType.remove(count, 1); //Remove 1 resource of this.resourceType from count
            return true; //True if there are enough resources to use the production
        }
        return false; //False, not enough resources
    }
    @Override
    public boolean useDepositAbility(PlayerDashboard player){return false;} //Used in DepositAbility
    @Override
    public Resource useWhiteChangeAbility(){return null;} //Used in WhiteChangeAbility
    @Override
    public boolean useDiscountAbility(ResourceCount cost) {return false;} //Used in DiscountAbility
}
