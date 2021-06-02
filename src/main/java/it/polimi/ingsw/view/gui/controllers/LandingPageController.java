package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.network.client.ClientManager;
import it.polimi.ingsw.network.messages.serverMessages.ReturnLobbiesMessage;
import it.polimi.ingsw.view.gui.GuiManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class LandingPageController extends GuiController{
    @FXML
    private Label error;
    @FXML
    private Label listLabel;
    @FXML
    private Button createButton;
    @FXML
    private ListView lobbyList;
    @FXML
    private TextField numberOfPlayersField;
    @FXML
    private TextField nicknameField;
    private GuiManager guiManager;
    private ArrayList<ReturnLobbiesMessage.availableGameLobbies> lobbies;

    @FXML
    @Override
    public void initialize() {
        guiManager = GuiManager.getInstance();
    }

    @FXML
    public void onCreateButtonClick(ActionEvent actionEvent) {
        String nickname = nicknameField.getText();
        ClientManager clientManager = guiManager.getClientManager();
        clientManager.setNickname(nickname);
        int numberOfPlayers = 0;
        try{numberOfPlayers = Integer.parseInt(numberOfPlayersField.getText());} catch(NumberFormatException e){}
        if(numberOfPlayers<1 || numberOfPlayers>4)
            createButton.setText("Insert a valid number of players");
        else{
            createButton.setDisable(true);
            int finalNumberOfPlayers = numberOfPlayers;
            Platform.runLater(()->clientManager.createGame(finalNumberOfPlayers));
            //guiManager.preGameChoice(null,2); //TESTING
        }
    }

    public void setLobbies(ArrayList<ReturnLobbiesMessage.availableGameLobbies> lobbies){
        this.lobbies = lobbies;
        if(lobbies.size() == 0) {
            listLabel.setVisible(false);
            lobbyList.setVisible(false);
        }
    }
}
