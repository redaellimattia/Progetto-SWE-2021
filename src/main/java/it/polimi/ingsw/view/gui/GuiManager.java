package it.polimi.ingsw.view.gui;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.network.client.ClientManager;
import it.polimi.ingsw.network.client.PlayerPoints;
import it.polimi.ingsw.network.messages.serverMessages.ReturnLobbiesMessage;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.gui.controllers.GuiController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;


public class GuiManager implements View {
    private ClientManager clientManager;

    private GuiController currentController;
    private Scene currentScene;
    private Stage stage;



    private static GuiManager instance;


    public GuiManager(ClientManager clientManager) {
        this.clientManager = clientManager;
        instance = this;
    }

    public ClientManager getClientManager() {
        return clientManager;
    }

    public GuiController getCurrentController() {
        return currentController;
    }

    public Scene getCurrentScene() {
        return currentScene;
    }

    public static GuiManager getInstance() {
        return instance;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setCurrentScene(Scene scene){
        this.currentScene = scene;
    }


    public void setLayout(String fileName){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(GuiManager.class.getResource("/fxml/"+fileName));
        Pane pane;
        try {
            pane = loader.load();
            currentScene.setRoot(pane);
            this.currentController = loader.getController();
        } catch (IOException e) {
            System.out.println("IOException while setting layout: "+e.getMessage());
        }
    }

    @Override
    public void failedConnection(String msg){
        stage.close();
    }

    @Override
    public void start() {
        javafx.application.Application.launch(GuiMain.class);
    }

    @Override
    public void printLobbies(ArrayList<ReturnLobbiesMessage.availableGameLobbies> availableGameLobbies) {
        setLayout("landingPage.fxml");
        currentController.setLobbies(availableGameLobbies);
        Platform.runLater(()->stage.setScene(currentScene));
    }

    @Override
    public void createNewGame() {

    }

    @Override
    public void joinExistingGame(ArrayList<ReturnLobbiesMessage.availableGameLobbies> availableGameLobbies) {

    }

    @Override
    public void printMsg(String msg) {

    }

    @Override
    public void preGameChoice(ArrayList<LeaderCard> leaders, int numberOfResources) {
        setLayout("preGameChoice.fxml");
        currentController.setPreGameChoice(leaders,numberOfResources);
        Platform.runLater(()->stage.setScene(currentScene));
    }

    @Override
    public void waitingForTurn() {

    }

    @Override
    public void yourTurn() {

    }

    @Override
    public void endGame(ArrayList<PlayerPoints> scoreboard) {

    }

    @Override
    public void endGame(boolean lorenzoWin, int playerPoints) {

    }

    @Override
    public void endTurn() {

    }

    @Override
    public void buyCard() {

    }

    @Override
    public void takeResourcesFromMarket() {

    }

    @Override
    public void startProduction() {

    }

    @Override
    public void leaderAction(ArrayList<LeaderCard> passedLeader, boolean isDiscard) {

    }

    @Override
    public void organizeResources() {

    }

    @Override
    public void vaticanReportActivated(int victoryPoints, ArrayList<String> nicknames) {

    }

    @Override
    public void clearView() {

    }
}
