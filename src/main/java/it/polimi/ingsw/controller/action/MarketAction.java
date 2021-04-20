package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.controller.action.marketAction.AtomicMarketAction;
import it.polimi.ingsw.model.*;

import java.util.ArrayList;

public class MarketAction extends Action {
    private int type;
    private int pos;
    private ArrayList<AtomicMarketAction> choices;

    public MarketAction(int type, int pos, ArrayList<AtomicMarketAction> choices) {
        this.type = type;
        this.pos = pos;
        this.choices = choices;
    }

    // Returns true if everything is done correctly
    // type = 0: row; type = 1: column (chosen from view)
    // pos: number of the row/column (starting from 1) chosen from view
    @Override
    public boolean useAction(PlayerDashboard player) {
        MarketMarble[] marbles = new MarketMarble[0];
        boolean error = false;

        // Getting marbles from the market
        if (type == 0) { // A row is selected
            try {
                marbles = Game.getMarket().getRow(pos);
            }
            catch(IndexOutOfBoundsException e) {
                return false;
            }
        }
        if (type == 1) { // A column is selected
            try {
                marbles = Game.getMarket().getColumn(pos);
            }
            catch(IndexOutOfBoundsException e) {
                return false;
            }
        }

        // Executing actions (useAction method will check if an action is legal)
        for(int i = 0; i < marbles.length; i++) {
            error = choices.get(i).useAction(marbles[i], player);
            if(error) return false;
        }
        return true;
        // TO-DO: The method creates an object in the model containing the marbles.
        // The user will choose from the view where to put every marble
        // The view will then call another method in this class to finalize the action
    }

}
