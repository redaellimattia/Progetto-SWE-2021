package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.network.client.ClientManager;

public abstract class GuiController {
    private ClientManager clientManager;

    public void setClientManager(ClientManager clientManager){
        this.clientManager = clientManager;
    }
}
