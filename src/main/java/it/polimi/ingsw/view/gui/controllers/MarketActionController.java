package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.model.MarketMarble;
import it.polimi.ingsw.network.client.ClientManager;
import it.polimi.ingsw.view.gui.GuiManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
    private Button backToDashboard, confirmAction;

    private int type;
    private int pos;
    private ArrayList<ImageView> marblesView;
    private ClientManager clientManager;

    @Override
    @FXML
    public void initialize() {
        super.setGuiManager(GuiManager.getInstance());
        this.clientManager = getGuiManager().getClientManager();
        confirmAction.setVisible(false);
        if(!clientManager.isMyTurn() || clientManager.isMainActionDone()) {
            marketActionMessage.setVisible(false);
            disableArrows();
        }
        setGrid();

    }
    public void setGrid(){
        Image whiteMarble = new Image("/img/marbles/whiteMarble.png");
        Image redMarble = new Image("/img/marbles/redMarble.png");
        Image yellowMarble = new Image("/img/marbles/yellowMarble.png");
        Image blueMarble = new Image("/img/marbles/blueMarble.png");
        Image purpleMarble = new Image("/img/marbles/purpleMarble.png");
        Image greyMarble = new Image("/img/marbles/greyMarble.png");

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

        MarketMarble structure[][] = clientManager.getGameStatus().getMarket().getStructure();

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
        switch (freeMarketMarble.getColour()) {
            case RED:
                freeMarble.setImage(redMarble);
                break;
            case BLUE:
                freeMarble.setImage(blueMarble);
                break;
            case GREY:
                freeMarble.setImage(greyMarble);
                break;
            case WHITE:
                freeMarble.setImage(whiteMarble);
                break;
            case PURPLE:
                freeMarble.setImage(purpleMarble);
                break;
            case YELLOW:
                freeMarble.setImage(yellowMarble);
                break;
        }
    }

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
        confirmAction.setVisible(true);
    }

    public void selectResources(int type, int pos) {
        MarketMarble marbles[];
        marbles = clientManager.getMarketMarbles(type, pos);
        for(MarketMarble m: marbles) {
            // Atomic choice
        }
    }

    public void disableArrows() {
        col1.setDisable(true);
        col2.setDisable(true);
        col3.setDisable(true);
        col4.setDisable(true);
        row1.setDisable(true);
        row2.setDisable(true);
        row3.setDisable(true);
    }

    public void goBackToDashboard(MouseEvent mouseEvent) {
        Platform.runLater(()->getGuiManager().callDashboard());
        getGuiManager().setNextScene();
    }

    public void doMarketAction(MouseEvent mouseEvent) {
        disableArrows();
        confirmAction.setVisible(false);
    }

    @Override
    public void updateMarket(){
        setGrid();
    }
}
