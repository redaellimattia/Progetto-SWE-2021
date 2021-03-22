package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumeration.Resource;

public class DiscountAbility implements SpecialAbility{
    private Resource resourceType;

    @Override
    public void useAbility(){}

    @Override
    public boolean isUsable(){return true;}
}
