package it.polimi.ingsw.model.token;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.network.server.Observer;

public class AdvanceToken implements SoloToken {
    private final int steps;
    private final boolean reRoll;

    public AdvanceToken(boolean reRoll){
        this.reRoll = reRoll;
         if(reRoll)
             this.steps = 1;
         else
             this.steps = 2;
    }

    public boolean isReRoll() {
        return reRoll;
    }

    /**
     * Executes the action represented by the token
     * @param lorenzo lorenzo dashboard
     * @param game the current Game object
     * @param observer serverLobby observer
     */
    @Override
    public void useToken(PlayerDashboard lorenzo, Game game, Observer observer){
        int countSteps = steps;
        while(countSteps!=0) {
            lorenzo.updatePathPosition();
            countSteps--;
        }
        if(this.reRoll)
            game.rollTokens();
        observer.lorenzoAction("Lorenzo advanced of: "+steps+" steps on the faith path!");
    }
}
