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

    /**
     * if there is text to be showed, it is passed to through this method
     * @param text to print
     */
    @FXML
    @Override
    public void setTextForWaiting(String text){
        textBox.setText(text);
        textBox.setX(-250);
    }
}
