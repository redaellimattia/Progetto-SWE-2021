package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.card.DepositAbility;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.card.ProductionAbility;
import it.polimi.ingsw.network.client.ClientManager;
import it.polimi.ingsw.view.gui.GuiManager;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import java.util.ArrayList;

/**
 * Player's dashboard controller
 */
public class ClientDashboardController extends GuiController{
    @FXML
    private AnchorPane bufferProduction;
    @FXML
    private Label xCoinBufferProduction,xServantBufferProduction,xShieldBufferProduction,xRockBufferProduction;
    @FXML
    private AnchorPane leaderProduction1,leaderProduction2;
    @FXML
    private Button startBasicProduction,devCardProduction1,devCardProduction2,devCardProduction3,endProduction,organizeButton,firstStorage,secondStorage,thirdStorage,firstLeaderButton
            ,secondLeaderButton;
    @FXML
    private Text nickname;
    @FXML
    private ComboBox otherPlayers;
    @FXML
    private AnchorPane playLeader1,discardLeader1,playLeader2,discardLeader2;
    @FXML
    private ImageView board,bwFaithPath;
    @FXML
    private TextFlow textFlowLog;
    @FXML
    private ImageView firstAbilityDeposit1,firstAbilityDeposit2,secondAbilityDeposit1,secondAbilityDeposit2;
    @FXML
    private AnchorPane firstAbilityDeposit,secondAbilityDeposit;
    @FXML
    private ImageView vaticanReport2,vaticanReport3,vaticanReport4;
    @FXML //CHEST
    private Label xCoin,xServant,xShield,xRock;
    @FXML //LEADCARDS
    private ImageView leaderCard1,leaderCard2;
    @FXML //DEVCARDS
    private ImageView firstPositionLevel1,firstPositionLevel2,firstPositionLevel3,secondPositionLevel1,secondPositionLevel2,secondPositionLevel3,
            thirdPositionLevel1,thirdPositionLevel2,thirdPositionLevel3;
    @FXML //STORAGE
    private ImageView firstRowImage,secondRowImage1,secondRowImage2,thirdRowImage1,thirdRowImage2,thirdRowImage3;
    @FXML
    private Button marketButton,shopButton,productionButton,endTurnButton,backHome;
    @FXML //FAITHPATH
    private ImageView faithPath;
    private PlayerDashboard playerDashboard;
    private ClientManager clientManager;
    private FaithPos[] faithPos;
    private int firstCounterTopSwapped,numberOfResourcesLeaderMove;

    /**
     * Sets playerdashboard, if the client is watching a player or not, and the ArrayList of log
     *
     * @param playerDashboard dashboard the client wants to see
     * @param watchingPlayer true if the client is watching another player not himself
     */
    @Override
    public void setPlayer(PlayerDashboard playerDashboard,boolean watchingPlayer) {
        this.playerDashboard = playerDashboard;
        setFaithPath(playerDashboard.getPathPosition());
        setDevCards(playerDashboard.getDevCards(),firstPositionLevel1,secondPositionLevel1,thirdPositionLevel1,firstPositionLevel2,secondPositionLevel2,thirdPositionLevel2
                                                 ,firstPositionLevel3,secondPositionLevel3,thirdPositionLevel3);
        setStorage(playerDashboard.getStorage(),firstRowImage,secondRowImage1,secondRowImage2,thirdRowImage1,thirdRowImage2,thirdRowImage3);
        setChest(playerDashboard.getChest(),xCoin,xShield,xRock,xServant);
        setLeaderCards(playerDashboard.getLeaderCards(),watchingPlayer);
        setAbilityDeposit(playerDashboard.getArrayDeposit(),playerDashboard.getLeaderCards());
        firstStorage.setVisible(false);
        secondStorage.setVisible(false);
        thirdStorage.setVisible(false);
        firstLeaderButton.setVisible(false);
        secondLeaderButton.setVisible(false);
        firstCounterTopSwapped = -1;
        numberOfResourcesLeaderMove=0;
        if(!getGuiManager().getClientManager().canMoveResources())
            organizeButton.setDisable(true);
        if(watchingPlayer){
            if(playerDashboard.isLorenzo())
                setImage(faithPath,"/img/punchboard/croce.png");
            setImage(board,"/img/board/inactiveBoard.jpg");
            nickname.setText(playerDashboard.getNickname());
            nickname.setVisible(true);
            bwFaithPath.setVisible(true);
            marketButton.setVisible(false);
            shopButton.setVisible(false);
            organizeButton.setVisible(false);
            productionButton.setVisible(false);
            endTurnButton.setVisible(false);
            otherPlayers.setVisible(false);
            backHome.setVisible(true);
        }
        else{

            if(!clientManager.isMyTurn()){
                productionButton.setDisable(true);
                endTurnButton.setDisable(true);
                organizeButton.setDisable(true);
            }
            if(clientManager.isMyTurn()&&clientManager.isMainActionDone()){
                productionButton.setDisable(true);
                endTurnButton.setDisable(false);
            }
            if(clientManager.isMyTurn()&&!clientManager.isMainActionDone()){
                if(clientManager.isProductionActionOnGoing())
                    setProductionOnGoing();
                else
                    if(!clientManager.canDoProduction())
                        productionButton.setDisable(true);
                endTurnButton.setDisable(true);
            }
        }
    }

