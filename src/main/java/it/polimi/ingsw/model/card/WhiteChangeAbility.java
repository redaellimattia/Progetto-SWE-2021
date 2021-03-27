package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.enumeration.Resource;

public class WhiteChangeAbility implements SpecialAbility {
    private Resource resourceType;

    @Override
    public boolean useAbility(){ //Devo per forza ritornare, da pensare
        return true;
    }

    @Override
    public boolean useAbility(ResourceCount count) {
        return true;
    }

    @Override
    public boolean isUsable(){return true;}
}
