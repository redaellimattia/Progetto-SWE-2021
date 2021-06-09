package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.model.MarketMarble;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.enumeration.MarbleColour;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.network.client.ClientManager;
import it.polimi.ingsw.view.gui.GuiManager;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.util.ArrayList;


public class MarketActionController extends GuiController {

    @FXML
    private Pane col1, col2, col3, col4, row1, row2, row3;

    @FXML
    private ImageView marble1, marble2, marble3, marble4, marble5, marble6,
            marble7, marble8, marble9, marble10, marble11, marble12,
            freeMarble;

    @FXML
    private Text marketActionMessage;

    @FXML
    private Button backToDashboard, confirmAction, closeMessage;

    @FXML
    private ListView<String> choicesList, getResourceList;

    @FXML
    private HBox preview;

    private Image whiteMarble = new Image("/img/marbles/whiteMarble.png");
    private Image redMarble = new Image("/img/marbles/redMarble.png");
    private Image yellowMarble = new Image("/img/marbles/yellowMarble.png");
    private Image blueMarble = new Image("/img/marbles/blueMarble.png");
    private Image purpleMarble = new Image("/img/marbles/purpleMarble.png");
    private Image greyMarble = new Image("/img/marbles/greyMarble.png");

    private int type;
    private int pos;
    MarketMarble[] marbles;
    int curChoice;
    boolean isWhiteMarbleConversion; // true if the current atomicMarketAction is a conversion with white marble
    LeaderCard chosenLeaderCard;
    private ArrayList<ImageView> marblesView;
    private ClientManager clientManager;

    @Override
    @FXML
    public void initialize() {
        super.setGuiManager(GuiManager.getInstance());
        this.clientManager = getGuiManager().getClientManager();
        confirmAction.setVisible(false);
        closeMessage.setVisible(false);
        choicesList.setVisible(false);
        getResourceList.setVisible(false);
        preview.setVisible(false);
        if(!clientManager.isMyTurn() || clientManager.isMainActionDone()) {
            marketActionMessage.setVisible(false);
            disableArrows();
        }
        setGrid();

    }

    /**
     * Shows the market dashboard placing marble images in the grid
     */
    public void setGrid(){
        marblesView = new ArrayList<>();
        marblesView.add(marble1);
        marblesView.add(marble2);
        marblesView.add(marble3);
        marblesView.add(marble4);
        marblesView.add(marble5);
        marblesView.add(marble6);
        marblesView.add(marble7);
        marblesView.add(marble8);
        marblesView.add(marble9);
        marblesView.add(marble10);
        marblesView.add(marble11);
        marblesView.add(marble12);

        MarketMarble[][] structure = clientManager.getGameStatus().getMarket().getStructure();

        int cur = 0;
        for(MarketMarble[] row: structure) {
            for(MarketMarble m: row) {
                switch (m.getColour()) {
                    case RED:
                        //setImage(marblesView.get(cur), "/img/marbles/whiteMarble.png");
                        marblesView.get(cur).setImage(redMarble);
                        break;
                    case BLUE:
                        //setImage(marblesView.get(cur), "/img/marbles/blueMarble.png");
                        marblesView.get(cur).setImage(blueMarble);
                        break;
                    case GREY:
                        //setImage(marblesView.get(cur), "/img/marbles/greyMarble.png");
                        marblesView.get(cur).setImage(greyMarble);
                        break;
                    case WHITE:
                        marblesView.get(cur).setImage(whiteMarble);
                        break;
                    case PURPLE:
                        marblesView.get(cur).setImage(purpleMarble);
                        break;
                    case YELLOW:
                        marblesView.get(cur).setImage(yellowMarble);
                        break;
                }
                cur++;
            }
        }
        MarketMarble freeMarketMarble = clientManager.getGameStatus().getMarket().getFreeMarble();
        setMarbleImage(freeMarketMarble, freeMarble);
    }