    /**
     * Init the guiManager, the vaticanReports, the otherPlayers and the positions on the faithPath
     */
    @FXML
    @Override
    public void initialize() {
        super.setGuiManager(GuiManager.getInstance());
        this.clientManager = getGuiManager().getClientManager();
        setVaticanReport(clientManager.getGameStatus().getReports());
        ObservableList<String> players = FXCollections.observableArrayList();
        for(PlayerDashboard p:clientManager.getGameStatus().getPlayers()){
            if(!p.getNickname().equals(clientManager.getThisClientDashboard().getNickname()))
                players.add(p.getNickname());
        }
        otherPlayers.setItems(players);
        faithPos = new FaithPos[]{
                new FaithPos(4,102),new FaithPos(56,102),new FaithPos(106,102),new FaithPos(106,52),new FaithPos(106,0),
                new FaithPos(156,0),new FaithPos(205,0),new FaithPos(253,0),new FaithPos(303,0),
                new FaithPos(355,0),new FaithPos(355,52),new FaithPos(355,99),new FaithPos(404,101),new FaithPos(453,101),
                new FaithPos(501,101),new FaithPos(551,101),new FaithPos(597,98),new FaithPos(597,51),
                new FaithPos(597,0),new FaithPos(648,0),new FaithPos(697,0),new FaithPos(746,0),new FaithPos(794,0),
                new FaithPos(844,0),new FaithPos(890,0)};
    }

    /**
     * Change button visibility if the production is still ongoing
     */
    @Override
    public void setProductionOnGoing(){
        endProduction.setVisible(true);
        if(!productionButton.isDisabled())
            productionButton.setDisable(true);
        startProduction();
    }

    /**
     * Setting position on the faith path
     *
     * @param position position of the player
     */
    private void setFaithPath(int position){
        if(position<25) {
            faithPath.setLayoutX(faithPos[position].getX());
            faithPath.setLayoutY(faithPos[position].getY());
        }
    }

