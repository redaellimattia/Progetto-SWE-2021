package it.polimi.ingsw.model;

public class AdvanceToken extends SoloToken {
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
}
