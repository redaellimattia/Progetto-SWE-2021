package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.view.gui.GuiManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

/**
 * End card shop buy scene controller
 */
public class EndCardBuyController extends GuiController{
    @FXML
    private ImageView firstDeck1,firstDeck2,firstDeck3,secondDeck1,secondDeck2,secondDeck3,thirdDeck1,thirdDeck2,thirdDeck3,chosenCard;
    @FXML
    private Button firstDeck,secondDeck,thirdDeck;
    ResourceCount storageCount,chestCount;
    DevelopmentCard card;

    /**
     * setting the decks of development cards owned by the player when choosing where to place the card
     */
    @FXML
    @Override
    public void initialize(){
        super.setGuiManager(GuiManager.getInstance());
        setDevCards(getGuiManager().getClientManager().getThisClientDashboard().getDevCards(),
                    firstDeck1,secondDeck1,thirdDeck1,firstDeck2,secondDeck2,thirdDeck2,firstDeck3,secondDeck3,thirdDeck3);
    }

    /**
     * passing the parameters in order to send the buyCardMessage after the selection of the deck
     * @param card chosen card
     * @param storageCount resources chosen from the storage
     * @param chestCount resources chosen from the chest
     */
    @Override
    public void setFinalStageBuy(DevelopmentCard card, ResourceCount storageCount, ResourceCount chestCount){
        this.card=card;
        this.storageCount=storageCount;
        this.chestCount=chestCount;
        setImage(chosenCard,"/img/cards/front/DevelopmentCards/"+ card.getId() + ".png");
    }

    /**
     * called upon the click of the "firstDeck" button
     */
    @FXML
    private void firstDeckChosen() {
        Platform.runLater(()-> getGuiManager().getClientManager().buyCard(storageCount,chestCount, card.getId(), 0));
        firstDeck.setDisable(true);
        secondDeck.setDisable(true);
        thirdDeck.setDisable(true);
        getGuiManager().getClientManager().setMainActionDone(true);
        Platform.runLater(()->getGuiManager().callDashboard());
    }

    /**
     * called upon the click of the "secondDeck" button
     */
    @FXML
    private void secondDeckChosen() {
        Platform.runLater(()-> getGuiManager().getClientManager().buyCard(storageCount,chestCount, card.getId(), 1));
        firstDeck.setDisable(true);
        secondDeck.setDisable(true);
        thirdDeck.setDisable(true);
        getGuiManager().getClientManager().setMainActionDone(true);
        Platform.runLater(()->getGuiManager().callDashboard());
    }

    /**
     * called upon the click of the "thirdDeck" button
     */
    @FXML
    private void thirdDeckChosen() {
        Platform.runLater(()-> getGuiManager().getClientManager().buyCard(storageCount,chestCount, card.getId(), 2));
        firstDeck.setDisable(true);
        secondDeck.setDisable(true);
        thirdDeck.setDisable(true);
        getGuiManager().getClientManager().setMainActionDone(true);
        Platform.runLater(()->getGuiManager().callDashboard());
    }
}
