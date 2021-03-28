package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.enumeration.Resource;

public interface SpecialAbility {

    public boolean useAbility();
    public boolean useAbility(ResourceCount count,boolean ctrl);
    public boolean useAbility(Resource res,int n);
    public boolean useAbility(ResourceCount cost);
}
