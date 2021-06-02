package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.view.gui.GuiManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
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
    private int numberOfResources;
    private ResourceCount chosenResources;



    @FXML
    @Override
    public void initialize() {
        guiManager = GuiManager.getInstance();
        chosenResources = new ResourceCount(0,0,0,0,0);
    }

    @Override
    public void setPreGameChoice(ArrayList<LeaderCard> leaders, int numberOfResources){
        this.numberOfResources = numberOfResources;
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

    @FXML
    public void coinClicked(MouseEvent mouseEvent) {
        addResource(Resource.COIN,xCoin);
    }

    public void rockClicked(MouseEvent mouseEvent) {
        addResource(Resource.ROCK,xRock);
    }

    public void shieldClicked(MouseEvent mouseEvent) {
        addResource(Resource.SHIELD,xShield);
    }

    public void servantClicked(MouseEvent mouseEvent) {
        addResource(Resource.SERVANT,xServant);
    }

    private void addResource(Resource res, Label xLabel){
        if(numberOfResources>ResourceCount.resCountToInt(chosenResources)){
            res.add(chosenResources,1);
            if(!xLabel.isVisible())
                xLabel.setVisible(true);
            xLabel.setText("x"+res.get(chosenResources));
        }
    }
}
