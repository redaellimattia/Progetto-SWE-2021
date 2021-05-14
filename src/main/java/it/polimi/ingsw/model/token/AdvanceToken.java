package it.polimi.ingsw.model.token;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.PlayerDashboard;

public class AdvanceToken implements SoloToken {
    private int steps;
    private boolean reRoll;
    private GameManager gameManager;

    public AdvanceToken(boolean reRoll, GameManager gameManager){
        this.gameManager = gameManager;
        this.reRoll = reRoll;
         if(reRoll)
             this.steps = 1;
         else
             this.steps = 2;
    }
    public int getSteps() {
        return steps;
    }

    public boolean isReRoll() {
        return reRoll;
    }

    @Override
    public void useToken(PlayerDashboard lorenzo, Game game){
        int countSteps = steps;
        while(countSteps!=0) {
            lorenzo.updatePathPosition();
            countSteps--;
        }
        if(this.reRoll) {
            game.rollTokens();
        }
    }
}
