package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.model.CounterTop;
import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.Storage;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.view.gui.GuiManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;

public class PaymentController extends GuiController{
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
    private int row;
    private int col;
    private boolean shopAction;
    private ResourceCount cost;
    private ResourceCount mockChest;
    private ResourceCount mockChestChosen;
    private DevelopmentCard devCard;
    private PlayerDashboard playerDashboard;


    @FXML
    @Override
    public void initialize() {
        super.setGuiManager(GuiManager.getInstance());
        playerDashboard = getGuiManager().getClientManager().getThisClientDashboard();
        addStorageToTest();
        addChestToTest(new ResourceCount(2,1,3,4,0));
        //Setting up what the player's have
        setStorage(playerDashboard.getStorage(),firstRowImageYouHave,secondRowImage1YouHave,secondRowImage2YouHave,thirdRowImage1YouHave,thirdRowImage2YouHave,thirdRowImage3YouHave);
        setChest(playerDashboard.getChest(),xCoinYouHave,xShieldYouHave,xRockYouHave,xServantYouHave);
        mockChest = playerDashboard.getChest();
        mockChestChosen = new ResourceCount(0,0,0,0,0);

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
        if(mockChest.getCoins()==0)
            chestCoinYouHave.setDisable(true);
        else
            chestCoinYouHave.setDisable(false);
        if(mockChest.getRocks()==0)
            chestRockYouHave.setDisable(true);
        if(mockChest.getShields()==0)
            chestShieldYouHave.setDisable(true);
        if(mockChest.getServants()==0)
            chestServantYouHave.setDisable(true);

    }

    public void setStillToPay(ResourceCount resourceCount){
        setChest(resourceCount,xCoinStillToPay,xShieldStillToPay,xRockStillToPay,xServantStillToPay);
    }

    public void setDevCardProduction(DevelopmentCard card){

    }

    public void setLeaderCardProduction(LeaderCard card, Resource res){

    }

    public void setBasicProduction(Resource res){

    }

    @Override
    public void setBuyCard(int row,int col,DevelopmentCard card){
        this.row=row;
        this.col=col;
        this.shopAction=true;
        this.devCard = card;
        this.cost = card.getCost();
        setStillToPay(cost);
    }
    @FXML
    public void registerPaymentStorage(MouseEvent mouseEvent) {
        String id = mouseEvent.getPickResult().getIntersectedNode().getId();
        switch (id){
            case "firstRowImageYouHave":
                firstRowImageChosen.setImage(firstRowImageYouHave.getImage());
                setImage(firstRowImageYouHave,null);
                resourceSelected(playerDashboard.getStorage().getFirstRow().getResourceType());
                firstRowImageYouHave.setDisable(true);
                firstRowImageChosen.setDisable(false);
                break;
            case "secondRowImage1YouHave":
                secondRowImage1Chosen.setImage(secondRowImage1YouHave.getImage());
                setImage(secondRowImage1YouHave,null);
                resourceSelected(playerDashboard.getStorage().getSecondRow().getResourceType());
                secondRowImage1YouHave.setDisable(true);
                secondRowImage1Chosen.setDisable(false);
                break;
            case "secondRowImage2YouHave":
                secondRowImage2Chosen.setImage(secondRowImage2YouHave.getImage());
                setImage(secondRowImage2YouHave,null);
                resourceSelected(playerDashboard.getStorage().getSecondRow().getResourceType());
                secondRowImage2YouHave.setDisable(true);
                secondRowImage2Chosen.setDisable(false);
                break;
            case "thirdRowImage1YouHave":
                thirdRowImage1Chosen.setImage(thirdRowImage1YouHave.getImage());
                setImage(thirdRowImage1YouHave,null);
                resourceSelected(playerDashboard.getStorage().getThirdRow().getResourceType());
                thirdRowImage1YouHave.setDisable(true);
                thirdRowImage1Chosen.setDisable(false);
                break;
            case "thirdRowImage2YouHave":
                thirdRowImage2Chosen.setImage(thirdRowImage2YouHave.getImage());
                setImage(thirdRowImage2YouHave,null);
                resourceSelected(playerDashboard.getStorage().getThirdRow().getResourceType());
                thirdRowImage2YouHave.setDisable(true);
                thirdRowImage2Chosen.setDisable(false);
                break;
            case "thirdRowImage3YouHave":
                thirdRowImage3Chosen.setImage(thirdRowImage3YouHave.getImage());
                setImage(thirdRowImage3YouHave,null);
                resourceSelected(playerDashboard.getStorage().getThirdRow().getResourceType());
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
                firstRowImageYouHave.setDisable(false);
                firstRowImageChosen.setDisable(true);
                break;
            case "secondRowImage1Chosen":
                secondRowImage1YouHave.setImage(secondRowImage1Chosen.getImage());
                setImage(secondRowImage1Chosen,null);
                resourceDeselected(playerDashboard.getStorage().getSecondRow().getResourceType());
                secondRowImage1YouHave.setDisable(false);
                secondRowImage1Chosen.setDisable(true);
                break;
            case "secondRowImage2Chosen":
                secondRowImage2YouHave.setImage(secondRowImage2Chosen.getImage());
                setImage(secondRowImage2Chosen,null);
                resourceDeselected(playerDashboard.getStorage().getSecondRow().getResourceType());
                secondRowImage2YouHave.setDisable(false);
                secondRowImage2Chosen.setDisable(true);
                break;
            case "thirdRowImage1Chosen":
                thirdRowImage1YouHave.setImage(thirdRowImage1Chosen.getImage());
                setImage(thirdRowImage1Chosen,null);
                resourceDeselected(playerDashboard.getStorage().getThirdRow().getResourceType());
                thirdRowImage1YouHave.setDisable(false);
                thirdRowImage1Chosen.setDisable(true);
                break;
            case "thirdRowImage2Chosen":
                thirdRowImage2YouHave.setImage(thirdRowImage2Chosen.getImage());
                setImage(thirdRowImage2Chosen,null);
                resourceDeselected(playerDashboard.getStorage().getThirdRow().getResourceType());
                thirdRowImage2YouHave.setDisable(false);
                thirdRowImage2Chosen.setDisable(true);
                break;
            case "thirdRowImage3Chosen":
                thirdRowImage3YouHave.setImage(thirdRowImage3Chosen.getImage());
                setImage(thirdRowImage3Chosen,null);
                resourceDeselected(playerDashboard.getStorage().getThirdRow().getResourceType());
                thirdRowImage3YouHave.setDisable(false);
                thirdRowImage3Chosen.setDisable(true);
                break;
        }
    }
    public void resourceDeselected(Resource res){
        switch (res){
            case COIN:
                cost.addCoins(1);
                break;
            case ROCK:
                cost.addRocks(1);
                break;
            case SHIELD:
                cost.addShields(1);
                break;
            case SERVANT:
                cost.addServants(1);
                break;
        }
        setStillToPay(cost);
    }