    /**
     * Selects the image file to show in the imageView based on the marble colour
     * @param marble the marble to represent
     * @param imageView the ImageView that will show the image of the marble
     */
    private void setMarbleImage(MarketMarble marble, ImageView imageView) {
        switch (marble.getColour()) {
            case RED:
                imageView.setImage(redMarble);
                break;
            case BLUE:
                imageView.setImage(blueMarble);
                break;
            case GREY:
                imageView.setImage(greyMarble);
                break;
            case WHITE:
                imageView.setImage(whiteMarble);
                break;
            case PURPLE:
                imageView.setImage(purpleMarble);
                break;
            case YELLOW:
                imageView.setImage(yellowMarble);
                break;
        }
    }

    /**
     * Method called when the user selects a row or column from the market
     * @param mouseEvent the mouseEvent associated with the choice (useful to retrieve the chosen row or column)
     */
    public void setChosenPos(MouseEvent mouseEvent) {
        String messageString;
        Pane clicked = (Pane) mouseEvent.getSource();
        switch(clicked.getId()) {
            case "row1":
                type = 0;
                pos = 1;
                break;
            case "row2":
                type = 0;
                pos = 2;
                break;
            case "row3":
                type = 0;
                pos = 3;
                break;
            case "col1":
                type = 1;
                pos = 1;
                break;
            case "col2":
                type = 1;
                pos = 2;
                break;
            case "col3":
                type = 1;
                pos = 3;
                break;
            case "col4":
                type = 1;
                pos = 4;
                break;
        }
        if(type == 0) {
            messageString = "You've selected row number ";
        }
        else {
            messageString = "You've selected column number ";
        }
        messageString += pos;
        marketActionMessage.setText(messageString);

        // Show a preview of the marbles selected
        MarketMarble[] marbles;
        marbles = clientManager.getMarketMarbles(type, pos);
        preview.getChildren().clear();
        ImageView previewImage;
        for(MarketMarble m: marbles) {
            previewImage = new ImageView();
            previewImage.setFitWidth(55);
            previewImage.setFitHeight(54);
            setMarbleImage(m, previewImage);
            preview.getChildren().add(previewImage);
        }
        preview.setVisible(true);
        confirmAction.setVisible(true);
        isWhiteMarbleConversion = false; // Value initialized
    }

    /**
     * Disables all the market arrows after the user has confirmed the choice
     * or if is not allowed to start a marketAction
     */
    public void disableArrows() {
        col1.setDisable(true);
        col2.setDisable(true);
        col3.setDisable(true);
        col4.setDisable(true);
        row1.setDisable(true);
        row2.setDisable(true);
        row3.setDisable(true);
    }

    /**
     * Returns to the dashboard scene
     * @param mouseEvent (could be null)
     */
    public void goBackToDashboard(MouseEvent mouseEvent) {
        Platform.runLater(()->getGuiManager().callDashboard());
        //getGuiManager().setNextScene();
    }

    /**
     * Called when the user confirms the row/column choice
     * @param mouseEvent the mouseEvent that triggered the method invocation
     */
    public void doMarketAction(MouseEvent mouseEvent) {
        disableArrows();
        confirmAction.setVisible(false);
        backToDashboard.setDisable(true);

        // Get marbles
        marbles = clientManager.getMarketMarbles(type, pos);
        curChoice = 0;
        doNextAtomicChoice();

    }

