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
import javafx.scene.text.TextFlow;

import java.util.ArrayList;

public class ClientDashboardController extends GuiController{
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
    @FXML //UP LEFT BUTTONS
    private Button marketButton,shopButton,productionButton,endTurnButton;
    @FXML //FAITHPATH
    private ImageView faithPath0,faithPath1,faithPath2,faithPath3,faithPath4,faithPath5,faithPath6,faithPath7,faithPath8,faithPath9,faithPath10,
            faithPath11,faithPath12,faithPath13,faithPath14,faithPath15,faithPath16,faithPath17,faithPath18,faithPath19,faithPath20,faithPath21,
            faithPath22,faithPath23,faithPath24;
    private PlayerDashboard playerDashboard;
    private ClientManager clientManager;

    @Override
    public void setPlayer(PlayerDashboard playerDashboard,boolean watchingPlayer) {
        this.playerDashboard = playerDashboard;
        setFaithPath(playerDashboard.getPathPosition());
        setDevCards(playerDashboard.getDevCards());
        setLeaderCards(playerDashboard.getLeaderCards(),watchingPlayer);
        setStorage(playerDashboard.getStorage());
        setChest(playerDashboard.getChest());
        setAbilityDeposit(playerDashboard.getArrayDeposit(),playerDashboard.getLeaderCards());
        if(watchingPlayer){
            setImage(board,"/img/board/inactiveBoard.jpg");
            bwFaithPath.setVisible(true);
            marketButton.setVisible(false);
            shopButton.setVisible(false);
            productionButton.setVisible(false);
            endTurnButton.setVisible(false);
            otherPlayers.setVisible(false);
        }
        else{
            if(!clientManager.isMyTurn()){
                marketButton.setDisable(true);
                shopButton.setDisable(true);
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
    }

    private void setFaithPath(int position){
        switch (position){
            case 0: faithPath0.setVisible(true);
                break;
            case 1: faithPath1.setVisible(true);
                break;
            case 2: faithPath2.setVisible(true);
                break;
            case 3: faithPath3.setVisible(true);
                break;
            case 4: faithPath4.setVisible(true);
                break;
            case 5: faithPath5.setVisible(true);
                break;
            case 6: faithPath6.setVisible(true);
                break;
            case 7: faithPath7.setVisible(true);
                break;
            case 8: faithPath8.setVisible(true);
                break;
            case 9: faithPath9.setVisible(true);
                break;
            case 10: faithPath10.setVisible(true);
                break;
            case 11: faithPath11.setVisible(true);
                break;
            case 12: faithPath12.setVisible(true);
                break;
            case 13: faithPath13.setVisible(true);
                break;
            case 14: faithPath14.setVisible(true);
                break;
            case 15: faithPath15.setVisible(true);
                break;
            case 16: faithPath16.setVisible(true);
                break;
            case 17: faithPath17.setVisible(true);
                break;
            case 18: faithPath18.setVisible(true);
                break;
            case 19: faithPath19.setVisible(true);
                break;
            case 20: faithPath20.setVisible(true);
                break;
            case 21: faithPath21.setVisible(true);
                break;
            case 22: faithPath22.setVisible(true);
                break;
            case 23: faithPath23.setVisible(true);
                break;
            case 24: faithPath24.setVisible(true);
                break;
        }
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
        for(int i=0;i<leaderCards.size();i++){
            LeaderCard l = leaderCards.get(i);
            if(!l.isInGame()&&!watchingPlayer){
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
    }

    private void setStorage(Storage storage){
        ArrayList<CounterTop> shelves = storage.getShelvesArray();

        if(shelves.size()!=0){
            for(int i=0;i<shelves.size();i++)
                if(shelves.get(i).getContent()!=0){
                    setStorageRow(shelves.get(0),i+1);
                }
        }
    }

    private void setStorageRow(CounterTop row,int i){
        switch(i){
            case 1: setImage(firstRowImage,getCounterTopImage(row.getResourceType()));
                break;
            case 2: String path = getCounterTopImage(row.getResourceType());
                    switch(row.getContent()){
                        case 1: setImage(secondRowImage1,path);
                                break;
                        case 2: setImage(secondRowImage1,path);
                                setImage(secondRowImage2,path);
                                break;
                    }
                break;
            case 3: path = getCounterTopImage(row.getResourceType());
                    switch(row.getContent()){
                        case 1: setImage(thirdRowImage1,path);
                            break;
                        case 2: setImage(thirdRowImage1,path);
                                setImage(thirdRowImage2,path);
                            break;
                        case 3: setImage(thirdRowImage1,path);
                                setImage(thirdRowImage2,path);
                                setImage(thirdRowImage3,path);
                            break;
                    }
                break;
        }
    }

    private String getCounterTopImage(Resource res){
        switch(res){
            case COIN:  return "/img/punchboard/coin2.png";
            case ROCK:  return "/img/punchboard/stone2.png";
            case SHIELD:  return "/img/punchboard/shield2.png";
            case SERVANT:  return "/img/punchboard/servant2.png";
            default: return null;
        }
    }

    private void setChest(ResourceCount chest){
        if(chest.getCoins()!=0)
            xCoin.setText("x"+chest.getCoins());
        if(chest.getShields()!=0)
            xShield.setText("x"+chest.getShields());
        if(chest.getRocks()!=0)
            xRock.setText("x"+chest.getRocks());
        if(chest.getServants()!=0)
            xServant.setText("x"+chest.getServants());
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
        Platform.runLater(()->getGuiManager().setLayout("marketAction.fxml"));
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
}
