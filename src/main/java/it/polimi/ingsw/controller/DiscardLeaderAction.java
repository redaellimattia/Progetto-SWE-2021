package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Production;
import it.polimi.ingsw.model.ResourceCount;

public class DiscardLeaderAction implements Action {
    @Override
    public void useAction() {

    }
    @Override
    public void useProductionAction(Production prod, ResourceCount playerCount) {
    }
    @Override
    public void endProductionAction(ResourceCount playerChest) {
    }
    @Override
    public void endAction(){}
}
