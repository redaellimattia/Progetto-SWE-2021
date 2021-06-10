package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.network.client.ClientManager;
import it.polimi.ingsw.view.gui.GuiManager;
import javafx.application.Platform;

public class SplashScreenController extends GuiController{
    /**
     * performing the connection to the server
     */
    @Override
    public void initialize() {
        GuiManager guiManager = GuiManager.getInstance();
        ClientManager clientManager = guiManager.getClientManager();
        Platform.runLater(()->clientManager.connection(clientManager.getAddress(),clientManager.getSocketPort()));
    }
}
