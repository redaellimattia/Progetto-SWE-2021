package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.enumeration.Resource;

public class DiscountAbility extends SpecialAbility {

    /**
     *
     * @param resourceType discount applicable as Resource
     */
    public DiscountAbility(Resource resourceType) {super(resourceType);}

    /**
     *
     * @param cost passed ResourceCount, it's the DevCard cost
     */
    @Override
    //WHEN TRYING TO BUY A CARD FROM THE SHOP,DECREASE ITS COST
    public void useDiscountAbility(ResourceCount cost){ //Decreasing Resource Cost of the card player is buying
        Resource resType = super.getResourceType();
        if(resType.get(cost)>0) //If there are not enough resource of this.resourceType, return false
            resType.remove(cost,1); //Remove 1 resource of this.resourceType from cost
    }

    /**
     *
     * @param o passed Object
     * @return true if o is the same object of this, or it has the same Resource discount
     */
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
