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
        player.initArrayDeposit(resourceType); //Init arrayDeposit in PlayerDashboard
        return true; //This should be always possible, because we run this method only when player actually puts the card in game
    }

    @Override
    public Resource useWhiteChangeAbility(){return null;} //Used in WhiteChangeAbility
    @Override
    public boolean useDiscountAbility(ResourceCount cost) {return false;} //Used in DiscountAbility
    @Override
    public boolean useProductionAbility(ResourceCount count){return false;} //Used in productionAbility
}
