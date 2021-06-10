package it.polimi.ingsw.view.gui;
import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.enumeration.Resource;
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


public class GuiManager implements View, GuiObserver{
    private ClientManager clientManager;

    private GuiController currentController;
    private Scene currentScene;
    private Stage stage;
    private ArrayList<String> log;
    private static GuiManager instance;


    public GuiManager(ClientManager clientManager) {
        this.clientManager = clientManager;
        instance = this;
        log = new ArrayList<>();
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
    public void setCurrentController(GuiController currentController) {
        this.currentController = currentController;
    }
    public void setCurrentScene(Scene scene){
        this.currentScene = scene;
    }

    /**
     * setting the scene on the passed path, updating also the controller for the specific fxml
     * @param fileName the fxml path for the selected scene
     */
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

    /**
     * called if the connection is refused from the server
     * @param msg message to be printed on terminal
     */
    @Override
    public void failedConnection(String msg){
        Platform.runLater(()->stage.close());
    }

    /**
     * launch of the Gui Application with the splash screen
     */
    @Override
    public void start() {
        javafx.application.Application.launch(GuiMain.class);
    }

    /**
     * call the landing page with the passed available lobbies, if there are any
     * @param availableGameLobbies lobbies passed by the server
     * @param message optional message to be printed
     */
    @Override
    public void printLobbies(ArrayList<ReturnLobbiesMessage.availableGameLobbies> availableGameLobbies,String message) {
        Platform.runLater(()->setLobbiesPage(availableGameLobbies,message));
    }

    /**
     * called in the runlater of printLobbies, set the layout and parameters on the controller
     * @param availableGameLobbies the list of lobbies
     * @param message optional message
     */
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

    /**
     * add to the log a message that will be printed in the clientDashboardController scrollpane
     * @param msg
     */
    @Override
    public void printMsg(String msg) {
        log.add(msg);
    }

    /**
     * define how many resources a player can choose in the pregame and which four leaders are given him
     * to choose two
     * @param leaders arraylist of the four leaders
     * @param numberOfResources number of resources he can choose
     */
    @Override
    public void preGameChoice(ArrayList<LeaderCard> leaders, int numberOfResources) {
        Platform.runLater(()->goToPregame(leaders, numberOfResources));
    }

    /**
     *
     * @param leaders
     * @param numberOfResources
     */
    public void goToPregame(ArrayList<LeaderCard> leaders, int numberOfResources){
        setLayout("preGameChoice.fxml");
        currentController.setPreGameChoice(leaders,numberOfResources);
    }

    @Override
    public void waitingForTurn() {
        //TO BE PASSED TRUE FOR A WAITING PLAYER
        Platform.runLater(() -> goToClientDashboard(false));
        //setNextScene();
    }

    @Override
    public void yourTurn() {
        Platform.runLater(()->goToClientDashboard(false));
        //setNextScene();
    }

    public void goToClientDashboard(boolean watchingPlayer){
        setLayout("clientDashboard.fxml");
        currentController.setPlayer(clientManager.getThisClientDashboard(),watchingPlayer,log);
    }

    public void callDashboard(){
        setLayout("clientDashboard.fxml");
        currentController.setPlayer(getClientManager().getThisClientDashboard(), false,log);
        setNextScene();
    }

    public void watchPlayer(String player){
        setLayout("clientDashboard.fxml");
        PlayerDashboard chosenPlayer = null;
        for(PlayerDashboard p:clientManager.getGameStatus().getPlayers()){
            if(p.getNickname().equals(player))
                chosenPlayer = p;
        }
        currentController.setPlayer(chosenPlayer, true,log);
    }

    @Override
    public void endGame(ArrayList<PlayerPoints> scoreboard) {
        Platform.runLater(()->goToEndGameMulti(scoreboard));
    }
    private void goToEndGameMulti(ArrayList<PlayerPoints> scoreboard){
        setLayout("endGame.fxml");
        currentController.setEndGame(scoreboard);
    }
    private void goToEndGameSingle(boolean lorenzoWin, int playerPoints){
        setLayout("endGame.fxml");
        currentController.setEndGame(lorenzoWin,playerPoints);
    }
    @Override
    public void endGame(boolean lorenzoWin, int playerPoints) {
        Platform.runLater(()->goToEndGameSingle(lorenzoWin,playerPoints));
    }

    @Override
    public void endTurn() {
        log.clear();
        Platform.runLater(()->clientManager.endTurn());
    }

    @Override
    public void buyCard() {

    }

    @Override
    public void takeResourcesFromMarket() {

    }

    @Override
    public void startProduction() {
        currentController.setProductionOnGoing();
    }

    @Override
    public void leaderAction(ArrayList<LeaderCard> passedLeader, boolean isDiscard) {

    }

    @Override
    public void organizeResources() {

    }

    @Override
    public void vaticanReportActivated(int victoryPoints, ArrayList<String> nicknames) {
        String playerNickname = clientManager.getNickname();
        String toAdd ="";
        toAdd +="A vatican report worth "+victoryPoints+" victory points has been activated! \n";
        if(!nicknames.contains(playerNickname))
            toAdd+="Unfortunately, you weren't affected.\n";
        else
            toAdd+="Well done, you have been affected by the vatican report!\n";
        toAdd+="Other players affected by the vatican report: \n";
        for(int i=0;i<nicknames.size();i++){
            String nick = nicknames.get(i);
            if(!nick.equals(playerNickname)) {
                toAdd += nick +"\n";
                if(i!=nicknames.size()-1&&(i==nicknames.size()-2&&!nicknames.get(i+1).equals(playerNickname)))
                    toAdd+=" - ";
            }
        }
        log.add(toAdd);
    }

    @Override
    public void clearView() {

    }

    public void sendMoveToLeader(Resource res, int num){
        getClientManager().moveLeaderResources(res,num,false);
        Platform.runLater(this::callDashboard);
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
        Platform.runLater(()->currentController.updateDevCards(nickname));
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
        Platform.runLater(()->currentController.updateBufferProduction(nickname));
    }
}
