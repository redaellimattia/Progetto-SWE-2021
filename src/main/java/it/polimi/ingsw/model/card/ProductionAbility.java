package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.Production;

public class ProductionAbility implements SpecialAbility {
    private Production production;

    @Override
    public void useAbility(){}

    @Override
    public boolean isUsable(){return true;}
}
