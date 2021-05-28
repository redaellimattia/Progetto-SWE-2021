package it.polimi.ingsw.exceptions.action;

import it.polimi.ingsw.controller.PlayerTurnManager;
import it.polimi.ingsw.exceptions.MasterOfRenaissanceRuntimeException;
import it.polimi.ingsw.model.PlayerDashboard;

public class MarketActionException extends MasterOfRenaissanceRuntimeException {
    public MarketActionException(PlayerDashboard p) {
        super("You made some not allowed choices when getting resources from the market!");
        PlayerTurnManager.resetMainAction();
        p.setMainActionError("You made some not allowed choices when getting resources from the market!");
    }
}
