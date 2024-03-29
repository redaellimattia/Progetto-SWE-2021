package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.model.Shop;
import it.polimi.ingsw.view.gui.GuiManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * Shop view scene controller
 */
public class ShopViewController extends GuiController {
    @FXML
    private Label errorLabel;
    @FXML
    private ImageView g3;
    @FXML
    private ImageView g2;
    @FXML
    private ImageView g1;
    @FXML
    private ImageView b3;
    @FXML
    private ImageView b2;
    @FXML
    private ImageView b1;
    @FXML
    private ImageView y3;
    @FXML
    private ImageView y2;
    @FXML
    private ImageView y1;
    @FXML
    private ImageView p3;
    @FXML
    private ImageView p2;
    @FXML
    private ImageView p1;
    @FXML
    private Button confirmButton;
    @FXML
    private ImageView selected;
    private int row;
    private int col;

    /**
     * Setting disable/enable based on the player's turn and loaading the shop
     */
    @FXML
    @Override
    public void initialize() {
        super.setGuiManager(GuiManager.getInstance());
        errorLabel.setVisible(false);
        confirmButton.setDisable(true);
        if(!getGuiManager().getClientManager().isMyTurn() || getGuiManager().getClientManager().isMainActionDone()) {
            confirmButton.setVisible(false);
            selected.setVisible(false);
        }
        setShop();
    }

    /**
     * Upon selecting an image, show it in the selected frame and save row and column of the selected one
     * @param event click event
     */
    @FXML
    private void selectImage(MouseEvent event) {
        String id = event.getPickResult().getIntersectedNode().getId();
        switch (id){
            case "g3":
                    row = 0;
                    col = 0;
                    selected.setImage(g3.getImage());
                break;
            case "g2":
                    row = 1;
                    col = 0;
                    selected.setImage(g2.getImage());
                break;
            case "g1":
                    row = 2;
                    col = 0;
                    selected.setImage(g1.getImage());
                break;
            case "b3":
                    row=0;
                    col =1;
                    selected.setImage(b3.getImage());
                break;
            case "b2":
                    row=1;
                    col =1;
                    selected.setImage(b2.getImage());
                break;
            case "b1":
                    row=2;
                    col =1;
                    selected.setImage(b1.getImage());
                break;
            case "y3":
                    row=0;
                    col =2;
                    selected.setImage(y3.getImage());
                break;
            case "y2":
                    row=1;
                    col =2;
                    selected.setImage(y2.getImage());
                break;
            case "y1":
                    row=2;
                    col =2;
                    selected.setImage(y1.getImage());
                break;
            case "p3":
                    row=0;
                    col =3;
                    selected.setImage(p3.getImage());
                break;
            case "p2":
                    row=1;
                    col =3;
                    selected.setImage(p2.getImage());
                break;
            case "p1":
                    row=2;
                    col =3;
                    selected.setImage(p1.getImage());
                break;
        }
        confirmButton.setDisable(false);
    }

    /**
     * After clicking "Confirm" button, call the method to pass on to the payment scene
     */
    @FXML
    private void sendToPayment() {
        if(confirmButton.isDisable()) {
            errorLabel.setVisible(true);
        }
        if(selected.getImage()==null) {
            errorLabel.setVisible(true);
        }
        else{
            //passing the parameter got in this scene to the next scene of payment;
            Platform.runLater(this::goToPayment);
        }
    }

    /**
     * Setting the layout for the payment scene and passing the card as parameter
     */
    private void goToPayment(){
        Shop shopGrid = getGuiManager().getClientManager().getGameStatus().getShop();
        getGuiManager().setLayout("payment.fxml");
        getGuiManager().getCurrentController().setBuyCard(shopGrid.getGrid()[row][col].getFirst());
    }

    /**
     * Go back to dashboard not committing the choice
     */
    public void goBackToDashboard() {
        Platform.runLater(()->getGuiManager().callDashboard());
    }

    /**
     * Called by the GuiObserver upon receiving a shop update, refresh the shop
     */
    @Override
    public void updateShop(){
        setShop();
    }

