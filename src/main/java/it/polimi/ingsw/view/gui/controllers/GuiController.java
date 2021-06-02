package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.network.client.ClientManager;
import it.polimi.ingsw.network.messages.serverMessages.ReturnLobbiesMessage;

import java.util.ArrayList;

public abstract class GuiController {

    public void initialize(){}
    public void setLobbies(ArrayList<ReturnLobbiesMessage.availableGameLobbies> lobbies){}
}
