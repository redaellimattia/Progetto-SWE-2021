package it.polimi.ingsw.exceptions.action;

import it.polimi.ingsw.controller.PlayerTurnManager;
import it.polimi.ingsw.exceptions.MasterOfRenaissanceRuntimeException;
import it.polimi.ingsw.model.PlayerDashboard;

public class IncompleteListException extends MasterOfRenaissanceRuntimeException {
    public IncompleteListException(PlayerDashboard p, PlayerTurnManager turnManager) {
        super("The list of choices is incomplete.");
        turnManager.resetMainAction();
        p.setMainActionError("The list of choices is incomplete.");
    }
}
