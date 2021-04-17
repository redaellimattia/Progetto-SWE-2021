package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;

public class MarketAction extends Action {
    private int type;
    private int pos;
    private ArrayList<MarketMarble> marbles;

    public MarketAction(int type, int pos) {
        this.type = type;
        this.pos = pos;
        this.marbles = new ArrayList<>();
    }

    // Returns true if everything is done correctly
    // type = 0: row; type = 1: column (chosen from view)
    // pos: number of the row/column (starting from 1) chosen from view
    @Override
    public boolean useAction(PlayerDashboard player) {
        int i;
        int total;
        if (type == 0) {
            total = 4; // If a row is selected, the user gets 4 marbles
            try {
                MarketMarble[] marbles = Game.getMarket().getRow(pos);
            }
            catch(IndexOutOfBoundsException e) {
                return false;
            }
        }
        if (type == 1) {
            total = 3; // If a column is selected, the user gets 3 marbles
            try {
                MarketMarble[] marbles = Game.getMarket().getColumn(pos);
            }
            catch(IndexOutOfBoundsException e) {
                return false;
            }

        }
        return true;
        // TO-DO: The method creates an object in the model containing the marbles.
        // The user will choose from the view where to put every marble
        // The view will then call another method in this class to finalize the action
    }

}
