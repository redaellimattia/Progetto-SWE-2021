package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.card.DepositAbility;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.enumeration.Resource;
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
                marketButton.setDisable(true);
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
                new FaithPos(0,105),new FaithPos(52,105),new FaithPos(102,105),new FaithPos(102,58),new FaithPos(101,2),
                new FaithPos(152,2),new FaithPos(201,2),new FaithPos(249,2),new FaithPos(299,2),
                new FaithPos(351,103),new FaithPos(351,56),new FaithPos(350,2),new FaithPos(400,105),new FaithPos(449,105),
                new FaithPos(497,105),new FaithPos(547,105),new FaithPos(593,102),new FaithPos(593,55),
                new FaithPos(592,1),new FaithPos(644,3),new FaithPos(693,5),new FaithPos(742,5),new FaithPos(790,5),
                new FaithPos(840,5),new FaithPos(886,2)};
    }

    private void setFaithPath(int position){
        setFaithPath(faithPos[position]);
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

    private void setFaithPath(FaithPos fp){
        faithPath.setLayoutX(fp.getX());
        faithPath.setLayoutY(fp.getY());
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

    public void startProduction(MouseEvent mouseEvent) {
    }

    public void endTurn(MouseEvent mouseEvent) {
        setImage(board,"/img/board/inactiveBoard.jpg");
        bwFaithPath.setVisible(true);
        Platform.runLater(()->clientManager.endTurn());
    }

    public void otherPlayers(MouseEvent mouseEvent) {
        //Enter PlayerChoose scene
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
            setChest(playerDashboard.getChest(),xCoin,xShield,xRock,xServant);
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
