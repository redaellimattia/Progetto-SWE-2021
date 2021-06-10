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
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class PreGameChoiceController extends GuiController{

    @FXML
    private Label error;
    @FXML
    private ImageView shieldImage,coinImage,servantImage,rockImage;
    @FXML
    private ImageView firstLeader,secondLeader,thirdLeader,fourthLeader;
    @FXML
    private Button sendChoicesButton;
    @FXML
    private Text chooseResourcesText;
    @FXML
    private AnchorPane firstLeaderChosen,secondLeaderChosen,thirdLeaderChosen,fourthLeaderChosen;
    @FXML
    private Label xServant,xCoin,xShield,xRock;
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

    /**
     * Sets leadercards image, and eventually make the choose resource visible
     *
     * @param leaders the 4 available leaders coming from the server
     * @param numberOfResources the amount of resources that the player has to choose
     *
     */
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

    /**
     * Image clicked add 1 coin
     */
    @FXML
    public void coinClicked() {
        addResource(Resource.COIN,xCoin,chosenResources,numberOfResources,coinImage,shieldImage,servantImage,rockImage);
    }

    /**
     * Image clicked add 1 rock
     */
    @FXML
    public void rockClicked() {
        addResource(Resource.ROCK,xRock,chosenResources,numberOfResources,coinImage,shieldImage,servantImage,rockImage);
    }

    /**
     * Image clicked add 1 shield
     */
    @FXML
    public void shieldClicked() {
        addResource(Resource.SHIELD,xShield,chosenResources,numberOfResources,coinImage,shieldImage,servantImage,rockImage);
    }

    /**
     * Image clicked add 1 servant
     */
    @FXML
    public void servantClicked() {
        addResource(Resource.SERVANT,xServant,chosenResources,numberOfResources,coinImage,shieldImage,servantImage,rockImage);
    }

    /**
     * Label clicked remove 1 coin
     */
    @FXML
    public void xCoinClicked() {
        removeResource(Resource.COIN,xCoin,chosenResources,coinImage,shieldImage,servantImage,rockImage);
    }

    /**
     * Label clicked remove 1 rock
     */
    @FXML
    public void xRockClicked() {
        removeResource(Resource.ROCK,xRock,chosenResources,coinImage,shieldImage,servantImage,rockImage);
    }

    /**
     * Label clicked remove 1 shield
     */
    @FXML
    public void xShieldClicked() {
        removeResource(Resource.SHIELD,xShield,chosenResources,coinImage,shieldImage,servantImage,rockImage);
    }

    /**
     * Label clicked remove 1 servant
     */
    @FXML
    public void xServantClicked() {
        removeResource(Resource.SERVANT,xServant,chosenResources,coinImage,shieldImage,servantImage,rockImage);
    }

    /**
     * When the fourth leader is clicked, add or remove it (if already clicked) from the list of chosen leaders
     */
    @FXML
    public void fourthLeaderClick() {
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

    /**
     * When the third leader is clicked, add or remove it (if already clicked) from the list of chosen leaders
     */
    @FXML
    public void thirdLeaderClick() {
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

    /**
     * When the second leader is clicked, add or remove it (if already clicked) from the list of chosen leaders
     */
    @FXML
    public void secondLeaderClick() {
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

    /**
     * When the first leader is clicked, add or remove it (if already clicked) from the list of chosen leaders
     */
    @FXML
    public void firstLeaderClick() {
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

    /**
     * Send choices to the clientManager preGameChoice
     */
    @FXML
    public void sendChoices() {
        if(ResourceCount.resCountToInt(chosenResources)!=numberOfResources||chosenLeaderCards.size()<2)
            error.setVisible(true);
        else{
            if(error.isVisible())
                error.setVisible(false);
            sendChoicesButton.setDisable(true);
            Platform.runLater(()->getGuiManager().getClientManager().preGameChoice(getResources(),chosenLeaderCards));
            goToWaiting("Waiting other player's choices...");
        }

    }

    /**
     * Turn the ResourceCount of chosen resources into an arrayList
     *
     * @return an ArrayList of Resource full of the chosen resources by the player
     */
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

    /**
     * Changes scene, go to waiting page
     *
     * @param msg msg that will be displayed in the waitingPage
     */
    public void goToWaiting(String msg){
        getGuiManager().setLayout("waitingPage.fxml");
        getGuiManager().getCurrentController().setTextForWaiting(msg);
    }
}
