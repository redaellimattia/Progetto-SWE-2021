package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.card.SpecialAbility;
import it.polimi.ingsw.model.enumeration.Resource;

public class  DepositAbility implements SpecialAbility {
    private Resource resourceType;

    public Resource getResourceType() {
        return resourceType;
    }

    @Override
    public void useAbility(){}

    @Override
    public void useAbility(ResourceCount count) {
    }

    @Override
    public boolean isUsable(){return true;}
}