    /**
     * Setting leaderCards of the player
     *
     * @param leaderCards LeaderCards of the player
     * @param watchingPlayer true if the client is watching another player
     */
    private void setLeaderCards(ArrayList<LeaderCard> leaderCards,boolean watchingPlayer){
        int i;
        for(i=0;i<leaderCards.size();i++){
            LeaderCard l = leaderCards.get(i);
            if(!l.isInGame()&&!watchingPlayer&&clientManager.isMyTurn()){
                if(i==0){
                    discardLeader1.setVisible(true);
                    if(clientManager.isRequirementPossible(l.getRequirement()))
                        playLeader1.setVisible(true);
                }
                else {
                    discardLeader2.setVisible(true);
                    if(clientManager.isRequirementPossible(l.getRequirement()))
                        playLeader2.setVisible(true);
                }
            }
            else{
                if(i==0) {
                    playLeader1.setVisible(false);
                    discardLeader1.setVisible(false);
                }
                if(i==1) {
                    playLeader2.setVisible(false);
                    discardLeader2.setVisible(false);
                }
            }
            if(i==0) {
                if (!watchingPlayer || l.isInGame())
                    setImage(leaderCard1, "/img/cards/front/LeaderCards/" + l.getId() + ".png");
                else
                    setImage(leaderCard1, "/img/cards/back/leaderCardBack.png");
            }
            else {
                if (!watchingPlayer || l.isInGame())
                    setImage(leaderCard2, "/img/cards/front/LeaderCards/" + l.getId() + ".png");
                else
                    setImage(leaderCard2, "/img/cards/back/leaderCardBack.png");
            }
        }
        if(i==0){
            leaderCard1.setVisible(false);
            leaderCard2.setVisible(false);
            playLeader1.setVisible(false);
            playLeader2.setVisible(false);
            discardLeader1.setVisible(false);
            discardLeader2.setVisible(false);
        }
        if(i==1){
            leaderCard2.setVisible(false);
            playLeader2.setVisible(false);
            discardLeader2.setVisible(false);
        }
    }

    /**
     * Set ability deposit visibility
     *
     * @param arrayDeposit CounterTop of the arrayDeposit
     * @param leaderCards leaderCards of the player
     */
    private void setAbilityDeposit(ArrayList<CounterTop> arrayDeposit,ArrayList<LeaderCard> leaderCards){
        for(int i=0;i<leaderCards.size();i++) {
            LeaderCard l = leaderCards.get(i);
            if (l.isInGame() && l.getSpecialAbility() instanceof DepositAbility){
                if(i==0) {
                    firstAbilityDeposit.setVisible(true);
                    if(arrayDeposit.get(0).getResourceType().equals(l.getSpecialAbility().getResourceType()))
                        setCounterTopAbilityDeposit(firstAbilityDeposit1,firstAbilityDeposit2,arrayDeposit.get(0));
                    else
                        setCounterTopAbilityDeposit(firstAbilityDeposit1,firstAbilityDeposit2,arrayDeposit.get(1));
                }
                else {
                    secondAbilityDeposit.setVisible(true);
                    if(arrayDeposit.get(0).getResourceType().equals(l.getSpecialAbility().getResourceType()))
                        setCounterTopAbilityDeposit(secondAbilityDeposit1,secondAbilityDeposit2,arrayDeposit.get(0));
                    else
                        setCounterTopAbilityDeposit(secondAbilityDeposit1,secondAbilityDeposit2,arrayDeposit.get(1));
                }
            }
        }
    }

    /**
     * Setting images in a counterTop of the ability deposit of a leader
     *
     * @param image1 first ImageView
     * @param image2 second ImageView
     * @param counterTop counterTop chosen
     */
    private void setCounterTopAbilityDeposit(ImageView image1,ImageView image2,CounterTop counterTop){
        String path = getCounterTopImage(counterTop.getResourceType());
        switch (counterTop.getContent()){
            case 0: setImage(image1,null);
                    setImage(image2,null);
                    break;
            case 1: setImage(image1,path);
                break;
            case 2: setImage(image1, path);
                    setImage(image2, path);
                break;
        }
    }

    /**
     * Setting vatican report images
     *
     * @param vReports vReports updated
     */
    private void setVaticanReport(VaticanReport[] vReports){
        if(vReports[0].isUsed())
            setImage(vaticanReport2,"/img/punchboard/pope_favor1_back.png");
        if(vReports[1].isUsed())
            setImage(vaticanReport3,"/img/punchboard/pope_favor2_back.png");
        if(vReports[2].isUsed())
            setImage(vaticanReport4,"/img/punchboard/pope_favor3_back.png");
    }

