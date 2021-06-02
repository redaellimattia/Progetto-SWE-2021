package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.network.client.ClientManager;
import it.polimi.ingsw.network.messages.serverMessages.ReturnLobbiesMessage;

import java.util.ArrayList;

public abstract class GuiController {

    public void initialize(){}
    public void setLobbies(ArrayList<ReturnLobbiesMessage.availableGameLobbies> lobbies){}
    public void setPreGameChoice(ArrayList<LeaderCard> leaders, int numberOfResources){}
}
