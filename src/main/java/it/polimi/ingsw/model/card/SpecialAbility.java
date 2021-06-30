package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.enumeration.Resource;

public abstract class SpecialAbility {
    private final Resource resourceType;

    /**
     *
     * @param resourceType Resource of the Card
     */
    public SpecialAbility(Resource resourceType) {
        this.resourceType = resourceType;
    }

    /**
     *
     * @return resourceType as Resource
     */
    public Resource getResourceType() {
        return resourceType;
    }

    /**
     *
     * @param player player whom i am adding the deposit
     */
    public void useDepositAbility(PlayerDashboard player) {} //DepositAbility

    /**
     *
     * @param count passed ResourceCount, equals to the resources chosen to pay
     * @return true if there are enough resources to use the production
     */
    public boolean useProductionAbility(ResourceCount count) {return false;} //ProductionAbility
    /**
     *
     * @param cost passed ResourceCount, it's the DevCard cost
     */
    public void useDiscountAbility(ResourceCount cost){}  //DiscountAbility
    /**
     *
     * @return Resource of the Card
     */
    public Resource useWhiteChangeAbility(){return null;}  //Used in WhiteChangeAbility

    /**
     *
     * @return true if the card has a production ability
     */
    public boolean isProductionAbility(){return false;}

    /**
     *
     * @return true if the card has a production ability
     */
    public boolean isDepositAbility(){return false;}

    public String toString(){return null;}

}
