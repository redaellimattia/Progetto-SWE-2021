package it.polimi.ingsw.view.gui;
import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.network.client.ClientManager;
import it.polimi.ingsw.network.client.PlayerPoints;
import it.polimi.ingsw.network.messages.serverMessages.ReturnLobbiesMessage;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.gui.controllers.ClientDashboardController;
import it.polimi.ingsw.view.gui.controllers.GuiController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;


public class GuiManager implements View, GuiObserver{
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
    public void printLobbies(ArrayList<ReturnLobbiesMessage.availableGameLobbies> availableGameLobbies,String message) {
        Platform.runLater(()->setLobbiesPage(availableGameLobbies,message));
        setNextScene();
    }
    public void setLobbiesPage(ArrayList<ReturnLobbiesMessage.availableGameLobbies> availableGameLobbies,String message){
        setLayout("landingPage.fxml");
        currentController.setLobbies(availableGameLobbies);
        if(message != null)
            currentController.setTextForWaiting(message);
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
        Platform.runLater(()->goToPregame(leaders, numberOfResources));
        setNextScene();
    }
    public void goToPregame(ArrayList<LeaderCard> leaders, int numberOfResources){
        setLayout("preGameChoice.fxml");
        currentController.setPreGameChoice(leaders,numberOfResources);
    }

    @Override
    public void waitingForTurn() {
        //TO BE PASSED TRUE FOR A WAITING PLAYER
        Platform.runLater(() -> goToClientDashboard(false));
        setNextScene();
    }

    @Override
    public void yourTurn() {
        Platform.runLater(()->goToClientDashboard(false));
        setNextScene();
    }

    public void goToClientDashboard(boolean watchingPlayer){
        setLayout("clientDashboard.fxml");
        currentController.setPlayer(clientManager.getThisClientDashboard(),watchingPlayer);
    }

    public void callDashboard(){
        setLayout("clientDashboard.fxml");
        currentController.setPlayer(getClientManager().getThisClientDashboard(), false);
    }

    public void watchPlayer(String player){
        setLayout("clientDashboard.fxml");
        PlayerDashboard chosenPlayer = null;
        for(PlayerDashboard p:clientManager.getGameStatus().getPlayers()){
            if(p.getNickname().equals(player))
                chosenPlayer = p;
        }
        currentController.setPlayer(chosenPlayer, true);
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

    public void setNextScene(){
        Platform.runLater(()->stage.setScene(currentScene));
    }

    @Override
    public void updateShop(){
        currentController.updateShop();
    }
    @Override
    public void updatePathPosition(String nickname){
        currentController.updatePathPosition(nickname);
    }
    @Override
    public void updateLeaders(String nickname){
        currentController.updateLeaders(nickname);
    }
    @Override
    public void updateMarket(){currentController.updateMarket();}
    @Override
    public void updateArrayDeposits(String nickname){
        currentController.updateArrayDeposits(nickname);
    }
    @Override
    public void updateChest(String nickname){
        currentController.updateChest(nickname);
    }
    @Override
    public void updateDevCards(String nickname){
        currentController.updateDevCards(nickname);
    }
    @Override
    public void updateStorage(String nickname){
        currentController.updateStorage(nickname);
    }
    @Override
    public void updateVaticanReports(){
        currentController.updateVaticanReports();
    }
    @Override
    public void updateBufferProduction(String nickname){
        currentController.updateBufferProduction(nickname);
    }
}
