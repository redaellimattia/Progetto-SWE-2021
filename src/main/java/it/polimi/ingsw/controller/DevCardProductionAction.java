package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.ResourceCount;

public class DevCardProductionAction {
    private ResourceCount bufferOutput;

    public DevCardProductionAction() {
        this.bufferOutput = new ResourceCount(0,0,0,0,0);
    }
}
