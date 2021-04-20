package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.enumeration.Resource;

public class ProductionAbility extends SpecialAbility {

    /**
     *
     * @param resourceType cost for the production as Resource
     */
    public ProductionAbility(Resource resourceType) {
        super(resourceType);
    }

    /**
     *
     * @param count passed ResourceCount, equals to the resources chosen to pay
     * @return true if there are enough resources to use the production
     */
    @Override
    //WHEN IN PRODUCTIONACTION, THIS PRODUCTION IS AVAILABLE, TRUE IF POSSIBLE
    public boolean useProductionAbility(ResourceCount count){
        Resource resType = super.getResourceType();
        if(count!=null)
            return resType.get(count) == 1; //True if there is exactly one res to use the production
        return false; //False, not enough resources
    }

    /**
     *
     * @param o passed Object
     * @return true if o is the same object of this, or it has the same Resource cost
     */
    @Override
    public boolean equals(Object o){
        if (o == this) { //True if it's this instance
            return true;
        }
        if (!(o instanceof ProductionAbility))
            return false;

        //Check if same values
        ProductionAbility c = (ProductionAbility) o;
        return this.getResourceType().equals(c.getResourceType()); //True if same values
    }
}
