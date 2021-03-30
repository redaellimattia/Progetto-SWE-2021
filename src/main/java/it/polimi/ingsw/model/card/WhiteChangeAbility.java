package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.enumeration.Resource;

public class WhiteChangeAbility implements SpecialAbility {
    private Resource resourceType;

    public WhiteChangeAbility(Resource resourceType) {
        this.resourceType = resourceType;
    }

    @Override
    public Resource useAbility(){ //Devo per forza ritornare, da pensare
        return resourceType;
    } //Returns resourceType

    @Override
    public boolean useDiscountAbility(ResourceCount count) {
        return false;
    } //Used in DiscountAbility
    @Override
    public boolean useProductionAbility(ResourceCount count){return false;} //Used in productionAbility
    @Override
    public boolean useDepositAbility(PlayerDashboard player){return false;} //Used in DepositAbility

}