    /**
     * Switch to market scene
     */
    public void goToMarket() {
        marketButton.setDisable(true);
        Platform.runLater(()-> getGuiManager().setLayout("marketAction.fxml"));
    }

    /**
     * Switch to shop scene
     */
    public void goToShop() {
        shopButton.setDisable(true);
        Platform.runLater(()->getGuiManager().setLayout("shopView.fxml"));
    }

    /**
     * Sets the available productions availability
     */
    private void setAvailableProductions(){
        if(clientManager.canDoBasicProduction(playerDashboard))
            startBasicProduction.setVisible(true);
        if(clientManager.canDoDevCardProduction(playerDashboard)) {
            for(int i=0;i<3;i++){
                if(playerDashboard.getDevCards()[i].getDeck().size()>0&&!clientManager.getDevCardProductionDone()[i]&&playerDashboard.getTotalResources().hasMoreOrEqualsResources(playerDashboard.getDevCards()[i].getFirst().getProductionPower().getInput())) {
                    switch(i){
                        case 0: devCardProduction1.setVisible(true);
                            break;
                        case 1: devCardProduction2.setVisible(true);
                            break;
                        case 2: devCardProduction3.setVisible(true);
                            break;
                    }
                }

            }
        }
        if(clientManager.canDoLeaderCardProduction(playerDashboard)){
            ResourceCount resource;
            for(int i=0;i<playerDashboard.getLeaderCards().size();i++){
                resource = new ResourceCount(0,0,0,0,0);
                LeaderCard l = playerDashboard.getLeaderCards().get(i);
                l.getSpecialAbility().getResourceType().add(resource, 1);
                if(l.isInGame()&&l.getSpecialAbility() instanceof ProductionAbility&&!clientManager.getLeaderCardProductionDone()[i]&&playerDashboard.getTotalResources().hasMoreOrEqualsResources(resource)){
                    if(i==0)
                        leaderProduction1.setVisible(true);
                    else
                        leaderProduction2.setVisible(true);
                }
            }
        }
    }

    /**
     * Number of resources to swap for a from leader swap (set by the modal stage)
     */
    public void setNumberOfResourcesLeaderMove(int numberOfResourcesLeaderMove) {
        this.numberOfResourcesLeaderMove = numberOfResourcesLeaderMove;
    }

    public int getFirstCounterTopSwapped() {
        return firstCounterTopSwapped;
    }

    /**
     * When endTurn button is clicked, end the turn
     */
    public void endTurn() {
        setImage(board,"/img/board/inactiveBoard.jpg");
        bwFaithPath.setVisible(true);
        getGuiManager().endTurn();
    }

    /**
     * When discardLeader2 is clicked, send the discardLeader message to the server
     */
    public void discardLeader2() {
        Platform.runLater(()->clientManager.discardLeader(playerDashboard.getLeaderCards().get(1)));
    }

    /**
     * When playLeader2 is clicked, send the discardLeader message to the server
     */
    public void playLeader2() {
        Platform.runLater(()->clientManager.playLeader(playerDashboard.getLeaderCards().get(1)));
    }

    /**
     * When discardLeader1 is clicked, send the discardLeader message to the server
     */
    public void discardLeader1() {
        Platform.runLater(()->clientManager.discardLeader(playerDashboard.getLeaderCards().get(0)));
    }

    /**
     * When playLeader1 is clicked, send the discardLeader message to the server
     */
    public void playLeader1() {
        Platform.runLater(()->clientManager.playLeader(playerDashboard.getLeaderCards().get(0)));
    }

    /**
     * After a player is selected, switch dashboard to watch him
     */
    public void selectPlayer() {
        String selectedPlayer = otherPlayers.getSelectionModel().getSelectedItem().toString();
        Platform.runLater(()->getGuiManager().watchPlayer(selectedPlayer));
    }

