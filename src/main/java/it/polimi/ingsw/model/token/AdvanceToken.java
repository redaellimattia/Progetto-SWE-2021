package it.polimi.ingsw.model.token;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.PlayerDashboard;

public class AdvanceToken implements SoloToken {
    private int steps;
    private boolean reRoll;

    public AdvanceToken(boolean reRoll){
         if(reRoll)
             steps = 1;
         else
             steps = 2;
    }
    public int getSteps() {
        return steps;
    }

    public boolean isReRoll() {
        return reRoll;
    }

    @Override
    public void useToken(PlayerDashboard player){
        int count;
        player.updatePathPosition(steps);
        if(this.reRoll) {
            Game.rollTokens();
        }
    }
}
