package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.enumeration.Resource;

public class  DepositAbility implements SpecialAbility {
    private Resource resourceType;

    public DepositAbility(Resource resourceType) {
        this.resourceType = resourceType;
    }

    @Override
    public boolean useDepositAbility(PlayerDashboard player){
        player.initArrayDeposit(resourceType);
        return true;
    }

    @Override
    public boolean useAbility(){return false;}
    @Override
    public boolean useDiscountAbility(ResourceCount cost) {return false;} //Used in DiscountAbility
    @Override
    public boolean useProductionAbility(ResourceCount count){return false;} //Used in productionAbility
}
