package it.polimi.ingsw.view.gui;
import it.polimi.ingsw.model.PlayerDashboard;
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

/**
 * Class that handles the GUI view
 */
public class GuiManager implements View, GuiObserver{
    private final ClientManager clientManager;

    private GuiController currentController;
    private Scene currentScene;
    private Stage stage;
    private final ArrayList<String> log;
    private static GuiManager instance;


    public GuiManager(ClientManager clientManager) {
        this.clientManager = clientManager;
        instance = this;
        log = new ArrayList<>();
    }
    public ArrayList<String> getLog(){
        return log;
    }
    public ClientManager getClientManager() {
        return clientManager;
    }
    public GuiController getCurrentController() {
        return currentController;
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
     * Setting the scene on the passed path, updating also the controller for the specific fxml
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
     * Called if the connection is refused from the server
     * @param msg message to be printed on terminal
     */
    @Override
    public void failedConnection(String msg){
        System.out.println(msg);
        Platform.runLater(()->stage.close());
    }

    /**
     * Launch of the Gui Application with the splash screen
     */
    @Override
    public void start() {
        javafx.application.Application.launch(GuiMain.class);
    }

    /**
     * Call the landing page with the passed available lobbies, if there are any
     * @param availableGameLobbies lobbies passed by the server
     * @param message optional message to be printed
     */
    @Override
    public void printLobbies(ArrayList<ReturnLobbiesMessage.availableGameLobbies> availableGameLobbies,String message) {
        Platform.runLater(()->setLobbiesPage(availableGameLobbies,message));
    }

    /**
     * Called in the runlater of printLobbies, set the layout and parameters on the controller
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
     * Add to the log a message that will be printed in the clientDashboardController scrollpane
     * @param msg msg coming from the server
     */
    @Override
    public void printMsg(String msg) {
        log.add(msg);
        if(!clientManager.isMyTurn())
         Platform.runLater(this::updateLogger);
    }

    /**
     * Define how many resources a player can choose in the pregame and which four leaders are given him
     * to choose two
     * @param leaders arraylist of the four leaders
     * @param numberOfResources number of resources he can choose
     */
    @Override
    public void preGameChoice(ArrayList<LeaderCard> leaders, int numberOfResources) {
        Platform.runLater(()->goToPregame(leaders, numberOfResources));
    }

    /**
     * Pregame choices
     *
     * @param leaders avaialble leaders
     * @param numberOfResources amount of resources that are needed to be chosen
     */
    public void goToPregame(ArrayList<LeaderCard> leaders, int numberOfResources){
        setLayout("preGameChoice.fxml");
        currentController.setPreGameChoice(leaders,numberOfResources);
    }

    @Override
    public void waitingForTurn() {
        Platform.runLater(() -> goToClientDashboard(false));
    }

    @Override
    public void yourTurn() {
        log.add("It's your turn!");
        Platform.runLater(this::callDashboard);
    }

    /**
     * Go back to the player's dashboard
     * @param watchingPlayer true if the dashboard is not of this client
     */
    public void goToClientDashboard(boolean watchingPlayer){
        setLayout("clientDashboard.fxml");
        currentController.setPlayer(clientManager.getThisClientDashboard(),watchingPlayer);
    }

    /**
     * Go back to the player's dashboard
     */
    public void callDashboard(){
        setLayout("clientDashboard.fxml");
        currentController.setPlayer(getClientManager().getThisClientDashboard(), false);
        Platform.runLater(this::updateLogger);
    }

    /**
     * Sets the watching another player dashboard scene
     * @param player player chosen to be watched
     */
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
        Platform.runLater(()->goToEndGameMulti(scoreboard));
    }

    /**
     * Switch to multiplayer endgame scene
     * @param scoreboard scoreboard of the game
     */
    private void goToEndGameMulti(ArrayList<PlayerPoints> scoreboard){
        setLayout("endGame.fxml");
        currentController.setEndGame(scoreboard);
        stage.setScene(currentScene);
    }

    /**
     * Switch to singleplayer endgame scene
     * @param lorenzoWin true if lorenzo won
     * @param playerPoints points of the player
     */
    private void goToEndGameSingle(boolean lorenzoWin, int playerPoints){
        setLayout("endGame.fxml");
        currentController.setEndGame(lorenzoWin,playerPoints);
        stage.setScene(currentScene);
    }

    @Override
    public void endGame(boolean lorenzoWin, int playerPoints) {
        Platform.runLater(()->goToEndGameSingle(lorenzoWin,playerPoints));
    }

    @Override
    public void endTurn() {
        log.clear();
        Platform.runLater(clientManager::endTurn);
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

    /**
     *
     * @param victoryPoints value of the vatican report
     * @param nicknames player affected by the vatican report
     */
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
        Platform.runLater(this::updateLogger);
    }

    @Override
    public void updateView() {}

    /**
     *
     * @param from the starting position of the move
     * @param to the target position of the move
     * @param num number of resources to move
     */
    public void sendMoveToLeader(int from, int to, int num){
        getClientManager().sendMoveToLeader(from,to,num);
        Platform.runLater(this::callDashboard);
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
    @Override
    public void updateLogger(){
        currentController.updateLogger(log);
    }

    public void clearLog(){
        log.clear();
    }

}
