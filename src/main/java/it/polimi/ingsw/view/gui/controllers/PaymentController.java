package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.model.CounterTop;
import it.polimi.ingsw.model.DeckDashboard;
import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.view.gui.GuiManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;

public class PaymentController extends GuiController{
    @FXML
    private Button continueButton;
    @FXML //XCHOSEN
    private Label xCoinChosen,xShieldChosen,xServantChosen,xRockChosen;
    @FXML //CHESTCHOSEN
    private AnchorPane chestCoinChosen,chestShieldChosen,chestServantChosen,chestRockChosen;
    @FXML //STORAGECHOSEN
    private ImageView firstRowImageChosen,secondRowImage1Chosen,secondRowImage2Chosen,thirdRowImage1Chosen,thirdRowImage2Chosen,thirdRowImage3Chosen;
    @FXML //XYOUHAVE
    private Label xCoinYouHave,xShieldYouHave,xServantYouHave,xRockYouHave;
    @FXML //XSTILL TO PAY
    private Label xCoinStillToPay,xShieldStillToPay,xServantStillToPay,xRockStillToPay;
    @FXML //CHESTYOUHAVE
    private AnchorPane chestCoinYouHave,chestShieldYouHave,chestServantYouHave,chestRockYouHave;
    @FXML //STORAGEYOUHAVE
    private ImageView firstRowImageYouHave,secondRowImage1YouHave,secondRowImage2YouHave,thirdRowImage1YouHave,thirdRowImage2YouHave,thirdRowImage3YouHave;
    private boolean shopAction,basicProduction,leaderCardProduction,devCardProduction;
    private Resource res;
    private ResourceCount cost,fixedCost,playerChest,chestCount,storageCount;
    private DevelopmentCard devCard;
    private LeaderCard leaderCard;
    private PlayerDashboard playerDashboard;


    @FXML
    @Override
    public void initialize() {
        super.setGuiManager(GuiManager.getInstance());
        playerDashboard = getGuiManager().getClientManager().getThisClientDashboard();
        //Setting up what the player's have
        setStorage(playerDashboard.getStorage(),firstRowImageYouHave,secondRowImage1YouHave,secondRowImage2YouHave,thirdRowImage1YouHave,thirdRowImage2YouHave,thirdRowImage3YouHave);
        setChest(playerDashboard.getChest(),xCoinYouHave,xShieldYouHave,xRockYouHave,xServantYouHave);
        playerChest = playerDashboard.getChest();
        chestCount = new ResourceCount(0,0,0,0,0);
        storageCount = new ResourceCount(0,0,0,0,0);

        firstRowImageChosen.setDisable(true);
        secondRowImage1Chosen.setDisable(true);
        secondRowImage2Chosen.setDisable(true);
        thirdRowImage1Chosen.setDisable(true);
        thirdRowImage2Chosen.setDisable(true);
        thirdRowImage3Chosen.setDisable(true);
        if(playerDashboard.getStorage().getFirstRow().getContent()==0)
            firstRowImageYouHave.setDisable(true);
        if(playerDashboard.getStorage().getSecondRow().getContent()==1)
            secondRowImage2YouHave.setDisable(true);
        if(playerDashboard.getStorage().getSecondRow().getContent()==0) {
            secondRowImage1YouHave.setDisable(true);
            secondRowImage2YouHave.setDisable(true);
        }
        if(playerDashboard.getStorage().getThirdRow().getContent()==0){
            thirdRowImage1YouHave.setDisable(true);
            thirdRowImage2YouHave.setDisable(true);
            thirdRowImage3YouHave.setDisable(true);
        }
        if(playerChest.getCoins()==0)
            chestCoinYouHave.setDisable(true);
        else
            chestCoinYouHave.setDisable(false);
        if(playerChest.getRocks()==0)
            chestRockYouHave.setDisable(true);
        if(playerChest.getShields()==0)
            chestShieldYouHave.setDisable(true);
        if(playerChest.getServants()==0)
            chestServantYouHave.setDisable(true);

    }

    public void setStillToPay(ResourceCount resourceCount){
        setChest(resourceCount,xCoinStillToPay,xShieldStillToPay,xRockStillToPay,xServantStillToPay);
    }

    @Override
    public void setDevCardProduction(DevelopmentCard card){
        devCardProduction = true;
        this.devCard = card;
        setCost(card.getProductionPower().getInput());
    }

    @Override
    public void setLeaderCardProduction(LeaderCard card, Resource res){
        leaderCardProduction = true;
        this.leaderCard = card;
        ResourceCount cost = new ResourceCount(0,0,0,0,0);
        card.getSpecialAbility().getResourceType().add(cost,1);
        setCost(cost);
        this.res = res;
    }

