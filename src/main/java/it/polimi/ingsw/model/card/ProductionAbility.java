package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.Production;
import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.enumeration.Resource;

public class ProductionAbility implements SpecialAbility {
    private Production production;

    public ProductionAbility(Production production) {
        this.production = production;
    }

    @Override
    public boolean useAbility(Resource res, int n){return false;}

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
