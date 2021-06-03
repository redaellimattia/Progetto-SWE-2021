package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.network.client.ClientManager;
import it.polimi.ingsw.view.gui.GuiManager;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class MarketActionController extends GuiController {

    @FXML
    private Pane col1, col2, col3, col4, row1, row2, row3;

    private int type;
    private int pos;
    private ClientManager clientManager;

    @Override
    @FXML
    public void initialize() {
        super.setGuiManager(GuiManager.getInstance());
        this.clientManager = getGuiManager().getClientManager();
    }


}
