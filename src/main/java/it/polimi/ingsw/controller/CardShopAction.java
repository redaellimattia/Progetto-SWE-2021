package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Production;
import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.card.DevelopmentCard;

public class CardShopAction implements Action {

    private DevelopmentCard boughtCard;

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
