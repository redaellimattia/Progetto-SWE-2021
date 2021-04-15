package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.enumeration.CardColour;
import it.polimi.ingsw.model.token.SoloToken;

public class SoloTokenAction extends Action {

    public boolean drawToken(PlayerDashboard player, PlayerDashboard lorenzo) {
        SoloToken token = Game.pickNextToken();
        token.useToken(lorenzo);
        // TO-DO: Check if Vatican Report is triggered (if it isn't checked by model)

        // Check if Lorenzo has reached the end of Faith Track
        if(lorenzo.getPathPosition() == 24) {
            // Inform GameManager that user has lost
            return true;
        }
        // Check if the user has reached the end of Faith Track
        if(player.getPathPosition() == 24) {
            // Inform GameManager that user has won
            return true;
        }
        // Check if there is at least one empty column in shop (the user has lost)
        boolean emptyCol = false; // flag that indicates that there is at least one empty column
        for(int i = 0; i < CardColour.values().length; i++) { // Check if each column has at least one non-empty deck
            boolean flag = false;
            for(int j = 0; j < 2; j++) {
                if(!Game.getShop().getGrid()[j][i].getDeck().isEmpty()) {
                    flag = true; // This column is not empty
                }
            }
            if(flag == false) { // At the end of each column, check if the column was empty
                emptyCol = true; // At least one empty column
            }
        }
        if(emptyCol) {
            // Inform GameManager that user has lost
            return true;
        }
        return true;
    }

}
