package it.polimi.ingsw.exceptions.action;

import it.polimi.ingsw.exceptions.MasterOfRenaissanceRuntimeException;
import it.polimi.ingsw.model.PlayerDashboard;

public class NoWhiteMarbleException extends MasterOfRenaissanceRuntimeException {
    public NoWhiteMarbleException(PlayerDashboard p) {
        super("You must select a white marble to use the Leader Card ability.");
        p.setExceptionError("You must select a white marble to use the Leader Card ability.");
    }
}
