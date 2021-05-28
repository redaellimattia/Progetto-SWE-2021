package it.polimi.ingsw.exceptions.action;

import it.polimi.ingsw.controller.PlayerTurnManager;
import it.polimi.ingsw.exceptions.MasterOfRenaissanceRuntimeException;
import it.polimi.ingsw.model.PlayerDashboard;

public class ProductionAlreadyDoneException extends MasterOfRenaissanceRuntimeException {
    public ProductionAlreadyDoneException(PlayerDashboard p) { super("Production already used in this turn!");
        PlayerTurnManager.resetMainAction();
        p.setMainActionError("Production already used in this turn!");
    }
}
