package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.enumeration.Resource;

public class ProductionAbility implements SpecialAbility {
    private Resource resourceType;

    public ProductionAbility(Resource resourceType) {
        this.resourceType = resourceType;
    }

    @Override
    public boolean useAbility(ResourceCount count,boolean ctrl){
        switch (resourceType){
            case COIN:  if(count.getCoins()>0) {
                            count.removeResources(1, 0, 0, 0);
                            return true;
                        }
                        break;
            case ROCK:  if(count.getRocks()>0) {
                            count.removeResources(0, 1, 0, 0);
                            return true;
                        }
                        break;
            case SERVANT:   if(count.getServants()>0) {
                                count.removeResources(0, 0, 1, 0);
                                return true;
                            }
                            break;
            case SHIELD:    if(count.getShields()>0) {
                                count.removeResources(0, 0, 0, 1);
                                return true;
                            }
                            break;
        }
        return false;
    }

    @Override
    public boolean useAbility(Resource res, int n){return false;} //Used in DepositAbility
    @Override
    public boolean useAbility(){return false;}
    @Override
    public boolean useAbility(ResourceCount cost) {return false;} //Used in DiscountAbility
}
