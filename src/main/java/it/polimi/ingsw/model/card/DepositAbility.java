package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.enumeration.Resource;

public class  DepositAbility implements SpecialAbility {
    private Resource[] deposit = new Resource[2];

    @Override
    public boolean useAbility(){return true;} //Va pensato

    @Override
    public boolean useAbility(ResourceCount count) {
        return false;
    }

    @Override
    public boolean isUsable(){return true;}
}
