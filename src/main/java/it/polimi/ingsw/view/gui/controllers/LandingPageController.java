package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.network.client.ClientManager;
import it.polimi.ingsw.view.gui.GuiManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class LandingPageController extends GuiController{
    @FXML
    private Button createButton;
    @FXML
    private ListView lobbyList;
    @FXML
    private TextField numberOfPlayersField;
    @FXML
    private TextField nicknameField;
    private GuiManager guiManager;
    @FXML
    @Override
    public void initialize() {
        guiManager = GuiManager.getInstance();
    }

    @FXML
    public void onCreateButtonClick(ActionEvent actionEvent) {
        String nickname = nicknameField.getText();
        ClientManager clientManager = super.getClientManager();
        clientManager.setNickname(nickname);
        int numberOfPlayers = 0;
        try{numberOfPlayers = Integer.parseInt(numberOfPlayersField.getText());} catch(NumberFormatException e){}
        if(numberOfPlayers<1 || numberOfPlayers>4)
            createButton.setText("Insert a valid number of players");
        else{
            clientManager.createGame(numberOfPlayers);
        }
    }
}
