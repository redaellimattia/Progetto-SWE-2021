package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.enumeration.Resource;

public class WhiteChangeAbility extends SpecialAbility {

    public WhiteChangeAbility(Resource resourceType) {
        super(resourceType);
    }

    @Override
    public Resource useWhiteChangeAbility(){ //Devo per forza ritornare, da pensare
        return super.getResourceType();
    } //Returns resourceType

}
