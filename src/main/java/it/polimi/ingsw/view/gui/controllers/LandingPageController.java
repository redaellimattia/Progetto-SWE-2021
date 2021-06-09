package it.polimi.ingsw.view.gui.controllers;


import it.polimi.ingsw.network.client.ClientManager;
import it.polimi.ingsw.network.messages.serverMessages.ReturnLobbiesMessage;
import it.polimi.ingsw.view.gui.GuiManager;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class LandingPageController extends GuiController{
    @FXML
    private ComboBox numberOfPlayers;
    @FXML
    private Label error;
    @FXML
    private Label listLabel;
    @FXML
    private Button createButton;
    @FXML
    private ListView<ReturnLobbiesMessage.availableGameLobbies> lobbyList;

    @FXML
    private TextField nicknameField;
    private ClientManager clientManager;
    private ArrayList<ReturnLobbiesMessage.availableGameLobbies> lobbies;

    @FXML
    @Override
    public void initialize() {
        super.setGuiManager(GuiManager.getInstance());
        clientManager = getGuiManager().getClientManager();
        error.setVisible(false);
        ObservableList<Integer> numbers = FXCollections.observableArrayList(1,2,3,4);
        numberOfPlayers.setItems(numbers);
    }

    @FXML
    public void onCreateButtonClick(MouseEvent actionEvent) {
        String nickname = nicknameField.getText();
        ClientManager clientManager = getGuiManager().getClientManager();
        if(numberOfPlayers.getSelectionModel().getSelectedItem()==null) {
            error.setText("Insert a valid number of players!");
            error.setVisible(true);
        }
        else{
            if(nicknameField.getText().equals("")) {
                error.setText("Insert a valid nickname!");
                error.setVisible(true);
                nicknameField.clear();
            }
            else {
                clientManager.setNickname(nickname);
                createButton.setDisable(true);
                int finalNumberOfPlayers = (int) numberOfPlayers.getSelectionModel().getSelectedItem();
                Platform.runLater(() -> clientManager.createGame(finalNumberOfPlayers));
                if(finalNumberOfPlayers != 1)
                    goToWaiting("Wait for all players to join the lobby!");
            }
        }
    }

    @FXML
    public void setLobbies(ArrayList<ReturnLobbiesMessage.availableGameLobbies> lobbies){
        this.lobbies = lobbies;
        if(lobbies.size() == 0) {
            listLabel.setVisible(false);
            lobbyList.setVisible(false);
        }
        else{
            ObservableList<ReturnLobbiesMessage.availableGameLobbies> items =FXCollections.observableArrayList ();
            items.addAll(lobbies);
            lobbyList.setItems(items);
        }

    }

    @FXML
    public void handleMouseClick(MouseEvent mouseEvent) {
        ReturnLobbiesMessage.availableGameLobbies selected = lobbyList.getSelectionModel().getSelectedItem();
        if(selected == null) {
            return;
        }
        if(nicknameField.getText().equals("")) {
            error.setText("Insert a valid nickname!");
            error.setVisible(true);
            nicknameField.clear();
            lobbyList.getSelectionModel().clearSelection();
        }
        else{
            String nickname = nicknameField.getText();
            clientManager.setNickname(nickname);
            Platform.runLater(()->clientManager.joinGame(selected.getServerThreadID()));
            goToWaiting("Wait for all players to join the lobby!");
        }
    }
    public void goToWaiting(String msg){
        getGuiManager().setLayout("waitingPage.fxml");
        getGuiManager().getCurrentController().setTextForWaiting(msg);
    }

    public void askLobbies(MouseEvent mouseEvent) {
        Platform.runLater(()-> clientManager.askLobbies());
    }
}
