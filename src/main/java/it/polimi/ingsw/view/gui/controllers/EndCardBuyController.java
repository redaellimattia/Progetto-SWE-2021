package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.view.gui.GuiManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;

import java.util.ArrayList;

public class EndCardBuyController extends GuiController{
    @FXML
    private ImageView firstDeck1,firstDeck2,firstDeck3,secondDeck1,secondDeck2,secondDeck3,thirdDeck1,thirdDeck2,thirdDeck3,chosenCard;
    @FXML
    private Button firstDeck,secondDeck,thirdDeck;
    ResourceCount storageCount,chestCount;
    DevelopmentCard card;

    @FXML
    @Override
    public void initialize(){
        super.setGuiManager(GuiManager.getInstance());
        setDevCards(getGuiManager().getClientManager().getThisClientDashboard().getDevCards(),
                    firstDeck1,secondDeck1,thirdDeck1,firstDeck2,secondDeck2,thirdDeck2,firstDeck3,secondDeck3,thirdDeck3);
    }

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
