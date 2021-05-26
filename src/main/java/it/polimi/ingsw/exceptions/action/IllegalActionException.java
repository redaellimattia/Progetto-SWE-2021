package it.polimi.ingsw.exceptions.action;

import it.polimi.ingsw.exceptions.MasterOfRenaissanceRuntimeException;
import it.polimi.ingsw.model.PlayerDashboard;

public class IllegalActionException extends MasterOfRenaissanceRuntimeException {
    public IllegalActionException(PlayerDashboard player) {
        super("This action is not permitted, you already did one main action.");
        player.setExceptionError("This action is not permitted, you already did one main action.");
    }
}