    @Override
    public void setBasicProduction(ResourceCount cost,Resource res){
        basicProduction = true;
        this.res = res;
        setCost(cost);
    }

    private void setCost(ResourceCount cost){
        this.fixedCost = cost;
        this.cost = new ResourceCount(fixedCost.getCoins(),fixedCost.getRocks(),fixedCost.getServants(),fixedCost.getShields(),0);
        setStillToPay(cost);
    }
    @Override
    public void setBuyCard(DevelopmentCard card){
        this.shopAction=true;
        this.devCard = card;
        setCost(card.getCost());
    }
    @FXML
    public void registerPaymentStorage(MouseEvent mouseEvent) {
        String id = mouseEvent.getPickResult().getIntersectedNode().getId();
        switch (id){
            case "firstRowImageYouHave":
                firstRowImageChosen.setImage(firstRowImageYouHave.getImage());
                setImage(firstRowImageYouHave,null);
                resourceSelected(playerDashboard.getStorage().getFirstRow().getResourceType());

                playerDashboard.getStorage().getFirstRow().getResourceType().add(storageCount,1);
                firstRowImageYouHave.setDisable(true);
                firstRowImageChosen.setDisable(false);
                break;
            case "secondRowImage1YouHave":
                secondRowImage1Chosen.setImage(secondRowImage1YouHave.getImage());
                setImage(secondRowImage1YouHave,null);
                resourceSelected(playerDashboard.getStorage().getSecondRow().getResourceType());

                playerDashboard.getStorage().getSecondRow().getResourceType().add(storageCount,1);
                secondRowImage1YouHave.setDisable(true);
                secondRowImage1Chosen.setDisable(false);
                break;
            case "secondRowImage2YouHave":
                secondRowImage2Chosen.setImage(secondRowImage2YouHave.getImage());
                setImage(secondRowImage2YouHave,null);
                resourceSelected(playerDashboard.getStorage().getSecondRow().getResourceType());

                playerDashboard.getStorage().getSecondRow().getResourceType().add(storageCount,1);
                secondRowImage2YouHave.setDisable(true);
                secondRowImage2Chosen.setDisable(false);
                break;
            case "thirdRowImage1YouHave":
                thirdRowImage1Chosen.setImage(thirdRowImage1YouHave.getImage());
                setImage(thirdRowImage1YouHave,null);
                resourceSelected(playerDashboard.getStorage().getThirdRow().getResourceType());

                playerDashboard.getStorage().getThirdRow().getResourceType().add(storageCount,1);
                thirdRowImage1YouHave.setDisable(true);
                thirdRowImage1Chosen.setDisable(false);
                break;
            case "thirdRowImage2YouHave":
                thirdRowImage2Chosen.setImage(thirdRowImage2YouHave.getImage());
                setImage(thirdRowImage2YouHave,null);
                resourceSelected(playerDashboard.getStorage().getThirdRow().getResourceType());

                playerDashboard.getStorage().getThirdRow().getResourceType().add(storageCount,1);
                thirdRowImage2YouHave.setDisable(true);
                thirdRowImage2Chosen.setDisable(false);
                break;
            case "thirdRowImage3YouHave":
                thirdRowImage3Chosen.setImage(thirdRowImage3YouHave.getImage());
                setImage(thirdRowImage3YouHave,null);
                resourceSelected(playerDashboard.getStorage().getThirdRow().getResourceType());

                playerDashboard.getStorage().getThirdRow().getResourceType().add(storageCount,1);
                thirdRowImage3YouHave.setDisable(true);
                thirdRowImage3Chosen.setDisable(false);
                break;
        }
    }

