package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.view.gui.GuiManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class PreGameChoiceController extends GuiController{

    @FXML
    private Label errorLabel;
    @FXML
    private ImageView shieldImage;
    @FXML
    private ImageView servantImage;
    @FXML
    private ImageView rockImage;
    @FXML
    private ImageView firstLeader;
    @FXML
    private ImageView secondLeader;
    @FXML
    private ImageView thirdLeader;
    @FXML
    private ImageView fourthLeader;
    @FXML
    private Button sendChoicesButton;
    @FXML
    private ImageView coinImage;
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
    private AnchorPane resourcesBox;

    private int numberOfResources;
    private ArrayList<LeaderCard> availableLeaders;
    private ArrayList<LeaderCard> chosenLeaderCards;
    private ResourceCount chosenResources;



    @FXML
    @Override
    public void initialize() {
        super.setGuiManager(GuiManager.getInstance());
        chosenResources = new ResourceCount(0,0,0,0,0);
        chosenLeaderCards = new ArrayList<>();
    }

    @Override
    public void setPreGameChoice(ArrayList<LeaderCard> leaders, int numberOfResources){
        this.availableLeaders = leaders;
        setImage(firstLeader,"/img/cards/front/LeaderCards/"+leaders.get(0).getId()+".png");
        setImage(secondLeader,"/img/cards/front/LeaderCards/"+leaders.get(1).getId()+".png");
        setImage(thirdLeader,"/img/cards/front/LeaderCards/"+leaders.get(2).getId()+".png");
        setImage(fourthLeader,"/img/cards/front/LeaderCards/"+leaders.get(3).getId()+".png");
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

    //When a resource image is clicked, add 1 resource
    @FXML
    public void coinClicked(MouseEvent mouseEvent) {
        addResource(Resource.COIN,xCoin);
    }
    @FXML
    public void rockClicked(MouseEvent mouseEvent) {
        addResource(Resource.ROCK,xRock);
    }
    @FXML
    public void shieldClicked(MouseEvent mouseEvent) {
        addResource(Resource.SHIELD,xShield);
    }
    @FXML
    public void servantClicked(MouseEvent mouseEvent) {
        addResource(Resource.SERVANT,xServant);
    }

    @FXML
    private void addResource(Resource res, Label xLabel){
        if(numberOfResources>ResourceCount.resCountToInt(chosenResources)){
            res.add(chosenResources,1);
            if(!xLabel.isVisible())
                xLabel.setVisible(true);
            if(xLabel.isDisable())
                xLabel.setDisable(false);
            xLabel.setText("x"+res.get(chosenResources));
            if(numberOfResources==ResourceCount.resCountToInt(chosenResources))
                disableResourcesClick();
        }

    }

    @FXML
    private void disableResourcesClick(){
        coinImage.setDisable(true);
        shieldImage.setDisable(true);
        servantImage.setDisable(true);
        rockImage.setDisable(true);
    }

    @FXML
    private void enableResourcesClick(){
        coinImage.setDisable(false);
        shieldImage.setDisable(false);
        servantImage.setDisable(false);
        rockImage.setDisable(false);
    }

    @FXML
    private void removeResource(Resource res, Label xLabel){
        res.remove(chosenResources,1);
        if(res.get(chosenResources)==0) {
            xLabel.setText("");
            xLabel.setDisable(true);
        }
        else
            xLabel.setText("x"+res.get(chosenResources));
        enableResourcesClick();
    }

    //When a label is clicked, remove 1 resource
    @FXML
    public void xCoinClicked(MouseEvent mouseEvent) {
        removeResource(Resource.COIN,xCoin);
    }
    @FXML
    public void xRockClicked(MouseEvent mouseEvent) {
        removeResource(Resource.ROCK,xRock);
    }
    @FXML
    public void xShieldClicked(MouseEvent mouseEvent) {
        removeResource(Resource.SHIELD,xShield);
    }
    @FXML
    public void xServantClicked(MouseEvent mouseEvent) {
        removeResource(Resource.SERVANT,xServant);
    }

    @FXML
    public void fourthLeaderClick(MouseEvent mouseEvent) {
        if(fourthLeaderChosen.isVisible()){ //Remove leader
            chosenLeaderCards.remove(availableLeaders.get(3));
            fourthLeaderChosen.setVisible(false);
        }
        else {
            if(chosenLeaderCards.size()<2) {
                this.chosenLeaderCards.add(availableLeaders.get(3));
                fourthLeaderChosen.setVisible(true);
            }
        }
    }
    @FXML
    public void thirdLeaderClick(MouseEvent mouseEvent) {
        if(thirdLeaderChosen.isVisible()){ //Remove leader
            chosenLeaderCards.remove(availableLeaders.get(2));
            thirdLeaderChosen.setVisible(false);
        }
        else {
            if(chosenLeaderCards.size()<2) {
                this.chosenLeaderCards.add(availableLeaders.get(2));
                thirdLeaderChosen.setVisible(true);
            }
        }
    }
    @FXML
    public void secondLeaderClick(MouseEvent mouseEvent) {
        if(secondLeaderChosen.isVisible()){ //Remove leader
            chosenLeaderCards.remove(availableLeaders.get(1));
            secondLeaderChosen.setVisible(false);
        }
        else {
            if(chosenLeaderCards.size()<2) {
                this.chosenLeaderCards.add(availableLeaders.get(1));
                secondLeaderChosen.setVisible(true);
            }
        }
    }
    @FXML
    public void firstLeaderClick(MouseEvent mouseEvent) {
        if(firstLeaderChosen.isVisible()){ //Remove leader
            chosenLeaderCards.remove(availableLeaders.get(0));
            firstLeaderChosen.setVisible(false);
        }
        else {
            if(chosenLeaderCards.size()<2) {
                this.chosenLeaderCards.add(availableLeaders.get(0));
                firstLeaderChosen.setVisible(true);
            }
        }
    }

    @FXML
    public void sendChoices(MouseEvent mouseEvent) {
        if(ResourceCount.resCountToInt(chosenResources)!=numberOfResources||chosenLeaderCards.size()<2)
            errorLabel.setVisible(true);
        else{
            if(errorLabel.isVisible())
                errorLabel.setVisible(false);
            sendChoicesButton.setDisable(true);
            Platform.runLater(()->getGuiManager().getClientManager().preGameChoice(getResources(),chosenLeaderCards));
            goToWaiting("Waiting other player's choices...");
        }

    }

    private ArrayList<Resource> getResources(){
        ArrayList<Resource> resources = new ArrayList<>();
        int takenRes = 0;
        while(takenRes!=numberOfResources){
            if(chosenResources.getCoins()>0) {
                resources.add(Resource.COIN);
                takenRes++;
            }
            if(chosenResources.getRocks()>0) {
                resources.add(Resource.ROCK);
                takenRes++;
            }
            if(chosenResources.getServants()>0) {
                resources.add(Resource.SERVANT);
                takenRes++;
            }
            if(chosenResources.getShields()>0) {
                resources.add(Resource.SHIELD);
                takenRes++;
            }
        }
        return resources;
    }

    public void goToWaiting(String msg){
        getGuiManager().setLayout("waitingPage.fxml");
        getGuiManager().getCurrentController().setTextForWaiting(msg);
    }
}
