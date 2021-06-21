package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.view.gui.GuiManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Choose resource scene controller
 */
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

    /**
     * When confirm is clicked, goToPayment if the player has chosen enough resources, or printError
     */
    public void confirmClick() {
        if (toChoose == ResourceCount.resCountToInt(chosenOutput)) {
            modal.close();
            goToPayment();
        }
        else
            printError();
    }

    /**
     * Switch to payment scene, if a basicProduction set the output only, if a leaderCard set both cost and output
     */
    private void goToPayment(){
        getGuiManager().setLayout("payment.fxml");
        if(isBasic)
            getGuiManager().getCurrentController().setBasicProduction(getResourceFromResourceCount());
        else
            getGuiManager().getCurrentController().setLeaderCardProduction(leaderCard,getResourceFromResourceCount());
    }

    /**
     *
     * @return a Resource starting from a ResourceCount, used for the output
     */
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

    /**
     * When the coin ImageView is clicked, addResource
     */
    public void coinClick() {
        addResource(Resource.COIN,xCoinChosen,chosenOutput,toChoose,coin,shield,servant,rock);
    }

    /**
     * When the shield ImageView is clicked, addResource
     */
    public void shieldClick() {
        addResource(Resource.SHIELD,xShieldChosen,chosenOutput,toChoose,coin,shield,servant,rock);
    }

    /**
     * When the servant ImageView is clicked, addResource
     */
    public void servantClick() {
        addResource(Resource.SERVANT,xServantChosen,chosenOutput,toChoose,coin,shield,servant,rock);
    }

    /**
     * When the rock ImageView is clicked, addResource
     */
    public void rockClick() {
        addResource(Resource.ROCK,xRockChosen,chosenOutput,toChoose,coin,shield,servant,rock);
    }

    /**
     * When the rock label is clicked, removeResource
     */
    public void xRockClick() {
        removeResource(Resource.ROCK,xRockChosen,chosenOutput,coin,shield,servant,rock);
    }

    /**
     * When the coin label is clicked, removeResource
     */
    public void xCoinClick() {
        removeResource(Resource.COIN,xCoinChosen,chosenOutput,coin,shield,servant,rock);
    }

    /**
     * When the servant label is clicked, removeResource
     */
    public void xServantClick() {
        removeResource(Resource.SERVANT,xServantChosen,chosenOutput,coin,shield,servant,rock);
    }

    /**
     * When the shield label is clicked, removeResource
     */
    public void xShieldClick() {
        removeResource(Resource.SHIELD,xShieldChosen,chosenOutput,coin,shield,servant,rock);
    }
}