    public void resourceSelected(Resource res){
        switch (res){
            case COIN:
                if(cost.getCoins()>0)
                    cost.removeCoins(1);
                break;
            case ROCK:
                if(cost.getRocks()>0)
                    cost.removeRocks(1);
                break;
            case SHIELD:
                if(cost.getShields()>0)
                    cost.removeShields(1);
                break;
            case SERVANT:
                if(cost.getServants()>0)
                    cost.removeServants(1);
                break;
        }
        setStillToPay(cost);
    }
    @FXML
    public void deselectPaymentStorage(MouseEvent mouseEvent) {
        String id = mouseEvent.getPickResult().getIntersectedNode().getId();
        switch (id){
            case "firstRowImageChosen":
                firstRowImageYouHave.setImage(firstRowImageChosen.getImage());
                setImage(firstRowImageChosen,null);
                resourceDeselected(playerDashboard.getStorage().getFirstRow().getResourceType());


                playerDashboard.getStorage().getFirstRow().getResourceType().remove(storageCount,1);
                firstRowImageYouHave.setDisable(false);
                firstRowImageChosen.setDisable(true);
                break;
            case "secondRowImage1Chosen":
                secondRowImage1YouHave.setImage(secondRowImage1Chosen.getImage());
                setImage(secondRowImage1Chosen,null);
                resourceDeselected(playerDashboard.getStorage().getSecondRow().getResourceType());

                playerDashboard.getStorage().getSecondRow().getResourceType().remove(storageCount,1);
                secondRowImage1YouHave.setDisable(false);
                secondRowImage1Chosen.setDisable(true);
                break;
            case "secondRowImage2Chosen":
                secondRowImage2YouHave.setImage(secondRowImage2Chosen.getImage());
                setImage(secondRowImage2Chosen,null);
                resourceDeselected(playerDashboard.getStorage().getSecondRow().getResourceType());

                playerDashboard.getStorage().getSecondRow().getResourceType().remove(storageCount,1);
                secondRowImage2YouHave.setDisable(false);
                secondRowImage2Chosen.setDisable(true);
                break;
            case "thirdRowImage1Chosen":
                thirdRowImage1YouHave.setImage(thirdRowImage1Chosen.getImage());
                setImage(thirdRowImage1Chosen,null);
                resourceDeselected(playerDashboard.getStorage().getThirdRow().getResourceType());

                playerDashboard.getStorage().getThirdRow().getResourceType().remove(storageCount,1);
                thirdRowImage1YouHave.setDisable(false);
                thirdRowImage1Chosen.setDisable(true);
                break;
            case "thirdRowImage2Chosen":
                thirdRowImage2YouHave.setImage(thirdRowImage2Chosen.getImage());
                setImage(thirdRowImage2Chosen,null);
                resourceDeselected(playerDashboard.getStorage().getThirdRow().getResourceType());

                playerDashboard.getStorage().getThirdRow().getResourceType().remove(storageCount,1);
                thirdRowImage2YouHave.setDisable(false);
                thirdRowImage2Chosen.setDisable(true);
                break;
            case "thirdRowImage3Chosen":
                thirdRowImage3YouHave.setImage(thirdRowImage3Chosen.getImage());
                setImage(thirdRowImage3Chosen,null);
                resourceDeselected(playerDashboard.getStorage().getThirdRow().getResourceType());

                playerDashboard.getStorage().getThirdRow().getResourceType().remove(storageCount,1);
                thirdRowImage3YouHave.setDisable(false);
                thirdRowImage3Chosen.setDisable(true);
                break;
        }
    }
    public void resourceDeselected(Resource res){
        switch (res){
            case COIN:
                if(cost.getCoins()< fixedCost.getCoins() && chestCount.getCoins() <fixedCost.getCoins())
                    cost.addCoins(1);
                break;
            case ROCK:
                if(cost.getRocks()< fixedCost.getRocks() && chestCount.getRocks() <fixedCost.getRocks())
                    cost.addRocks(1);
                break;
            case SHIELD:
                if(cost.getShields()< fixedCost.getShields() && chestCount.getShields() <fixedCost.getShields())
                    cost.addShields(1);
                break;
            case SERVANT:
                if(cost.getServants()< fixedCost.getServants() && chestCount.getServants() <fixedCost.getServants())
                    cost.addServants(1);
                break;
        }
        setStillToPay(cost);
    }

    public void registerPaymentChest(MouseEvent mouseEvent) {
        final Node SOURCE = (Node) mouseEvent.getSource();
        String id = SOURCE.getId();
        switch (id){
            case "chestCoinYouHave":
                chestCount.addCoins(1);
                xCoinChosen.setText("x"+ chestCount.getCoins());
                playerChest.removeCoins(1);
                xCoinYouHave.setText("x"+ playerChest.getCoins());
                if(playerChest.getCoins()==0)
                    chestCoinYouHave.setDisable(true);
                if(chestCount.getCoins()!=0)
                    chestCoinChosen.setDisable(false);
                resourceSelected(Resource.COIN);
                break;
            case "chestRockYouHave":
                chestCount.addRocks(1);
                xRockChosen.setText("x"+ chestCount.getRocks());
                playerChest.removeRocks(1);
                xRockYouHave.setText("x"+ playerChest.getRocks());
                if(playerChest.getRocks()==0)
                    chestRockYouHave.setDisable(true);
                if(chestCount.getRocks()!=0)
                    chestRockChosen.setDisable(false);
                resourceSelected(Resource.ROCK);
                break;
            case "chestShieldYouHave":
                chestCount.addShields(1);
                xShieldChosen.setText("x"+ chestCount.getShields());
                playerChest.removeShields(1);
                xShieldYouHave.setText("x"+ playerChest.getShields());
                if(playerChest.getShields()==0)
                    chestShieldYouHave.setDisable(true);
                if(chestCount.getShields()!=0)
                    chestShieldChosen.setDisable(false);
                resourceSelected(Resource.SHIELD);
                break;
            case "chestServantYouHave":
                chestCount.addServants(1);
                xServantChosen.setText("x"+ chestCount.getServants());
                playerChest.removeServants(1);
                xServantYouHave.setText("x"+ playerChest.getServants());
                if(playerChest.getServants()==0)
                    chestServantYouHave.setDisable(true);
                if(chestCount.getServants()!=0)
                    chestServantChosen.setDisable(false);
                resourceSelected(Resource.SERVANT);
                break;
        }
    }

