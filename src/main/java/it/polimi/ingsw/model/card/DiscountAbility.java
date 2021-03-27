package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.enumeration.Resource;

public class DiscountAbility implements SpecialAbility {
    private Resource resourceType;

    public DiscountAbility(Resource resourceType) {
        this.resourceType = resourceType;
    }

    @Override
    public boolean useAbility(ResourceCount cost){ //Decreasing Resource Cost
        switch(resourceType){
            case COIN:  if(cost.getCoins()>0)
                cost.removeResources(1,0,0,0);
                break;
            case ROCK:  if(cost.getRocks()>0)
                cost.removeResources(0,1,0,0);
                break;
            case SERVANT:   if(cost.getServants()>0)
                cost.removeResources(0,0,1,0);
                break;
            case SHIELD:    if(cost.getShields()>0)
                cost.removeResources(0,0,0,1);
                break;
        }
        return true;
    }

    @Override
    public boolean useAbility(){return false;}

    @Override
    public boolean useAbility(Resource res,int n){return false;}
}