    /**
     * Loading the graphic resources for the shop
     */
    private void setShop(){
        Shop shopGrid = getGuiManager().getClientManager().getGameStatus().getShop();
        //GREEN CARDS
        if (shopGrid.getGrid()[0][0].getDeck().size() != 0) {
            setImage(g3, "/img/cards/front/DevelopmentCards/" + shopGrid.getGrid()[0][0].getFirst().getId() + ".png");
            if(!getGuiManager().getClientManager().canBuySpecificCard(shopGrid.getGrid()[0][0].getFirst().getId())){
                g3.setDisable(true);
                g3.setOpacity(0.7);
            }
        }
        else
            g3.setDisable(true);
        if (shopGrid.getGrid()[1][0].getDeck().size() != 0) {
            setImage(g2, "/img/cards/front/DevelopmentCards/" + shopGrid.getGrid()[1][0].getFirst().getId() + ".png");
            if(!getGuiManager().getClientManager().canBuySpecificCard(shopGrid.getGrid()[1][0].getFirst().getId())){
                g2.setDisable(true);
                g2.setOpacity(0.7);
            }
        }
        else
            g2.setDisable(true);
        if (shopGrid.getGrid()[2][0].getDeck().size() != 0) {
            setImage(g1, "/img/cards/front/DevelopmentCards/" + shopGrid.getGrid()[2][0].getFirst().getId() + ".png");
            if(!getGuiManager().getClientManager().canBuySpecificCard(shopGrid.getGrid()[2][0].getFirst().getId())){
                g1.setDisable(true);
                g1.setOpacity(0.7);
            }
        }
        else
            g1.setDisable(true);
        //BLUE CARDS
        if (shopGrid.getGrid()[0][1].getDeck().size() != 0) {
            setImage(b3, "/img/cards/front/DevelopmentCards/" + shopGrid.getGrid()[0][1].getFirst().getId() + ".png");
            if(!getGuiManager().getClientManager().canBuySpecificCard(shopGrid.getGrid()[0][1].getFirst().getId())){
                b3.setDisable(true);
                b3.setOpacity(0.7);
            }
        }
        else
            b3.setDisable(true);
        if (shopGrid.getGrid()[1][1].getDeck().size() != 0) {
            setImage(b2, "/img/cards/front/DevelopmentCards/" + shopGrid.getGrid()[1][1].getFirst().getId() + ".png");
            if(!getGuiManager().getClientManager().canBuySpecificCard(shopGrid.getGrid()[1][1].getFirst().getId())){
                b2.setDisable(true);
                b2.setOpacity(0.7);
            }
        }
        else
            b2.setDisable(true);
        if (shopGrid.getGrid()[2][1].getDeck().size() != 0) {
            setImage(b1, "/img/cards/front/DevelopmentCards/" + shopGrid.getGrid()[2][1].getFirst().getId() + ".png");
            if(!getGuiManager().getClientManager().canBuySpecificCard(shopGrid.getGrid()[2][1].getFirst().getId())){
                b1.setDisable(true);
                b1.setOpacity(0.7);
            }
        }
        else
            b1.setDisable(true);

        //YELLOW CARDS
        if (shopGrid.getGrid()[0][2].getDeck().size() != 0) {
            setImage(y3, "/img/cards/front/DevelopmentCards/" + shopGrid.getGrid()[0][2].getFirst().getId() + ".png");
            if(!getGuiManager().getClientManager().canBuySpecificCard(shopGrid.getGrid()[0][2].getFirst().getId())){
                y3.setDisable(true);
                y3.setOpacity(0.7);
            }
        }
        else
            y3.setDisable(true);
        if (shopGrid.getGrid()[1][2].getDeck().size() != 0) {
            setImage(y2, "/img/cards/front/DevelopmentCards/" + shopGrid.getGrid()[1][2].getFirst().getId() + ".png");
            if(!getGuiManager().getClientManager().canBuySpecificCard(shopGrid.getGrid()[1][2].getFirst().getId())){
                y2.setDisable(true);
                y2.setOpacity(0.7);
            }
        }
        else
            y2.setDisable(true);
        if (shopGrid.getGrid()[2][2].getDeck().size() != 0) {
            setImage(y1, "/img/cards/front/DevelopmentCards/" + shopGrid.getGrid()[2][2].getFirst().getId() + ".png");
            if(!getGuiManager().getClientManager().canBuySpecificCard(shopGrid.getGrid()[2][2].getFirst().getId())){
                y1.setDisable(true);
                y1.setOpacity(0.7);
            }
        }
        else
            y1.setDisable(true);

        //PURPLE CARDS
        if (shopGrid.getGrid()[0][3].getDeck().size() != 0) {
            setImage(p3, "/img/cards/front/DevelopmentCards/" + shopGrid.getGrid()[0][3].getFirst().getId() + ".png");
            if(!getGuiManager().getClientManager().canBuySpecificCard(shopGrid.getGrid()[0][3].getFirst().getId())){
                p3.setDisable(true);
                p3.setOpacity(0.7);
            }
        }
        else
            p3.setDisable(true);
        if (shopGrid.getGrid()[1][3].getDeck().size() != 0) {
            setImage(p2, "/img/cards/front/DevelopmentCards/" + shopGrid.getGrid()[1][3].getFirst().getId() + ".png");
            if(!getGuiManager().getClientManager().canBuySpecificCard(shopGrid.getGrid()[1][3].getFirst().getId())){
                p2.setDisable(true);
                p2.setOpacity(0.7);
            }
        }
        else
            p2.setDisable(true);
        if (shopGrid.getGrid()[2][3].getDeck().size() != 0) {
            setImage(p1, "/img/cards/front/DevelopmentCards/" + shopGrid.getGrid()[2][3].getFirst().getId() + ".png");
            if (!getGuiManager().getClientManager().canBuySpecificCard(shopGrid.getGrid()[2][3].getFirst().getId())) {
                p1.setDisable(true);
                p1.setOpacity(0.7);
            }
        }
        else
            p1.setDisable(true);
    }
}