    public void deselectPaymentChest(MouseEvent mouseEvent) {
        final Node SOURCE = (Node) mouseEvent.getSource();
        String id = SOURCE.getId();
        switch (id){
            case "chestCoinChosen":
                chestCount.removeCoins(1);
                xCoinChosen.setText("x"+ chestCount.getCoins());
                playerChest.addCoins(1);
                xCoinYouHave.setText("x"+ playerChest.getCoins());
                if(playerChest.getCoins()!=0)
                    chestCoinYouHave.setDisable(false);
                if(chestCount.getCoins()==0)
                    chestCoinChosen.setDisable(true);
                resourceDeselected(Resource.COIN);
                break;
            case "chestRockChosen":
                chestCount.removeRocks(1);
                xRockChosen.setText("x"+ chestCount.getRocks());
                playerChest.addRocks(1);
                xRockYouHave.setText("x"+ playerChest.getRocks());
                if(playerChest.getRocks()!=0)
                    chestRockYouHave.setDisable(false);
                if(chestCount.getRocks()==0)
                    chestRockChosen.setDisable(true);
                resourceDeselected(Resource.ROCK);
                break;
            case "chestShieldChosen":
                chestCount.removeShields(1);
                xShieldChosen.setText("x"+ chestCount.getShields());
                playerChest.addShields(1);
                xShieldYouHave.setText("x"+ playerChest.getShields());
                if(playerChest.getShields()!=0)
                    chestShieldYouHave.setDisable(false);
                if(chestCount.getShields()==0)
                    chestShieldChosen.setDisable(true);
                resourceDeselected(Resource.SHIELD);
                break;
            case "chestServantChosen":
                chestCount.removeServants(1);
                xServantChosen.setText("x"+ chestCount.getServants());
                playerChest.addServants(1);
                xServantYouHave.setText("x"+ playerChest.getServants());
                if(playerChest.getServants()!=0)
                    chestServantYouHave.setDisable(false);
                if(chestCount.getServants()==0)
                    chestServantChosen.setDisable(true);
                resourceDeselected(Resource.SERVANT);
                break;
        }
    }

    public void addChestToTest(ResourceCount chest){
        playerDashboard.setChest(chest);
    }
    public void addStorageToTest(){
        CounterTop first = new CounterTop(Resource.COIN,1);
        CounterTop second = new CounterTop(Resource.SHIELD,2);
        CounterTop third = new CounterTop(Resource.SERVANT,2);
        playerDashboard.getStorage().setFirstRow(first);
        playerDashboard.getStorage().setSecondRow(second);
        playerDashboard.getStorage().setThirdRow(third);
    }

    public void endBuy(MouseEvent mouseEvent) {
        if(shopAction)
            Platform.runLater(() -> goToEndBuyCard());
        if(basicProduction)
            Platform.runLater(() -> payBasicProduction());
        if(leaderCardProduction)
            Platform.runLater(() -> payLeaderCardProduction());
        if(devCardProduction)
            Platform.runLater(() -> payDevCardProduction());
    }

    private void payDevCardProduction(){
        int index = -1;
        for(int i=0;i<3;i++){
            DeckDashboard deck = playerDashboard.getDevCards()[i];
            if(deck.getDeck().size()>0&&deck.getFirst().equals(devCard))
                index = i;
        }
        getGuiManager().getClientManager().devCardProduction(index,devCard,storageCount, chestCount);
        getGuiManager().callDashboard();
        getGuiManager().getCurrentController().setProductionOnGoing();
    }

    private void payLeaderCardProduction(){
        getGuiManager().getClientManager().leaderProduction(playerDashboard.getLeaderPos(leaderCard),leaderCard,storageCount, chestCount,res);
        getGuiManager().callDashboard();
        getGuiManager().getCurrentController().setProductionOnGoing();
    }

    private void payBasicProduction(){
        getGuiManager().getClientManager().basicProduction(storageCount, chestCount,res);
        getGuiManager().callDashboard();
        getGuiManager().getCurrentController().setProductionOnGoing();
    }

    private void goToEndBuyCard(){
        getGuiManager().setLayout("endCardBuy.fxml");
        getGuiManager().getCurrentController().setFinalStageBuy(devCard,storageCount, chestCount);
    }
}
