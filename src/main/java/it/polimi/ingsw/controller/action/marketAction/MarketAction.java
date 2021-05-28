package it.polimi.ingsw.controller.action.marketAction;
import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.exceptions.action.IllegalMarketPositionException;
import it.polimi.ingsw.exceptions.action.IncompleteListException;
import it.polimi.ingsw.exceptions.action.MarketActionException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.enumeration.MarbleColour;

import java.util.ArrayList;

public class MarketAction extends Action {
    private int type;
    private int pos;
    private ArrayList<AtomicMarketAction> choices;
    private MarketDashboard market;
    private GameManager gameManager;
    private Storage tempStorage;
    private ArrayList<CounterTop> tempArrayDeposit;

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
     * Creates a copy of the user storage at the beginning of the MarketAction
     * that will be updated when user makes choices in order to perform validity controls before executing actions
     */
    public void initTempStorage(PlayerDashboard player) {
        ArrayList<CounterTop> tempStorageRows = new ArrayList<CounterTop>();
        tempArrayDeposit = new ArrayList<CounterTop>();
        // Create a COPY of each "regular" counterTop
        for(CounterTop c: player.getStorage().getShelvesArray()) {
            tempStorageRows.add(new CounterTop(c.getResourceType(), c.getContent()));
        }
        // Create a COPY of each additional counterTop
        for(CounterTop c: player.getArrayDeposit()) {
            tempArrayDeposit.add(new CounterTop(c.getResourceType(), c.getContent()));
        }
        tempStorage = new Storage(tempStorageRows.get(0), tempStorageRows.get(1), tempStorageRows.get(2));
    }

    /**
     *
     * @param player the player performing the action
     */
    @Override
    public boolean useAction(PlayerDashboard player) {
        MarketMarble[] marbles = new MarketMarble[0];

        // Getting marbles from the market
        if (type == 0) { // A row is selected
            try {
                marbles = market.getRow(pos);
            }
            catch(IndexOutOfBoundsException e) {
                throw new IllegalMarketPositionException(player);
            }
        }
        if (type == 1) { // A column is selected
            try {
                marbles = market.getColumn(pos);
            }
            catch(IndexOutOfBoundsException e) {
                throw new IllegalMarketPositionException(player);
            }
        }

        // Checking actions
        initTempStorage(player);
        int j = 0; // current position in choices list
        // (different from i because choices doesn't contain elements associated to red marbles)
        for (MarketMarble marble : marbles) {
            if (!(marble.getColour() == MarbleColour.RED)) {
                try {
                    if(!(choices.get(j).checkAction(marble, player, tempStorage, tempArrayDeposit))) {
                        throw new MarketActionException(player);
                    }
                } catch (IndexOutOfBoundsException e) {
                    throw new IncompleteListException(player);
                }
                j++;
            }
        }

        // Executing actions (useAction method will check if an action is legal)
        j = 0; // current position in choices list
        // (different from i because choices doesn't contain elements associated to red marbles)
        for (MarketMarble marble : marbles) {
            if (marble.getColour() == MarbleColour.RED) {
                player.updatePathPosition();
            } else {
                try {
                    choices.get(j).useAction(marble, player, gameManager);
                } catch (IndexOutOfBoundsException e) {
                    throw new IncompleteListException(player);
                }
                j++;
            }
        }

        // Fixing grid
        market.fixGrid(type, pos);
        return true;
    }

}
