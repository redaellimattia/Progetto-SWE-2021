package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Production;
import it.polimi.ingsw.model.ResourceCount;

public interface Action {

    public void useAction();
    public void useProductionAction(Production prod, ResourceCount playerCount);
    public void endProductionAction(ResourceCount playerChest);
    public void endAction();
}
