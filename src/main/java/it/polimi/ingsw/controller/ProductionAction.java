package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Production;
import it.polimi.ingsw.model.ResourceCount;

public class ProductionAction implements Action{

    private ResourceCount bufferOutput;

    public ProductionAction(ResourceCount bufferOutput) { //Will be called in PlayerTurnManager
        this.bufferOutput = bufferOutput;
    }

    //LEADERCARD PRODUCTION STILL NEEDS TO BE DONE
    @Override
    public void useProductionAction(Production prod,ResourceCount playerCount) { //Adds output to bufferOutput
        bufferOutput.sumCounts(prod.useProduction(playerCount));
    }
    @Override
    public void endProductionAction(ResourceCount playerChest) {
        playerChest.sumCounts(bufferOutput);
        bufferOutput = null;
    }

    @Override
    public void useAction(){}
    @Override
    public void endAction(){}
}