    /**
     * When an updatePathPosition arrives from the server, update the GUI
     *
     * @param nickname nickname of the player affected
     */
    @Override
    public void updatePathPosition(String nickname){
        if(nickname.equals(playerDashboard.getNickname()))
            setFaithPath(playerDashboard.getPathPosition());
    }

    /**
     * When an updateLeaders arrives from the server, update the GUI
     *
     * @param nickname nickname of the player affected
     */
    @Override
    public void updateLeaders(String nickname){
        if(nickname.equals(playerDashboard.getNickname()))
            setLeaderCards(playerDashboard.getLeaderCards(),!playerDashboard.getNickname().equals(clientManager.getNickname()));
        if(clientManager.canDoProduction()&&!clientManager.isMainActionDone()){
            productionButton.setDisable(false);
        }
    }

    /**
     * When an updateArrayDeposits arrives from the server, update the GUI
     *
     * @param nickname nickname of the player affected
     */
    @Override
    public void updateArrayDeposits(String nickname){
        if(nickname.equals(playerDashboard.getNickname()))
            setAbilityDeposit(playerDashboard.getArrayDeposit(),playerDashboard.getLeaderCards());
    }

    /**
     * When an updateChest arrives from the server, update the GUI
     *
     * @param nickname nickname of the player affected
     */
    @Override
    public void updateChest(String nickname){
        if(nickname.equals(playerDashboard.getNickname())) {
            Platform.runLater(() -> setChest(playerDashboard.getChest(), xCoin, xShield, xRock, xServant));
            if(clientManager.canPlayLeader())
                Platform.runLater(()->setLeaderCards(playerDashboard.getLeaderCards(),false));
        }
    }

    /**
     * When an updateBufferProduction arrives from the server, update the GUI
     *
     * @param nickname nickname of the player affected
     */
    @Override
    public void updateBufferProduction(String nickname){
        if(nickname.equals(playerDashboard.getNickname()))
            Platform.runLater(()->setChest(playerDashboard.getBufferProduction(),xCoinBufferProduction,xShieldBufferProduction,xRockBufferProduction,xServantBufferProduction));
    }

    /**
     * When an updateDevCards arrives from the server, update the GUI
     *
     * @param nickname nickname of the player affected
     */
    @Override
    public void updateDevCards(String nickname){
        if(nickname.equals(playerDashboard.getNickname()))
            setDevCards(playerDashboard.getDevCards(),firstPositionLevel1,secondPositionLevel1,thirdPositionLevel1,firstPositionLevel2,secondPositionLevel2,thirdPositionLevel2
                    ,firstPositionLevel3,secondPositionLevel3,thirdPositionLevel3);
        if(clientManager.canPlayLeader()){
            setLeaderCards(playerDashboard.getLeaderCards(),false);
        }
    }

    /**
     * When an updateStorage arrives from the server, update the GUI
     *
     * @param nickname nickname of the player affected
     */
    @Override
    public void updateStorage(String nickname){
        if(nickname.equals(playerDashboard.getNickname())) {
            setStorage(playerDashboard.getStorage(), firstRowImage, secondRowImage1, secondRowImage2, thirdRowImage1, thirdRowImage2, thirdRowImage3);
        }
    }

    /**
     * When an updateVaticanReport arrives from the server, update the GUI
     */
    @Override
    public void updateVaticanReports(){
        setVaticanReport(clientManager.getGameStatus().getReports());
    }

    /**
     * when some text is added to the log i reprint it
     *
     * @param log the logger
     */
    @Override
    public void updateLogger(ArrayList<String> log){
        printLog(log);
    }

    @FXML
    private void printLog(ArrayList<String> log) {
        for (String msg : log) {
            Text text = new Text(msg + "\n");
            text.setFill(Color.RED);
            text.getStyleClass().add("log");
            textFlowLog.getChildren().add(text);
        }
        getGuiManager().clearLog();
    }

    /**
     * Switch back to the client's dashboard
     */
    public void backToHome() {
        getGuiManager().callDashboard();
    }

