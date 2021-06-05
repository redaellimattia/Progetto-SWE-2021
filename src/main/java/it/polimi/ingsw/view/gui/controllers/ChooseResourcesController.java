package it.polimi.ingsw.view.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class ChooseResourcesController extends GuiController{
    @FXML
    private Label error;
    @FXML
    private Text title;
    @FXML
    private Label xCoinChosen,xRockChosen,xShieldChosen,xServantChosen;
    @FXML
    private AnchorPane coin,shield,servant,rock;
    private int toChoose;

    @Override
    public void setModal(boolean isInput) {
        if(isInput){
            toChoose = 2;
        }
        else
            toChoose = 1;
    }
}
