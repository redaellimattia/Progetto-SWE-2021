package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.enumeration.Resource;

public class WhiteChangeAbility extends SpecialAbility {

    /**
     *
     * @param resourceType Resource of the whitChange
     */
    public WhiteChangeAbility(Resource resourceType) {
        super(resourceType);
    }

    /**
     *
     * @return Resource of the Card
     */
    @Override
    public Resource useWhiteChangeAbility(){
        return super.getResourceType(); //Returns resourceType
    }

    /**
     * Equals
     *
     * @param o passed Object
     * @return true if o is the same object of this, or it has the same Resource
     */
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

    @Override
    public String toString(){
        return "This card will permit you to change a white marble to a " + getResourceType();
    }
}
