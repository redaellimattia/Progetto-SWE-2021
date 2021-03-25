package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.Production;
import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.enumeration.Resource;

public class ProductionAbility implements SpecialAbility {
    private Production production;

    @Override
    public void useAbility(){}

    @Override
    public void useAbility(ResourceCount count) {
    }

    @Override
    public boolean isUsable(){return true;}
}
