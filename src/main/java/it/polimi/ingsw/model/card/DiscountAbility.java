package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.enumeration.Resource;

public class DiscountAbility implements SpecialAbility {
    private Resource resourceType;

    @Override
    public void useAbility(){}

    @Override
    public void useAbility(ResourceCount count){ //Decreasing Resource Cost
        switch(resourceType){
            case COIN:  if(count.getCoins()>0)
                            count.removeResources(1,0,0,0);
                        break;
            case ROCK:  if(count.getRocks()>0)
                            count.removeResources(0,1,0,0);
                        break;
            case SERVANT:   if(count.getServants()>0)
                                count.removeResources(0,0,1,0);
                            break;
            case SHIELD:    if(count.getShields()>0)
                                count.removeResources(0,0,0,1);
                            break;
        }
    }
    @Override
    public boolean isUsable(){return true;}
}
