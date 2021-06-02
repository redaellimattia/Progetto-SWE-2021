package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.view.gui.GuiManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class PreGameChoiceController extends GuiController{

    @FXML
    private Text chooseResourcesText;
    @FXML
    private AnchorPane fourthLeaderChosen;
    @FXML
    private AnchorPane thirdLeaderChosen;
    @FXML
    private AnchorPane secondLeaderChosen;
    @FXML
    private AnchorPane firstLeaderChosen;
    @FXML
    private Label xServant;
    @FXML
    private Label xShield;
    @FXML
    private Label xRock;
    @FXML
    private Label xCoin;
    @FXML
    private Image fourthLeaderImage;
    @FXML
    private Image thirdLeaderImage;
    @FXML
    private Image secondLeaderImage;
    @FXML
    private Image firstLeaderImage;
    @FXML
    private AnchorPane resourcesBox;
    
    private GuiManager guiManager;


    @FXML
    @Override
    public void initialize() {
        guiManager = GuiManager.getInstance();
    }

    @Override
    public void setPreGameChoice(ArrayList<LeaderCard> leaders, int numberOfResources){
        switch(numberOfResources){
            case 0: resourcesBox.setVisible(false);
                break;
            case 1:
                    chooseResourcesText.setText("Choose 1 Resource");
                break;
            case 2:   chooseResourcesText.setText("Choose 2 Resources");
                break;
        }
    }
}
