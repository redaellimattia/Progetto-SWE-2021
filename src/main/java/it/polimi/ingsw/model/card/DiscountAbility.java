package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.enumeration.Resource;

public class DiscountAbility extends SpecialAbility {

    public DiscountAbility(Resource resourceType) {super(resourceType);}

    @Override
    //WHEN TRYING TO BUY A CARD FROM THE SHOP,DECREASE ITS COST
    public boolean useDiscountAbility(ResourceCount cost){ //Decreasing Resource Cost of the card player is buying
        Resource resType = super.getResourceType();
        if(resType.get(cost)>0) //If there are enough resource of this.resourceType
            resType.remove(cost,1); //Remove 1 resource of this.resourceType from cost
        return true; //True, devCard cost is reduced
    }
    @Override
    public boolean equals(Object o){
        if (o == this) { //True if it's this instance
            return true;
        }
        if (!(o instanceof DiscountAbility))
            return false;

        //Check if same values
        DiscountAbility c = (DiscountAbility) o;
        return this.getResourceType().equals(c.getResourceType()); //True if same values
    }
}
