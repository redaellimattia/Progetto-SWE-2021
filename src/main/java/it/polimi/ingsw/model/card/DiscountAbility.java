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
    //WHEN TRYING TO BUY A CARD FROM THE SHOP,DECREASE ITS COST
    public boolean useDiscountAbility(ResourceCount cost){ //Decreasing Resource Cost of the card player is buying
        if(resourceType.get(cost)>0) //If there are enough resource of this.resourceType
            resourceType.remove(cost,1); //Remove 1 resource of this.resourceType from cost
        return true; //True, devCard cost is reduced
    }
    @Override
    public Resource useWhiteChangeAbility(){return null;} //Used in WhiteChangeAbility
    @Override
    public boolean useProductionAbility(ResourceCount count){return false;} //Used in productionAbility
    @Override
    public boolean useDepositAbility(PlayerDashboard player){return false;} //Used in DepositAbility
}
