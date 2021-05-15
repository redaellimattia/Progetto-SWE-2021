package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.enumeration.Resource;

public class  DepositAbility extends SpecialAbility {

    /**
     *
     * @param resourceType Resource of the deposit
     */
    public DepositAbility(Resource resourceType) {
        super(resourceType);
    }

    /**
     * WHEN PLAYED, INITIALIZE arrayDeposit IN PLAYERDASHBOARD
     *
     * @param player player whom i am adding the deposit
     */
    @Override
    public void useDepositAbility(PlayerDashboard player){
        Resource resType = super.getResourceType();
        player.initArrayDeposit(resType); //Init arrayDeposit in PlayerDashboard
    }

    /**
     * EQUALS
     *
     * @param o passed Object
     * @return true if o is the same object of this, or it has the same Resource deposit
     */
    @Override
    public boolean equals(Object o){
        if (o == this) { //True if it's this instance
            return true;
        }
        if (!(o instanceof DepositAbility))
            return false;

        //Check if same values
        DepositAbility c = (DepositAbility) o;
        return this.getResourceType().equals(c.getResourceType()); //True if same values
    }

    @Override
    public String toString(){
        return "Add a deposit that can contain 2 "  + getResourceType();
    }
}
