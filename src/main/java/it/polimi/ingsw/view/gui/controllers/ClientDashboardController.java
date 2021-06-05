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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.ArrayList;

public class ClientDashboardController extends GuiController{
    @FXML
    private AnchorPane leaderProduction1,leaderProduction2;
    @FXML
    private Button startBasicProduction,devCardProduction1,devCardProduction2,devCardProduction3,endProduction;
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
    private boolean startingFirstProduction;

    @Override
    public void setPlayer(PlayerDashboard playerDashboard,boolean watchingPlayer) {
        this.playerDashboard = playerDashboard;
        setFaithPath(playerDashboard.getPathPosition());
        setDevCards(playerDashboard.getDevCards());
        setLeaderCards(playerDashboard.getLeaderCards(),watchingPlayer);
        setStorage(playerDashboard.getStorage(),firstRowImage,secondRowImage1,secondRowImage2,thirdRowImage1,thirdRowImage2,thirdRowImage3);
        setChest(playerDashboard.getChest(),xCoin,xShield,xRock,xServant);
        setAbilityDeposit(playerDashboard.getArrayDeposit(),playerDashboard.getLeaderCards());
        if(watchingPlayer){
            if(playerDashboard.isLorenzo())
                setImage(faithPath,"/img/punchboard/croce.png");
            setImage(board,"/img/board/inactiveBoard.jpg");
            nickname.setText(playerDashboard.getNickname());
            nickname.setVisible(true);
            bwFaithPath.setVisible(true);
            marketButton.setVisible(false);
            shopButton.setVisible(false);
            productionButton.setVisible(false);
            endTurnButton.setVisible(false);
            otherPlayers.setVisible(false);
            backHome.setVisible(true);
        }
        else{
            if(!clientManager.isMyTurn()){
                marketButton.setDisable(true);
                productionButton.setDisable(true);
                endTurnButton.setDisable(true);
            }
            if(clientManager.isMyTurn()&&clientManager.isMainActionDone()){
                productionButton.setDisable(true);
                endTurnButton.setDisable(false);
            }
            if(clientManager.isMyTurn()&&!clientManager.isMainActionDone()){
                if(!clientManager.canDoProduction())
                    productionButton.setDisable(true);
                endTurnButton.setDisable(true);
            }
        }
    }

    @FXML
    @Override
    public void initialize() {
        super.setGuiManager(GuiManager.getInstance());
        this.clientManager = getGuiManager().getClientManager();
        setVaticanReport(clientManager.getGameStatus().getvReports());
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
        startingFirstProduction = false;
    }

    @Override
    public void setProductionOnGoing(){
        endProduction.setVisible(true);
        if(!productionButton.isDisabled())
            productionButton.setDisable(true);
        startProduction(null);
    }

    private void setFaithPath(int position){
        faithPath.setLayoutX(faithPos[position].getX());
        faithPath.setLayoutY(faithPos[position].getY());
    }

    private void setDevCards(DeckDashboard[] devCards){
        for(int i=0;i<3;i++){
            ArrayList<DevelopmentCard> deck = devCards[i].getDeck();
            if(deck.size()!=0){
                for(DevelopmentCard d:deck)
                    switch (d.getLevel()){
                        case 1: insertLevelOneCard(d.getId(),i);
                        break;
                        case 2: insertLevelTwoCard(d.getId(),i);
                            break;
                        case 3: insertLevelThreeCard(d.getId(),i);
                            break;
                    }
            }
        }
    }

    private void insertLevelOneCard(int ID,int i){
        insertCard(ID, i, firstPositionLevel1, secondPositionLevel1, thirdPositionLevel1);
    }
    private void insertLevelTwoCard(int ID,int i){
        insertCard(ID, i, firstPositionLevel2, secondPositionLevel2, thirdPositionLevel2);
    }
    private void insertLevelThreeCard(int ID,int i){
        insertCard(ID, i, firstPositionLevel3, secondPositionLevel3, thirdPositionLevel3);
    }

    private void insertCard(int ID, int i, ImageView firstPosition, ImageView secondPosition, ImageView thirdPosition) {
        switch (i){
            case 0: setImage(firstPosition,"/img/cards/front/DevelopmentCards/"+ID+".png");
                break;
            case 1: setImage(secondPosition,"/img/cards/front/DevelopmentCards/"+ID+".png");
                break;
            case 2: setImage(thirdPosition,"/img/cards/front/DevelopmentCards/"+ID+".png");
                break;
        }
    }