    /**
     * Updates the GUI to show options associated with the next marble the user is taking
     * or, if the marketAction is completed, notifies the server and returns to the dashboard
     */
    public void doNextAtomicChoice() {
        if((type == 0 && curChoice == 4) || (type == 1 && curChoice == 3)) {
            clientManager.endMarketAction(type, pos);
            goBackToDashboard(null);
        }
        else {
            // Highlight only the marble associated with this atomicMarketAction
            for(Node n: preview.getChildren()) {
                if(preview.getChildren().indexOf(n) == curChoice) {
                    n.setOpacity(1);
                }
                else {
                    n.setOpacity(0.25);
                }
            }
            if(marbles[curChoice].getColour() == MarbleColour.RED) {
                marketActionMessage.setText("You took a red marble, you will gain 1 faith point!");
                closeMessage.setVisible(true);
            }
            else if(marbles[curChoice].getColour() == MarbleColour.WHITE) {
                if(clientManager.hasWhiteChangeAbility()) {
                    marketActionMessage.setText("What do you want to do with the white marble?");
                    ObservableList<String> choices = FXCollections.observableArrayList("Do nothing");
                    for(LeaderCard c: clientManager.getThisClientDashboard().getLeaderCards()) {
                        if(c.getSpecialAbility().useWhiteChangeAbility() != null && c.isInGame()) {
                            choices.add("Convert to " + c.getSpecialAbility().useWhiteChangeAbility().name() + " with Leader Card");
                        }
                    }
                    choicesList.setItems(choices);
                    choicesList.getSelectionModel().clearSelection();
                    choicesList.setVisible(true);
                }
                else {
                    marketActionMessage.setText("You don't have a Leader Card that can convert the white marble.");
                    clientManager.getResource(0);
                    closeMessage.setVisible(true);
                }
            }
            else {
                showStoreResourceChoices(marbles[curChoice].getColour().convertToResource());
            }
        }
    }

    /**
     * Called when the user closes a message informing that there was only one possible choice
     * @param mouseEvent the mouseEvent that triggered the method invocation
     */
    public void noChoiceMessageClosed(MouseEvent mouseEvent) {
        closeMessage.setVisible(false);
        curChoice++;
        doNextAtomicChoice();
    }

    /**
     * Updates the GUI to ask user to select the storage row in witch store the resource (plus discard option)
     * @param resource the resource to store
     */
    public void showStoreResourceChoices(Resource resource) {
        marketActionMessage.setText("In which deposit do you want to store the " + resource + "?");
        ObservableList<String> options = FXCollections.observableArrayList("Discard", "Add to first storage row", "Add to second storage row", "Add to third storage row");
        if(clientManager.hasAdditionalDeposit(resource)) {
            options.add("Add to additional storage");
        }
        getResourceList.setItems(options);
        getResourceList.getSelectionModel().clearSelection();
        getResourceList.setVisible(true);
    }

    /**
     * Shows options related to the conversion of a white marble
     * @param mouseEvent the mouseEvent that triggered the method invocation
     */
    public void whiteMarbleChoice(MouseEvent mouseEvent) {
        // Check if the user has clicked on a part of the list with no item
        if(choicesList.getSelectionModel().getSelectedIndex() < 0) {
            return; // return without saving choice
        }
        if(choicesList.getSelectionModel().getSelectedIndex() == 0) {
            clientManager.getResource(0);
            choicesList.setVisible(false);
            curChoice++;
            doNextAtomicChoice();
        }
        else {
            chosenLeaderCard = clientManager.getWhiteChangeCard(choicesList.getSelectionModel().getSelectedIndex());
            Resource convertedResource = chosenLeaderCard.getSpecialAbility().getResourceType();
            isWhiteMarbleConversion = true;
            choicesList.setVisible(false);
            showStoreResourceChoices(convertedResource);
        }
    }

    /**
     * Called when the user selects where to store a resource
     * @param mouseEvent the mouseEvent that triggered the method invocation
     */
    public void storeResourceChoice(MouseEvent mouseEvent) {
        // Check if the user has clicked on a part of the list with no item
        if(getResourceList.getSelectionModel().getSelectedIndex() < 0) {
            return; // return without saving choice
        }
        if(getResourceList.getSelectionModel().getSelectedIndex() == 0) {
            clientManager.discardResource();
            isWhiteMarbleConversion = false; // In any case this value must be reset
        }
        else {
            if(isWhiteMarbleConversion) {
                clientManager.convertMarble(chosenLeaderCard, getResourceList.getSelectionModel().getSelectedIndex());
                isWhiteMarbleConversion = false; // reset the value after the action is done
            }
            else {
                clientManager.getResource(getResourceList.getSelectionModel().getSelectedIndex());
            }
        }
        getResourceList.setVisible(false);
        curChoice++;
        doNextAtomicChoice();
    }

    /**
     * Called when the server notifies an update of the market structure
     */
    @Override
    public void updateMarket(){
        setGrid();
    }
}