    /**
     * Starts the productionAction, disables all other possible mainActions, once a production is done, it will be disabled
     *
     */
    public void startProduction() {
        resetProduction();
        clientManager.setProductionActionOnGoing(true);
        shopButton.setDisable(true);
        marketButton.setDisable(true);
        organizeButton.setDisable(true);
        productionButton.setDisable(true);
        otherPlayers.setDisable(true);
        setAvailableProductions();
        bufferProduction.setVisible(true);
        endProduction.setVisible(true);
    }

    /**
     * Start the basic production, launching the chooseOutput modal
     */
    public void startBasicProduction() {
        Stage modal = launchModal("/fxml/chooseResources.fxml");
        getGuiManager().getCurrentController().setModal(true,null,modal);
        Platform.runLater(modal::show);
    }

    /**
     * Starts the first leaderProduction when clicked, launching the choose resource modal
     */
    public void startLeaderProduction1() {
        launchChooseResourceLeaderProduction(playerDashboard.getLeaderCards().get(0));
    }

    /**
     * Starts the second leaderProduction when clicked, launching the choose resource modal
     */
    public void startLeaderProduction2() {
        launchChooseResourceLeaderProduction(playerDashboard.getLeaderCards().get(1));
    }

    /**
     * Starts the first devCard production when clicked, switching to the payment scene
     */
    public void startDevCardProduction1() {
        goToPayment(playerDashboard.getDevCards()[0].getFirst());
    }

    /**
     * Starts the second devCard production when clicked, switching to the payment scene
     */
    public void startDevCardProduction2() {
        goToPayment(playerDashboard.getDevCards()[1].getFirst());
    }

    /**
     * Starts the third devCard production when clicked, switching to the payment scene
     */
    public void startDevCardProduction3() {
        goToPayment(playerDashboard.getDevCards()[2].getFirst());
    }

    /**
     * Switch to payment scene
     *
     * @param card devCard chosen
     */
    private void goToPayment(DevelopmentCard card){
        getGuiManager().setLayout("payment.fxml");
        getGuiManager().getCurrentController().setDevCardProduction(card);
    }

    /**
     * When endProduction is clicked, if the mainAction is not done, then reset all the production, otherwise send the endAction to the server
     */
    public void endProduction() {
        clientManager.setProductionActionOnGoing(false);
        if(!clientManager.isMainActionDone()) {
            resetProduction();
            productionButton.setDisable(false);
        }
        else {
            resetProduction();
            Platform.runLater(() -> clientManager.endAction());
        }
    }

    /**
     * Launch choose resource modal for the leader card production
     * @param card LeaderCard chosen
     */
    private void launchChooseResourceLeaderProduction(LeaderCard card){
        Stage modal = launchModal("/fxml/chooseResources.fxml");
        getGuiManager().getCurrentController().setModal(false,card,modal);
        Platform.runLater(modal::show);
    }

    /**
     * When a productionAction is interrupted, disable production buttons and enable the other actions buttons
     */
    public void resetProduction(){
        disableProductionFields();
        shopButton.setDisable(false);
        marketButton.setDisable(false);
        endProduction.setVisible(false);
        organizeButton.setDisable(false);
        otherPlayers.setDisable(false);
    }

    /**
     * Disable the production fields
     */
    private void disableProductionFields(){
        startBasicProduction.setVisible(false);
        devCardProduction1.setVisible(false);
        devCardProduction2.setVisible(false);
        devCardProduction3.setVisible(false);
        leaderProduction1.setVisible(false);
        leaderProduction2.setVisible(false);
        bufferProduction.setVisible(false);
    }

