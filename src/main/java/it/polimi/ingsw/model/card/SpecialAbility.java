package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.enumeration.Resource;

public interface SpecialAbility {

    public boolean useDepositAbility(PlayerDashboard player); //DepositAbility
    public boolean useProductionAbility(ResourceCount count); //ProductionAbility
    public boolean useDiscountAbility(ResourceCount cost); //DiscountAbility
    public Resource useAbility();
}
