package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.enumeration.Resource;

public class ProductionAbility extends SpecialAbility {

    public ProductionAbility(Resource resourceType) {
        super(resourceType);
    }

    @Override
    //WHEN IN PRODUCTIONACTION, THIS PRODUCTION IS AVAILABLE, TRUE IF POSSIBLE
    public boolean useProductionAbility(ResourceCount count){
        Resource resType = super.getResourceType();
        if(count!=null) {
            if (resType.get(count) == 1) //If there is exactly one res
                return true; //True if there are enough resources to use the production
        }
        return false; //False, not enough resources
    }
}
