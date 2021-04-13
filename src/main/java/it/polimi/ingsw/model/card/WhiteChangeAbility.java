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

    @Override
    public boolean equals(Object o){
        if (o == this) { //True if it's this instance
            return true;
        }
        if (!(o instanceof WhiteChangeAbility))
            return false;

        //Check if same values
        WhiteChangeAbility c = (WhiteChangeAbility) o;
        return this.getResourceType().equals(c.getResourceType()); //True if same values
    }
}
