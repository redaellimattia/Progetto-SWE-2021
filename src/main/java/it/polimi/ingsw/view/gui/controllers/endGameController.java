package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.network.client.PlayerPoints;
import it.polimi.ingsw.view.gui.GuiManager;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class endGameController extends GuiController{
    @FXML
    private Text firstPlayer,secondPlayer,thirdPlayer,fourthPlayer;
    @FXML
    private ImageView endGameLogo;

    @FXML
    @Override
    public void initialize(){
        super.setGuiManager(GuiManager.getInstance());
    }
    @Override
    public void setEndGame(boolean lorenzoWin, int playerPoints){
        thirdPlayer.setVisible(false);
        fourthPlayer.setVisible(false);
        secondPlayer.setVisible(false);
        if(lorenzoWin) {
            setImage(endGameLogo, "Inserire path dell'immagine you lost");
            firstPlayer.setText("Unfortunately you lost, but you managed to score: " + playerPoints + " points");
        }
        else {
            setImage(endGameLogo, "Inserire path dell'immagine you won");
            firstPlayer.setText("You won with a total of: " + playerPoints+ " points");
        }
    }

    @Override
    public void setEndGame(ArrayList<PlayerPoints> scoreboard){
        if(getGuiManager().getClientManager().getNickname().equals(scoreboard.get(0).getPlayer()))
            setImage(endGameLogo,"Inserire path dell'immagine you won");
        else
            setImage(endGameLogo,"Inserire path dell'immagine you lost");

        firstPlayer.setText("1. " + scoreboard.get(0).getPlayer() + scoreboard.get(0).getVictoryPoints());
        secondPlayer.setText("2. " + scoreboard.get(1).getPlayer() + scoreboard.get(1).getVictoryPoints());
        if(scoreboard.size()==3)
            thirdPlayer.setText("3. " + scoreboard.get(2).getPlayer() + scoreboard.get(2).getVictoryPoints());
        else
            thirdPlayer.setVisible(false);
        if(scoreboard.size()==4)
            fourthPlayer.setText("4. " + scoreboard.get(3).getPlayer() + scoreboard.get(3).getVictoryPoints());
        else
            fourthPlayer.setVisible(false);

    }
}
