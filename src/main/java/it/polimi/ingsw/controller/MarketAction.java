package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.MarketMarble;
import it.polimi.ingsw.model.Production;
import it.polimi.ingsw.model.ResourceCount;

import java.util.ArrayList;

public class MarketAction implements Action {

    private ArrayList<MarketMarble> marbles;

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
