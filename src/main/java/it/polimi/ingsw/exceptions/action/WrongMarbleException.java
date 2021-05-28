package it.polimi.ingsw.exceptions.action;

import it.polimi.ingsw.controller.PlayerTurnManager;
import it.polimi.ingsw.exceptions.MasterOfRenaissanceRuntimeException;
import it.polimi.ingsw.model.MarketMarble;
import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.enumeration.MarbleColour;

public class WrongMarbleException extends MasterOfRenaissanceRuntimeException {
    public WrongMarbleException(MarketMarble marble, PlayerDashboard player,PlayerTurnManager turnManager) {
        super("You cannot get a resource from a " + marble.getColour().name() + " marble.");
        turnManager.resetMainAction();
        player.setMainActionError("You cannot get a resource from a " + marble.getColour().name() + " marble.");
    }
}
