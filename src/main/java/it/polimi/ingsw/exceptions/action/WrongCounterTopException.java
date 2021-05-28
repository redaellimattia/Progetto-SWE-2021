package it.polimi.ingsw.exceptions.action;

import it.polimi.ingsw.controller.PlayerTurnManager;
import it.polimi.ingsw.exceptions.MasterOfRenaissanceRuntimeException;
import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.enumeration.Resource;

public class WrongCounterTopException extends MasterOfRenaissanceRuntimeException {
    public WrongCounterTopException(Resource res, PlayerDashboard p,PlayerTurnManager turnManager) {
        super("You cannot add a " + res.name() + " into this CounterTop.");
        turnManager.resetMainAction();
        p.setMainActionError("You cannot add a " + res.name() + " into this CounterTop.");
    }
}
