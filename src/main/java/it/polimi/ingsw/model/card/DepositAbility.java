package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.enumeration.Resource;

public class  DepositAbility extends SpecialAbility {
    public DepositAbility(Resource resourceType) {
        super(resourceType);
    }

    @Override
    //WHEN PLAYED, INITIALIZE arrayDeposit IN PLAYERDASHBOARD
    public boolean useDepositAbility(PlayerDashboard player){
        Resource resType = super.getResourceType();
        player.initArrayDeposit(resType); //Init arrayDeposit in PlayerDashboard
        return true; //This should be always possible, because we run this method only when player actually puts the card in game
    }
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
}
