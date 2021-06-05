package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.view.gui.GuiManager;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;

public class EndCardBuyController extends GuiController{
    @FXML
    private ImageView chosenCard;
    @FXML
    private AnchorPane deckThree,deckOne,deckTwo;

    @FXML
    public void deckSelected(MouseEvent mouseEvent) {
        Node source = (Node) mouseEvent.getSource();
        switch (source.getId()){
            case "deckOne":
                Platform.runLater(()-> getGuiManager().getClientManager().buyCard(storageCount,chestCount, card.getId(), 0));
            case "deckTwo":
                Platform.runLater(()-> getGuiManager().getClientManager().buyCard(storageCount,chestCount, card.getId(), 1));
            case "deckThree":
                Platform.runLater(()-> getGuiManager().getClientManager().buyCard(storageCount,chestCount, card.getId(), 2));
        }
        deckOne.setDisable(true);
        deckTwo.setDisable(true);
        deckThree.setDisable(true);
        Platform.runLater(()->getGuiManager().setLayout("clientDashboard.fxml"));
    }
    ResourceCount storageCount,chestCount;
    DevelopmentCard card;

    @FXML
    @Override
    public void initialize(){
        super.setGuiManager(GuiManager.getInstance());

    }

    @Override
    public void setFinalStageBuy(DevelopmentCard card, ResourceCount storageCount, ResourceCount chestCount){
        this.card=card;
        this.storageCount=storageCount;
        this.chestCount=chestCount;
        setImage(chosenCard,"/img/cards/front/DevelopmentCards/"+ card.getId() + ".png");
    }

}
