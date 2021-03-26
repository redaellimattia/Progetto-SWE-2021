package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.ResourceCount;

public interface SpecialAbility {

    public void useAbility();
    public void useAbility(ResourceCount count);
    public boolean isUsable();

}
