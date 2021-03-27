package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.enumeration.Resource;

public class WhiteChangeAbility implements SpecialAbility {
    private Resource resourceType;

    public WhiteChangeAbility(Resource resourceType) {
        this.resourceType = resourceType;
    }

    @Override
    public boolean useAbility(Resource res,int n){return false;}

    @Override
    public boolean useAbility(){ //Devo per forza ritornare, da pensare
        return true;
    } //Da fare

    @Override
    public boolean useAbility(ResourceCount count) {
        return false;
    }

}
