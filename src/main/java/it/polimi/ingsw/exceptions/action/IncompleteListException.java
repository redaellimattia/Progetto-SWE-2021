package it.polimi.ingsw.exceptions.action;

import it.polimi.ingsw.exceptions.MasterOfRenaissanceRuntimeException;
import it.polimi.ingsw.model.PlayerDashboard;

public class IncompleteListException extends MasterOfRenaissanceRuntimeException {
    public IncompleteListException(PlayerDashboard p) {
        super("The list of choices is incomplete.");
        p.setExceptionError("The list of choices is incomplete.");
    }
}
