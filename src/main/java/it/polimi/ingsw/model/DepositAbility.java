package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumeration.Resource;

public class DepositAbility implements SpecialAbility{
    private Resource resourceType;

    public Resource getResourceType() {
        return resourceType;
    }

    @Override
    public void useAbility(){}

    @Override
    public boolean isUsable(){return true;}
}
