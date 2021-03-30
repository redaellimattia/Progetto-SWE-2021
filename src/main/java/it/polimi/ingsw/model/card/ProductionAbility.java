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
        if(resourceType.get(count)>0){
            resourceType.remove(count, 1);
            return true;
        }
        return false;
    }
    @Override
    public boolean useDepositAbility(PlayerDashboard player){return false;} //Used in DepositAbility
    @Override
    public boolean useAbility(){return false;}
    @Override
    public boolean useDiscountAbility(ResourceCount cost) {return false;} //Used in DiscountAbility
}
