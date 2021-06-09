package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.view.gui.GuiManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ChooseResourcesController extends GuiController{
    @FXML
    private ImageView coin,rock,shield,servant;
    @FXML
    private Label error;
    @FXML
    private Text title;
    @FXML
    private Label xCoinChosen,xRockChosen,xShieldChosen,xServantChosen;

    private Stage modal;
    private int toChoose;
    private ResourceCount chosenOutput;
    private boolean isBasic;
    private LeaderCard leaderCard;

    @Override
    public void setModal(boolean isBasic,LeaderCard card,Stage modal) {
        this.isBasic = isBasic;
        this.leaderCard = card;
        this.modal = modal;
        toChoose = 1;
        chosenOutput = new ResourceCount(0,0,0,0,0);
        title.setText("Choose 1 Resource as output");
    }



    @FXML
    @Override
    public void initialize() {
        super.setGuiManager(GuiManager.getInstance());
    }

    public void confirmClick(MouseEvent mouseEvent) {
        if (toChoose == ResourceCount.resCountToInt(chosenOutput)) {
            modal.close();
            goToPayment();
        }
        else
            printError();
    }

    private void goToPayment(){
        getGuiManager().setLayout("payment.fxml");
        if(isBasic)
            getGuiManager().getCurrentController().setBasicProduction(getResourceFromResourceCount());
        else
            getGuiManager().getCurrentController().setLeaderCardProduction(leaderCard,getResourceFromResourceCount());
    }

    private Resource getResourceFromResourceCount(){
        if(chosenOutput.getCoins()>0)
            return Resource.COIN;
        if(chosenOutput.getShields()>0)
            return Resource.SHIELD;
        if(chosenOutput.getRocks()>0)
            return Resource.ROCK;
        if(chosenOutput.getServants()>0)
            return Resource.SERVANT;
        return null;
    }

    private void printError(){
        error.setText("You haven't chosen enough resources!");
        error.setVisible(true);
    }

    public void coinClick(MouseEvent mouseEvent) {
        addResource(Resource.COIN,xCoinChosen,chosenOutput,toChoose,coin,shield,servant,rock);
    }

    public void shieldClick(MouseEvent mouseEvent) {
        addResource(Resource.SHIELD,xShieldChosen,chosenOutput,toChoose,coin,shield,servant,rock);
    }

    public void servantClick(MouseEvent mouseEvent) {
        addResource(Resource.SERVANT,xServantChosen,chosenOutput,toChoose,coin,shield,servant,rock);
    }

    public void rockClick(MouseEvent mouseEvent) {
        addResource(Resource.ROCK,xRockChosen,chosenOutput,toChoose,coin,shield,servant,rock);
    }

    public void xRockClick(MouseEvent mouseEvent) {
        removeResource(Resource.ROCK,xRockChosen,chosenOutput,coin,shield,servant,rock);
    }

    public void xCoinClick(MouseEvent mouseEvent) {
        removeResource(Resource.COIN,xCoinChosen,chosenOutput,coin,shield,servant,rock);
    }

    public void xServantClick(MouseEvent mouseEvent) {
        removeResource(Resource.SERVANT,xServantChosen,chosenOutput,coin,shield,servant,rock);
    }

    public void xShieldClick(MouseEvent mouseEvent) {
        removeResource(Resource.SHIELD,xShieldChosen,chosenOutput,coin,shield,servant,rock);
    }
}
