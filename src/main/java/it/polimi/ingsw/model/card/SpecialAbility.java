package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.ResourceCount;

public interface SpecialAbility {

    public boolean useAbility();
    public boolean useAbility(ResourceCount count);
    public boolean isUsable();
}
