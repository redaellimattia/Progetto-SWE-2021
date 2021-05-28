package it.polimi.ingsw.exceptions.action;

import it.polimi.ingsw.controller.PlayerTurnManager;
import it.polimi.ingsw.exceptions.MasterOfRenaissanceRuntimeException;
import it.polimi.ingsw.model.PlayerDashboard;

public class InvalidRowException extends MasterOfRenaissanceRuntimeException {
    public InvalidRowException(PlayerDashboard p,PlayerTurnManager turnManager) {
        super("Invalid storage row number.");
        turnManager.resetMainAction();
        p.setMainActionError("Invalid storage row number.");
    }
}
