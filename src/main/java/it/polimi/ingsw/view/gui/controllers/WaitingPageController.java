package it.polimi.ingsw.view.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class WaitingPageController extends GuiController{
    @FXML
    private Text textBox;

    @Override
    public void initialize() {
        super.initialize();
    }

    @FXML
    @Override
    public void setTextForWaiting(String text){
        textBox.setText(text);
        textBox.setX(-250);
    }
}
