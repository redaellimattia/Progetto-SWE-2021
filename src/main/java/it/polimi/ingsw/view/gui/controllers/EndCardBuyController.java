package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.view.gui.GuiManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;

public class EndCardBuyController extends GuiController{
    @FXML
    private Button firstDeck;
    @FXML
    private Button secondDeck;
    @FXML
    private Button thirdDeck;
    @FXML
    private ImageView chosenCard;

    ResourceCount storageCount,chestCount;
    DevelopmentCard card;

    @FXML
    @Override
    public void initialize(){ super.setGuiManager(GuiManager.getInstance()); }

    @Override
    public void setFinalStageBuy(DevelopmentCard card, ResourceCount storageCount, ResourceCount chestCount){
        this.card=card;
        this.storageCount=storageCount;
        this.chestCount=chestCount;
        setImage(chosenCard,"/img/cards/front/DevelopmentCards/"+ card.getId() + ".png");
    }
    @FXML
    private void firstDeckChosen(MouseEvent mouseEvent) {
        Platform.runLater(()-> getGuiManager().getClientManager().buyCard(storageCount,chestCount, card.getId(), 0));
        firstDeck.setDisable(true);
        secondDeck.setDisable(true);
        thirdDeck.setDisable(true);
        getGuiManager().getClientManager().setMainActionDone(true);
        getGuiManager().callDashboard();
    }
    @FXML
    private void secondDeckChosen(MouseEvent mouseEvent) {
        Platform.runLater(()-> getGuiManager().getClientManager().buyCard(storageCount,chestCount, card.getId(), 1));
        firstDeck.setDisable(true);
        secondDeck.setDisable(true);
        thirdDeck.setDisable(true);
        getGuiManager().getClientManager().setMainActionDone(true);
        getGuiManager().callDashboard();
    }
    @FXML
    private void thirdDeckChosen(MouseEvent mouseEvent) {
        Platform.runLater(()-> getGuiManager().getClientManager().buyCard(storageCount,chestCount, card.getId(), 2));
        firstDeck.setDisable(true);
        secondDeck.setDisable(true);
        thirdDeck.setDisable(true);
        getGuiManager().getClientManager().setMainActionDone(true);
        getGuiManager().callDashboard();
    }
}
