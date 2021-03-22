package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.card.SpecialAbility;
import it.polimi.ingsw.model.enumeration.Resource;

public class WhiteChangeAbility implements SpecialAbility {
    private Resource resourceType;

    @Override
    public void useAbility(){}

    @Override
    public boolean isUsable(){return true;}
}
