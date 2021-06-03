package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.model.MarketMarble;
import it.polimi.ingsw.network.client.ClientManager;
import it.polimi.ingsw.view.gui.GuiManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;


public class MarketActionController extends GuiController {

    @FXML
    private Pane col1, col2, col3, col4, row1, row2, row3;

    @FXML
    private Text message;

    private int type;
    private int pos;
    private ClientManager clientManager;

    @Override
    @FXML
    public void initialize() {
        super.setGuiManager(GuiManager.getInstance());
        this.clientManager = getGuiManager().getClientManager();
    }

    public void setChosenPos(MouseEvent mouseEvent) {
        Pane clicked = (Pane) mouseEvent.getSource();
        switch(clicked.getId()) {
            case "row1":
                type = 0;
                pos = 1;
                break;
            case "row2":
                type = 0;
                pos = 2;
                break;
            case "row3":
                type = 0;
                pos = 3;
                break;
            case "col1":
                type = 1;
                pos = 1;
                break;
            case "col2":
                type = 1;
                pos = 2;
                break;
            case "col3":
                type = 1;
                pos = 3;
                break;
            case "col4":
                type = 1;
                pos = 4;
                break;
        }
        message.setText("Selected type: " + type + ", pos: " + pos);
        col1.setDisable(true);
        col2.setDisable(true);
        col3.setDisable(true);
        col4.setDisable(true);
        row1.setDisable(true);
        row2.setDisable(true);
        row3.setDisable(true);

    }

    public void selectResources(int type, int pos) {
        MarketMarble marbles[];
        marbles = clientManager.getMarketMarbles(type, pos);
        for(MarketMarble m: marbles) {
            // Atomic choice
        }
    }
}
