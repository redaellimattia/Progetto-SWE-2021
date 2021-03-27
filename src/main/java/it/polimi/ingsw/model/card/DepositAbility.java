package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.enumeration.Resource;

public class  DepositAbility implements SpecialAbility {
    private Resource resourceType;
    private int contDeposit;

    public DepositAbility(Resource resourceType) {
        this.resourceType = resourceType;
        this.contDeposit = 0;
    }

    @Override
    public boolean useAbility(Resource res,int n){
        if(res!=resourceType)
            return false;
        else{
            switch(n){
                case 1: if(contDeposit!=2)
                            contDeposit++;
                        else
                            return false;
                        break;
                case 2: if(contDeposit==0)
                            contDeposit+=2;
                        else
                            return false;
                        break;
                default: return false;
            }
        }
        return true;
    }

    @Override
    public boolean useAbility(){return false;}

    @Override
    public boolean useAbility(ResourceCount count) {
        return false;
    }
}