    /**
     * Enable the organizing fields
     */
    public void startOrganizing() {
        if(getGuiManager().getClientManager().canMoveResources()) {
        shopButton.setDisable(true);
        marketButton.setDisable(true);
        organizeButton.setDisable(true);
        productionButton.setDisable(true);
        otherPlayers.setDisable(true);
        firstStorage.setVisible(true);
        secondStorage.setVisible(true);
        thirdStorage.setVisible(true);
        if(playerDashboard.getArrayDeposit().size()>0) {
            if (firstAbilityDeposit.isVisible())
                firstLeaderButton.setVisible(true);
            if (secondAbilityDeposit.isVisible())
                secondLeaderButton.setVisible(true);
        }
        }
    }

    /**
     * The first storage's row is selected to start/end the swap
     */
    public void firstStorageSelected() {
        if(firstCounterTopSwapped==-1) {
            firstCounterTopSwapped = 1;
        }
        else{
            if(firstCounterTopSwapped<4)
                getGuiManager().getClientManager().organizeStorage(firstCounterTopSwapped, 1);
            else
                clientManager.sendMoveFromLeader(firstCounterTopSwapped-3,1,numberOfResourcesLeaderMove);
            getGuiManager().callDashboard();
        }
    }

    /**
     * The second storage's row is selected to start/end the swap
     */
    public void secondStorageSelected() {
        if(firstCounterTopSwapped==-1)
            firstCounterTopSwapped = 2;
        else{
            if(firstCounterTopSwapped<4)
                getGuiManager().getClientManager().organizeStorage(firstCounterTopSwapped, 2);
            else
                clientManager.sendMoveFromLeader(firstCounterTopSwapped-3,2,numberOfResourcesLeaderMove);
            getGuiManager().callDashboard();
        }
    }

    /**
     * The third storage's row is selected to start/end the swap
     */
    public void thirdStorageSelected() {
        if(firstCounterTopSwapped==-1)
            firstCounterTopSwapped = 3;
        else{
            if(firstCounterTopSwapped<4)
                getGuiManager().getClientManager().organizeStorage(firstCounterTopSwapped, 3);
            else
                clientManager.sendMoveFromLeader(firstCounterTopSwapped-3,3,numberOfResourcesLeaderMove);

            getGuiManager().callDashboard();
        }
    }

    /**
     * The first leader deposit is selected to start/end the swap
     */
    public void firstLeaderSelected() {
        if(firstCounterTopSwapped==-1) {
            firstCounterTopSwapped = 4;
            Stage modal = launchModal("/fxml/askResourcesToMove.fxml");
            getGuiManager().getCurrentController().setModal(false,playerDashboard.getArrayDeposit().get(0),this,modal,0);
            Platform.runLater(modal::show);
        }
        else{
            Stage modal = launchModal("/fxml/askResourcesToMove.fxml");
            getGuiManager().getCurrentController().setModal(true,playerDashboard.getArrayDeposit().get(0),this,modal,1);
            Platform.runLater(modal::show);
        }
    }

    /**
     * The second leader deposit is selected to start/end the swap
     */
    public void secondLeaderSelected() {
        if(firstCounterTopSwapped==-1) {
            firstCounterTopSwapped = 5;
            Stage modal = launchModal("/fxml/askResourcesToMove.fxml");
            if(playerDashboard.getArrayDeposit().size()>1)
                getGuiManager().getCurrentController().setModal(false,playerDashboard.getArrayDeposit().get(1),this,modal,0);
            else
                getGuiManager().getCurrentController().setModal(false,playerDashboard.getArrayDeposit().get(0),this,modal,0);
            Platform.runLater(modal::show);
        }
        else{
            Stage modal = launchModal("/fxml/askResourcesToMove.fxml");
            if(playerDashboard.getArrayDeposit().size()>1)
                getGuiManager().getCurrentController().setModal(true,playerDashboard.getArrayDeposit().get(1),this,modal,2);
            else
                getGuiManager().getCurrentController().setModal(true,playerDashboard.getArrayDeposit().get(0),this,modal,2);
            Platform.runLater(modal::show);
        }
    }


    /**
     * Class used to identify the position of the image on the faith path
     */
    private static class FaithPos{
        private final int x;
        private final int y;

        public FaithPos(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }
}