    private void setLeaderCards(ArrayList<LeaderCard> leaderCards,boolean watchingPlayer){
        int i=0;
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

    private void setVaticanReport(VaticanReport[] vReports){
        if(vReports[0].isUsed())
            setImage(vaticanReport2,"/img/punchboard/pope_favor1_back.png");
        if(vReports[1].isUsed())
            setImage(vaticanReport3,"/img/punchboard/pope_favor2_back.png");
        if(vReports[2].isUsed())
            setImage(vaticanReport4,"/img/punchboard/pope_favor3_back.png");
    }



    public void goToMarket(MouseEvent mouseEvent) {
        marketButton.setDisable(true);
        Platform.runLater(()-> getGuiManager().setLayout("marketAction.fxml"));
        getGuiManager().setNextScene();
    }
    public void goToShop(MouseEvent mouseEvent) {
        shopButton.setDisable(true);
        Platform.runLater(()->getGuiManager().setLayout("shopView.fxml"));
        getGuiManager().setNextScene();
    }

    /**
     * Starts the productionAction, disables all other possible mainActions, once a production is done, it will be disabled
     *
     * @param mouseEvent click
     */
    public void startProduction(MouseEvent mouseEvent) {
        shopButton.setDisable(true);
        marketButton.setDisable(true);
        productionButton.setDisable(true);
        otherPlayers.setDisable(true);
        setAvailableProductions();
        endProduction.setVisible(true);
    }

    private void setAvailableProductions(){
        if(!clientManager.isBasicProductionDone())
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
            ResourceCount resource = new ResourceCount(0,0,0,0,0);
            for(int i=0;i<playerDashboard.getLeaderCards().size();i++){
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

    public void resetProduction(){
        startBasicProduction.setVisible(false);
        devCardProduction1.setVisible(false);
        devCardProduction2.setVisible(false);
        devCardProduction3.setVisible(false);
        leaderProduction1.setVisible(false);
        leaderProduction2.setVisible(false);
        shopButton.setDisable(false);
        marketButton.setDisable(false);
        productionButton.setDisable(false);
        endProduction.setVisible(false);
    }

    public void startBasicProduction(MouseEvent mouseEvent) {
        launchChooseResources(true,true,null,null);
    }

    public void endTurn(MouseEvent mouseEvent) {
        setImage(board,"/img/board/inactiveBoard.jpg");
        bwFaithPath.setVisible(true);
        Platform.runLater(()->clientManager.endTurn());
    }

    public void discardLeader2(MouseEvent mouseEvent) {
        Platform.runLater(()->clientManager.discardLeader(playerDashboard.getLeaderCards().get(1)));
    }

    public void playLeader2(MouseEvent mouseEvent) {
        Platform.runLater(()->clientManager.playLeader(playerDashboard.getLeaderCards().get(1)));
    }

    public void discardLeader1(MouseEvent mouseEvent) {
        Platform.runLater(()->clientManager.discardLeader(playerDashboard.getLeaderCards().get(0)));
    }

    public void playLeader1(MouseEvent mouseEvent) {
        Platform.runLater(()->clientManager.playLeader(playerDashboard.getLeaderCards().get(0)));
    }

    public void selectPlayer(ActionEvent actionEvent) {
        String selectedPlayer = otherPlayers.getSelectionModel().getSelectedItem().toString();
        Platform.runLater(()->getGuiManager().watchPlayer(selectedPlayer));
    }

    @Override
    public void updatePathPosition(String nickname){
        if(nickname.equals(playerDashboard.getNickname()))
            setFaithPath(playerDashboard.getPathPosition());
    }

    @Override
    public void updateLeaders(String nickname){
        if(nickname.equals(playerDashboard.getNickname()))
            setLeaderCards(playerDashboard.getLeaderCards(),!playerDashboard.getNickname().equals(clientManager.getNickname()));
    }

    @Override
    public void updateArrayDeposits(String nickname){
        if(nickname.equals(playerDashboard.getNickname()))
            setAbilityDeposit(playerDashboard.getArrayDeposit(),playerDashboard.getLeaderCards());
    }
    @Override
    public void updateChest(String nickname){
        if(nickname.equals(playerDashboard.getNickname()))
            Platform.runLater(()->setChest(playerDashboard.getChest(),xCoin,xShield,xRock,xServant));
    }
    @Override
    public void updateDevCards(String nickname){
        if(nickname.equals(playerDashboard.getNickname()))
            setDevCards(playerDashboard.getDevCards());
    }
    @Override
    public void updateStorage(String nickname){
        if(nickname.equals(playerDashboard.getNickname()))
            setStorage(playerDashboard.getStorage(),firstRowImage,secondRowImage1,secondRowImage2,thirdRowImage1,thirdRowImage2,thirdRowImage3);
    }
    @Override
    public void updateVaticanReports(){
        setVaticanReport(clientManager.getGameStatus().getvReports());
    }

    public void backToHome(MouseEvent mouseEvent) {
        getGuiManager().callDashboard();
    }


    public void startDevCardProduction(MouseEvent mouseEvent) {
        String ID = mouseEvent.getPickResult().getIntersectedNode().getId();
        switch (ID){
            case "devCardProduction1": goToPayment(playerDashboard.getDevCards()[0].getFirst());
            break;
            case "devCardProduction2": goToPayment(playerDashboard.getDevCards()[1].getFirst());
                break;
            case "devCardProduction3": goToPayment(playerDashboard.getDevCards()[2].getFirst());
                break;
        }
    }

    private void goToPayment(DevelopmentCard card){
        getGuiManager().setLayout("payment.fxml");
        getGuiManager().getCurrentController().setDevCardProduction(card);
    }

    public void endProduction(MouseEvent mouseEvent) {
        if(!clientManager.isMainActionDone())
            resetProduction();
        else {
            endProduction.setVisible(false);
            marketButton.setDisable(false);
            shopButton.setDisable(false);
            otherPlayers.setDisable(false);
            Platform.runLater(() -> clientManager.endAction());
        }
    }

    public void startLeaderProduction(MouseEvent mouseEvent) {
        String ID = mouseEvent.getPickResult().getIntersectedNode().getId();
        LeaderCard card = null;
        switch (ID){
            case "leaderProduction1": card = playerDashboard.getLeaderCards().get(0);
                break;
            case "leaderProduction2": card = playerDashboard.getLeaderCards().get(1);
                break;
        }
        launchChooseResources(false,false,card,null);
    }

    //FAITH PATH IMG POSITION
    private static class FaithPos{
        private int x;
        private int y;

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
