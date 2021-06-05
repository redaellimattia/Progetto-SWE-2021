package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.enumeration.Resource;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class ChooseResourcesController extends GuiController{
    @FXML
    private ImageView coin,rock,shield,servant;
    @FXML
    private Label error;
    @FXML
    private Text title;
    @FXML
    private Label xCoinChosen,xRockChosen,xShieldChosen,xServantChosen;


    private int toChoose;
    private boolean isInput;
    private ResourceCount chosenInput;
    private ResourceCount chosenOutput;
    private boolean isBasic;

    @Override
    public void setModal(boolean isInput,boolean isBasic) {
        this.isInput = isInput;
        this.isBasic = isBasic;
        if(isInput){
            toChoose = 2;
            chosenInput = new ResourceCount(0,0,0,0,0);
            title.setText("Choose 2 Resources as input");
        }
        else {
            toChoose = 1;
            chosenOutput = new ResourceCount(0,0,0,0,0);
            title.setText("Choose 1 Resource as output");
        }
    }

    @FXML
    @Override
    public void initialize(){
    }

    public void confirmClick(MouseEvent mouseEvent) {
            if (isInput) {
                if(toChoose==ResourceCount.resCountToInt(chosenInput))
                    launchChooseResources(false,isBasic);
                else
                    printError();
            }
            else {
                if (toChoose == ResourceCount.resCountToInt(chosenOutput))
                    launchChooseResources(true,isBasic); //PAYMENT
                else
                    printError();
            }
    }

    private void printError(){
        error.setText("You haven't chosen enough resources!");
        error.setVisible(true);
    }

    public void coinClick(MouseEvent mouseEvent) {
        if(isInput)
            addResource(Resource.COIN,xCoinChosen,chosenInput,toChoose,coin,shield,servant,rock);
        else
            addResource(Resource.COIN,xCoinChosen,chosenOutput,toChoose,coin,shield,servant,rock);
    }

    public void shieldClick(MouseEvent mouseEvent) {
        if(isInput)
            addResource(Resource.SHIELD,xShieldChosen,chosenInput,toChoose,coin,shield,servant,rock);
        else
            addResource(Resource.SHIELD,xShieldChosen,chosenOutput,toChoose,coin,shield,servant,rock);
    }

    public void servantClick(MouseEvent mouseEvent) {
        if(isInput)
            addResource(Resource.SERVANT,xServantChosen,chosenInput,toChoose,coin,shield,servant,rock);
        else
            addResource(Resource.SERVANT,xServantChosen,chosenOutput,toChoose,coin,shield,servant,rock);
    }

    public void rockClick(MouseEvent mouseEvent) {
        if(isInput)
            addResource(Resource.ROCK,xRockChosen,chosenInput,toChoose,coin,shield,servant,rock);
        else
            addResource(Resource.ROCK,xRockChosen,chosenOutput,toChoose,coin,shield,servant,rock);
    }

    public void xRockClick(MouseEvent mouseEvent) {
        if(isInput)
            removeResource(Resource.ROCK,xRockChosen,chosenInput,coin,shield,servant,rock);
        else
            removeResource(Resource.ROCK,xRockChosen,chosenOutput,coin,shield,servant,rock);
    }

    public void xCoinClick(MouseEvent mouseEvent) {
        if(isInput)
            removeResource(Resource.COIN,xCoinChosen,chosenInput,coin,shield,servant,rock);
        else
            removeResource(Resource.COIN,xCoinChosen,chosenOutput,coin,shield,servant,rock);
    }

    public void xServantClick(MouseEvent mouseEvent) {
        if(isInput)
            removeResource(Resource.SERVANT,xServantChosen,chosenInput,coin,shield,servant,rock);
        else
            removeResource(Resource.SERVANT,xServantChosen,chosenOutput,coin,shield,servant,rock);
    }

    public void xShieldClick(MouseEvent mouseEvent) {
        if(isInput)
            removeResource(Resource.SHIELD,xShieldChosen,chosenInput,coin,shield,servant,rock);
        else
            removeResource(Resource.SHIELD,xShieldChosen,chosenOutput,coin,shield,servant,rock);
    }






}
