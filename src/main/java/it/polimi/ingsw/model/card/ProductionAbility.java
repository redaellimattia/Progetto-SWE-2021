package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.Production;
import it.polimi.ingsw.model.ResourceCount;

public class ProductionAbility implements SpecialAbility {
    private Production production;

    @Override
    public boolean useAbility(){ //Devo ritornare l'output?
        return false;
    }

    @Override
    public boolean useAbility(ResourceCount count) {
        return false;
    }

    @Override
    public boolean isUsable(){
        return true;
    }
}
