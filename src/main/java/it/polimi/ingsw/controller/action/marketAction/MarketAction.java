package it.polimi.ingsw.controller.action.marketAction;
import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.exceptions.action.IllegalMarketPositionException;
import it.polimi.ingsw.exceptions.action.IncompleteListException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.enumeration.MarbleColour;

import java.util.ArrayList;

public class MarketAction extends Action {
    private int type;
    private int pos;
    private ArrayList<AtomicMarketAction> choices;
    private MarketDashboard market;
    private GameManager gameManager;

    /**
     *
     * @param type 0: user selected a row; 1: user selected a column
     * @param pos number of the row/column (starting from 1)
     * @param choices the choices made by the user for each marble (excluding red marbles)
     */
    public MarketAction(int type, int pos, ArrayList<AtomicMarketAction> choices, GameManager gameManager) {
        this.type = type;
        this.pos = pos;
        this.choices = choices;
        this.gameManager = gameManager;
        this.market = gameManager.getGame().getMarket();
    }

    /**
     *
     * @param player the player performing the action
     */
    @Override
    public void useAction(PlayerDashboard player) {
        MarketMarble[] marbles = new MarketMarble[0];

        // Getting marbles from the market
        if (type == 0) { // A row is selected
            try {
                marbles = market.getRow(pos);
            }
            catch(IndexOutOfBoundsException e) {
                throw new IllegalMarketPositionException();
            }
        }
        if (type == 1) { // A column is selected
            try {
                marbles = market.getColumn(pos);
            }
            catch(IndexOutOfBoundsException e) {
                throw new IllegalMarketPositionException();
            }
        }

        // Executing actions (useAction method will check if an action is legal)
        int j = 0; // current position in choices list
        // (different from i because choices doesn't contain elements associated to red marbles)
        for (MarketMarble marble : marbles) {
            if (marble.getColour() == MarbleColour.RED) {
                player.updatePathPosition();
            } else {
                try {
                    choices.get(j).useAction(marble, player, gameManager);
                } catch (IndexOutOfBoundsException e) {
                    throw new IncompleteListException();
                }
                j++;
            }
        }

        // Fixing grid
        market.fixGrid(type, pos);
    }

}