    public void registerPaymentChest(MouseEvent mouseEvent) {
        String id = mouseEvent.getPickResult().getIntersectedNode().getId();
        switch (id){
            case "chestCoinYouHave":
                mockChestChosen.addCoins(1);
                xCoinChosen.setText("x"+mockChestChosen.getCoins());
                mockChest.removeCoins(1);
                xCoinYouHave.setText("x"+mockChest.getCoins());
                if(mockChest.getCoins()==0)
                    chestCoinYouHave.setDisable(true);
                resourceSelected(Resource.COIN);
                break;
            case "chestRockYouHave":
                mockChestChosen.addRocks(1);
                xRockChosen.setText("x"+mockChestChosen.getRocks());
                mockChest.removeRocks(1);
                xRockYouHave.setText("x"+mockChest.getRocks());
                if(mockChest.getRocks()==0)
                    chestRockYouHave.setDisable(true);
                resourceSelected(Resource.ROCK);
                break;
            case "chestShieldYouHave":
                mockChestChosen.addShields(1);
                xShieldChosen.setText("x"+mockChestChosen.getShields());
                mockChest.removeShields(1);
                xShieldYouHave.setText("x"+mockChest.getShields());
                if(mockChest.getShields()==0)
                    chestShieldYouHave.setDisable(true);
                resourceSelected(Resource.SHIELD);
                break;
            case "chestServantYouHave":
                mockChestChosen.addServants(1);
                xServantChosen.setText("x"+mockChestChosen.getServants());
                mockChest.removeServants(1);
                xServantYouHave.setText("x"+mockChest.getServants());
                if(mockChest.getServants()==0)
                    chestServantYouHave.setDisable(true);
                resourceSelected(Resource.SERVANT);
                break;
        }
    }

    public void deselectPaymentChest(MouseEvent mouseEvent) {
        String id = mouseEvent.getPickResult().getIntersectedNode().getId();
        switch (id){
            case "chestCoinChosen":
                mockChestChosen.removeCoins(1);
                xCoinChosen.setText("x"+mockChestChosen.getCoins());
                mockChest.addCoins(1);
                xCoinYouHave.setText("x"+mockChest.getCoins());
                if(mockChest.getCoins()!=0)
                    chestCoinYouHave.setDisable(false);
                if(mockChestChosen.getCoins()==0)
                    chestCoinChosen.setDisable(true);
                resourceDeselected(Resource.COIN);
                break;
            case "chestRockChosen":
                mockChestChosen.removeRocks(1);
                xRockChosen.setText("x"+mockChestChosen.getRocks());
                mockChest.addRocks(1);
                xRockYouHave.setText("x"+mockChest.getRocks());
                if(mockChest.getRocks()!=0)
                    chestRockYouHave.setDisable(false);
                if(mockChestChosen.getRocks()==0)
                    chestRockChosen.setDisable(true);
                resourceDeselected(Resource.ROCK);
                break;
            case "chestShieldChosen":
                mockChestChosen.removeShields(1);
                xShieldChosen.setText("x"+mockChestChosen.getShields());
                mockChest.addShields(1);
                xShieldYouHave.setText("x"+mockChest.getShields());
                if(mockChest.getShields()!=0)
                    chestShieldYouHave.setDisable(false);
                if(mockChestChosen.getShields()==0)
                    chestShieldChosen.setDisable(true);
                resourceDeselected(Resource.SHIELD);
                break;
            case "chestServantChosen":
                mockChestChosen.removeServants(1);
                xServantChosen.setText("x"+mockChestChosen.getServants());
                mockChest.addServants(1);
                xServantYouHave.setText("x"+mockChest.getServants());
                if(mockChest.getServants()!=0)
                    chestServantYouHave.setDisable(false);
                if(mockChestChosen.getServants()==0)
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
}
